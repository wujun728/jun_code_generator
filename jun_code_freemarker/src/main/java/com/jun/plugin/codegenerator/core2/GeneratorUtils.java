package com.jun.plugin.codegenerator.core2;

import com.jun.plugin.codegenerator.core2.model.Column;
import com.jun.plugin.codegenerator.core2.model.ModelInfo;
import com.jun.plugin.codegenerator.core2.model.Table;
import com.jun.plugin.common.generator.FieldInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.util.StringUtils;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class GeneratorUtils {

	public static Logger log = Logger.getLogger(GeneratorUtils.class.toString());

	public static void main(String[] args) throws IOException {
		 //调用该方法即可
    	log.info("开始生成代码");
    	GeneratorUtils.builder();
//    	GeneratorUtils.builderV2();
        //打开文件夹
//        Runtime.getRuntime().exec("cmd.exe /c start "+GenUtils.PROJECT_PATH + GenUtils.PACKAGE_BASE.replace(".", "/"));
        log.info("代码生成完成");
	}

	// 配置文件
	private static Properties props = new Properties();

	// pojoPackage
	public static String PACKAGE_POJO;

	// mapperPackage
	public static String PACKAGE_MAPPER;

	// pojoPackage
	public static String PACKAGE_BASE;

	// serviceInterfacePackage
	public static String PACKAGE_SERVICE_INTERFACE;

	// serviceInterfaceImplPackage
	public static String PACKAGE_SERVICE_INTERFACE_IMPL;

	// controllerPackage
	public static String PACKAGE_CONTROLLER;

	// feignPackage
	public static String PACKAGE_FEIGN;

	// 数据库账号
	public static String UNAME;

	// 项目路径
	public static String PROJECT_PATH;

	// 是否使用swagger
	public static Boolean SWAGGER;

	// 服务名字
	public static String SERVICENAME;

	// swagger-ui路径
	public static String SWAGGERUI_PATH;

	public static String OUTROOT;
	public static String TEMPLATE_PATH;
	public static String TEMPLATE_NAME;
	public static String TABLEREMOVEPREFIXES;
	public static String ROWREMOVEPREFIXES;
	public static String SKIPTABLE;
	public static String INCLUETABLES;

	static {
		try {
			// 加载配置文件
			InputStream is = GeneratorUtils.class.getClassLoader().getResourceAsStream("config.properties");

			// 创建Properties对象
			props.load(is);
			PACKAGE_BASE = props.getProperty("basePackage");
			TEMPLATE_PATH = GeneratorUtils.class.getClassLoader().getResource("").getPath().replace("/target/classes/",
					"") + "/src/main/resources/" + props.getProperty("template_path");
			TEMPLATE_NAME = props.getProperty("template_path");
			TABLEREMOVEPREFIXES = props.getProperty("tableRemovePrefixes");
			ROWREMOVEPREFIXES = props.getProperty("rowRemovePrefixes");
			SKIPTABLE = props.getProperty("skipTable");
			INCLUETABLES = props.getProperty("inclueTables");

			// 获取对应的配置信息
			PACKAGE_POJO = props.getProperty("pojoPackage");
			PACKAGE_MAPPER = props.getProperty("mapperPackage");
			PACKAGE_SERVICE_INTERFACE = props.getProperty("serviceInterfacePackage");
			PACKAGE_SERVICE_INTERFACE_IMPL = props.getProperty("serviceInterfaceImplPackage");
			PACKAGE_CONTROLLER = props.getProperty("controllerPackage");
			PACKAGE_FEIGN = props.getProperty("feignPackage");
			UNAME = props.getProperty("uname");
			SWAGGER = Boolean.valueOf(props.getProperty("enableSwagger"));
			SERVICENAME = props.getProperty("serviceName");
			SWAGGERUI_PATH = props.getProperty("swaggeruipath");
			// 工程路径
			PROJECT_PATH = GeneratorUtils.class.getClassLoader().getResource("").getPath().replace("/target/classes/",
					"") + "/src/main/java/";

			// 加载数据库驱动
			Class.forName(props.getProperty("driver"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 模板构建
	 */
	public static void builder() {
		try {
			// 获取数据库连接
			Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("uname"),
					props.getProperty("pwd"));
			DatabaseMetaData metaData = conn.getMetaData();

			// 获取数据库类型：MySQL
			String databaseType = metaData.getDatabaseProductName();

			// 针对MySQL数据库进行相关生成操作
			if (databaseType.equals("MySQL")) {
				// 获取所有表结构
				ResultSet tableResultSet = metaData.getTables(null, "%", "%", new String[] { "TABLE" });

				// 获取数据库名字
				String database = conn.getCatalog();

				// 循环所有表信息
				while (tableResultSet.next()) {
					// 获取表名
					String tableName = tableResultSet.getString("TABLE_NAME");
					// 名字操作,去掉tab_,tb_，去掉_并转驼峰
					String table = GeneratorUtils.replace_(GeneratorUtils.replaceTab(tableName));
					// 大写对象
					String Table = GeneratorUtils.firstUpper(table);

					// 需要生成的Pojo属性集合
					List<ModelInfo> models = new ArrayList<ModelInfo>();
					// 所有需要导包的类型
					Set<String> typeSet = new HashSet<String>();

					// 获取表所有的列
					ResultSet cloumnsSet = metaData.getColumns(database, UNAME, tableName, null);
					// 获取主键
					ResultSet keySet = metaData.getPrimaryKeys(database, UNAME, tableName);
					String key = "", keyType = "";
					while (keySet.next()) {
						key = keySet.getString(4);
					}

					while (cloumnsSet.next()) {
						// 列的描述
						String remarks = cloumnsSet.getString("REMARKS");
						// 获取列名
						String columnName = cloumnsSet.getString("COLUMN_NAME");
						// 处理列名
						String propertyName = GeneratorUtils.replace_(columnName);
						// 获取类型，并转成JavaType
						String javaType = GeneratorUtils.getType(cloumnsSet.getInt("DATA_TYPE"));
						// 创建该列的信息
						models.add(new ModelInfo(javaType, GeneratorUtils.simpleName(javaType), propertyName,
								GeneratorUtils.firstUpper(propertyName), remarks, key.equals(columnName), columnName,
								cloumnsSet.getString("IS_AUTOINCREMENT")));
						// 需要导包的类型
						typeSet.add(javaType);
						// 主键类型
						if (columnName.equals(key)) {
							keyType = GeneratorUtils.simpleName(javaType);
						}

					}

					// 创建该表的JavaBean
					Map<String, Object> modelMap = new HashMap<String, Object>();
					modelMap.put("table", table);
					modelMap.put("Table", Table);
					modelMap.put("swagger", SWAGGER);
					modelMap.put("TableName", tableName);
					modelMap.put("models", models);
					modelMap.put("typeSet", typeSet);
					// 主键操作
					modelMap.put("keySetMethod", "set" + GeneratorUtils.firstUpper(GeneratorUtils.replace_(key)));
					modelMap.put("keyType", keyType);
					modelMap.put("serviceName", SERVICENAME);
					
					//************************************************************************
					//************************************************************************
					
					//************************************************************************
					//************************************************************************

					// 创建JavaBean
					GeneratorUtils.batchBuilderAll(modelMap);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/***
     * 模板构建
     */
    public static void builderV2(){
        try {
            //获取数据库连接
            Connection conn = DriverManager.getConnection(GeneratorUtils.props.getProperty("url"),GeneratorUtils.props.getProperty("uname"),GeneratorUtils.props.getProperty("pwd"));
            DatabaseMetaData metaData = conn.getMetaData();
            
            System.out.println("获取数据库的产品名称: " + metaData.getDatabaseProductName());
            System.out.println("获取数据库的版本号: " + metaData.getDatabaseProductVersion());
            System.out.println("获取数据库的用户名: " + metaData.getUserName());
            System.out.println("获取数据库的URL: " + metaData.getURL());
            System.out.println("获取数据库的驱动名称: " + metaData.getDriverName());
            System.out.println("获取数据库的驱动版本号: " + metaData.getDriverVersion());
            System.out.println("查看数据库是否只允许读操作: " + metaData.isReadOnly());
            System.out.println("查看数据库是否支持事务: " + metaData.supportsTransactions());

            //获取数据库类型：MySQL
            String databaseType = metaData.getDatabaseProductName();
            log.info("数据库类型："+databaseType);
            //针对MySQL数据库进行相关生成操作
            if(databaseType.equals("MySQL")){
                //获取所有表结构
                ResultSet tableResultSet = metaData.getTables(null, "%", "%", new String[]{"TABLE","REMARKS"});//"","REMARKS"

                //获取数据库名字
                String database = conn.getCatalog();

                //循环所有表信息
                while (tableResultSet.next()){
                    //获取表名
                    String tableName=tableResultSet.getString("TABLE_NAME");
                    String tableComment=tableResultSet.getString("REMARKS");
                    //名字操作,去掉tab_,tb_，去掉_并转驼峰
                    String className = GeneratorUtils.replace_(GeneratorUtils.replaceTab(tableName));
                    //大写对象
                    String classNameFirstUpper =GeneratorUtils.firstUpper(className);
                    
                    if(GeneratorUtils.checkTab(tableName)) {
                    	continue;
                    }
                    log.info("当前表名："+tableName);
                    //模板V0
                    //需要生成的Pojo属性集合
                    List<Map<String,Object>> colList = new ArrayList<Map<String,Object>>();
                    List<ModelInfo> models = new ArrayList<ModelInfo>();
                    List<Column> columnList = new ArrayList<Column>();
                    List<Column> primaryKeyColumns = new ArrayList<Column>();
                    
                    //模板V1
                    // field List
                    List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
                    
                    //所有需要导包的类型
                    Set<String> typeSet = new HashSet<String>();

                    //获取表所有的列
                    ResultSet cloumnsSet = metaData.getColumns(database, GeneratorUtils.UNAME, tableName, null);
                    //获取主键
                    ResultSet keySet = metaData.getPrimaryKeys(database, GeneratorUtils.UNAME, tableName);
                    String key ="",keyType="";
                    while (keySet.next()){
                        key=keySet.getString(4);   
                        String columnName = keySet.getString("COLUMN_NAME");// 列名
                    }

                    while (cloumnsSet.next()){
                        
                       String remarks = cloumnsSet.getString("REMARKS");//列的描述
                       String columnName = cloumnsSet.getString("COLUMN_NAME"); //获取列名
                       String javaType = GeneratorUtils.getType(cloumnsSet.getInt("DATA_TYPE"));//获取类型，并转成JavaType
                       int COLUMN_SIZE  = cloumnsSet.getInt("COLUMN_SIZE");//获取
                       String TABLE_SCHEM  = cloumnsSet.getString("TABLE_SCHEM");//获取
                       String COLUMN_DEF  = cloumnsSet.getString("COLUMN_DEF");//获取
                       int NULLABLE   = cloumnsSet.getInt("NULLABLE");//获取

                       String propertyName = GeneratorUtils.replace_(GeneratorUtils.replaceRow(columnName));//处理列名，驼峰
                       
                       //创建该列的信息
                       models.add(new ModelInfo(javaType, GeneratorUtils.simpleName(javaType),propertyName,
                    		   GeneratorUtils.firstUpper(propertyName),remarks, key.equals(columnName),columnName,
                    		   cloumnsSet.getString("IS_AUTOINCREMENT")));
                        typeSet.add(javaType);//需要导包的类型
                        if(columnName.equals(key)){
                            keyType=GeneratorUtils.simpleName(javaType);//主键类型,单主键支持
                        }
                        Map<String,Object> col = new HashMap<String,Object>();
                        col.put("javaType", javaType);
                        col.put("simpleType", GeneratorUtils.simpleName(javaType));
                        col.put("name", propertyName);
                        col.put("upperName", GeneratorUtils.firstUpper(propertyName));
                        col.put("desc", remarks);
                        col.put("id", key.equals(columnName));
                        col.put("column", columnName);
                        col.put("identity", cloumnsSet.getString("IS_AUTOINCREMENT"));
                        col.put("COLUMN_SIZE", COLUMN_SIZE);
                        col.put("COLUMN_DEF", COLUMN_DEF);
                        colList.add(col);

                        //V0
                        Column column = new Column();
                        column.setType(javaType);
                        column.setSimpleType(GeneratorUtils.simpleName(javaType));
                        column.setName(propertyName);
                        column.setUpperName(GeneratorUtils.firstUpper(propertyName));
                        column.setDesc(remarks);
                        column.setId(key.equals(columnName));
                        column.setColumn(columnName);
                        column.setLength(String.valueOf(COLUMN_SIZE));
                        column.setIdentity(cloumnsSet.getString("IS_AUTOINCREMENT"));
                        columnList.add(column);
                        
                        //V1
                        FieldInfo fieldInfo = new FieldInfo();
                        fieldInfo.setColumnName(columnName);
                        fieldInfo.setFieldName(propertyName);
                        fieldInfo.setFieldClass(GeneratorUtils.simpleName(javaType));
                        fieldInfo.setFieldComment(remarks);
                        fieldList.add(fieldInfo);
                    }

//                    //模板V0的modelMap，创建该表的JavaBean元数据
//                    Map<String,Object> modelMap = new HashMap<String,Object>();
//                    modelMap.put("table",table);
//                    modelMap.put("Table",Table);
//                    modelMap.put("swagger",SWAGGER);
//                    modelMap.put("TableName",tableName);
//                    modelMap.put("models",models);
//                    modelMap.put("typeSet",typeSet);
//                    //主键操作
//                    modelMap.put("keySetMethod","set"+CodeGenerator.firstUpper(CodeGenerator.replace_(key)));
//                    modelMap.put("keyGetMethod","get"+CodeGenerator.firstUpper(CodeGenerator.replace_(key)));
//                    modelMap.put("keyType",keyType);
//                    modelMap.put("serviceName",SERVICENAME);
                    
                    
                    //模板V1的classInfo，创建该表的JavaBean元数据
                    Map<String, Object> modelMap = new HashMap<String, Object>();
                    modelMap.put("isAutoImport", Boolean.TRUE);
                    
                    //模板V0
                    modelMap.put("authorName","wujun");
                    modelMap.put("packageName",GeneratorUtils.PACKAGE_BASE);
                    modelMap.put("returnUtil","ReturnInfo");
                    modelMap.put("returnUtilSuccess","ReturnInfo.ok");
                    modelMap.put("returnUtilFailure","ReturnInfo.error");
                    //兼容模板V0属性
                    modelMap.put("table",className);
                    modelMap.put("Table",classNameFirstUpper);
                    modelMap.put("swagger",GeneratorUtils.SWAGGER);
                    modelMap.put("TableName",tableName);
                    modelMap.put("models",models);
                    modelMap.put("typeSet",typeSet);
                    //兼容模板V0属性，主键操作
                    modelMap.put("keySetMethod","set"+GeneratorUtils.firstUpper(GeneratorUtils.replace_(key)));
                    modelMap.put("keyGetMethod","get"+GeneratorUtils.firstUpper(GeneratorUtils.replace_(key)));
                    modelMap.put("keyType",keyType);
                    modelMap.put("serviceName",GeneratorUtils.SERVICENAME);
//                    modelMap.put("isAutoImport","true");
                    modelMap.put("Swagger",Boolean.FALSE);
                    
                    Table classInfo = new Table();
                    classInfo.setTableName(tableName);
                    classInfo.setClassName(classNameFirstUpper);
                    classInfo.setClassComment(tableComment);
                    classInfo.setFieldList(fieldList);
                    classInfo.setColumnList(columnList);
                    modelMap.put("classInfo", classInfo);

                    GeneratorUtils.batchBuilderV2(modelMap);
                    log.info("正在生成模型："+modelMap);

                    //添加swagger路径映射
                    String format="string";
                    if(keyType.equalsIgnoreCase("integer") || keyType.equalsIgnoreCase("long")){
                        format="int64";
                    }
                }


//                log.info("正在生成swagger："+swaggerModelMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/***
	 * 构建Controller
	 * 
	 * @param modelMap
	 */
	public static void builder(Map<String, Object> modelMap, // 数据模型
			String templatePath, // 模板路径
			String templateFile, // 模板文件
			String storePath, // 存储路径
			String suffix) { // 生成文件后缀名字
		try {
			// 获取模板对象
			Template template = GeneratorUtils.loadTemplate(GeneratorUtils.class.getResource(templatePath).getPath(),
					templateFile);

			// 创建文件夹
			String path = GeneratorUtils.PROJECT_PATH + storePath.replace(".", "/");
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}

			// 创建文件
			GeneratorUtils.writer(template, modelMap, path + "/" + modelMap.get("Table") + suffix);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 创建模板对象
	 * 
	 * @param path
	 * @param ftl
	 * @return
	 * @throws Exception
	 */
	public static Template loadTemplate(String path, String ftl) throws Exception {
		// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
//		Configuration configuration = new Configuration(Configuration.getVersion());
		Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		// 第二步：设置模板文件所在的路径。
		configuration.setDirectoryForTemplateLoading(new File(path));
		// 第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("utf-8");
		// 第四步：加载一个模板，创建一个模板对象。
		Template template = configuration.getTemplate(ftl);
		return template;
	}

	/***
	 * 输出文件
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void writer(Template template, Map dataModel, String file) throws Exception {
		// 包参数
		dataModel.put("package_controller", GeneratorUtils.PACKAGE_CONTROLLER);
		dataModel.put("package_pojo", GeneratorUtils.PACKAGE_POJO);
		dataModel.put("package_mapper", GeneratorUtils.PACKAGE_MAPPER);
		dataModel.put("package_service", GeneratorUtils.PACKAGE_SERVICE_INTERFACE);
		dataModel.put("package_service_impl", GeneratorUtils.PACKAGE_SERVICE_INTERFACE_IMPL);

		// 创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		Writer out = new FileWriter(new File(file));
		// 调用模板对象的process方法输出文件。
		template.process(dataModel, out);
		// 关闭流。
		out.close();
	}

	// new builder 2021-08-14
	public static void builder(Map<String, Object> modelMap, String templatePath, String templateFile) {
		String templateName = templateFile.substring(0, templateFile.lastIndexOf("."));
		String storePath = GeneratorUtils.PACKAGE_BASE + "." + templateName; // 存储路径
		String suffix = templateName + ".java"; // 生成文件后缀名字
		GeneratorUtils.builder(modelMap, templatePath, templateFile, storePath, suffix);
	}

	/***
	 * 
	 */
	public static void batchBuilderAll(Map<String, Object> modelMap) {
		// 生成Controller层文件
		GeneratorUtils.builder(modelMap, "/template/controller", "Controller.java", GeneratorUtils.PACKAGE_CONTROLLER,
				"Controller.java");
		// 生成Service层文件
		GeneratorUtils.builder(modelMap, "/template/service", "Service.java", GeneratorUtils.PACKAGE_SERVICE_INTERFACE,
				"Service.java");
		// 生成ServiceImpl层文件
		GeneratorUtils.builder(modelMap, "/template/service/impl", "ServiceImpl.java",
				GeneratorUtils.PACKAGE_SERVICE_INTERFACE_IMPL, "ServiceImpl.java");
		// 生成Dao层文件
		GeneratorUtils.builder(modelMap, "/template/dao", "Mapper.java", GeneratorUtils.PACKAGE_MAPPER, "Mapper.java");
		// 生成Pojo层文件
		GeneratorUtils.builder(modelMap, "/template/pojo", "Pojo.java", GeneratorUtils.PACKAGE_POJO, ".java");
	}

	/***
	 * 构建
	 * 
	 * @param modelMap
	 */
	public static void batchBuilder(Map<String, Object> modelMap) {
		// 生成Controller层文件
		GeneratorUtils.builder(modelMap, "/template_v1/controller", "Controller.java");
		// 生成Dao层文件
		GeneratorUtils.builder(modelMap, "/template_v1/dao", "Mapper.java");
		// 生成Feign层文件
		GeneratorUtils.builder(modelMap, "/template_v1/feign", "Feign.java");
		// 生成Pojo层文件
		GeneratorUtils.builder(modelMap, "/template_v1/pojo", "Pojo.java");
		// 生成Service层文件
		GeneratorUtils.builder(modelMap, "/template_v1/service", "Service.java");
		// 生成ServiceImpl层文件
		GeneratorUtils.builder(modelMap, "/template_v1/service/impl", "ServiceImpl.java");

	}

	/***
	 * 构建 Java文件
	 * 
	 * @param modelMap
	 */
	public static void batchBuilderV2(Map<String, Object> modelMap) {
		List<Map<String, Object>> srcFiles = new ArrayList<Map<String, Object>>();
		getFile(GeneratorUtils.TEMPLATE_PATH, srcFiles);

		for (int i = 0; i < srcFiles.size(); i++) {
			HashMap<String, Object> m = (HashMap<String, Object>) srcFiles.get(i);
			Set<String> set = m.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if ((boolean) modelMap.get("Swagger") == true) {
					if (!key.contains(".json")) {
						continue;
					}
				}
				String templateFileName = key;
				String templateFileNameSuffix = key.substring(key.lastIndexOf("."), key.length());
				String templateFileNamePrefix = key.substring(0, key.lastIndexOf("."));
				String templateFilePathAndName = String.valueOf(m.get(key));
				String templateFilePath = templateFilePathAndName.replace("\\" + templateFileName, "");
				String templateFilePathMiddle = "";
				if (!templateFilePath.endsWith(GeneratorUtils.TEMPLATE_NAME.replace("/", "\\"))) {
					templateFilePathMiddle = templateFilePath
							.substring(templateFilePath.lastIndexOf("\\"), templateFilePath.length()).replace("\\", "");
				}
				if (key.contains(".json")) {
					log.info("templateFilePath=" + templateFilePath);
					;
					continue;
				}
				try {
					// 获取模板对象
					Template template = GeneratorUtils.loadTemplate(templateFilePath, templateFileName);
					String path = null;
					if (templateFileNameSuffix.equalsIgnoreCase(".java")) {
						// 创建文件夹
						path = GeneratorUtils.PROJECT_PATH + "/" + GeneratorUtils.PACKAGE_BASE.replace(".", "/") + "/"
								+ templateFileNamePrefix.toLowerCase();
					}
					if (templateFileNameSuffix.equalsIgnoreCase(".ftl")) {
						path = GeneratorUtils.PROJECT_PATH + "/" + GeneratorUtils.PACKAGE_BASE.replace(".", "/") + "/"
								+ templateFilePathMiddle + "/";
					}
					File file = new File(path);
					if (!file.exists()) {
						file.mkdirs();
					}
					String fileNameNew = templateFileNamePrefix
							.replace("${className}", String.valueOf(modelMap.get("Table")))
							.replace("${classNameLower}", String.valueOf(modelMap.get("Table")).toLowerCase());
					// 创建文件
					GeneratorUtils.writer(template, modelMap, path + "/" + fileNameNew);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	/***
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstUpper(String str) {
		if(!StringUtils.isEmpty(str) && str.length()>1){
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}else{
			return str;
		}
	}

	/**
	 * 首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLower(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	/***
	 * 移除tab_,tb_
	 * 
	 * @return
	 */
	public static String replaceTab(String str) {
		return str.replaceFirst("tab_", "").replaceFirst("tb_", "");
	}

	/***
	 * 将下划线替换掉
	 * 
	 * @param str
	 * @return
	 */
	public static String replace_(String str) {
		// 根据下划线分割
		String[] split = str.split("_");
		// 循环组装
		String result = split[0];
		if (split.length > 1) {
			for (int i = 1; i < split.length; i++) {
				result += firstUpper(split[i]);
			}
		}
		return result;
	}

	/***
	 * 根据java.sql.Types的值返回java的类型
	 * 
	 * @param value
	 * @return
	 */
	public static String getType(int value) {
		switch (value) {
		case -6:
			return "java.lang.Integer";
		case 5:
			return "java.lang.Integer";
		case 4:
			return "java.lang.Integer";
		case -5:
			return "java.lang.Long";
		case 6:
			return "java.lang.Float";
		case 8:
			return "java.lang.Double";
		case 1:
			return "java.lang.String";
		case 12:
			return "java.lang.String";
		case -1:
			return "java.lang.String";
		case 91:
			return "java.util.Date";
		case 92:
			return "java.util.Date";
		case 93:
			return "java.util.Date";
		case 16:
			return "java.lang.Boolean";
		default:
			return "java.lang.String";
		}
	}

	/***
	 * 去掉数据类型的包
	 * 
	 * @param type
	 * @return
	 */
	public static String simpleName(String type) {
		return type.replace("java.lang.", "").replaceFirst("java.util.", "");
	}

	/***
	 * 去掉数据类型的包，并且首字母小写
	 * 
	 * @param type
	 * @return
	 */
	public static String simpleNameLowerFirst(String type) {
		// 去掉前缀
		type = simpleName(type);
		// 将第一个字母转成小写
		return GeneratorUtils.firstLower(type);
	}

	public static String upperCaseFirstWord(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

//	public static void main(String[] args) {
//        // This is the path where the file's name you want to take.
//        String path = "D:\\workspace\\github\\jun_code_generator\\jun_code_helper\\code_generator\\src\\main\\resources\\template_ds";
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        getFile(path,list);
//    }
	
	public static Boolean checkTab(String str){
    	str = str.toLowerCase();
    	for(String x : GeneratorUtils.SKIPTABLE.split(",")){
    		if(str.contains(x.toLowerCase())){
    			return true;
    		}
    	}
    	if(GeneratorUtils.INCLUETABLES.equals("*")){
			return false;
		}
    	for(String x : GeneratorUtils.INCLUETABLES.split(",")){
    		if(str.contains(x.toLowerCase())){
    			return false;
    		}
    	}
        return true;
    }

	public static void getFile(String path, List<Map<String, Object>> list) {
		File file = new File(path);
		File[] array = file.listFiles();
		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				Map<String, Object> map = new HashMap<String, Object>();
				// only take file name
				// System.out.println("^^^^^" + array[i].getName());
				// take file path and name
				// System.out.println("*****" + array[i].getPath());
				map.put(array[i].getName(), array[i].getPath());
				list.add(map);
			} else if (array[i].isDirectory()) {
				getFile(array[i].getPath(), list);
			}
		}
	}
	public static String replaceRow(String str){
    	str = str.toLowerCase().replaceFirst("tab_","").replaceFirst("tb_","").replaceFirst("t_","");
    	for(String x : GeneratorUtils.ROWREMOVEPREFIXES.split(",")){
    		str = str.replaceFirst(x.toLowerCase(),"");
    	}
    	return str;
    }
}

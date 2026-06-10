package io.github.wujun728.admin.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * freemarker tool
 */
public class FreemarkerUtil {
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

    /**
     * freemarker config
     */
    private static Configuration freemarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    static {
        String templatePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        int wei = templatePath.lastIndexOf("WEB-INF/classes/");
        if (wei > -1) {
            templatePath = templatePath.substring(0, wei);
        }
        init2DirectoryForTemplateLoading(templatePath + "templates");
    }

    public static void init2ClassForTemplateLoading(String templatePath) {
        freemarkerConfig.setClassForTemplateLoading(FreemarkerUtil.class, templatePath);
        freemarkerConfig.setNumberFormat("#");
        freemarkerConfig.setClassicCompatible(true);
        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setLocale(Locale.CHINA);
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public static void init2DirectoryForTemplateLoading(String templateDirectoryPath) {
        try {
            freemarkerConfig.setDirectoryForTemplateLoading(new File(templateDirectoryPath));
            freemarkerConfig.setNumberFormat("#");
            freemarkerConfig.setClassicCompatible(true);
            freemarkerConfig.setDefaultEncoding("UTF-8");
            freemarkerConfig.setLocale(Locale.CHINA);
            freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (FileNotFoundException e) {
            logger.info("默认加载[\\templates]目录下模板失败，目录不存在。");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * process String
     */
    public static String processString(String templateName, Map<String, Object> params)
            throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(templateName);
        String htmlText = processTemplateIntoString(template, params);
        return htmlText;
    }

    /**
     * process Template Into String
     */
    public static String processTemplateIntoString(Template template, Object model)
            throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }

    /**
     * 解析字符串模板
     */
    public static String processStringTemplate(String templateStr, Map<String, Object> root) throws IOException, TemplateException {
        return processStringTemplate("temp", templateStr, root);
    }

    /**
     * 解析字符串模板
     */
    public static String processStringTemplate(String templateName, String templateStr, Map<String, Object> root) throws IOException, TemplateException {
        Configuration configuration = configuration();
        StringWriter stringWriter = new StringWriter();
        Template template = new Template(templateName, templateStr, configuration);
        template.process(root, stringWriter);
        return stringWriter.toString();
    }

    /**
     * 配置 freemarker configuration
     */
    private static Configuration configuration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }

}
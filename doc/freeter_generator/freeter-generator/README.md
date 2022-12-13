**项目介绍** 
- 飞特后台管理系统的超级代码生成器，可直接生成四种实体类，controller、service、dao、xml代码到eclipse或ij，并支持多表连接，支持mybatis-plus 减少70%以上的开发任务
- 目前只支持MySQL、下个版本计划支持oracle,并支持最新版mybatis-plus 3.0
<br>

**加强特点** 
- mapper文件可生成多表连接
- 实体类默认引入swagger-ui 的注解。
- 实体类默认引入日期类格式化 的注解。
- 实体类默认引入hibernate-validator的注解
- 操作特别简单，能灵活生成到主流ide里面
- 灵活的权限控制，可控制到页面或按钮
- 灵活的接口开发，满足绝大部分的前后端分离
- 友好的代码结构及注释，便于阅读及二次开发

<br>

**实体类设计思想** 
- entity 是数据库层do，通用操作实体类（普通增删改查）

- model  接收传参的实体类  取自ModelAndView 的model名称
     	   实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了） 
- vo     返回的视图类  主要是手机端接口返回的实体类 
	          （主要作用去除一些不必要的字段） 

- view  后端返回视图实体辅助类   
                 （通常后端关联的表或者自定义的字段需要返回使用）
<br> 

**部署程序步骤** 

1.git下载https://gitee.com/xcOschina/freeter-admin.git 项目,完成后导入到ide中

2.eclipse File import... Maven Existing Projects into Workspace 选择项目的根路径。

3.IDE 会下载maven依赖包，自动编译 如果有报错 请update project... jdk环境配置。

4.执行doc/mysql-test.sql文件，初始化数据【按需导入表结构及数据】

5.最后修改数据库连接参数,配置文件在src/main/resources/application.yml

6.启动项目，Eclipse、IDEA运行GeneratorApplication.java

7.浏览器输入http://127.0.0.1:8082/freeter-generator
<br>
<br>

 **软件需求** 
- JDK1.8
- MySQL5+
- Tomcat8+
- Maven3.0+

需要引入
			hibernate-validator
			<artifactId>hutool-all</artifactId>
			<artifactId>commons-beanutils</artifactId>
			<artifactId>springfox-swagger-ui</artifactId>
<br>

 **项目演示**
- 演示地址：<a href="http://47.106.39.83:8082/freeter-generator/#generator.html"  target="_blank">
47.106.39.83:8082/freeter-generator/#generator.html</a>
<br>

**如何交流、反馈、参与贡献？** 
- 开发文档：https://gitee.com/xcOschina/freeter-admin.git
- 官方免费QQ群：806251058<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=4469c242246546fbe5548083e31b154f5f27df10c777c9ace61b094fbf7d922f"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="飞特开源技术交流" title="飞特开源技术交流"></a>
- gitee仓库：https://gitee.com/xcOschina/freeter-admin.git
- github仓库：暂不考虑，支持国产
- 如需关注项目最新动态，请Watch、Star项目，同时也是对项目最好的支持
- 技术讨论、二次开发等咨询、问题和建议，请移步到官方免费QQ群，我会在第一时间进行解答和回复！
- 微信扫码并关注【飞特开源】，获得项目最新动态及更新提醒，暂未开放<br>

<br>
<br>
<br>

**代码生成器：**
![输入图片说明](http://img.cnadmart.com/20180621/9b7b21a26bb74536985b073488eae307.png "在这里输入图片标题")

http://img.cnadmart.com/20180621/d5d9635baf644d26ac80e04c8a122668.jpg
<br>


<br>

![捐赠](http://img.cnadmart.com/20180621/f4bb4447a6894653b2da80fcd745390a.jpg "捐赠") 
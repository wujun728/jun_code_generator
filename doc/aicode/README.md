lemur-generation
----------------------
思考业务,代码它写
----------------------
	
	Gen是我构思了挺久的一个代码生成项目,之前零零散散也写了不少代码生成,
	但是很多都不太理想不能够满足通用性和特殊定制性的需求,
	每次生成出来还要改一部分的代码,很难做到0修改,而这个就是为了做到让大家0修改上线的一个小系统,
	帮减少大家的开发时间,专注于业务开发.而不是写这些单纯的CURD.功能强大的代码生成器,
	点一下就可以完成我们想要的,定制容易,二次开发简单,可远程可以本地
	一个通用版本的代码生成器,不单单局限于自己用,可以分享,可以浏览别人的代码
	好的代码是可以共享,好的代码也是促进开发的

**QQ群**

	官网： http://www.afterturn.cn/
	邮箱： qrb.jueyue@foxmail.com
	QQ群:  364192721(满) 116844390
	

[VIP技术服务](https://lemur.taobao.com)

    提供一年的技术支持服务
    提供10次内的1V1服务,限1小时
    提供升级指导
    针对lemur提供的所有开源项目提供支持服务

**使用指南**

**[http://opensource.afterturn.cn/doc/lemur-gen.html](http://opensource.afterturn.cn/doc/lemur-gen.html)**

**线上环境,支持直接使用,不需本地安装,注册即可使用**

**[http://gen.afterturn.cn](http://gen.afterturn.cn)**

新增了模板代码在admin/resources/template下面,供大家参考修改,希望大家提供优秀的代码模板

数据类型和平台类型对比表
-----------------------
|数据库|数据库类型|java.sql.Types|输出类型|
|----|----|----|----|
|Oracle|blob|blob|byte[]|
| |char|char|String|
| |clob|clob|String|
| |date|date|Date|
| |number|decimal|BigDecimal|
| |long|longvarchar|String|
| |nclob,nvarchar2|other||
| |smallint|smallint|Integer|
| |timestamp|timestamp|Date|
| |raw|varbinary|byte[]|
| |varchar2|varchar|String|
|Sql server|bigint (2005,2008) |bigint|Integer|
| |timestamp,binary|binary|byte[]|
| |bit|bit|Boolean|
| |char,nchar,unqualified |char|String|
| |datetime|date|Date|
| |money,smallmoney,decimal|decimal|BigDecimal|
| |float (2005,2008)|double|Double|
| |float(2000)|float|Double|
| |int|integer|Integer|
| |image|longvarbinary|byte[]|
| |text,ntext,xml|longvarchar|byte[]|
| |numeric|numeric|BigDecimal|
| |real|real|Float|
| |smallint|smallint|Short|
| |smalldatetime|timestamp|Date|
| |tinyint|tinyint|byte|
| |varbinary|varbinay|byte[]|
| |nvarchar,varchar|varchar|String|
|DB2|bigint|bigint|Long|
| |blob|blob|byte[]|
| |character,graphic|char|String|
| |clob|clob|String|
| |date|date|Date|
| |decimal|decimal|BigDecimal|
| |double|double|Double|
| |integer|integer|Integer|
| |longvargraphic,longvarchar|longvarchar|byte[]|
| |real|real|Long|
| |smallint|smallint|Short|
| |time|time|Date|
| |timestamp|timestamp|Date|
| |vargraphic,varchar|varchar|String|
|MySQL|bigint|bigint|Long|
| |tinyblob|binary|byte[]|
| |int|integer|Integer|
| |bit|bit|Boolean|
| |enum,set,char|char|String|
| |date,year|date|Date|
| |decimal,numeric|decimal|BigDecimal|
| |double,real|double|Double|
| |mediumint,int|integer|Integer|
| |blob,mediumblob,longblob|blob|byte[]|
| |float|real|Float|
| |smallint|smallint|Short|
| |time|time|Date|
| |timestamp,datetime|timestamp|Date|
| |tinyint|tinyint|byte|
| |varbinary,binary|varbinay|byte[]|
| |varchar,tinytext,text|varchar|String|


功能
---------
|功能|子功能|进度|开始时间|版本支持|
|----|----|----|----|----|
|数据库管理| |80%|2017-9-01|1.0|
| | CURD|100%|2017-9-01|1.0|
| | 密码加密| | | |
|表管理| |60%| | |
| |CURD| 100%| | |
| |数据库生成表| 100%| | |
| |SQL生成表[已支持:mysql,db2,oracle]| 50%|2018-01-29| |
| |JSON生成表| | | |
| |XML生成表| | | |
|模板管理| |80%|2017-9-01|1.0|
| | CURD|100%|2017-9-01|1.0|
| |语法高亮|100%|2017-9-01|1.0|
| |历史版本|100%|2018-01-29|1.2|
|模板组管理| |100%|2017-9-01|1.0|
| | CURD|100%|2017-9-01|1.0|
| |分享|80% | | 1.2|
|参数管理| |100%|2017-9-01|1.0|
| | CURD|100%|2017-9-01|1.0|
|代码生成| |30%|2017-9-01|1.0|
| | Mysql|100%|2017-9-01|1.0|
| | Oracle| | | |
| | SqlServer| | | |
| | PostgreSQL| | | |
| | DB2| | | |




启动方法
-----------
    1. 下载代码
    2. 导入gen.sql的SQL
    3.运行 GenApplication
    4.访问 localhost
    5.登录 admin/111111

***使用方法***

    1.在数据库管理,新建数据库连接
    2.模板分组建立自己的模板分组
    3.参数管理建立自己的参数
    4.建立自己的模板
    5.代码生成,选择想要生成的表

版本
---------------------------------------------------------------------------------
 - 1.1.0
    - 表管理 
    - 数据库导入
    - SQL导入
    - 代码生成
 - 1.0.1
    - 基础版本
    - 数据库管理
    - 数据库生成 

界面演示
----------------------------------------------------------------------------------
**数据库生成**
![代码生成界面](https://git.oschina.net/uploads/images/2017/0913/214120_a097692e_69288.png "gencode.png")
**DB导入**
![输入图片说明](https://static.oschina.net/uploads/img/201710/27180007_3cHi.png "在这里输入图片标题")
![输入图片说明](https://static.oschina.net/uploads/img/201710/27173859_57Vh.png "在这里输入图片标题")

**配置编辑**

![编辑界面](https://static.oschina.net/uploads/img/201710/27180151_XbfW.png "编辑界面")

**字段编辑**

![字段编辑](https://static.oschina.net/uploads/img/201710/27180302_Y23y.png "字段编辑")
**SQL导入**


![输入图片说明](https://static.oschina.net/uploads/img/201710/27174109_UeHH.png "在这里输入图片标题")

** 配置表生成代码 **

![输入图片说明](https://static.oschina.net/uploads/img/201710/27174217_sRvy.png "在这里输入图片标题")
![组](https://git.oschina.net/uploads/images/2017/0913/215434_b40e7468_69288.png "group.png")
![db](https://git.oschina.net/uploads/images/2017/0913/215453_fb373cfc_69288.png "db.png")
![tt](https://git.oschina.net/uploads/images/2017/0913/215501_81a29e95_69288.png "params.png")
![ttt](https://git.oschina.net/uploads/images/2017/0913/215537_c0e2a3b6_69288.png "template.png")
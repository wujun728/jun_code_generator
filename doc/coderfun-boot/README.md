 
演示地址：<a href="http://106.15.195.9:8080/admin/" target="_blank">http://106.15.195.9:8080/admin/</a><br>
项目文档：<a href="https://www.kancloud.cn/coderfun-boot/coderfun-boot-zh/868497" target="_blank">https://www.kancloud.cn/coderfun-boot/coderfun-boot-zh/868497</a><br>
在飞速变化的互联网时代，时间是最大的成本和代价。开源的网站基础开发平台已经多如牛毛了，有思想的项目并不多。从互联网创业浪潮一路走来，总结这几年的开发经验，在空闲时间将之前做的项目经行重构，coderfun-boot便诞生了。本项目最大的特色就是，快速开发。一张业务表的增删改查（后台管理接口+后台管理页面+对外接口），30分钟足矣。
 
曾经，我用一周开发一个点餐系统，用不到一个月开发一个进销存系统，只有我一个人。[fieldmeta项目](https://gitee.com/klguang/fieldmeta)也是功不可没。可能你会说，你做的是demo(假项目)吧，手动捂脸哈哈哈。事实上，一年半过去了，这些系统在多个地方依然运行良好。你的代码在运行，总是一件幸福的事情。
 
 
## 项目特点

- maven模块化开发
- 通用网站开发脚手架，用户、角色、权限、菜单、字典管理
- 支持集群，基于redis session 共享，阿里云oss做文件存储
- klg-jpa快捷增删改查封装
- easyui快捷增删改查封装
- springmvc 统一异常处理和错误码标准规范
- Aop日志模块
 
 
## 技术选型

- 运行环境：jdk1.7
- 数据库：mysql 5.7
- java框架：spring mvc+spring-data-jpa+shiro
- 前端框架：easyui 1.5.3
 

关于easyui+jpa的技术选型，可能很多人会吐槽，不过这里我说两句：

1. easyui并不过时，很适合做管理系统，界面美观，并且适合做结构复杂、数据量多的页面，组件丰富，文档完善。
2. jpa运行效率并不低，或者说对系统的使用根本没有影响。缓存、cdn才是最主要需要考虑的。
3. 脱离应用场景说技术选型，没有意义。对于个人独立开发者（接私活哈哈），中小企业，甚至大公司做系统原型，快速开发是很重要的。
 
 
 
## 快速上手
1.klg-jpa 便捷查询，详见[klg-jpa项目](https://gitee.com/klguang/klg-jpa)

```
@ResponseBody
@RequestMapping("/findpage")
public EasyUIPage findpage(
	@RequestParam int page,
	@RequestParam int rows,
	@RequestParam(required=false) Long employeeid,
	@RequestParam(required=false) String typeCode,
	@RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
	@RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
	Pageable pageable=new PageRequest(page<1?0:page-1, rows, new Sort(Direction.DESC,"numId"));

	Page<DrugOut> pageData=drugOutService.findPage(pageable, 
		AExpr.eq(DrugOut_.employeeid, employeeid).igEmpty(),// igEmpty()忽略空值，包括null和""
		AExpr.eq(DrugOut_.typeCode, typeCode).igEmpty(),
		AExpr.gte(DrugOut_.saledate, startDate).igEmpty(),//大于
		AExpr.lte(DrugOut_.saledate, endDate).igEmpty());//小于

	return new EasyUIPage(pageData);
}
```

2.easyui 表单增删改查封装，详见 [easyui增删改查封装文档](https://www.kancloud.cn/coderfun-boot/coderfun-boot-zh/868509)

```
	var dataTable = new DataTable({
		$datagrid_table :$("#datagrid-table"),
		$data_form_dialog : $("#data-form-dlg"),
		$data_form : $("#data-form"),
		data_form_name : "测评项目",
		
		addOpt : {
			url : adminActionPath+"/project/add"
		},
		editOpt : {
			url : adminActionPath+"/project/edit"
		},
		removeOpt : {
			url : adminActionPath+"/project/delete"
		},
		saveOpt : {},
		searchOpt : {
			$searchForm : $("#search-form"),
		}
	});
```

## 部署说明
演示地址：[http://106.15.195.9:8080/admin/](http://106.15.195.9:8080/admin/)<br>
admin：111111<br>
- 本项目依赖：<br>
https://gitee.com/klguang/coderfun-bom<br>
https://gitee.com/klguang/xutils<br>
https://gitee.com/klguang/klg-jpa<br>
将上面项目下载到本地，并执行maven install；<br>
coderfun-boot-web 是项目入口，可maven install打成war包，或直接在eclipse 中运行。
 
- 修改配置文件application.properties<br>
1.数据库配置，项目根目录 database 文件下的coderfun-boot.sql导入数据库（设置编码为UTF-8）；<br>
2.redis<br>
3.阿里云oss<br>

## 更新日志
2018-12-06 发布Alpha 0.0.1-SNAPSHOT

## 项目预览
![用户管理](https://images.gitee.com/uploads/images/2018/1203/183607_d68e8ebf_1063744.png "user.png")
![角色](https://images.gitee.com/uploads/images/2018/1203/183629_a73ca81c_1063744.png "role.png")
![登录日志](https://images.gitee.com/uploads/images/2018/1203/183735_b8298cef_1063744.png "loginlog.png")
![系统日志](https://images.gitee.com/uploads/images/2018/1203/183752_cebe210f_1063744.png "systemlog.png")
<br>
<br>
![菜单管理](https://images.gitee.com/uploads/images/2018/1203/183805_7169e2ba_1063744.png "menu.png")
![权限管理](https://images.gitee.com/uploads/images/2018/1203/183714_d31294e8_1063744.png "permission.png")
![数据字典](https://images.gitee.com/uploads/images/2018/1203/183725_e95d2af3_1063744.png "code.png")

#### 社群
qq群：743769300
<br>
![coderfun](https://images.gitee.com/uploads/images/2019/0810/023723_afe8ac12_1063744.jpeg)
<br>
进群须知：star、watch、fork项目，可进群

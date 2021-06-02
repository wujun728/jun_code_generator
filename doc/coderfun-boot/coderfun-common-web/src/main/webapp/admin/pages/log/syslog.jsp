<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<title>系统日志</title>
   		<c:import url="/admin/pages/common/headsource.jsp"/>
   		<style type="text/css">
   			#detail-content{
   				margin-left: 5px;
   				margin-right: 5px;
   			}
   			#detail-content .log-detail{
   				margin: 0 0 30px 0;
   				padding: 0;
   			}
   			#detail-content hr{
   				margin-bottom: 10px;
   			}
   		</style>		
  	</head>
<body>
<c:import url="/admin/pages/common/loading.jsp"/>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',collapsible:false,border:false" title="系统日志" style="height: 55px"> 
			<div id="toolbar">
			<div class="easyui-panel"
			    data-options="collapsible:true,minimizable:true">
				<form id="search-form" class="search-form" enctype="multipart/form-data">    
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
						style="color: red"  onclick="resetForm('search-form')">条件重置</a>	
	
					<label>操作人</label>
					<input name="opusername" style="width:125px;" type="text">
					<span class="inline-clear"></span>
			
					<label>操作名称</label>
					<input name="name" style="width:125px;" type="text">
					<span class="inline-clear"></span>
					
					<label>所属模块</label>
					<input name="moduleCode" class="easyui-combobox"
						data-options="valueField:'code',textField:'name',editable:false,panelHeight:'auto',
								enableNull:true,codeClass:'log_module'">
					<span class="inline-clear"></span>
	
					<label>是否成功</label>
					<input name="successed" class="easyui-combobox"
						data-options="valueField:'code',textField:'name',editable:false,panelHeight:'auto',
								enableNull:true,codeClass:'yes_or_no'">
					<span class="inline-clear"></span>
	
					
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="logsearch()">查询</a>
			     </form>
			</div></div>
		</div>
		<div data-options="region:'center',border:false">
			<table id="datagrid-table" class="easyui-datagrid"
				data-options="
					rownumbers		: true,
					singleSelect	: true,
					fitColumns		: true, 
					url				: adminActionPath + '/syslog/findpage',
					fit				: true,
					pagination		: true,
					pageSize		: 15,
					pageList        : [10,15,20,25,30],
					showFooter		: true,
					onLoadSuccess	: selectFirstRow,
					onSelect      	: viewLogDetail,
					idField			: 'id'">
				<thead>
					<tr>
						<th data-options="field:'name',width:80,align:'left',formatter:complexCol">操作名称</th>
						<th data-options="field:'moduleCode',width:80,align:'left',formatter:codeCol,codeClass:'log_module'">所属模块</th>
						<th data-options="field:'successed',width:80,align:'left',formatter:codeCol,codeClass:'yes_or_no'">是否成功</th>
						
						<th data-options="field:'opusername',width:80,align:'left',formatter:complexCol">操作人</th>
						<th data-options="field:'optime',width:140,align:'left',formatter:EasyUiDateTime">操作时间</th>				
						<th data-options="field:'opip',width:120,formatter:complexCol">ip</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'east',border:false" style="width:45%;" title="详情">
    		<div id="detail-content"></div>
		</div>
	</div>





	<script type="text/html" id="detail-template">
    			<h3>Operating System</h3><hr>
				<dir class="log-detail">{{os}}</dir>
				<h3>Browser</h3><hr>
				<dir class="log-detail">{{browser}}</dir>
				<h3>Method</h3><hr>
    			<dir class="log-detail">{{method}}</dir>
    			<h3>Prameters</h3><hr>
    			<dir class="log-detail"><pre>{{params}}<pre></dir>
    			<h3>Message</h3><hr>
    			<dir class="log-detail">{{message}}</dir>
	</script>
	<script type="text/javascript" src="${root}/static/plugins/art-template-4.13.2.js"></script>


	<script type="text/javascript">
		function logsearch(){
			$("#datagrid-table").datagrid("load",$("#search-form").serializeJson());
		}
		function viewLogDetail(index, row){
			var html = template('detail-template', row);
			$("#detail-content").html(html);
		}
		
		function selectFirstRow(data){
			if (data.rows.length > 0) {
				$("#datagrid-table").datagrid("selectRow",0);
			}else{
				$("#detail-content").html("");
			}
		}
		
	</script>
	

</body>
</html>
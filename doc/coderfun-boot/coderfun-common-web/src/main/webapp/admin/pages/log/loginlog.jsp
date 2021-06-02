<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<title></title>
   		<c:import url="/admin/pages/common/headsource.jsp"/>		

  	</head>
<body>
<c:import url="/admin/pages/common/loading.jsp"/>
	<table id="datagrid-table" class="easyui-datagrid" title="系统日志"
		data-options="
			rownumbers		: true,
			singleSelect	: true,
			fitColumns		: true, 
			url				: adminActionPath + '/loginlog/findpage',
			toolbar			: '#toolbar',
			fit				: true,
			pagination		: true,
			pageSize		: 15,
			pageList        : [10,15,20,25,30],
			showFooter		: true,
			idField			: 'id'">
		<thead>
			<tr>
				<th data-options="field:'opusername',width:100,align:'left',formatter:complexCol">用户名</th>
				<th data-options="field:'successed',width:60,align:'left',formatter:codeCol,codeClass:'yes_or_no'">是否成功</th>
				<th data-options="field:'message',width:160,align:'left',formatter:complexCol">消息</th>
				
				<th data-options="field:'opip',width:120,formatter:complexCol">ip</th>
				<th data-options="field:'optime',width:120,align:'left',formatter:EasyUiDateTime">操作时间</th>

				<th data-options="field:'os',width:70,formatter:complexCol">操作系统</th>
				<th data-options="field:'browser',width:120,formatter:complexCol">浏览器</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<div class="easyui-panel"
		    data-options="collapsible:true,minimizable:true">
			<form id="search-form" class="search-form" enctype="multipart/form-data">    
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
					style="color: red"  onclick="resetForm('search-form')">条件重置</a>	

				<label>用户名</label>
				<input name="opusername" style="width:125px;" type="text">
				<span class="inline-clear"></span>

				<label>是否成功</label>
				<input name="successed" class="easyui-combobox"
					data-options="valueField:'code',textField:'name',editable:false,panelHeight:'auto',
							enableNull:true,codeClass:'yes_or_no'">
				<span class="inline-clear"></span>

				
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="logsearch()">查询</a>
		     </form>
		</div>
	</div>
	
	<script type="text/javascript">
		function logsearch(){
			$("#datagrid-table").datagrid("load",$("#search-form").serializeJson());
		}
	</script>
</body>
</html>
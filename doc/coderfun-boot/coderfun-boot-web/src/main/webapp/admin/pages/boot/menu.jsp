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
<table id="datagrid-table" class="easyui-treegrid" style="width:600px;height:400px"   
        data-options="
        	url				: adminActionPath + '/boot/permission/getAllMenus',
        	loadFilter		: menuLoadFilter,
        	idField			: 'id',
        	treeField		: 'name',
        	rownumbers		: true,
			fitColumns		: true, 
			fit				: true,
			toolbar			: '#toolbar',
			onDblClickRow   : function(){dataTable.edit();}">   
    <thead>   
        <tr>   
            <th data-options="field:'name',width:80">菜单名称</th>   
            <th data-options="field:'url',width:160">路径</th>
            <th data-options="field:'orderNum',width:30">排序</th>   
            <th data-options="field:'description',width:100">描述</th>   
        </tr>   
    </thead>   
</table> 
	<div id="toolbar">
<shiro:hasPermission name="boot:permission:add">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="dataTable.add()">添加</a>
</shiro:hasPermission>
<shiro:hasPermission name="boot:permission:edit">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="dataTable.edit()">修改</a>
</shiro:hasPermission>  
	</div>
	
	<div id="data-form-dlg" class="easyui-dialog" style="width: 400px; height: 380px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons" modal="true">
		<form id="data-form" class="data-form" method="post">
			<input name="id" style="display: none" />
			<input name="type" value="menu" style="display: none" />
			<table style="margin-left:-20px;">
				<tr class="tr_padding">
					<td><label>菜单名称</label></td>
					<td>
						<input name="name" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td><label>菜单路径</label></td>
					<td>
						<input name="url" class="easyui-validatebox" data-options="validType:'notCHS'">
					</td>		
				</tr>

				<tr class="tr_padding">
					<td><label>菜单图标</label></td>
					<td>
						<input  name="iconCls" id="icon" class="easyui-textbox" data-options="buttonIcon:'icon-search',onClickButton:openIconAllHtml"/>
					</td>
				</tr>
				<tr>
					<td><label>上级菜单</label></td>
					<td>
						<input name="parentId" id="parentMenuId">
					</td>		
				</tr>
				<tr>
					<td><label>排序</label></td>
					<td>
						<input name="orderNum" class="easyui-validatebox" data-options="validType:'positiveNumber'">
					</td>		
				</tr>
				<tr class="tr_padding">
					<td><label>描<span class="letter-space-2"></span>述</label></td>
					<td	colspan="3">
						<textarea rows="3" name="description" class="textarea easyui-validatebox"
							style="width: 150px"></textarea>
					</td>
				</tr>
			</table> 
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="dataTable.save()">保存</a> 
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#data-form-dlg').dialog('close')">取消</a>
	</div>


<script type="text/javascript">
function menuLoadFilter(json){
	var str = JSON.stringify(json);
	var nstr = str.replace(/closed/g,"open");
	return JSON.parse(nstr);
}

$(function(){
	initMenuCombotree();
})

function initMenuCombotree(){
	$("#parentMenuId").combotree({
		url:adminActionPath + '/boot/permission/getAllMenus',
		loadFilter: menuLoadFilter,
		idField	: 'id',
		textField: 'name', 
		panelHeight:'auto'
	});
}


var dataTable = new DataTable({
	$datagrid_table :$("#datagrid-table"),
	$data_form_dialog : $("#data-form-dlg"),
	$data_form : $("#data-form"),
	data_form_name : "菜单",
	isTreeGrid : true,
	
	addOpt : {
		url : adminActionPath+"/boot/permission/add"
	},
	editOpt : {
		url : adminActionPath+"/boot/permission/edit"
	},
	saveOpt : {
		onSuccess : function(json){
			initMenuCombotree();
		}
	},
	searchOpt : {
		$searchForm : $("#search-form"),
	}
});

function openIconAllHtml(){
		window.open("${root}/static/easyui/easyui-extend/IconsExtension/IconExtension.html");
}
</script>


</body>
</html>
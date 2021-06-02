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
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" title="菜单列表" style="width:40%;">
			<table id="menu-table" class="easyui-treegrid" style="width:600px;height:400px"   
			        data-options="
			        	url				: adminActionPath + '/boot/permission/getAllMenus',
			        	loadFilter		: menuLoadFilter,
			        	idField			: 'id',
			        	treeField		: 'name',
			        	rownumbers		: true,
						fitColumns		: true, 
						fit				: true,
						onSelect   	    : loadOperation">   
			    <thead>   
			        <tr>   
			            <th data-options="field:'name',width:80">菜单名称</th>
			            <th data-options="field:'description',width:160">描述</th>     
			        </tr>   
			    </thead>   
			</table> 
		</div>
		<div data-options="region:'center'" title="权限列表">
			<table id="permission-table" class="easyui-datagrid" style="width:600px;height:400px"   
			        data-options="
			        	url				: adminActionPath + '/boot/permission/getOperations',
			        	idField			: 'id',
			        	singleSelect	: true,
			        	rownumbers		: true,
						fitColumns		: true, 
						fit				: true,
						toolbar			: '#toolbar',
						onDblClickRow   : function(){dataTable.edit();}">   
			    <thead>   
			        <tr>   
			            <th data-options="field:'name',width:80">权限名称</th>   
			            <th data-options="field:'permCode',width:160">权限编码</th>
			            <th data-options="field:'orderNum',width:30">排序</th>   
			            <th data-options="field:'description',width:160">描述</th>
 
			        </tr>   
			    </thead>   
			</table> 
		</div>
	</div>
	<div id="data-form-dlg" class="easyui-dialog" style="width: 400px; height: 380px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons" modal="true">
		<form id="data-form" class="data-form" method="post">
			<input name="id" style="display: none" />
			<input name="parentId" style="display: none" />
			<input name="type" value="operation" style="display: none" />
			<table style="margin-left:-20px;">
				<tr class="tr_padding">
					<td><label>权限名称</label></td>
					<td>
						<input name="name" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td><label>权限编码</label></td>
					<td>
						<input name="permCode" class="easyui-validatebox" data-options="validType:'notCHS'">
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
	<div id="toolbar">
<shiro:hasPermission name="boot:permission:add">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="dataTable.add()">添加</a>
</shiro:hasPermission>
<shiro:hasPermission name="boot:permission:edit">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="dataTable.edit()">修改</a>
</shiro:hasPermission>
<shiro:hasPermission name="boot:permission:delete">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="dataTable.remove()">删除</a>
</shiro:hasPermission>
	


  

	</div>
	
	
<script type="text/javascript">
var ag_parentId=null;



function menuLoadFilter(json){
	var str = JSON.stringify(json);
	var nstr = str.replace(/closed/g,"open");
	return JSON.parse(nstr);
}

function loadOperation(node){
	$("#permission-table").datagrid("reload",{"parentId":node.id});
	ag_parentId=node.id;
}

var dataTable = new DataTable({
	$datagrid_table :$("#permission-table"),
	$data_form_dialog : $("#data-form-dlg"),
	$data_form : $("#data-form"),
	data_form_name : "权限",
	
	addOpt : {
		url : adminActionPath+"/boot/permission/add",
		alertValidation : function(){
			return ag_parentId?true:false;
		},
		afterOpenDlg : function($data_form){
			$data_form.find("input[name='parentId']").val(ag_parentId);
		}
	},
	editOpt : {
		url : adminActionPath+"/boot/permission/edit"
	},
	removeOpt : {
		url : adminActionPath+"/boot/permission/delete"
	},
	saveOpt : {}
});

</script>
</body>
</html>
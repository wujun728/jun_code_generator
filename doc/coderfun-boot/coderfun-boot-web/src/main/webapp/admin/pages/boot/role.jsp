<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<title>角色管理</title>
   		<c:import url="/admin/pages/common/headsource.jsp"/>		
	<script type="text/javascript" src="${root}/static/easyui/easyui-extend/treegridex.cascade.js"></script>
  	</head>
<body>	
<c:import url="/admin/pages/common/loading.jsp"/>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" title="角色列表" style="width:60%;">
			<table id="role-table" class="easyui-datagrid"
				data-options="
					toolbar			: '#role-toolbar',
					rownumbers		: true,
					singleSelect	: true,
					fitColumns		: true, 
					url				: adminActionPath + '/boot/role/findlist',
					fit				: true,
					showFooter		: true,
					idField			: 'id',
					loadFilter		: function(json){return json.data},
					onClickRow		: showPermission">
				<thead>
					<tr>
						<th data-options="field:'name',width:100,align:'left',formatter:complexCol">角色名称</th>
						<th data-options="field:'roleCode',width:100,align:'left',formatter:complexCol">角色编码</th>
						<th data-options="field:'description',width:200,align:'left',formatter:complexCol">描述</th>
					</tr>
				</thead>
			</table>		
		
		</div>
		<div data-options="region:'center'" title="权限列表">
			<table id="menu-table" class="easyui-treegrid" style="width:600px;height:400px"   
			        data-options="
			        	url				: adminActionPath + '/boot/permission/findAll',
			        	toolbar			: '#menu-toolbar',
			        	idField			: 'id',
			        	treeField		: 'name',
			        	rownumbers		: true,
						fitColumns		: true,
						fit				: true,
						onBeforeSelect  : checkRoleSelected">   
			    <thead>   
			        <tr>

			        	<th data-options="field:'id',hidden:true,width:80">id</th>   
			            <th data-options="field:'name',width:80">权限名称</th>
			            <th data-options="field:'description',width:160">描述</th>      
			        </tr>   
			    </thead>   
			</table>

		</div>
	</div>


	
	<div id="role-form-dlg" class="easyui-dialog" style="width: 400px; height: 320px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons" modal="true">
		<form id="role-form" class="data-form" method="post">
			<input name="id" style="display: none" />
			<table>
				<tr class="tr_padding">
					<td><label>角色名称</label></td>
					<td><input name="name" class="easyui-validatebox" data-options="required:true,validType:'length[2,20]'"/></td>
				</tr>
				
				<tr class="tr_padding">
					<td><label>角色编码</label></td>
					<td><input name="roleCode" class="easyui-validatebox" data-options="required:true,validType:['length[3,20]','word']"></td>
				</tr>
			
				<tr class="tr_padding">
					<td><label>描<span class="letter-space-2"></span>述</label></td>
					<td><textarea name="description" style="width: 150px" class="easyui-validatebox"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="dataTable.save()">保存</a> 
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#role-form-dlg').dialog('close')">取消</a>
	</div>
	
	<div id="role-toolbar">
<shiro:hasPermission name="boot:role:add">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="dataTable.add()">添加</a>
</shiro:hasPermission>
<shiro:hasPermission name="boot:role:edit">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="dataTable.edit()">修改</a>
</shiro:hasPermission>
  
  
	</div>
	

	<div id="menu-toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePerm()">保存授权</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-2012080412301" plain="true" onclick="restorePerm()">撤销</a>  
	</div>
	
	<script type="text/javascript">	

	var ag_perm_ids_holder=null;
	
	
		var dataTable = new DataTable({
			$datagrid_table :$("#role-table"),
			$data_form_dialog : $("#role-form-dlg"),
			$data_form : $("#role-form"),
			data_form_name : "用户角色",
			
			addOpt : {
				url : adminActionPath+"/boot/role/add"
			},
			editOpt : {
				url : adminActionPath+"/boot/role/edit"
			},
			saveOpt : {}
		});
		
		function showPermission(index, row){
			MaskUtil.mask()
			var roleId = row.id;
			if(ag_perm_ids_holder){
				//清空勾选
				$("#menu-table").treegrid("unselectAll");							
			}

			$.post(adminActionPath +"/boot/rolepermission/findPermissionIdsByRoleId","roleId="+roleId,function(json){
				if(json.type == "success"){
					var ids=json.data;
					ag_perm_ids_holder=ids;
					selectWithAgPermIds();
					MaskUtil.unmask();
				}				
			},"json")
			
		}
		
		function selectWithAgPermIds(){
			for(var i in ag_perm_ids_holder){
				try {
					$("#menu-table").treegrid("select",ag_perm_ids_holder[i]);							
				} catch (e) {
				}
			}
		}
		
		
		function checkRoleSelected(){
			var role = $("#role-table").datagrid("getSelected");
			if(!role){
				$.messager.alert({
					title:"提示",
					msg:"请选择角色！"
				})
				return false;
			}else{
				return true;
			}
			
		}
		
		
		function savePerm(){
			if(!checkRoleSelected()){
				return false;
			}
			var role = $("#role-table").datagrid("getSelected");
			var permission = $("#menu-table").treegrid("getSelections");
			var permissionIds=[];
			for(var i = 0; i < permission.length; i++){
				permissionIds.push(permission[i].id);
			}
			var data = {
				"roleId" 		: role.id,
				"permissionIds" : permissionIds 
			};
			$.ajax({
			    url:adminActionPath +"/boot/rolepermission/updateRolePermissions",
			    /**必须是POST方法**/
			    type:'post',
			    dataType:'json',
			    /**必须制定请求的类型**/
			    contentType:'application/json;charset=utf-8',
			    data:JSON.stringify(data),
			    success:function(json){
					if(json.type == "success"){
						$.messager.show({
							title:"提示",
							msg:"保存成功！"
						})
					}
			    }
			});
		}
		
		function restorePerm(){
			if(ag_perm_ids_holder){
				//清空勾选
				$("#menu-table").treegrid("unselectAll");
				selectWithAgPermIds();				
			}	
		}
		
	</script>
</body>
</html>
			
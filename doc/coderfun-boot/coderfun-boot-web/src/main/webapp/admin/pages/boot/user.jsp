<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="code" uri="jstl.coderfun.common"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>管理员管理</title>
<c:import url="/admin/pages/common/headsource.jsp" />

</head>
<body>
<c:import url="/admin/pages/common/loading.jsp"/>
	<table id="datagrid-table" class="easyui-datagrid" title="管理员列表"
		data-options="
			rownumbers		: true,
			singleSelect	: true,
			fitColumns		: true, 
			url				: adminActionPath + '/boot/user/findpage',
			toolbar			: '#toolbar',
			fit				: true,
			pagination		: true,
			pageSize		: 15,
			pageList        : [10,15,20,25,30],
			showFooter		: true,
			idField			: 'id'
<shiro:hasPermission name="boot:user:edit">			
			,
			onDblClickRow   : function(){dataTable.edit();}
</shiro:hasPermission >			
			">
		<thead>
			<tr>
				<th data-options="field:'loginName',width:60,align:'left',formatter:complexCol">账号</th>
				<th data-options="field:'name',width:50,align:'left',formatter:complexCol">姓名</th>
				<th data-options="field:'gender',width:30,align:'left',formatter:codeCol,codeClass:'gender'">性别</th>
				<th data-options="field:'birthday',width:60,align:'left',formatter:EasyUiDate">生日</th>

				<th data-options="field:'email',width:70,align:'left',formatter:complexCol">Email</th>
				<th data-options="field:'phone',width:60,align:'left',formatter:complexCol">电话</th>
				
				<th data-options="field:'state',width:30,align:'left',formatter:codeCol,codeClass:'user_state',highlightCode:'forbidden'">状态</th>
				<th data-options="field:'description',width:100,formatter:complexCol">备注</th>

			</tr>
		</thead>
	</table>
	<div id="toolbar">

		<div class="easyui-panel" data-options="collapsible:true,minimizable:true">
			<form id="search-form" class="search-form" enctype="multipart/form-data">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" style="color: red" onclick="resetForm('search-form')">条件重置</a>
				<label>账号</label>
				<input name="loginName" style="width: 125px;" type="text">
				<span class="inline-clear"></span>

				<label>姓名</label>
				<input name="name" style="width: 125px;" type="text">
				<span class="inline-clear"></span>

				<label>电话</label>
				<input name="phone" style="width: 125px;" type="text">
				<span class="inline-clear"></span>
				
				<label>状态</label>
				<input name="state" style="width: 125px;" class="easyui-combobox" data-options="enableNull:true,required:true,codeClass:'user_state'">
				<span class="inline-clear"></span>
				
				<!--  
				<label>最近登录</label>
				<input name="startDate" style="width: 100px;" class="easyui-datebox" data-options="editable:false">
				-
				<input name="endDate" style="width: 100px;" class="easyui-datebox" data-options="editable:false">
				
				-->
				
				<span class="inline-clear"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="dataTable.search()">查询</a>

			</form>
		</div>
		
<shiro:hasPermission name="boot:user:add">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="dataTable.add()">添加</a>			
</shiro:hasPermission >

<shiro:hasPermission name="boot:user:edit">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="dataTable.edit()">修改</a>
</shiro:hasPermission>
<shiro:hasPermission name="boot:user:updatePassword">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-2012080412263" plain="true" onclick="openPasswordDlg()">重设密码</a>
</shiro:hasPermission>
<shiro:hasPermission name="boot:userRole:update">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-group" plain="true" onclick="showUserRoles()">角色设置</a>
</shiro:hasPermission>


	</div>

	<div id="data-form-dlg" class="easyui-dialog" style="width: 600px; height: 420px; padding: 10px 20px" closed="true" buttons="#dlg-buttons" modal="true">
		<form id="data-form" class="data-form">
			<input name="id" style="display: none" />
			<table style="margin-left: -20px;">
				<tr class="tr_padding" id="password-flag">
					<td><label>账<span class="letter-space-2"></span> 号</label></td>
					<td><input name="loginName" id="loginName"
						class="easyui-validatebox" data-options="required:true,validType:['length[3,20]','word']"></td>
					<td><label>姓<span class="letter-space-2"></span> 名</label></td>
					<td><input name="name" class="easyui-validatebox" data-options="required:true,validType:'length[3,20]'"></td>
				</tr>

				<tr class="tr_padding">
					<td><label>性<span class="letter-space-2"></span> 别</label></td>
					<td><input name="gender" class="easyui-combobox" data-options="required:true,codeClass:'gender'"></td>
					<td><label>出生日期</label></td>
					<td><input name="birthday" class="easyui-datebox"></td>
				</tr>
				<tr class="tr_padding">
					<td><label>Email</label></td>
					<td><input name="email" class="easyui-validatebox" data-options="validType:'email'"></td>
					<td><label>电<span class="letter-space-2"></span> 话</label></td>
					<td><input name="phone" class="easyui-validatebox" data-options="validType:'phoneNumber'"></td>
				</tr>
				
				<tr class="tr_padding">
					<td><label>状<span class="letter-space-2"></span> 态</label></td>
					<td><input name="state" class="easyui-combobox" data-options="required:true,codeClass:'user_state'"></td>
					<td></td>
					<td></td>
				</tr>
				<tr class="tr_padding">
					<td><label>
							备 <span class="letter-space-2"></span> 注
						</label></td>
					<td colspan="3"><textarea rows="3" name="description" class="textarea easyui-validatebox" style="width: 375px"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="dataTable.save()">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#data-form-dlg').dialog('close')">取消</a>
	</div>
	
	<div id="password-dlg" title="重设密码" class="easyui-dialog" style="width: 300px; height: 220px; padding: 10px 20px" closed="true" buttons="#password-dlg-buttons" modal="true">
		<form id="password-update">
		<input name="userId" id="password-userId" style="display: none" />
			<table>
				<tr><td><label>新密码：</label></td></tr>
				<tr><td><input id="plainPassword" name="plainPassword" style="width:230px" type="password" class="easyui-validatebox" data-options="required:'required',validType:'length[6,20]'"/></td></tr>
				<tr><td><label>确认密码：</label></td></tr>
				<tr><td><input id="confirmPassword" name="confirmPassword" style="width:230px" type="password" class="easyui-validatebox" data-options="required:'required',validType:'same[\'plainPassword\',\'confirmPassword\',\'密码\']'"/></td></tr>
			</table>
	
		</form>
	</div>
	<div id="password-dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="updatePassword()">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#password-dlg').dialog('close')">取消</a>
	</div>
	
	
	<div id="user-roles-dlg" title='角色管理' class="easyui-dialog" style="width: 600px; height: 420px; padding: 10px 20px" closed="true" buttons="#user-roles-dlg-buttons" modal="true">
		<table id="role-table" class="easyui-datagrid"
			data-options="
				rownumbers		: true,
				fitColumns		: true, 
				url				: adminActionPath + '/boot/role/findlist',
				fit				: true,
				showFooter		: true,
				idField			: 'id',
				loadFilter		: function(json){return json.data}">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'name',width:100,align:'left',formatter:complexCol">角色名称</th>
					<th data-options="field:'roleCode',width:100,align:'left',formatter:complexCol">角色编码</th>
					<th data-options="field:'description',width:200,align:'left',formatter:complexCol">描述</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="user-roles-dlg-buttons">
		<a href="javascript:void(0);" id="updateUserRoles-btn" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateUserRoles()">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#user-roles-dlg').dialog('close')">取消</a>
	</div>
	
	
	<script type="text/html" id="password-confirm-template">
				<tr class="tr_padding" id="password-confirm">
					<td><label>密码</label></td>
					<td>
						<input id="plainPassword" name="plainPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[6,20]'"/>
					</td>	
					<td><label>确认密码</label></td>
					<td>
						<input id="confirmPassword" name="confirmPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'same[\'plainPassword\',\'confirmPassword\',\'密码\']'"/>
					</td>
				</tr>

	</script>

	<script type="text/javascript">
		var dataTable = new DataTable({
			$datagrid_table : $("#datagrid-table"),
			$data_form_dialog : $("#data-form-dlg"),
			$data_form : $("#data-form"),
			data_form_name : "管理员",

			addOpt : {
				url : adminActionPath + "/boot/user/add",
				afterOpenDlg : function($data_form) {
					$data_form.find("#loginName").removeAttr("disabled");
					$data_form.find("#password-flag").after(
							$("#password-confirm-template").html());
					$.parser.parse('#password-confirm'); // 解析某个具体节点
				}
			},
			editOpt : {
				url : adminActionPath + "/boot/user/edit",
				afterOpenDlg : function($data_form){
					$data_form.find("#loginName").attr("disabled","disabled");
				}
			},
			saveOpt : {
				validation : function(){
					return validateLoginName();
				}
			},
			searchOpt : {
				$searchForm : $("#search-form"),
			}
		});
		//删除密码框
		$("#data-form-dlg").dialog({
			onClose : function() {
				$("#password-confirm").remove();
			}
		});
		
		$("#loginName").change(function(){
			checkLoginName();
		})
		var ag_LoginNameExisted = false;
		function checkLoginName(){
			var loginName = $("#loginName").val();
			$.post(adminActionPath + '/boot/user/checkLoginName',{"loginName":loginName},function(data,status,xhr){
				ag_LoginNameExisted = data;
				validateLoginName();
			},"json"); 			
		}
		
		function validateLoginName(){
			if(ag_LoginNameExisted){
				$.messager.alert("提示", "管理员账号已经存在！");
				return false;
			}
			return true;
		}
		
		function openPasswordDlg(){
			var row = $("#datagrid-table").datagrid("getSelected");
			if(row){
				$("#password-dlg").dialog("open");
				$("#password-userId").val(row.id);
			}else{
	            $.messager.alert({
	            	title : "提示",
	            	msg : "请选择一行记录!"
	            }) 
			}
		}
		
		
		function updatePassword(){
			if(!$('#password-update').form("validate")){
				return false;
			}
			
			var formData=$('#password-update').serializeJson();
			
			$.post(adminActionPath + '/boot/user/updatePassword',formData,function(data,status,xhr){
		        var data = eval('(' + data + ')');  // change the JSON string to javascript object    
		        if (data.type == "success"){    
		            $.messager.show({
		            	title : "提示",
		            	msg : "修改密码成功！"
		            })
		            $("#password-update").form("clear");
		            $("#password-dlg").dialog("close");
		        }			
			}); 
		}
		
		function showUserRoles(){
			var row = $("#datagrid-table").datagrid("getSelected");
			var roleTable=$("#role-table");
			roleTable.datagrid("uncheckAll");
			if(row){
				MaskUtil.mask()
				$.post(adminActionPath +"/boot/userrole/findRoleIdsByUserId","userId="+row.id,function(json){
					var ids=json.data;
					for(var i=0;i<ids.length;i++){
						roleTable.datagrid("selectRecord",ids[i]);						
					}
					MaskUtil.unmask();
					$("#user-roles-dlg").dialog("open");
				},"json"); 
			}else{
	            $.messager.alert({
	            	title : "提示",
	            	msg : "请选择一行记录!"
	            }) 
			}
		}
		
		function updateUserRoles(){
			var row = $("#datagrid-table").datagrid("getSelected");
			
			var roles = $("#role-table").datagrid("getChecked");
			var roleIds=[];
			for(var i=0;i<roles.length;i++){
				roleIds.push(roles[i].id);
			}
			if(row){
				$("#updateUserRoles-btn").linkbutton("disable");
				var data={
						"userId"  : row.id,
						"roleIds" : roleIds
				};

				$.ajax({
				    url:adminActionPath +"/boot/userrole/updateUserRoles",
				    /**必须是POST方法**/
				    type:'post',
				    dataType:'json',
				    /**必须制定请求的类型**/
				    contentType:'application/json;charset=utf-8',
				    data:JSON.stringify(data),
				    success:function(json){
						if(json.type == "success"){
							$.messager.show({
								title :"提示",
								msg   :"保存成功！"
							})
						}
						$("#user-roles-dlg").dialog("close");
						$("#updateUserRoles-btn").linkbutton("enable");
				    }
				});

			}else{
	            $.messager.alert({
	            	title : "提示",
	            	msg : "请选择一行记录!"
	            }) 
			}
			

		}
		

	</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<title>用户</title>
   		<c:import url="/admin/pages/common/headsource.jsp"/>		
  	</head>
<body>
	<table id="datagrid-table" class="easyui-datagrid" title="用户列表"
		data-options="
			rownumbers		: true,
			singleSelect	: true,
			fitColumns		: true, 
			url				: adminActionPath + '/user/findpage',
			toolbar			: '#toolbar',
			fit				: true,
			pagination		: true,
			pageSize		: 15,
			pageList        : [10,15,20,25,30],
			showFooter		: true,
			idField			: 'id',
			onDblClickRow   : function(){dataTable.edit();}">
		<thead>
			<tr>

				<th data-options="field:'id',width:100,align:'left',formatter:complexCol">ID</th>
				<th data-options="field:'createTime',width:100,align:'left',formatter:EasyUiDateTime">创建时间</th>
				<th data-options="field:'remark',width:100,align:'left',formatter:complexCol">备注</th>
				<th data-options="field:'username',width:100,align:'left',formatter:complexCol">用户名XXX</th>
				<th data-options="field:'email',width:100,align:'left',formatter:complexCol">邮箱</th>
				<th data-options="field:'score',width:100,align:'left',formatter:complexCol">分数</th>
				<th data-options="field:'userGender',width:100,align:'left',formatter:codeCol,codeClass:'user_gender'">性别</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
	
		<div class="easyui-panel"
		    data-options="collapsible:true,minimizable:true">
			<form id="search-form" class="search-form" enctype="multipart/form-data">    
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
					style="color: red"  onclick="resetForm('search-form')">条件重置</a>	
		
				<label>ID</label>
				<input name="id" style="width:125px;" type="text">
				<span class="inline-clear"></span>
				<label>创建时间</label>
				<input name="createTime" style="width:125px;" type="text">
				<span class="inline-clear"></span>
				<label>用户名XXX</label>
				<input name="username" style="width:125px;" type="text">
				<span class="inline-clear"></span>
				<label>邮箱</label>
				<input name="email" style="width:125px;" type="text">
				<span class="inline-clear"></span>
				<label>性别</label>
				<input name="userGender" class="easyui-combobox"
				data-options="valueField:'code',textField:'name',editable:false,panelHeight:'auto',enableNull:true,defaultFirst:true,codeClass:'user_gender'">
				<span class="inline-clear"></span>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="dataTable.search()">查询</a>
		     </form>
		</div>
	
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="dataTable.add()">添加</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="dataTable.edit()">修改</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="dataTable.remove()">删除</a>

	</div>
	
	<div id="data-form-dlg" class="easyui-dialog" style="width: 600px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons" modal="true">
		<form id="data-form" class="data-form" method="post">
			<input name="id" style="display: none" />
			<table style="margin-left:-20px;">
				<tr class="tr_padding">
					<td><label>备注</label></td>
					<td  colspan="3" >
						<textarea rows="3" name="remark" class="textarea easyui-validatebox" 
							style="width: 375px"></textarea>
					</td>
				</tr>
				<tr class="tr_padding">
					<td><label>用户名XXX</label></td>
					<td  >
						<input name="username" class="easyui-validatebox" required=true validType="maxLength[255]">
					</td>
				</tr>
				<tr class="tr_padding">
					<td><label>邮箱</label></td>
					<td  >
						<input name="email" class="easyui-validatebox" required=true validType="email">
					</td>
				</tr>
				<tr class="tr_padding">
					<td><label>分数</label></td>
					<td  >
						<input name="score" class="easyui-validatebox"  validType="digits">
					</td>
				</tr>
				<tr class="tr_padding">
					<td><label>性别</label></td>
					<td  >
						<input name="userGender" class="easyui-combobox" 
						data-options="valueField:'code',textField:'name',editable:false,panelHeight:'auto',
							defaultFirst:true,codeClass:'user_gender'">
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
		
		var dataTable = new DataTable({
			$datagrid_table :$("#datagrid-table"),
			$data_form_dialog : $("#data-form-dlg"),
			$data_form : $("#data-form"),
			data_form_name : "用户",
			
			addOpt : {
				url : adminActionPath+"/user/add"
			},
			editOpt : {
				url : adminActionPath+"/user/edit"
			},
			removeOpt : {
				url : adminActionPath+"/user/delete"
			},
			saveOpt : {},
			searchOpt : {
				$searchForm : $("#search-form"),
			}
		});
		
	</script>
</body>
</html>

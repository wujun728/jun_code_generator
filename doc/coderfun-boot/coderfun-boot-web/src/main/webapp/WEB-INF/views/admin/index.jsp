<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.coderfun.boot.core.BootSettings" %>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<title>桌面</title>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index.css"/>
   		<c:import url="/admin/pages/common/headsource.jsp"/>	
  	</head>
<body class="easyui-layout" data-options="fit:true">
<c:import url="/admin/pages/common/loading.jsp"/>
	<div data-options="region:'north'" id="index-header"> 
		<span class="project-name">coderfun-boot</span>
		<span class="right-bar">&nbsp;
   			<a href="javascript:void(0)" id="user_select_menu" class="easyui-menubutton index_top_linkbutton" data-options="menu:'#menu_select', iconCls:'icon-user'">
   				<shiro:principal property="name"/>
   			</a>   
			<div id="menu_select" style="width:150px;" data-options="noline:true">   
			    	<div onclick="javascript:userView()" data-options="iconCls:'icon-standard-vcard'">个人信息</div>
			    	<div onclick="javascript:openPasswordDlg()" class="my_menu_select" data-options="iconCls:'icon-standard-user-edit'">修改密码</div>  
			</div>
			<a href="javascript:void(0)" onclick="javascript:logout()" class="easyui-linkbutton index_top_linkbutton" plain="true" icon="icon-bullet_go" >安全退出</a>
   		</span>
	</div>
	
	<div data-options="region:'south',split:true" style="height:50px;">
		<div class="login-copyright">
				© 2018 coderfun-boot - Powered By <a href="https://gitee.com/klguang" target="_blank">klguang</a>.
		</div>
	</div>
	
	<div data-options="region:'west',split:true" title="菜单" style="width:220px;">
		<div class="easyui-accordion" data-options="border:true,fit:true" id="menu-container">
           	<c:forEach var="menu" items="${menus}">
                    <c:if test="${menu.parentId==null}">
                        <div title="${menu.name}" data-options="iconCls:'${menu.iconCls}'">
                        	<c:forEach var="childMenu" items="${menus}">
                                <c:if test='${childMenu.parentId==menu.id}'>
	                                <a class="easyui-linkbutton menuItem-btn" data-options="plain:true,iconCls:'${childMenu.iconCls}'"
	                                style="width:99.8%;margin-bottom:5px;padding-left:10px;text-align:left;" 
	                                	onclick='menuClick("${childMenu.name}","${root}${childMenu.url}","${childMenu.iconCls}")'>
	                                	${childMenu.name}
	                                </a>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
	</div>
	<div data-options="region:'center',iconCls:'icon-ok'">
		<div class="easyui-tabs" id="tt" data-options="fit:true,border:false,plain:true,enableConextMenu:true,contextMenu:tab_menu">

		</div>
	</div>
	<div id="password-dlg" title="重设密码" class="easyui-dialog" style="width: 300px; height: 260px; padding: 10px 20px" closed="true" buttons="#password-dlg-buttons" modal="true">
		<form id="password-update">
			<table>
				<tr><td><label>原密码：</label></td></tr>
				<tr><td><input id="oldPlainPassword" name="oldPlainPassword" style="width:230px" type="password" class="easyui-validatebox" data-options="required:'required',validType:'length[6,20]'"/></td></tr>
				<tr><td><label>新密码：</label></td></tr>
				<tr><td><input id="newPlainPassword" name="newPlainPassword" style="width:230px" type="password" class="easyui-validatebox" data-options="required:'required',validType:'length[6,20]'"/></td></tr>
				<tr><td><label>确认密码：</label></td></tr>
				<tr><td><input id="confirmPassword" name="confirmPassword" style="width:230px" type="password" class="easyui-validatebox" data-options="required:'required',validType:'same[\'newPlainPassword\',\'confirmPassword\',\'密码\']'"/></td></tr>
			</table>
	
		</form>
	</div>
	
	<div id="password-dlg-buttons">
		<a href="javascript:void(0);" id="password-dlg-savebtn" class="easyui-linkbutton" iconCls="icon-ok" onclick="changePassword()">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#password-dlg').dialog('close')">取消</a>
	</div>
	
	
	<div id="profile-dlg" class="easyui-dialog" style="width: 600px; height: 420px; padding: 10px 20px" closed="true" buttons="#profile-dlg-buttons" modal="true">
		<form id="profile-form" class="data-form">
			<input name="id" style="display: none" />
			<table style="margin-left: -20px;">
				<tr class="tr_padding" id="password-flag">
					<td><label>账<span class="letter-space-2"></span> 号</label></td>
					<td><input readonly="readonly" name="loginName" id="loginName" disabled="disabled"></td>
					<td><label>姓<span class="letter-space-2"></span> 名</label></td>
					<td><input readonly="readonly" name="name" disabled="disabled"></td>
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
			</table>
		</form>
	</div>
	
	<div id="profile-dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveProfile()">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#profile-dlg').dialog('close')">取消</a>
	</div>
</body>

<script src="${root}/static/easyui/jeasyui-extensions/jquery.jdirk.js"></script>
<script src="${root}/static/easyui/jeasyui-extensions/jeasyui.extensions.panel.iframe.js"></script>
<script src="${root}/static/easyui/jeasyui-extensions/jeasyui.extensions.menu.js"></script>
<script src="${root}/static/easyui/jeasyui-extensions/jeasyui.extensions.tabs.getTabs.js"></script>
<script src="${root}/static/easyui/jeasyui-extensions/jeasyui.extensions.tabs.closeTabs.js"></script>
<script src="${root}/static/easyui/jeasyui-extensions/jeasyui.extensions.tabs.contextMenu.js"></script>



<script>

function userView(){
	MaskUtil.mask()
	$.get(adminActionPath + "/boot/userAccess/getProfile",function(json){
		var profileForm = $("#profile-form");
		profileForm.form("reset");
		profileForm.form("myLoad", json.data);
		$("#profile-dlg").dialog("open");
		MaskUtil.unmask();
	},'json');
}
 
function saveProfile(){
	if(!$('#profile-form').form("validate")){
		return false;
	}
	
	var data = $('#profile-form').serializeJson();
	$.post(adminActionPath + "/boot/userAccess/changeProfile",data,function(json){
		if(json.type == "success"){
			$('#profile-dlg').dialog('close');
            $.messager.show({
            	title : "提示",
            	msg : "修改个人资料成功！"
            })
		}
	},"json"); 
}

function openPasswordDlg(){
	$("#password-dlg").dialog("open");
}

function changePassword(){
	if(!$('#password-update').form("validate")){
		return false;
	}
	
	var data = $('#password-update').serializeJson();
	$.ajax({
		url : adminActionPath + "/boot/userAccess/changePassword",
		data : data,
		type : "POST",
		dataType : "json",
		complete : function(XMLHttpRequest, textStatus) {
			//用户修改密码成功后，断言会UnknownSessionException
			var isSuccessed = !XMLHttpRequest.responseText || JSON.parse(XMLHttpRequest.responseText).type == "success";
			
			if(isSuccessed){
	            $.messager.alert('提示', '修改密码成功！', 'info',function(){
					window.location.href = "${root}<%=BootSettings.getAdminPath()%>/login";
	            });
	            return ;
			}
			$("#password-dlg-savebtn").linkbutton("unselect");
		}
	});
	
	
	
}

function logout(){
    $.messager.confirm('提示','确定要退出?',function(r){
        if (r){
        	window.location.href="${root}/admin/action/boot/userAccess/logout";
        }
    });
	
}



//tab右键刷新菜单
var tab_menu=[
             	{id:'refresh',text:'刷新',hideOnClick:true,
             		handler:function(e, menuItem, menu, target, title, index){
					 var tab = $(target).tabs('getSelected');  // 获取选择的面板
					 tab.panel('refresh');
					}
				},
             	{id:'openInNewWin',text:'在新窗口打开',hideOnClick:true,
					handler:function(e, menuItem, menu, target, title, index){
	             		var tab = $(target).tabs('getSelected');  // 获取选择的面板
	             		var options = tab.panel('options'); 
	             			window.open(options.href);
					}
				}
]

// 打开一个tab iframe
function addTab(option){
	if ($('#tt').tabs('exists', option.title)){
		$('#tt').tabs('select', option.title);
	} else {
		$('#tt').tabs('add',option);
	}
}
//tree 的点击事件，新打开一个tab iframe
function menuClick(title,url,iconCls){
	addTab({
		title:title,
		closable:true,
		href:url,
		iniframe:true,
		iconCls:iconCls
	});		 

}
$(function(){
	$('.menuItem-btn').on('click', function(){    
		$('.menuItem-btn').linkbutton({selected:false}); 
	    $(this).linkbutton({selected:true});  
	});
});


</script>
</html>
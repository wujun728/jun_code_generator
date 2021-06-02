<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.coderfun.boot.core.BootSettings" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<c:set var="root" value="${pageContext.request.contextPath}" scope="application"></c:set>
	<c:set var="easyuiVersion" value="jquery-easyui-1.5.3" scope="application"></c:set>
	<c:set var="easyuiThemeName" value="gray" scope="application"></c:set>

	<c:set var="nowDate" scope="request" value="<%=new java.util.Date()%>"></c:set>
		
	<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${root}/static/easyui/${easyuiVersion}/themes/${easyuiThemeName }/easyui.css"/>  
    <link rel="stylesheet" type="text/css" href="${root}/static/easyui/easyui-extend/IconsExtension/IconExtension.css"/>
    <link rel="stylesheet" type="text/css" href="${root}/static/easyui/${easyuiVersion}/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="${root}/static/css/form.css"/>
    
    
    <script type="text/javascript" src="${root}/static/easyui/${easyuiVersion}/jquery.min.js"></script>  
    <script type="text/javascript" src="${root}/static/easyui/${easyuiVersion}/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${root}/static/easyui/${easyuiVersion}/locale/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="${root}/static/js/calutil.js"></script>
	<script type="text/javascript" src="${root}/static/js/klg.util.js"></script>
	
	<script type="text/javascript" src="${root}/static/easyui/easyui-extend/dataTable.js"></script>
	<script type="text/javascript" src="${root}/static/easyui/easyui-extend/common.js"></script>
	<script type="text/javascript" src="${root}/static/easyui/easyui-extend/jeasyuiex.js"></script>
	<script type="text/javascript" src="${dict_json_url}?t=${nowDate.time}"></script>
	
	
	<script type="text/javascript"> 
	
		var basePath = "${root}";
		var loginPath = "${root}<%=BootSettings.getAdminPath()%>/login";
		var adminActionPath=basePath+"/admin/action"
		//字典默认配置
		$.extend($.fn.combobox.defaults,{valueField:'code',textField:'name',panelHeight:'auto',editable:false,defaultFirst:true});
		
		
		//-------------默认错误提示 begin-------------
		$.extend(DataTable.config, {
			messageField : "message", 
			isSavedSuccess : function(result){
				return result.type == "success";
			},
			isRemovedSuccess : function(result,callback){
				return result.type == "success";
			},
			ajaxError : ajaxError
		});
		
		$.extend($.fn.tree.defaults,{ onLoadError : ajaxError });
		$.extend($.fn.combobox.defaults,{ onLoadError : ajaxError });
		$.extend($.fn.combotree.defaults,{ onLoadError : ajaxError });
		$.extend($.fn.datagrid.defaults,{ onLoadError : ajaxError });
		$.extend($.fn.treegrid.defaults,{ onLoadError : ajaxError });
		
		
		/**
		 * 改变jQuery的AJAX默认属性和方法
		 */
		$.ajaxSetup({
			error:function(XMLHttpRequest,textStatus,error){
				ajaxError(XMLHttpRequest,textStatus,error,"操作失败")
		    }
		});
		
		//-------------默认错误提示 end-------------
		
		function ajaxError(XMLHttpRequest,textStatus,error,title){
	        //通过XMLHttpRequest取得响应头，sessionStatus
	        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionStatus");
	        if(sessionstatus == "timeout"){
	            //如果超时就处理 ，指定要跳转的页面
	            var top = getTopWinow(); //获取当前页面的顶层窗口对象
	            $.messager.alert('提示', '登录超时-请重新登录！', 'info',function(){
	            	top.location.href = loginPath;
	            });
            	return false;
	        }
	        if(!XMLHttpRequest.responseText){
	        	return false;
	        }
	        
	        var json = JSON.parse(XMLHttpRequest.responseText);
            $.messager.show({
            	title : title?title:"操作失败",
            	msg : json.message
            })
		}
		/**
		 * 在页面中任何嵌套层次的窗口中获取顶层窗口
		 * @return 当前页面的顶层窗口对象
		 */
		function getTopWinow(){
		    var p = window;
		    while(p != p.parent){
		        p = p.parent;
		    }
		    return p;
		}		
	</script>
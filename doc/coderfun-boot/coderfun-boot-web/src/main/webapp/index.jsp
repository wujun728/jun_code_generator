<%@page import="org.coderfun.boot.core.BootSettings"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.coderfun.boot.core.BootSettings" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	request.setAttribute("adminPath", BootSettings.getAdminPath());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <script type="text/javascript">
    	//进入后台管理首页
    	document.location = "${pageContext.request.contextPath}${adminPath}/";
    </script>
<body>

</body>
</html>
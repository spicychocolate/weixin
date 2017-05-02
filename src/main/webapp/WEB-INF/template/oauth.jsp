<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,user-scalable=0">
<title>Insert title here</title>
<style type="text/css">
        *{margin:0; padding:0}
        table{border:1px dashed #B9B9DD;font-size:12pt}
        td{border:1px dashed #B9B9DD;word-break:break-all; word-wrap:break-word;}
    </style>
</head>
<body>
	
 <table width="100%" cellspacing="0" cellpadding="0">
        <tr><td width="20%">属性</td><td width="80%">值</td></tr>
        <tr><td>OpenID</td><td>${user.openId}</td></tr>
        <tr><td>昵称</td><td>${user.nickname}</td></tr>
        <tr><td>性别</td><td>${user.sex }</td></tr>
        <tr><td>国家</td><td>${user.country}</td></tr>
        <tr><td>省份</td><td>${user.province}</td></tr>
        <tr><td>城市</td><td>${user.city}</td></tr>
        <tr><td>头像</td><td>${user.headImgUrl}</td></tr>
        <tr><td>state:</td><td>${state}</td></tr>
    </table>
</body>
</html>
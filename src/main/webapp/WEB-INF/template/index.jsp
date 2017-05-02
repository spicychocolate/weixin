<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,user-scalable=0">
<title>Insert title here</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<td>序号</td>
				<td>姓名</td>
				<td>登录IP</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="admin" items="${list}"  varStatus="status">

				<tr>
					<td>${status.index + 1}</td>
					<td>${admin.username}</td>
					<td>${admin.loginIp}</td>
				</tr>

			</c:forEach>
		</tbody>

	</table>
	<h1>${path}</h1>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table class="gridtable" style="width: 100%; text-align: center;">
		<tr>
			<c:if test="${page.hasPreviousPage}">
				<td><a href="?pageNum=${page.prePage}&pageSize=${page.pageSize}">前一页</a></td>
			</c:if>
			<c:forEach items="${page.navigatepageNums}" var="nav">
				<c:if test="${nav == page.pageNum}">
					<td style="font-weight: bold;">${nav}</td>
				</c:if>
				<c:if test="${nav != page.pageNum}">
					<td><a href="?pageNum=${nav}&pageSize=${page.pageSize}">${nav}</a></td>
				</c:if>
			</c:forEach>
			<c:if test="${page.hasNextPage}">
				<td><a href="?pageNum=${page.nextPage}&pageSize=${page.pageSize}">下一页</a></td>
			</c:if>
		</tr>
	</table>
</body>
</html>
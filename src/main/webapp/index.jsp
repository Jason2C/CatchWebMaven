<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<style type="text/css">
* {
	margin: 0;
}

html,body {
	height: 100%;
}

.wrapper {
	min-height: 100%;
	height: auto !important;
	height: 100%;
	margin: 0 auto -155px;
}

.footer,.push {
	height: 155px;
}

table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
	margin: 5px auto;
}

table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}

table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}

.middle {
	text-align: center;
	margin: 0 auto;
	width: 600px;
	height: auto;
}

.info {
	font-size: 12px;
	text-align: center;
	line-height: 20px;
	padding: 40px;
}

.info a {
	margin: 0 10px;
	text-decoration: none;
	color: green;
}
</style>

</head>
<body><br><br>
	<div class="wrapper">
		<div class="middle">
			<h1>~商品浏览表~</h1>
			<form action="${pageContext.request.contextPath}/admin/testjson.do"
				method="post">
				<!-- /admin/testPage.do -->
				<table class="gridtable" style="width: 100%;">
					<tr>
						<th>商品：</th>
						<td><input type="text" name="pageNum" /></td>
						<th>页码：</th>
						<td><input type="text" name="pageNum" /></td>
						<th>页面大小：</th>
						<td><input type="text" name="pageSize" /></td>
						<td><input type="submit" value="查询" /></td>
					</tr>
				</table>
			</form>
			<c:if test="${page!=null}">
				<table class="gridtable" style="width: 100%;">
					<thead>
						<tr>
							<th colspan="4">查询结果</th>
						</tr>
						<tr>
							<th>商品</th>
							<th>商品图片</th>
							<th>单价</th>
							<th>爱淘网</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="content">
							<tr>
								<td>${content.ware}</td>
								<td><img alt="${content.ware}" src="${content.pictureurl}"></td>
								<td>${content.preferentialprice}</td>
								<td><a href="${content.lovetaobaourl}">浏览商品</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<jsp:include page="page.jsp" />
			</c:if>
		</div>
		<div class="push"></div>
	</div>
	<div class="footer">
		<div class="info">
			<a href="http://git.oschina.net/free/Mybatis_PageHelper">Mybatis分页插件</a>
			<a href="http://git.oschina.net/free/Mybatis-Sample">Mybatis-Sample</a>
			<br /> 作者：<a href="http://blog.csdn.net/isea533" style="margin: 0;">@Isea533/abel533</a>
		</div>
	</div>
</body>
<script type="text/javascript">
	$("#button1").click(function() {
		alert(123)
		$.ajax({
			url : "admin/testjson.do",
			type : "post",
			success : function(result) {
				$("#span1").html(result);
			}
		});
	});
</script>
</html>

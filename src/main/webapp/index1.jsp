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
<body> <h1>165416161651</h1>
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

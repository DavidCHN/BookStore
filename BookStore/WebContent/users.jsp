<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="./build/react.js"></script>  
<script src="./build/react-dom.js"></script>  
<script src="./build/browser.min.js"></script>
<link rel="stylesheet" href="https://unpkg.com/antd@3.0.1/dist/antd.min.css">
<script type="text/javascript" src="https://unpkg.com/antd@3.0.1/dist/antd.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/babel" src="react.jsx"></script>  
<body>
<div id="root"></div>
<center>
	<form action="UserServlet" method="post">
		用户名：<input type="text" name="username">
		<input type="submit" value="查询交易记录">
	</form>
</center>

</body>
</html>
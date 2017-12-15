<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <link rel="stylesheet" href="./build/semantic.css">
  <script src="./build/semantic.js"></script>
   <script src="./build/jquery.min.js"></script>
<title>Insert title here</title>
</head> 

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
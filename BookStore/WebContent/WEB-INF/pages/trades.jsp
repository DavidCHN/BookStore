<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>

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
<center>
	<c:forEach items="${user.trades }" var="trade">
		<table class="ui celled striped table">

			<thead>
				<tr>
					<th colspan="3">交易人姓名: ${user.username }<br>交易时间: ${trade.tradeTime }  </th>
				</tr>
			</thead>
					<thead>
    <tr><th>名称</th>
    <th>单价</th>
    <th>数量</th>
  </tr></thead>
			<tbody>
				<c:forEach items="${trade.items }" var="item">

					<tr>
						<td class="collapsing">${item.book.title }</td>
						<td class="left aligned">${item.book.price }</td>
						<td class="left aligned">${item.quantity }</td>
					</tr>

				</c:forEach>
			</tbody>
		</table>
	</c:forEach>
</center>
</body>
</html>
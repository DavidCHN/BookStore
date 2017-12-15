<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="./build/react.js"></script>  
<script src="./build/react-dom.js"></script>  
<script src="./build/browser.min.js"></script>
<link rel="stylesheet" href="https://unpkg.com/antd@3.0.1/dist/antd.min.css">
<script type="text/javascript" src="https://unpkg.com/antd@3.0.1/dist/antd.min.js"></script>


<title>Insert title here</title>
</head>
<body>
<script type="text/babel">  

import React from 'react';  
import ReactDOM from 'react-dom';  
import { DatePicker, message } from 'antd';  
  
class App extends React.Component {  
  constructor(props) {  
    super(props);  
    this.state = {  
      date: '',  
    };  
  }  
  handleChange(date) {  
    message.info('您选择的日期是: ' + date.toString());  
    this.setState({ date });  
  }  
  render() {  
    return (  
      <div style={{ width: 400, margin: '100px auto' }}>  
        <DatePicker onChange={value => this.handleChange(value)} />  
        <div style={{ marginTop: 20 }}>当前日期：{this.state.date.toString()}</div>  
      </div>  
    );  
  }  
}  
ReactDOM.render(<App />, document.getElementById('root'));  

</script>  
	<div id="root"></div>
	<center>
		<br><br>
		<h4>User: ${user.username }</h4>
		
		<br><br>
			<table>
				<c:forEach items="${user.trades }" var="trade">
					
					<tr>
					<td>
					<table border="1" cellpadding="10" cellspacing="0">
					
						<tr>
							<td colspan="3">TradTime: ${trade.tradeTime }</td>
						</tr>
		
						<c:forEach items="${trade.items }" var="item">
							
							<tr>
								<td>${item.book.title }</td>
								<td>价格：${item.book.price }</td>
								<td>数量：${item.quantity }</td>
							</tr>
							
						</c:forEach>
						
					</table>
					<br><br>
					</td>
					</tr>
					
				</c:forEach>
			</table>
		
	</center>
	
</body>
</html>
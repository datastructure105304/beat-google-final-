<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Result</title>
</head>
<body bgColor="#F0DEC4">
</p><img src="https://i.screenshot.net/0n9yvae" width="200" height="150"></p>
	<%
		out.println("相關關鍵字：");
	%>
	<br>
	<%
		String[] reList = (String[]) request.getAttribute("related");
		for (int i = 0; i < reList.length; i++) {
	%>
	<h style="font-size:16px ;"><%=reList[i]%></h>
	<br>
	<%
		}
	
	%>
	<br>

	</p><%
		out.println("我們為你找到的咖啡廳結果如下：");
	%></p>
	<br>
	<%
		String[][] orderList = (String[][]) request.getAttribute("result");
		for (int i = 0; i < orderList.length; i++) {
	%>
	<h style="font-size:16px ;"><%=orderList[i][0]%></h>
	<br>
	<a href='<%=orderList[i][1]%>'><%=orderList[i][1]%></a>
	
	
	<br>
	<br>
	<%
		}
	%>
</body>
</html>
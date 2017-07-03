<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/10
  Time: 17:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="/file/cluster" enctype="multipart/form-data">
    <input type="file" name="file" /> <input type="submit" value="submit" />
<input type="hidden" name="uid" value="103">
<input type="text" name="uname" value="10">
</form>
</body>
</html>

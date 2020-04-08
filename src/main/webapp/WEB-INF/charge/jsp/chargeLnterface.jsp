<%--
  Created by IntelliJ IDEA.
  User: 37292
  Date: 2020/3/9
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">
    <title>收费端</title>
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>

</head>
<style>
    /*body {*/
    /*	background-image: linear-gradient(#8b8a8c, #8B8A8C);*/
    /*	background-size: 100% 100%;*/
    /*	background-attachment: fixed;*/
    /*}*/
</style>
<body>
<input type="hidden" id="path" value="<%=path%>">
收费啊收费
<form class="layui-form" onsubmit="return false;"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->

</form>
<script src=<%=path + "/layui/layui.js"%>></script>

<script>

</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String jsPath = request.getContextPath() + "/js/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>自助月缴续费</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
    <script src=<%=jsPath + "json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<div class="layui-form-item" style="text-align: center;font-size: 30px;padding-top: 200px;">
    <label>付款失败!</label>
</div>
<meta http-equiv="refresh" content="5; url=<%=path + "/alipay/path/selfServicePayment"%>">
</body>
</html>
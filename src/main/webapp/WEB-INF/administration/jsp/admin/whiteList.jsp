<%--
  Created by IntelliJ IDEA.
  User: 37292
  Date: 2020/4/14
  Time: 17:27
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
    <title>白名单设置</title>
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/administration/js/admin/whiteList.js"%>></script>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<div style="padding-top: 2%;padding-left: 25%;">
    <table id="demo" lay-filter="test"></table>
</div>
<form class="layui-form" style="display: none" action="" id="modify">
    <div class="layui-form-item"><h1></h1></div>
    <div class="layui-form-item">
        <label class="layui-form-label">联系方式</label>
        <div class="layui-input-inline">
            <input type="text" name="price" id="userTel" required lay-verify="required" placeholder="请输入联系方式"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">车牌号</label>
        <div class="layui-input-inline">
            <input type="text" name="price" id="carNumber" required lay-verify="required" placeholder="请输入车牌号"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-inline">
            <input type="text" name="price" id="userName" required lay-verify="required" placeholder="请输入姓名"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
</form>
</body>
</html>
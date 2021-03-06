<%--
  Created by IntelliJ IDEA.
  User: 37292
  Date: 2020/4/12
  Time: 15:29
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
    <title>收费规则设置</title>
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/administration/js/admin/chargingSettings.js"%>></script>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<div style="padding-top: 2%;padding-left: 25%;">
    <table id="demo" lay-filter="test"></table>
</div>
<form class="layui-form" style="display: none" action="" id="modify">
    <div class="layui-form-item"><h1></h1></div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">时间选择</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="test4" placeholder="HH:mm:ss">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">收费类型</label>
        <div class="layui-input-block">
            <input type="radio" name="type" value="0" title="直接收取" id="zhijie" lay-filter="laytype" checked>
            <input type="radio" name="type" value="1" title="叠加收取" id="diejia" lay-filter="laytype">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">叠加间隔</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="test5" placeholder="HH:mm:ss">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">金额</label>
        <div class="layui-input-inline">
            <input type="text" name="price" id="price" required lay-verify="required" placeholder="请输入收费金额"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
</form>
</body>
</html>


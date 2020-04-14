<%--
  Created by IntelliJ IDEA.
  User: 92059
  Date: 2020/4/14
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <script src=<%=path + "/administration/js/admin/incomeCount.js"%>></script>
    <script charset="UTF-8" src=<%=path+"/js/echarts.js"%>></script>
    <title>收入统计页</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
    <ul class="layui-tab-title">
        <li class="layui-this">近七天收入</li>
        <li>上一月收入</li>
        <li>本季度收入</li>
        <li>近半年收入</li>
        <li>本年度收入</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show"><div id="sevenHistogram" style="width: 100%;height: 500px;"></div></div>
        <div class="layui-tab-item"><div id="preMonthHistogram" style="width:100%;height:500px;"></div></div>
        <div class="layui-tab-item">内容3</div>
        <div class="layui-tab-item">内容4</div>
        <div class="layui-tab-item">内容5</div>
    </div>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String jsPath = request.getContextPath() + "/js/";
    String path = request.getContextPath();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>月缴办理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
    <script src=<%=jsPath + "json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body style=" background-repeat:no-repeat; background-size:100% 100%; background-attachment: fixed;height: 100%">
<input type="hidden" id="path" value="<%=path%>">
<div class="layui-form">
    <div class="layui-row">
        <div class="layui-form-item" style="font-size: 30px;text-align: center;padding-top: 10px">月缴办理</div>
    </div>
    <div class="layui-form-item" style="margin-top: 200px">
        <div class="layui-input-block" style="text-align: center;">
            <input class="layui-btn layui-btn-normal" onclick="addHandle(this)" type="button"
                   value="新增月缴用户" style="width: 150px;">
            <input class="layui-btn layui-btn-normal" onclick="renew(this)" type="button"
                   value="月缴用户续费" style="width: 150px;">
        </div>
    </div>
</div>
<script>

    //新增月缴用户
    function addHandle(node) {
        var path = $("#path").val();
        layui.use('layer', function () {
            var layer = layui.layer;

            layer.open({
                type: 2,
                title: false,
                fix: false,
                maxmin: false,
                shadeClose: true,
                shade: 0.3,
                area: ['420px', '400px'],
                content: path + '/charge/findMonthCharge',
                end: function () {
                    location.reload();
                }
            });
        });
    }
</script>
</body>
</html>

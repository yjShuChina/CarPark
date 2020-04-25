<%--
  Created by IntelliJ IDEA.
  User: 37292
  Date: 2020/4/25
  Time: 9:46
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
    <title>Title</title>
    <%
        String path = request.getContextPath();
    %>
    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>

</head>

<body>
<input type="hidden" id="path" value="<%=path%>">
<form class="layui-form" > <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div style="padding-top: 10%;padding-left: 50%;">
        <div class="layui-form-item">
            <label class="layui-form-label">接受到的帐号：</label>
            <div class="layui-input-inline">
                <input type="text" name="aacc" id="aacc" placeholder="请输入帐号" required lay-verify="required"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">接受到的密码：</label>
            <div class="layui-input-inline">
                <input type="text" name="apass" id="apass" required lay-verify="required" placeholder="请输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <button class="layui-btn" type="button" onclick="getyuejitian()">今天是当月的第几天</button>
        <br>
        <br>
        <button class="layui-btn" type="button" onclick="getWeekOfDate()">今天是周几</button>
        <br>
        <br>
        <button class="layui-btn" type="button" onclick="getdiyitian()">今年的第一天是周几</button>
        <br>
        <br>
        <button class="layui-btn" type="button" onclick="getjitian()">今天是当年的第几天</button>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">请输入一个字符串：</label>
        <div class="layui-input-inline">
            <input type="text" name="aacc" id="str" placeholder="请输入帐号" required lay-verify="required"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <button class="layui-btn" type="button" onclick="asdasd()">提交字符串</button>
</form>
<script src=<%=path + "/layui/layui.js"%>></script>
<script src=<%=path + "/admin/js/post.js"%>></script>
<script>
    function getWeekOfDate() {
        var path = $("#path").val();

        $.ajax({
                url: path + "/admin/getWeekOfDate",
                async: "true",
                type: "Post",
                dataType: "text",
                success: function (res) {
                    layer.alert(res);
                },
                error: function () {
                    layer.msg('网络正忙', {icon: 6});
                }
            }
        );
    }
    function getdiyitian() {
        var path = $("#path").val();

        $.ajax({
                url: path + "/admin/getdiyitian",
                async: "true",
                type: "Post",
                dataType: "text",
                success: function (res) {
                    layer.alert(res);
                },
                error: function () {
                    layer.msg('网络正忙', {icon: 6});
                }
            }
        );
    }
    function getjitian() {
        var path = $("#path").val();

        $.ajax({
                url: path + "/admin/getjitian",
                async: "true",
                type: "Post",
                dataType: "text",
                success: function (res) {
                    layer.alert(res);
                },
                error: function () {
                    layer.msg('网络正忙', {icon: 6});
                }
            }
        );
    }
    function getyuejitian() {
        var path = $("#path").val();

        $.ajax({
                url: path + "/admin/getyuejitian",
                async: "true",
                type: "Post",
                dataType: "text",
                success: function (res) {
                    layer.alert(res);
                },
                error: function () {
                    layer.msg('网络正忙', {icon: 6});
                }
            }
        );
    }
    function asdasd() {
        var path = $("#path").val();
        var str = $("#str").val();
        console.log(str);
        $.ajax({
                url: path + "/admin/str",
                async: "true",
                type: "Post",
                data: "str=" + str,
                dataType: "text",
                success: function (res) {
                    layer.alert(res);
                },
                error: function () {
                    layer.msg('网络正忙', {icon: 6});
                }
            }
        );
    }
</script>
<script>
    //配置插件目录
    layui.config({
        base: '${pageContext.request.contextPath}/layui/'
        , version: '1.0'
    });
    //一般直接写在一个js文件中
    layui.use(['layer', 'form', 'layarea'], function () {
        var layer = layui.layer
            , form = layui.form
            , layarea = layui.layarea;

        layarea.render({
            elem: '#area-picker',
            change: function (res) {
                //选择结果
                console.log(res);
            }
        });
    });

    var path = $("#path").val();
    var websocket = null;
    if ('WebSocket' in window) {
        // var str = "ws://"+path+"/websocket/charge";
        // websocket = new WebSocket("ws://127.0.0.1:8080/Carpark/websocket/charge");
        websocket = new WebSocket("ws://127.0.0.1:8080/Carpark/websocket/charge");
        // websocket = new WebSocket(str);
    } else {
        alert("您的浏览器不支持websocket");
    }

    //消息接收
    websocket.onmessage = function (event) {
        console.log(event)
        var obj = JSON.parse(event.data);
        console.log(obj)
        $("#aacc").val(obj.aacc);
        $("#apass").val(obj.apass);
    }
</script>
</body>
</html>

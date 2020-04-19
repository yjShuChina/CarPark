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
    <script src=<%=path + "/layui/layui.js"%>></script>

</head>
<style>
    a {
        color: #ffffff
    }
</style>
<body>
<input type="hidden" id="path" value="<%=path%>">
<form class="layui-form"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div style="height: 700px;width: 1080px;margin: 0 auto;padding-top:20px;">
        <div style="height: 50px;width: 1080px;margin: 0 auto;background:#393D49">
            <h2 style="color:#009688;padding-top:10px;padding-left: 10px">智能停车场收费端</h2>
        </div>

        <div style="height: 670px;width: 450px;float: left;background:#393D49">

            <div style="height: 360px;width: 440px;padding-left:10px;">
                <div style="height: 20px;width: 440px;margin: 0 auto;background:#2F4056">
                    <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">入口</h6>
                </div>
                <img src="https://i.loli.net/2020/04/19/eGCm64ihjyKkDBp.jpg" id="imgPreJ"
                     style="width: 440px;height: 300px;background:#e2e2e2">
                <div style="height: 40px;width: 440px;margin: 0 auto" class="layui-bg-cyan">
                    <h6 style="color:#ffffff;padding-top:10px;padding-left: 10px">
                        入库时间：2020-04-20 23:56:23 车牌：闽X88888 月缴车辆
                    </h6>
                </div>
            </div>

            <div style="height: 290px;width: 450px;padding-left:10px;padding-top:10px">
                <div style="height: 290px;width: 450px;background:#2F4056">
                    <table id="changnei" lay-filter="test"></table>
                </div>
            </div>
        </div>


        <div style="height: 670px;width: 450px;float: left;background:#393D49">

            <div style="height: 360px;width: 440px;float: left;padding-left:10px;">
                <div style="height: 20px;width: 440px;margin: 0 auto;background:#2F4056">
                    <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">出口</h6>
                </div>
                <img src="https://i.loli.net/2020/04/19/eGCm64ihjyKkDBp.jpg" id="imgPreC"
                     style="width: 440px;height: 300px;">
                <div style="height: 40px;width: 440px;margin: 0 auto;background:#2F4056">
                    <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">
                        <a>入库时间:</a><a id="timer">2020-04-20 23:56:23</a>

                        <a style="padding-left: 10px">车牌:</a><a id="car">闽X88888</a>
                        <a id="" style="padding-left: 10px">月缴车辆</a><br>

                        <a>出库时间:</a><a>2020-04-20 23:56:23</a>
                        <a style="padding-left: 10px">停车时长:</a>1天23小时02分30秒

                    </h6>
                </div>
            </div>

            <div style="height: 330px;width: 440px;float: left;padding-left:10px;padding-top:10px">
                <div style="height: 290px;width: 440px;background:#2F4056">
                    <table id="chuchang" lay-filter="test"></table>
                </div>
            </div>
        </div>

        <div style="height: 670px;width: 170px;float: left;background:#393D49;padding-left:10px;">
            <div style="background:#2F4056;height: 180px;width: 160px;padding-top:20px;">
                <div style="padding-left:30px;">
                    <button type="button" style="height: 40px;width: 100px;" class="layui-btn">换班
                    </button>
                </div>
                <div style="padding-left:30px;padding-top:20px">
                    <button type="button" style="height: 40px;width: 100px;" class="layui-btn">月缴办理
                    </button>
                </div>
            </div>
            <div style="background:#2F4056;height: 120px;width: 160px;padding-top:20px;">
                <h6>停车场信息</h6>
                <div style="padding-left:10px;padding-top:20px">
                <a>场内停车位:</a><a >230</a><br>
                <a>剩余停车位:</a><a >100</a>
                </div>
            </div>
            <div style="background:#2F4056;height: 120px;width: 160px;padding-top:20px;">
                <h6>收费员信息</h6>
                <div style="padding-left:10px;padding-top:20px">
                    <a>收费人员:</a><a >老王</a><br>
                    <a>上班时间:</a><br>
                    <a >2020-04-20 23:56:23</a>
                </div>
            </div>
        </div>

    </div>
</form>


<script>
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://127.0.0.1:8080/Carpark/websocket/1");
    } else {
        alert("您的浏览器不支持websocket");
    }
    websocket.onerror = function () {
        setMessageInHtml("send error！");
    }
    websocket.onopen = function () {
        setMessageInHtml("connection success！")
    }
    websocket.onmessage = function (event) {
        setMessageInHtml(event.data);
        console.log(event);
    }
    websocket.onclose = function () {
        setMessageInHtml("closed websocket!")
    }
    window.onbeforeunload = function () {
        clos();
    }

    // 接收信息
    function setMessageInHtml(message) {
        document.getElementById('message').innerHTML += message;
    }

    //关闭连接
    function clos() {
        websocket.close(3000, "强制关闭");
    }

    //发送信息
    function send() {
        var msg = document.getElementById('text').value;
        websocket.send(msg);
    }
</script>

</body>
</html>

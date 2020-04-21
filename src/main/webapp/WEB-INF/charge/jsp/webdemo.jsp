<%--
  Created by IntelliJ IDEA.
  User: 37292
  Date: 2020/4/17
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"></meta>
    <title>springboot项目WebSocket测试demo</title>
</head>
<body>
<h3>springboot项目websocket测试demo</h3>
<h4>测试说明</h4>
<h5>文本框中数据数据，点击‘发送测试’，文本框中的数据会发送到后台websocket，后台接受到之后，会再推送数据到前端，展示在下方；点击关闭连接，可以关闭该websocket；可以跟踪代码，了解具体的流程；代码上有详细注解</h5>
<br />
<input id="text" type="text" />
<button onclick="send()">发送测试</button>
<hr />
<button onclick="clos()">关闭连接</button>
<hr />
<div id="message"></div>
<script>
    var websocket = null;
    if('WebSocket' in window){
        websocket = new WebSocket("ws://127.0.0.1:8080/Carpark/websocket/charge");
    }else{
        alert("您的浏览器不支持websocket");
    }
    websocket.onerror = function(){
        setMessageInHtml("send error！");
    }
    websocket.onopen = function(){
        setMessageInHtml("connection success！")
    }
    websocket.onmessage  = function(event){
        setMessageInHtml(event.data);
        console.log(event);
    }
    websocket.onclose = function(){
        setMessageInHtml("closed websocket!")
    }
    window.onbeforeunload = function(){
        clos();
    }

    // 接收信息
    function setMessageInHtml(message){
        document.getElementById('message').innerHTML += message;
    }

    //关闭连接
    function clos(){
        websocket.close(3000,"强制关闭");
    }

    //发送信息
    function send(){
        var msg = document.getElementById('text').value;
        websocket.send(msg);
    }
</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String jsPath = request.getContextPath() + "/js/";
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <title>人脸添加</title>
    <link rel="stylesheet" type="text/css" href=<%=path+"/css/facelogincss.css"%>/>
</head>
<body>
<div id="logo">
    <h1 class="hogo"><i> 人脸添加</i></h1>
</div>
<section class="stark-login">
    <form action="" method="">
        <br>
        <video id="video" width="300" height="230" autoplay style=" border: 5px solid #00fffc;"></video>
        <div id="fade-box">
            <input type="button" onclick="addFace()" value="确认添加" class="submit_btn"/>
            <canvas id="canvas" width="400" height="300" hidden></canvas>
        </div>
    </form>
    <div class="hexagons">
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <br>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <br>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <br>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <br>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
        <span>&#x2B22;</span>
    </div>
</section>
<div id="circle1">
    <div id="inner-cirlce1">
        <h2></h2>
    </div>
</div>
<ul>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
</ul>
<script type="text/javascript" src=<%=jsPath + "jquery-3.4.1.js" %>></script>
<script src=<%=path + "/layuiadmin/layui/layui.js"%>></script>

<script type="text/javascript">
    //访问用户媒体设备的兼容方法
    function getUserMedia(constraints, success, error) {
        if (navigator.mediaDevices.getUserMedia) {
            //最新的标准API
            navigator.mediaDevices.getUserMedia(constraints).then(success).catch(error);
        } else if (navigator.webkitGetUserMedia) {
            //webkit核心浏览器
            navigator.webkitGetUserMedia(constraints,success, error)
        } else if (navigator.mozGetUserMedia) {
            //firfox浏览器
            navigator.mozGetUserMedia(constraints, success, error);
        } else if (navigator.getUserMedia) {
            //旧版API
            navigator.getUserMedia(constraints, success, error);
        }
    }

    //var 是定义变量
    var video = document.getElementById("video"); //获取video标签
    var canvas = document.getElementById("canvas");
    var context = canvas.getContext("2d");

    function success(stream) {
        //兼容webkit核心浏览器
        var CompatibleURL = window.URL || window.webkitURL;
        //将视频流设置为video元素的源
        console.log(stream);
        video.srcObject = stream;
        video.play();
    }

    function error(error) {
        console.log('访问用户媒体设备失败${error.name}, ${error.message}');
    }

    if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
        //调用用户媒体设备, 访问摄像头
        getUserMedia({video : {width: 1980, height: 1024}}, success, error);
    } else {
        alert('不支持访问用户媒体');
    }

    function addFace() {
        //把流媒体数据画到convas画布上去
        context.drawImage(video, 0, 0, 400, 300);
        var adminFace = getBase64();
        // console.log('adminFace='+adminFace);
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/adminFace/addAdminFace",
            data: {"adminFace": adminFace},
            success: function (data) {
                if (data.msg === "1") {
                    alert("添加成功")
                    parent.location.reload();
                } else if (data.msg === "2") {
                    alert("添加失败");
                }
            }
        });
    }

    function getBase64() {
        var imgSrc = document.getElementById("canvas").toDataURL(
            "image/png");
        return imgSrc.split("base64,")[1];
    }

</script>

</body>
</html>
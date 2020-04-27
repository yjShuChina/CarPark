<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/8 0008
  Time: 下午 8:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<style>
    a {
        font-size: 30px;
        margin-left: 80px;
    }

    h1 {
        color: white;
    }

 </style>
<head>
    <title>车闸</title>
    <%String path = request.getContextPath();%>
    <script rel="script" src=<%=path + "/js/jquery-3.4.1.js"%>></script>
</head>
<body style="margin: 0">
<div style="height: 100%;width: 19%;float: left;border: 1px solid #92B8B1">
    <img src="https://i.loli.net/2020/04/09/2SBdcZ6i1GIgDMT.png" style="width: 100%;">
    <div style="width: 100%;">
        <br><br>
        <br><br>
        <a>智能停车场</a><br><br>
        <a>&nbsp;&nbsp;欢迎您</a>
    </div>
</div>

<div style="height: 100%;width: 80%;float:right;background:white">

    <div id="img" style="margin: 4% auto;width: 60%;height: 40%;background: #c9c9c9">
        <input type="file" name="fileaot" id="fileaot"  onchange="preImg('fileaot','imgPre')"
               style="margin-top: -50px">
        <input type="button" value="确定" onclick="add()" id="btn"
               style="margin-top: -50px">
        <img src="" id="imgPre" style="width: 100%;height: 100%;margin-top:-20px">
    </div>

    <div id="msg" style="margin: 0 auto;width: 70%;height: 40%;background:white;border:1px solid cornflowerblue">
        <div style="width: 20%;height: 100%;background: chocolate;float: left">
            <div style="margin: 0 auto;width: 30%;height: 100%">
                <h1>用</h1>
                <h1>户</h1>
                <h1>信</h1>
                <h1>息</h1>
            </div>
        </div>
        <a id="username">用户：
        </a><br><br>
        <a id="carnumber">车牌号：</a><br><br>
        <a id="state">车辆情况：
        </a><br><br>
        <a id="time">入库时间：</a><br><br>
        <a id="ps">停车位：</a>
    </div>
</div>


</body>
<script>
    /**
     * 将本地图片 显示到浏览器上
     */
    function preImg(sourceId, targetId) {
        <%--$.ajax({--%>
        <%--            url:"${pageContext.request.contextPath}/gate/img",--%>
        <%--            async: "true",--%>
        <%--            type: "Post",--%>
        <%--            data: "",--%>
        <%--            dataType: "text",--%>
        <%--            success: function (res) {--%>
        <%--                var imgPre = document.getElementById('imgPre');--%>
        <%--                imgPre.src ="data:image/png;base64,"+ res;--%>
        <%--            },--%>
        <%--            error: function () {--%>
        <%--            }--%>
        <%--        }--%>
        <%--);--%>
        var url = getFileUrl(sourceId);
        var imgPre = document.getElementById(targetId);
        imgPre.src = url;
    }

    //从 file 域获取 本地图片 url
    function getFileUrl(sourceId) {
        var url = '';
        var img = document.getElementsByName('img').value;
        if (navigator.userAgent.indexOf("MSIE") >= 1) { // IE
            url = document.getElementById(sourceId).value;
        } else if (navigator.userAgent.indexOf("Firefox") > 0) { // Firefox
            url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
        } else if (navigator.userAgent.indexOf("Chrome") > 0) { // Chrome
            url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
        }
        console.log(document.getElementById(sourceId).files.item(0))
        return url;
    }

    function add() {
        var formData = new FormData();
        formData.append('file', $('#fileaot')[0].files[0]);  //添加图片信息的参数
        $.ajax({
            url: "${pageContext.request.contextPath}/gate/findusermsg",
            type: "post",
            cache: false,
            data: formData,
            processData: false,// 不处理数据
            contentType: false, // 不设置内容类型
            success: function (data) {
                if (data == "NO") {
                    // var file = document.getElementById("fileaot");
                    // var btn = document.getElementById("btn");
                    // file.style.display = "none";
                    // btn.style.display = "none";
                    alert("车位已满")
                }else if(data=="NOCAR"){
                    alert("无识别到车牌")
                }else if (data=="HAVEING") {
	                alert("车牌重复，车辆已入库")
                }else {
	                var carnum = data.split(",")[0];
	                var time1 = data.split(",")[3];
	                var state = data.split(",")[2];
	                var user = data.split(",")[1];
	                var ps = data.split(",")[4];
	                document.getElementById("username").innerHTML = "用户名：  " + user;
	                document.getElementById("carnumber").innerHTML = "车牌号 :  " + carnum;
	                document.getElementById("state").innerHTML = "车辆情况 :  " + state;
	                document.getElementById("time").innerHTML = "入库时间 :  " + time1;
	                document.getElementById("ps").innerHTML = "停车位:  " + ps;
                }
            }
        })

    }
</script>
</html>

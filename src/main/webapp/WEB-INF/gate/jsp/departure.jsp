<%--
  Created by IntelliJ IDEA.
  User: junlong
  Date: 2019-11-16
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>车辆出场</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <%
        String path = request.getContextPath();
    %>
</head>
<style>
    a {
        font-size: 19px;
        margin-left: 20px;
    }

    h1 {
        color: white;
    }
</style>
<body>
<input type="hidden" id="path" value="<%=path%>">
<form class="layui-form" action="" lay-filter="example">
    <div style="height: 100%;width: 19%;float: left;border: 1px solid #92B8B1">
        <img src="https://i.loli.net/2020/04/09/2SBdcZ6i1GIgDMT.png" style="width: 100%;" >
        <div style="width: 100%;text-align:center;">
            <br><br>
            <br><br>
            <a>XXX智能停车场</a><br><br>
            <a>欢迎您下次光临</a>
        </div>
    </div>


    <div class="layadmin-user-login-box layadmin-user-login-header" style="height: 100%;width: 80%;float:right;background:white">
        <div style="padding-bottom: 10px;margin: 0 auto;width: 70%;height: 40%;background:white;margin-top: 30px">
            <div class="layui-upload">
                <button type="button" class="layui-btn layui-btn-normal" name="fileaot" id="fileaot">选择图片</button>
                <button type="button" class="layui-btn" id="test9">确定</button>
                <img src="" id="imgPre" style="width: 100%;height: 400px;margin-top: 10px">
            </div>
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
            <a>车辆牌照：</a><a id="carnumber"></a><br>
            <a>入场时间：</a><a id="timej"></a><br>
            <a>出场时间：</a><a id="timeC"></a><br>
            <a>停放时长：</a><a id="timeData"></a><br>
            <a>应缴费用：</a><a id="money"></a><br>
            <a>车辆情况：</a><a id="state"></a><br>
        </div>
        <div class="demoTable">
            <div style="padding-bottom: 10px;">
                <div class="layui-upload">

                </div>
            </div>
        </div>

    </div>
</form>

<script>
    // function preImg(sourceId, targetId) {
    //     alert("zhixig");
    //     var url = getFileUrl(sourceId);
    //     var imgPre = document.getElementById(targetId);
    //     imgPre.src = url;
    // }
    layui.use(['upload', 'jquery'], function () {
        var upload = layui.upload;
        var path = $("#path").val();
        //执行实例
        var uploadInst = upload.render({
            elem: '#fileaot' //绑定元素
            , url: path + '/charge/uploadTrainPicture' //上传接口
            , auto: false
            , accept: 'file'
            , bindAction: '#test9'
            , done: function (res) {

                console.log(res);
                document.getElementById("carnumber").innerHTML = res.carnumber;
                document.getElementById("timej").innerHTML = res.timej;
                document.getElementById("timeC").innerHTML = res.timeC;
                document.getElementById("timeData").innerHTML = res.timeData;
                document.getElementById("money").innerHTML = res.money;
                document.getElementById("state").innerHTML = res.state;

                document.getElementById("imgPre").src = path + res.url;

            }
            , error: function () {
                //请求异常回调
                layer.msg("上传失败！");
            }

        });
    });
</script>
</body>
</html>

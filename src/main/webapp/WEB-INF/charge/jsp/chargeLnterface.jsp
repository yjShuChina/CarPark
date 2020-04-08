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

</head>
<style>
    /*body {*/
    /*	background-image: linear-gradient(#8b8a8c, #8B8A8C);*/
    /*	background-size: 100% 100%;*/
    /*	background-attachment: fixed;*/
    /*}*/
</style>
<body>
<input type="hidden" id="path" value="<%=path%>">
收费啊收费
<form class="layui-form" onsubmit="return false;"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div style="padding-top: 10%;padding-left: 50%;">
        <div class="layui-form-item">
            <label class="layui-form-label">帐号：</label>
            <div class="layui-input-inline">
                <input type="text" name="cashier_account" placeholder="请输入帐号" required lay-verify="required" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码：</label>
            <div class="layui-input-inline">
                <input type="password" name="cashier_pwd" required lay-verify="required" placeholder="请输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline">
                <img title="点击更换" id="img" src="${pageContext.request.contextPath}/admin/CheckCodeServlet"
                     onclick="changeCode(this)">
            </div>
            <div></div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">验证码：</label>
            <div class="layui-input-inline">
                <input type="text" name="captcha" placeholder="请输入验证码" required lay-verify="required" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="login">登录</button>
                <button type="button" class="layui-btn layui-btn-primary" onclick="register()">注册</button>
            </div>
        </div>
    </div>

</form>
<script src=<%=path + "/layui/layui.js"%>></script>

<script>
    function changeCode(msg) {//验证码
        var path = $("#path").val();
        msg.src = path + "/admin/CheckCodeServlet?num=" + Math.random() + 1;
    }

    layui.use('form', function () {
        var form = layui.form;
        //监听提交
        form.on('submit(login)', function (data) {
            var path = $("#path").val();
            $.ajax({
                    url: path + "/charge/chargeLogin",
                    async: "true",
                    type: "Post",
                    data: data.field,
                    dataType: "text",
                    success: function (res) {
                        if ("success" == res) {
                            layer.msg('登录成功', {icon: 6});
                            window.location = path + "/admin/BackMain";
                        } else if ("captchaerror" == res) {
                            layer.msg('验证码错误', {icon: 5});
                        } else {
                            layer.msg('帐号或密码错误', {icon: 5});
                        }
                    },
                    error: function () {
                        layer.msg('网络正忙', {icon: 6});
                    }
                }
            );
            return false;
        });
    });
</script>
</body>
</html>

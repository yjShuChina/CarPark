<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <meta charset="utf-8">
    <title>管理员登录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href=<%=path+"/layuiadmin/layui/css/layui.css"%> media="all">
    <link rel="stylesheet" href=<%=path+"/layuiadmin/style/admin.css"%> media="all">
    <link rel="stylesheet" href=<%=path+"/layuiadmin/style/login.css"%> media="all">
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <style>
        /* 登陆表单 */
        .lofo_main {
            background-color: rgba(255, 255, 255, 0.9);
            width: 400px;
            position: absolute;
            right: 10%;
            top: 22%;
            z-index: 99999999;
        }

        @media screen and (max-width: 768px) {
            .lofo_main {
                width: 100%;
                height: 350px;
                right: 0;
            }
        }
    </style>
</head>
<body style="background-image: url(https://i.loli.net/2020/04/23/piSHEqKTxzQBIwo.jpg);background-repeat:no-repeat;
        background-size:100% 100%;
        background-attachment: fixed;">
<input type="hidden" id="path" value="<%=path%>">
<form class="layui-form" onsubmit="return false;">
    <div class="lofo_main">
        <div class="layui-row layui-col-space15">
            <div class="layadmin-user-login-main">
                <div class="layadmin-user-login-box layadmin-user-login-header">
                    <h2>传一停车场</h2>
                    <p>管理员登录</p>
                </div>
                <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-username"
                               for="LAY-user-login-username"></label>
                        <input type="text" name="adminAccount" id="LAY-user-login-username" required
                               lay-verify="required" autocomplete="off"
                               placeholder="请输入帐号"
                               class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                               for="LAY-user-login-password"></label>
                        <input type="password" name="adminPwd" id="LAY-user-login-password" required
                               lay-verify="required"
                               placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-row">
                            <div class="layui-col-xs7">
                                <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"
                                       for="LAY-user-login-vercode"></label>
                                <input type="text" name="captcha" id="LAY-user-login-vercode" required
                                       lay-verify="required" autocomplete="off"
                                       placeholder="图形验证码" class="layui-input">
                            </div>
                            <div class="layui-col-xs5">
                                <div style="margin-left: 10px;">
                                    <img id="image" src="${pageContext.request.contextPath}/admin/CheckCodeServlet"
                                         alt="更换验证码"
                                         height="36" width="100%" onclick="changeCode(this)">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item" style="margin-bottom: 20px;">
                        <a class="layadmin-user-jump-change layadmin-link" style="margin-top: 7px;"
                           href=<%=path + "/adminFace/path/adminFaceLogin"%>>人脸识别登录&nbsp;&nbsp;</a>
                    </div>
                    <div class="layui-form-item">
                        <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script src=<%=path + "/layuiadmin/layui/layui.js"%>></script>
<script>
    //获取路径
    var path = $("#path").val();
    //管理员ajax登陆
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(login)', function(data){
            $.ajax({
                    url: path + "/admin/adminLogin",
                    type: "post",
                    data: data.field,
                    dataType: "text",
                    success: function (res) {
                        if(res === 'success'){
                            layer.msg('验证成功，两秒后自动跳转',{icon:6, time:2000},function () {
                                window.location.href = path+'/url/admin/adminMain';
                            })
                        }else {
                            layer.msg(res);
                            changeCode();
                        }
                    },
                    error: function () {
                        layer.msg('网络正忙', {icon: 5});
                    }
                }
            );
            return false;
        });
    });
    //ajax获取验证码
    function changeCode() {
        $("#image").attr("src",path + "/admin/CheckCodeServlet?num="+Math.random())
    }

</script>
</body>
</html>

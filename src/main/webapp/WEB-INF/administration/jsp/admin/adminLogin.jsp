<%--
  Created by IntelliJ IDEA.
  User: 92059
  Date: 2020/4/7
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <title>管理员登陆</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<form class="layui-form" onsubmit="return false;" > <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div style="padding-top: 10%;padding-left: 50%;">
        <div class="layui-form-item">
            <h3 style="align-content: center">智能停车场管理端</h3>
            <hr style="width: 40%">
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">帐号：</label>
            <div class="layui-input-inline">
                <input type="text" name="admin_account" placeholder="请输入帐号" required lay-verify="required" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码：</label>
            <div class="layui-input-inline">
                <input type="password" name="admin_pwd" required lay-verify="required" placeholder="请输入密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline">
                <img title="点击更换" id="img" src="${pageContext.request.contextPath}/admin/CheckCodeServlet" onclick="changeCode(this)">
            </div>
            <div ></div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">验证码：</label>
            <div class="layui-input-inline">
                <input type="text" name="captcha" placeholder="请输入验证码" required lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="login">登录</button>
            </div>
        </div>
    </div>
</form>
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
                        if(res === '验证成功'){
                            layer.alert(res)
                        }else {
                            layer.alert(res)
                            changeCode($('#img'));
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
    //ajax获取验证码
    function changeCode(msg) {
        msg.src = path + "/admin/CheckCodeServlet?num="+Math.random()+1;
    }
</script>
</body>
</html>

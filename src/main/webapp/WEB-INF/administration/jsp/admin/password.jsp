<%--
  Created by IntelliJ IDEA.
  User: 92059
  Date: 2020/4/17
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <script src=<%=path+"/charge/chargeInterface"%>></script>
    <title>修改密码</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">修改密码</div>
                <div class="layui-card-body" pad15>
                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">原密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="oldPassword" lay-verify="required" lay-verType="tips" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="password" lay-verify="pass" lay-verType="tips" autocomplete="off"  class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux">5到16个字符</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">确认新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="repassword" lay-verify="repass" lay-verType="tips" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" onclick="resetPassword()">确认修改</button>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script>
    layui.use(['layer'],function () {
        var layer = layui.layer;
        window.resetPassword =  function() {
            layer.confirm('确定要修改吗',function () {
                $.ajax({
                    url:$('#path').val() + '/admin/resetAdminPassword',
                    type:'post',
                    data:{'oldPassword':$('#oldPassword').val(),'newPassword':$('#password').val()},
                    beforeSend:function () {
                        if($('#oldPassword').val().length < 1){
                            layer.msg('请输入原密码');
                            return false;
                        }else if($('#password').val().length < 5 || $('#password').val().length > 16){
                            layer.msg('密码不在5~16位之间');
                            return false;
                        }else if($('#password').val() !== $('#repassword').val()){
                            layer.msg('两次密码输入不一致');
                            return false;
                        }
                    },
                    success:function (msg) {
                        if(msg === 'success'){
                            layer.msg('修改成功，2秒后跳转至主页',{icon:6, time:2000},function () {
                                window.location.href = $('#path').val()+'/url/admin/homePage';
                            });
                        }else {
                            layer.msg(msg);
                        }
                    },
                    error:function () {
                        layer.msg('网络开小差',{icon:5});
                    }
                })
            })
        }
    })
</script>
</body>
</html>

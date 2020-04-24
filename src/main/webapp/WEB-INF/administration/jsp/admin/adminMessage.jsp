<%--
  Created by IntelliJ IDEA.
  User: 92059
  Date: 2020/4/23
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <title>管理员个人信息页面</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<blockquote class="layui-elem-quote layui-text">
    如信息有误，请联系管理员更改
</blockquote>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>管理员个人信息</legend>
</fieldset>
<form class="layui-form" onsubmit="return false;" lay-filter="formDemo">
    <div class="layui-form-item">
        <label class="layui-form-label">您的账号：</label>
        <div class="layui-input-block">
            <input type="text" name="adminAccount" id="adminAccount" lay-verify="title" autocomplete="off"  class="layui-input" disabled>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">您的姓名：</label>
        <div class="layui-input-block">
            <input type="text" name="adminName" id="adminName" lay-verify="required"  autocomplete="off" class="layui-input" disabled>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手机号:</label>
        <div class="layui-input-block">
            <input type="text" name="adminPhone" id="adminPhone" lay-verify="identity" autocomplete="off" class="layui-input" disabled>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">原地址：</label>
        <div class="layui-input-block">
            <input type="text" name="adminAddress" id="adminAddress" lay-verify="identity" autocomplete="off" class="layui-input" disabled>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">性别：</label>
        <div class="layui-input-block">
            <input type="radio" name="adminSex" value="男" title="男" checked>
            <input type="radio" name="adminSex" value="女" title="女">
        </div>
    </div>

    <div class="layui-upload">
        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
            头像：
            <div class="layui-upload-list">
                <img class="layui-upload-img" id="adminHeadImg" style="width: 100px;height: 100px">
            </div>
        </blockquote>
    </div>
</form>

</body>
<script>
    layui.use(['upload','layer', 'form'], function () {
        var layer = layui.layer
            , form = layui.form
            ,upload = layui.upload;

        $(document).ready(function () {
            var path = $('#path').val();
            $.ajax({
                url:path + '/admin/findCurrentAdmin',
                type:'post',
                success:function (msg) {
                    if(msg !== null && msg !== ''){
                        form.val('formDemo', {
                            "adminAccount": msg.adminAccount // "name": "value"
                            ,"adminName": msg.adminName
                            ,"adminAddress": msg.adminAddress
                            ,"adminPhone":msg.adminPhone
                            ,"adminSex": msg.adminSex
                        });
                        $('#adminHeadImg').attr('src',path+msg.adminHeadImg);
                    }
                },
                error:function () {
                    layer.msg('网络开小差啦',{icon:5});
                }
            })
        });
    });
</script>
</html>

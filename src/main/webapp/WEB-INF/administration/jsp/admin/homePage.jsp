<%--
  Created by IntelliJ IDEA.
  User: 92059
  Date: 2020/4/15
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <title>控制页主页</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>停车场实时流量</legend>
</fieldset>
<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm6 layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">
                    空余车位
                    <span class="layui-badge layui-bg-blue layuiadmin-badge" style="margin-left: 70%">今日</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <h1 id="restPosition">55</h1>
                    <p>
                        总车位数
                        <span class="layuiadmin-span-color" id="totalPosition" style="float: right">100<i class="layui-inline layui-icon layui-icon-flag" ></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">
                    新增月缴用户
                    <span class="layui-badge layui-bg-blue layuiadmin-badge" style="margin-left: 65%">今日</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <h1 id="newUser">5</h1>
                    <p>
                        月缴用户总数
                        <span class="layuiadmin-span-color" id="totalUser" style="float: right">100<i class="layui-inline layui-icon layui-icon-flag" ></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">
                    停车场收入
                    <span class="layui-badge layui-bg-green layuiadmin-badge" style="margin-left: 70%">今日</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <h1  id="newIncome">1000</h1>
                    <p>
                        总收入
                        <span class="layuiadmin-span-color" id="totalIncome" style="float: right">10000<i class="layui-inline layui-icon layui-icon-dollar"></i></span>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
layui.user(['layer'],function () {
    var layer = layui.layer,time;

    $(document).ready(function () {
        getData();
        time = setInterval(getData,600000);
    })


    window.getData = function () {
        $.ajax({
            url:$('#path').val() + '/admin/getData',
            type:'post',
            success:function (msg) {

            },
            error:function () {
                layer.msg('网络开小差',{icon:5});
            }
        })
    }
});
</script>
</html>

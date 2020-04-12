<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String jsPath = request.getContextPath() + "/js/";
    String path = request.getContextPath();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>月缴办理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
    <script src=<%=jsPath + "json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body style=" background-repeat:no-repeat; background-size:100% 100%; background-attachment: fixed;height: 100%">
<input type="hidden" id="path" value="<%=path%>">
<div class="layui-form">
    <div class="layui-row">
        <div class="layui-form-item" style="font-size: 30px;text-align: center;padding-top: 10px">月缴办理</div>
    </div>
    <div class="layui-form-item" style="margin-top: 200px;">
        <div class="layui-input-block" style="padding-left: 10%;">
            <label class="layui-form-label" style="display: inline-block;">车牌号：</label>
            <form class="layui-form" action="">
                <div class="layui-input-inline" style="display: inline-block;">
                    <input type="text" id="carNumber" name="${carNumber}" lay-verify="carNumber" placeholder="请输入车牌号"
                           autocomplete="off" class="layui-input">
                </div>
                <div class="layui-input-inline" style="display: inline-block;">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="formDemo" style="width: 100px;">
                        查询
                    </button>
                </div>
            </form>
            <div class="layui-input-inline" style="display: inline-block;">
                <input class="layui-btn layui-btn-normal" onclick="addHandle(this)" type="button"
                       value="新增月缴用户" style="width: 150px;">
            </div>
            <div class="layui-input-inline" style="display: inline-block;">
                <input class="layui-btn layui-btn-normal" onclick="renew(this)" type="button"
                       value="办理月缴续费" style="width: 150px;">
            </div>
            <div class="layui-input-inline" style="display: inline-block;">
                <input class="layui-btn layui-btn-normal" onclick="refund(this)" type="button"
                       value="办理月缴退费" style="width: 150px;">
            </div>
        </div>
    </div>
</div>
<script>

    layui.use(['form', 'jquery','layer',], function () {
        var form = layui.form;
        $ = layui.jquery;
        var layer = layui.layer;
        var path = $("#path").val();

        form.verify({
            carNumber: [
                /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$/
                , '请输入正确的车牌号'
            ]
        });

        //监听提交
        form.on('submit(formDemo)', function (data) {

            // layer.msg(JSON.stringify(data.field));
            var carNumber = $("#carNumber").val();
            console.log("查询车牌号 = " + carNumber);
            $.ajax({
                url: path + "/charge/monthVIP",
                async: true,
                type: "POST",
                data: "carNumber=" + carNumber,
                datatype: "text",
                success: function (msg) {

                    if (msg == "success") {
                        layer.alert('该用户为月缴用户！', {icon: 6});
                    } else if (msg == "pass") {
                        layer.alert('该用户月缴已过期，请充值！', {icon: 5});
                    } else {
                        layer.alert('用户不是月缴用户！', {icon: 5});
                    }
                },
                error: function () {
                    layer.alert('网络繁忙！', {icon: 7});
                }
            });
            return false;//阻止表单跳转
        });
    });

    //新增月缴用户
    function addHandle(node) {
        var carNumber = $("#carNumber").val();
        var path = $("#path").val();
        layui.use('layer', function () {
            var layer = layui.layer;

            layer.open({
                type: 2,
                title: false,
                fix: false,
                maxmin: false,
                shadeClose: true,
                shade: 0.3,
                area: ['420px', '400px'],
                content: path + '/charge/findMonthCharge?carNumber=' + carNumber,
                end: function () {
                    location.reload();
                }
            });
        });
    }

    //月缴用户到期续费
    function renew(node) {
        var carNumber = $("#carNumber").val();
        var path = $("#path").val();
        layui.use('layer', function () {
            var layer = layui.layer;

            layer.open({
                type: 2,
                title: false,
                fix: false,
                maxmin: false,
                shadeClose: true,
                shade: 0.3,
                area: ['420px', '300px'],
                content: path + '/charge/renewalFeeShow?carNumber=' + carNumber,
                end: function () {
                    location.reload();
                }
            });
        });
    }

    //月缴用户退费
    function refund(node) {
        var carNumber = $("#carNumber").val();
        var path = $("#path").val();
        layui.use('layer', function () {
            var layer = layui.layer;

            layer.open({
                type: 2,
                title: false,
                fix: false,
                maxmin: false,
                shadeClose: true,
                shade: 0.3,
                area: ['420px', '350px'],
                content: path + '/charge/refundShow?carNumber=' + carNumber,
                end: function () {
                    location.reload();
                }
            });
        });
    }
</script>
</body>
</html>

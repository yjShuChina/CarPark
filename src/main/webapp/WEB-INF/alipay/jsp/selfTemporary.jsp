<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String jsPath = request.getContextPath() + "/js/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>临时车辆缴费</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
    <script src=<%=jsPath + "json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body>
<form class="layui-form" action=<%=path + "/alipay/tradePay"%> method=post target="_parent">
    <div class="layui-form-item" style="text-align: center;font-size: 30px;padding-top: 10px;">
        <label>临时车辆缴费</label>
    </div>
    <input type="hidden" id="path" value="<%=path%>">
    <div class="layui-form-item">
        <label class="layui-form-label">订单号：</label>
        <div class="layui-input-inline">
            <input type="text" id="outTradeNo" name="outTradeNo" class="layui-input" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">订单名称：</label>
        <div class="layui-input-inline">
            <input type="text" id="subject" name="subject" class="layui-input" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">车牌号：</label>
        <div class="layui-input-inline">
            <input type="text" id="carNumber" name="carNumber" value="${carNumber}" required lay-verify="required"
                   placeholder="请输入车牌号"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">入场时间：</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="entryTime" name="entryTime" required lay-verify="required" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">缴费时间：</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="handleTime" name="handleTime" required lay-verify="required" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">停放时长：</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="time" name="time" required lay-verify="required" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">付款金额：</label>
        <div class="layui-input-inline">
            <input type="text" id="totalAmount" name="totalAmount" class="layui-input" required readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <input class="layui-btn" type="submit" value="立即结算">
        </div>
    </div>
</form>
<script>

    layui.use(['laydate', 'layer', 'form'], function () {
        var form = layui.form;
        var laydate = layui.laydate;
        var layer = layui.layer;

        //输入车牌号临时用户的出入场时间和费用
        $("#carNumber").mouseout(function () {
            var carNumber = $("#carNumber").val();
            var check = new RegExp("^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$");
            if (carNumber.length > 0) {
                if (carNumber.length >= 7 && carNumber.length <= 8 && check.test(carNumber)) {
                    console.log('carNumber=' + carNumber);
                    var path = $("#path").val();
                    $.ajax({
                        url: path + "/alipay/temporaryCarShow",
                        async: true,
                        type: "POST",
                        data: "carNumber=" + carNumber,
                        datatype: "text",
                        success: function (res) {
                            if (res == "error") {
                                layer.alert('无进场记录！', {icon: 5});

                            }else {
                                console.log('进场时间=' + res.timej);
                                console.log('缴费时间=' + res.timeC);
                                console.log('停车时长=' + res.timeData);
                                console.log('停车费用=' + res.money);
                                $("#entryTime").val(res.timej);
                                $("#handleTime").val(res.timeC);
                                $("#time").val(res.timeData);
                                $("#totalAmount").val(res.money);
                            }
                        },
                        error: function () {
                            layer.alert('网络繁忙！', {icon: 7});
                        }
                    });
                } else {
                    layer.alert('车牌号输入有误！', {icon: 5});
                }
            }
        });

        form.verify({
            carNumber: [
                /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/
                , '请输入正确的车牌号'
            ]
        });

        function GetDateNow() {
            var vNow = new Date();
            var sNow = "";
            sNow += String(vNow.getFullYear());
            sNow += String(vNow.getMonth() + 1);
            sNow += String(vNow.getDate());
            sNow += String(vNow.getHours());
            sNow += String(vNow.getMinutes());
            sNow += String(vNow.getSeconds());
            sNow += String(vNow.getMilliseconds());
            document.getElementById("outTradeNo").value = sNow;
            document.getElementById("subject").value = "临时车辆";
        }

        GetDateNow();
    });
</script>
</body>
</html>
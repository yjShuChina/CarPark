<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String jsPath = request.getContextPath() + "/js/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>自助缴费办理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
    <script src=<%=jsPath + "json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body>
<form class="layui-form" action=<%=path + "/alipay/tradePay"%> method=post target="_parent">
    <div class="layui-form-item" style="text-align: center;font-size: 30px;padding-top: 10px;">
        <label>自助缴费办理</label>
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
            <input type="text" id="carNumber" name="carNumber" value="${carNumber}" required lay-verify="carNumber"
                   placeholder="请输入车牌号"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">生效时间：</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="monthVipBegin" name="monthVipBegin" required lay-verify="required" placeholder="yyyy-mm-dd" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">月缴产品：</label>
        <div class="layui-input-inline">
            <select id="mcpId" name="mcpId" required lay-filter="mcpId">
                <option value="">请选择</option>
                <c:forEach items="${monthChargeParameterList}" begin="0" step="1" var="i">
                    <option value="${i.mcpId}">${i.month}个月</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">付款金额：</label>
        <div class="layui-input-inline">
            <input type="text" id="totalAmount" name="totalAmount" class="layui-input" required lay-verify="required" readonly>
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

        //输入车牌号获取原到期时间，做新的生效时间
        $("#carNumber").mouseout(function () {
            var carNumber = $("#carNumber").val();
            var check = new RegExp("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$");
            if (carNumber.length > 0) {
                if (carNumber.length >= 7 && carNumber.length <= 8 && check.test(carNumber)) {
                    console.log('carNumber=' + carNumber);
                    var path = $("#path").val();
                    $.ajax({
                        url: path + "/alipay/newTime",
                        async: true,
                        type: "POST",
                        data: "carNumber=" + carNumber,
                        datatype: "text",
                        success: function (msg) {
                            if (msg == "pass") {
                                layer.alert('该用户月缴已过期，请充值', {icon: 6});
                            } else if (msg == "error") {
                                layer.alert('用户不是月缴用户', {icon: 5});
                            } else {
                                console.log('msg=' + msg);
                                $("#monthVipBegin").val(msg);
                                // $("#monthVipBegin").attr("disabled", true); //禁用
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

        //选择月份查询对应的月缴金额
        form.on("select(mcpId)", function (data) {
            var mcpId = $("#mcpId").val();
            console.log('月缴金额mcpId=' + mcpId);
            var path = $("#path").val();
            $.ajax({
                url: path + "/alipay/payment",
                async: true,
                type: "POST",
                data: "mcpId=" + mcpId,
                datatype: "text",
                success: function (msg) {
                    console.log('msg=' + msg);
                    $("#totalAmount").val(msg);
                },
                error: function () {
                    layer.alert('网络繁忙！', {icon: 7});
                }
            });
        });

        form.verify({
            carNumber: [
                /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$/
                , '请输入正确的车牌号'
            ]
        });

        //监听提交
        // form.on('submit(formDemo)', function (data) {
        //
        //     var outTradeNo = $("#outTradeNo").val();
        //     var subject = $("#subject").val();
        //     var carNumber = $("#carNumber").val();
        //     var monthVipBegin = $("#monthVipBegin").val();
        //     var mcpId = $("#mcpId").val();
        //     var totalAmount = $("#totalAmount").val();
        //     var path = $("#path").val();
        //
        //     $.ajax({
        //         url: path + "/alipay/tradePay",
        //         async: true,
        //         type: "POST",
        //         data: "outTradeNo=" + outTradeNo + "&subject=" + subject + "&carNumber=" + carNumber + "&monthVipBegin=" + monthVipBegin + "&mcpId=" + mcpId + "&totalAmount=" + totalAmount,
        //         datatype: "text",
        //         success: function (msg) {
        //
        //             if (msg == "success") {
        //                 alert('新增成功！');
        //                 var index = parent.layer.getFrameIndex(window.name);
        //                 parent.layer.close(index);//关闭弹出的子页面窗口
        //                 window.location.reload();
        //             } else {
        //                 alert('新增失败！');
        //             }
        //         },
        //         error: function () {
        //             layer.alert('网络繁忙！', {icon: 7});
        //         }
        //     });
        //     return false;
        // });

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
            document.getElementById("subject").value = "月缴续费";
        }

        GetDateNow();

    });
</script>
</body>
</html>
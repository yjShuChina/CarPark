<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String jsPath = request.getContextPath() + "/js/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>办理月缴续费</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
    <script src=<%=jsPath + "json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body>
<form class="layui-form" action="">
    <div class="layui-form-item" style="text-align: center;font-size: 30px;padding-top: 10px;">
        <label>办理月缴续费</label>
    </div>
    <input type="hidden" id="path" value="<%=path%>">
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
        <label class="layui-form-label">月缴产品：</label>
        <div class="layui-input-inline">
            <select id="mcpId" name="mcpId" lay-verify="required">
                <option value="">请选择</option>
                <c:forEach items="${monthChargeParameterList}" begin="0" step="1" var="i">
                    <option value="${i.mcpId}">${i.month}个月</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">生效时间：</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="monthVipBegin" name="${monthVipBegin}" lay-verify="monthVipBegin"
                   placeholder="yyyy-mm-dd" readonly>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
        </div>
    </div>
</form>
<script>

    layui.use(['laydate', 'layer', 'form'], function () {
        var form = layui.form;
        var laydate = layui.laydate;
        var layer = layui.layer;

        //输入车牌号验证用户是否月缴到期
        $("#carNumber").mouseout(function () {
            var carNumber = $("#carNumber").val();
            var check = new RegExp("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$");
            if (carNumber.length > 0) {
                if (carNumber.length >= 7 && carNumber.length <= 8 && check.test(carNumber)) {
                    console.log('carNumber=' + carNumber);
                    var path = $("#path").val();
                    $.ajax({
                        url: path + "/charge/timeOut",
                        async: true,
                        type: "POST",
                        data: "carNumber=" + carNumber,
                        datatype: "text",
                        success: function (msg) {
                            if (msg == "pass") {
                                layer.alert('该用户月缴已过期，请充值', {icon: 6});
                                var currentdate = getNowFormatDate();
                                $("#monthVipBegin").val(currentdate);
                            } else if (msg == "error") {
                                layer.alert('非月缴用户,请去新增用户！', {icon: 5});
                            } else {
                                console.log('msg=' + msg);
                                $("#monthVipBegin").val(msg);
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
                /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$/
                , '请输入正确的车牌号'
            ]
        });
        //监听提交
        form.on('submit(formDemo)', function (data) {
            // layer.msg(JSON.stringify(data.field));

            var carNumber = $("#carNumber").val();
            var monthVipBegin = $("#monthVipBegin").val();
            var mcpId = $("#mcpId").val();
            var path = $("#path").val();
            var tbUser = {
                "carNumber": carNumber,
                "monthVipBegin": monthVipBegin
            };
            tbUser = JSON.stringify(tbUser);

            $.ajax({
                url: path + "/charge/renewalFee",
                async: true,
                type: "POST",
                data: "tbUser=" + tbUser + "&mcpId=" + mcpId,
                datatype: "text",
                success: function (msg) {

                    if (msg == "success") {
                        alert('续费成功！');
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);//关闭弹出的子页面窗口
                        window.location.reload();
                    } else {
                        alert('续费失败！');
                    }
                },
                error: function () {
                    layer.alert('网络繁忙！', {icon: 7});
                }
            });

            return false;
        });
    });

    //今天时间
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }

</script>
</body>
</html>
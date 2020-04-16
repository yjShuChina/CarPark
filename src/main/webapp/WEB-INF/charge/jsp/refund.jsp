<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String jsPath = request.getContextPath() + "/js/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>办理月缴退费</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
    <script src=<%=jsPath + "json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body>
<form class="layui-form" action="">
    <div class="layui-form-item" style="text-align: center;font-size: 30px;padding-top: 10px;">
        <label>办理月缴退费</label>
    </div>
    <input type="hidden" id="path" value="<%=path%>">
    <div class="layui-form-item">
        <label class="layui-form-label">车牌号：</label>
        <div class="layui-input-inline">
            <input type="text" id="carNumber" name="carNumber" value="${carNumber}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">生效时间：</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="monthVipBegin" name="${monthVipBegin}" disabled>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">月缴产品：</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="month" value="${month}" disabled>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">退款金额：</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="price" name="${price}" disabled>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
        </div>
    </div>
</form>
</body>
</html>
<script>

    layui.use(['layer', 'form'], function () {
        var form = layui.form;
        var layer = layui.layer;

        //输入车牌号验证用户是否月缴到期
        $("#carNumber").mouseout(function () {
            var carNumber = $("#carNumber").val();
            var check = new RegExp("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$");
            if (carNumber.length > 0) {
                if (carNumber.length >= 7 && carNumber.length <= 8 && check.test(carNumber)) {
                    console.log('退费carNumber=' + carNumber);
                    var path = $("#path").val();
                    $.ajax({
                        url: path + "/charge/refundCheck",
                        async: true,
                        type: "POST",
                        data: "carNumber=" + carNumber,
                        datatype: "text",
                        success: function (msg) {
                            var data = JSON.parse(msg);
                            console.log("data=" + data.toString());
                            var currentdate = getNowFormatDate();
                            console.log('今天时间=' + currentdate);
                            var monthVipBegin = data.monthVipBegin;
                            console.log('到期时间=' + monthVipBegin);
                            if (new Date(currentdate) < new Date(monthVipBegin)) {
                                //显示退费信息
                                $("#monthVipBegin").val(data.monthVipBegin);
                                $("#month").val(data.month + '个月');
                                $("#price").val(data.price);
                            } else {
                                layer.alert('月缴已生效，无法办理退费！', {icon: 5});
                            }
                        },
                        error: function () {
                            alert("网络繁忙！")
                        }
                    });
                } else {
                    layer.alert('车牌号输入有误！', {icon: 5});
                }
            }
        });

        //监听提交
        form.on('submit(formDemo)', function (data) {
            // layer.msg(JSON.stringify(data.field));

            var carNumber = $("#carNumber").val();
            var price = $("#price").val();
            var path = $("#path").val();

            $.ajax({
                url: path + "/charge/refund",
                async: true,
                type: "POST",
                data: "carNumber=" + carNumber + "&price=" + price,
                datatype: "text",
                success: function (msg) {

                    if (msg == "success") {
                        alert('退费办理成功！');
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);//关闭弹出的子页面窗口
                        window.location.reload();
                    } else {
                        alert('退费办理失败！');
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

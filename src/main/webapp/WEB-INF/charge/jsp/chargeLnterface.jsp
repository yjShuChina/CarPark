<%--
  Created by IntelliJ IDEA.
  User: 37292
  Date: 2020/3/9
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">
    <title>收费端</title>
    <%String path = request.getContextPath();%>

    <%--    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>--%>
    <script src=<%=path + "/js/json2.js"%>></script>
    <%--    <script src=<%=path + "/layui/layui.js"%>></script>--%>
    <link rel="stylesheet" href=<%=path+"/dist/css/layui.css"%>>
    <script src=<%=path + "/dist/layui.js"%>></script>

</head>
<style>
    a {
        color: #ffffff
    }
</style>
<body>
<input type="hidden" id="path" value="<%=path%>">
<form class="layui-form"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div style="height: 700px;width: 1080px;margin: 0 auto;padding-top:20px;">
        <div style="height: 50px;width: 1080px;margin: 0 auto;background:#393D49">
            <h2 style="color:#009688;padding-top:10px;padding-left: 10px">智能停车场收费端</h2>
        </div>

        <div style="height: 670px;width: 450px;float: left;background:#393D49">

            <div style="height: 360px;width: 440px;padding-left:10px;">
                <div style="height: 20px;width: 440px;margin: 0 auto;background:#2F4056">
                    <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">入口</h6>
                </div>
                <img src="https://i.loli.net/2020/04/19/eGCm64ihjyKkDBp.jpg" id="imgPreGate"
                     style="width: 440px;height: 300px;background:#e2e2e2">
                <div style="height: 40px;width: 440px;margin: 0 auto" class="layui-bg-cyan">
                    <h6 style="color:#ffffff;padding-top:10px;padding-left: 10px">
                        <a>入库时间:</a><a id="gateCarTime">暂无信息</a>
                        <a style="padding-left: 10px">车牌:</a><a id="gateCarNumber">暂无信息</a>
                        <a id="gateCarIdentity" style="padding-left: 10px"></a><br>
                    </h6>
                </div>
            </div>

            <div style="height: 290px;width: 440px;padding-left:10px;padding-top:10px">
                <div style="height: 20px;width: 440px;background:#2F4056">
                    <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">场内现有车辆</h6>
                </div>
                <div style="height: 270px;width: 440px;background:#2F4056">
                    <table id="changnei" lay-filter="test"></table>
                </div>
            </div>
        </div>


        <div style="height: 670px;width: 450px;float: left;background:#393D49">

            <div style="height: 360px;width: 440px;float: left;padding-left:10px;">
                <div style="height: 20px;width: 440px;margin: 0 auto;background:#2F4056">
                    <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">出口</h6>
                </div>
                <img src="https://i.loli.net/2020/04/19/eGCm64ihjyKkDBp.jpg" id="departureUrl"
                     style="width: 440px;height: 300px;">
                <div style="height: 40px;width: 440px;margin: 0 auto;background:#2F4056">
                    <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">
                        <a>入库时间: </a><a id="timer">暂无信息</a>
                        <a style="padding-left: 10px">车牌: </a><a id="carnumber">暂无信息</a>
                        <a id="state" style="padding-left: 10px"></a><br>

                        <a>出库时间: </a><a id="timeC">暂无信息</a>
                        <a style="padding-left: 10px">停车时长: </a><a id="timeData">暂无信息</a>

                    </h6>
                </div>
            </div>

            <div style="height: 330px;width: 440px;float: left;padding-left:10px;padding-top:10px">
                <div style="height: 20px;width: 440px;margin: 0 auto;background:#2F4056">
                    <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">出场车辆历史记录</h6>
                </div>
                <div style="height: 270px;width: 440px;background:#2F4056">
                    <table id="chuchang" lay-filter="test"></table>
                </div>
            </div>
        </div>

        <div style="height: 670px;width: 170px;float: left;background:#393D49;padding-left:10px;">
            <div style="background:#2F4056;height: 240px;width: 160px;padding-top:20px;">
                <div style="padding-left:30px;">
                    <%--                    <a href="${pageContext.request.contextPath}/charge/download" download="xxx.xls">--%>
                    <button type="button" style="height: 40px;width: 100px;" class="layui-btn" onclick="settlement()">
                        下班结单
                    </button>
                    <%--                    </a>--%>
                </div>
                <div style="padding-left:30px;padding-top:20px">
                    <button type="button" style="height: 40px;width: 100px;" class="layui-btn"
                            onclick="monthlyPayment()">月缴办理
                    </button>
                </div>
                <div style="padding-left:30px;padding-top:20px">
                    <button type="button" style="height: 40px;width: 100px;" class="layui-btn" onclick="jumpAddFace()">
                        添加人脸
                    </button>
                </div>
                <div style="padding-left:30px;padding-top:20px">
                        <button type="button" style="height: 40px;width: 100px;" class="layui-btn" onclick="exitcharge()">
                            退出登录
                        </button>
                </div>
            </div>
            <div style="background:#2F4056;height: 120px;width: 160px;padding-top:20px;">
                <h6>停车场信息</h6>
                <div style="padding-left:10px;padding-top:20px">
                    <a>场内停车位: </a><a id="allps">0</a><br>
                    <a>剩余停车位: </a><a id="parkspase">0</a>
                </div>
            </div>
            <div style="background:#2F4056;height: 120px;width: 160px;padding-top:20px;">
                <h6>收费员信息</h6>
                <div style="padding-left:10px;padding-top:20px">
                    <a>收费人员: </a><a>${tbCashier.name}</a><br>
                    <a>上班时间: </a><br>
                    <a>${tbCashier.time}</a>
                </div>
            </div>
        </div>
    </div>

</form>
<form class="layui-form" style="display: none" action="" id="modify">
    <div class="layui-form-item"><h1></h1></div>
    <div style="height: 430px;width: 910px;background:#393D49;margin: 0 auto;">

        <div style="height: 320px;width: 440px;padding-left:10px;float: left;padding-top:10px">
            <div style="height: 20px;width: 440px;margin: 0 auto;background:#2F4056">
                <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">车辆进场时照片</h6>
            </div>
            <img src="https://i.loli.net/2020/04/19/eGCm64ihjyKkDBp.jpg"
                 style="width: 440px;height: 300px;background:#e2e2e2" id="chargeGateUrl">

        </div>

        <div style="height: 320px;width: 440px;padding-left:10px;float: left;padding-top:10px">
            <div style="height: 20px;width: 440px;margin: 0 auto;background:#2F4056">
                <h6 style="color:#ffffff;padding-top:1px;padding-left: 10px">车辆出场照片</h6>
            </div>
            <img src="https://i.loli.net/2020/04/19/eGCm64ihjyKkDBp.jpg"
                 style="width: 440px;height: 300px;background:#e2e2e2" id="chargeDepartureUrl">
        </div>
        <div style="height: 90px;width: 890px;padding-left:10px;float: left;padding-top:10px;">
            <div style="background:#2F4056;height: 80px;width: 890px;">
                <div style="float: left">
                    <h6 style="color:#ffffff;padding-top:15px;padding-left: 15px">
                        <a>入库时间: </a><a id="chargeTimer">2020-4-20 23:53:6</a>
                    </h6>
                    <h6 style="color:#ffffff;padding-top:15px;padding-left: 15px">
                        <a>出库时间: </a><a id="chargeTimeC">2020-4-20 23:53:6</a>
                    </h6>
                </div>

                <div style="float: left">
                    <h6 style="color:#ffffff;padding-top:10px;padding-left: 150px">
                        <a>车牌: </a><a id="chargeCarnumber" style="color:#ffff00;font-size:20px;">闽D12345</a>
                    </h6>
                    <h6 style="color:#ffffff;padding-top:10px;padding-left: 185px">
                        <a style="color:#ffff00;font-size:20px;" id="chargeState">月缴车辆</a>
                    </h6>
                </div>
                <div style="float: left">
                    <h6 style="color:#ffffff;padding-top:5px;padding-left: 190px">
                        <a>应收: </a><a id="chargeMoney" style="color:#ffff00;font-size:27px;">0元</a>
                    </h6>
                    <h6 style="color:#ffffff;padding-top:10px;padding-left: 150px">
                        <a>停车时长: </a><a id="chargeTimeData" style="color:#ffff00;font-size:18px;">2020-4-20 23:53:6</a>
                    </h6>
                </div>
            </div>
        </div>
    </div>
</form>
<form class="layui-form" style="display: none" action="" id="settlement">

</form>

<script src=<%=path + "/charge/js/messaging.js"%>></script>
<%--<script src=<%=path + "/js/jquery-3.4.1.js"%>></script>--%>
<script src=<%=path + "/charge/js/chargeLnterface.js"%>></script>

<script>
    function jumpAddFace() {
        window.location.href = "${pageContext.request.contextPath}/charge/path/addChargeFace";
    }
</script>
</body>
</html>

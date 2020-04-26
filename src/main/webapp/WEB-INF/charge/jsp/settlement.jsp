<%--
  Created by IntelliJ IDEA.
  User: 37292
  Date: 2020/4/25
  Time: 23:50
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
    <title>Title</title>
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
</head>

<body>
<input type="hidden" id="path" value="<%=path%>">
<form class="layui-form" onsubmit="return false;" > <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div style="text-align: center;margin: 10px auto;"><h2>日结账单</h2></div>
    <div class="layui-form-item" style="text-align: center;margin: 30px auto">
        <label class="layui-form-label">渠道选择:</label>
        <div class="layui-input-inline">
            <select name="lySelect" id="lySelect" lay-filter="fieldChoice">
                <option value="">请选择</option>
                <option value="人工收取">人工收取</option>
                <option value="自助缴费">自助缴费</option>
                <option value="月缴车辆">月缴车辆</option>
                <option value="高级VIP">高级VIP</option>
            </select>
        </div>
    </div>
    <table id="settlementTable" lay-filter="test"></table>
</form>

<script>
    layui.use(['form', 'layer', 'table'], function () {
        var layer = layui.layer //弹层
            , table = layui.table //表格
        var path = $("#path").val();
        table.render({
            elem: '#settlementTable'
            , height: 300
            , url: path + '/charge/settlementQuery' //数据接口
            , title: '日结账单'
            // , width: 810
            , toolbar: true //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            , totalRow: true //开启合计行
            // , page: true //开启分页
            // , size: 'sm' //小尺寸的表格
            , cols: [[ //表头
                {field: 'tceId', title: 'ID', width: 80, hide: true}
                , {field: 'carNumber', title: '车牌号', width: 100}
                , {field: 'carIdentity', title: '车辆类型', width: 95}
                , {field: 'parkSpaceId', title: '所停车位', width: 95}
                , {field: 'channel', title: '收费渠道', width: 95}
                , {field: 'price', title: '金额', width: 80, sort: true, totalRow: true}
                , {
                    field: 'entryTime', title: '进场时间', width: 170, sort: true, templet: function (d) {
                        return timeDatezhuang(d.entryTime);
                    }
                }
                , {
                    field: 'exitTime', title: '出场时间', width: 170, sort: true, templet: function (d) {
                        return timeDatezhuang(d.exitTime);
                    }
                }
            ]]
            , id: 'settlementid'
        });

    })
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        form.on('select(fieldChoice)', function (data) {
            var val = data.value;
            layui.use('table', function () {
                var table = layui.table;
                table.reload('settlementid', {
                    where: {
                        aname: val
                    }
                });
            })
        });
    });
    function timeDatezhuang(time) {
        var date = new Date(time);
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
    }
</script>
</body>
</html>

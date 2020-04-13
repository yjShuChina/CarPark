<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String jsPath = request.getContextPath() + "/js/";
    String path = request.getContextPath();
%>
<%--<!DOCTYPE html>--%>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理月缴产品</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
    <script src=<%=jsPath + "json2.js"%>></script>
    <script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<div style="padding-top: 2%;" class="demoTable">
    <div class="layui-row">
        <div class="layui-form-item" style="font-size: 30px;text-align: center;padding-top: 10px">管理月缴产品</div>
    </div>
    <div class="layui-form-item" style="text-align: right;padding-right: 10%;">
        <div class="layui-input-block">
            <input class="layui-btn layui-btn-normal" onclick="addUser(this)" type="button"
                   value="新增月缴产品" style="width: 150px;">
        </div>
    </div>
</div>
<div style="text-align: center;">
    <div class="layui-inline">
        <table id="demo" layui-filter="demotest"></table>
    </div>
</div>

<script type="text/html" id="toolbarDemo">
    <button class="layui-btn layui-btn-sm delete">删除</button>
    <button class="layui-btn layui-btn-sm update">修改</button>
</script>

<script>

    layui.use(['layer', 'table'], function () {
        var table = layui.table, $ = layui.jquery;
        var layer = layui.layer;
        var path = $("#path").val();

        //显示表格数据
        table.render({
            elem: '#demo'
            , height: 470
            , url: path + '/month/findMonthByPage' //数据接口
            , page: true //开启分页
            , id: 'demotable'
            , cols: [[ //表头
                {field: 'mcpId', title: 'ID', width: 80, sort: true, fixed: 'left'}
                , {field: 'month', title: '月份', width: 80}
                , {field: 'price', title: '价格', width: 80, sort: true}
                , {field: '', title: '操作', width: 240, toolbar: "#toolbarDemo"}
            ]]
        })
        ;
    });

    //新增月缴产品
    function addUser(node) {

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
                area: ['420px', '250px'],
                content: path + '/month/path/addMonth',
                end: function () {
                    location.reload();
                }
            });
        });
    }

    //删除月缴产品
    $('body').on('click', '.delete', function () {
        var type = $(this).text();
        console.log("type=" + type);
        var path = $("#path").val();
        var $td = $(this).parents('tr').children('td');
        var mcpId = $td.eq(0).text();//获取点击按钮相对应的id
        console.log("mcpId=" + mcpId);
        layui.use('layer', function () {
            var layer = layui.layer;

            layer.confirm('确定删除?', function (index) {

                $.ajax({
                    url: path + "/month/delMonthById",
                    async: true,
                    type: "POST",
                    data: "mcpId=" + mcpId,
                    datatype: "text",
                    success: function (msg) {

                        if (msg == "success") {
                            window.location.reload();
                        } else {
                            alert("删除失败");
                        }
                    },
                    error: function () {
                        alert("网络繁忙！")
                    }
                });

                layer.close(index);
            });

        });
    });

    //修改月缴产品
    $('body').on('click', '.update', function () {
        var type = $(this).text();
        console.log("type=" + type);
        var path = $("#path").val();
        var $td = $(this).parents('tr').children('td');
        var mcpId = $td.eq(0).text();//获取点击按钮相对应的id
        var month = $td.eq(1).text();
        var price = $td.eq(2).text();
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
                content: path + '/month/path/alterMonth',
                success: function (layero, index) {
                    var body = layer.getChildFrame("body", index);
                    body.find("#mcpId").val(mcpId);//设置弹窗的值
                    body.find("#month").val(month);
                    body.find("#price").val(price);
                },
                end: function () {
                    location.reload();
                }
            });
        });
    });

</script>

</body>
</html>

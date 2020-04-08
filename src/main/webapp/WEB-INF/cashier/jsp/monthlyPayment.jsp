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
    <title>菜单权限管理</title>
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
        <div class="layui-form-item" style="font-size: 30px;text-align: center;padding-top: 10px">菜单权限管理</div>
    </div>
    <div class="layui-form-item" style="margin-top: 200px">
        <label class="layui-form-label" style="margin-left: 38%;">角色：</label>
        <div class="layui-input-inline">
            <select id="roid" name="roid" lay-filter="roid">
                <option value="">请选择</option>
                <c:forEach items="${roleList}" begin="0" step="1" var="i">
                    <option value="${i.roid}">${i.role}</option>
                </c:forEach>
            </select>
        </div>
    </div>
</div>
<script>
    var roid = null;//角色id

    layui.use(['table', 'form'], function () {
        var form = layui.form;
        var path = $("#path").val();

        //通过领域弹出相应的领域题目
        form.on("select(roid)", function (data) {
            roid = $("#roid").val();
            console.log("当前角色id=" + roid);
            if (roid == 1) {
                layer.open({
                    type: 2,
                    title: false,
                    fix: false,
                    maxmin: false,
                    offset: '10px',
                    shadeClose: true,
                    shade: 0.3,
                    area: ['300px', '400px'],
                    // content: path + '/MenuManageServlet?method=menuShow&roid=' + roid,
                    content: path + '/admin/path/MenuManage?roid=' + roid,
                    success: function(layero, index){
                        // var body = layer.getChildFrame('body', index);
                        // body.find('#roid').val(roid);
                        var iframe = window['layui-layer-iframe' + index];
                        iframe.child(roid);
                    },
                    end: function () {
                        location.reload();
                    }
                });
            }
        });
    });
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: 92059
  Date: 2020/4/8
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%--郭子淳--%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <script src=<%=path + "/administration/js/admin/menuManager.js"%>></script>
    <title>菜单管理页面</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<fieldset class="layui-elem-field">
    <legend>菜单管理</legend>
    <div class="layui-field-box">
        <form class="layui-form" onsubmit="return false;">
            <div class="layui-form-item">
                <label class="layui-form-label">菜单名称：</label>
                <div class="layui-input-inline">
                    <input type="text" id ="menuName" name ="menuName" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
                <button class="layui-btn" lay-submit lay-filter="formDemo"><i class="layui-icon layui-icon-search"></i> 查询</button>
            </div>
        </form>
        <table class="layui-table">
            <colgroup>
                <col width="100">
                <col width="150">
                <col width="200">
                <col width="300">
            </colgroup>
            <thead style="text-align: center;">
            <tr>
                <th>菜单ID</th>
                <th>菜单名称</th>
                <th>菜单路径</th>
                <th>菜单等级</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tbody" style="text-align: center;">

            </tbody>
        </table>
        <div id="test1" style="text-align: center"></div>
    </div>
</fieldset>
</body>
</html>

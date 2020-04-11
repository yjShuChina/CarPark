<%--
  Created by IntelliJ IDEA.
  User: 92059
  Date: 2020/4/10
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--郭子淳--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <script src=<%=path + "/administration/js/admin/roleManager.js"%>></script>
    <title>角色管理</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<fieldset class="layui-elem-field">
    <legend>角色管理</legend>
    <div class="layui-field-box">
        <form class="layui-form" onsubmit="return false;">
            <div class="layui-form-item">
                <label class="layui-form-label">角色名称：</label>
                <div class="layui-input-inline">
                    <input type="text" id ="role" name ="role" placeholder="请输入角色名称" autocomplete="off" class="layui-input">
                </div>
                <button class="layui-btn" title="查询角色" lay-submit lay-filter="formDemo"><i class="layui-icon layui-icon-search"></i> 查询</button>
                <button title="新增角色" class="layui-btn"  onclick="addRole()"><i class="layui-icon layui-icon-add-circle"></i> 增加</button>
            </div>
        </form>
        <table class="layui-hide" id="demotable" lay-filter="demotable"></table>
    </div>
</fieldset>
</body>
<div hidden = "hidden" id="roleDiv"></div>
<div id="test7" class="demo-tree" hidden="hidden"></div>
<script type="text/html" id="barDemo">
    {{# if (d.role === '超级管理员' || d.roleId === 1){ }}
    <a title="查看角色权限" class="layui-btn layui-btn-xs layui-btn" lay-event="search"><i class="layui-icon layui-icon-search"></i>查看权限</a>
    {{# } else{ }}
    <a title="修改角色权限" class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>修改权限</a>
    <a title="删除角色" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除角色</a>
    {{#  } }}
</script>
</html>

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
                <button title="新增父级菜单" class="layui-btn"  onclick="addParentMenu()"><i class="layui-icon layui-icon-add-circle"></i> 增加</button>
            </div>
        </form>
        <table class="layui-hide" id="demotable" lay-filter="demotable"></table>
    </div>
</fieldset>
</body>
<div hidden="hidden" id="submenu"></div>
<div>
</div>
<script type="text/html" id="barDemo">
    <a title="删除菜单" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>
    {{# if (d.parentId === 0){ }}
    <a title="新增二级菜单" class="layui-btn layui-btn-xs layui-btn" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>新增</a>
    {{# } else{ }}
    <a title="更改父级菜单" class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>更改</a>
    {{#  } }}
</script>
</html>

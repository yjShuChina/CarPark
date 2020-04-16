<%--
  Created by IntelliJ IDEA.
  User: 92059
  Date: 2020/4/16
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">
    <%String path = request.getContextPath();%>
    <link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
    <script src=<%=path + "/layui/layui.js"%>></script>
    <script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
    <script src=<%=path + "/administration/js/admin/paramManager.js"%>></script>
    <title>系统参数管理页</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<fieldset class="layui-elem-field">
    <legend>系统参数配置</legend>
    <div class="layui-field-box">
        <form class="layui-form" onsubmit="return false;">
            <div class="layui-form-item">
                <label class="layui-form-label">参数名：</label>
                <div class="layui-input-inline">
                    <input type="text" id ="parameterName" name ="parameterName" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
                <label class="layui-form-label">参数说明：</label>
                <div class="layui-input-inline">
                    <input type="text" id ="parameterExplain" name ="parameterExplain" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
                <button class="layui-btn" title="查询参数" lay-submit lay-filter="formDemo"><i class="layui-icon layui-icon-search"></i> 查询</button>
                <button title="新增参数" class="layui-btn"  onclick="addSysParam()"><i class="layui-icon layui-icon-add-circle"></i> 增加</button>
            </div>
        </form>
        <table class="layui-hide" id="demotable" lay-filter="demotable"></table>
    </div>
</fieldset>
<div hidden = "hidden" id="parameterDiv"></div>
</body>
<script type="text/html" id="barDemo">
    <a title="删除该参数" class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>
</script>
</html>

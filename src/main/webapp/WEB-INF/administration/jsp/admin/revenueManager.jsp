<%--
  Created by IntelliJ IDEA.
  User: 92059
  Date: 2020/4/12
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script src=<%=path + "/administration/js/admin/revenueManager.js"%>></script>
    <title>收支明细页面</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<fieldset class="layui-elem-field">
    <legend>收支明细</legend>
    <div class="layui-field-box">
        <form class="layui-form" onsubmit="return false;">
            <div class="layui-form-item">
                <label class="layui-form-label">缴费渠道：</label>
                <div class="layui-input-inline">
                    <select name="incomeType" id="incomeType" lay-filter = "incomeType">
                        <option value="" selected>所有</option>
                        <option value="phone">手机端</option>
                        <option value="auto">自助缴费机</option>
                        <option value="manual">人工收费</option>
                    </select>
                </div>
                <label class="layui-form-label">收入/支出：</label>
                <div class="layui-input-inline">
                    <select name="revenue" id="revenue" lay-filter = "revenue">
                        <option value="" selected>所有</option>
                        <option value="1">收入</option>
                        <option value="2">支出</option>
                    </select>
                </div>
                <label class="layui-form-label">月缴产品：</label>
                <div class="layui-input-inline">
                    <select name="month" id="month" >
                        <option value="">无</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">时间：</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" id="test1" placeholder="时间范围">
                </div>
                <button class="layui-btn" lay-submit lay-filter="formDemo"><i class="layui-icon layui-icon-search"></i> 查询</button>
                <button title="新增收支明细" class="layui-btn"  onclick="addRevenue()"><i class="layui-icon layui-icon-add-circle"></i> 增加</button>
            </div>
        </form>
        <table class="layui-hide" id="demotable" lay-filter="demotable"></table>
    </div>
</fieldset>
</body>
<div hidden="hidden" id="revenueDiv"></div>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>更改</a>
</script>
<script type="text/html" id="tpl1">
    {{# if (d.month === 0){ }}
    <span>临时用户</span>
    {{# } else{ }}
    <span>{{ d.month }}月</span>
    {{#  } }}
</script>
<script type="text/html" id="tpl2">
    {{# if (d.revenue === 1){ }}
    <span>收入</span>
    {{# } else{ }}
    <span>支出</span>
    {{#  } }}
</script>
<script type="text/html" id="tpl3">
    {{# if (d.incomeType === "phone"){ }}
    <span>手机端</span>
    {{# } else if(d.incomeType === "auto"){ }}
    <span>自助端</span>
    {{# } else{ }}
    <span>人工收费</span>
    {{#  } }}
</script>
</html>

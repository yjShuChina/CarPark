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
	<title>Title</title>
	<%
		String path = request.getContextPath();
	%>
	<link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
	<script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
	<script src=<%=path + "/js/json2.js"%>></script>

</head>
<style>
	/*body {*/
	/*	background-image: linear-gradient(#8b8a8c, #8B8A8C);*/
	/*	background-size: 100% 100%;*/
	/*	background-attachment: fixed;*/
	/*}*/
</style>
<body>
<input type="hidden" id="path" value="<%=path%>">
<form class="layui-form" onsubmit="return false;" > <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
	<div style="padding-top: 10%;padding-left: 50%;">
		<div class="layui-form-item">
			<label class="layui-form-label">帐号：</label>
			<div class="layui-input-inline">
				<input type="text" name="aacc" placeholder="请输入帐号" required lay-verify="required" autocomplete="off"
					   class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">密码：</label>
			<div class="layui-input-inline">
				<input type="password" name="apass" required lay-verify="required" placeholder="请输入密码"
					   autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"></label>
			<div class="layui-input-inline">
				<img title="点击更换" id="img" src="${pageContext.request.contextPath}/admin/CheckCodeServlet"
					 onclick="changeCode(this)">
			</div>
			<div ></div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">验证码：</label>
			<div class="layui-input-inline">
				<input type="text" name="captcha" placeholder="请输入验证码" required lay-verify="required" autocomplete="off"
					   class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="login">登录</button>
				<button type="button" class="layui-btn layui-btn-primary" onclick="register()">注册</button>
			</div>
		</div>
	</div>

</form>
<script src=<%=path + "/layui/layui.js"%>></script>
<script src=<%=path + "/back/js/index.js"%>></script>
<form class="layui-form" style="display: none" action="" id="register">
	<div class="layui-form-item"><h1></h1></div>
	<div class="layui-form-item">
		<label class="layui-form-label">姓名</label>
		<div class="layui-input-inline">
			<input type="text" name="con_name" id="con_name" required lay-verify="required" placeholder="请输入姓名"
				   autocomplete="off"
				   class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">帐号</label>
		<div class="layui-input-inline">
			<input type="text" name="con_acc" id="con_acc" required lay-verify="required" placeholder="请输入6~16位的账号"
				   autocomplete="off"
				   class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">密码</label>
		<div class="layui-input-inline">
			<input type="password" name="con_password" id="con_password" required lay-verify="required"
				   placeholder="请输入6~16位的密码" autocomplete="off"
				   class="layui-input">
		</div>

	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">确认密码</label>
		<div class="layui-input-inline">
			<input type="password" name="con_pwd" id="con_pwd" required lay-verify="required" placeholder="请再次输入密码"
				   autocomplete="off"
				   class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">性别</label>
		<div class="layui-input-block">
			<input type="radio" name="sex" value="男" title="男" checked>
			<input type="radio" name="sex" value="女" title="女">
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">出生年月</label>
			<div class="layui-input-inline">
				<input type="text" class="layui-input" id="test3" placeholder="yyyy-MM">
			</div>
		</div>
	</div>
	<div class="layui-form-item" id="area-picker">
		<label class="layui-form-label">地址</label>
		<div class="layui-input-inline" style="width:100px">
			<select name="province" class="province-selector" lay-filter="province-1" id="province">
				<option value="">请选择省</option>
			</select>
		</div>
		<div class="layui-input-inline" style="width:100px">
			<select name="city" class="city-selector" lay-filter="city-1" id="city">
				<option value="">请选择市</option>
			</select>
		</div>
		<div class="layui-input-inline" style="width:100px">
			<select name="county" class="county-selector" lay-filter="county-1" id="county">
				<option value="">请选择县/区</option>
			</select>

		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label"></label>
		<div class="layui-input-inline" style="width:300px">
			<input type="text" name="addr" id="addr" required lay-verify="required" placeholder="请输入具体地址+街道+门牌号"
				   autocomplete="off"
				   class="layui-input">
		</div>
	</div>
</form>
<button type="button" onclick="demo()">测试</button>
<script>
	layui.use('laydate', function () {
		var laydate = layui.laydate;
		//年月选择器
		laydate.render({
			elem: '#test3'
			, type: 'month'
			, done: function (value, date, endDate) {
				age = value;
			}
		});
	});
</script>
<script>
	//配置插件目录
	layui.config({
		base: '${pageContext.request.contextPath}/layui/'
		, version: '1.0'
	});
	//一般直接写在一个js文件中
	layui.use(['layer', 'form', 'layarea'], function () {
		var layer = layui.layer
				, form = layui.form
				, layarea = layui.layarea;

		layarea.render({
			elem: '#area-picker',
			change: function (res) {
				//选择结果
				console.log(res);
			}
		});
	});
</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String jsPath = request.getContextPath() + "/js/";
%>
<html>
<head>
	<meta charset="UTF-8">
	<title>新增月缴信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
	<script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
	<script src=<%=jsPath + "json2.js"%>></script>
	<script src=<%=path + "/layui/layui.js"%>></script>
	<script src=<%=path + "/page/js/AddUserMsg.js"%>></script>
</head>
<body>
<form class="layui-form" action="">
	<div class="layui-form-item" style="text-align: center;font-size: 30px;padding-top: 10px;">
		<label>新增月缴信息</label>
	</div>
	<input type="hidden" id="path" value="<%=path%>">
	<div class="layui-form-item">
		<label class="layui-form-label">用户名：</label>
		<div class="layui-input-inline">
			<input type="text" id="user_name" name="user_name" required lay-verify="user_name" placeholder="请输入用户名"
			       autocomplete="off"
			       class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">车牌号：</label>
		<div class="layui-input-inline">
			<input type="text" id="car_number" name="car_number" required lay-verify="car_number" placeholder="请输入手机号"
				   autocomplete="off"
				   class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">手机号：</label>
		<div class="layui-input-inline">
			<input type="text" id="user_tel" name="user_tel" required lay-verify="user_tel" placeholder="请输入手机号"
				   autocomplete="off"
				   class="layui-input">
		</div>
	</div>
	<div class="layui-form-item" style="display: inline-block;width: 24%;">
		<label class="layui-form-label">生效时间：</label>
		<div class="layui-input-inline">
			<input type="text" class="layui-input" id="month_vip_begin" name="${month_vip_begin}" placeholder="请选择生效日期">
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" lay-submit lay-filter="userForm">立即提交</button>
		</div>
	</div>
</form>
<script>
	layui.use(['laydate', 'layer', 'form'], function () {
		var form = layui.form;
		var laydate = layui.laydate;
		var layer = layui.layer;

		laydate.render({
			elem: '#beginDate' //指定元素
			, theme: '#009688'
			, showBottom: false
			, format: 'yyyy-MM-dd'
		});

		form.verify({
			user_name: [
				/^[\u4E00-\u9FA5A-Za-z0-9_]{2,18}$/
				, '正确用户名为2到18位中文、下划线、字母和数字组成'
			],
			car_number: [
				/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{5}[A-Z0-9挂学警港澳]{1}$/
				, '请输入正确的车牌号'
			],
			user_tel: [
				/^1[345789]\d{9}$/
				, '手机号码1[3-9]xxxxxxxxx'
			]
		});
		//监听提交
		form.on('submit(userForm)', function (data) {
			layer.msg(JSON.stringify(data.field));

			var user_name = $("#user_name").val();
			var car_number = $("#car_number").val();
			var user_tel = $("#user_tel").val();
			var month_vip_begin = $("#month_vip_begin").val();
			var path = $("#path").val();
			var tbUser = {
				"user_name": user_name,
				"car_number": car_number,
				"user_tel": user_tel,
				"month_vip_begin": month_vip_begin
			};
			tbUser = JSON.stringify(tbUser);
			console.log("用户弹窗信息 = " + tbUser);

			$.ajax({
				url: path + "/charge/addMonthlyPayment",
				async: true,
				type: "POST",
				data: "tbUser=" + tbUser,
				datatype: "text",
				success: function (msg) {

					if (msg == "success") {
						alert("新增成功");
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭弹出的子页面窗口
						window.location.reload();
					} else {
						alert("新增失败");
					}
				},
				error: function () {
					alert("网络繁忙！")
				}
			});

			return false;
		});
	});
</script>
</body>
</html>
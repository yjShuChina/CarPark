<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String jsPath = request.getContextPath() + "/js/";
%>
<html>
<head>
	<meta charset="UTF-8">
	<title>自助新增月缴信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
	<script src=<%=jsPath + "jquery-3.4.1.js"%>></script>
	<script src=<%=jsPath + "json2.js"%>></script>
	<script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body>
<form class="layui-form" action="">
	<div class="layui-form-item" style="text-align: center;font-size: 30px;padding-top: 10px;">
		<label>自助新增月缴信息</label>
	</div>
	<input type="hidden" id="path" value="<%=path%>">
	<div class="layui-form-item">
		<label class="layui-form-label">用户名：</label>
		<div class="layui-input-inline">
			<input type="text" id="userName" name="userName" required lay-verify="userName" placeholder="请输入用户名"
			       autocomplete="off"
			       class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">车牌号：</label>
		<div class="layui-input-inline">
			<input type="text" id="carNumber" name="carNumber" value="${carNumber}" required lay-verify="carNumber" placeholder="请输入车牌号"
				   autocomplete="off"
				   class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">手机号：</label>
		<div class="layui-input-inline">
			<input type="text" id="userTel" name="userTel" required lay-verify="userTel" placeholder="请输入手机号"
				   autocomplete="off"
				   class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">月缴产品：</label>
		<div class="layui-input-inline">
			<select id="mcpId" name="mcpId" lay-verify="required">
				<option value="">请选择</option>
				<c:forEach items="${monthChargeParameterList}" begin="0" step="1" var="i">
					<option value="${i.mcpId}">${i.month}个月</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">生效时间：</label>
		<div class="layui-input-inline">
			<input type="text" class="layui-input" id="monthVipBegin" name="${monthVipBegin}" lay-verify="required" placeholder="请选择生效日期">
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
		</div>
	</div>
</form>
<script>
	layui.use(['laydate', 'layer', 'form'], function () {
		var form = layui.form;
		var laydate = layui.laydate;
		var layer = layui.layer;

		laydate.render({
			elem: '#monthVipBegin' //指定元素
			, theme: '#009688'
			, showBottom: false
			, format: 'yyyy-MM-dd'
		});

		form.verify({
			userName: [
				/^[\u4E00-\u9FA5A-Za-z0-9_]{2,18}$/
				, '正确用户名为2到18位中文、下划线、字母和数字组成'
			],
			carNumber: [
				/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$/
				, '请输入正确的车牌号'
			],
			userTel: [
				/^1[345789]\d{9}$/
				, '手机号码1[3-9]xxxxxxxxx'
			]
		});
		//监听提交
		form.on('submit(formDemo)', function (data) {
			layer.msg(JSON.stringify(data.field));

			var userName = $("#userName").val();
			var carNumber = $("#carNumber").val();
			var userTel = $("#userTel").val();
			var monthVipBegin = $("#monthVipBegin").val();
			var mcpId = $("#mcpId").val();
			var path = $("#path").val();
			var tbUser = {
				"userName": userName,
				"carNumber": carNumber,
				"userTel": userTel,
				"monthVipBegin": monthVipBegin
			};
			tbUser = JSON.stringify(tbUser);

			$.ajax({
				url: path + "/charge/addMonthlyPayment",
				async: true,
				type: "POST",
				data: "tbUser=" + tbUser + "&mcpId=" + mcpId,
				datatype: "text",
				success: function (msg) {

					if (msg == "success") {
						alert('新增成功！');
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭弹出的子页面窗口
						window.location.reload();
					} else {
						alert('新增失败！');
					}
				},
				error: function () {
					layer.alert('网络繁忙！', {icon: 7});
				}
			});
			return false;
		});
	});
</script>
</body>
</html>
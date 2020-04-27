<%--
  Created by IntelliJ IDEA.
  User: apple6356555
  Date: 2020/4/16
  Time: 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<meta charset="utf-8">
<title>用户管理——管理员详情</title>
<meta name="renderer" content="webkit">
<%String path = request.getContextPath();%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
<script src=<%=path + "/layui/layui.js"%>></script>
<script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
<script src=<%=path + "/js/json2.js"%>></script>
<body>
<form class="layui-form" action="">
	<div class="layui-form-item">
		<label class="layui-form-label">id</label>
		<div class="layui-input-inline">
			<input type="text" name="uid" readonly = "readonly" class="layui-input" value="${sessionScope.updateAdmin.adminId}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">账号</label>
		<div class="layui-input-inline">
			<input type="text" name="adminAccountUpdate" readonly = "readonly" placeholder="请输入账号" autocomplete="off" class="layui-input" value="${sessionScope.updateAdmin.adminAccount}">
		</div>
	</div>
	<!--************这里是上传图片的代码***************-->
	<!--************这里添加的隐藏的输入框，用来传递images的参数***************-->
	<input type="hidden" name="images" readonly = "readonly" id="image" class="layui-input" value="${sessionScope.updateAdmin.adminHeadImg}">
	<div class="layui-form-item">
		<label class="layui-form-label">头像</label>
		<div class="layui-input-inline">
			<div class="layui-upload-list">
				<img class="layui-upload-img headImage" style="height: 160px;width: 250px" src="${pageContext.request.contextPath}${sessionScope.updateAdmin.adminHeadImg}" id="demo1">
				<p id="demoText"></p>
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">姓名</label>
		<div class="layui-input-inline">
			<input type="text" name="adminNameUpdate" readonly = "readonly" placeholder="请输入姓名" autocomplete="off" class="layui-input" value="${sessionScope.updateAdmin.adminName}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">性别</label>
		<div class="layui-input-inline">
			<input type="text" name="adminSexUpdate" readonly = "readonly" placeholder="请输入性别" autocomplete="off" class="layui-input" value="${sessionScope.updateAdmin.adminSex}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">手机号</label>
		<div class="layui-input-inline">
			<input type="tel" name="adminPhoneUpdate" readonly = "readonly" placeholder="请输入手机号" autocomplete="off" class="layui-input" value="${sessionScope.updateAdmin.adminPhone}">
<%--			<input type="text" name="cashierPhoneUpdate" required lay-verify="phones" placeholder="请输入手机号" autocomplete="off" class="layui-input" value="${sessionScope.tbAdmin.adminPhone}">--%>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">居住地址</label>
		<div class="layui-input-inline">
			<input type="text" name="adminAddressUpdate" readonly = "readonly" placeholder="请输入居住地址" autocomplete="off" class="layui-input" value="${sessionScope.updateAdmin.adminAddress}">
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" id="close">关闭</button>
		</div>
	</div>
</form>
</body>
<script>
	layui.use(['form',"upload"], function(){
		var $=layui.jquery;
//关闭
		$("#close").click(function () {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		});
	});
</script>
</html>

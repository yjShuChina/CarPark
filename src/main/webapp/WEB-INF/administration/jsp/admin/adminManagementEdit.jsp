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
<title>用户管理——编辑</title>
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
			<input type="text" name="uid" readonly = "readonly" class="layui-input" value="${sessionScope.tbCashier.cashierId}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">账号</label>
		<div class="layui-input-inline">
			<input type="text" name="cashierAccountUpdate" required lay-verify="required" placeholder="请输入账号" autocomplete="off" class="layui-input" value="${sessionScope.tbCashier.cashierAccount}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">密码</label>
		<div class="layui-input-inline">
			<input type="text" name="cashierPwdUpdate" required lay-verify="pwd" placeholder="请输入密码" autocomplete="off" class="layui-input" id="passUpdate" value="${sessionScope.tbCashier.cashierPwd}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">姓名</label>
		<div class="layui-input-inline">
			<input type="text" name="cashierNameUpdate" required lay-verify="name" placeholder="请输入姓名" autocomplete="off" class="layui-input" value="${sessionScope.tbCashier.cashierName}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">手机号</label>
		<div class="layui-input-inline">
			<input type="text" name="cashierPhoneUpdate" required lay-verify="phones" placeholder="请输入手机号" autocomplete="off" class="layui-input" value="${sessionScope.tbCashier.cashierPhone}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">居住地址</label>
		<div class="layui-input-inline">
			<input type="text" name="cashierAddressUpdate" required lay-verify="required" placeholder="请输入居住地址" autocomplete="off" class="layui-input" value="${sessionScope.tbCashier.cashierAddress}">
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" lay-submit lay-filter="update">修改</button>
		</div>
	</div>
</form>
</body>
<script>
	var path = $("#path").val();
	layui.use(['form'], function(){
		var form = layui.form
			, $=layui.jquery;
		//编辑
		form.on('submit(update)', function(data){
			$.ajax({
				url:"${pageContext.request.contextPath}/admin/toUpdateCashier",
				type:'post',
				data: data.field,
				success:function(data){
					if (data==='修改成功') {
						layer.msg(data);
					}
					else {
						layer.msg(data);
					}
					layer.closeAll('iframe');
					setTimeout('window.location.reload()', 3000);
				}
			});
			return false;//阻止表单跳转
		});

	});
</script>
</html>

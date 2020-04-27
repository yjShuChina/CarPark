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
<title>用户管理——管理员编辑</title>
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
			<input type="text" name="adminAccountUpdate" required lay-verify="required" placeholder="请输入账号" autocomplete="off" class="layui-input" value="${sessionScope.updateAdmin.adminAccount}">
		</div>
	</div>
	<!--************这里是上传图片的代码***************-->
	<!--************这里添加的隐藏的输入框，用来传递images的参数***************-->
	<input type="hidden" name="images" required lay-verify="required" id="image" class="layui-input" value="${sessionScope.updateAdmin.adminHeadImg}">
	<div class="layui-form-item">
		<label class="layui-form-label">修改头像</label>
		<div class="layui-input-inline uploadHeadImage">
			<div class="layui-upload-drag" id="headImg">
				<i class="layui-icon"></i>
				<p>点击上传图片，或将图片拖拽到此处</p>
			</div>
		</div>
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
			<input type="text" name="adminNameUpdate" required lay-verify="name" placeholder="请输入姓名" autocomplete="off" class="layui-input" value="${sessionScope.updateAdmin.adminName}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">手机号</label>
		<div class="layui-input-inline">
			<input type="tel" name="adminPhoneUpdate" lay-verify="required|phone" placeholder="请输入手机号" autocomplete="off" class="layui-input" value="${sessionScope.updateAdmin.adminPhone}">
<%--			<input type="text" name="cashierPhoneUpdate" required lay-verify="phones" placeholder="请输入手机号" autocomplete="off" class="layui-input" value="${sessionScope.tbAdmin.adminPhone}">--%>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">居住地址</label>
		<div class="layui-input-inline">
			<input type="text" name="adminAddressUpdate" required lay-verify="required" placeholder="请输入居住地址" autocomplete="off" class="layui-input" value="${sessionScope.updateAdmin.adminAddress}">
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
	layui.use(['form',"upload"], function(){
		var form = layui.form
			,upload = layui.upload
			, $=layui.jquery;
		//编辑
		form.on('submit(update)', function(data){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			$.ajax({
				url:"${pageContext.request.contextPath}/admin/toUpdateAdmin",
				type:'post',
				data: data.field,
				success:function(data){
					if (data==='修改成功') {
						// layer.msg(data);
						parent.layer.msg(data, { offset: '300px' });
						window.location.reload();
					}
					else {
						parent.layer.msg(data, { offset: '300px' });
					}
					parent.layer.close(index);
					parent.layui.table.reload('one',{page: {curr: 1}});
					window.location.reload();
					// setTimeout('window.location.reload()', 3000);
				}
			});
			return false;//阻止表单跳转
		});
		//上传头像
		var uploadInst = upload.render({
			elem: '#headImg'
			, url: '${pageContext.request.contextPath}/admin/uploadHeadImgCashier'
			, size: 500
			, before: function (obj) {
				//预读本地文件示例，不支持ie8
				obj.preview(function (index, file, result) {
					$('#demo1').attr('src', result); //图片链接（base64）
				});
			}
			, done: function (res) {
				//如果上传失败
				if (res.code > 0) {
					return layer.msg('上传失败');
				}
				//上传成功
				//打印后台传回的地址: 把地址放入一个隐藏的input中, 和表单一起提交到后台, 此处略..
				/*   console.log(res.data.src);*/
				var demoText = $('#demoText');
				demoText.html('<span style="color: #8f8f8f;">上传成功!!!</span>');
				var fileupload = $("#image");
				fileupload.attr("value",res.data.src);
				console.log(fileupload.attr("value"));
			}
			, error: function () {
				//演示失败状态，并实现重传
				var demoText = $('#demoText');
				demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
				demoText.find('.demo-reload').on('click', function () {
					uploadInst.upload();
				});
			}
		});

	});
</script>
</html>

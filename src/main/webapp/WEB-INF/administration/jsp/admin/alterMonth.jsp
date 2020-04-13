<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String jsPath = request.getContextPath() + "/js/";
%>
<html>
<head>
	<meta charset="UTF-8">
	<title>月缴产品信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
	<script type="text/javascript" src=<%=jsPath + "jquery-3.4.1.js"%>></script>
	<script type="text/javascript" src=<%=jsPath + "json2.js"%>></script>
	<script src=<%=path + "/layui/layui.js"%>></script>
</head>
<body>
<form class="layui-form" action="">
	<div class="layui-form-item" style="text-align: center;font-size: 30px;padding-top: 10px;">
		<label>月缴产品信息</label>
	</div>
	<input type="hidden" id="path" value="<%=path%>">
	<div class="layui-form-item">
		<label class="layui-form-label">月缴ID：</label>
		<div class="layui-input-inline">
			<input type="text" class="layui-input" id="mcpId" value="${mcpId}" disabled>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">月缴月份：</label>
		<div class="layui-input-inline">
			<input type="text" class="layui-input" id="month" value="${month}">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">月缴金额：</label>
		<div class="layui-input-inline">
			<input type="text" class="layui-input" id="price" name="${price}">
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" lay-submit lay-filter="formDemo" style="text-align: center">立即提交</button>
		</div>
	</div>
</form>
<script>
	layui.use('form', function () {
		var form = layui.form;
		var path = $("#path").val();
		//监听提交
		form.on('submit(formDemo)', function (data) {
			// layer.msg(JSON.stringify(data.field));

			var mcpId = $("#mcpId").val();
			var month = $("#month").val();
			var price = $("#price").val();
			var tbMcp = {
				"mcpId": mcpId,
				"month": month,
				"price": price
			};
			tbMcp = JSON.stringify(tbMcp);
			console.log("弹窗信息 = " + tbMcp);

			$.ajax({
				url: path + "/month/alterMonth",
				async: true,
				type: "POST",
				data: "tbMcp=" + tbMcp,
				datatype: "text",
				success: function (msg) {

					if (msg == "success") {
						alert("修改成功");
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭弹出的子页面窗口
						window.location.reload();
					} else {
						alert("修改失败");
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
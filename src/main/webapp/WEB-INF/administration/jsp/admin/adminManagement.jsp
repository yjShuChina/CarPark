<%--
  Created by IntelliJ IDEA.
  User: apple6356555
  Date: 2020/4/1
  Time: 0:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="utf-8">
	<title>用户管理——收费员</title>
	<meta name="renderer" content="webkit">
	<%String path = request.getContextPath();%>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href=<%=path+"/layui/css/layui.css"%>>
	<script src=<%=path + "/layui/layui.js"%>></script>
	<script src=<%=path + "/js/jquery-3.4.1.js"%>></script>
	<script src=<%=path + "/js/json2.js"%>></script>
</head>
<style>
	body{
		width: 1150px;
		margin-left: auto;
		margin-right: auto;
		margin-top: 150px;
	}
</style>
<body>

<form class="layui-form" lay-filter="component-form-group" id="search_submits" onsubmit="return false">
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">收费员姓名</label>
			<div class="layui-input-inline">
				<input type="text" name="password" id="uid" required lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">新增时间</label>
				<div class="layui-input-block">
					<input name="beginTimeFrom" type="text" autocomplete="off" id="startTime" class="layui-input laydate">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<div class="layui-input-block">
					<input name="beginTimeTo" type="text" autocomplete="off" id="endTime" class="layui-input laydate">
				</div>
			</div>
			<div class="layui-inline">
				<button class="layui-btn" data-type="reload" id="search">搜索</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
			<div class="layui-inline">
				<button class="layui-btn" id="res">新增</button>
			</div>
		</div>
	</div>
</form>
<table class="layui-hide" id="test" lay-filter="test"></table>
<div style="display: none;" id="gb">
	<form class="layui-form" action="">
		<div class="layui-form-item">
			<label class="layui-form-label">账号</label>
			<div class="layui-input-inline">
				<input type="text" name="cashierAccount" required lay-verify="required" placeholder="请输入账号" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">密码</label>
			<div class="layui-input-inline">
				<input type="password" name="cashierPwd" required lay-verify="pwd" placeholder="请输入密码" autocomplete="off" class="layui-input" id="pass1">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">确认密码</label>
			<div class="layui-input-inline">
				<input type="password" name="cashierPwd2" required lay-verify="pwd" placeholder="请再次输入密码" autocomplete="off" class="layui-input" id="pass2">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">姓名</label>
			<div class="layui-input-inline">
				<input type="text" name="cashierName" required lay-verify="name" placeholder="请输入姓名" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">性别</label>
			<div class="layui-input-block">
				<input type="radio" name="cashierSex" value="男" title="男" checked>
				<input type="radio" name="cashierSex" value="女" title="女">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机号</label>
			<div class="layui-input-inline">
				<input type="text" name="cashierPhone" required lay-verify="phones" placeholder="请输入手机号" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">居住地址</label>
			<div class="layui-input-inline">
				<input type="text" name="cashierAddress" required lay-verify="required" placeholder="请输入居住地址" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="formDemo2">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
</div>
<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">修改</a>
	{{#  if(d.cashierState == "1"){ }}
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="forbidden">禁用</a>
	{{#  } }}
	{{#  if(d.cashierState == "0"){ }}
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="open">启用</a>
	{{#  } }}
	<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="reset">重置密码</a>
	<a class="layui-btn layui-btn layui-btn-xs" lay-event="resign">离职</a>
</script>
<script>
	var path = $("#path").val();
	layui.use(['table', 'form','laydate'], function(){
		var table =layui.table
			,form = layui.form
			,laydate = layui.laydate
			, $=layui.jquery;
		table.render({
			elem:'#test',
			height:321,
			url: "${pageContext.request.contextPath}/admin/adminManagement",
			type:'get',
			dataType:"json",
			id:'one',
			page:true,
			cellMinWidth: 80,
			cols: [[
				{field:'cashierId', title: 'ID' , align: 'center'},
				{field:'cashierTime', title: '新增时间' , align: 'center'},
				{field:'cashierAccount', title: '收费员账号' , align: 'center'},
				{field:'cashierName', title: '收费员姓名' , align: 'center'},
				{field:'cashierSex', title: '性别' , align: 'center'},
				{field:'cashierPhone', title: '手机号' , align: 'center'},
				{field:'cashierAddress', title: '居住地址' , align: 'center'},
				{field:'cashierState', title: '状态',align: 'center'},
				{fixed: 'right', title:'操作', toolbar: '#barDemo', width:250,align: 'center'}
			]],
			done: function(res, curr, count){
				//如果是异步请求数据方式，res即为你接口返回的信息。
				//如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
				$("[data-field='cashierState']").children().each(function(){
					if($(this).text()=='1'){
						$(this).text("启用")
					}else if($(this).text()=='0'){
						$(this).text("禁用")
					}
					else if($(this).text()=='2'){
						$(this).text("离职")
					}
				});
				// pageCurr=curr;
			}
		});
		//禁用启用
		table.on('tool(test)', function(obj){
			var data = obj.data;
			if(obj.event === 'forbidden'){
				layer.confirm('确定要禁用?', {icon: 2, title:'提示'}, function(index) {
				var table =layui.table, $=layui.jquery;
				var uid = $("#uid").val();
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				var stateId = obj.data.cashierId;
				var oId = null;
				var resignId = null;
				table.reload('one',{
					method:'post',
					where:{
						'uid':uid,
						'startTime':startTime,
						'endTime':endTime,
						'oId':oId,
						'stateId':stateId,
						'resignId':resignId
					},
					page:{
						page:1
					}
				});
					layer.msg('禁用成功！');
					layer.close(index);
				});
				}
			else if(obj.event === 'open'){
				layer.confirm('确定要启用?', {icon: 1, title:'提示'}, function(index) {
				var table =layui.table, $=layui.jquery;
				var uid = $("#uid").val();
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				var oId = obj.data.cashierId;
				var stateId = null;
				var resignId = null;
				table.reload('one',{
					method:'post',
					where:{
						'uid':uid,
						'startTime':startTime,
						'endTime':endTime,
						'oId':oId,
						'stateId':stateId,
						'resignId':resignId
					},
					page:{
						page:1
					}
				});
					layer.msg('启用成功！');
					layer.close(index);
				});
			}
			else if(obj.event === 'resign'){
				layer.confirm('确定改为离职状态?', {icon: 1, title:'提示'}, function(index) {
					var table =layui.table, $=layui.jquery;
					var uid = $("#uid").val();
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					var oId = null;
					var stateId = null;
					var resignId = obj.data.cashierId;
					table.reload('one',{
						method:'post',
						where:{
							'uid':uid,
							'startTime':startTime,
							'endTime':endTime,
							'oId':oId,
							'stateId':stateId,
							'resignId':resignId
						},
						page:{
							page:1
						}
					});
					layer.msg('离职成功！');
					layer.close(index);
				});
			}
			else if(obj.event === 'reset'){
				layer.confirm('确定将密码重置为123456?', {icon: 1, title:'提示'}, function(index) {
					var table =layui.table, $=layui.jquery;
					var uid = $("#uid").val();
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					var oId = null;
					var stateId = null;
					var resignId = null;
					var resetId = obj.data.cashierId;
					table.reload('one',{
						method:'post',
						where:{
							'uid':uid,
							'startTime':startTime,
							'endTime':endTime,
							'oId':oId,
							'stateId':stateId,
							'resignId':resignId,
							'resetId':resetId
						},
						page:{
							page:1
						}
					});
					layer.msg('重置密码成功！');
					layer.close(index);
				});
			}
			else if(obj.event === 'open'){
				layer.confirm('确定要启用?', {icon: 1, title:'提示'}, function(index) {
					var table =layui.table, $=layui.jquery;
					var uid = $("#uid").val();
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					var oId = obj.data.cashierId;
					var stateId = null;
					var resignId = null;
					table.reload('one',{
						method:'post',
						where:{
							'uid':uid,
							'startTime':startTime,
							'endTime':endTime,
							'oId':oId,
							'stateId':stateId,
							'resignId':resignId
						},
						page:{
							page:1
						}
					});
					layer.msg('启用成功！');
					layer.close(index);
				});
			}
			else if(obj.event === 'edit'){
				var uid = obj.data.cashierId;
				$.ajax({
					url:"${pageContext.request.contextPath}/admin/updateCashier",
					type:'post',
					data: {'uid':uid},
					dataType:"json",
					success:function(data){
						// setTimeout('window.location.reload()', 1);
					}
				});
				layer.confirm('确定要编辑?', {icon: 1, title:'提示'}, function(index) {
					layer.open({
						type: 2,
						area: ['700px', '450px'],
						fixed: false, //不固定
						maxmin: true,
						content: '${pageContext.request.contextPath}/url/admin/adminManagementEdit'
					});
					layer.close(index);
				});
				form.render();
			}
		});
		//搜索
		$("#search").click(function () {
			var table =layui.table, $=layui.jquery;
			var uid = $("#uid").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			table.reload('one',{
				method:'post',
				where:{
					'uid':uid,
					'startTime':startTime,
					'endTime':endTime
				},
				page:{
					page:1
				}
			});
		});
		//日期
		var nowTime=new Date();
		var startTime=laydate.render({
			elem:'#startTime',
			type:'date',
			btns: ['confirm'],
			max:'nowTime',//默认最大值为当前日期
			done:function(value,date){
				console.log(value); //得到日期生成的值，如：2017-08-18
				//    	console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
				endTime.config.min={
					year:date.year,
					month:date.month-1,//关键
					date:date.date,
					hours:date.hours,
					minutes:date.minutes,
					seconds:date.seconds
				};
			}
		});
		var endTime=laydate.render({
			elem:'#endTime',
			type:'date',
			btns: ['confirm'],
			max:'nowTime',
			done:function(value,date){
				console.log(value); //得到日期生成的值，如：2017-08-18
//    	    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
				startTime.config.max={
					year:date.year,
					month:date.month-1,//关键
					date:date.date,
					hours:date.hours,
					minutes:date.minutes,
					seconds:date.seconds
				}

			}
		});
		//新增收费员
		$("#res").click(function() {
			var index=layer.open({
				type: 1,
				title: "新增收费员",
				area: ['600px', '550px'],
				offset: '20px',
				content: $("#gb"),
				cancel: function() {
					// 你点击右上角 X 取消后要做什么
					setTimeout('window.location.reload()', 1);
				},
				success: function() {
					form.on('submit(formDemo)', function(data) {
					});
				}
			});
			form.render();
		});
		//新增收费员
		form.on('submit(formDemo2)', function(data){
			$.ajax({
				url:"${pageContext.request.contextPath}/admin/addCashier",
				type:'post',
				data: data.field,
				success:function(data){
					if (data==='新增成功') {
						layer.msg(data);
					}
					else {
						layer.msg(data);
					}
					layer.closeAll('page');
					setTimeout('window.location.reload()', 3000);
				},error:function (err) {
					console.log(err);
				}
			});
			return false;//阻止表单跳转
		});
		//验证姓名
		form.verify({
			//数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
			name: [
				/^[\u4E00-\u9FA5]{2,4}$/
				,'请输入正确格式姓名'
			]
		});
	});
</script>
</body>

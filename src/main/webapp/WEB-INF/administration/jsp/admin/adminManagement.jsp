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
	<title>用户管理</title>
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
			<label class="layui-form-label">用户名</label>
			<div class="layui-input-inline">
				<input type="text" name="password" id="uid" required lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">注册时间</label>
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
		</div>
	</div>
</form>
<table class="layui-hide" id="test" lay-filter="test"></table>

<script type="text/html" id="barDemo">
	{{#  if(d.state == "1"){ }}
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="forbidden">禁用</a>
	{{#  } }}
	{{#  if(d.state == "0"){ }}
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="open">启用</a>
	{{#  } }}
</script>

<script>
	layui.use(['table', 'laydate'], function(){
		var table =layui.table
			,laydate = layui.laydate
			, $=layui.jquery;
		table.render({
			elem:'#test',
			height:321,
			url: "../admin/adminManagement",
			type:'get',
			dataType:"json",
			id:'one',
			page:true,
			cellMinWidth: 80,
			cols: [[
				{field:'cashier_name', title: '用户名' , align: 'center'},
				{field:'cashier_state', title: '用户状态',align: 'center'},
				{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150,align: 'center'}
			]]
		});


		//监听行工具事件
		table.on('tool(test)', function(obj){
			var data = obj.data;
			if(obj.event === 'forbidden'){
				console.log(obj.data.username);
				var table =layui.table, $=layui.jquery;
				var uid = $("#uid").val();
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				var stateId = obj.data.username;
				var oId = null;
				table.reload('one',{
					method:'post',
					where:{
						'uid':uid,
						'startTime':startTime,
						'endTime':endTime,
						'oId':oId,
						'stateId':stateId
					},
					page:{
						page:1
					}
				});
			} else if(obj.event === 'open'){
				console.log(obj.data.username);
				var table =layui.table, $=layui.jquery;
				var uid = $("#uid").val();
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				var oId = obj.data.username;
				var stateId = null;
				table.reload('one',{
					method:'post',
					where:{
						'uid':uid,
						'startTime':startTime,
						'endTime':endTime,
						'oId':oId,
						'stateId':stateId
					},
					page:{
						page:1
					}
				});
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
			// return false;
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

	});
</script>

</body>

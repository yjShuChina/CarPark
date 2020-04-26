<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/9
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>layui表格</title>
	<%String path = request.getContextPath();%>
	<script src=<%=path + "/layui/layui.js"%>></script>
	<link rel="stylesheet" href=<%=path + "/layui/css/layui.css"%>>
	<script rel="script" src=<%=path + "/js/jquery-3.4.1.js"%>></script>
	<script rel="script" src=<%=path + "/js/json2.js"%>></script>
	<style>


	</style>
</head>
<body class="layui-layout-body">

		<div class="layui-form-item">
			<label class="layui-form-label">账号查询</label>
			<div class="layui-input-inline">
				<input type="text" name="name" id="name" required  lay-verify="required" placeholder="请输入名字" autocomplete="off" class="layui-input">
			</div>
				<label class="layui-form-label" style="margin-left: 20px">操作查询</label>
				<div class="layui-input-inline">
					<select id="type" name="type" lay-verify="required" class="layui-input">
						<option value="">无</option>
						<option value="insert">写入</option>
						<option value="update">修改</option>
						<option value="delete">删除</option>
						<option value="login">登录</option>
					</select>
				</div>
			<button class="layui-btn" data-type="reload" lay-filter="formDemo" style="margin-left: 60px">查找</button>
		</div>
	<table id="demo" lay-filter="demotest"></table>
</div>


<script>

	layui.use('table', function(){
		var table = layui.table,
		$=layui.jquery;

		//第一个实例
		table.render({
			elem: '#demo'
			,height: 320
			,url: "${pageContext.request.contextPath}/admin/table" //数据接口
			,page: true //开启分页
			,dataType : "json"
			,limit:5
			,id:'demotable'
			,cols: [[ //表头
				{field: 'logId', title: '记录ID', width:80},
				{field: 'uname', title: '用户名', width:180},
				{field: 'operation', title: '操作事项', width:180},
				{field: 'operationType', title: '操作类型', width: 205},
				{field: 'operationTime', title: '操作时间', width:220}
			]]
		});
		$('.layui-btn').on('click',function () {
			var type=$(this).data('type');//指改按钮的类型
			if(type=='reload'){
				table.reload('demotable',{//上面设置的表格的特殊标识
					page:{
						curr:1//设置当前页为1
					}
					,where:{
						key:$("#name").val()//获取文本框的值
						,type:$("#type option:selected").val()//这里还可以写文本框的值
					}
				});
			}

		});
	});
	//将表单转为js对象数据
	function serializeObject($, array){
		var obj=new Object();
		$.each(array, function(index,param){
			if(!(param.name in obj)){
				obj[param.name]=param.value;
			}
		});
		return obj;
	}
	function createrFormat(o){
		return o;
	}

</script>

</body>
</html>

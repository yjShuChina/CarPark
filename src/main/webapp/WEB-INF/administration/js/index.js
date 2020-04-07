var age;

layui.use('form', function(){
	var form = layui.form;
	//监听提交
	form.on('submit(login)', function(data){
		var acc = JSON.stringify(data.field);
		var path = $("#path").val();

		$.ajax({
				url: path + "/admin/login",
				async: "true",
				type: "Post",
				data: "acc=" + acc,
				dataType: "text",
				success: function (res) {
					if ("success" == res) {
						layer.msg('登录成功', {icon: 6});
						window.location = path + "/admin/BackMain";
					} else if ("captchaerror" == res) {
						layer.msg('验证码错误', {icon: 5});
					}else {
						layer.msg('帐号或密码错误', {icon: 5});
					}
				},
				error: function () {
					layer.msg('网络正忙', {icon: 6});
				}
			}
		);
		return false;
	});
});
function changeCode(msg) {//验证码
	var path = $("#path").val();
	msg.src = path + "/admin/CheckCodeServlet?num="+Math.random()+1;
}
function register() {
	var layer = layui.layer;
	var path = $("#path").val();
	layer.open({
		type: 1,
		title: '注册',
		content: $("#register"),
		offset: '100px',
		anim: 1,
		isOutAnim: true,
		resize: false,
		area: ['700px', '550px'],
		closeBtn: 2,
		shade: 0.6,
		move: false,
		btn: ['提交', '取消'],
		btn1: function (index, layero) {
			var add_name_mind = /^([\u4E00-\u9FA5]{2,20}|[a-zA-Z\s]{1,20})$/;
			var add_name = $("#con_name").val();
			if (!add_name_mind.test(add_name)) {
				layer.msg('姓名为2-4位中文或字母组成', {icon: 6});
				setTimeout("$('#add_name').focus()", 1);
				return false;
			}
			var add_acc_mind = /^[A-Za-z0-9]{6,16}$/;
			var add_acc = $("#con_acc").val();
			if (!add_acc_mind.test(add_acc)) {
				layer.msg('帐号长度不在6~16位或含非法字符', {icon: 6});
				setTimeout("$('#add_acc').focus()", 1);
				return false;
			}

			var add_password_mind = /^[a-zA-Z0-9]{6,16}$/;
			var add_password = $("#con_password").val();
			if (!add_password_mind.test(add_password)) {
				layer.msg('密码长度不在6~16位或含非法字符', {icon: 6});
				setTimeout("$('#add_password').focus()", 1);
				return false;
			}

			var add_pwd = $("#con_pwd").val();
			if (add_password !== add_pwd) {
				layer.msg('两次密码不一致请重新输入', {icon: 6});
				setTimeout("$('#add_pwd').focus()", 1);
				return false;
			}

			var sex = $("input[name='sex']:checked").val();//性别

			if (age == null) {
				layer.msg('未选择出生年月，请选择', {icon: 6});
				setTimeout("$('#test3').focus()", 1);
				return false;
			}

			var addr = $("#addr").val();
			if (addr == null || addr == undefined || addr == '') {
				layer.msg('请输入您的详细地址', {icon: 6});
				setTimeout("$('#addr').focus()", 1);
				return false;
			}
			var province = $("#province").val();
			var city = $("#city").val();
			var county = $("#county").val();
			var address = province + city + county + addr;

			var useradd = {
				"aacc": add_acc,
				"apass": add_pwd,
				"sex": sex,
				"age":age,
				"address": address,
				"aname": add_name
			};
			useradd = JSON.stringify(useradd);
			layer.msg('注册中');
			$.ajax({
				url: path + "/admin/register",
				async: "true",
				type: "Post",
				data: "useradd=" + useradd,
				dataType: "text",
				success: function (res) {
					if (res == "addSucceed") {
						layer.msg('注册成功返回登录', {
							icon: 1,
							time: 1500 //2秒关闭（如果不配置，默认是3秒）
						}, function () {
							layer.close(index);
						});

					} else if (res == "accRepeat") {
						layer.msg('帐号重复，请修改后提交');
					} else {
						layer.msg('注册失败请重试');
					}
				},
				error: function () {
					layer.msg('网络正忙', {icon: 6});
				}
			})
		},
		cancel: function (index, layero) {
			layer.confirm('是否取消注册', {icon: 3, title: '提示'}, function (indexs) {
				//do something
				layer.close(indexs);
				layer.close(index);
			});
			return false;
		},
		btn2: function (index, layero) {
			layer.confirm('是否取消注册', {icon: 3, title: '提示'}, function (indexs) {
				//do something
				layer.close(indexs);
				layer.close(index);
			});
			return false;
		}
	});

}//主界面注册窗口方法

function demo() {
	$.ajax({
			url: "/demo",
			type: "Post",
			success: function (res) {
				var qq = JSON.stringify(res);
				alert(qq);
			},
			error: function () {
				layer.msg('网络正忙', {icon: 6});
			}
		}
	);
}
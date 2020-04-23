<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String jsPath=request.getContextPath()+"/js/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>人脸添加</title>
	<link rel="stylesheet" type="text/css" href=<%=path+"/css/normalize.css"%> />
	<link rel="stylesheet" type="text/css" href=<%=path+"/css/default.css"%> />
	<link rel="stylesheet" type="text/css" href=<%=path+"/css/facelogincss.css"%> />
</head>
<body>
<div id="logo">
	<h1 class="hogo"><i> 人脸添加</i></h1>
</div>
<section class="stark-login" >
	<form action="" method="">
		<br>
		<video id="video" width="300" height="230" autoplay style=" border: 5px solid #00fffc;"></video>
		<div id="fade-box"  >
	<input type="button" onclick="addFace()" value="确认添加"
	       class="submit_btn" />
			<canvas id="canvas" width="400" height="300" hidden></canvas>
		</div>
	</form>
	<div class="hexagons">
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<br>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<br>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<br>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<br>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
		<span>&#x2B22;</span>
	</div>
</section>
<div id="circle1">
	<div id="inner-cirlce1">
		<h2> </h2>
	</div>
</div>
<ul>
	<li></li>
	<li></li>
	<li></li>
	<li></li>
	<li></li>
</ul>
<script type="text/javascript" src=<%=jsPath+"jquery-3.4.1.js" %>></script>
<script src=<%=path + "/layuiadmin/layui/layui.js"%>></script>

<script type="text/javascript">
	//var 是定义变量
	var video = document.getElementById("video"); //获取video标签
	var context = canvas.getContext("2d");
	var con  ={
		audio:false,
		video:{
			width:1980,
			height:1024,
		}
	};

	//导航 获取用户媒体对象
	navigator.mediaDevices.getUserMedia(con)
		.then(function(stream){
			video.srcObject = stream;
			video.onloadmetadate = function(e){
				video.play();
			}
		});


	function addFace(){
		//把流媒体数据画到convas画布上去
		context.drawImage(video,0,0,400,300);
		var chargeFace = getBase64();

		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/chargeFace/addChargeFace",
			data:{"chargeFace":chargeFace},
			success:function(data){
				if(data.msg==="1"){
					alert("添加成功")
					window.location.href="${pageContext.request.contextPath}/charge/path/chargeLnterface";
				}else if(data.msg==="2"){
					alert("添加失败");

				}

			}
		});

	}


	function getBase64() {
		var imgSrc = document.getElementById("canvas").toDataURL(
			"image/png");
		return imgSrc.split("base64,")[1];

	};
</script>

</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/8 0008
  Time: 下午 8:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>车闸</title>
	<%String path = request.getContextPath();%>
	<script rel="script" src=<%=path + "/js/jquery-3.4.1.js"%>></script>
</head>
<body style="margin: 0">
<div style="height: 100%;width: 20%;background:#c9c9c9;float: left">

</div>
<div style="height: 100%;width: 80%;float:right;background:#eaeaea">
	<c:choose>
		<c:when test="${sessionScope.img== null}">
			<div id="img" style="margin: 4% auto;width: 60%;height: 40%;background: #c9c9c9">
				<form action="${pageContext.request.contextPath}/gate/findusermsg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
					选择图片<input type="file" name="fileaot" id="fileaot" onchange="preImg('fileaot','imgPre')" >
					<input type="submit" value="确定" >
					<img src="${sessionScope.img}" id="imgPre" style="width: 100%;height: 100%;">
				</form>
			</div>
		</c:when>
		<c:otherwise>
			<div style="margin: 4% auto;width: 60%;height: 40%;">
			<img src="${sessionScope.img}" style="width: 100%;height: 100%;">
			</div>
		</c:otherwise>
	</c:choose>

	<div id="msg" style="margin: 0 auto;width: 70%;height: 40%;background: #c9c9c9;border:1px solid cornflowerblue">
		<div style="width: 20%;height: 100%;background: chocolate;float: left">
			<div style="margin: 0 auto;width: 30%;height: 100%">
				<h1>用</h1>
				<h1>户</h1>
				<h1>信</h1>
				<h1>息</h1>
			</div>
		</div>
			<a style="font-size: 30px">用户：${ sessionScope.username}</a><br><br>
			<a>车牌号：${ sessionScope.carnumber} </a><br><br>
			<a>车辆情况：
				<c:choose>
					<c:when test="${sessionScope.username== null}">
						临时车辆
					</c:when>
					<c:otherwise>
						月卡车辆
					</c:otherwise>
				</c:choose>
			</a><br><br>
			<a>入库时间：${ sessionScope.time}</a>
</div>
</div>


</body>
<script>
	/**
	 * 将本地图片 显示到浏览器上
	 */
	function preImg(sourceId, targetId) {
		var url = getFileUrl(sourceId);
		var imgPre = document.getElementById(targetId);
		imgPre.src = url;
	}
	//从 file 域获取 本地图片 url
	function getFileUrl(sourceId) {
		var url='';
		var img=document.getElementsByName('img').value;
		if (navigator.userAgent.indexOf("MSIE") >= 1) { // IE
			url = document.getElementById(sourceId).value;
		} else if (navigator.userAgent.indexOf("Firefox") > 0) { // Firefox
			url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
		} else if (navigator.userAgent.indexOf("Chrome") > 0) { // Chrome
			url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
		}
		return url;

	}
</script>
</html>

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
	<title>自助查询(二号)</title>
	<%String path = request.getContextPath();%>
	<script rel="script" src=<%=path + "/js/jquery-3.4.1.js"%>></script>
	<script src=<%=path + "/js/esmap-1.6.min.js"%> > </script >
	<script src=<%=path + "/js/json2.js"%>></script>
</head>
<style>
	li{
		content:".";
		color:white;
	}
	span{
		color:white;;
	}
</style>
<body>
<input type="hidden" id="path" value="<%=path%>">
<input type="hidden" id="x" value="null">
<input type="hidden" id="y" value="null">
<%out.write("<input type='hidden' id='Cnum' value='"+request.getSession().getAttribute("Cps")+"'>");%>
<div id="map-container" style="margin: 0 auto;width: 100%;height: 100%">
</div>

<div style="margin-top: -300px">
	<button id="btn2D" class="btn btn-default">2D</button>
	<button id="btn3D" class="btn btn-default">3D</button>
	<button onclick="prom()">查询车辆信息</button>
	<div class="parking fix" id="parking"><span id="carid">车辆情况：</span></div>
	<div class="codition fix">
		<ul>
			<li ><span class="codition-first">车牌号:</span>
				<%out.write("<span>"+request.getSession().getAttribute("Cnum")+"</span>");%>
			</li>
			<li ><span class="codition-second">停车类型:</span>
				<%out.write("<span>"+request.getSession().getAttribute("Identity")+"</span>");%>
			</li>
			<li ><span class="codition-first">停车位:</span>
				<%out.write("<span>"+request.getSession().getAttribute("Cps")+"</span>");%>
			</li>
			<li ><span class="codition-first">入库时间:</span>
				<%out.write("<span>"+request.getSession().getAttribute("Ctime")+"</span>");%>
			</li>
		</ul>
	</div>
</div>

</body>
<script>
	var path = $("#path").val();

	var map;
	//定义全局map变量
	var esmapID = 'machine2';
	//定义选用的地图id
	var styleid = 1004;
	//选用的style的id
	var floorControl;
	map = new esmap.ESMap({
		container: $("#map-container")[0], // 渲染dom
		// 	container:document.getElementById('map-container'),
		token:"rujiu2333",
		mapDataSrc: path+"/esmap", //地图数据位置
		mapThemeSrc:path+"/esmap/theme/", //主题数据位置
		focusAlphaMode: true, // 对不可见图层启用透明设置 默认为true
		focusAnimateMode: true, // 开启聚焦层切换的动画显示
		focusAlpha: 0.4, // 对不聚焦图层启用透明设置，当focusAlphaMode = true时有效
		focusFloor: 1,
		// visibleFloors: "all",
		themeID: styleid //自定义样式主题ID
	});



	map.openMapById(esmapID); //打开地图
	map.showCompass = true; //显示指南针

	$('#btn2D').on('click', function () {
		map.viewMode = esmap.ESViewMode.MODE_2D;//2维模式
	});

	//3维模式
	$('#btn3D').on('click', function () {
		map.viewMode = esmap.ESViewMode.MODE_3D;; //3维模式
	});


	var queryFloors = "all"
	var queryParams = {
		nodeType: esmap.ESNodeType.MODEL_LIFT, //nodeType指定为房间类型
		typeID : 40003
	};

	function prom() {
		var carnum = prompt("请输入您的车牌号", ""); //将输入的内容赋给变量 name ，

		//这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值
		if (carnum)//如果返回的有内容
		{
			$.ajax({
					url: path + "/gate/findcarmsg",
					async: "true",
					type: "Post",
					data: {"carnum":carnum},
					dataType: "text",
					success: function (res) {
						if(res!='no'){
							$("#x").val(res.split(",")[1]);
							$("#y").val(res.split(",")[0]);
							myrefresh()
						}else {
							alert("输入车牌有误，请重试")
						}
					}

				}
			);
		}

	}

	map.on("loadComplete", function () {
		var x=$("#x").val().toString();
		console.log(x)
		//初始化导航对象
		if(x!='null'){
			var y=$("#y").val();
			var navi = new esmap.ESNavigation({
				map: map,
				locationMarkerUrl: 'image/pointer.png',   //定位标注图片地址
				locationMarkerSize: 150,    //定位标注尺寸大小
				speed: 5,   //模拟导航速度
				followAngle: true,  //地图是否跟随旋转
				followPosition: true,  //地图视角是否跟随位置
				followGap:3,      //导航视角跟随间隔(单位:/s)
				tiltAngle: 30,   //模拟导航时的倾斜角
				audioPlay:false,  //是否开启语音播报
				// scaleLevel:0,   //模拟导航时的放大等级
				// mode:2,         //mode=1:人行(默认),mode=2：车行
				offsetHeight: 1,    //定位标注的高度
				ladderType:1,  //跨层方案选择。1:距离最近(默认),2:电梯 3.楼梯 4.扶梯
				lineStyle: {   //路径规划线样式配置
					color: '#33cc61',
					//设置线为导航线样式
					lineType: esmap.ESLineType.ESARROW,
					// lineType: esmap.ESLineType.FULL,
					lineWidth: 6,		// 设置导航线的宽度
					offsetHeight: 0.5,	// 设置导航线的高度
					smooth: true,		// 设置导航线的转角线平滑效果
					seeThrough: false,	// 设置导航线的穿透楼层地板总是显示的效果
					noAnimate: false	// 设置导航线的动画效果
					//设置边线的颜色
					// godEdgeColor: '#920000'
					//设置箭头颜色
					// godArrowColor: "#ff0000"
				},
			});

			navi.setStartPoint({
				x: 12958010.812,
				y: 4858233.242,
				fnum: 1,
				height: 1,
				url: 'image/start.png',
				size: 64
			});
			//确定终点
			navi.setEndPoint({
				x:x ,
				y:y,
				fnum: 1,
				height: 1,
				url: 'image/end.png',
				size: 64
			});
			console.log("!!!")
			navi.drawNaviLine();

			var Cnum=$("#Cnum").val().toString();
			console.log(Cnum)
			map.changeModelColor({name:Cnum,color:'#676766'});

			setTimeout('remove()',5000); //指定10秒刷新一次
		}
	});


	//刷新
	function myrefresh() {
		window.location.reload();
	}
	function remove() {
		$("#x").val("null");
		$("#y").val("null");
	}
	function parking() {
		var parking=new Array();
		$.ajax({
				url: path + "/gate/findParking",
				async: "true",
				type: "Post",
				data: "",
				dataType: "json",
				success: function (res) {
					parking =res;
					console.log(res);
					map.changeModelColor({name:"'"+parking+"'",color:'#Fb7999'})
				},
				error: function () {
					console.log("!!!")
				}
			}
		);
	}


</script>
</html>

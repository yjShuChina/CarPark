<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/8 0008
  Time: 下午 8:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>自助查询（一号）</title>
    <%String path = request.getContextPath();%>
    <script rel="script" src=<%=path + "/js/jquery-3.4.1.js"%>></script>
    <script src=<%=path + "/js/esmap-1.6.min.js"%>></script>
    <script src=<%=path + "/js/json2.js"%>></script>
</head>
<style>
    li {
        content: ".";
        color: white;
    }

    span {
        color: white;
    }

    button {
        font-size: 15px;
    }
</style>
<body>
<input type="hidden" id="path" value="<%=path%>">
<input type="hidden" id="area" value='<%=session.getAttribute("Area")%>'>
<%out.write("<input type='hidden' id='Cnum' value='" + request.getSession().getAttribute("Cps") + "'>");%>
<div id="map-container" style="margin: 0 auto;width: 100%;height: 100%">
</div>

<div style="margin-top: -300px;float: top">
    <button id="btn2D" class="btn btn-default">2D</button>
    <button id="btn3D" class="btn btn-default">3D</button>
    <br>
    <button onclick="prom()">查询车辆信息</button>
    <button onclick="pay()">自助缴费办理</button>
    <div class="parking fix" id="parking"><span id="carid">车辆情况：</span></div>
    <div class="codition fix">
        <ul>
            <li><span class="codition-first">车牌号:</span>
                <%out.write("<span>" + request.getSession().getAttribute("Cnum") + "</span>");%>
            </li>
            <li><span class="codition-second">停车类型:</span>
                <%out.write("<span>" + request.getSession().getAttribute("Identity") + "</span>");%>
            </li>
            <li><span class="codition-first">停车位:</span>
                <%out.write("<span>" + request.getSession().getAttribute("Cps") + "</span>");%>
            </li>
            <li><span class="codition-first">入库时间:</span>
                <%out.write("<span>" + request.getSession().getAttribute("Ctime") + "</span>");%>
            </li>
        </ul>
    </div>
</div>
<br>
<br>
<button style="float:end;width: 120px;font-size:13px" onclick="pwd()">设备编号修改</button>
<br>
</body>
<script>
    area();//设备编号

    var path = $("#path").val();
    var map;
    //定义全局map变量
    var esmapID = 'machine1';
    //定义选用的地图id
    var styleid = 1004;
    //选用的style的id
    var floorControl;
    map = new esmap.ESMap({
        container: $("#map-container")[0], // 渲染dom
        // 	container:document.getElementById('map-container'),
        token: "rujiu2333",
        mapDataSrc: path + "/esmap", //地图数据位置
        mapThemeSrc: path + "/esmap/theme/", //主题数据位置
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
        map.viewMode = esmap.ESViewMode.MODE_3D;
        ; //3维模式
    });


    var queryFloors = "all"
    var queryParams = {
        nodeType: esmap.ESNodeType.MODEL_LIFT, //nodeType指定为房间类型
        typeID: 40003
    };


    map.on("loadComplete", function () {
        var x = '<%=session.getAttribute("x")%>';
        var y = '<%=session.getAttribute("y")%>';
        console.log(x)
        parking();
        //初始化导航对象
        if (x != 'null') {
            // var y=$("#y").val();
            var navi = new esmap.ESNavigation({
                map: map,
                locationMarkerUrl: 'image/pointer.png',   //定位标注图片地址
                locationMarkerSize: 150,    //定位标注尺寸大小
                speed: 5,   //模拟导航速度
                followAngle: true,  //地图是否跟随旋转
                followPosition: true,  //地图视角是否跟随位置
                followGap: 3,      //导航视角跟随间隔(单位:/s)
                tiltAngle: 30,   //模拟导航时的倾斜角
                audioPlay: false,  //是否开启语音播报
                // scaleLevel:0,   //模拟导航时的放大等级
                // mode:2,         //mode=1:人行(默认),mode=2：车行
                offsetHeight: 1,    //定位标注的高度
                ladderType: 1,  //跨层方案选择。1:距离最近(默认),2:电梯 3.楼梯 4.扶梯
                lineStyle: {   //路径规划线样式配置
                    color: '#33cc61',
                    //设置线为导航线样式
                    lineType: esmap.ESLineType.ESARROW,
                    // lineType: esmap.ESLineType.FULL,
                    lineWidth: 6,		// 设置导航线的宽度
                    offsetHeight: 0.7,	// 设置导航线的高度
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
                x: 12958009.622,
                y: 4858217.788,
                fnum: 1,
                height: 1,
                url: '',
                size: 64
            });
            //确定终点
            navi.setEndPoint({
                x: x,
                y: y,
                fnum: 1,
                height: 1,
                url: '',
                size: 64
            });

            //通过名字区别创建不同的layer
            var floorLayer = map.getFloor(1);  //获取第一层的楼层对象
            var layer = floorLayer.getOrCreateLayerByName("camera", esmap.ESLayerType.IMAGE_MARKER);
            im = new esmap.ESImageMarker({
                x: x - 0.1,
                y: y - 0.1,   //如果不添加x和y，则默认坐标在地图中心。
                url: 'https://i.loli.net/2020/04/20/FTJM9EuqoR5GHOC.png',  //图片标注的图片地址
                size: 64,   			//图片大小 或者 size:{w:32,h:64},
                spritify: true,			//跟随地图缩放变化大小，默认为true，可选参数
                height: 2,    			//距离地面高度
                showLevel: 20,  		//地图缩放等级达到多少时隐藏,可选参数
                seeThrough: true,		//是否可以穿透楼层一直显示,可选参数
                //angle:30,  	//如果设置了就是固定marker角度，与地图一起旋转。(size需要重新设置)
                id: 2017,   			//id，可自定义
                name: 'myMarker'   		//name可自定义
            });
            layer.addMarker(im);              //将imageMarker添加到图层
            floorLayer.addLayer(layer);       //将图层添加到楼层对象


            console.log("!!!")
            navi.drawNaviLine();

            var Cnum = $("#Cnum").val().toString();
            // console.log(Cnum)
            // map.changeModelColor({name:Cnum,color:'#676766'});

            setTimeout('remove();parking()', 5000); //指定10秒刷新一次
        }
    });

    function prom() {
        var carnum = prompt("请输入您的车牌号", ""); //将输入的内容赋给变量 name ，
        //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值
        if (carnum)//如果返回的有内容
        {
            $.ajax({
                    url: path + "/gate/findcarmsg",
                    async: "true",
                    type: "Post",
                    data: {"carnum": carnum},
                    dataType: "text",
                    success: function (res) {
                        if (res != 'no') {
                            myrefresh()
                        } else {
                            alert("输入车牌有误，请重试")
                        }
                    }
                }
            );
        }
    }//车牌查询
    function pwd() {
        var pwd = prompt("请输入设备密码", ""); //将输入的内容赋给变量 name ，
        //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值
        if (pwd)//如果返回的有内容
        {
            $.ajax({
                    url: path + "/gate/machinepwd",
                    async: "true",
                    type: "Post",
                    data: {"pwd": pwd},
                    dataType: "text",
                    success: function (res) {
                        if (res != 'no') {
                            Area();
                        } else {
                            alert("输入设备密码有误，请重试")
                        }
                    }
                }
            );
        }
    }//密码验证
    function Area() {
        var carnum = prompt("请输入设备号（1、2）", ""); //将输入的内容赋给变量 name ，
        //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值
        if (carnum)//如果返回的有内容
        {
            if (carnum == '1' || carnum == '2') {
                $.ajax({
                        url: path + "/gate/saveArea",
                        async: "true",
                        type: "Post",
                        data: {'area': carnum},
                        success: function (res) {
                            myrefresh();
                        },
                        error: function () {
                        }
                    }
                );
            } else {
                alert('设备号有误！')
                Area();
            }
        }
    }//设备号验证

    function pay() {
        saveArea();
        var flag = confirm("是否跳转到缴费页面");
        if (flag) {
            window.location.href = path + '/alipay/path/selfServicePayment'
        } else {

        }
    }//页面跳转
    //页面选择
    function area() {
        var area = $("#area").val();
        area = '<%=session.getAttribute("Area")%>'
        if (area == '2') {
            window.location.href = path + '/gate/cn/machine2'
        }
    }

    function saveArea() {
        var area = $("#area").val();
        $.ajax({
                url: path + "/gate/saveArea",
                async: "true",
                type: "Post",
                data: {'area': area},
                success: function (res) {
                },
                error: function () {
                }
            }
        );
    }

    //刷新
    function myrefresh() {
        window.location.reload();
    }

    function remove() {
        $("#x").val("null");
        $("#y").val("null");
    }

    function parking() {
        var parking = new Array();
        $.ajax({
                url: path + "/gate/findParking",
                async: "true",
                type: "Post",
                data: "",
                dataType: "json",
                success: function (res) {
                    parking = res;
                    console.log(res);
                    map.changeModelColor({name: parking, color: '#Fb7999'})
                },
                error: function () {
                    console.log("!!!")
                }
            }
        );
    }


</script>
</html>

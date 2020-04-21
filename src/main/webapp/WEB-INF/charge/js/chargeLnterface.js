var path = $("#path").val();
var websocket = null;
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://127.0.0.1:8080/Carpark/websocket/charge");
} else {
    alert("您的浏览器不支持websocket");
}

//消息接收
websocket.onmessage = function (event) {
    // console.log(event)
    var obj = JSON.parse(event.data);
    switch (obj.type) {
        case "departure":
            departure(obj);
            break;
        case "gate":
            gate();
            break;
    }
}

// 车辆出场
function departure(obj) {
    var timej = obj.timej;
    if (obj.timej != "未找到") {
        timej = timeDatezhuang(timej);
    }
    document.getElementById("timer").innerHTML = timej;
    document.getElementById("carnumber").innerHTML = obj.carnumber;
    document.getElementById("state").innerHTML = obj.state;
    document.getElementById("timeC").innerHTML = timeDatezhuang(obj.timeC);
    document.getElementById("timeData").innerHTML = obj.timeData;

    document.getElementById("departureUrl").src = "data:image/png;base64," + obj.departureUrl;
}


//车辆入场
function gate() {
    var path = $("#path").val();

    $.ajax({
            url: path + "/charge/gateMaxQuery",
            async: "true",
            success: function (res) {
                if (res != null) {
                    var gateCarTime = timeDatezhuang(res.carTime);
                    document.getElementById("gateCarTime").innerHTML = gateCarTime;

                    document.getElementById("gateCarNumber").innerHTML = res.carNumber;
                    document.getElementById("gateCarIdentity").innerHTML = res.carIdentity;
                    document.getElementById("imgPreGate").src = "data:image/png;base64," + res.imgUrl;
                }
            },
            error: function () {
                layer.msg('网络正忙', {icon: 6});
            }
        }
    );
    layui.use('table', function () {
        var table = layui.table;
        table.reload('changneitable', {});
    });
}


layui.use(['form', 'layer', 'table'], function () {
    var layer = layui.layer //弹层
        , table = layui.table //表格

    var path = $("#path").val();

    table.render({
        elem: '#changnei'
        , height: 260
        , url: path + '/charge/parkQuery' //数据接口
        , title: '场内车辆'
        , width: 440
        , page: true //开启分页
        , size: 'sm' //小尺寸的表格
        , cols: [[ //表头
            {field: 'pciId', title: 'ID', width: 80, hide: true}
            , {field: 'carNumber', title: '车牌号', width: 100}
            , {field: 'carIdentity', title: '车辆类型', width: 95}
            , {field: 'parkSpaceId', title: '所停车位', width: 80}
            , {
                field: 'carTime', title: '进场时间', width: 140, templet: function (d) {
                    return timeDatezhuang(d.carTime);
                }
            }
        ]]
        , id: 'changneitable'
    });

    table.render({
        elem: '#chuchang'
        , height: 260
        , url: path + '/charge/carExitQuery' //数据接口
        , title: '出场车辆'
        , width: 440
        , page: true //开启分页
        , size: 'sm' //小尺寸的表格
        , cols: [[ //表头
            {field: 'tceId', title: 'ID', width: 80, hide: true}
            , {field: 'carNumber', title: '车牌号', width: 100}
            , {field: 'carIdentity', title: '车辆类型', width: 80}
            , {field: 'price', title: '费用', width: 40}
            , {field: 'channel', title: '缴费渠道', width: 80}
            , {
                field: 'entryTime', title: '进场时间', width: 120, templet: function (d) {
                    return timeDatezhuang(d.entryTime);
                }
            }
            , {
                field: 'exitTime', title: '出场时间', width: 120, templet: function (d) {
                    return timeDatezhuang(d.exitTime);

                }
            }

        ]]
        , id: 'chuchangtable'
    });
})

//停车场车位数量信息
function chargeQuery() {
    var path = $("#path").val();
    $.ajax({
            url: path + "/charge/chargeQuery",
            async: "true",
            success: function (res) {
                document.getElementById("allps").innerHTML = res.allps;
                document.getElementById("parkspase").innerHTML = res.parkspase;
            },
            error: function () {
                layer.msg('网络正忙', {icon: 6});
            }
        }
    );
}

gate();
chargeQuery();

function timeDatezhuang(time) {
    var date = new Date(time);
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
}

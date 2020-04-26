var path;
var $;
layui.use(['layim', 'jquery'], function (layim) {
    $ = layui.$

    path = $("#path").val();
    var websocket = null;
    if ('WebSocket' in window) {
        // var str = "ws://"+path+"/websocket/charge";
        websocket = new WebSocket("ws://127.0.0.1:8080/Carpark/websocket/charge");
        // websocket = new WebSocket("ws://112.74.72.11:10086/Carpark/websocket/charge");
        // websocket = new WebSocket(str);
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
                chargeWindow(obj);
                break;
            case "gate":
                gate();
                chargeQuery();
                break;
            case "payment":
                departure(obj);
                chargeQuery();
                break;
            case "paymentExpire":
                departure(obj);
                chargeQuery();
                if (obj.money > 0) {
                    chargeWindow(obj);
                }
                break;
        }
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
                    field: 'entryTime', title: '进场时间', width: 140, templet: function (d) {
                        return timeDatezhuang(d.entryTime);
                    }
                }
                , {
                    field: 'exitTime', title: '出场时间', width: 140, templet: function (d) {
                        return timeDatezhuang(d.exitTime);
                    }
                }
            ]]
            , id: 'chuchangtable'
        });
    })

//车辆入场
    function gate() {

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


    function chargeWindow(obj) {
        var timej = obj.timej;
        if (obj.timej != "未找到") {
            timej = timeDatezhuang(timej);
        }
        //进场时间
        document.getElementById("chargeTimer").innerHTML = timej;
        //车牌号
        document.getElementById("chargeCarnumber").innerHTML = obj.carnumber;
        //车辆状态
        document.getElementById("chargeState").innerHTML = obj.state;
        //出场时间
        document.getElementById("chargeTimeC").innerHTML = timeDatezhuang(obj.timeC);
        //停车时长
        document.getElementById("chargeTimeData").innerHTML = obj.timeData;
        //金额
        document.getElementById("chargeMoney").innerHTML = obj.money + "元"
        //出场图片
        document.getElementById("chargeDepartureUrl").src = "data:image/png;base64," + obj.departureUrl;
        //进场图片
        document.getElementById("chargeGateUrl").src = "data:image/png;base64," + obj.gateUrl


        var layer = layui.layer;
        var path = $("#path").val();
        layer.open({
            type: 1,
            title: '车辆出场',
            content: $("#modify"),
            offset: '100px',
            anim: 1,
            resize: false,
            area: ['1000px', '550px'],
            closeBtn: 2,
            shade: 0.6,
            move: false,
            btn: ['确认收款'],
            btn1: function (index, layero) {
                $.ajax({
                        url: path + "/charge/confirmCollection",
                        async: "true",
                        success: function (res) {
                            if (res == "error") {
                                layer.alert("确认失败，请修正收费信息");
                            } else {
                                layer.msg("收款成功");
                                layer.close(index);

                                layui.use('table', function () {
                                    var table = layui.table;
                                    table.reload('changneitable', {});
                                });
                                layui.use('table', function () {
                                    var table = layui.table;
                                    table.reload('chuchangtable', {});
                                });
                            }
                            console.log(res);
                        },
                        error: function () {
                            layer.msg('网络正忙', {icon: 6});
                        }
                    }
                );
            },
            cancel: function (index, layero) {
                layer.close(index);
                return false;
            },
        });
    }

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
    chargeQuery();
    gate();
});

function settlement() {
    var layer = layui.layer;
    var path = $("#path").val();
    layer.open({
        type: 2,
        title: '日结账单',
        content: path + "/charge/path/settlement",
        offset: '100px',
        anim: 1,
        resize: false,
        area: ['1000px', '550px'],
        closeBtn: 2,
        shade: 0.6,
        move: false,
        cancel: function (index, layero) {
            layer.close(index);
            return false;
        },
    });
}

function monthlyPayment() {
    var layer = layui.layer;
    var path = $("#path").val();
    layer.open({
        type: 2,
        title: '月缴办理',
        content: path + "/charge/path/paymentMonth",
        offset: '100px',
        anim: 1,
        resize: false,
        area: ['90%', '500px'],
        closeBtn: 2,
        shade: 0.6,
        move: false,
        cancel: function (index, layero) {
            layer.close(index);
            return false;
        },
    });
}



function timeDatezhuang(time) {
    var date = new Date(time);
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
}




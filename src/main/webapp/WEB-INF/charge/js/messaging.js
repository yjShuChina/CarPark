layui.use(['layim', 'jquery'], function (layim) {
    var $ = layui.$
    var path = $("#path").val();
    console.log(path);
    var websocketmsg = null;
    $.ajax({
            url: path + "/msg/getChargeId",
            async: "false",
            success: function (res) {
                // websocketmsg = new WebSocket("ws://127.0.0.1:8080/Carpark/websocket/" + res);
                websocketmsg = new WebSocket("ws://112.74.72.11:10086/Carpark/websocket/" + res);
            },
            error: function () {
                layer.msg('网络正忙', {icon: 6});
            }
        }
    );



    //基础配置
    layim.config({
        //初始化接口
        init: {
            url: path + '/msg/getList'
            , data: {}
        }
        , title: 'WebIM' //自定义主面板最小化时的标题
        , initSkin: '1.jpg' //1-5 设置初始背景
        , isgroup: false //是否开启群组
        , notice: true //是否开启桌面消息提醒，默认false
        , msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
        , find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
    });



    // 监听layim建立就绪
    layim.on('ready', function (res) {
        //接受消息
        websocketmsg.onmessage = function (event) {
            console.log(JSON.parse(event.data))
            layim.getMessage(JSON.parse(event.data));
        }
    });

    //监听发送消息
    layim.on('sendMessage', function (data) {
        console.log(data)
        websocketmsg.send(JSON.stringify(data));
        //演示自动回复

    });

    // //监听自定义工具栏点击，以添加代码为例
    // layim.on('tool(code)', function (insert) {
    //     layer.prompt({
    //         title: '插入代码'
    //         , formType: 2
    //         , shade: 0
    //     }, function (text, index) {
    //         layer.close(index);
    //         insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
    //     });
    // });

    // //监听聊天窗口的切换
    // layim.on('chatChange', function (res) {
    //     var type = res.data.type;
    //     console.log(res.data.id)
    //     if (type === 'friend') {
    //         //模拟标注好友状态
    //         //layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
    //     } else if (type === 'group') {
    //         //模拟系统消息
    //         layim.getMessage({
    //             system: true
    //             , id: res.data.id
    //             , type: "group"
    //             , content: '模拟群员' + (Math.random() * 100 | 0) + '加入群聊'
    //         });
    //     }
    // });


});
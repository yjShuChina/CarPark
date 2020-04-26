layui.use(['layim', 'jquery'], function (layim) {
    var $ = layui.$
    var path = $("#path").val();
    console.log(path);
    var websocket = null;
    $.ajax({
            url: path + "/msg/getChargeId",
            async: "true",
            success: function (res) {
                // websocket = new WebSocket("ws://127.0.0.1:8080/Carpark/websocket/" + res);
                websocket = new WebSocket("ws://112.74.72.11:10086/Carpark/websocket/" + res);
            },
            error: function () {
                layer.msg('网络正忙', {icon: 6});
            }
        }
    );

    //演示自动回复
    var autoReplay = [
        '您好，我现在有事不在，一会再和您联系。',
        '你没发错吧？face[微笑] ',
        '洗澡中，请勿打扰，偷窥请购票，个体四十，团体八折，订票电话：一般人我不告诉他！face[哈哈] ',
        '你好，我是主人的美女秘书，有什么事就跟我说吧，等他回来我会转告他的。face[心] face[心] face[心] ',
        'face[威武] face[威武] face[威武] face[威武] ',
        '<（@￣︶￣@）>',
        '你要和我说话？你真的要和我说话？你确定自己想说吗？你一定非说不可吗？那你说吧，这是自动回复。',
        'face[黑线]  你慢慢说，别急……',
        '(*^__^*) face[嘻嘻] ，是贤心吗？'
    ];

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

    //监听在线状态的切换事件
    layim.on('online', function (data) {
        //console.log(data);
    });

    //监听签名修改
    layim.on('sign', function (value) {
        //console.log(value);
    });

    //监听自定义工具栏点击，以添加代码为例
    layim.on('tool(code)', function (insert) {
        layer.prompt({
            title: '插入代码'
            , formType: 2
            , shade: 0
        }, function (text, index) {
            layer.close(index);
            insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
        });
    });

    // 监听layim建立就绪
    layim.on('ready', function (res) {
        //接受消息
        websocket.onmessage = function (event) {
            layim.getMessage(JSON.parse(event.data));
        }

    });

    //监听发送消息
    layim.on('sendMessage', function (data) {

        websocket.send(JSON.stringify(data));
        //演示自动回复
    });


    //监听聊天窗口的切换
    layim.on('chatChange', function (res) {
        var type = res.data.type;
        console.log(res.data.id)
        if (type === 'friend') {
            //模拟标注好友状态
            //layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
        } else if (type === 'group') {
            //模拟系统消息
            layim.getMessage({
                system: true
                , id: res.data.id
                , type: "group"
                , content: '模拟群员' + (Math.random() * 100 | 0) + '加入群聊'
            });
        }
    });


});
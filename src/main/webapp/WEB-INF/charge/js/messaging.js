layui.use(['layim', 'jquery'], function (layim) {
    var $ = layui.$
    var path = $("#path").val();
    console.log(path);
    // const path = "http://"+document.location.host+":"+document.location.port;  //通用
    // console.log(path);
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

        // 查看群员接口
        , members: {
            url: path + '/msg/getMembers'
            , data: {}
        }

        , title: 'WebIM' //自定义主面板最小化时的标题
        //,right: '100px' //主面板相对浏览器右侧距离
        //,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
        , initSkin: '1.jpg' //1-5 设置初始背景
        //,skin: ['aaa.jpg'] //新增皮肤
        //,isfriend: false //是否开启好友
        ,isgroup: false //是否开启群组
        //,min: true //是否始终最小化主面板，默认false
        , notice: true //是否开启桌面消息提醒，默认false
        //,voice: false //声音提醒，默认开启，声音文件为：default.mp3

        , msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
        , find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
        // , chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可

    });

    /*
    layim.chat({
      name: '在线客服-小苍'
      ,type: 'kefu'
      ,avatar: 'http://tva3.sinaimg.cn/crop.0.0.180.180.180/7f5f6861jw1e8qgp5bmzyj2050050aa8.jpg'
      ,id: -1
    });
    layim.chat({
      name: '在线客服-心心'
      ,type: 'kefu'
      ,avatar: 'http://tva1.sinaimg.cn/crop.219.144.555.555.180/0068iARejw8esk724mra6j30rs0rstap.jpg'
      ,id: -2
    });
    layim.setChatMin();*/

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

    //监听layim建立就绪
    // layim.on('ready', function (res) {
    //
    //     setTimeout(function () {
    //         //接受消息（如果检测到该socket）
    //
    //
    //
    //     }, 3000);
    // });
    layim.getMessage({
        username: "贤心"
        , avatar: "http://tp1.sinaimg.cn/1571889140/180/40030060651/1"
        , id: "100001"
        , type: "friend"
        , content: "嗨，你好！欢迎体验LayIM。演示标记：" + new Date().getTime()
    });
    //监听发送消息
    layim.on('sendMessage', function (data) {
        console.log(data)
        var To = data.to;
        //console.log(data);

        if (To.type === 'friend') {
            layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
        }

        //演示自动回复
        setTimeout(function () {
            var obj = {};
            if (To.type === 'group') {
                obj = {
                    username: '模拟群员' + (Math.random() * 100 | 0)
                    , avatar: layui.cache.dir + 'images/face/' + (Math.random() * 72 | 0) + '.gif'
                    , id: To.id
                    , type: To.type
                    , content: autoReplay[Math.random() * 9 | 0]
                }
            } else {
                obj = {
                    username: To.name
                    , avatar: To.avatar
                    , id: To.id
                    , type: To.type
                    // , content: autoReplay[Math.random() * 9 | 0]
                    , content: data.mine.content
                }
                layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
            }
            layim.getMessage(obj);
        }, 1000);
    });

    //监听查看群员
    layim.on('members', function (data) {
        //console.log(data);
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
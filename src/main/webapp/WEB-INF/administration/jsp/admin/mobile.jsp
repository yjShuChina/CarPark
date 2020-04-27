<%--
  Created by IntelliJ IDEA.
  User: 37292
  Date: 2020/4/26
  Time: 23:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, height=device-height, user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="format-detection" content="telephone=no">
    <title>IM 移动版</title>
    <%String path = request.getContextPath();%>

    <script src=<%=path + "/js/json2.js"%>></script>
    <link rel="stylesheet" href=<%=path+"/dist/css/layui.css"%>>
    <script src=<%=path + "/dist/layui.js"%>></script>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">

<script>
    layui.use('jquery', function () {
        var $ = layui.$
        var path = $("#path").val();

        var websocket = null;
        $.ajax({
                url: path + "/msg/getAdminId",
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
        layui.config({
            version: true
        }).use('mobile', function () {
            var mobile = layui.mobile
                , layim = mobile.layim
                , layer = mobile.layer;


            $.ajax({
                    url: path + "/msg/getAdminList",
                    async: "false",
                    success: function (res) {
                        var data = JSON.parse(res);
                        // console.log(data);
                        layim.config({
                            //初始化接口
                            init: data
                            , isNewFriend: false //是否开启“新的朋友”

                        });
                    },
                    error: function () {
                        layer.msg('网络正忙', {icon: 6});
                    }
                }
            );

            // 监听layim建立就绪
            layim.on('ready', function (res) {
                //接受消息
                websocket.onmessage = function (event) {
                    console.log(JSON.parse(event.data));
                    layim.getMessage(JSON.parse(event.data));
                }

            });

            //监听发送消息
            layim.on('sendMessage', function (data) {
                console.log(data);
                websocket.send(JSON.stringify(data));
                //演示自动回复
            });


            // //监听点击“新的朋友”
            // layim.on('newFriend', function () {
            //     layim.panel({
            //         title: '新的朋友' //标题
            //         , tpl: '<div style="padding: 10px;">自定义模版，{{d.data.test}}</div>' //模版
            //         , data: { //数据
            //             test: '么么哒'
            //         }
            //     });
            // });
            //
            // //查看聊天信息
            // layim.on('detail', function (data) {
            //     //console.log(data); //获取当前会话对象
            //     layim.panel({
            //         title: data.name + ' 聊天信息' //标题
            //         ,
            //         tpl: '<div style="padding: 10px;">自定义模版，<a href="http://www.layui.com/doc/modules/layim_mobile.html#ondetail" target="_blank">参考文档</a></div>' //模版
            //         ,
            //         data: { //数据
            //             test: '么么哒'
            //         }
            //     });
            // });

            //监听点击更多列表
            // layim.on('moreList', function(obj){
            //   switch(obj.alias){
            //     case 'find':
            //       layer.msg('自定义发现动作');
            //
            //       //模拟标记“发现新动态”为已读
            //       layim.showNew('More', false);
            //       layim.showNew('find', false);
            //     break;
            //     case 'share':
            //       layim.panel({
            //         title: '邀请好友' //标题
            //         ,tpl: '<div style="padding: 10px;">自定义模版，{{d.data.test}}</div>' //模版
            //         ,data: { //数据
            //           test: '么么哒'
            //         }
            //       });
            //     break;
            //   }
            // });

            // //监听返回
            // layim.on('back', function () {
            //     //如果你只是弹出一个会话界面（不显示主面板），那么可通过监听返回，跳转到上一页面，如：history.back();
            // });
            //
            // //监听自定义工具栏点击，以添加代码为例
            // layim.on('tool(code)', function (insert, send) {
            //     insert('[pre class=layui-code]123[/pre]'); //将内容插入到编辑器
            //     send();
            // });




            // //监听查看更多记录
            // layim.on('chatlog', function (data, ul) {
            //     console.log(data);
            //     layim.panel({
            //         title: '与 ' + data.name + ' 的聊天记录' //标题
            //         , tpl: '<div style="padding: 10px;">这里是模版，{{d.data.test}}</div>' //模版
            //         , data: { //数据
            //             test: 'Hello'
            //         }
            //     });
            // });

        });

    })
</script>
</body>
</html>


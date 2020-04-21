// 郭子淳

function changePath(node) {
    window.manager.location.href =$('#path').val() + node.title;
}

//JavaScript代码区域
layui.use(['element','layer'], function(){
    var element = layui.element,layer = layui.layer;
    $(document).ready(function () {
        var path = $('#path').val();
        //获取用户信息
        $.ajax({
            url:path+'/admin/findCurrentAdmin',
            type:'post',
            success:function (msg) {
                $('#admin_name').text(msg.adminName);
            },
            error:function (msg) {
                layer.msg('网络开小差啦',{icon:5})
            }
        })
        //ajax获取菜单
        $.ajax({
            url:path+'/admin/findMenu',
            type:'post',
            success:function (msg) {
                var html = "";
                for (var i = 0;i < msg.length; i++) {
                    html += '<li class="layui-nav-item"><a class="" href="javascript:;">'+msg[i].menuName+'</a><dl class="layui-nav-child">';
                    for (var j = 0; j < msg[i].submenuList.length;j++) {
                        html += '<dd><a href="javascript:;" title="'+msg[i].submenuList[j].menuUrl+'" onclick="changePath(this)">'+msg[i].submenuList[j].menuName+'</a></dd>';
                    }
                    html += '</dl></li>';
                }
                $('#menuNode').html(html);
                layui.element.init();
            },
            error:function (msg) {
                layer.msg('网络错误',{icon:5})
            }
        })
    })

    $('#exit').on('click',function () {
        var path = $('#path').val();
        layer.confirm('您确定要退出吗',function () {
            $.ajax({
                url:path+'/admin/exit',
                type:'post',
                success:function (msg) {
                    if(msg === 'success'){
                        window.location.href = path+"/url/admin/adminLogin";
                    }
                },
                error:function (msg) {
                    layer.msg("网络开小差啦！",{icon:5})
                }
            })
        })
    })
});
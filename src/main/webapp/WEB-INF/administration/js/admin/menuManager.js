layui.use(['form','laypage','layer','tree','util'], function(){
    var form = layui.form,
        laypage = layui.laypage,
        layer = layui.layer,
        tree = layui.tree,
        util = layui.util;

    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });

    $(document).ready(function () {
        var path = $('#path').val();
        $.ajax({
            url:path + '/admin/findMenuById',
            data:{'parentId':0,'page':1,'limit':5},
            type:'post',
            success:function (msg) {
                // var html = '';
                // for (var i = 0;i < msg.data.length;i++)
                // {
                //     html += '<tr>';
                //     html += '<td>'+ msg.data[i].menuId+'</td>';
                //     html += '<td>'+ msg.data[i].menuName+'</td>';
                //     html += '<td>'+ msg.data[i].menuUrl+'</td>';
                //     html += '</tr>';
                // }
                // $('#tbody').html(html);
                form.render();
                laypage.render({
                    elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
                    ,count:msg.count//数据总数，从服务端得到
                    ,limit:5
                    ,jump:function (obj,first) {
                        $.ajax({
                            url: path + '/admin/findMenuById',
                            type: 'post',
                            data:{'parentId':0,'page':obj.curr,'limit':obj.limit},
                            success:function (msg) {
                                var html = '';
                                for (var i = 0;i < msg.data.length;i++)
                                {
                                    html += '<tr>';
                                    html += '<td>'+ msg.data[i].menuId+'</td>';
                                    html += '<td>'+ msg.data[i].menuName+'</td>';
                                    html += '<td>'+ msg.data[i].menuUrl+'</td>';
                                    if(msg.data[i].parentId === 0){}
                                    {
                                        html += '<td>一级菜单</td>';
                                    }
                                    html += '</tr>';
                                }
                                $('#tbody').html(html);
                                form.render();
                            },
                            error:function (msg) {
                                layer.msg('网络开小差啦',{icon:5});
                            }
                        })
                    }
                });
            },
            error:function (msg) {
                layer.msg('网络开小差啦',{icon:5});
            }
        })
    })
});
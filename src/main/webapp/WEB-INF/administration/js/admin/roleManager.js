layui.use(['form','laypage','layer','tree','util','table'], function() {
    var form = layui.form,
        laypage = layui.laypage,
        layer = layui.layer,
        tree = layui.tree,
        table = layui.table,
        util = layui.util;

    //监听提交
    form.on('submit(formDemo)', function(data){
        table.reload('demotable', {
            url:$('#path').val()+'/admin/findRoleByPage'
            ,where:{
                role:$('#role').val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        })
    });

    //新增角色
    window.addRole = function() {
        var html = '<form class="layui-form" onsubmit="return false;">' +
            '    <div class="layui-form-item">' +
            '        <label class="layui-form-label">角色名称：</label>' +
            '        <div class="layui-input-inline">' +
            '            <input type="text" id ="roleName" name ="roleName" placeholder="请输入角色名称" autocomplete="off" class="layui-input">' +
            '        </div>' +
            '    </div>' +
            '    <div class="layui-form-item">' +
            '       <label class="layui-form-label">立即应用所有菜单：</label>' +
            '       <div class="layui-input-inline">' +
            '       <input type="radio" name="use" value="yes" title="是">' +
            '       <input type="radio" name="use" value="no" title="否" checked>'+
            '       </div>' +
            '    </div>'+
            '</form>';
        $('#roleDiv').empty();
        $('#roleDiv').html(html);
        form.render();
        layer.open({
            type: 1,
            title:'新增角色',
            skin: 'layui-layer-rim', //加上边框
            area: ['400px', '240px'], //宽高
            content: $('#roleDiv'),
            btn:['确定','取消'],
            btn1:function(index, layero) {
                layer.confirm('您确定新增吗',function () {
                    $.ajax({
                        url:$('#path').val() + '/admin/addRole',
                        type:'post',
                        data:{'role':$('#roleName').val(),'use':$('input[name="use"]:checked').val()},
                        beforeSend:function(){
                            if($('#roleName').val().length< 2 || $('#roleName').val().length >10){
                                layer.alert("角色名("+$('#roleName').val()+")不在2~10位之间");
                                return false;
                            }
                        },
                        success:function (msg) {
                            if(msg === 'success'){
                                table.reload('demotable', {
                                    url:$('#path').val()+'/admin/findRoleByPage'
                                    ,where:{
                                        role:$('#role').val()
                                    }
                                    ,page: {
                                        curr: 1 //重新从当前页开始
                                    }
                                })
                                layer.msg(msg);
                                layer.close(index);
                            }else {
                                layer.msg(msg);
                            }
                        },
                        error:function (msg) {
                            layer.msg('网络开小差啦',{icon:5});
                            layer.close(index);
                        }
                    })
                })
            },
            btn2:function (index, layero) {
                layer.close(index);
            }
        });
    }

    //数据表格渲染
    table.render({
        elem: '#demotable'
        ,url:$('#path').val()+'/admin/findRoleByPage'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,limit:10
        ,id: 'demotable'
        ,page: true
        ,cols: [
            [
                {field:'roleId', title: '角色ID', sort: true}
                ,{field:'role', title: '角色名称',edit: 'text'}
                ,{field:'right', title: '操作',toolbar: '#barDemo'}
            ]
        ]
    });

    //监听单元格编辑
    table.on('edit(demotable)', function(obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field //得到字段
            , old = $(this).prev().text();//旧值
        layer.confirm('您确定要修改吗',{
            btn:['确定','取消']
            ,btn1:function () {
                $.ajax({
                    url:$('#path').val() + '/admin/updateRole',
                    type:'post',
                    data:{'roleId':data.roleId,'role':value},
                    beforeSend:function(){
                        if(value === '' || value === null){
                            obj.update({
                                [field]:old
                            });
                            layer.msg("不能为空");
                            return false;
                        }
                    },
                    success:function (msg) {
                        if(msg === 'success'){
                            layer.msg('[角色ID:'+ data.roleId +']' + field + '字段更改为：'+ value);
                        }else {
                            layer.msg(msg);
                        }
                    },
                    error:function (msg) {
                        obj.update({
                            [field]:old
                        });
                        layer.msg('网络开小差',{icon:5});
                    }
                })}
            ,btn2:function (index) {
                obj.update({
                    [field]:old
                });
                layer.close(index);
            }
        })
    });

    table.on('tool(demotable)', function(obj) {
        var data = obj.data;
        //console.log(obj)
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        var roleId = tr.find("td").eq(0).text();

        if(obj.event === 'search'){
            $.ajax({
                url:$('#path').val()+'/admin/findRoleMenu',
                type:'post',
                data:{'roleId':roleId},
                success:function (msg) {
                    tree.render({
                        elem: '#test7'
                        ,data: msg
                        ,showCheckbox: false
                        ,id:'treeNode'
                    });

                    layer.open({
                        title:'权限管理'
                        ,type:1
                        ,area:['360px','400px']
                        ,shadeClose:true
                        ,content:$('#test7')
                        ,fixed: false
                        ,resize:false
                        ,btn:['确定']
                        ,btn1:function (index, layero) {
                            layer.close(index);
                        }
                    })
                },
                error:function (msg) {
                    layer.msg('网络开小差啦',{icon:5});
                }
            })
        }else if(obj.event === 'edit'){
            $.ajax({
                url:$('#path').val()+'/admin/findRoleMenu',
                type:'post',
                data:{'roleId':roleId},
                success:function (msg) {
                    tree.render({
                        elem: '#test7'
                        ,data: msg
                        ,showCheckbox: true
                        ,id:'treeNode'
                    });

                    layer.open({
                        title:'权限管理'
                        ,type:1
                        ,area:['360px','400px']
                        ,shadeClose:true
                        ,content:$('#test7')
                        ,fixed: false
                        ,resize:false
                        ,btn:['确定','取消']
                        ,btn1:function (index, layero) {
                            layer.confirm('您确定要修改吗',function () {
                                $.ajax({
                                    url:$('#path').val()+'/admin/updateRoleMenu',
                                    type:'post',
                                    data:{'treeDate':JSON.stringify(tree.getChecked('treeNode')),'roleId':roleId},
                                    success:function (msg) {
                                        layer.alert(msg);
                                        layer.close(index);
                                    },
                                    error:function (msg) {
                                        layer.msg('网络开小差啦！',{icon:5})
                                    }
                                })
                            })
                        }
                        ,btn2:function (index, layero) {
                            layer.close(index);
                        }
                    })
                },
                error:function (msg) {
                    layer.msg('网络开小差啦',{icon:5});
                }
            })
        }else if(obj.event === 'delete'){
            layer.confirm('确定要删除该角色吗?', function(index){
                $.ajax({
                    url:$('#path').val()+'/admin/deleteRole',
                    type:'post',
                    data:{'roleId':roleId},
                    success:function (msg) {
                        layer.msg(msg);
                        //重载表格
                        table.reload('demotable', {
                            url:$('#path').val()+'/admin/findRoleByPage'
                            ,where:{
                                role:$('#role').val()
                            }
                            ,page: {
                                curr: 1 //重新从当前页开始
                            }
                        })
                    },
                    error:function () {
                        layer.msg('网络开小差啦',{icon:5});
                    }
                })
            });
        }
    });
});
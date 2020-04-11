layui.use(['form','laypage','layer','tree','util','table'], function(){
    var form = layui.form,
        laypage = layui.laypage,
        layer = layui.layer,
        tree = layui.tree,
        table = layui.table,
        util = layui.util;

    //监听提交
    form.on('submit(formDemo)', function(data){
        table.reload('demotable', {
            url:$('#path').val()+'/admin/findMenuById'
            ,where:{
                menuName:$('#menuName').val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        })
    });
    //数据表格渲染
    table.render({
        elem: '#demotable'
        ,url:$('#path').val()+'/admin/findMenuById'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,limit:10
        ,id: 'demotable'
        ,page: true
        ,cols: [
            [
            {field:'menuId', title: '菜单ID', sort: true}
            ,{field:'menuName', title: '菜单名称',edit: 'text'}
            ,{field:'menuUrl', title: '菜单路径',edit: 'text'}
            ,{field:'parentId', title: '上一级菜单（0为一级菜单）',sort:true}
            ,{field:'right', title: '操作',toolbar: '#barDemo'}
            ]
        ]
    });



    //监听单元格编辑
    table.on('edit(demotable)', function(obj){
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field //得到字段
            ,old=$(this).prev().text();//旧值
        layer.confirm('您确定要修改吗，涉及路径请谨慎修改',{
            btn:['确定','取消']
            ,btn1:function () {
                $.ajax({
                    url:$('#path').val() + '/admin/updateMenu',
                    type:'post',
                    data:{'menuId':data.menuId,[field]:value},
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
                            layer.msg('[菜单ID: '+ data.menuId +'] ' + field + ' 字段更改为：'+ value);
                        }else {
                            layer.msg('菜单路径或菜单名重复');
                            obj.update({
                                [field]:old
                            });
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

    //新增父级菜单
    window.addParentMenu = function() {
        layer.prompt({title: '请输入一级菜单名称',maxlength: 16, formType: 0}, function(pass, index){
            $.ajax({
                url:$('#path').val() + '/admin/addMenu',
                type:'post',
                data:{'menuName':pass,'menuUrl':'null','parentId':0},
                beforeSend:function(){
                    if(pass === '' || pass === null){//判定下是否为空
                        return false;
                    }
                },
                success:function (msg) {
                    layer.msg(msg);
                    if(msg === '增加成功'){
                        table.reload('demotable', {
                            url:$('#path').val()+'/admin/findMenuById'
                            ,where:{
                                menuName:$('#menuName').val()
                            }
                            ,page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        })
                    }
                    layer.close(index);
                },
                error:function (msg) {
                    layer.msg('网络开小差啦',{icon:5});
                    layer.close(index);
                }
            })
        });
    }

    table.on('tool(demotable)', function(obj){
        var data = obj.data;
        //console.log(obj)
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        var menuId = tr.find("td").eq(0).text();
        if(obj.event === 'delete'){
            layer.confirm('确定删除该菜单吗？如果是一级菜单会导致其所有二级菜单也被删除！', function(index){
                $.ajax({
                    url:$('#path').val()+'/admin/deleteMenu',
                    type:'post',
                    data:{'menuId':menuId,'parentId':tr.find("td").eq(3).text()},
                    success:function (msg) {
                        layer.msg(msg);
                        //重载表格
                        table.reload('demotable', {
                            url:$('#path').val()+'/admin/findMenuById'
                            ,where:{
                                menuName:$('#menuName').val()
                            }
                            ,page: {
                                curr: 1 //重当前页
                            }
                        })
                    },
                    error:function () {
                        layer.msg('网络开小差啦',{icon:5});
                    }
                })
            });
        }else if(obj.event === 'edit'){
            $.ajax({
                url:$('#path').val() + '/admin/findSubmenu',
                type:'post',
                data:{parentId:0},
                success:function (msg) {
                    var html = '<form class="layui-form" onsubmit="return false;" lay-filter="formTest">';
                    html += '<div class="layui-form-item">' +
                        '        <label class="layui-form-label">菜单名称：</label>' +
                        '        <div class="layui-input-inline">' +
                        '            <input type="text" autocomplete="off" value="'+tr.find("td").eq(1).text()+'" class="layui-input" disabled>'+
                        '        </div>' +
                        '    </div>'
                    html += '  <div class="layui-form-item">' +
                        '    <label class="layui-form-label">一级菜单:</label>' +
                        '    <div class="layui-input-inline">' +
                        '      <select name="parentId" id="parentId" lay-verify="required" lay-filter="parentId">';
                    for (var i = 1;i < msg.length;i++){
                        html += '<option value="'+msg[i].menuId+'">'+msg[i].menuName+'</option>';
                    }
                    html += '</select></div></div>'
                    html += '</form>'
                    $('#submenu').empty();//清空容器
                    $('#submenu').html(html);
                    form.val('formTest',{'parentId':tr.find("td").eq(3).text()});//给下拉框赋初始值，formTest是form的lay-filter,parentId是name
                    form.render();//重新渲染（必须）
                    layer.open({
                        type: 1,
                        title:'更改一级菜单',
                        skin: 'layui-layer-rim', //加上边框
                        area: ['360px', '320px'], //宽高
                        content: $('#submenu'),
                        btn:['确定','取消'],
                        btn1:function(index, layero) {
                            layer.confirm('你确定要修改该菜单的父级菜单吗',function () {
                                $.ajax({
                                    url:$('#path').val() + '/admin/updateMenuParentId',
                                    type:'post',
                                    data:{'menuId':menuId,'parentId':$('#parentId').val()},
                                    success:function (msg) {
                                        layer.msg(msg);
                                        if(msg === 'success'){
                                            obj.update({
                                                parentId:$('#parentId').val()
                                            });
                                        }
                                        layer.close(index);
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
                },
                error:function (msg) {
                    layer.msg('网络开小差啦',{icon:5});
                }
            })
        }else if(obj.event === 'add'){
            var html = '<form class="layui-form" onsubmit="return false;">' +
                '    <div class="layui-form-item">' +
                '        <label class="layui-form-label">菜单名称：</label>' +
                '        <div class="layui-input-inline">' +
                '            <input type="text" id ="submenuName" name ="submenuName" placeholder="请输入菜单名称" autocomplete="off" class="layui-input">' +
                '        </div>' +
                '    </div>' +
                '    <div class="layui-form-item">' +
                '        <label class="layui-form-label">菜单路径：</label>' +
                '        <div class="layui-input-inline">' +
                '            <input type="text" id ="menuUrl" name ="menuUrl" placeholder="例：/url/admin/adminLogin" autocomplete="off" class="layui-input">' +
                '        </div>' +
                '    </div>' +
                '    <div class="layui-form-item">' +
                '       <label class="layui-form-label">父级菜单：</label>' +
                '       <div class="layui-input-inline">' +
                '       <input type="text" autocomplete="off" value="'+tr.find("td").eq(1).text()+'" class="layui-input" disabled>'+
                '       </div>' +
                '    </div>'+
                '    <div class="layui-form-item">' +
                '       <label class="layui-form-label">立即应用所有角色：</label>' +
                '       <div class="layui-input-inline">' +
                '       <input type="radio" name="use" value="yes" title="是">' +
                '       <input type="radio" name="use" value="no" title="否" checked>'+
                '       </div>' +
                '    </div>'+
                '</form>';
            $('#submenu').empty();
            $('#submenu').html(html);
            form.render();
            layer.open({
                type: 1,
                title:'增加二级菜单',
                skin: 'layui-layer-rim', //加上边框
                area: ['450px', '320px'], //宽高
                content: $('#submenu'),
                btn:['确定','取消'],
                btn1:function(index, layero) {
                    layer.confirm('你确定增加吗',function () {
                        $.ajax({
                            url:$('#path').val() + '/admin/addSubmenu',
                            type:'post',
                            data:{'menuName':$('#submenuName').val(),'menuUrl':$('#menuUrl').val(),'parentId':tr.find("td").eq(0).text(),'use':$('input[name="use"]:checked').val()},
                            beforeSend:function(){
                              if($('#submenuName').val().length < 2 || $('#submenuName').val().length >10){
                                  layer.msg('菜单名('+$('#submenuName').val()+')长度不在2~10位之间');
                                  return false;
                              }
                              if($('#menuUrl').val() === '' || $('#menuUrl').val() === null){
                                  layer.msg('菜单路径不能为空');
                                  return false;
                              }
                            },
                            success:function (msg) {
                                if(msg === 'success'){
                                    table.reload('demotable', {
                                        url:$('#path').val()+'/admin/findMenuById'
                                        ,where:{
                                            menuName:$('#menuName').val()
                                        }
                                        ,page: {
                                            curr: obj.curr //重新从当前页开始
                                        }
                                    })
                                    layer.close(index);
                                }
                                layer.msg(msg);
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
    });
});


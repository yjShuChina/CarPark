layui.use(['layer','table','form','util'],function () {
    var form = layui.form,table = layui.table,layer = layui.layer,util = layui.util;

    form.on('submit(formDemo)', function(data){
        table.reload('demotable', {
            url:$('#path').val()+'/admin/findSysParamByPage'
            ,where:{
                parameterName:$('#parameterName').val(),
                parameterExplain:$('#parameterExplain').val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        })
    });

    table.render({
        elem: '#demotable'
        ,url:$('#path').val()+'/admin/findSysParamByPage'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,limit:10
        ,id: 'demotable'
        ,page: true
        ,cols: [
            [
                {field:'parameterId', title: '参数ID', sort: true}
                ,{field:'parameterName', title: '参数名称',edit: 'text'}
                ,{field:'parameterValue', title: '参数值',edit: 'text'}
                ,{field:'parameterExplain', title: '参数说明',edit: 'text'}
                ,{field:'right', title: '操作',toolbar: '#barDemo'}
            ]
        ]
    });

    table.on('edit(demotable)', function(obj){
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field //得到字段
            ,old=$(this).prev().text();//旧值
        layer.confirm('您确定要修改吗?',{
            btn:['确定','取消']
            ,btn1:function () {
                $.ajax({
                    url:$('#path').val() + '/admin/updateSysParam',
                    type:'post',
                    data:{'parameterId':data.parameterId,[field]:value},
                    beforeSend:function(){
                        if(value === '' || value === null){
                            obj.update({
                                [field]:old
                            });
                            layer.msg("不能为空");
                            return false;
                        }else if(field === 'parameterName' && value.length > 16){
                            obj.update({
                                [field]:old
                            });
                            layer.msg("参数名过长");
                            return false;
                        }else if(field === 'parameterValue' && value.length > 32){
                            obj.update({
                                [field]:old
                            });
                            layer.msg("参数值过长");
                            return false;
                        }else if(field === 'parameterExplain' && value.length > 50){
                            obj.update({
                                [field]:old
                            });
                            layer.msg("说明过长");
                            return false;
                        }
                    },
                    success:function (msg) {
                        if(msg === 'success'){
                            layer.msg(msg);
                        }else {
                            layer.msg('参数名已存在');
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

    table.on('tool(demotable)', function(obj) {
        var data = obj.data;
        //console.log(obj)
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        var parameterId = tr.find("td").eq(0).text();
        if(obj.event === 'delete'){
            layer.confirm('确定要删除该参数吗',function () {
                $.ajax({
                    url:$('#path').val()+'/admin/deleteSysParam',
                    type:'post',
                    data:{'parameterId':parameterId},
                    success:function (msg) {
                        layer.msg(msg);
                        if(msg === 'success'){
                            table.reload('demotable', {
                                url:$('#path').val()+'/admin/findSysParamByPage'
                                ,where:{
                                    parameterName:$('#parameterName').val(),
                                    parameterExplain:$('#parameterExplain').val()
                                }
                                ,page: {
                                    curr: 1 //重新从第 1 页开始
                                }
                            })
                        }
                    },
                    error:function () {
                        layer.msg('网络开小差啦',{icon:5});
                    }
                })
            })
        }
    });

    window.addSysParam = function () {
        var html = '<form class="layui-form" onsubmit="return false;">' +
            '            <div class="layui-form-item">' +
            '                <label class="layui-form-label">参数名：</label>' +
            '                <div class="layui-input-inline">' +
            '                    <input type="text" id ="parameterName2" name ="parameterName" placeholder="请输入" autocomplete="off" class="layui-input">' +
            '                </div>' +
            '            </div>' +
            '            <div class="layui-form-item">' +
            '                <label class="layui-form-label">参数值：</label>' +
            '                <div class="layui-input-inline">' +
            '                    <input type="text" id ="parameterValue2" name ="parameterValue" placeholder="请输入" autocomplete="off" class="layui-input">' +
            '                </div>' +
            '            </div>' +
            '            <div class="layui-form-item">' +
            '                <label class="layui-form-label">参数说明：</label>' +
            '                <div class="layui-input-inline">' +
            '                    <input type="text" id ="parameterExplain2" name ="parameterExplain" placeholder="请输入" autocomplete="off" class="layui-input">' +
            '                </div>' +
            '            </div>' +
            '        </form>';
        $('#parameterDiv').html(html);
        form.render();
        layer.open({
            title:'添加系统参数'
            ,type:1
            ,area:['360px','280px']
            ,skin: 'layui-layer-rim'//加上边框
            ,content:$('#parameterDiv')
            ,resize:false
            ,btn:['确定','取消']
            ,btn1:function (index,layero) {
                layer.confirm('确定添加吗',function () {
                    $.ajax({
                        url:$('#path').val() + '/admin/addSysParam',
                        type:'post',
                        data:{'parameterName':$('#parameterName2').val(),'parameterValue':$('#parameterValue2').val(),'parameterExplain':$('#parameterExplain2').val()},
                        beforeSend:function () {
                            if($('#parameterName2').val() === '' ||$('#parameterName2').val() === null){
                                layer.msg('参数名不能为空');
                                return false;
                            }else if($('#parameterName2').val().length > 16){
                                layer.msg('参数名过长');
                                return false;
                            };
                            if($('#parameterValue2').val()===''||$('#parameterValue2').val() === null){
                                layer.msg('参数值不能为空');
                                return false;
                            }else if($('#parameterValue2').val().length > 32){
                                layer.msg('参数值过长');
                                return false;
                            }
                            if($('#parameterExplain2').val().length > 50){
                                layer.msg('参数说明过长');
                                return false;
                            }
                        },
                        success:function (msg) {
                            if(msg === 'success'){
                                layer.msg('添加成功');
                                layer.close(index);
                                table.reload('demotable', {
                                    url:$('#path').val()+'/admin/findSysParamByPage'
                                    ,where:{
                                        parameterName:$('#parameterName').val(),
                                        parameterExplain:$('#parameterExplain').val()
                                    }
                                    ,page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                })
                            }else {
                                layer.msg(msg);
                            }
                        },
                        error:function () {
                            layer.msg('网络开小差',{icon:5});
                        }
                    })
                })
            }
            ,btn2:function (index,layero) {
                layer.close(index);
            }
        })
    }
});
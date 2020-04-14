layui.use(['form','layer','util','table','laydate'], function() {
    var form = layui.form,
        layer = layui.layer,
        table = layui.table,
        laydate = layui.laydate,
        util = layui.util;

    laydate.render({
        elem: '#test1'
        ,type: 'datetime'
        ,range: '~' //或 range: '~' 来自定义分割字符
    });

    $(document).ready(function () {
        $.ajax({
            url:$('#path').val() + '/admin/findMonthParameter',
            type:'post',
            success:function (msg) {
                var html = '<option value="0">临时用户</option>';
                for(var i = 0;i < msg.length;i ++){
                    html += '<option value="'+msg[i].month+'">'+msg[i].month+'月</option>'
                }
                $('#month').append(html);
                form.render();
            },
            error:function () {
                layer.msg('网络开小差',{icon:5});
            }
        })
    });

    //监听提交
    form.on('submit(formDemo)', function(data){
        table.reload('demotable', {
            url:$('#path').val()+'/admin/findRevenueByPage'
            ,where:{
                incomeType:$('#incomeType').val(),
                revenue:$('#revenue').val(),
                month:$('#month').val(),
                time:$('#test1').val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        })
    });
    //数据表格渲染
    table.render({
        elem: '#demotable'
        ,url:$('#path').val()+'/admin/findRevenueByPage'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,limit:10
        ,id: 'demotable'
        ,page: true
        ,cols: [
            [
                {field:'revenueId', title: '收支表ID',width:180, sort: true}
                ,{field:'incomeType', title: '缴费渠道',width:150,templet:'#tpl3'}
                ,{field:'month', title: '月缴产品',width:150,templet:'#tpl1'}
                ,{field:'price', title: '金额（元）',width:150,sort:true}
                ,{field:'time', title: '发生时间',sort:true}
                ,{field:'revenue', title: '收入/支出',width:150,templet:'#tpl2'}
                ,{field:'right', title: '操作',toolbar: '#barDemo'}
            ]
        ]
    });

    window.addRevenue = function () {
        $.ajax({
            url:$('#path').val() + '/admin/findMonthParameter',
            type:'post',
            success:function (msg) {
                var html = '<form class="layui-form" onsubmit="return false;">'+
                    '<div class="layui-form-item">'+
                    '   <label class="layui-form-label">缴费渠道：</label>'+
                    '   <div class="layui-input-inline">' +
                    '        <select name="incomeType2" id="incomeType2">' +
                    '             <option value="phone">手机端</option>' +
                    '             <option value="auto">自助缴费机</option>' +
                    '             <option value="manual">人工收费</option>' +
                    '        </select>' +
                    '   </div>'+
                    '</div>'+
                    '<div class="layui-form-item">'+
                    '   <label class="layui-form-label">收入/支出：</label>'+
                    '   <div class="layui-input-inline">' +
                    '        <select name="revenue2" id="revenue2">' +
                    '             <option value="1">收入</option>' +
                    '             <option value="2">支出</option>' +
                    '        </select>' +
                    '    </div>'+
                    '</div>'+
                    '<div class="layui-form-item">'+
                    '   <label class="layui-form-label">月缴产品：</label>'+
                    '   <div class="layui-input-inline">'+
                    '       <select name="month2" id="month2" lay-filter = "month2">'+
                    '           <option value="0">临时用户</option>';
                    for(var i = 0;i < msg.length;i ++){
                        html += '<option value="'+msg[i].month+'">'+msg[i].month+'月</option>'
                    }
                    html += '</select></div></div>'+
                    '<div class="layui-form-item">'+
                    '   <label class="layui-form-label">金额：</label>'+
                    '   <div class="layui-input-inline">' +
                    '       <input type="text" name="price" id="price"  placeholder="请输入金额" autocomplete="off" class="layui-input">'+
                    '   </div>'+
                    '</div>'+
                    '<div class="layui-form-item">'+
                    '   <label class="layui-form-label">发生时间：</label>'+
                    '   <div class="layui-input-inline">' +
                    '        <input type="text" name="time2" id="time2" placeholder="yy-MM-dd HH:mm:ss" autocomplete="off" class="layui-input" >'+
                    '   </div></div></form>';
                $('#revenueDiv').empty();
                $('#revenueDiv').append(html);
                form.render();

                laydate.render({
                    elem: '#time2'
                    ,type: 'datetime'
                });
            },
            error:function () {
                layer.msg('网络开小差',{icon:5});
            }
        });
        form.on('select(month2)',function (data){
            if(data.value === '0'){
                $('#price').removeAttr('disabled')
            }else {
                $.ajax({
                    url:$('#path').val() + '/admin/selectPriceByMonth',
                    type:'post',
                    data:{'month':data.value},
                    success:function (msg) {
                        $('#price').val(msg)
                    },
                    error:function () {
                        layer.msg('网络开小差',{icon:5});
                    }
                })
                $('#price').attr('disabled','disabled')
            }
            form.render();
        });
        layer.open({
            title:'添加收支明细'
            ,type:1
            ,area:['360px','420px']
            ,skin: 'layui-layer-rim'//加上边框
            ,content:$('#revenueDiv')
            ,resize:false
            ,btn:['确定','取消']
            ,btn1:function (index,layero) {
                layer.confirm('确定添加吗',function () {
                    $.ajax({
                        url:$('#path').val() + '/admin/addRevenue',
                        type:'post',
                        data:{'incomeType':$('#incomeType2').val(),'revenue':$('#revenue2').val(),'month':$('#month2').val(),'price':$('#price').val(),'time':$('#time2').val()},
                        beforeSend:function () {
                            if($('#month2').val() === '0' && ($('#price').val() === '' ||$('#price').val() === null)){
                                layer.msg('金额不能为空');
                                return false;
                            };
                            if($('#time2').val()===''||$('#time2').val().length<1){
                                layer.msg('请选择时间');
                                return false;
                            }
                        },
                        success:function (msg) {
                            if(msg === 'success'){
                                layer.msg('添加成功');
                                layer.close(index);
                                table.reload('demotable', {
                                    url:$('#path').val()+'/admin/findRevenueByPage'
                                    ,where:{
                                        incomeType:$('#incomeType').val(),
                                        revenue:$('#revenue').val(),
                                        month:$('#month').val(),
                                        time:$('#test1').val()
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
        });
    }

    table.on('tool(demotable)', function(obj) {
        var data = obj.data;
        //console.log(obj)
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        var revenueId = tr.find("td").eq(0).text();
        if(obj.event === 'delete'){
            layer.confirm('您确定要删除该条记录吗',function () {
                $.ajax({
                    url:$('#path').val() + '/admin/deleteRevenueById',
                    type:'post',
                    data:{'revenueId':revenueId},
                    success:function (msg) {
                        if(msg === 'success'){
                            obj.del();
                        }
                        layer.msg(msg);
                    },
                    error:function () {
                        layer.msg('网络开小差',{icon:5});
                    }
                })
            })
        }else if(obj.event === 'edit'){
            $.ajax({
                url:$('#path').val() + '/admin/findMonthParameter',
                type:'post',
                success:function (msg) {
                    $.ajax({
                        url:$('#path').val() + '/admin/findRevenueById',
                        type:'post',
                        data:{'revenueId':revenueId},
                        success:function (msg2) {
                            var html = '<form class="layui-form" onsubmit="return false;" lay-filter="formTest">'+
                                '<div class="layui-form-item">'+
                                '   <label class="layui-form-label">ID：</label>'+
                                '   <div class="layui-input-inline">' +
                                '       <input type="text" id="revenueID" value="'+revenueId+'" autocomplete="off" class="layui-input" disabled = "disabled">'+
                                '   </div>'+
                                '</div>'+
                                '<div class="layui-form-item">'+
                                '   <label class="layui-form-label">缴费渠道：</label>'+
                                '   <div class="layui-input-inline">' +
                                '        <select id="incomeType3" name="incomeType3">' +
                                '             <option value="phone">手机端</option>' +
                                '             <option value="auto">自助缴费机</option>' +
                                '             <option value="manual">人工收费</option>' +
                                '        </select>' +
                                '   </div>'+
                                '</div>'+
                                '<div class="layui-form-item">'+
                                '   <label class="layui-form-label">收入/支出：</label>'+
                                '   <div class="layui-input-inline">' +
                                '        <select id="revenue3" name="revenue3">' +
                                '             <option value="1">收入</option>' +
                                '             <option value="2">支出</option>' +
                                '        </select>' +
                                '    </div>'+
                                '</div>'+
                                '<div class="layui-form-item">'+
                                '   <label class="layui-form-label">月缴产品：</label>'+
                                '   <div class="layui-input-inline">'+
                                '       <select id="month3" name = "month3" lay-filter="month3">'+
                                '           <option value="0">临时用户</option>';
                            for(var i = 0;i < msg.length;i ++){
                                html += '<option value="'+msg[i].month+'">'+msg[i].month+'月</option>'
                            }
                            html += '</select></div></div>'+
                                '<div class="layui-form-item">'+
                                '   <label class="layui-form-label">金额：</label>'+
                                '   <div class="layui-input-inline">' +
                                '       <input type="text" name="price3" id="price3" value="'+msg2.price+'" placeholder="请输入金额" autocomplete="off" class="layui-input" disabled="disabled">'+
                                '   </div>'+
                                '</div>'+
                                '<div class="layui-form-item">'+
                                '   <label class="layui-form-label">发生时间：</label>'+
                                '   <div class="layui-input-inline">' +
                                '        <input type="text" id="time3" value="'+msg2.time+'" placeholder="yy-MM-dd HH:mm:ss" autocomplete="off" class="layui-input" >'+
                                '   </div></div></form>';
                            $('#revenueDiv').empty();
                            $('#revenueDiv').append(html);
                            form.val('formTest',{
                                'incomeType3':msg2.incomeType,
                                'revenue3':msg2.revenue,
                                'month3':msg2.month
                            });//给下拉框赋初始值，formTest是form的lay-filter,parentId是name
                            form.render();
                            laydate.render({
                                elem: '#time3'
                                ,type: 'datetime'
                            });
                        },
                        error:function () {
                            layer.msg('网络开小差',{icon:5});
                        }
                    })
                },
                error:function () {
                    layer.msg('网络开小差',{icon:5});
                }
            });
            form.on('select(month3)',function (data){
                if(data.value === '0'){
                    $('#price3').removeAttr('disabled')
                }else {
                    $.ajax({
                        url:$('#path').val() + '/admin/selectPriceByMonth',
                        type:'post',
                        data:{'month':data.value},
                        success:function (msg) {
                            $('#price3').val(msg)
                        },
                        error:function () {
                            layer.msg('网络开小差',{icon:5});
                        }
                    })
                    $('#price3').attr('disabled','disabled')
                }
                form.render();
            });

            layer.open({
                title:'修改收支明细'
                ,type:1
                ,area:['360px','450px']
                ,skin: 'layui-layer-rim'//加上边框
                ,content:$('#revenueDiv')
                ,resize:false
                ,btn:['确定','取消']
                ,btn1:function (index,layero) {
                    layer.confirm('确定修改吗',function () {
                        $.ajax({
                            url:$('#path').val() + '/admin/updateRevenue',
                            type:'post',
                            data:{'revenueId':revenueId,'incomeType':$('#incomeType3').val(),'revenue':$('#revenue3').val(),'month':$('#month3').val(),'price':$('#price3').val(),'time':$('#time3').val()},
                            beforeSend:function () {
                                if($('#month3').val() === '0' && ($('#price3').val() === '' ||$('#price3').val() === null)){
                                    layer.msg('金额不能为空');
                                    return false;
                                };
                                if($('#time3').val()===''||$('#time3').val().length<1){
                                    layer.msg('请选择时间');
                                    return false;
                                }
                            },
                            success:function (msg) {
                                if(msg === 'success'){
                                    obj.update({
                                        incomeType:$('#incomeType3').val(),
                                        revenue:$('#revenue3').val(),
                                        month:$('#month3').val(),
                                        price:$('#price3').val(),
                                        time:$('#time3').val()
                                    });
                                    layer.close(index);
                                }
                                layer.msg(msg);
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
            });
        }
    });
});
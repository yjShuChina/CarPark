layui.use(['form', 'layer', 'table'], function () {
    var layer = layui.layer //弹层
        , table = layui.table //表格

    var path = $("#path").val();


    var form = layui.form;

    //各种基于事件的操作，下面会有进一步介绍
    form.on('radio(laytype)', function (data) {

        if (data.value == 0) {
            $("#test5").attr("disabled", true); //禁用
            cpType = 0;
        }
        if (data.value == 1) {
            $("#test5").attr("disabled", false); //启用
            cpType = 1;
        }
        form.render();
    });

    //执行一个 table 实例
    table.render({
        elem: '#demo'
        , height: 420
        , url: path + '/charge/chargePrice' //数据接口
        , title: '收费规则表'
        , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , width: 494
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'cpId', title: 'ID', width: 80, hide: true}
            , {field: 'chargeTime', title: '时间', width: 120}
            , {field: 'cpType', title: '收费类型', width: 120}
            , {field: 'stackTime', title: '间隔时间', width: 120}
            , {field: 'price', title: '金额', width: 80}

        ]]
        , id: 'demotable'
        , done: function (res, curr, count) {

            $(".layui-table-box").find("[data-field='cpId']").css("display", "none");

            $("[data-field='cpType']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("直接收取")
                } else if ($(this).text() == '1') {
                    $(this).text("叠加收取")
                }
            });
            pageCurr = curr;
        }
    });

    //监听头工具栏事件
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                add();
                break;
            case 'update':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else if (data.length > 1) {
                    layer.msg('只能同时编辑一个');
                } else {
                    edit(checkStatus.data[0]);
                }
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('是否删除所选行', {icon: 3, title: '提示'}, function (indexs) {
                        del(checkStatus.data);
                        layer.close(indexs);
                        return false;
                    });
                }
                break;
        }
    });
});
var chargeTime;
var stackTime;
var cpType;

function edit(data) {
    var layer = layui.layer;
    var path = $("#path").val();

    $("#price").val(data.price);

    chargeTime = data.chargeTime;
    stackTime = data.stackTime;
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //时间段选择
        laydate.render({
            elem: '#test4'
            , type: 'time'
            , value: data.chargeTime
            , done: function (value, date, endDate) {
                chargeTime = value;
            }
        });
        //时间间隔选择
        laydate.render({
            elem: '#test5'
            , type: 'time'
            , value: data.stackTime
            , done: function (value, date, endDate) {
                stackTime = value;
            }
        });
    });
    layui.use('form', function () {
        var form = layui.form;

        if (data.cpType == 0) {
            $("#test5").attr("disabled", true); //禁用
            $("#diejia").removeAttr("checked");
            $("#zhijie").attr("checked", "checked");

        }
        if (data.cpType == 1) {
            $("#test5").attr("disabled", false); //启用
            $("#zhijie").removeAttr("checked");
            $("#diejia").attr("checked", "checked");

        }
        cpType = data.cpType;
        form.render();
    });

    layer.open({
        type: 1,
        title: '修改',
        content: $("#modify"),
        offset: '100px',
        anim: 1,
        resize: false,
        area: ['370px', '350px'],
        closeBtn: 2,
        shade: 0.6,
        move: false,
        btn: ['提交', '取消'],
        btn1: function (index, layero) {

            var price = document.getElementById("price").value;

            var usermodify = {
                "cpId": data.cpId
                , "chargeTime": chargeTime
                , "cpType": cpType
                , "stackTime": stackTime
                , "price": price
            };
            // usermodify = JSON.stringify(usermodify);

            layer.msg('修改中');
            $.ajax({
                url: path + "/charge/modifyChargePrice",
                async: "true",
                type: "Post",
                data: usermodify,
                dataType: "text",
                success: function (res) {
                    if (res == "succeed") {
                        layui.use('table', function () {
                            var table = layui.table;
                            table.reload('demotable', {});
                        });
                        layer.msg('修改成功', {icon: 6});
                        layer.close(index);

                    } else {
                        layer.msg('修改失败', {icon: 5});
                    }

                },
                error: function () {
                    layer.msg('网络正忙', {icon: 6});
                }
            })

        },
        cancel: function (index, layero) {
            layer.confirm('是否取消修改', {icon: 3, title: '提示'}, function (indexs) {
                //do something
                layer.close(indexs);
                layer.close(index);
            });
            return false;
        },
        btn2: function (index, layero) {
            layer.confirm('是否取消修改', {icon: 3, title: '提示'}, function (indexs) {
                //do something
                layer.close(indexs);
                layer.close(index);
            });
            return false;
        }
    });
}

function add() {
    var layer = layui.layer;
    var path = $("#path").val();

    $("#price").val("0");
    chargeTime = "00:00:00";
    stackTime = "00:00:00";
    cpType = 0;

    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //时间选择器
        laydate.render({
            elem: '#test4'
            , type: 'time'
            , value: '00:00:00'
            , done: function (value, date, endDate) {
                chargeTime = value;
            }
        });

        //时间间隔选择
        laydate.render({
            elem: '#test5'
            , type: 'time'
            , value: '00:00:00'
            , done: function (value, date, endDate) {
                stackTime = value;
            }
        });
    });

    $("#test5").attr("disabled", true); //禁用
    $("#diejia").removeAttr("checked");
    $("#zhijie").attr("checked", "checked");



    layer.open({
        type: 1,
        title: '添加',
        content: $("#modify"),
        offset: '100px',
        anim: 1,
        resize: false,
        area: ['370px', '350px'],
        closeBtn: 2,
        shade: 0.6,
        move: false,
        btn: ['提交', '取消'],
        btn1: function (index, layero) {

            var price = document.getElementById("price").value;

            var usermodify = {
                 "chargeTime": chargeTime
                , "cpType": cpType
                , "stackTime": stackTime
                , "price": price
            };
            // usermodify = JSON.stringify(usermodify);

            layer.msg('添加中');
            $.ajax({
                url: path + "/charge/addChargePrice",
                async: "true",
                type: "Post",
                data: usermodify,
                dataType: "text",
                success: function (res) {
                    if (res == "succeed") {
                        layui.use('table', function () {
                            var table = layui.table;
                            table.reload('demotable', {});
                        });
                        layer.msg('添加成功', {icon: 6});
                        layer.close(index);

                    } else {
                        layer.msg('添加失败', {icon: 5});
                    }

                },
                error: function () {
                    layer.msg('网络正忙', {icon: 6});
                }
            })

        },
        cancel: function (index, layero) {
            layer.confirm('是否取消添加', {icon: 3, title: '提示'}, function (indexs) {
                //do something
                layer.close(indexs);
                layer.close(index);
            });
            return false;
        },
        btn2: function (index, layero) {
            layer.confirm('是否取消添加', {icon: 3, title: '提示'}, function (indexs) {
                //do something
                layer.close(indexs);
                layer.close(index);
            });
            return false;
        }
    });
}

function del(data) {
    var layer = layui.layer;
    var path = $("#path").val();
    layer.msg('删除中');
    $.ajax({
        url: path + "/charge/delChargePrice",
        async: "true",
        type: "post",
        data: "data=" + JSON.stringify(data),
        dataType: "text",
        success: function (res) {
            if (res == "succeed") {
                layui.use('table', function () {
                    var table = layui.table;
                    table.reload('demotable', {});
                });
                layer.msg('删除成功', {icon: 6});
                layer.close(index);

            } else {
                layer.msg('删除失败', {icon: 5});
            }

        },
        error: function () {
            layer.msg('网络正忙', {icon: 6});
        }
    })
}
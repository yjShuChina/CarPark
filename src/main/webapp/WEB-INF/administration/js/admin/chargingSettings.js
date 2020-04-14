layui.use(['layer', 'table'], function () {
    var layer = layui.layer //弹层
        , table = layui.table //表格

    var path = $("#path").val();

    //执行一个 table 实例
    table.render({
        elem: '#demo'
        , height: 420
        , url: path + '/charge/chargePrice' //数据接口
        , title: '用户表'
        , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'cpId', title: 'ID', width: 80, hide: true}
            , {field: 'chargeTime', title: '时间', width: 80}
            , {field: 'price', title: '金额', width: 90}

        ]]
        , id: 'demotable'
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
function edit(data) {
    var layer = layui.layer;
    var path = $("#path").val();

    $("#price").val(data.price);

    chargeTime = data.chargeTime;
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //时间选择器
        laydate.render({
            elem: '#test4'
            , type: 'time'
            , value: data.chargeTime
            , done: function (value, date, endDate) {
                chargeTime = value;
            }
        });
    });

    layer.open({
        type: 1,
        title: '修改',
        content: $("#modify"),
        offset: '100px',
        anim: 1,
        resize: false,
        area: ['350px', '230px'],
        closeBtn: 2,
        shade: 0.6,
        move: false,
        btn: ['提交', '取消'],
        btn1: function (index, layero) {

            var price = document.getElementById("price").value;

            var usermodify = {
                "cpId": data.cpId
                , "chargeTime": chargeTime
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
    });

    layer.open({
        type: 1,
        title: '添加',
        content: $("#modify"),
        offset: '100px',
        anim: 1,
        resize: false,
        area: ['350px', '230px'],
        closeBtn: 2,
        shade: 0.6,
        move: false,
        btn: ['提交', '取消'],
        btn1: function (index, layero) {

            var price = document.getElementById("price").value;

            var usermodify = {
                "chargeTime": chargeTime
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

function del(data){

}

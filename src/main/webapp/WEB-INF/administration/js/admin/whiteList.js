layui.use(['form', 'layer', 'table'], function () {
    var layer = layui.layer //弹层
        , table = layui.table //表格

    var path = $("#path").val();

    var form = layui.form;

    //各种基于事件的操作，下面会有进一步介绍


    //执行一个 table 实例
    table.render({
        elem: '#demo'
        , height: 420
        , url: path + '/white/query' //数据接口
        , title: '白名单'
        , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , width: 493
        , page: true //开启分页
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'wlId', title: 'ID', width: 80, hide: true}
            , {field: 'userTel', title: '联系方式', width: 120}
            , {field: 'carNumber', title: '车牌号', width: 120}
            , {field: 'userName', title: '姓名', width: 120}

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

function edit(data) {
    var layer = layui.layer;
    var path = $("#path").val();

    $("#userTel").val(data.userTel);
    $("#carNumber").val(data.carNumber);
    $("#userName").val(data.userName);

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

            var userTel = document.getElementById("userTel").value;
            var tel_mind = /^1[345789]\d{9}$/;
            if (!tel_mind.test(userTel)) {
                layer.msg('请输入正确的手机号码', {icon: 6});
                setTimeout("$('#userTel').focus()", 1);
                return false;
            }
            var carNumber = document.getElementById("carNumber").value;

            var carNumber_mind = /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/
            if (!carNumber_mind.test(carNumber)) {
                layer.msg('请输入正确的车牌号码', {icon: 6});
                setTimeout("$('#carNumber').focus()", 1);
                return false;
            }

            var userName = document.getElementById("userName").value;
            if (userName == null || userName == undefined || userName == '') {
                layer.msg('请输入车主姓名', {icon: 6});
                setTimeout("$('#userName').focus()", 1);
                return false;
            }


            var usermodify = {
                "wlId": data.wlId
                , "userTel": userTel
                , "carNumber": carNumber
                , "userName": userName
            };
            // usermodify = JSON.stringify(usermodify);

            layer.msg('修改中');
            $.ajax({
                url: path + "/white/modifyWhiteList",
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


    $("#userTel").val("");
    $("#carNumber").val("");
    $("#userName").val("");

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

            var userTel = document.getElementById("userTel").value;
            var tel_mind = /^1[345789]\d{9}$/;
            if (!tel_mind.test(userTel)) {
                layer.msg('请输入正确的手机号码', {icon: 6});
                setTimeout("$('#userTel').focus()", 1);
                return false;
            }
            var carNumber = document.getElementById("carNumber").value;

            var carNumber_mind = /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/
            if (!carNumber_mind.test(carNumber)) {
                layer.msg('请输入正确的车牌号码', {icon: 6});
                setTimeout("$('#carNumber').focus()", 1);
                return false;
            }

            var userName = document.getElementById("userName").value;
            if (userName == null || userName == undefined || userName == '') {
                layer.msg('请输入车主姓名', {icon: 6});
                setTimeout("$('#userName').focus()", 1);
                return false;
            }

            var usermodify = {
                 "userTel": userTel
                , "carNumber": carNumber
                , "userName": userName
            };
            layer.msg('添加中');
            $.ajax({
                url: path + "/white/addWhiteList",
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
        url: path + "/white/delWhiteList",
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
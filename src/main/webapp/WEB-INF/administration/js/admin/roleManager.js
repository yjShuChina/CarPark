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
                menuName:$('#role').val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        })
    });

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
});
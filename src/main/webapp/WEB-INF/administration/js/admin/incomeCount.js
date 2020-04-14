layui.use(['element','layer'],function () {
    var element = layui.element,layer = layui.layer;

    $(document).ready(function () {
        $.ajax({
            url:$('#path').val() + '/admin/queryNearlySevenDays',
            type:'post',
            success:function (msg) {
                var nameArr = [], valueArr1 = [],valueArr2 = [],valueArr3 = []
                for(var key in msg){
                    if(key === 'phone'){
                        for(var i = 0;i < msg['phone'].length;i ++){
                            nameArr.push(msg['phone'][i].datas);
                            valueArr1.push(msg['phone'][i].counts);
                        }
                    }else if(key === 'auto'){
                        for(var i = 0;i < msg['auto'].length;i ++){
                            valueArr2.push(msg['auto'][i].counts);
                        }
                    }else if(key === 'manual'){
                        for(var i = 0;i < msg['manual'].length;i ++){
                            valueArr3.push(msg['manual'][i].counts);
                        }
                    }
                }
                createHistogram(nameArr,valueArr1,valueArr2,valueArr3);
            },
            error:function () {
                layer.msg('网络开小差',{icon:5});
            }
        })
    })

    window.createHistogram = function(nameArr,valueArr1,valueArr2,valueArr3) {

        //基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('sevenHistogram'));//dark为暗黑主题 不要可以去掉

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '近七天不同缴费渠道收入统计(单位：人民币)'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['手机端', '自主缴费端', '人工缴费']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: nameArr
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: '手机端',
                    type: 'line',
                    stack: '总量',
                    data: valueArr1
                },
                {
                    name: '自主缴费端',
                    type: 'line',
                    stack: '总量',
                    data: valueArr2
                },
                {
                    name: '人工缴费',
                    type: 'line',
                    stack: '总量',
                    data: valueArr3
                },
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }
});

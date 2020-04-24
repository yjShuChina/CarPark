layui.use(['element','layer'],function () {
    var element = layui.element,layer = layui.layer;

    $(document).ready(function () {
        $.ajax({
            url:$('#path').val() + '/admin/queryNearlySevenDays',
            type:'post',
            success:function (msg) {
                var nameArr = [],totalArr = [];
                for(var key in msg){
                    var data = [];
                    if(key === 'phone'){
                        for(var i = 0;i < msg['phone'].length;i ++){
                            nameArr.push(msg['phone'][i].datas);
                            data.push(msg['phone'][i].counts);
                        }
                        totalArr.push({'name':'手机端','type':'line','data':data});
                    }else if(key === 'auto'){
                        for(var i = 0;i < msg['auto'].length;i ++){
                            data.push(msg['auto'][i].counts);
                        }
                        totalArr.push({'name':'自主缴费端','type':'line','data':data});
                    }else if(key === 'manual'){
                        for(var i = 0;i < msg['manual'].length;i ++){
                            data.push(msg['manual'][i].counts);
                        }
                        totalArr.push({'name':'人工缴费','type':'line','data':data});
                    }
                }
                createHistogram('近七天不同渠道收入统计','sevenHistogram',nameArr,totalArr);
            },
            error:function () {
                layer.msg('网络开小差',{icon:5});
            }
        });
    });

    element.on('tab(docDemoTabBrief)', function(data){
        console.log(this); //当前Tab标题所在的原始DOM元素
        console.log(data.index); //得到当前Tab的所在下标
        console.log(data.elem); //得到当前的Tab大容器
        if(data.index === 0){
            $.ajax({
                url:$('#path').val() + '/admin/queryNearlySevenDays',
                type:'post',
                success:function (msg) {
                    var nameArr = [],totalArr = [];
                    for(var key in msg){
                        var data = [];
                        if(key === 'phone'){
                            for(var i = 0;i < msg['phone'].length;i ++){
                                nameArr.push(msg['phone'][i].datas);
                                data.push(msg['phone'][i].counts);
                            }
                            totalArr.push({'name':'手机端','type':'line','data':data});
                        }else if(key === 'auto'){
                            for(var i = 0;i < msg['auto'].length;i ++){
                                data.push(msg['auto'][i].counts);
                            }
                            totalArr.push({'name':'自主缴费端','type':'line','data':data});
                        }else if(key === 'manual'){
                            for(var i = 0;i < msg['manual'].length;i ++){
                                data.push(msg['manual'][i].counts);
                            }
                            totalArr.push({'name':'人工缴费','type':'line','data':data});
                        }
                    }
                    createHistogram('近七天不同渠道收入统计','sevenHistogram',nameArr,totalArr);
                },
                error:function () {
                    layer.msg('网络开小差',{icon:5});
                }
            });
        }else if(data.index === 1){
            $.ajax({
                url:$('#path').val() + '/admin/queryNearlyMonth',
                type:'post',
                success:function (msg) {
                    var nameArr = [],totalArr = [];
                    for(var key in msg){
                        var data = [];
                        if(key === 'phone'){
                            for(var i = 0;i < msg['phone'].length;i ++){
                                nameArr.push(msg['phone'][i].datas);
                                data.push(msg['phone'][i].counts);
                            }
                            totalArr.push({'name':'手机端','type':'line','data':data});
                        }else if(key === 'auto'){
                            for(var i = 0;i < msg['auto'].length;i ++){
                                data.push(msg['auto'][i].counts);
                            }
                            totalArr.push({'name':'自主缴费端','type':'line','data':data});
                        }else if(key === 'manual'){
                            for(var i = 0;i < msg['manual'].length;i ++){
                                data.push(msg['manual'][i].counts);
                            }
                            totalArr.push({'name':'人工缴费','type':'line','data':data});
                        }
                    }
                    createHistogram('上一个月不同渠道收入统计','preMonthHistogram',nameArr,totalArr);
                },
                error:function () {
                    layer.msg('网络开小差',{icon:5});
                }
            });
        }else if(data.index === 2){
            $.ajax({
                url:$('#path').val() + '/admin/queryCurYearBySeason',
                type:'post',
                success:function (msg) {
                    var nameArr = [],totalArr = [];
                    for(var key in msg){
                        var data = [];
                        if(key === 'phone'){
                            for(var i = 0;i < msg['phone'].length;i ++){
                                nameArr.push(msg['phone'][i].datas);
                                data.push(msg['phone'][i].counts);
                            }
                            totalArr.push({'name':'手机端','type':'line','data':data});
                        }else if(key === 'auto'){
                            for(var i = 0;i < msg['auto'].length;i ++){
                                data.push(msg['auto'][i].counts);
                            }
                            totalArr.push({'name':'自主缴费端','type':'line','data':data});
                        }else if(key === 'manual'){
                            for(var i = 0;i < msg['manual'].length;i ++){
                                data.push(msg['manual'][i].counts);
                            }
                            totalArr.push({'name':'人工缴费','type':'line','data':data});
                        }
                    }
                    createHistogram('今年每季度不同渠道收入统计','curYearHistogram',nameArr,totalArr);
                },
                error:function () {
                    layer.msg('网络开小差',{icon:5});
                }
            });
        }else if(data.index === 3){
            $.ajax({
                url:$('#path').val() + '/admin/queryCurYearByMonth',
                type:'post',
                success:function (msg) {
                    var nameArr = [],totalArr = [];
                    for(var key in msg){
                        var data = [];
                        if(key === 'phone'){
                            for(var i = 0;i < msg['phone'].length;i ++){
                                nameArr.push(msg['phone'][i].datas);
                                data.push(msg['phone'][i].counts);
                            }
                            totalArr.push({'name':'手机端','type':'line','data':data});
                        }else if(key === 'auto'){
                            for(var i = 0;i < msg['auto'].length;i ++){
                                data.push(msg['auto'][i].counts);
                            }
                            totalArr.push({'name':'自主缴费端','type':'line','data':data});
                        }else if(key === 'manual'){
                            for(var i = 0;i < msg['manual'].length;i ++){
                                data.push(msg['manual'][i].counts);
                            }
                            totalArr.push({'name':'人工缴费','type':'line','data':data});
                        }
                    }
                    createHistogram('今年每月不同渠道收入统计','curSeasonHistogram',nameArr,totalArr);
                },
                error:function () {
                    layer.msg('网络开小差',{icon:5});
                }
            });
        }else if(data.index === 4){
            $.ajax({
                url:$('#path').val() + '/admin/queryMonthRevenue',
                type:'post',
                success:function (msg) {
                    creatPieChart(msg['nameArr'],msg['valueArr']);
                },
                error:function () {
                    layer.msg('网络开小差',{icon:5});
                }
            });
        }
    });

    window.createHistogram = function(titleText,divId,nameArr,totalArr) {

        //基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(divId),'light');//dark为暗黑主题 不要可以去掉

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: titleText,
                subtext: '（单位：人名币）'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['手机端','自主缴费端','人工缴费']
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
            series: totalArr
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }


    window.creatPieChart = function (nameArr,valueArr) {
        var myChart = echarts.init(document.getElementById('monthPieChart'));//dark为暗黑主题 不要可以去掉
        var option = {
            title: {
                text: '本年度不用月缴产品收入',
                subtext: '（单位：人名币）',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: nameArr
            },
            series: [
                {
                    name: '月缴产品',
                    type: 'pie',
                    radius: '65%',
                    center: ['50%', '60%'],
                    data: valueArr,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
    }
});

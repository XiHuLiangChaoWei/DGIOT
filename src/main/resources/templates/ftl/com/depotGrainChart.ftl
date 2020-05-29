
<body style="height: 100%; margin: 0">
    <div id="container" style="height: 100%"></div>
    <script type="text/javascript" src="./js/echarts.min.js"></script>
    <script type="text/javascript" src="./js/echarts-gl.min.js"></script>
    <script type="text/javascript" src="./js/jquery.min.js"></script>
    <script type="text/javascript">
        var depotChart;
        var data = null;
        var xData = [];
        var data1 = [];
        var data2 = [];
        var data3 = [];
        function getValue(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        }
        function formatValue(d) {
            if (d == -100 || d == -250) {
                return 0;
            }
            return d;
        }

        function getData() {
            $.ajax({
                type: "get",
                url: "../../page/web/depotGrainChart",
                // data: {depotId:depotId,batchId:batchId},
                success: function (result) {
                    data = result;
                    analysisData();
                },
                error: function () {
                    alert("3D模型数据获取出错，请重新尝试！");
                }
            });
        }
        function analysisData() {
            var tempMin = 0.0, tempMax = 0.0;
            var tempAve = 0.0;
            $.each(data, function (index, item) {
                if (item.tempMin) tempMin = item.tempMin;
                if (item.tempMax) tempMax = item.tempMax;
                if (item.tempAve) tempAve = item.tempAve;
                xData.push(item.remark);
                data1.push(tempMin);
                data2.push(tempAve);
                data3.push(tempMax);
            });

            showData();
        }
        function showData() {
            depotChart = initDepotChart();
            depotChart.option.xAxis[0].data = xData;
            depotChart.option.series[0].data = data1;
            depotChart.option.series[1].data = data2;
            depotChart.option.series[2].data = data3;
            depotChart.chart.setOption(depotChart.option, true);
        }
        function initDepotChart() {
            var option = {
                title: {
                    show: false
                    //text : '仓库存储量统计',
                    // subtext : '单位：℃'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: ['最低温度', '平均温度', "最高温度"]
                },
                grid: {
                    left: '2%',
                    right: '2%',
                    bottom: '5%',
                    containLabel: true
                },
                xAxis: [{
                    type: 'category',
                    data: ['一号仓库', '二号仓库', '三号仓库', '四号仓库', '五号仓库', '六号仓库', '七号仓库']
                }],
                yAxis: [{
                    type: 'value'
                }],
                series: [{
                    name: '最低温度',
                    type: 'bar',
                    data: [00, 00, 00, 00, 00, 00, 00]
                },
                {
                    name: '平均温度',
                    type: 'bar',
                    data: [00, 00, 00, 00, 00, 00, 00]
                }
                    , {
                    name: '最高温度',
                    type: 'bar',
                    data: [00, 00, 00, 00, 00, 00, 00]
                }
                ]
            };

            var myChart = echarts.init(document.getElementById("container"), "light");
            myChart.setOption(option, true);
            return {
                "chart": myChart,
                "option": option
            };
        }
        getData();
    </script>
</body>

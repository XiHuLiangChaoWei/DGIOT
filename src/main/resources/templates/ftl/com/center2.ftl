<link rel="stylesheet" href="./layui/css/layui.css" media="all">
<link rel="stylesheet" href="./css/layer.css">
<link rel="stylesheet" href="./css/center2.css">
<script src="./js/jquery-1.8.0.min.js"></script>

<#--<script type="text/javascript">-->
<#--<!---->
<#--	function detection(){-->

<#--		$.ajax({-->
<#--			url : 'http://localhost:8080/igds/page/detection/detection.do',-->
<#--			type : "post",-->
<#--			data :  {detection:"detection"},-->
<#--			dataType : 'json',-->
<#--			success : function(d) {-->
<#--					if(d.code != -1){-->

<#--					}-->
<#--			},-->
<#--			error: function(d) {-->

<#--			}-->
<#--		});-->
<#--	}-->


<#--	function getDetection(){-->

<#--		$.ajax({-->
<#--			url : 'http://localhost:8080/igds/page/detection/getDetection.do',-->
<#--			type : "post",-->
<#--			data :  {detection:"detection"},-->
<#--			dataType : 'json',-->
<#--			success : function(d) {-->
<#--				if(d.code != -1){-->
<#--					var json = d.msg.split(",");-->
<#--					$("#main_1").html(json[0]+" m/s");-->
<#--					$("#main_2").html(json[1] + "<br/>mm/min");-->
<#--					$("#main_3").html(json[2] + " °C");-->
<#--					$("#main_4").html(json[3] + " %RH");-->
<#--					$("#main_5").html(json[4]);-->
<#--					$("#main_6").html(json[5]);-->
<#--					$("#main_7").html(json[6] + " KPa");-->
<#--				}-->
<#--			},-->
<#--			error: function (XMLHttpRequest, textStatus, errorThrown) {-->

<#--			}-->
<#--		});-->
<#--	}-->

<#--	$(function(){-->
<#--		detection();-->
<#--		getDetection();-->
<#--		self.setInterval("getDetection()",3600000);-->
<#--	});-->
<#--&ndash;&gt;-->
<#--	-->
<#--	-->
<#--	-->
<#--	-->
<#--	-->
<#--	function detection_1(){-->
<#--		detection();-->
<#--		alert("请稍等一下, 数据正在回传中......");-->
<#--		var start = new Date().getTime();-->
<#--		while (true){-->
<#--			if(new Date().getTime() - start > 5000){-->
<#--				getDetection();-->
<#--				break;-->
<#--			}-->
<#--		}-->
<#--	}-->

<#--	-->
<#--</script>-->


<style type="text/css">
    .index {

        border: 1px #71c6fd solid;
        display: block;
        background-color: #f8f8f8;
        color: #fff;
        border-radius: 2px;
        /* transition: all .3s; */
        /* -webkit-transition: all .3s; */
        height: 150px;
        font-size: 15px;
        border-radius: 10px;
        width: 140px;

    }

    .index p {
        /*   font-style: normal;
          font-size: 30px;
          font-weight: 300;
          color: #009688; */

        padding: 10px;
        text-align: center;
    }

    .p_con {
        background-color: #71c6fd;
        font-size: 22px;
        border-radius: 8px 8px 0 0;
    }

    .div_con {
        color: #37a2da;
        text-align: center;
        font-size: 29px;
    }

    li {
        list-style: none;
    }

    .lift_one {
        background-color: #fff;
    }

    .lift_two {
        height: 207px;
        width: 1251px;
        background-color: #fff;

    }

    .layui-col-xs3 {
        width: 14%;
    }

    #button {
        color: #606060;
        border: solid 1px #b7b7b7;
        background: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#ededed));
        display: inline-block;
        zoom: 1;
        display: inline;
        vertical-align: baseline;
        margin: 0 2px;
        outline: none;
        cursor: pointer;
        text-align: center;
        text-decoration: none;
        font: 14px/100% Arial, Helvetica, sans-serif;
        padding: 4px;
        text-shadow: 0 1px 1px rgba(0, 0, 0, .3);
        -webkit-border-radius: .5em;
        -moz-border-radius: .5em;
        border-radius: .5em;
        -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
        -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
        box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
    }

</style>

<body layadmin-themealias="green">
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md6">
                    <div class="layui-card" style=" width: 202%;">
                        <div class="layui-card-header">
                            气象站详情
                            <span type="button" id="button" style="margin-left: 900px;"
                                  onclick="detection_1();"> 手动提取 </span>

                        </div>
                        <div class="layui-card-body">
                            <div class="layui-carousel layadmin-carousel layadmin-shortcut"
                                 lay-indicator="inside" lay-arrow="none" style="width: 100%;">
                                <ul id="ul-quickList"
                                    class="layui-row layui-col-space10 layui-this">
                                    <li class="layui-col-xs3">
                                        <div class="index">
                                            <p class="p_con">风速</p>
                                            <div style="height : 45px;"></div>
                                            <div id="main_1" class="div_con">
                                                -- m/s
                                            </div>
                                        </div>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <div class="index">
                                            <p class="p_con">雨量</p>
                                            <div style="height : 45px;"></div>
                                            <div id="main_2" class="div_con">
                                                -- mm/min
                                            </div>
                                        </div>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <div class="index">
                                            <p class="p_con">温度</p>
                                            <div style="height : 45px;"></div>
                                            <div id="main_3" class="div_con">
                                                -- °C
                                            </div>
                                        </div>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <div class="index">
                                            <p class="p_con">湿度</p>
                                            <div style="height : 45px;"></div>
                                            <div id="main_4" class="div_con">
                                                -- %RH
                                            </div>
                                        </div>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <div class="index">
                                            <p class="p_con">风向</p>
                                            <div style="height : 45px;"></div>
                                            <div id="main_5" class="div_con">
                                                --
                                            </div>
                                        </div>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <div class="index">
                                            <p class="p_con">雨感</p>
                                            <div style="height : 45px;"></div>
                                            <div id="main_6" class="div_con">
                                                --
                                            </div>
                                        </div>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <div class="index">
                                            <p class="p_con">大气压</p>
                                            <div style="height : 45px;"></div>
                                            <div id="main_7" class="div_con">
                                                -- KPa
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-tab layui-tab-brief" lay-filter="tabChart">
                            <ul class="layui-tab-title">
                                <li class="layui-this">最新粮温</li>
                            </ul>

                            <div class="layui-tab-content" style="height: 400px;">
                                <div class="layui-tab-item layui-show">
                                    <#include "depotGrainChart.ftl">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="layui-card">
                        <div class="layui-tab layui-tab-brief"
                             lay-filter="docDemoTabBrief">
                            <ul class="layui-tab-title">
                                <li class="layui-this">仓库信息</li>
                                <!-- 									<li>仓库信息</li> -->
                            </ul>
                            <div class="layui-tab-content" style="height: 475px;">
                                <!-- 									<div class="layui-tab-item layui-show"> -->
                                <!-- 										<table class="layui-hide" id="tableGrain"></table> -->
                                <!-- 									</div> -->
                                <div class="layui-tab-item layui-show">
                                    <table class="layui-hide" id="tableDepot"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">天气预报</div>
                <div class="layui-card-body layui-text">
                    <div class="weather">
                        <iframe allowtransparency="true" frameborder="0" width="500"
                                height="170" scrolling="no"
                                src="//tianqi.eastday.com/plugin/widget_v1.html?sc=2&z=3&t=0&v=0&d=4&bd=0&k=&f=&q=1&a=1&c=54511&w=500&h=170&align=center"></iframe>
                        <!--  <iframe width="500" scrolling="no" height="170" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=19&color=%23&icon=1&temp=1&num=3&site=14"></iframe>
                    -->
                    </div>
                </div>
            </div>

            <div class="layui-card">
                <div class="layui-card-header">珍惜时间</div>
                <div class="layui-card-body">
                    <div class="clock">
                        <div id="Date"></div>
                        <ul>
                            <li id="hours">00</li>
                            <li id="point">:</li>
                            <li id="min">30</li>
                            <li id="point">:</li>
                            <li id="sec">15</li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="layui-card">
                <div class="layui-card-header">实时监控</div>
                <div class="layui-card-body layadmin-takerates">
                    <div class="layui-progress">
                        <h3>当日命令执行成功率</h3>
                        <div id="control_0" class="layui-progress-bar layui-bg-orange"
                             lay-percent="">
                            <span class="layui-progress-text"></span>
                        </div>
                    </div>
                    <div class="layui-progress">
                        <h3>分机在线情况</h3>
                        <div id="control_1" class="layui-progress-bar layui-bg-blue"
                             lay-percent="100%" style="width: 100%">
                            <span class="layui-progress-text">100%</span>
                        </div>
                    </div>
                    <div class="layui-progress">
                        <h3>当日出入库完成情况</h3>
                        <div id="control_2" class="layui-progress-bar" lay-percent="100%"
                             style="width: 100%">
                            <span class="layui-progress-text">100%</span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-card">
                <div class="layui-tab layui-tab-brief">
                    <ul class="layui-tab-title">
                        <li class="layui-this">警告信息</li>
                    </ul>
                    <div class="layui-tab-content" style="height: 475px;">
                        <div class="layui-tab-item layui-show">
                            <table class="layui-hide" id="tableWarn"></table>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>


    <#--    <!-- 粮库介绍 &ndash;&gt;-->
    <#--    <div class="layui-row layui-col-space15">-->
    <#--        <div class="layui-card">-->
    <#--            <div class="layui-tab layui-tab-brief">-->
    <#--                <ul class="layui-tab-title">-->
    <#--                    <li class="layui-this">企业介绍</li>-->
    <#--                </ul>-->
    <#--                <div class="layui-tab-content">-->
    <#--                    <div class="layui-tab-item layui-show" id="company_intro">-->

    <#--                    </div>-->
    <#--                </div>-->
    <#--            </div>-->
    <#--        </div>-->
    <#--    </div>-->

</div>

<script src="./layui/layui.all.js"></script>
<script src="./js/indexMain.js"></script>
</body>
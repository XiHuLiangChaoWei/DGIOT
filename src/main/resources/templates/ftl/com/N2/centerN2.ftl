<link rel="stylesheet" href="/layui/css/layui.css" media="all">
<link rel="stylesheet" href="/css/layer.css">
<link rel="stylesheet" href="/css/center2.css">
<script src="/js/jquery-1.8.0.min.js"></script>


<style type="text/css">
    .index p {
        /*   font-style: normal;
          font-size: 30px;
          font-weight: 300;
          color: #009688; */

        padding: 10px;
        text-align: center;
    }

    li {
        list-style: none;
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
            <form action="" method="post" name="">
                <div class="tab_1">
                    <span>选择分机 :</span>
                    <label>
                        <select id="lian" name="warehouse">
                            <option value="chrome">1-1</option>
                            <option value="safari">1-2</option>
                            <option value="Edge">1-3</option>
                            <option value="firefox">1-4</option>
                            <option value="ie8">1-5</option>
                        </select>
                    </label>
                    <button class="" type="">查询刷新</button>
                </div>
            </form>
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md8 zhinengqitiao">
                    <div>
                    </div>
                    <img src="/images/1智能气调.bmp">
                </div>
                <div class="layui-col-md4 zhinengqitiao">
                    <table class="layui-table" lay-skin="nob">
                        <colgroup>
                            <col width="150">
                            <col width="200">
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <button onclick="buttonC(1)" action1="" type="button"
                                        class="layui-btn layui-btn-normal layui-btn-fluid">上冲下排
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <button onclick="buttonC(2)" action1="xiachongshangpai" type="button"
                                        class="layui-btn layui-btn-normal layui-btn-fluid">下冲上排
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <button onclick="buttonC(3)" action1="huanliuxunzheng" type="button"
                                        class="layui-btn layui-btn-normal layui-btn-fluid">环流熏蒸
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <button onclick="buttonC(4)" action1="fuyachongdan" type="button"
                                        class="layui-btn layui-btn-normal layui-btn-fluid">负压充氮
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="border-collapse: collapse; border-bottom-width: 0"></td>
                        </tr>
                        <tr>
                            <td>
                                <button onclick="buttonC(0)" action1="stop" type="button"
                                        class="layui-btn layui-btn-danger layui-btn-fluid">停止
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="shoudongceshi" style="display: none">
                    <div class="layui-form">
                        <table class="layui-table">
                            <colgroup>
                            </colgroup>
                            <thead>
                            <tr>
                                <th></th>
                                <th style="text-align: center"><b>电动阀门</b></th>
                                <th style="text-align: center"><b>环流风机</b></th>
                                <th style="text-align: center"><b>电磁阀</b></th>
                                <th style="text-align: center"><b>气泵</b></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td style="max-width: 100px">
                                    1
                                </td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td></td>
                                <td></td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td></td>
                                <td></td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>8</td>
                                <td></td>
                                <td></td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>9</td>
                                <td></td>
                                <td></td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>10</td>
                                <td></td>
                                <td></td>
                                <td>
                                    <div class="layui-btn-group">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">关
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td colspan="5" style="text-align: left"><b>IO 状态 :</b></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="layui-col-md9 qiticeshi" style="display: none">
                    <div style="padding: 20px; background-color: #F2F2F2;">
                        <div class="layui-row layui-col-space15">
                            <div class="layui-col-md6">
                                <div class="layui-card">
                                    <#--                                    <div class="layui-card-header" style="float: right"></div>-->

                                    <table class="layui-table">
                                        <colgroup>
                                            <col>
                                            <col width="200">
                                            <col width="200">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>氧气(%)</th>
                                            <th>CO2(ppm)</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道1</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道2</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道3</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道4</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道5</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="layui-col-md6">
                                <div class="layui-card">
                                    <table class="layui-table">
                                        <colgroup>
                                            <col>
                                            <col width="200">
                                            <col width="200">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>氧气(%)</th>
                                            <th>CO2(ppm)</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道6</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道7</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道8</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道9</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-fluid">通道10</button>
                                            </td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <table class="layui-table" lay-size="sm" style="width: 70%">
                                <colgroup>
                                    <col width="30%">
                                    <col width="70%">
                                </colgroup>
                                <tbody>
                                <tr>
                                    <td>开始通道</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">-
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">+
                                        </button>
                                    </td>
                                </tr>

                                <tr>
                                    <td>结束通道</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">-
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">+
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>收集时间</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">-
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">+
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <button type="button" class="layui-btn layui-btn-sm" style="float: right">保存设置
                            </button>
                        </div>
                    </div>
                </div>
                <div class="layui-col-md3 qiticeshi" style="display: none;height: 500px">
                    <div style="padding: 20px; background-color: #F2F2F2;height: 300px">
                        <div class="layui-col-md12">
                            <div class="layui-card" style="height: 150px">
                                <div class="layui-card-header" style="background-color: #1b6d85;color: white">实时</div>
                                <div class="layui-card-body ">
                                    <div>氧气<input style="width: 50%">%</div>
                                    <div>CO2<input style="width: 50%">ppm</div>
                                </div>
                            </div>
                            <b>状 态:</b><br>
                            <b>测量通道:</b>
                        </div>
                    </div>
                    <br><br>
                    <button type="button" class="layui-btn layui-btn-fluid layui-btn-lg">开始
                    </button>
                    <br><br>
                    <button type="button" class="layui-btn layui-btn-fluid layui-btn-lg layui-btn-danger">停止
                    </button>
                </div>

                <div class="qimijiance" style="display:none;">
                    <table class="layui-table" lay-skin="nob" style="background-color: #e3e3e3">
                        <tbody>
                        <tr>
                            <td>当前仓内气压:</td>
                            <td><input>Pa</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>设定半衰期压力:</td>
                            <td><input>Pa</td>
                            <td>-<input>Pa</td>
                        </tr>
                        <tr>
                            <td>半衰期时间:</td>
                            <td><input>(s)</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>检测结果:</td>
                            <td>
                                <button class="layui-btn-radius" style="width: 100px">良好</button>
                            </td>
                            <td>半衰期 > 300 s</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <button class="layui-btn-radius" style="width: 100px">中等</button>
                            </td>
                            <td>250 s < 半衰期 ≤ 300 s</td>
                        </tr>
                        <tr>
                            <td>检测结果:</td>
                            <td>
                                <button class="layui-btn-radius" style="width: 100px">较差</button>
                            </td>
                            <td>半衰期 ≤ 250 s</td>
                        </tr>
                        </tbody>
                    </table>
                    <table style="width: 100%;text-align: center">
                        <tbody style="width: 80%">
                        <td>
                            <button class="layui-btn" style="width: 60%">开始</button>
                        </td>
                        <td>
                            <button class="layui-btn layui-btn-danger" style="width: 60%">结束</button>
                        </td>
                        </tbody>
                    </table>
                </div>

                <div class="xitongshezhi" style="display:none;">
                    <div class="layui-card">
                        <div class="layui-card-body">
                            <table class="layui-table">
                                <colgroup>
                                    <col width="150">
                                    <col width="200">
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <td>蝶阀时间</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">-
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">+
                                        </button>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>蝶阀反馈</td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>环流风机反馈</td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <div class="layui-card">
                        <div class="layui-card-body">
                            <table class="layui-table">
                                <colgroup>
                                    <col width="100">
                                    <col width="200">
                                    <col width="200">
                                </colgroup>
                                <thead>
                                <tr>
                                    <td>氮气目标浓度(%)</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">上限-
                                        </button>
                                        <#--                                        <button style="visibility: hidden" type="button"-->
                                        <#--                                                class="layui-btn layui-btn-sm  layui-btn-normal">开-->
                                        <#--                                        </button>-->
                                        <input placeholder="v" style="width: 30%">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">上限+
                                        </button>
                                    </td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">下限—
                                        </button>
                                        <input placeholder="v" style="width: 30%">
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">下限+
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>负压设置(Pa)</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">-
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">+
                                        </button>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>氮气检测时间间隔(min)</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">-
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">+
                                        </button>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>充氮时间(时)</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">-
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">+
                                        </button>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>充氮时间(分)</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">-
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">+
                                        </button>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>充氮时间(秒)</td>
                                    <td>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-danger">-
                                        </button>
                                        <button style="visibility: hidden" type="button"
                                                class="layui-btn layui-btn-sm  layui-btn-normal">开
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-sm  layui-btn-normal">+
                                        </button>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>循环测试</td>
                                    <td>

                                    </td>
                                    <td></td>
                                </tr>
                                </thead>
                            </table>
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
                        <iframe allowtransparency="true" frameborder="0" width="300"
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

    <script type="text/javascript">
        $(document).ready(function () {
            showDepotList();
            changeInfo();
        });

        function changeInfo() {
            let options = $("#lian option:selected");
            let bh = options.attr("bh");
            let zh = options.attr("zh");
            $("#bh").text("当前编号:[" + bh + "]");
            $("#zh").text("当前编号:[" + zh + "]");
        }

        function showDepotList() {
            $("#lian").empty();
            // <option value="chrome">1-1</option>
            $.ajax({
                "url": "/sql/dev/getQTList",
                "type": "GET",
                "dateType": "json",
                "success": function (json) {
                    let ls = json.data;
                    // console.log(ls);
                    for (let i = 0; i < ls.length; i++) {
                        let inner = "<option class='.iDev' bh='{bh}' zh='{zh}' value='{depotName}' >{depotName}</option>";
                        inner = inner.replace(/{depotName}/g, ls[i].deviceName);
                        inner = inner.replace("{bh}", ls[i].devBH);
                        inner = inner.replace("{zh}", ls[i].devZH);
                        $("#lian").append(inner);
                        // console.log(inner)
                    }
                }
            })
        }
    </script>

</div>

<script src="/layui/layui.all.js"></script>
<script src="/js/indexMain.js"></script>
</body>
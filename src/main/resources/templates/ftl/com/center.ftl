<script type="text/javascript" src=" ../../js/jquery-1.8.3.min.js"></script>
<link type="text/css" rel="stylesheet" href=" ../../css/common.css">
<link type="text/css" rel="stylesheet" href=" ../../css/adminpage.css">
<link type="text/css" rel="stylesheet" href=" ../../css/style.css">
<link type="text/css" rel="stylesheet" href=" ../../layui/css/layui.css">
<script type="text/javascript" src=" ../../layui/layui.all.js"></script>
<script type="text/javascript">

    var width;
    var height;

    function getWidthHeight() {
        if (window.innerWidth)
            width = window.innerWidth;
        else if ((document.body) && (document.body.clientWidth))
            width = document.body.clientWidth;

        if (window.innerHeight)
            height = window.innerHeight;
        else if ((document.body) && (document.body.clientHeight))
            height = document.body.clientHeight;
    }


    function detection() {
        layui.use('layer', function () {
            var layer = layui.layer;
            getWidthHeight();
            width = (width * 0.75) + "px";
            height = (height * 0.75) + "px";


            layer.open({
                type: 2,
                title: ['定时任务', 'font-size:18px;'],
                area: [width, height],
                offset: '15%',
                content: '/job/Quartz.html' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });

        });
    }


    function getExcel() {
        var lian = $("#lian").val();
        var pi = $("#pi").val();
        window.location = "/getExcel.do?lian=" + lian + "&pi=" + pi;
    }

    function getPdf() {
        var lian = $("#lian").val();
        var pi = $("#pi").val();
        window.location = "/getPdf.do?lian=" + lian + "&pi=" + pi;
    }

</script>
<body>
<#--<div class="cm-fullpage">-->
<#--    <div class="cm-sitemap">-->
<#--        <div class="fl navinfo">-->
<#--            <span>您现在的位置：</span>-->
<#--            <span><a href="#">菜单列表</a></span>-->
<#--            <span>&nbsp;&gt;&nbsp;</span>-->
<#--            <span>粮情检测</span>-->
<#--        </div>-->
<#--        <div class="clear"></div>-->
<#--    </div>-->
<#--    <div class="cm-center">-->
<#--        <div class="cm-pagebody">-->

<div class="titlediv">
    <div class="tname">粮情检测
        <span style="float:right;margin-right: 50px;">
                                <button class="button_2">
                                    <a onclick="detection();" href="javaScript:void(0);">定时检测</a>
                                </button>
                                <button class="button_2">手动检测</button>
                            </span>
    </div>

</div>
<form action="http://localhost:8080/index.html" method="post" name="post提交">
    <div class="tab_1">
        <span>选择仓库 :</span>
        <label>
            <select id="lian" name="warehouse">
                <option value="chrome">1-1</option>
                <option value="safari">1-2</option>
                <option value="Edge">1-3</option>
                <option value="firefox">1-4</option>
                <option value="ie8">1-5</option>
            </select>
        </label>
        <span>选择批次 :</span>
        <label>
            <select id="pi" name="batch">
                <option value="chrome">1-1</option>
                <option value="safari">1-2</option>
                <option value="Edge">1-3</option>
                <option value="firefox">1-4</option>
                <option value="ie8">1-5</option>
            </select>
        </label>
        <button class="button_1" type="submit">查询刷新</button>
    </div>
</form>

<div class="titlediv">
    <div class="tname">最新粮情信息</div>
</div>

<div class="tab_2">
    <table>
        <tr>
            <td>所属仓库</td>
            <td>粮食性质</td>
            <td>仓外温度</td>
            <td>仓外湿度</td>
            <td>仓内温度</td>
            <td>仓内湿度</td>
            <td>平均粮温</td>
            <td>最高粮温</td>
            <td>最低粮温</td>
            <td>检测批次</td>
        </tr>
        <tr>
            <td id="depot">12</td>
            <td>unknown</td>
            <td id="outT">unknown</td>
            <td id="outH">unknown</td>
            <td id="innerT">unknown</td>
            <td id="innerH">unknown</td>
            <td id="avgT">465</td>
            <td id="maxT">132</td>
            <td id="minT">15</td>
            <td id="batch">165</td>
        </tr>
    </table>

</div>

<div class="titlediv">
    <div class="tname">当前批次详细报表
        <span style="float:right;margin-right: 50px;">
                                <button class="button_2">
                                    <a onclick="getPdf()" href="javaScript:void(0);">PDF导出</a>
                                </button>
                                <button class="button_2">
                                    <a onclick="getExcel()" href="javaScript:void(0);">EXCEL导出</a>
                                </button>
                            </span>
    </div>
</div>

<table id="table_1">
    <tbody>
    <tr class="tbTitle">
        <th>行/列</th>
        <th>列1</th>
        <th>列2</th>
        <th>列3</th>
        <th>列4</th>
        <th>列5</th>
        <th>列6</th>
    </tr>
    <tr class="tbContext tbContext0">
        <td>行1</td>
        <td>数据1</td>
        <td>数据2</td>
        <td>数据3</td>
        <td>数据4</td>
        <td>数据5</td>
        <td>数据6</td>
    </tr>
    <tr class="tbContext tbContext1">
        <td>行2</td>
        <td>数据1</td>
        <td>数据2</td>
        <td>数据3</td>
        <td>数据4</td>
        <td>数据5</td>
        <td>数据6</td>
    </tr>
    <tr class="tbContext tbContext0">
        <td>行3</td>
        <td>数据1</td>
        <td>数据2</td>
        <td>数据3</td>
        <td>数据4</td>
        <td>数据5</td>
        <td>数据6</td>
    </tr>
    <tr class="tbContext tbContext1">
        <td>行4</td>
        <td>数据1</td>
        <td>数据2</td>
        <td>数据3</td>
        <td>数据4</td>
        <td>数据5</td>
        <td>数据6</td>
    </tr>
    <tr class="tbContext tbContext0">
        <td>行5</td>
        <td>数据1</td>
        <td>数据2</td>
        <td>数据3</td>
        <td>数据4</td>
        <td>数据5</td>
        <td>数据6</td>
    </tr>
    <tr class="tbContext tbContext1">
        <td>行6</td>
        <td>数据1</td>
        <td>数据2</td>
        <td>数据3</td>
        <td>数据4</td>
        <td>数据5</td>
        <td>数据6</td>
    </tr>
    <tr class="tbContext tbContext0">
        <td>行7</td>
        <td>数据1</td>
        <td>数据2</td>
        <td>数据3</td>
        <td>数据4</td>
        <td>数据5</td>
        <td>数据6</td>
    </tr>
    <tr class="tbContext tbContext1">
        <td>行8</td>
        <td>数据1</td>
        <td>数据2</td>
        <td>数据3</td>
        <td>数据4</td>
        <td>数据5</td>
        <td>数据6</td>
    </tr>
    <tr class="tbContext tbContext0">
        <td>行9</td>
        <td>数据1</td>
        <td>数据2</td>
        <td>数据3</td>
        <td>数据4</td>
        <td>数据5</td>
        <td>1</td>
    </tr>
    </tbody>
</table>


<div class="clear"></div>
<#--        </div>-->

<#--    </div>-->
<#--    <div class="hsplit">&nbsp;</div>-->
<#--</div>-->
<script type="text/javascript">


    $(document).ready(function () {
        showDepotList();

        $("#lian").change(function () {
            // alert("change")
            changeInfo();
        })
        $("#pi").change(function () {
            // alert("change")
            showGrainInfoDetail();
        })
    });

    // function showDepotInfoD() {
    //     let options = $("#pi :selected");
    //     let bh = options.attr("bh");
    //     let zh = options.attr("zh");
    //
    // }
    // $("#lian").click(showDepotList());


    // <label><input name="Fruit" type="radio" value="" />苹果 </label>
    //     <label><input name="Fruit" type="radio" value="" />桃子 </label>
    //     <label><input name="Fruit" type="radio" value="" />香蕉 </label>
    //     <label><input name="Fruit" type="radio" value="" />梨 </label>
    //     <label><input name="Fruit" type="radio" value="" />其它 </label>

    function showGrainInfoDetail() {
        $("#table_1").empty();
        let options = $("#pi :selected");
        let bh = options.attr("bh");
        let zh = options.attr("zh");
        let batchId = options.val();
        console.log("bg=" + bh + "  zh=" + zh + "  batchId=" + batchId)
        $.ajax({
            "url": "/sql/grain/choose?batchId=" + batchId + "&devBH=" + bh + "&devZH=" + zh,
            "type": "GET",
            "dateType": "json",
            "success": function (json) {
                let ls = json.data;
                console.log("grainDetails" + ls)
                // console.log(ls.depotId)
                $("#depot").html(ls.depotId);
                // console.log(ls.maxTemp)
                $("#maxT").html(ls.maxTemp);
                // console.log(ls.minTemp)
                $("#minT").html(ls.minTemp);
                $("#avgT").html(ls.avgTemp.toFixed(2));
                console.log("batch=" + ls.batchId)
                $("#batch").html(ls.batchId);

                let xx = ls.x;
                let yy = ls.y;
                let zz = ls.z;
                let ts = ls.date;
                let zz1 = ts.filter(
                    function (temp) {
                        return temp.z == 1;
                    }
                );
                console.log(zz1)
                let zz2 = ts.filter(
                    function (temp) {
                        return temp.z == 2;
                    }
                );
                console.log(zz2)
                let zz3 = ts.filter(
                    function (temp) {
                        return temp.z == 3;
                    }
                );
                console.log(zz3)
                let zz4 = ts.filter(
                    function (temp) {
                        return temp.z == 4;
                    }
                );


                let zz1x1 = zz1.filter(function (a) {
                    return a.x == 0;
                });
                let zz1x2 = zz1.filter(function (a) {
                    return a.x == 1;
                });
                let zz1x3 = zz1.filter(function (a) {
                    return a.x == 2;
                });
                let zz1x4 = zz1.filter(function (a) {
                    return a.x == 3;
                });
                let zz1x5 = zz1.filter(function (a) {
                    return a.x == 4;
                });
                let zz1x6 = zz1.filter(function (a) {
                    return a.x == 5;
                });
                let zz1x7 = zz1.filter(function (a) {
                    return a.x == 6;
                });
                console.log(zz1x1)

                let start = "<tbody>\n" +
                    "    <tr class=\"tbTitle\">\n" +
                    "        <th>行/列</th>\n";
                for (let i = 1; i <= yy; i++) {
                    let a = " <th>列" + i + "</th>";
                    start = start + a;
                }
                start = start + " </tr>";


                $("#table_1").append(start);

                let table1 = "<tr class=\"tbContext tbContext0\">\n" +
                    "        <td>行1</td>\n" +
                    "        <td>{0}</td>\n" +
                    "        <td>{1}</td>\n" +
                    "        <td>{2}</td>\n" +
                    "        <td>{3}</td>\n" +
                    "        <td>{4}</td>\n" +
                    "        <td>{5}</td>\n" +
                    "    </tr>";
                for (let i = 0; i < zz1x1.length; i++) {
                    let a =zz1x1[i].temp;
                    console.log(i +":"+a);
                  table1 =  table1.replace("{"+i+"}", a);
                }
                $("#table_1").append(table1);
            }
        })
    }


    function changeInfo() {
        let options = $("#lian :selected");
        let bh = options.attr("bh");
        let zh = options.attr("zh");
        let devName = options.attr("value");
        // console.log(bh)
        // console.log(zh)
        // console.log("devName="+devName)
        $("#pi").empty();
        // let a = "<option value=\"chrome\">1-1</option>"
        $.ajax({
            "url": "/sql/getBatchList?devBH=" + bh + "&devZH=" + zh,
            "type": "GET",
            "dateType": "json",
            "success": function (json) {
                let ls = json.data[0];
                console.log(ls);
                for (let i = 0; i < ls.length; i++) {
                    console.log((ls[i].batchId));
                    let inner = "<option bh='devBH' zh='devZH' value=\"{batchId}\">{batchId}</option>";
                    inner = inner.replace(/{batchId}/g, ls[i].batchId);
                    inner = inner.replace("devBH", bh);
                    inner = inner.replace("devZH", zh);
                    console.log(inner)
                    $("#pi").append(inner);
                }
                showGrainInfoDetail();
            },
            "error": function () {
                alert("no info")
            }
        });

    }


    function showDepotList() {
        $("#lian").empty();
        // <option value="chrome">1-1</option>
        $.ajax({
            "url": "/sql/dev/getLQList",
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
                changeInfo();
            }
        })
    }
</script>

</body>
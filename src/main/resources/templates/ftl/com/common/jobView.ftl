<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>大公智能仓储</title>
    <script type="text/javascript" src=" ../js/jquery-1.8.3.min.js"></script>
    <link type="text/css" rel="stylesheet" href=" ../css/common.css">
    <link type="text/css" rel="stylesheet" href=" ../css/adminpage.css">
    <link type="text/css" rel="stylesheet" href="../css/style.css">
    <link type="text/css" rel="stylesheet" href="../layui/css/layui.css">
    <script type="text/javascript" src="../layui/layui.js"></script>
    <script type="text/javascript">
    </script>
    <style type="text/css">
        .titlediv {
            background: #f7f7f7;
            height: 38px;
            padding-left: 20px;
            border: 1px solid #f7f7f7;
        }

        .tname {
            line-height: 38px;
            font-size: 16px;
            color: #333;
        }


        div {
            position: relative;
        }

        .input_check {
            position: absolute;
            visibility: hidden;
        }

        .input_check + label {
            display: inline-block;
            width: 16px;
            height: 16px;
            border: 1px solid #BBBBBB;
            border-radius: 4px;
        }

        .input_check:checked + label:after {
            margin-left: 69px;
            content: "";
            position: absolute;
            left: 2px;
            bottom: 12px;
            width: 9px;
            height: 4px;
            border: 2px solid #BB192A;
            border-top-color: transparent;
            border-right-color: transparent;
            -ms-transform: rotate(-60deg);
            -moz-transform: rotate(-60deg);
            -webkit-transform: rotate(-60deg);
            transform: rotate(-45deg);
        }

        .layui-center {
            text-align: center;
            width: 95%;
            height: 100%;
            padding: 12px
        }

    </style>
</head>
<body>


<div class="titlediv">
    <div class="tname">说明
        <span style="float:right;margin-right: 50px;">
                <button class="button_2">
                    <a onclick="configuration();" href="javaScript:void(0);">新增配置</a>
                </button>
                <button class="button_2">
                    <a onclick="updateConfiguration();" href="javaScript:void(0);">修改配置</a>
                </button>
                <button class="button_2">
                    <a onclick="deleteConfiguration();" href="javaScript:void(0);">删除配置</a>
                </button>
                <button class="button_2">
                    <a onclick="openConfiguration();" href="javaScript:void(0);">启动任务</a>
                </button>
                <button class="button_2">
                    <a onclick="pauseConfiguration();" href="javaScript:void(0);">停止任务</a>
                </button>
            </span>
    </div>
</div>

<div class="layui-center">
    <table id="demo" lay-filter="test"></table>
</div>


<script type="text/javascript">
    var width;
    var height;
    var data = "";
    $(function () {
        getWidthHeight();
    });

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


    layui.use('table', function () {

        var table = layui.table;
        table.render({
            elem: '#demo',
            id: 'idTest',
            url: '/job/demo/table/user.do',
            /* width : 1700,
            height : 685, */
            cellMinWidth: "11.1%",
            width: width - 20,
            height: height - 100,
            cols: [[ //标题栏
                {type: 'radio', width: '11.1%', align: 'center'},
                {field: 'id', title: 'ID', align: 'center', hide: true},
                {field: 'schemeType', title: 'schemeType', align: 'center', hide: true},
                {field: 'week', title: 'week', align: 'center', hide: true},
                {field: 'timeDate', title: 'timeDate', align: 'center', hide: true},
                {field: 'hour', title: 'hour', align: 'center', hide: true},
                {field: 'minute', title: 'minute', align: 'center', hide: true},
                {field: 'setTime', title: 'setTime', align: 'center', hide: true},
                {field: 'schemeName', title: '方案名称', width: '11.1%', align: 'center'},
                {field: 'jobClass', title: '方案类型', width: '11.1%', align: 'center'},
                {field: 'particulars', title: '方案详情', width: '11.1%', align: 'center'},
                {field: 'start', title: '运行状态', width: '11.1%', align: 'center'},
                {field: 'updateTime', title: '更新时间', width: '11.1%', align: 'center'},
                {field: 'updatePeople', title: '更新人', width: '11.1%', align: 'center'},
                {field: 'remark', title: '备注', width: '11.1%', align: 'center'},
                {field: 'operation', title: '操作', width: '11.2%', align: 'center', toolbar: "#typeBar"}

            ]],
            page: true //是否显示分页
            ,
            where: {key: ""},
            limits: [10, 20],
            limit: 10

        });


        table.on('radio(test)', function (obj) {
            data = obj.data;
        });

    });

    function configuration() {
        layui.use('layer', function () {
            var layer = layui.layer;
            getWidthHeight();
            width = (width * 0.5) + "px";
            height = (height - 50) + "px";


            layer.open({
                type: 2,
                title: ['新增任务', 'font-size:18px;'],
                area: [width, height],
                offset: '10px',
                content: '/job/test.html', //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                end: function () {

                }
            });
        });
    }


    function updateConfiguration() {
        layui.use(['layer', 'form', 'table'], function () {
            var layer = layui.layer,
                form = layui.form;

            if (data === "") {
                layer.msg('必须选择先一个任务!');
                return false;
            }

            getWidthHeight();
            width = (width * 0.5) + "px";
            height = (height - 50) + "px";

            layer.open({
                type: 2,
                title: ['修改任务', 'font-size:18px;'],
                area: [width, height],
                offset: '10px',
                content: '/updateQuartz.html?jobID=' + data.id, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                end: function () {

                }
            });


        });
    }


    function WarehouseConfiguration() {
        layui.use('layer', function () {
            var layer = layui.layer;
            getWidthHeight();
            width = (width * 0.5) + "px";
            height = (height - 50) + "px";


            layer.open({
                type: 2,
                title: ['仓库配置', 'font-size:18px;'],
                area: [width, height],
                offset: '10px',
                content: '/job/warehouse.html', //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                end: function () {

                }
            });
        });
    }


    function deleteConfiguration() {
        if (data === "") {
            layer.msg('必须选择先一个任务!');
            return false;
        }

        $.ajax({
            url: "/job/deleteQuartz.do",
            data: {jobID: data.id},
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (null == data) {
                    layer.msg('系统出错!');
                    return false;
                }
                if (data.flag === "00") {
                    layer.msg('操作出错!');
                    return false;
                }
                if (data.flag === "01") {
                    layer.msg('操作成功!');
                }
                window.location.reload();
            },
            error: function () {
                info.innerText = "提示：验证码错误！！";
            }
        });
    }


    function openConfiguration() {
        if (data === "") {
            layer.msg('必须选择先一个任务!');
            return false;
        }

        $.ajax({
            url: "/job/openQuartz.do",
            data: {jobID: data.id},
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (null == data) {
                    layer.msg('系统出错!');
                    return false;
                }
                if (data.flag === "00") {
                    layer.msg('操作出错!');
                    return false;
                }
                if (data.flag === "01") {
                    layer.msg('操作成功!');
                }
                window.location.reload();
            },
            error: function () {
                info.innerText = "提示：验证码错误！！";
            }
        });
    }


    function pauseConfiguration() {
        if (data === "") {
            layer.msg('必须选择先一个任务!');
            return false;
        }

        $.ajax({
            url: "/job/pauseQuartz.do",
            data: {jobID: data.id},
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (null == data) {
                    layer.msg('系统出错!');
                    return false;
                }
                if (data.flag === "00") {
                    layer.msg('操作出错!');
                    return false;
                }
                if (data.flag === "01") {
                    layer.msg('操作成功!');
                }
                window.location.reload();
            },
            error: function () {
                info.innerText = "提示：验证码错误！！";
            }
        });
    }

</script>
<script type="text/html" id="typeBar">
    <button class="button_2">
        <a onclick="WarehouseConfiguration();" href="javaScript:void(0);">配置仓库</a>
    </button>
</script>


</body>
</html>
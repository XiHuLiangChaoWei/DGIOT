<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>大公智能仓储</title>
    <script type="text/javascript" src=" ../js/jquery-1.8.3.min.js"></script>
    <link type="text/css" rel="stylesheet" href=" ../css/common.css">
    <link type="text/css" rel="stylesheet" href=" ../css/adminpage.css">
    <link type="text/css" rel="stylesheet" href =" ../css/style.css">
    <link type="text/css" rel="stylesheet" href =" ../layui/css/layui.css">
    <script type="text/javascript" src=" ../layui/layui.js"></script>
    <script type="text/javascript">
    </script>
    <style type="text/css">
        .titlediv{
            background: #f7f7f7;
            height: 38px;
            padding-left: 20px;
            border: 1px solid #f7f7f7;
        }

        .tname{
            line-height: 38px;
            font-size: 16px;
            color: #333;
        }

        div{position: relative;}
        .input_check {position: absolute;visibility: hidden;}

        .input_check+label {display: inline-block;width: 16px;height: 16px;border: 1px solid #BBBBBB;border-radius:4px;}

        .input_check:checked+label:after {margin-left: 69px; content: "";position: absolute;left: 2px;bottom: 12px;width: 9px;height: 4px;border: 2px solid #BB192A;border-top-color: transparent;border-right-color: transparent;-ms-transform: rotate(-60deg);-moz-transform: rotate(-60deg);-webkit-transform: rotate(-60deg);transform: rotate(-45deg);}

        .layui-center {text-align: center; width :95%; height: 100%; padding : 12px}

    </style>
</head>
<body>


<div class="titlediv">
    <div class="tname">说明
        <span style="float:right;margin-right: 50px;">
                <button class = "button_2">
                    <a onclick = "configuration();" href="javaScript:void(0);">确认选择</a>
                </button>
            </span>
    </div>
</div>

<div class = "layui-center">
    <table id="demo" lay-filter="test"></table>
</div>



<script type="text/javascript">
    var width;
    var height;
    $(function(){
        getWidthHeight();
    });

    function getWidthHeight(){
        if (window.innerWidth)
            width = window.innerWidth;
        else if ((document.body) && (document.body.clientWidth))
            width = document.body.clientWidth;

        if (window.innerHeight)
            height = window.innerHeight;
        else if ((document.body) && (document.body.clientHeight))
            height = document.body.clientHeight;
    }


    layui.use(['table','laytpl'],function(){

        var table = layui.table,
            laytpl = layui.laytpl;
        table.render({
            elem: '#demo',
            id : 'idTest',
            url : '/depot/depotAll.do',
            /* width : 1700,
            height : 685, */
            width : width - 20,
            height : height-100,
            cols : [ [ //标题栏
                {type:'checkbox',align : 'center',width : '11%'},
                {field : 'id', title: 'ID', align: 'center', hide: true},
                {field : 'name',title : '仓库名称',width : '18%',align : 'center'},
                {field : 'depotType',title : '仓库类型',width : '18%',align : 'center', toolbar:"#depotType"},
                {field : 'foodType',title : '粮食性质',width : '18%',align : 'center', toolbar:"#typeBar"},
                {field : 'storageReal',title : '储量',width : '18%',align : 'center'},
                {field : 'foodLevel',title : '粮食等级',width : '18%',align : 'center', toolbar:"#typeBar"}
            ]],
            page : true //是否显示分页
            ,
            where: { key:"" },
            limits : [ 10, 20,50, 100 ],
            limit : 10
        });
    });

</script>
<script type="text/html" id="depotType">
    {{#  if(depotType == 0){ }}
        储备粮
    {{#  } else if(depotType == 1) { }}
        省级储备粮
    {{#  } else if (depotType == 2){ }}
        县级储备粮
    {{#  } else if (depotType == 2){ }}
        乡级储备粮
    {{#  } else{ }}
        战略储备粮
    {{# } }}
</script>

</body></html>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>大公智能仓储</title>
    <script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
    <link type="text/css" rel="stylesheet" href="/css/common.css">
    <link type="text/css" rel="stylesheet" href="/css/adminpage.css">
    <link type="text/css" rel="stylesheet" href="/css/style.css">
    <link type="text/css" rel="stylesheet" href="/layui/css/layui.css">
    <script type="text/javascript" src="/layui/layui.all.js"></script>
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
            location.href = "/getExcel.do?lian=" + lian + "&pi=" + pi;
            // window.location="http://localhost:8080/getExcel.do?lian="+lian+"&pi="+pi;
        }

        function getPdf() {
            var lian = $("#lian").val();
            var pi = $("#pi").val();
            location.href = "/getPdf.do?lian=" + lian + "&pi=" + pi;
            // window.location="http://localhost:8080/getPdf.do?lian="+lian+"&pi="+pi;
        }

    </script>
</head>
<body>
<!-- 上方菜单 -->
<#include "com/topmenu.ftl">

<div class="cm-fullpage">
    <div class="cm-center">
        <div class="cm-sitemap">
            <div class="fl navinfo">
                <span>您现在的位置：</span>
                <span><a href="#">菜单列表</a></span>
                <span>&nbsp;&gt;&nbsp;</span>
                <span>粮情检测</span>
            </div>
            <div class="clear"></div>
        </div>

        <div class="cm-center">
            <div class="cm-pagebody">
                <!-- 左侧菜单 -->
                <#include "com/leftMenu.ftl">

                <div class="fr rightbody">
                    <#include "com/center.ftl">
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="hsplit">&nbsp;</div>
</div>
<!-- 下方菜单 -->
<#include "com/downMenu.ftl">


</body>
</html>
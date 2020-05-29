<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>大公智能仓储</title>
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

    </script>

</head>
<body>
<!-- 上方菜单 -->
<#include "com/topmenu.ftl">
<div class="cm-center">
    <div class="cm-sitemap">
        <div class="fl navinfo">
            <span>您现在的位置：</span>
            <span><a href="#">菜单列表</a></span>
            <span>&nbsp;&gt;&nbsp;</span>
            <span>智能气调</span>
        </div>
        <div class="clear"></div>

    </div>

    <div class="cm-page-center">
        <#--        <div class="layui-col-md3">-->
        <#--&lt;#&ndash;            <div style="float: right"></div>&ndash;&gt;-->
        <#--            1-->
        <#--        </div>-->
        <#--        <div class="layui-col-md8">-->
        <#--           <form action="" method="post" name="">-->
        <#--                    <div class="tab_1">-->
        <#--                        <span>选择分机 :</span>-->
        <#--                        <label>-->
        <#--                            <select id="lian" name="warehouse">-->
        <#--                                <option value="chrome">1-1</option>-->
        <#--                                <option value="safari">1-2</option>-->
        <#--                                <option value="Edge">1-3</option>-->
        <#--                                <option value="firefox">1-4</option>-->
        <#--                                <option value="ie8">1-5</option>-->
        <#--                            </select>-->
        <#--                        </label>-->
        <#--                        <button class="" type="">查询刷新</button>-->
        <#--                    </div>-->
        <#--                </form>-->
        <#--        </div>-->
        <div class="cm-pagebody">
            <!-- 左侧菜单 -->
            <#include "com/N2/leftMenuN2.ftl">
            <div class="fr rightbody" style="width: 85%">
                <#include "com/N2/centerN2.ftl">
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <div class="hsplit">&nbsp;</div>
</div>
<!-- 下方菜单 -->
<#include "com/downMenu.ftl">
<script type="text/javascript">
    function buttonC(a) {
        // alert(a);
        let options = $("#lian option:selected");
        let b = options.val();
        // alert(b);
        $.ajax({
            url: "/sendModel.do?model=" + a + "&dev=" + b,
            type: "GET",
            dateType: "json",
            success: {}
        })
    }
</script>

</body>
</html>
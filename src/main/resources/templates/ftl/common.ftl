<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>layout 后台大布局 - Layui</title>
    <link rel="stylesheet" href="./layui/css/layui.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><img src="./images/logo.png" alt=""></div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a class="layui-icon layui-icon-home" href=""> 首页 </a>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img" alt="">
                    贤心
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">退了</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">

            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a  class="layui-icon layui-icon-home"  href="javascript:"> 首页  </a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:"> 首页 </a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:">设备管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:">列表一</a></dd>
                        <dd><a href="javascript:">列表二</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:">灯光设备</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:">列表一</a></dd>
                        <dd><a href="javascript:">列表二</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:">客户管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:">列表一</a></dd>
                        <dd><a href="javascript:">列表二</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:">固件管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:">列表一</a></dd>
                        <dd><a href="javascript:">列表二</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:">系统管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:">列表一</a></dd>
                        <dd><a href="javascript:">列表二</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body" style="margin-left: 1px">
        <div class="layui-tab layui-tab-brief" lay-allowClose="true" lay-filter="docDemoTabBrief" style="margin-top: -3px">
            <ul class="layui-tab-title"  id="layui-top-test1">
                <li class="layui-this layui-bg-black">首页</li>
<#--                <li>测试</li>-->
<#--                <li>测试</li>-->
<#--                <li>测试</li>-->
<#--                <li>测试</li>-->
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <!-- 内容主体区域 -->
                    <div style="padding: 5px;">
<#--                        <#include "com/center2.ftl">-->
                    </div>
                </div>
                <div class="layui-tab-item">2</div>
                <div class="layui-tab-item">3</div>
                <div class="layui-tab-item">4</div>
                <div class="layui-tab-item">5</div>
                <div class="layui-tab-item">6</div>
            </div>
        </div>

    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        <#include "com/downMenu.ftl">
    </div>
</div>
<script src="../layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
</script>
<style type="text/css">
    .layui-this{
        border-radius: 10px 10px 0 0;
    }
    #layui-top-test1 li{
        border: 1px solid #e7e7e7;
        border-radius: 10px 10px 0 0;
    }
    .layui-tab-close{
        text-align: right;
    }
</style>
</body>
</html>
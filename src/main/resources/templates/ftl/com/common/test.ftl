<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>大公智能仓储</title>
    <link type="text/css" rel="stylesheet" href=" ../../../css/common.css">
    <link type="text/css" rel="stylesheet" href="/css/common.css">
    <link type="text/css" rel="stylesheet" href=" ../../../css/adminpage.css">
    <link type="text/css" rel="stylesheet" href=" /css/adminpage.css">
    <link type="text/css" rel="stylesheet" href =" ../../../css/style.css">
    <link type="text/css" rel="stylesheet" href =" ../../../layui/css/layui.css">\
    <link type="text/css" rel="stylesheet" href =" /layui/css/layui.css">
    <script type="text/javascript" src=" ../../../layui/layui.js"></script>
    <script type="text/javascript" src=" /layui/layui.js"></script>
    <script type="text/javascript" src=" ../../../js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src=" /js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
    </script>
    <style type="text/css">

        .layui-input, .layui-textarea {
            width: 85%;
        }
        .layui-form-label {
            width: 90px;
        }
    </style>
</head>
<body>


<div style="height: 20px"></div>
<form class="layui-form" lay-filter="formSubmit">
    <div class="layui-form-item">
        <label class="layui-form-label">方案名称: </label>
        <div class="layui-input-block">
            <input type="text" id="schemeName"  name="schemeName" placeholder="请输入方案名称" autocomplete="off" class="layui-input" value="${quartz.schemeName!}">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">方案类型: </label>
        <div class="layui-input-block">
            <input type="radio" lay-filter="radio" name="schemeType" value="0" title="每周" <#if (quartz.schemeType! == '0')>checked='true'</#if> >
            <input type="radio" lay-filter="radio" name="schemeType" value="1" title="每天" <#if (quartz.schemeType! == '1')>checked='true'</#if>>
            <input type="radio" lay-filter="radio" name="schemeType" value="2" title="指定时间" <#if (quartz.schemeType! == '2')>checked='true'</#if>>
            <input type="radio" lay-filter="radio" name="schemeType" value="3" title="每隔一段时间" <#if (quartz.schemeType! == '3')>checked='true'</#if>>
        </div>
    </div>


    <div class="layui-form-item " id = "layui-radio-1" style="display: none">
        <label class="layui-form-label">星期 :</label>
        <div class="layui-input-block">
            <input type="text" id="week" name="week" placeholder="请输入星期几，如星期三: 3"  class="layui-input" value="${quartz.week!}">
        </div>
    </div>

    <div class="layui-form-item" id = "layui-radio-2" style="display: none">
        <label class="layui-form-label">指定年/月/日</label>
        <div class="layui-input-block">
            <input type="text" id="timeDate" name="timeDate" placeholder="点击选中时间" class="layui-input" value="${quartz.timeDate!}">
        </div>
    </div>

    <div class="layui-form-item" id = "layui-radio-3" style="display: none">
        <label class="layui-form-label">小时：</label>
        <div class="layui-input-block">
            <input type="text"  id="hour" name="hour" placeholder="请输入小时，如12点: 12" autocomplete="off" class="layui-input" value="${quartz.hour!}">
        </div>
    </div>

    <div class="layui-form-item" id = "layui-radio-4" style="display: none">
        <label class="layui-form-label">分钟：</label>
        <div class="layui-input-block">
            <input type="text"  id="minute" name="minute" placeholder="请输入分钟，如30分: 30" autocomplete="off" class="layui-input" value="${quartz.minute!}">
        </div>
    </div>


    <div class="layui-form-item" id = "layui-radio-5" style="display: none">
        <label class="layui-form-label">指定间隔时间: </label>
        <div class="layui-input-block">
            <select name="setTime"  lay-filter="">
                <option value="">- 请选择间隔执行时间 -</option>
                <option value="1m" <#if (quartz.setTime! == '1m')>class = "layui-this" selected = 'selected'</#if>>1分钟</option>
                <option value="10m" <#if (quartz.setTime! == '10m')>class = "layui-this" selected = 'selected'</#if>>10分钟</option>
                <option value="30m" <#if (quartz.setTime! == '30m')>class = "layui-this" selected = 'selected'</#if>>30分钟</option>
                <option value="1h" <#if (quartz.setTime! == '1h')>class = "layui-this" selected = 'selected'</#if>>1小时</option>
                <option value="3h" <#if (quartz.setTime! == '3h')>class = "layui-this" selected = 'selected'</#if>>3小时</option>
                <option value="6h" <#if (quartz.setTime! == '6h')>class = "layui-this" selected = 'selected'</#if>>6小时</option>
            </select>
        </div>
    </div>


    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注: </label>
        <div class="layui-input-block">
            <textarea placeholder="请输入内容" id="remark" name="remark" class="layui-textarea">${quartz.remark!}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <input type="hidden" name="type" value="grain">
            <input type="hidden" name="id" value="${quartz.id!}">
            <button class="layui-btn" lay-submit lay-filter="*">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>

</form>

<script>

    $(document).ready(function(){
        layui.use(['form', 'laydate'],function(){
            var form = layui.form,
                laydate = layui.laydate,
                $ = layui.$;

            //执行一个laydate实例
            laydate.render({
                elem: '#timeDate' //指定元素
                ,format: 'yyyy-MM-dd' //可任意组合

            });


            form.on('radio(radio)', function(data){

                document.getElementById("layui-radio-1").style.display="none";
                document.getElementById("layui-radio-2").style.display="none";
                document.getElementById("layui-radio-3").style.display="none";
                document.getElementById("layui-radio-4").style.display="none";
                document.getElementById("layui-radio-5").style.display="none";

                if(data.value === '0'){
                    document.getElementById("layui-radio-1").style.display="block";
                    document.getElementById("layui-radio-3").style.display="block";
                    document.getElementById("layui-radio-4").style.display="block";
                }else if(data.value === '1'){
                    document.getElementById("layui-radio-3").style.display="block";
                    document.getElementById("layui-radio-4").style.display="block";
                }else if(data.value === '2'){
                    document.getElementById("layui-radio-2").style.display="block";
                    document.getElementById("layui-radio-3").style.display="block";
                    document.getElementById("layui-radio-4").style.display="block";
                }else if(data.value === '3'){
                    document.getElementById("layui-radio-5").style.display="block";
                }else{
                    alert("提示: 选择出错 !");
                }
            });


            form.on('submit(*)', function(data){

                //console.log(data.field);

                var url = "/job/updateQuartz.do";

                if("${quartz.remark!}" ==="" || "${quartz.remark!}" === undefined ){
                    url = "/job/saveQuartz.do";
                }

                $.ajax({
                    url: url,
                    data: data.field,
                    type: "POST",
                    dataType: "json",
                    success: function(data) {
                        if (null == data){
                            layer.msg('系统异常!');
                            return false;
                        }
                        if(data.flag === "00"){
                            layer.msg('存在错误操作!');
                            return false;
                        }
                        if(data.flag === "01"){
                            layer.msg('成功!');
                        }
                    },
                    error: function(){
                        info.innerText = "提示: 执行错误！！";
                    }
                });

                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);//关闭当前页
                window.parent.location.replace("/job/Quartz.html")//刷新父级页面

            });

            var htmlElements = undefined;
            var htmlElement = document.getElementsByName("schemeType");
            for (var i=0; i<htmlElement.length; i++) {
                if (htmlElement[i].checked) {
                    //alert(htmlElement[i].value);
                    htmlElements = htmlElement[i].value;
                }
            }

            if(htmlElements === '0'){
                document.getElementById("layui-radio-1").style.display="block";
                document.getElementById("layui-radio-3").style.display="block";
                document.getElementById("layui-radio-4").style.display="block";
            }else if(htmlElements === '1'){
                document.getElementById("layui-radio-3").style.display="block";
                document.getElementById("layui-radio-4").style.display="block";
            }else if(htmlElements === '2'){
                document.getElementById("layui-radio-2").style.display="block";
                document.getElementById("layui-radio-3").style.display="block";
                document.getElementById("layui-radio-4").style.display="block";
            }else if(htmlElements === '3'){
                document.getElementById("layui-radio-5").style.display="block";
            }

        });
    });






</script>


</body></html>
<div class="fl leftbody">
    <div class="titlediv">
        <div class="fl tname">菜单列表</div>
        <div class="clear"></div>
    </div>
    <div class="menusdiv">
        <div class="menus padding">
            <div class="menu" id="zhinengqitiao" style="cursor:pointer;">
                <div class="fl icos">
                    <div class="ico"></div>
                </div>
                <div class="fl mtext">智能气调</div>
                <div class="clear"></div>
            </div>

            <div class="menu" id="shoudongceshi" style="cursor:pointer;">
                <div class="fl icos">
                    <div class="ico"></div>
                </div>
                <div class="fl mtext">手动测试</div>
                <div class="clear"></div>
            </div>

            <div class="menu" id="qiticeshi" style="cursor:pointer;">
                <div class="fl icos">
                    <div class="ico"></div>
                </div>
                <div class="fl mtext">气体测试</div>
                <div class="clear"></div>
            </div>

            <div class="menu" id="qimijiance" style="cursor:pointer;">
                <div class="fl icos">
                    <div class="ico"></div>
                </div>
                <div class="fl mtext">气密检测</div>
                <div class="clear"></div>
            </div>

            <div class="menu" id="xitongshezhi" style="cursor:pointer;">
                <div class="fl icos">
                    <div class="ico"></div>
                </div>
                <div class="fl mtext">系统设置</div>
                <div class="clear"></div>
            </div>

            <div class="menu" id="shebeizhuce" style="cursor:pointer;">
                <div class="fl icos">
                    <div class="ico"></div>
                </div>
                <div class="fl mtext">设备注册</div>
                <div class="clear"></div>
            </div>

            <div class="menu" id="guanyuwomen" style="cursor:pointer;">
                <div class="fl icos">
                    <div class="ico"></div>
                </div>
                <div class="fl mtext">关于我们</div>
                <div class="clear"></div>
            </div>


            <script type="text/javascript">

                $(document).ready(function () {
                    try {
                        $('.menus .menu').click(function () {
                          let choosed =  $(this).attr("id");
                          // alert(choosed);console.log($("."+choosed))
                            $("."+choosed).siblings().css("display","none");
                            $("."+choosed).css("display","inline");
                        });

                    } catch (e) {
                    }
                });
            </script>
        </div>
    </div>
</div>


    /**
     * 客户信息JS
     */
    $(document).ready(function(){
        //初始载入数据
        loadData();
        //刷新数据
        reload();
    });

    //初始化
    function loadData()
    {
        var url = "../app/getQt";
        var param = null;
        var id = "id";
        var query = location.search.substring(1);
        var values= query.split("&");
        for(var i = 0; i < values.length; i++) {
            var pos = values[i].indexOf('=');
            if (pos == -1) continue;
            var paramname = values[i].substring(0,pos);
            var value = values[i].substring(pos+1);
            if(id==paramname){
                param = value.replace("img","");
            }
        }
        //查询需要载入的数据
        getDateGrid();
        getData(url,param);
    };

    function getDateGrid() {
        $('#wu-datagrid').datagrid({
            loadFilter:pagerFilter,
            rownumbers:true,
            singleSelect:false,
            pageSize:20,
            pagination:true,
            multiSort:true,
            fitColumns:true,
            fit:true,
            autoRowHeight:true,
            columns:[[
                // {checkbox:true},
                // { field:'id',title:'序号',width:100,sortable:true},
                { field:'bianhao',title:'编号',width:100,sortable:true},
                { field:'zhanhao',title:'站号',width:100},
                { field:'moshi',title:'模式',width:100},
                { field:'qiya',title:'气密检查量程(pa)',width:100},
                { field:'cdl',title:'仓内外压差(pa)',width:100},
                { field:'nongdu',title:'氮气浓度(%)',width:100},
                { field:'begintime',title:'开始时间',width:100},
                // { field:'endtime',title:'结束时间',width:100}
            ]]
        });
    }



    function getData(url,param)
    {
        $.ajax({
            url: url,
            type: "POST",
            dataType:"json",
            contentType:'application/json;charset=UTF-8',
            // data: JSON.stringify(param),
            data:param,
            timeout: 20000,
            success : function (data) {
                console.log("气调信息"+data.state);
                    $("#cangfang").html(data.data[0]+"号仓");
                    $('#moshi').html(data.data[1]);
                    $("#nongdu").html(data.data[2]+"%");
                    $('#qiya').html(data.data[3]+"pa");
                    $('#bsqyali').html(data.data[4]+"pa");
                    $('#bsqtime').html(data.data[5]+"s");
                    $('#iresult').html(data.data[6]);
                    $('#status').html(data.data[7]);
                    $('#cang').html(data.data[0]);
                    $("#img").attr("src", data.data[8]);
                 reLodadDateGrid(data.data1);
            },
            error : function (data){
            }
        });
    }

    /*
     * 组装dataGrid数据
     */
    function reLodadDateGrid(data)
    {
        var values = [];
         for ( var i = 0; i <data.length; i++) {
            var a = {
                // 'id' : data[i][0],
                'bianhao' : data[i][0],
                'zhanhao' : data[i][1],
                'moshi' : data[i][2],
                'qiya' : data[i][3],
                'cdl' : data[i][4],
                'nongdu' : data[i][5],
                'begintime' : data[i][6],
                // 'endtime' : data[i][0],
            };

            values.push(a);
         }
        $('#wu-datagrid').datagrid('loadData', values);
    }


    /**
     * 刷新记录
     */
    function reload()
    {
        $("#customer-reload").click(function(){
            $.messager.confirm('信息提示','确定要刷新？', function(result)
            {
                loadData();
            });
        });
    }

    /**
     * 分页
     * @param data
     * @returns {}
     */
    function pagerFilter(data) {
        if (typeof data.length == 'number' && typeof data.splice == 'function') {// is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage: function (pageNum, pageSize) {
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                        pager.pagination('refresh', {pageNumber: pageNum, pageSize: pageSize});
                dg.datagrid('loadData', data);
            }
        });
        if (!data.originalRows) {
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }



    //发送命令
    function yuduhttpsendorder(canghao, model) {
        uurl = '../app/sendModel.do';
        var sendok = 0;
        var statushtml;
        $.ajax({
                type: 'get',
                url:  uurl,
                data: "depotId=" + canghao + "&model=" + model,
                dataType: 'json',
                success: function (data2) {
                    console.log("于都气调：" + data2);
                }            }
        )
        return sendok;
    };


    function zhidanji(controller){

        $.ajax({
                type: 'get',
                url:  "../app/N2DevControler",
                data: "controller=" + controller,
                dataType: 'json',
                success: function (data) {
                    alert(data.message);

                }
            }
        )
    }
(function() {
	layui.use(['table','element','layer'], function() {
		var $ = layui.jquery
		var table = layui.table;
		table.render({
			elem : '#tableDepot',
			height : 472,
			url : './page/main/depotList',
			limit : 10,
			page : true,
			text:"没有获取到数据……",
			cols : [ [ {
				field : 'name',
				title : '仓库名称',
				align : 'center'
			}, {
				field : 'depotType',
				title : '仓库类型',
				align : 'center'
			}, {
				field : 'foodType',
				title : '粮食性质',
				align : 'center'
			}, {
				field : 'foodVariety',
				title : '粮食品种',
				align : 'center'
			}, {
				field : 'foodLevel',
				title : '粮食登记',
				align : 'center'
			}, {
				field : 'storageReal',
				title : '目前储量',
				align : 'center',
				templet: function(d){
					if(d.storageReal){
						return d.storageReal+"KG"
					}else{
						return "";
					}
				}
			}, {
				field : 'foodYear',
				title : '粮食年份',
				align : 'center'
			} ] ]
		});
		
		table.render({
			elem : '#tableWarn',
			height : 482,
			url : './page/main/warnList',
			limit : 10,
			page : true,
			text:"没有获取到数据……",
			cols : [[ {
				field : 'warnInfo',
				title : '警告内容',
//				align : 'center'
			}, {
				field : 'strWarnDate',
				title : '警告时间',
				align : 'center',
				width : '135'
			}]]
		});
		
		var element = layui.element;
		element.on('tab(tabChart)', function(data) {

		});
		
		var layer = layui.layer;
		$('#setQuick').on('click', function(){
			layer.open({
				  type: 2,
				  title: '配置个人快捷方式',
				  shadeClose: true,
				  shade: 0.8,
				  area: ['50%', '80%'],
				  content: './com.ld.depot.common.ConfigQuick.d'
			});
		});
		
		
		//获取快捷方式列表
		// $.get("./page/main/quickList",function(result){
		// 	if(null == result) return null;
		// 	var temp = null;
		// 	layui.each(result, function(index,quick){
		// 		temp = $("#quickList-"+index);
		// 		temp.attr("onClick","newTab2("+JSON.stringify(quick)+")");
		// 		temp.find("img").attr("src",quick.bigIcon);
		// 		temp.find("cite").text(quick.name)
		// 	});
		// },"json");
		
		//首页中需要的数据
		// $.get("./page/main/getNum",function(result){
		//
		// 	$("#task_0").text(result.task_0);
		// 	$("#task_1").text(result.task_1);
		// 	$("#task_2").text(result.task_2);
		// 	$("#task_3").text(result.task_3);
		//
		// 	var temp = $("#control_0");
		// 	temp.attr("lay-percent",result.control_0);
		// 	temp.css("width",result.control_0);
		// 	temp.find("span").text(result.control_0);
		//
		// 	temp = $("#control_1");
		// 	temp.attr("lay-percent",result.control_1);
		// 	temp.css("width",result.control_1);
		// 	temp.find("span").text(result.control_1);
		//
		// 	temp = $("#control_2");
		// 	temp.attr("lay-percent",result.control_2);
		// 	temp.css("width",result.control_2);
		// 	temp.find("span").text(result.control_2);
		//
		// },"json");
		
		//首页企业信息
		// $.get("./page/main/companyIntro",function(result){
		// 	$("#company_intro").html(result.introduce);
		// },"json");
		
		//开启新的tab
		newTab = function(url,caption,iconClass){
			window.parent.openUrlInFrameTab2(url,caption,iconClass,false);
		};
		
		//开启新的tab
		newTab2 = function(data){
			newTab(data.url,data.name,null);
		}
		
		//待办任务
		openTask = function(index){
			if(index ==0) newTab("com.ld.depot.bdf2.Message.d","站内信息","fa fa-envelope-o");
			if(index ==1) newTab("com.ld.depot.oa.manage.PlanInfo.d","计划信息","fa fa-flag-checkered");
			if(index ==2) newTab("com.ld.depot.oa.manage.PlanDepotIn.d","入库安排",null);
			if(index ==3) newTab("com.ld.depot.oa.manage.PlanDepotOut.d","出库安排",null);
		};
		
		
		/******************时钟*******************/
		
		// 创建两个变量，一个数组中的月和日的名称
		var monthNames = [ "1月", "2月", "3月", "4月", "5月","6月", "7月", "8月", "9月", "10月", "11月", "12月" ]; 
		var dayNames= ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]

		var newDate = new Date();
		// 提取当前的日期从日期对象
		newDate.setDate(newDate.getDate());
		//输出的日子，日期，月和年
		$('#Date').html(newDate.getFullYear() + "年" + monthNames[newDate.getMonth()] + "" + newDate.getDate() + "日  " + dayNames[newDate.getDay()]);

		setInterval( function() {
			// 创建一个对象，并提取newDate（）在访问者的当前时间的秒
			var seconds = new Date().getSeconds();
			//添加前导零秒值
			$("#sec").html(( seconds < 10 ? "0" : "" ) + seconds);
		},1000);
		
		setInterval( function() {
			// 创建一个对象，并提取newDate（）在访问者的当前时间的分钟
			var minutes = new Date().getMinutes();
			// 添加前导零的分钟值
			$("#min").html(( minutes < 10 ? "0" : "" ) + minutes);
	    },1000);
		
		setInterval( function() {
			var hours = new Date().getHours();
			// 添加前导零的小时值
			$("#hours").html(( hours < 10 ? "0" : "" ) + hours);
	    }, 1000);
		/******************时钟结束*******************/

	});
}).call(this);
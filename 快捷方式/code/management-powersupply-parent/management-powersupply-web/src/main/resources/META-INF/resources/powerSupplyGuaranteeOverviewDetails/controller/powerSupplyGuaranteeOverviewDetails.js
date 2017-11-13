define(['akui', 'util', 'exportData', 'akapp', 'echarts','echartsWrapper'],
		function( ak ,myak,exak,akapp,echarts){
	var me ={};
	ak.parse();
	var m = 1;//全局变量为了使第二次点击弹窗的时候，它的值然后是2,而不是重新复制为1 ，又会重新滚动了。出现bug
	var n = 1;
	
	me.tasks = [];

	// 	保电总览跳转到保电概览添加返回按钮
	var paramUrl = {
			id: "89f68f743add11e79d900242ac120007",
			nodeType: "menu",
			text: "保电总览",
			url: akapp.appname+"/powerSupplyGuaranteeOverviewTotal/index.jsp"	
	}
	$("body").append('<div id="ak_prevButton" class="ak-prevPage ak-active" style="position:absolute;bottom:0;right:0;z-index:1050"></div>');
	$("#ak_prevButton").on("click",function(){
		_skipPage(paramUrl);
	});
	
	function _skipPage(item){
		top.ak("mainMenu").fire("itemclick", {
            item :item,
            isLeaf : true,
            sender: top.ak("mainMenu") 
        });
	}		
			
	// 获取URL参数
	var params = JSON.parse(decodeURIComponent(window.location.search.split("?id=")[1]));
	me.taskId = params.id;
	me.theme = params.theme;
	me.ensureLevel = params.ensureLevel;
	me.ensureDetails = params.ensureDetails;
	me.startTime = params.startTime;
	me.kssj = me.startTime.substr(0, 10);
	me.endTime = params.endTime;
	me.jssj = me.endTime.substr(0, 10);
	me.introPictureId = params.introPicture;	// 简介图
	if(params.introPictureNames == null){
		me.introPictureName = "";
	}else{
		me.introPictureName = params.introPictureNames.split(",")[0];
	}
	
	me.ensureMapId = params.ensureMap;			// 供电图
	if(params.ensureMapNames == null){
		me.ensureMapName = "";
	}else{
		me.ensureMapName = params.ensureMapNames.split(",")[0];
	}

	me.ensurePlan = "";		// 组织机构-保电方案
	
//	me.taskId = "2fd46cce-87d8-48d8-8cf8-620fe7f8082c";
//	me.theme = "高考保电";
//	me.ensureLevel = "一级";
//	me.ensureDetails = "江苏省高校保证全部供电";
//	me.startTime = "2017-06-06 09:00";
//	me.kssj = "2017-06-06";
//	me.endTime = "2017-07-15 09:00";
//	me.jssj = "2017-07-15";
//	me.introPictureId = "9da63cde-36c2-464a-84c8-316451d4224a";	// 简介图
//	me.introPictureName = "Chrysanthemum.jpg";
//	me.ensureMapId = "924470bd-835e-47db-8ef6-9d1ab2b48f73";	// 供电图
//	me.ensureMapName = "Desert.jpg";
//	me.ensurePlan = "";		// 组织机构-保电方案
	
	me.organization = [];
	me.orgId = "";
	
	me.phases = null;
	
	var userGrid = ak("userGrid");
	var stationLineGrid = ak("stationLineGrid");
	var personGrid = ak("personGrid");
	var carGrid = ak("carGrid");
	var pointGrid = ak("pointGrid");
	var hotelGrid = ak("hotelGrid");
	var hospitalGrid = ak("hospitalGrid");
	
	me.userTab = "1";	// "1":特级; "2":一级; "3":二级; "4":三级;
	me.solTab = "02";	// sol: Station Or Line; "01":线路; "02":电站
	var LineId = null;
	var stationId = null;
	var stationOrLineId = null;
	
	me.personChartType = ""; // 饼图人员类型 变电、输电、换流
	me.carChartType = ""; // 饼图车辆类型
	me.render = function(){
		$("#theme")[0].innerHTML = me.theme;
		$("#ensureLevel")[0].innerHTML = me.ensureLevel;
		
		getOrganization();		// 获取组织机构
		orgClick();				// 保电组织点击事件绑定
		
		initClickBind();		// 初始化点击绑定事件
		getPhases();			// 获取保电任务的各保电阶段信息
		getDate();				// 日历
		monthChangeBind();		// 改变日历月份点击事件
		
		getUserLevel();			// 获取用户保电级别统计
		
		initChartData();		// 获取chart图数据
		initWeatherInfo();		// 获取气象信息
		initDispatchChart();	// 调度信息曲线图
		
		getResourceCount();			// 获取资源保障的数量（人员、车辆、驻点、宾馆、医院）
		getRunningContentCount();	// 获取运行内容的数量（故障、缺陷）
		
//		_initOverLoadCount() // 重过载数量统计
//		_initMonitoringCount() // 在线监测数量统计
//		_initWeatherCount(); // 气象预报数量统计
//		_initEnvironmentCount(); // 环境预报警数量统计
//		_initGridRiskCount() // 电网风险预报警数量统计
	}
	
	/**
	  * 展示Tips消息提示框
	  */
	 function showTips(type, content){
		 ak.showTips({
           content: content,
           state: type,
           x: 'center',
           y: 'center',
           timeout: 4000
       });
	 }
	
	// 获取组织机构
	function getOrganization(){
		var p_path = "/powerSupplyGuaranteeOverviewDetails/getOrganizationResult";	// 获取组织机构树
		var params = {"taskId":me.taskId};
    	myak.send(p_path,'GET',params,false,function(result){
    		me.organization = result;
		});
    	
    	// 加载总部信息
    	me.headquartersId = "";
    	me.headquartersName = "总部";
    	me.hqOrgBriefText = "";
    	me.hqDutyPerson = "";
    	me.hqDutyPersonPhoto = "";
    	me.hqDutyPersonPhotoNames = "";
    	me.hqPhoneNumber = "";
    	me.hqContactNumber = "";
    	
    	var l = me.organization.length;
    	for(var i=0; i<l; i++){
    		if(me.organization[i].superOrgID == null){
    			me.headquartersId = me.organization[i].id;
    			me.headquartersName = me.organization[i].orgName;
    			me.hqOrgBriefText = me.organization[i].orgIntro;
    			me.hqDutyPerson = me.organization[i].dutyPerson;
    			me.hqDutyPersonPhoto = me.organization[i].dutyPersonPhoto;
    			me.hqDutyPersonPhotoNames = me.organization[i].dutyPersonPhotoNames;
    			me.hqPhoneNumber = me.organization[i].phoneNumber;
    			me.hqContactNumber = me.organization[i].contactNumber;
    			me.hqEnsurePlan = me.organization[i].ensurePlan;
    			me.ensurePlan = me.hqEnsurePlan;
    			break;
    		}
    	}
    	
    	$("#headquarters")[0].innerHTML = me.headquartersName;
    	$("#briefText")[0].innerHTML = me.ensureDetails;
    	
    	if(me.introPictureId == "" || me.introPictureId == null){
    		$("#briefPhoto").css("background-image","url(res/img/no-jianjietu-black.png)");
    	}else{
//    		$("#briefPhoto").css("background-image","url(http://10.144.15.216/mcsas-dfs/files/download?filePath=" +
    		$("#briefPhoto").css("background-image","url(/mcsas-dfs/files/download?filePath=" +
        			"/user/mcsas/electricityEnsure/"+me.introPictureId+"/"+me.introPictureName+")");
    	}
    	
    	$("#orgName")[0].innerHTML = me.headquartersName;
    	$("#orgBriefText")[0].innerHTML = me.hqOrgBriefText;
    	$("#dutyPerson")[0].innerHTML = me.hqDutyPerson;
    	
    	setTimeout(function(){
    		if(me.hqDutyPersonPhoto == "" || me.hqDutyPersonPhoto == null){
    			$("#dutyPersonPhoto").css("background-image","url(res/img/no-person-black.png)");
    		}else{
//    			$("#dutyPersonPhoto").css("background-image","url(http://10.144.15.216/mcsas-dfs/files/download?filePath=" +
    			$("#dutyPersonPhoto").css("background-image","url(/mcsas-dfs/files/download?filePath=" +
            			"/user/mcsas/electricityEnsureOrg/"+me.hqDutyPersonPhoto+"/"+me.hqDutyPersonPhotoNames+")");
    		}
    	}, 1000);
    	
    	$("#phoneNumber")[0].innerHTML = me.hqPhoneNumber;
    	$("#contactNumber")[0].innerHTML = me.hqContactNumber;
    	
    	// 加载二级组织信息
    	me.orgCount = 1;	// 二级保电组织个数
    	for(var i=0; i<l; i++){
    		if(me.organization[i].superOrgID == me.headquartersId){
    			var div = $("<div id='"+me.organization[i].id+"' style='height:32px; margin-top:30px; margin-left:20px; " +
    					"float:left; background:rgba(255,255,255,0.2); cursor:pointer;'>" +
								"<div id='org"+me.orgCount+"' style='text-align:center; line-height:32px; float:left;'>" + 
									"<span style='font-size:16px; color:white; padding:0 20px; display:inline-block; max-width: 150px;" +
									"text-overflow:ellipsis; white-space:nowrap; overflow:hidden;'>"+me.organization[i].orgName+"</span>" +
								"</div>" +
								"<div id='img"+me.orgCount+"' style='width:30px; text-align:center; line-height:32px; float:right;'>" + 
									"<img src='./res/img/youjiantou-xi.png'>" +
								"</div>" +
							"</div>");
    			
    			$("#orgList").append(div);
    			me.orgCount++;
    		}
    	}
	}
	
	// 保电组织点击事件绑定
	function orgClick(){
		// 总部
		$("#hq").on("click", function(){
			$("#briefText")[0].innerHTML = me.ensureDetails;
	    	$("#orgName")[0].innerHTML = me.headquartersName;
	    	$("#orgBriefText")[0].innerHTML = me.hqOrgBriefText;
	    	$("#dutyPerson")[0].innerHTML = me.hqDutyPerson;
	    	$("#phoneNumber")[0].innerHTML = me.hqPhoneNumber;
	    	$("#contactNumber")[0].innerHTML = me.hqContactNumber;
	    	me.ensurePlan = me.hqEnsurePlan;
	    	
	    	if(me.hqDutyPersonPhoto == "" || me.hqDutyPersonPhoto == null){
	    		$("#dutyPersonPhoto").css("background-image","url(res/img/no-person-black.png)");
	    	}else{
//	    		$("#dutyPersonPhoto").css("background-image","url(http://10.144.15.216/mcsas-dfs/files/download?filePath=" +
	    		$("#dutyPersonPhoto").css("background-image","url(/mcsas-dfs/files/download?filePath=" +
	        			"/user/mcsas/electricityEnsureOrg/"+me.hqDutyPersonPhoto+"/"+me.hqDutyPersonPhotoNames+")");
	    	}
	    	
	    	hideThirdOrgList();
	    	
	    	me.orgId = "";
	    	getUserLevel();
	    	getResourceCount();
	    	initChartData();
	    	getUserInfo(me.userTab);
	    	getStationLineInfo(me.solTab);
	    	getPersonInfo();
	    	getCarInfo();
	    	getPointInfo();
	    	getRunningContentCount();
		});
		
		// 二级组织机构
		for(var i=1; i<=me.orgCount; i++){
			// 组织名称点击事件
			$("#org"+i).on("click", function(e){
				hideThirdOrgList();
				
				me.orgId = e.target.parentNode.parentNode.id;
				var l = me.organization.length;
		    	for(var i=0; i<l; i++){
		    		if(me.organization[i].id == me.orgId){
		    			$("#orgName")[0].innerHTML = me.organization[i].orgName;
		    			$("#orgBriefText")[0].innerHTML = me.organization[i].orgIntro;
		    			$("#dutyPerson")[0].innerHTML = me.organization[i].dutyPerson;
		    			$("#phoneNumber")[0].innerHTML = me.organization[i].phoneNumber;
		    			$("#contactNumber")[0].innerHTML = me.organization[i].contactNumber;
		    			me.ensurePlan = me.organization[i].ensurePlan;
		    			
		    			if(me.organization[i].dutyPersonPhoto == "" || me.organization[i].dutyPersonPhoto == null){
		    				$("#dutyPersonPhoto").css("background-image","url(res/img/no-person-black.png)");
		    			}else{
//		    				$("#dutyPersonPhoto").css("background-image","url(http://10.144.15.216/mcsas-dfs/files/download?filePath=" +
		    				$("#dutyPersonPhoto").css("background-image","url(/mcsas-dfs/files/download?filePath=" +
			            			"/user/mcsas/electricityEnsureOrg/"+me.organization[i].dutyPersonPhoto+"/"+me.organization[i].dutyPersonPhotoNames+")");
		    			}
		    			break;
		    		}
		    	}
		    	
		    	getUserLevel();
		    	getResourceCount();
		    	initChartData();
		    	getUserInfo(me.userTab);
		    	getStationLineInfo(me.solTab);
		    	getPersonInfo();
		    	getCarInfo();
		    	getPointInfo();
		    	getRunningContentCount();
			});
			
			// 组织名称左侧箭头图片点击事件
			me.imgId = "";	// 上次点击的箭头图片的ID
			$("#img"+i).on("click", function(e){
				$("#thirdOrgUl").children().remove();	// 先清空原三级组织机构数据
				for(var i=1; i<=me.orgCount; i++){		// 将所有二级组织机构箭头图片换成朝右的图片
					$("#img"+i+" img").attr({src:"./res/img/youjiantou-xi.png" });
				}
				
				var orgDiv = $("#"+e.currentTarget.id).parent()[0];		// 获取箭头图片所在的组织机构DIV
				var orgId = orgDiv.id;									// 获取箭头图片所在的组织机构ID
				var l = me.organization.length;
				var haveChildren = false;			// 是否有三级组织机构
		    	for(var i=0; i<l; i++){		// 添加三级组织机构列表
		    		if(me.organization[i].superOrgID == orgId){
		    			var li = $("<li id='"+me.organization[i].id+"' style='cursor:pointer;'>" +
		    					"<div style='text-overflow: ellipsis; white-space: nowrap; overflow: hidden;'>" +
		    					me.organization[i].orgName+"</div></li>");
		    			$("#thirdOrgUl").append(li);
		    			haveChildren = true;
		    		}
		    	}
		    	
		    	if(haveChildren){		// 有三级组织机构
		    		if($("#thirdOrgList").hasClass("show")){	// 已经展开
						if(me.imgId == e.currentTarget.id){		// 点击的是相同的组织机构
							hideThirdOrgList();
						}else{		// 点击的是不同的组织机构
							$("#thirdOrgList").removeClass("hidden");
							$("#thirdOrgList").addClass("show");
							$("#thirdOrgList").css({"width":orgDiv.offsetWidth, "left":orgDiv.offsetLeft, "top":"-36px"});
							$("#"+e.currentTarget.id+" img").attr({src:"./res/img/down-w.png" });
						}
					}else{	// 未展开
						$("#thirdOrgList").removeClass("hidden");
						$("#thirdOrgList").addClass("show");
						$("#thirdOrgList").css({"width":orgDiv.offsetWidth, "left":orgDiv.offsetLeft, "top":"-36px"});
						$("#"+e.currentTarget.id+" img").attr({src:"./res/img/down-w.png" });
					}
		    		
		    		var liOrg = $("#thirdOrgUl").children();
					var length = liOrg.length;
					for(var i=0; i<length; i++){
						$("#"+liOrg[i].id).on("click", function(e){		// 三级组织机构点击事件
							me.orgId = e.currentTarget.id;
							var l = me.organization.length;
							for(var i=0; i<l; i++){
					    		if(me.organization[i].id == me.orgId){
					    			$("#orgName")[0].innerHTML = me.organization[i].orgName;
					    			$("#orgBriefText")[0].innerHTML = me.organization[i].orgIntro;
					    			$("#dutyPerson")[0].innerHTML = me.organization[i].dutyPerson;
					    			$("#phoneNumber")[0].innerHTML = me.organization[i].phoneNumber;
					    			$("#contactNumber")[0].innerHTML = me.organization[i].contactNumber;
					    			me.ensurePlan = me.organization[i].ensurePlan;
					    			
					    			if(me.organization[i].dutyPersonPhoto == "" || me.organization[i].dutyPersonPhoto == null){
					    				$("#dutyPersonPhoto").css("background-image","url(res/img/no-person-black.png)");
					    			}else{
//					    				$("#dutyPersonPhoto").css("background-image","url(http://10.144.15.216/mcsas-dfs/files/download?filePath=" +
					    				$("#dutyPersonPhoto").css("background-image","url(/mcsas-dfs/files/download?filePath=" +
						            			"/user/mcsas/electricityEnsureOrg/"+me.organization[i].dutyPersonPhoto+"/"+me.organization[i].dutyPersonPhotoNames+")");
					    			}
					    			break;
					    		}
					    	}
							
							getUserLevel();
							getResourceCount();
							initChartData();
							getUserInfo(me.userTab);
							getStationLineInfo(me.solTab);
							getPersonInfo();
							getCarInfo();
							getPointInfo();
							getRunningContentCount();
						});
					}
		    	}else{		// 无三级组织机构
		    		hideThirdOrgList();
		    	}
				
				me.imgId = e.currentTarget.id;
			});
		}
	}
	
	// 隐藏三级组织机构
	function hideThirdOrgList(){
		$("#thirdOrgList").removeClass("show");
		$("#thirdOrgList").addClass("hidden");
		for(var i=1; i<=me.orgCount; i++){		// 将所有二级组织机构箭头图片换成朝右的图片
			$("#img"+i+" img").attr({src:"./res/img/youjiantou-xi.png" });
		}
	}
	
	// 加载GIS地图
	function initGis(){
		 setTimeout(function(){
			if(myIframe_2D.window.Gis2d_dwxxLayer){ // 初始化基础图层
				myIframe_2D.window.Gis2d_dwxxLayer();
			}
			initGis();
		}, 100); 
	}
	
	/**
	 * 获取保电任务的各保电阶段数据
	 */
	function getPhases(){
		var url = "/powerSupplyGuaranteeOverviewDetails/getElectricityEnsurePhaseResult";
		var params = {"taskId":me.taskId};
		myak.send(url,'GET',params,false,function(result){
			if(result.length > 0){
				me.phases = result;
			}
		});
	}
	
	/*
	 * 获取时间
	 */
	function getDate() {
		// 获得时间
		var date = new Date();
		me.year = date.getFullYear();
		me.month = date.getMonth()+1;
		me.today = date.getDate();
		
		// 第一个日历的年、月
		me.yearFirst = parseInt(me.kssj.split("-")[0]);
		me.monthFirst = parseInt(me.kssj.split("-")[1]);
		
		me.yearSecond = me.yearFirst;
		me.monthSecond = me.monthFirst+1;
		if(me.monthFirst == 12){
			me.yearSecond = me.yearFirst+1;
			me.monthSecond = 1;
		}
		
		initCalendar("calendarFirst", "dateFirst", "dayFirst", me.yearFirst, me.monthFirst, me.today, me.kssj, me.jssj);
		initCalendar("calendarSecond", "dateSecond", "daySecond", me.yearSecond, me.monthSecond, me.today, me.kssj, me.jssj);
		addPhases();
	}

	/*
	 * 初始化日历表
	 */
	function initCalendar(calendarId, dateId, dayId, year, month, day, kssj, jssj) {
		$("#"+calendarId).children().remove();	// 清除日历表
		$("#"+dateId)[0].innerHTML = year + "年" + month + "月";
		var tr = "";
		for(var i=0; i<6; i++) {
			var td = "";
			for(var j=0; j<7; j++) {
				td += "<td class='align-c' id='" + dayId + (i*7+(j+1)) + "'></td>";
			}
			tr += "<tr>" + td + "</tr>";
		}
		var table = $("<table style='width:100%; height:100%;'><tbody>" + tr + "</tbody></table>");
		$("#"+calendarId).append(table);
		
		var myDate = new Date(year, (month-1), 1);
		var weekday = myDate.getDay();	// 该月的第一天是星期几
		var today = weekday;
		if(weekday == 0) {
			today = weekday = 7;
		}
		
		var date = new Date(year, month, 0);
		var dayCount = date.getDate();		//该月的天数
		
		// 开始时间
		var yearKSSJ = parseInt(kssj.split("-")[0]);
		var monthKSSJ = parseInt(kssj.split("-")[1]);
		var dayKSSJ = parseInt(kssj.split("-")[2]);
		
		// 结束时间
		var yearJSSJ = parseInt(jssj.split("-")[0]);
		var monthJSSJ = parseInt(jssj.split("-")[1]);
		var dayJSSJ = parseInt(jssj.split("-")[2]);
		var rq = "";
		var rqCSS = {"color":"white", "background":"#34c0e2", "border-radius":"5px"};
		
		for(var k=1; k<=(dayCount+today-1); k++) {
			if(k <= dayCount) {
				$("#" + dayId + (weekday++))[0].innerHTML = k;
			}
			
			rq = $("#" + dayId + k)[0].innerHTML;
			
//			if(yearKSSJ == yearJSSJ) {	// 开始时间与结束时间同年
//				if(year >= yearKSSJ && year <= yearJSSJ && month >= monthKSSJ && month <= monthJSSJ) {
//					if(month == monthKSSJ || month == monthJSSJ) {	// 日历选择的是开始时间的月份或结束时间的月份
//						if(monthKSSJ == monthJSSJ) {	// 开始时间与结束时间的月份相同
//							if(rq != "" && parseInt(rq) >= dayKSSJ && parseInt(rq) <= dayJSSJ) {
//								$("#" + dayId + k).css(rqCSS);
//							}
//						} else {	// 开始时间与结束时间的月份不相同
//							if(month == monthKSSJ) {	// 日历选择的是开始时间的月份
//								if(rq != "" && parseInt(rq) >= dayKSSJ) {
//									$("#" + dayId + k).css(rqCSS);
//								}
//							} else {	// 日历选择的是结束时间的月份
//								if(rq != "" && parseInt(rq) <= dayJSSJ) {
//									$("#" + dayId + k).css(rqCSS);
//								}
//							}
//						}
//					} else {	// 日历选择的是介于开始时间和结束时间的月份
//						if(rq != "") {
//							$("#" + dayId + k).css(rqCSS);
//						}
//					}
//				}
//			} else {	// 开始时间与结束时间不同年
//				if(year == yearKSSJ) {	// 日历选择的是开始时间的年份
//					if(month == monthKSSJ) {	// 日历选择的是开始时间的月份
//						if(rq != "" && parseInt(rq) >= dayKSSJ) {
//							$("#" + dayId + k).css(rqCSS);
//						}
//					} else if(month > monthKSSJ) {	// 日历选择的是大于开始时间的月份
//						if(rq != "") {
//							$("#" + dayId + k).css(rqCSS);
//						}
//					}
//				} else if(year == yearJSSJ) {	// 日历选择的是结束时间的年份
//					if(month == monthJSSJ) {	// 日历选择的是结束时间的月份
//						if(rq != "" && parseInt(rq) <= dayJSSJ) {
//							$("#" + dayId + k).css(rqCSS);
//						}
//					} else if(month < monthJSSJ) {	// 日历选择的是小于结束时间的月份
//						if(rq != "") {
//							$("#" + dayId + k).css(rqCSS);
//						}
//					}
//				} else {	// 日历选择的是介于开始时间和结束时间的年份
//					if(rq != "") {
//						$("#" + dayId + k).css(rqCSS);
//					}
//				}
//			}
//			
//			if(year == me.year && month == me.month) {
//				$("#" + dayId + (day+today-1)).css({"color":"red", "font-weight":"bold"});
//			}
		}
	}
	
	/**
	 * 日历月份改变点击事件
	 */
	function monthChangeBind(){
		// 往前
		$("#lastMonth").on("click", function(){
			if(me.monthFirst == 12){
				me.monthFirst = me.monthFirst-2;
				me.yearSecond = me.yearFirst;
				me.monthSecond = 11;
			}else if(me.monthFirst > 2){
				me.monthFirst = me.monthFirst-2;
				me.monthSecond = me.monthFirst+1;
			}else if(me.monthFirst == 2){
				me.yearFirst = me.yearFirst-1;
				me.monthFirst = 12;
				me.monthSecond = 1;
			}else if(me.monthFirst == 1){
				me.yearFirst = me.yearFirst-1;
				me.monthFirst = 11;
				me.yearSecond = me.yearFirst;
				me.monthSecond = 12;
			}
			
			initCalendar("calendarFirst", "dateFirst", "dayFirst", me.yearFirst, me.monthFirst, me.today, me.kssj, me.jssj);
			initCalendar("calendarSecond", "dateSecond", "daySecond", me.yearSecond, me.monthSecond, me.today, me.kssj, me.jssj);
			addPhases();
		});
		
		// 往后
		$("#nextMonth").on("click", function(){
			if(me.monthSecond == 12){
				me.yearFirst = me.yearFirst+1;
				me.monthFirst = 1;
				me.yearSecond = me.yearSecond+1;
				me.monthSecond = 2;
			}else if(me.monthSecond == 11){
				me.monthFirst = 12;
				me.yearSecond = me.yearSecond+1;
				me.monthSecond = 1;
			}else if(me.monthSecond >= 2){
				me.monthFirst = me.monthFirst+2;
				me.monthSecond = me.monthFirst+1;
			}else if(me.monthSecond == 1){
				me.yearFirst = me.yearFirst+1;
				me.monthFirst = 2;
				me.monthSecond = 3;
			}
			
			initCalendar("calendarFirst", "dateFirst", "dayFirst", me.yearFirst, me.monthFirst, me.today, me.kssj, me.jssj);
			initCalendar("calendarSecond", "dateSecond", "daySecond", me.yearSecond, me.monthSecond, me.today, me.kssj, me.jssj);
			addPhases();
		});
	}
	
	/**
	 * 向日历中添加各保电阶段
	 * 根据各阶段的保电级别不同，在日历上展示的该阶段背景色不同
	 */
	function addPhases(){
		if(me.phases != null){
			var kssj = "";
			var jssj = "";
			var color = "";
			var length = me.phases.length;
			for(var i=0; i<length; i++){
				switch(me.phases[i].ensureLevel){
					case "特级":
						color = "#f01d1d ";
						break;
					case "一级":
						color = "#eb9f0d ";
						break;
					case "二级":
						color = "#ab8ee0 ";
						break;
					case "三级":
						color = "#009694 ";
						break;
					default:
						break;
				}
				
				kssj = me.phases[i].startTime.substr(0, 10);
				jssj = me.phases[i].endTime.substr(0, 10);
				
				colorChange(kssj, jssj, color);
			}
		}
	}
	
	/**
	 * 根据不同保电阶段修改日历颜色
	 */
	function colorChange(kssj, jssj, color){
		var rqCSS = {"color":"white", "background":color, "border-radius":"5px"};
		var today = me.year + "-" + addZero(me.month) + "-" + addZero(me.today);
		
		// 第一个日历
		for(var i=1; i<=42; i++){
			if($("#dayFirst"+i)[0].innerHTML != ""){
				var day = addZero($("#dayFirst"+i)[0].innerHTML);
				var month = addZero(me.monthFirst);
				var rq = me.yearFirst + "-" + month + "-" + day;
				if(rq >= kssj && rq <= jssj){
					$("#dayFirst"+i).css(rqCSS);
				}
				
//				if(rq == today){
//					$("#dayFirst"+i).css({"color":"red", "font-weight":"bold"});
//				}
			}
		}
		
		// 第二个日历
		for(var i=1; i<=42; i++){
			if($("#daySecond"+i)[0].innerHTML != ""){
				var day = addZero($("#daySecond"+i)[0].innerHTML);
				var month = addZero(me.monthSecond);
				var rq = me.yearSecond + "-" + month + "-" + day;
				if(rq >= kssj && rq <= jssj){
					$("#daySecond"+i).css(rqCSS);
				}
				
//				if(rq == today){
//					$("#daySecond"+i).css({"color":"red", "font-weight":"bold"});
//				}
			}
		}
	}
	
	/**
	 * 小于10时补“0”
	 * num: 包含String和int型
	 */
	function addZero(num){
		if(num < 10){
			num = "0" + num;
		}
		return num;
	}
	
	// 获取用户保电级别统计
	function getUserLevel(){
		var p_path = "/powerSupplyGuaranteeOverviewDetails/getUserLevel";
		var params = {"taskId":me.taskId, "belongOrgID":me.orgId};
    	myak.send(p_path,'GET',params,false,function(result){
    		$("#user1")[0].innerHTML = 0;
    		$("#user2")[0].innerHTML = 0;
    		$("#user3")[0].innerHTML = 0;
    		$("#user4")[0].innerHTML = 0;
    		
    		var l = result.length;
    		for(var i=0; i<l; i++){
    			switch(result[i].ensureLevel){
	    			case 1:
	    				$("#user1")[0].innerHTML = result[i].count;
	    				break;
	    			case 2:
	    				$("#user2")[0].innerHTML = result[i].count;
	    				break;
	    			case 3:
	    				$("#user3")[0].innerHTML = result[i].count;
	    				break;
	    			case 4:
	    				$("#user4")[0].innerHTML = result[i].count;
	    				break;
    				default:
    					break;
    			}
    		}
		});
	}
	
	// 获取用户表格详细信息
	var userClickCount = 1;
	function getUserInfo(ensureLevel){
		if(userClickCount == 1){
			userGrid.on('drawcell',function(e){
				var divImg = "";
				switch (e.field) {
				 	case "emergencyPlan":
						if(e.value != null){
				 			divImg = "<img src='res/img/yuan.png' style='width:18px; cursor:pointer;' />";
				 			e.cellHtml = divImg;
						}
						break;
				 	case "planInfo":
				 		divImg = "<img src='res/img/fujian.png' style='width:18px; cursor:pointer;' />";
				 		e.cellHtml = divImg;
				 		break;
					case "geographyLocation":
						if(e.value != null){
							divImg = "<img src='res/img/weizhi.png' style='width:18px; cursor:pointer;' />";
							e.cellHtml = divImg;
						}
						break;
					default:
						break;
				}
		 	});
			
			userGrid.on("cellclick", function(e){
				switch(e.field){
				 	case "emergencyPlan":
				 		var emergencyPlan = e.row.emergencyPlan;
				 		if(emergencyPlan == null){
				 			showTips("info", "<b>注意</b> <br/>该记录尚未维护预案！");
				 		}else{
				 			 var win = ak.open({
				 				 title: '查看附件',
//				 			     url:"http://10.144.15.224:9091?url=/user/mcsas/{业务表名}/{业务数据的唯一标示}/&readOnly=true",
				 				 url:akapp.commonFileUpload+"/upload.html?url=/user/mcsas/electricityEnsureUser/"+emergencyPlan+"/&readOnly=true",
				 				 showModal: false,
				 				 width: 1140,
				 				 height: 630,
				 	           	 allowResize: Boolean,       //允许尺寸调节
				 	           	 allowDrag: Boolean,         //允许拖拽位置
				 	           	 showCloseButton: Boolean,   //显示关闭按钮
				 	           	 showMaxButton: Boolean,     //显示最大化按钮
				 	           	 showModal: Boolean,         //显示遮罩
				 	           	 loadOnRefresh: false
				 		    });
				 	        win.showAtPos("330px", "120px");
				 		}
						break;
				 	case "planInfo":
				 		var id = e.row.id;
				 		ak("userPlanInfoWinGrid").load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getElectricityEnsurePhaseResult');
						var params = {"taskId":id};
				 		ak("userPlanInfoWinGrid").load(params);
				 		
				 		ak("userPlanInfoWindow").show("645px", "145px");
				 		$("#userPlanInfoWinGrid").parent().parent().css({"background":"#eceef6", "padding":"10px"});
						break;
				 	case "geographyLocation":
				 		$("#tim_pow_org").css({display :"none"});
						$("#jianjie").css({display :"block"});
						
						coordGisPosition("user", e.row);
						break;
					default:
						break;
				 }
			 });
			
			userGrid.load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getUserResult');
		}
		
		var params = {"taskId":me.taskId, "ensureLevel":ensureLevel, "belongOrgID":me.orgId};
		userGrid.load(params);
		
		userClickCount++;
	}
	
	// 获取线路、电站、人员、车辆chart图数据
	function initChartData(){
		var p_path = "/powerSupplyGuaranteeOverviewDetails/getChartData";
		var params = {"taskId":me.taskId, "belongOrg":me.orgId};
    	myak.send(p_path,'GET',params,false,function(data){
    		initPersonChart(data.personChart);
    		initCarChart(data.carChart);
    		initLineChart(data.lineChart);
    		initStationChart(data.stationChart);
    		stationOrLineId = LineId + stationId
    		if(stationOrLineId != null && stationOrLineId != ""){
    			stationOrLineId = stationOrLineId.substring(",".length);
    		}
		});
	}
	
	// 线路柱状图
	function initLineChart(params){
		LineId = params.idData;
		var data = {
			"seriesData":params.seriesData,
			"xAxisData":params.xAxisData
		};
		var l1 = data.seriesData.length;
		var max = 0;
		for(var i=0; i<l1; i++){
			var l2 = data.seriesData[i].length;
			for(var j=0; j<l2; j++){
				if(data.seriesData[i][j] > max){
					max = data.seriesData[i][j];
				}
			}
		}
		if(max < 4){
			max = 4;
		}
		
		var option = {
				color:["#f01d1d", "#eb9f0d", "#ab8ee0", "#009694"],
				grid : {
					x : '8%',
					y : '5%',
					x2 : '0%',
					y2 : '30%'
				},
				xAxis : [{
						axisLabel : {// x轴 刻度 1234
//							rotate : 18,	// 横坐标旋转
							interval : 0,
							textStyle : {
								fontSize:10
							}
						}
					}],
				yAxis : [{
					min : 0,
					max : max
				}]
		};
		ak("lineChart").setChartType("bar");
		ak("lineChart").setDisplayModel(option);
		ak("lineChart").setViewModel(data);
	}
	// 柱状图点击事件
	ak("lineChart").on("chartclick", _lineChart_onclick);
	function _lineChart_onclick(e) {
		var code = e.xAxisCode;
	}
	
	// 电站柱状图
	function initStationChart(params){
		stationId = params.idData;
		var data = {
				"seriesData":params.seriesData,
				"xAxisData":params.xAxisData
			};
		var l1 = data.seriesData.length;
		var max = 0;
		for(var i=0; i<l1; i++){
			var l2 = data.seriesData[i].length;
			for(var j=0; j<l2; j++){
				if(data.seriesData[i][j] > max){
					max = data.seriesData[i][j];
				}
			}
		}
		if(max < 4){
			max = 4;
		}
		
		var option = {
				color:["#f01d1d", "#eb9f0d", "#ab8ee0", "#009694"],
				grid : {
					x : '8%',
					y : '5%',
					x2 : '0%',
					y2 : '30%'
				},
				xAxis : [{
						axisLabel : {// x轴 刻度 1234
//							rotate : 18,	// 横坐标旋转
							interval : 0,
							textStyle : {
								fontSize:10
							}
						}
					}],
				yAxis : [{
					min : 0,
					max : max
				}]
		};
		ak("stationChart").setChartType("bar");
		ak("stationChart").setDisplayModel(option);
		ak("stationChart").setViewModel(data);
	}
	
	ak("stationChart").on("chartclick", _stationChart_onclick);
	function _stationChart_onclick(e) {
		var code = e.xAxisCode;
	}
	
	// 获取站线表格详细信息
	var solClickCount = 1;
	function getStationLineInfo(stationLineType){
		if(solClickCount == 1){
			stationLineGrid.on('drawcell',function(e){
				var divImg = "";
				switch (e.field) {
					case "geographyLocation":
						divImg = "<img src='res/img/weizhi.png' style='width:18px; cursor:pointer;' />";
						e.cellHtml = divImg;
						break;
					case "stationLineManage":
				 		divImg = "<img src='res/img/fujian.png' style='width:18px; cursor:pointer;' />";
				 		e.cellHtml = divImg;
				 		break;
					
					default:
						break;
				}
		 	});
			
			stationLineGrid.on("cellclick", function(e){
				switch(e.field){
				 	case "geographyLocation":
				 		$("#tim_pow_org").css({display :"none"});
						$("#jianjie").css({display :"block"});
						
				 		solGisPosition(e.row);
						break;
				 	case "stationLineManage":
				 		var stationLineID = e.row.stationLineID;
				 		var stationLineName = e.row.stationLineName;
				 		var stationLineType = e.row.stationLineType;
				 		var voltageLevel = e.row.voltageLevel;
				 		var urlParam = null;
				 		var sid = null;
				 		if(stationLineType == "01"){	// 线路
				 			urlParam= "/transmissionLinemanagementWeb/linemanagement/index.jsp?lineId="+stationLineID+"&lineName="+stationLineName;
				 			sid = "linemanagement";
				 		}else if(stationLineType == "02"){	// 电站
				 			if(voltageLevel.indexOf("±") == -1){	// 变电
				 				urlParam= "/transformStationManageWeb/stationManage/index.jsp?subStationId="+stationLineID;
				 				sid = "stationManage";
				 			}else{	// 换流
				 				urlParam= "/convertionStationManageWeb/stationManageView/index.jsp?substationLine="+stationLineID;
				 				sid = "stationManageView";
				 			}
				 		}
//				 		http://localhost:8080/stationManageView/index.jsp?substationLine=FF000000-0000-0000-1000-000000000600-00794
				 		
				 		var op = {
								id : "id_"+sid,
								url : urlParam,
								iframeName : "iframename_"+sid,
								iframeId : "iframeid_"+sid,
								parentContainer : $(".ak-fit"),
								returnPageRefresh : false,// 返回时是否刷新
								nextButton : false,
								callback : function() {
								}
							};
						var jump = myak.pageJumping(op);
						jump.f();
				 		
						break;
					default:
						break;
				 }
			 });
			
			stationLineGrid.load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getStationOrLineResult');
		}
		
		var params = {"taskId":me.taskId, "stationLineType":stationLineType, "belongOrg":me.orgId};
		stationLineGrid.load(params);
		
		solClickCount++;
	}
	
	// 获取资源保障的数量（人员、车辆、驻点、宾馆、医院）
	function getResourceCount(){
		var p_path = "/powerSupplyGuaranteeOverviewDetails/getResourceCount";
		var params = {"taskId":me.taskId, "belongOrg":me.orgId};
    	myak.send(p_path,'GET',params,false,function(data){
    		$("#personCount")[0].innerHTML = data.personCount;
    		$("#carCount")[0].innerHTML = data.carCount;
    		$("#pointCount")[0].innerHTML = data.pointCount;
    		$("#hotelCount")[0].innerHTML = data.hotelCount;
    		$("#hospitalCount")[0].innerHTML = data.hospitalCount;
		});
	}
	
	// 人员饼图
	function initPersonChart(data){
		var option = {
				legend : {
					orient : 'horizontal',
					y : 'bottom',
					x : 'center',
					textStyle : {
						fontSize:10
					},
					itemWidth: 10,
					itemHeight: 10
				},
				series : [ {
					center : [ '50%', '45%' ]
				} ]
		};
		ak("personChart").setChartType("pie"); 
		ak("personChart").setDisplayModel(option);
		ak("personChart").isRightAngle = false;
		ak("personChart").setViewModel(data);
		ak("personChart").on("chartclick", personChart_click);
	}

	function personChart_click(e){
		me.personChartType = e.legendCode;
		me.carChartType = "";
		resourcesProtect('1');
	}
	
	// 获取人员表格详细信息
	function getPersonInfo(){
		personGrid.load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getPersonResult');
		var params = {"taskId":me.taskId, "type":me.personChartType, "belongOrg":me.orgId};
		personGrid.load(params);
	}
	
	var wtlyLengend = [];
	var wtlySeriesData = [];
	var wtly_AllCount = 0;
	var colorArray = [ '#34c0e2', '#eb9f0d', '#009694','#ab8ee0', '#26ae50', '#ff3d58', '#37e468', '#f4c874' ];
	// 车辆饼图
	function initCarChart(data){
		var option = {
				series : [ {
					center : [ '50%', '45%' ]
				} ]
		};
		if(data.legendData.length <4){
			option.legend ={
				orient : 'horizontal',
				y : 'bottom',
				x : 'center',
				textStyle : {
					fontSize:10
				},
				itemWidth: 10,
				itemHeight: 10
			}
		}else{
			option.legend ={
				show:false	
			}
			$("#wtly_zkxq").css("display","block");
			// 展开详情 下拉数据
	 		var lengendDataLen = data.legendData.length;
			for (var i = 0; i < lengendDataLen; i ++) {
				wtlyLengend.push(data.legendData[i]);
			}
			var seriesDataLen = data.seriesData[0].length;
			for (var i = 0; i < seriesDataLen; i ++) {
				wtly_AllCount += parseInt(data.seriesData[0][i].value);
				wtlySeriesData.push(data.seriesData[0][i]);
			}
			// 点击事件
			$("#wtly_zkxq").on("click",function(e){ 
		 		var color = '';
		 		var gridData = new Array();
		 		for (var i = 0; i < wtlyLengend.length; i ++) {
		 			if (i >= colorArray.length) {
		 				color = colorArray[i % colorArray.length];
		 			} else {
		 				color = colorArray[i];
		 			}
		 			
		 			var tmpData = {
		 					"btmc": wtlyLengend[i].name,
		 					"cnt":wtlySeriesData[i].value,
		 					"percent":Math.round(wtlySeriesData[i].value / wtly_AllCount * 10000) / 100.00 + "%"
		 			};
		 			gridData.push(tmpData);
		 		}
		 		var grid = ak("wtly_datagrid");
		 		grid.setData(gridData);
		 		
		 		if($("#wtly_zkxq").html() == "展开详情"){
		 			$("#wtly_datagrid").show();
		 			$("#wtly_zkxq").css("background","#eb9f0d");
		 			$("#wtly_zkxq").html("收缩详情");
		 		} else{
		 			$("#wtly_datagrid").hide();
		 			$("#wtly_zkxq").css("background","#009694");
		 			$("#wtly_zkxq").html("展开详情");
		 		}
			})
		}
		ak("carChart").setChartType("pie"); 
		ak("carChart").setDisplayModel(option);
		ak("carChart").isRightAngle = false;
		ak("carChart").setViewModel(data);
		ak("carChart").on("chartclick", carChart_click);
	}
	
	function carChart_click(e){
		me.carChartType = e.legendCode;
		me.personChartType = "";
		resourcesProtect('2');
	}
	
	$("#pointCount").on("click",function(e){
		resourcesProtect('3');
	});
	$("#hotelCount").on("click",function(e){
		resourcesProtect('4');	
	});
	$("#hospitalCount").on("click",function(e){
		resourcesProtect('5');	
	});
	
	// 获取车辆表格详细信息
	function getCarInfo(){
		carGrid.load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getCarResult');
		var params = {"taskId":me.taskId, "type":me.carChartType, "belongOrg":me.orgId};
		carGrid.load(params);
	}
	
	// 获取驻点表格详细信息
	var pointClickCount = 1;
	function getPointInfo(){
		var params = {"taskId":me.taskId, "belongOrg":me.orgId};
		pointGrid.load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getPointResult');
		pointGrid.load(params);
		if(pointClickCount == 1){
			pointGrid.on('drawcell',function(e){
				var divImg = "";
				switch (e.field) {
					case "location":
						if(e.value != null){
							divImg = "<img src='res/img/weizhi.png' style='width:16px; cursor:pointer;' />";
							e.cellHtml = divImg;
						}
						break;
					default:
						break;
				}
		 	});
			
			pointGrid.on("cellclick", function(e){
				switch(e.field){
				 	case "location":
				 		$("#tim_pow_org").css({display :"none"});
						$("#jianjie").css({display :"block"});
						
						coordGisPosition("point", e.row);
						break;
					default:
						break;
				 }
			 });
		}
		pointClickCount++;
	}
	
	// 获取宾馆表格详细信息
	var hotelClickCount = 1;
	function getHotelInfo(){
		var params = {"taskId":me.taskId};
		hotelGrid.load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getHotelResult');
		hotelGrid.load(params);
		if(hotelClickCount == 1){
			hotelGrid.on('drawcell',function(e){
				var divImg = "";
				switch (e.field) {
					case "location":
						if(e.value != null){
							divImg = "<img src='res/img/weizhi.png' style='width:16px; cursor:pointer;' />";
							e.cellHtml = divImg;
						}
						break;
					default:
						break;
				}
		 	});
			
			hotelGrid.on("cellclick", function(e){
				switch(e.field){
				 	case "location":
				 		$("#tim_pow_org").css({display :"none"});
						$("#jianjie").css({display :"block"});
						
						coordGisPosition("hotel", e.row);
						break;
					default:
						break;
				 }
			 });
			
		}
		hotelClickCount++;
	}
	
	// 获取医院表格详细信息
	var hospitalClickCount = 1;
	function getHospitalInfo(){
		var params = {"taskId":me.taskId};
		hospitalGrid.load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getHospitalResult');
		hospitalGrid.load(params);
		if(hospitalClickCount == 1){
			hospitalGrid.on('drawcell',function(e){
				var divImg = "";
				switch (e.field) {
					case "location":
						if(e.value != null){
							divImg = "<img src='res/img/weizhi.png' style='width:16px; cursor:pointer;' />";
							e.cellHtml = divImg;
						}
						break;
					default:
						break;
				}
		 	});
			
			hospitalGrid.on("cellclick", function(e){
				switch(e.field){
				 	case "location":
				 		$("#tim_pow_org").css({display :"none"});
						$("#jianjie").css({display :"block"});
						
						coordGisPosition("hospital", e.row);
						break;
					default:
						break;
				 }
			 });
			
		}
		hospitalClickCount++;
	}
	
	// 获取气象信息
	function initWeatherInfo(){
		var cityName = "null";
		var url = "/powerSupplyGuaranteeOverviewDetails/getLogCity";
		myak.send(url,'GET',null,false,function(data){
			if(data.belongCityName != undefined && data.belongCityName != null){
				cityName = data.belongCityName;
			}
		});
		
		var myDate = new Date();
		var w_param = {};
		w_param.date = ak.formatDate(myDate, 'yyyyMMdd');
		w_param.city = cityName;
		w_param.callback = '';
		
//		w_param.date = "20170716";
//		w_param.city = "上海";
		
		$.get(akapp.weather +'/nmcForecast/getWeathersourceByTimeCity',w_param,function(result){
			if(result != ""){
				// 24小时
				if(result[0].minwdesc == result[0].maxwdesc){	// 天气
					$("#weather_24")[0].innerHTML = result[0].minwdesc;
				}else{
					$("#weather_24")[0].innerHTML = result[0].minwdesc + "转" + result[0].maxwdesc;
				}
				
				$("#temperature_24")[0].innerHTML = result[0].mintemp + "~" + result[0].maxtemp;	// 温度
				
				if(result[0].minpower == result[0].maxpower){	// 风速
					$("#wind_24")[0].innerHTML = result[0].minpower;
				}else{
					$("#wind_24")[0].innerHTML = result[0].minpower + "转" + result[0].maxpower;
				}
				
				// 48小时
				if(result[1].minwdesc == result[1].maxwdesc){	// 天气
					$("#weather_48")[0].innerHTML = result[1].minwdesc;
				}else{
					$("#weather_48")[0].innerHTML = result[1].minwdesc + "转" + result[1].maxwdesc;
				}
				
				$("#temperature_48")[0].innerHTML = result[1].mintemp + "~" + result[1].maxtemp;	// 温度
				
				if(result[1].minpower == result[1].maxpower){	// 风速
					$("#wind_48")[0].innerHTML = result[1].minpower;
				}else{
					$("#wind_48")[0].innerHTML = result[1].minpower + "转" + result[1].maxpower;
				}
			}
		});
	}
	
	// 调度信息曲线图
	function initDispatchChart(){
		var data = {
				"legendData":[
					{"code":"1","name":"同期最高负荷"},
					{"code":"2","name":"当日最高负荷"}
				],
				"xAxisData":[
					{"code":"1","name":"1"},
					{"code":"2","name":"2"},
					{"code":"3","name":"3"},
					{"code":"4","name":"4"},
					{"code":"5","name":"5"},
					{"code":"6","name":"6"},
					{"code":"7","name":"7"},
					{"code":"8","name":"8"},
				],
				"seriesData":[
					[1,3,2,4,3,2,3,2],
					[2,4,3,5,4,3,4,3]
				]
			}
			var option = {
				color:["#34c0e2", "#eb9f0d"],
				legend: {
			        orient: 'horizontal',
			        x: 'right',
			        y:'top',
			        itemWidth: 10,
					itemHeight: 10
			    },
				series :[
					{
						symbol:'circle',
			        	symbolSize:4,
			        	stack: null,
			        	areaStyle: {
			    			normal: {
			    				color: "#FFF"
			    			}
			    		}
					},{
						symbol:'circle',
			        	symbolSize:4,
			        	stack: null,
			        	label: {
			                 normal: {
			                     show: true,
			                     position: 'top'
			                 }
			             },
			        	areaStyle: {
			    			normal: {
			    				color: "#FFF"
			    			}
			    		}
					}
				]
		};
			ak("dispatchChart").setChartType("line"); 
			ak("dispatchChart").setDisplayModel(option);
			ak("dispatchChart").setIsRightAngle(true);
			ak("dispatchChart").isMultiSeries=true;
			ak("dispatchChart").setViewModel(data);
	}
	
	// 获取运行内容的数量（故障、缺陷）
	function getRunningContentCount(){
		var p_path = "/powerSupplyGuaranteeOverviewDetails/getRunningContentCount";
		var params = {"taskId":me.taskId, "belongOrg":me.orgId, "startTime":me.startTime, "endTime":me.endTime};
    	myak.send(p_path,'GET',params,false,function(data){
    		if(data.successful != undefined && !data.successful){
    			$("#recoveredCount")[0].innerHTML = 0;
        		$("#disposingCount")[0].innerHTML = 0;
        		$("#eliminatedCount")[0].innerHTML = 0;
        		$("#uneliminatedCount")[0].innerHTML = 0;
    		}else{
    			$("#recoveredCount")[0].innerHTML = data.recoveredCount;
        		$("#disposingCount")[0].innerHTML = data.disposingCount;
        		$("#eliminatedCount")[0].innerHTML = data.eliminatedCount;
        		$("#uneliminatedCount")[0].innerHTML = data.uneliminatedCount;
    		}
		});
	}
	
	// 初始化点击绑定事件
	function initClickBind(){
//		$("#downorup").mouseover(function(){
////			$("#menutheme").removeClass("hidden");
////			$("#menutheme").addClass("show");
////			$("#downorup img").attr({ src: "./res/img/up-w.png" }); 
//		});
//		
//		/*左上方点击事件（暂时不做）*/
//		$("#downorup").click(function(){
//			$("#tasksList").children().remove();
//			
//			var p_path = "/powerSupplyGuaranteeOverviewDetails/getpowerSupplyGuaranteeOverviewDetailsResult";	// 获取保电任务
//	    	myak.send(p_path,'GET',null,false,function(result){
//	    		me.tasks = result;
//			});
//	    	
//	    	var l = me.tasks.length;
//	    	for(var i=0; i<l; i++){
//	    		var li = $("<li id='"+me.tasks[i].id+"'><div>"+me.tasks[i].theme+"</div></li>");
//    			
//    			$("#tasksList").append(li);
//	    	}
//			
//			if($("#menutheme").hasClass("show")){
//				$("#menutheme").addClass("hidden");
//				$("#menutheme").removeClass("show");
//				$("#downorup img").attr({ src: "./res/img/down-w.png" });
//			}else{
//				$("#menutheme").removeClass("hidden");
//				$("#menutheme").addClass("show");
//				$("#downorup img").attr({ src: "./res/img/up-w.png" }); 
//			}
//		});
//		
//		/*g20待选框鼠标离开事件 */
//		$("#menutheme").mouseleave(function(){
//			$("#menutheme").addClass("hidden");
//			$("#menutheme").removeClass("show");
//			$("#downorup img").attr({ src: "./res/img/down-w.png" }); 
//		});
		
		// 双箭头图片点击事件
		$("#moreOrgs").on("click", function(){
			if(n%2 == 1){
				$("#moreOrgs").css({"transform":"rotate(180deg)"});
				$("#orgList").css("margin-left", "-1187px");
			}else{
				$("#moreOrgs").css({"transform":"rotate(0deg)"});
				$("#orgList").css("margin-left", "0px");
			}
			hideThirdOrgList();
			n++;
		});
		
		/*简介按钮事件切换到gis*/
		$("#brief_intro").click(function(){
			$("#tim_pow_org").css({display :"block"});
			$("#jianjie").css({display :"none"});
		});
		
		/*gis按钮事件，切换到简介事件*/
		$("#gis").click(function(){
			$("#tim_pow_org").css({display :"none"});
			$("#jianjie").css({display :"block"});
			initGis();
		});
		
		// 保电方案点击事件
		$("#ensureNotice").on("click", function(){
			var win = ak.open({
				 title: '附件上传',
				 url:akapp.commonFileUpload+"/upload.html?url=/user/mcsas/electricityEnsureOrg/"+me.ensurePlan+"/&readOnly=true",
				 showModal: false,
				 width: 1140,
				 height: 630,
	           	 allowResize: Boolean,       //允许尺寸调节
	           	 allowDrag: Boolean,         //允许拖拽位置
	           	 showCloseButton: Boolean,   //显示关闭按钮
	           	 showMaxButton: Boolean,     //显示最大化按钮
	           	 showModal: Boolean,         //显示遮罩
	           	 loadOnRefresh: false,       //true每次刷新都激发onload事件
	           	 onload: function () {       //弹出页面加载完成
	           	 }
		    });

	        win.showAtPos("330px", "120px");
		});
		
		/*用户单位点击事件*/
		$("#userOfpow_guar").click(function(){
			hideThirdOrgList();
			
			$('#specialLevel').addClass("selected")            //当前<li>元素高亮
	          .siblings().removeClass("selected");  
			
			me.userTab = 1;
			
			ak('userofpowWindow').show ("563px", "89px");
	        var $div_li =$("div.tab_menu ul li");
	        $div_li.click(function(){
		         $(this).addClass("selected")            //当前<li>元素高亮
		          .siblings().removeClass("selected");  //去掉其它同辈<li>元素的高亮
		          var index =  $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
		          me.userTab = index+1;
		          getUserInfo(me.userTab);
	        }).hover(function(){
	          $(this).addClass("hover");     //over out,分别代表鼠标悬停和移除的函数
	        },function(){
	          $(this).removeClass("hover");
	        });
		          
	        getUserInfo(me.userTab);
		});
		
		
		/*站线柱图点击事件*/
		$("#stationorline").click(function(){
			hideThirdOrgList();
			
//			$('div.tab_menu ul li.default').addClass("selected")            //当前<li>元素高亮
			$('#stationTab').addClass("selected") 
	          .siblings().removeClass("selected");  
			
			ak('stationorlinewindow').show ( "563px", "89px" );
			 	var $div_li =$("div.tab_menu ul li");
			    $div_li.click(function(){
					$(this).addClass("selected")            //当前<li>元素高亮
						   .siblings().removeClass("selected");  //去掉其它同辈<li>元素的高亮
		            var index =  $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
		            if(index == 4){
		            	me.solTab = "02";
		            	ak("stationLineGrid").updateColumn("stationLineName", {header: "电站名称"});
		            	ak("stationLineGrid").updateColumn("stationLineManage", {header: "站所管理"});
		            	getStationLineInfo(me.solTab);
		            }else if(index == 5){
		            	me.solTab = "01";
		            	ak("stationLineGrid").updateColumn("stationLineName", {header: "线路名称"});
		            	ak("stationLineGrid").updateColumn("stationLineManage", {header: "线路管理"});
		            	getStationLineInfo(me.solTab);
		            }
				}).hover(function(){
					$(this).addClass("hover");     //over out,分别代表鼠标悬停和移除的函数
				},function(){
					$(this).removeClass("hover");
				});
			    
			    me.solTab = "02";
			    ak("stationLineGrid").updateColumn("stationLineName", {header: "电站名称"});
            	ak("stationLineGrid").updateColumn("stationLineManage", {header: "站所管理"});
            	getStationLineInfo(me.solTab);
		});
		
		/*资源保障包括人员，车辆，驻点，宾馆，医院点击事件*/
//		$("#resourcesprotect").click(function(){});
		
		/*调度信息图片事件*/
		$("#dispatch_info").click(function(){
			ak('dispatch_info_window').show ("left", "89px");
			
			if(me.ensureMapId == "" || me.ensureMapId == null){
				$("#ensureMap").css("background-image","url(res/img/no-gongdiantu.png)");
			}else{
//				$("#ensureMap").css("background-image","url(http://10.144.15.216/mcsas-dfs/files/download?filePath=" +
				$("#ensureMap").css("background-image","url(/mcsas-dfs/files/download?filePath=" +
		    			"/user/mcsas/electricityEnsure/"+me.ensureMapId+"/"+me.ensureMapName+")");
			}
		});
		
		// 故障数据点击事件
		// 故障-已恢复
		$("#recoveredCount").on("click", function(){
			hideThirdOrgList();
			ak("faultWindow").show("0", "89px");
			
			ak("faultGrid").load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getFaultResult');
			var params = {"taskId":me.taskId, "belongOrg":me.orgId, "startTime":me.startTime, "endTime":me.endTime, "flag":"recovered"};
			ak("faultGrid").load(params);
		});
		// 故障-处理中
		$("#disposingCount").on("click", function(){
			hideThirdOrgList();
			ak("faultWindow").show("0", "89px");
			
			ak("faultGrid").load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getFaultResult');
			var params = {"taskId":me.taskId, "belongOrg":me.orgId, "startTime":me.startTime, "endTime":me.endTime, "flag":"disposing"};
			ak("faultGrid").load(params);
		});
		
		// 缺陷数据点击事件
		// 缺陷-已消缺
		$("#eliminatedCount").on("click", function(){
			hideThirdOrgList();
			ak("defectWindow").show("0", "89px");
			
			ak("defectGrid").load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getDefectResult');
			var params = {"taskId":me.taskId, "belongOrg":me.orgId, "startTime":me.startTime, "endTime":me.endTime, "flag":"eliminated"};
			ak("defectGrid").load(params);
		});
		// 故障-未消缺
		$("#uneliminatedCount").on("click", function(){
			hideThirdOrgList();
			ak("defectWindow").show("0", "89px");
			
			ak("defectGrid").load(akapp.appname+'/powerSupplyGuaranteeOverviewDetails/getDefectResult');
			var params = {"taskId":me.taskId, "belongOrg":me.orgId, "startTime":me.startTime, "endTime":me.endTime, "flag":"uneliminated"};
			ak("defectGrid").load(params);
		});
	}
 
	var numFlag = 0;
	ak('resourcesPro').on('close',function(e){
		numFlag = 0;
	});
	function resourcesProtect(param){
		if(numFlag == 0){
			if(param != '1' && param != '2'){
				me.carChartType = "";
				me.personChartType = "";
			}
			ak('resourcesPro').show ( "right", "bottom" );		
			var $content = $(".scrollable_demo");
			var i = 1;  //已知显示的<a>元素的个数		 
			var count = $content.children("div").length;//总共的<a>元素的个数
			/*向右点击事件*/
			$(".rightbutton_click").click(function(){
				if( !$content.is(":animated")){  //判断元素是否正处于动画，如果不处于动画状态，则追加动画。
					if(m<count){  //判断 i 是否小于总的个数
						m++;
						$content.animate({left: "-=100%"}, 600);
					}
				}
			});
			/*向左点击事件*/
			$(".leftbutton_click").click(function(){
				if( !$content.is(":animated")){
					if(m>i){ //判断 i 是否小于总的个数
						m--;
						$content.animate({left: "+=100%"}, 600);
					}
				}
			});		  
			hideThirdOrgList();
			getPersonInfo();
			getCarInfo();
			getPointInfo();
			getHotelInfo();
			getHospitalInfo();
			numFlag ++;
		}else{
			if(param == '1'){
				getPersonInfo();				
			}else if(param == '2'){
				getCarInfo();				
			}else if(param == '3'){
				getPointInfo();
			}else if(param == '4'){
				getHotelInfo();
			}else if(param == '5'){
				getHospitalInfo();
			}
		}
	}
/***********************************重过载*************************************************************/		
	
	function _initOverLoadCount(){
		var data = {
				"stationOrLineId":stationOrLineId
		};
 		var p_path = '/heavyOverload/showHeavyOverLoadCountResult';
 		ak.send(akapp.heavyOverLoad + p_path,'GET',data,true,function(data){
 			if(data.processing != undefined){
 				$("#overLoad")[0].innerText = data.processing;
 			}else{
 				$("#overLoad")[0].innerText = "0";
 			}
 		});
	}
	$("#overLoad").on("click",function(e){
		hideThirdOrgList();
		
		ak("overLoadWindow").showAtPos("0px", "55px");
		var overLoadDataGrid = ak("overLoadDataGrid");
		overLoadDataGrid.setUrl(akapp.heavyOverLoad + '/heavyOverload/showHeavyOverloadResult');
		overLoadDataGrid.load({'confirmStatus':'1,2,3,4',"stationOrLineId":stationOrLineId});
	});
	
/***********************************在线监测*************************************************************/		
	
	function _initMonitoringCount(){
		var data = {"stationOrLineId":stationOrLineId};
 		var p_path = '/onlineMonitoring/showOnlineMonitoringCountResult';
 		ak.send(akapp.heavyOverLoad + p_path,'GET',data,true,function(data){
 			if(data.tracking != undefined){
 				$("#monitoring")[0].innerText = data.tracking;
 			}else{
 				$("#monitoring")[0].innerText = "0";
 			}
 		});
	}
	$("#monitoring").on("click",function(e){
		hideThirdOrgList();
		
		ak("monitoringWindow").showAtPos("0px", "55px");
		var monitoringDataGrid = ak("monitoringDataGrid");
		monitoringDataGrid.setUrl(akapp.heavyOverLoad + '/onlineMonitoring/showOnlineMonitoringResult');
		monitoringDataGrid.load({'confirmStatus':'2',"stationOrLineId":stationOrLineId});
	});	

/***********************************气象预报*************************************************************/		
	
	function _initWeatherCount(){
		var data = {'type':'weather',"stationOrLineId":stationOrLineId};
 		var p_path = '/gridOperationInformation/showWeatherCountResult';
 		ak.send(akapp.warnPlatForm + p_path,'GET',data,true,function(data){
 			if(data.data != undefined){
 				$("#weather")[0].innerText = data.data;
 			}else{
 				$("#weather")[0].innerText = "0";
 			}
 		});
	}
	$("#weather").on("click",function(e){
		hideThirdOrgList();
		
		ak("weatherWindow").showAtPos("0px", "55px");
		var weatherDataGrid = ak("weatherDataGrid");
		weatherDataGrid.setUrl(akapp.heavyOverLoad + '/meteorological/showMeteorologicalResult');
		weatherDataGrid.load({
			'meteorologicalType':'1,2,3,4,5,6,7',
			'confirmStatus':'1,2',
			'maintenanceUnitId':null,
			'startTimeFrom':ak.formatDate(new Date(new Date()-24*60*60*1000),'yyyy-MM-dd HH:mm:ss'),
			'startTimeTo':ak.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')
		});
	});	

/***********************************环境预报*************************************************************/		
	
	function _initEnvironmentCount(){
		var data = {'type':'environment',"stationOrLineId":stationOrLineId};
 		var p_path = '/gridOperationInformation/showWeatherCountResult';
 		ak.send(akapp.warnPlatForm + p_path,'GET',data,true,function(data){
 			if(data.data != undefined){
 				$("#environment")[0].innerText = data.data;
 			}else{
 				$("#environment")[0].innerText = "0";
 			}
 		});
	}
	$("#environment").on("click",function(e){
		hideThirdOrgList();
		
		ak("weatherWindow").showAtPos("0px", "55px");
		var weatherDataGrid = ak("weatherDataGrid");
		weatherDataGrid.setUrl(akapp.heavyOverLoad + '/meteorological/showMeteorologicalResult');
		weatherDataGrid.load({
			'meteorologicalType':'8,9,10,11',
			'confirmStatus':'1,2',
			'maintenanceUnitId':null,
			'startTimeFrom':ak.formatDate(new Date(new Date()-24*60*60*1000),'yyyy-MM-dd HH:mm:ss'),
			'startTimeTo':ak.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')

		});
	});	
	
/***********************************电网风险*************************************************************/		
	
	function _initGridRiskCount(){
		var data = {"stationOrLineId":stationOrLineId};
 		var p_path = '/gridOperationInformation/showGridRiskCountResult';
 		ak.send(akapp.warnPlatForm + p_path,'GET',data,true,function(data){
 			if(data.data != undefined){
 				$("#gridRisk")[0].innerText = data.data;
 			}else{
 				$("#gridRisk")[0].innerText = "0";
 			}
 		});
	}
	$("#gridRisk").on("click",function(e){
		hideThirdOrgList();
		
		ak("gridRiskWindow").showAtPos("0px", "55px");
		var gridRiskDataGrid = ak("gridRiskDataGrid");
		gridRiskDataGrid.setUrl(akapp.warnPlatForm + '/gridOperationInformation/showGridRiskResult');
		gridRiskDataGrid.load({"stationOrLineId":stationOrLineId});
	});	
	
	// 站线GIS定位
	function solGisPosition(param){
		var id = param.stationLineID;
		var name = param.stationLineName;
		var stationLineType = param.stationLineType;
		var dataDzArray = new Array();
		var dataXlArray = new Array();
		var bd = {sblx : "300000", ywid : "info_sczh_dz", title : "变电站"};
		var xl = {sblx : "101000", ywid : "info_xl", title : "线路"};
		var dzItems = new Array();
		var xlItems = new Array();
		if(stationLineType == '01'){
			var item = {
					sbid : id,color:"blue",
					btns:[ {id:"info_xl", name:"线路"}],
					params:[{key : "线路名称", value :name}]
			};
			xlItems.push(item);
		}else{
			var item = {
					sbid : id,
					btns:[ {id:"info_sczh_dz", name:"电站"}],
					params:[{key : "电站名称", value :name}]
			}
			dzItems.push(item);
		}

		
		
		//后台取到数据，就拼接数组
		if(dzItems.length >0){
			bd.sbarray = dzItems;
			dataDzArray.push(bd);
		}
	    if(xlItems.length >0){
	    	xl.sbarray = xlItems;
	    	dataXlArray.push(xl);
	    }
	    //图片标注渲染
	    if(myIframe_2D.window.Gis2d_vectorLineBySBID != undefined){ // 线路
	    	myIframe_2D.window.Gis2d_vectorLineBySBID(dataXlArray);
	    	if(myIframe_2D.window.Gis2d_vectorImgBySBID){ // 电站
				myIframe_2D.window.Gis2d_vectorImgBySBID(dataDzArray);
			} 
	    }else{
	    	setTimeout(solGisPosition(param),500);
	    }
	}
	
	// 坐标GIS定位
	function coordGisPosition(type, params){
		var title = "";
		var coord = "";		// 坐标
		var lon = null;		// 经度
		var lat = null;		// 纬度
		var key1 = "";
		var value1 = "";
		var key2 = "";
		var value2 = "";
		
		switch(type){
		case "user":
			title = "用户";
			coord = params.geographyLocation;
			key1 = "用户名称";
			value1 = params.userName;
			key2 = "负责人";
			value2 = params.dutyPersonName;
			break;
		case "point":
			title = "驻点";
			coord = params.location;
			key1 = "驻点名称";
			value1 = params.stationName;
			key2 = "负责人";
			value2 = params.dutyPerson;
			break;
		case "hotel":
			title = "宾馆";
			coord = params.location;
			key1 = "宾馆名称";
			value1 = params.hotelName;
			key2 = "负责人";
			value2 = params.linkman;
			break;
		case "hospital":
			title = "医院";
			coord = params.location;
			key1 = "医院名称";
			value1 = params.hospitalName;
			key2 = "负责人";
			value2 = params.linkman;
			break;
		default:
			break;
		}
		
		if(coord != null){
			lon = parseFloat(coord.split(",")[0]);
			lat = parseFloat(coord.split(",")[1]);
		}
		var arr = [
	        {
	            sblx: "300000", ywid:"info_sczh_dz",title:title,
	            sbarray: [
	                {
	                    coordinates: [lon, lat],
	                    params: [{key: key1, value: value1}, {key: key2, value: value2}]
	                }
	            ]
	        },
	    ];
		
	    //图片标注渲染
	    if(myIframe_2D.window.Gis2d_vectorPointByCoordinate != undefined){
	    	if(myIframe_2D.window.Gis2d_vectorPointByCoordinate){
				myIframe_2D.window.Gis2d_vectorPointByCoordinate(arr, 9);
			} 
	    }else{
	    	setTimeout(coordGisPosition(type, params),500);
	    }
	}
	
	return me;
})

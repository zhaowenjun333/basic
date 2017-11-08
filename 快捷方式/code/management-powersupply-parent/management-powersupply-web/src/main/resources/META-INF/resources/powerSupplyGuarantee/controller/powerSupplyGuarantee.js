define(['akui', 'util', 'exportData', 'akapp'],function(ak, myak, exak, akapp){
	var me = {};
	
	ak.parse();
	
	var tabFlag = "addTab";
	
	var tabBtn = ak("radio"); // 获取 tab 按钮
	var grid = ak("dataGrid");
	
//	var dailyParam = {tab:1,recordId:'83936b7b-9258-4827-9fc4-b752e5bf7fc9'};
//	var dailyParam = {tab:2,recordId:'2fd46cce-87d8-48d8-8cf8-620fe7f8082c'};
	var dailyParam = myak.getDailyParams();
	
	/**
	 * 初始化方法
	 */
	me.render = function(){
		grid.load(akapp.appname+'/powerSupplyGuarantee/getPowerSupplyGuaranteeResult');
		
		me.button = $("#button").children(".ak-button");
		
		initTabBtn();
//		tabClick();
		exactSearchClick();
		searchImgClick();
		getGridsCount();	// 获取各阶段记录总数
		getDataGrid();
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
	
	 /**
	  * 新增、需跟踪、已闭锁、已归档 切换
	  */
	function initTabBtn(){
		var tabIndex = 0;
		if(dailyParam != null){
			switch(dailyParam.tab){//根据每个页面实际情况改写
				case 1://新增
					tabIndex = 0;
					tabFlag = "addTab";
					break;
				case 2://需跟踪
					tabIndex = 1;
					tabFlag = "followTab";
					break;
				case 3://已闭环
					tabIndex = 2;
					tabFlag = "closedTab";
					break;
				case 4://已归档
					tabIndex = 3;
					tabFlag = "filedTab";
					break;
				default:
					break;
			}
		}
		
		var arrlist = [
			{"id":"addTab","text":"新增"},
			{"id":"followTab","text":"需跟踪"},
			{"id":"closedTab","text":"已闭环"},
			{"id":"filedTab","text":"已归档"}
		];		
		tabBtn.setData(arrlist);
		tabBtn.setValue(arrlist[tabIndex].id);
		
		tabClick();
		tabBtn.fire("valuechanged");//触发对应方法初始化页面内容
		
		$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(0).attr("afterData","0");
		$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(1).attr("afterData","0");
		$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(2).attr("afterData","0");
		$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(3).attr("afterData","0");
	}
	
	/**
	 * 新增、需跟踪、已闭环、已归档点击事件
	 */
	function tabClick(){
		tabBtn.on("valuechanged",function(){
			tabFlag = this.getValue();
			initButton();
			switch(tabFlag){
				case "addTab":
					$("#dailyBtn").css("display","none");
					$("#closeBtn").css("display","none");
					$("#fileBtn").css("display","none");
					
					var params = {"tabType":tabFlag, "beginTime1":"", "beginTime2":"", "endTime1":"", "endTime2":"",
						 	"responsibleDepartmentId":"", "taskSource":"", "powerLevel":""};
					grid.load(params);
					grid.on('drawcell',function(e) {
				 		e.source._rowsEl.style.color = "#ff3d58";
				 	});
				break;
				case "followTab":
					$("#addBtn").css("display","none");
					$("#deleteBtn").css("display","none");
					$("#followBtn").css("display","none");
					$("#fileBtn").css("display","none");
					
					var params = {"tabType":tabFlag, "beginTime1":"", "beginTime2":"", "endTime1":"", "endTime2":"",
						 	"responsibleDepartmentId":"", "taskSource":"", "powerLevel":""};
					grid.load(params);
					grid.on('drawcell',function(e) {
				 		e.source._rowsEl.style.color = "#009694";
				 	});
				break;
				case "closedTab":
					$("#addBtn").css("display","none");
					$("#deleteBtn").css("display","none");
					$("#followBtn").css("display","none");
					$("#closeBtn").css("display","none");
					
					var params = {"tabType":tabFlag, "beginTime1":"", "beginTime2":"", "endTime1":"", "endTime2":"",
						 	"responsibleDepartmentId":"", "taskSource":"", "powerLevel":""};
					grid.load(params);
					grid.on('drawcell',function(e) {
				 		e.source._rowsEl.style.color = "black";
				 	});
				break;
				case "filedTab":
					$("#addBtn").css("display","none");
					$("#deleteBtn").css("display","none");
					$("#sendBtn").css("display","none");
					$("#followBtn").css("display","none");
					$("#closeBtn").css("display","none");
					$("#fileBtn").css("display","none");
					
					tabFlag = "filedTab";
					
					var params = {"tabType":tabFlag, "beginTime1":"", "beginTime2":"", "endTime1":"", "endTime2":"",
						 	"responsibleDepartmentId":"", "taskSource":"", "powerLevel":""};
					grid.load(params);
					grid.on('drawcell',function(e) {
				 		e.source._rowsEl.style.color = "black";
				 	});
				break;
				default: break;
			}
		});
	}
	
	/**
	 * 初始化功能按钮
	 */
	function initButton(){
		for(var i=0; i<me.button.length; i++){
			$("#"+me.button[i].id).css("display","block");
		}
	}
	
	/**
	 * 新增按钮点击事件
	 */
	$("#addBtn").on("click", function(){
		// 弹出窗
		var url = akapp.appname+"/powerSupplyGuaranteeWin/index.jsp";
		var title = "保电任务-新增";
		myak.open(url,title,null,function(action){
			grid.reload();
			getGridsCount();
		});
	});
	
	/**
	 * 编辑按钮点击事件
	 */
	$("#editBtn").on("click", function(){
		var selecteds = grid.getSelecteds();
		var length = selecteds.length;
		if(length == 1){
			selecteds[0].triggerType = "button";
			selecteds[0].triggerObj = "edit";
			selecteds[0].updateDateTime = selecteds[0].updateDateTime.getTime();
			params = JSON.stringify(selecteds[0]);
			
			// 弹出窗
//			var url = encodeURI(akapp.appname+"/powerSupplyGuaranteeWin/index.jsp?params="+params);
			var url = akapp.appname+"/powerSupplyGuaranteeWin/index.jsp?params="+encodeURIComponent(params);
			var title = "保电任务-编辑";
			myak.open(url,title,null,function(action){
				grid.reload();
			});
		}else{
			showTips("info", "<b>注意</b> <br/>请勾选且只能勾选一条数据！");
		}
	});
	
	/**
	 * 删除按钮点击事件
	 */
	$("#deleteBtn").on("click", function(){
		var selecteds = grid.getSelecteds();
		var length = selecteds.length;
		if(length > 0){
			ak.confirm("确定删除所勾选的"+length+"条记录？", "确定？",
		            function(action){
		                if (action == "ok") {
		                	var concurrent = whetherConcurrent(selecteds);
		                	if(concurrent == "false"){
		                		var params = {};
			                	for(var i=0; i<length; i++){
			                		var key = "id"+i;
			                		params[key] = selecteds[i].id;
			                	}
			                	params.rowsCount = length;
			                	
			                	var p_path = "/powerSupplyGuarantee/deletePowerSupplyGuarantee";
			                	myak.send(p_path,'GET',params,false,function(result){
			                		if(result.successful){
			                			grid.reload();
					        			showTips("success", "<b>确认</b> <br/>删除成功！");
			                		}else{
			                			showTips("danger", "<b>确认</b> <br/>删除失败！");
			                		}
				        		});
			                	
			                	getGridsCount();
		                	}else{
		                		showTips("danger", "<b>注意</b> <br/>勾选的数据中保电主题为【"+concurrent+"】的数据存在并发操作，请稍候刷新数据再重复当前操作！");
		                	}
		                }
		            }
		        );
			
		}else{
			showTips("info", "<b>注意</b> <br/>请至少勾选一条数据！");
		}
	});
	
	/**
	 * 查看按钮点击事件
	 */
	$("#examineBtn").on("click", function(){
		var selecteds = grid.getSelecteds();
		var length = selecteds.length;
		if(length == 1){
			selecteds[0].triggerType = "button";
			selecteds[0].triggerObj = "examine";
			params = JSON.stringify(selecteds[0]);
			
			// 弹出窗
//			var url = encodeURI(akapp.appname+"/powerSupplyGuaranteeWin/index.jsp?params="+params);
			var url = akapp.appname+"/powerSupplyGuaranteeWin/index.jsp?params="+encodeURIComponent(params);
			var title = "保电任务-查看";
			myak.open(url,title,null,function(action){
				
			});
		}else{
			showTips("info", "<b>注意</b> <br/>请勾选且只能勾选一条数据！");
		}
	});
	
	/**
	 * 日报按钮点击事件
	 */
	$("#dailyBtn").on("click", function(){
		var selecteds = grid.getSelecteds();
		var length = selecteds.length;
		if(length == 1){
			var taskId = selecteds[0].id;
			var theme = selecteds[0].theme;
			var dutyUnitName = selecteds[0].dutyUnitName;
			
			// 弹出窗
			var url = akapp.appname+"/powerSupplyGuaranteeDailyWin/index.jsp?taskId=" + taskId
				+ "&theme=" + theme + "&dutyUnitName=" + dutyUnitName + "&tabFlag=" + tabFlag;
			var title = theme + "保电日报";
			myak.open(url,title,null,function(action){
				
			});
		}else{
			showTips("info", "<b>注意</b> <br/>请勾选且只能勾选一条数据！");
		}
	});
	
	/**
	 * 导出按钮点击事件
	 */
	$("#exportBtn").on("click", function(){
		var tabName = "";
		switch(tabFlag){
			case "addTab":
				tabName = "新增";
				break;
			case "followTab":
				tabName = "需跟踪";
				break;
			case "closedTab":
				tabName = "已闭环";
				break;
			case "filedTab":
				tabName = "已归档";
				break;
			default:
				break;
		}
		var params = JSON.stringify({"tabType":tabFlag, "beginTime1":"", "beginTime2":"", "endTime1":"", "endTime2":"",
		 	"responsibleDepartmentId":"", "taskSource":"", "powerLevel":""});
		exak.exportPagesDataToExcel(grid, "primary", params, "保电任务（"+tabName+"）");
	});
	
	/**
	 * 发送按钮点击事件
	 */
	$("#sendBtn").on("click", function(){
		var selecteds = grid.getSelecteds();
		var length = selecteds.length;
		if(length == 1){
			// 弹出窗
			var data = [];
			data[0] = "";
//			data[1] = selecteds[0].theme+"工作汇报";
			data[1] = selecteds[0].theme;
//			data[2] = "";
			data[2] = selecteds[0].startTime + selecteds[0].endTime + selecteds[0].ensureLevel + "详见保电通知";
			data[3] = "";
			data[4] = 7;
//			var url = akapp.messageSendService+"/communicationMessageSend/index.jsp?state="+data;
			var url = akapp.messageSendService+"/communicationMessageSend/index.jsp?state="+encodeURIComponent(ak.encode(data));
			var title = "保电任务-发送";
			myak.open(url,title,null,function(action){
				
			});
		}else{
			showTips("info", "<b>注意</b> <br/>请勾选且只能勾选一条数据！");
		}
	});
	
	/**
	 * 跟踪按钮点击事件
	 */
	$("#followBtn").on("click", function(){
		var selecteds = grid.getSelecteds();
		var length = selecteds.length;
		if(length > 0){
			ak.confirm("记录将变为需跟踪状态，不可再删除。", "发送确认",
		            function(action){
		                if (action == "ok") {
		                	var concurrent = whetherConcurrent(selecteds);
		                	if(concurrent == "false"){
		                		var ids = "";
			                	for(var i=0; i<length; i++){
			                		if(i < (length-1)){
			                			ids += (selecteds[i].id + ",");
			                		}else{
			                			ids += selecteds[i].id;
			                		}
			                	}
			                	statusChange(ids, "2");
		                	}else{
		                		showTips("danger", "<b>注意</b> <br/>勾选的数据中保电主题为【"+concurrent+"】的数据存在并发操作，请稍候刷新数据再重复当前操作！");
		                	}
		                } else {
		                    return;
		                }
		            }
		        );
		}else{
			showTips("info", "<b>注意</b> <br/>请至少勾选一条数据！");
		}
	});
	
	/**
	 * 闭环按钮点击事件
	 */
	$("#closeBtn").on("click", function(){
		var selecteds = grid.getSelecteds();
		var length = selecteds.length;
		if(length > 0){
			ak.confirm("闭环后除简介图片其他信息不能更新</br>确定闭环记录？", "闭环确认",
		            function(action){
		                if (action == "ok") {
		                	// 检查所有选中保电任务相关联的所有保电站线的设备数据
		                	var solCheck = true;
		                	for(var i=0; i<length; i++){
		                		var data = [];
			        			var p_path = "/powerSupplyGuarantee/getStationOrLineResult";
			        	    	var params = {"taskId":selecteds[i].id, "voltageLevel":"", "departmentID":"", "solRightName":""};
			        	    	myak.send(p_path,'GET',params,false,function(result){
			        	    		data = result.data;
			        			});
			        	    	
			        	    	if(data.length == 0){
			        	    		showTips("info", "<b>注意</b> <br/>" + selecteds[i].theme + "未维护保电站线信息，请点击【编辑】先进行维护再闭环！");
			        	    		solCheck = false;
			        	    		break;
			        	    	}else{
			        	    		for(var j=0; j<data.length; j++){
			        	    			if(data[j].ensureLevel == null){
			        	    				showTips("info", "<b>注意</b> <br/>" + selecteds[i].theme + "未维护保电站线的保电等级信息，请点击【编辑】先进行维护再闭环！");
					        	    		solCheck = false;
					        	    		break;
			        	    			}else if(data[j].orgBelongID == null){
			        	    				showTips("info", "<b>注意</b> <br/>" + selecteds[i].theme + "未维护保电站线的所属组织信息，请点击【编辑】先进行维护再闭环！");
					        	    		solCheck = false;
					        	    		break;
			        	    			}
			        	    		}
			        	    	}
			        	    	
			        	    	if(!solCheck){
			        	    		break;
			        	    	}
		                	}
		                	
		                	if(solCheck){
		                		var concurrent = whetherConcurrent(selecteds);
			                	if(concurrent == "false"){
			                		var ids = "";
				                	for(var i=0; i<length; i++){
				                		if(i < (length-1)){
				                			ids += (selecteds[i].id + ",");
				                		}else{
				                			ids += selecteds[i].id;
				                		}
				                	}
				                	
				                	var hadSol = whetherHadSol();
				                	if(hadSol){
				                		statusChange(ids, "3");
				                	}
			                	}else{
			                		showTips("danger", "<b>注意</b> <br/>勾选的数据中保电主题为【"+concurrent+"】的数据存在并发操作，请稍候刷新数据再重复当前操作！");
			                	}
		                	}
		                }
		            }
		        );
		}else{
			showTips("info", "<b>注意</b> <br/>请至少勾选一条数据！");
		}
	});
	
	/**
	 * 归档按钮点击事件
	 */
	$("#fileBtn").on("click", function(){
		var selecteds = grid.getSelecteds();
		var length = selecteds.length;
		if(length > 0){
			ak.confirm("归档后所有信息不能更新</br>确定归档记录？", "归档确认",
		            function(action){
		                if (action == "ok") {
		                	var concurrent = whetherConcurrent(selecteds);
		                	if(concurrent == "false"){
		                		var ids = "";
		                    	for(var i=0; i<length; i++){
		                    		if(i < (length-1)){
		                    			ids += (selecteds[i].id + ",");
		                    		}else{
		                    			ids += selecteds[i].id;
		                    		}
		                    	}
		                    	statusChange(ids, "4");
		                	}else{
		                		showTips("danger", "<b>注意</b> <br/>勾选的数据中保电主题为【"+concurrent+"】的数据存在并发操作，请稍候刷新数据再重复当前操作！");
		                	}
		                }
			});
		}else{
			showTips("info", "<b>注意</b> <br/>请至少勾选一条数据！");
		}
	});
	
	/**
	 * 点击发送、闭环、归档按钮后记录状态改变方法
	 * 修改confirmStatus字段
	 */
	function statusChange(ids, status){
		var clickedBtnName = "";
		switch (status) {
		case "2":
			clickedBtnName = "跟踪";
			break;
		case "3":
			clickedBtnName = "闭环";
			break;
		case "4":
			clickedBtnName = "归档";
			break;
		default:
			break;
		}
		
		var params = {"ids":ids, "status":status, "flag":"statusChange"};
		var p_path = "/powerSupplyGuarantee/updatePowerSupplyGuarantee";
		myak.send(p_path,'POST',ak.encode(params),false,function(result){
			if(result.successful){
				grid.reload();
				getGridsCount();
				showTips("success", "<b>确认</b> <br/>" + clickedBtnName + "成功！");
			}else{
				showTips("danger", "<b>注意</b> <br/>" + clickedBtnName + "失败！");
			}
		});
	}
	
	/**
	 * 精确搜索按钮点击事件
	 */
	 var exactSearchClickCount = 0;
	 var ywdwTreeClickCount = 0;
	 function exactSearchClick(){
		$("#exactSearchBtn").on("click", function(){
			var flag=$("#searchArea").css("display");
			$("#searchArea").slideToggle();
			if(flag=="block"){
				$("#gridArea").css("height","99%");
				$("#exactSearchBtn").children("span")[0].innerHTML = "精确搜索 ﹀"
				flag="none";
			}else{
				ak("taskSourceSelect").load(akapp.appname+"/powerSupplyGuarantee/getTaskSource");
				ak("powerLevelSelect").load(akapp.appname+"/powerSupplyGuarantee/getPowerLever");
				
				/********************负责单位选择树********************/
				if(exactSearchClickCount == 0){
					$("#responsibleDepartmentSelect").on("click", function(){
						if(ywdwTreeClickCount == 0){
							ak("responsibleDepartmentSelect").on("beforeload",function(e){
								var type = e.node.nodeType;
								var id = e.node.id;
								if (typeof (type) == 'undefined') {
									e.params.nodeType = 'root';
								} else {
									e.params.id = id;
									e.params.nodeType = type;
								}
							});
							ak("responsibleDepartmentSelect").load(akapp.commonService+"/treeUtil/getYwdwTree.do");
							ywdwTreeClickCount++;
						}
					});
					exactSearchClickCount++;
				}
				
				$("#gridArea").css("height","80.5%");
				$("#exactSearchBtn").children("span")[0].innerHTML = "精确搜索 ︿"
				flag="block";
			}
			grid.reload();
		});
	 }
	 
	 /**
	  * 模糊查询（放大镜）
	  */
	 function searchImgClick(){
		 $("#searchImg").on("click", function(){
			 var keyWords = ak("keyWords").value;
			 var params ={"tabType":tabFlag, "keyWords":keyWords};
			 grid.load(params);
		 });
	 }
	 
	 /**
	  * 查询、重置按钮点击事件
	  */
	 $("#searchBtn").on("click", function(){
		 var beginTime1 = ak("beginTime1Select").text;					// 开始时间1
		 var beginTime2 = ak("beginTime2Select").text;					// 开始时间2
		 var endTime1 = ak("endTime1Select").text;						// 结束时间1
		 var endTime2 = ak("endTime2Select").text;						// 结束时间2
		 var responsibleDepartmentText = ak("responsibleDepartmentSelect").text;	// 负责单位
		 var responsibleDepartmentValue = ak("responsibleDepartmentSelect").value;
		 var taskSourceText = ak("taskSourceSelect").text;				// 任务来源
		 var taskSourceValue = ak("taskSourceSelect").value;
		 var powerLevelText = ak("powerLevelSelect").text;				// 保电级别
		 var powerLevelValue = ak("powerLevelSelect").value;
		 
		 var params = {"tabType":tabFlag, "beginTime1":beginTime1, "beginTime2":beginTime2, "endTime1":endTime1, "endTime2":endTime2,
				 	"responsibleDepartmentId":responsibleDepartmentValue, "taskSource":taskSourceValue, "powerLevel":powerLevelValue};
		 grid.load(params);
	 });
	 
	 $("#resetBtn").on("click", function(){
		 ak("beginTime1Select").setText();
		 ak("beginTime2Select").setText();
		 ak("endTime1Select").setText();
		 ak("endTime2Select").setText();
		 ak("responsibleDepartmentSelect").setValue("");
		 ak("taskSourceSelect").setValue("");
		 ak("powerLevelSelect").setValue("");
	 });
	 
	 /**
	  * 获取各阶段记录总数
	  */
	 function getGridsCount(){
		 var p_path = "/powerSupplyGuarantee/getGridsCount";
 		 myak.send(p_path,'GET',null,true,function(result){
 			var length = result.data.length;
	    	for(var i=0; i<length; i++){
				switch (result.data[i].status) {
					case 1:
						$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(0).attr("afterData", result.data[i].count);
						break;
					case 2:
						$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(1).attr("afterData", result.data[i].count);
						break;
					case 3:
						$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(2).attr("afterData", result.data[i].count);
						break;
					case 4:
						$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(3).attr("afterData", result.data[i].count);
						break;
					default:
						break;
				}
	    	}
 		 });
	 }
	 
	 /**
	  * 获取表格中数据
	  */
	 function getDataGrid(){
		 grid.on('drawcell',function(e) {
//		 	 e.source._rowsEl.style.color = "#ff3d58";
		 	
		 	 var divImg = "";
			 switch (e.field) {
				 case "ensureNotice":
					 if(e.row.ensureNotice != "" && e.row.ensureNotice != null){
						 divImg = "<img src='res/img/attachment_green.png' style='width:18px; cursor:pointer;' />";
						 e.cellHtml = divImg;
					 }
					 break;
				 case "introPicture":
					 if(e.row.introPicture != "" && e.row.introPicture != null){
						 divImg = "<img src='res/img/introPicture_white.png' style='width:18px; cursor:pointer;' />";
						 e.cellHtml = divImg;
					 }
					 break;
				 case "stationOrLine":
					 divImg = "<img src='res/img/stationOrLineText.png' style='width:30px; cursor:pointer;' />";
					 e.cellHtml = divImg;
					 break;
				 case "organizationInfo":
					 divImg = "<img src='res/img/organization.png' style='width:18px; cursor:pointer;' />";
					 e.cellHtml = divImg;
					 break;
				 default:
					 break;
			 }
			 
			 if(e.row.isSelected){
				 this.setSelected(e.row);
				 e.rowCls = "ak-grid-row-selected";
			 }
		 });
		 
		 grid.on("cellclick", function(e){
		 	var id = e.row.id;
			switch(e.field){
			 	case "ensureNotice":
			 		if(e.row.ensureNotice != null){
			 			attachmentClick(e.row.ensureNotice);
			 		}else{
			 			showTips("info", "<b>注意</b> <br/>该记录尚未上传相关附件,请先点击【编辑】按钮上传相关附件。");
			 		}
					break;
				case "introPicture":
					if(e.row.introPicture != null){
						attachmentClick(e.row.introPicture);
					}else{
						showTips("info", "<b>注意</b> <br/>该记录尚未上传简介图片,请先点击【编辑】按钮上传简介图片。");
					}
					break;
				case "stationOrLine":
					var data = {};
					data = e.row;
					data.triggerType = "grid";
					data.triggerObj = "涉及站线";
					var params = JSON.stringify(data);
					
					// 弹出窗
					var url = akapp.appname+"/powerSupplyGuaranteeWin/index.jsp?params="+encodeURIComponent(params);
					var title = "保电任务-查看";
					myak.open(url,title,null,function(action){
						
					});
					break;
				case "organizationInfo":
					var data = {};
					data = e.row;
					data.triggerType = "grid";
					data.triggerObj = "组织信息";
					var params = JSON.stringify(data);
					
					// 弹出窗
					var url = akapp.appname+"/powerSupplyGuaranteeWin/index.jsp?params="+encodeURIComponent(params);
					var title = "保电任务-查看";
					myak.open(url,title,null,function(action){
						
					});
					break;
				default:
					break;
			 }
		 });
		 
		 var params = {"tabType":tabFlag, "beginTime1":"", "beginTime2":"", "endTime1":"", "endTime2":"",
				 	"responsibleDepartmentId":"", "taskSource":"", "powerLevel":""};
		 
		 if(dailyParam != null){//传入参数中加入记录ID条件过滤
			 params.recordId = dailyParam.recordId;
			 dailyParam = null;//移除dailyParam
		 }
		 
		 grid.load(params, function(e){
			 myak.setPageIndex(e);//跳转到对应页
			 myak.removeLoadParam(e,"recordId");//移除参数recordId
		 });
	 }
	 
	 /**
	  * 附件点击事件
	  */
	 function attachmentClick(id){
		 if(id == "" || id == null){
			var p_path = "/powerSupplyGuarantee/getId";
    		myak.send(p_path,'GET',null,false,function(data){
    			id = data.id;
    		});
		 }
		 
		 var iframe = null;
		 var win = ak.open({
			 title: '附件上传',
//		         url:"http://10.144.15.224:9091?url=/user/mcsas/{业务表名}/{业务数据的唯一标示}/&readOnly=true",
			 url:akapp.commonFileUpload+"/upload.html?url=/user/mcsas/electricityEnsure/"+id+"/&readOnly=true",
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
           		 iframe = this.getIFrameEl(); 
           		 var data = {};   
//		           		 iframe.contentWindow.Urlll("");
//		           		 调用弹出页面方法进行初始化
           	 },
           	 ondestroy: function(e){
           		var data = iframe.contentWindow.initialize.allreturn();
           		if(data.length > 0){
           			var urlId = data[0].url.substr(0, data[0].url.length-1).split("/user/mcsas/electricityEnsure/")[1];
           			
           		}
           	 }
	    });

        win.showAtPos("330px", "120px");
	 }
	 
	 /**
	  * 判断是否并发方法
	  * 返回“false”表示未出现并非操作，返回数字n表示勾选的第n条记录存在并发操作
	  */
	function whetherConcurrent(data){
		var concurrent = "false";
		var length = data.length;
		for(var i=0; i<length; i++){
			var id = data[i].id;
			var updateDateTime = data[i].updateDateTime.getTime();
			var params = {"id":id, "tableName":"sgcim30.assets.management.ElectricityEnsure"};
			myak.send("/powerSupplyGuarantee/getUpdateTime",'GET',params,false,function(result){
				var time = new Date(result[0].updateDateTime).getTime();	// 转换为毫秒
				if(time != updateDateTime){
					concurrent = data[i].theme;
				}
			});
			
			if(concurrent != "false"){
				break;
			}
		}
		return concurrent;
	}
	
	/**
	 * 点击闭环时判断勾选的保电任务（可多选）是否已关联保电站线
	 * true:已关联, false:未关联
	 * Sol: StationOrLine缩写
	 */
	function whetherHadSol(){
		var hadSol = true;
		var rows = grid.getSelecteds();
		var l = rows.length;
		
		for(var i=0; i<l; i++){
			var params = {"id":rows[i].id};
			myak.send("/powerSupplyGuarantee/getWhetherHadSol",'GET',params,false,function(result){
				if(result.length < 1){
					hadSol = false;
					var theme = rows[i].theme;
					showTips("danger", "<b>注意</b> <br/>勾选的数据中保电主题为【"+theme+"】的保电任务未维护保电站线数据，请先进行维护再闭环！");
				}
			});
			
			if(!hadSol){
				break;
			}
		}
		
		return hadSol;
	}
	
	return me;
})

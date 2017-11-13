define(['akui', 'util', 'exportData', 'akapp', 'myCommon'],function(ak, myak, exak, akapp, myCommon){
	var me = {};
	ak.parse();
	
	me.taskId = "";
	me.theme = "";
	me.dutyUnitName = "";
	me.tabFlag = "";
	me.treeList = null;
	
	me.logCloseEdit = false;
	me.processCloseEdit = false;
	
	var logGrid = ak("logGrid");
	var processGrid = ak("processGrid");
	
	/**
	 * 初始化方法
	 */
	me.render = function(){
		// 获取参数
		var params = decodeURI(window.location.search).split("&");
		me.taskId = params[0].split("=")[1];
		me.theme = params[1].split("=")[1];
		me.dutyUnitName = params[2].split("=")[1];
		me.tabFlag = params[3].split("=")[1];
		
		logGrid.allowCellEdit = false;		// 取消所有行编辑
		processGrid.allowCellEdit = false;	// 取消所有行编辑
		
		aboutDatepicker();		// 关于日期选择框
		getLogGrid();			// 获取汇报记录表格数据
		getProcessGrid();		// 获取保电过程表格数据
		functionBtnsClick();	// 各功能按钮点击事件
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
	  * 获取当天时间（精确到天，格式：YYYY-MM-DD）
	  */
	function getToday(){
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		if(month < 10){
			month = "0" + month;
		}
		var day = date.getDate();
		if(day < 10){
			day = "0" + day;
		}
		var today = year + "-" + month + "-" + day;
		
		return today;
	}
	
	/**
	 * 关于日期选择框
	 */
	function aboutDatepicker(){
		var today = getToday();
		ak("date").setValue(today);
		
		ak("date").on("valuechanged", function(e){
			var time = ak("date").getText();
			if(time != today){
				$("#logCopyBtn").css("display","block");
			}else{
				$("#logCopyBtn").css("display","none");
			}
			
			var params = {"taskId":me.taskId, "logDayTime":time};
			logGrid.load(params);
			processGrid.load(params);
		});
	}
	
	/**
	 * 获取汇报记录表格数据
	 */
	function getLogGrid(){
		logGrid.on('drawcell',function(e) {
		 	 var divImg = "";
			 switch (e.field) {
			 	case "workReport":
			 		if(ak("workReport")._IsValid != undefined && ak("workReport")._IsValid == false){
			 			showTips("info", "<b>注意</b> <br/>汇报记录表格中值班汇报字数太长，请重新填写！（0~2000个字符）");
			 		}
			 		break;
			 	case "reportObject":
			 		if(ak("reportObject")._IsValid != undefined && ak("reportObject")._IsValid == false){
			 			showTips("info", "<b>注意</b> <br/>汇报记录表格中汇报对象字数太长，请重新填写！（0~1000个字符）");
			 		}
			 		break;
			 	case "otherAccessory":
					 if(e.value != null){
						 divImg = "<img src='res/img/attachment_green.png' style='width:18px; cursor:pointer;' />";
						 e.cellHtml = divImg;
					 }
					 break;
			 	case "remark":
			 		if(ak("logRemark")._IsValid != undefined && ak("logRemark")._IsValid == false){
			 			showTips("info", "<b>注意</b> <br/>汇报记录表格中备注字数太长，请重新填写！（0~1000个字符）");
			 		}
			 		break;
				 default:
					 break;
				}
		 	});
		
		logGrid.on("cellclick", function(e){
			switch(e.field){
			 	case "otherAccessory":
			 		var id = e.row.otherAccessory;
			 		if(logGrid.allowCellEdit){
			 			attachmentClick("electricityEnsureLog", id, false, "log_otherAccessory");
			 		}else{
			 			if(me.logCloseEdit){
			 				attachmentClick("electricityEnsureLog", id, false, "log_otherAccessory");
			 			}else{
			 				if(id == undefined || id == ""){
				 				if(me.tabFlag != "filedTab"){
				 					showTips("info", "<b>注意</b> <br/>该记录尚未上传附件,请先点击【编辑】按钮上传附件。");
				 				}else{
				 					showTips("info", "<b>注意</b> <br/>该记录尚未上传附件。");
				 				}
					 		}else{
					 			attachmentClick("electricityEnsureLog", id, true, "log_otherAccessory");
					 		}
			 			}
			 		}
					break;
				default:
					break;
			 }
		 });
		
		var today = getToday();
		logGrid.load(akapp.appname+'/powerSupplyGuarantee/getLog');
		var params = {"taskId":me.taskId, "logDayTime":today};
		logGrid.load(params);
	}
	
	/**
	 * 获取保电过程表格数据
	 */
	function getProcessGrid(){
		var p_path = "/powerSupplyGuarantee/getOrganizationTree";	// 获取组织机构树
		var params = {"taskId":me.taskId};
    	myak.send(p_path,'GET',params,false,function(result){
    		me.treeList = result;
		});
		
		processGrid.on('drawcell',function(e) {
		 	 var divImg = "";
			 switch (e.field) {
			 	case "unit":
			 		var l = me.treeList.length;
			 		for(var i=0; i<l; i++){
			 			if(e.value == me.treeList[i].id){
			 				e.cellHtml = me.treeList[i].text;
			 				break;
			 			}
			 		}
			 		break;
			 	case "dutyPerson":
			 		if(ak("dutyPerson")._IsValid != undefined && ak("dutyPerson")._IsValid == false){
			 			showTips("info", "<b>注意</b> <br/>保电过程表格中负责人字数太长，请重新填写！（0~100个字符）");
			 		}
			 		break;
			 	case "personCount":
			 		if(e.value == 0){
			 			e.cellHtml = "";
			 		}
			 		break;
			 	case "vehicleCount":
			 		if(e.value == 0){
			 			e.cellHtml = "";
			 		}
			 		break;
			 	case "majorWork":
			 		if(ak("majorWork")._IsValid != undefined && ak("majorWork")._IsValid == false){
			 			showTips("info", "<b>注意</b> <br/>保电过程表格中主要保电工作字数太长，请重新填写！（0~2000个字符）");
			 		}
			 		break;
			 	case "problem":
			 		if(ak("problem")._IsValid != undefined && ak("problem")._IsValid == false){
			 			showTips("info", "<b>注意</b> <br/>保电过程表格中存在问题字数太长，请重新填写！（0~2000个字符）");
			 		}
			 		break;
			 	case "needHelpThing":
			 		if(ak("needHelpThing")._IsValid != undefined && ak("needHelpThing")._IsValid == false){
			 			showTips("info", "<b>注意</b> <br/>保电过程表格中需协调事项字数太长，请重新填写！（0~2000个字符）");
			 		}
			 		break;
			 	case "relatedAccessory":
					 if(e.value != null){
						 divImg = "<img src='res/img/attachment_green.png' style='width:18px; cursor:pointer;' />";
						 e.cellHtml = divImg;
					 }
					 break;
			 	case "remark":
			 		if(ak("processRemark")._IsValid != undefined && ak("processRemark")._IsValid == false){
			 			showTips("info", "<b>注意</b> <br/>保电过程表格中备注字数太长，请重新填写！（0~1000个字符）");
			 		}
			 		break;
				default:
					break;
			}
			
			var unit = e.row.unit;
			var personCount = e.row.personCount;
			var vehicleCount = e.row.vehicleCount;
			if(unit == null || personCount == 0 || vehicleCount == 0){
				e.cellStyle = "color:red";
			}
		});
		
		processGrid.on("cellclick", function(e){
		 	var id = e.row.id;
			switch(e.field){
				case "unit":
			 		if(processGrid.allowCellEdit){
			 			ak("unit").loadList(me.treeList, "id", "pid");
			 		}
					break;
				case "personCount":
			 		$("#personCount").children("span").children("span").children(".ak-buttonedit-button").css("width", "16px");
					break;
				case "vehicleCount":
			 		$("#vehicleCount").children("span").children("span").children(".ak-buttonedit-button").css("width", "16px");
					break;
			 	case "relatedAccessory":
			 		var id = e.row.relatedAccessory;
			 		if(processGrid.allowCellEdit){
			 			attachmentClick("electricityEnsureProcess", id, false, "process_relatedAccessory");
			 		}else{
			 			if(me.processCloseEdit){
			 				attachmentClick("electricityEnsureProcess", id, false, "process_relatedAccessory");
			 			}else{
			 				if(id == undefined || id == ""){
				 				if(me.tabFlag != "filedTab"){
				 					showTips("info", "<b>注意</b> <br/>该记录尚未上传附件,请先点击【编辑】按钮上传附件。");
				 				}else{
				 					showTips("info", "<b>注意</b> <br/>该记录尚未上传附件。");
				 				}
					 		}else{
					 			attachmentClick("electricityEnsureProcess", id, true, "process_relatedAccessory");
					 		}
			 			}
			 		}
					break;
				default:
					break;
			 }
		 });
		
		var today = getToday();
		processGrid.load(akapp.appname+'/powerSupplyGuarantee/getProcess');
		var params = {"taskId":me.taskId, "logDayTime":today};
		processGrid.load(params);
	}
	
	/**
	 * 各功能按钮点击事件
	 */
	function functionBtnsClick(){
		if(me.tabFlag != "closedTab" && me.tabFlag != "filedTab"){
			//////// 汇报记录
			// 新增
			$("#logAddBtn").on("click", function(){
				logGrid.allowCellEdit = true;		// 允许所有行编辑
				
				var newRow = { name: "New Row" };
				logGrid.addRow(newRow, logGrid.data.length);
			});
			
			// 删除
			$("#logDeleteBtn").on("click", function(){
				var data = logGrid.data;
				var l1 = data.length;
				var lackId = false;
				for(var i=0; i<l1; i++){
					if(data[i].id == undefined){
						lackId = true;
						showTips("info", "<b>注意</b> <br/>汇报记录表格中有未保存数据，请先进行保存。");
						break;
					}
				}
				
				if(!lackId){
					var rows = logGrid.getSelecteds();
					var l2 = rows.length;
					var ids = "";
					for(var i=0; i<l2; i++){
						if(i == 0){
							ids = rows[0].id;
						}else{
							ids += ("," + rows[i].id);
						}
					}
					
					ak.confirm("确定要删除选中的"+l2+"条记录吗？", "删除确认",
			            function(action){
			                if (action == "ok") {
			                	var p_path = "/powerSupplyGuarantee/deleteLog";
			                	var params = {"ids":ids};
			                	myak.send(p_path,'GET',params,false,function(result){
			                		if(result.successful){
			                			logGrid.reload();
				            			showTips("success", "<b>确认</b> <br/>删除成功！");
			                		}else{
			                			showTips("danger", "<b>注意</b> <br/>删除失败！");
			                		}
			            		});
			                } else {
			                    return;
			                }
			            }
			        );
				}
			});
			
			// 复制
			$("#logCopyBtn").on("click", function(){
				var rows = logGrid.getSelecteds();
				var l = rows.length;
				if(l != 1){
					showTips("info", "<b>注意</b> <br/>请从汇报记录表中勾选一条记录！");
					return;
				}else{
					var p_path = "/powerSupplyGuarantee/copyLog";
	            	var params = {"belongTaskId":rows[0].belongTaskId, "workReport":rows[0].workReport,
	            			"reportObject":rows[0].reportObject, "otherAccessory":rows[0].otherAccessory,
	            			"remark":rows[0].remark
	            	};
	            	myak.send(p_path,'GET',params,false,function(result){
	            		if(result.successful){
	            			showTips("success", "<b>确认</b> <br/>复制成功！");
	            		}else{
	            			showTips("danger", "<b>注意</b> <br/>复制失败！");
	            		}
	        		});
				}
			});
			
			// 发送
			$("#logSendBtn").on("click", function(){
				var today = getToday();
				var selecteds = logGrid.getSelecteds();
				var length = selecteds.length;
				var logDate = "";
				var reportObject = "";
				var workReport = "";
				
				if(selecteds[0].logDate != null){
					logDate = selecteds[0].logDate;
				}
				if(selecteds[0].reportObject != null){
					reportObject = selecteds[0].reportObject;
				}
				if(selecteds[0].workReport != null){
					workReport = selecteds[0].workReport;
				}
				
				if(length == 1){
					// 弹出窗
					var data = [];
					data[0] = "";
					data[1] = me.theme+"-日报";
//					data[2] = today + " " + me.theme + " " + me.dutyUnitName + " " + selecteds[0].workReport;
					data[2] = logDate + "已向" + reportObject + "汇报:" + workReport;
					data[3] = "";
					data[4] = 7;
//					var url = akapp.messageSendService+"/communicationMessageSend/index.jsp?state="+data;
					var url = akapp.messageSendService+"/communicationMessageSend/index.jsp?state="+encodeURIComponent(ak.encode(data));
					var title = "保电任务-日报-汇报记录-发送";
					myak.open(url,title,null,function(action){
						
					});
				}else{
					showTips("info", "<b>注意</b> <br/>请勾选且只能勾选一条数据！");
				}
			});
			
			//////// 保电过程
			// 新增
			$("#processAddBtn").on("click", function(){
				processGrid.allowCellEdit = true;		// 允许所有行编辑
				
				var newRow = { name: "New Row" };
				processGrid.addRow(newRow, processGrid.data.length);
			});
			
			// 删除
			$("#processDeleteBtn").on("click", function(){
				var data = processGrid.data;
				var l1 = data.length;
				var lackId = false;
				for(var i=0; i<l1; i++){
					if(data[i].id == undefined){
						lackId = true;
						showTips("info", "<b>注意</b> <br/>保电过程表格中有未保存数据，请先进行保存。");
						break;
					}
				}
				
				if(!lackId){
					var rows = processGrid.getSelecteds();
					var l2 = rows.length;
					var ids = "";
					for(var i=0; i<l2; i++){
						if(i == 0){
							ids = rows[0].id;
						}else{
							ids += ("," + rows[i].id);
						}
					}
					
					ak.confirm("确定要删除选中的"+l2+"条记录吗？", "删除确认",
			            function(action){
			                if (action == "ok") {
			    				var p_path = "/powerSupplyGuarantee/deleteProcess";
			                	var params = {"ids":ids};
			                	myak.send(p_path,'GET',params,false,function(result){
			                		if(result.successful){
			                			processGrid.reload();
				            			showTips("success", "<b>确认</b> <br/>删除成功！");
			                		}else{
			                			showTips("danger", "<b>注意</b> <br/>删除失败！");
			                		}
			            		});
			                } else {
			                    return;
			                }
			            }
			        );
				}
			});
			
			// 导入
			$("#processImportBtn").on("click", function(){
				myCommon.importExcelData(me.taskId, "maintainPower_process", function(returnInfo){
					if(returnInfo == "导入成功！"){
						showTips("success", "<b>确认</b> <br/>" + returnInfo);
						processGrid.reload();
					}else{
						showTips("danger", "<b>注意</b> <br/>" + returnInfo);
					}
				});
			});
		}
		
		if(me.tabFlag != "filedTab"){
			// 汇报记录编辑
			$("#logEditBtn").on("click", function(){
				if(logGrid.data.length < 1){
					showTips("info", "<b>注意</b> <br/>汇报记录表格中暂无数据，请先新增数据。");
				}else{
					if(me.tabFlag != "closedTab"){
						logGrid.allowCellEdit = true;		// 允许所有行编辑
						showTips("info", "<b>提示</b> <br/>汇报记录表格的编辑权限已放开，请直接单击单元格进行编辑。");
					}else{
						me.logCloseEdit = true;
						showTips("info", "<b>提示</b> <br/>汇报记录表格的【附件】编辑权限已放开，请直接单击单元格进行编辑。");
					}
				}
			});
			
			// 保电过程编辑
			$("#processEditBtn").on("click", function(){
				if(processGrid.data.length < 1){
					showTips("info", "<b>注意</b> <br/>保电过程表格中暂无数据，请先新增数据。");
				}else{
					if(me.tabFlag != "closedTab"){
						processGrid.allowCellEdit = true;		// 允许所有行编辑
						showTips("info", "<b>注意</b> <br/>保电过程表格的编辑权限已放开，请直接单击单元格进行编辑。");
					}else{
						me.processCloseEdit = true;
						showTips("info", "<b>提示</b> <br/>保电过程表格的【附件】编辑权限已放开，请直接单击单元格进行编辑。");
					}
				}
			});
			
			////////整体保存按钮
			$("#saveBtn").on("click", function(){
				var logGridData = logGrid.data;
				var gridLength1 = logGridData.length;
				var processGridData = processGrid.data;
				var gridLength2 = processGridData.length;
				var logDayTime = ak("date").text;
				
				var savable = true;
				if(me.tabFlag != "closedTab" && me.tabFlag != "filedTab"){
					// 汇报记录
					for(var i=0; i<gridLength1; i++){
						if(logGridData[i].workReport != undefined){
							if(len(logGridData[i].workReport) > 2000){
								showTips("info", "<b>注意</b> <br/>汇报记录表格中第"+(i+1)+"行的值班汇报字数太长，请重新填写！（0~2000个字符）");
								savable = false;
								break;
							}
						}
						
						if(logGridData[i].reportObject != undefined){
							if(len(logGridData[i].reportObject) > 1000){
								showTips("info", "<b>注意</b> <br/>汇报记录表格中第"+(i+1)+"行的汇报对象字数太长，请重新填写！（0~1000个字符）");
								savable = false;
								break;
							}
						}
						
						if(logGridData[i].remark != undefined){
							if(len(logGridData[i].remark) > 1000){
								showTips("info", "<b>注意</b> <br/>汇报记录表格中第"+(i+1)+"行的备注字数太长，请重新填写！（0~1000个字符）");
								savable = false;
								break;
							}
						}
					}
					
					// 保电过程
					for(var i=0; i<gridLength2; i++){
						if(processGridData[i].unit == undefined){
							savable = false;
							showTips("danger", "<b>注意</b> <br/>请录入保电过程表格中第"+(i+1)+"行的单位！");
							break;
						}
						
						if(processGridData[i].dutyPerson != undefined){
							if(len(processGridData[i].dutyPerson) > 100){
								showTips("info", "<b>注意</b> <br/>保电过程表格中第"+(i+1)+"行的负责人字数太长，请重新填写！（0~100个字符）");
								savable = false;
								break;
							}
						}
						
						if(processGridData[i].personCount == undefined || processGridData[i].personCount == 0){
							savable = false;
							showTips("danger", "<b>注意</b> <br/>请录入保电过程表格中第"+(i+1)+"行的人数！");
							break;
						}
						
						if(processGridData[i].vehicleCount == undefined || processGridData[i].vehicleCount == 0){
							savable = false;
							showTips("danger", "<b>注意</b> <br/>请录入保电过程表格中第"+(i+1)+"行的车辆数！");
							break;
						}
						
						if(processGridData[i].majorWork != undefined){
							if(len(processGridData[i].majorWork) > 2000){
								showTips("info", "<b>注意</b> <br/>保电过程表格中第"+(i+1)+"行的主要保电工作字数太长，请重新填写！（0~2000个字符）");
								savable = false;
								break;
							}
						}
						
						if(processGridData[i].problem != undefined){
							if(len(processGridData[i].problem) > 2000){
								showTips("info", "<b>注意</b> <br/>保电过程表格中第"+(i+1)+"行的存在问题字数太长，请重新填写！（0~2000个字符）");
								savable = false;
								break;
							}
						}
						
						if(processGridData[i].needHelpThing != undefined){
							if(len(processGridData[i].needHelpThing) > 2000){
								showTips("info", "<b>注意</b> <br/>保电过程表格中第"+(i+1)+"行的需协调事项字数太长，请重新填写！（0~2000个字符）");
								savable = false;
								break;
							}
						}
						
						if(processGridData[i].remark != undefined){
							if(len(processGridData[i].remark) > 2000){
								showTips("info", "<b>注意</b> <br/>保电过程表格中第"+(i+1)+"行的备注字数太长，请重新填写！（0~2000个字符）");
								savable = false;
								break;
							}
						}
					}
				}
				
				if(savable){
					var url = "/powerSupplyGuarantee/insertOrUpdateDaily";
					var params = {
				    	"taskId":me.taskId, "logDayTime":logDayTime,
				    	"gridData1":logGridData, "gridLength1":gridLength1,
				    	"gridData2":processGridData, "gridLength2":gridLength2
				    };
					myak.send(url,'POST',ak.encode(params),false,function(result){
						if(result.successful){
							logGrid.allowCellEdit = false;		// 取消所有行编辑
							processGrid.allowCellEdit = false;		// 取消所有行编辑
							
							me.logCloseEdit = false;		// 汇报记录附件不可编辑
							me.processCloseEdit = false;	// 保电过程附件不可编辑
							
							logGrid.reload();
							processGrid.reload();
							
							showTips("success", "<b>确认</b> <br/>保存成功！");
						}else{
							showTips("danger", "<b>注意</b> <br/>保存失败！");
						}
					});
				}
			});
		}else{
			ak("gridAndButtonArea").hidePane(2);
		}
		
		// 导出
		$("#processExportBtn").on("click", function(){
			var day = ak("date").text;
			var params = JSON.stringify({'taskId':me.taskId, "logDayTime":day});
			exak.exportPagesDataToExcel(processGrid, "process", params, "保电任务-日报-保电过程（"+day+"）");
		});
		
		// 模板
		$("#processTemplateBtn").on("click", function(){
			myCommon.downLoadExcelTemplate("maintainPower_process");
		});
	}
	
	/**
	 * 判断字符长度（包括汉字、英文字符）
	 */
	function len(str){
		var l = 0;
		if(str != null){
			var a = str.split("");
			for(var i=0; i<a.length; i++){
				if(a[i].charCodeAt(0) < 299){
					l++;
				}else{
					l += 2;
				}
			}
		}
		return l;
	}
	
	/**
	 * 附件弹出框
	 * @param tableName
	 * @param id
	 * @param fileFlag
	 * @param fieldsName
	 * @returns
	 */
	function attachmentClick(tableName, id, fileFlag, fieldsName){
		 if(id == "" || id == null){
			var p_path = "/powerSupplyGuarantee/getId";
	   		myak.send(p_path,'GET',null,false,function(data){
	   			id = data.id;
	   		});
		 }
		 
		 var iframe = null;
		 var win = ak.open({
			 title: '附件上传',
//		     url:"http://10.144.15.224:9091?url=/user/mcsas/{业务表名}/{业务数据的唯一标示}/&readOnly=true",
			 url:akapp.commonFileUpload+"/upload.html?url=/user/mcsas/"+tableName+"/"+id+"/&readOnly="+fileFlag,
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
//          		 var urlId = data[0].url.substr(0, data[0].url.length-1).split("/user/mcsas/electricityEnsure/")[1];
          			 var urlId = data[0].url.split("/")[4];
          			 switch (fieldsName) {
						case "log_otherAccessory":
							var row = logGrid.getSelected();
			           		row.otherAccessory = urlId;
							break;
						case "process_relatedAccessory":
							var row = processGrid.getSelected();
			           		row.relatedAccessory = urlId;
						default:
							break;
					}
          		}
          	 }
	    });

       win.showAtPos("330px", "120px");
	 }
	
	return me;
})

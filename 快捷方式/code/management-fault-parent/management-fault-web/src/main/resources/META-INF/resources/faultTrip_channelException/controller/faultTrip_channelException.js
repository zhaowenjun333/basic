define([ 'akui','util','akapp'], function( ak,myak,akapp ) { 
	var me={};
	me.type="add";
	var LineNameTree = ak("LineName"); //  线路
	var stationTree = ak("station"); //战
	var deviceNameTree= ak("deviceName");  // 设备树
	var deviceTypeTree= ak("deviceType"); 
	var datagria = ak("datatail") ;
	var gridRiskID = null;
	var attachment = ak("attachment"); 
	me.render = function() { 
		//查看编辑新增,只有初始化下拉才可以赋值，所以要先初始化
		initwaitingselect();//初始化下拉选择框
		_initAllDrop();
		var params = decodeURI(window.location.search); 
	    params=params.split('?data=')[1].split('&');//截取参数
		if(params[0] != ''){//查看和编辑
			params=JSON.parse('{'+ params[0]+'}');
			me.type = params.edit; 
/*++++++++++==+++++++++++++++++++++++++++++++++css+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	*/		
	    	ak("organization").setValue(params.organizationId) ; 
			ak("organization").setText(params.organization) ; 
			ak("stationOrLineName").setValue(params.stationOrLineId) ; 
	    	ak("stationOrLineName").setText(params.stationOrLineName) ; 
	    	ak("deviceName").setValue(params.deviceId) ; 
	    	ak("deviceName").setText(params.deviceName) ; 
	    	ak("faultTime").setValue(params.faultTime) ; 
	    	ak("attachment").setValue(params.attachment) ; 
	    	ak("attachmentpath").setValue(params.attachmentPath) ; 
	    	ak("weather").setValue(params.weather) ; 
	    	ak("updateDateTime").setValue(params.updateDateTime) ; 
	    	/*获取编码*/
	    	var voltageLevelcode="";
	    	var FaultCategorycode="";
	    	var FaultTypecode="";
	    	var faultPhaseIdcode="";
	    	 for(var i=0;i<ak("voltageLevel").data.length;i++){
	    		 if(ak("voltageLevel").data[i].text==params.voltageLevel){
	    			 voltageLevelcode=ak("voltageLevel").data[i].id;
	    		 }
	    	 }
	    	 for(var i=0;i<ak("faultCategory").data.length;i++){
	    		 if(ak("faultCategory").data[i].text==params.faultCategory){
	    			 FaultCategorycode=ak("faultCategory").data[i].id;
	    		 }
	    	 }
	    	 for(var i=0;i<ak("faultType").data.length;i++){
	    		 if(ak("faultType").data[i].text==params.faultType){
	    			 FaultTypecode=ak("faultType").data[i].id;
	    		 }
	    	 }
	    	 for(var i=0;i<ak("faultPhaseId").data.length;i++){
	    		 if(ak("faultPhaseId").data[i].text==params.faultPhaseId){
	    			 faultPhaseIdcode=ak("faultPhaseId").data[i].id;
	    		 }
	    	 }
	    	 	ak("voltageLevel").setValue(voltageLevelcode) ; 
		    	ak("faultCategory").setValue(FaultCategorycode) ;  
		    	ak("faultType").setValue(FaultTypecode) ; 
		    	ak("faultPhaseId").setValue(faultPhaseIdcode) ; 
		    	 for(var i=0;i<ak("repairOrNot").data.length;i++){
	    			 if(ak("repairOrNot").data[i].text==params.repairOrNot){
	    				 repairOrNotcode=ak("repairOrNot").data[i].id;
	    				 ak("repairOrNot").setValue(repairOrNotcode);
	    			 }
	    		 }
	    		 for(var i=0;i<ak("repairEndOrNot").data.length;i++){
	    			 if(ak("repairEndOrNot").data[i].text==params.repairEndOrNot){
	    				 repairEndOrNotcode=ak("repairEndOrNot").data[i].id;
	    				 ak("repairEndOrNot").setValue(repairEndOrNotcode);
	    			 }
	    		 }
	    		 for(var i=0;i<ak("threeSpanOrNot").data.length;i++){
	    			 if(ak("threeSpanOrNot").data[i].text==params.threeSpanOrNot){
	    				 threeSpanOrNotcode=ak("threeSpanOrNot").data[i].id;
	    				  ak("threeSpanOrNot").setValue(threeSpanOrNotcode);
	    			 }
	    		 }
	    		 ak("powerOutTime").setValue(params.powerOutTime); 
	    		 ak("recoveryDate").setValue(params.recoveryDate); 
	    		 ak("powerOutReason").setValue(params.powerOutReason); 
	    		 ak("faultLocator").setValue(params.faultLocator);
	    		 //分页组件
	    		 var datajson={};
	    		 datajson.warnNum=params.id;
	    		 datagria.load(akapp.appname +'/fault/queryWarnTrackStatusResult');
	    		 datajson.pageIndex =0 ;
	    		 datajson.pageSize =10 ;
	    		 datagria.load(datajson,function(e){ 
	    			 
	    			 
	    			 dataResult = ak.decode(e.data);
		    		 if(dataResult !=null && dataResult.length>0){
				 		 me.WarnTrackLength=dataResult.length;//获取后台查询跟踪情况的表格长度
				 		 for(var i=1;i<=dataResult.length;i++){
				 		 }
				 		ak("trackContent").setValue( dataResult[0].trackContent);
				 	 }
//	    			 myak.setPageIndex(e);//跳转到对应页
	    		});
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++业务判断+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/		    
		    //编辑的时候
		    if(me.type == "编辑"){
		    	if(params.status==1 || params.status ==2){
		    		$(".searchPic").on("click",function(){
		    			var newRow = {};
		    			datagria.addRow(newRow, datagria.data.length);
		    			datagria.beginEditRow (datagria.data.length-1 )  ;
		    		});
		    	}
		    	 ak("faultTime").readOnly=true;
		    	 ak("faultCategory").readOnly=true;
		    	 ak("faultPhaseId").readOnly=true;
		    	 if(params.status==3 || params.status ==4 ){
		    		 ak("faultType").readOnly=true;
			    	 ak("organization").readOnly=true;
			    	 ak("voltageLevel").readOnly=true;
			    	 ak("stationOrLineName").readOnly=true;
			    	 ak("deviceName").readOnly=true;
			    	 ak("weather").readOnly=true;
			    	 ak("threeSpanOrNot").readOnly=true;
			    	 ak("repairEndOrNot").readOnly=true;
			    	 ak("repairOrNot").readOnly=true;
			    	 ak("faultLocator").readOnly=true;
			    	 ak("recoveryDate").readOnly=true;
			    	 ak("trackContent").readOnly=true;
		    	 }
		    	//新增的状态编辑可以，改写
		    		var count=1 ;
				$(".words").click(function(){
					
					
					if(count==1){
						 
					 	var form = new ak.Form("#form");            
						var data = form.getData(); 
						if(data.weather.length> 25){
							showtips("天气最大长度21,请重新填写","info");
							return;
						}
						if(data.powerOutReason.length> 21){
							showtips("拉停原因最大长度21,请重新填写","info");
							return;
						} 
						if(data.faultLocator.length> 50){
							showtips("处理详情最大长度50,请重新填写","info");
							return;
						}
						
						data.id=params.id;
						data.status=params.status;
						data.updateDateTime=params.updateDateTime;
						data.stationOrLineName=ak("stationOrLineName").getText();//抢修站线
						data.deviceName=ak("deviceName").getText();//抢修设备
						data.organization=ak("organization").getText();//运维单位名称
						//获取id 
						data.stationOrLineId=ak("stationOrLineName").getValue();
						data.deviceId=ak("deviceName").getValue();
						data.organizationId=ak("organization").getValue();
						if(data.repairOrNot=="1"){
//							先从抢修模块查询有没有之前插入的数据，如果有就不用插入了。可以更新。
							var faultid={};
							faultid.id=params.id;
							myak.send('/emergencyrepair/queryByFaultId','GET',faultid,false,function(text){
//								返回值是一个json对象
								if(text.length == 0){
//									插入应急抢修一条记录；
									var qxmk={};
									qxmk.stationOrLineName=ak("stationOrLineName").getText();//抢修站线
									qxmk.deviceName=ak("deviceName").getText();//抢修设备
									qxmk.organization=ak("organization").getText();//运维单位名称
									qxmk.stationOrLineId=ak("stationOrLineName").getValue();//抢修站线
									qxmk.deviceId=ak("deviceName").getValue();//抢修设备
									qxmk.organizationId=ak("organization").getValue();//运维单位名称
									qxmk.voltageLevel=ak("voltageLevel").getValue();//电压等级
									qxmk.id=params.id; //故障id
//									qxmk.repairEndOrNot=ak("repairEndOrNot").getValue();
//									qxmk.recoveryDate=ak("recoveryDate").getValue();
//									故障详情，而装的实际值是 处理详情
//									qxmk.faultLocator=ak("faultLocator").getValue();
									 myak.send('/emergencyrepair/faultinsert','GET',qxmk,false,function(text){ 
								  		});
								}else{
									//更新抢修表里面的字段
									var param = {};
									//先判断哪些改变了，如果有改变就更新没有就不用放到里面去更新了
									param.stationOrLineName=ak("stationOrLineName").getText();//抢修站线
									param.deviceName=ak("deviceName").getText();//抢修设备
									param.organization=ak("organization").getText();//运维单位名称
									param.voltageLevel=ak("voltageLevel").getValue();//电压等级
									param.stationOrLineId=ak("stationOrLineName").getValue();
									param.deviceId=ak("deviceName").getValue();
									param.organizationId=ak("organization").getValue(); 
									param.id=params.id; //故障id
									myak.send('/emergencyrepair/updateEmergencyRepair','GET',param,false,function(text){ 
								  	});
								}	
						  	});
						}
					    myak.send('/fault/update','GET',data,false,function(text){ 
					    	if(text.successful==true){
					    		
	 							showtips("更新成功");
	 						}else{
	 							showtips("更新失败，该记录已发生变化，请刷新页面");
	 						}
				  		});
					    //把最后的一个赋值到
					    var flgsomedata= new Array();  
					    flgsomedata =ak("datatail").getSelecteds();
					    var trackOrNotTable = datagria.getData(); // 跟踪情况
					    if(me.WarnTrackLength!=undefined){
						    if(flgsomedata.length==1 && trackOrNotTable.length-me.WarnTrackLength==1){
								flgsomedata[0].trackContent= ak("trackContent").getValue()
							}
							for(var i= me.WarnTrackLength;i<=trackOrNotTable.length-1;i++){
								count++;
								trackOrNotTable[i].faultid=params.id;
								myak.send('/fault/updatetrackOrNot','GET',trackOrNotTable[i],false,function(text){ 
									showtips("更新成功");
								});
							}
					    }else{//第一次添加
					    	if(flgsomedata.length==1  ){
					    		flgsomedata[0].trackContent= ak("trackContent").getValue()
					    	}
					    	for(var i= 0;i<=trackOrNotTable.length-1;i++){
					    		count++;
					    		trackOrNotTable[i].faultid=params.id;
					    		myak.send('/fault/updatetrackOrNot','GET',trackOrNotTable[i],false,function(text){ 
					    			showtips("更新成功");
					    		});
					    	}
					    }
				
					}else{
						showtips("跟踪情况已变更，请重新编辑");
					}
				});
		    }
		     //查看的时候
		    if(me.type == "查看"){ 
		    	ak("powerOutReason").readOnly=true;
		    	ak("powerOutTime").readOnly=true;
		    	ak("faultTime").readOnly=true;
		    	 ak("faultCategory").readOnly=true;
		    	 ak("faultPhaseId").readOnly=true;
	    		 ak("faultType").readOnly=true;
		    	 ak("organization").readOnly=true;
		    	 ak("voltageLevel").readOnly=true;
		    	 ak("stationOrLineName").readOnly=true;
		    	 ak("deviceName").readOnly=true;
		    	 ak("weather").readOnly=true;
//			    	 ak("stationFaultOrNot").readOnly=true;
		    	 ak("threeSpanOrNot").readOnly=true;
		    	 ak("repairEndOrNot").readOnly=true;
		    	 ak("repairOrNot").readOnly=true;
		    	 ak("faultLocator").readOnly=true;
		    	 ak("recoveryDate").readOnly=true;
		    	 datagria.allowCellEdit=false;
		    	 ak("trackContent").readOnly=true;
		    	 $(".btncss").css("display","none");//隐藏保存按钮
		    	  
		    }
		} 
		ak("repairOrNot").on('valuechanged',function(){
			if(ak("repairOrNot").getValue()=="1"){
				ak("repairEndOrNot").readOnly=false;
			}
			
			if(ak("repairOrNot").getValue()=="0"){
				ak("repairEndOrNot").readOnly=true;
				ak("repairEndOrNot").setValue("");
			}
		});
		ak("datatail").on("cellbeginedit",function(p){
			if(p.rowIndex <  me.WarnTrackLength){
				 p.cancel="true";
			}
	 	 }) ; 
		//选中事件
		ak("datatail").on("select", function () { 
			 var flgsomedata= new Array();  
			 flgsomedata =ak("datatail").getSelecteds(); 
			 ak("trackContent").setValue( flgsomedata[0].trackContent); 
		});
		//切换前的选中对象p
		ak("datatail").on("beforedeselect", function (p) { 
			var flgsomedata= new Array();  
			flgsomedata =ak("datatail").getSelecteds();
			flgsomedata[0].trackContent=ak("trackContent")._textEl.value;
		});
		$("#gridRiskUploadFile").on("click",function(e){
			_fileUpLoad(params.id); 
		})
	};
	
	/**
	 * 上传附件
	 * @param id
	 * @returns
	 */
	function _fileUpLoad(id){
		var iframe = null;
		 var win = ak.open({
           title: '附件上传',
           url:akapp.commonFileUpload+"upload.html?url=/user/mcsas/faultTrip/"+id+"/&readOnly=false",
//           url:akapp.commonFileUpload+"?url=/user/mcsas/faultTrip/"+id+"/&readOnly=true",
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
               //iframe.contentWindow.Urlll("");
               //调用弹出页面方法进行初始化
           },
           ondestroy: function(e){
           	var data = iframe.contentWindow.initialize.allreturn(); 
           	var file = '';
           	for(var i=0;i<data.length;i++){
           		if(data.length >=1 && i==0){
           			file += data[i].name;
           		}else{
           			file += ','+data[i].name
           		}
           	}
        	ak("attachment").setValue(file);  
           	photoPath = '/mcsas-dfs/files/download?filePath='+data[0].url;
        	ak("attachmentpath").setValue(photoPath);
           }
	        });
			win.showAtPos("332px", "150px");
	        //关闭之前事件
	        win.on("beforeclose",function(){
//	        	var bbb = iframe.contentWindow.initialize.allreturn(); 
	        })
	}
	
	function _initAllDrop() {
		var path = '/faultTrip_channelException/initAllDrop';
		myak.send(path, "get", {}, true, function(data) {
//			warnLevel.setData(data.warnLevel);
//			warnOrigin.setData(data.warnResources);
			gridRiskID = data.id;
		});
	}
	//下拉框数组暂时先静态
	function initwaitingselect(){
		$("#but").click(function(){ 
			if($(".selected").text() == '站' ){
				if(typeof(stationTree.getSelectedNode() ) =="undefined" ){
					showtips("请选择电站");
					return ;
				}
				if(  stationTree.getSelectedNode().nodeType=="bdz"){
					me.deviceNameflag=stationTree.getSelectedNode().nodeType;
					ak("stationOrLineName").setText(stationTree.getSelectedNode().text) ; 
					ak("stationOrLineName").setValue(stationTree.getSelectedNode().id) ; 
					ak("voltageLevel").setValue(stationTree.getNode(stationTree.getSelectedNode().pid).id.split('#')[1]);
					me.deviceid = stationTree.getSelectedNode().id;
					 //设备名称
				 	deviceNameTree.on("beforeload",function(e){
						var type = e.node.nodeType;
						var id = e.node.id;
						if (typeof (type) == 'undefined') {
							e.params.nodeType = 'root';
						} else {
							e.params.id = id;
							e.params.nodeType = type;
						}
						   e.params.rootID = me.deviceid ;
				 });
				deviceNameTree.load(akapp.commonService+'/treeUtil/getBdzYcsbTree'); 
				}else{
					showtips("请选择电站");
					return;
				}
			}else{
				if(typeof(LineNameTree.getSelectedNode() ) =="undefined" ){
					showtips("请选择线路");
					return ;
				}
				 if( LineNameTree.getSelectedNode().nodeType =="xl" ){
					 ak("stationOrLineName").setText(LineNameTree.getSelectedNode().text) ; 
					 ak("stationOrLineName").setValue(LineNameTree.getSelectedNode().id) ; 
					 ak("voltageLevel").setValue(LineNameTree.getSelectedNode().voltageLevel);
					 me.deviceid = LineNameTree.getSelectedNode().id;
					 // 初始化设备下拉框
					 //设备名称
					 	deviceNameTree.on("beforeload",function(e){
							var type = e.node.nodeType;
							var id = e.node.id;
							if (typeof (type) == 'undefined') {
								e.params.nodeType = 'root';
							} else {
								e.params.id = id;
								e.params.nodeType = type;
							}
							   e.params.rootID = me.deviceid ;
					 });
					deviceNameTree.load(akapp.commonService+'/treeUtil/getXlSbTree'); 
				}else{
					showtips("请选择线路");
					return;
				}
//				 直接给电压赋值
			}
			ak("deviceOrLineWindow").hide ( ) 
		});
		//线路/站
		ak("stationOrLineName").on("buttonclick",function(){
			ak("deviceOrLineWindow").show();
		    var $div_li =$("div.tab_menu ul li");
		    $div_li.click(function(){
				$(this).addClass("selected")            //当前<li>元素高亮
					   .siblings().removeClass("selected");  //去掉其它同辈<li>元素的高亮
	            var index =  $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
				$("div.tab_box > div")   	//选取子节点。不选取子节点的话，会引起错误。如果里面还有div 
						.eq(index).show()   //显示 <li>元素对应的<div>元素
						.siblings().hide(); //隐藏其它几个同辈的<div>元素
			}).hover(function(){
				$(this).addClass("hover");
			},function(){
				$(this).removeClass("hover");
			})
			 //线路
			LineNameTree.on("beforeload",function(e){
				var type = e.node.nodeType;
				var id = e.node.id;
				if (typeof (type) == 'undefined') {
					e.params.nodeType = 'root';
				} else {
					e.params.id = id;
					e.params.nodeType = type;
				}
				if(typeof(ak("organization").getSelectedNode () ) !="undefined"){
					e.params.rootID = ak("organization").getSelectedNode ().id;
				}
			});
			LineNameTree.load(akapp.commonService+'/treeUtil/getDeptAndXlTree.do');
				//		站
			stationTree.on("beforeload",function(e){
				var type = e.node.nodeType;
				var id = e.node.id;
				if (typeof (type) == 'undefined') {
					e.params.nodeType = 'root';
				} else {
					e.params.id = id;
					e.params.nodeType = type;
				}
				if(typeof(ak("organization").getSelectedNode () ) !="undefined"){
					e.params.rootID = ak("organization").getSelectedNode ().id;
				}
		 });
			stationTree.load(akapp.commonService+'/treeUtil/getDeptAndDzTree.do');
		})
		 //所属单位
		ak("organization").on("beforeload",function(e){		// 运维单位树
			 var type = e.node.nodeType;
			 var id = e.node.id;
			 if (typeof (type) == 'undefined') {
				 e.params.nodeType = 'root';
			 } else {
				 e.params.id = id;
				 e.params.nodeType = type;
			 }
			//e.params.rootID = '1139aca634a511e78ffa0242ac110005';
		 });
		 ak("organization").load(akapp.commonService+"/treeUtil/getYwdwTree.do");
		 ak('faultType').load(akapp.appname+'/faultTrip_edit_new/getFaultType');//故障类型
		 ak('faultType').on("click",function(){
			/*重新加载基数，否则会出现bug，基数一直是改变的，一直变为0*/
			ak('faultType').load(akapp.appname+'/faultTrip_edit_new/getFaultType');//故障类型
			/*三个专业的数组*/
			var targetarrdc=new Array();
			var targetarrac=new Array();
			var targetarrce=new Array();
			if(ak('faultCategory').getText()=='直流'){
				 var j =0 ;
				 for(var i = 0;i<ak("faultType").data.length;i++){
					 if(i==3){targetarrdc[j]=ak("faultType").data[i] ; j++;}
					 if(i==4){targetarrdc[j]=ak("faultType").data[i] ; j++;}
					 if(i==5){targetarrdc[j]=ak("faultType").data[i] ; j++;}
				 } 
				ak("faultType").setData(targetarrdc);
//				换相失败，直流再启动，直流闭锁
			}else if(ak('faultCategory').getText()=='交流'){
				var j =0 ;
				for(var i = 0;i<ak("faultType").data.length;i++){
					if(i==1){targetarrac[j]=ak("faultType").data[i] ; j++;}
					if(i==2){targetarrac[j]=ak("faultType").data[i] ; j++;}
				} 
				ak("faultType").setData(targetarrac);
			}else{//通道异常
				var j =0 ;
				for(var i = 0;i<ak("faultType").data.length;i++){
					if(i==2){targetarrce[j]=ak("faultType").data[i] ; j++;}
				} 
				ak("faultType").setData(targetarrce);
			}
		});
		ak('faultPhaseId').load(akapp.appname+'/faultTrip_edit_new/getfaultPhaseId');//故障相别极号
		ak('faultCategory').load(akapp.appname+'/faultTrip_edit_new/getFaultCategory');//故障类别
		ak('voltageLevel').load(akapp.appname+'/faultTrip_edit_new/getvoltageLevel');//电压等级
		ak('repairOrNot').load(akapp.appname+'/faultTrip_edit_new/getrepairOrNot');// 是否抢修
		ak('repairEndOrNot').load(akapp.appname+'/faultTrip_edit_new/getrepairEndOrNot');//是否抢修结束
		ak('threeSpanOrNot').load(akapp.appname+'/faultTrip_edit_new/getthreeSpanOrNot');// 是否三跨区域
//		ak('stationFaultOrNot').load(akapp.appname+'/faultTrip_edit_new/getstationFaultOrNot');// 是否站内故障
		ak("faultTime").on("drawdate",function(e){
			onDrawDate(e);
		});
		ak("powerOutTime").on("drawdate",function(e){
			onDrawDate(e);
		});
		ak("recoveryDate").on("drawdate",function(e){
			onDrawDate(e);
		});
		deviceNameTree.on("nodeclick",function(e){ 
			 if(typeof(e.node.sblx) != 'undefined'){
				 deviceTypeTree.setValue(e.node.sblx);
				 showtips("设备类型已选择");
			 }else{
				 if(me.deviceNameflag=='bdz'){
					 showtips("无法获取设备类型，如果需要维护设备类型，重新选择设备");
//					 deviceNameTree.setValue(""); 因为站线下面不一定都有设备，所以这个不能通用，除非统一都有，没有的无法选择
//					 deviceNameTree.setText("");
					 deviceTypeTree.setValue("");
					 deviceTypeTree.setText("");
				 }else{//线路的时候要去除之前电站设备的设备类型
					 deviceTypeTree.setValue("");
					 deviceTypeTree.setText("");
				 }
			 }
		 });
	}
//	// 分页填充细节处理============================================================================
//    function fillData(pageIndex, pageSize, dataResult, grid) { 
//    	if(dataResult==null){
//    		return ;
//    	}
//        var data = dataResult , totalCount = dataResult.length;
//        var arr = [];
//        var start = pageIndex * pageSize, end = start + pageSize;
//        for (var i = start, l = end; i < l; i++) {
//            var record = data[i];
//            if (!record) continue;
//            arr.push(record);
//        }
//        datagria.setTotalCount(totalCount);
//        datagria.setPageIndex(pageIndex);
//        datagria.setPageSize(pageSize);
//        datagria.setData(arr);
////        $('#dtdiv').css('top','0px');删了会上拉
//    }
	/*禁止选择*/
	function onDrawDate(e) {
        var date = e.date;
        var d = new Date();
        if (date.getTime() > d.getTime()) {
            e.allowSelect = false;
        }
    }
	/* 提示框*/
	 function showtips(aa){
		 ak.showTips({
	            content: "<b>提示</b> <br/>"+aa,
	            state: 'info',
	            x: 'center',
	            y: 'center',
	            timeout: 2400
	        });
	 }
	return me;
});

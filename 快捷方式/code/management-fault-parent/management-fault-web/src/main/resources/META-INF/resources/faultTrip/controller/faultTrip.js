define([ 'akui','util','exportData','akapp' ], function( ak,myak,exak,akapp) { 
	var me ={};
	me.datajson={};
	me.gzsj1 ="";
	me.gzsj2 ="";
	me.dydj  ="";
	me.ssds  ="";
	me.sbmc  ="";
	me.zxl   ="";
	me.status=1;
	me.mh    ="";
	me.LineNameTree = ak("LineName"); //  线路
	me.stationTree = ak("station"); //战
	me.deviceNameTree= ak("sbmc"); 	
	me.aksplitter = ak("aksplitter");
	me.tabBtn = ak("radio"); 
	me.grid = ak("datagrid1");
	var dailyParam = myak.getDailyParams();
//	 dailyParam = {tab:1,recordId:'321d8cd9-a0c9-40ca-a284-522fed357041'};
	me.render=function(){
		me.aksplitter.hidePane(1);
		_initTabBtn();
		initmenu_button();
		initdatagrid_num();
	}
//	\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	function _initTabBtn(){
		var tabIndex = 0;
		if(dailyParam != null){
			switch(dailyParam.tab){//根据每个页面实际情况改写
			case 1://新增
				tabIndex = 0;
				me.status=1;
				break;
			case 2://需跟踪
				tabIndex = 1;
				me.status=2;
				break;
			case 3://已闭环
				tabIndex = 2;
				me.status=3;
				break;
			}
			
		}
		var arrlist = [
			{"id":"new","text":"新增"},
			{"id":"follow","text":"需跟踪"},
			{"id":"close","text":"已闭环"},
			{"id":"sort","text":"已归档"}
		];		
		me.tabBtn.setData(arrlist);
		me.tabBtn.setValue(arrlist[tabIndex].id);
		me.tabBtn.fire("valuechanged");	
		$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(0).attr("afterData","0");
		$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(1).attr("afterData","0");
		$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(2).attr("afterData","0");
		$(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(3).attr("afterData","0");
	}
	
	function   initmenu_button(){
		loadfirstmenu();
		initbutton();
	};
	
	function initnum(){
		var json ={
//				"startTime":"2017-7-22 00:00",
//				"endTime":"2017-7-22 23:59"
		} ;
		 myak.send('/fault/getstatusnum','GET',json,true,function(data){ 
			 if(data.successful==false){
				 return ;
			 }
			 var a = data.list[0] ;
			 var b =a.xz; 
			 var c = a.xgz;
			 var d =a.ybh;
			 var e =a.ygd;
			 $(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(0).attr("afterData",b);
			 $(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(1).attr("afterData",c);
			 $(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(2).attr("afterData",d);
			 $(".ak-radiobuttonlisttip .ak-radiobuttonlist-item").eq(3).attr("afterData",e);
 		});
	}
	
	function initdatagrid (){
//		if( me.status ==1){ 
//			 me.grid.hideColumn ( "faultLocator" );
//			 me.grid.hideColumn ( "repairOrNot" );
//			 me.grid.hideColumn ( "recoveryDate" );
//			 }else{
//				 me.grid.showColumn ( "faultLocator" );
//				 me.grid.showColumn ( "repairOrNot" );
//				 me.grid.showColumn ( "recoveryDate" );
//			 }
		 me.grid.load(akapp.appname +'/fault/showFaultTrip');
		 getcxtj();
		 me.datajson.pageIndex =0 ;
		 me.datajson.pageSize =20 ;
		 if(dailyParam != null){//传入参数中加入记录ID条件过滤
		 	me.datajson.recordId = dailyParam.recordId;
		 	dailyParam = null;//移除dailyParam
		 }
		  
		 me.grid.load(me.datajson,function(e){ 
			 myak.setPageIndex(e);//跳转到对应页
		 	 myak.removeLoadParam(e,"recordId");//移除参数recordId
			});
		 
		 me.grid.on('drawcell',function(e) {
	    	if(me.status=='1'){
	    		e.source._rowsEl.style.color='#ff3d58';
	    	}else if(me.status=='2'){
	    		e.source._rowsEl.style.color='#009694';
	    	}else if(me.status=='3' || me.status=='4'){
	    		e.source._rowsEl.style.color='black';
	    	}
	    	var row = e.row;
			if(row.isSelected){
				this.setSelected(e.row);
					 me.grid.off("drawcell",arguments.callee);
			}
		 });
		 
	}
	
	//============================================分离出供第一次菜单调用加载和切换加载============================================		
	function loadfirstmenu(){
		var html = "";
		$("#menudiv").html(html);
		html += "<a class='btn wordBtn  '  id='xz'>" + '新增'  + "</a>&nbsp;";
		html += "<a class='btn wordBtn' id='bj'>" +  '编辑' + "</a>&nbsp;";
		html += "<a class='btn wordBtn'   id='sc'>" +  '删除' + "</a>&nbsp;";
		html += "<a class='btn wordBtn'   id='fs'>" +  '发送' + "</a>&nbsp;";
		html += "<a class='btn wordBtn' id='gz'>" +  '跟踪' + "</a>&nbsp;";
		html += "<a class='btn wordBtn'   id='dc'>" +  '导出' + "</a>&nbsp;";
		$("#menudiv").append(html); 
		$("#xz").click(function(){
			 add(); 
			 initdatagrid_num( );
			$(".btn.wordBtn").removeClass("selected");
			$("#xz").addClass("selected");
		});
		$("#bj").click(function(){ 
			 	 var djson={};
				 var flgsomedata= new Array;  
				 flgsomedata =me.grid.getSelecteds();  
				 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
					 showtips("请勾选且只能勾选一条数据！","info");
					 return;
					 }
				 for(var i=0;i<flgsomedata.length;i++){ 
					 //tab1 理论说只有九个字段需要传值 
					 djson.id=(flgsomedata[i].id); 
					 djson.faultType=(flgsomedata[i].faultType);  
					 djson.faultCategory=(flgsomedata[i].faultCategory);  
					 djson.faultTime=(flgsomedata[i].faultTime); 
					 djson.organization=(flgsomedata[i].organization); 
					 djson.organizationId=(flgsomedata[i].organizationId); 
					 djson.voltageLevel=(flgsomedata[i].voltageLevel); 
					 djson.stationOrLineName=(flgsomedata[i].stationOrLineName); 
					 djson.stationOrLineId=(flgsomedata[i].stationOrLineId); 
					 djson.deviceName=(flgsomedata[i].deviceName); 
					 djson.deviceId=(flgsomedata[i].deviceId); 
					 djson.faultPhaseId=(flgsomedata[i].faultPhaseId); 
					 djson.weather=(flgsomedata[i].weather); 
					 djson.updateDateTime=(flgsomedata[i].updateDateTime); 
					 djson.edit="编辑";
					 djson.status=me.status;
				 }
			 var tipopen="";
			 if(flgsomedata[0].faultCategory=="交流"){
				 tipopen="交流-编辑"
			 }
			 if(flgsomedata[0].faultCategory=="直流"){
				 tipopen="直流-编辑"
			 }
			 if(flgsomedata[0].faultCategory=="通道异常"){
				 tipopen="通道异常-编辑"
			 }
			 var json= JSON.stringify(djson);
			 var len=json.length;
			 json=json.substring(1,len-1);
			 var url = akapp.appname+"/faultTrip_edit_new/index.jsp?"+"data="+json ;
			 var title = tipopen;
			 myak.openlittle(url,title,null,function(action){
			 	if("success" == action) {showtips( "修改成功","success");}
			 	 initdatagrid();
			 });
			 $(".btn.wordBtn").removeClass("selected");
			 $("#bj").addClass("selected");
		});
		$("#sc").click(function(){
			/*ak.confirm("是否确定删除？", "确定？",
    	            function (action) {
    	                if (action == "ok") {
    	                	
    	                } 
    	            }
    	      );*/
			deletete();
			$(".btn.wordBtn").removeClass("selected");
			$("#sc").addClass("selected");
			changeWinCSS();
		});
		$("#fs").click(function(){ 
		 	 var djson="";
			 var flgsomedata= new Array;  
			 flgsomedata =me.grid.getSelecteds();  
			 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
				 showtips( "请勾选且只能勾选一条数据！","info");
				 return;
				 }
			 for(var i=0;i<flgsomedata.length;i++){ 
				 djson += (flgsomedata[i].organization)+"于 "; 
				 djson += (flgsomedata[i].faultTime)+" ，出现了"; 
				 if(flgsomedata[i].faultType !=null){
					 djson += (flgsomedata[i].faultType)+"类型的";  
				 }
				 djson += "故障";  
			 }
		 	var obj= [];
    		obj[0] = "";
    		obj[1] = "故障异常";
    		obj[2] = djson;
    		obj[3] = "";
    		obj[4] = "3";
    		ak.confirm("是否确定发送短信？", "确定？",
    	            function (action) {
    	                if (action == "ok") {
    	                	var url = akapp.messageSendService+"/communicationMessageSend/index.jsp?state="+encodeURIComponent(ak.encode(obj));
    	        			var title = "故障异常-短信发送";
    	        			myak.open(url,title,null,function(action){
    	        				 if(action == 'true'){
    	        					 showtips( "发送成功","success");
    	        				 }
    	        			});
    	                	
    	                } 
    	            }
    	      );
    		changeWinCSS();
    		$(".btn.wordBtn").removeClass("selected");
    		$("#fs").addClass("selected");
			
		});
		$("#dc").click(function(){ 
			var temp = JSON.stringify({
				'status':me.status,//当前状态，新增需跟踪，闭环，归档
				'gzlb':me.gzlb,//故障类别
				'dydj':me.dydj,//电压等级
				'ssds':me.ssds,//运维单位
			    'zxl':me.zxl, 
			    'sbmc':me.sbmc,
			    'sfqx':me.sfqx,
			    'gzsj1':  me.gzsj1,
			    'gzsj2':me.gzsj2
			}); 
			 
			if(me.grid.data.length==0){
				showtips( "当前无数据无法导出","warning");
				return ;
			}
			
			ak.confirm("是否确定导出？", "确定？",
    	            function (action) {
    	                if (action == "ok") { 
    	                	exak.exportPagesDataToExcel(me.grid,temp,"故障跳闸");
    	        			$(".btn.wordBtn").removeClass("selected");
    	        			$("#dc").addClass("selected");
    	                } 
    	            }
    	      );
			
			changeWinCSS();
		});
		$("#gz").click(function(){ 
		 //校验必填字段，只有三个
			 var id="";
			 var status="";
			 var tipsinfo="";
			 var flgsomedata= new Array();  
			 flgsomedata =me.grid.getSelecteds();  
			 if( flgsomedata.length<1){
				 showtips( "请至少选择一条记录！","info");
				 return;
			 }
			 
			 for(var i=0;i<flgsomedata.length;i++){ 
				 	
				 	
				 	if(flgsomedata[i].faultPhaseId==""||	 
							flgsomedata[i].faultPhaseId==null||
							flgsomedata[i].faultCategory==""||	 
							flgsomedata[i].faultCategory==null||
							flgsomedata[i].faultTime==""||	 
							flgsomedata[i].faultTime==null 
					){
				 		tipsinfo+= i+",";
//							 showtips("请重新编辑故障时间，故障类别，故障相别/极号","info");
//							 return;
					}else{
						id +=flgsomedata[i].id+",";
					 	status +=flgsomedata[i].confirmStatus+",";
					}
			 }
			 //把数组放到对象里面传到后台
			 id=id.substring(0,id.length-1) ;
			 status=status.substring(0,status.length-1 ) ;
			 debugger;
			 var djson={"id":id,"status":status};
			 var infopass="";
			 if(tipsinfo != ""){
				 tipsinfo = tipsinfo.substring(0,tipsinfo.length-2);
				 infopass="第"+tipinfo+"项未填写必填字段，是否忽略他们，其他转入需跟踪状态"
			 }else{
				 infopass="是否确认转入需跟踪状态？";
			 }
//			 是否确认转入需跟踪状态？
			 ak.confirm(infopass , "确定？",
	    	            function (action) {
	    	                if (action == "ok") {
	    	                	
	    	                	myak.send('/fault/updatestatus','GET',djson,false,function(data){ 
	    	                		showtips("跟踪成功","success");
	    	                		setTimeout(function(){
	    	                			initdatagrid( );
	    	                			initnum();
	    	                		},500);
	    	                	}); 
	    	                	
	    	                } 
	    	            }
	    	      );
			 
			 $(".btn.wordBtn").removeClass("selected");
			 $("#gz").addClass("selected");
			 changeWinCSS();
			 
		});
	}
	
	function   initbutton(){ /*new follow close sort*/
		me.tabBtn.on("valuechanged",function(){
			var tabBtnFlag = this.getValue();
			//变化后清空查询条件
			ak("gzsj1").setText();
	   		ak("gzsj2").setText();
	   		ak("dydj").setValue("");
	   		ak("ssds").setValue("");
	   		ak("zxl").setValue("");
	   		ak("sbmc").setValue("");
			if(tabBtnFlag == 'new'){
				me.status=1;
				loadfirstmenu();          //加载第一次菜单
				$(".top_botton > button").removeClass("active");
				$("#new").addClass("active");
				initdatagrid_num( );
				me.aksplitter.hidePane(1);
			}else if(tabBtnFlag == 'follow'){
				$(".top_botton > button").removeClass("active");
				$("#follow").addClass("active");
				me.status=2;
				me.grid.setPageIndex(0);
				me.grid.setPageSize(10);
				initdatagrid_num( );
				var html = "";
				$("#menudiv").html(html);
				html += "<a class='btn wordBtn' id='bj'>" +  '编辑' + "</a>&nbsp;";
				html += "<a class='btn wordBtn ' id='ck'>" + '查看'  + "</a>&nbsp;";
				html += "<a class='btn wordBtn' id='fs'>" +  '发送' + "</a>&nbsp;";
				html += "<a class='btn wordBtn' id='bh'>" +  '闭环' + "</a>&nbsp;";
				html += "<a class='btn wordBtn' id='dc'>" +  '导出' + "</a>&nbsp;";
				$("#menudiv").append(html);
				$("#bj").click(function(){ 
					 var djson={};
					 var flgsomedata= new Array;  
					 flgsomedata =me.grid.getSelecteds();    
					 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
						 showtips("请勾选且只能勾选一条数据！","info");
						 return;
					 }
					 
					 //公共的字段
					 for(var i=0;i<flgsomedata.length;i++){
						 djson.id=(flgsomedata[i].id);  
						 djson.faultType=(flgsomedata[i].faultType);  
						 djson.faultTime=(flgsomedata[i].faultTime); 
						 djson.organization=(flgsomedata[i].organization); 
						 djson.organizationId=(flgsomedata[i].organizationId); 
						 djson.voltageLevel=(flgsomedata[i].voltageLevel); 
						 djson.faultCategory=(flgsomedata[i].faultCategory); 
						 djson.stationOrLineName=(flgsomedata[i].stationOrLineName); 
						 djson.stationOrLineId=(flgsomedata[i].stationOrLineId); 
						 djson.deviceName=(flgsomedata[i].deviceName); 
						 djson.deviceId=(flgsomedata[i].deviceId); 
						 djson.faultPhaseId=(flgsomedata[i].faultPhaseId); 
						 djson.weather=(flgsomedata[i].weather); 
						 djson.attachment=(flgsomedata[i].attachment); 
						 djson.attachmentPath=(flgsomedata[i].attachmentPath); 
						 djson.updateDateTime=(flgsomedata[i].updateDateTime); 
						 djson.edit="编辑";
						 djson.status=me.status;
					 }
					 var tipopen="";
					 var url=""; 
					 if(flgsomedata[0].faultCategory=="交流"){
						 url="/faultTrip_ac/index.jsp?"; 
						 tipopen="交流-编辑";
						 //只传交流字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.reclosingAction			=flgsomedata[0].reclosingAction ;
						 djson.sendSuccessOrNot			=flgsomedata[0].sendSuccessOrNot ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultReason			    =flgsomedata[0].faultReason;
						 djson.protection			    =flgsomedata[0].protection;
						 djson.managementDetail			    =flgsomedata[0].managementDetail;
						 djson.faultDetail			    =flgsomedata[0].faultDetail;
					 }
					 if(flgsomedata[0].faultCategory=="直流"){
						 url="/faultTrip_dc/index.jsp?";
						 tipopen="直流-编辑";
						 //只传直流字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultReason			    =flgsomedata[0].faultReason;
						 djson.protection			    =flgsomedata[0].protection;
						 djson.managementDetail			    =flgsomedata[0].managementDetail;
						 djson.faultDetail			    =flgsomedata[0].faultDetail;
						 djson.blockOrNot			    =flgsomedata[0].blockOrNot;
						 djson.blockCase			    =flgsomedata[0].blockCase;
					 }
					 if(flgsomedata[0].faultCategory=="通道异常"){
						 url="/faultTrip_channelException/index.jsp?";
						 tipopen="通道异常-编辑"; 
						 //只传通道异常字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.powerOutReason	 =flgsomedata[0].powerOutReason			    ;
						 djson.powerOutTime	 =flgsomedata[0].powerOutTime			    ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
					 }
					 var json= JSON.stringify(djson);
					 var len=json.length;
					 json=json.substring(1,len-1);  
					var url = akapp.appname+url+"data="+json ;
					var title = tipopen;
					myak.open(url,title,null,function(action){ 
						 initdatagrid( );
					});
			        changeWinCSS();
					$(".btn.wordBtn").removeClass("selected");
					$("#bj").addClass("selected");
				});
				$("#ck").click(function(){ 
					 var djson={};
					 var flgsomedata= new Array;  
					 flgsomedata =me.grid.getSelecteds();  
					 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
						 showtips("请勾选且只能勾选一条数据！","info" );
						 return;
					 }
					 for(var i=0;i<flgsomedata.length;i++){
						 djson.id=(flgsomedata[i].id);  
						 djson.faultType=(flgsomedata[i].faultType);  
						 djson.faultTime=(flgsomedata[i].faultTime); 
						 djson.organization=(flgsomedata[i].organization); 
						 djson.voltageLevel=(flgsomedata[i].voltageLevel); 
						 djson.faultCategory=(flgsomedata[i].faultCategory); 
						 djson.stationOrLineName=(flgsomedata[i].stationOrLineName); 
						 djson.deviceName=(flgsomedata[i].deviceName); 
						 djson.faultPhaseId=(flgsomedata[i].faultPhaseId); 
						 djson.attachment=(flgsomedata[i].attachment); 
						 djson.attachmentPath=(flgsomedata[i].attachmentPath); 
						 djson.weather=(flgsomedata[i].weather); 
						 djson.edit="查看";
						 djson.status=me.status;
					 }
					 var tipopen="";
					 var url="";
					 if(flgsomedata[0].faultCategory=="交流"){
						 url="/faultTrip_ac/index.jsp?";
						 tipopen="交流-查看";
						 //只传交流字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.reclosingAction			=flgsomedata[0].reclosingAction ;
						 djson.sendSuccessOrNot			=flgsomedata[0].sendSuccessOrNot ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultReason			    =flgsomedata[0].faultReason;
						 djson.protection			    =flgsomedata[0].protection;
						 djson.managementDetail			    =flgsomedata[0].managementDetail;
						 djson.faultDetail			    =flgsomedata[0].faultDetail;
					 }
					 if(flgsomedata[0].faultCategory=="直流"){
						 url="/faultTrip_dc/index.jsp?";
						 tipopen="直流-查看";
						 //只传直流字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultReason			    =flgsomedata[0].faultReason;
						 djson.protection			    =flgsomedata[0].protection;
						 djson.managementDetail			    =flgsomedata[0].managementDetail;
						 djson.faultDetail			    =flgsomedata[0].faultDetail;
						 djson.blockOrNot			    =flgsomedata[0].blockOrNot;
						 djson.blockCase			    =flgsomedata[0].blockCase;
					 }
					 if(flgsomedata[0].faultCategory=="通道异常"){
						 url="/faultTrip_channelException/index.jsp?";
						 tipopen="通道异常-查看";
						 //只传通道异常字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.powerOutReason	 =flgsomedata[0].powerOutReason			    ;
						 djson.powerOutTime	 =flgsomedata[0].powerOutTime			    ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
					 }
					 var json= JSON.stringify(djson);
					 var len=json.length;
					 json=json.substring(1,len-1); 		
					 var url = akapp.appname+url+"data="+json  ;
					var title = tipopen;
					myak.open(url,title,null,function(action){ 
						 initdatagrid( );
					});
			        changeWinCSS();
					$(".btn.wordBtn").removeClass("selected");
					$("#ck").addClass("selected");
				});
				$("#fs").click(function(){ 
					var djson="";
					 var flgsomedata= new Array;  
					 flgsomedata =me.grid.getSelecteds();  
					 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
						 showtips("请勾选且只能勾选一条数据！","info");
						 return;
						 }
					 for(var i=0;i<flgsomedata.length;i++){ 
						 djson += (flgsomedata[i].organization)+"于 "; 
						 djson += (flgsomedata[i].faultTime)+" ，出现了"; 
						 if(flgsomedata[i].faultType !=null){
							 djson += (flgsomedata[i].faultType)+"类型的";  
						 }
						 djson += "故障";    
					 }
				 	var obj= [];
		    		obj[0] = "";
		    		obj[1] = "故障异常";
		    		obj[2] = djson;
		    		obj[3] = "";
		    		obj[4] = "3";
		    		
		    		ak.confirm("是否确定发送短信？", "确定？",
		    	            function (action) {
		    	                if (action == "ok") { 
		    	                	 
		    	                	var url = akapp.messageSendService+"/communicationMessageSend/index.jsp?state="+encodeURIComponent(ak.encode(obj));
		    	                	var title = "故障异常-短信发送";
		    	                	myak.open(url,title,null,function(action){ 
		    	                		if(action == 'true'){
		    	                			showtips( "发送成功","success");
		    	                		}
		    	                	});
		    	                } 
		    	            }
		    	      );
		    		
			        changeWinCSS();
					$(".btn.wordBtn").removeClass("selected");
					$("#fs").addClass("selected");
				});
				$("#bh").click(function(){ 
					updatestatus( "bh");
					
					changeWinCSS();
					$(".btn.wordBtn").removeClass("selected");
					$("#bh").addClass("selected");
				});
				$("#dc").click(function(){
					var temp = JSON.stringify({
						'status':me.status,//当前状态，新增需跟踪，闭环，归档
						'gzlb':me.gzlb,//故障类别
						'dydj':me.dydj,//电压等级
						'ssds':me.ssds,//运维单位
					    'zxl':me.zxl, 
					    'sbmc':me.sbmc,
					    'sfqx':me.sfqx,
					    'gzsj1':  me.gzsj1,
					    'gzsj2':me.gzsj2
					}); 
					if(me.grid.data.length==0){
						showtips("当前无数据无法导出","warning");
						return ;
					}
					ak.confirm("是否确定导出？", "确定？",
		    	            function (action) {
		    	                if (action == "ok") { 
		    	                	exak.exportPagesDataToExcel(me.grid,temp,"故障跳闸");
		    	                	
		    	                } 
		    	            }
		    	      );
					$(".btn.wordBtn").removeClass("selected");
					$("#dc").addClass("selected");
					changeWinCSS();
				});
				me.aksplitter.hidePane(1);
			}else if(tabBtnFlag == 'close'){
				$(".top_botton > button").removeClass("active");
				$("#close").addClass("active");
				me.status=3;
				initdatagrid_num( );
				var html = "";
				$("#menudiv").html(html);
				html += "<a class='btn wordBtn' id='bj'>" +  '编辑' + "</a>&nbsp;";
				html += "<a class='btn wordBtn' id='ck'  >" + '查看'  + "</a>&nbsp;";
				html += "<a class='btn wordBtn' id='fs'>" +  '发送' + "</a>&nbsp;";
				html += "<a class='btn wordBtn' id='gd'>" +  '归档' + "</a>&nbsp;";
				html += "<a class='btn wordBtn' id='dc'>" +  '导出' + "</a>&nbsp;";
				$("#menudiv").append(html);
				$("#ck").click(function(){ 
					 var djson={};
					 var flgsomedata= new Array;  
					 flgsomedata =me.grid.getSelecteds();  
					 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
						 showtips( "请勾选且只能勾选一条数据！","info");
						 return;
					 }
					 for(var i=0;i<flgsomedata.length;i++){
						 djson.id=(flgsomedata[i].id);  
						 djson.faultType=(flgsomedata[i].faultType);  
						 djson.faultTime=(flgsomedata[i].faultTime); 
						 djson.organization=(flgsomedata[i].organization); 
						 djson.voltageLevel=(flgsomedata[i].voltageLevel); 
						 djson.faultCategory=(flgsomedata[i].faultCategory); 
						 djson.stationOrLineName=(flgsomedata[i].stationOrLineName); 
						 djson.deviceName=(flgsomedata[i].deviceName); 
						 djson.faultPhaseId=(flgsomedata[i].faultPhaseId); 
						 djson.weather=(flgsomedata[i].weather); 
						 djson.edit="查看";
						 djson.status=me.status;
					 }
					 var tipopen="";
					 var url="";
					 if(flgsomedata[0].faultCategory=="交流"){
						 url="/faultTrip_ac/index.jsp?";
						 tipopen="交流-查看";
						 //只传交流字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.reclosingAction			=flgsomedata[0].reclosingAction ;
						 djson.sendSuccessOrNot			=flgsomedata[0].sendSuccessOrNot ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultReason			    =flgsomedata[0].faultReason;
						 djson.protection			    =flgsomedata[0].protection;
						 djson.managementDetail			    =flgsomedata[0].managementDetail;
						 djson.faultDetail			    =flgsomedata[0].faultDetail;
					 }
					 if(flgsomedata[0].faultCategory=="直流"){
						 url="/faultTrip_dc/index.jsp?";
						 tipopen="直流-查看";
						 //只传直流字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultReason			    =flgsomedata[0].faultReason;
						 djson.protection			    =flgsomedata[0].protection;
						 djson.managementDetail			    =flgsomedata[0].managementDetail;
						 djson.faultDetail			    =flgsomedata[0].faultDetail;
						 djson.blockOrNot			    =flgsomedata[0].blockOrNot;
						 djson.blockCase			    =flgsomedata[0].blockCase;
					 }
					 if(flgsomedata[0].faultCategory=="通道异常"){
						 url="/faultTrip_channelException/index.jsp?";
						 tipopen="通道异常-查看";
						 //只传通道异常字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.powerOutReason	 =flgsomedata[0].powerOutReason			    ;
						 djson.powerOutTime	 =flgsomedata[0].powerOutTime			    ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
					 }
					 var json= JSON.stringify(djson);
					 var len=json.length;
					 json=json.substring(1,len-1); 
					 var url =akapp.appname+url+"data="+json  ;
						var title =tipopen;
						myak.open(url,title,null,function(action){ 
							 initdatagrid( );
						});
				        changeWinCSS();
					$(".btn.wordBtn").removeClass("selected");
					$("#ck").addClass("selected");
				});
				$("#bj").click(function(){ 
					 var djson={};
					 var flgsomedata= new Array;  
					 flgsomedata =me.grid.getSelecteds();  
					 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
						 showtips("请勾选且只能勾选一条数据！","info");
						 return;
					 }
					 for(var i=0;i<flgsomedata.length;i++){
						 djson.id=(flgsomedata[i].id);  
						 djson.faultType=(flgsomedata[i].faultType);  
						 djson.faultTime=(flgsomedata[i].faultTime); 
						 djson.organization=(flgsomedata[i].organization); 
						 djson.voltageLevel=(flgsomedata[i].voltageLevel); 
						 djson.faultCategory=(flgsomedata[i].faultCategory); 
						 djson.stationOrLineName=(flgsomedata[i].stationOrLineName); 
						 djson.deviceName=(flgsomedata[i].deviceName); 
						 djson.faultPhaseId=(flgsomedata[i].faultPhaseId); 
						 djson.weather=(flgsomedata[i].weather); 
						 djson.updateDateTime=(flgsomedata[i].updateDateTime); 
						 djson.edit="编辑";
						 djson.status=me.status;
					 }
					 var tipopen="";
					 var url="";
					 if(flgsomedata[0].faultCategory=="交流"){
						 url="/faultTrip_ac/index.jsp?";
						 tipopen="交流-编辑";
						 //只传交流字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.reclosingAction			=flgsomedata[0].reclosingAction ;
						 djson.sendSuccessOrNot			=flgsomedata[0].sendSuccessOrNot ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultReason			    =flgsomedata[0].faultReason;
						 djson.protection			    =flgsomedata[0].protection;
						 djson.managementDetail			    =flgsomedata[0].managementDetail;
						 djson.faultDetail			    =flgsomedata[0].faultDetail;
					 }
					 if(flgsomedata[0].faultCategory=="直流"){
						 url="/faultTrip_dc/index.jsp?";
						 tipopen="直流-编辑";
						 //只传直流字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultReason			    =flgsomedata[0].faultReason;
						 djson.protection			    =flgsomedata[0].protection;
						 djson.managementDetail			    =flgsomedata[0].managementDetail;
						 djson.faultDetail			    =flgsomedata[0].faultDetail;
						 djson.blockOrNot			    =flgsomedata[0].blockOrNot;
						 djson.blockCase			    =flgsomedata[0].blockCase;
					 }
					 if(flgsomedata[0].faultCategory=="通道异常"){
						 url="/faultTrip_channelException/index.jsp?";
						 tipopen="通道异常-编辑";
						 //只传通道异常字段
						 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
						 djson.powerOutReason	 =flgsomedata[0].powerOutReason			    ;
						 djson.powerOutTime	 =flgsomedata[0].powerOutTime			    ;
						 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
						 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
						 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
						 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
						 djson.faultLocator			    =flgsomedata[0].faultLocator;
					 }
					 var json= JSON.stringify(djson);
					 var len=json.length;
					 json=json.substring(1,len-1); 		
					 var url =akapp.appname+url+"data="+json ;
						var title = tipopen;
						myak.open(url,title,null,function(action){ 
							initdatagrid( );
						});
				        changeWinCSS();
					$(".btn.wordBtn").removeClass("selected");
					$("#ck").addClass("selected");
				});
				$("#fs").click(function(){ 
					var djson="";
					 var flgsomedata= new Array;  
					 flgsomedata =me.grid.getSelecteds();  
					 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
						 showtips( "请勾选且只能勾选一条数据！","info");
						 return;
						 }
					 for(var i=0;i<flgsomedata.length;i++){ 
						 djson += (flgsomedata[i].organization)+"于 "; 
						 djson += (flgsomedata[i].faultTime)+" ，出现了"; 
						 if(flgsomedata[i].faultType !=null){
							 djson += (flgsomedata[i].faultType)+"类型的";  
						 }
						 djson += "故障";  
					 }
				 	var obj= [];
		    		obj[0] = "";
		    		obj[1] = "故障异常";
		    		obj[2] = djson;
		    		obj[3] = "";
		    		obj[4] = "3";
		    		
		    		ak.confirm("是否确定发送短信？", "确定？",
		    	            function (action) {
		    	                if (action == "ok") { 
		    	                	var url = akapp.messageSendService+"/communicationMessageSend/index.jsp?state="+encodeURIComponent(ak.encode(obj));
		    	                	var title = "故障异常-短信发送";
		    	                	myak.open(url,title,null,function(action){ 
		    	                		if(action == 'true'){
		    	                			showtips("发送成功","success");
		    	                		}
		    	                	});
		    	                	
		    	                } 
		    	            }
		    	      );
			        changeWinCSS();
					$(".btn.wordBtn").removeClass("selected");
					$("#fs").addClass("selected");
				});
				$("#gd").click(function(){ 
					updatestatus("gd");
					/*ak.confirm("是否确定转入归档状态？", "确定？",
		    	            function (action) {
		    	                if (action == "ok") { 
		    	                	
		    	                } 
		    	            }
		    	      );*/
					changeWinCSS();
					$(".btn.wordBtn").removeClass("selected");
					$("#gd").addClass("selected");
				});
				$("#dc").click(function(){ 
					var temp = JSON.stringify({
						'status':me.status,//当前状态，新增需跟踪，闭环，归档
						'gzlb':me.gzlb,//故障类别
						'dydj':me.dydj,//电压等级
						'ssds':me.ssds,//运维单位
					    'zxl':me.zxl, 
					    'sbmc':me.sbmc,
					    'sfqx':me.sfqx,
					    'gzsj1':  me.gzsj1,
					    'gzsj2':me.gzsj2
					}); 
					if(me.grid.data.length==0){
						showtips("当前无数据无法导出","warning");
						return ;
					}
					ak.confirm("是否确定导出？", "确定？",
		    	            function (action) {
		    	                if (action == "ok") { 
		    	                	exak.exportPagesDataToExcel(me.grid,temp,"故障跳闸");
		    	                	
		    	                } 
		    	            }
		    	      );
					$(".btn.wordBtn").removeClass("selected");
					changeWinCSS();
					$("#dc").addClass("selected");
				});
				me.aksplitter.hidePane(1);
			}else if(tabBtnFlag == 'sort'){
				me.status=4;
					$(".top_botton > button").removeClass("active");
					$("#sort").addClass("active");
					initdatagrid_num( );
					var html = "";
					$("#menudiv").html(html);  //把之前的动态加载的菜单移除
					html += "<a class='btn wordBtn  ' id='bj'>" +  '编辑' + "</a>&nbsp;";
					html += "<a class='btn wordBtn   ' id='ck'>" + '查看'  + "</a>&nbsp;";
					html += "<a class='btn wordBtn' id='dc'>" +  '导出' + "</a>&nbsp;";
					$("#menudiv").append(html);
					$("#ck").click(function(){ 
						 var djson={};
						 var flgsomedata= new Array;  
						 flgsomedata =me.grid.getSelecteds();  
						 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
							 showtips( "请勾选且只能勾选一条数据！","info");
							 return;
						 }
						 for(var i=0;i<flgsomedata.length;i++){
							 djson.id=(flgsomedata[i].id);  
							 djson.faultType=(flgsomedata[i].faultType);  
							 djson.faultTime=(flgsomedata[i].faultTime); 
							 djson.organization=(flgsomedata[i].organization); 
							 djson.voltageLevel=(flgsomedata[i].voltageLevel); 
							 djson.faultCategory=(flgsomedata[i].faultCategory); 
							 djson.stationOrLineName=(flgsomedata[i].stationOrLineName); 
							 djson.deviceName=(flgsomedata[i].deviceName); 
							 djson.faultPhaseId=(flgsomedata[i].faultPhaseId); 
							 djson.weather=(flgsomedata[i].weather); 
							 djson.edit="查看";
							 djson.status=me.status;
						 }
						 var tipopen="";
						 var url="";
						 if(flgsomedata[0].faultCategory=="交流"){
							 url="/faultTrip_ac/index.jsp?";
							 tipopen="交流-查看";
							 //只传交流字段
							 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
							 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
							 djson.reclosingAction			=flgsomedata[0].reclosingAction ;
							 djson.sendSuccessOrNot			=flgsomedata[0].sendSuccessOrNot ;
							 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
							 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
							 djson.faultLocator			    =flgsomedata[0].faultLocator;
							 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
							 djson.faultReason			    =flgsomedata[0].faultReason;
							 djson.protection			    =flgsomedata[0].protection;
							 djson.managementDetail			    =flgsomedata[0].managementDetail;
							 djson.faultDetail			    =flgsomedata[0].faultDetail;
						 }
						 if(flgsomedata[0].faultCategory=="直流"){
							 url="/faultTrip_dc/index.jsp?";
							 tipopen="直流-查看";
							 //只传直流字段
							 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
							 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
							 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
							 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
							 djson.faultLocator			    =flgsomedata[0].faultLocator;
							 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
							 djson.faultReason			    =flgsomedata[0].faultReason;
							 djson.protection			    =flgsomedata[0].protection;
							 djson.managementDetail			    =flgsomedata[0].managementDetail;
							 djson.faultDetail			    =flgsomedata[0].faultDetail;
							 djson.blockOrNot			    =flgsomedata[0].blockOrNot;
							 djson.blockCase			    =flgsomedata[0].blockCase;
						 }
						 if(flgsomedata[0].faultCategory=="通道异常"){
							 url="/faultTrip_channelException/index.jsp?";
							 tipopen="通道异常-查看";
							 //只传通道异常字段
							 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
							 djson.powerOutReason	 =flgsomedata[0].powerOutReason			    ;
							 djson.powerOutTime	 =flgsomedata[0].powerOutTime			    ;
							 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
							 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
							 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
							 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
							 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 }
						 var json= JSON.stringify(djson);
						 var len=json.length;
						 json=json.substring(1,len-1); 	
						 var url = akapp.appname+url+"data="+json ;
							var title = tipopen;
							myak.open(url,title,null,function(action){
								 initdatagrid( );
							});
					        changeWinCSS();
						$(".btn.wordBtn").removeClass("selected");
						$("#ck").addClass("selected");
					});
					$("#bj").click(function(){ 
						 var djson={};
						 var flgsomedata= new Array;  
						 flgsomedata =me.grid.getSelecteds();  
						 if(flgsomedata.length> 1 ||flgsomedata.length==0 ){
							 showtips("<b>注意</b> <br/>请勾选且只能勾选一条数据！","info" );
							 return;
						 }
						 for(var i=0;i<flgsomedata.length;i++){
							 djson.id=(flgsomedata[i].id);  
							 djson.faultType=(flgsomedata[i].faultType);  
							 djson.faultTime=(flgsomedata[i].faultTime); 
							 djson.organization=(flgsomedata[i].organization); 
							 djson.voltageLevel=(flgsomedata[i].voltageLevel); 
							 djson.faultCategory=(flgsomedata[i].faultCategory); 
							 djson.stationOrLineName=(flgsomedata[i].stationOrLineName); 
							 djson.deviceName=(flgsomedata[i].deviceName); 
							 djson.faultPhaseId=(flgsomedata[i].faultPhaseId); 
							 djson.weather=(flgsomedata[i].weather); 
							 djson.updateDateTime=(flgsomedata[i].updateDateTime); 
							 djson.edit="编辑";
							 djson.status=me.status;
						 }
						 var tipopen="";
						 var url="";
						 if(flgsomedata[0].faultCategory=="交流"){
							 url="/faultTrip_ac/index.jsp?";
							 tipopen="交流-编辑";
							 //只传交流字段
							 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
							 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
							 djson.reclosingAction			=flgsomedata[0].reclosingAction ;
							 djson.sendSuccessOrNot			=flgsomedata[0].sendSuccessOrNot ;
							 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
							 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
							 djson.faultLocator			    =flgsomedata[0].faultLocator;
							 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
							 djson.faultReason			    =flgsomedata[0].faultReason;
							 djson.protection			    =flgsomedata[0].protection;
							 djson.managementDetail			    =flgsomedata[0].managementDetail;
							 djson.faultDetail			    =flgsomedata[0].faultDetail;
						 }
						 if(flgsomedata[0].faultCategory=="直流"){
							 url="/faultTrip_dc/index.jsp?";
							 tipopen="直流-编辑";
							 //只传直流字段
							 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
							 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
							 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
							 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
							 djson.faultLocator			    =flgsomedata[0].faultLocator;
							 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
							 djson.faultReason			    =flgsomedata[0].faultReason;
							 djson.protection			    =flgsomedata[0].protection;
							 djson.managementDetail			    =flgsomedata[0].managementDetail;
							 djson.faultDetail			    =flgsomedata[0].faultDetail;
							 djson.blockOrNot			    =flgsomedata[0].blockOrNot;
							 djson.blockCase			    =flgsomedata[0].blockCase;
						 }
						 if(flgsomedata[0].faultCategory=="通道异常"){
							 url="/faultTrip_channelException/index.jsp?";
							 tipopen="通道异常-编辑";
							 //只传通道异常字段
							 djson.stationFaultOrNot		    =flgsomedata[0].stationFaultOrNot ;
							 djson.powerOutReason	 =flgsomedata[0].powerOutReason			    ;
							 djson.powerOutTime	 =flgsomedata[0].powerOutTime			    ;
							 djson.threeSpanOrNot			=flgsomedata[0].threeSpanOrNot ;
							 djson.repairOrNot			=flgsomedata[0].repairOrNot	 ;
							 djson.repairEndOrNot			=flgsomedata[0].repairEndOrNot ;
							 djson.recoveryDate		     	=flgsomedata[0].recoveryDate ;
							 djson.faultLocator			    =flgsomedata[0].faultLocator;
						 }
						 var json= JSON.stringify(djson);
						 var len=json.length;
						 json=json.substring(1,len-1); 		
						 var url =akapp.appname+url+"data="+json ;
							var title =tipopen;
							myak.open(url,title,null,function(action){
								 initdatagrid( );
							});
					        changeWinCSS();
						$(".btn.wordBtn").removeClass("selected");
						$("#ck").addClass("selected");
					});
					$("#dc").click(function(){
						var temp = JSON.stringify({
							'status':me.status,//当前状态，新增需跟踪，闭环，归档
							'gzlb':me.gzlb,//故障类别
							'dydj':me.dydj,//电压等级
							'ssds':me.ssds,//运维单位
						    'zxl':me.zxl, 
						    'sbmc':me.sbmc,
						    'sfqx':me.sfqx,
						    'gzsj1':  me.gzsj1,
						    'gzsj2':me.gzsj2
						}); 
						if(me.grid.data.length==0){
							showtips(  "当前无数据无法导出","warning");
							return ;
						}
						ak.confirm("是否确定导出？", "确定？",
			    	            function (action) {
			    	                if (action == "ok") { 
			    	                	
			    	                	exak.exportPagesDataToExcel(me.grid,temp,"故障跳闸");
			    	                } 
			    	            }
			    	      );
						$(".btn.wordBtn").removeClass("selected");
						$("#dc").addClass("selected");
						changeWinCSS();
					});
					me.aksplitter.hidePane(1);
			}
		});
		//模糊搜索
		$("#searchImg").click(
				function(){
					getcxtj();
					initdatagrid_num( );
				});
		function jqss() {
			var display = $('#jqcxtj').css('display');
			if (display == "none") {
				$('#dtdiv').css('top',"64px");
				$('#jqcxtj').css('size','0%');
				$('#jqcxtj').slideToggle();
			} else {
				$('#jqcxtj').hide();
				$('#dtdiv').css('size',"0px");
				$('#jqcxtj').slideToggle('size','10%');
			}
		}
		//精确搜索
		$(".jqss").click(function(){
			hidechxuntianjian();
//			jqss();
			ak("gzsj1").on("drawdate",function(e){
				onDrawDate(e);
			} );
			ak("gzsj2").on("drawdate",function(e){
				onDrawDate(e);
			} );
//			 //所属地市
			ak("ssds").on("beforeload",function(e){		// 运维单位树
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
			 ak("ssds").load(akapp.commonService+"/treeUtil/getYwdwTree.do");
			 ak("ssds").on("closeclick",function(e){
				  var obj = e.sender;
		          obj.setText("");
		          obj.setValue("");
			});
			//线路/站
			ak("zxl").on("buttonclick",function(){
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
					me.LineNameTree.on("beforeload",function(e){
						var type = e.node.nodeType;
						var id = e.node.id;
						if (typeof (type) == 'undefined') {
							e.params.nodeType = 'root';
						} else {
							e.params.id = id;
							e.params.nodeType = type;
						}
						if(typeof(ak("ssds").getSelectedNode() ) !="undefined"){
							e.params.rootID = ak("ssds").getSelectedNode().id;
						}
					});
					me.LineNameTree.load(akapp.commonService+'/treeUtil/getDeptAndXlTree.do');
						//		站
					me.stationTree.on("beforeload",function(e){
						var type = e.node.nodeType;
						var id = e.node.id;
						if (typeof (type) == 'undefined') {
							e.params.nodeType = 'root';
						} else {
							e.params.id = id;
							e.params.nodeType = type;
						}
						if(typeof(ak("ssds").getSelectedNode() ) !="undefined"){
							e.params.rootID = ak("ssds").getSelectedNode().id;
						}
					});
					me.stationTree.load(akapp.commonService+'/treeUtil/getDeptAndDzTree.do');
			});
			
			 ak("zxl").on("closeclick",function(e){
				  var obj = e.sender;
		          obj.setText("");
		          obj.setValue("");
			});
			
			$("#but").click(function(){
				if($(".selected").text() == '站' ){
					if( me.stationTree.getSelectedNode()  !="undefined" && me.stationTree.getSelectedNode().nodeType=="bdz"){
						ak("zxl").setText(me.stationTree.getText()) ;   
						 //设备名称
					 	me.deviceNameTree.on("beforeload",function(e){
							var type = e.node.nodeType;
							var id = e.node.id;
							if (typeof (type) == 'undefined') {
								e.params.nodeType = 'root';
							} else {
								e.params.id = id;
								e.params.nodeType = type;
							}
							e.params.rootID =  me.stationTree.getSelectedNode ().id;
					 	});
						me.deviceNameTree.load(akapp.commonService+'/treeUtil/getBdzYcsbTree'); 
						me.deviceNameTree.on("closeclick",function(e){
							  var obj = e.sender;
					            obj.setText("");
					            obj.setValue("");
						});
					}else{
						showtips( "请选择电站","info");
						return;
					}
				}else{
					 if(me.LineNameTree.getSelectedNode() !="undefined" &&me.LineNameTree.getSelectedNode().nodeType =="xl" ){
						 ak("zxl").setText(me.LineNameTree.getText()) ;  
						 //设备名称
						 	me.deviceNameTree.on("beforeload",function(e){
								var type = e.node.nodeType;
								var id = e.node.id;
								if (typeof (type) == 'undefined') {
									e.params.nodeType = 'root';
								} else {
									e.params.id = id;
									e.params.nodeType = type;
								}
								  e.params.rootID =  me.LineNameTree.getSelectedNode ().id;
						 });
						me.deviceNameTree.load(akapp.commonService+'/treeUtil/getXlSbTree');
						me.deviceNameTree.on("closeclick",function(e){
							  var obj = e.sender;
					            obj.setText("");
					            obj.setValue("");
						});
					}else{
						showtips("请选择线路","info" );
						return;
					}
				}
				ak("deviceOrLineWindow").hide ( ) ;
			}); 
			 
			ak('dydj').load(akapp.appname+'/faultTrip_edit_new/getvoltageLevel');//电压等级
			ak('gzlb').load(akapp.appname+'/faultTrip_edit_new/getFaultCategory');
			//是否抢修
			ak('sfqx').load(akapp.appname+'/faultTrip_edit_new/getrepairOrNot');// 是否抢修
			//设备名称
			$("#searchBtn").click(function(){
				getcxtj(); 
				initdatagrid_num();
			});
			$("#resetBtn").click(function(){
				ak("gzsj1").setText();
		   		ak("gzsj2").setText();
		   		ak("dydj").setValue("");
		   		ak("ssds").setValue("");
		   		ak("zxl").setText("");
		   		ak("sbmc").setValue("");
		   		ak("gzlb").setValue("");
		   		ak("sfqx").setValue("");
		   		 
		   		me.deviceNameTree.load("[ ]" );
			});
		});
	};
	
	/*禁止选择*/
	function onDrawDate(e) {
        var date = e.date;
        var d = new Date();
        if (date.getTime() > d.getTime()) {
            e.allowSelect = false;
        }
    }
	
	function getcxtj(){
    	me.gzsj1=ak('gzsj1').text;// 故障时间1
		me.gzsj2=ak('gzsj2').text;//故障时间
		me.dydj=ak('dydj').value;//电压等级
		me.ssds=ak('ssds').text;//所属地市
		me.zy=ak('zy').value;//专业
		me.sbmc=ak('sbmc').text;//设备 名称
		me.zxl=ak('zxl').text;//线路/站
		me.gzlb=ak('gzlb').value;// 故障类别
		me.sfqx=ak('sfqx').value;// 是否抢修
		me.mh=ak("keywords").getValue();  
	    me.datajson.gzsj1 =me.gzsj1 ;
	    me.datajson.gzsj2 =me.gzsj2 ;
	    me.datajson.dydj  =me.dydj  ;
	    me.datajson.ssds  =me.ssds  ;
	    me.datajson.zy    =me.zy    ;
	    me.datajson.sbmc  =me.sbmc  ;
	    me.datajson.zxl   =me.zxl   ;
	    me.datajson.gzlb   =me.gzlb   ;
	    me.datajson.sfqx   =me.sfqx   ;
	    me.datajson.status=me.status;
	    me.datajson.mh=me.mh
    }
	
	function initdatagrid_num(){
		initnum();
		initdatagrid();
	}
	
	function hidechxuntianjian(){
		if($("#jqcxtj").hasClass('block')){
			me.aksplitter.showPane(1);
			$("#jqcxtj").removeClass("block");
		}else{
			me.aksplitter.hidePane(1);
			$("#jqcxtj").addClass("block");
		}
	}
	
	function add() {
		var url = akapp.appname+"/faultTrip_edit_new/index.jsp?data=";
		var title = "故障-新增";
		myak.openlittle(url,title,null,function(action){
			if("success" == action) {showtips("新增成功","success");}
			setTimeout(function(){
				initdatagrid_num( );
			},500);
		});
        changeWinCSS();
    } 
	
	 /**
	  * 修改弹出框样式
	  */
	 function changeWinCSS(){
		 $(".ak-modal").css("background", "black");				// 修改弹出框蒙版为黑色
		 $(".ak-panel-header").css("background", "#009694");		// 修改弹出框标题背景色
		 $(".ak-panel-border").css("border-color", "#009694");	// 修改弹出框边框颜色
	 }
	 
	 /* 提示框*/
	 function showtips(aa,bb){
		 ak.showTips({
	            content: "<b>提示</b> <br/>"+aa,
	            state: bb,
	            x: 'center',
	            y: 'center',
	            timeout: 2400
	        });
	 }
	/* function showtips(aa){
		 ak.showTips({
			 content: "info",
			 state: bb,
			 x: 'center',
			 y: 'center',
			 timeout: 2400
		 });
	 }*/
	 
	 function deletete(){
		 var djson={};
		 var id="";
		 var updateDateTime="";
		 var flgsomedata= new Array();  
		 flgsomedata =me.grid.getSelecteds();  
		 if( flgsomedata.length==0 ){
			 showtips("请勾选至少一条数据！","info");
			 return;
		 }
		 //把所有的id放到一个数组
		 for(var i=0;i<flgsomedata.length;i++){ 
		 	id+=flgsomedata[i].id+",";
		 	updateDateTime+=flgsomedata[i].updateDateTime+",";
		 }
		 id = id.substring(0,id.length-1);
		 updateDateTime = updateDateTime.substring(0,updateDateTime.length-1);
		 //把数组放到对象里面传到后台
		 djson.ids=id;
		 djson.updateDateTimes=updateDateTime;
		 confirmClick(djson);
	 }
	 
	 // 删除的确认按钮
	 function confirmClick(objparam) {
	        ak.confirm("确定删除记录？", "确定？",
	            function (action) {
	                if (action == "ok") {
		               	 myak.send('/fault/delete','GET',objparam,false,function(text){
		               		if(text.successful==true){
	 							showtips("删除成功","success");
	 							initdatagrid_num();
	 						}
							 if(text.successful==false){
								  showtips( "更新失败，该记录已发生变化，请刷新页面","warning");
							 }
		          		});
	                } 
	            }
	        );
	    }
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++更新状态+++++++++++++++++++++++
	 function updatestatus( stat){
		 var djson={}; 
		 var id="";
		 var status="";
		 var  tipsinfo ="";
		 var countnoallow = 0 ;
		 var flgsomedata= new Array();  
		 flgsomedata =me.grid.getSelecteds();  
		 if( flgsomedata.length<1){
			 showtips("请至少选择一条记录！","info");
			 return;
		 }
		 
		 for(var i=0;i<flgsomedata.length;i++){ 
			 
			 if(flgsomedata[i].faultCategory=="交流" ){
				 if( 
					flgsomedata[i].deviceName==""||	 
					flgsomedata[i].deviceName==null||	 
					flgsomedata[i].faultCategory==""||	 
					flgsomedata[i].faultCategory==null||	 
					flgsomedata[i].faultPhaseId==""||	 
					flgsomedata[i].faultPhaseId==null||	 
					flgsomedata[i].faultReason==""||	 
					flgsomedata[i].faultReason==null||	 
					flgsomedata[i].faultTime==""||	 
					flgsomedata[i].faultTime==null||	 
					flgsomedata[i].faultType==""||	 
					flgsomedata[i].faultType==null||	 
					flgsomedata[i].stationFaultOrNot=="" ||	 
					flgsomedata[i].stationFaultOrNot==null ||	 
					flgsomedata[i].stationOrLineName=="" ||	 
					flgsomedata[i].stationOrLineName==null 	 
				 ){
					 tipsinfo+=(i+1+",");
					 countnoallow++;
//					 showtips("请编辑未填写字段","info");
//				     return ;
				 }else{
					 id +=flgsomedata[i].id+",";
					 status +=flgsomedata[i].confirmStatus+",";
				 }
			 } 
			 if(flgsomedata[i].faultCategory=="直流" ){
				 if( 
							flgsomedata[i].deviceName==""||	 
							flgsomedata[i].deviceName==null||	 
							flgsomedata[i].faultCategory==""||	 
							flgsomedata[i].faultCategory==null||	 
							flgsomedata[i].faultPhaseId==""||	 
							flgsomedata[i].faultPhaseId==null||	 
							flgsomedata[i].faultReason==""||	 
							flgsomedata[i].faultReason==null||	 
							flgsomedata[i].faultTime==""||	 
							flgsomedata[i].faultTime==null||	 
							flgsomedata[i].faultType==""||	 
							flgsomedata[i].faultType==null||	 
							flgsomedata[i].stationFaultOrNot=="" ||	 
							flgsomedata[i].stationFaultOrNot==null ||	 
							flgsomedata[i].stationOrLineName=="" ||	 
							flgsomedata[i].stationOrLineName==null  	 
						 ){
							 tipsinfo+=(i+1+",");
							 countnoallow++;
		//					 showtips("请编辑未填写字段","info");
		//				     return ;
						 }else{
							 id +=flgsomedata[i].id+",";
							 status +=flgsomedata[i].confirmStatus+",";
						 }
				 
			 } 
			 if(flgsomedata[i].faultCategory=="通道异常" ){
				 if( 
							flgsomedata[i].deviceName==""||	 
							flgsomedata[i].deviceName==null||	 
							flgsomedata[i].faultCategory==""||	 
							flgsomedata[i].faultCategory==null||	 
							flgsomedata[i].faultPhaseId==""||	 
							flgsomedata[i].faultPhaseId==null||	 
							flgsomedata[i].faultTime==""||	 
							flgsomedata[i].faultTime==null||	 
							flgsomedata[i].faultType==""||	 
							flgsomedata[i].faultType==null||	 
							flgsomedata[i].powerOutTime==""||	 
							flgsomedata[i].powerOutTime==null||	 
							flgsomedata[i].stationOrLineName=="" ||	 
							flgsomedata[i].stationOrLineName==null  	 
						 ){
							 tipsinfo+=(i+1+",");
							 countnoallow++;
		//					 showtips("请编辑未填写字段","info");
		//				     return ;
						 }else{
							 id +=flgsomedata[i].id+",";
							 status +=flgsomedata[i].confirmStatus+",";
						 }
			 } 
		 	
		 }
		 if(countnoallow == flgsomedata.length){
			  showtips("所勾选的没有通过校验，请重新编辑必填字段后尝试","info");return ;
		 }else{
			//把数组放到对象里面传到后台
			 id=id.substring(0,id.length-1) ;
			 status=status.substring(0,status.length-1 ) ;
			 var djson={"id":id,"status":status};
			 var infopass="";
			 if(tipsinfo != ""){
				 tipsinfo = tipsinfo.substring(0,tipsinfo.length-1);
				 infopass="第"+tipsinfo+"项未填写必填字段，是否忽略他们，其他转入需跟踪状态"
			 }else{
				 infopass="是否确认转入需跟踪状态？";
			 }
			 ak.confirm(infopass , "确定？",
	 	            function (action) {
	 	                if (action == "ok") { 
	 	                	//把数组放到对象里面传到后台
	 	                	myak.send('/fault/updatestatus','GET',djson,false,function(data){ 
	 	                		if(stat=='bh'){
	 	                			showtips("闭环成功！","info");
	 	                		}else{
	 	                			showtips("归档成功！","info");
	 	                		}
	 	                		initdatagrid( );
	 	          		        initnum();
	 	            		}); 
	 	                } 
	 	            }
	 	      	); 
		 }
		 
		 
		 
		 
	 }
	return me;
});

define([ 'akui','util','akapp'], function( ak,myak,akapp ) { 
	var me={};
	ak.parse();
	me.type="add";
	me.deviceNameflag=null;
	var LineNameTree = ak("LineName"); //  线路
	var stationTree = ak("station"); //战
	var deviceNameTree= ak("deviceName"); 					   // 设备树
	var deviceTypeTree= ak("deviceType"); 					   // 设备类型，变电要求增加
	me.render = function() { 
		//查看编辑新增,只有初始化下拉才可以赋值，所以要先初始化
		initwaitingselectandsureButton();//初始化下拉选择框
		//和你的树加载不加载没多大关系
		 deviceNameTree.on("nodeclick",function(e){ 
			 if(typeof(e.node.sblx) != 'undefined'){
				 deviceTypeTree.setValue(e.node.sblx);
				 showtips("设备类型已选择","info");
			 }else{
				 if(me.deviceNameflag=='bdz'){
					 showtips("无法获取设备类型，如果需要维护设备类型，重新选择设备","info");
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
		var params = decodeURI(window.location.search); 
		    params=params.split('?data=')[1].split('&');//截取参数
		if(params[0] != ''){//查看和编辑
			params=JSON.parse('{'+ params[0]+'}');
			me.type = params.edit; 
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++业务判断+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/		    
		    //编辑的时候
		    if(me.type == "编辑"){
//		    	赋值 这三个暂时不传值，
		    	ak("organization").setValue(params.organizationId) ; 
				ak("organization").setText(params.organization) ; 
				ak("stationOrLineName").setValue(params.stationOrLineId) ; 
		    	ak("stationOrLineName").setText(params.stationOrLineName) ; 
		    	ak("deviceName").setValue(params.deviceId) ; 
		    	ak("deviceName").setText(params.deviceName) ; 
		    	ak("faultTime").setValue(params.faultTime) ; 
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
		    }
		     //查看的时候
		    if(me.type == "查看"){ //把input属性设置为:allowInput="false",才可以禁用输入
		    	 $(".btncss").css("display","none");//隐藏保存按钮
		    	 return ;
		    }
		    var count=0 ;
			$(".words").click(function(){ 
				count=1;
				if(count==1){
					var form = new ak.Form("#form");            
					var data = form.getData(); debugger; 
					if(data.faultTime=="" ||data.faultCategory==""||data.faultPhaseId==""){
						showtips("请填写：故障类别、故障时间、故障相别/极号","info");
						return;
					}
					if(data.weather.length> 25){
						showtips("天气最大长度21,请重新填写","info");
						return;
					}
					data.id=params.id;
					data.updateDateTime=params.updateDateTime;
					data.status=params.status;
					data.stationOrLineName=ak("stationOrLineName").getText();
					data.deviceName=ak("deviceName").getText();
					data.organization=ak("organization").getText();
					//获取id 
					data.stationOrLineId=ak("stationOrLineName").getValue();
					data.deviceId=ak("deviceName").getValue();
					data.organizationId=ak("organization").getValue(); 
					myak.send('/fault/update','GET',data,false,function(text){ 
						  if(text.successful==true){
	 							showtips("更新成功","info");
	 						}
						  if(text.successful==false){
							  showtips("更新失败，该记录已发生变化，请刷新页面","info");
						  }
				  	}); 
				}
			});
		}else{
			save();
		}
	};
	
	//设置不可编辑
	function setReadOnly(){
		 ak("type").readOnly=true;
	   	 ak("stationOrLineName").readOnly=true;
	   	 ak("deviceName").readOnly=true;
	   	 ak("typeName").readOnly=true;
	   	 ak("faultType").readOnly=true;
	   	 ak("faultTime").readOnly=true;
	   	 ak("organization").readOnly=true;
	   	 ak("voltageLevel").readOnly=true; 
	   	 if(""!=ak("repairOrNot").getValue() || ak("repairOrNot").getValue()==null){
	   		 ak("repairEndOrNot").readOnly=true;//当点击选择repairOrNot时候，再打开
	   	 }
	}
	//下拉框数组暂时先静态
	function initwaitingselectandsureButton(){
//		绑定确定按钮
		$("#but").click(function(){
			if($(".selected").text() == '站' ){
				if(typeof(stationTree.getSelectedNode() ) =="undefined" ){
					showtips("请选择电站","info");
					return ;
				}
				if( stationTree.getSelectedNode().nodeType=="bdz"){
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
						e.params.rootID =me.deviceid;    // stationTree.getSelectedNode().id;
				 	});
				 	deviceNameTree.load(akapp.commonService+'/treeUtil/getBdzYcsbTree'); 
				}else{
					showtips("请选择电站","info");
					return;
				}
			}else{
				if(typeof(LineNameTree.getSelectedNode() ) =="undefined" ){
					showtips("请选择线路","info");
					return ;
				}
				 if( LineNameTree.getSelectedNode().nodeType =="xl" ){
					 me.deviceNameflag=LineNameTree.getSelectedNode().nodeType;
					 ak("stationOrLineName").setText(LineNameTree.getSelectedNode().text) ; 
					 ak("stationOrLineName").setValue(LineNameTree.getSelectedNode().id) ; 
					 ak("voltageLevel").setValue(LineNameTree.getSelectedNode().voltageLevel);
					 me.deviceid = LineNameTree.getSelectedNode().id;
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
							  e.params.rootID =me.deviceid ;//  LineNameTree.getSelectedNode().id;
					 });
					deviceNameTree.load(akapp.commonService+'/treeUtil/getXlSbTree'); 
				}else{
					showtips("请选择线路","info");
					return;
				}
			}
			ak("deviceOrLineWindow").hide ( ) ;
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
		ak('faultPhaseId').load(akapp.appname+'/faultTrip_edit_new/getfaultPhaseId');//故障相别极号
		ak('faultCategory').load(akapp.appname+'/faultTrip_edit_new/getFaultCategory');//故障类别
		ak('faultCategory').on("valuechanged",function(){ 
			/*重新加载基数，否则会出现bug，基数一直是改变的，一直变为0*/
			ak('faultType').load(akapp.appname+'/faultTrip_edit_new/getFaultType');//故障类型
			ak('faultPhaseId').load(akapp.appname+'/faultTrip_edit_new/getfaultPhaseId');//故障相别极号
			/*三个专业的数组*/
			var targetarrdc=new Array();
			var targetarrac=new Array();
			var targetarrce=new Array();
			var faultPhaseIddc=new Array();
			var faultPhaseIdac=new Array();
			if(ak('faultCategory').getText()=='直流'){
				 var j =0 ;
				 for(var i = 0;i<ak("faultType").data.length;i++){
					 if(i==3){targetarrdc[j]=ak("faultType").data[i] ; j++;}
					 if(i==4){targetarrdc[j]=ak("faultType").data[i] ; j++;}
					 if(i==5){targetarrdc[j]=ak("faultType").data[i] ; j++;}
				 } 
				 var fc =0 ;
				 for(var i = 0;i<ak("faultPhaseId").data.length;i++){ 
					 if(i==13){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==14){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==15){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
				 } 
				 ak("faultPhaseId").setData(faultPhaseIddc);
				ak("faultType").setData(targetarrdc);
//				换相失败，直流再启动，直流闭锁
			}else if(ak('faultCategory').getText()=='交流'){
				var j =0 ;
				for(var i = 0;i<ak("faultType").data.length;i++){
					if(i==1){targetarrac[j]=ak("faultType").data[i] ; j++;}
					if(i==2){targetarrac[j]=ak("faultType").data[i] ; j++;}
				} 
				 var fc =0 ;
				 for(var i = 0;i<ak("faultPhaseId").data.length;i++){ 
					 if(i==0){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==1){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==2){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==3){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==4){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==5){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==6){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==7){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==8){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==9){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==10){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==11){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
					 if(i==12){faultPhaseIddc[fc]=ak("faultPhaseId").data[i] ; fc++;}
				 } 
				 ak("faultPhaseId").setData(faultPhaseIddc);
				ak("faultType").setData(targetarrac);
			}else{//通道异常
				var j =0 ;
				for(var i = 0;i<ak("faultType").data.length;i++){
					if(i==2){targetarrce[j]=ak("faultType").data[i] ; j++;}
				} 
				ak("faultType").setData(targetarrce);
			}
		});
		ak('voltageLevel').load(akapp.appname+'/faultTrip_edit_new/getvoltageLevel');//电压等级
		ak("faultTime").on("drawdate",function(e){
			onDrawDate(e);
		});
	}
	/*禁止选择*/
	function onDrawDate(e) {
        var date = e.date;
        var d = new Date();
        if (date.getTime() > d.getTime()) {
            e.allowSelect = false;
        }
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
	 //保存
	 function save(){
		   $(".words").click(function(){ 
			    var form = new ak.Form("#form");            
				var data = form.getData();      //获取表单多个控件的数据
				if(data.weather.length> 25){
					showtips("天气最大长度21,请重新填写","info");
					return;
				}
				data.stationOrLineName=ak("stationOrLineName").getText();
				data.deviceName=ak("deviceName").getText();
				data.organization=ak("organization").getText();
				data.stationOrLineId=ak("stationOrLineName").getValue();
				data.deviceId=ak("deviceName").getValue();
				data.organizationId=ak("organization").getValue();
				if(data.faultTime=="" ||data.faultCategory==""||data.faultPhaseId==""){
					showtips("请填写：故障类别、故障时间、故障相别/极号","info");
					return;
				}
				if(data.weather.length>21){
					showtips("天气字数超过最大长度请重新填写","info");
					return;
				}
				myak.send('/fault/save','GET',data,false,function(text){
					  if(text.successful== false ){
						  showtips("更新失败请稍后尝试","info");return;
					  }
					  showtips("保存成功","info");
					  //关闭窗口
					  window.CloseOwnerWindow("success");
					  
			  	}); 
		   });   
	  }   
	return me;
});

define(['akui', 'util', 'exportData', 'akapp', 'echarts','echartsWrapper'],function( ak ,myak,exak,akapp,echarts){
	var me ={};
	me.grid=  ak("taskdatagrid");
	me.status="进行中";
	me.organizationid=null;
	me.tasklevel=null;
	me.strids=null;
	
	var m = 1;
	var userGrid = ak("userGrid");
	var stationLineGrid = ak("stationLineGrid");
	var   personGrid  = ak("personGrid");
	var   carGrid     = ak("carGrid");
	var   pointGrid   = ak("pointGrid");
	var   hospitalGrid  = ak("hospitalGrid");
	var   hotelGrid   = ak("hotelGrid");
	var   faultGrid   = ak("faultGrid");
	var   defectGrid   = ak("defectGrid");
 
	var   weatherGrid   = ak("weatherGrid");
	var   environmentGrid   = ak("environmentGrid");
	var   gridRiskDataGrid   = ak("gridRiskDataGrid");
	var   overLoadDataGrid   = ak("overLoadDataGrid");
	var   monitoringDataGrid   = ak("monitoringDataGrid");
	
	
	
	me.render = function(){
		ak.parse();
		init_mission_status();  //任务状态
		initdata();
		_initButton();
		_initsecondview();
	}
	
	function _initsecondview(){
		//用户
		//特级过滤
		$("#user_level_super").on('click',function(e){
			ak('userofpowWindow').show ("1px", "1px");
			$("#specialLevel").addClass("selected")            //当前<li>元素电站高亮
			.siblings().removeClass("selected");  
			me.userTab = 1;   //过滤用户级别
			getUserInfo(me.userTab);
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
		});
		//一级过滤
		$("#user_level_one").on('click',function(e){
			ak('userofpowWindow').show ("1px", "1px");
			$("#oneLevel").addClass("selected")            //当前<li>元素电站高亮
			.siblings().removeClass("selected");  
			me.userTab = 2;   //过滤用户级别
			getUserInfo(me.userTab);
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
		});
		//二级过滤
		$("#user_level_two").on('click',function(e){
			ak('userofpowWindow').show ("1px", "1px");
			$("#twoLevel").addClass("selected")            //当前<li>元素电站高亮
			.siblings().removeClass("selected");  
			me.userTab = 3;   //过滤用户级别
			getUserInfo(me.userTab);
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
		});
		//三级过滤
		$("#user_level_three").on('click',function(e){
			ak('userofpowWindow').show ("1px", "1px");
			$("#threeLevel").addClass("selected")            //当前<li>元素电站高亮
			.siblings().removeClass("selected");  
			me.userTab = 4;   //过滤用户级别
			getUserInfo(me.userTab);
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
		});
		//线路
		ak("lineChart").on("chartclick", 	function  (e) { 
			var code = e.xAxisCode;//电压等级35
			var slensurelevel = e.seriesIndex+1;
			ak('stationorlinewindow').show ("1px", "1px");
			$("#lineTab").addClass("selected")            // 线路高亮
	          .siblings().removeClass("selected");
			
		 	stationLineGrid.load(akapp.appname+'/overviewTotal/getLineorStationGrid');
			var params = {"status":me.status,
					"organizationid":me.organizationid,
					"taskLevel": me.tasklevel,
					"voltagelevel":code,
					"slensurelevel":slensurelevel,
					"stationlinetype":"01"
					};
			stationLineGrid.load(params);
		  
			
			
			 var $div_li =$("div.tab_menu ul#SLTabs li");
		        $div_li.click(function(){
			         $(this).addClass("selected")            //当前<li>元素高亮
			          .siblings().removeClass("selected");  //去掉其它同辈<li>元素的高亮
			          var index = $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。数组的下标
			          if(index==0){
//			        	  加载电站
			        	  params = {"status":me.status,
			  					"organizationid":me.organizationid,
			  					"taskLevel": me.tasklevel,
			  					"stationlinetype":"02"
			  					};
			  			stationLineGrid.load(params);
			        	  
			          }else{
//			        	  加载线路
			        	params = {"status":me.status,
			  					"organizationid":me.organizationid,
			  					"taskLevel": me.tasklevel,
			  					"voltagelevel":code,
			  					"slensurelevel":slensurelevel,
			  					"stationlinetype":"01"
			  					};
			  			stationLineGrid.load(params);
			          }
		        }).hover(function(){
		          $(this).addClass("hover");     //over out,分别代表鼠标悬停和移除的函数
		        },function(){
		          $(this).removeClass("hover");
		        });
		});
		//电站
		ak("stationChart").on("chartclick", 	function  (e) { 
			var code = e.xAxisCode;
			var slensurelevel = e.seriesIndex+1;
			ak('stationorlinewindow').show ("1px", "1px");
			$("#stationTab").addClass("selected")            //当前<li>元素电站高亮
			.siblings().removeClass("selected");  //手动选择tab
			
			stationLineGrid.load(akapp.appname+'/overviewTotal/getLineorStationGrid');
			var params = {"status":me.status,
					"organizationid":me.organizationid,
					"taskLevel": me.tasklevel,
					"voltagelevel":code,
					"slensurelevel":slensurelevel,
					"stationlinetype":"02"
					};
			stationLineGrid.load(params);
		 
			
			 var $div_li =$("div.tab_menu ul#SLTabs li");
		        $div_li.click(function(){
			         $(this).addClass("selected")            //当前<li>元素高亮
			          .siblings().removeClass("selected");  //去掉其它同辈<li>元素的高亮
			          var index =  $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
			          if(index==0){
//		        	  加载电站 
		        	  params = {"status":me.status,
		  					"organizationid":me.organizationid,
		  					"taskLevel": me.tasklevel,
		  					"voltagelevel":code,
							"slensurelevel":slensurelevel,
		  					"stationlinetype":"02"
		  					};
		  			stationLineGrid.load(params);
		        	  
		          }else{
//		        	  加载线路
		        	params = {"status":me.status,
		  					"organizationid":me.organizationid,
		  					"taskLevel": me.tasklevel,
		  					"stationlinetype":"01"
		  					};
		  			stationLineGrid.load(params);
		          }
		        }).hover(function(){
		          $(this).addClass("hover");     //over out,分别代表鼠标悬停和移除的函数
		        },function(){
		          $(this).removeClass("hover");
		        });
		});
		//人员
		$("#personcount").on("click",function(e){
			 showresourcewindow(1);
		});
//		车辆
		$("#Vehiclecount").on("click",function(e){
			showresourcewindow(1);
		});
		
//		驻点
		$("#Stationcount").on("click",function(e){
			showresourcewindow(2);
		});
//		医院
		$("#Hospitalcount").on("click",function(e){
			showresourcewindow(3);
		});
//		宾馆
		$("#Hotelcount").on("click",function(e){
			showresourcewindow(2);
		});
//		故障已恢复
		$("#fault_dealed").on("click",function(e){
			ak("faultWindow").show ("1px", "1px");
			faultGrid.load(akapp.appname+'/overviewTotal/getFaultgrid');
			var params = {
					"status":me.status,
  					"organizationid":me.organizationid,
  					"taskLevel": me.tasklevel,
  					"flag":"已恢复"
			}
			ak("faultGrid").load(params);
			 
		});
//		故障处理中
		$("#fault_dealing").on("click",function(e){
			ak("faultWindow").show ("1px", "1px");
			 
			faultGrid.load(akapp.appname+'/overviewTotal/getFaultgrid');
			var params = {
					"status":me.status,
  					"organizationid":me.organizationid,
  					"taskLevel": me.tasklevel,
  					"flag":"处理中"
			}
			ak("faultGrid").load(params);
		});
		
//		缺陷已消缺
		$("#DefectStatedealed").on("click",function(e){
			ak("defectWindow").show ("1px", "1px");
			defectGrid.load(akapp.appname+'/overviewTotal/getDefectgrid');
			var params = {
					"status":me.status,
  					"organizationid":me.organizationid,
  					"taskLevel": me.tasklevel,
  					"flag":"已消缺"
			}
			defectGrid.load(params);
		});
//		缺陷未消缺
		$("#DefectStatedealing").on("click",function(e){
			ak("defectWindow").show ("1px", "1px");
			defectGrid.load(akapp.appname+'/overviewTotal/getDefectgrid');
			var params = {
					"status":me.status,
  					"organizationid":me.organizationid,
  					"taskLevel": me.tasklevel,
  					"flag":"未消缺"
			}
			defectGrid.load(params);
		});
//		气象
		$("#weather").on("click",function(e){
			show_run_workinfo_second(1);
		});
//		环境
		$("#environment").on("click",function(e){
			show_run_workinfo_second(2);
		});
//		电网风险
		$("#gridRisk").on("click",function(e){
			show_run_workinfo_second(3);
		});
//		重过载
		$("#overLoad").on("click",function(e){
			show_run_workinfo_second(4);
		});
//		在线监测
		$("#monitoring").on("click",function(e){
			show_run_workinfo_second(5);
		});
	}
	 /*运行工况二级页面*/
	function  show_run_workinfo_second( clickindex ){
		
		 ak('run_workinfopro').show ("1px", "1px");//window 弹出位置
		 
		 
		 var $div_li =$("div.tab_menu_info ul li");
	        $div_li.click(function(){
		         $(this).addClass("selected")            //当前<li>元素高亮
		          .siblings().removeClass("selected");  //去掉其它同辈<li>元素的高亮
		          var index =  $div_li.index(this);  // 获取当前点击的<li>元素 在 全部li元素中的索引。
		          $("div.tab_box_info > div")   	//选取子节点。不选取子节点的话，会引起错误。如果里面还有div 
					.eq(index).show()   //显示 <li>元素对应的<div>元素
					.siblings().hide(); //隐藏其它几个同辈的<div>元素
		          if(index ==0){
//		        	  加载气象grid
		        	  weatherGrid.setUrl(akapp.heavyOverLoad + '/meteorological/showMeteorologicalResult');
		     		 weatherGrid.load({
		     				'meteorologicalType':'1,2,3,4,5,6,7',
		     				'confirmStatus':'1,2',
		     				'maintenanceUnitId':me.organizationid,
		     				'stationOrLineId': me.strids,
		     				'startTimeFrom':ak.formatDate(new Date(new Date()-24*60*60*1000),'yyyy-MM-dd hh:mm:ss'),
		     				'startTimeTo':ak.formatDate(new Date(),'yyyy-MM-dd hh:mm:ss')
		     			});
		          }
		          if(index ==1){
//		        	  加载环境grid
		        	  environmentGrid.setUrl(akapp.heavyOverLoad + '/meteorological/showMeteorologicalResult');
		        	  environmentGrid.load({
		      			'meteorologicalType':'8,9,10,11',
		      			'confirmStatus':'1,2',
		      			'maintenanceUnitId':me.organizationid,
		      			'stationOrLineId': me.strids,
		      			'startTimeFrom':ak.formatDate(new Date(new Date()-24*60*60*1000),'yyyy-MM-dd hh:mm:ss'),
		      			'startTimeTo':ak.formatDate(new Date(),'yyyy-MM-dd hh:mm:ss')
		      		});
		          }
		          if(index ==2){
//		        	  加载电网风险grid
		        	  gridRiskDataGrid.setUrl(akapp.gridRisk + '/gridOperationInformation/showGridRiskResult');
		        	  gridRiskDataGrid.load({'maintenanceUnitId':me.organizationid,
		        		  'stationOrLineId':me.strids});
		          }
		          if(index ==3){
//		        	  加载重过载grid
		        	  overLoadDataGrid.setUrl(akapp.heavyOverLoad + '/heavyOverload/showHeavyOverloadResult');
		        	  overLoadDataGrid.load({
		      			'confirmStatus':'1,2,3,4',
		      			'maintenanceUnitId':me.organizationid,
		      			 'stationOrLineId':me.strids
		      		}); 
		          }
		          if(index ==4){
//		        	  加载在线监测grid
		        	  monitoringDataGrid.setUrl(akapp.heavyOverLoad + '/onlineMonitoring/showOnlineMonitoringResult');
		        	  monitoringDataGrid.load({
		      			'confirmStatus':'2',
		      			'maintenanceUnitId':me.organizationid,
		      			 'stationOrLineId':me.strids
		      		});
		          }
	        }).hover(function(){
	          $(this).addClass("hover");     //over out,分别代表鼠标悬停和移除的函数
	        },function(){
	          $(this).removeClass("hover");
	        });
		 
		 
		/* var params = {
				 "status":me.status,
				 "organizationid":me.organizationid,
				 "taskLevel": me.tasklevel,
		 }*/
		   
		 /* myak.send('/overviewTotal/getLineorStationGrid','GET',params,false,function(data){ 
			  if(typeof(data)!='undefined' && data.length>1 ){
				  for(var i =0 ;i<data.length;i++){
					  me.strids+= data[i].id ;
				  }
			  }
		 }); */
		 if(clickindex ==1 ){debugger;
		 $("#qxweather").click();        //当前<li>元素 高亮
			 $("#qxweather").addClass("selected")            //当前<li>元素 高亮
				.siblings().removeClass("selected");  
			 weatherGrid.setUrl(akapp.heavyOverLoad + '/meteorological/showMeteorologicalResult');
			 weatherGrid.load({
				 'meteorologicalType':'1,2,3,4,5,6,7',
				 'confirmStatus':'1,2',
				 'maintenanceUnitId':me.organizationid,
				 'stationOrLineId': me.strids,
				 'startTimeFrom':ak.formatDate(new Date(new Date()-24*60*60*1000),'yyyy-MM-dd hh:mm:ss'),
				 'startTimeTo':ak.formatDate(new Date(),'yyyy-MM-dd hh:mm:ss')
			 });
		 }
		 if(clickindex ==2){
			 $("#hj").click();           //当前<li>元素 高亮
			 $("#hj").addClass("selected")            //当前<li>元素 高亮
			 .siblings().removeClass("selected");  
//			 加载环境grid
       	  environmentGrid.setUrl(akapp.heavyOverLoad + '/meteorological/showMeteorologicalResult');
       	  environmentGrid.load({
     			'meteorologicalType':'8,9,10,11',
     			'confirmStatus':'1,2',
     			'maintenanceUnitId':me.organizationid,
     			'stationOrLineId': me.strids,
     			'startTimeFrom':ak.formatDate(new Date(new Date()-24*60*60*1000),'yyyy-MM-dd hh:mm:ss'),
     			'startTimeTo':ak.formatDate(new Date(),'yyyy-MM-dd hh:mm:ss')
     		});
		 }
		 if(clickindex ==3 ){
			 $("#dwfx").click();           //当前<li>元素 高亮
			 $("#dwfx").addClass("selected")            //当前<li>元素 高亮
			 .siblings().removeClass("selected");  
//			 加载电网风险grid
       	  gridRiskDataGrid.setUrl(akapp.gridRisk + '/gridOperationInformation/showGridRiskResult');
       	  gridRiskDataGrid.load({'maintenanceUnitId':me.organizationid,
       		  'stationOrLineId':me.strids});
			 
		 }
		 if(clickindex ==4){
			 $("#zgz").click();
			 $("#zgz").addClass("selected")            //当前<li>元素 高亮
			 .siblings().removeClass("selected");  
//			 加载重过载grid
       	  overLoadDataGrid.setUrl(akapp.heavyOverLoad + '/heavyOverload/showHeavyOverloadResult');
       	  overLoadDataGrid.load({
     			'confirmStatus':'1,2,3,4',
     			'maintenanceUnitId':me.organizationid,
     			 'stationOrLineId':me.strids
     		}); 
		 }
		 if(clickindex ==5 ){
			 $("#zxjc").click() ;
			 $("#zxjc").addClass("selected")            //当前<li>元素 高亮
			 .siblings().removeClass("selected");  
//			  加载在线监测grid
        	  monitoringDataGrid.setUrl(akapp.heavyOverLoad + '/onlineMonitoring/showOnlineMonitoringResult');
        	  monitoringDataGrid.load({
      			'confirmStatus':'2',
      			'maintenanceUnitId':me.organizationid,
      			 'stationOrLineId':me.strids
      		});
		 }
		 
		 
		  
		
	}
	
	function showresourcewindow(clickindex ){
		  ak('resourcesPro').show ("1px", "1px");
		  /*加载所有的grid*/
		  loadallresourcegrid();
		  /*左右按钮效果*/
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
		  
		  if(clickindex == 1){
			  $(".leftbutton_click").click();
		  }else{
			  $(".rightbutton_click").click();
		  }
		  
	}
	
	 
	function loadallresourcegrid(){
//		
		personGrid.load(akapp.appname+'/overviewTotal/getpersonGrid');
		var params = {"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel": me.tasklevel 
				};
		personGrid.load(params);
//		
		carGrid.load(akapp.appname+'/overviewTotal/getcarGrid');
		var params = {"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel": me.tasklevel 
		};
		carGrid.load(params);
//		   
		pointGrid.load(akapp.appname+'/overviewTotal/getpointGrid');
		var params = {"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel": me.tasklevel 
		};
		pointGrid.load(params);
//		     
		hospitalGrid.load(akapp.appname+'/overviewTotal/gethospitalGrid');
		var params = {"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel": me.tasklevel 
		};
		hospitalGrid.load(params);
		   
//		
		hotelGrid.load(akapp.appname+'/overviewTotal/gethotelGrid');
		var params = {"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel": me.tasklevel 
		};
		hotelGrid.load(params);
	}
	var userClickCount=1;
	function  getUserInfo(ensureLevel ){
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
						divImg = "<img src='res/img/weizhi.png' style='width:18px; cursor:pointer;' />";
						e.cellHtml = divImg;
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
				 		ak("userPlanInfoWindow").show("center", "145px");
				 		$("#userPlanInfoWinGrid").parent().parent().css({"background":"#eceef6", "padding":"10px"});
						break;
				 	case "geographyLocation":
				 		$("#tim_pow_org").css({display :"none"});
						$("#jianjie").css({display :"block"});
						break;
					default:
						break;
				 }
			 });
//			userGrid.setPageSize(2);
			userGrid.load(akapp.appname+'/overviewTotal/getUsergrid');
		}
		
		var params = {"status":me.status,
				"organizationid":me.organizationid,
				"userlevel": ensureLevel,   //任务等级用来过滤 保电任务的
				"taskLevel": me.tasklevel
				};
		userGrid.load(params);
		
		userClickCount++;
	
		
	}
	
	function initdata(){
		_loadGis();  //放开会报错
		init_getTaskLevel(); //任务等级
		init_getUserState(); //用户
		init_getresourceguarantee(); //资源保障
		_loadLineChart( );//线路
		_loadStationChart( );//站线
		_loadtaskGrid(); //保电任务清单
		_initrun_workinfo();//运行工况
		_initrun_content();//运行内容
	}
	function _initButton() {
		$("#underway").click(function (){
			clearGisPosition();
			me.status="进行中";
			me.tasklevel=null;
			$(".super_level").removeClass("levelselected");
	    	$(".one_level").removeClass("levelone");
	    	$(".two_level").removeClass("leveltwo");
	    	$(".three_level").removeClass("levelthree");
			$("#super_level").text(0);
			$("#one_level").text(0);
			$("#two_level").text(0);
			$("#three_level").text(0);
			$("#user_level_super").text(0);
			$("#user_level_one").text(0);
			$("#user_level_two").text(0);
			$("#user_level_three").text(0);
			initdata();init_mission_status();
			$(this).css({"background-color": "rgb( 235, 159 ,9 )"});
			$("#prepared").css({"background-color": "rgb( 0, 150 ,148 )"});
		} ); 
		
		$("#prepared").click(function (){
			clearGisPosition();
			me.status="准备中";
			me.tasklevel=null;
			$(".super_level").removeClass("levelselected");
	    	$(".one_level").removeClass("levelone");
	    	$(".two_level").removeClass("leveltwo");
	    	$(".three_level").removeClass("levelthree");
			$("#super_level").text(0);
			$("#one_level").text(0);
			$("#two_level").text(0);
			$("#three_level").text(0);
			$("#user_level_super").text(0);
			$("#user_level_one").text(0);
			$("#user_level_two").text(0);
			$("#user_level_three").text(0);
			initdata();init_mission_status();
			$(this).css({"background-color": "rgb( 235, 159 ,9 )"});
			$("#underway").css({"background-color": "rgb( 0, 150 ,148 )"});
		} ); 
	    $(".super_level").click(function (){
	    	clearGisPosition();
	    	$(".super_level").addClass("levelselected");
	    	$(".one_level").removeClass("levelone");
	    	$(".two_level").removeClass("leveltwo");
	    	$(".three_level").removeClass("levelthree");
	    	$("#user_level_super").text(0);
			$("#user_level_one").text(0);
			$("#user_level_two").text(0);
			$("#user_level_three").text(0);
	    	me.tasklevel=1;initdata();
		} );
		$(".one_level").click(function (){
			clearGisPosition();
			$(".one_level").addClass("levelone");
			$(".two_level").removeClass("leveltwo");
			$(".super_level").removeClass("levelselected");
	    	$(".three_level").removeClass("levelthree");
			$("#user_level_super").text(0);
			$("#user_level_one").text(0);
			$("#user_level_two").text(0);
			$("#user_level_three").text(0);
			me.tasklevel=2;initdata();
		} );	    
		$( ".two_level").click(function (){
			clearGisPosition();
			$(".one_level").removeClass("levelone");
			$(".super_level").removeClass("levelselected");
			$(".two_level").addClass("leveltwo");
	    	$(".three_level").removeClass("levelthree");
			$("#user_level_super").text(0);
			$("#user_level_one").text(0);
			$("#user_level_two").text(0);
			$("#user_level_three").text(0);
			me.tasklevel=3;initdata();
		} );  
		$(".three_level").click(function (){
			clearGisPosition();
			$(".one_level").removeClass("levelone");
			$(".super_level").removeClass("levelselected");
			$(".two_level").removeClass("leveltwo");
	    	$(".three_level").addClass("levelthree");
			$("#user_level_super").text(0);
			$("#user_level_one").text(0);
			$("#user_level_two").text(0);
			$("#user_level_three").text(0);
			me.tasklevel=4;initdata();
		});	 
		_initYwdwStyle();
	}
	
	function _initrun_content(){
		_init_count_faultTrip(); //故障
		_init_count_DefectState();//缺陷
	}
	
	function _initrun_workinfo(){
		_initstrids();
		_initOverLoadCount() // 重过载数量统计
		_initMonitoringCount() // 在线监测数量统计
		_initWeatherCount(); // 气象预报数量统计
		_initEnvironmentCount(); // 环境预报警数量统计
		_initGridRiskCount() // 电网风险预报警数量统计
	}
	
	function _initstrids(){
		 var params = {
				 "status":me.status,
				 "organizationid":me.organizationid,
				 "taskLevel": me.tasklevel,
		 }
		 
		 myak.send('/overviewTotal/getLineorStationGridall','GET',params,false,function(data){ 
			 me.strids='';
			  if(typeof(data[0].data)!='undefined' && data[0].data.length>1 ){
				  for(var i =0 ;i<data[0].data.length;i++){
					  me.strids+= data[0].data[i].id ;
				  }
			  }
		 }); 
	};
	 
	
	function _init_count_faultTrip(){
		var djson={
				"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel":me.tasklevel
		};
		myak.send('/overviewTotal/getcount_faultTrip','GET',djson,false,function(data){
			 $("#fault_dealing").text(0);
			 $("#fault_dealed").text(0); 
			 $("#fault_dealed").text(data[0].c);
			 $("#fault_dealing").text(data[1].c);
			if(data.successful==true){
			}
		});
	}
	
	function _init_count_DefectState(){
		var djson={
				"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel":me.tasklevel
		};
		myak.send('/overviewTotal/getcount_DefectState','GET',djson,false,function(data){
			  $("#DefectStatedealed").text(0);
			  $("#DefectStatedealing").text(0);
			  $("#DefectStatedealed").text(data[0].c);
			  $("#DefectStatedealing").text(data[1].c);
			if(data.successful==true){
			}
		});
	}
	
	function _initGridRiskCount(){
		var data = {
				 'maintenanceUnitId':me.organizationid ,'stationOrLineId':me.strids 
		};//站线id
 		var p_path = '/gridOperationInformation/showGridRiskCountResult';
 		myak.sendwithoutappname(akapp.gridRisk + p_path,'GET',data,true,function(data){
 			$("#gridRisk")[0].innerText = data.data;
 		});
	}
	
	function _initEnvironmentCount(){
		var data = {'type':'environment','maintenanceUnitId':me.organizationid ,'stationOrLineId':me.strids};
 		var p_path = '/gridOperationInformation/showWeatherCountResult';
 		myak.sendwithoutappname(akapp.gridRisk + p_path,'GET',data,true,function(data){
 			$("#environment")[0].innerText = data.data;
 		});
	}
	
	function _initWeatherCount(){
		var datar = {'type':'weather','maintenanceUnitId':me.organizationid ,'stationOrLineId':me.strids };
		var p_path = '/gridOperationInformation/showWeatherCountResult';
		myak.sendwithoutappname(akapp.gridRisk + p_path,'GET',datar,true,function(datat){ 
			if(datat != null  ){
				$("#weather")[0].innerText = datat.data;
			}
		});
	}
	
	function _initOverLoadCount(){
		var param = {
				'maintenanceUnitId' :me.organizationid,
				'stationOrLineId':me.strids
		};
 		var p_path = '/heavyOverload/showHeavyOverLoadCountResult';
 		myak.sendwithoutappname(akapp.heavyOverLoad + p_path,'GET',param,true,function(data){
 			if( data != null ){
 				$("#overLoad")[0].innerText = data.processing;
 			}
 		});
	}
	
	function _initMonitoringCount(){
		var data = {
				'maintenanceUnitId' :me.organizationid,
				'stationOrLineId':me.strids
				
		};
 		var p_path = '/onlineMonitoring/showOnlineMonitoringCountResult';
 		myak.sendwithoutappname(akapp.heavyOverLoad + p_path,'GET',data,true,function(data){
 			$("#monitoring")[0].innerText = data.tracking;
 		});
	}
//	---------------------------------------------------------------------------
	
	function _loadtaskGrid(){
		me.grid.load(akapp.appname +'/overviewTotal/gettaskGrid');
		var djson={
				"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel":me.tasklevel
		};
//		var 23f= a ;
		me.grid.setPageIndex(0);
		me.grid.setPageSize(10);
		me.grid.load(djson);
		me.grid.on("drawcell",function (e){
			if (e.field == "gointo") {
				var val = e.row.id;
				var divLeft = "<div class='box' style=\"width:0px\"><a href='javascript:void(0)'><div class='tboxo1' id= '"+val+"'>进入</div></a></div>";
			    e.cellHtml = divLeft;
			}
			if (e.field == "location") {
				var val = e.row.id;
				var divLeft = "<div class='box' style=\"width:0px\"><a href='javascript:void(0)'><div class='tboxo1' style='background: url(res/img/weizhi.png) no-repeat center; width:17px;" +
					"height: 33px; width: 17px; margin-left: 25px;' id= '"+val+"'> </div></a></div>";
			    e.cellHtml = divLeft;
			} 
		});
		me.grid.on('cellclick',function(e){
			if (e.field == "gointo") { //进入概览页面
				var rowobject = e.row ;
				turnon_details(rowobject);
			}
			if (e.field == "location") {
				gisPosition(e.row);
			}
		});	
	}
	
	
	function turnon_details(data){
		 var json= JSON.stringify(data);
		 var len=json.length;
		window.location.href= akapp.appname+"/powerSupplyGuaranteeOverviewDetails/index.jsp?id="+ encodeURIComponent(json);
		
//		var op = {
//				id : "id_powerSupplyGuaranteeOverviewDetails",
//				url : akapp.appname+"/powerSupplyGuaranteeOverviewDetails/index.jsp?id="+ encodeURIComponent(json),
//				iframeName : "iframename_powerSupplyGuaranteeOverviewDetails",
//				iframeId : "iframeid_powerSupplyGuaranteeOverviewDetails",
//				parentContainer : $(".ak-fit"),
//				returnPageRefresh : false,// 返回时是否刷新
//				nextButton : false,
//				callback : function() {
//				}
//			};
//		var jump = myak.pageJumping(op);
//		jump.f();
	}
	
	 function skipPage(item){
			top.ak("mainMenu").fire("itemclick", {
		        item :item,
		        isLeaf : true,
		        sender: top.ak("mainMenu") 
		    })
		}
	
	/*获取保电用户状态统计信息*/
	function init_getresourceguarantee(){
		var djson={
				"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel":me.tasklevel
		};
		myak.send('/overviewTotal/getresourceguarantee','GET',djson,false,function(data){
			if(data.length>=1 ){
				 $("#personcount").text(data[0].c);
				 $("#Vehiclecount").text(data[1].c);
				 $("#Stationcount").text(data[2].c);
				 $("#Hotelcount").text(data[3].c);
				 $("#Hospitalcount").text(data[4].c);
			}
		
		});
		
	}
	
	/*获取保电用户状态统计信息*/
	function init_getUserState(){
		var djson={
				"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel":me.tasklevel
		};
		myak.send('/overviewTotal/getUserState','GET',djson,false,function(data){ 
			if(data !=null){ 
				for ( var i = 0 ;i<=data.length-1;i++){
					if(data[i].ensureLevel==1){
						$("#user_level_super").text(data[i].cnt);
					}else if(data[i].ensureLevel==2){
						$("#user_level_one").text(data[i].cnt);
					}else if(data[i].ensureLevel==3){
						$("#user_level_two").text(data[i].cnt);
					}else if(data[i].ensureLevel==4){
						$("#user_level_three").text(data[i].cnt);
					}
				}
			}
			/*if(data[0]  !=null){
				$("#user_level_super").text(data[0].特级);
				$("#user_level_one").text(data[0].一级);
				$("#user_level_two").text(data[0].二级);
				$("#user_level_three").text(data[0].三级);
			}*/
		}); 
	}
	
	/*获取保电任务等级统计信息*/
	function init_getTaskLevel(){
		var djson={
				"status":me.status,
				"organizationid":me.organizationid
		};
		//加载任务状态
		myak.send('/overviewTotal/getTaskLevel','GET',djson,false,function(data){
			if(data !=null){ 
				for ( var i = 0 ;i<=data.length-1;i++){
					if(data[i].ensureLevel==1){
						$("#super_level").text(data[i].count);
					}else if(data[i].ensureLevel==2){
						$("#one_level").text(data[i].count);
					}else if(data[i].ensureLevel==3){
						$("#two_level").text(data[i].count);
					}else if(data[i].ensureLevel==4){
						$("#three_level").text(data[i].count);
					}
				}
			}
		}); 
	}
	
	//统计任务状态 个数
		function init_mission_status(){//把所有进行中的准备中的数据全部加载出来，返回全台
		//过滤参数：运维单位
		var djson={
			"organizationid":me.organizationid		
		};
		//加载任务状态
		myak.send('/overviewTotal/getmissionStatus','GET',djson,false,function(data){
				for(var a =0;a<data.length;a++ ){
					if(data[a].a=='进行中'){
						$("#underway_mission").text(data[a].cnumber1);
					}else{
						$("#preparation_mission").text(data[a].cnumber1);
					}
				}
		}); 
	}
	
	function _loadLineChart( ){
		var datat={}
		var djson={
				"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel":me.tasklevel,
				"stationLineType":"01"
		};
		myak.send('/overviewTotal/getLineChart','GET',djson,false,function(data){
			datat=data; 
		});
		var l1 = datat.seriesData.length;
		var max = 0;
		for(var i=0; i<l1; i++){
			var l2 = datat.seriesData[i].length;
			for(var j=0; j<l2; j++){
				if(datat.seriesData[i][j] > max){
					max = datat.seriesData[i][j];
				}
			}
		}
		if(max < 4){
			max = 4;
		}
		/*data = {
			"xAxisData":[
				{"code":"32","name":"700kV"},
				{"code":"33","name":"800kV"},
				{"code":"34","name":"900kV"},
			],
			"seriesData":[
				[10,9,1],
				[2,9,1],
				[10,2,5],
				[3,9,1]
			]
		}*/
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
		ak("lineChart").setViewModel(datat);
	}
	
	function _loadStationChart( ){ 
		var datat={}
		var djson={
				"status":me.status,
				"organizationid":me.organizationid,
				"taskLevel":me.tasklevel,
				"stationLineType":"02"
		};
		myak.send('/overviewTotal/getLineChart','GET',djson,false,function(data){
			datat=data; 
		});
		var l1 = datat.seriesData.length;
		var max = 0;
		for(var i=0; i<l1; i++){
			var l2 = datat.seriesData[i].length;
			for(var j=0; j<l2; j++){
				if(datat.seriesData[i][j] > max){
					max = datat.seriesData[i][j];
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
		ak("stationChart").setViewModel(datat);
	}
	 /**
	 * 加载gis
	 */
	  function _loadGis(){
				 setTimeout(function(){
					if(myIframe_2D.window.Gis2d_dwxxLayer){ // 初始化基础图层
						myIframe_2D.window.Gis2d_dwxxLayer();
					}
					_loadGis();
				}, 100); 
	} 
	// 初始化运维单位样式
		var _initYwdwStyle = function(){
			ak("ywdw").on("valuechanged", function(e){ //订阅组织机构改变事件，
				clearGisPosition();
				if("qb" ==ak("ywdw").getValue() ){
					me.organizationid= null;
				}else{	
					me.organizationid=ak("ywdw").getValue(); 
				}
				 
				//任务等级清零
				$("#super_level").text(0);
				$("#one_level").text(0);
				$("#two_level").text(0);
				$("#three_level").text(0);
				//用户等级清零
				$("#user_level_super").text(0);
				$("#user_level_one").text(0);
				$("#user_level_two").text(0);
				$("#user_level_three").text(0);
				initdata();
				init_mission_status(); 
				clearGisPosition();
			});
			ak("ywdw").on("beforeshowpopup", function(e){// 运维单位样式
				$(e.sender._listbox.el.parentNode).removeClass("ak-popuptwolist").addClass("ak-popuplist ak-popupthreelist");	
			});
			/*var ywdwArr = [
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSPKZ","text":"长兴供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP06","text":"浦东供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP03","text":"市区供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP08","text":"嘉定供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP0B","text":"金山供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP04","text":"市北供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP05","text":"市南供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP07","text":"奉贤供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP09","text":"青浦供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP0A","text":"松江供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSPHZ","text":"崇明供电公司"}, 
				{ "id":"FFFFFFFFFFFFFFFFFFFFFFFFFFFFSP50","text":"检修公司"},
				{ "id":null,"text":"全选"}
			];
			ak("ywdw").setData(ywdwArr);*/
			//加载运维单位数组
			var p_path = akapp.gridRisk+'/gridOperationInformation/showOrgIdResult';
	 		myak.sendwithoutappname(p_path,'GET',{},true,function(param){
	 			
	 			ak("ywdw").setData(param.data); 
			
			});

		};
	// GIS定位
	function gisPosition(param){
		myIframe_2D.window.Gis2d_vectorImgBuddleBySBIDClose(['info_imgpp_zxjc_fubing']);
		myIframe_2D.window.Gis2d_vectorPointClose(['info_fxpg_sh']) ;
		//最好定位成一个点所以需要对数据进行处理，让这个点最接近真实的保电任务
		var id = param.id;
		var theme = param.theme;
		var datat={}
		var djson={
				"belongtaskid":id
		};
		//获取 最高保电等级用户的中间点
	 	myak.send('/overviewTotal/getmaxleveluser','GET',djson,false,function(data){
			datat=data; 
		}); 
	 	var locax=[];
	 	var locay=[];
	 	 if( datat.length  != 0){
	 		 for ( var i = 0 ; i < datat.length;i++  ){
	 			 var location =[];
	 			var s =  datat[i].location ;
	 			location= s.split(",");
	 			locax[i] = location[0];
	 			locay[i] = location[1];
	 		 }
	 		  var tar = new Array();
	 		  var xd  =0.0 ;
	 		 var  yd = 0.0;
	 		 for (var i = 0 ;i < locax.length;i++){
	 			xd  += parseFloat(  locax[i])  ;
	 			yd  += parseFloat(  locay[i])  ;
	 		 } 
	 		tar[0 ] = xd/ locax.length ;
	 		tar[1 ] = yd/ locax.length ;
	 		var arrcoordinates = [
		        {
		            sblx: "102000", ywid:"info_fxpg_sh",title:"保电用户",
		            sbarray: [{
		                    sbid: "00119007947173",color : "red",
		                    coordinates:  tar, 
		                    params: [{key: "用户名称", value: theme} ]
		             }],
		        },
		    ];
			myIframe_2D.window.Gis2d_vectorPointByCoordinate(arrcoordinates,6 );
	 	} else{
	 		//获取最高等级，最高电压的站线的设备，注意站线表维护站线到设备
	 		myak.send('/overviewTotal/getmaxdeviceid','GET',djson,false,function(data){
				datat=data; 
			}); 
	 		if( datat.length!= 0){
	 			var dev =  datat[0].deviceid;
	 			arr = [{sblx : "102000",ywid:"info_imgpp_zxjc_fubing",title:"保电任务",
	 				sbarray : [ {
	 					sbid : dev  ,
	 					bubble:{sbCount:"保",color:"red"},
	 				} ]
	 			}]
	 			myIframe_2D.window.Gis2d_vectorImgBuddleBySBID(arr,4);
	 		} 
	 	}
	}
	
	function clearGisPosition(){ // 清除gis定位
			myIframe_2D.window.Gis2d_vectorImgBuddleBySBIDClose(['info_imgpp_zxjc_fubing']);
			myIframe_2D.window.Gis2d_vectorPointClose(['info_fxpg_sh']) ;
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
	return me;
})

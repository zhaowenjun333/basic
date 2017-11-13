define(['akui', 'util', 'exportData', 'akapp', 'myCommon'],function(ak, myak, exak, akapp, myCommon){
	var me = {};
	ak.parse();
	
	// 各tab页
	var tabs = ["baseInfoTab", "organizationTab", "stationOrLineTab", "userTab", 
		"personnelTab", "carTab", "stagnationPointTab", "hotelTab", "hospitalTab"];
	var tabsLength = tabs.length;
	
	// 保电站线tab页（含站线tab页和保电点击tab页，sol:StationOrLine）
	var solTabs1 = ["stationTab", "lineTab"];
	var solTabs1Length = solTabs1.length;
	
	var solTabs2 = ["specialLevelTab", "firstLevelTab", "secondLevelTab", "thirdLevelTab"];
	var solTabs2Length = solTabs2.length;
	
	// 各区域
	var areas = ["baseInfoArea", "functionBtnsArea", "organizationArea", "stationOrLineArea",
		"userArea", "personnelArea", "carArea", "stagnationPointArea", "hotelArea", "hospitalArea", "totalSaveBtnArea"];
	var lengthAreas = areas.length;
	
	// 各功能按钮
	var btns = ["addBtn", "editBtn", "deleteBtn", "copyBtn", "exportBtn", "importBtn", "templateBtn"];
	var lengthBtns = btns.length;
	
	// 各tab页表格
	var baseInfoGrid = ak("baseInfoGrid");
	var organizationGrid = ak("organizationGrid");
	var stationOrLineGrid = ak("stationOrLineGrid");
	var solWinGrid = ak("solWinGrid");		// sol: StationOrLine
	var userGrid = ak("userGrid");
	var userWinGrid = ak("userWinGrid");
	var userLocationWinGrid = ak("userLocationWinGrid");
	var personnelGrid = ak("personnelGrid");
	var carGrid = ak("carGrid");
	var stagnationPointGrid = ak("stagnationPointGrid");
	var stagnationPointLocationWinGrid = ak("stagnationPointLocationWinGrid");
	var hotelGrid = ak("hotelGrid");
	var hotelLocationWinGrid = ak("hotelLocationWinGrid");
	var hospitalGrid = ak("hospitalGrid");
	var hospitalLocationWinGrid = ak("hospitalLocationWinGrid");
	
	me.id = "";
	me.triggerType = "button";
	me.btnType = "add";
	me.status = "1";
	
	me.btnUsable = true;
	
	me.tabID = "baseInfoTab";
	me.solTab1ID = "stationTab";
	
	me.baseInfoTabVisited = false;
	me.organizationTabVisited = false;
	me.stationOrLineTabVisited = false;
	me.userTabVisited = false;
	me.personnelTabVisited = false;
	me.carTabVisited = false;
	me.stagnationPointTabVisited = false;
	me.hotelTabVisited = false;
	me.hospitalTabVisited = false;
	
	// 图片、附件
	me.baseIntroPicture = "";		// 基础信息
	me.baseIntroPictureName = "";
	me.baseEnsureMap = "";
	me.baseEnsureMapName = "";
	me.baseEnsureNotice = "";
	me.baseEnsureNoticeName = "";
	
	me.orgDutyPersonPhoto = "";		// 组织机构
	me.orgDutyPersonPhotoName = "";
	me.orgEnsurePlan = "";
	
	me.solEnsurePlan = "";			// 保电站线
	
	me.userEmergencyPlan = "";		// 用户
	me.userJoinLineMap = "";
	
	/**
	 * 初始化方法
	 */
	me.render = function(){
		/********************负责单位选择树********************/
		ak("baseDepartment").on("beforeload",function(e){		// 负责单位树
			var type = e.node.nodeType;
			var id = e.node.id;
			if (typeof (type) == 'undefined') {
				 e.params.nodeType = 'root';
			} else {
				e.params.id = id;
				e.params.nodeType = type;
			}
		});
		ak("baseDepartment").load(akapp.commonService+"/treeUtil/getYwdwTree.do");
		ak("baseSource").load(akapp.appname+"/powerSupplyGuarantee/getTaskSource");		// 任务来源
		
		// 获取URL参数
		var filter = window.location.search;
		if(filter.indexOf("params=") != -1){
			filter = filter.split("?params=")[1];
			filter = decodeURIComponent(filter.split("&")[0]);
			me.params = JSON.parse(filter);
			
			me.id = me.params.id;
			me.theme = me.params.theme;
			me.triggerType = me.params.triggerType;
			me.btnType = me.params.triggerObj;
			me.status = me.params.status;
			
			me.baseIntroPicture = me.params.introPicture;
			me.baseIntroPictureName = me.params.introPictureNames;
			me.baseEnsureMap = me.params.ensureMap;
			me.baseEnsureMapName = me.params.ensureMapNames;
			me.baseEnsureNotice = me.params.ensureNotice;
			me.baseEnsureNoticeName = me.params.ensureNoticeNames;
		}
		
		if(me.triggerType == "grid" || me.btnType == "examine" || me.status == "3" || me.status == "4"){
			me.btnUsable = false;	// 各功能按钮不可用
			
			// 基础信息
			ak("baseTheme").readOnly = true;
			ak("baseSource").readOnly = true;
			ak("baseDepartment").readOnly = true;
			ak("baseKeepPowerContent").readOnly = true;
			ak("baseLeaderRequest").readOnly = true;
			baseInfoGrid.allowCellEdit = false;		// 取消所有行编辑
		}
		
		// 区域隐藏
		for(var i=0; i<lengthAreas; i++){
			$("#"+areas[i]).css("display", "none");
		}
		
		// 功能按钮全部显示
		for(var i=0; i<lengthBtns; i++){
			$("#"+btns[i]).css("display", "block");
		}
		
		if(me.triggerType == "button"){		// 如果是点击工具栏按钮展示弹窗
			$("#baseInfoArea").css("display","block");
			$("#baseEnsureLevelArea").css("height","362px");
			$("#baseInfoGrid").css("height","267px");
			$("#totalSaveBtnArea").css("display", "block");
			drawGridCell(baseInfoGrid, "baseEnsureLevel");	// 保电阶段表格中获取保电级别下拉框
			
			if(me.btnType == "edit" || me.btnType == "examine"){
				initBaseInfoData();		// 加载基础信息数据
				
//				if(me.btnType == "examine" || me.status == "3" || me.status == "4"){
				if(me.btnType == "examine" || me.status == "4"){
					$("#baseInfoArea").css("display","block");
					$("#baseEnsureLevelArea").css("height","400px");
					$("#baseInfoGrid").css("height","305px");
					$("#totalSaveBtnArea").css("display", "none");
				}
			}
		}else if(me.triggerType == "grid"){		// 如果是点击首页保电任务表涉及站线、组织信息单元格展示弹窗
			if(me.btnType == "涉及站线"){	// 涉及站线单元格
				me.tabID = "stationOrLineTab";
				
				// 展示相应区域
				$("#stationOrLineArea").css({"height":"94%" ,"display":"block"});
				
				ak("solVoltageLevel").load(akapp.appname+"/powerSupplyGuarantee/getVoltageLevelList");	// 获取电压等级
				
				/********************所属单位选择树********************/
				ak("solDepartment").on("beforeload",function(e){
					var type = e.node.nodeType;
					var id = e.node.id;
					if (typeof (type) == 'undefined') {
						e.params.nodeType = 'root';
					} else {
						e.params.id = id;
						e.params.nodeType = type;
					}
				});
				ak("solDepartment").load(akapp.commonService+"/treeUtil/getYwdwTree.do");
				
				initSolDeviceAllTree();	// 加载站线树
				getSolGrid();			// 获取表格数据
			}else if(me.btnType == "组织信息"){		// 组织信息单元格
				me.tabID = "organizationTab";
				
				$("#functionBtnsArea").css("display", "block");
				$("#organizationArea").css({"height":"86%", "display":"block"});
				
				$("#importBtn").css("display", "none");
				$("#templateBtn").css("display", "none");
				
				getOrgTree();	// 获取组织机构树
				getOrgGrid();	// 加载表格数据
				
				organizationGrid.allowCellEdit = false;		// 取消所有行编辑
			}
		}
		
		// 将相应tab页样式设置为active
		for(var i=0; i<tabsLength; i++){
			$("#"+tabs[i]).removeClass("active");
			$("#"+tabs[i]+"Star").removeClass("tabStarActive");
		}
		$("#"+me.tabID).addClass("active");
		$("#"+me.tabID+"Star").addClass("tabStarActive");
		
		tabsBindClick();
		functionBtnsClick();
		totalSaveBtnClick();
		
		// 基础信息
		basePhotosBindClick();
		
		// 保电站线
		solTabsBindClick();
		solBtnsBindClick();		// 保电站线查询、编辑按钮绑定点击事件
		solPhotosBindClick();
		
		// 用户
		userPhotosBindClick();
		userWinSaveClick();
	}
	
	
	
	/******************************************公共******************************************/
	/**
	 * tab页绑定点击事件
	 */
	function tabsBindClick(){
		for(var i=0; i<tabsLength; i++){
			$("#"+tabs[i]).on("click", function(e){
				var tabName = "";
				if(e.target.innerText.indexOf("*") != -1){
					tabName = e.target.innerText.split("* ")[1];
				}else{
					tabName = e.target.innerText;
				}
				tabChanged(tabName);
			});
		}
	}
	
	/**
	 * tab页切换事件
	 */
	function tabChanged(tabName){
		switch (tabName) {
			case "基础信息":
				if(me.tabID != "baseInfoTab"){
					me.tabID = "baseInfoTab";
					
					// 区域
					for(var i=0; i<lengthAreas; i++){
						$("#"+areas[i]).css("display", "none");
					}
					
//					if(me.triggerType == "button" && me.btnType != "examine" && me.status != "3" && me.status != "4"){
					if(me.triggerType == "button" && me.btnType != "examine" && me.status != "4"){
						$("#baseInfoArea").css("display","block");
						$("#baseEnsureLevelArea").css("height","362px");
						$("#baseInfoGrid").css("height","267px");
						$("#totalSaveBtnArea").css("display","block");
					}else{
						$("#baseInfoArea").css("display","block");
						$("#baseEnsureLevelArea").css("height","400px");
						$("#baseInfoGrid").css("height","305px");
						$("#totalSaveBtnArea").css("display", "none");
					}
					
					if(me.btnType != "add"){
						initBaseInfoData();		// 加载基础信息数据
					}
					
					me.baseInfoTabVisited = true;
				}
				break;
			case "组织机构":
				if(me.tabID != "organizationTab"){
					me.tabID = "organizationTab";
					
					// 区域
					for(var i=0; i<lengthAreas; i++){
						$("#"+areas[i]).css("display", "none");
					}
					$("#functionBtnsArea").css("display", "block");
					if(me.triggerType == "button" && me.btnType != "examine" && me.status != "3" && me.status != "4"){
						$("#organizationArea").css({"height":"80%","display":"block"});
						$("#totalSaveBtnArea").css("display","block");
					}else{
						$("#organizationArea").css({"height":"86%","display":"block"});
						$("#totalSaveBtnArea").css("display", "none");
					}
					
					
					
					// 功能按钮
					for(var i=0; i<lengthBtns; i++){
						$("#"+btns[i]).css("display", "block");
					}
					$("#importBtn").css("display", "none");
					$("#templateBtn").css("display", "none");
					
//					if(me.triggerType == "button" && me.btnType != "examine" && me.status != "3" && me.status != "4"){
//						
//					}else{
//						
//					}
					
					getOrgTree();	// 获取组织机构树
					getOrgGrid();	// 加载表格数据
					organizationGrid.allowCellEdit = false;		// 取消所有行编辑
					me.organizationTabVisited = true;
				}
				break;
			case "保电站线":
				if(me.tabID != "stationOrLineTab"){
					me.tabID = "stationOrLineTab";
					
					// 区域
					for(var i=0; i<lengthAreas; i++){
						$("#"+areas[i]).css("display", "none");
					}
					
					if(me.triggerType == "button" && me.btnType != "examine" && me.status != "3" && me.status != "4"){
						$("#stationOrLineArea").css({"height":"88%","display":"block"});
						$("#totalSaveBtnArea").css("display","block");
					}else{
						$("#stationOrLineArea").css({"height":"93%","display":"block"});
						$("#totalSaveBtnArea").css("display", "none");
					}
					
					ak("solVoltageLevel").load(akapp.appname+"/powerSupplyGuarantee/getVoltageLevelList");	// 获取电压等级
					
					/********************所属单位选择树********************/
					ak("solDepartment").on("beforeload",function(e){
						var type = e.node.nodeType;
						var id = e.node.id;
						if (typeof (type) == 'undefined') {
							e.params.nodeType = 'root';
						} else {
							e.params.id = id;
							e.params.nodeType = type;
						}
					});
					ak("solDepartment").load(akapp.commonService+"/treeUtil/getYwdwTree.do");
					
					ak("solVoltageLevel").setValue("");
					ak("solDepartment").setValue("");
					ak("solRightName").setValue("");
					
					initSolDeviceAllTree();
					getSolGrid();	// 加载表格数据
					me.stationOrLineTabVisited = true;
				}
				break;
			case "用户":
				if(me.tabID != "userTab"){
					me.tabID = "userTab";
					
					// 区域
					for(var i=0; i<lengthAreas; i++){
						$("#"+areas[i]).css("display", "none");
					}
					$("#functionBtnsArea").css("display", "block");
					$("#userArea").css("display", "block");
					$("#totalSaveBtnArea").css("display", "none");
					
					// 功能按钮
					for(var i=0; i<lengthBtns; i++){
						$("#"+btns[i]).css("display", "block");
					}
					$("#copyBtn").css("display", "none");
					
					getUserGrid();	// 获取用户表格数据
					me.userTabVisited = true;
				}
				break;
			case "人员":
				if(me.tabID != "personnelTab"){
					me.tabID = "personnelTab";
					
					// 区域
					for(var i=0; i<lengthAreas; i++){
						$("#"+areas[i]).css("display", "none");
					}
					$("#functionBtnsArea").css("display", "block");
					$("#personnelArea").css("display", "block");
					$("#totalSaveBtnArea").css("display", "none");
					
					// 功能按钮
					for(var i=0; i<lengthBtns; i++){
						$("#"+btns[i]).css("display", "block");
					}
					$("#addBtn").css("display", "none");
					$("#editBtn").css("display", "none");
					$("#copyBtn").css("display", "none");
					
					getPersonGrid();	// 获取人员表格数据
					me.personnelTabVisited = true;
				}
				break;
			case "车辆":
				if(me.tabID != "carTab"){
					me.tabID = "carTab";
					
					// 区域
					for(var i=0; i<lengthAreas; i++){
						$("#"+areas[i]).css("display", "none");
					}
					$("#functionBtnsArea").css("display", "block");
					$("#carArea").css("display", "block");
					$("#totalSaveBtnArea").css("display", "none");
					
					// 功能按钮
					for(var i=0; i<lengthBtns; i++){
						$("#"+btns[i]).css("display", "block");
					}
					$("#addBtn").css("display", "none");
					$("#editBtn").css("display", "none");
					$("#copyBtn").css("display", "none");
					
					getCarGrid();
					me.carTabVisited = true;
				}
				break;
			case "驻点":
				if(me.tabID != "stagnationPointTab"){
					me.tabID = "stagnationPointTab";
					
					// 区域
					for(var i=0; i<lengthAreas; i++){
						$("#"+areas[i]).css("display", "none");
					}
					$("#functionBtnsArea").css("display", "block");
					$("#stagnationPointArea").css("display", "block");
					$("#totalSaveBtnArea").css("display", "none");
					
					// 功能按钮
					for(var i=0; i<lengthBtns; i++){
						$("#"+btns[i]).css("display", "block");
					}
					$("#addBtn").css("display", "none");
					$("#editBtn").css("display", "none");
					$("#copyBtn").css("display", "none");
					
					getPointGrid();
					me.stagnationPointTabVisited = true;
				}
				break;
			case "宾馆":
				if(me.tabID != "hotelTab"){
					me.tabID = "hotelTab";
					
					// 区域
					for(var i=0; i<lengthAreas; i++){
						$("#"+areas[i]).css("display", "none");
					}
					$("#functionBtnsArea").css("display", "block");
					$("#hotelArea").css("display", "block");
					$("#totalSaveBtnArea").css("display", "none");
					
					// 功能按钮
					for(var i=0; i<lengthBtns; i++){
						$("#"+btns[i]).css("display", "block");
					}
					$("#addBtn").css("display", "none");
					$("#editBtn").css("display", "none");
					$("#copyBtn").css("display", "none");
					
					getHotelGrid();
					me.hotelTabVisited = true;
				}
				break;
			case "医院":
				if(me.tabID != "hospitalTab"){
					me.tabID = "hospitalTab";
					
					// 区域
					for(var i=0; i<lengthAreas; i++){
						$("#"+areas[i]).css("display", "none");
					}
					$("#functionBtnsArea").css("display", "block");
					$("#hospitalArea").css("display", "block");
					$("#totalSaveBtnArea").css("display", "none");
					
					// 功能按钮
					for(var i=0; i<lengthBtns; i++){
						$("#"+btns[i]).css("display", "block");
					}
					$("#addBtn").css("display", "none");
					$("#editBtn").css("display", "none");
					$("#copyBtn").css("display", "none");
					
					getHospitalGrid();
					me.hospitalTabVisited = true;
				}
				break;
			default:
				break;
		}
		
		for(var i=0; i<tabsLength; i++){
			$("#"+tabs[i]).removeClass("active");
			$("#"+tabs[i]+"Star").removeClass("tabStarActive");
		}
		$("#"+me.tabID).addClass("active");
		$("#"+me.tabID+"Star").addClass("tabStarActive");
		
		// 清空模糊查询框
		ak("keyWords").setValue("");
	}
	
	/**
	 * 功能按钮点击事件
	 */
	function functionBtnsClick(){
		if(me.btnUsable){
			// 新增
			$("#addBtn").on("click", function(){
				if(me.id == ""){
					showTips("info", "<b>注意</b> <br/>请先保存基础信息内容！");
					return;
				}else{
					if(me.tabID == "organizationTab"){	// 组织机构新增
						organizationGrid.allowCellEdit = true;		// 取消所有行编辑
						
						var orgTree = ak("orgTree");
			            var node = orgTree.getSelectedNode();
			            if(node != undefined){
			            	var level = orgTree.getLevel (node);
			            	if(level != 2){
			            		var newRow = { "belongTask":me.theme, "belongTaskId": me.id, "superOrgID":node.id, "superOrg": node.text };
								organizationGrid.addRow(newRow, organizationGrid.data.length);
			            	}else{
			            		showTips("info", "<b>注意</b> <br/>保电组织暂只支持3个层级！");
				            	return;
			            	}
			            	
			            }else{
			            	showTips("info", "<b>注意</b> <br/>请从左侧组织机构树上选择一个节点！");
			            	return;
			            }
						
					}else{	// 用户新增
						me.winBtnType = "add";
						ak("userWindow").setTitle("用户-新增");
						ak("userName").setValue();
						ak("userDutyPerson").setValue();
						me.userEmergencyPlan = "";
						me.userJoinLineMap = "";
						ak("userEmergencyPlan").setValue();
						ak("userEmergencyPlan").text = "";
						me.userLeveCount = 0;
						userWinGrid.clearRows();
						ak("userWindow").show();
						
						// 修改弹窗样式
						$("#userWindow").children().children(".ak-panel-viewport").children(".ak-panel-body").css({"background":"#eceef6", "padding":"10px"});
						$("#userWindow").children().children(".ak-panel-header").css({"color":"black", "background":"#e8f5f5"});
						
						var p_path = "/powerSupplyGuarantee/getOrganizationTree";	// 获取组织机构树
						var params = {"taskId":me.id};
				    	myak.send(p_path,'GET',params,false,function(treeList){
				    		ak("userOrg").loadList(treeList, "id", "pid");
				    		ak("userOrg").on("nodeclick", function(e){
								ak("userDutyPerson").setValue(e.node.person);
							});
						});
					}
				}
			});
			
			// 编辑
			$("#editBtn").on("click", function(){
				if(me.tabID == "organizationTab"){	// 组织机构编辑
					organizationGrid.allowCellEdit = true;		// 允许所有行编辑
					showTips("info", "<b>注意</b> <br/>编辑功能已放开，请点击表格中的单元格直接进行编辑！");
				}else{	// 用户编辑
					me.winBtnType = "edit";
					var rows = userGrid.getSelecteds();
					var length = rows.length;
					if(length > 0){
						var p_path = "/powerSupplyGuarantee/getOrganizationTree";	// 获取组织机构树
						var params = {"taskId":me.id};
				    	myak.send(p_path,'GET',params,false,function(treeList){
				    		ak("userOrg").loadList(treeList, "id", "pid");
				    		ak("userOrg").on("nodeclick", function(e){
								ak("userDutyPerson").setValue(e.node.person);
							});
						});
				    	
						if(length == 1){
							ak("userName").setValue(rows[0].userName);
							ak("userOrg").setValue(rows[0].belongOrgID);	// 需先加载组织机构树，在赋值
							ak("userDutyPerson").setValue(rows[0].dutyPersonName);
							$("#userWinUserNameArea").css("display", "block");
							$("#userWinEmergencyPlanArea").css("display", "block");
							$("#userWinOrgArea").css({"width":"50%", "text-align":"right"});
							$("#userOrg").css("width", "220px");
							$("#userWinDutyPersonArea").css("width", "50%");
							$("#userDutyPerson").css("width", "220px");
							$("#userJoinLineMap").css("display", "block");
							
							if(rows[0].emergencyPlan != "" && rows[0].emergencyPlan != null){
								ak("userEmergencyPlan").setValue("有");
								ak("userEmergencyPlan").text = rows[0].emergencyPlan;
							}else{
								ak("userEmergencyPlan").setValue("无");
								ak("userEmergencyPlan").text = "";
							}
							me.userEmergencyPlan = rows[0].emergencyPlan;
							me.userJoinLineMap = rows[0].joinLineMap;
							
							userWinGrid.load(akapp.appname+"/powerSupplyGuarantee/getElectricityEnsurePhaseResult");	// 获取表格数据
							var params = {"taskId":rows[0].id};
							userWinGrid.load(params, function(){
								me.userLeveCount = userWinGrid.data.length;
							});
							drawGridCell(userWinGrid, "userEnsureLevel");		// 保电阶段表格中获取保电级别下拉框
						}else{
							userWinGrid.clearRows();
							me.userLeveCount = 0;
							
							ak("userName").setValue();
							ak("userOrg").setValue("");	// 需先加载组织机构树，在赋值
							ak("userDutyPerson").setValue();
							ak("userEmergencyPlan").setValue("无");
							ak("userEmergencyPlan").text = "";
							
							$("#userWinUserNameArea").css("display", "none");
							$("#userWinEmergencyPlanArea").css("display", "none");
							$("#userWinOrgArea").css({"width":"100%", "text-align":"left"});
							$("#userOrg").css("width", "520px");
							$("#userWinDutyPersonArea").css("width", "100%");
							$("#userDutyPerson").css("width", "520px");
							$("#userJoinLineMap").css("display", "none");
						}
						
						ak("userWindow").setTitle("用户-编辑");
						ak("userWindow").show();
						// 修改弹窗样式
						$("#userWindow").children().children(".ak-panel-viewport").children(".ak-panel-body").css({"background":"#eceef6", "padding":"10px"});
						$("#userWindow").children().children(".ak-panel-header").css({"color":"black", "background":"#e8f5f5"});
					}else{
						showTips("info", "<b>注意</b> <br/>请至少勾选一条数据！");
						return;
					}
				}
			});
			
			// 删除
			$("#deleteBtn").on("click", function(){
				var grid;
				var selecteds;
				var length = 0;
				var url = "";
				
				var subIds = "";		// 组织机构-获取的下级组织机构ID
				var tableName = "";		// 各表的FQL表名
				
				switch (me.tabID) {
					case "organizationTab":		// 组织机构
						var rootNode = ak("orgTree").getRootNode();		// 获取根节点
						var rootID = rootNode.children[0].id;
						
						grid = organizationGrid;
						selecteds = grid.getSelecteds();
						length = selecteds.length;
						for(var i=0; i<length; i++){
							if(rootID == selecteds[i].id){
								showTips("info", "<b>注意</b> <br/>所选记录中包含总部节点，不可删除！请重新选择。");
								return;
							}
						}
						
						url = "/powerSupplyGuarantee/deleteOrganization";
						tableName = "sgcim30.assets.management.ElectricityEnsureOrg";
						break;
					case "userTab":		// 用户
						grid = userGrid;
						selecteds = grid.getSelecteds();
						length = selecteds.length;
						url = "/powerSupplyGuarantee/deleteUser";
						tableName = "sgcim30.assets.management.ElectricityEnsureUser";
						break;
					case "personnelTab":	// 人员
						grid = personnelGrid;
						selecteds = grid.getSelecteds();
						length = selecteds.length;
						url = "/powerSupplyGuarantee/deletePerson";
						tableName = "sgcim30.assets.management.ElectricityEnsurePerson";
						break;
					case "carTab":	// 车辆
						grid = carGrid;
						selecteds = grid.getSelecteds();
						length = selecteds.length;
						url = "/powerSupplyGuarantee/deleteCar";
						tableName = "sgcim30.assets.management.ElectricityEnsureVehicle";
						break;
					case "stagnationPointTab":	// 驻点
						grid = stagnationPointGrid;
						selecteds = grid.getSelecteds();
						length = selecteds.length;
						url = "/powerSupplyGuarantee/deletePoint";
						tableName = "sgcim30.assets.management.ElectricityEnsureStation";
						break;
					case "hotelTab":	// 宾馆
						grid = hotelGrid;
						selecteds = grid.getSelecteds();
						length = selecteds.length;
						url = "/powerSupplyGuarantee/deleteHotel";
						tableName = "sgcim30.assets.management.ElectricityEnsureHotel";
						break;
					case "hospitalTab":		// 医院
						grid = hospitalGrid;
						selecteds = grid.getSelecteds();
						length = selecteds.length;
						url = "/powerSupplyGuarantee/deleteHospital";
						tableName = "sgcim30.assets.management.ElectricityEnsureHospital";
						break;
					default:
						break;
				}
				
				if(length > 0){
					var message =  "确定删除所勾选的"+length+"条记录？";
					if(me.tabID == "organizationTab"){
						// 获取是否有下级组织机构
						var superIds = "";
	                	for(var i=0; i<length; i++){
	                		superIds += selecteds[i].id + ",";
	                	}
	                	superIds = superIds.substring(0, superIds.length-1);
	                	
	                	myak.send("/powerSupplyGuarantee/getSubOrganizationResult", 'GET', {"superIds":superIds}, false, function(result){
	                		if(result.length > 0){
	                			message = "所选记录有下级组织机构，删除所选记录后，其下级组织机构也将会被删除。<br/>确定删除所勾选的"+length+"条记录？";
	                			for(var i=0; i<result.length; i++){
	                				if(i == 0){
	                					subIds = result[0].subId;
	                				}else{
	                					subIds += (","+result[i].subId);
	                				}
	                			}
	                		}
		        		});
					}
					
					ak.confirm(message, "确定？",
				            function(action){
				                if (action == "ok") {
				                	var ids = "";
				                	for(var i=0; i<length; i++){
				                		ids += selecteds[i].id + ",";
				                	}
				                	ids = ids.substring(0,ids.length-1);
				                	if(subIds != ""){
				                		ids += ("," + subIds);
				                	}
				                	
				                	var concurrent = whetherConcurrent(selecteds, "", tableName, 2);
				                	if(concurrent == "false"){
				                		var params = {"ids":ids};
					                	myak.send(url,'GET',params,false,function(result){
					                		if(result.successful){
					                			if(me.tabID == "organizationTab"){
						                			getOrgTree();
													organizationGrid.reload();
													organizationGrid.allowCellEdit = false;		// 取消所有行编辑
						                		}
						                		
						                		grid.reload();
							        			showTips("success", "<b>确认</b> <br/>删除成功！");
					                		}else{
					                			showTips("danger", "<b>注意</b> <br/>删除失败！");
					                		}
						        		});
				                	}else{
				                		showTips("danger", "<b>注意</b> <br/>勾选的数据中存在并发操作，请稍候刷新数据再重复当前操作！");
				                	}
				                } else {
				                    return;
				                }
				            }
				        );
				}else{
					showTips("info", "<b>注意</b> <br/>请至少勾选一条数据！");
					return;
				}
			});
			
			// 复制
			$("#copyBtn").on("click", function(){
				var selecteds = organizationGrid.getSelecteds();
				var length = selecteds.length;
				if(length == 1){
					if(selecteds[0].superOrgID != null){
						var newRow = { "belongTask":selecteds[0].belongTask, "belongTaskId":selecteds[0].belongTaskId, "orgName":selecteds[0].orgName, 
								"superOrgID":selecteds[0].superOrgID, "superOrg":selecteds[0].superOrg, "orgIntro":selecteds[0].orgIntro,
								"dutyPerson":selecteds[0].dutyPerson, "phoneNumber":selecteds[0].phoneNumber, "contactNumber":selecteds[0].contactNumber,
								"dutyPersonPhoto":selecteds[0].dutyPersonPhoto, "dutyPersonPhotoNames":selecteds[0].dutyPersonPhotoNames,
								"ensurePlan":selecteds[0].ensurePlan, "ensureArea":selecteds[0].ensureArea};
							organizationGrid.addRow(newRow, organizationGrid.data.length);
					}else{
						showTips("info", "<b>注意</b> <br/>该记录为总部，不能被复制，请重新勾选！");
						return;
					}
				}else{
					showTips("info", "<b>注意</b> <br/>勾选且只能勾选一条记录，请重新勾选！");
					return;
				}
			});
			
			// 导入
			$("#importBtn").on("click", function(){
				if(me.id == ""){
					showTips("info", "<b>注意</b> <br/>请先保存基础信息内容！");
					return;
				}else{
					switch (me.tabID) {
						case "userTab":
							myCommon.importExcelData(me.id, "maintainPower_user", function(returnInfo){
								afterImport(userGrid, returnInfo);
							});
							break;
						case "personnelTab":
							myCommon.importExcelData(me.id, "maintainPower_personnel", function(returnInfo){
								afterImport(personnelGrid, returnInfo);
							});
							break;
						case "carTab":
							myCommon.importExcelData(me.id, "maintainPower_car", function(returnInfo){
								afterImport(carGrid, returnInfo);
							});
							break;
						case "stagnationPointTab":
							myCommon.importExcelData(me.id, "maintainPower_stagnationPoint", function(returnInfo){
								afterImport(stagnationPointGrid, returnInfo);
							});
							break;
						case "hotelTab":
							myCommon.importExcelData(me.id, "maintainPower_hotel", function(returnInfo){
								afterImport(hotelGrid, returnInfo);
							});
							break;
						case "hospitalTab":
							myCommon.importExcelData(me.id, "maintainPower_hospital", function(returnInfo){
								afterImport(hospitalGrid, returnInfo);
							});
							break;
						default:
							break;
					}
				}
			});
			
			// 点击导入后反馈方法
			function afterImport(grid, info){
				if(info == "导入成功！"){
					showTips("success", "<b>确认</b> <br/>" + info);
					grid.reload();
				}else{
					showTips("danger", "<b>注意</b> <br/>" + info);
				}
			}
		}
		
		// 导出
		$("#exportBtn").on("click", function(){
			var params = JSON.stringify({"taskId":me.id});
			switch (me.tabID) {
				case "organizationTab":
					exak.exportPagesDataToExcel(organizationGrid, "organization", params, "保电任务-组织机构记录表");
					break;
				case "userTab":
					exak.exportPagesDataToExcel(userGrid, "user", params, "保电任务-用户记录表");
					break;
				case "personnelTab":
					exak.exportPagesDataToExcel(personnelGrid, "personnel", params, "保电任务-人员记录表");
					break;
				case "carTab":
					exak.exportPagesDataToExcel(carGrid, "car", params, "保电任务-车辆记录表");
					break;
				case "stagnationPointTab":
					exak.exportPagesDataToExcel(stagnationPointGrid, "stagnationPoint", params, "保电任务-驻点记录表");
					break;
				case "hotelTab":
					exak.exportPagesDataToExcel(hotelGrid, "hotel", params, "保电任务-宾馆记录表");
					break;
				case "hospitalTab":
					exak.exportPagesDataToExcel(hospitalGrid, "hospital", params, "保电任务-医院记录表");
					break;
				default:
					break;
			}
//			alert(me.tabID);
		});
		
		// 模板
		$("#templateBtn").on("click", function(){
			switch (me.tabID) {
				case "userTab":
					myCommon.downLoadExcelTemplate("maintainPower_user");
					break;
				case "personnelTab":
					myCommon.downLoadExcelTemplate("maintainPower_personnel");
					break;
				case "carTab":
					myCommon.downLoadExcelTemplate("maintainPower_car");
					break;
				case "stagnationPointTab":
					myCommon.downLoadExcelTemplate("maintainPower_stagnationPoint");
					break;
				case "hotelTab":
					myCommon.downLoadExcelTemplate("maintainPower_hotel");
					break;
				case "hospitalTab":
					myCommon.downLoadExcelTemplate("maintainPower_hospital");
					break;
				default:
					break;
			}
		});
		
		// 模糊查询放大镜图标点击事件
		$("#searchImg").on("click", function(){
			var keyWords = ak("keyWords").value;
			var params ={"taskId":me.id, "keyWords":keyWords};
			switch (me.tabID) {
				case "organizationTab": organizationGrid.load(params);
					break;
				case "userTab": userGrid.load(params);
					break;
				case "personnelTab": personnelGrid.load(params);
					break;
				case "carTab": carGrid.load(params);
					break;
				case "stagnationPointTab": stagnationPointGrid.load(params);
					break;
				case "hotelTab": hotelGrid.load(params);
					break;
				case "hospitalTab": hospitalGrid.load(params);
					break;
				default:
					break;
			}
		});
	}
	
	 /**
	  * 总体保存按钮点击事件
	  */
	 function totalSaveBtnClick(){
		 $("#totalSaveBtn").on("click", function(){
			 var params;
			 if(me.tabID == "baseInfoTab"){		// 基础信息
				 var theme = ak("baseTheme").value;
				 var source = ak("baseSource").text;
				 var sourceId = ak("baseSource").value;
				 var department = ak("baseDepartment").text;
				 var departmentId = ak("baseDepartment").value;
				 var content = ak("baseKeepPowerContent").value;
				 var leaderRequest = ak("baseLeaderRequest").value;
				 
				 if(theme == ""){
					 showTips("info", "<b>注意</b> <br/>请填写保电主题！");
					 return;
				 }
				 if(!ak("baseTheme")._IsValid){
					 showTips("info", "<b>注意</b> <br/>保电主题字数太长，请重新填写！（1~80个字符）");
					 return;
				 }
				 
				 if(departmentId == ""){
			 		 showTips("info", "<b>注意</b> <br/>请填写负责单位！");
					 return;
				 }else if(sourceId == ""){
					 showTips("info", "<b>注意</b> <br/>请填写任务来源！");
					 return;
				 }
				 
				 if(!ak("baseKeepPowerContent")._IsValid){
					 showTips("info", "<b>注意</b> <br/>保电内容字数太长，请重新填写！（0~2000个字符）");
					 return;
				 }
				 if(!ak("baseLeaderRequest")._IsValid){
					 showTips("info", "<b>注意</b> <br/>其他要求字数太长，请重新填写！（0~2000个字符）");
					 return;
				 }
				 
				 
				 var success = gridSaveCheckout(baseInfoGrid);
				 if(success){
					 var gridData = [];
					 var data = baseInfoGrid.data;
					 var gridLength = data.length;
					 var startTime = data[0].startTime;
					 var endTime = data[gridLength-1].endTime;
					 var ensureLevel = "4";
					 var concurrent = "false";	// 是否并发  false:未并发, true:并发
					 
					 for(var i=0; i<gridLength; i++){
						 var gridEnsureLevel;
							switch (data[i].ensureLevel) {
							case "特级":
								gridEnsureLevel = "1";
								break;
							case "一级":
								gridEnsureLevel = "2";
								break;
							case "二级":
								gridEnsureLevel = "3";
								break;
							case "三级":
								gridEnsureLevel = "4";
								break;
							default: gridEnsureLevel = data[i].ensureLevel;
								break;
							}
						if(gridEnsureLevel < ensureLevel){	// 获取最高保电级别
							ensureLevel = gridEnsureLevel;
						}
						gridData.push(data[i]);
					 }
					 
					 params = {
				    	"id":me.id, "theme":theme, "taskSource":sourceId, "dutyUnitID":departmentId,
				    	"dutyUnitName":department, "ensureDetails":content,
				    	"leaderRequest":leaderRequest, "startTime":startTime, "endTime":endTime, "ensureLevel":ensureLevel,
				    	"gridData":gridData, "gridLength":gridLength, "flag":"edit",
				    	"introPicture":me.baseIntroPicture, "introPictureNames":me.baseIntroPictureName,
				    	"ensureMap":me.baseEnsureMap, "ensureMapNames":me.baseEnsureMapName,
				    	"ensureNotice":me.baseEnsureNotice, "ensureNoticeNames":me.baseEnsureNoticeName
					 };
					 
					 var url = "";
					 if(me.btnType == "add"){
						 url = "/powerSupplyGuarantee/insertPowerSupplyGuarantee";
					 }else if(me.btnType == "edit"){
						 url = "/powerSupplyGuarantee/updatePowerSupplyGuarantee";
						 
						 // 检测是否存在并发操作
						 if(me.params != undefined){
							 var updateDateTime = me.params.updateDateTime;
							 var id = me.id;
							 var tableName = "sgcim30.assets.management.ElectricityEnsure";
							 concurrent = whetherConcurrent(updateDateTime, id, tableName, 1);
						 }
					 }
					 
					 if(concurrent == "false"){
						 myak.send(url,'POST',ak.encode(params),false,function(result){
							 if(me.btnType == "add"){
								 if(result.id != undefined){
									 me.id = result.id;
									 me.theme = theme;
									 me.btnType = "edit";	// 如果是新增，保存成功后由新增变为编辑状态
									 
									 showTips("success", "<b>确认</b> <br/>保存成功！");
								 }else{
									 showTips("danger", "<b>注意</b> <br/>保存失败！");
								 }
							 }else if(me.btnType == "edit"){
								 if(result.successful){
									 showTips("success", "<b>确认</b> <br/>编辑成功！");
								 }else{
									 showTips("danger", "<b>注意</b> <br/>编辑失败！");
								 }
							 }
							 
//							 var params = {"taskId":me.id};
//							 baseInfoGrid.load(params);
						 });
					 }
				 }else{
					 return;
				 }
			 }else{
				 if(me.id == ""){
					 showTips("info", "<b>注意</b> <br/>请先保存基础信息内容！");
					 return;
				 }else{
					 if(me.tabID == "organizationTab"){
						var success = orgGridSaveCheckout();
						if(success){
							var data = organizationGrid.data;
							var gridLength = data.length;
							
							// 校验同一保电任务的同一保电组织层级上是否有重名的组织
							var nameUsed = false;
							var nameUsedArr = [];
							for(var i=0; i<gridLength; i++){
								var usedCount = 0;
								for(var j=0; j<gridLength; j++){
									if(data[i].orgName == data[j].orgName){
										usedCount++;
									}
									
									if(usedCount == 2){		// 等于 1 是自己，等于 2 是重名
										nameUsedArr.push(data[i]);
										break;
									}
								}
								
								if(usedCount == 2){
									break;
								}
							}
							
							if(nameUsedArr.length > 0){
								if(nameUsedArr[0].superOrg == null){
									showTips("danger", "<b>注意</b> <br/>"+nameUsedArr[0].belongTask+"保电任务下已有"
											+nameUsedArr[0].orgName+"，请为新增的组织机构重新命名！");
								}else{
									showTips("danger", "<b>注意</b> <br/>"+nameUsedArr[0].belongTask+"保电任务的"+nameUsedArr[0].superOrg
											+"组织下已有"+nameUsedArr[0].orgName+"，请为新增的组织机构重新命名！");
								}
								return;
							}else{
								var tableName = "sgcim30.assets.management.ElectricityEnsureOrg";
								var concurrent = whetherConcurrent(data, "", tableName, 2);
								if(concurrent == "false"){
									var gridData = [];
									for(var i=0; i<gridLength; i++){
										data[i].recordDate = ak.formatDate(data[i].recordDate,'yyyy-MM-dd HH:mm:ss');
										gridData.push(data[i]);
									}
									
									var url = "/powerSupplyGuarantee/insertOrUpdateOrganization";
									var params = {
								    	"taskId":me.id, "gridData":gridData, "gridLength":gridLength
								    };
									myak.send(url,'POST',ak.encode(params),false,function(result){
										if(result.successful){
											showTips("success", "<b>注意</b> <br/>保存成功！");
										}else{
											showTips("danger", "<b>注意</b> <br/>保存失败！");
										}
										getOrgTree();
										organizationGrid.reload();
										organizationGrid.allowCellEdit = false;		// 取消所有行编辑
									});
								}else{
									showTips("danger", "<b>注意</b> <br/>第"+concurrent+"条数据存在并发操作，请稍候刷新数据再重复当前操作！");
								}
							}
						}
					 }else if(me.tabID == "stationOrLineTab"){
						 var data =stationOrLineGrid.data;
						 var length = data.length;
						 var gridData = [];
						 for(var i=0; i<length; i++){
							 if(data[i].id == ""){
								 gridData.push(data[i]);
							 }
						 }
						 
						 var url = "/powerSupplyGuarantee/insertStationOrLine";
							var params = {
						    	"taskId":me.id, "gridData":gridData, "gridLength":gridData.length
						    };
							
							myak.send(url,'GET',params,false,function(result){
								if(result.successful){
									ak("stationDeviceTree").uncheckAllNodes();
									ak("lineDeviceTree").uncheckAllNodes();
									showTips("success", "<b>确认</b> <br/>保存成功！");
								 }else{
									showTips("danger", "<b>注意</b> <br/>保存失败！");
								 }
								 stationOrLineGrid.reload();
							});
					 }
				 }
			 }
		 });
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
	  * 分阶段添加保电级别
	  */
	 function addEnsureLevels(grid, endTimeId, count){
		 if(count == 0){
			var newRow = { name: "New Row" };
			grid.addRow(newRow, grid.data.length);
			grid.beginEditCell(newRow, "startTime");
		}else{
			if(grid.data[count-1].startTime == undefined || grid.data[count-1].startTime == ""){
				showTips("info", "<b>注意</b> <br/>请先填写第"+count+"阶段的开始时间！");
				return false;
			}else if(grid.data[count-1].endTime == undefined || grid.data[count-1].endTime == ""){
				showTips("info", "<b>注意</b> <br/>请先填写第"+count+"阶段的结束时间！");
				return false;
			}else if(grid.data[count-1].startTime > grid.data[count-1].endTime){
				showTips("info", "<b>注意</b> <br/>第"+count+"阶段的开始时间应小于结束时间，请重新填写！");
				return false;
			}else if(grid.data[count-1].ensureLevel == undefined){
				showTips("info", "<b>注意</b> <br/>请先填写第"+count+"阶段的保电级别！");
				return false;
			}
			
			var newRow = { name: "New Row", startTime:grid.data[count-1].endTime };
			grid.addRow(newRow, grid.data.length);
			grid.beginEditCell(newRow, "endTime");
			
			// 结束时间改变事件（如果下有下一行，修改下一行的开始时间为该行修改后的结束时间）
			ak(endTimeId).on("valuechanged", function(e){
				var ownerRowID = ak(endTimeId).ownerRowID;
				var dataLength = grid.data.length;
				if(ownerRowID != dataLength){
					var row = grid.getRow(ownerRowID);	// 获取下一行
					grid.updateRow(row, { name: "New Row", startTime:e.value });	// 修改该下一行的开始时间为相应的值
				}
			});
		}
		 
		 return true;
	 }
	 
	 /**
	  * 渲染分阶段添加保电级别的表格
	  */
	 function drawGridCell(grid, ensureLevelId){
		 var ensureLevel = [{ id: "1", text: "特级" }, { id: "2", text: "一级"}, { id: "3", text: "二级"}, { id: "4", text: "三级"}];
			
			// 获取保电级别
			grid.on('cellclick',function(e) {
//				if(baseInfoGrid.allowCellEdit){
				if(grid.allowCellEdit){
					if(e.field == "ensureLevel"){
						ak(ensureLevelId).load(ensureLevel);
					}
				}
			});
			
			grid.on('drawcell',function(e) {
				if(e.field == "startTime" && e.value != undefined){
					e.cellHtml = e.value.substring(0, 16);
				}else if(e.field == "endTime" && e.value != undefined){
					e.cellHtml = e.value.substring(0, 16);
				}else if(e.field == "ensureLevel"){
					for (var i = 0; i < 4; i++) {
		                var g = ensureLevel[i];
		                if (g.id == e.value) e.cellHtml = g.text;
		            }
				}
			});
	 }
	 
	 /**
	  * 基础信息、用户分阶段添加保电级别表格保存校验方法
	  */
	 function gridSaveCheckout(grid){
		 var data = grid.data;
		 var length = data.length;
		 var text = "";
		 if(grid.id == "baseInfoGrid"){
			 text = "阶段";
		 }else{
			 text = "级别";
		 }
		 
		 if(length > 0){
			 // 判空
			 for(var i=0; i<length; i++){
				 if(data[i].startTime == undefined || data[i].startTime == ""){	// 开始时间
					 showTips("info", "<b>注意</b> <br/>请填写保电"+text+"第"+(i+1)+"阶段的开始时间！");
					 return false;
				 }else if(data[i].endTime == undefined || data[i].endTime == ""){	// 结束时间
					 showTips("info", "<b>注意</b> <br/>请填写保电"+text+"第"+(i+1)+"阶段的结束时间！");
					 return false;
				 }else if(data[i].startTime.substr(0, 16) >= data[i].endTime.substr(0, 16)){
					 showTips("info", "<b>注意</b> <br/>第"+(i+1)+"阶段的结束时间应大于开始时间，请重新填写！");
					 return false;
				 }else if(data[i].ensureLevel == undefined){	// 保电级别
					 showTips("info", "<b>注意</b> <br/>请填写保电"+text+"第"+(i+1)+"阶段的保电级别！");
					 return false;
				 }
			 }
			 
			 // 判断第i条记录的结束时间与第i+1条记录的开始时间是否相等
			 if(length > 1){
				 for(var i=1; i<length; i++){
					 if(data[i].startTime.substring(0, 16) != data[i-1].endTime.substring(0, 16)){
						 showTips("info", "<b>注意</b> <br/>第"+(i+1)+"阶段的开始时间与第"+i+"阶段的结束时间不相等，请重新填写！");
						 return false;
					 }
				 }
			 }
			 
		 }else{
			 showTips("info", "<b>注意</b> <br/>请填写保电"+text+"信息！");
			 return false;
		 }
		 return true;
	 }
	
	 /**
	  * 图片、附件弹出框
	  */
	 function attachmentClick(tableName, id, fileFlag, fieldsName){
		 if(id == "" || id == null){
			var p_path = "/powerSupplyGuarantee/getId";
    		myak.send(p_path,'GET',null,false,function(data){
    			id = data.id;
    		});
		 }
		 
		 if(me.btnType == "examine" || me.status == "4"){
			 fileFlag = true;
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
//           		 var urlId = data[0].url.substr(0, data[0].url.length-1).split("/user/mcsas/electricityEnsure/")[1];
           			 var urlId = data[0].url.split("/")[4];
           			 var filesName = "";
           			 for(var i=0; i<data.length; i++){
           				 if(i == 0){
           					 filesName = data[0].name;
           				 }else{
           					 filesName += (","+data[i].name);
           				 }
           			 }
           			 switch (fieldsName) {
						case "base_introPicture":
							me.baseIntroPicture = urlId;
							me.baseIntroPictureName = filesName;
							break;
						case "base_ensureMap":
							me.baseEnsureMap = urlId;
							me.baseEnsureMapName = filesName;
							break;
						case "base_ensureNotice":
							me.baseEnsureNotice = urlId;
							me.baseEnsureNoticeName = filesName;
							break;
						case "org_dutyPersonPhoto":
							me.orgDutyPersonPhoto = urlId;
							me.orgDutyPersonPhotoName = filesName;
							
							var row = organizationGrid.getSelected();
			           		row.dutyPersonPhoto = urlId;
			           		row.dutyPersonPhotoNames = filesName;
							break;
						case "org_ensurePlan":
							me.orgEnsurePlan = urlId;
							
							var row = organizationGrid.getSelected();
			           		row.ensurePlan = urlId;
							break;
						case "sol_ensurePlan":
							me.solEnsurePlan = urlId;
							
							ak("solAttachment").setValue("有");
							ak("solAttachment").text = urlId;
							break;
						case "user_emergencyPlan":
							me.userEmergencyPlan = urlId;
							
							ak("userEmergencyPlan").setValue("有");
							ak("userEmergencyPlan").text = urlId;
							break;
						case "user_joinLineMap":
							me.userJoinLineMap = urlId;
						default:
							break;
					}
           		}
           	 }
	    });

        win.showAtPos("330px", "120px");
	 }
	 
	 /**
	  * 判断是否并发方法
	  * "false":未并发, "true":并发
	  */
	 function whetherConcurrent(data, id, tableName, type){
		 var concurrent = "false";
		 var params = {"id":id, "tableName":tableName};
		 var url = "/powerSupplyGuarantee/getUpdateTime";
		 
		 if(type == 1){
			 myak.send(url, 'GET', params, false, function(result){
				 if(result.length > 0){
					 var time = new Date(result[0].updateDateTime).getTime();
					 if(time != data){
						 concurrent = "true";
						 showTips("danger", "<b>注意</b> <br/>该数据存在并发操作，请稍候刷新数据再重复当前操作！");
					 }
				 }else{
					 concurrent = "true";
					 showTips("danger", "<b>注意</b> <br/>该数据存在并发操作，请稍候刷新数据再重复当前操作！");
				 }
			 });
		 }else if(type == 2){
			 var length = data.length;
			 for(var i=0; i<length; i++){
				 var id = data[i].id;
				 if(id != undefined){
					 var updateDateTime = data[i].updateDateTime.getTime();
					 var params = {"id":id, "tableName":tableName};
					 myak.send(url, 'GET', params, false, function(result){
						 if(result.length > 0){
							 var time = new Date(result[0].updateDateTime).getTime();	// 转换为毫秒
							 if(time != updateDateTime){
								 concurrent = (i+1);
							 }
						 }else{
							 concurrent = (i+1);
						 }
					 });
					
					 if(concurrent != "false"){
						 break;
					 }
				 }
			 }
		 }
		 
		 
		 return concurrent;
	 }
	 
	 
	 
	/******************************************基础信息******************************************/
	/**
	 * 保电阶段新增、删除图标
	 * 简介图片、供电图、保电通知绑定点击事件
	 * 二级页面动态图、静态图Tab页点击事件
	 * 三级页面保存按钮
	 */
	function basePhotosBindClick(){
		me.baseLeveCount = 0;	// 基础信息tab页中保电阶段表格中的记录数
		
		// 保电阶段-新增
		$("#baseAddImg").on("click", function(){
			if(me.triggerType == "button" && me.btnType != "examine" && me.status != "3" && me.status != "4"){	// 不是查看、已闭环、已归档时可点击
				var success = addEnsureLevels(baseInfoGrid, "baseEndTime", me.baseLeveCount);
				drawGridCell(baseInfoGrid, "baseEnsureLevel");		// 保电阶段表格中获取保电级别下拉框
				
				if(success){
					me.baseLeveCount++;
				}
			}
		});
		
		// 保电阶段-删除
		$("#baseDeleteImg").on("click", function(){
			if(me.triggerType == "button" && me.btnType != "examine" && me.status != "3" && me.status != "4"){	// 不是查看、已闭环、已归档时可点击
				var rows = baseInfoGrid.getSelecteds();
		        if (rows.length > 0) {
		        	ak.confirm("确定要删除该阶段记录吗？", "删除确认",
			            function(action){
			                if (action == "ok") {
			                	baseInfoGrid.removeRows(rows, true);
			                	me.baseLeveCount -= rows.length;
			                	if(baseInfoGrid.data.length == 0){
			                		baseInfoGrid.load({"taskId":""});
			                	}
			                } else {
			                    return;
			                }
			            }
			        );
		        }else{
		        	showTips("info", "<b>注意</b> <br/>请从右侧表格中至少勾选一条记录！");
		        }
			}
		});
		
		// 简介图片
		$("#introPhoto").on("click", function(){
			if(typeof(me.params) == "object"){
				if(me.status == "4" && me.params.introPicture == null){
					showTips("info", "<b>注意</b> <br/>该保电任务未维护简介图片！");
				}else if(me.status == "4" && me.params.introPicture != null){
					attachmentClick("electricityEnsure", me.params.introPicture, true, "base_introPicture");
				}else{
					attachmentClick("electricityEnsure", me.params.introPicture, false, "base_introPicture");
				}
			}else{
				attachmentClick("electricityEnsure", null, false, "base_introPicture");
			}
		});
		
		// 供电图
		$("#powerSupplyPhoto").on("click", function(){
			if(typeof(me.params) == "object"){
				if(me.status == "4" && me.params.ensureMap == null){
					showTips("info", "<b>注意</b> <br/>该保电任务未维护供电图！");
				}else if(me.status == "4" && me.params.ensureMap != null){
					attachmentClick("electricityEnsure", me.params.ensureMap, true, "base_ensureMap");
				}else{
					attachmentClick("electricityEnsure", me.params.ensureMap, false, "base_ensureMap");
				}
			}else{
				attachmentClick("electricityEnsure", null, false, "base_ensureMap");
			}
		});
		
		// 相关附件
		$("#attachmentPhoto").on("click", function(){
			if(typeof(me.params) == "object"){
				if(me.status == "4" && me.params.ensureNotice == null){
					showTips("info", "<b>注意</b> <br/>该保电任务未维护相关附件！");
				}else if(me.status == "4" && me.params.ensureNotice != null){
					attachmentClick("electricityEnsure", me.params.ensureNotice, true, "base_ensureNotice");
				}else{
					attachmentClick("electricityEnsure", me.params.ensureNotice, false, "base_ensureNotice");
				}
			}else{
				attachmentClick("electricityEnsure", null, false, "base_ensureNotice");
			}
		});
	}
	
	/**
	 * 加载基础信息数据
	 */
	function initBaseInfoData(){
		var p_path = "/powerSupplyGuarantee/getNoteInfo";
		var params = {"id":me.id};
    	myak.send(p_path,'GET',params,false,function(data){
    		if(data.length == 1){
    			ak("baseTheme").setValue(data[0].theme);						// 保电主题
    			ak("baseSource").setValue(data[0].taskSource);					// 任务来源
    			ak("baseDepartment").setValue(data[0].dutyUnitID);				// 负责单位
    			ak("baseDepartment").setText(data[0].dutyUnitName);
    			ak("baseKeepPowerContent").setValue(data[0].ensureDetails);		// 保电内容
    			ak("baseLeaderRequest").setValue(data[0].leaderRequest);		// 领导要求
    		}else{
    			showTips("danger", "<b>注意</b> <br/>获取该记录的基础信息失败！");
    		}
		});
		
		baseInfoGrid.load(akapp.appname+"/powerSupplyGuarantee/getElectricityEnsurePhaseResult");
		var params = {"taskId":me.id};
		baseInfoGrid.load(params, function(){
			me.baseLeveCount = baseInfoGrid.data.length;
		});
	}
	
	
	
	/******************************************组织机构******************************************/
	/**
	 * 获取组织机构树
	 */
	function getOrgTree(){
		var p_path = "/powerSupplyGuarantee/getOrganizationTree";
		var params = {"taskId":me.id};
    	myak.send(p_path,'GET',params,false,function(treeList){
    		ak("orgTree").loadList(treeList, "id", "pid");
		});
	}
	
	/**
	 * 获取组织机构表格
	 */
	function getOrgGrid(){
		if(!me.organizationTabVisited){
			organizationGrid.on('drawcell',function(e) {
			 	 var divImg = "";
				 switch (e.field) {
				 	case "orgName" :
				 		if(ak("orgNameValue")._IsValid != undefined && ak("orgNameValue")._IsValid == false){
				 			showTips("info", "<b>注意</b> <br/>组织名称字数太长，请重新填写！（1~100个字符）");
				 		}
				 		break;
				 	case "orgIntro" :
				 		if(ak("orgIntroValue")._IsValid != undefined && ak("orgIntroValue")._IsValid == false){
				 			showTips("info", "<b>注意</b> <br/>组织简介字数太长，请重新填写！（0~500个字符）");
				 		}
				 		break;
				 	case "dutyPerson" :
				 		if(ak("dutyPersonValue")._IsValid != undefined && ak("dutyPersonValue")._IsValid == false){
				 			showTips("info", "<b>注意</b> <br/>负责人字数太长，请重新填写！（0~100个字符）");
				 		}
				 		break;
				 	case "phoneNumber" :
				 		if(ak("phoneNumberValue")._IsValid != undefined && ak("phoneNumberValue")._IsValid == false){
				 			showTips("info", "<b>注意</b> <br/>手机号码字数太长，请重新填写！（0~100个字符）");
				 		}
//				 		else if(ak("phoneNumberValue").value != undefined && ak("phoneNumberValue").value != ""){
//				 			if(!(/^1[34578]\d{9}$/.test(ak("phoneNumberValue").value))){
//				 				showTips("info", "<b>注意</b> <br/>手机号码格式不正确，请重新填写！");
//				 			}
//				 		}
				 		break;
				 	case "contactNumber" :
				 		if(ak("contactNumberValue")._IsValid != undefined && ak("contactNumberValue")._IsValid == false){
				 			showTips("info", "<b>注意</b> <br/>联系电话字数太长，请重新填写！（0~100个字符）");
				 		}
//				 		else if(ak("contactNumberValue").value != undefined && ak("contactNumberValue").value != ""){
//				 			if(!(/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,8}$/.test(ak("contactNumberValue").value))){
//				 				showTips("info", "<b>注意</b> <br/>联系电话格式不正确，请重新填写！");
//				 			}
//				 		}
				 		break;
				 	case "dutyPersonPhoto":
						 if(e.row.dutyPersonPhoto != null && e.row.dutyPersonPhoto != ""){
							 divImg = "<img src='res/img/picture_green.png' style='width:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 case "ensurePlan":
						 if(e.row.ensurePlan != null && e.row.ensurePlan != ""){
							 divImg = "<img src='res/img/attachment_green.png' style='width:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 case "ensureArea":
						 if(e.row.ensureArea != null && e.row.ensureArea != ""){
							 divImg = "<img src='res/img/site_green.png' style='height:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 default:
						 break;
					}
			 	});
			
			organizationGrid.on("cellclick", function(e){
				var rows = organizationGrid.getSelecteds();
		 		var l = rows.length;
				switch(e.field){
				 	case "dutyPersonPhoto":
				 		if(l > 1){
				 			showTips("info", "<b>注意</b> <br/>请勾选一条记录上传负责人照片！");
				 		}else{
				 			if(organizationGrid.allowCellEdit){
				 				attachmentClick("electricityEnsureOrg", e.row.dutyPersonPhoto, false, "org_dutyPersonPhoto");
				 			}else{
				 				if(rows[0].dutyPersonPhoto != null){
				 					attachmentClick("electricityEnsureOrg", e.row.dutyPersonPhoto, true, "org_dutyPersonPhoto");
					 			}else if(me.triggerType != "grid" && me.btnType != "examine" && me.status != "3" && me.status != "4"){
					 				showTips("info", "<b>注意</b> <br/>该记录尚未上传负责人照片,请先点击【编辑】按钮上传负责人照片。");
					 			}
				 			}
				 		}
						break;
				 	case "ensurePlan":
				 		if(l > 1){
				 			showTips("info", "<b>注意</b> <br/>请勾选一条记录上传保电方案！");
				 		}else{
				 			if(organizationGrid.allowCellEdit){
				 				attachmentClick("electricityEnsureOrg", e.row.ensurePlan, false, "org_ensurePlan");
				 			}else{
				 				if(rows[0].ensurePlan != null){
				 					attachmentClick("electricityEnsureOrg", e.row.ensurePlan, true, "org_ensurePlan");
				 				}else if(me.triggerType != "grid" && me.btnType != "examine" && me.status != "3" && me.status != "4"){
				 					showTips("info", "<b>注意</b> <br/>该记录尚未上传保电方案,请先点击【编辑】按钮上传保电方案。");
				 				}
				 			}
				 		}
						break;
				 	case "ensureArea":
//						alert("保电区域");
						break;
					default:
						break;
				 }
			 });
		}
		
		organizationGrid.load(akapp.appname+'/powerSupplyGuarantee/getOrganizationResult');
		var params = {"taskId":me.id};
		organizationGrid.load(params);
	}
	
	/**
	 * 组织机构表格保存必填项校验方法
	 */
	function orgGridSaveCheckout(){
		var rootNode = ak("orgTree").getRootNode();
		var rootID = rootNode.children[0].id;
		var data = organizationGrid.data;
		var length = data.length;
		
		for(var i=0; i<length; i++){
			if(data[i].orgName == "" || data[i].orgName == null || data[i].orgName == undefined){	// 组织名称
				 showTips("info", "<b>注意</b> <br/>请填写第"+(i+1)+"行的组织名称！");
				 return false;
			}else{
				if(len(data[i].orgName) > 100){
					showTips("info", "<b>注意</b> <br/>第"+(i+1)+"行的组织名称字数太长，请重新填写！（1~100个字符）");
					return false;
				}
			}
			
			if(data[i].id != rootID){	// 不是根节点时
				if(data[i].superOrgID == undefined || data[i].superOrgID == ""){	// 上级组织
					 showTips("info", "<b>注意</b> <br/>请填写第"+(i+1)+"行的上级组织！");
					 return false;
				}
			}
			
			if(data[i].orgIntro != "" && data[i].orgIntro != null &&　data[i].orgIntro != undefined){	// 组织简介
				if(len(data[i].orgIntro) > 500){
					showTips("info", "<b>注意</b> <br/>第"+(i+1)+"行的组织简介字数太长，请重新填写！（0~500个字符）");
					return false;
				}
			}
			
			if(data[i].dutyPerson != "" && data[i].dutyPerson != null &&　data[i].dutyPerson != undefined){	// 负责人
				if(len(data[i].dutyPerson) > 100){
					showTips("info", "<b>注意</b> <br/>第"+(i+1)+"行的负责人字数太长，请重新填写！（0~500个字符）");
					return false;
				}
			}
			
			if(data[i].phoneNumber != "" && data[i].phoneNumber != null &&　data[i].phoneNumber != undefined){	// 手机号码
				if(len(data[i].phoneNumber) > 11){
					showTips("info", "<b>注意</b> <br/>第"+(i+1)+"行的手机号码字数太长，请重新填写！（0~100个字符）");
					return false;
				}else{
		 			if(!(/^1[34578]\d{9}$/.test(data[i].phoneNumber))){		// 手机号码校验
		 				showTips("info", "<b>注意</b> <br/>第"+(i+1)+"行的手机号码格式不正确，请重新填写！");
		 				return false;
		 			}
		 		}
			}
			
			if(data[i].contactNumber != "" && data[i].contactNumber != null &&　data[i].contactNumber != undefined){	// 联系电话
				if(len(data[i].contactNumber) > 13){
					showTips("info", "<b>注意</b> <br/>第"+(i+1)+"行的联系电话字数太长，请重新填写！（0~100个字符）");
					return false;
				}else{
		 			if(!(/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,8}$/.test(data[i].contactNumber))){	// 联系电话校验
		 				showTips("info", "<b>注意</b> <br/>第"+(i+1)+"行的联系电话格式不正确，请重新填写！");
		 				return false;
		 			}
		 		}
			}
		}
		
		return true;
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
	
	
	
	/******************************************保电站线******************************************/
	/**
	 * 获取保电站线表格
	 */
	function getSolGrid(){
		if(!me.stationOrLineTabVisited){
			stationOrLineGrid.on('drawcell',function(e) {
			 	 var divImg = "";
				 switch (e.field) {
					 case "ensurePlan":
						 if(e.value != null){
							 divImg = "<img src='res/img/attachment_green.png' style='width:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 case "voltageLevel":
						 // 获取电压等级
						 var voltageLevel = e.row.voltageLevel;
						 var voltageLevelLists = ak("solVoltageLevel").data;
						 var l = voltageLevelLists.length;
						 for(var i=0; i<l; i++){
							 if(voltageLevel == voltageLevelLists[i].id){
								 e.cellHtml = voltageLevelLists[i].text;
								 break;
							 }
						 }
						 break;
					 default:
						 break;
					}
			 	});
			
			stationOrLineGrid.on("cellclick", function(e){
				switch(e.field){
				 	case "ensurePlan":
				 		var rows = stationOrLineGrid.getSelecteds();
				 		var l = rows.length;
				 		if(l > 1){
				 			showTips("info", "<b>注意</b> <br/>请勾选一条记录上传保电方案！");
				 		}else{
				 			if(rows.ensurePlan != null){
				 				attachmentClick("stationLineEnsure", e.row.ensurePlan, true, "sol_ensurePlan");
				 			}else if(me.triggerType != "grid" && me.btnType != "examine" && me.status != "3" && me.status != "4"){
				 				showTips("info", "<b>注意</b> <br/>该记录尚未上传保电方案,请先点击【编辑】按钮上传保电方案。");
				 			}
				 		}
						break;
					default:
						break;
				 }
			 });
		}
		
		var voltageLevel = ak("solVoltageLevel").value;
		var departmentID= ak("solDepartment").value;
		var solRightName = ak("solRightName").value;
		
		stationOrLineGrid.load(akapp.appname+'/powerSupplyGuarantee/getStationOrLineResult');
		var params = {"taskId":me.id, "voltageLevel":voltageLevel, "departmentID":departmentID, "solRightName":solRightName};
		stationOrLineGrid.load(params);
	}
	
	/**
	 * 保电站线tab页绑定点击事件
	 */
	function solTabsBindClick(){
		for(var i=0; i<solTabs1Length; i++){
			$("#"+solTabs1[i]).on("click", function(e){
				solTabChanged(e.target.innerText);
			});
		}
	}
	
	/**
	 * 保电站线tab页改变事件
	 */
	function solTabChanged(tabName){
		if(tabName == "站"){
			$("#lineTab").removeClass("active");
			$("#stationTab").addClass("active");
//			$("#solLeftText")[0].innerHTML = "电站名称";
			$("#stationTreeArea").css("display","block");
			$("#lineTreeArea").css("display","none");
		}else{
			$("#stationTab").removeClass("active");
			$("#lineTab").addClass("active");
//			$("#solLeftText")[0].innerHTML = "线路名称";
			$("#stationTreeArea").css("display","none");
			$("#lineTreeArea").css("display","block");
			ak("lineDeviceTree").expandNode(ak("lineDeviceTree").getRootNode());
		}
	}
	
	/**
	 * 保电站线查询、移除、编辑按钮绑定点击事件
	 */
	function solBtnsBindClick(){
		// 左侧查询
		$("#solLeftSearchBtn").on("click", function(){
			alert("leftSearch");
		});
		
		// 右侧查询
		$("#solRightSearchBtn").on("click", function(){
			ak("stationDeviceTree").uncheckAllNodes();
			ak("lineDeviceTree").uncheckAllNodes();
			getSolGrid();	// 加载表格数据
		});
		
		if(me.btnUsable){
			// 右测移除
			$("#solMoveBtn").on("click", function(){
				var rows = stationOrLineGrid.getSelecteds();
				var l = rows.length;
				if(l > 0){
					var ids = "";
					var lackIdRows = [];
					for(var i=0; i<l; i++){
						if(rows[i].id != ""){
							ids += (rows[i].id + ",");
						}else{
							lackIdRows.push(rows[i]);
						}
					}
					ids = ids.substr(0, ids.length-1);
					
					if(ids == ""){
						solMoveLackIdRows(rows);	// 无ID记录移除
					}else{
						ak.confirm("勾选的记录中有已存入数据库的记录，确定删除所勾选的"+l+"条记录？", "确定？",
				            function(action){
				                if (action == "ok") {
				                	var tableName = "sgcim30.assets.management.StationLineEnsure";
				                	var concurrent = whetherConcurrent(rows, "", tableName, 2);
				                	if(concurrent == "false"){
				                		solMoveLackIdRows(lackIdRows);
					                	
					                	var p_path = "/powerSupplyGuarantee/deleteStationOrLine";
					                	var params = {"ids":ids};
					                	myak.send(p_path,'GET',params,false,function(result){
					                		if(result.successful){
					                			ak("stationDeviceTree").uncheckAllNodes();
						            			ak("lineDeviceTree").uncheckAllNodes();
						                		stationOrLineGrid.reload();
							        			showTips("success", "<b>确认</b> <br/>删除成功！");
					                		}else{
					                			showTips("danger", "<b>注意</b> <br/>删除失败！");
					                		}
						        		});
				                	}else{
				                		showTips("danger", "<b>注意</b> <br/>勾选的数据中存在并发操作，请稍候刷新数据再重复当前操作！");
				                	}
				                }
						});
					}
				}else{
					showTips("info", "<b>注意</b> <br/>请至少勾选一条记录！");
				}
			});
			
			// 右侧编辑
			$("#solEditBtn").on("click", function(){
				var isSavedAll = true;
				for(var i=0; i<stationOrLineGrid.data.length; i++){
					if(stationOrLineGrid.data[i].id == ""){
						isSavedAll = false;
						break;
					}
				}
				
				if(isSavedAll){
					var rows = stationOrLineGrid.getSelecteds();
					var length = rows.length;
					if(length == 0){
						showTips("info", "<b>注意</b> <br/>请至少勾选一条记录！");
					}else{
						var p_path = "/powerSupplyGuarantee/getOrganizationTree";	// 获取组织机构树
						var params = {"taskId":me.id};
				    	myak.send(p_path,'GET',params,false,function(treeList){
				    		ak("solOrg").loadList(treeList, "id", "pid");
				    		ak("solOrg").on("nodeclick", function(e){
								ak("solDutyPerson").setValue(e.node.person);
							});
						});
				    	
						if(length == 1){
							solWinGrid.load(akapp.appname+"/powerSupplyGuarantee/getElectricityEnsurePhaseResult");		// 获取表格数据
							var params = {"taskId":rows[0].id};
							solWinGrid.load(params, function(){
								me.solLeveCount = solWinGrid.data.length;
							});
							
							drawGridCell(solWinGrid, "solEnsureLevel");		// 保电阶段表格中获取保电级别下拉框
							
							ak("solOrg").setValue(rows[0].orgBelongID);
							ak("solDutyPerson").setValue(rows[0].dutyPerson);
							
							$("#solAttachmentArea").css("display","block");
							if(rows[0].ensurePlan != null){
								ak("solAttachment").setValue("有");
							}else{
								ak("solAttachment").setValue();
							}
						}else{	// 多条编辑
							solWinGrid.clearRows();
							me.solLeveCount = 0;
							
							ak("solOrg").setValue("");
							ak("solDutyPerson").setValue();
							
							$("#solAttachmentArea").css("display","none");
						}
						
						ak("stationOrLineWindow").show();
						// 修改弹窗样式
						$("#stationOrLineWindow").children().children(".ak-panel-header").css({"color":"black", "background":"#e8f5f5"});
					}
				}else{
					showTips("info", "<b>注意</b> <br/>表格中有未保存的数据，请先进行保存！");
				}
			});
			
			// 弹窗中的保存按钮
			$("#solWinSaveBtn").on("click", function(){
				var rows = stationOrLineGrid.getSelecteds();
				var tableName = "sgcim30.assets.management.StationLineEnsure";
				var concurrent = whetherConcurrent(rows, "", tableName, 2);
				
				if(concurrent == "false"){
					var success = gridSaveCheckout(solWinGrid);
					if(success){
						var l = rows.length;
                		var solIds = "";
    					for(var i=0; i<l; i++){
    						if(i == 0){
    							solIds = rows[i].id;
    						}else{
    							solIds += ("," + rows[i].id);
    						}
    					}
    					
    					var orgId = ak("solOrg").value;
    					var orgName = ak("solOrg").text;
    					var dutyPerson = ak("solDutyPerson").value;
    					var ensurePlan = ak("solAttachment").text;	// 此处ak("solAttachment").text存id，ak("solAttachment").value存汉字“有”

    					if(orgId == null || orgId == ""){
    						showTips("info", "<b>注意</b> <br/>请选择所属组织！");
    						return;
    					}
    					if(!ak("solDutyPerson")._IsValid){
    						showTips("info", "<b>注意</b> <br/>负责人名字太长，请重新填写！（0~100个字符）");
    						return;
    					}
    					
    					var gridData = [];
    					var data = solWinGrid.data;
    					var length = data.length;
    					
    					var startTime = data[0].startTime;
    					var endTime = data[length-1].endTime;
    					var ensureLevel = "4";
    					
    					for(var i=0; i<length; i++){
    						var gridEnsureLevel;
    						switch (data[i].ensureLevel) {
    						case "特级":
    							gridEnsureLevel = "1";
    							break;
    						case "一级":
    							gridEnsureLevel = "2";
    							break;
    						case "二级":
    							gridEnsureLevel = "3";
    							break;
    						case "三级":
    							gridEnsureLevel = "4";
    							break;
    						default: gridEnsureLevel = data[i].ensureLevel;
    							break;
    						}
    						if(gridEnsureLevel < ensureLevel){	// 获取最高保电级别
    							ensureLevel = gridEnsureLevel;
    						}
    						gridData.push(data[i]);
    					 }
    					
    					params = {
    				    	"ids":solIds, "orgBelongID":orgId, "orgBelongName":orgName, "dutyPerson":dutyPerson,
    				    	"ensurePlan":ensurePlan, "startTime":startTime, "endTime":endTime, "ensureLevel":ensureLevel,
    				    	"gridData":gridData, "gridLength":length
    					};
    					
    					myak.send("/powerSupplyGuarantee/updateStationOrLine",'GET',params,false,function(result){
    						stationOrLineGrid.reload();
    						ak("stationOrLineWindow").hide();
    					});
					}
				}else{
					if(rows.length == 1){
						showTips("danger", "<b>注意</b> <br/>保电站线表中勾选的数据存在并发操作，请稍候刷新数据再重复当前操作！");
					}else{
						showTips("danger", "<b>注意</b> <br/>保电站线表中勾选的第"+concurrent+"条数据存在并发操作，请稍候刷新数据再重复当前操作！");
					}
				}
			});
		}
	}
	
	/**
	 * 右侧移除无ID的新增记录
	 */
	function solMoveLackIdRows(rows){
		var l = rows.length;
		for(var i=0; i<l; i++){
			var selectedNode = rows[i].deviceID;
			var node = null;
			if(node == null){
				node = ak("stationDeviceTree").getNode(selectedNode);
				if(node != null){
					// 修改树上节点勾选状态
					node.checked = false;
					var parentNode = ak("stationDeviceTree").getParentNode(node);
					ak("stationDeviceTree").collapseNode(parentNode);
					ak("stationDeviceTree").expandNode(parentNode);
				}
			}
			if(node == null){
				node = ak("lineDeviceTree").getNode(selectedNode);
				if(node != null){
					// 修改树上节点勾选状态
					node.checked = false;
					var parentNode = ak("lineDeviceTree").getParentNode(node);
					ak("lineDeviceTree").collapseNode(parentNode);
					ak("lineDeviceTree").expandNode(parentNode);
				}
			}
		}
		
		// 将勾选的数据从表格中移除
		stationOrLineGrid.removeRows(rows, true);
	}
	
	/**
	 * 保电站线图片点击绑定事件
	 * sol:StationOrLine
	 */
	function solPhotosBindClick(){
		if(me.btnUsable){
			me.solLeveCount = 0;	// 保电站线tab页中保电级别表格中的记录数
			// 保电级别-新增
			$("#solAddImg").on("click", function(){
				var success = addEnsureLevels(solWinGrid, "solEndTime", me.solLeveCount);
				drawGridCell(solWinGrid, "solEnsureLevel");		// 保电阶段表格中获取保电级别下拉框
				
				if(success){
					me.solLeveCount++;
				}
			});
			
			// 保电级别-删除
			$("#solDeleteImg").on("click", function(){
				var rows = solWinGrid.getSelecteds();
		        if (rows.length > 0) {
		        	ak.confirm("确定要删除该阶段记录吗？", "删除确认",
			            function(action){
			                if (action == "ok") {
			                	solWinGrid.removeRows(rows, true);
			                	me.solLeveCount -= rows.length;
			                	if(solWinGrid.data.length == 0){
			                		solWinGrid.load({"taskId":""});
			                	}
			                } else {
			                    return;
			                }
			            }
			        );
		        }else{
		        	showTips("info", "<b>注意</b> <br/>请从右侧表格中至少勾选一条记录！");
		        }
			});
			
			// 保电方案
			$("#solAttachmentImg").on("click", function(){
				var ensurePlan = stationOrLineGrid.getSelected().ensurePlan;
				attachmentClick("stationLineEnsure", ensurePlan, false, "sol_ensurePlan");
			});
		}
	}
	
	/**
	 * 加载站线设备全树
	 */
	function initSolDeviceAllTree(){
		// 电站设备全树
		ak("stationDeviceTree").on("beforeload",function(e){		// 电站树
			 var type = e.node.nodeType;
			 var id = e.node.id;
			 if (typeof (type) == 'undefined') {
				 e.params.nodeType = 'root';
			 } else {
				 e.params.id = id;
				 e.params.nodeType = type;
			 }
			 e.params.endNodeType="jgdy";
		 });
		
		if(me.triggerType == "grid" || me.btnType == "examine" || me.status == "3" || me.status == "4"){
			ak("stationDeviceTree").showCheckBox = false;
		}
		
		ak("stationDeviceTree").load(akapp.commonService+"/treeUtil/getSbAllTree.do");
		ak("stationDeviceTree").on("nodecheck", function(e){
			var checkedNodes = ak("stationDeviceTree").getCheckedNodes();
			var length = checkedNodes.length;
			for(var i=0; i<length; i++){
				if(checkedNodes[i].nodeType != "jgdy"){
					checkedNodes[i].checked = false;
				}
			}
			
			if(me.triggerType != "grid" && me.btnType != "examine" && me.status != "3" && me.status != "4"){
				loadSolData();
			}
		});
		
		
		// 线路设备全树
		ak("lineDeviceTree").on("beforeload",function(e){		// 电站树
			var type = e.node.nodeType;
			var id = e.node.id;
			if (typeof (type) == 'undefined') {
				e.params.nodeType = 'root';
			} else {
				e.params.id = id;
				e.params.nodeType = type;
			}
		});

		if(me.triggerType == "grid" || me.btnType == "examine" || me.status == "3" || me.status == "4"){
			ak("lineDeviceTree").showCheckBox = false;
		}
		
		ak("lineDeviceTree").load(akapp.commonService+"/treeUtil/getDeptAndXlTree.do");
		ak("lineDeviceTree").on("nodecheck", function(e){
			var checkedNodes = ak("lineDeviceTree").getCheckedNodes();
			var length = checkedNodes.length;
			for(var i=0; i<length; i++){
				if(checkedNodes[i].nodeType != "xl"){
					checkedNodes[i].checked = false;
				}
			}
			
			if(me.triggerType != "grid" && me.btnType != "examine" && me.status != "3" && me.status != "4"){
				loadSolData();
			}
		});
	}
	
	/**
	 * 向表格中添加/删除记录
	 */
	function loadSolData(){
		stationOrLineGrid.reload(function(){
			// 获取选中保电任务相关联的所有保电站线的设备数据
			var data = [];
			var p_path = "/powerSupplyGuarantee/getStationOrLineResult";
	    	var params = {"taskId":me.id, "voltageLevel":"", "departmentID":"", "solRightName":""};
	    	myak.send(p_path,'GET',params,false,function(result){
	    		data = result.data;
			});
			
			// 电站
			var checkedNodes1 = ak("stationDeviceTree").getCheckedNodes();
			var length1 = checkedNodes1.length;
			for(var i=0; i<length1; i++){
				if(checkedNodes1[i].nodeType == "jgdy"){
					var haved = false;
					var l = data.length;
					for(var j=0; j<l; j++){
						var deviceID = "";
						deviceID = data[j].deviceID;
						
						if(checkedNodes1[i].id == deviceID){
							checkedNodes1[i].checked = false;
							var parentNode = ak("stationDeviceTree").getParentNode(checkedNodes1[i]);
							ak("stationDeviceTree").collapseNode(parentNode);
							ak("stationDeviceTree").expandNode(parentNode);
							
							haved = true;
							showTips("info", "<b>注意</b> <br/>右侧表格中已存在该设备，请重新勾选数据！");
							continue;
						}
					}
					
					if(!haved){
						var stationNode = checkedNodes1[i];
						while(stationNode.nodeType != "bdz"){
							stationNode = ak("stationDeviceTree").getParentNode(stationNode);
						}
						var newRow = {"id":"", "voltageLevel":checkedNodes1[i].voltageLevel, "organizationId":checkedNodes1[i].maintenanceUnit,
							"organization":checkedNodes1[i].maintenanceUnitName, "stationLineID":stationNode.id, "stationLineName":stationNode.text,
							"stationLineType":"02", "deviceID":checkedNodes1[i].id, "deviceName":checkedNodes1[i].text
						};
						stationOrLineGrid.addRow(newRow, stationOrLineGrid.data.length);
					}
				}
			}
			
			// 线路
			var checkedNodes2 = ak("lineDeviceTree").getCheckedNodes();
			var length2 = checkedNodes2.length;
			for(var i=0; i<length2; i++){
				if(checkedNodes2[i].nodeType == "xl"){
					var haved = false;
					var l = data.length;
					for(var j=0; j<l; j++){
						var deviceID = "";
						deviceID = data[j].deviceID;
						
						if(checkedNodes2[i].id == deviceID){
							checkedNodes2[i].checked = false;
							var parentNode = ak("lineDeviceTree").getParentNode(checkedNodes2[i]);
							ak("lineDeviceTree").collapseNode(parentNode);
							ak("lineDeviceTree").expandNode(parentNode);
							
							haved = true;
							showTips("info", "<b>注意</b> <br/>右侧表格中已存在该设备，请重新勾选数据！");
							continue;
						}
					}
					
					if(!haved){
						var newRow = {"id":"", "voltageLevel":checkedNodes2[i].voltageLevel, "organizationId":checkedNodes2[i].eomsUnit,
							"organization":checkedNodes2[i].eomsUnitName, "stationLineID":checkedNodes2[i].id, "stationLineName":checkedNodes2[i].text,
							"stationLineType":"01", "deviceID":checkedNodes2[i].id, "deviceName":checkedNodes2[i].text
						};
						stationOrLineGrid.addRow(newRow, stationOrLineGrid.data.length);
					}
				}
			}
		});
	}
	
	
	
	/******************************************用户******************************************/
	/**
	 * 获取用户表格
	 */
	function getUserGrid(){
		if(!me.userTabVisited){
			userGrid.on('drawcell',function(e) {
			 	 var divImg = "";
				 switch (e.field) {
					 case "geographyLocation":
						 if(e.value != null){
							 divImg = "<img src='res/img/site_green.png' style='height:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 case "emergencyPlan":
						 if(e.value != null){
							 divImg = "<img src='res/img/attachment_green.png' style='width:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 case "joinLineMap":
						 if(e.value != null){
							 divImg = "<img src='res/img/picture_green.png' style='width:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 default:
						 break;
					}
				 
				 var belongOrgID = e.row.belongOrgID;
				 var ensureLevel = e.row.ensureLevel;
				 if(belongOrgID == null || ensureLevel == null){
					 e.cellStyle = "color:red";
				 }
			});
			
			userGrid.on("cellclick", function(e){
				var rows = userGrid.getSelecteds();
		 		var l = rows.length;
				switch(e.field){
					case "geographyLocation":
						// 弹出GIS定位窗口
						var url = akapp.appname+"/powerSupplyGuaranteeMap/index.jsp?taskId="+me.id+"&type=user";
						var title = "用户位置信息维护";
						myak.open(url,title,null,function(action){
							userGrid.reload();
						});
						
						break;
					case "emergencyPlan":
						if(l > 1){
							showTips("info", "<b>注意</b> <br/>请勾选一条记录查看应急预案！");
						}else{
							if(rows[0].emergencyPlan != null){
								attachmentClick("electricityEnsureUser", e.row.emergencyPlan, true, "user_emergencyPlan");
							}else{
								showTips("info", "<b>注意</b> <br/>该记录尚未上传应急预案,请先点击【编辑】按钮上传应急预案。");
							}
						}
						break;
				 	case "joinLineMap":
				 		if(l > 1){
				 			showTips("info", "<b>注意</b> <br/>请勾选一条记录查看接线图！");
				 		}else{
				 			if(rows[0].joinLineMap != null){
				 				attachmentClick("electricityEnsureUser", e.row.joinLineMap, true, "user_joinLineMap");
				 			}else{
				 				showTips("info", "<b>注意</b> <br/>该记录尚未上传接线图,请先点击【编辑】按钮上传接线图。");
				 			}
				 		}
						break;
					default:
						break;
				 }
			 });
		}
		
		userGrid.load(akapp.appname+'/powerSupplyGuarantee/getUserResult');
		var params = {"taskId":me.id};
		userGrid.load(params);
	}
	
	/**
	 * 用户图片绑定点击事件
	 */
	function userPhotosBindClick(){
		me.userLeveCount = 0;	// 用户tab页中保电级别表格中的记录数
		// 保电级别-新增
		$("#userAddImg").on("click", function(){
			var success = addEnsureLevels(userWinGrid, "userEndTime", me.userLeveCount);
			drawGridCell(userWinGrid, "userEnsureLevel");	// 保电阶段表格中获取保电级别下拉框
			
			if(success){
				me.userLeveCount++;
			}
		});
		
		// 保电级别-删除
		$("#userDeleteImg").on("click", function(){
			var rows = userWinGrid.getSelecteds();
	        if (rows.length > 0) {
	        	ak.confirm("确定要删除该阶段记录吗？", "删除确认",
			            function(action){
			                if (action == "ok") {
			                	userWinGrid.removeRows(rows, true);
			    	        	me.userLeveCount -= rows.length;
			    	        	if(userWinGrid.data.length == 0){
			    	        		userWinGrid.load({"taskId":""});
			                	}
			                }
	        	});
	        }else{
	        	showTips("info", "<b>注意</b> <br/>请从右侧表格中至少勾选一条记录！");
	        }
		});
		
		// 应急预案
		$("#userEmergencyPlanImg").on("click", function(){
			attachmentClick("electricityEnsureUser", me.userEmergencyPlan, false, "user_emergencyPlan");
		});
		
		// 接线图
		$("#userJoinLineMap").on("click", function(){
			attachmentClick("electricityEnsureUser", me.userJoinLineMap, false, "user_joinLineMap");
		});
		
		// 地理位置
		$("#userGeographyLocation").on("click", function(){
			alert("dlwz");
		});
	}
	
	/**
	 * 用户tab页中新增弹框中保存按钮点击事件
	 */
	function userWinSaveClick(){
		$("#userWinSaveBtn").on("click", function(){
			var rows = userGrid.getSelecteds();
			var tableName = "sgcim30.assets.management.ElectricityEnsureUser";
			var concurrent = whetherConcurrent(rows, "", tableName, 2);
			
			if(concurrent == "false"){
				var userName = ak("userName").getValue();
				var orgId = ak("userOrg").value;
				var orgName = ak("userOrg").text;
				var dutyPerson = ak("userDutyPerson").value;
				
				if(rows.length <=1){
					if(userName == "" || userName == null){
						showTips("info", "<b>注意</b> <br/>请填写用户名称！");
						return;
					}
					if(!ak("userName")._IsValid){
						showTips("info", "<b>注意</b> <br/>用户名称字数太长，请重新填写！（1~100个字符）");
						return;
					}
				}
				if(orgId == "" || orgId == null){
					showTips("info", "<b>注意</b> <br/>请填写所属组织！");
					return;
				}
				if(dutyPerson == "" || dutyPerson == null){
					showTips("info", "<b>注意</b> <br/>请填写负责人！");
					return;
				}
				if(!ak("userDutyPerson")._IsValid){
					showTips("info", "<b>注意</b> <br/>负责人字数太长，请重新填写！（1~100个字符）");
					return;
				}
				
				var success = gridSaveCheckout(userWinGrid);
				if(success){
					var userIds = "";
					var userGeographyLocation = "";
					var data = userWinGrid.data;
					var length = data.length;
					var startTime = data[0].startTime;
					var endTime = data[length-1].endTime;
					var ensureLevel = 4;
					 
					for(var i=0; i<length; i++){
						var gridEnsureLevel;
						switch (data[i].ensureLevel) {
						case "特级":
							gridEnsureLevel = 1;
							break;
						case "一级":
							gridEnsureLevel = 2;
							break;
						case "二级":
							gridEnsureLevel = 3;
							break;
						case "三级":
							gridEnsureLevel = 4;
							break;
						default: gridEnsureLevel = data[i].ensureLevel;
							break;
						}
						if(gridEnsureLevel < ensureLevel){	// 获取最高保电级别
							ensureLevel = gridEnsureLevel;
						}
					}
					
					var url = "";
					if(me.winBtnType == "add"){
						url = "/powerSupplyGuarantee/insertUser";
					}else if(me.winBtnType == "edit"){
						url = "/powerSupplyGuarantee/updateUser";
						for(var i=0; i<rows.length; i++){
							if(i == 0){
								userIds = rows[i].id;
							}else{
								userIds += ("," + rows[i].id);
							}
						}
					}
					
					if(rows.length <= 1){
						params = {
							"ids": userIds, "taskId":me.id, "userName":userName,
					    	"belongOrgID":orgId, "belongOrgName":orgName, "dutyPersonName":dutyPerson,
					    	"startTime":startTime, "endTime":endTime, "ensureLevel":ensureLevel,
					    	"gridData":data, "gridLength":length,
					    	"emergencyPlan":me.userEmergencyPlan, "joinLineMap":me.userJoinLineMap
						 };
					}else if(rows.length > 1){
						params = {
							"ids": userIds, "taskId":me.id,
					    	"belongOrgID":orgId, "belongOrgName":orgName, "dutyPersonName":dutyPerson,
					    	"startTime":startTime, "endTime":endTime, "ensureLevel":ensureLevel,
					    	"gridData":data, "gridLength":length
						 };
					}
					
					myak.send(url,'GET',params,false,function(result){
						ak("userWindow").hide();
						userGrid.reload();
					});
				}
			}else{
				if(rows.length == 1){
					showTips("danger", "<b>注意</b> <br/>用户表中勾选的数据存在并发操作，请稍候刷新数据再重复当前操作！");
				}else{
					showTips("danger", "<b>注意</b> <br/>用户表中勾选的第"+concurrent+"条数据存在并发操作，请稍候刷新数据再重复当前操作！");
				}
			}
		});
	}
	
	
	
	/******************************************人员******************************************/
	/**
	 * 获取人员表格
	 */
	function getPersonGrid(){
		personnelGrid.on('drawcell',function(e) {
			var belongOrgName = e.row.belongOrgName;
			var type = e.row.type;
			var phoneNumber = e.row.phoneNumber;
			if(belongOrgName == null || type == null || phoneNumber == null){
				e.cellStyle = "color:red";
			}
		});
		
		personnelGrid.load(akapp.appname+'/powerSupplyGuarantee/getPersonResult');
		var params = {"taskId":me.id};
		personnelGrid.load(params);
	}

	
	
	/******************************************车辆******************************************/
	/**
	 * 获取车辆表格
	 */
	function getCarGrid(){
		carGrid.on('drawcell',function(e) {
			var type = e.row.type;
			var belongOrgName = e.row.belongOrgName;
			var phoneNumber = e.row.phoneNumber;
			if(type == null || belongOrgName == null || phoneNumber == null){
				e.cellStyle = "color:red";
			}
		});
		
		carGrid.load(akapp.appname+'/powerSupplyGuarantee/getCarResult');
		var params = {"taskId":me.id};
		carGrid.load(params);
	}

	
	
	/******************************************驻点******************************************/
	/**
	 * 获取驻点表格
	 */
	function getPointGrid(){
		if(!me.stagnationPointTabVisited){
			stagnationPointGrid.on('drawcell',function(e) {
			 	 var divImg = "";
				 switch (e.field) {
					 case "location":
						 if(e.value != null){
							 divImg = "<img src='res/img/site_green.png' style='height:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 default:
						 break;
					}
				 
				 	var belongOrgName = e.row.belongOrgName;
				 	var phoneNumber = e.row.phoneNumber;
					if(belongOrgName == null || phoneNumber == null){
						e.cellStyle = "color:red";
					}
			 	});
			
			stagnationPointGrid.on("cellclick", function(e){
				switch(e.field){
				 	case "location":
						// 弹出GIS定位窗口
						var url = akapp.appname+"/powerSupplyGuaranteeMap/index.jsp?taskId="+me.id+"&type=point";
						var title = "驻点位置信息维护";
						myak.open(url,title,null,function(action){
							stagnationPointGrid.reload();
						});
						
						break;
					default:
						break;
				 }
			 });
		}
		
		stagnationPointGrid.load(akapp.appname+'/powerSupplyGuarantee/getPointResult');
		var params = {"taskId":me.id};
		stagnationPointGrid.load(params);
	}

	
	
	/******************************************宾馆******************************************/
	/**
	 * 获取宾馆表格
	 */
	function getHotelGrid(){
		if(!me.hotelTabVisited){
			hotelGrid.on('drawcell',function(e) {
			 	 var divImg = "";
				 switch (e.field) {
					 case "location":
						 if(e.value != null){
							 divImg = "<img src='res/img/site_green.png' style='height:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 default:
						 break;
					}
				 
				 	var phoneNumber = e.row.phoneNumber;
					if(phoneNumber == null){
						e.cellStyle = "color:red";
					}
			 	});
			
			hotelGrid.on("cellclick", function(e){
				switch(e.field){
				 	case "location":
				 		// 弹出GIS定位窗口
						var url = akapp.appname+"/powerSupplyGuaranteeMap/index.jsp?taskId="+me.id+"&type=hotel";
						var title = "宾馆位置信息维护";
						myak.open(url,title,null,function(action){
							hotelGrid.reload();
						});
						break;
					default:
						break;
				 }
			 });
		}
		
		hotelGrid.load(akapp.appname+'/powerSupplyGuarantee/getHotelResult');
		var params = {"taskId":me.id};
		hotelGrid.load(params);
	}

	
	
	/******************************************医院******************************************/
	/**
	 * 获取医院表格
	 */
	function getHospitalGrid(){
		if(!me.hospitalTabVisited){
			hospitalGrid.on('drawcell',function(e) {
			 	 var divImg = "";
				 switch (e.field) {
					 case "location":
						 if(e.value != null){
							 divImg = "<img src='res/img/site_green.png' style='height:18px; cursor:pointer;' />";
							 e.cellHtml = divImg;
						 }
						 break;
					 default:
						 break;
					}
				 
				 	var phoneNumber = e.row.phoneNumber;
					if(phoneNumber == null){
						e.cellStyle = "color:red";
					}
			 	});
			
			hospitalGrid.on("cellclick", function(e){
				switch(e.field){
				 	case "location":
				 		// 弹出GIS定位窗口
						var url = akapp.appname+"/powerSupplyGuaranteeMap/index.jsp?taskId="+me.id+"&type=hospital";
						var title = "医院位置信息维护";
						myak.open(url,title,null,function(action){
							hospitalGrid.reload();
						});
						break;
					default:
						break;
				 }
			 });
		}
		
		hospitalGrid.load(akapp.appname+'/powerSupplyGuarantee/getHospitalResult');
		var params = {"taskId":me.id};
		hospitalGrid.load(params);
	}
	
	return me;
})

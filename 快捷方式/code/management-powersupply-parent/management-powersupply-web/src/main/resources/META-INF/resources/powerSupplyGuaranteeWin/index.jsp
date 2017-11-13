<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
<title>保电任务弹窗</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="res/css/page.css" rel="stylesheet" type="text/css" />

<ak:framework />
</head>
<body style="padding: 10px; background: #eceef6;">
	<!------------------------------------ Tab区域 ------------------------------------>
	<div style="height: 32px; font-size: 16px;">
		<div class="tab active" id="baseInfoTab">
			<span id="baseInfoTabStar" class="tabStar">*</span>
			<span>基础信息</span>
		</div>
		<div class="tab" id="organizationTab">
			<span id="organizationTabStar" class="tabStar">*</span>
			<span>组织机构</span>
		</div>
		<div class="tab" id="stationOrLineTab">
			<span id="stationOrLineTabStar" class="tabStar">*</span>
			<span>保电站线</span>
		</div>
		<div class="tab" id="userTab">
			<span>用户</span>
		</div>
		<div class="tab" id="personnelTab">
			<span>人员</span>
		</div>
		<div class="tab" id="carTab">
			<span>车辆</span>
		</div>
		<div class="tab" id="stagnationPointTab">
			<span>驻点</span>
		</div>
		<div class="tab" id="hotelTab">
			<span>宾馆</span>
		</div>
		<div class="tab" id="hospitalTab">
			<span>医院</span>
		</div>
	</div>
	
	<!------------------------------------ 功能按钮区域 ------------------------------------>
	<!-- 功能按钮区 -->
	<div id="functionBtnsArea" class="ak-toolbar" style="width:100%; height:8%; float:left; display:none;">
		<div id="addBtn" class="btn" style="display: block;">
			<span class="wordBtn">新增</span>
		</div>
		<div id="editBtn" class="btn" style="display: block;">
			<span class="wordBtn">编辑</span>
		</div>
		<div id="deleteBtn" class="btn" style="display: block;">
			<span class="wordBtn">删除</span>
		</div>
		<div id="copyBtn" class="btn" style="display: block;">
			<span class="wordBtn">复制</span>
		</div>
		<div id="exportBtn" class="btn" style="display: block;">
			<span class="wordBtn">导出</span>
		</div>
		<div id="importBtn" class="btn" style="display: none;">
			<span class="wordBtn">导入</span>
		</div>
		<div id="templateBtn" class="btn" style="display: none;">
			<span class="wordBtn">模板</span>
		</div>
		
		<div style="float: right;">
			<input id="keyWords" class="ak-textbox ak-textboxBtnstyle" emptyText="请输入搜索关键字"
				style="height: 32px; width: 310px; margin: 10px; border: 1px solid #e8e8e8;" />
			<img id="searchImg" src="res/img/search.png" 
				style="position: relative; height: 24px; left: 90%; margin-top: 4%; cursor:pointer; float: left;"/>
		</div>
	</div>
	
	<!------------------------------------ 基础信息 ------------------------------------>
	<div id="baseInfoArea" style="display:block;">
		<div class="ak-toolbar" style="padding:20px;">
			<div style="height:25px;">
				<div style="width:33.3%; float:left;">
					<span style="color:red;">*</span>
					<span>保电主题</span>
					<input id="baseTheme" class="ak-textbox asLabel" emptyText="请输入..."
						vtype="rangeChar:1,80" required="false" style="width:260px; margin-left:10px;" />
				</div>
				
				<div style="width:33.3%; text-align:center; float:left;">
					<span style="color:red;">*</span>
					<span>负责单位</span>
					<input id="baseDepartment" class="ak-treeselect" emptyText="请选择..."
						style="width: 260px; margin-left:10px; font-family: 微软雅黑;" />
				</div>
				
				<div style="width:33.3%; text-align:right; float:left;">
					<span style="color:red;">*</span>
					<span>任务来源</span>
					<input id="baseSource" class="ak-combobox" url=""
						textField="text" valueField="id" style="width:260px; margin-left:10px;" />
				</div>
			</div>
			
			<div style="height:115px; margin-top:20px;">
				<div style="width:50%; float:left;">
					<span style="position: relative; top: -45px; margin-left:10px;">保电内容</span>
					<textarea class="ak-textarea" id="baseKeepPowerContent" emptyText="请输入..."
						vtype="rangeChar:1,2000" style="width:80%; height: 115px; margin-left:10px;"></textarea>
				</div>
				
				<div style="width:50%; text-align:right; float:right;">
					<span style="position: relative; top: -45px;">其他要求</span>
					<textarea class="ak-textarea" id="baseLeaderRequest" emptyText="请输入..."
						vtype="rangeChar:1,2000" style="width:80%; height: 115px; margin-left:10px;"></textarea>
				</div>
			</div>
		</div>
		
		<div id="baseEnsureLevelArea" class="ak-toolbar" style="padding:20px 10px; margin-top:10px;">
			<div style="height:18px;">
				<span style="color:red;">*</span>
				<span>保电阶段</span>
				<img id="baseAddImg" src="res/img/add_green.png" style="height: 18px; margin: -5px 10px; cursor: pointer;"/>
				<img id="baseDeleteImg" src="res/img/delete_green.png" style="height: 18px; margin: -5px 0px; cursor: pointer;"/>
			</div>
			<div id="baseInfoGrid" class="ak-datagrid"
				style="width: 100%; height: 267px; margin-top:20px;" idField="id" allowResize="false" pageSize="15" 
				allowCellWrap="false" allowCellEdit="true" allowCellSelect="true" multiSelect="true"
				editNextRowCell="true" url="" showPager="false" allowAlternating="true">
				<div property="columns">
					<div type="checkcolumn"></div>
					<div name="startTime" field="startTime" width="18%" headerAlign="center"
						align="center" allowSort="false"><span style="color:red;">*</span>
						开始时间 <input id="baseBeginTime" property="editor" class="ak-datepicker" 
						format="yyyy-MM-dd H:mm" showTime="true" allowInput="false"/>
					</div>
					<div name="endTime" field="endTime" width="18%" headerAlign="center"
						align="center" allowSort="false"><span style="color:red;">*</span>
						结束时间 <input id="baseEndTime" property="editor" class="ak-datepicker"
						format="yyyy-MM-dd H:mm" showTime="true" allowInput="false"/>
					</div>
					<div name="ensureLevel" field="ensureLevel" width="19%" headerAlign="center"
						align="center" allowSort="false"><span style="color:red;">*</span>
						保电级别 <input id="baseEnsureLevel" property="editor" class="ak-combobox" url="" />
					</div>
				</div>
			</div>
			
			<div style="height:36px; margin-top:20px;">
				<!-- 简介图片 -->
				<div id="introPhoto" style="width:8%; height:36px; margin-left: 135px; cursor:pointer; float:left;">
					<img id="" src="res/img/introPicture_blue.png" />
					<span style="position: relative; top: -12px; left: 10px;  text-decoration: underline;">简介图片</span>
				</div>
				
				<!-- 供电图 -->
				<div id="powerSupplyPhoto" style="width:8%; margin-left: 15%; cursor:pointer; float:left;">
					<img id="powerSupplyPhotoImg" src="res/img/powerSupply.png" />
					<span style="position: relative; top: -12px; left: 10px;  text-decoration: underline;">供电图</span>
				</div>
				
				<!-- 相关附件 -->
				<div id="attachmentPhoto" style="width:8%; margin-left: 15%; cursor:pointer; float:left;">
					<img id="" src="res/img/notification.png" />
					<span style="position: relative; top: -12px; left: 10px;  text-decoration: underline;">相关附件</span>
				</div>
			</div>
		</div>
	</div>
	
	<!------------------------------------ 组织机构 ------------------------------------>
	<div id="organizationArea" style="height: 80%; width: 100%; margin-top: 10px; float: left; display: none;">
		<!-- 左边部分 -->
		<div id="organizationLeftArea" class="ak-toolbar" style="width:18%; height:96%; padding:10px; float:left;">
			<ul id="orgTree" class="ak-tree" style="width:100%; height:100%; padding:5px;" expandOnLoad="true"
		        showTreeIcon="true" showTreeLines="false"  textField="text" idField="id" >        
		    </ul>
		</div>
		
		<!-- 右边部分 -->
		<div id="organizationRightArea" class="ak-toolbar" style="width:78.3%; height:95%; padding:10px; margin-left:10px; float:left;">
			<!-- 表格 -->
			<div id="organizationGrid" class="ak-datagrid"
				style="width: 100%; height: 100%;"
				idField="id" allowResize="false"
					pageSize="15" allowCellWrap="false" allowCellEdit="true"
					allowCellSelect="true" multiSelect="true"
					editNextRowCell="true" url=""
					allowAlternating="true">
				<div property="columns" style="background: #eceef6;">
					<div type="checkcolumn"></div>
					<div name="id" field="id" header="主键" visible="false"></div>
					<div name="orgName" field="orgName" header="组织名称" headerAlign="center"
						width="10%" align="center" allowSort="false">
						<input id="orgNameValue" property="editor" class="ak-textbox" vtype="rangeChar:1,100"/></div>
					<div name="superOrgID" field="superOrgID" header="上级组织ID" visible="false"></div>
					<div name="superOrg" field="superOrg" header="上级组织" headerAlign="center"
						width="10%" align="center" allowSort="false"></div>
					<div name="orgIntro" field="orgIntro" header="组织简介" headerAlign="center"
						width="10%" align="center" allowSort="false">
						<input id="orgIntroValue" property="editor" class="ak-textbox" vtype="rangeChar:0,500"/></div>
					<div name="dutyPerson" field="dutyPerson" header="负责人" headerAlign="center"
						width="10%" align="center" allowSort="false">
						<input id="dutyPersonValue" property="editor" class="ak-textbox" vtype="rangeChar:0,100"/></div>
					<div name="phoneNumber" field="phoneNumber" header="手机号码" headerAlign="center"
						width="10%" align="center" allowSort="false">
						<input id="phoneNumberValue" property="editor" class="ak-textbox" vtype="rangeChar:0,100"/></div>
					<div name="contactNumber" field="contactNumber" header="联系电话" headerAlign="center"
						width="10%" align="center" allowSort="false">
						<input id="contactNumberValue" property="editor" class="ak-textbox" vtype="rangeChar:0,100"/></div>
					<div name="dutyPersonPhoto" field="dutyPersonPhoto" header="负责人照片" headerAlign="center" width="10%"
						align="center" allowSort="false"></div>
					<div name="ensurePlan" field="ensurePlan" header="保电方案" headerAlign="center"
						width="10%" align="center" allowSort="false"></div>
					<div name="ensureArea" field="ensureArea" header="保电区域" headerAlign="center"
						width="10%" align="center" allowSort="false"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!------------------------------------ 保电站线 ------------------------------------>
	<div id="stationOrLineArea" style="height: 88%; width: 100%; margin-top: 10px; float: left; display: none;">
		<!-- 左边部分 -->
		<div id="stationOrLineLeftArea" class="ak-toolbar" style="width:24.6%; height:100%; float:left;">
			<div style="height: 32px; background: #eceef6; font-size: 16px;">
				<div class="tab active" id="stationTab">
					<span class="wordStyle">站</span>
				</div>
				<div class="tab" id="lineTab">
					<span class="wordStyle">线</span>
				</div>
			</div>
			
			<!-- 对站线树的查询功能暂时不做 -->
			<!-- <div style="height: 25px; background: rgb(236, 238, 246); margin: 10px; padding:20px 10px;">
				<div style="float:left;">
					<span id="solLeftText">电站名称</span>
					<input id="solLeftName"
						class="ak-textbox asLabel" emptyText="请输入..."
						required="false" style="width: 145px; margin-left:10px;" />
				</div>
				
				<div class="winBtn" style="float: right;">
					<button id="solLeftSearchBtn">查询</button>
				</div>
			</div> -->
			
			<div id="stationTreeArea" style="height:92%; padding:10px; display:block;">
				<ul id="stationDeviceTree" class="ak-tree" style="width:100%; height:100%; padding:5px;" expandOnLoad="true"
			        showTreeIcon="true" showTreeLines="false"  textField="text" idField="id"
			        showCheckBox="true" allowSelect="false" imgPath="res/img/icons/" >        
			    </ul>
			</div>
			
			<div id="lineTreeArea" style="height:92%; padding:10px; display:none;">
				<ul id="lineDeviceTree" class="ak-tree" style="width:100%; height:100%; padding:5px;" expandOnLoad="true"
			        showTreeIcon="true" showTreeLines="false"  textField="text" idField="id"
			        showCheckBox="true" allowSelect="false" imgPath="res/img/icons/" >        
			    </ul>
			</div>
		</div>
		
		<!-- 右边部分 -->
		<div id="stationOrLineRightArea" class="ak-toolbar" style="width:74.5%; height:100%; margin-left:10px; float:left;">
			<div style="height: 25px; background: rgb(236, 238, 246); margin: 10px; padding:20px;">
				<div style="float:left;">
					<span>电压等级</span>
					<input id="solVoltageLevel" class="ak-combobox" emptyText="请选择..."
						url="" multiSelect="true"
						textField="text" valueField="id" style="width: 150px; margin-left:10px;" />
				</div>
				
				<div style="margin-left:40px; float:left;">
					<span>所属单位</span>
					<input id="solDepartment" class="ak-treeselect"  emptyText="请选择..."
						multiSelect="true" showFolderCheckBox="true" style="width: 150px; font-family: 微软雅黑;"/>
				</div>
				
				<div style="margin-left:40px; float:left;">
					<span>站线名称</span>
					<input id="solRightName"
						class="ak-textbox asLabel" emptyText="请输入..."
						required="false" style="width: 150px; margin-left:10px;" />
				</div>
				
				<div class="winBtn" style="float: right;">
					<button id="solMoveBtn">移除</button>
					<button id="solRightSearchBtn">查询</button>
					<button id="solEditBtn">编辑</button>
				</div>
			</div>
			
			<!-- 表格 -->
			<div style="height: 84%; background:#ffffff; padding:0 10px; float: left;">
				<div id="stationOrLineGrid" class="ak-datagrid"
					allowAlternating="true" allowResize="false" pageSize="15" multiSelect="true"
					style="width: 100%; height: 100%;">
					<div property="columns" style="background: #eceef6;">
						<div type="checkcolumn"></div>
						<div name="id" field="id" header="主键" visible="false"></div>
						<div name="voltageLevel" field="voltageLevel" header="电压等级" headerAlign="center"
							width="10%" align="center" allowSort="false"></div>
						<div name="organization" field="organization" header="所属单位" headerAlign="center"
							width="10%" align="center" allowSort="false"></div>
						<div name="stationLineName" field="stationLineName" header="站线名称" headerAlign="center"
							width="10%" align="center" allowSort="false"></div>
						<div name="deviceName" field="deviceName" header="保电设备" headerAlign="center"
							width="10%" align="center" allowSort="false"></div>
						<div name="ensureLevel" field="ensureLevel" headerAlign="center"
							width="10%" align="center" allowSort="false">
							<span style="color:red; margin-right:4px;">*</span>保电级别
						</div>
						<div name="orgBelongName" field="orgBelongName" headerAlign="center"
							width="10%" align="center" allowSort="false">
							<span style="color:red; margin-right:4px;">*</span>所属组织
						</div>
						<div name="dutyPerson" field="dutyPerson" header="负责人" headerAlign="center"
							width="10%" align="center" allowSort="false"></div>
						<div name="ensurePlan" field="ensurePlan" header="保电方案" headerAlign="center" width="10%"
							align="center" allowSort="false"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 站线弹窗 -->
	<div id="stationOrLineWindow" class="ak-window" title="站线-编辑" style="width:47%; height:60.5%;" showModal="true" allowDrag="false">
		<div style="height:18px; margin:15px 0 0 15px;">
			<span style="color:red;">*</span>
			<span>保电级别</span>
			<img id="solAddImg" src="res/img/add_green.png" style="height: 18px; margin: -5px 10px; cursor: pointer;"/>
			<img id="solDeleteImg" src="res/img/delete_green.png" style="height: 18px; margin: -5px 0px; cursor: pointer;"/>
		</div>
		
		<div id="solWinGrid" class="ak-datagrid"
			style="height: 220px; width:auto; margin:20px 5px;" idField="id" allowResize="false"
			pageSize="15" allowCellWrap="false" allowCellEdit="true"
			allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"
			editNextRowCell="true" url="" showPager="false"
			showVGridLines="false" showHGridLines="false"
			allowAlternating="true"  editNextOnEnterKey="true">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div name="startTime" field="startTime" width="18%" headerAlign="center"
					align="center" allowSort="false"><span style="margin-right:4px; color:red;">*</span>开始时间 
					<input id="solBeginTime" property="editor" 
						class="ak-datepicker" format="yyyy-MM-dd H:mm" showTime="true" allowInput="false" />
				</div>
				<div name="endTime" field="endTime" width="18%" headerAlign="center"
					align="center" allowSort="false"><span style="margin-right:4px; color:red;">*</span>结束时间 
					<input id="solEndTime" property="editor" 
						class="ak-datepicker" format="yyyy-MM-dd H:mm" showTime="true" allowInput="false" />
				</div>
				<div name="ensureLevel" field="ensureLevel" width="19%" headerAlign="center"
					align="center" allowSort="false"><span style="margin-right:4px; color:red;">*</span>保电级别 
					<input id="solEnsureLevel" property="editor" 
						 class="ak-combobox" url="" style="width: 100%;" />
				</div>
			</div>
		</div>
		
		<div style="width:100%; height:25px;">
			<div style="width:50%; float:left;">
				<span style="color:red; margin-left:15px;">*</span>
				<span>所属组织</span>
				<input id="solOrg" emptyText="请选择..." class="ak-treeselect" style="width: 220px;" url=""
	        		textField="text" valueField="id" parentField="pid" expandOnLoad="true" allowInput="false" />
			</div>
			
			<div style="width:50%; text-align:right; float:left;">
				<span>负责人</span>
				<input id="solDutyPerson" class="ak-textbox asLabel" emptyText="请输入..." vtype="rangeChar:0,100"
					required="false" allowInput="true" style="width: 220px; margin-right:15px;" />
			</div>
		</div>
		
		<div style="height:25px; margin:20px 15px 15px 15px;">
			<div id="solAttachmentArea" style="width:50%; float:left;">
				<span style="margin-left:10px;">保电方案</span>
				<input id="solAttachment" class="ak-textbox" emptyText="请选择..."
					required="false" allowInput="false" style="width: 220px;" />
				<img id="solAttachmentImg" src="res/img/attachment_green.png"
					style="position: relative; width: 18px; top: 5px; left: -24px; cursor:pointer;" />
			</div>
			
			<div class="winBtn" style="float:right;">
				<button id="solWinSaveBtn">保存</button>
			</div>
		</div>
	</div>
	
	<!------------------------------------ 用户 ------------------------------------>
	<div id="userArea" style="height: 83%; background:#ffffff; padding:10px; margin-top: 10px; float: left; display: none;">
		<div id="userGrid" class="ak-datagrid" allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true"
			style="width: 100%; height: 99.9%;">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div name="id" field="id" header="主键" visible="false"></div>
				<div name="userName" field="userName" header="用户名称" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="ensureLevel" field="ensureLevel" header="保电级别" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="geographyLocation" field="geographyLocation" header="地理位置" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="belongOrgName" field="belongOrgName" header="所属组织" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="dutyPersonName" field="dutyPersonName" header="负责人" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="emergencyPlan" field="emergencyPlan" header="应急预案" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="joinLineMap" field="joinLineMap" header="接线图" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
			</div>
		</div>
	</div>
	
	<!-- 用户弹窗 -->
	<div id="userWindow" class="ak-window" title="用户-新增" style="width:47%; height:80.5%;" showModal="true" allowDrag="false">
		<div class="ak-toolbar" style="padding:20px;">
			<div style="height:25px;">
				<div id="userWinUserNameArea" style="width:50%; float:left;">
					<span style="color:red;">*</span>
					<span>用户名称</span>
					<input id="userName" class="ak-textbox asLabel" emptyText="请输入..."
						vtype="rangeChar:1,100" required="false" style="width: 220px;" />
				</div>
				
				<div id="userWinOrgArea" style="width:50%; text-align:right; float:left;">
					<span style="color:red;">*</span>
					<span>所属组织</span>
					<input id="userOrg" emptyText="请选择..." class="ak-treeselect" style="width: 220px;" url=""
		        		textField="text" valueField="id" parentField="pid" expandOnLoad="true" allowInput="false" />
				</div>
			</div>
			
			<div style="height:25px; margin-top:20px;">
				<div id="userWinDutyPersonArea" style="width:50%; float:left;">
					<span style="margin-left:12px; color:red;">*</span>
					<span>负责人</span>
					<input id="userDutyPerson" class="ak-textbox asLabel" emptyText="请输入..."
						vtype="rangeChar:1,100" required="false" allowInput="true" style="width: 220px;" />
				</div>
				
				<div id="userWinEmergencyPlanArea" style="width:50%; text-align:right; float:left;">
					<span style="margin-left:9px;">应急预案</span>
					<input id="userEmergencyPlan" class="ak-textbox asLabel" emptyText="请选择..."
						required="false" allowInput="false" style="width: 220px;" />
					<img id="userEmergencyPlanImg" src="res/img/attachment_green.png"
							style="position: relative; width: 18px; top: -22px; left: -3px; cursor:pointer;" />
				</div>
			</div>
		</div>
		
		<div class="ak-toolbar" style="margin-top:10px; padding:10px;">
			<div style="height:25px; margin:10px;">
				<span style="color:red;">*</span>
				<span>保电级别</span>
				<img id="userAddImg" src="res/img/add_green.png" style="height: 18px; margin: -5px 10px; cursor: pointer;"/>
				<img id="userDeleteImg" src="res/img/delete_green.png" style="height: 18px; margin: -5px 0px; cursor: pointer;"/>
			</div>
			
			<div id="userWinGrid" class="ak-datagrid"
				style="width: 100%; height: 220px; margin-top:10px;" idField="id" allowResize="false"
				pageSize="15" allowCellWrap="false" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"
				editNextRowCell="true" url="" showPager="false"
				showVGridLines="false" showHGridLines="false"
				allowAlternating="true"  editNextOnEnterKey="true">
				<div property="columns">
					<div type="checkcolumn"></div>
					<div name="startTime" field="startTime" width="18%" headerAlign="center"
						align="center" allowSort="false"><span style="color:red;">*</span>开始时间 
						<input id="userBeginTime" property="editor" 
							class="ak-datepicker" format="yyyy-MM-dd H:mm" showTime="true" allowInput="false" />
					</div>
					<div name="endTime" field="endTime" width="18%" headerAlign="center"
						align="center" allowSort="false"><span style="color:red;">*</span>结束时间 
						<input id="userEndTime" property="editor" 
							class="ak-datepicker" format="yyyy-MM-dd H:mm" showTime="true" allowInput="false" />
					</div>
					<div name="ensureLevel" field="ensureLevel" width="19%" headerAlign="center"
						align="center" allowSort="false"><span style="color:red;">*</span>保电级别 
						<input id="userEnsureLevel" property="editor" 
							 class="ak-combobox" url="" style="width: 100%;" />
					</div>
				</div>
			</div>
			
			<div style="height:36px;  margin: 20px 0 10px 10px;">
				<!-- 接线图 -->
				<div id="userJoinLineMap" style="width:100px; cursor:pointer; float:left;">
					<img id="userJoinLineMapImg" src="res/img/powerSupply.png" />
					<span style="position: relative; top: -12px; left: 10px;  text-decoration: underline;">接线图</span>
				</div>
				
				<!-- 地理位置 -->
				<!-- <div id="userGeographyLocation" style="width:100px; margin-left:60px; cursor:pointer; float:left;">
					<img id="userGeographyLocationImg" src="res/img/site_blue.png" />
					<span style="position: relative; top: -12px; left: 10px;  text-decoration: underline;">地理位置</span>
				</div> -->
			</div>
		</div>
		
		<div class="winBtn" style="margin-top:10px; float:right;">
			<button id="userWinSaveBtn">保存</button>
		</div>
	</div>
	
	<!------------------------------------ 人员 ------------------------------------>
	<div id="personnelArea" style="height: 83%; background:#ffffff; padding:10px; margin-top: 10px; float: left; display: none;">
		<div id="personnelGrid" class="ak-datagrid" allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true"
			style="width: 100%; height: 99.9%;">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div name="id" field="id" header="主键" visible="false"></div>
				<div name="personDate" field="personDate" header="日期" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="personName" field="personName" header="姓名" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="type" field="type" header="专业" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="organizationName" field="organizationName" header="所属单位" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="belongOrgName" field="belongOrgName" header="所属组织" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="phoneNumber" field="phoneNumber" header="联系电话" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="taskDeclare" field="taskDeclare" header="任务说明" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
			</div>
		</div>
	</div>
	
	<!------------------------------------ 车辆 ------------------------------------>
	<div id="carArea" style="height: 83%; background:#ffffff; padding:10px; margin-top: 10px; float: left; display: none;">
		<div id="carGrid" class="ak-datagrid" allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true"
			style="width: 100%; height: 99.9%;">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div name="id" field="id" header="主键" visible="false"></div>
				<div name="vehicleDate" field="vehicleDate" header="日期" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="brandNumber" field="brandNumber" header="车牌号" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="type" field="type" header="车辆类型" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="organizationName" field="organizationName" header="所属单位" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="belongOrgName" field="belongOrgName" header="所属组织" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="driver" field="driver" header="驾驶员" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="phoneNumber" field="phoneNumber" header="联系电话" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="taskDeclare" field="taskDeclare" header="任务说明" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
			</div>
		</div>
	</div>
	
	<!------------------------------------ 驻点 ------------------------------------>
	<div id="stagnationPointArea" style="height: 83%; background:#ffffff; padding:10px; margin-top: 10px; float: left; display: none;">
		<div id="stagnationPointGrid" class="ak-datagrid" allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true"
			style="width: 100%; height: 99.9%;">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div name="id" field="id" header="主键" visible="false"></div>
				<div name="stationName" field="stationName" header="驻点名称" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="location" field="location" header="位置" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="belongOrgName" field="belongOrgName" header="所属组织" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="dutyPerson" field="dutyPerson" header="负责人" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="phoneNumber" field="phoneNumber" header="联系电话" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="taskDeclare" field="taskDeclare" header="任务说明" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
			</div>
		</div>
	</div>
	
	<!------------------------------------ 宾馆 ------------------------------------>
	<div id="hotelArea" style="height: 83%; background:#ffffff; padding:10px; margin-top: 10px; float: left; display: none;">
		<div id="hotelGrid" class="ak-datagrid" allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true"
			style="width: 100%; height: 99.9%;">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div name="id" field="id" header="主键" visible="false"></div>
				<div name="hotelName" field="hotelName" header="宾馆名称" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="location" field="location" header="位置" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="linkman" field="linkman" header="联系人" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="phoneNumber" field="phoneNumber" header="联系电话" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="remark" field="remark" header="备注" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
			</div>
		</div>
	</div>
	
	<!------------------------------------ 医院 ------------------------------------>
	<div id="hospitalArea" style="height: 83%; background:#ffffff; padding:10px; margin-top: 10px; float: left; display: none;">
		<div id="hospitalGrid" class="ak-datagrid" allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true"
			style="width: 100%; height: 99.9%;">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div name="id" field="id" header="主键" visible="false"></div>
				<div name="hospitalName" field="hospitalName" header="医院名称" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="location" field="location" header="位置" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="linkman" field="linkman" header="联系人" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="phoneNumber" field="phoneNumber" header="联系电话" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
				<div name="remark" field="remark" header="备注" headerAlign="center"
					width="10%" align="center" allowSort="false"></div>
			</div>
		</div>
	</div>
	
	
	
	<div id="totalSaveBtnArea" class="winBtn" style="margin-top: 10px; float: right;">
		<button id="totalSaveBtn">保存</button>
	</div>
</body>
</html>
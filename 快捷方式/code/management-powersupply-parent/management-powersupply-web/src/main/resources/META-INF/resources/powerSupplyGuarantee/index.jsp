<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<ak:framework />
<title>供电任务</title>
<link href="res/css/page.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="ak-fit" style="overflow: hidden;">
		<div class="ak-splitter" vertical="true" allowResize="false"
			handlerSize="0" style="width: 100%; height: 100%;">
			<div size="15%" showCollapseButton="false">
				<div class="ak-splitter" vertical="true" allowResize="false"
					handlerSize="0" style="width: 100%; height: 100%;">
					<div size="50%" showCollapseButton="false" class="bg-color">
						<!-- tab -->
						<div id="radio"
							class="ak-radiobuttonlist ak-radiobuttonlistnewstyle ak-radiobuttonlisttip ak-radiobuttonlistcolor"
							style="left: 0px; top: 14px; position: absolute;" repeatItems="1"
							repeatLayout="table" repeatDirection="vertical" textField="text"
							valueField="id"></div>
					</div>
					<div size="50%" showCollapseButton="false" class="bg-color">
						<!-- 功能按钮  -->
						<div id="button" class="F-box F-box-hoveroff" style="width: 100%; height: 90%; background:#ffffff;">
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="addBtn" style="display: block">新增</a>
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="editBtn" style="display: block">编辑</a>
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="deleteBtn" style="display: block">删除</a>
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="examineBtn" style="display: block">查看</a>
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="dailyBtn" style="display: none">日报</a>
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="sendBtn" style="display: block">发送</a>
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="followBtn" style="display: block">跟踪</a>
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="closeBtn" style="display: none">闭环</a>
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="fileBtn" style="display: none">归档</a>
							<a class="ak-button ak-btnnewstyle" href="javascript:void(0)" id="exportBtn" style="display: block">导出</a>
							
							<div>
								<a class="ak-button ak-normalbutton" id="exactSearchBtn" href="javascript:void(0)"
									style="height: 32px; width: 118px; text-align: center; margin: 8px 10px 0 10px; background: #009694; float: right;">精确搜索﹀</a>
								<div style="float: right;">
									<input id="keyWords" class="ak-textbox ak-textboxBtnstyle" emptyText="请输入搜索关键字"
										style="height: 32px; width: 310px; margin-top: 8px; border: 1px solid #e8e8e8;" />
									<img id="searchImg" src="res/img/search.png" 
										style="position: relative; height: 24px; left: 90%; margin-top: 4%; cursor:pointer; float: left;"/>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div size="85%" showCollapseButton="false" class="swiper-container">
				<div id="searchArea" showCollapseButton="false" class="bg-color" 
					style="height:70px; padding: 20px; font-size: 14px; background: #e8f5f5; margin-bottom:7px; display: none;">
					<div style="width:100%; height:25px;">
						<div style="float:left;">
							<div style="width:65px; float:left;">开始时间</div>
							<input id="beginTime1Select" class="ak-datepicker" style="width: 220px;" emptyText="请选择..."
								format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" allowInput="false" />
						</div>
						
						<div style="margin-left:70px; text-align:right; float:left;">
							<div style="width:65px; float:left;"><span style="margin-right:5px;">至</span></div>
							<input id="beginTime2Select" class="ak-datepicker" style="width: 220px;" emptyText="请选择..."
								format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" allowInput="false" />
						</div>
						
						<div style="margin-left:70px; text-align:right; float:left;">
							<div style="width:65px; float:left;"><span style="margin-right:5px;">结束时间</span></div>
							<input id="endTime1Select" class="ak-datepicker" style="width: 220px;" emptyText="请选择..."
								format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" allowInput="false" />
						</div>
						
						<div style="text-align:right; float:right;">
							<div style="width:65px; float:left;"><span style="margin-right:5px;">至</span></div>
							<input id="endTime2Select" class="ak-datepicker" style="width: 220px;" emptyText="请选择..."
								format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" allowInput="false" />
						</div>
					</div>
					
					<div style="width:100%; height:25px; margin-top:20px;">
						<div style="float:left;">
							<div style="width:65px; float:left;">负责单位</div>
							<!-- <input id="responsibleDepartmentSelect" class="ak-combobox" style="width: 220px;" textField="text" valueField="id"
								emptyText="请选择..." required="false" url="" showNullItem="true" nullItemText="全部" /> -->
							<input id="responsibleDepartmentSelect" class="ak-treeselect"  emptyText="请选择..."
								style="width: 220px; font-family: 微软雅黑;"/>
						</div>
						
						<div style="margin-left:70px; float:left;">
							<div style="width:65px; float:left;">任务来源</div>
							<input id=taskSourceSelect class="ak-combobox" style="width: 220px;" textField="text" valueField="id"
								emptyText="请选择..." required="false" url="" showNullItem="true" nullItemText="全部" />
						</div>
						
						<div style="margin-left:70px; float:left;">
							<div style="width:65px; float:left;">保电级别</div>
							<input id="powerLevelSelect" class="ak-combobox" style="width: 220px;" textField="text" valueField="id"
								emptyText="请选择..." required="false" url="" showNullItem="true" nullItemText="全部" />
						</div>
						
						<div class="pageBtn" style="float:right;">
							<a class="ak-button ak-normalbuttonstyle" id="searchBtn" href="javascript:void(0)"style="width:88px; margin-right:10px; border:0px; float: left;" >查询</a>
	                        <a class="ak-button ak-normalbuttonstyle" id="resetBtn" href="javascript:void(0)"style="width:88px; border:0px; float: left;" >重置</a>		
						</div>
					</div>
				</div>
				
				<div id="gridArea" showCollapseButton="false" class="bg-color" style="width:100%; height:99%;">
					<div id="showgrid F-box F-box-hoveroff"
						style="width: 100%; height: 100%;">
						<div id="dataGrid" class="ak-datagrid"
							style="width: 100%; height: 100%;"
							allowResize="false" url="" idField="id" multiSelect="true"
							pageSize="20" showVGridLines="false" showHGridLines="false"
							allowAlternating="true">
							<div property="columns" style="background: #eceef6;">
								<div type="checkcolumn"></div>
								<div name="id" field="id" header="主键" visible="false"></div>
								<!-- <div type="indexcolumn" header="序号" headerAlign="center" width="5%"></div> -->
								<div name="theme" field="theme" header="保电主题" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="startTime" field="startTime" header="开始时间" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="endTime" field="endTime" header="结束时间" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="taskSource" field="taskSource" header="任务来源" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="dutyUnitName" field="dutyUnitName" header="负责单位" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="ensureLevel" field="ensureLevel" header="保电级别" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="ensureDetails" field="ensureDetails" header="保电内容" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="leaderRequest" field="leaderRequest" header="其他要求" headerAlign="center" width="10%"
									align="center" allowSort="false"></div>
								<div name="ensureNotice" field="ensureNotice" header="相关附件" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="introPicture" field="introPicture" header="简介图片" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="stationOrLine" field="stationOrLine" header="涉及站线" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
								<div name="organizationInfo" field="organizationInfo" header="组织信息" headerAlign="center"
									width="10%" align="center" allowSort="false"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
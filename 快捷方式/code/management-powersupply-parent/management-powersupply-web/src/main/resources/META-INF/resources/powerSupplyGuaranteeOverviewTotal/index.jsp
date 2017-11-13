<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
<ak:framework />
<link href="res/css/index.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保电总览</title>
</head>
<body style="background-color: #313C6D">
	<div class="ak-fit" style="overflow: hidden;">
		<div class="ak-splitter" vertical="true" allowResize="false"
			handlerSize="0" style="width: 100%; height: 100%;">
			<div size="2%" showCollapseButton="false">
				<div style="position: absolute; left: 0px;">
					<span class="ak-selectleftnewstyle" id="ywdwc"><font
						color="white">运维单位</font></span> <input id="ywdw"
						class="ak-combobox ak-comboboxnewstyle"
						style="height: 30px; width: 240px; background: #4F5796; opacity: 1; color: #FFF; border: 0; padding: 0; padding-left: 20px;"
						textField="text" valueField="id" emptyText="请选择地域"
						required="false" allowInput="false" showNullItem="false"
						nullItemText="全部" />
				</div>
			</div>
			<div size="97.5%" showCollapseButton="false">
				<div class="ak-splitter" vertical="true" allowResize="false"
					handlerSize="6" style="width: 100%; height: 100%;">
					<!-- 总体布局上 -->
					<div size="71%" showCollapseButton="false">
						<div style="width: 100%; height: 100%; background-color:;">
							<div class="ak-splitter" vertical="false" allowResize="false"
								handlerSize="6" style="width: 100%; height: 100%;">
								<div size="45.7%" showCollapseButton="false">
									<div
										style="width: 100%; height: 100%; background-color: rgba(5, 125, 197, 0.11);">


										<!-- 浮动 class="F-box"-->
										<div style="height: 100%; width: 100%">

											<iframe name="myIframe_2D" id="iframe_2D"
												src="/mcsasWebgis2d/2dmap.html"
												style="width: 100%; height: 100%; border: none;"></iframe>
										</div>
										<!-- /mcsasWebgis2d/2dmap.html  http://127.0.0.1:8090/2dmap.html
					     http://10.144.139.45:8090/2dmap.html
					  -->
									</div>
								</div>
								<!-- 上面布局右面部分 -->
								<div size="54.3%" showCollapseButton="false">
									<div style="width: 100%; height: 100%; background-color:;">
										<div class="ak-splitter" vertical="true" allowResize="false"
											handlerSize="6" style="width: 100%; height: 100%;">
											<!-- 保电任务 -->
											<div size="17%" showCollapseButton="false">
												<div
													style="width: 100%; height: 100%; background-color: white;">
													<!-- 左 -->
													<div
														style="display: flex; float: left; width: 4.1%; height: 100%; background-color: rgb(0, 150, 148);">
														<div
															style="text-align: center; margin: auto; width: 50%; height: 99%;">
															<font style="color: aliceblue; font-size: 13px;">保电任务</font>
														</div>
													</div>
													<!-- 右 -->
													<div style="float: right; width: 95.9%; height: 100%;">
														<!-- 任务状态 -->
														<div
															style="display: flex; float: left; width: 38%; height: 100%; background-color: white">
															<div
																style="margin: auto; height: 36%; width: 84%; background-color:">
																<font
																	style="line-height: 32px; font-size: 16px; font-weight: bold; float: left; width: 30%; height: 100%">
																	任务状态 </font>
																<div style="float: left; width: 35%; height: 100%">
																	<div id="underway"
																		style="float: left; text-align: center; line-height: 32px; height: 90%; width: 84%; cursor: pointer; background-color: rgb(235, 159, 9)">
																		进行中</div>
																	<div id="underway_mission" class="radius">0</div>
																</div>
																<div
																	style="text-align: center; float: left; width: 35%; height: 100%">
																	<div id="prepared"
																		style="float: left; line-height: 32px; height: 90%; width: 84%; cursor: pointer; background-color: rgb(0, 150, 148)">
																		准备中</div>
																	<div id="preparation_mission" class="radius">0</div>
																</div>
															</div>
														</div>
														<!-- 任务等级 -->
														<div
															style="display: flex; float: right; width: 62%; height: 100%; background-color:">
															<div
																style="margin: auto; height: 36%; width: 84%; background-color:">
																<font
																	style="line-height: 32px; font-size: 16px; font-weight: bold; float: left; width: 20%; height: 100%">
																	任务等级 </font>
																<!-- 四个颜色等级的父标签 -->
																<div style="float: left; width: 69%; height: 100%">
																	<div
																		style="text-align: center; line-height: 32px; height: 90%; width: 100%; background-color:">
																		<div
																			style="line-height: 13px; height: 45%; width: 100%">
																			<div id="super_level" class="super_level"
																				style="float: left; height: 100%; width: 24%">0</div>
																			<div id="one_level" class="one_level"
																				style="float: left; height: 100%; width: 25%">0</div>
																			<div id="two_level" class="two_level"
																				style="float: left; height: 100%; width: 25%">0</div>
																			<div id="three_level" class="three_level"
																				style="float: left; height: 100%; width: 25%">0</div>
																		</div>
																		<div
																			style="line-height: 5px; height: 10%; width: 100%">
																			<div class="super_level"
																				style="float: left; height: 100%; width: 24%; background-color: rgb(251, 63, 88)">
																			</div>
																			<div class="one_level"
																				style="float: left; height: 100%; width: 25%; background-color: rgb(235, 159, 9)">
																			</div>
																			<div class="two_level"
																				style="float: left; height: 100%; width: 25%; background-color: rgb(169, 143, 232)">
																			</div>
																			<div class="three_level"
																				style="float: left; height: 100%; width: 25%; background-color: rgb(1, 150, 149)">
																			</div>
																		</div>
																		<div
																			style="line-height: 13px; height: 45%; width: 100%">
																			<div class="super_level"
																				style="float: left; height: 100%; width: 24%">特级</div>
																			<div class="one_level"
																				style="float: left; height: 100%; width: 25%">一级</div>
																			<div class="two_level"
																				style="float: left; height: 100%; width: 25%">二级</div>
																			<div class="three_level"
																				style="float: left; height: 100%; width: 25%">三级</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
											<!-- 柱状图 -->
											<div size="83%" showCollapseButton="false">
												<div
													style="width: 100%; height: 100%; background-color: white;">
													<!-- 保电用户 -->
													<div style="float: right; width: 100%; height: 20%;">
														<!-- 左 -->
														<div style="float: left; width: 4.1%; height: 100%;">
															<div
																style="display: flex; width: 100%; height: 96%; background-color: rgb(0, 150, 148);">
																<div
																	style="text-align: center; margin: auto; width: 50%; height: 100%; line-height: 20px;">
																	<font style="color: aliceblue; font-size: 13px;">保电用户</font>
																</div>
															</div>
														</div>
														<!-- 右 -->
														<div style="float: right; width: 95.9%; height: 100%;">
															<!-- 用户 -->
															<div style="width: 100%; height: 99%; background-color:">
																<!-- 图片 -->
																<div style="float: left; height: 100%; width: 10%;">
																	<div style="display: flex; height: 70%; width: 100%">
																		<div
																			style="margin: auto; height: 90%; width: 90%; background: url(./res/img/yonghu.png) no-repeat center;">
																		</div>
																	</div>
																	<div style="display: flex; height: 30%; width: 100%">
																		<div
																			style="margin: 0 auto; margin-top: -18px; text-align: center">用户</div>
																	</div>
																</div>
																<!-- 彩条 -->
																<div id="userdata"
																	style="float: left; height: 100%; width: 80%; background-color:">
																	<div
																		style="text-align: center; line-height: 44px; height: 100%; width: 100%; background-color:">
																		<div
																			style="line-height: 44px; height: 45%; width: 100%">
																			<div id="user_level_super" class="user_level_super u"
																				style="float: left; height: 100%; width: 24%">0</div>
																			<div id="user_level_one" class="user_level_one u"
																				style="float: left; height: 100%; width: 25%">0</div>
																			<div id="user_level_two" class="user_level_two u"
																				style="float: left; height: 100%; width: 25%">0</div>
																			<div id="user_level_three" class="user_level_three u"
																				style="float: left; height: 100%; width: 25%">0</div>
																		</div>
																		<div
																			style="margin: auto; line-height: 5px; height: 6%; width: 100%">
																			<div class="user_level_super"
																				style="float: left; height: 100%; width: 24%; background-color: rgb(251, 63, 88)">
																			</div>
																			<div class="user_level_one"
																				style="float: left; height: 100%; width: 25%; background-color: rgb(235, 159, 9)">
																			</div>
																			<div class="user_level_two"
																				style="float: left; height: 100%; width: 25%; background-color: rgb(169, 143, 232)">
																			</div>
																			<div class="user_level_three"
																				style="float: left; height: 100%; width: 25%; background-color: rgb(1, 150, 149)">
																			</div>
																		</div>
																		<div
																			style="line-height: 38px; height: 45%; width: 100%">
																			<div class="user_level_super"
																				style="float: left; height: 100%; width: 24%">特级</div>
																			<div class="user_level_one"
																				style="float: left; height: 100%; width: 25%">一级</div>
																			<div class="user_level_two"
																				style="float: left; height: 100%; width: 25%">二级</div>
																			<div class="user_level_three"
																				style="float: left; height: 100%; width: 25%">三级</div>
																		</div>
																	</div>
																</div>
																<!-- 单位：个 -->
																<div
																	style="text-align: center; line-height: 86px; float: left; height: 100%; width: 10%; background-color:">
																	单位：个</div>
															</div>
															<!-- 蓝色的细线 -->
															<div
																style="margin-left: 22px; width: 95%; height: 1%; background-color: rgb(0, 150, 148);">
															</div>
														</div>
													</div>
													<!-- 保电站线 -->
													<div style="float: right; width: 100%; height: 60%;">
														<!-- 左 -->
														<div style="float: left; width: 4.1%; height: 100%;">
															<div
																style="display: flex; margin-top: 6px; width: 100%; height: 96%; background-color: rgb(0, 150, 148);">
																<div
																	style="text-align: center; margin: auto; width: 50%; height: 33%;">
																	<font style="color: aliceblue; font-size: 13px;">保电站线</font>
																</div>
															</div>
														</div>
														<!-- 右 -->
														<div
															style="float: right; width: 95.9%; height: 100%; background-color: white;">
															<!-- 线路和电站-->
															<div
																style="width: 100%; height: 99.5%; background-color:;">
																<!-- 线路 -->
																<div
																	style="height: 50%; width: 100%; background-color:;">
																	<div style="float: left; height: 100%; width: 10%;">
																		<div style="display: flex; height: 70%; width: 100%">
																			<div
																				style="margin: auto; height: 90%; width: 90%; background: url(./res/img/xianlu.png) no-repeat center;">
																			</div>
																		</div>
																		<div style="display: flex; height: 30%; width: 100%">
																			<div
																				style="margin: 0 auto; margin-top: -18px; text-align: center">线路</div>
																		</div>
																	</div>
																	<!-- echart -->
																	<div
																		style="float: left; height: 100%; width: 90%; background-color:">

																		<div id="lineChart" class="ak-echarts"
																			style="width: 100%; height: 100%;"></div>

																	</div>
																</div>
																<!-- 电站 -->
																<div style="height: 50%; width: 100%; background-color:">
																	<div style="float: left; height: 100%; width: 10%;">
																		<div style="display: flex; height: 70%; width: 100%">
																			<div
																				style="margin: auto; height: 90%; width: 90%; background: url(./res/img/dianzhan.png) no-repeat center;">
																			</div>
																		</div>
																		<div style="display: flex; height: 30%; width: 100%">
																			<div
																				style="margin: 0 auto; margin-top: -18px; text-align: center">电站</div>
																		</div>
																	</div>
																	<!-- echart -->
																	<div
																		style="float: left; height: 100%; width: 90%; background-color:">
																		<div id="stationChart" class="ak-echarts"
																			style="width: 100%; height: 100%;"></div>

																	</div>
																</div>
															</div>
															<!-- 蓝色的细线 -->
															<div
																style="margin-left: 22px; width: 95%; height: 0.5%; background-color: rgb(0, 150, 148);">
															</div>
														</div>
													</div>
													<!-- 资源保障 -->
													<div style="float: right; width: 100%; height: 20%;">
														<!-- 左 -->
														<div
															style="float: left; width: 4.1%; height: 100%; background-color:;">
															<div
																style="display: flex; margin-top: 6px; width: 100%; height: 94%; background-color: rgb(0, 150, 148);">
																<div
																	style="line-height: 19px; text-align: center; margin: auto; width: 50%; height: 100%;">
																	<font style="color: aliceblue; font-size: 13px;">资源保障</font>
																</div>

															</div>
														</div>
														<!-- 右 -->
														<div
															style="float: right; width: 95.9%; height: 100%; background-color: white;">
															<!-- 人员占24 -->
															<div style="float: left; height: 100%; width: 24%">
																<!-- 人员和图标 -->
																<div style="float: left; height: 100%; width: 40%">
																	<div style="display: flex; height: 60%; width: 100%">
																		<div
																			style="display: flex; margin: auto; -webkit-border-radius: 20px; height: 40px; width: 40px; background-color: rgb(52, 190, 224); box-shadow: #62BCD7 0px 0px 14px;">
																			<div
																				style="margin: auto; height: 90%; width: 90%; background: url(./res/img/renyuan.png) no-repeat center; background-size: 67%;">
																			</div>
																		</div>
																	</div>
																	<div style="display: flex; height: 40%; width: 100%">
																		<div style="margin: auto; text-align: center">人员</div>
																	</div>
																</div>
																<!-- 10000人数字 -->
																<div
																	style="display: flex; float: left; height: 100%; width: 60%">
																	<div style="height: 30%; width: 100%; margin: auto;">
																		<font id="personcount" style="font-size: 25px;">0</font>
																		人
																	</div>
																</div>
															</div>
															<!-- 车辆占19 -->
															<div style="float: left; height: 100%; width: 19%">
																<div style="float: left; height: 100%; width: 40%">
																	<div style="display: flex; height: 60%; width: 100%">
																		<div
																			style="display: flex; margin: auto; -webkit-border-radius: 20px; height: 40px; width: 40px; box-shadow: #62BCD7 0px 0px 14px; background-color: rgb(52, 190, 224);">
																			<div
																				style="margin: auto; height: 90%; width: 90%; background: url(./res/img/cheliang.png) no-repeat center; background-size: 73%;">
																			</div>
																		</div>
																	</div>
																	<div style="display: flex; height: 40%; width: 100%">
																		<div style="margin: auto; text-align: center">车辆</div>
																	</div>
																</div>
																<!--  数字 -->
																<div
																	style="display: flex; float: left; height: 100%; width: 60%">
																	<div style="height: 30%; width: 100%; margin: auto;">
																		<font id="Vehiclecount" style="font-size: 25px;">0</font>
																		辆
																	</div>
																</div>
															</div>
															<!-- 驻点占19 -->
															<div style="float: left; height: 100%; width: 19%">
																<div style="float: left; height: 100%; width: 40%">
																	<div style="display: flex; height: 60%; width: 100%">
																		<div
																			style="display: flex; margin: auto; -webkit-border-radius: 20px; height: 40px; width: 40px; background-color: rgb(52, 190, 224); box-shadow: #62BCD7 0px 0px 14px;">
																			<div
																				style="margin: auto; height: 90%; width: 90%; background: url(./res/img/zhudian.png) no-repeat center; background-size: 52%;">
																			</div>
																		</div>
																	</div>
																	<div style="display: flex; height: 40%; width: 100%">
																		<div style="margin: auto; text-align: center">驻点</div>
																	</div>
																</div>
																<!--  数字 -->
																<div
																	style="display: flex; float: left; height: 100%; width: 60%">
																	<div style="height: 30%; width: 100%; margin: auto;">
																		<font id="Stationcount" style="font-size: 25px;">0</font>
																		个
																	</div>
																</div>
															</div>
															<!-- 医院占19 -->
															<div style="float: left; height: 100%; width: 19%">
																<div style="float: left; height: 100%; width: 40%">
																	<div style="display: flex; height: 60%; width: 100%">
																		<div
																			style="display: flex; margin: auto; -webkit-border-radius: 20px; height: 40px; width: 40px; background-color: rgb(52, 190, 224); box-shadow: #62BCD7 0px 0px 14px;">
																			<div
																				style="margin: auto; height: 90%; width: 90%; background: url(./res/img/yiyuan.png) no-repeat center; background-size: 70%;">
																			</div>
																		</div>
																	</div>
																	<div style="display: flex; height: 40%; width: 100%">
																		<div style="margin: auto; text-align: center">医院</div>
																	</div>
																</div>
																<!--  数字 -->
																<div
																	style="display: flex; float: left; height: 100%; width: 60%">
																	<div style="height: 30%; width: 100%; margin: auto;">
																		<font id="Hospitalcount" style="font-size: 25px;">0</font>
																		个
																	</div>
																</div>
															</div>
															<!-- 宾馆占19 -->
															<div style="float: left; height: 100%; width: 19%">
																<div style="float: left; height: 100%; width: 40%">
																	<div style="display: flex; height: 60%; width: 100%">
																		<div
																			style="display: flex; margin: auto; -webkit-border-radius: 20px; height: 40px; width: 40px; background-color: rgb(52, 190, 224); box-shadow: #62BCD7 0px 0px 14px;">
																			<div
																				style="margin: auto; height: 90%; width: 90%; background: url(./res/img/binguan.png) no-repeat center; background-size: 70%;">
																			</div>
																		</div>
																	</div>
																	<div style="display: flex; height: 40%; width: 100%">
																		<div style="margin: auto; text-align: center">宾馆</div>
																	</div>
																</div>
																<!--  数字 -->
																<div
																	style="display: flex; float: left; height: 100%; width: 60%">
																	<div style="height: 30%; width: 100%; margin: auto;">
																		<font id="Hotelcount" style="font-size: 25px;">0</font>
																		个
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 总体布局下-->
					<div size="29%" showCollapseButton="false"
						class="swiper-container  ">
						<div style="width: 100%; height: 100%; background-color:;">
							<div class="ak-splitter" vertical="false" allowResize="false"
								handlerSize="6" style="width: 100%; height: 100%;">
								<div size="62%" showCollapseButton="false">
									<div
										style="width: 99.9%; height: 12%; background-color: white; text-ailgn: center; line-height: 27px; font-size: 18px; font-weight: 900;">
										&nbsp;&nbsp;&nbsp;&nbsp;保电任务清单</div>
									<div style="width: 100%; height: 88%; background-color:;">
										<div id="taskdatagrid" pagerButtons="#buttons"
											class="ak-datagrid" style="width: 100%; height: 100%;"
											allowResize="false" showColumns="true" idField="id"
											multiSelect="true" sizeList="[10,20,30,50]" pageSize="15"
											allowCellSelect="false" showVGridLines="true"
											showHGridLines="false" allowAlternating="true"
											showPageInfo="true">
											<div property="columns">
												<div headerAlign="center">
													<div property="columns">
														<!-- <div type="checkcolumn"  > </div> -->
														<div visible="false" field="id">主键</div>
														<div field="theme" width="10%" headerAlign="center"
															align="center" allowSort="true">保电主题</div>
														<div field="ensureLevel" width="8%" headerAlign="center"
															align="center" allowSort="true">最高保电级别</div>
														<div field="ensureLevel1" width="8%" headerAlign="center"
															align="center" allowSort="true">当前保电级别</div>
														<div field="dutyUnitName" width="8%" headerAlign="center"
															align="center" allowSort="true">负责单位名称</div>
														<div field="ensureDetails" width="10%"
															headerAlign="center" align="center" allowSort="true">保电内容</div>
														<div field="startTime" width="10%" headerAlign="center"
															align="center" allowSort="true">开始时间</div>
														<div field="endTime" width="10%" headerAlign="center"
															align="center" allowSort="true">结束时间</div>
														<div field="location" width="6%" headerAlign="center"
															align="center" allowSort="true">定位</div>
														<div field="gointo" width="4%" headerAlign="center"
															align="center" allowSort="true"></div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div size="38%" showCollapseButton="false">
									<div
										style="width: 100%; height: 100%; background-color: white;">
										<!-- 运行工况 -->
										<div style="float: left; width: 50%; height: 100%;">
											<div style="width: 100%; height: 12%;">
												<div
													style="float: left; width: 60%; height: 100%; line-height: 30px;">
													<font style="font-size: 20px; font-weight: 900;">&nbsp;&nbsp;&nbsp;&nbsp;运行工况</font>
													&nbsp;&nbsp;预报警
												</div>
												<div
													style="line-height: 29px; text-align: center; float: right; width: 40%; height: 100%; line-height: 36px;">
													&nbsp;&nbsp;&nbsp;&nbsp;单位&nbsp;:&nbsp;条</div>
											</div>
											<div style="width: 100%; height: 88%;">
												<!-- 气象 -->
												<div style="float: left; width: 33.3%; height: 50%;">
													<div id="weather"
														style="width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
														0</div>
													<div style="width: 100%; height: 40%; text-align: center;">
														气象</div>
												</div>
												<!-- 环境 -->
												<div style="float: left; width: 33.3%; height: 50%;">
													<div id="environment"
														style="width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
														0</div>
													<div style="width: 100%; height: 40%; text-align: center;">
														环境</div>
												</div>
												<div style="float: left; width: 33.3%; height: 50%;">
													<div id="gridRisk"
														style="width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
														0</div>
													<div style="width: 100%; height: 40%; text-align: center;">
														电网风险</div>
												</div>
												<div style="float: left; width: 33.3%; height: 50%;">
													<div id="overLoad"
														style="width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
														0</div>
													<div style="width: 100%; height: 40%; text-align: center;">
														重过载</div>
												</div>
												<div style="float: left; width: 33.3%; height: 50%;">
													<div id="monitoring"
														style="width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
														0</div>
													<div style="width: 100%; height: 40%; text-align: center;">
														在线监测</div>
												</div>
											</div>
										</div>
										<!-- 中间的分割线 -->
										<div style="float: left; width: 0.1%; height: 100%;">
											<div
												style="margin-top: 23px; float: left; width: 50%; height: 80%; border: #34BEE0 1px solid">
											</div>
										</div>
										<!-- 运行内容 -->
										<div style="float: right; width: 49.9%; height: 100%;">
											<div style="width: 100%; height: 10%;">
												<div style="float: left; width: 60%; height: 100%;">
													<font
														style="line-height: 29px; font-size: 16px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;运行内容</font>
												</div>
												<div
													style="line-height: 29px; text-align: center; float: right; width: 40%; height: 100%; line-height: 36px;">
													&nbsp;&nbsp;&nbsp;&nbsp;单位&nbsp;:&nbsp;项</div>
											</div>
											<div style="width: 100%; height: 90%;">
												<!-- 故障 -->
												<div style="float: left; width: 50%; height: 50%;">
													<div
														style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
														<div
															style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/guzhang.png) no-repeat center; background-position-x: 49.5%; background-size: 62%; background-color: #34BEE0;">
														</div>
													</div>
													<div style="width: 100%; height: 40%; text-align: center;">
														故障</div>
												</div>
												<!-- 环境 -->
												<div style="float: left; width: 50%; height: 50%;">
													<div style="width: 100%; height: 60%; line-height: 83px;">
														<div style="height: 100%; width: 50%; float: left">已恢复：&nbsp;&nbsp;</div>
														<div style="height: 100%; width: 50%; float: left">
															<font id="fault_dealed"
																style="font-size: 31px; color: #34BEE0">0</font>
														</div>
													</div>
													<div style="width: 100%; height: 40%;">
														<div style="height: 100%; width: 50%; float: left">处理中：&nbsp;&nbsp;</div>
														<div style="height: 100%; width: 50%; float: left">
															<font id="fault_dealing"
																style="font-size: 31px; color: #EB9F09">0</font>
														</div>
													</div>
												</div>
												<div style="float: left; width: 50%; height: 50%;">
													<div
														style="display: flex; width: 100%; height: 60%; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
														<div
															style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/quexian.png) no-repeat center; background-size: 55%; background-color: #34BEE0;">
														</div>
													</div>
													<div style="width: 100%; height: 40%; text-align: center;">
														缺陷</div>
												</div>
												<div style="float: left; width: 50%; height: 50%;">
													<div style="width: 100%; height: 60%; line-height: 83px;">
														<div style="height: 100%; width: 50%; float: left">已消缺：&nbsp;&nbsp;</div>
														<div style="height: 100%; width: 50%; float: left">
															<font id="DefectStatedealed"
																style="font-size: 31px; color: #34BEE0">0</font>
														</div>
													</div>
													<div style="width: 100%; height: 40%;">
														<div style="height: 100%; width: 50%; float: left">未消缺：&nbsp;&nbsp;</div>
														<div style="height: 100%; width: 50%; float: left">
															<font id="DefectStatedealing"
																style="font-size: 31px; color: #EB9F09">0</font>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 用户计划明细弹窗 -->
	<div id="userPlanInfoWindow" class="ak-window"
		headerStyle="background:#e8f5f5;" style="width:50%; height:45%;"
		showModal="true" allowDrag="false">
		<div class="ak-toolbar" style="padding: 10px;">
			<div id="userPlanInfoWinGrid" class="ak-datagrid"
				allowAlternating="true" allowResize="false" pageSize="20"
				multiSelect="true" style="width: 100%; height: 260px;">
				<div property="columns">
					<div name="startTime" field="startTime" header="开始时间"
						headerAlign="center" width="10%" align="center" allowSort="false"></div>
					<div name="endTime" field="endTime" header="结束时间"
						headerAlign="center" width="10%" align="center" allowSort="false"></div>
					<div name="ensureLevel" field="ensureLevel" header="保电级别"
						headerAlign="center" width="10%" align="center" allowSort="false"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 用户单位的window ，相对于整个body的布局-->
	<div id="userofpowWindow" class="ak-window" showHeader="true"
		showCloseButton="true"
		headerStyle="background: white;
		 margin-top: -7px; border-bottom: solid 0px #009694; height: 21px;"
		style="width: 100%; height: 100%" showModal="false" allowDrag="true">
		<div style="height: 100%; width: 100%; margin: 0 auto;">
			<div class="tab">
				<div class="tab_menu">
					<ul>
						<li id="specialLevel" class="selected">特级</li>
						<li id="oneLevel">一级</li>
						<li id="twoLevel">二级</li>
						<li id="threeLevel">三级</li>
					</ul>
				</div>
				<div class="tab_box">
					<div class="select_li_div">
						<div
							style="float: left; width: 100%; height: 100%; background-color: #fff; overflow-y: auto;">
							<div id="userGrid" class="ak-datagrid" allowAlternating="true"
								allowResize="false" pageSize="13" multiSelect="true"
								style="width: 100%; height: 100%;" showVGridLines="false"
								showHGridLines="false" allowAlternating="true"
								allowAlternating="false">
								<div property="columns">
									<div name="userName" field="userName" header="用户名称"
										headerAlign="center" width="10%" align="center"
										allowSort="false"></div>
									<div name="belongOrgName" field="belongOrgName" header="负责小组"
										headerAlign="center" width="10%" align="center"
										allowSort="false"></div>
									<div name="dutyPersonName" field="dutyPersonName" header="负责人"
										headerAlign="center" width="10%" align="center"
										allowSort="false"></div>
									<div name="emergencyPlan" field="emergencyPlan" header="预案"
										headerAlign="center" width="10%" align="center"
										allowSort="false"></div>
									<div name="planInfo" field="planInfo" header="计划明细"
										headerAlign="center" width="10%" align="center"
										allowSort="false"></div>
									<!-- <div name="geographyLocation" field="geographyLocation"
										header="定位" headerAlign="center" width="10%" align="center"
										allowSort="false"></div> -->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="stationorlinewindow" class="ak-window" showHeader="true"
		showCloseButton="true"
		headerStyle="background: white;
		 margin-top: -7px; border-bottom: solid 0px #009694; height: 21px;"
		style="width: 100%; height: 100%" showModal="false" allowDrag="true">
		<div style="height: 100%; width: 100%; margin: 0 auto;">
			<div class="tab">
				<div class="tab_menu">
					<ul id="SLTabs">
						<li id="stationTab" class="selected">电站</li>
						<li id="lineTab">线路</li>
					</ul>
				</div>
				<div class="tab_box">
					<div class="select_li_div">
						<div
							style="float: left; width: 100%; height: 100%; background-color: #fff; overflow-y: auto;">
							<div id="stationLineGrid" pagerButtons="#buttons"
								class="ak-datagrid" style="width: 100%; height: 100%;"
								allowResize="false" idField="id" multiSelect="true"
								sizeList="[15,30,50,100]" pageSize="15" showVGridLines="false"
								showHGridLines="false" allowAlternating="true"
								allowAlternating="false">
								<div property="columns">
									<div headerAlign="center">
										<div property="columns">
											<div name="stationLineName" field="stationLineName"
												header="电站/线路名称" headerAlign="center" width="10%"
												align="center" allowSort="false"></div>
											<div name="ensureLevel" field="ensureLevel" header="保电级别"
												headerAlign="center" width="10%" align="center"
												allowSort="false"></div>
											<div name="ensureLevelnow" field="ensureLevelnow"
												header="当前保电级别" headerAlign="center" width="10%"
												align="center" allowSort="false"></div>
											<div name="voltageLevel" field="voltageLevel" header="电压等级"
												headerAlign="center" width="10%" align="center"
												allowSort="false"></div>
											<div name="orgBelongName" field="orgBelongName" header="负责小组"
												headerAlign="center" width="10%" align="center"
												allowSort="false"></div>
											<div name="dutyPerson" field="dutyPerson" header="负责人"
												headerAlign="center" width="10%" align="center"
												allowSort="false"></div>
											<div name="phoneNumber" field="phoneNumber" header="电话"
												headerAlign="center" width="10%" align="center"
												allowSort="false"></div>
											<!-- <div name="geographyLocation" field="geographyLocation"
												header="定位" headerAlign="center" width="10%" align="center"
												allowSort="false"></div> -->
											<div name="stationLineManage" field="stationLineManage"
												header="站所/线路管理" headerAlign="center" width="10%"
												align="center" allowSort="false"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 资源保障弹出框-->
	<div id="resourcesPro" class="ak-window" showHeader="true"
		showCloseButton="true"
		headerStyle="background: white;
		    border-bottom: solid 0px #009694; margin-top: -7px; height: 21px;"
		style="width: 100%; height: 100%" showModal="false" allowDrag="true">
		<div
			style="height: 100%; width: 100%; background: #FDFFE7; margin: 0 auto;">
			<div id="scrollable">
				<!-- 上一页按钮 -->
				<div class="leftbutton_click"
					style="top: 358px; position: absolute; height: 50px; width: 50px; z-index: 99">
					<img style="position: absolute; left: -12px; width: 50%;"
						src="res/img/left.png">
				</div>
				<!--  外部div使用相对定位，里面的div使用绝对定位 -->
				<div
					style="overflow: hidden; position: relative; visibility: visible; width: 100%; height: 100%">
					<div class="scrollable_demo"
						style="position: relative; height: 100%; width: 100%; left: 0px;">
						<!--  第一页 -->
						<div
							style="height: 100%; width: 100%; position: absolute; background: white;">
							<!-- 人员 -->
							<div style="float: left; width: 50%; height: 100%;">
								<div style="width: 100%; height: 5%;">
									<div
										style="float: left; width: 60%; height: 100%; line-height: 30px;">
										<font style="font-size: 18px; font-weight: 900;"> 资源保障</font>
										<font
											style="color: #009694; font-size: 15px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;人员</font>
									</div>
								</div>
								<div style="width: 100%; height: 95%;">
									<div id="personGrid" class="ak-datagrid"
										style="border: 0px; width: 100%; height: 100%;"
										allowResize="false" multiSelect="true" pageSize="10"
										showVGridLines="false" showHGridLines="false"
										allowAlternating="true" allowAlternating="false">
										<div property="columns">
											<div header=" " headerAlign="center">
												<div property="columns">
													<div name="belongOrgName" field="belongOrgName"
														header="所属小组" headerAlign="center" width="10%"
														align="center" allowSort="false"></div>
													<div name="personName" field="personName" header="姓名"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="type" field="type" header="专业"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="phoneNumber" field="phoneNumber" header="电话"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="organizationName" field="organizationName"
														header="所属单位" headerAlign="center" width="10%"
														align="center" allowSort="false"></div>
													<div name="taskDeclare" field="taskDeclare" header="工作任务"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 中间的分割线 -->
							<div style="float: left; width: 0.1%; height: 100%;">
								<div
									style="margin-top: 10px; float: left; width: 10%; height: 98%; border: #34BEE0 1px solid">
								</div>
							</div>
							<!--  车辆 -->
							<div style="float: right; width: 49.9%; height: 100%;">
								<div style="width: 100%; height: 5%;">
									<div style="float: left; width: 60%; height: 100%;">
										<font
											style="color: #009694; line-height: 29px; font-size: 16px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;车辆</font>
									</div>
								</div>
								<div style="width: 100%; height: 95%;">
									<div id="carGrid" class="ak-datagrid"
										style="border: 0px; width: 100%; height: 100%;"
										allowResize="false" multiSelect="true" pageSize="10"
										showVGridLines="false" showHGridLines="false"
										allowAlternating="true" allowAlternating="false">
										<div property="columns">
											<div header=" " headerAlign="center">
												<div property="columns">
													<div name="belongOrgName" field="belongOrgName"
														header="所属小组" headerAlign="center" width="10%"
														align="center" allowSort="false"></div>
													<div name="brandNumber" field="brandNumber" header="车牌号"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="type" field="type" header="车辆类型"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="driver" field="driver" header="驾驶员"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="phoneNumber" field="phoneNumber" header="电话"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="taskDeclare" field="taskDeclare" header="任务说明"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--  第二  页 -->
						<div
							style="height: 100%; width: 100%; position: absolute; left: 100%; background: white;">
							<!-- 驻点 -->
							<div style="float: left; width: 33.3%; height: 100%;">
								<div style="width: 100%; height: 5%;">
									<div
										style="float: left; width: 60%; height: 100%; line-height: 30px;">
										<font style="font-size: 18px; font-weight: 900;"> 资源保障</font>
										<font
											style="color: #009694; font-size: 15px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;驻点</font>
									</div>
								</div>
								<div style="width: 100%; height: 95%;">
									<div id="pointGrid" class="ak-datagrid"
										style="border: 0px; width: 100%; height: 100%;"
										allowResize="false" multiSelect="true" pageSize="10"
										showVGridLines="false" showHGridLines="false"
										allowAlternating="true" allowAlternating="false">
										<div property="columns">
											<div header=" " headerAlign="center">
												<div property="columns">
													<div name="belongOrgName" field="belongOrgName"
														header="所属小组" headerAlign="center" width="10%"
														align="center" allowSort="false"></div>
													<div name="stationName" field="stationName" header="驻点名称"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="dutyPerson" field="dutyPerson" header="负责人"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="phoneNumber" field="phoneNumber" header="联系电话"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="taskDeclare" field="taskDeclare" header="工作任务"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<!-- <div name="location" field="location" header="定位"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div> -->
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 中间的分割线 -->
							<div style="float: left; width: 0.1%; height: 100%;">
								<div
									style="margin-top: 10px; float: left; width: 1%; height: 98%; border: #34BEE0 1px solid">
								</div>
							</div>
							<!-- 医院 -->
							<div style="float: right; width: 33.3%; height: 100%;">
								<div style="width: 100%; height: 5%;">
									<div style="float: left; width: 60%; height: 100%;">
										<font
											style="color: #009694; line-height: 29px; font-size: 16px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;医院</font>
									</div>
								</div>
								<div style="width: 100%; height: 95%;">
									<div id="hospitalGrid" class="ak-datagrid"
										style="border: 0px; width: 100%; height: 100%;"
										allowResize="false" multiSelect="true" pageSize="10"
										showVGridLines="false" showHGridLines="false"
										allowAlternating="true" allowAlternating="false">
										<div property="columns">
											<div header=" " headerAlign="center">
												<div property="columns">
													<div name="hospitalName" field="hospitalName" header="医院名称"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="linkman" field="linkman" header="联系人"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="phoneNumber" field="phoneNumber" header="联系电话"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<!-- <div name="location" field="location" header="定位"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div> -->
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 中间的分割线 -->
							<div style="float: right; width: 0.1%; height: 100%;">
								<div
									style="margin-top: 10px; float: left; width: 10%; height: 98%; border: #34BEE0 1px solid">
								</div>
							</div>
							<!-- 宾馆 -->
							<div style="float: right; width: 33.2%; height: 100%;">
								<div style="width: 100%; height: 5%;">
									<div style="float: left; width: 60%; height: 100%;">
										<font
											style="color: #009694; line-height: 29px; font-size: 16px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;宾馆</font>
									</div>
								</div>
								<div style="width: 100%; height: 95%;">
									<div id="hotelGrid" class="ak-datagrid"
										style="border: 0px; width: 100%; height: 100%;"
										allowResize="false" multiSelect="true" pageSize="10"
										showVGridLines="false" showHGridLines="false"
										allowAlternating="true" allowAlternating="false">
										<div property="columns">
											<div header=" " headerAlign="center">
												<div property="columns">
													<div name="hotelName" field="hotelName" header="宾馆名称"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="linkman" field="linkman" header="联系人"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<div name="phoneNumber" field="phoneNumber" header="联系电话"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
													<!-- <div name="location" field="location" header="定位"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div> -->
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 下一页按钮 -->
				<div class="rightbutton_click"
					style="height: 50px; width: 50px; right: 17px; top: 358px; position: absolute;">
					<img style="position: absolute; right: -15px; width: 50%;"
						src="res/img/right.png">
				</div>
			</div>
		</div>
	</div>

	<!-- 缺陷弹窗 -->
	<div id="defectWindow" class="ak-window" title="故障" showHeader="true"
		showCloseButton="true"
		headerStyle="background: white;
	    border-bottom: solid 0px #009694; margin-top: -7px; height: 21px;"
		style="width: 100%; height: 100%" showModal="false" allowDrag="true">
		<div id="defectGrid" class="ak-datagrid"
			style="width: 100%; height: 100%" allowResize="false" idField="id"
			multiSelect="true" pageSize="10" url="" showVGridLines="false"
			showHGridLines="false" allowAlternating="true" allowCellWrap="true">
			<div property="columns">
				<div headerAlign="center">
					<div property="columns">
						<div type="checkcolumn"></div>
						<div field="discoveredDate" width="7%" headerAlign="center"
							align="center" dateFormat="yyyy-MM-dd HH:mm:ss">发现日期</div>
						<div field="belongCity" width="11%" headerAlign="center"
							align="center">所属地市</div>
						<div field="powerStation" width="7%" headerAlign="center"
							align="center">线路/站</div>
						<div field="defectMainEquipment" width="7%" headerAlign="center"
							align="center">缺陷主设备</div>
						<div field="voltageClass" width="7%" headerAlign="center"
							align="center">电压等级</div>
						<div field="defectProperties" width="7%" headerAlign="center"
							align="center">缺陷性质</div>
						<div field="defectContent" width="7%" headerAlign="center"
							align="center">缺陷内容</div>
						<div field="defectState" width="7%" headerAlign="center"
							align="center">缺陷状态</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 故障弹窗 -->
	<div id="faultWindow" class="ak-window" title="故障" showHeader="true"
		showCloseButton="true"
		headerStyle="background: white;
	    border-bottom: solid 0px #009694; margin-top: -7px; height: 21px;"
		style="width: 100%; height: 100%" showModal="false" allowDrag="true">
		<div id="faultGrid" class="ak-datagrid"
			style="width: 100%; height: 100%" allowResize="false" idField="id"
			multiSelect="true" pageSize="10" url="" showVGridLines="false"
			showHGridLines="false" allowAlternating="true" allowCellWrap="true">
			<div property="columns">
				<div headerAlign="center">
					<div property="columns">
						<div type="checkcolumn"></div>
						<div field="faultTime" width="10%" headerAlign="center"
							align="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">故障时间</div>
						<div field="organization" width="10%" headerAlign="center"
							align="center" allowSort="true">所属单位</div>
						<div field="stationOrLineName" width="10%" headerAlign="center"
							align="center" allowSort="true">线路/站</div>
						<div field="deviceName" width="10%" headerAlign="center"
							align="center" allowSort="true">设备名称</div>
						<div field="voltageLevel" width="10%" headerAlign="center"
							align="center" allowSort="true">电压等级</div>
						<div field="faultPhaseId" width="10%" headerAlign="center"
							align="center" allowSort="true">故障相别/极号</div>
						<div field="faultType" width="10%" headerAlign="center"
							align="center" allowSort="true">故障类型</div>
						<div field="faultDetail" width="12%" headerAlign="center"
							align="center" allowSort="true">故障详情</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 运行工况二级页面 -->
	<div id="run_workinfopro" class="ak-window" title="运行工况"
		showHeader="true" showCloseButton="true"
		headerStyle="background: white;
	    border-bottom: solid 0px #009694; margin-top: -7px; height: 21px;"
		style="width: 100%; height: 100%" showModal="false" allowDrag="true">

		<div class="tab">
			<div class="tab_menu_info">
				<ul>
					<li id="qxweather" class="selected">气象</li>
					<li id="hj">环境</li>
					<li id="dwfx">电网风险</li>
					<li id="zgz">重过载</li>
					<li id="zxjc">在线监测</li>
				</ul>
			</div>
			<div class="tab_box_info">

				<div id="weatherGrid" class="ak-datagrid" style="width:100%;height:100%" allowResize="false" 
				         idField="id" multiSelect="true" pageSize="20" url=""
				        showVGridLines="false" showHGridLines="false" allowAlternating="true" allowCellWrap ="true">
					   <div property="columns">   
			           <div  headerAlign="center">
		               <div property="columns" style="">
		               	<div type="checkcolumn"></div>  
	   					<div type="indexcolumn">序号</div>  
   						<div field="id" width="7%" headerAlign="center" align ="center"  visible="flase">id</div>
		           		<div field="startTime" width="9%" headerAlign="center" align ="center" dateFormat="yyyy-MM-dd HH:mm:ss">起报时间</div>
		           		<div field="effectivePeriod" width="7%" headerAlign="center" align ="center" >有效时段</div>
		           		<div field="line" width="8%" headerAlign="center" align ="center" >影响线路（回）</div>
		           		<div field="station" width="8%" headerAlign="center" align ="center" >影响电站（座）</div>
		           		<div field="meteorologicalType" width="7%" headerAlign="center" align ="center" >气象类型</div>
		           		<div field="content" width="12%" headerAlign="center" align ="center" >内容</div>
		           		<div field="remark" width="12%" headerAlign="center" align ="left" >备注</div>
		               </div>
		           </div> 
       			</div>
			   </div>	



				<div id="environmentGrid" pagerButtons="#buttons"
					class="ak-datagrid  "
					style="display: none; width: 100%; height: 100%;"
					allowResize="false" idField="id" multiSelect="true"
					sizeList="[15,30,50,100]" pageSize="15" showVGridLines="false"
					showHGridLines="false" allowAlternating="true"
					allowAlternating="false">
					<div property="columns">
						<div headerAlign="center">
							<div property="columns" style="width: 100%; height: 100%;">
								<div type="checkcolumn"></div>
								<div type="indexcolumn">序号</div>
								<div field="id" width="7%" headerAlign="center" align="center"
									visible="flase">id</div>
								<div field="startTime" width="9%" headerAlign="center"
									align="center" dateFormat="yyyy-MM-dd HH:mm:ss">起报时间</div>
								<div field="effectivePeriod" width="7%" headerAlign="center"
									align="center">有效时段</div>
								<div field="line" width="8%" headerAlign="center" align="center">影响线路（回）</div>
								<div field="station" width="8%" headerAlign="center"
									align="center">影响电站（座）</div>
								<div field="meteorologicalTypeName" width="7%"
									headerAlign="center" align="center">气象类型</div>
								<div field="content" width="12%" headerAlign="center"
									align="center">内容</div>
								<div field="remark" width="12%" headerAlign="center"
									align="left">备注</div>
							</div>
						</div>
					</div>
				</div>
				 
				<div id="gridRiskDataGrid" class="ak-datagrid" style="width:100%;height:100%" allowResize="false" 
				         idField="id" multiSelect="true" pageSize="20" url=""
				        showVGridLines="false" showHGridLines="false" allowAlternating="true" allowCellWrap ="true">
				   <div property="columns">   
		           <div  headerAlign="center">
		               <div property="columns" style="">
		               	<div type="checkcolumn"></div>  
		   					<div type="indexcolumn">序号</div>  
			           		<div field="createDate" width="9%" headerAlign="center" align ="center"  dateFormat="yyyy-MM-dd HH:mm">建档时间</div>
			           		<div field="warnOriginName" width="9%" headerAlign="center" align ="center"  >预警来源</div>
			           		<div field="warnNum" displayField ="warnNum" width="7%" headerAlign="center" align ="center" >预警单编号</div>
			           		<div field="warnLevelName" width="9%" headerAlign="center" align ="center" >预警级别</div>
			           		<div field="publishDate" width="9%" headerAlign="center" align ="center"  dateFormat="yyyy-MM-dd HH:mm">预警开始时间</div>
			           		<div field="RemoveDate" width="9%" headerAlign="center" align ="center"  dateFormat="yyyy-MM-dd HH:mm">预警解除时间</div>
			           		<div field="maintenanceUnitName" width="9%" headerAlign="center" align ="center" >运维单位</div>  
			           		<div field="powercutEquipmentOrLine" width="9%" headerAlign="center" align ="center" >停电设备/线路</div> 
			           		<div field="focusEquipmentOrLine" width="9%" headerAlign="center" align ="center" >重点关注设备/线路</div>   
			           		<div field="WarningAnalysis" width="9%" headerAlign="center" align ="center" allowSort="false">风险分析</div> 
			           		<div field="precaution" width="9%" headerAlign="center" align ="center" allowSort="false">预控措施</div>
		               </div>
		           </div> 
       			</div>
			   </div>	

				<div id="overLoadDataGrid" class="ak-datagrid" style="width:100%;height:100%" allowResize="false" 
				         idField="id" multiSelect="true" pageSize="20" url=""
				        showVGridLines="true" showHGridLines="true" allowAlternating="true" allowCellWrap ="true" >
				 	<div property="columns">   
			           <div  headerAlign="center">
			               <div property="columns" style=""> 
				           		<div type="checkcolumn"></div>  
			   					<div type="indexcolumn">序号</div>
			   					<div field="id" width="7%" headerAlign="center" align ="center"  visible="flase">id</div>
				           		<div field="startTime" width="7%" headerAlign="center" align ="center" dateFormat="yyyy-MM-dd HH:mm:ss">开始时间</div>
				           		<div field="duration" width="8%" headerAlign="center" align ="center" >持续时间</div>
				           		<div field="recoveryDate" width="7%" headerAlign="center" align ="center" dateFormat="yyyy-MM-dd HH:mm:ss">恢复时间</div>
				           		<div field="maintenanceUnitName" width="7%" headerAlign="center" align ="center" >运维单位</div>
				           		<div field="stationOrLineName" width="7%" headerAlign="center" align ="center" >线路/站</div>
				           		<div field="deviceName" width="7%" headerAlign="center" align ="center" >设备名称</div>
				           		<div field="voltageLevel" width="7%" headerAlign="center" align ="center" >电压等级</div>
				           		<div field="ratedCapacity" width="9.5%" headerAlign="center" align ="center" >额定值(MW/MVA)</div>
				           		<div field="currentLoad" width="7.2%" headerAlign="center" align ="center" >当前负荷(MW)</div>
				           		<div field="loadRate" width="7%" headerAlign="center" align ="center" >当前负载率</div>
				           		<div field="maxLoadRate" width="7%" headerAlign="center" align ="center" >最大负载率</div>	
						      	<div field="onlineMonitoringCnt" width="5%" headerAlign="center" align ="center" >在线监测</div>
				           		<div field="weatherCnt" width="5%" headerAlign="center" align ="center" >气象环境</div>
				           		<div field="gridRiskCnt" width="5%" headerAlign="center" align ="center" >电网风险</div>
				           		<div field="defectCnt" width="4%" headerAlign="center" align ="center" >缺陷</div>
				           		<div field="electricityEnsureCnt" width="4%" headerAlign="center" align ="center" >保电</div>						           									           		
			               </div>
			           </div> 
	       			</div>
			    </div>	

				<div id="monitoringDataGrid" class="ak-datagrid" style="width:100%;height:100%" allowResize="false" 
				         idField="id" multiSelect="true" pageSize="20" url=""
				        showVGridLines="false" showHGridLines="false" allowAlternating="true" allowCellWrap ="true">
				   <div property="columns">   
		           <div  headerAlign="center">
		               <div property="columns" style="">
		               	<div type="checkcolumn"></div>  
	   					<div type="indexcolumn">序号</div>  
		   					<div field="id" width="7%" headerAlign="center" align ="center"  visible="flase">id</div>
			           		<div field="startTime" width="7%" headerAlign="center" align ="center" dateFormat="yyyy-MM-dd HH:mm:ss">开始时间</div>
			           		<div field="duration" width="7%" headerAlign="center" align ="center" >持续时间</div>
			           		<div field="recoveryDate" width="7%" headerAlign="center" align ="center" dateFormat="yyyy-MM-dd HH:mm:ss">恢复时间</div>
			           		<div field="maintenanceUnitName" width="7%" headerAlign="center" align ="center" >运维单位</div>
			           		<div field="type" width="7%" headerAlign="center" align ="center" >专业</div>
			           		<div field="stationOrLineName" width="7%" headerAlign="center" align ="center" >线路/站</div>
			           		<div field="deviceName" width="7%" headerAlign="center" align ="center" >设备名称</div>
			           		<div field="phase" width="7%" headerAlign="center" align ="center" >相别</div>
			           		<div field="voltageLevel" width="7%" headerAlign="center" align ="center" >电压等级</div>
			           		<div field="monitoringType" width="10%" headerAlign="center" align ="center" >监测类型</div>
			           		<!-- <div field="forecastAlarm" width="7%" headerAlign="center" align ="center" >预警/报警</div> -->
			           		<div field="forecastAlarmContent" width="15%" headerAlign="center" align ="center" >报警内容</div>
			           		<div field="monitoringDeviceType" width="7%" headerAlign="center" align ="center" >监测装置型号</div>
			           		<div field="manufacturerName" width="7%" headerAlign="center" align ="center" >厂家名称</div>
		               </div>
		           </div> 
       			</div>
			   </div>	

			</div>
		</div>

	</div>
</body>
</html>
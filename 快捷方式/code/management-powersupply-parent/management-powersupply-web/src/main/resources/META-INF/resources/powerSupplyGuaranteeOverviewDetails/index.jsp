<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<ak:framework />
<title>保电概览</title>
<link href="res/css/index.css" rel="stylesheet" type="text/css" />
</head>
<body style="background-color: #383F73">
<div class="ak-fit" style="overflow: hidden;">
	<div class="ak-splitter" vertical="true" allowResize="false"
		handlerSize="6" style="width: 100%; height: 100%;">
		<!-- 总体布局上和中 -->
		<div size="71%" showCollapseButton="false">
			<div class="ak-splitter" vertical="true" allowResize="false"
				handlerSize="6" style="width: 100%; height: 100%;">
				<!-- 总体布局上 -->
				<div size="16.8%" showCollapseButton="false">
					<div style="width: 100%; height: 100%; background-color:;">
						<!-- g20切换 -->
						<div
							style="float: left; width: 5.5%; height: 100%; background-color: #EB9F0D;">
							<div style="height: 40%; width: 100%; line-height: 53px; text-align:center; color:white;
								text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">
								<font id="theme" style="font-size:16px; color:white;"></font>
							</div>
							<div id="ensureLevel" style="height:30%; width:100%; font-size:14px; color:white; text-align:center;line-height:38px;"></div>
							<div id="downorup" style="height: 30%; width: 100%; text-align: center;">
								<img src="./res/img/down-w.png" />
							</div>
						</div>
						<!-- 组织保障 -->
						<div
							style="float: left; width: 94.5%; height: 100%; background-color: rgba(255, 255, 255, 0.1);">
							<!-- 组织保障 -->
							<div
								style="display: flex; float: left; width: 6%; height: 100%; background-color:; text-align: center;">
								<div
									style="font-size: 16px; color: white; margin: auto; width: 50%; height: 50%">组织保障</div>
							</div>
							<!-- 动态加载树 -->
							<div style="width: 90%; height: 100%; float: left; overflow: hidden;">
								<div id="orgList" style="width:2374px; height:100%;">
									<div id="hq" style="height:32px; margin:30px 0; font-size:16px; 
										background:rgba(255, 255, 255, 0.2); cursor:pointer; float:left;">
										<div style="text-align: center; line-height: 32px; padding: 0 20px; color:white; max-width: 150px; 
											text-overflow: ellipsis; white-space: nowrap; overflow: hidden; float: left;">
											<span id="headquarters" style="color: white;"></span>
										</div>
										<div
											style="width: 30px; text-align: center; line-height: 32px; float: right;">
											<img src="./res/img/youjiantou-tou.png">
										</div>
									</div>
								</div>
							</div>
							<div style="display: flex; float: left; width: 4%; height: 100%; text-align: center; z-in">
								<div style="margin: auto;">
									<img id="moreOrgs" style="cursor: pointer;"
										src="./res/img/shuangjiantou.png">
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 总体布局中 -->
				<div size="83.2%" showCollapseButton="false">
					<div style="width: 100%; height: 100%; background-color:;">
						<div class="ak-splitter" vertical="false" allowResize="false"
							handlerSize="6" style="width: 100%; height: 100%;">
							<!--时间计划 -->
							<div size="40%" showCollapseButton="false">
								<!-- 保电主题 -->
								<div id="menutheme" class="hidden"
									style="z-index: 999999; position: absolute; width: 40.5%; background-color: rgba(0, 0, 0, 0.6);">
									<ul id="tasksList" style="font-size:16px; padding:5px;">
										<!-- <li><div>复奉保电</div></li>
										<li><div>安塘保电</div></li>
										<li><div>全国重要会议保电</div></li> -->
									</ul>
								</div>

								<div id="thirdOrgList" class="hidden"
									style="z-index: 999999; position: absolute; background-color: rgba(0, 0, 0, 0.6);">
									<ul id="thirdOrgUl" style="color: white; font-size: 16px; margin: 5px; list-style-type: none;"></ul>
								</div>

								<div id="jianjie" class=""
									style="display: none; position: absolute; width: 100%; height: 100%; background-color: rgba(25, 159, 213, 0.4);">
									<div id="brief_intro"
										style="position: absolute; right: 5px; width: 57px; height: 29px; text-align: center; color: white;  cursor:pointer;
										line-height: 28px; font-size: 18px; background-color: #EB9F0D; margin-right: 0px; margin-top: 5px;">简介</div>
									<div
										style="width: 100%; height: 100%; background-color: rgba(5, 125, 197, 0.2);">
										<!-- 浮动 class="F-box"-->
										<div style="height: 100%; width: 100%">
											<iframe name="myIframe_2D" id="iframe_2D" src="/mcsasWebgis2d/2dmap.html"
												style="width: 100%; height: 100%; border: none;"></iframe>
										</div>
										<!-- /mcsasWebgis2d/2dmap.html or http://10.144.139.45:8090/2dmap.html-->
									</div>
								</div>
								<!-- 时间计划日历，保电简介，组织信息 -->
								<div id="tim_pow_org"
									style="width: 100%; height: 100%; background-color: white;">
									<!-- 时间计划日历 -->
									<div style="height: 51%; width: 100%">
										<!-- 时间计划和gis -->
										<div style="height: 13%; width: 100%">
											<div
												style="padding-left: 10px; line-height: 39px; float: left; height: 100%; width: 45%">
												<font style="font-size: 18px; font-weight: bold;">时间计划</font>
											</div>
											<div
												style="line-height: 35px; float: right; height: 100%; width: 50%">
												<div id="gis"
													style="float: right; width: 57px; height: 29px; text-align: center; color: white; cursor:pointer;
													line-height: 31px; font-size: 18px; background-color: #EB9F0D; margin-right: 5px; margin-top: 5px;">GIS</div>
											</div>
										</div>
										<!-- 日历 -->
										<div style="height: 87%; width: 100%">
											<div style="float: left; height: 100%; width: 49.8%">
												<div style="margin-left: 10px; border-width: 0 0 0 0px; border-style: solid; border-color: black;">
													<table style="width: 98%; height: 10%; background:; border-radius: 20px 20px 0 0;">
														<tbody>
															<tr>
																<td id="lastMonth" class="align-c"
																	style="width: 14.3%; font-weight: bold; cursor: pointer;"><</td>
																<td colspan="5" id="dateFirst" class="align-c"
																	style="width: 71.4%; font-weight: bold;"></td>
																<td class="align-c"
																	style="width: 14.3%; font-weight: bold;"></td>
															</tr>
															<tr>
																<td class="align-c" style="width: 14.3%;">一</td>
																<td class="align-c" style="width: 14.3%;">二</td>
																<td class="align-c" style="width: 14.3%;">三</td>
																<td class="align-c" style="width: 14.3%;">四</td>
																<td class="align-c" style="width: 14.3%;">五</td>
																<td class="align-c" style="width: 14.3%;">六</td>
																<td class="align-c" style="width: 14.3%;">日</td>
															</tr>
														</tbody>
													</table>
													<table
														style="width: 100%; height: 50%; background: rgba(255, 255, 255, 0.3); border-radius: 0 0 20px 20px;">
														<tbody>
															<tr>
																<td id="calendarFirst"></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<!-- 中间的虚线在此不准出墙 -->
											<div style="float: left; height: 100%; width: 0.04%">
												<div
													style="height: 100%; width: 50%; border: 1px solid black">
												</div>
											</div>
											<!-- 下一个月的日历 -->
											<div style="float: left; height: 100%; width: 49.8%">
												<div style="margin-left: 10px; border-width: 0 0 0 0px; border-style: solid; border-color: black;">
													<table style="width: 98%; height: 10%; background:; border-radius: 20px 20px 0 0;">
														<tbody>
															<tr>
																<td class="align-c"
																	style="width: 14.3%; font-weight: bold;">
																</td>
																<td colspan="5" id="dateSecond" class="align-c"
																	style="width: 71.4%; font-weight: bold;"></td>
																<td id="nextMonth" class="align-c"
																	style="width: 14.3%; font-weight: bold; cursor: pointer;">></td>
															</tr>
															<tr>
																<td class="align-c" style="width: 14.3%;">一</td>
																<td class="align-c" style="width: 14.3%;">二</td>
																<td class="align-c" style="width: 14.3%;">三</td>
																<td class="align-c" style="width: 14.3%;">四</td>
																<td class="align-c" style="width: 14.3%;">五</td>
																<td class="align-c" style="width: 14.3%;">六</td>
																<td class="align-c" style="width: 14.3%;">日</td>
															</tr>
														</tbody>
													</table>
													<table
														style="width: 100%; height: 50%; background: rgba(255, 255, 255, 0.3); border-radius: 0 0 20px 20px;">
														<tbody>
															<tr>
																<td id="calendarSecond"></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<!--  保电简介 -->
									<div style="height: 15%; width: 100%; margin-top:10px;">
										<div style="display: flex; float: left; width: 18%; height: 100%;">
											<div style="width: 80%; height: 100%; margin: auto;">
												<font style="font-size: 18px; font-weight: 700;">保电简介</font>
											</div>
										</div>
										<div style="float: left; width: 18%; height: 100%;">
											<div id="briefPhoto" style="height: 100%; width: 88%; margin: auto; 
												background: url(res/img/no-jianjietu-black.png) no-repeat center; background-size: 98%; margin-right: 19px;">
											</div>
										</div>
										<div style="float: left; width: 64%; height: 100%;">
											<div id="briefText" style="text-indent:25px; height:90px; overflow-y:auto;"></div>
										</div>
									</div>
									<!--  组织信息 -->
									<div style="height: 34%; width: 100%">
										<div style="height: 10%; width: 90%;">
											<font style="margin-left: 15px; font-size: 18px; font-weight: 700;">组织信息</font>
										</div>
										<div style="display: flex; height: 90%; width: 100%;">
											<div style="width: 96%; height: 80%; margin: auto; background-color: rgba(180, 180, 181, 0.2);">
												<div style="display: flex; float: left; width: 14%; height: 98%; background-color:;">
													<div id="dutyPersonPhoto" style="width:90%;
														background: url(res/img/no-person-black.png) no-repeat center; background-size:100%;">
													</div>
												</div>
												<div style="display: flex; float: left; width: 30%; height: 100%;">
													<div style="height: 85%; width: 98%; margin: auto; text-overflow:ellipsis; white-space:nowrap; overflow:hidden;">
														主要负责人：<span id="dutyPerson"></span><br/>
														联系方式：<span id="phoneNumber"></span><br/>
														工作电话：<span id="contactNumber"></span><br/>
														<img style="width:18px; margin-right:6px;" src="res/img/fujian.png">附件：
														<span id="ensureNotice" style="cursor:pointer;">保电方案</span>
													</div>
												</div>
												<div style="display: flex; float: left; width: 56%; height: 100%;">
													<div style="width:100%; margin: auto;">
														<font id="orgName" style="font-size: 16px;"></font>
														<p id="orgBriefText" style="text-indent:25px; height:80px; overflow-y:auto;"></p>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 右面部分，可以再分保电范围和资源保障 -->
							<div size="60%" showCollapseButton="false">
								<div class="ak-splitter" vertical="false" allowResize="false"
									handlerSize="6" style="width: 100%; height: 100%;">
									<!-- 保电范围 和站线-->
									<div size="52.7%" showCollapseButton="false">
										<div
											style="width: 100%; height: 100%; background-color: white;">
											<!-- 保电范围 -->
											<div
												style="height: 40%; width: 93%; margin: 0px 15px 15px 15px; background-color:;">
												<div style="line-height: 39px; height: 17%; width: 45%">
													<font style="font-size: 18px; font-weight: bold;">保电范围</font>
												</div>
												<div style="margin-top: 15px; height: 83%; width: 100%">
													保电用户(单位：个)
													<!-- 用户 -->
													<div style="width: 100%; height: 68%; display: flex;">
														<!-- 图片 -->
														<div
															style="width: 10%; text-align: center; margin: auto; float: left;">
															<img src="res/img/yonghu.png" style="width: 85%;" /> <span>用户</span>
														</div>

														<!-- 彩条 -->
														<div
															style="float: left; height: 100%; width: 90%; background-color:">
															<div id="userOfpow_guar"
																style="text-align: center; line-height: 44px; height: 100%; width: 100%; background-color:">
																<!--    单位：个  
														 		<div style="  position:absolute;  text-align: center;line-height: 86px; height:36px;width:30px;background-color: ">
														 		    单位：个
														 		</div> -->
																<div style="line-height: 44px; height: 45%; width: 100%">
																	<div id="user1"
																		style="float: left; height: 100%; width: 24%">0</div>
																	<div id="user2"
																		style="float: left; height: 100%; width: 25%">0</div>
																	<div id="user3"
																		style="float: left; height: 100%; width: 25%">0</div>
																	<div id="user4"
																		style="float: left; height: 100%; width: 25%">0</div>
																</div>
																<div style="margin: auto; line-height: 5px; height: 6%; width: 100%">
																	<div
																		style="float: left; height: 100%; width: 24%; background-color: rgb(251, 63, 88)">
																	</div>
																	<div
																		style="float: left; height: 100%; width: 25%; background-color: rgb(235, 159, 9)">
																	</div>
																	<div
																		style="float: left; height: 100%; width: 25%; background-color: rgb(169, 143, 232)">
																	</div>
																	<div
																		style="float: left; height: 100%; width: 25%; background-color: rgb(1, 150, 149)">
																	</div>
																</div>
																<div style="line-height: 38px; height: 45%; width: 100%">
																	<div style="float: left; height: 100%; width: 24%">特级</div>
																	<div style="float: left; height: 100%; width: 25%">一级</div>
																	<div style="float: left; height: 100%; width: 25%">二级</div>
																	<div style="float: left; height: 100%; width: 25%">三级</div>
																</div>
															</div>
														</div>

													</div>
												</div>
											</div>
											<!-- 分割线 -->
											<div style="display: flex; height: 0.1%; width: 100%">
												<div
													style="margin: auto; height: 50%; width: 90%; border: 1px solid rgb(52, 190, 224);">
												</div>
											</div>
											<!-- 保电站线 -->
											<div style="height: 49.9%; width: 93%; margin: 15px;">
												<!-- 线路和电站-->
												<div id="stationorline" style="width: 100%; height: 99.5%;">
													保电站线(单位：站【座】,线【条】)
													<!-- 线路 -->
													<div style="height: 50%; width: 100%; display: flex;">
														<div
															style="width: 10%; text-align: center; margin: auto; float: left;">
															<img src="res/img/xianlu.png" style="width: 80%;" /> <span>线路</span>
														</div>

														<!-- echart -->
														<div
															style="float: left; height: 100%; width: 90%; background-color:">
															<div id="lineChart" class="ak-echarts"
																style="width: 100%; height: 100%;"></div>
														</div>
													</div>
													<!-- 电站 -->
													<div style="height: 50%; width: 100%; display: flex;">
														<div
															style="width: 10%; text-align: center; margin: auto; float: left;">
															<img src="res/img/dianzhan.png" style="width: 80%;" /> <span>电站</span>
														</div>

														<!-- echart -->
														<div
															style="float: left; height: 100%; width: 90%; background-color:">
															<div id="stationChart" class="ak-echarts"
																style="width: 100%; height: 100%;"></div>

														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- 资源保障 -->
									<div size="47.3%" showCollapseButton="false">
										<div id="resourcesprotect"
											style="width: 100%; height: 100%; background-color: white;">
											<!-- 资源保障-->
											<div
												style="height: 51%; width: 93%; margin: 0px 15px 15px 15px; background-color:;">
												<div style="line-height: 39px; height: 17%; width: 45%">
													<font style="font-size: 18px; font-weight: bold;">资源保障</font>
												</div>
												<div style="height: 83%; width: 100%">
													<div style="float: left; height: 100%; width: 50%">
														<div
															style="text-align: center; float: left; height: 15%; width: 100%">
															人员&nbsp;&nbsp;<font id="personCount"
																style="font-size: 18px;">0</font>&nbsp;&nbsp;人
														</div>
														<div style="float: left; height: 85%; width: 100%">
															<div id="personChart" class="ak-echarts"
																style="width: 100%; height: 100%;"></div>
														</div>
													</div>
													<div style="float: left; height: 100%; width: 50%">
														<div
															style="text-align: center; float: left; height: 15%; width: 100%">
															车辆&nbsp;&nbsp;<font id="carCount"
																style="font-size: 18px;">0</font>&nbsp;&nbsp;辆
														</div>
														<div style="float: left; height: 85%; width: 100%">
															<div id="carChart" class="ak-echarts"
																style="width: 100%; height: 100%;"></div>	
														</div>
														<div>
							 									<div id="wtly_zkxq" class="zkxq" style="display:none">展开详情</div>
							 									<div id="wtly_datagrid" class="ak-datagrid" pageSize="10"
							 										showPager="false" allowAlternating="true"
							 										style="width: 97%; height: 50%; display: none;position: absolute;top: 120px;right: 10px;left:1px;opacity:0.8; background: white;background-color:white;z-index: 2;overflow: auto; ">
							 										<div property="columns" >
							 											<div type="indexcolumn" width="9%" headerAlign="center"
							 												align="center">序号</div>
							 											<div field="btmc" width="55%" headerAlign="center"
							 												align="center">标题名称</div>
							 											<div field="cnt" width="15%"
							 												headerAlign="center" align="center">数量</div>
							 											<div field="percent" width="16%" headerAlign="center"
							 												align="center">占比</div>
							 										</div>
								
							 									</div>
						 									</div>	
													</div>
												</div>
											</div>
											<!-- 分割线 -->
											<div style="display: flex; height: 0.1%; width: 100%">
												<div
													style="margin: auto; height: 50%; width: 90%; border: 1px solid rgb(52, 190, 224);">
												</div>
											</div>
											<!-- 驻点宾馆医院-->
											<div
												style="height: 38.9%; width: 93%; margin: 15px 15px 15px 15px; background-color:;">
												<!-- 驻点 -->
												<div style="float: left; height: 50%; width: 50%">
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
															<font id="pointCount" style="font-size: 25px;">0</font> 个
														</div>
													</div>
												</div>
												<!-- 宾馆 -->
												<div style="float: left; height: 50%; width: 50%">
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
															<font id="hotelCount" style="font-size: 25px;">0</font> 个
														</div>
													</div>
												</div>
												<!--  医院 -->
												<div style="float: left; height: 50%; width: 50%">
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
															<font id="hospitalCount" style="font-size: 25px;">0</font>
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
		<div size="29%" showCollapseButton="false" class="swiper-container">
			<!-- 资源保障-->
			<div style="width: 100%; height: 100%; background-color: white;">
				<!--  气象信息 -->
				<div
					style="float: left; height: 95%; width: 25%; margin: 0px 15px 15px 15px; background-color:;">
					<div style="line-height: 39px; height: 17%; width: 45%">
						<font style="font-size: 18px; font-weight: bold;">运行监控</font>
						<font style="font-size: 16px; margin-left:10px;">气象信息</font>
						
					</div>
					<div style="line-height: 39px;; height: 83%; width: 100%">
						<div style="height: 50%; width: 100%">
							<div
								style="text-align: center; line-height: 82px; float: left; background-color:; height: 100%; width: 16%">
								<font
									style="color: #F1AA22; font-size: 13px; font-weight: bold;">24小时</font>
							</div>
							<div
								style="float: left; background-color:; height: 100%; width: 21%">
								<div
									style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
									<div
										style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/rain.png) no-repeat center; background-position-x: 33.5%; background-position-y: 0px; background-color: #34BEE0;">
									</div>
								</div>
								<div id="weather_24" style="width: 100%; height: 40%; text-align: center;">--</div>
							</div>
							<div
								style="float: left; background-color:; height: 100%; width: 21%">
								<div
									style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
									<div
										style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/C.png) no-repeat center; background-position-x: 33.5%; background-position-y: -2px;; background-color: #34BEE0;">
									</div>
								</div>
								<div style="width: 100%; height: 40%; text-align: center; line-height: 12px;">
									温度<br /><span id="temperature_24">--℃</span>
								</div>
							</div>
							<div
								style="float: left; background-color:; height: 100%; width: 21%">
								<div
									style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
									<div
										style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/wind.png) no-repeat center; background-position-x: -4px; background-position-y: -2px; background-color: #34BEE0;">
									</div>
								</div>
								<div style="width: 100%; height: 40%; text-align: center; line-height: 12px;">
									风速<br /><span id="wind_24">--</span>
								</div>
							</div>
							<div
								style="float: left; background-color:; height: 100%; width: 21%">
								<div
									style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
									<div
										style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/water.png) no-repeat center; background-position-x: -5px; background-position-y: -3px; background-color: #34BEE0;">
									</div>
								</div>
								<div
									style="width: 100%; height: 40%; text-align: center; line-height: 12px;">
									湿度<br /><span id="humidity_24">--%RH</span>
								</div>
							</div>
						</div>
						<div style="height: 50%; width: 100%">
							<div
								style="text-align: center; line-height: 82px; float: left; background-color:; height: 100%; width: 16%">
								<font
									style="color: #F1AA22; font-size: 13px; font-weight: bold;">48小时</font>
							</div>
							<div
								style="float: left; background-color:; height: 100%; width: 21%">
								<div
									style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
									<div
										style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/rain.png) no-repeat center; background-position-x: 33.5%; background-position-y: 0px; background-color: #34BEE0;">
									</div>
								</div>
								<div id="weather_48" style="width: 100%; height: 40%; text-align: center;">--</div>
							</div>
							<div
								style="float: left; background-color:; height: 100%; width: 21%">
								<div
									style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
									<div
										style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/C.png) no-repeat center; background-position-x: 33.5%; background-position-y: -2px;; background-color: #34BEE0;">
									</div>
								</div>
								<div
									style="width: 100%; height: 40%; text-align: center; line-height: 12px;">
									温度<br /><span id="temperature_48">--℃</span>
								</div>
							</div>
							<div
								style="float: left; background-color:; height: 100%; width: 21%">
								<div
									style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
									<div
										style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/wind.png) no-repeat center; background-position-x: -4px; background-position-y: -2px; background-color: #34BEE0;">
									</div>
								</div>
								<div
									style="width: 100%; height: 40%; text-align: center; line-height: 12px;">
									风速<br /><span id="wind_48">--</span>
								</div>
							</div>
							<div
								style="float: left; background-color:; height: 100%; width: 21%">
								<div
									style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
									<div
										style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/water.png) no-repeat center; background-position-x: -5px; background-position-y: -3px; background-color: #34BEE0;">
									</div>
								</div>
								<div
									style="width: 100%; height: 40%; text-align: center; line-height: 12px;">
									湿度<br /><span id="humidity_48">--%RH</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 分割线 -->
				<div style="float: left; display: flex; height: 100%; width: 0.1%">
					<div
						style="margin: auto; height: 89%; width: 90%; border: 1px solid rgb(52, 190, 224);">
					</div>
				</div>
				<!-- 调度信息-->
				<div
					style="float: left; height: 95%; width: 26%; margin: 0px 15px 15px 15px; background-color:;">
					<div style="line-height: 29px; height: 10%; width: 100%">
						<div style="font-size: 16px; float: left; height: 100%; width: 50%;">调度信息</div>
						<div style="float: right; height: 100%; width: 50%; margin-top: 7px; text-align: right;">
							<img id="dispatch_info" style="width:18px; cursor:pointer" src="./res/img/jiexiantu.png">
						</div>
					</div>
					<div style="line-height: 29px; height: 90%; width: 100%">
						<div style="margin-left: 34px; position: absolute">日最高负荷8888MW</div>
						<div id="dispatchChart" class="ak-echarts"
							style="width: 100%; height: 100%;"></div>
					</div>
				</div>
				<!-- 分割线 -->
				<div style="float: left; display: flex; height: 100%; width: 0.1%">
					<div
						style="margin: auto; height: 89%; width: 90%; border: 1px solid rgb(52, 190, 224);">
					</div>
				</div>
				<!-- 预报警-->
				<div
					style="float: left; height: 95%; width: 20%; margin: 0px 15px 15px 15px; background-color:;">
					<div style="line-height: 29px; height: 10%; width: 100%">
						<div
							style="font-size: 16px; float: left; height: 100%; width: 50%;">预报警</div>
						<div
							style="float: right; height: 100%; width: 50%; text-align: right;">
							单位：条</div>
					</div>
					<div style="line-height: 29px; height: 90%; width: 100%">
						<!-- 气象 -->
						<div style="float: left; width: 33.3%; height: 50%;">
							<div id = "weather"
								style="cursor: pointer;width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
								0</div>
							<div style="width: 100%; height: 40%; text-align: center;">
								气象</div>
						</div>
						<!-- 环境 -->
						<div style="float: left; width: 33.3%; height: 50%;">
							<div id = "environment"
								style="cursor: pointer;width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
								0</div>
							<div style="width: 100%; height: 40%; text-align: center;">
								环境</div>
						</div>
						<div style="float: left; width: 33.3%; height: 50%;">
							<div id = "gridRisk"
								style="cursor: pointer; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
								0</div>
							<div style="width: 100%; height: 40%; text-align: center;">
								电网风险</div>
						</div>
						<div style="float: left; width: 33.3%; height: 50%;">
							<div id = "overLoad"
								style="cursor: pointer;width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
								0</div>
							<div style="width: 100%; height: 40%; text-align: center;">
								重过载</div>
						</div>
						<div style="float: left; width: 33.3%; height: 50%;">
							<div id = "monitoring"
								style="cursor: pointer;width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
								0</div>
							<div style="width: 100%; height: 40%; text-align: center;">
								在线监测</div>
						</div>
					</div>
				</div>
				<!-- 分割线 -->
				<div style="float: left; display: flex; height: 100%; width: 0.1%">
					<div
						style="margin: auto; height: 89%; width: 90%; border: 1px solid rgb(52, 190, 224);">
					</div>
				</div>
				<!-- 运行内容-->
				<div
					style="float: left; height: 95%; width: 20%; margin: 0px 15px 15px 15px; background-color:;">
					<div style="line-height: 29px; height: 10%; width: 100%">
						<div style="font-size: 16px; float: left; height: 100%; width: 50%;">运行内容</div>
						<div style="float: right; height: 100%; width: 50%; text-align: right;">单位：项</div>
					</div>
					<div style="line-height: 29px; height: 90%; width: 100%">
						<!-- 故障 -->
						<div style="float: left; width: 50%; height: 50%;">
							<div
								style="display: flex; width: 100%; height: 60%; text-align: center; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
								<div
									style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/guzhang.png) no-repeat center; background-position-x: 49.5%; background-size: 68%; background-color: #34BEE0;">
								</div>
							</div>
							<div style="width: 100%; height: 40%; text-align: center;">故障</div>
						</div>
						<!-- 环境 -->
						<div style="float: left; width: 50%; height: 50%;">
							<div style="width: 100%; height: 60%; line-height: 83px;">
								<div style="height: 100%; width: 50%; float: left">已恢复：&nbsp;&nbsp;</div>
								<div style="height: 100%; width: 50%; float: left">
									<font id="recoveredCount" style="font-size: 31px; color: #34BEE0; cursor:pointer;">0</font>
								</div>
							</div>
							<div style="width: 100%; height: 40%;">
								<div style="height: 100%; width: 50%; float: left">处理中：&nbsp;&nbsp;</div>
								<div style="height: 100%; width: 50%; float: left">
									<font id="disposingCount" style="font-size: 31px; color: #EB9F09; cursor:pointer;">0</font>
								</div>
							</div>
						</div>
						<div style="float: left; width: 50%; height: 50%;">
							<div
								style="display: flex; width: 100%; height: 60%; line-height: 83px; font-size: 31px; font-color: red; color: #34BEE0;">
								<div
									style="height: 40px; width: 40px; margin: auto; -webkit-border-radius: 36px; background: url(./res/img/quexian.png) no-repeat center; background-size: 68%; background-color: #34BEE0;">
								</div>
							</div>
							<div style="width: 100%; height: 40%; text-align: center;">缺陷</div>
						</div>
						<div style="float: left; width: 50%; height: 50%;">
							<div style="width: 100%; height: 60%; line-height: 83px;">
								<div style="height: 100%; width: 50%; float: left">已消缺：&nbsp;&nbsp;</div>
								<div style="height: 100%; width: 50%; float: left">
									<font id="eliminatedCount" style="font-size: 31px; color: #34BEE0; cursor:pointer;">0</font>
								</div>
							</div>
							<div style="width: 100%; height: 40%;">
								<div style="height: 100%; width: 50%; float: left">未消缺：&nbsp;&nbsp;</div>
								<div style="height: 100%; width: 50%; float: left">
									<font id="uneliminatedCount" style="font-size: 31px; color: #EB9F09; cursor:pointer;">0</font>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	<!-- 调度信息图片的window ，相对于整个body的布局-->
	<div id="dispatch_info_window" class="ak-window" showHeader="true" showCloseButton="true"
		headerStyle="background:white; margin-top:-7px; border-bottom:solid 0px #009694; height:21px;"
		style="width:39.7%; height:58.6%;" showModal="false" allowDrag="true">
		<!-- <img style="width: 100%;" src="res/img/gongdiantu.png"> -->
		
		<div id="ensureMap" style="height:100%; background: url(res/img/no-gongdiantu.png) no-repeat center; background-size:100%;">
		</div>
	</div>

	<!-- 用户单位的window ，相对于整个body的布局-->
	<div id="userofpowWindow" class="ak-window" showHeader="true"
		showCloseButton="true" headerStyle="background: white;
		 margin-top: -7px; border-bottom: solid 0px #009694; height: 21px;"
		style="width:59.5%; height:58.6%;" showModal="false" allowDrag="true">
		<div style="height: 98%; width: 100%; margin: 0 auto;">
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
								allowResize="false" pageSize="20" multiSelect="true"
								style="width: 100%; height: 100%;">
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
									<div name="geographyLocation" field="geographyLocation"
										header="定位" headerAlign="center" width="10%" align="center"
										allowSort="false"></div>
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

	<!-- 站线弹框 -->
	<div id="stationorlinewindow" class="ak-window" showHeader="true"
		showCloseButton="true" headerStyle="background: white;
		 margin-top: -7px; border-bottom: solid 0px #009694; height: 21px;"
		style="width: 59.5%; height: 58.6%;" showModal="false" allowDrag="true">
		<div style="height: 98%; width: 100%; margin: 0 auto;">
			<div class="tab">
				<div class="tab_menu">
					<ul>
						<li id="stationTab" class="selected">电站</li>
						<li id="lineTab">线路</li>
					</ul>
				</div>
				<div class="tab_box">
					<div class="select_li_div">
						<div style="float: left; width: 100%; height: 100%; background-color: #fff; overflow-y: auto;">
							<div id="stationLineGrid" pagerButtons="#buttons"
								class="ak-datagrid" style="width: 100%; height: 100%;"
								allowResize="false" idField="id" multiSelect="true"
								pageSize="20" showVGridLines="false"
								showHGridLines="false" allowAlternating="true"
								allowAlternating="false">
								<div property="columns">
									<div headerAlign="center">
										<div property="columns">
											<div name="stationLineName" field="stationLineName" header="电站名称"
												headerAlign="center" width="10%" align="center" allowSort="false"></div>
											<div name="ensureLevelName" field="ensureLevelName" header="保电级别"
												headerAlign="center" width="10%" align="center" allowSort="false"></div>
											<div name="voltageLevel" field="voltageLevel" header="电压等级"
												headerAlign="center" width="10%" align="center" allowSort="false"></div>
											<div name="orgBelongName" field="orgBelongName" header="负责小组"
												headerAlign="center" width="10%" align="center" allowSort="false"></div>
											<div name="dutyPerson" field="dutyPerson" header="负责人"
												headerAlign="center" width="10%" align="center" allowSort="false"></div>
											<!-- <div name="phoneNumber" field="phoneNumber" header="电话"
												headerAlign="center" width="10%" align="center" allowSort="false"></div> -->
											<div name="geographyLocation" field="geographyLocation" header="定位"
												headerAlign="center" width="10%" align="center" allowSort="false"></div>
											<div name="stationLineManage" field="stationLineManage" header="站所管理"
												headerAlign="center" width="10%" align="center" allowSort="false"></div>
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
		showCloseButton="true" headerStyle="background: white;
		    border-bottom: solid 0px #009694; margin-top: -7px; height: 21px;"
		style="width: 100%; height: 29%;" showModal="false" allowDrag="true">
		<div
			style="height: 98%; width: 99%; background: #FDFFE7; margin: 0 auto;">
			<div id="scrollable">
				<!-- 上一页按钮 -->
				<div class="leftbutton_click"
					style="top: 78px; position: absolute; height: 50px; width: 50px; z-index: 99">
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
								<div style="width: 100%; height: 14%;">
									<div
										style="float: left; width: 60%; height: 100%; line-height: 30px;">
										<font style="font-size: 18px; font-weight:900;"> 资源保障</font>
										<font
											style="color: #009694; font-size: 15px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;人员</font>
									</div>
								</div>
								<div style="width: 100%; height: 86%;">
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
									style="margin-top: 23px; float: left; width: 10%; height: 80%; border: #34BEE0 1px solid">
								</div>
							</div>
							<!--  车辆 -->
							<div style="float: right; width: 49.9%; height: 100%;">
								<div style="width: 100%; height: 14%;">
									<div style="float: left; width: 60%; height: 100%;">
										<font
											style="color: #009694; line-height: 29px; font-size: 16px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;车辆</font>
									</div>
								</div>
								<div style="width: 100%; height: 86%;">
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
								<div style="width: 100%; height: 14%;">
									<div
										style="float: left; width: 60%; height: 100%; line-height: 30px;">
										<font style="font-size: 18px; font-weight: 900;"> 资源保障</font>
										<font
											style="color: #009694; font-size: 15px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;驻点</font>
									</div>
								</div>
								<div style="width: 100%; height: 86%;">
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
													<div name="location" field="location" header="定位"
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
									style="margin-top: 23px; float: left; width: 1%; height: 80%; border: #34BEE0 1px solid">
								</div>
							</div>
							<!-- 医院 -->
							<div style="float: right; width: 33.3%; height: 100%;">
								<div style="width: 100%; height: 14%;">
									<div style="float: left; width: 60%; height: 100%;">
										<font
											style="color: #009694; line-height: 29px; font-size: 16px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;医院</font>
									</div>
								</div>
								<div style="width: 100%; height: 86%;">
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
													<div name="location" field="location" header="定位"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 中间的分割线 -->
							<div style="float: right; width: 0.1%; height: 100%;">
								<div
									style="margin-top: 23px; float: left; width: 10%; height: 80%; border: #34BEE0 1px solid">
								</div>
							</div>
							<!-- 宾馆 -->
							<div style="float: right; width: 33.2%; height: 100%;">
								<div style="width: 100%; height: 14%;">
									<div style="float: left; width: 60%; height: 100%;">
										<font
											style="color: #009694; line-height: 29px; font-size: 16px; font-weight: 100;">&nbsp;&nbsp;&nbsp;&nbsp;宾馆</font>
									</div>
								</div>
								<div style="width: 100%; height: 86%;">
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
													<div name="location" field="location" header="定位"
														headerAlign="center" width="10%" align="center"
														allowSort="false"></div>
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
					style="height: 50px; width: 50px; right: 17px; top: 78px; position: absolute;">
					<img style="position: absolute; right: -15px; width: 50%;"
						src="res/img/right.png">
				</div>
			</div>
		</div>
	</div>
	
	<!-- 故障弹窗 -->
	<div id="faultWindow" class="ak-window" title="故障" showHeader="true"
		showCloseButton="true" headerStyle="background: white;
	    border-bottom: solid 0px #009694; margin-top: -7px; height: 21px;"
		style="width: 100%; height: 58.6%;" showModal="false" allowDrag="true">
		<div id="faultGrid" class="ak-datagrid" style="width:100%;height:100%" allowResize="false" 
	         idField="id" multiSelect="true" pageSize="10" url=""
	        showVGridLines="false" showHGridLines="false" allowAlternating="true" allowCellWrap ="true">
		   <div property="columns">   
	           <div  headerAlign="center">
	               <div property="columns"  >
		               	<div type="checkcolumn"> </div>
 	            		<div field="faultTime" width="10%" headerAlign="center" align ="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">故障时间</div>
 	            		<div field="organization" width="10%" headerAlign="center" align ="center" allowSort="true">所属单位</div>    
 	            		<div field="stationOrLineName" width="10%" headerAlign="center" align ="center" allowSort="true">线路/站</div>
	            		<div field="deviceName" width="10%" headerAlign="center" align ="center" allowSort="true">设备名称</div>
	            		<div field="voltageLevel" width="10%" headerAlign="center" align ="center" allowSort="true">电压等级</div>
 	            		<div field="faultPhaseId" width="10%" headerAlign="center" align ="center" allowSort="true">故障相别/极号</div>    
  		                <div field="faultType" width="10%" headerAlign="center" align ="center" allowSort="true">故障类型</div>
 			            <div field="faultDetail" width="12%" headerAlign="center" align ="center" allowSort="true">故障详情</div>  
	               </div>
	           </div> 
      		</div>
	   </div>	
	</div>
	
	<!-- 缺陷弹窗 -->
	<div id="defectWindow" class="ak-window" title="故障" showHeader="true"
		showCloseButton="true" headerStyle="background: white;
	    border-bottom: solid 0px #009694; margin-top: -7px; height: 21px;"
		style="width: 100%; height: 58.6%;" showModal="false" allowDrag="true">
		<div id="defectGrid" class="ak-datagrid" style="width:100%;height:100%" allowResize="false" 
	         idField="id" multiSelect="true" pageSize="10" url=""
	        showVGridLines="false" showHGridLines="false" allowAlternating="true" allowCellWrap ="true">
		   <div property="columns">   
	           <div  headerAlign="center">
	               <div property="columns"  >
		               	<div type="checkcolumn"> </div>
 	            		<div field="discoveredDate" width="7%" headerAlign="center" align ="center" dateFormat="yyyy-MM-dd HH:mm:ss" >发现日期</div>
		           		<div field="belongCity" width="11%" headerAlign="center" align ="center" >所属地市</div>
		           		<div field="powerStation" width="7%" headerAlign="center" align ="center" >线路/站</div>
		           		<div field="defectMainEquipment" width="7%" headerAlign="center" align ="center" >缺陷主设备</div>
		           		<div field="voltageClass" width="7%" headerAlign="center" align ="center" >电压等级</div>
		           		<div field="defectProperties" width="7%" headerAlign="center" align ="center" >缺陷性质</div>
		           		<div field="defectContent" width="7%" headerAlign="center" align ="center" >缺陷内容</div>
		           		<div field="defectState" width="7%" headerAlign="center" align ="center" >缺陷状态</div> 
	               </div>
	           </div> 
      		</div>
	   </div>	
	</div>
	<%--****************************************************预警信息台**************************************************************** --%>
	<div id="overLoadWindow" class="ak-window" title="重过载"
			style="width: 100%; height: 100%;" showModal="true" allowDrag="false">
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
	</div>
		
	<div id="monitoringWindow" class="ak-window" title="在线监测"
			style="width: 100%; height: 100%;" showModal="true" allowDrag="false">
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
		
	<div id="weatherWindow" class="ak-window" title="气象预报"
			style="width: 100%; height: 100%;" showModal="true" allowDrag="false">
			<div id="weatherDataGrid" class="ak-datagrid" style="width:100%;height:100%" allowResize="false" 
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
	</div>
	
	<div id="gridRiskWindow" class="ak-window" title="电网风险"
			style="width: 100%; height: 100%;" showModal="true" allowDrag="false">
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
	</div>
<%--****************************************************预警信息台**************************************************************** --%>	
</body>
</html>
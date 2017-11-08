<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
<ak:framework />
<title>智能运检系统 | 故障异常</title>
<link href="res/css/page.css" rel="stylesheet" type="text/css" />
<link href="res/css/faultTrip.css" rel="stylesheet" type="text/css" />
</head>
<body style="background: ">
	<div class="ak-fit" style="overflow: hidden; ">
		<div class="ak-splitter" vertical="true" allowResize="false"
			handlerSize="5" style="width: 100%; height: 100%;">
			
			<div size="15%" showCollapseButton="false">
				<div class="ak-splitter" vertical="true" allowResize="false" handlerSize="5" style="width: 100%; height: 100%;">
					<div size="50%" showCollapseButton="false" class="bg-color">	
						 <!-- tab -->
						<div id="radio" class="ak-radiobuttonlist ak-radiobuttonlistnewstyle ak-radiobuttonlisttip ak-radiobuttonlistcolor" 
						style="left: 0px; top: 14px; position: absolute;"repeatItems="1" repeatLayout="table"
						repeatDirection="vertical" textField="text" valueField="id">
						</div>
					</div>
					<div size="50%" showCollapseButton="false"  >	
						 <!-- 新增  -->
						<!-- <div style="background:#ffffff;width:100%;height:90%"> -->
						<div class="F-box F-box-hoveroff" style="width:100%;height:100%">
							<div>
								<div   id="menudiv" style="float: left;height:100px;width:600px">
								<!--  //菜单 -->
								</div>
							
								<a class="ak-button ak-normalbutton jqss" id = "controlDiv" href="javascript:void(0)"style="height:32px; width:118px; text-align:center; margin:12px 10px 0 10px; background:#009694; float:right;" >精确搜索 ﹀</a>
								<div style="float:right;">
									<input id="keywords" class="ak-textbox ak-textboxBtnstyle" emptyText="请输入搜索关键字"  style="height:32px; width:310px; margin-top:13px; border:1px solid #e8e8e8;"/>
									<span id="searchImg"  class="searchPic"></span>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
			
			<div size="85%" showCollapseButton="false" class="swiper-container">
				<div id ="aksplitter" class="ak-splitter" vertical="true" allowResize="false" handlerSize="5" style="width: 100%; height: 100%;">
					<div id="jqcxtj" class = "block" size="10%" showCollapseButton="false"   expanded="true" >	
							<div class="F-box F-box-hoveroff" >
								<table style="width:100%;padding-left: 14px;">
					                <tr>
					                	<td class="spanTd"><span style="height: 30px;"
											class="floatLeft value">类别</span></td>
										<td><input id="gzlb" class="ak-combobox"
											style="width: 175px;" textField="text" valueField="id"
											emptyText="请选择..."  required="false" url=""
											allowInput="false" showNullItem="true" nullItemText="全部" /></td>
					                	
					                	<td class="spanTd"><span style="height: 30px;"
											class="floatLeft value">故障(异常)时间</span></td>
										<td><input id="gzsj1" class="ak-datepicker" style="width:175px; height:30px; font-family:微软雅黑;"
											emptyText="请选择..." format="yyyy-MM-dd H:mm"   showTime="true" 
											showOkButton="true" showClearButton="false" allowInput="false" /></td>
			
										<td class="spanTd"><span style="height: 30px;"
											class="floatLeft value">至</span></td>
										<td><input id="gzsj2" class="ak-datepicker" style="width:175px; height:30px; font-family:微软雅黑;"
											emptyText="请选择..." format="yyyy-MM-dd H:mm"   showTime="true" 
											showOkButton="true" showClearButton="false" allowInput="false" /></td>
			
										
			
										<td class="spanTd"><span style="height: 30px;"
											class="floatLeft value"> 运维单位 </span></td>
										<td> <input id="ssds" class="ak-treeselect" emptyText="请选择..." 
											multiSelect="true"  showClose="true" showFolderCheckBox="true"
											style="width: 175px; font-family: 微软雅黑;" showTreeIcon="false" imgPath="res/images/icons/" name="ssds"  allowInput="true"/>  
											</td>
										<td style="width: 91px;">
											  <a class="ak-button ak-normalbuttonstyle" id = "searchBtn" href="javascript:void(0)"style="width: 66px;margin: 0px 10px;float: left;" >查询</a>  
										</td>	
									</tr>
			
									<tr>
										
			
										<td class="spanTd"><span style="height: 30px;"
											class="floatLeft value">线路/站</span></td>
										<td>
										<input id="zxl" style="width: 175px;" 
											class="ak-buttonedit"  multiSelect="true"  showClose="true"
							  				name="zxl" />
										
										<!-- <input id="zxl" class="ak-combobox"
											style="width: 175px;" textField="text" valueField="id"
											emptyText="请选择..."  required="false" url=""
											allowInput="false" showNullItem="true" nullItemText="全部" /> -->
											</td>
			
			
										<td class="spanTd"><span style="height: 30px;"
											class="floatLeft value">设备(杆塔)</span></td>
										<td><!-- <input id="sbmc"  
											style="width: 175px;"  class="ak-textbox" emptyText="输入设备名称"   /> -->
											<!-- <input id="sbmc" class="ak-combobox"
											style="width: 175px;" textField="text" valueField="id"  
											emptyText="请选择..."  required="false" url=""
											allowInput="false" showNullItem="true" nullItemText="全部" /> -->
											<input id="sbmc" class="ak-treeselect" emptyText="请选择..." 
												style="width: 175px; font-family: 微软雅黑;"  multiSelect="true"  showClose="true"
						 						allowInput="true" name="sbmc" /> 
											
											</td> 
										<td class="spanTd"><span style="height: 30px;" 
											class="floatLeft value">电压等级</span></td>
										<td><input id="dydj" class="ak-combobox"
											style="width: 175px;" textField="text" valueField="id"  
											emptyText="请选择..."  required="false" url=""
											allowInput="false" showNullItem="true" nullItemText="全部" /></td>
											
										<td class="spanTd"><span style="height: 30px;"
											class="floatLeft value">是否抢修</span></td>
										<td  ><input id="sfqx" class="ak-combobox"
											style="width: 175px;" textField="text" valueField="id"
											emptyText="请选择..."  required="false" url=""
											allowInput="false" showNullItem="true" nullItemText="全部" /></td>	
									 
										<td style="width: 91px;">
					                        <a class="ak-button ak-normalbuttonstyle" id = "resetBtn" href="javascript:void(0)"style="width: 66px;float: left;margin: 0px 10px;background: rgb(235, 159, 13);" >重置</a>  				                         
										</td>
					                </tr>
			                 	</table>
			                 </div>
			         </div>
	                 <div id='dtdiv'size="90%" showCollapseButton="false" class="bg-color"> 	
					 	<div id ="showgrid F-box F-box-hoveroff" style="width:100%;height:100%;">
					 		<div  id="datagrid1"  pagerButtons="#buttons"  class="ak-datagrid" style="width:100%;height:100%;" allowResize="false" 
					 	         idField="id" multiSelect="true" sizeList="[10,20,30,50]" pageSize="15" allowCellSelect="false"
					 	        showVGridLines="false" showHGridLines="false" allowAlternating="true" >
					 			    <div property="columns">   
					 		           <div  headerAlign="center">
					 		               <div property="columns"  >
					 			               	<div type="checkcolumn"  > </div>
					 	 	                	<div  visible="false" field="id">主键</div>    
					 	 	            		<div field="faultCategory" width="10%" headerAlign="center" align ="center" allowSort="false">类别</div>
					 	 	            		<!-- <div field="faultTypeId" width="8%" headerAlign="center" align ="center" allowSort="true">故障类型id</div> -->
					 	 	            		<div field="faultTime" width="10%" headerAlign="center" align ="center" allowSort="false">故障(异常)时间</div>
					 	 	            		<div field="organization" width="10%" headerAlign="center" align ="center" allowSort="false">运维单位</div>    
					 	 	            		<!-- <div field="type" width="8%" headerAlign="center" align ="center" allowSort="true" name="TYPE">专业</div> -->
					 	 	            		<!-- <div field="typeId" width="8%" headerAlign="center" align ="center" allowSort="true" name="TYPE">专业id</div> -->
					 	 	            		<div field="stationOrLineName" width="10%" headerAlign="center" align ="center" allowSort="false">线路/站</div>
					 		            		<div field="deviceName" width="10%" headerAlign="center" align ="center" allowSort="false">设备(杆塔)</div>
					 		            		<div field="voltageLevel" width="8%" headerAlign="center" align ="center" allowSort="false">电压等级</div>
					 		            		<!-- <div field="voltageLevelId" width="8%" headerAlign="center" align ="center" allowSort="true">电压等级id</div> -->
					 	 	            		<div field="faultPhaseId" width="8%" headerAlign="center" align ="center" allowSort="false">故障相别/极号</div>    
					 	  		                <div field="faultType" width="8%" headerAlign="center" align ="center" allowSort="false">故障类型</div>
					 	 			            <div field="weather" width="8%" headerAlign="center" align ="center" allowSort="false">现场天气</div>  
					 	 			            		<!-- 后三个tab页的公共 -->
					 	 			            <div  visible="false" field="faultLocator" width="8%" headerAlign="center" align ="center" allowSort="false" name="faultLocator">故障测距</div>  
					 	 			            <div  visible="false" field="repairOrNot" width="8%" headerAlign="center" align ="center" allowSort="false" name="repairOrNot">是否抢修</div>  
					 	 			            <div  visible="false" field="recoveryDate" width="8%" headerAlign="center" align ="center" allowSort="false" name="recoveryDate">恢复时间</div> 
					 	 			            <!-- <div field="updateDateTime" width="8%" headerAlign="center" align ="center" allowSort="false" name="updateDateTime">更新时间</div>  -->
					 	 			            
					 	 			            	<!-- 交流的字段 -->
					 	 			           <!--  <div field="stationFaultOrNot" width="8%" headerAlign="center" align ="center" allowSort="true" name="stationFaultOrNot">是否站内故障</div> 
					 	 			            <div field="threeSpanOrNot" width="8%" headerAlign="center" align ="center" allowSort="true" name="threeSpanOrNot">是否三跨区段</div> 
					 	 			            <div field="reclosingAction" width="8%" headerAlign="center" align ="center" allowSort="true" name="reclosingAction">重合闸动作情况</div> 
					 	 			            <div field="sendSuccessOrNot" width="8%" headerAlign="center" align ="center" allowSort="true" name="sendSuccessOrNot">强送是否成功</div> 
					 	 			            <div field="repairEndOrNot" width="8%" headerAlign="center" align ="center" allowSort="true" name="repairEndOrNot">抢修是否结束</div> 
					 	 			            
					 	 			            <div field="faultReason" width="8%" headerAlign="center" align ="center" allowSort="true" name="faultReason">故障原因</div> 
					 	 			            <div field="protection" width="8%" headerAlign="center" align ="center" allowSort="true" name="protection">保护动作情况</div> 
					 	 			            <div field="managementDetail" width="8%" headerAlign="center" align ="center" allowSort="true" name="managementDetail">处理详情</div> 
					 	 			            <div field="faultDetail" width="8%" headerAlign="center" align ="center" allowSort="true" name="faultDetail">故障详情</div> 
					 		              		 -->
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
	
	
	<!-- 站线选择 -->
	<div id="deviceOrLineWindow" class="ak-window" title="线路/站"
			style="width: 830px; height: 350px;" showModal="true"
			allowDrag="false">
			 
			 
			<div class="tab1"  >
				<div class="tab_menu">
					<ul>
						<li class="selected xlzbtn"  >线路</li>
						<li class='xlzbtn'>站</li>
						 
					</ul>
				</div>
				<div class="tab_box"> 
					 <div>
					 	 <div id="leftTree"
							style="float: left; width: 99%; height:250px; background-color: #fff; overflow-y: auto;">
							<div id="LineName" class="ak-tree"  showCheckBox="true"    
								style="width: 100%; height: 100%; padding: 5px;"
								showTreeIcon="true" showTreeLines="false" textField="text"
								onbeforeload="onBeforeTreeLoad" nodeclick="onDbClick"
								showTreeLines="false" idField="id" parentField="pid"
								resultAsTree="true" imgPath="res/images/icons/">
							</div>
						</div>
					 </div>
					 <div class="hide">
					 		<div id="rightTree"
								style="float: left; width: 99%; height: 250px; background-color: #fff; overflow-y: auto;">
								<div id="station" class="ak-tree"   showCheckBox="true"  
									style="width: 100%; height: 100%; padding: 5px;"
									showTreeIcon="true" showTreeLines="false" textField="text"
									showTreeLines="false" idField="id" parentField="pid"
									resultAsTree="true" imgPath="res/images/icons/">
								</div>
							</div> 
					  </div>
					  
				</div>
				 
				 
			</div>
			<button id="but" class="but">确定</button>
				 
		</div>
</body>
</html>
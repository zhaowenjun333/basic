<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html >
<html>
<head>
<ak:framework />
<title>智能运检系统 | 故障异常-通道异常-编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="res/faultTrip_channelException.css" rel="stylesheet" type="text/css" />
</head>
<body>
<body style="background:#EBEEF7"> 
		<div style="height:45%;width: 99%;background:white; margin: 0 auto;margin-top: 6px; padding-top: 22px;" >
 		<form id="form"  style="margin-top: 6px; text-align: center;margin: 0 auto;border:  0px black solid;height:100%;width:88%"><!-- style="border:1px black solid" --> 
			 
			 <!-- height属性：更改高度 -->
			 <table style="margin-top: 5px;border:  0px black solid;width: 100%; height:90%;border-collapse: separate; border-spacing: 0px 16px;">
				<tr style="border:  0px black solid;">
                	<td    style="border: 0px black solid;text-align: right;width: 8.3%;">
                		<span style="color: red;"> * </span>类别</td>
					<td><input id="faultCategory" class="ak-combobox text-box  " 
						style="width: 262px;" textField="text" valueField="id"  allowInput="false"
						emptyText="请选择..." required="true"   name="faultCategory"
						showNullItem="true" nullItemText="请选择..." /></td>
					 
					<td   style="text-align: right;width: 13.3%;"><span
						style="color: red;"> * </span>故障(异常)时间</td>
					<td><input id="faultTime" class="ak-datepicker" 
						style="width:262px; height:30px; font-family:微软雅黑;"
						emptyText="请选择..." format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" name="faultTime"
						showOkButton="true" showClearButton="false" allowInput="false" /></td>
					 
					<td   style="text-align: right;width:13.3%;">
						<span style="color: red;"> </span>运维单位</td>
					<td  ><!-- <input id="organization"  class="ak-combobox text-box" 
					    style="width: 262px;"  name="organization"
						textField="text" valueField="id" emptyText="请选择..." url=""
						required="true" allowInput="false" showNullItem="true"
						nullItemText="请选择..."  ItemText="{南京 :shagnhai }"/> -->
						<input id="organization" class="ak-treeselect" emptyText="请选择..." 
						style="width: 262px; font-family: 微软雅黑;"  name="organization" /> 
						
						
						
						</td>
				</tr>
				<tr >
					<!-- <td style="text-align: right; "><span style="color: red; "> * </span>是否站内故障</td>
					<td ><input id="stationFaultOrNot" class="ak-combobox text-box"
						style="width: 262px;" textField="text" valueField="id"
						emptyText="请选择..." url="" required="true" allowInput="false" name="stationFaultOrNot"
						showNullItem="true" nullItemText="请选择..." /></td> -->
					
					
					
					<td style="text-align: right; " ><span style="color: red;"> * </span>线路/站</td>
					<td><!-- <input id="stationOrLineName" class="ak-combobox text-box"
						style="width: 262px;" textField="text" valueField="id"
						emptyText="请选择..." required="true" allowInput="false" name="stationOrLineName"
						showNullItem="true" nullItemText="请选择..." /> -->
						
						<input id="stationOrLineName" style="width: 262px;"  allowInput="false"
						 class="ak-buttonedit"  name="stationOrLineName" />
						</td>
				    
				    <td style="text-align: right; "><span style="color: red;"> * </span>设备(杆塔)</td>
					<td> <!-- <input id="deviceName" class="ak-combobox text-box"   
						style="width: 262px;" textField="text" valueField="id"
						emptyText="请选择..." required="true" allowInput="false" name="deviceName"
						showNullItem="true" nullItemText="请选择..." /> -->
						
						 <input id="deviceName" class="ak-treeselect" emptyText="请选择..." 
						style="width: 262px; font-family: 微软雅黑;"  name="deviceName" /> 
						<input id="deviceType" class="ak-treeselect" emptyText="请选择..." 
							style="display: none ;width: 262px; font-family: 微软雅黑;"  allowInput="false" name="deviceType" />
						
					 </td>
				     
				     <td style="text-align: right; "><span style="color: red;">   </span>电压等级</td>
					<td><input id="voltageLevel" class="ak-combobox text-box"   
						style="width: 262px;" textField="text" valueField="id"
						emptyText="请选择..." required="true" allowInput="false" name="voltageLevel"
						showNullItem="true" nullItemText="请选择..." /></td>	
				</tr>
				<tr>
					<td style="text-align: right; "><span style="color: red;">*</span>故障相别/极号</td>
					<td><input id="faultPhaseId" class="ak-combobox text-box"
						style="width: 262px;" textField="text" valueField="id"
						emptyText="请选择..." url="" required="true" allowInput="false" name="faultPhaseId"
						showNullItem="true" nullItemText="请选择..." /></td>
					<td  style="text-align: right; "><span style="color: red;"> * </span>故障类型</td>
					<td><input id="faultType" class="ak-combobox text-box"
						style="width: 262px;" textField="text" valueField="id"
						emptyText="请选择..." required="true" allowInput="false"   name="faultType"
						showNullItem="true" nullItemText="请选择..." /></td>	
					<td style="text-align: right; " ><span style="color: red;"> * </span>拉停原因</td>
					<td><input id="powerOutReason" style="width:262px"  class="ak-textbox"
					 emptyText="请输入" name="powerOutReason"
						showNullItem="true"   /></td>
				</tr>
				<tr>
					<td   style="text-align: right;width: 13.3%;"><span
							style="color: red;"> * </span>拉停时间</td>
					<td><input id="powerOutTime" class="ak-datepicker" 
						style="width:262px; height:30px; font-family:微软雅黑;"
						emptyText="请选择..." format="yyyy-MM-dd H:mm" timeFormat="H:mm" 
						showTime="true" name="powerOutTime"
						showOkButton="true" showClearButton="false" allowInput="false" />
						<!-- 更新时间 -->
						<input id="updateDateTime" class="ak-datepicker" 
						style="display:none    ; width:262px; height:30px; font-family:微软雅黑;"
						emptyText="请选择..." format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" 
						name="updateDateTime"
						showOkButton="true" showClearButton="false" allowInput="false" />
						
						</td>
					<td style="text-align: right; " ><span style="color: red;">   </span>现场天气</td>
					<td><input id="weather" style="width:262px"  class="ak-textbox"
						 emptyText="请输入" name="weather"
						showNullItem="true"   /></td>	
	 				<td  style="text-align: right; "><span style="color: red;">   </span>是否三跨区段</td>
	 				 <td style="border:  0px black solid;">
						 <input id="threeSpanOrNot"   class="ak-combobox text-box"
							style="  width: 262px;" textField="text" valueField="id"
							emptyText="请选择..." required="true" 
							allowInput="false"   name="threeSpanOrNot"
							showNullItem="true" nullItemText="请选择..." />
					</td>
				</tr>
	 			<tr>
	 				<td  style="text-align: right; "><span style="color: red;">  </span>是否抢修</td>
	 				<td>
	 					 <input id="repairOrNot"   class="ak-combobox text-box"
							style="  width: 262px;"  textField="text" valueField="id"
							emptyText="请选择..." required="true" allowInput="false"
							   name="repairOrNot" showNullItem="true" nullItemText="请选择..." />
	 				</td>
	 				<td  style="text-align: right; "><span style="color: red;">   </span>抢修是否结束</td>
					<td><input id="repairEndOrNot" class="ak-combobox text-box"
							style="width: 262px;" textField="text" valueField="id"
							emptyText="请选择..." required="true" allowInput="false"   
							name="repairEndOrNot" showNullItem="true" nullItemText="请选择..." /></td>
					<td   style="text-align: right;width: 13.3%;"><span
							style="color: red;">   </span>恢复时间</td>
					<td><input id="recoveryDate" class="ak-datepicker" 
						style="width:262px; height:30px; font-family:微软雅黑;"
						emptyText="请选择..." format="yyyy-MM-dd H:mm" timeFormat="H:mm" 
						showTime="true" name="recoveryDate"
						showOkButton="true" showClearButton="false" allowInput="false" /></td>	
	 			</tr>
	 			<tr>
	 				<td style="text-align: right; " ><span style="color: red;">   </span>附件</td>
					<td><input id="attachment" class="ak-textbox text-box"  name="attachment"
						style="width:262px ;float:left" textField="text" valueField="id" emptyText="请选择..."
						url="" required="false" allowInput="false" showNullItem="true"
						nullItemText="请选择..." />
						<span class="uploadFilePic" id = "gridRiskUploadFile"></span>
						<input id="attachmentpath"  class="ak-textbox"   
							style="display:none; width: 262px;"   name="attachmentpath"
							showNullItem="true" nullItemText="请选择..." />
					 </td>
					<td style="text-align: right; " ><span style="color: red;">   </span>处理详情</td>
					<td colspan="4"><input id="faultLocator" style=" width: 100%" class="ak-textbox"
						 emptyText="请输入" name="faultLocator"
						showNullItem="true"   /></td>
				</tr>
			</table>
		</form>				
	</div>
	<div style="height: 45%;width: 99%;background:white; margin: 0 auto;    margin-top: 10px; " >
		<div style="height:100%;width:88% ;margin: 0 auto">
		<div class="ak-splitter" vertical="true" allowResize="false"
			handlerSize="0" style="width: 100%; height: 100%;">
				<div size="15%" showCollapseButton="false" >
					<div style="    margin-top: 6px;margin-left: 1px;"><span style="font-size:15px;">跟踪信息</span>
					<span id="jia" class="searchPic">
						<img style="position: relative; top: 6%; left: 7%; height: 20x; cursor: pointer; float: left;"
								src="res/img/add.png" />
					</span></div>
				</div>
				<div size="80%" showCollapseButton="false">
					<div class="ak-splitter" vertical="false" allowResize="false"
						handlerSize="0" style="width: 100%; height: 100%;">
							<div size="60%" showCollapseButton="false">
								<div id ="showgrid F-box F-box-hoveroff" style="width:100%;height:100%;left:10%;">
									<div id="datatail" class="ak-datagrid" style="width:100%;height:100%" allowResize="false" 
								         idField="id"   pageSize="15" allowCellWrap="false" allowCellEdit="true"
										 allowCellSelect="true" multiSelect="false" editNextOnEnterKey="true"
										 editNextRowCell="true" url=""showVGridLines="false" showHGridLines="false"allowAlternating="true">
								    		<div property="columns">
									        	<div type="checkcolumn"></div>
									        	<div type="indexcolumn" headerAlign="center"  header="序号"></div>
									        	<div field="id" header="ID" headerAlign="center" width="5%" align="center" allowSort="false" visible="false">
									        		<input property="editor" class="ak-textbox" style="width: 100%;"  value="1"/>
									        	</div>
									            <div field="time" header="时间" headerAlign="center" width="8%" align ="center" allowSort="false">
									            <input property="editor" class="ak-datepicker" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" /></div> 
									            <div field="deptName" header="部门" headerAlign="center" width="8%" align ="center" allowSort="false">
									            <input property="editor" class="ak-textbox" style="width: 100%;" /></div>
									            <div field="userName" header="人员" headerAlign="center" width="8%" align ="center" allowSort="false">
									            	<input property="editor" class="ak-textbox" style="width: 100%;" />
									            </div> 
								        		<div field="trackContent" width="19%" headerAlign="center"
													align="center" allowSort="false" visible="false">
													跟踪内容 <input property="editor" class="ak-textarea"
														style="width: 100%;" />
												</div>
								        	</div>
									</div>
								</div>
							</div>
							<div size="45%" showCollapseButton="false"   >
								<div align="center" style="border-left: 1px solid #cccccc;
								   border-top: 1px solid #cccccc;    border-right: 1px solid #cccccc; 
								   line-height: 32px ;    background: #ebeef5;	width:99.6%;height:11.2%;font-weight: bold;">
									   跟踪内容
								 </div>
								<textarea id="trackContent" class="ak-textarea text-box"  style="width:100%;height:88.7%;" emptyText="请输入工作内容" ></textarea> 
							</div>
					</div>
				</div>
		</div>
	
		</div>
	 
	</div>
		
	<div class="btncss" style="margin-top:1px" >
			<span class="words">保存</span>
	</div>
 	
	 <div id="deviceOrLineWindow" class="ak-window" title="线路/站"
			style="width: 830px; height: 350px;" showModal="true"
			allowDrag="false">
			 
			 
			<div class="tab"  >
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
							<div id="LineName" class="ak-tree"
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
								<div id="station" class="ak-tree"
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
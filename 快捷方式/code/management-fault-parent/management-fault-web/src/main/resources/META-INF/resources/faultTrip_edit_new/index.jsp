<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html >
<html>
<head>
<ak:framework />
<title>智能运检系统 | 故障异常-新增</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="res/faultTrip_edit_new.css" rel="stylesheet" type="text/css" />
</head>
<body>
<body style="background: ">
 	<div style="height: 99%;width: 99%;background:  ; margin: 0 auto; " >
 		<form id="form"  style="margin-top: 6px; text-align: center;margin: 0 auto;border:  0px black solid;height:100%;width:93%"><!-- style="border:1px black solid" --> 
			 <table style="margin-top: 5px;border:  0px black solid;width: 100%; height:64%;border-collapse: separate; border-spacing: 0px 16px;">
				<tr >
                	<td  style="text-align: right;width: 6.3%;"><span style="color: red;"> * </span>类别&nbsp;&nbsp;</td>
					<td><input id="faultCategory" class="ak-combobox text-box  " 
						style="width: 262px;" textField="text" valueField="id"  allowInput="false"
						emptyText="请选择..." required="true"   name="faultCategory"
						showNullItem="true" nullItemText="请选择..." /></td>
					 
					<td   style="text-align: right;width: 13.3%;"><span
						style="color: red;"> * </span>故障(异常)时间&nbsp;&nbsp;</td>
					<td><input id="faultTime" class="ak-datepicker" 
						style="width:262px; height:30px; font-family:微软雅黑;"
						emptyText="请选择..." format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" name="faultTime"
						showOkButton="true" showClearButton="false" allowInput="false" /></td>
					<td   style="text-align: right;width:13.3%;">运维单位&nbsp;&nbsp;</td>
					<td  >  
						 <input id="organization" class="ak-treeselect" emptyText="请选择..." 
						style="width: 262px; font-family: 微软雅黑;"  name="organization" allowInput="false"/>  
						</td>
				</tr>
				<tr >
					<td style="text-align: right; " >线路/站&nbsp;&nbsp;</td>
					<td> 
						<input id="stationOrLineName"  allowInput="false"  style="width: 262px;" class="ak-buttonedit"
						  name="stationOrLineName"  />
					</td>
				    
				    <td style="text-align: right; ">设备(杆塔)&nbsp;&nbsp;</td>
					<td> 
						 <input id="deviceName" class="ak-treeselect" emptyText="请选择..." 
							style="width: 262px; font-family: 微软雅黑;"  allowInput="false" name="deviceName" /> 
						 <input id="deviceType" class="ak-treeselect" emptyText="请选择..." 
							style=" display: none ;width: 262px; font-family: 微软雅黑;"  allowInput="false" name="deviceType" />
					</td>
				    <td style="text-align: right; ">电压等级&nbsp;&nbsp;</td>
					<td>
						<input id="voltageLevel" class="ak-combobox text-box"   
						style="width: 262px;" textField="text" valueField="id"
						emptyText="请选择..." required="true" allowInput="false" name="voltageLevel"
						showNullItem="true" nullItemText="请选择..." />
					</td>	
				</tr>
				<tr>
					<td  style="width: 118px;text-align: right; ">故障类型&nbsp;&nbsp;</td>
					<td><input id="faultType" class="ak-combobox text-box"
						style="width: 262px;" textField="text" valueField="id"
						emptyText="请选择..." required="true" allowInput="false"   name="faultType"
						showNullItem="true" nullItemText="请选择..." /></td>	
					<td style="text-align: right; " >现场天气&nbsp;&nbsp;</td>
					<td><input id="weather" style="width:262px"  class="ak-textbox" emptyText="请输入" name="weather"
						showNullItem="true"   /></td>
					 
					<td style="text-align: right; "><span style="color: red;">*</span>故障相别/极号&nbsp;&nbsp;</td>
					<td><input id="faultPhaseId" class="ak-combobox text-box"
						style="width: 262px;" textField="text" valueField="id"  z-index=999999;
						emptyText="请选择..." url="" required="true" allowInput="false" name="faultPhaseId"
						showNullItem="true" nullItemText="请选择..." />
						<input id="updateDateTime" class="ak-datepicker" 
						style="display: none ; width:262px; height:30px; font-family:微软雅黑;"
						emptyText="请选择..." format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" 
						name="updateDateTime"
						showOkButton="true" showClearButton="false" allowInput="false" />
						</td>
				</tr>
			</table>
	 		<div class="btncss" style=" " >
				<span class="words">保存</span>
			</div>
		</form>				
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
	</div>
</body>
</html>
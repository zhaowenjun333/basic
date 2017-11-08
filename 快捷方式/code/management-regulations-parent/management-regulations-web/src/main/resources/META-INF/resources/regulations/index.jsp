<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
<ak:framework />
<title>智能运检系统 | 规章制度</title>
<link href="res/css/page.css" rel="stylesheet" type="text/css" />
<link href="res/css/lay.css" rel="stylesheet" type="text/css" />
<link href="res/css/index.css" rel="stylesheet" type="text/css" />
<link href="res/css/faultTrip_edit_new.css" rel="stylesheet" type="text/css" />
</head>
<body style="background:#5f558a">
	<div class="ak-fit" style="overflow: hidden; ">
		<div class="ak-splitter" vertical="true" allowResize="false"
			handlerSize="5" style="width: 100%; height: 100%;">
			<div size="7.5%" showCollapseButton="false" >
			 <div style="height:94%;width:100%;background:white ; margin-top: 5px;"> 
			 	<div class="ak-splitter" vertical="false" allowResize="false"
					handlerSize="5" style="width: 100%; height: 100%;">
				<div size="50%" showCollapseButton="false" >
					 <div style="height:100%;width:100%;background: ; line-height: 45px;" >
			  			 <a class='btn wordBtn ' id='new'> 新增  </a>&nbsp;
						 <a class='btn wordBtn ' id='edit'> 编辑  </a>&nbsp;
						 <a class='btn wordBtn ' id='delete'> 删除  </a>&nbsp;
					  </div>
				</div>
				<div size="50%" showCollapseButton="false" >
					 <div style="height:100%;width:100%;background: white;">
					 		<div id="exactSearchBtn" style="height: 32px; width: 118px; text-align: center; margin: 8px 10px 0 10px; background: #009694; float: right;">
								<span  class="jqss" style="font-size: 16px; color: #ffffff; vertical-align: -webkit-baseline-middle;cursor:pointer">精确搜索 ﹀</span>
							</div>
					 	 	<div style="float: right;">
								<img id="searchImg" style="position:relative; height:23px; left:91%; margin-top:4%; float:left;" src="res\image\search.png" />
								<input id="keywords" placeholder="请输入搜索关键字"
									style="height: 32px; width: 310px; margin-top: 8px; border: 1px solid #e8e8e8;" />
							</div>
					  </div>
			 	</div>
				</div>
			</div>	
		</div>		
			<div size="92.5%" showCollapseButton="false" class="swiper-container"  >
			   <div style="height:100%;width:100%;background:"> 
			  		<div id ="aksplitter" class="ak-splitter" vertical="true" allowResize="false" handlerSize="5" style="width: 100%; height: 100%;">
			  			<div id="jqsstjsplitter" style="" class = "" size="14%" showCollapseButton="false" class="bg-color" expanded="true" >
			  				<div style="height:100%;width:100%;background:;margin-top: 1px;"> 
			  				 	 <div class="F-box F-box-hoveroff" >
				  					<table style="width: 100%;height: 100%; font-size: 16px;">
											<tr>
												<td class="spanTd"><span style="height: 30px;"
													class="floatLeft value">下发单位</span></td>
												<td><input id="issuedUnit1" class="ak-combobox"
													style="width: 175px;" textField="text" valueField="id"
													emptyText="请选择..."  required="false" url=""  multiSelect="true" 
													allowInput="false"  showClose="true" />
													</td>
												<td class="spanTd"><span style="height: 30px;"
													class="floatLeft value">年度</span></td>
												<td>
													<input id="year1" class="ak-combobox"
													style="width: 175px;" textField="text" valueField="id"
													emptyText="请选择..."  required="false" url=""  showClose="true" multiSelect="true" 
													allowInput="false"  />
												
												<!-- <input id="year1" class="ak-datepicker" style="width:175px;" 
													onvaluechanged="onValueChanged" nullValue="null" format="yyyy" 
													timeFormat="H:mm:ss" showTime="true" showOkButton="true" 
													showClearButton="false"/> -->
  												</td>
												<td class="spanTd"><span style="height: 30px;"
													class="floatLeft value">文号</span></td>
												<td><input id="fileNumber1"  
													style="width: 175px;"  class="ak-textbox" emptyText="请输入..."   /></td>
												<td class="spanTd"><span style="height: 30px;"
													class="floatLeft value">名称</span></td>
												<td><input id="name1"  
													style="width: 175px;"  class="ak-textbox" emptyText="请输入..."   /></td>
											</tr>
											<tr>
												<td class="spanTd"><span style="height: 30px;"
													class="floatLeft value">专业</span></td>
												<td> 
													<input id="specialty1" class="ak-combobox"
													style="width: 175px;" textField="text" valueField="id"
													emptyText="请选择..."  required="false" url=""  showClose="true" multiSelect="true" 
													allowInput="false"  />
													</td>
												<td class="spanTd"><span style="height: 30px;"
													class="floatLeft value">类型</span></td>
												<td> 
													<input id="type1" class="ak-combobox"
													style="width: 175px;" textField="text" valueField="id"
													emptyText="请选择..."  required="false" url=""  showClose="true" multiSelect="true" 
													allowInput="false"   />
													</td>
												<td > </td>
												<td>  </td>
												<td class="spanTd"></td>
												<td>
													<div class="pageBtn">
														<button id="searchBtn">查询</button>
														<button id="resetBtn">重置</button>
													</div>
												</td>
											</tr>
									</table>
				  				</div>
			  				</div>
			  			</div>
			  			<div id='gridpersonsplitter'size="86%" showCollapseButton="false" class="bg-color"> 
			  				<div style="height:100%;width:100%;background: ">
			  						<div id="gridregulations"  pagerButtons="#buttons"  class="ak-datagrid" style="width:100%;height:100%;" allowResize="false" 
										 idField="id" multiSelect="true" sizeList="[15,30,50,100]" pageSize="15"
								        showVGridLines="false" showHGridLines="false" allowAlternating="true" allowAlternating="false">
									        <div property="columns">      
									            <div header=" " headerAlign="center">	
									                <div property="columns" id="dtcolumn">
									                 		<div type="checkcolumn"  > </div>
									 	                	<div visible="false" field="id">主键</div>    
									 	            		<div field="issuedUnit" width="8%" headerAlign="center" align ="center" allowSort="true">下发单位</div>
									 	            		<div field="type" width="8%" headerAlign="center" align ="center" allowSort="true">类型</div>
									 	            		<div field="year" width="8%" headerAlign="center" align ="center" allowSort="true">年度</div>
									 	            		<div field="specialty" width="8%" headerAlign="center" align ="center" allowSort="true">专业</div>    
										            		<div field="fileNumber" width="8%" headerAlign="center" align ="center" allowSort="true">文号</div>
										            		<div field="name" width="8%" headerAlign="center" align ="center" allowSort="true">名称</div>
									 	            		<div field="attachment" width="8%" headerAlign="center" align ="center" allowSort="true" name="TYPE">附件</div>   
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
	<div id="editWindow" class="ak-window"   style=" 
	 width:85%; height:45%; background:grey" showModal="true" allowDrag="true">
		 <div style="height: 98%;width: 99%; margin: 0 auto; background:white" " >
	 		<form id="form"  style="margin-top: 6px; text-align: center;margin: 0 auto;border:  0px black solid;height:100%;width:91%"><!-- style="border:1px black solid" --> 
				 <table style="margin-top: 5px;border:  0px black solid;width: 100%; height:64%;border-collapse: separate; border-spacing: 0px 16px;">
					<tr >
	                	<td  style="text-align: right;width: 6.3%;"><span style="color: red;"> * </span>名称&nbsp;&nbsp;</td>
						<td> 
							<input id="name" name="name" style="width: 262px;"class="ak-textbox" emptyText="请输入..."  />
							<input id="id" name="id" style="width: 262px;display:none"class="ak-textbox" emptyText="请输入..."  />
							</td>
						<td   style="text-align: right;width: 13.3%;"><span
							style="color: red;"> * </span>下发单位&nbsp;&nbsp;</td>
						<td><input id="issuedUnit" class="ak-combobox text-box"   
							style="width: 262px;" textField="text" valueField="id"
							emptyText="请选择..." required="true" allowInput="false" name="issuedUnit" 
							showOkButton="true" showClearButton="false" showNullItem="true" nullItemText="请选择..." /></td>
						<td   style="text-align: right;width:13.3%;"><span
							style="color: red;"> * </span>年度&nbsp;&nbsp;</td>
						<td  >
							<input id="year" class="ak-combobox"
								style="width:262px;" textField="text" valueField="id"
								emptyText="请选择..."  required="false" url=""   
								allowInput="false"  name="year"  />
						 </td>
					</tr>
					<tr >
						<td style="text-align: right; " >文号&nbsp;&nbsp;</td>
						<td> 
							<input id="fileNumber" name="fileNumber" style="width: 262px;"class="ak-textbox" emptyText="请输入..."  />
							</td>
					    <td style="text-align: right; ">专业&nbsp;&nbsp;</td>
						<td> <input id="specialty" class="ak-combobox text-box"   
							style="width: 262px;" textField="text" valueField="id"
							emptyText="请选择..." required="true" allowInput="false" name="specialty"
							showNullItem="true" nullItemText="请选择..." />
						 </td>
					    <td style="text-align: right; "><span
							style="color: red;"> * </span>类型&nbsp;&nbsp;</td>
						<td><input id="type" class="ak-combobox text-box"   
							style="width: 262px;" textField="text" valueField="id"
							emptyText="请选择..." required="true" allowInput="false" name="type"
							showNullItem="true" nullItemText="请选择..." /></td>	
					</tr>
					<tr>
						<td  style="text-align: right; ">附件&nbsp;&nbsp;</td>
						<td> 
							<input id="attachment" class="ak-textbox text-box"
							style="width:262px ;float:left" textField="text" valueField="id" 
							emptyText="请选择..." url="" required="false" allowInput="false" 
							showNullItem="true" nullItemText="请选择..." name="attachment" />
							<span class="uploadFilePic" id = "gridRiskUploadFile"></span>
							</td>	
						<td style="text-align: right; " > </td>
						<td> </td>
						<td > 
						</td>
						<td style="text-align: right; ">
							<div class="btncss" style=" " >
								<span class="words">保存</span>
							</div>
						 </td>
					</tr>
				</table>
			</form>				
		</div>
	</div>	
</body>
</html>
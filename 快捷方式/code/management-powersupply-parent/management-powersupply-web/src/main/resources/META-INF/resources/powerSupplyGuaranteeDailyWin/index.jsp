<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
<title>保电任务日报弹窗</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="res/css/page.css" rel="stylesheet" type="text/css" />
<ak:framework />
</head>
<body style="padding: 10px; background: #eceef6;">
	<div class="ak-fit" style="overflow: hidden;">
		<div class="ak-splitter" vertical="true" allowResize="false"
			handlerSize="0" style="width: 100%; height: 100%;">
			<div size="5%" showCollapseButton="false">
				<div style="font-size: 16px; margin:0 10px; float:left;">日期</div>
				<input id="date" class="ak-datepicker" style="width: 175px; float:left;" emptyText="请选择..."
					format="yyyy-MM-dd" showTime="false" allowInput="false" ondrawdate="onDrawDate" />
			</div>
			
			<div size="95%" showCollapseButton="false">
				<div id="gridAndButtonArea" class="ak-splitter" vertical="true" allowResize="false"
					handlerSize="0" style="width: 100%; height: 100%;">
					<div size="95%" showCollapseButton="false">
						<div class="ak-splitter" vertical="true" allowResize="false"
							handlerSize="0" style="width: 100%; height: 100%;">
							<div size="50%" showCollapseButton="false">
								<div style="width:99%; height:94%; padding:0 10px 10px 10px; background:#ffffff;">
									<div class="ak-splitter" vertical="true" allowResize="false"
										handlerSize="-2" style="width: 99.5%; height: 100%;">
										<div size="17%" showCollapseButton="false">
											<div style="width:100%; height:100%; background:white;">
												<div style="font-size: 16px; font-weight: bold; padding:14px 0; float:left;">汇报记录</div>
												<div class="ak-toolbar" style="width:auto; float:right;">
													<div id="logAddBtn" class="btn" style="display: block;">
														<span class="wordBtn">新增</span>
													</div>
													<div id="logEditBtn" class="btn" style="display: block;">
														<span class="wordBtn">编辑</span>
													</div>
													<div id="logDeleteBtn" class="btn" style="display: block;">
														<span class="wordBtn">删除</span>
													</div>
													<div id="logCopyBtn" class="btn" style="display: none;">
														<span class="wordBtn">复制</span>
													</div>
													<div id="logSendBtn" class="btn" style="display: block;">
														<span class="wordBtn">发送</span>
													</div>
												</div>
											</div>
										</div>
										
										<div size="83%" showCollapseButton="false">
											<div id="logGrid" class="ak-datagrid" style="width: 100%; height: 100%;"
												allowResize="false" url="" idField="id" multiSelect="true" allowCellEdit="true"
												pageSize="10" editNextRowCell="true" allowAlternating="true" allowCellSelect="true">
												<div property="columns">
													<div type="checkcolumn"></div>
													<div name="id" field="id" header="主键" visible="false"></div>
													<div name="logDate" field="logDate" header="时间" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<input id="logDate" property="editor" class="ak-datepicker" emptyText="请选择..."
															format="yyyy-MM-dd H:mm" showTime="true" allowInput="false"/>
													</div>
													<div name="workReport" field="workReport" header="值班汇报" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<input id="workReport" property="editor" class="ak-textbox" emptyText="请输入..." vtype="rangeChar:0,2000"/>
													</div>
													<div name="reportObject" field="reportObject" header="汇报对象" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<input id="reportObject" property="editor" class="ak-textbox" emptyText="请输入..." vtype="rangeChar:0,1000"/>
													</div>
													<div name="otherAccessory" field="otherAccessory" header="附件" headerAlign="center" width="10%"
														align="center" allowSort="false"></div>
													<div name="remark" field="remark" header="备注" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<input id="logRemark" property="editor" class="ak-textbox" emptyText="请输入..." vtype="rangeChar:0,1000"/>
													</div>
													
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div size="50%" showCollapseButton="false">
								<div style="width:99%; height:94%; padding:0 10px 10px 10px; background:#ffffff;">
									<div class="ak-splitter" vertical="true" allowResize="false"
										handlerSize="-2" style="width: 99.5%; height: 100%;">
										<div size="17%" showCollapseButton="false">
											<div style="width:100%; height:100%; background:white;">
												<div style="font-size: 16px; font-weight: bold; padding:14px 0; float:left;">保电过程</div>
												<div class="ak-toolbar" style="width:auto; float:right;">
													<div id="processAddBtn" class="btn" style="display: block;">
														<span class="wordBtn">新增</span>
													</div>
													<div id="processEditBtn" class="btn" style="display: block;">
														<span class="wordBtn">编辑</span>
													</div>
													<div id="processDeleteBtn" class="btn" style="display: block;">
														<span class="wordBtn">删除</span>
													</div>
													<div id="processImportBtn" class="btn" style="display: block;">
														<span class="wordBtn">导入</span>
													</div>
													<div id="processExportBtn" class="btn" style="display: block;">
														<span class="wordBtn">导出</span>
													</div>
													<div id="processTemplateBtn" class="btn" style="display: block;">
														<span class="wordBtn">模板</span>
													</div>
												</div>
											</div>
										</div>
										
										<div size="83%" showCollapseButton="false">
											<div id="processGrid" class="ak-datagrid" style="width: 100%; height: 100%;"
												allowResize="false" url="" idField="id" multiSelect="true" allowCellEdit="true"
												pageSize="10" editNextRowCell="true" allowAlternating="true" allowCellSelect="true">
												<div property="columns">
													<div type="checkcolumn"></div>
													<div name="unit" field="unit" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<span style="color:red; margin-right:4px;">*</span>单位
	        											<input id="unit" property="editor" class="ak-treeselect" style="width: 100%;"
	        												textField="text" valueField="id" parentField="pid" expandOnLoad="true"/>
													</div>
													<div name="dutyPerson" field="dutyPerson" header="负责人" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<input id="dutyPerson" property="editor" class="ak-textbox" emptyText="请输入..." vtype="rangeChar:0,100"/>
													</div>
													<div name="personCount" field="personCount" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<span style="color:red; margin-right:4px;">*</span>人数
														<input id="personCount" property="editor" class="ak-spinner" minValue="1" emptyText="请输入..."/>
													</div>
													<div name="vehicleCount" field="vehicleCount" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<span style="color:red; margin-right:4px;">*</span>车辆数
														<input id="vehicleCount" property="editor" class="ak-spinner" minValue="1" emptyText="请输入..."/>
													</div>
													<div name="majorWork" field="majorWork" header="主要保电工作" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<input id="majorWork" property="editor" class="ak-textbox" emptyText="请输入..." vtype="rangeChar:0,2000"/>
													</div>
													<div name="problem" field="problem" header="存在问题" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<input id="problem" property="editor" class="ak-textbox" emptyText="请输入..." vtype="rangeChar:0,2000"/>
													</div>
													<div name="needHelpThing" field="needHelpThing" header="需协调事项" headerAlign="center"
														width="10%" align="center" allowSort="false">
														<input id="needHelpThing" property="editor" class="ak-textbox" emptyText="请输入..." vtype="rangeChar:0,2000"/>
													</div>
													<div name="relatedAccessory" field="relatedAccessory" header="附件" headerAlign="center"
														width="10%" align="center" allowSort="false"></div>
													<div name="remark" field="remark" header="备注" headerAlign="center" width="10%"
														align="center" allowSort="false">
														<input id="processRemark" property="editor" class="ak-textbox" emptyText="请输入..." vtype="rangeChar:0,1000"/>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div size="5%" showCollapseButton="false">
						<div class="winBtn" style="left: 95%; float: right;">
							<button id="saveBtn" style="width:48px; height:28px;">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
        function onDrawDate(e) {
            var date = e.date;
            var d = new Date();

            if (date.getTime() > d.getTime()) {
                e.allowSelect = false;
            }
        }
    </script>
</body>
</html>
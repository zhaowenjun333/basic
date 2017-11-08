<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ak" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
<title>地图测试</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="res/css/page.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="plugin/ol/ol.css" />
<link rel="stylesheet" href="css/map.css" type="text/css">
<script src="plugin/ol/ol-debug.js"></script>
<script src="plugin/bootstrap/js/bootstrap.min.js"></script>

<ak:framework />
</head>
<body>
	<div id="map" style="width:70%; height:97%; padding:10px;">
	    <!-- <div id="editdiv" style="display:inline">
	        <div style="float:left">
	            <button id="btnStartEdit" class="btn btn-default" onclick="testDraw()">测试编辑</button>
				<button id="btnSeleteEdit" class="btn btn-default" onclick="SeleteFeature()">选择</button>
				<button id="btnModifyEdit" class="btn btn-default" onclick="modifyFeature()">修改</button>
				<button id="btnDeteteEdit" class="btn btn-default" onclick="deleteFeature()">删除</button>
	            <button class="btn btn-default" onclick="stopEdit()">退出编辑</button>
	        </div>
	    </div> -->
	    <div class="locationscale">
	        <div id="location"></div>
	    </div>
	</div>
	
	<div class="ak-toolbar" style="width:28%; height:97%; padding:10px; float:right;">
		<!-- 用户 -->
		<div id="userGrid" class="ak-datagrid" style="width: 100%; height: 100%; display:none;"
			allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true">
			<div property="columns">
				<div type="indexcolumn" header="序号" headerAlign="center" width="20%"></div>
				<div name="userName" field="userName" header="用户名称" headerAlign="center"
					width="60%" align="center" allowSort="false"></div>
				<div name="geographyLocation" field="geographyLocation" header="" headerAlign="center"
					width="20%" align="center" allowSort="false"></div>
			</div>
		</div>
		
		<!-- 驻点 -->
		<div id="pointGrid" class="ak-datagrid" style="width: 100%; height: 100%; display:none;"
			allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true">
			<div property="columns">
				<div type="indexcolumn" header="序号" headerAlign="center" width="20%"></div>
				<div name="stationName" field="stationName" header="驻点名称" headerAlign="center"
					width="60%" align="center" allowSort="false"></div>
				<div name="location" field="location" header="" headerAlign="center"
					width="20%" align="center" allowSort="false"></div>
			</div>
		</div>
		
		<!-- 宾馆 -->
		<div id="hotelGrid" class="ak-datagrid" style="width: 100%; height: 100%; display:none;"
			allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true">
			<div property="columns">
				<div type="indexcolumn" header="序号" headerAlign="center" width="20%"></div>
				<div name="hotelName" field="hotelName" header="宾馆名称" headerAlign="center"
					width="60%" align="center" allowSort="false"></div>
				<div name="location" field="location" header="" headerAlign="center"
					width="20%" align="center" allowSort="false"></div>
			</div>
		</div>
		
		<!-- 医院 -->
		<div id="hospitalGrid" class="ak-datagrid" style="width: 100%; height: 100%; display:none;"
			allowAlternating="true" allowResize="false" pageSize="20" multiSelect="true">
			<div property="columns">
				<div type="indexcolumn" header="序号" headerAlign="center" width="20%"></div>
				<div name="hospitalName" field="hospitalName" header="医院名称" headerAlign="center"
					width="60%" align="center" allowSort="false"></div>
				<div name="location" field="location" header="" headerAlign="center"
					width="20%" align="center" allowSort="false"></div>
			</div>
		</div>
	</div>
</body>
</html>
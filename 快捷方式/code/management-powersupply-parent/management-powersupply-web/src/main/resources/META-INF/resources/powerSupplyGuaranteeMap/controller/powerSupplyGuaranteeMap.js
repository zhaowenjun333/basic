define(['akui', 'util', 'exportData', 'akapp', 'myCommon'],function(ak, myak, exak, akapp, myCommon){
	var me = {};
	ak.parse();
	
	me.taskId = "";
	me.type = "";
	
	var userGrid = ak("userGrid");
	var pointGrid = ak("pointGrid");
	var hotelGrid = ak("hotelGrid");
	var hospitalGrid = ak("hospitalGrid");
	
	// GIS地图
	var map,mapview;
	var wkt=new ol.format.WKT();
	var geojson=new ol.format.GeoJSON();
	var app={
	    drawInteraction:undefined,//绘制编辑器
	    drawFeatures:new ol.Collection()
	}
	
	me.render = function(){
		var params = window.location.search.split("?")[1].split("&");
		var l = params.length;
		for(var i=0; i<l; i++){
			var filter = params[i].split("=");
			if(filter[0] == "taskId"){
				me.taskId = filter[1];
			}else if(filter[0] == "type"){
				me.type = filter[1];
			}
		}
		
		initMap();
		
		switch(me.type){
			case "user":
				$("#userGrid").css("display", "block");
				$("#pointGrid").css("display", "none");
				$("#hotelGrid").css("display", "none");
				$("#hospitalGrid").css("display", "none");
				getUserGrid();
				break;
			case "point":
				$("#userGrid").css("display", "none");
				$("#pointGrid").css("display", "block");
				$("#hotelGrid").css("display", "none");
				$("#hospitalGrid").css("display", "none");
				getPointGrid();
				break;
			case "hotel":
				$("#userGrid").css("display", "none");
				$("#pointGrid").css("display", "none");
				$("#hotelGrid").css("display", "block");
				$("#hospitalGrid").css("display", "none");
				getHotelGrid();
				break;
			case "hospital":
				$("#userGrid").css("display", "none");
				$("#pointGrid").css("display", "none");
				$("#hotelGrid").css("display", "none");
				$("#hospitalGrid").css("display", "block");
				getHospitalGrid();
				break;
			default: 
				break;
		}
		
		// 修改弹窗样式
		$("#userLocationWinGrid").parent().parent().css({"background":"#eceef6", "padding":"10px"});
		$("#userLocationWinGrid").parent().parent().parent().parent().children(".ak-panel-header").css({"color":"black", "background":"#e8f5f5"});
	}
	
	/**
	  * 展示Tips消息提示框
	  */
	 function showTips(type, content){
		 ak.showTips({
           content: content,
           state: type,
           x: 'center',
           y: 'center',
           timeout: 4000
       });
	 }
	
	/**
	 * 初始化地图
	 */
	function initMap() {
	    //绘图层
		var bounds = [73.1922912597656, 3.15773010253906,
	                    135.398468017578, 53.8086738586426];
	    app.drawSource=new ol.source.Vector({
	        features:app.drawFeatures
	    });
	    app.drawLayer=new ol.layer.Vector({
	        source:app.drawSource
	    });
	    var baseMap= new ol.layer.Tile({
	        source: new ol.source.TileWMS({
	          url: 'http://10.144.15.214:8090/geoserver/mcsas_province/wms',
	          params: {'FORMAT': 'image/png', 
	                   'VERSION': '1.1.1',
	                   tiled: true,
	                STYLES: '',
	                LAYERS: 'mcsas_province:xingzhengquhua',
	             tilesOrigin: 73.1922912597656 + "," + 3.15773010253906
	          }
	        })
	      });
	      var projection = new ol.proj.Projection({
	          code: 'EPSG:4326',
	          units: 'degrees',
	          axisOrientation: 'neu',
	          global: true
	      });
	    map = new ol.Map({
	        controls: [],
	        layers: [
	            baseMap,
	            app.drawLayer
	        ],
	        target: 'map',
	          view: new ol.View({
			   center:[121.48,31.23],
			   zoom:13,
	           projection: projection
	        })
	    });
	    mapview=map.getView();
	    var mousePositionControl = new ol.control.MousePosition({
	        coordinateFormat:function(coor){
	            if(coor[0]<-180)
	                coor[0]=coor[0]+360;
	            coor=[coor[0]%180,coor[1]];
	            return ol.coordinate.createStringXY(4)(coor);
	        },
	        projection: 'EPSG:4326',
	        className: 'custom-mouse-position',
	        target: document.getElementById('location'),
	        undefinedHTML: '&nbsp;'
	    });
	    var zoomControl=new ol.control.Zoom({zoomInTipLabel:'放 大',zoomOutTipLabel:'缩 小'});
	    map.addControl(zoomControl);
	    map.addControl(mousePositionControl);
	    var fullcontrol=new ol.control.FullScreen({
	        tipLabel:'全屏'
	    });
	    map.addControl(fullcontrol);
	    app.select=new ol.interaction.Select({
	        layers:[app.drawLayer]
	    });
	    app.modify=new ol.interaction.Modify({
	        features: app.drawFeatures
	    });
	    app.modify.setActive(false);
	    map.addInteraction(app.select);
	    map.addInteraction(app.modify);
	    app.modify.on('modifyend',function(e){
			var feature= e.features.item(0);//绘制的图形
	        var geom=feature.getGeometry().getCoordinates();
	        var lon=geom[0];//经度
	        var lat=geom[1];//纬度
	        
			ak.confirm("保存当前位置信息到台账？", "确定？", function(action){
				if (action == "ok") {
					var coord = lon+","+lat;
					var id = "";
					var url = "";
					var params = null;
					switch(me.type){
						case "user":
							id = userGrid.getSelected().id;
							url = "/powerSupplyGuarantee/updateUser";
							params = {"ids":id, "geographyLocation":coord};
							myak.send(url,'GET',params,false,function(result){
								if(result.successful){
									showTips("success", "<b>提示</b> <br/>定位成功！");
								}else{
									showTips("danger", "<b>提示</b> <br/>定位失败，请重新定位！");
								}
								userGrid.reload();
							});
							break;
						case "point":
							id = pointGrid.getSelected().id;
							url = "/powerSupplyGuarantee/updatePoint";
							params = {"id":id, "location":coord};
							myak.send(url,'GET',params,false,function(result){
								if(result.successful){
									showTips("success", "<b>提示</b> <br/>定位成功！");
								}else{
									showTips("danger", "<b>提示</b> <br/>定位失败，请重新定位！");
								}
								pointGrid.reload();
							});
							break;
						case "hotel":
							id = hotelGrid.getSelected().id;
							url = "/powerSupplyGuarantee/updateHotel";
							params = {"id":id, "location":coord};
							myak.send(url,'GET',params,false,function(result){
								if(result.successful){
									showTips("success", "<b>提示</b> <br/>定位成功！");
								}else{
									showTips("danger", "<b>提示</b> <br/>定位失败，请重新定位！");
								}
								hotelGrid.reload();
							});
							break;
						case "hospital":
							id = hospitalGrid.getSelected().id;
							url = "/powerSupplyGuarantee/updateHospital";
							params = {"id":id, "location":coord};
							myak.send(url,'GET',params,false,function(result){
								if(result.successful){
									showTips("success", "<b>提示</b> <br/>定位成功！");
								}else{
									showTips("danger", "<b>提示</b> <br/>定位失败，请重新定位！");
								}
								hospitalGrid.reload();
							});
							break;
						default: 
							break;
					}
                }
                
				app.drawSource.removeFeature(feature);
				
				app.drawInteraction.setActive(false);
			    app.modify.setActive(false);
			});
		
		});
	    app.drawInteraction=new ol.interaction.Draw({
	        source: app.drawSource,
	        type: 'Point'
	    });
	    //绘制开始
	    app.drawInteraction.on('drawstart',function(e){

	    });
	    //绘制结束
	    app.drawInteraction.on('drawend',function(e){
	        var feature= e.feature;//绘制的图形
	        var geom=feature.getGeometry().getCoordinates();
	        var lon=geom[0];//经度
	        var lat=geom[1];//纬度
	        
	        //后面自己写。弹窗，是否确认
	        this.setActive(false);
	    });
	    app.drawInteraction.setActive(false);
	    map.addInteraction(app.drawInteraction);
	}

	//开始编辑
	function coordDraw()
	{
	    //测试编辑id
	    //editID='abc123';//这里的id应当传入你要编辑的数据的id，根据id将字段和图形对应起来
	    app.drawInteraction.setActive(true);
	    app.modify.setActive(true);
	    //
	}
	
	//停止编辑
	function stopEdit()
	{
	    app.drawInteraction.setActive(false);
	    app.modify.setActive(false);
	}
	
	/**
	 * 获取用户位置信息维护弹窗表格
	 */
	function getUserGrid(){
		userGrid.on('drawcell',function(e) {
		 	 var divImg = "";
			 switch (e.field) {
				 case "geographyLocation":
					 if(e.value != null){
						 e.cellHtml = "<span style='text-decoration:underline; cursor:pointer;'>修改</span>";
					 }else{
						 e.cellHtml = "<span style='text-decoration:underline; cursor:pointer;'>维护</span>";
					 }
					 break;
				 default:
					 break;
				}
			 
			 var geographyLocation = e.row.geographyLocation;
			 if(geographyLocation != null){
				 e.cellStyle = "color:#eb9f0d";
			 }
		});
		
		userGrid.on("cellclick", function(e){
			switch(e.field){
				case "geographyLocation":
					showTips("info", "<b>提示</b> <br/>请从左侧地图上单击鼠标进行定位。");
					coordDraw();
					break;
				default:
					break;
			 }
		 });
		
		userGrid.load(akapp.appname+'/powerSupplyGuarantee/getUserResult');
		var params = {"taskId":me.taskId};
		userGrid.load(params);
	}
	
	/**
	 * 获取驻点位置信息维护弹窗表格
	 */
	function getPointGrid(){
		pointGrid.on('drawcell',function(e) {
		 	 var divImg = "";
			 switch (e.field) {
				 case "location":
					 if(e.value != null){
						 e.cellHtml = "<span style='text-decoration:underline; cursor:pointer;'>修改</span>";
					 }else{
						 e.cellHtml = "<span style='text-decoration:underline; cursor:pointer;'>维护</span>";
					 }
					 break;
				 default:
					 break;
				}
			 
			 var location = e.row.location;
			 if(location != null){
				 e.cellStyle = "color:#eb9f0d";
			 }
		});
		
		pointGrid.on("cellclick", function(e){
			switch(e.field){
				case "location":
					showTips("info", "<b>提示</b> <br/>请从左侧地图上单击鼠标进行定位。");
					coordDraw();
					break;
				default:
					break;
			 }
		 });
		
		pointGrid.load(akapp.appname+'/powerSupplyGuarantee/getPointResult');
		var params = {"taskId":me.taskId};
		pointGrid.load(params);
	}
	
	/**
	 * 获取宾馆位置信息维护弹窗表格
	 */
	function getHotelGrid(){
		hotelGrid.on('drawcell',function(e) {
		 	 var divImg = "";
			 switch (e.field) {
				 case "location":
					 if(e.value != null){
						 e.cellHtml = "<span style='text-decoration:underline; cursor:pointer;'>修改</span>";
					 }else{
						 e.cellHtml = "<span style='text-decoration:underline; cursor:pointer;'>维护</span>";
					 }
					 break;
				 default:
					 break;
				}
			 
			 var location = e.row.location;
			 if(location != null){
				 e.cellStyle = "color:#eb9f0d";
			 }
		});
		
		hotelGrid.on("cellclick", function(e){
			switch(e.field){
				case "location":
					showTips("info", "<b>提示</b> <br/>请从左侧地图上单击鼠标进行定位。");
					coordDraw();
					break;
				default:
					break;
			 }
		 });
		
		hotelGrid.load(akapp.appname+'/powerSupplyGuarantee/getHotelResult');
		var params = {"taskId":me.taskId};
		hotelGrid.load(params);
	}
	
	/**
	 * 获取医院位置信息维护弹窗表格
	 */
	function getHospitalGrid(){
		hospitalGrid.on('drawcell',function(e) {
		 	 var divImg = "";
			 switch (e.field) {
				 case "location":
					 if(e.value != null){
						 e.cellHtml = "<span style='text-decoration:underline; cursor:pointer;'>修改</span>";
					 }else{
						 e.cellHtml = "<span style='text-decoration:underline; cursor:pointer;'>维护</span>";
					 }
					 break;
				 default:
					 break;
				}
			 
			 var location = e.row.location;
			 if(location != null){
				 e.cellStyle = "color:#eb9f0d";
			 }
		});
		
		hospitalGrid.on("cellclick", function(e){
			switch(e.field){
				case "location":
					showTips("info", "<b>提示</b> <br/>请从左侧地图上单击鼠标进行定位。");
					coordDraw();
					break;
				default:
					break;
			 }
		 });
		
		hospitalGrid.load(akapp.appname+'/powerSupplyGuarantee/getHospitalResult');
		var params = {"taskId":me.taskId};
		hospitalGrid.load(params);
	}
	
	return me;
})

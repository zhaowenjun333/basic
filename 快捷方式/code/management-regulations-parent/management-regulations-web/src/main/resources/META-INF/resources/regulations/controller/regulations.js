define([ 'akui','util','akapp','exportData' ], function( ak,myak47,akapp,exak ) { 
	/*重写之前faultTrip的页面逻辑*/
	var me={};
	me.datajson={};
	me.flagofgetidornot='';
	me.gridregulations=ak("gridregulations");
	me.aksplitter = ak("aksplitter");
	me.render = function(){ 
		ak.parse();
		me.aksplitter.hidePane(1);//折叠精确查询页面
		initbutton();
		initgrid();
	};
	function initbutton(){
		$("#searchImg").click(function(){
			 me.datajson.mh=$("#keywords")[0].value;//或者用val ()方法
			 me.gridregulations.load(me.datajson);
		});
		//精确搜索按钮单击事件 
		$(".jqss").click(function(){
			if($("#jqsstjsplitter").hasClass('show')){
				me.aksplitter.hidePane(1);
				$("#jqsstjsplitter").removeClass("show");
			}else{
				me.aksplitter.showPane(1);
				$("#jqsstjsplitter").addClass("show");
			}
			//初始化下拉框
			var issuedUnit=ak("issuedUnit1");//复选清空
			issuedUnit.on("closeclick",function(e){
				  var obj = e.sender;
		            obj.setText("");
		            obj.setValue("");
			});
//			类型
			var type=ak("type1");
			type.on("closeclick",function(e){
				  var obj = e.sender;
		            obj.setText("");
		            obj.setValue("");
			});
			//专业
			var specialty=ak("specialty1");
			specialty.on("closeclick",function(e){
				  var obj = e.sender;
		            obj.setText("");
		            obj.setValue("");
			});
			//年度
			var year1=ak("year1");
			year1.on("closeclick",function(e){
				var obj = e.sender;
				obj.setText("");
				obj.setValue("");
			});
			var path = '/regulations/initAllDrop';
			myak47.send(path, "GET", {}, false, function(data) {
				issuedUnit.setData(data.issuedUnit); //  
				type.setData(data.type); //  
				specialty.setData(data.specialty); // 
				year1.setData(data.year);
			});
			//绑定精确查询按钮
		$("#searchBtn").click(function(){ 
			me.datajson.issuedUnit =ak('issuedUnit1').value ;
		    me.datajson.year =ak('year1').getText(); 
		    me.datajson.fileNumber  =ak('fileNumber1').value ;
		    me.datajson.name  =ak('name1').value  ;
		    me.datajson.specialty  =ak('specialty1').value  ;
		    me.datajson.type =ak('type1').value   ;
		    me.datajson.mh=$("#keywords")[0].value;//或者用val ()方法
			 me.gridregulations.load(me.datajson);
		});
		//重置按钮事件
		$("#resetBtn").click(function(){
			/*把数据置空*/
			ak('issuedUnit1').setValue("");
			ak('year1').setValue("");
			ak('name1').setValue("");
			ak('specialty1').setValue("");
			ak('type1').setValue("");
			ak('fileNumber1').setValue("");
		});
		});
		$("#new").click(function(){
			me.flagofgetidornot='new';
			ak('editWindow').setTitle("规章制度-新增"); 
			ak('editWindow').show ( "center", "middle" );
			reset();
			/*ak('editWindow').on( "beforeclose", function(){
			} );*/
//			 下发单位
			var issuedUnit=ak("issuedUnit");
//			类型
			var type=ak("type");
			 //专业
			var specialty=ak("specialty");
			var year=ak("year");
			var path = '/regulations/initAllDrop';
			 myak47.send(path, "get", {}, true, function(data) {
				issuedUnit.setData(data.issuedUnit); //  
				type.setData(data.type); //  
				specialty.setData(data.specialty); //  
				year.setData(data.year);
			}); 
			/* $("#gridRiskUploadFile").on("click",function(e){
				 	 
				 
				})*/
		}); 
		//编辑
		$("#edit").click(function(){
			me.flagofgetidornot='edit';
			var dataedit =me.gridregulations.getSelecteds() ; 
			if(dataedit.length ==1){
				ak('editWindow').setTitle("规章制度-编辑"); 
				ak('editWindow').show ( "center", "middle" );
				ak('editWindow').on( "beforeclose", function(){
					 reset();
				} );
//				 下发单位
				var issuedUnit=ak("issuedUnit");
//				类型
				var type=ak("type");
				//专业
				var specialty=ak("specialty");
				var year=ak("year");
				var path = '/regulations/initAllDrop';
				myak47.send(path, "get", {}, true, function(data) {
					issuedUnit.setData(data.issuedUnit); //  
					type.setData(data.type); //  
					specialty.setData(data.specialty); //  
					year.setData(data.year);
					var issuedUnitcode="";
					for(var i=0;i<issuedUnit.data.length;i++){
			    		 if(ak("issuedUnit").data[i].text==dataedit[0].issuedUnit){
			    			 issuedUnitcode=issuedUnit.data[i].id;
			    		 }
			    	 }
					issuedUnit.setValue(issuedUnitcode);
					var specialtycode="";
					for(var i=0;i<specialty.data.length;i++){
			    		 if(ak("specialty").data[i].text==dataedit[0].specialty){
			    			 specialtycode=specialty.data[i].id;
			    		 }
			    	 }
					specialty.setValue(specialtycode);
					var typecode="";
					for(var i=0;i<type.data.length;i++){
			    		 if(ak("type").data[i].text==dataedit[0].type){
			    			 typecode=type.data[i].id;
			    		 }
			    	 }
					type.setValue(typecode);
					var yearcode="";
					for(var i=0;i<year.data.length;i++){
						if(ak("year").data[i].text==dataedit[0].year){
							yearcode=year.data[i].id;
						}
					}
					year.setValue(yearcode);
				});
//				设置数值
				ak('year').setText(dataedit[0].year);
				ak('fileNumber').setValue(dataedit[0].fileNumber);
				ak('name').setValue(dataedit[0].name);
				ak('id').setValue(dataedit[0].id);
				ak('attachment').setValue(dataedit[0].attachment);
			}else{
				showtips("请选择一条编辑",'info');
			}
		}); 
		
		$("#gridRiskUploadFile").on("click",function(e){ 
			if(me.flagofgetidornot=='new'){
				var path = '/regulations/getgridRiskID';
				myak47.send(path, "get", {}, true, function(data) { 
				me.gridRiskID = data.id; 
				_fileUpLoad(me.gridRiskID); 
				});
			}else{
				_fileUpLoad(me.gridregulations.getSelecteds()[0].id); 
			}
		})
		
		$(".words").click(function(){
			 
			var form = new ak.Form("#form");           
			var data = form.getData();       //获取表单多个控件的数据
			if(data.fileNumber.length> 25){
				showtips("文号最大长度25,请重新填写","info");return;
			}
			if(data.name.length> 25){
				showtips("名称最大长度25,请重新填写","info");return;
			}
			data.year=ak('year').getText();	 
			if(data.year =='' || data.name =='' || data.issuedUnit == '' ||data.type ==''){
				showtips("请输入带*必填字段","info");
				return ;
			}
			if(me.flagofgetidornot=='new'){
				//新增
				data.id=me.gridRiskID;  
				myak47.send('/regulations/save','GET',data,false,function(text){
					  if(text.successful== "false" ){
						  if(text.same == "true" ){
							  showtips("保存的记录已经存在请重新编辑","danger");return;
						  }else{
							  showtips("保存失败请稍后尝试","danger");return;
						  }
					  }
					  if(text.successful== "true"){
						  
						  showtips("保存成功","info");
						  //关闭窗口
						  ak('editWindow').close();
						  me.gridregulations.reload();
						  me.datajson.total =+ 1;
					  }
			  	}); 
			}else{ 
				//更新操作
				myak47.send('/regulations/update','GET',data,false,function(text){
					  if(text.successful== false ){
//						  text.successful=true;
						  showtips("保存失败请稍后尝试","info");return;
					  }else{
						  showtips("更新成功","info");
						  //关闭窗口
						  ak('editWindow').close();
						  me.gridregulations.reload();  
					  }
			  	}); 
			}
		});
		$("#delete").click(function(){
			var dataedit = new  Array();
			 dataedit =me.gridregulations.getSelecteds() ;
			if(dataedit.length==0){
				showtips("请选择数据","info");
				return ;
			}else{
				 var djson={};
				 var id="";
				 //把所有的id放到一个数组
				 for(var i=0;i<dataedit.length;i++){ 
				 	id+=dataedit[i].id+",";
				 }
				 id = id.substring(0,id.length-1);
				 djson.ids=id;
		        ak.confirm("确定删除记录？", "确定？",
		            function (action) {
		                if (action == "ok") {
			               	 myak47.send('/regulations/delete','GET',djson,false,function(text){
			               		if(text.successful=="true"){
		 							showtips("删除成功","info");
		 							setTimeout(function(){
//		 								me.gridregulations.load();
		 								if(dataedit.length == (me.datajson.total%me.datajson.pageSize)){
		 									me.datajson.pageIndex = me.datajson.pageIndex - 1;
		 								}
		 								me.gridregulations.load(me.datajson,function(e){
		 									me.gridregulations.setPageIndex(e);
		 									 me.datajson.pageSize = e.pageSize;
		 									 me.datajson.total = e.total;
		 									 me.datajson.pageIndex = e.pageIndex;
		 								 });
		 							},500);
		 						}else{
		 							showtips("更新失败，该记录已发生变化，请刷新页面","info");
		 						}
			          		});
		                } 
		         });
			}
		});
		
	}
	//初始化数据
	function initgrid(){
		me.gridregulations.on("cellclick", function (e) {
			if(e.field == "attachment"){ //  附件
				 var id =e.row.id; 
				 _fileUpLoad(id); 
			}
		});
		 me.gridregulations.on("drawcell", function (e) { 
				if(e.field == "attachment"){ //  附件
					var val = e.cellHtml;
					if(val != '' && val!= null){
						e.cellHtml = '<span style="background: url(./res/image/fujian.png) no-repeat;background-size: 100%;display: inline-block;vertical-align: middle;width: 9%;height: 21px;"></span>';
					}else{
						e.cellHtml = '';
					}
				}
			});
	 
		me.gridregulations.load(akapp.appname+"/regulations/showregulations");
		me.datajson.issuedUnit =ak('issuedUnit').value ;
	    me.datajson.year =ak('year').value;
	    me.datajson.number  =ak('fileNumber').value ;
	    me.datajson.name  =ak('name').value  ;
	    me.datajson.specialty  =ak('specialty').value  ;
	    me.datajson.type =ak('type').value   ;
	    me.datajson.mh=$("#keywords")[0].value;//或者用val ()方法
	    me.datajson.pageSize = 10;
	    me.datajson.pageIndex = 0;
		 me.gridregulations.load(me.datajson,function(e){
			 me.gridregulations.setPageIndex(e);
			 me.datajson.pageSize = e.pageSize;
			 me.datajson.total = e.total;
			 me.datajson.pageIndex = e.pageIndex;
		 });
	}
	
	/* 提示框*/
	 function showtips(aa,bb){
		 ak.showTips({
	            content:"<b>提示</b> <br/>"+ aa,
	            state: bb,
	            x: 'center',
	            y: 'center',
	            timeout: 2400
	        });
	 }
	 
	 /**
		 * 上传附件
		 * @param id
		 * @returns
		 */
		function _fileUpLoad(id){
			var iframe = null; 
			 var win = ak.open({
	           title: '附件上传',
//	           url:"http://10.144.15.224:9091/upload.html?url=/user/mcsas/regulations/"+id+"/&readOnly=true",
	           url:akapp.commonFileUpload+"upload.html?url=/user/mcsas/regulations/"+id+"/&readOnly=false",
	           showModal: false,
	           width: 1140,
	           height: 630,
	           allowResize: Boolean,       //允许尺寸调节
	           allowDrag: Boolean,         //允许拖拽位置
	           showCloseButton: Boolean,   //显示关闭按钮
	           showMaxButton: Boolean,     //显示最大化按钮
	           showModal: Boolean,         //显示遮罩
	           loadOnRefresh: false,       //true每次刷新都激发onload事件
	           onload: function () {       //弹出页面加载完成
	               iframe = this.getIFrameEl(); 
	               var data = {};   
	           },
	           ondestroy: function(e){
	           	var data = iframe.contentWindow.initialize.allreturn(); 
	           	var file = '';
	           	for(var i=0;i<data.length;i++){
	           		if(data.length >=1 && i==0){
	           			file += data[i].name;
	           		}else{
	           			file += ','+data[i].name
	           		}
	           	}
	        	ak("attachment").setValue(file);  
	           }
		   });
			 win.showAtPos("332px", "150px");
	        //关闭之前事件
	        win.on("beforeclose",function(){
//		        	var bbb = iframe.contentWindow.initialize.allreturn(); 
	        })
		}
		
		function reset (){
			ak("attachment").setValue("");
			ak("issuedUnit").setValue("");
			ak("year").setValue("");
			ak("fileNumber").setValue("");
			ak("name").setValue("");
			ak("specialty").setValue("");
			ak("type").setValue("");
		}
	return me ;
});

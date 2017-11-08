define(['akui','../common/js/app' ],function(ak,app){
	var util = {};
	
	util.send = function(p_path, p_method, p_data, p_async, p_callback){
		ak.send(app.appname+ p_path, p_method, p_data, p_async, p_callback);
	};
	
	//二级页面弹出窗口
	util.open = function(url,title,onload,ondestroy){
		var options = {
			    url: url,        //页面地址
			    title: title,      //标题
			    width: "87%",      //宽度
			    height: "93%",     //高度
			    allowResize: true,       //允许尺寸调节
			    allowDrag: true,         //允许拖拽位置
			    showCloseButton: true,   //显示关闭按钮
			    showMaxButton: true,     //显示最大化按钮
			    showModal: true,         //显示遮罩
			    loadOnRefresh: false,       //true每次刷新都激发onload事件
			    onload: onload,      //弹出页面加载完成
			    ondestroy: ondestroy  //弹出页面关闭前
			};
		var win = ak.open(options);
		win.showAtPos("206px", "55px");
	};
	util.openlittle = function(url,title,onload,ondestroy){
		var options = {
				url: url,        //页面地址
				title: title,      //标题
				width: "75%",      //宽度
				height: "60%",     //高度
				allowResize: true,       //允许尺寸调节
				allowDrag: true,         //允许拖拽位置
				showCloseButton: true,   //显示关闭按钮
				showMaxButton: true,     //显示最大化按钮
				showModal: true,         //显示遮罩
				loadOnRefresh: false,       //true每次刷新都激发onload事件
				onload: onload,      //弹出页面加载完成
				ondestroy: ondestroy  //弹出页面关闭前
		};
		var win = ak.open(options);
		win.showAtPos("290px", "210px");
	};
	
	util.getParams = function(){
		 var ohref=window.location.href;//获取url
		 return ak.getParams(ohref);
	 }
	 
	 util.getDailyParams = function(){
		 return ak.decode(decodeURIComponent(util.getParams().dailyParam));
	 }
	 
	 /**根据后台传入pageIndex设置页数并且删除recordId参数*/
//	 util.setPageIndex = function(e){
//			var pageIndex = e.result.pageIndex;
//			if(pageIndex){
//				e.sender._dataSource.setPageIndex(pageIndex);
//			}
//			
//		}
	 util.setPageIndex = function(e){
			var pageIndex = e.result.pageIndex;
			if(pageIndex){
				if(e.sender._dataSource){
					e.sender._dataSource.setPageIndex(pageIndex);
				}else if(e.sender.setPageIndex){
					e.sender.setPageIndex(pageIndex)
				}
			}
		}
	 util.removeLoadParam = function(e,recordId){
		 if(e.sender._dataSource){
			 e.sender._dataSource.loadParams[recordId] = null;
		 }else if(e.sender.loadParams){
			 e.sender.loadParams[recordId] = null
		 }
	 }
	 /*util.removeLoadParam = function(e,recordId){ 
		 e.sender.loadParams[recordId] = null;
	 }*/
	return util;
});
define(['akui','../common/js/app' ],function(ak,app){
	var util = {};
	
	util.send = function(p_path, p_method, p_data, p_async, p_callback){
		ak.send(app.appname+ p_path, p_method, p_data, p_async, p_callback);
	};
	
	util.sendwithoutappname = function(p_path, p_method, p_data, p_async, p_callback){
		ak.send( p_path, p_method, p_data, p_async, p_callback);
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
	
	// 日常业务相关
	util.getParams = function(){
		 var ohref=window.location.href;//获取url
		 return ak.getParams(ohref);
	 }
	 
	 util.getDailyParams = function(){
		 return ak.decode(decodeURIComponent(util.getParams().dailyParam));
	 }
	 
	 /**根据后台传入pageIndex设置页数并且删除recordId参数*/
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
	 
	 ak.MessageBox.buttonText.ok = "确认";
	
	 
	 
	 util.pageJumping = function(o){
			var n = {
					p:{// 默认
						id:"page_id",
						parentContainer:$("body"),
						returnPageRefresh:false,
						title:null,
						url:"",
						iframeName:"iframe_pageName",
						iframeId:"iframe_id",
						prev:".ak-prevPage",
						next:".ak-nextPage",
						ifParent:false,
						nextButton:true,
						defaultBut:true
					},
					c:function(){
						for(var a in o){
							this.p[a] = o[a];
						}
						if(this.p.defaultBut){
							this.b();
						}
					},
					b:function(){
						var a_next = "<div class='ak-nextPage ak-active'></div>";
						if(this.p.nextButton){
							this.p.parentContainer.append(a_next);
						}
					},
					f:function(){
						$(this.p.next).hide();
						var thisTitle = this.p.title,parentTitle = window.top.$("#curMenuTitle"),parentTitleText = parentTitle.text();
						var box = "<div id='"+this.p.id+"' class='page_box'>" +
								"<div class='ak-prevPage ak-active'></div>"+
								"<iframe id='"+this.p.iframeId+"' name='"+this.p.iframeName+"' src="+this.p.url+" class='page_iframe'></iframe>"+
							"</div>";
						var p_box = this.p.parentContainer;
						if(p_box.find('#'+this.p.id).length){
							p_box.find('#'+n.p.id).show();
							$(parentTitle).text(thisTitle||document.getElementById(n.p.iframeId).contentWindow.document.title);
						}else{
							this.p.parentContainer.append(box);
						}
						if(this.p.ifParent){
							if(thisTitle != null){
								$(parentTitle).text(thisTitle);	
							}
						}else{
							window[this.p.iframeName].onload = function(e){
								thisTitle = thisTitle||document.getElementById(n.p.iframeId).contentWindow.document.title;
								$(parentTitle).text(thisTitle);
							};
						}
						p_box.find(this.p.prev).off("click").on("click",function(){
							n.p.callback();
							parentTitle.text(parentTitleText);
							$(n.p.next).show();
							n.p.returnPageRefresh?p_box.find('#'+n.p.id).remove():p_box.find('#'+n.p.id).hide();
						})
					}
				}
			n.c(); 
			return n;
		}
	 
	return util;
});
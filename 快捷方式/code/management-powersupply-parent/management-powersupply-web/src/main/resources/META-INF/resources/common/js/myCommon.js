define(['akui', '../common/js/app' ],function(ak, app) {
	var me = {};
	me.importExcelData = function(taskId, excelTemplateName, fun) {
		var strUrl = app.appname + "/powerSupplyGuarantee/importTemplete.do";
		var form = $("<form>"); // 定义一个form表单
		form.attr('style', 'display:none'); // 在form表单中添加查询参数
		form.attr('id', 'excelUpload');
		form.attr('action', strUrl);
		form.attr('method', 'post');
		form.attr('enctype', 'multipart/form-data');
		form.attr('target', '_iframe');
		
		var input = $('<input>');
		input.attr('type', 'file');
		input.attr('id', 'excelFile');
		input.attr('name', 'excelFile');
		input.attr('accept','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel');
		form.append(input);
		
		input = $('<input>');
		input.attr('type', 'hidden');
		input.attr('name', 'taskId');
		input.attr('value', taskId);
		form.append(input);
		
		input = $('<input>');
		input.attr('type', 'hidden')
		input.attr('name', 'excelTemplateName');
		input.attr('value', excelTemplateName);
		form.append(input); 
		var iframe=$('<iframe>');
		iframe.attr('id','_iframe');
		iframe.attr('name','_iframe');
		iframe.attr('style', 'display:none'); 
		
		$('body').append(form); 
		$('body').append(iframe);
		document.getElementById('excelFile').onchange = function() {
			$("#excelUpload").submit();
			$("#excelUpload").remove();
		}
		document.getElementById('excelFile').click();
		
		$('#_iframe').load(function(){
			var body=$(window.frames['_iframe'].document.body);
			var returnInfo=body[0].textContent;
			fun(returnInfo);
			
			$("#_iframe").remove();
		});
		
		
	}

	me.downLoadExcelTemplate=function(templateCfgFileName){
		var strUrl = app.appname + "/powerSupplyGuarantee/downloadExcelTemplate";
		var form = $("<form>"); 
		form.attr('style', 'display:none'); 
		form.attr('id', 'excelDownload');
		form.attr('action', strUrl);
		form.attr('target', '_iframe');
		input = $('<input>');
		input.attr('type', 'hidden')
		input.attr('name', 'templateCfgFileName');
		input.attr('value', templateCfgFileName);
		form.append(input); 
		
		var iframe=$('<iframe>');
		iframe.attr('id','_iframe');
		iframe.attr('name','_iframe');
		iframe.attr('style', 'display:none'); 
		$('body').append(form); 
		$('body').append(iframe);
		$("#excelDownload").submit();
		$("#excelDownload").remove();
	}
	
	return me;
});
define(['akui','../common/js/app' ],function(ak,app){
	var exportData = {};
	exportData.exportPagesDataToExcel = function(grid,type,fileName) {
		var form = $('<form>');
		form.attr('style', 'display:none');
		form.attr('id', 'excelForm');
		form.attr('method', 'get');
		form.attr('target', 'excelIFrame');
		form.attr('action', app.appname+'/fault/exportDataResult');
		var input = $('<input>');
		input.attr('type', 'hidden');
		input.attr('name', 'excelData');
		input.attr('id', 'excelData');
		form.append(input);
		$('body').append(form);
		var columns = grid.getBottomColumns();
		function getColumns(columns) {
			var returnColums = [];
			for (var i = 0; i < columns.length; i++) {
				var column = columns[i];
				if (!column.visible) {
					continue;
				}
				if (!column.field) {
					columns.removeAt(i);
				} else {
					var c = {
						header : column.header,
						field : column.field
					};
					returnColums.push(c);
				}
			}
			return returnColums;
		}
		var columns = getColumns(columns); 
		var datas = grid.getSelecteds();
		var ids  = new Array() ;
		for(var  i  = 0 ;i < datas.length ; i++){
			ids[i ] =datas[i].id;
		}
		var json = {
			type : type,
			excelName : fileName,
			columns : JSON.stringify(columns),
			ids : JSON.stringify(ids)
		};
		document.getElementById("excelData").value = ak.encode(json);
		var excelForm = document.getElementById("excelForm");
		excelForm.submit();
	}
	return exportData;
})
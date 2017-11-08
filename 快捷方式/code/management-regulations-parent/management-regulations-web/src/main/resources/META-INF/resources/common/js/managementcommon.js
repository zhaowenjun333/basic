define(['akui','../common/js/app' ],function(ak,app){
	var exportData = {};
	
	exportData.exportPagesDataToExcel = function(grid, fileName) {
		var form = $('<form>');
		form.attr('style', 'display:none');
		form.attr('id', 'excelForm');
		form.attr('method', 'post');
		form.attr('target', 'excelIFrame');
		form.attr('action', app.appname+'/CommunicationMessageSend/exportDataResult');
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
		var datas = grid.getData();
		var json = {
			type : 'currentPage',
			excelName : fileName,
			columns : JSON.stringify(columns),
			datas : JSON.stringify(datas)
		};
		document.getElementById("excelData").value = ak.encode(json);
		var excelForm = document.getElementById("excelForm");
		excelForm.submit();
	}
	
	/**
	 * 导出数据到excel中
	 * @param grid 表格对象
	 * @param fileName 导出到excel生成的文件名
	 * @param cimName cim模型实体对象全类名
	 * @param where 拼接fql的过滤条件 
	 * @param select 拼接fql的查询字段
	 * @param filterMap 执行fql的占位符map对象
	 * @returns
	 */
	exportData.exportDatasToExcelWithFilter=function(grid, fileName, cimName, where, select, filterMap) {
		var form = $('<form>');
		form.attr('style', 'display:none');
		form.attr('id', 'excelForm');
		form.attr('method', 'post');
		form.attr('target', 'excelIFrame');
		form.attr('action', app.commonService+'/excelUtil/exportToExcel');
		var input = $('<input>');
		input.attr('type', 'hidden');
		input.attr('name', 'excelData');
		input.attr('id', 'excelData');
		form.append(input);
		$('body').append(form);
		var columesData = {};
		var columns = grid.getBottomColumns();
		for (var i = 0; i < columns.length; i++) {
			var column = columns[i];
			if (!column.visible) {
				continue;
			}
			if (!column.field) {
				columns.removeAt(i);
			} else {
				columesData[column.field] = column.header;
			}
		}
		var json = {
			type : "all",
			excelName : fileName,
			cimName : cimName,
			where : where,
			select : select,
			columesData : JSON.stringify(columesData),
			filterMap : JSON.stringify(filterMap)
		};

		document.getElementById("excelData").value = ak.encode(json);
		var excelForm = document.getElementById("excelForm");
		excelForm.submit();
	}
	
	return exportData;
});

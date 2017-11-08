define(['akui','../common/js/app' ],function(ak,app){
	var exportData = {};
	
	exportData.exportPagesDataToExcel = function(grid, type, params, fileName) {
		var form = $('<form>');
		form.attr('style', 'display:none');
		form.attr('id', 'excelForm');
		form.attr('method', 'get');
		form.attr('target', 'excelIFrame');
		form.attr('action', app.appname+'/powerSupplyGuarantee/exportDataResult');
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
				if (!column.field || column.header == "相关附件" || column.header == "简介图片" || column.header == "涉及站线" 
					|| column.header == "组织信息" || column.header == "附件" || column.header == "负责人照片" 
					|| column.header == "保电方案" || column.header == "应急预案" || column.header == "接线图"
					|| column.header == "保电区域" || column.header == "地理位置" || column.header == "位置") {
					columns.removeAt(i);
					i--;
				} else {
					if(column.header.indexOf("</span>") != -1){
						column.header = column.header.split("</span>")[1].trim();
					}
					
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
//		var datas = grid.getData();debugger;
		var json = {
			type : type,
			params : params,
			excelName : fileName,
			columns : JSON.stringify(columns)
//			,datas : JSON.stringify(datas)
		};
		document.getElementById("excelData").value = ak.encode(json);
		var excelForm = document.getElementById("excelForm");
		excelForm.submit();
	}
	
	
//	exportData.send = function(p_path, p_method, p_data, p_async, p_callback){
//		ak.send(app.appname+ p_path, p_method, p_data, p_async, p_callback);
//	};
	return exportData;
});










/**
 * 依据查询区域的div ID获取查询区域的过滤条件对象
 * @param divID  查询区域的div ID
 * @returns  查询区域的过滤条件对象
 *//*
function getSearchFilter(divID) {
	var filterObj = {};
	var jqcxkArray = $('#' + divID).find('input');
	for (var i = 0; i < jqcxkArray.length; ++i) {
		var _$oneInput = $(jqcxkArray[i]);
		var idType = _$oneInput.attr('id');
		if (typeof (idType) == 'undefined') {
			continue;
		}
		var fieldName = idType.split('$')[0];
		var idTypeFieldTextOrValue = idType.split('$')[1];
		if ('value' == idTypeFieldTextOrValue) {
			var fieldValue = _$oneInput.val();
			if (fieldValue != null && fieldValue != "") {
				filterObj[fieldName] = fieldValue;
			}
		}
	}
	return filterObj;
}
*//**
 * 导出表格数据到excel，导出的数据为 grid.getData() 数据
 * @param grid  表格对象
 * @param fileName 导出的excel表名
 * @returns
 *//*
function exportPagesDataToExcel(grid, fileName) {
	var form = $('<form>');
	form.attr('style', 'display:none');
	form.attr('id', 'excelForm');
	form.attr('method', 'post');
	form.attr('target', 'excelIFrame');
	form.attr('action', '../excelUtil/exportToExcel');
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
*//**
 * 导出数据到excel中
 * @param grid 表格对象
 * @param fileName 导出到excel生成的文件名
 * @param cimName cim模型实体对象全类名
 * @param where 拼接fql的过滤条件 
 * @param select 拼接fql的查询字段
 * @param filterMap 执行fql的占位符map对象
 * @returns
 *//*
function exportDatasToExcelWithFilter(grid, fileName, cimName, where, select, filterMap) {
	var form = $('<form>');
	form.attr('style', 'display:none');
	form.attr('id', 'excelForm');
	form.attr('method', 'post');
	form.attr('target', 'excelIFrame');
	form.attr('action', '../excelUtil/exportToExcel');
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
}*/
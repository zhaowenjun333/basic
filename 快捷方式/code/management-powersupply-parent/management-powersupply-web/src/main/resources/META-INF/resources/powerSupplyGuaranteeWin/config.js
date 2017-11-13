require([ '../ak/scripts/common', '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'powerSupplyGuaranteeWin' : appRoot + app.appname  + '/powerSupplyGuaranteeWin/controller/powerSupplyGuaranteeWin',
			'util' : appRoot + app.appname + '/common/js/util',
			'exportData' : appRoot + app.appname + '/common/js/managementcommon',
			'akapp' : appRoot + app.appname + '/common/js/app',
			'myCommon':appRoot + app.appname + '/common/js/myCommon'
		}
	});

	require([ 'powerSupplyGuaranteeWin' ], function(eo) {
		eo.render();
	});
});
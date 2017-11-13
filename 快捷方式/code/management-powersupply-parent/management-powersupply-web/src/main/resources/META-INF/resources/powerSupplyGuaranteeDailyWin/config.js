require([ '../ak/scripts/common', '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'powerSupplyGuaranteeDailyWin' : appRoot + app.appname  + '/powerSupplyGuaranteeDailyWin/controller/powerSupplyGuaranteeDailyWin',
			'util' : appRoot + app.appname + '/common/js/util',
			'exportData' : appRoot + app.appname + '/common/js/managementcommon',
			'akapp' : appRoot + app.appname + '/common/js/app',
			'myCommon':appRoot + app.appname + '/common/js/myCommon'
		}
	});

	require([ 'powerSupplyGuaranteeDailyWin' ], function(eo) {
		eo.render();
	});
});
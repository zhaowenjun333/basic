require([ '../ak/scripts/common', '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'powerSupplyGuaranteeMap' : appRoot + app.appname  + '/powerSupplyGuaranteeMap/controller/powerSupplyGuaranteeMap',
			'util' : appRoot + app.appname + '/common/js/util',
			'exportData' : appRoot + app.appname + '/common/js/managementcommon',
			'akapp' : appRoot + app.appname + '/common/js/app',
			'myCommon':appRoot + app.appname + '/common/js/myCommon'
		}
	});

	require([ 'powerSupplyGuaranteeMap' ], function(eo) {
		eo.render();
	});
});
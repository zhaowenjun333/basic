require([ '../ak/scripts/common', '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'powerSupplyGuarantee' : appRoot + app.appname  + '/powerSupplyGuarantee/controller/powerSupplyGuarantee',
			'util' : appRoot + app.appname + '/common/js/util',
			'exportData' : appRoot + app.appname + '/common/js/managementcommon',
			'akapp' : appRoot + app.appname + '/common/js/app'
		}
	});

	require([ 'powerSupplyGuarantee' ], function(eo) {
		eo.render();
	});
});
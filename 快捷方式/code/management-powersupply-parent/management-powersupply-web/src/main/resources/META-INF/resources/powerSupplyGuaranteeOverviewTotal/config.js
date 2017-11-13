require([ '../ak/scripts/common', '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'powerSupplyGuaranteeOverviewTotal' : appRoot + app.appname  + '/powerSupplyGuaranteeOverviewTotal/controller/powerSupplyGuaranteeOverviewTotal',
			'util' : appRoot + app.appname + '/common/js/util',
			'exportData' : appRoot + app.appname + '/common/js/managementcommon',
			'akapp' : appRoot + app.appname + '/common/js/app'
		}
	});

	require(['powerSupplyGuaranteeOverviewTotal'], function(eo) {
		eo.render();
	});
});
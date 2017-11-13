require([ '../ak/scripts/common', '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'powerSupplyGuaranteeOverviewDetails' : appRoot + app.appname  + '/powerSupplyGuaranteeOverviewDetails/controller/powerSupplyGuaranteeOverviewDetails',
			'util' : appRoot + app.appname + '/common/js/util',
			'exportData' : appRoot + app.appname + '/common/js/managementcommon',
			'akapp' : appRoot + app.appname + '/common/js/app'
		}
	});

	require(['powerSupplyGuaranteeOverviewDetails'], function(eo) {
		eo.render();
	});
});
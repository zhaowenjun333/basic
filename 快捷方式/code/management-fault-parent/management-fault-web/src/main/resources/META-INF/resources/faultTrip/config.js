require([ '../ak/scripts/common' , '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'faultTrip' : appRoot + app.appname  + '/faultTrip/controller/faultTrip',
			'util' : appRoot + app.appname + '/common/js/util',
			'akapp' : appRoot + app.appname + '/common/js/app',
			'exportData' : appRoot + app.appname + '/common/js/managementcommon'
		}
	});
	
	require([ 'faultTrip' ], function(eo) {
		eo.render();
	});
});

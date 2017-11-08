require([ '../ak/scripts/common' , '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'faultTrip_dc' : appRoot + app.appname + '/faultTrip_dc/controller/faultTrip_dc',
			'akapp' : appRoot + app.appname + '/common/js/app',
			'util' : appRoot + app.appname + '/common/js/util'
		}
	});

	 require([ 'faultTrip_dc' ], function(eo) {
		eo.render();
	}); 
});

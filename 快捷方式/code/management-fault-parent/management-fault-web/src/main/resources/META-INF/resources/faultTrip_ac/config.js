require([ '../ak/scripts/common' , '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'faultTrip_ac' : appRoot + app.appname + '/faultTrip_ac/controller/faultTrip_ac',
			'akapp' : appRoot + app.appname + '/common/js/app',
			'util' : appRoot + app.appname + '/common/js/util'
		}
	});

	 require([ 'faultTrip_ac' ], function(eo) {
		eo.render();
	}); 
});

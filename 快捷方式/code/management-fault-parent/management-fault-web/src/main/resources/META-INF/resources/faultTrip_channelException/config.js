require([ '../ak/scripts/common' , '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'faultTrip_channelException' : appRoot + app.appname + '/faultTrip_channelException/controller/faultTrip_channelException',
			'akapp' : appRoot + app.appname + '/common/js/app',
			'util' : appRoot + app.appname + '/common/js/util'
		}
	});

	 require([ 'faultTrip_channelException' ], function(eo) {
		eo.render();
	});  
});

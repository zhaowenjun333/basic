require([ '../ak/scripts/common' , '../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'faultTrip_edit_new' : appRoot + app.appname + '/faultTrip_edit_new/controller/faultTrip_edit_new',
			'akapp' : appRoot + app.appname + '/common/js/app',
			'util' : appRoot + app.appname + '/common/js/util'
		}
	});

	  require([ 'faultTrip_edit_new' ], function(eo) {
		eo.render();
	});  
});

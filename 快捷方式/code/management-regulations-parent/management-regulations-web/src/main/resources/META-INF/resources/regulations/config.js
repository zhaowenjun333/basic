require([ '../ak/scripts/common','../common/js/app' ], function(common,app) {

	require.config({
		paths : {
			'regulations' : appRoot + app.appname + '/regulations/controller/regulations',
			'util' : appRoot + app.appname + '/common/js/util',
			'akapp' : appRoot + app.appname + '/common/js/app',
			'exportData' : appRoot + app.appname + '/common/js/managementcommon'
		}
	});

	 require([ 'regulations' ], function(eo) {
		eo.render();
	}); 
});

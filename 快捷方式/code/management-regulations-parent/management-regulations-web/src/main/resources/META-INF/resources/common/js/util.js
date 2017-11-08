define(['akui','../common/js/app' ],function(ak,app){
	var util = {};
	
	util.send = function(p_path, p_method, p_data, p_async, p_callback){
		ak.send(app.appname+ p_path, p_method, p_data, p_async, p_callback);
	};
	return util;
});
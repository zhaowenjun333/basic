define(function(){
	var app = {};
	app.appname = "";		/*  /managementPowerSupplyWeb/  */
	app.commonService="/managementCommonWeb/";		/*  /managementCommonWeb/  Or  http://10.144.119.91:9999  */
	app.commonFileUpload = "/mcsas-dfs/";			/*  /mcsas-dfs/  Or  http://10.144.15.216/mcsas-dfs/  */
	app.messageSendService = "/managementCommunicationWeb/";	/*  /managementCommunicationWeb/  Or  http://10.144.139.45:8089  */
	app.heavyOverLoad = "http://10.144.47.16:10217/managementDailyWeb/"; // 重过载 在线监测
//	app.heavyOverLoad = "/managementDailyWeb/"; // 重过载 在线监测
	app.gridRisk = 'http://10.144.47.16:10217/managementPlatformOverviewWeb';
//	app.gridRisk = '/managementPlatformOverviewWeb/';
	app.warnPlatForm = "/managementPlatformOverviewWeb/"; /* /managementPlatformOverviewWeb/ Or http://10.144.139.45:7085 */
	app.weather = "/mcsas-gis-weather-business/";	/* /mcsas-gis-weather-business/ Or http://10.144.15.216:9090 */
	return app;
});
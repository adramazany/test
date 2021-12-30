if (typeof(obips_importmefirst_js) == "undefined") {
	obips_importmefirst_js = 1;
	if (!window.obips) {
		obips = function () {}
	}
	obips.masterResourceBundlePath = "master/master.xml";
	obips.getBaseURL = function () {
		var b = (window.location.port) ? ":" + window.location.port : "";
		var d = window.location.pathname;
		if (d.indexOf("/") == 0) {
			d = d.substr(1)
		}
		var a = "/" + d.split("/")[0] + "/";
		var c = window.location.protocol + "//" + window.location.hostname + b + a;
		return c
	};
	var baseURL = obips.getBaseURL();
	if (!obips.selenium) {
		obips.selenium = function () {};
		obips.selenium.registerObject = function () {};
		obips.selenium.unregisterObject = function () {}
	}
};

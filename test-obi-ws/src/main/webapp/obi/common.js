if (!window.saw_common_js) {
	saw_common_js = 1;
	if (typeof(saw) == "undefined") {
		saw = function () {}
	}
	saw.extend = function (c, a) {
		function b() {}
		b.prototype = a.prototype;
		c.prototype = new b();
		c.prototype.constructor = c;
		c.baseConstructor = a;
		c.superClass = a.prototype
	};
	saw.hooks = function () {};
	saw.json = function () {};
	saw.json.getClass = function (g, b) {
		if (g) {
			try {
				if (g.constructor && g.constructor.toString) {
					var f = g.constructor.toString();
					var a = f.match(/function\s*(\w+)/);
					if (a && a.length == 2) {
						return a[1]
					}
					a = f.match(/\[(\w+)\]/);
					if (a && a.length == 2) {
						return a[1]
					}
					if (b) {
						return f
					}
				}
			} catch (d) {
				var c = (d.message ? d.message : d + "");
				if (c.indexOf("Array.constructor") != -1) {
					return "Array"
				}
				throw d
			}
		}
		return "undefined"
	};
	saw.json._dateToString = function (a) {
		var b = function (c) {
			return c < 10 ? "0" + c : c
		};
		return a.getUTCFullYear() + "-" + b(a.getUTCMonth() + 1) + "-" + b(a.getUTCDate()) + "T" + b(a.getUTCHours()) + ":" + b(a.getUTCMinutes()) + ":" + b(a.getUTCSeconds()) + "Z"
	};
	saw.json.toString = function (e, c) {
		if (c == null) {
			c = true
		}
		if (c) {
			var l = window.JSON && window.JSON.stringify;
			if (l) {
				return l(e)
			}
		}
		function b(n) {
			return {
				obj: n,
				type: typeof(n)
			}
		}
		var g = [];
		g.push(b(e));
		var m = "";
		while (g.length > 0) {
			var f = g[g.length - 1];
			if (f.obj == null) {
				m += "null"
			} else {
				var a = f.type;
				if (a == "object") {
					var d = saw.json.getClass(f.obj);
					switch (d) {
					case "Array":
						a = "array";
						break;
					case "Date":
						a = "date";
						break;
					case "XMLDocument":
					case "Element":
					case "Text":
						a = "xml";
						break;
					case "undefined":
						if (f.obj.tagName == "XML") {
							a = "xml"
						}
						break
					}
				}
				switch (a) {
				case "string":
					m += '"' + saw.encode.encodeJavaScript(f.obj) + '"';
					break;
				case "xml":
					m += '"' + saw.encode.encodeJavaScript(saw.getXmlText(f.obj)) + '"';
					break;
				case "array":
					if (f.curIdx == null) {
						f.curIdx = 0;
						m += "["
					} else {
						f.curIdx += 1
					}
					if (f.curIdx < f.obj.length) {
						if (f.curIdx != 0) {
							m += ","
						}
						g.push(b(f.obj[f.curIdx]));
						continue
					} else {
						m += "]"
					}
					break;
				case "date":
					m += '"' + saw.json._dateToString(f.obj) + '"';
					break;
				case "object":
					if (!f.a) {
						m += "{";
						f.a = [];
						for (var j in f.obj) {
							f.a.push(j)
						}
						f.curIdx = 0;
						f.bFirst = true
					} else {
						f.curIdx += 1
					}
					if (f.curIdx < f.a.length) {
						var k = f.a[f.curIdx];
						var h = b(f.obj[k]);
						if (h.type != "function" || !c) {
							if (!f.bFirst) {
								m += ","
							} else {
								f.bFirst = false
							}
							m += '"' + saw.encode.encodeJavaScript(k) + '":';
							g.push(h)
						}
						continue
					} else {
						m += "}"
					}
					break;
				case "function":
					if (!c) {
						m += String(f.obj)
					}
					break;
				default:
					m += String(f.obj);
					break
				}
			}
			g.pop()
		}
		return m
	};
	saw.json.parse = function (sJson, bSkipSecurityCheck, bOnErrorThrow) {
		var fnNative = window.JSON && window.JSON.parse;
		if (!fnNative && !bSkipSecurityCheck && !/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/.test(sJson.replace(/\\./g, "@").replace(/"[^"\\\n\r]*"/g, ""))) {
			if (bOnErrorThrow) {
				throw new Error("Insecure JSON string: " + sJson)
			}
			return null
		}
		try {
			return (fnNative && !bSkipSecurityCheck) ? fnNative(sJson) : eval("(" + sJson + ")")
		} catch (x) {
			if (bOnErrorThrow) {
				throw new Error("Invalid JSON string: " + sJson)
			}
			return null
		}
	};
	saw.charsToHTMLEscape = ["<", "&lt;", ">", "&gt;", '"', "&quot;", " ", "&nbsp;"];
	saw.HTMLEscape = function (d) {
		var c = d;
		var b = saw.charsToHTMLEscape;
		var a = 0;
		while (a < b.length) {
			c = c.split(b[a++]).join(b[a++])
		}
		return c
	};
	saw.generateUniqueId = function () {
		var c = "";
		var a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (var b = 0; b < 5; b++) {
			c += a.charAt(Math.floor(Math.random() * a.length))
		}
		return c
	};
	if (!saw.xml) {
		saw.xml = function () {}
	}
	saw.xml.getInnerText = function (d) {
		var c = "";
		switch (d.nodeType) {
		case 1:
			var b = d.childNodes;
			for (var a = 0; a < b.length; ++a) {
				c += saw.xml.getInnerText(b[a])
			}
			break;
		case 2:
			c = d.nodeValue;
			break;
		case 3:
			c = d.nodeValue;
			break;
		case 4:
			c = d.nodeValue;
			break
		}
		return c
	};
	saw.xml.isEqual = function (b, a) {
		return saw.xml.getXMLString(b) == saw.xml.getXMLString(a)
	};
	saw.xml.getXMLString = function (a) {
		if ("ActiveXObject" in window) {
			return a.xml
		} else {
			return (new XMLSerializer()).serializeToString(a)
		}
	};
	var tWebScriptMap = new Array();
	saw.childWindows = new Array();
	WebScriptMapAdd = function () {
		var b = WebScriptMapAdd.arguments;
		if (tWebScriptMap) {
			var a = 0;
			while (a < b.length) {
				key = b[a++];
				value = b[a++];
				tWebScriptMap[key] = value
			}
		}
	};
	WebScriptMapGet = function (a) {
		return tWebScriptMap[a]
	};
	if (typeof(sawC2U) != "undefined" && typeof(sawP2U) != "undefined") {
		saw.commandToURL = sawC2U;
		saw.pageToURL = sawP2U
	} else {
		saw.commandToURL = function (a) {
			saw.logicErrorAlert('Improperly setup page. Unable to determine route to command: "' + a + '"')
		};
		saw.pageToURL = function (a) {
			saw.logicErrorAlert('Improperly setup page. Unable to determine route to page: "' + a + '"')
		}
	}
	saw.arrayRemoveIndex = function (c, a) {
		if (a == 0) {
			return c.slice(a + 1, c.length)
		} else {
			if (a == c.length - 1) {
				return c.slice(0, a)
			}
		}
		var b = new Array();
		return b.concat(c.slice(0, a), c.slice(a + 1, c.length))
	};
	saw.arrayIndexOf = function (d, c) {
		var a = -1;
		for (var b = 0; b < d.length; b++) {
			if (d[b] == c) {
				a = b;
				break
			}
		}
		return a
	};
	var g_tBackSlashRE = new RegExp("\\\\", "gi");
	function escapechar(d, f, e) {
		d = d.replace(g_tBackSlashRE, "\\\\");
		var b = "g";
		if (e) {
			b += "i"
		}
		var a = new RegExp(f, b);
		return d.replace(a, "\\" + f)
	}
	function escapequotes(c, a) {
		c = c.replace(g_tBackSlashRE, "\\\\");
		if (!a) {
			var b = new RegExp('"', "g");
			c = c.replace(b, '\\"');
			b = new RegExp("'", "g");
			c = c.replace(b, "\\'")
		} else {
			var b = new RegExp("'", "g");
			c = c.replace(b, "\x27")
		}
		return c
	}
	saw.ltrim = function (b) {
		var a = /\s*((\S+\s*)*)/;
		return b.replace(a, "$1")
	};
	saw.rtrim = function (b) {
		var a = /((\s*\S+)*)\s*/;
		return b.replace(a, "$1")
	};
	saw.trim = function (a) {
		return saw.ltrim(saw.rtrim(a))
	};
	function SAWMoreInfo(c, b) {
		var e = (b == null) ? saw.getEventTarget(c) : document.getElementById(b);
		var a;
		if (e.tagName == "IMG") {
			a = e;
			do {
				e = saw.getNextSiblingElement(e)
			} while (e && e.nodeType != 1)
		}
		if (!e) {
			return false
		}
		if (e.getAttribute("compresssrc") == undefined) {
			e = saw.getNextSiblingElement(e)
		}
		if (!e) {
			return false
		}
		if (!a) {
			a = saw.getPreviousSiblingElement(e);
			if (!a) {
				return false
			}
			while (a.nodeType != 1 || a.tagName != "IMG") {
				a = saw.getPreviousSiblingElement(a);
				if (!a) {
					return false
				}
			}
		}
		var d = a.src;
		a.src = e.getAttribute("compresssrc");
		a.alt = "";
		e.setAttribute("compresssrc", d);
		e.style.display = e.style.display == "none" ? "" : "none";
		return false
	}
	saw.popupWindow = function (d, b, e, f, h) {
		var a = e == null ? 912 : e;
		var g = f == null ? 684 : f;
		var k = h == null ? "resizable=yes,scrollbars=yes" : h;
		var j = "width=" + a + ",height=" + g + "," + k;
		var c = b == null ? "_blank" : b;
		window.name = obips.launcher ? obips.launcher.launchNewWindow(d, c, j) : window.open(d, c, j);
		window.saw.registerChildWindows(window[name])
	};
	saw.popupHelp = function (a) {
		saw.popupWindow(a, null, null, null, "menubar=0,toolbar=1,scrollbars=1,location=0,status=0,resizable=1")
	};
	saw.registerChildWindows = function (a) {
		saw.childWindows[saw.childWindows.length] = a
	};
	saw.closeChildWindows = function () {
		for (var a = 0; a < saw.childWindows.length; a++) {
			if (saw.childWindows[a] && saw.childWindows[a].open && !saw.childWindows[a].closed) {
				saw.childWindows[a].close()
			}
		}
	};
	saw.fixResourceUrlPath = function (c) {
		if (c.charAt(0) != "/" && c.toLowerCase().indexOf("http") != 0) {
			var b = saw.commandToURL("dummy");
			if (b.charAt(0) == "/") {
				var a = saw.getFolder(b) + "/";
				c = a + c
			}
		}
		return c
	};
	saw.dynamicLoadCSS = function (g, b, c, f) {
		var h = false;
		b = saw.fixResourceUrlPath(b);
		c = c ? c : b;
		if (!f) {
			f = saw.getExistingResourceFiles(g, saw.ResourceTypes.CSS)
		}
		if (f && f[c]) {
			return
		}
		if (!g) {
			g = document
		}
		var d = g.getElementsByTagName("head")[0];
		if (!saw.userAgent.is_ie || document.styleSheets.length < 30) {
			css = document.createElement("link");
			css.setAttribute("rel", "stylesheet");
			css.setAttribute("type", "text/css");
			css.setAttribute("href", b);
			d.appendChild(css)
		} else {
			if (g.styleSheets.length == 30) {
				var e = g.createElement("style");
				d.appendChild(e);
				saw.userAgent.is_ie11up ? e.sheet.addImport(b) : e.styleSheet.addImport(b);
				saw._styleElementToImportMoreCSS = g.styleSheets[30]
			} else {
				if (!saw._importedCSSs) {
					saw._importedCSSs = {}
				} else {
					if (saw._importedCSSs[c]) {
						return
					}
				}
				saw._importedCSSs[c] = true;
				var e = saw._styleElementToImportMoreCSS || g.styleSheets[30];
				if (h) {
					if (g.styleSheets.length > 31) {
						saw.logicErrorAlert("Too many stylesheets in the page! " + g.styleSheets.length)
					}
					if (e.href) {
						saw.logicErrorAlert("Too many stylesheets in the page! (30 currently)")
					}
					if (e.imports.length > 30) {
						saw.logicErrorAlert("Too many imports in the page! " + e.imports.length)
					}
				}
				try {
					e.addImport(b)
				} catch (a) {}
			}
		}
		if (f) {
			f[c] = true
		}
	};
	saw.loadedJsFiles = {};
	saw.ResourceTypes = function () {};
	saw.ResourceTypes.CSS = "css";
	saw.ResourceTypes.SCRIPT = "script";
	saw.getExistingResourceFiles = function (f, h, k) {
		f = f ? f : document;
		if (!k) {
			k = new Object
		}
		var b = f;
		var g = h == saw.ajax.ResourceTypes.SCRIPT;
		if (g) {
			var l = "script";
			var n = "src"
		} else {
			var l = "link";
			var n = "href"
		}
		var e = b.getElementsByTagName(l);
		for (var d = 0; d < e.length; d++) {
			var a = e[d].getAttribute(n);
			if (a) {
				k[a] = true
			}
		}
		if (!g && saw.userAgent.is_ie) {
			for (var d = 0; d < f.styleSheets.length; d++) {
				var m = f.styleSheets[d];
				if (m.imports.length > 0) {
					for (var c = 0; c < m.imports.length; c++) {
						if (m.imports[c].href) {
							k[m.imports[c].href] = true
						}
					}
				}
			}
		}
		if (g) {
			for (var d in saw.loadedJsFiles) {
				k[d] = true
			}
			saw.loadedJsFiles = k
		}
		return k
	};
	saw.isElementAttachToDocumentBody = function (a) {
		for (var b = a; b; b = b.parentNode) {
			if (b == b.ownerDocument.body) {
				return true
			}
			if (b.nodeType == 1 || b.nodeType == 3) {}
			else {
				return false
			}
		}
		return false
	};
	saw.globalToLocal = function (a, b) {
		return {
			x: a.clientX - b.getBoundingClientRect().left,
			y: a.clientY - b.getBoundingClientRect().top
		}
	};
	saw.calcAbsoluteLeft = function (d, e) {
		if (saw.isRToL()) {
			return NQWGetTopRight(d, e)
		}
		var a = 0;
		try {
			if (!e) {
				var b = d.parentNode;
				while (b && b != d.ownerDocument.body && (b.nodeType == 1 || b.nodeType == 3)) {
					a -= b.scrollLeft;
					b = b.parentNode
				}
			}
			while (d != null) {
				a += d.offsetLeft;
				d = d.offsetParent
			}
		} catch (c) {}
		return a
	};
	saw.calcAbsoluteTop = function (c, d) {
		var e = 0;
		try {
			if (!d && c) {
				var a = c.parentNode;
				while (a && a != c.ownerDocument.body && (a.nodeType == 1 || a.nodeType == 3)) {
					e -= a.scrollTop;
					a = a.parentNode
				}
			}
			while (c != null) {
				e += c.offsetTop;
				c = c.offsetParent
			}
		} catch (b) {}
		return e
	};
	saw.calcAbsoluteRight = function (b, c) {
		var a = 0;
		if (b != null) {
			a += b.offsetWidth
		}
		return a + saw.calcAbsoluteLeft(b, c)
	};
	saw.calcAbsoluteBottom = function (a, b) {
		var c = 0;
		if (a != null) {
			c += a.offsetHeight
		}
		return c + saw.calcAbsoluteTop(a, b)
	};
	saw.calcRelativePositionX = function (b, a) {
		return (a.clientX + window.document.body.scrollLeft - saw.calcAbsoluteLeft(b))
	};
	saw.calcRelativePositionY = function (b, a) {
		return (a.clientY + window.document.body.scrollTop - saw.calcAbsoluteTop(b))
	};
	saw.calcAbsoluteLeftOffsetParent = function (b) {
		if (saw.isRToL()) {
			return NQWGetAbsoluteRightOffsetParent(b)
		}
		var a = 0;
		while (b != null) {
			if (b.style.position == "absolute") {
				a += b.offsetLeft
			}
			b = b.offsetParent
		}
		return a
	};
	saw.calcAbsoluteTopOffsetParent = function (a) {
		var b = 0;
		while (a != null) {
			if (a.style.position == "absolute") {
				b += a.offsetTop
			}
			a = a.offsetParent
		}
		return b
	};
	saw.GlobalPostProcess = function () {};
	saw.GlobalPostProcess.g_aQueue = [];
	saw.GlobalPostProcess.g_nTimeOut = 100;
	saw.GlobalPostProcess.g_sTimeOutID = null;
	saw.GlobalPostProcess.g_nCurrentExecution = 0;
	saw.GlobalPostProcess.g_aCurrentExecution = [];
	saw.GlobalPostProcess.PostProcess = function (b, a) {
		this.oCallBack = b;
		this.oParams = a
	};
	saw.GlobalPostProcess.isCheckedIn = function () {
		return (saw.GlobalPostProcess.g_nCurrentExecution > 0 ? true : false)
	};
	saw.GlobalPostProcess.checkIn = function () {
		++saw.GlobalPostProcess.g_nCurrentExecution;
		saw.GlobalPostProcess.g_aCurrentExecution.push(0)
	};
	saw.GlobalPostProcess.checkOut = function () {
		saw.GlobalPostProcess.g_aCurrentExecution.splice(0, 1);
		--saw.GlobalPostProcess.g_nCurrentExecution
	};
	saw.GlobalPostProcess.registerPostProcess = function (a) {
		saw.GlobalPostProcess.g_aQueue.push(a);
		if (saw.GlobalPostProcess.g_nCurrentExecution == 0) {
			saw.GlobalPostProcess._kickoffPostProcessTimer()
		}
	};
	saw.GlobalPostProcess._kickoffPostProcessTimer = function () {
		if (saw.GlobalPostProcess.g_sTimeOutID) {
			window.clearTimeout(saw.GlobalPostProcess.g_sTimeOutID);
			saw.GlobalPostProcess.g_sTimeOutID = null
		}
		var a = saw.GlobalPostProcess.g_nTimeOut;
		if (saw.userAgent.is_ie && !saw.userAgent.is_ie8up || saw.userAgent.is_ie8up && saw.userAgent.ie_docMode < 8) {
			a = 2000
		}
		saw.GlobalPostProcess.g_sTimeOutID = window.setTimeout(saw.GlobalPostProcess._executPostProcess, saw.GlobalPostProcess.g_nTimeOut)
	};
	saw.GlobalPostProcess._executPostProcess = function () {
		saw.GlobalPostProcess.g_sTimeOutID = null;
		if (saw.GlobalPostProcess.isCheckedIn()) {
			return
		}
		saw.GlobalPostProcess.checkIn();
		while (saw.GlobalPostProcess.g_aQueue.length > 0) {
			var a = saw.GlobalPostProcess.g_aQueue.splice(0, 1);
			if (a && a.length > 0) {
				a = a[0]
			}
			if (a.oCallBack) {
				a.oCallBack.functionPointer.call(a.oCallBack.thisContext, a.oParams)
			}
		}
		saw.GlobalPostProcess.checkOut();
		if (saw.GlobalPostProcess.g_aQueue.length > 0) {
			saw.GlobalPostProcess._kickoffPostProcessTimer()
		}
	};
	saw.calcLongestStringPixelWidth = function (o, c, n, d) {
		var l = (c ? c : document.body);
		var j = l.ownerDocument;
		var m = j.createElement("div");
		m.className = "tempPaddingTableDiv" + (d ? " " + d : "");
		var k = j.createElement("table");
		if (saw.getSessionInfos().isScreenReaderMode()) {
			k.setAttribute("role", "presentation")
		}
		k.className = "tempPaddingTable";
		k.cellSpacing = 0;
		k.cellPadding = 0;
		var h = j.createElement("tbody");
		m.appendChild(k);
		l.appendChild(m);
		k.appendChild(h);
		var p = [];
		for (var f = 0; f < o.length; f++) {
			var g = j.createElement("tr");
			var b = j.createElement("td");
			b.noWrap = true;
			b.className = "tempPaddingTableTD" + (d ? " " + d : "");
			p.push(b);
			h.appendChild(g);
			g.appendChild(b);
			var q = o[f];
			saw.setInnerText(b, q + "")
		}
		var e = new obips.Callback(null, saw._calcLongestStringPixelWidthHandler);
		var a = new saw.GlobalPostProcess.PostProcess(e, {
				tHiddingDiv: m,
				tContainer: l,
				aTestLengthTableTD: p,
				oCallback: n
			});
		saw.GlobalPostProcess.registerPostProcess(a)
	};
	saw._calcLongestStringPixelWidthHandler = function (e) {
		var b = e;
		var d = -1;
		for (var c = 0; c < b.aTestLengthTableTD.length; c++) {
			var a = b.aTestLengthTableTD[c].offsetWidth;
			if (a > d) {
				d = a
			}
		}
		var f = saw.getParentElement(b.tHiddingDiv);
		if (f && f == b.tContainer) {
			b.tContainer.removeChild(b.tHiddingDiv)
		}
		if (b.oCallback) {
			b.oCallback.functionPointer.call(b.oCallback.thisContext, d, b.oCallback.oParams)
		}
	};
	saw.calcStringPixelWidth = function (l, b, c) {
		var j = (b ? b : document.body);
		var g = j.ownerDocument;
		var k = g.createElement("div");
		k.className = "tempPaddingTableDiv" + (c ? " " + c : "");
		var h = g.createElement("table");
		if (saw.getSessionInfos().isScreenReaderMode()) {
			h.setAttribute("role", "presentation")
		}
		h.className = "tempPaddingTable";
		h.cellSpacing = 0;
		h.cellPadding = 0;
		var e = g.createElement("tbody");
		k.appendChild(h);
		j.appendChild(k);
		h.appendChild(e);
		var d = g.createElement("tr");
		var a = g.createElement("td");
		a.noWrap = true;
		a.className = "tempPaddingTableTD" + (c ? " " + c : "");
		e.appendChild(d);
		d.appendChild(a);
		saw.setInnerText(a, l + "");
		var f = a.offsetWidth;
		j.removeChild(k);
		return f
	};
	var g_NQWPrevDocumentClick = null;
	var g_NQWCurrentPopup = null;
	var g_nNQWCurrentlyInResizeHandler = 0;
	saw.isCurrentlyInResizeHandler = function () {
		if (is_ie) {
			return (g_nNQWCurrentlyInResizeHandler > 0) ? true : false
		} else {
			return false
		}
	};
	saw.checkInResizeHandler = function () {
		++g_nNQWCurrentlyInResizeHandler
	};
	saw.checkOutResizeHandler = function () {
		--g_nNQWCurrentlyInResizeHandler
	};
	function NQWOverlaps(g, d) {
		var j = saw.calcAbsoluteTop(g);
		var h = saw.calcAbsoluteTop(d);
		var f = saw.calcAbsoluteLeft(g);
		var c = saw.calcAbsoluteLeft(d);
		var e = g.tagName == "OBJECT" ? g.getAttribute("height") : g.clientHeight;
		var a = d.tagName == "OBJECT" ? d.getAttribute("height") : d.clientHeight;
		var k = g.tagName == "OBJECT" ? g.getAttribute("width") : g.clientWidth;
		var b = d.tagName == "OBJECT" ? d.getAttribute("width") : d.clientWidth;
		if (d.tagName == "OBJECT") {
			h = saw.calcAbsoluteTop(d) - a
		}
		if ((parseInt(j) + parseInt(e) <= parseInt(h) || parseInt(h) + parseInt(a) <= parseInt(j)) || (parseInt(f) + parseInt(k) <= parseInt(c) || parseInt(c) + parseInt(b) <= parseInt(f))) {
			return false
		}
		return true
	}
	saw.isDescendantOf = function (b, a) {
		for (var c = a; c != null; c = c.parentNode) {
			if (c == b) {
				return true
			}
		}
		return false
	};
	saw.hideTypeByTag = function (a, d, f, h) {
		var g = document.getElementsByTagName(a);
		for (var j = 0; j != g.length; ++j) {
			var e = g[j];
			if ((!h || h(e)) && (f == true || NQWOverlaps(d, e))) {
				if (e.oldVisibility == null && !saw.isDescendantOf(d, e)) {
					var b = e;
					var c = true;
					while (b != null) {
						if (b.style != undefined && b.style.display == "none") {
							c = false;
							break
						}
						b = saw.getParentElement(b)
					}
					if (c) {
						e.oldVisibility = e.style.visibility;
						e.underObject = d;
						e.style.visibility = "hidden"
					}
				}
			}
		}
	};
	saw.hideCharts = function (b, a) {
		if (saw.userAgent.is_linux || saw.userAgent.is_solaris) {
			saw.hideTypeByTag("EMBED", b, a)
		}
	};
	saw.hideAllCharts = function (a) {
		saw.hideCharts(a, true)
	};
	saw.showTypeByTag = function (b, d) {
		var e = document.getElementsByTagName(b);
		for (var c = 0; c != e.length; ++c) {
			var a = e[c];
			if (a.oldVisibility != null && a.underObject == d) {
				a.style.visibility = a.oldVisibility;
				a.oldVisibility = null
			}
		}
	};
	saw.showCharts = function (b, a) {
		if (saw.userAgent.is_linux || saw.userAgent.is_solaris) {
			saw.showTypeByTag("EMBED", b, a)
		}
	};
	saw.getDocumentClientDims = function () {
		var a = new Object();
		if (self.innerHeight) {
			a.x = self.innerWidth;
			a.y = self.innerHeight
		} else {
			if (document.documentElement && document.documentElement.clientHeight) {
				a.x = document.documentElement.clientWidth;
				a.y = document.documentElement.clientHeight
			} else {
				if (document.body) {
					a.x = document.body.clientWidth;
					a.y = document.body.clientHeight
				}
			}
		}
		return a
	};
	saw.setStyleLeft = function (a, b) {
		if (saw.isRToL()) {
			a.style.right = b
		} else {
			a.style.left = b
		}
	};
	saw.Position = function (c, b, a, f, d, e) {
		this.refDOMElement = c;
		this.dir = b;
		this.evt = a;
		this.xPos = f;
		this.yPos = d;
		if (a) {
			this.yPos = saw.getEventPageY(a);
			this.xPos = saw.getEventPageX(a);
			this.evtDocument = saw.getEventOwnerDocument(a)
		}
		this.isXPosDistanceToRight = e
	};
	saw.Position.createByDOMElement = function (c, b, a, d) {
		return new saw.Position(c, b, null, a, d)
	};
	saw.Position.createByEvent = function (a) {
		return new saw.Position(null, null, a)
	};
	saw.Position.createByKeydownEvent = function (a) {
		var g = new saw.Position(null, null, a);
		if (a && a.type === "keydown" && a.keyCode === 13) {
			var d = saw.getEventTarget(a);
			if (d) {
				var e = saw.calcAbsoluteTop(d);
				var c = Math.round(e + d.offsetHeight / 2);
				var b = saw.calcAbsoluteLeft(d);
				var f = Math.round(b + d.offsetWidth / 2);
				g.yPos = c;
				g.xPos = f
			}
		}
		return g
	};
	saw.Position.createByRawCoordinates = function (c, a, b) {
		return new saw.Position(null, null, null, c, a, b)
	};
	saw.positionPopup = function (a, g) {
		if (!g) {
			g = new saw.Position()
		}
		if (g.refDOMElement) {
			if (g.dir == "center") {
				var d = saw.calcAbsoluteTop(g.refDOMElement);
				var c = d + Math.round((g.refDOMElement.offsetHeight - a.clientHeight) / 2);
				var b = saw.calcAbsoluteLeft(g.refDOMElement);
				var f = b + Math.round((g.refDOMElement.offsetWidth - a.clientWidth) / 2);
				saw.positionByRawCoordinates(a, f, c)
			} else {
				saw.positionByDOMElement(g.refDOMElement, a, g.dir, g.xPos, g.yPos)
			}
		} else {
			var c = g.yPos;
			var f = g.xPos;
			if (g.evtDocument) {
				if (g.evtDocument != a.ownerDocument) {
					var e = saw.Region.getRegion(saw.getDocumentOwnerWindow(g.evtDocument).frameElement);
					f += e.left - saw.getDocumentScrollLeft(g.evtDocument);
					c += e.top - saw.getDocumentScrollTop(g.evtDocument)
				}
			}
			saw.positionByRawCoordinates(a, f, c, g.isXPosDistanceToRight)
		}
	};
	saw.positionByRawCoordinates = function (e, f, c, k) {
		var j = e.ownerDocument;
		if (c == null) {
			c = Math.round((saw.getClientHeight(j) - e.clientHeight) / 2)
		} else {
			c -= saw.getDocumentScrollTop(j);
			if (c + e.clientHeight > saw.getClientHeight(j)) {
				c = Math.max(0, saw.getClientHeight(j) - e.clientHeight - 5)
			}
		}
		var g = saw.calcAbsoluteTopOffsetParent(e.offsetParent);
		e.style.top = Math.max(saw.getDocumentScrollTop(j) + c, 0) - g + "px";
		if (!saw.isRToL()) {
			if (f == null) {
				f = Math.round((saw.getClientWidth(j) - e.clientWidth) / 2)
			} else {
				f -= saw.getDocumentScrollLeft(j);
				if (f + e.clientWidth > saw.getClientWidth(j)) {
					f = Math.max(0, saw.getClientWidth(j) - e.clientWidth - 5)
				}
			}
			var m = saw.calcAbsoluteLeftOffsetParent(e.offsetParent);
			e.style.left = Math.max(saw.getDocumentScrollLeft(j) + f, 0) - m + "px"
		} else {
			var m = NQWGetAbsoluteRightOffsetParent(e.offsetParent);
			var l = e.clientWidth;
			var d = saw.getClientWidth(j);
			var b = 0;
			if (j.body.offsetLeft < 0) {
				b = j.body.offsetWidth - d - j.documentElement.scrollLeft;
				if (b < 0) {
					b = 0
				}
			}
			var a = d + b;
			var h = b;
			if (f == null) {
				e.style.right = h + Math.round((d - l) / 2) + "px"
			} else {
				if (k) {
					f = h + f
				} else {
					f = a - f
				}
				if (f + l > a) {
					e.style.right = a - l - m + "px"
				} else {
					e.style.right = f - m + "px"
				}
			}
		}
	};
	saw.positionByDOMElement = function (e, k, b, j, g) {
		if (j == null) {
			j = 0
		}
		if (g == null) {
			g = 0
		}
		var l = e;
		var f = saw.calcAbsoluteTop(l);
		var m = saw.calcAbsoluteTopOffsetParent(k.offsetParent);
		var o = l.offsetHeight;
		var d = k.offsetHeight;
		if (e.ownerDocument != k.ownerDocument) {
			var n = saw.Region.getRegion(saw.getDocumentOwnerWindow(e.ownerDocument).frameElement);
			f += n.top - saw.getDocumentScrollTop(e.ownerDocument)
		}
		var h = 0;
		k.style.position = "absolute";
		var p = 0;
		if (k.popupScrollContainer) {
			var c = document.getElementById(k.popupScrollContainer);
			if (null != c) {
				p = c.scrollTop
			}
		}
		var a = saw.getClientHeight() + saw.getDocumentScrollTop();
		if ("right" == b || "left" == b) {
			if (f + d < a) {
				k.style.top = f + Math.min(g, a - f - d) - m - p + "px"
			} else {
				k.style.top = Math.max(h, f + o - g - d) - m - p + "px"
			}
		} else {
			if (f - d >= h && f + o + d > a) {
				k.style.top = Math.max(h, f - g - d) - m - p + "px"
			} else {
				k.style.top = f + o + Math.min(g, a - f - o - d) - m - p + "px"
			}
		}
		NQWPositionPopupH(l, k, b, j)
	};
	function NQWPositionPopupH(a, c, b, d) {
		if (saw.isRToL()) {
			NQWPositionPopupRToL(a, c, b, d)
		} else {
			NQWPositionPopupLToR(a, c, b, d)
		}
	}
	function NQWPositionPopupLToR(j, g, a, e) {
		var k = saw.calcAbsoluteLeft(j);
		var n = saw.calcAbsoluteLeftOffsetParent(g.offsetParent);
		var d = j.offsetWidth;
		var h = g.offsetWidth;
		var m = 0;
		var f = 0;
		if (j.ownerDocument != g.ownerDocument) {
			var l = saw.Region.getRegion(saw.getDocumentOwnerWindow(j.ownerDocument).frameElement);
			k += l.left - saw.getDocumentScrollLeft(j.ownerDocument)
		}
		if (g.popupScrollContainer) {
			var c = document.getElementById(g.popupScrollContainer);
			if (null != c) {
				f = c.scrollLeft
			}
		}
		var b = saw.getClientWidth() + saw.getDocumentScrollLeft();
		if ("right" == a) {
			if (k + d + h < b) {
				g.style.left = k + d + Math.min(e, b - k - d - h) - n - f + "px"
			} else {
				g.style.left = Math.max(saw.getDocumentScrollLeft(), k - h - e) - n - f + "px"
			}
		} else {
			if ("left" == a) {
				if (k - h > 0) {
					g.style.left = k - h - Math.min(e, k - h) - n - f + "px"
				} else {
					g.style.left = 0 - n - f + "px"
				}
			} else {
				if (k - h >= m && k + h > b) {
					g.style.left = Math.max(saw.getDocumentScrollLeft(), k - h + d - e) - n - f + "px"
				} else {
					if (k + h > b) {
						g.style.left = Math.max(0, b - h) - n - f + "px"
					} else {
						g.style.left = k + Math.min(e, b - k - h) - n - f + "px"
					}
				}
			}
		}
		if (parseInt(g.style.left) + h > parseInt(0 + b)) {
			g.style.left = b - h - f > 0 ? b - h - f + "px" : "0px"
		}
	}
	function NQWPositionPopupRToL(h, g, a, d) {
		var k = h.offsetWidth;
		var m = g.offsetWidth;
		var f = h.ownerDocument.documentElement.clientWidth;
		var e = NQWGetTopRight(h);
		var c = 0;
		var n = NQWGetAbsoluteRightOffsetParent(g.offsetParent);
		if (h.ownerDocument.body.offsetLeft < 0) {
			c = h.ownerDocument.body.offsetWidth - f - h.ownerDocument.documentElement.scrollLeft;
			if (c < 0) {
				c = 0
			}
		}
		var b = f + c - 16;
		var j = c;
		if ("left" == a) {
			if (e - m < j) {
				g.style.right = j - n + "px"
			} else {
				g.style.right = e - Math.min(d, e - m - j) - m - n + "px"
			}
		} else {
			if ("right" == a) {
				if (e + k + m > b) {
					g.style.right = b - m - n + "px"
				} else {
					g.style.right = e + k + Math.min(d, b - e - k - m) - n + "px"
				}
			} else {
				var l = 0;
				if (e + m <= b) {
					l = e + Math.min(d, b - e - m)
				} else {
					if (e + k <= b) {
						l = e + k - m - d
					} else {
						l = b - m
					}
				}
				if (l < j) {
					l = j
				}
				g.style.right = l - n + "px"
			}
		}
	}
	function NQWGetAbsoluteRightOffsetParent(b) {
		var a = 0;
		while (b != null && b.style.position != "absolute") {
			b = b.offsetParent
		}
		if (b != null) {
			a += b.offsetWidth;
			do {
				a += b.offsetLeft;
				if (b.offsetLeft < 0) {
					break
				}
			} while ((b = b.offsetParent) != null)
		}
		return 0 == a ? 0 : b.ownerDocument.documentElement.clientWidth - a
	}
	function NQWGetTopRight(c, g) {
		var d = c.ownerDocument;
		var j = c.offsetWidth;
		var h = false;
		var f = false;
		var e = c.offsetLeft;
		if (!g && (saw.userAgent.is_nav || saw.userAgent.is_ie8up)) {
			var b = 0;
			var a = c.parentNode;
			while (a && a != d.body && (a.nodeType == 1 || a.nodeType == 3)) {
				b += a.scrollLeft;
				a = a.parentNode
			}
			if (saw.userAgent.is_nav && b < 0) {
				b = -b
			}
			j += b
		}
		if (!is_nav && "a" == c.tagName.toLowerCase() && c.children[0] && "img" == c.children[0].tagName.toLowerCase()) {
			e = c.children[0].offsetLeft
		}
		if (e >= 0) {
			j += e
		} else {
			h = true
		}
		while (c = c.offsetParent) {
			if (c.offsetLeft < 0) {
				if (h) {
					continue
				}
				if (!f) {
					f = true;
					j += c.offsetLeft
				} else {
					break
				}
			} else {
				h = false;
				if (f) {
					f = false
				}
				j += c.offsetLeft
			}
		}
		if (!is_nav && j > d.documentElement.clientWidth) {
			j -= d.documentElement.scrollWidth - d.documentElement.clientWidth
		}
		return d.documentElement.clientWidth - j
	}
	saw.clearPopup = function (a) {
		if (!g_NQWCurrentPopup) {
			return
		}
		g_NQWCurrentPopup.style.display = "none";
		saw.removeEventListener(document, "click", saw.clearPopup);
		if (is_nav) {
			saw.showCharts(g_NQWCurrentPopup)
		}
		g_NQWCurrentPopup = null
	};
	saw.popupObject = function (c, b, d, a) {
		if (g_NQWCurrentPopup != null) {
			if (g_NQWCurrentPopup == d) {
				return
			} else {
				saw.clearPopup(c)
			}
		}
		if (!b && c) {
			b = saw.getEventTarget(c)
		}
		d.style.display = "block";
		d.style.visibility = "hidden";
		NQWPositionPopup(b, d, a);
		d.style.visibility = "visible";
		saw.addEventListener(document, "click", saw.clearPopup);
		if (is_nav) {
			saw.hideCharts(d)
		}
		g_NQWCurrentPopup = d;
		if (c) {
			saw.stopEventPropagation(c)
		}
	};
	function NQWWaitCursor() {
		document.body.style.cursor = "wait";
		return true
	}
	function NQWDefaultCursor() {
		document.body.style.cursor = "";
		return true
	}
	saw.submitForm = function (d, b, a) {
		if (b != null) {
			d.action = b
		}
		var g = d.target;
		if (a && a.targetWindow) {
			d.target = a.targetWindow
		}
		if (typeof(onBeforeSubmitNQWForm) != "undefined" && onBeforeSubmitNQWForm != null) {
			onBeforeSubmitNQWForm(d, null)
		}
		if (saw.shouldAddSessionSecurityCodeInput(d, a)) {
			saw.addSessionSecurityCodeInput(d)
		}
		try {
			if (a && a.checkPopupBlocker && saw.userAgent.is_safari && d.target && d.target != "_self") {
				if (d.target == "_blank") {
					d.target = new Date().valueOf()
				}
				var c = window.open(saw.getEmptyHtm(), d.target, "");
				if (!c || c.closed) {
					alert(obips.ResourceManager.getSingleton().getResource("common/saw.header.xml", "kmsgLauncherPopupBlocked").getString())
				}
				d.submit()
			} else {
				d.submit()
			}
		} catch (f) {}
		if (typeof(onAfterSubmitNQWForm) != "undefined" && onAfterSubmitNQWForm != null) {
			onAfterSubmitNQWForm(d, null)
		}
		d.target = g
	};
	saw.shouldAddSessionSecurityCodeInput = function (d, g) {
		if (g && g.ensureFreshUrl) {
			return true
		} else {
			if (g && typeof(g.ensureFreshUrl) != "undefined" && !g.ensureFreshUrl) {
				return false
			} else {
				var h = saw.ltrim(d.action + "");
				var c = saw.ltrim(saw.commandToURL(""));
				var a = h.indexOf(c);
				if (a > 0) {
					var e = "";
					if (typeof(baseURL) != "undefined") {
						e += baseURL + c
					} else {
						e += saw.getBaseURL() + "/" + c
					}
					a = h.indexOf(saw.ltrim(e));
					if (a != 0) {
						var f = d.ownerDocument;
						if (f) {
							var j = saw.createForm("_newtempsawform", c, f);
							var b = j.action + "";
							a = h.indexOf(saw.ltrim(b));
							f.body.removeChild(j)
						}
					}
					if (a == 0) {
						return true
					}
				}
			}
		}
		return false
	};
	function NQWSubmitFormWithView(b, c, a) {
		if (a != null) {
			b.action = a
		}
		if (typeof(onBeforeSubmitNQWForm) != "undefined" && onBeforeSubmitNQWForm != null) {
			onBeforeSubmitNQWForm(b, c)
		}
		if (saw.shouldAddSessionSecurityCodeInput(b, null)) {
			saw.addSessionSecurityCodeInput(b)
		}
		b.submit();
		if (typeof(onAfterSubmitNQWForm) != "undefined" && onAfterSubmitNQWForm != null) {
			onAfterSubmitNQWForm(b, c)
		}
	}
	function NQWMoreInfo(d, b) {
		var e = b == null ? d.nextSibling : document.getElementById(b);
		var a = d.firstChild;
		var c = a.src;
		a.src = e.compresssrc;
		a.alt = "";
		e.compresssrc = c;
		e.style.display = e.style.display == "none" ? "" : "none"
	}
	function NQWHasClassName(b, d) {
		if (b.className == null) {
			return false
		}
		var a = b.className.split(" ");
		for (var c = 0; c < a.length; c++) {
			if (a[c] == d) {
				return true
			}
		}
		return false
	}
	function NQWAddClassName(a, b) {
		if (a.className == null) {
			a.className = b
		} else {
			a.className += " " + b
		}
	}
	function NQWRemoveClassName(c, d) {
		if (c.className == null) {
			return
		}
		var a = c.className.split(" ");
		for (var b = 0; b != a.length; ) {
			if (a[b] == d) {
				a = saw.arrayRemoveIndex(a, b)
			} else {
				++b
			}
		}
		c.className = a.join(" ")
	}
	function NQWSetHasClassName(a, c, b) {
		if (b) {
			if (!NQWHasClassName(a, c)) {
				NQWAddClassName(a, c)
			} else {}
		} else {
			NQWRemoveClassName(a, c)
		}
	}
	function NQWToggleClassName(a, b) {
		if (NQWHasClassName(a, b)) {
			NQWRemoveClassName(a, b)
		} else {
			NQWAddClassName(a, b)
		}
	}
	function NQWGetContainer(c, a, b, d) {
		while (c != null) {
			if (c.tagName != null && c.tagName == a) {
				if (b == null || (c[b] == d) || (b == "className" && NQWHasClassName(c, d))) {
					return c
				}
			}
			c = c.parentNode
		}
		return c
	}
	saw.getURLArgs = function () {
		var c = new Object();
		var a = location.search.substring(1).split("&");
		for (var d = 0; d < a.length; d++) {
			var f = a[d].indexOf("=");
			if (f == -1) {
				continue
			}
			var b = unescape(a[d].substring(0, f));
			var e = unescape(a[d].substring(f + 1));
			if (b != "_scid") {
				c[b] = e
			}
		}
		return c
	};
	function NQWNull() {}
	function NQWGetRadioGroupValue(b) {
		for (var a = 0; a < b.length; ++a) {
			if (b[a].checked) {
				return b[a].value
			}
		}
		return null
	}
	function NQWSetRadioGroupValue(b, c) {
		for (var a = 0; a < b.length; ++a) {
			b[a].checked = (b[a].value == c)
		}
	}
	function NQWGetSelectValue(a) {
		if (a.selectedIndex >= 0 && a.selectedIndex < a.options.length) {
			return a.options[a.selectedIndex].value
		} else {
			if (a.options.length > 0) {
				return a.options[0]
			} else {
				return null
			}
		}
	}
	function NQWSetSelectValue(b, c) {
		for (var a = 0; a < b.options.length; ++a) {
			if (b.options[a].value == c) {
				b.selectedIndex = a;
				break
			}
		}
	}
	saw.disableSelectionOnNode = function (a) {
		if (!a) {
			a = document
		}
		if (is_ie) {
			a.onselectstart = function () {
				return false
			}
		} else {
			a.onmousedownFunc = a.onmousedown;
			a.onmousedown = function (b) {
				b.preventDefault()
			}
		}
	};
	saw.enableSelectionOnNode = function (a) {
		if (!a) {
			a = document
		}
		if (is_ie) {
			a.onselectstart = null
		} else {
			a.onmousedown = a.onmousedownFunc
		}
	};
	saw.findAncestorElement = function (c, a) {
		if (c == null) {
			return null
		}
		for (var b = c.parentNode; b != null; b = b.parentNode) {
			if (b.tagName == a) {
				return b
			}
		}
		return null
	};
	saw.findAncestorElementOrSelf = function (c, a) {
		for (var b = c; b != null; b = b.parentNode) {
			if (b.tagName == a) {
				return b
			}
		}
		return null
	};
	saw.findFirstChildElement = function (c, a) {
		for (var b = c.firstChild; b != null; b = b.nextSibling) {
			if (b.tagName == a) {
				return b
			}
		}
		return null
	};
	saw.findFirstDescendant = function (d, a, c, g) {
		var e = d.getElementsByTagName(a);
		if (e.length > 0) {
			if (c) {
				var f = c.toLowerCase() == "class";
				for (var b = 0; b < e.length; b++) {
					if (f && e[b].className == g || e[b].getAttribute(c) == g) {
						return e[b]
					}
				}
			} else {
				return e[0]
			}
		}
		return null
	};
	saw.moveAllChildren = function (a, b) {
		while (a.firstChild) {
			b.appendChild(a.firstChild)
		}
	};
	saw.findFormInputElement = function (b, c) {
		for (var a = 0; a < b.elements.length; a++) {
			if (b.elements[a].name == c) {
				return b.elements[a]
			}
		}
		return null
	};
	saw.createForm = function (d, a, c) {
		if (!c) {
			c = document
		}
		var b = c.createElement("FORM");
		b.name = d;
		b.method = "post";
		if (a) {
			b.action = a
		}
		c.body.appendChild(b);
		return b
	};
	saw.createInput = function (c, e, d, b) {
		if (!b) {
			b = document
		}
		var a = b.createElement("INPUT");
		a.setAttribute("name", c);
		a.setAttribute("type", d);
		if (e) {
			a.value = e
		} else {
			a.value = ""
		}
		return a
	};
	saw.addInput = function (b, d, f, e, c) {
		var a = saw.findFormInputElement(b, d);
		if (a == null) {
			a = saw.createInput(d, f, e, c)
		}
		if (f) {
			a.value = f
		} else {
			a.value = ""
		}
		b.appendChild(a)
	};
	saw.addHiddenInput = function (a, c, d, b) {
		saw.addInput(a, c, d, "hidden", b)
	};
	saw.addSessionSecurityCodeInput = function (a) {
		saw.addHiddenInput(a, "_scid", window.obips_scid)
	};
	saw.convertFormToString = function (b) {
		var d = "";
		for (i = 0; i < b.elements.length; i++) {
			var a = b.elements[i];
			var c = (a.tagName == "INPUT" && a.type == "checkbox" && !a.checked);
			d += saw.encodeURIComponent(b.elements[i].name);
			d += "=";
			if (!c) {
				d += saw.encodeURIComponent(b.elements[i].value)
			}
			if ((i + 1) < b.elements.length) {
				d += "&"
			}
		}
		return d
	};
	saw.createFormFromString = function (k) {
		var b = saw.createForm("tempForm");
		var j = "";
		var g = k.indexOf("#");
		if (g >= 0) {
			j = k.substr(g);
			k = k.substr(0, g)
		}
		var f = k;
		if (k.indexOf("?") >= 0) {
			f = k.substr(k.indexOf("?") + 1)
		}
		var h = f.split("&");
		for (var d = 0; d < h.length; d++) {
			var n = h[d].indexOf("=");
			if (n != -1) {
				var c = saw.decodeURIComponent(h[d].substring(0, n));
				var m = saw.decodeURIComponent(h[d].substring(n + 1, h[d].length));
				saw.addHiddenInput(b, c, m)
			}
		}
		var l = /^.+\?[^\=]+\&/;
		var e = l.exec(k);
		if (e) {
			var a = k.indexOf("&");
			b.action = k.substr(0, a)
		} else {
			if (k.indexOf("=") > 0) {
				var o = k.indexOf("?");
				b.action = o == -1 ? k : k.substr(0, o)
			} else {
				b.action = k
			}
		}
		b.action += j;
		return b
	};
	saw.runThisURL = function (e, b) {
		if (e.length > 2047 || b && b.ensureFreshUrl) {
			var d = saw.createFormFromString(e);
			if (d) {
				if (saw.userAgent.is_appleapp && d.mobileNav) {
					var c = d.mobileNav.value;
					if (c) {
						d.action += (d.action.indexOf("?") >= 0 ? "&" : "?") + "mobileNav=" + saw.encodeURIComponent(c);
						d.removeChild(d.mobileNav)
					}
				}
				if (saw.shouldAddSessionSecurityCodeInput(d, b)) {
					saw.addSessionSecurityCodeInput(d)
				}
				d.submit()
			}
		} else {
			try {
				window.location.href = e
			} catch (a) {}
		}
		return false
	};
	saw.getFrameWindowByName = function (a) {
		return window.frames[a]
	};
	saw.getLastPathPart = function (a) {
		var c = a.lastIndexOf("/");
		var d = a;
		while (c >= 0) {
			var b = 1;
			while (c >= b && a.charAt(c - b) == "\\") {
				b++
			}
			if (c < b || (b - 1) % 2 == 0) {
				break
			}
			d = d.substr(0, c - b + 1);
			c = d.lastIndexOf("/")
		}
		return a.substr(c + 1, a.length - c - 1)
	};
	saw.getItemName = function (a) {
		return saw.pathPartToItemName(saw.getLastPathPart(a))
	};
	saw.getFolder = function (a) {
		var b = saw.getLastPathPart(a);
		return a.substr(0, a.lastIndexOf(b) - 1)
	};
	saw.getPathComponents = function (a) {
		if (a.charAt(0) != "/") {
			saw.logicErrorAlert("Invalid Path: " + a);
			return new Array()
		}
		var b = saw.getLastPathPart(a);
		var d = a.substr(0, a.lastIndexOf(b) - 1);
		if (!d) {
			d = "/"
		}
		var c = saw.pathPartToItemName(b);
		return new Array(d, b, c)
	};
	saw.getPathParts = function (a) {
		var c = new Array();
		var b = a;
		do {
			var d = saw.getPathComponents(b);
			c.push(d[1]);
			b = d[0]
		} while (d[0] != "/");
		return c
	};
	saw.pathPartToItemName = function (c) {
		var a = c;
		var b = new RegExp("\\\\\\*", "gi");
		a = a.replace(b, "*");
		b = new RegExp("\\\\\\?", "gi");
		a = a.replace(b, "?");
		b = new RegExp("\\\\\\\\", "gi");
		a = a.replace(b, "\\");
		b = new RegExp("\\\\\\/", "gi");
		a = a.replace(b, "/");
		b = new RegExp("\\\\\\~", "gi");
		a = a.replace(b, "~");
		return a
	};
	saw.captionToItemName = function (a) {
		var c = a;
		var b = new RegExp("\\\\", "gi");
		c = c.replace(b, "\\\\");
		b = new RegExp("\\*", "gi");
		c = c.replace(b, "\\*");
		b = new RegExp("\\?", "gi");
		c = c.replace(b, "\\?");
		b = new RegExp("\\/", "gi");
		c = c.replace(b, "\\/");
		b = new RegExp("\\~", "gi");
		c = c.replace(b, "\\~");
		return c
	};
	saw.infoPopupGetDiv = function (b) {
		var d = saw.getEventTarget(b);
		var a = saw.findAncestorElementOrSelf(d, "TD");
		if (a) {
			var c = a.getElementsByTagName("DIV");
			if (c.length > 0) {
				return c[0]
			}
		}
		return null
	};
	saw.showInfoPopup = function (a) {
		var b = saw.getEventTarget(a);
		var c = saw.infoPopupGetDiv(a);
		if (saw.getCurrentStyle(c).display == "none") {
			saw.popupObject(a, b, c, "right")
		} else {
			c.style.display = "none"
		}
	};
	saw.string = function () {};
	saw.string._reWhitespaceString = /^\s*$/;
	saw.string.isBlankLine = function (a) {
		return a == null || (a.replace(saw.string._reWhitespaceString, "").length == 0)
	};
	saw.string.noCaseEquals = function (b, a) {
		return (b.toLowerCase() == a.toLowerCase())
	};
	saw.string._numValidateRE = new RegExp("(^\\s*-?\\d\\d*\\s*$)");
	saw.string.validateInt = function (a) {
		return saw.string._numValidateRE.test(a)
	};
	saw.isBlankLine = saw.string.isBlankLine;
	saw.noCaseEquals = saw.string.noCaseEquals;
	saw.validateInt = saw.string.validateInt;
	saw.encode = function () {};
	saw.encode._strRegExp = new RegExp("(['\"\\\\])", "g");
	saw.encode.encodeHTML = function (a) {
		if (a == null) {
			return ""
		}
		return a.replace(/&/g, "&amp;").replace(/\'/g, "&#039;").replace(/\"/g, "&quot;").replace(/>/g, "&gt;").replace(/</g, "&lt;")
	};
	saw.encode.encodeHTMLAttrValue = function (a) {
		return saw.encode.encodeHTML(a)
	};
	saw.encode.encodeXML = function (a) {
		return saw.encode.encodeHTML(a)
	};
	saw.encode._jsEscapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
	saw.encode._jsCharEscape = {
		"\b": "\\b",
		"\t": "\\t",
		"\n": "\\n",
		"\f": "\\f",
		"\r": "\\r",
		'"': '\\"',
		"\\": "\\\\"
	};
	saw.encode.encodeJavaScript = function (b) {
		var a = saw.encode._jsEscapable;
		a.lastIndex = 0;
		return a.test ? b.replace(a, function (c) {
			return saw.encode._jsCharEscape[c] || "\\u" + ("0000" + c.charCodeAt(0).toString(16)).slice(-4)
		}) : b
	};
	saw._s_oXUIValidateXSARerExp = new RegExp("^XSA\\s*\\(\\s*'.*'\\s*\\)$");
	saw.isXSA = function (a) {
		return saw._s_oXUIValidateXSARerExp.test(a)
	};
	saw.ensureQuotedTableName = function (a) {
		if (a.match(/^\"(.*)\"$/) || saw.isXSA(a)) {
			return a
		} else {
			return '"' + a + '"'
		}
	};
	saw.unquoteSubjectAreaSql = function (a) {
		if (a.match(/^\"(.*)\"$/)) {
			return RegExp.$1
		} else {
			return a
		}
	};
	saw.encodeHTML = saw.encode.encodeHTML;
	saw.encodeHTMLAttrValue = saw.encode.encodeHTMLAttrValue;
	saw.encodeXML = saw.encode.encodeXML;
	function NQWIsInAnswers() {
		try {
			return window.bAnswers || parent.bAnswers
		} catch (a) {
			return false
		}
	}
	function NQWIsInSWE() {
		try {
			return saw.checkObjectReference(top.SWEPersonalizationGotoview)
		} catch (a) {
			return false
		}
	}
	saw.getFirstChildElement = function (b) {
		if (b != null && b.childNodes != null) {
			for (var a = 0; a < b.childNodes.length; a++) {
				if (b.childNodes[a].nodeType == 1) {
					return b.childNodes[a]
				}
			}
		}
		return null
	};
	saw.getLastChildElement = function (b) {
		if (b != null && b.childNodes != null) {
			for (var a = b.childNodes.length - 1; a >= 0; a--) {
				if (b.childNodes[a].nodeType == 1) {
					return b.childNodes[a]
				}
			}
		}
		return null
	};
	saw.getNextSiblingElement = function (a) {
		var b = (a ? a.nextSibling : null);
		while (b && b.nodeType != 1) {
			b = b.nextSibling
		}
		return b
	};
	saw.getPreviousSiblingElement = function (a) {
		var b = (a ? a.previousSibling : null);
		while (b && b.nodeType != 1) {
			b = b.previousSibling
		}
		return b
	};
	saw.getChildElementsLength = function (c) {
		var b = 0;
		for (var a = 0; a < c.childNodes.length; a++) {
			if (c.childNodes[a].nodeType == 1) {
				++b
			}
		}
		return b
	};
	saw.getChildElementByIndex = function (c, a) {
		for (var b = 0; b < c.childNodes.length; b++) {
			if (c.childNodes[b].nodeType == 1) {
				if (a == 0) {
					return c.childNodes[b]
				}
				a--
			}
		}
		return null
	};
	saw.getChildElementLogicalIndex = function (e, d) {
		var a = -1;
		var b = -1;
		for (var c = 0; c < e.childNodes.length; c++) {
			if (e.childNodes[c].nodeType == 1) {
				++b;
				if (e.childNodes[c] == d) {
					a = b;
					break
				}
			}
		}
		return a
	};
	saw.getCellNumber = function (c, a) {
		for (var b = 0; b < c.cells.length; b++) {
			if (c.cells[b] == a) {
				break
			}
		}
		if (b == c.cells.length) {
			saw.logicErrorAlert("could not find cell:" + a + " in row:" + c);
			return null
		}
		return b
	};
	saw.getRowNumber = function (b, c) {
		for (var a = 0; a < b.rows.length; a++) {
			if (b.rows[a] == c) {
				break
			}
		}
		if (a == b.rows.length) {
			saw.logicErrorAlert("could not find row:" + c + " in table:" + b);
			return null
		}
		return a
	};
	saw.Region = function (d, e, a, c) {
		this.top = d;
		this.right = e;
		this.bottom = a;
		this.left = c
	};
	saw.Region.prototype.contains = function (a) {
		return (a.left >= this.left && a.right <= this.right && a.top >= this.top && a.bottom <= this.bottom)
	};
	saw.Region.prototype.getWidth = function () {
		return this.right - this.left
	};
	saw.Region.prototype.getHeight = function () {
		return this.bottom - this.top
	};
	saw.Region.getRegion = function (g) {
		var f = saw.getElementXY(g);
		if (f) {
			var d = f[1];
			var e = f[0] + g.offsetWidth;
			var a = f[1] + g.offsetHeight;
			var c = f[0];
			return new saw.Region(d, e, a, c)
		} else {
			return null
		}
	};
	saw.Point = function (a, b) {
		this.x = a;
		this.y = b;
		this.top = b;
		this.right = a;
		this.bottom = b;
		this.left = a
	};
	saw.Point.prototype = new saw.Region();
	saw.screenToWindowRToL = function (a) {
		return document.body.offsetWidth - document.documentElement.scrollLeft - a
	};
	saw.getXmlText = saw.xml.getXMLString;
	saw.getParentElement = function (a, b) {
		if (!b) {
			b = 1
		}
		while (b > 0) {
			a = a.parentNode;
			if (!a) {
				return null
			}
			if (a.nodeType == 1) {
				b--
			}
		}
		return a
	};
	if (!saw.impl) {
		saw.impl = function () {}
	}
	saw.impl.g_vBlockElements = {
		DIV: 1,
		TABLE: 1,
		P: 1,
		BLOCKQUOTE: 1,
		H1: 1,
		H2: 1,
		H3: 1,
		H4: 1,
		H5: 1,
		H6: 1,
		CENTER: 1,
		PRE: 1,
		FORM: 1,
		HR: 1
	};
	saw.getInnerText = function (c) {
		if (is_ie) {
			return c.innerText
		} else {
			var a;
			if (saw.impl.g_vBlockElements[c.tagName]) {
				a = " "
			} else {
				a = ""
			}
			for (var b = 0; b < c.childNodes.length; b++) {
				if (c.childNodes[b].nodeType == 3) {
					a += c.childNodes[b].data
				} else {
					if (c.childNodes[b].nodeType == 1) {
						a += saw.getInnerText(c.childNodes[b])
					}
				}
			}
			return a
		}
	};
	saw.pixelLength = function (a) {
		var c = document.getElementById("sawruler");
		if (c != null) {
			c.style.display = "inline";
			c.innerHTML = a;
			var b = c.offsetWidth;
			c.style.display = "none";
			return b
		}
		return -1
	};
	saw.trimToPxLength = function (a, d) {
		var c = a;
		var b = a;
		if (saw.pixelLength(b) > d) {
			b += "...";
			while (saw.pixelLength(b) > d) {
				c = c.substring(0, c.length - 1);
				b = c + "..."
			}
		}
		return b
	};
	saw.doFSCommand = function (command, args) {
		eval("window." + command)
	};
	saw.getContextAwareObjectTagMovieURL = function (f) {
		var c = f;
		if ((saw.isSWEInline == "undefined") || (saw.isSWEInline == null) || (saw.isSWEInline == "true")) {
			var b = document.getElementsByTagName("link");
			for (var d = 0; d < b.length; d++) {
				var e = b[d].href;
				if ((e.search(/views\.css/i) != -1) && (e.search(/SWEMethod=ProxyUrl/i) != -1)) {
					saw.isSWEInline = true;
					var a = saw.encodeURIComponent(f);
					c = e.replace(/res%2fs_blafp%2fb_mozilla_4%2fviews.css$/i, a);
					break
				}
			}
			if (d >= b.length) {
				saw.isSWEInline = false
			}
		}
		return c
	};
	saw.ieActiveXFix = function (g, b, m, a, f, e, d, h, c) {
		var k = saw.getContextAwareObjectTagMovieURL(e);
		var l = document.getElementById(d + "_ActiveXDiv");
		var j = '<object classid="' + g + '" width="' + b + '" height="' + m + '" id="' + a + '" codebase="' + f + '" data="' + k + '"><param name="wmode" value="' + c + '"/><param name="movie" value="' + k + '"/>';
		if (h) {
			j += '<param name="FlashVars" value="' + h + '"/>'
		}
		j += "</object>";
		if (l) {
			l.innerHTML = j
		}
	};
	saw.fijiFlashTag = function (j, b, o, a, g, f, d, h, e, m) {
		var l = saw.getContextAwareObjectTagMovieURL(f);
		var n = document.getElementById(d + "_ActiveXDiv");
		var k = '<object classid="' + j + '" width="' + b + '" height="' + o + '" id="' + a + '" codebase="' + g + '"><param name="allowFullScreen" value="true"><param name="wmode" value="transparent"><param name="movie" value="' + l + '"><param name="FlashVars" value="userid=' + h + "&sid=" + e + "&docname=" + m + '"></object>';
		var c = '<embed src="' + l + '" id="' + a + '" FlashVars="userid=' + h + "&sid=" + e + "&docname=" + m + '" wmode="transparent" allowFullScreen="true" quality="high" bgcolor="white" width="' + b + '" height="' + o + '" name="' + a + '" play="true" loop="false" quality="high" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer"></embed>';
		if (is_ie) {
			n.innerHTML = k
		} else {
			n.innerHTML = c
		}
	};
	saw.getBaseURL = function () {
		var c = window.location.href;
		var d = c.indexOf("?");
		if (d > 0) {
			c = c.substr(0, d - 1)
		}
		var b = c.lastIndexOf("/");
		if (b < 0) {
			return ""
		}
		var a = c.substr(0, b);
		return a
	};
	function instanceOf(b, a) {
		while (b != null) {
			if (b == a.prototype) {
				return true
			}
			b = b.__proto__
		}
		return false
	}
	saw.StringBuffer = function StringBuffer() {
		this.buffer = []
	};
	saw.StringBuffer.prototype.append = function append(a) {
		this.buffer.push(a);
		return this
	};
	saw.StringBuffer.prototype.toString = function toString() {
		return this.buffer.join("")
	};
	saw.preventMainBarJump = function () {
		if (is_ie6 && saw.hasHScrollBar()) {
			var a = document.getElementById("idSawDummyObject");
			if (!a) {
				a = document.createElement("span");
				a.id = "idSawDummyObject";
				a.style.visibility = "hidden";
				document.body.appendChild(a)
			}
			a.style.display = a.style.display == "none" ? "" : "none"
		}
	};
	saw.resizeStretchedChild = function (e) {
		if (e) {
			var d = e.parentNode;
			var g = saw.getComputedStyle(d);
			var f = saw.cssLengthToInt(g.paddingLeft) + saw.cssLengthToInt(g.paddingRight);
			var c = (d.clientWidth - f);
			var b = saw.cssLengthToInt(g.paddingTop) + saw.cssLengthToInt(g.paddingBottom);
			var a = (d.clientHeight - b);
			saw.setOuterWidth(e, c);
			saw.setOuterHeight(e, a)
		}
	};
	saw.setElementOuterXY = function (d, a, f) {
		var c = saw.getComputedStyle(d);
		if (is_ie) {
			if (c.top == "auto") {
				d.style.top = "0px"
			}
			if (saw.isRToL()) {
				if (c.right == "auto") {
					d.style.right = "0px"
				}
			} else {
				if (c.left == "auto") {
					d.style.left = "0px"
				}
			}
		}
		var b = c.marginLeft;
		if (saw.isRToL() && saw.userAgent.is_webkit) {
			var e = d.style.display;
			d.style.display = "inline-block";
			b = c.marginLeft;
			d.style.display = e
		}
		saw.setElementXY(d, a == null ? null : a + saw.cssLengthToInt(b), f == null ? null : f + saw.cssLengthToInt(c.marginTop))
	};
	saw.setOuterWidth = function (b, e) {
		var a = saw.getComputedStyle(b);
		var c = e - saw.cssLengthToInt(a.paddingLeft) - saw.cssLengthToInt(a.paddingRight);
		if (saw.userAgent.is_webkit) {
			if (b.tagName != "TD") {
				var d = b.style.display;
				b.style.display = "inline-block";
				c -= (saw.cssLengthToInt(a.marginLeft) + saw.cssLengthToInt(a.marginRight));
				b.style.display = d
			}
		} else {
			c -= (saw.cssLengthToInt(a.marginLeft) + saw.cssLengthToInt(a.marginRight))
		}
		if (b.tagName.toUpperCase() != "TABLE") {
			c -= (saw.cssLengthToInt(a.borderLeftWidth) + saw.cssLengthToInt(a.borderRightWidth))
		}
		if (c < 0) {
			c = 0
		}
		b.style.width = c + "px"
	};
	saw.setOuterHeight = function (c, d) {
		var b = saw.getComputedStyle(c);
		var a = d - saw.cssLengthToInt(b.paddingTop) - saw.cssLengthToInt(b.paddingBottom) - saw.cssLengthToInt(b.marginTop) - saw.cssLengthToInt(b.marginBottom);
		if (c.tagName.toUpperCase() != "TABLE") {
			a -= (saw.cssLengthToInt(b.borderTopWidth) + saw.cssLengthToInt(b.borderBottomWidth))
		}
		if (a < 0) {
			a = 0
		}
		c.style.height = a + "px"
	};
	saw.cssLengthToInt = function (b) {
		var a = 0;
		if (b.length > 0 && b != "auto") {
			a = parseInt(b)
		}
		if (isNaN(a)) {
			a = 0
		}
		return a
	};
	saw.restrictLength = function (b, a) {
		if (b.value.length > a) {
			b.value = b.value.substring(0, a)
		}
	};
	saw.appendNBSP = function (a) {
		saw.appendTextNode(a, "\u00a0")
	};
	saw.appendTextNode = function (a, b) {
		a.appendChild(a.ownerDocument.createTextNode(b))
	};
	saw.clearInnerHTML = function (a) {
		a.innerHTML = ""
	};
	saw.deleteInnerHTML = function (a) {
		while (a.hasChildNodes()) {
			a.removeChild(a.firstChild)
		}
	};
	saw.setInnerText = function (b, a) {
		saw.clearInnerHTML(b);
		saw.appendTextNode(b, a)
	};
	saw.createChildElement = function (b, c, a) {
		var d = b.ownerDocument.createElement(c);
		if (a) {
			d.className = a
		}
		b.appendChild(d);
		return d
	};
	saw.createTable = function (h, f, a, k) {
		var g = document;
		var b = g.createElement("TABLE");
		b.cellSpacing = "0";
		b.cellPadding = "0";
		b.border = "0";
		if (k) {
			saw.setAriaRole(b, "presentation")
		}
		if (typeof(h) == "integer" || typeof(h) == "number") {
			for (var e = 0; e < h; e++) {
				var c = b.insertRow(e);
				if (f) {
					for (var d = 0; d < f; d++) {
						c.insertCell(d)
					}
				}
			}
		}
		if (a) {
			a.appendChild(b)
		}
		return b
	};
	saw.createLabel = function (c, e, a, b) {
		var f = b && b.ownerDocument || document;
		var d = f.createElement("label");
		d.appendChild(f.createTextNode(c));
		if (e) {
			d.setAttribute("for", e)
		}
		if (a) {
			d.className = a
		}
		if (b) {
			b.appendChild(d)
		}
		return d
	};
	saw.getTextFieldCursorPosition = function (e, c, d) {
		var a = c;
		if (d) {
			e.focus()
		}
		if (document.selection) {
			var b = document.selection.createRange().duplicate();
			b.moveStart("character", -e.value.length);
			a = b.text.length
		} else {
			a = e.selectionStart
		}
		return a
	};
	saw.setTextFieldCursorPosition = function (e, c, d) {
		if (d) {
			e.focus()
		}
		if (e.setSelectionRange) {
			e.setSelectionRange(c, c)
		} else {
			if (e.createTextRange) {
				var b = e.createTextRange();
				b.collapse(true);
				var a = b.move("character", c);
				b.select()
			}
		}
	};
	saw.addInvisibleLayerBeforeUnload = function () {
		if (saw.userAgent.is_nav) {
			saw.addEventListener(window, "unload", saw.addInvisibleLayerBeforeUnloadImpl)
		}
	};
	saw.addInvisibleLayerBeforeUnloadImpl = function () {
		var a = document.createElement("div");
		a.style.position = "absolute";
		a.style.zIndex = 9999;
		a.style.backgroundColor = "transparent";
		a.style.height = "100%";
		a.style.width = "100%";
		a.style.left = 0;
		a.style.top = 0;
		document.body.appendChild(a)
	};
	saw.help = function () {};
	saw.help.ModelBase = function (b, a) {
		this.url = b;
		this.text = a
	};
	saw.help.ModelBase.prototype.getText = function () {
		return this.text
	};
	saw.help.ModelBase.prototype.getUrl = function () {
		return this.url
	};
	saw.help.ModelBase.prototype.getCallback = function () {
		return null
	};
	saw.getStackTrace = function () {
		try {
			i.dont.exist += 0
		} catch (a) {
			return saw.getStackTraceFromError(a)
		}
	};
	saw.getStackTraceFromError = function (g) {
		var f = [];
		var b = false;
		f.push(g.message);
		if (g.stack) {
			var c = g.stack.split("\n");
			for (var d = 0, a = c.length; d < a; d++) {
				if (c[d].length > 0) {
					f.push(c[d])
				}
			}
			b = true
		}
		if (f && f.length > 0) {
			return f.join("\n")
		} else {
			return g.message
		}
	};
	saw.log = {
		_append: function (c, a) {
			var b = document.getElementById("idSAWLogContainer");
			if (!b) {
				b = document.createElement("DIV");
				b.id = "idSAWLogContainer";
				b.style.display = "none";
				b.style.position = "fixed";
				b.style.top = "0px";
				b.style.left = "0px";
				b.style.height = "100px";
				b.style.overflowY = "auto";
				b.style.backgroundColor = "white";
				document.body.appendChild(b);
				saw.addEventListener(document, "keydown", saw.log._toggleLogWindow, b)
			}
			saw.appendTextNode(b, a + ": " + c);
			b.appendChild(document.createElement("BR"))
		},
		_toggleLogWindow: function (a, b) {
			if (a.keyCode == 76 && a.ctrlKey && a.altKey) {
				b.style.display = b.style.display == "none" ? "" : "none"
			}
		},
		debug: function (a) {
			if (!window.obips_debug) {
				return
			}
			if (window.console) {
				if (console.debug) {
					console.debug(a)
				} else {
					console.info(a)
				}
			} else {
				saw.log._append(a, "Debug")
			}
		},
		info: function (a) {
			window.console ? console.info(a) : saw.log._append(a, "Info")
		},
		warn: function (a) {
			window.console ? console.warn(a) : saw.log._append(a, "Warn")
		},
		error: function (a) {
			window.console ? console.error(a) : saw.log._append(a, "Error")
		}
	};
	saw.setAriaRole = function (b, a) {
		b.setAttribute("role", a)
	};
	saw.logicErrorAlert = function (b) {
		var a = obips.ResourceManager.getSingleton().getResource("common/saw.common.xml", "kmsgUnexpectedConditionError");
		alert(obips.ResourceManager.Resource.prototype.getString.apply(a, [""].concat(b)))
	};
	saw.handleServerError = function (e, b) {
		var d = "";
		if (b == null) {
			b = e.errors[0]
		}
		var c;
		for (c = 0; c < b.length; c++) {
			var f = "Fatal";
			var a = b[c].getMessage();
			d = f + ":" + a + "\n"
		}
		saw.logicErrorAlert(d)
	};
	saw.setAriaProperty = function (a, b, c) {
		a.setAttribute(b, c)
	};
	CatalogBrowser = {};
	function NQWGetXMLText(a) {
		return saw.getXmlText(a)
	}
	function escapespaces(d) {
		var b = d.split("\\");
		var c = b.join("\\\\");
		b = c.split(" ");
		return b.join("\\ ")
	}
	escapeplus = saw.encodeURIComponent;
	function escapeamp(d) {
		var b = d.split("&");
		var c = b.join("&amp;");
		return c
	}
	NQWCommand = saw.commandToURL;
	PopupWindow = saw.popupWindow;
	NQWFindAncestorElement = saw.findAncestorElement;
	NQWFindAncestorElementOrSelf = saw.findAncestorElementOrSelf;
	NQWIsDescendantOf = saw.isDescendantOf;
	NQWDisableSelectionOnNode = saw.disableSelectionOnNode;
	NQWEnableSelectionOnNode = saw.enableSelectionOnNode;
	NQWFindFirstChildElement = saw.findFirstChildElement;
	NQWPositionPopup = saw.positionByDOMElement;
	saw.common = true
};

if (!window.saw) {
	saw = function () {}
}
if (!saw.ajax) {
	saw.ajax = function () {};
	saw.ajax._secureJsonPayload = true;
	if (!saw.extend) {
		saw.extend = function () {}
	}
	saw.ajax.resourcefile = "common/saw.ajax.xml";
	saw.ajax.Request = function (c, d, b, a) {
		this.sUrl = saw.ajax._isSAWCommand(c) ? saw.commandToURL(c) : c;
		this.fCallback = d;
		this.tCallerObj = b ? b : null;
		this.tContextInfo = a ? a : null;
		this.fFatalErrorHandler = null;
		this.fWaitHandler = null;
		this.nWaitInterval = 500;
		this.sQueryString = "";
		this.responseProcessor = null;
		this.tLoadingMessageDisplayer = null
	};
	saw.ajax.Request.prototype.setQueryString = function (a) {
		this.sQueryString = a
	};
	saw.ajax.Request.CheckDuplicateArg = {
		SKIP_CHECK: 1,
		KEEP_EXISTING: 2,
		REPLACE: 3
	};
	saw.ajax.Request.prototype.addArg = function (b, d, c) {
		if (typeof(d) == "undefined" || d == null) {
			d = ""
		} else {
			if (typeof(d) != "string" && d.toString) {
				d = d.toString()
			}
		}
		if (this.sQueryString && c == saw.ajax.Request.CheckDuplicateArg.KEEP_EXISTING) {
			var a = this.sQueryString.indexOf(b + "=");
			if (a == 0 || a > 0 && this.sQueryString.charAt(a - 1) == "&") {
				return
			}
		}
		if (this.sQueryString) {
			this.sQueryString += "&"
		}
		this.sQueryString += saw.encodeURIComponent(b) + "=" + saw.encodeURIComponent(d)
	};
	saw.ajax.Request.prototype.addXmlElementArg = function (b, a) {
		this.sQueryString += saw.encodeURIComponent(b) + "=" + saw.encodeURIComponent(a.xml)
	};
	saw.ajax.Request.prototype.setFatalErrorHandler = function (a) {
		this.fFatalErrorHandler = a
	};
	saw.ajax.Request.prototype.setWaitHandler = function (b, a) {
		this.fWaitHandler = b;
		if (a) {
			this.nWaitInterval = a
		}
	};
	saw.ajax.Request.prototype.setResponseProcessor = function (a) {
		if (typeof(a) == "string") {
			if (a == "general") {
				this.responseProcessor = new saw.ajax.ResponseProcessor()
			} else {
				if (a != "default") {
					throw new Error("Undefined response processor: " + a)
				}
			}
		} else {
			this.responseProcessor = a
		}
	};
	saw.ajax.Request.prototype.setLoadingMessageDisplayer = function (a) {
		this.tLoadingMessageDisplayer = a
	};
	saw.ajax.Request.prototype.setNamedQueue = function (a, b) {
		this.queueName = a;
		this.queueCapacity = b
	};
	saw.ajax.Request.prototype.isInProgress = function () {
		return (this._getReadyState() != saw.ajax.Connection.UNINITIALIZED && this._getReadyState() != saw.ajax.Connection.COMPLETE)
	};
	saw.ajax.Request.prototype._getReadyState = function () {
		return saw.ajax.Connection.UNINITIALIZED
	};
	saw.ajax.Request.prototype._equals = function (a) {
		if (!a) {
			return false
		}
		return this.sUrl == a.sUrl && this.fCallback == a.fCallback && this.tCallerObj == a.tCallerObj && this.fFatalErrorHandler == a.fFatalErrorHandler && this.sQueryString == a.sQueryString && this.tForm == a.tForm && this.tContextInfo == a.tContextInfo
	};
	saw.ajax.Request.prototype._clone = function () {
		var a = new saw.ajax.Request(this.sUrl, this.fCallback);
		for (i in this) {
			a[i] = this[i]
		}
		return a
	};
	saw.ajax.Request.prototype._callBack = function (a) {
		if (!this.fCallback) {
			throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgNoCallbackRegisterError").getString())
		} else {
			if (this.tCallerObj) {
				this.fCallback.call(this.tCallerObj, a, this.tContextInfo)
			} else {
				this.fCallback(a, this.tContextInfo)
			}
		}
	};
	saw.ajax.Request.prototype.getCallbackContext = function () {
		return new saw.ajax.Request.CallbackContext(this.fCallback, this.tCallerObj, this.tContextInfo)
	};
	saw.ajax.Request.prototype._handleErrors = function (f) {
		if (this.fFatalErrorHandler) {
			var g = new saw.ajax.Response();
			var e = [];
			for (var c = 0; c != f.length; ++c) {
				var a = f[c];
				for (var b = 0; b != a.length; ++b) {
					e.push(a[b])
				}
			}
			if (this.tCallerObj) {
				this.fFatalErrorHandler.call(this.tCallerObj, g, e, this.tContextInfo)
			} else {
				this.fFatalErrorHandler(g, e, this.tContextInfo)
			}
		} else {
			var d = saw.array.map(f, function (h) {
					return saw.array.map(h, function (j) {
						return j.message
					}).join("\n")
				}).join("\n");
			throw new Error(d)
		}
	};
	saw.ajax.Request.CallbackContext = function (c, b, a) {
		this.fnCB = c;
		this.callerObj = b;
		this.ctxInfo = a
	};
	saw.ajax.Request.CallbackContext.prototype.equals = function (a) {
		return this.fnCB == a.fnCB && this.callerObj == a.callerObj && this.ctxInfo == a.ctxInfo
	};
	saw.ajax.JsonRequest = function (c, d, b, a) {
		this.bJsonPalyload = true;
		saw.ajax.JsonRequest.baseConstructor.call(this, c, d, b, a)
	};
	saw.extend(saw.ajax.JsonRequest, saw.ajax.Request);
	saw.ajax.ResponseRawData = function (b, a) {
		if (b) {
			this.xml = b.responseXML;
			if (!this.xml || !this.xml.documentElement) {
				this.xml = null;
				this.text = b.responseText
			}
		} else {
			if (a) {
				this.iframe = a
			} else {
				throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgNoResponseDataError").getString())
			}
		}
	};
	saw.ajax.ResponseProcessor = function () {};
	saw.ajax.ResponseProcessor.prototype.processResponse = function (b, a) {};
	saw.ajax.SawRespProcessor = function () {};
	saw.extend(saw.ajax.SawRespProcessor, saw.ajax.ResponseProcessor);
	saw.ajax.SawRespProcessor.prototype.parseTextResponse = function (g, c) {
		if (c.substr(0, 5) != "OBIPS") {
			var b = new saw.ajax.ResponseBlock();
			b.type = saw.ajax.ResponseBlock.TYPE_TEXT;
			b.data = c;
			g.push(b);
			return
		}
		var a = c.substr(5, 2) - 0;
		var e = c.substr(7, a);
		if (e.length != a) {
			return
		}
		this.separator = e;
		var f = c.split(e + "0");
		for (var d = 1; d < f.length; ) {
			g.push(this._parseBlock(f[d], f[d + 1]));
			d += 2
		}
	};
	saw.ajax.SawRespProcessor.prototype._parseBlock = function (b, e, a) {
		var h = saw.json.parse(b, true, true);
		var c = new saw.ajax.ResponseBlock();
		c.id = h.i;
		c.type = h.t;
		c.properties = h.props;
		c.level = a || 0;
		if (c.type < 1 || c.type > 6) {
			throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgUndefinedResponseError").getString() + c.type + ". (" + sResponse + ")")
		} else {
			if (c.type == saw.ajax.ResponseBlock.TYPE_ERROR) {
				c.fatal = h.fatal;
				c.errors = [];
				saw.ajax.ResponseBlock._walkErrorJSON(c.errors, saw.json.parse(e, !saw.ajax._secureJsonPayload, true))
			} else {
				var g = c.level + 1;
				var f = e.split(this.separator + g);
				if (f.length > 1) {
					e = f[0];
					c.subBlocks = [];
					for (var d = 1; d < f.length; d += 3) {
						if (d + 2 < f.length) {
							e += f[d + 2] || ""
						}
						c.subBlocks.push(this._parseBlock(f[d], f[d + 1], g))
					}
				}
				saw.ajax.ResponseBlock._handlePayload(c, e)
			}
		}
		return c
	};
	saw.ajax.SawRespProcessor.prototype.postProcessResponse = function (c, f) {
		var d = [];
		for (var b = 0; b != f.length; ++b) {
			var a = f[b];
			if (a.isError() && a.fatal) {
				d.push(a.errors)
			}
		}
		if (d.length == 0 && f.length == 1 && f[0].isError() && f[0].clientGen) {
			d.push(f[0].errors)
		}
		if (d.length != 0) {
			return {
				errors: d,
				response: null
			}
		} else {
			var e = new saw.ajax.Response();
			for (var b = 0; b != f.length; ++b) {
				e._addBlock(f[b])
			}
			return {
				errors: null,
				response: e
			}
		}
	};
	saw.ajax.SawXhrRespProcessor = function () {};
	saw.extend(saw.ajax.SawXhrRespProcessor, saw.ajax.SawRespProcessor);
	saw.ajax.SawXhrRespProcessor.prototype.processResponse = function (b, a) {
		var c = [];
		if (!a.xml) {
			if (a.text) {
				this.parseTextResponse(c, a.text)
			} else {
				throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgEmptyResponseError").getString())
			}
		} else {
			throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgResponseFormatError").getString())
		}
		return this.postProcessResponse(b, c)
	};
	saw.ajax.IframeRespProcessor = function () {};
	saw.extend(saw.ajax.IframeRespProcessor, saw.ajax.SawRespProcessor);
	saw.ajax.IframeRespProcessor.prototype.processResponse = function (d, c) {
		var f = [];
		if (!c.iframe) {
			throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgResponseNoIframeError").getString())
		} else {
			var e = c.iframe.document.body.firstChild;
			if (e && e.tagName == "PRE") {
				this.parseTextResponse(f, e.innerText || e.firstChild.wholeText)
			} else {
				var a = c.iframe.idAJAX;
				if (!a) {
					throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgAjaxObjectResponseError").getString())
				} else {
					for (var b = 0; b != a.length; ++b) {
						f.push(saw.ajax.ResponseBlock._createFromJSON(a[b]))
					}
				}
			}
		}
		return this.postProcessResponse(d, f)
	};
	saw.ajax.Error = function (a) {
		this.message = a;
		this.msg = a
	};
	saw.ajax.Error.prototype.getMessage = function () {
		return this.message
	};
	saw.ajax.Error.prototype.getType = function () {
		return ""
	};
	saw.ajax.ResponseBlock = function () {
		this.type = 0;
		this.id = null;
		this.data = null;
		this.level = 0;
		this.fatal = null;
		this.errors = null
	};
	saw.ajax.ResponseBlock.TYPE_HTML = 1;
	saw.ajax.ResponseBlock.TYPE_XML = 2;
	saw.ajax.ResponseBlock.TYPE_TEXT = 3;
	saw.ajax.ResponseBlock.TYPE_JSON = 4;
	saw.ajax.ResponseBlock.TYPE_SCRIPT = 5;
	saw.ajax.ResponseBlock.TYPE_ERROR = 6;
	saw.ajax.ResponseBlock._handlePayload = function (b, d) {
		if (b.type == saw.ajax.ResponseBlock.TYPE_XML) {
			try {
				b.data = saw.parseXML(d)
			} catch (f) {
				b.type = saw.ajax.ResponseBlock.TYPE_ERROR;
				b.clientGen = true;
				b.fatal = false;
				var c = obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgInvalidXmlPayload").getString() + d + "\n" + f.message;
				b.errors = [new saw.ajax.Error(c)]
			}
		} else {
			if (b.type == saw.ajax.ResponseBlock.TYPE_JSON) {
				try {
					var a = saw.ajax._secureJsonPayload || obips.selenium.testEnabled || window.obips_debug;
					b.data = saw.json.parse(d, !a, true)
				} catch (f) {
					b.type = saw.ajax.ResponseBlock.TYPE_ERROR;
					b.clientGen = true;
					b.fatal = false;
					var c = obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgInvalidJasonPayload").getString() + d + "\n" + f.message;
					b.errors = [new saw.ajax.Error(c)]
				}
			} else {
				b.data = d
			}
		}
		return b
	};
	saw.ajax.ResponseBlock._createFromJSON = function (b) {
		if (b.type < 1 || b.type > 6) {
			throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgUndefinedResponseError").getString() + b.type + ". (" + saw.json.toString(b) + ")")
		}
		var a = new saw.ajax.ResponseBlock();
		a.type = b.type;
		if (b.id) {
			a.id = b.id
		}
		if (a.type == saw.ajax.ResponseBlock.TYPE_ERROR) {
			a.fatal = b.data.fatal;
			a.errors = [];
			saw.ajax.ResponseBlock._walkErrorJSON(a.errors, b.data.error)
		} else {
			saw.ajax.ResponseBlock._handlePayload(a, b.data)
		}
		return a
	};
	saw.ajax.ResponseBlock._walkErrorJSON = function (b, d) {
		if (d.msg) {
			b.push(new saw.ajax.Error(d.msg));
			if (d.linked) {
				for (var c = 0; c != d.linked.length; ++c) {
					var a = d.linked[c];
					if (a) {
						saw.ajax.ResponseBlock._walkErrorJSON(b, a)
					} else {
						if (c + 1 != d.linked.length) {
							throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgInvalidJasonSyntax").getString())
						}
					}
				}
			}
		} else {
			throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgInvalidJasonSyntax").getString())
		}
	};
	saw.ajax.ResponseBlock.prototype.isError = function () {
		return this.type == saw.ajax.ResponseBlock.TYPE_ERROR
	};
	saw.ajax.ResponseBlock.prototype.getSubBlocks = function () {
		return this.subBlocks
	};
	saw.ajax.ResponseBlock.prototype.getSubBlockById = function (d, b) {
		var f = this.subBlocks;
		if (!f) {
			return null
		}
		var c = null;
		for (var e = 0; e != f.length; ++e) {
			var a = f[e];
			if (a.id == d && (!b || a.type == b)) {
				if (a.data != "") {
					return a
				} else {
					c = a
				}
			}
		}
		return c
	};
	saw.ajax.ResponseBlock.prototype.getFirstSubBlockOfType = function (c) {
		var d = this.subBlocks;
		if (!d) {
			return null
		}
		for (var b = 0; b != d.length; ++b) {
			var a = d[b];
			if (a.type == c) {
				return a
			}
		}
		return null
	};
	saw.ajax.Response = function () {
		this.blocks = [];
		this.errors = []
	};
	saw.ajax.Response.prototype._addBlock = function (a) {
		this.blocks.push(a);
		if (a.isError()) {
			this.errors.push(a)
		}
	};
	saw.ajax.Response.prototype.getBlockById = function (c, b) {
		for (var d = 0; d != this.blocks.length; ++d) {
			var a = this.blocks[d];
			if (a.id == c && (!b || a.type == b)) {
				return a
			}
		}
		return null
	};
	saw.ajax.Response.prototype.getFirstBlockOfType = function (c) {
		for (var b = 0; b != this.blocks.length; ++b) {
			var a = this.blocks[b];
			if (a.type == c) {
				return a
			}
		}
		return null
	};
	saw.ajax.Response.prototype._getFirstPayloadOfType = function (b) {
		var a = this.getFirstBlockOfType(b);
		if (a) {
			return a.data
		}
		return null
	};
	saw.ajax.Response.prototype.getHTML = function () {
		return this._getFirstPayloadOfType(saw.ajax.ResponseBlock.TYPE_HTML)
	};
	saw.ajax.Response.prototype.getXML = function () {
		return this._getFirstPayloadOfType(saw.ajax.ResponseBlock.TYPE_XML)
	};
	saw.ajax.Response.prototype.getJSON = function () {
		return this._getFirstPayloadOfType(saw.ajax.ResponseBlock.TYPE_JSON)
	};
	saw.ajax.Response.prototype.getScript = function () {
		return this._getFirstPayloadOfType(saw.ajax.ResponseBlock.TYPE_SCRIPT)
	};
	saw.ajax.Response.prototype.getText = function () {
		return this._getFirstPayloadOfType(saw.ajax.ResponseBlock.TYPE_TEXT)
	};
	saw.ajax.Response.prototype.hasErrors = function () {
		return this.errors.length != 0
	};
	saw.ajax.Response.prototype.getErrors = function () {
		return this.errors
	};
	saw.ajax.Response.prototype.getAllBlocks = function () {
		return this.blocks
	};
	saw.ajax.Connection = function (a) {
		this.req = a ? a : null
	};
	saw.ajax.Connection.UNINITIALIZED = 0;
	saw.ajax.Connection.LOADING = 1;
	saw.ajax.Connection.LOADED = 2;
	saw.ajax.Connection.INTERACTIVE = 3;
	saw.ajax.Connection.COMPLETE = 4;
	saw.ajax.Connection.prototype.getQueueName = function () {
		return this.req.queueName
	};
	saw.ajax.Connection.prototype.getQueueCapacity = function () {
		return this.req.queueCapacity
	};
	saw.ajax.Connection.prototype.get = function () {
		return this._submitType("get")
	};
	saw.ajax.Connection.prototype.post = function () {
		return this._submitType("post")
	};
	saw.ajax.Connection.prototype.postForm = function (a) {
		this.req.tForm = a;
		return this.post()
	};
	saw.ajax.Connection.prototype.postStringData = function (a) {
		this.req.sQueryString = a;
		return this.post()
	};
	saw.ajax.Connection.prototype._submitType = function (a) {
		this.submitMethod = a;
		if (!this._validate()) {
			throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgInvalidAjaxRequest").getString())
		}
		return saw.ajax.Manager.getInstance().run(this)
	};
	saw.ajax.Connection.prototype._actualSubmit = function () {
		if (!this.bSync) {
			var a = this.req.tLoadingMessageDisplayer || saw.ajax.Manager.getInstance().getDefaultProgressIndicator();
			if (a) {
				a.show()
			}
		}
		var b = this._submit();
		if (!this.bSync) {
			this._startRequestWatcher();
			saw.ajax.pendingRequestCount++;
			if (saw.ajax.pendingRequestCount == 1) {
				saw.ajax.Manager.getInstance().globalBusyIndicator.show()
			}
		}
		return b
	};
	saw.ajax.Connection.prototype._validate = function () {
		return true
	};
	saw.ajax.Connection.prototype._submit = function (b, a) {
		throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgSubmitNotImplementedError").getString())
	};
	saw.ajax.Connection.prototype._startRequestWatcher = function () {
		var b = this.req;
		if (b.fWaitHandler) {
			var a = b.fWaitHandler;
			b.sIntervalID = window.setInterval(function () {
					if (b.isInProgress()) {
						try {
							if (b.tCallerObj) {
								a.call(b.tCallerObj, b)
							} else {
								a(b)
							}
						} catch (c) {}
					}
				}, b.nWaitInterval)
		}
	};
	saw.ajax.Connection.prototype._cleanupFinishedRequest = function (a) {
		if (a.sIntervalID) {
			window.clearInterval(a.sIntervalID);
			a.sIntervalID = null
		}
		saw.ajax.Manager.getInstance().onRequestHandled(this)
	};
	saw.ajax.Connection._handleNotification = function (b, d) {
		saw.ajax.pendingRequestCount--;
		if (saw.ajax.pendingRequestCount == 0) {
			saw.ajax.Manager.getInstance().globalBusyIndicator.hide()
		}
		if (!d) {
			return
		}
		try {
			if (d.errors) {
				b._handleErrors(d.errors)
			} else {
				b._callBack(d.response)
			}
		} catch (c) {
			saw.log.error("saw.ajax.Connection._handleNotification: " + saw.getStackTraceFromError(c))
		}
		if (!this.bSync) {
			var a = b.tLoadingMessageDisplayer || saw.ajax.Manager.getInstance().getDefaultProgressIndicator();
			if (a) {
				a.hide()
			}
		}
	};
	saw.ajax.Connection.prototype._login = function () {
		if (saw.ajax._connectionForLogin) {
			return
		}
		if (!(typeof(biadf) == "undefined") && biadf.syndicate || typeof(obitech) !== "undefined") {
			return
		}
		var e = document;
		var f = saw.createChildElement(e.body, "div");
		f.id = "idOBIPSAjaxLogin";
		var d = saw.createChildElement(f, "div", "OpaqueLayer");
		saw.appendNBSP(d);
		d.style.zIndex = 10001;
		d.style.position = "absolute";
		d.style.height = saw.getClientHeight() + "px";
		d.style.width = "100%";
		d.style.top = "0px";
		d.style.left = "0px";
		var c = saw.createTable(1, 1, f);
		c.style.zIndex = 10001;
		c.style.position = "absolute";
		c.style.backgroundColor = "transparent";
		c.style.height = saw.getClientHeight() + "px";
		c.style.width = "100%";
		c.style.top = "0px";
		c.rows[0].cells[0].style.textAlign = "center";
		var b = saw.pageToURL("pages/common/ajaxloggedin.html");
		var a = saw.createIFrame("OBIPSAjaxLogin", "OBIPSAjaxLogin", document, b);
		a.style.backgroundColor = "#ffffff";
		a.style.width = "80%";
		a.style.height = Math.round(saw.getClientHeight() * 0.8) + "px";
		c.rows[0].cells[0].appendChild(a);
		saw.ajax._connectionForLogin = this
	};
	saw.ajax.onUserReloggedIn = function (a) {
		var b = parent.document.getElementById("idOBIPSAjaxLogin");
		if (b) {
			b.parentNode.removeChild(b)
		}
		if (saw.ajax._connectionForLogin) {
			saw.ajax.Connection._handleNotification(saw.ajax._connectionForLogin.req, {
				errors: [[new saw.ajax.Error(a.message)]]
			});
			saw.ajax._connectionForLogin = null
		}
	};
	saw.ajax.IFrameConnection = function (a) {
		saw.ajax.IFrameConnection.baseConstructor.call(this, a);
		this.uid = "idsaw.ajax.connection";
		this.bVisible = false
	};
	saw.extend(saw.ajax.IFrameConnection, saw.ajax.Connection);
	saw.ajax.IFrameConnection.prototype.abort = function () {
		if (this.iframe && this.iframe.contentWindow.stop) {
			this.iframe.contentWindow.stop();
			this.iframe = null
		}
	};
	saw.ajax.IFrameConnection.prototype.setID = function (a) {
		this.uid = a
	};
	saw.ajax.IFrameConnection.prototype.setVisible = function (a) {
		this.bVisible = a
	};
	saw.ajax.IFrameConnection._returnInProgressReadyState = function () {
		return saw.ajax.Connection.LOADING
	};
	saw.ajax.IFrameConnection._returnCompleteReadyState = function () {
		return saw.ajax.Connection.COMPLETE
	};
	saw.ajax.IFrameConnection._iframeKeys = [];
	saw.ajax.IFrameConnection._onLoad = function (a) {
		a = saw.getEvent(a);
		var b = saw.getEventCurrentTarget(a);
		var d = window.frames[b.id];
		if (!d) {
			saw.logicErrorAlert("SAW AJAX: Unable to find the IFRAME which received the onLoad event.")
		} else {
			var c = b.sawAjaxLastConn;
			if (!c) {
				saw.logicErrorAlert("SAW AJAX: Unable to find IFRAME connection object.")
			} else {
				c._onLoadImpl(d)
			}
		}
	};
	saw.ajax.IFrameConnection.prototype._makeIFrameID = function () {
		var e = this.req.getCallbackContext();
		var c = saw.ajax.IFrameConnection._iframeKeys;
		for (var b = 0; b != c.length; ++b) {
			var d = c[b];
			if (d.key.equals(e) && d.uid == this.uid) {
				return d.iframeID
			}
		}
		var a = this.uid + "IFrame" + c.length;
		c.push({
			key: e,
			uid: this.uid,
			iframeID: a
		});
		return a
	};
	saw.ajax.IFrameConnection.prototype._submit = function () {
		var f = this._makeIFrameID();
		var e = document.getElementById(f);
		if (e == null) {
			e = saw.createHiddenIFrame(f, f);
			if (this.bVisible) {
				e.style.display = "";
				e.style.visibility = "visible";
				e.style.height = "300px";
				e.style.width = "300px"
			}
			saw.addEventListener(e, "load", saw.ajax.IFrameConnection._onLoad)
		}
		this.iframe = e;
		e.sawAjaxLastConn = this;
		this.req._getReadyState = saw.ajax.IFrameConnection._returnInProgressReadyState;
		if (this.submitMethod == "post") {
			var d = this.req.tForm;
			if (!d) {
				this.req.addArg("ajaxType", "iframe");
				if (this.req.sUrl.charAt(0) == "/") {
					this.req.addArg("urlGenerator", "qualified")
				}
				d = saw.createFormFromString(this.req.sQueryString)
			} else {
				saw.addHiddenInput(d, "ajaxType", "iframe");
				if (this.req.sUrl.charAt(0) == "/") {
					saw.addHiddenInput(d, "urlGenerator", "qualified")
				}
			}
			var b = d.target;
			var c = d.method;
			var a = d.action;
			d.target = e.id;
			d.method = this.submitMethod;
			d.action = this.req.sUrl;
			saw.addSessionSecurityCodeInput(d);
			d.submit();
			d.target = b;
			d.method = c;
			d.action = a
		} else {
			e.src = this.req.sUrl + "&ajaxType=iframe"
		}
		return true
	};
	saw.ajax.IFrameConnection.prototype._onLoadImpl = function (b) {
		var a = this.req;
		var h = this._makeIFrameID();
		saw.removeEventListener(document.getElementById(h), "load", this.myHandler);
		a._getReadyState = saw.ajax.IFrameConnection._returnCompleteReadyState;
		if (b.sawAjaxEmptyResponseFlag) {
			return
		}
		var g = null;
		try {
			var f = new saw.ajax.ResponseRawData(null, b);
			var c = f.iframe && f.iframe.document.body.firstChild;
			if (c && c.nodeType == 3) {
				if (c.nodeValue == "OBIPSNotLoggedIn") {
					this._login()
				}
			} else {
				if (a.responseProcessor) {
					g = a.responseProcessor.processResponse(a, f)
				} else {
					g = (new saw.ajax.IframeRespProcessor()).processResponse(a, f)
				}
			}
		} catch (d) {
			g = {
				errors: [[new saw.ajax.Error(d.message)]]
			}
		}
		finally {
			this._cleanupFinishedRequest(a)
		}
		saw.ajax.Connection._handleNotification(a, g)
	};
	saw.ajax.XhrConnection = function (a) {
		saw.ajax.XhrConnection.baseConstructor.call(this, a);
		this.aHeader = new Array();
		this.bSync = false;
		this.bByPassLogin = false
	};
	saw.extend(saw.ajax.XhrConnection, saw.ajax.Connection);
	saw.ajax.XhrConnection.prototype.abort = function () {
		var a = this.xhr.readyState != saw.ajax.Connection.COMPLETE && this.xhr.readyState != saw.ajax.Connection.UNINITIALIZED;
		if (this.xhr && this.xhr.abort) {
			this.xhr.abort();
			if (a) {
				saw.ajax.pendingRequestCount--
			}
		}
	};
	saw.ajax.XhrConnection.prototype.addHeader = function (a, b) {
		this.aHeader[a] = b
	};
	saw.ajax.XhrConnection.prototype.setSync = function (a) {
		this.bSync = a
	};
	saw.ajax.XhrConnection.prototype._submit = function () {
		this.xhr = saw.ajax.XhrConnection._getXmlHttpReq();
		var f = this.xhr;
		this.req._getReadyState = function () {
			return f.readyState
		};
		if (!this.bSync) {
			var a = this;
			f.onreadystatechange = function () {
				a._processReqChange()
			}
		}
		f.open(this.submitMethod, this.req.sUrl, !this.bSync);
		for (var b = 0; b < this.aHeader.length; ++b) {
			var d = this.aHeader[b];
			f.setRequestHeader(d.name, d.value)
		}
		f.setRequestHeader("X-OBIPS-AJAX", 1);
		var c = this.req.bJsonPalyload;
		var e = "";
		if (this.req.tForm) {
			saw.addHiddenInput(this.req.tForm, "ajaxType", "xhr");
			e = saw.convertFormToString(this.req.tForm)
		} else {
			e = this.req.sQueryString
		}
		if (this.submitMethod == "post" && e.indexOf("_scid=") == -1 && c != true) {
			if (e) {
				e += "&"
			}
			e += "_scid=" + window.obips_scid
		}
		if (this.submitMethod == "post" && e && e.indexOf("icharset") == -1 && c != true) {
			if (e) {
				e += "&"
			}
			e += "icharset=utf-8"
		}
		if (this.submitMethod == "post" && this.req.sUrl.charAt(0) == "/" && c != true) {
			if (e) {
				e += "&"
			}
			e += "urlGenerator=qualified"
		}
		if (e && this.submitMethod == "post") {
			if (c == true) {
				f.setRequestHeader("Content-Type", "application/json")
			} else {
				f.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
			}
		}
		f.send(e);
		if (this.bSync) {
			return this._processReqChange()
		}
	};
	saw.ajax.XhrConnection._getXmlHttpReq = function () {
		var a = null;
		if (window.XMLHttpRequest) {
			a = new XMLHttpRequest()
		} else {
			try {
				a = new ActiveXObject("Msxml2.XMLHTTP")
			} catch (b) {
				try {
					a = new ActiveXObject("Microsoft.XMLHTTP")
				} catch (b) {
					throw new Error(obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgXMLHttpRequestNotSupported").getString())
				}
			}
		}
		return a
	};
	saw.ajax.XhrConnection.prototype._setLoginByPass = function (a) {
		this.bByPassLogin = (a ? true : false)
	};
	saw.ajax.XhrConnection.prototype._processReqChange = function () {
		if (typeof(saw) == "undefined") {
			return
		}
		var l = this.xhr;
		if (l.readyState != saw.ajax.Connection.COMPLETE || !l.status) {
			return
		}
		var f = this.req;
		var c = null;
		try {
			var b = l.status;
			var d = false;
			if (b < 500) {
				var k = l.getResponseHeader("Content-Type");
				if (k == null) {
					d = true
				} else {
					if (b == 403) {
						if (k.indexOf("text/") >= 0 && l.responseText) {
							d = (l.responseText == "OBIPSNotLoggedIn")
						}
					} else {
						if (k.indexOf("text/html") >= 0 && l.responseText) {
							d = l.responseText.indexOf("g_LocaleInfo") >= 0
						}
					}
				}
			}
			if (d && !this.bByPassLogin) {
				this._login()
			} else {
				if (b == 200) {
					var j = new saw.ajax.ResponseRawData(l);
					if (f.responseProcessor) {
						c = f.responseProcessor.processResponse(f, j)
					} else {
						c = (new saw.ajax.SawXhrRespProcessor()).processResponse(f, j)
					}
				} else {
					var h = null;
					if (b == 404) {
						h = obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgRequestError").getString()
					} else {
						var h = obips.ResourceManager.getSingleton().getResource(saw.ajax.resourcefile, "kmsgResponseRetrieveError").getString() + l.statusText + " (" + b + ")";
						if (l.responseText && (l.responseText != "OBIPSNotLoggedIn")) {
							h += ". " + l.responseText
						}
					}
					var a = new saw.ajax.Error(h);
					c = {
						errors: [[a]]
					}
				}
			}
		} catch (g) {
			c = {
				errors: [[new saw.ajax.Error(g.message)]]
			};
			if (saw.userAgent.is_nav) {
				if (g.message == "EmptyResponse") {
					c = null
				}
			}
		}
		finally {
			this._cleanupFinishedRequest(f)
		}
		if (this.bSync) {
			return c
		}
		if (c) {
			saw.ajax.Connection._handleNotification(f, c)
		}
	};
	saw.ajax._isSAWCommandRegExp = /^[A-z0-9_]*$/;
	saw.ajax._isSAWCommand = function (a) {
		return saw.ajax._isSAWCommandRegExp.test(a)
	};
	saw.ajax.Connection.TYPE_IFRAME = "iframe";
	saw.ajax.Connection.TYPE_XHR = "xhr";
	saw.ajax.createConnection = function (a, b, c) {
		if (b == saw.ajax.Connection.TYPE_IFRAME) {
			var d = new saw.ajax.IFrameConnection(a);
			if (c) {
				d.setID(c)
			}
			return d
		} else {
			return new saw.ajax.XhrConnection(a)
		}
	};
	saw.ajax.LoadingIndicator = function () {
		if (obips.mobile && obips.mobile.ProgressIndicator) {
			return new obips.mobile.ProgressIndicator("default")
		}
		this.counter = 0
	};
	saw.ajax.LoadingIndicator.prototype.show = function () {
		if (this.counter == 0) {
			this.cursor = document.body.style.cursor
		}
		document.body.style.cursor = "wait";
		this.counter++
	};
	saw.ajax.LoadingIndicator.prototype.hide = function () {
		if (document.body.style.cursor == "wait") {
			document.body.style.cursor = this.cursor
		}
		this.counter--
	};
	saw.ajax.GlobalBusyIndicator = function () {
		this.show = function () {
			if (saw.header) {
				saw.header.showBusyIndicator(true)
			}
		};
		this.hide = function () {
			if (saw.header) {
				saw.header.showBusyIndicator(false)
			}
		}
	};
	saw.ajax.DummyLoadingIndicator = function () {
		this.show = function () {};
		this.hide = function () {}
	};
	saw.ajax.Manager = function () {
		if (saw.ajax._manager) {
			return saw.ajax._manager
		}
		this.queues = {};
		saw.ajax._manager = this;
		this.defaultProgressIndicator = null;
		this.globalBusyIndicator = new saw.ajax.GlobalBusyIndicator()
	};
	saw.ajax.Manager.getInstance = function () {
		return new saw.ajax.Manager()
	};
	saw.ajax.Manager.prototype.getDefaultProgressIndicator = function () {
		return this.defaultProgressIndicator
	};
	saw.ajax.Manager.prototype.setDefaultProgressIndicator = function (a) {
		this.defaultProgressIndicator = a
	};
	saw.ajax.Manager.prototype.run = function (c) {
		var b = !c.bSync && c.getQueueName();
		if (b) {
			var a = this.queues[b] || new saw.ajax.ConnectionQueue(b);
			a.setCapacity(c.getQueueCapacity());
			this.queues[b] = a;
			a.addAndRun(c)
		} else {
			return c._actualSubmit()
		}
	};
	saw.ajax.Manager.prototype.onRequestHandled = function (c) {
		var b = c.getQueueName();
		if (b) {
			var a = this.queues[b];
			obips.Array.remove(a.running, c);
			a.runNext()
		}
	};
	saw.ajax.ConnectionQueue = function (b, a) {
		this.name = b;
		this.setCapacity(a);
		this.running = [];
		this.waiting = []
	};
	saw.ajax.ConnectionQueue.prototype.setCapacity = function (a) {
		this.capacity = a || 1
	};
	saw.ajax.ConnectionQueue.prototype.addAndRun = function (a) {
		this.waiting.push(a);
		this.runNext()
	};
	saw.ajax.ConnectionQueue.prototype.runNext = function () {
		while (this.running.length < this.capacity && this.waiting.length > 0) {
			var b = this.waiting.shift();
			this.running.push(b);
			b._actualSubmit()
		}
		if (this.timer) {
			clearTimeout(this.timer);
			this.timer = null
		}
		if (this.waiting.length > 0) {
			var a = this;
			this.timer = setTimeout(function () {
					a.runNext()
				}, 50)
		}
	};
	saw.ajax.hasPendingRequest = function () {
		return saw.ajax.pendingRequestCount > 0
	};
	saw.ajax.sendSimpleRequest = function (g, f, h, e, a) {
		var d = new saw.ajax.Request(g, h, e, a);
		if (f) {
			for (var b = 0; b < f.length; b++) {
				d.addArg(f[b].name, f.value)
			}
		}
		var c = saw.ajax.createConnection(d);
		c.post()
	};
	saw.ajax.ResourceTypes = saw.ResourceTypes;
	saw.ajax.getExistingResourceFiles = saw.getExistingResourceFiles;
	saw.ajax.syncLoadJS = function (j, b, a, h) {
		j = saw.fixResourceUrlPath(j);
		b = b ? b : j;
		if (h && h[b] || !saw.ajax.kbReRunLoadedJsFile && saw.ajax.ajaxLoadedStaticJsFiles[b]) {
			return
		}
		var g = null;
		try {
			if (saw.ajax.kbCacheLoadedJsFiles && saw.ajax.ajaxLoadedStaticJsFiles[b]) {
				g = saw.ajax.cachedJsFiles[b]
			} else {
				var c = new saw.ajax.Request(j);
				c.setResponseProcessor({
					processResponse: function (e, k) {
						g = k.text
					}
				});
				c.setLoadingMessageDisplayer(new saw.ajax.LoadingIndicator());
				var d = saw.ajax.createConnection(c);
				d._setLoginByPass(true);
				d.setSync(true);
				d.get()
			}
			if (g) {
				saw.ajax.evalInGlobalScope(g, a);
				if (j.indexOf(".js") == j.length - 3) {
					saw.ajax.ajaxLoadedStaticJsFiles[b] = true;
					if (saw.ajax.kbCacheLoadedJsFiles) {
						saw.ajax.cachedJsFiles[b] = g
					}
				}
			} else {
				saw.log.error('saw.ajax.syncLoadJS: Fail to load "' + j + '"')
			}
		} catch (f) {
			saw.log.error('saw.ajax.syncLoadJS: Fail to load "' + j + '": ' + saw.getStackTraceFromError(f))
		}
		if (h) {
			h[b] = true
		}
	};
	saw.ajax.evalInGlobalScope = function (sScript, tFrame, bOnErrorThrow) {
		if (sScript == "") {
			return
		}
		tFrame = tFrame ? tFrame : window;
		try {
			if (saw.userAgent.is_ie && "execScript" in window) {
				tFrame.execScript(sScript)
			} else {
				if (saw.userAgent.is_webkit) {
					var tDoc = tFrame.document;
					var tScript = tDoc.createElement("script");
					tScript.appendChild(tDoc.createTextNode(sScript));
					tScript.type = "text/javascript";
					tScript.defer = false;
					var head = tDoc.getElementsByTagName("head").item(0);
					head.appendChild(tScript)
				} else {
					tFrame.eval(sScript)
				}
			}
		} catch (e) {
			var sMsg = saw.getStackTraceFromError(e) + " : \n" + sScript;
			saw.log.error(sMsg);
			if (bOnErrorThrow) {
				throw new Error(sMsg)
			}
		}
	};
	saw.ajax.evalScriptsInString = function (m, n, j, e) {
		if (!m) {
			return
		}
		if (!j) {
			j = window
		}
		try {
			var k = j.document;
			n = saw.ajax.getExistingResourceFiles(k, saw.ajax.ResourceTypes.SCRIPT, n);
			var g = /<script[\S\s]*?<\/script>/gi;
			var f = m.match(g);
			if (f) {
				var l = /src\=\"(.*?)\"/i;
				var d = /<script.*?>([\S\s]*?)<\/script>/i;
				for (var h = 0; h < f.length; h++) {
					if (f[h].match(l)) {
						var b = RegExp.$1.replace(/&amp;/gi, "&");
						saw.ajax.syncLoadJS(b, null, j, n)
					} else {
						if (f[h].match(d)) {
							if (is_ie && f[h].match(/ event=\"(.*?)\"/i)) {}
							else {
								saw.ajax.evalInGlobalScope(RegExp.$1, j, e)
							}
						}
					}
				}
			}
		} catch (a) {
			var c = saw.getStackTraceFromError(a) + " : \n" + sScript;
			saw.log.error(c);
			if (e) {
				throw new Error(c)
			}
		}
	};
	saw.ajax.loadCSSFiles = function (g, e, h) {
		if (!g) {
			return
		}
		var d = /<!--.*?-->/gi;
		g = g.replace(d, "");
		var j = /<link.*?type=\"text\/css\"[^>]*/gi;
		var f = g.match(j);
		if (f) {
			h = saw.ajax.getExistingResourceFiles(e, saw.ajax.ResourceTypes.CSS, h);
			var b = /href\=\"(.*?)\"/i;
			for (var c = 0; c < f.length; c++) {
				if (f[c].match(b)) {
					var a = RegExp.$1.replace(/&amp;/gi, "&");
					saw.dynamicLoadCSS(e, a, null, h)
				}
			}
		}
	};
	saw.ajax.evalHtmlHeadFragment = function (a, b) {
		saw.ajax.loadCSSFiles(a, b ? b : document);
		saw.ajax.evalScriptsInString(a, null, window, true)
	};
	saw.ajax.replaceTargetHtml = function (j, a, b, f) {
		var d = [];
		saw.ajax.loadCSSFiles(a, j.ownerDocument, d);
		var g = [];
		saw.ajax.evalScriptsInString(a, g, window, true);
		if (b) {
			saw.ajax.loadCSSFiles(b, j.ownerDocument, d);
			var h = /<link.*?type=\"text\/css\"[^>]*>([\s]*<\/link>)?/gi;
			var c = /<script.*?<\/script>/gi;
			var e = b.replace(h, "");
			e = e.replace(c, "");
			j.innerHTML = e;
			if (f) {
				j.parentNode.replaceChild(saw.getFirstChildElement(j), j)
			}
			saw.ajax.evalScriptsInString(b, g, window, true)
		}
	};
	saw.ajax.setDefaultProgressIndicator = function (a) {
		saw.ajax.Manager.getInstance().setDefaultProgressIndicator(a)
	};
	saw.ajax.pendingRequestCount = 0;
	saw.ajax.kbReRunLoadedJsFile = true;
	saw.ajax.kbCacheLoadedJsFiles = saw.ajax.kbReRunLoadedJsFile;
	saw.ajax.ajaxLoadedStaticJsFiles = {};
	saw.ajax.cachedJsFiles = {};
	saw.ajax.collapseErrors = function (a) {
		var b = "";
		obips.Array.forEach(a, function (c, d) {
			if (d != 0) {
				b += "\n"
			}
			b += c.message
		});
		return b
	}
}
sawr = saw.ajax;
saw.ajax.serverRequest = saw.ajax.Request;
saw.ajax.xhrConnection = saw.ajax.XhrConnection;
sawr.iFrameConnection = saw.ajax.IFrameConnection;
saw.ajax.Request.prototype.setCallerObj = function (a) {
	this.tCallerObj = a
};
saw.ajax.Request.prototype.setContextInfo = function (a) {
	this.tContextInfo = a
};
saw.ajax.Response.prototype.getCallerObj = function (a) {
	saw.logicErrorAlert('This function is obsolete. Use "this" pointer in the callback function.')
};
saw.ajax.Response.prototype.getContextInfo = function () {
	saw.logicErrorAlert("This function is obsolete. Use the second parameter of the callback function.")
};
saw.ajax.Request.prototype.setErrorHandler = function (a) {
	this.fFatalErrorHandler = a
};

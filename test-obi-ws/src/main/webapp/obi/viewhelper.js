if (!window.saw_viewhelper_js) {
	saw_viewhelper_js = 1;
	if (typeof(saw) == "undefined") {
		saw = function () {}
	}
	var strPortletIDReporPrefix = "m:portlet~r:";
	getIdsMapElementId = function (b) {
		var a = b.indexOf(strPortletIDReporPrefix);
		if (a != -1) {
			b = b.substr(a + strPortletIDReporPrefix.length, b.length - a - strPortletIDReporPrefix.length)
		}
		a = b.indexOf("~");
		if (a != -1) {
			b = b.substr(0, a)
		}
		return b
	};
	getEffectiveID = function (b, d) {
		if (d == undefined || d == null || d.length == 0) {
			return b
		}
		var a = getIdsMapElementId(d);
		vDiv = document.getElementById(a);
		if (vDiv == undefined || vDiv == null) {
			return b
		}
		var c = vDiv.getAttribute("Effective_" + b + "_ID");
		if (c != null) {
			return c
		}
		return b
	};
	HereLink = function (d, b, c) {
		var a = XUIPanel.getEditor("idView");
		if (a) {
			a.refreshData()
		} else {
			obips.views.ViewController.handleViewAction("Refresh", d, (c ? {
					ViewState: c
				}
					 : null), null, null, {
				isCumulative: true
			})
		}
	};
	GetMySearchID = function (a) {
		while (a && !((a.tagName == "DIV" || a.tagName == "TD") && a.getAttribute("sid") != null)) {
			a = saw.getParentElement(a)
		}
		if (a) {
			return a.getAttribute("sid")
		} else {
			return ""
		}
	};
	GetMyViewID = function (a) {
		while (a && !((a.tagName == "DIV" || a.tagName == "TD") && a.getAttribute("vid") != null)) {
			a = saw.getParentElement(a)
		}
		if (a) {
			return a.getAttribute("vid")
		} else {
			return ""
		}
	};
	GetMyPromptSearchID = function (a) {
		while (a && !((a.tagName == "DIV" || a.tagName == "TD") && a.getAttribute("sid") != null)) {
			a = saw.getParentElement(a)
		}
		if (a) {
			return a.getAttribute("pid")
		} else {
			return ""
		}
	};
	PortalPageNav = function (b, e, j) {
		b = saw.getEvent(b);
		var g = new NavigateInfo();
		g.SetSearchID(GetMySearchID(saw.getEventTarget(b)));
		g.SetPromptSearchID(GetMyPromptSearchID(saw.getEventTarget(b)));
		g.SetViewID(GetMyViewID(saw.getEventTarget(b)));
		g.SetTargets(new Array(new Array(NQWNavPortal, e, j)));
		var a = 0;
		for (var f = 3; (f + 2 < arguments.length) && (a < 10); f += 3) {
			var h = arguments[f];
			var d = arguments[f + 1];
			var c = arguments[f + 2];
			if (c) {
				g.AddValue("eq", NQMakeFormula(h, d), c)
			}
			++a
		}
		g.oNavObject = g;
		NQNavigate(b, g)
	};
	PortalNav = function (e, c, a, d, b) {
		PortalPageNav(e, c, null, a, d, b)
	};
	NavigateInfo_SetPortal = function (c, a, b) {
		this.sPath = "";
		this.sPortal = c;
		this.sPage = a;
		this.sIsMeasure = (null != b) ? b : "false"
	};
	NavigateInfo_SetReportPath = function (a, b) {
		this.sPath = a;
		this.sIsMeasure = (null != b) ? b : "false"
	};
	NavigateInfo_SetActionLink = function (a, b, d, c) {
		this.sSWEView = a;
		this.sSWEApplet = b;
		this.sSWESrcColumnID = d;
		this.sSWESrcFormula = c
	};
	NQFixTableName = function (a) {
		if (a.search(/\s/) != -1) {
			a = '"' + a + '"'
		}
		return a
	};
	NavigateInfo_AddValue = function (a, b, c) {
		this.AddCodeValue(a, b, c)
	};
	NavigateInfo_AddCodeValue = function (b, a, e, c) {
		var d = new Object();
		d.sFormula = a;
		if (c) {
			d.sCodeFormula = c
		}
		if (e == null) {
			e = "*NQ_NULL*"
		}
		if (e == "*NQ_NULL*") {
			d.sOp = (b == "nin" || b == "neq") ? "nnull" : "null"
		} else {
			d.sOp = b
		}
		d.sValue = e;
		this.vValues[this.vValues.length] = d
	};
	NavigateInfo_SetSearchID = function (a) {
		this.sSearchID = a
	};
	NavigateInfo_SetPromptSearchID = function (a) {
		this.sPromptSearchID = a
	};
	NavigateInfo_SetViewID = function (a) {
		this.sViewID = a
	};
	NavigateInfo_SetTargets = function (a) {
		this.vTargets = a
	};
	NavigateInfo_setIsNewWindow = function (a) {
		this.sWindowTarget = a
	};
	NavigateInfo = function () {
		this.SetReportPath = NavigateInfo_SetReportPath;
		this.SetPortal = NavigateInfo_SetPortal;
		this.SetActionLink = NavigateInfo_SetActionLink;
		this.AddValue = NavigateInfo_AddValue;
		this.SetSearchID = NavigateInfo_SetSearchID;
		this.SetPromptSearchID = NavigateInfo_SetPromptSearchID;
		this.SetViewID = NavigateInfo_SetViewID;
		this.SetTargets = NavigateInfo_SetTargets;
		this.AddCodeValue = NavigateInfo_AddCodeValue;
		this.setIsNewWindow = NavigateInfo_setIsNewWindow;
		this.sPath = null;
		this.sPortal = null;
		this.sPage = null;
		this.sIsMeasure = null;
		this.sWindowTarget = "_self";
		this.sSWEView = null;
		this.sSWEApplet = null;
		this.sSWESrcColumnID = null;
		this.sSWESrcFormula = null;
		this.vValues = new Array();
		this.vTargets = null
	};
	NQNavigate = function (a, c, e) {
		var f = c.oNavObject.vTargets;
		var b = "NQNavigateHandler(" + c.sJSObjName + ",";
		var d = c.oNavObject.sSWEView != null;
		if (d) {
			NQNavigateHandler(c, null);
			return false
		}
		if (f.length == 1) {
			NQNavigateHandler(c, 0);
			return false
		}
		return false
	};
	generateColumnFormulaXMlNode = function (a, b, e) {
		var c = XUICreateElement(saw.xml.kSawNamespace, "columnFormula");
		c.setAttribute("formulaUse", b);
		var d = XUICreateElement(saw.xml.kSawxNamespace, "expr");
		d.setAttribute("xsi:type", "sawx:sqlExpression");
		XUISetElementText(d, e);
		c.appendChild(d);
		a.appendChild(c)
	};
	setExprLHSXMlForMappedColumn = function (b, a, c, d) {
		var e = XUICreateElement(saw.xml.kSawxNamespace, "expr");
		e.setAttribute("xsi:type", "sawx:columnExpression");
		e.setAttribute("formulaUse", d ? "code" : "display");
		generateColumnFormulaXMlNode(e, "display", a);
		b.appendChild(e)
	};
	setExprLHSXMlForUnMappedColumn = function (b, c) {
		var a = XUICreateElement(saw.xml.kSawxNamespace, "expr");
		a.setAttribute("xsi:type", "sawx:sqlExpression");
		XUISetElementText(a, c);
		b.appendChild(a)
	};
	createNavQDRExprFromNavInfo = function (f, h) {
		var g = Math.min(f.vValues.length, 64);
		var m = f.vTargets[h];
		m[0](m, f);
		var b = XUICreateElement(saw.xml.kSawxNamespace, "expr");
		if (g > 0) {
			b = XUICreateElement(saw.xml.kSawxNamespace, "expr");
			b.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			b.setAttribute("xsi:type", "sawx:logical");
			b.setAttribute("op", "and");
			for (var e = 0; e != g; ++e) {
				var c = f.vValues[e];
				if (!f.navColType || f.navColType[e] == false) {
					var j = XUIAppendNewElement(b, saw.xml.kSawxNamespace, "expr");
					XUISetXsiType(j, saw.xml.kSawxNamespace, "comparison");
					XUISetAttributeString(j, "op", (null != c.sOp && c.sOp == "null") ? "null" : "equal");
					var k = (c.sCodeFormula != null);
					if (k) {
						setExprLHSXMlForMappedColumn(j, c.sFormula, c.sCodeFormula, true)
					} else {
						setExprLHSXMlForUnMappedColumn(j, c.sFormula)
					}
					if (null == c.sOp || c.sOp != "null") {
						var d = XUIAppendNewElement(j, saw.xml.kSawxNamespace, "expr");
						XUISetXsiType(d, saw.xml.kSawxNamespace, "untypedLiteral");
						XUISetElementText(d, c.sValue)
					}
				} else {
					if (f.navColType && e < f.navColType.length && f.navColType[e] == true) {
						var a = obips.XMLDOM.parseXML(c.sValue);
						var l = obips.XMLDOM.selectSingleNode(a, "sawx:expr");
						if (l) {
							b.appendChild(l.cloneNode(true))
						}
					}
				}
			}
			if (g == 1) {
				b = b.firstChild
			}
		}
		return {
			nVals: g,
			tExpr: b
		}
	};
	NQNavigateHandler = function (i, h) {
		NQWClearActiveMenu();
		var f = i.oNavObject;
		var d = f.sSWEView != null;
		if (d) {
			if (f.sNavValue != "") {
				NQSWENav(f.sSWEView, f.sSWEApplet, f.sNavValue)
			}
			return
		}
		var k = createNavQDRExprFromNavInfo(f, h);
		var g = k.nVals;
		var b = k.tExpr;
		var a = f.sPortal != null;
		var m = {
			reloadInplace: false,
			commandRequest: "go"
		};
		var l = {};
		l.Path = f.sPath;
		l.ViewID = "";
		var c = document.getElementById("idPageID");
		if (c) {
			l.PageID = c.getAttribute("pageID")
		}
		if (g > 0) {
			l.P0 = saw.getXmlText(b)
		}
		l.P19 = f.sSearchID;
		l.P17 = f.sPromptSearchID;
		l.P16 = "NavRuleDefault";
		l.NavFromViewID = f.sViewID;
		if (a) {
			m.command = "Dashboard";
			m.workspaceModule = "biee.dashboards";
			l.PortalPath = f.sPortal;
			l.P1 = "dashboard";
			if (f.sPage) {
				l.Page = f.sPage
			} else {
				l.Page = ""
			}
		} else {
			var e = f.sViewID.substr(0, f.sViewID.indexOf("~v:") == -1 ? f.sViewID.length : f.sViewID.indexOf("~v:"));
			var j = document.getElementById(e + "Result");
			if (j) {
				l.P18 = j.getAttribute("options").replace(new RegExp("s", "gi"), "")
			}
		}
		m.mobileNav = a ? "dashboard" : "report";
		m.targetWindow = f.sWindowTarget;
		obips.views.ViewController.handleViewAction("Navigate", f.sViewID, l, m, null, {
			canChangeStateId: false,
			requireUpToDateStateId: false
		})
	};
	GoNav = function (f, d, a, e, c, b) {
		GoNavEx(f, d, b, a, e, c)
	};
	NQMakeFormula = function (b, a) {
		return NQFixTableName(b) + "." + NQFixTableName(a)
	};
	GoNavEx = function (a, c, b, h, o, p, g, n, m, f, l, k, e, j, i) {
		a = saw.getEvent(a);
		var d = new NavigateInfo();
		d.SetTargets(new Array(new Array(NQWNavReport, c)));
		d.SetSearchID(GetMySearchID(saw.getEventTarget(a)));
		d.SetPromptSearchID(GetMyPromptSearchID(saw.getEventTarget(a)));
		d.SetViewID(GetMyViewID(saw.getEventTarget(a)));
		d.setIsNewWindow(b);
		if (p) {
			d.AddValue("eq", NQMakeFormula(h, o), p)
		}
		if (m) {
			d.AddValue("eq", NQMakeFormula(g, n), m)
		}
		if (k) {
			d.AddValue("eq", NQMakeFormula(f, l), k)
		}
		if (i) {
			d.AddValue("eq", NQMakeFormula(e, j), i)
		}
		d.oNavObject = d;
		NQNavigate(a, d)
	};
	NQWNavReport = function (a, b) {
		b.SetReportPath(a[1], a[2])
	};
	NQWNavPortal = function (a, b) {
		b.SetPortal(a[1], a[2], a[3])
	};
	NQWNavActionLink = function (a, b) {
		b.SetActionLink(a[1], a[2], a[3], a[4])
	};
	NQSWENav = function (a, c, d) {
		try {
			top.SWEPersonalizationGotoview(a, "SWEPostnApplet=" + saw.encodeURIComponent(c) + "&SWEPostnRowId=" + saw.encodeURIComponent(d) + "&SWEReloadFrames=0")
		} catch (b) {}
	};
	saw.doDownload = function (b) {
		var a = new obips.DownloadGuard(b)
	};
	saw._download = function (b) {
		var f = b.url;
		var g = b.viewname;
		var h = b.id;
		var a = b.viewpath;
		var c = saw.view.getClientStateManager(a);
		var e = c.getCurrentStateXmlAsString();
		if (g != null) {
			f = f + "&ViewName=" + g
		}
		var d = saw.createFormFromString(f);
		saw.addHiddenInput(d, "DownloadId", h);
		if (e) {
			saw.addHiddenInput(d, "clientStateXml", e)
		}
		if (is_ie) {
			saw.createHiddenIFrame("sawIEDownload", "sawIEDownload", null);
			d.target = "sawIEDownload"
		} else {
			saw.createHiddenIFrame("sawMozDownload", "sawMozDownload", null);
			d.target = "sawMozDownload"
		}
		d.submit()
	};
	Download = function (b, c, a) {
		saw.doDownload({
			url: b,
			viewname: c,
			viewpath: a,
			downloadFunc: saw._download
		})
	};
	saw.printAnalysis = function (h, d, c, b) {
		if (d) {
			h += "&Format=" + d
		}
		var g = saw.createFormFromString(h);
		saw.addHiddenInput(g, "Action", "print");
		c = saw.getEvent(c);
		var a = saw.getEventCurrentTarget(c).target || "_blank";
		g.target = a;
		var e = saw.view.getClientStateManager(b);
		var f = e.getCurrentStateXmlAsString();
		if (f) {
			saw.addHiddenInput(g, "clientStateXml", f)
		}
		g.submit()
	};
	ViewBack = function (f, a, c) {
		var d = {};
		var e = {};
		var g = null;
		if ("PromptRestart" == a) {
			d = {
				ignoreClientStateXml: true
			}
		} else {
			if ("DrillBack" == a) {
				g = c;
				c = null
			}
		}
		if (c == null) {
			var b = obips.views.ViewController.getController(f);
			if (b) {
				c = b.getViewEnvParam("ViewState")
			}
		}
		e.ViewState = c;
		e.NewViewState = g;
		obips.views.ViewController.handleViewAction(a, f, e, d)
	};
	tryAccessSubjectArea = function (b, e) {
		var a = {};
		var d = new saw.ajax.Request("canAccessSubjectArea", this.handleAccessSubjectArea, this, a);
		if (b) {
			d.addArg("Path", b);
			a.path = b
		}
		a.cmd = e;
		var c = saw.ajax.createConnection(d);
		c.post()
	};
	handleAccessSubjectArea = function (b, o) {
		var h = b.getBlockById("json").data.canAccess;
		if (h != null && h == "true") {
			var a = b.getBlockById("json").data.objectType;
			if (a != null && a == "report") {
				var e = o.cmd;
				var k = {
					workspaceModule: "biee.answers",
					ensureFreshUrl: true
				};
				var n = saw.getSessionInfos().user.prefs.getValue("defaultAnalysisMode");
				if (saw.getSessionInfos().isEnableSection508() || (n == "composer" && saw.catalog.getPrivilege("canUseBIComposer") && saw.catalog.getPrivilege("biComposerEnabled"))) {
					k = {
						workspaceModule: "biee.answers",
						targetWindow: "_blank"
					};
					var g = saw.createFormFromString(e);
					if (saw.getSessionInfos().isEnableSection508()) {
						e = saw.getSessionInfos().getProperty("biComposerContext") + "/faces/answersWizard.jspx?accMode=screenReader"
					} else {
						e = saw.getSessionInfos().getProperty("biComposerContext") + "/faces/answersWizard.jspx?accMode=inaccessible"
					}
					var f = g.Folder.value + "/" + saw.captionToItemName(g.ItemName.value);
					var l = saw.catalog.getPrivilege("biUIDefaultStyle");
					e += "&skinFamily=" + saw.encodeURIComponent(l) + "&path=" + saw.encodeURIComponent(f)
				}
				obips.launcher.launchURL(e, k);
				return false
			} else {
				if (a != null && a == "prompt") {
					var d = "&path=" + saw.encodeURIComponent(o.path);
					obips.launcher.launchURL(saw.commandToURL("Answers") + d, o, "biee.answers");
					return false
				}
			}
		} else {
			var c = saw.catalog.getLocalizedString("kmsgCatalogSubjectAreaAccessDenied");
			var m = saw.catalog.getLocalizedString("kmsgCatalogErrorDialogTitle");
			var j = new obips.MessageDialog.Model(m, c, null, obips.MessageDialog.TITLE_IMAGE_ERROR);
			var i = new obips.MessageDialog.Viewer(j, obips.FloatingWindow.Manager.getSingleton())
		}
		return true
	};
	ModifyReport = function (d, c) {
		if (NQWIsInAnswers()) {
			if (parent.saw && parent.saw.runThisURL) {
				parent.saw.runThisURL(d)
			} else {
				parent.location.href = d
			}
		} else {
			var b = saw.createFormFromString(d);
			var a = b.Folder.value + "/" + saw.captionToItemName(b.ItemName.value);
			this.tryAccessSubjectArea(a, d);
			return false
		}
	};
	ModifyPrompt = function (a) {
		this.tryAccessSubjectArea(a);
		return false
	};
	GetViewParent = function (a) {
		if (!saw.checkObjectReference(a)) {
			return ""
		}
		var b = 0;
		var c = a;
		if (a) {
			while (c.indexOf("~") >= 0) {
				b += c.indexOf("~");
				c = c.substr(c.indexOf("~") + 1, c.length)
			}
			return a.substr(0, b + 1)
		} else {
			return a
		}
	};
	saw.getReloadInline = function (a, c) {
		if (!c) {
			c = a.substr(0, a.indexOf("~v:") == -1 ? a.length : a.indexOf("~v:"))
		}
		var b = document.getElementById(getEffectiveID("EmbedView" + c, a));
		return (b && b.getAttribute("reloadInline") == "true")
	};
	saw.view = function () {};
	saw.view.getRefreshStatePath = function (c, d) {
		var b = c.indexOf("~v:compoundView");
		var a = c;
		if (b != -1) {
			a = c.substr(0, b)
		} else {
			b = c.indexOf("~v:" + d);
			if (b != -1) {
				a = c.substr(0, b)
			}
		}
		return a
	};
	saw.view.showAddToBriefingBookDialog = function (d, f, c, b, a) {
		var e = new saw.ondemandload.FuncProxy("obips.briefingbook.launchAddToBBDialog", {
				messageTemplate: "kuiAddToBriefingBookDialogHead"
			});
		e.exec(d, f, c, true, b, a)
	};
	saw.view.getClientStateManager = function (a) {
		var b = obips.views.ClientStateManager.getReportsViewStatePath(a);
		var c = obips.views.ViewController.getController(b);
		return c.getClientStateManager()
	};
	saw.view.getGlobalViewStateID = function () {
		var c = obips.views.ClientStateManager.getReportsViewStatePath();
		var d = obips.views.ViewController.getController(c);
		var a = null;
		var b = d.getClientStateManager();
		if (b) {
			a = b.getCurrentStateID()
		}
		if (!a && d.viewEnv) {
			a = d.viewEnv.viewState
		}
		return a
	}
};

if (!window.obips_javascriptextensions_js) {
	obips_javascriptextensions_js = 1;
	if (typeof(obips) === "undefined") {
		obips = {}
	}
	function extend(c, a) {
		function b() {}
		b.prototype = a.prototype;
		c.prototype = new b();
		c.prototype.constructor = c;
		c.baseConstructor = a;
		c.superClass = a.prototype
	}
	function clone(f) {
		var a = null;
		if (typeof(f) !== "object" || f === null) {
			a = f
		} else {
			if (f instanceof Array) {
				a = obips.Array.clone(f, true)
			} else {
				var c = new Object();
				for (var b in f) {
					var d = (f[b]instanceof Array);
					c[b] = d ? obips.Array.clone(f[b], true) : clone(f[b])
				}
				a = c
			}
		}
		return a
	}
	obips.getSingleton = function (f) {
		var c = window;
		var b = null;
		var a = 0;
		while (b == null) {
			a++;
			if (a > 50) {
				break
			}
			try {
				if (c.globalRegister) {
					b = c.globalRegister.getObject(f)
				}
			} catch (d) {
				break
			}
			if (c != top) {
				c = c.parent
			} else {
				break
			}
		}
		return b
	};
	obips.Array = function () {};
	obips.Array.indexOf = function (d, c) {
		var a = -1;
		for (var b = 0; b < d.length; b++) {
			if (d[b] == c) {
				a = b;
				break
			}
		}
		return a
	};
	obips.Array.contains = function (b, a) {
		return (obips.Array.indexOf(b, a) > -1)
	};
	obips.Array.append = function (f, d, b, c) {
		var a = false;
		if (!(b && obips.Array.contains(f, d))) {
			if (c) {
				f.unshift(d)
			} else {
				f.push(d)
			}
			a = true
		}
		return a
	};
	obips.Array.remove = function (c, b) {
		var a = obips.Array.indexOf(c, b);
		if (a >= 0) {
			return c.splice(a, 1)
		} else {
			return null
		}
	};
	obips.Array.clone = function (a, d) {
		d = (d == true);
		var b = new Array();
		if (a.length == 0) {
			for (elementID in a) {
				b[elementID] = d ? clone(a[elementID]) : a[elementID]
			}
		} else {
			for (var c = 0; c < a.length; c++) {
				b[c] = d ? clone(a[c]) : a[c]
			}
		}
		return b
	};
	obips.Array.mapToIndex = function (b) {
		var a = new Array();
		for (key in b) {
			a.push(b[key])
		}
		return a
	};
	obips.Array.map = function (c, k, d) {
		var g = null;
		if (!Array.prototype.map) {
			var b = c.length;
			var j = new Array(b);
			for (var h = 0; h < b; ++h) {
				if (h in c) {
					j[h] = k.call(d, c[h], h, c)
				}
			}
			g = j
		} else {
			g = c.map(k, d)
		}
		return g
	};
	if (!Array.prototype.forEach) {
		obips.Array.forEach = function (c, h, d) {
			var b = c.length;
			for (var g = 0; g < b; ++g) {
				if (g in c) {
					h.call(d, c[g], g, c)
				}
			}
		}
	} else {
		obips.Array.forEach = function (b, d, c) {
			return b.forEach(d, c)
		}
	}
	obips.AssociativeArray = function () {};
	obips.AssociativeArray.isEmpty = function (a) {
		for (e in a) {
			return false
		}
		return true
	};
	obips.AssociativeArray.getKeys = function (a) {
		var b = new Array();
		for (key in a) {
			b.push(key)
		}
		return b
	};
	Number.prototype.toBinaryByte = function () {
		var b = parseInt(this);
		var d = new Array(128, 64, 32, 16, 8, 4, 2, 1);
		var c = null;
		if (b <= 255 && b >= 0) {
			c = "";
			for (var a = 0; a < 8; a++) {
				if (b >= d[a]) {
					c = c.concat("1");
					b = b - d[a]
				} else {
					c = c.concat("0")
				}
			}
		}
		return c
	};
	Number.prototype.toHex = function () {
		return this.toString(16)
	};
	Date.XSD = function (c) {
		var b = null;
		if (c != null) {
			var d = parseInt(c.substr(0, 4));
			var f = c.substr(5, 2);
			var a = parseInt(c.substr(8, 2));
			b = new Date(d, f - 1, a)
		}
		return b
	};
	Date.prototype.getXSDDate = function () {
		var b = this.getUTCFullYear();
		var d = "0".concat(this.getMonth() + 1);
		var f = d.substr(d.length - 2, 2);
		var c = "0".concat(this.getDate());
		var a = c.substr(c.length - 2, 2);
		return b + "-" + f + "-" + a
	};
	Date.prototype.getXSDDateTime = function () {
		var b = this.getXSDDate();
		var a = this.getXSDTime();
		return b + "T" + a
	};
	Date.prototype.getXSDTime = function () {
		var g = "0".concat(this.getHours());
		var a = g.substr(g.length - 2, 2);
		var b = "0".concat(this.getMinutes());
		var c = b.substr(b.length - 2, 2);
		var d = "0".concat(this.getSeconds());
		var f = d.substr(d.length - 2, 2);
		return a + ":" + c + ":" + f
	};
	Date.prototype.getFormattedDate = function () {
		var a = this.getDate();
		var c = Date.Months[this.getMonth()];
		var b = this.getFullYear();
		return a + " " + c + " " + b
	};
	Date.Months = new Array();
	Date.Months.push("January");
	Date.Months.push("February");
	Date.Months.push("March");
	Date.Months.push("April");
	Date.Months.push("May");
	Date.Months.push("June");
	Date.Months.push("July");
	Date.Months.push("August");
	Date.Months.push("September");
	Date.Months.push("October");
	Date.Months.push("November");
	Date.Months.push("December");
	obips.String = function () {};
	obips.String.hashCode = function (c) {
		var d = 0;
		if (this.length > 0) {
			for (var a = 0; a < this.length; a++) {
				var b = this.charCodeAt(a);
				d = ((d << 5) - d) + b;
				d = d & d
			}
		}
		d = "h" + d;
		d = d.replace(/-/gi, "_");
		return d
	};
	obips.Callback = function (a, b) {
		this.thisContext = a;
		this.functionPointer = b
	};
	obips.Callback.prototype.exec = function () {
		return this.functionPointer.apply(this.thisContext ? this.thisContext : null, arguments)
	};
	obips.Callback.createFunctionRefWithContext = function (a, b) {
		return function () {
			b.apply(a, args)
		}
	};
	obips.CallbackCustom = function (b, d, c, a) {
		this.thisContext = b;
		this.functionPointer = d;
		this.userObject = c;
		this.uniqueId = a;
		this.isACustomCallback = true
	};
	obips.CallbackCustom.prototype.exec = function () {
		var a = obips.Array.clone(arguments);
		a.push(this.userObject);
		a.push(this.uniqueId);
		return this.functionPointer.apply(this.thisContext ? this.thisContext : null, a)
	};
	obips.CallbackChain = function (b) {
		this.isACallbackChain = true;
		for (var a = 0; a < b.length; a++) {
			if (b[a].isACallbackChain) {
				saw.logicErrorAlert("Assertion failure: cannot add a callback chain to a callback chain, instead use the 'addNewCallback' method to add a callback to this callback chain");
				return
			}
		}
		this.callbacksChain = b
	};
	obips.CallbackChain.prototype.clone = function (d) {
		var a = new Array();
		var c = new obips.CallbackChain(a);
		for (var b = 0; b < d.callbacksChain.length; b++) {
			c.addNewCallback(d.callbacksChain[b])
		}
		return c
	};
	obips.CallbackChain.prototype.addNewCallback = function (a) {
		if (a.isACallbackChain) {
			saw.logicErrorAlert("Assertion failure: cannot add a callback chain to a callback chain, add individual callbacks to this callback chain")
		} else {
			this.callbacksChain.unshift(a)
		}
	};
	obips.CallbackChain.prototype.exec = function () {
		var b = this.callbacksChain.shift();
		var a = obips.Array.clone(arguments);
		if (b.isACustomCallback) {
			a.push(b.userObject);
			a.push(b.uniqueId);
			a.push(this);
			b.functionPointer.apply(b.thisContext ? b.thisContext : null, a)
		} else {
			a.push(this);
			b.functionPointer.apply(b.thisContext ? b.thisContext : null, a)
		}
	};
	if (!document.importNode) {
		document.importNode = function (d, f) {
			var c;
			if (d.nodeType == 1) {
				c = document.createElement(d.nodeName);
				for (var b = 0; b < d.attributes.length; b++) {
					var a = d.attributes[b];
					if (a.nodeValue != null && a.nodeValue != "") {
						c.setAttribute(a.name, a.nodeValue)
					}
				}
				if (typeof d.style != "undefined") {
					c.style.cssText = d.style.cssText
				}
			} else {
				if (d.nodeType == 3) {
					c = document.createTextNode(d.nodeValue)
				}
			}
			if (f && d.hasChildNodes()) {
				for (var b = 0; b < d.childNodes.length; b++) {
					c.appendChild(document.importNode(d.childNodes[b], true))
				}
			}
			return c
		}
	}
	obips.createException = function (c, b) {
		var a = new Error(b);
		a.errorCode = c;
		return a
	};
	obips.asyncCall = function (b, g) {
		if (typeof(obipsAsyncIDs) === "undefined") {
			obipsAsyncIDs = {}
		}
		var d = "obips_async_" + new Date().valueOf();
		var h = d;
		var f = 0;
		while (obipsAsyncIDs[h] && f < 100) {
			h = d + (++f)
		}
		if (f === 100) {
			saw.logicErrorAlert("Too many async call requests!")
		} else {
			var g = g || 1;
			var a = [];
			for (var c = 2; c < arguments.length; ++c) {
				a.push(arguments[c])
			}
			obipsAsyncIDs[h] = {
				callback: b,
				args: a
			};
			return setTimeout("obips.asyncCallHandler('" + h + "')", g)
		}
	};
	obips.asyncCallHandler = function (b) {
		if (typeof(obipsAsyncIDs) !== "undefined" && obipsAsyncIDs) {
			var a = obipsAsyncIDs[b];
			a.callback.functionPointer.apply(a.callback.thisContext, a.args);
			obipsAsyncIDs[b] = undefined
		}
	}
};

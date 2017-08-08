Array.prototype.indexOfKey = function(key) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] && this[i].key == key) {
			return i;
		}
	}

	return -1;
};

Array.prototype.indexOf = function(elm) {
	for (var i = 0; i < this.length; i++)
		if (elm == this[i])
			return i;
	return -1;
}

Array.prototype.erase = function(elm) {
	for (var i = 0; i < this.length; i++)
		if (elm == this[i])
			this[i] = null;
}

Array.prototype.get = function(key) {
	var index = this.indexOfKey(key);

	if (-1 == index)
		return null;

	return this[index];
}

Array.prototype.removei = function(index) {
	if (index < 0 || index >= this.length)
		return;
	for (var i = index; i < this.length; i++)
		this[i] = this[i + 1];
	this.length--;
}

// remove the element in the array
Array.prototype.removeo = function(obj) {
	var bl;

	bl = false;
	for (var i = 0; i < this.length; i++) {
		if (obj === this[i] || bl) {
			this[i] = this[i + 1];
			bl = true;
		}
	}
	if (bl) {
		this.length--;
	}
}

String.prototype.trim=function(){
	return this.replace(/^\s*|\s*$/g,'');
}

String.prototype.trimn=function(){
	return this.replace(/^[\n|\r\n]*|[\n|\r\n]*$/g,'');
}

if (window.zk) {
	throw new Error("zk has registered.");
}
(function() {
	window.STRING_EMPTY = "";

	var userAgent = navigator.userAgent.toLowerCase();

	window.zk = {
		browser : {
			version : (userAgent.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [
					0, '0'])[1],
			safari : /webkit/.test(userAgent),
			opera : /opera/.test(userAgent),
			msie : /msie/.test(userAgent) && !/opera/.test(userAgent),
			mozilla : /mozilla/.test(userAgent)
					&& !/(compatible|webkit)/.test(userAgent)
		},
		get : function(id) {
			return document.getElementById(id);
		},
		setStyle : function(el, styleText) {
			if (this.browser.msie) {
				el.style.cssText = styleText;
			} else {
				el.setAttribute("style", styleText);
			}
		},
		addCss : function(el, css) {
			if (!css || '' == css) {
				return;
			}
			var ncss = this._getCsss(el);
			var acsss = css.split(/\s+/);
			for (var i = 0; i < acsss.length; i++) {
				ncss[acsss[i]] = acsss[i];
			}
			var cssName = '';
			for (k in ncss) {
				if ('' == k) {
					continue;
				}
				cssName += ' ' + k;
			}
			this.setCss(el, cssName);
		},
		removeCss : function(el, css) {
			if (!css || '' == css) {
				return;
			}
			var ncss = this._getCsss(el);
			var cssName = '';
			for (k in ncss) {
				if ('' == k || css == k) {
					continue;
				}
				cssName += ' ' + k;
			}
			this.setCss(el, cssName);
		},
		_getCsss : function(el) {
			var ncss = {};
			var ocss = this.getCss(el);
			var oacsss = ocss ? ocss.split(/\s+/) : [];
			for (var i = 0; i < oacsss.length; i++) {
				ncss[oacsss[i]] = oacsss[i];
			}

			return ncss;
		},
		getCss : function(el) {
			if (this.browser.msie) {
				return el.getAttribute("className");
			} else {
				return el.getAttribute("class");
			}
		},
		setCss : function(el, argCssName) {
			if (this.browser.msie) {
				el.setAttribute("className", argCssName);
			} else {
				el.setAttribute("class", argCssName);
			}
		},
		clearCss : function(el) {
			this.setStyle('');
			if (this.browser.msie) {
				el.removeAttribute("className");
			} else {
				el.removeAttribute("class");
			}
		},
		/**
		 * get the left of the element
		 */
		getPixLeft : function(argObj) {
			var pixleft;

			pixleft = 0;
			while (argObj) {
				pixleft += argObj.offsetLeft;
				argObj = argObj.offsetParent;
			};

			return pixleft;
		},
		/**
		 * get the top of the element
		 */
		getPixTop : function(argObj) {
			var pixtop;

			pixtop = 0;
			while (argObj) {
				pixtop += argObj.offsetTop;
				argObj = argObj.offsetParent;
			};

			return pixtop;
		},

		remove : function(argObj) {
			if (argObj.parentNode) {
				argObj.parentNode.removeChild(argObj);
			}
		},

		on : function(elem, type, fn) {
			if (elem.addEventListener)
				elem.addEventListener(type, function(e) {
							fn(e);
						}, false);
			else if (elem.attachEvent)
				elem.attachEvent("on" + type, function() {
							fn(window.event);
						});
		},
		/*
		 * un : function(el, type, fn) { $(el).unbind(type, fn); },
		 */
		oe : function(o, type, fn) {
			if (!o.eves)
				o.eves = [];
			if (!o.eves[type])
				o.eves[type] = [];
			o.eves[type][o.eves[type].length] = fn;
		},
		ue : function(o, type, fn) {
			if (!o.eves)
				return;
			if (!o.eves[type])
				return;
			o.eves[type].erase(fn);
		},
		fire : function(o, type, ops) {
			if (!o.eves)
				return;
			if (!o.eves[type])
				return;
			var e = {
				src : o,
				stop : false
			};
			for (var i = 0; i < o.eves[type].length; i++) {
				if (o.eves[type][i]) {
					o.eves[type][i](e, ops);
					if (e.stop)
						return;
				}
			}
		},
		cr : function(tag) {
			return document.createElement(tag);
		},
		log : function(msg) {
			if (!this.console) {
				return;
			}
			this.console.value += msg + '\n';
		},
		ok : function(fn) {
			zk.oe(zk, 'ok', fn);
		},
		isnum : function(argNum) {
			if (0 != argNum && !argNum) {
				return false;
			}

			if ("" == argNum) {
				return false;
			}

			return !isNaN(new Number(argNum));
		}
	};

	zk.on(window, "load", function() {
				zk.fire(zk, 'ok');
			});
})();
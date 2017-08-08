if (!window.test) {
	window.test = {};
}

window.test.Beanform = function(form) {
	var _this = this;

	var els_r = [];
	var els_ex = [];
	var msgs = [];
	var fns = [];

	this.require = function(txt, msg) {
		if (!txt && !txt.value) {
			return;
		}
		els_r[els_r.length] = {
			el : txt,
			msg : msg
		};
	};

	this.regex = function(txt, regex, msg) {
		if (!txt || !txt.value || !regex || ''==regex) {
			return;
		}
		els_ex[els_ex.length] = {
			el : txt,
			regex : regex,
			msg : msg
		};
	};
	
	this.valide = function (fn){
		if (!fn){		
			return;
		}
		fns[fns.length]=fn;
	};

	form.onsubmit=function(e) {
						
				for (var i = 0; i < els_r.length; i++) {
					var el = els_r[i].el;
					if ('' == el.value.trimn()) {
						msgs[msgs.length] = els_r[i].msg;
					}
				}

				for (var i = 0; i < els_ex.length; i++) {
					var el = els_ex[i].el;
					var regex =  new RegExp(els_ex[i].regex);
					if (!regex.test(el.value)) {
						msgs[msgs.length] = els_ex[i].msg;
					}
				}
				
				for (var i = 0; i < fns.length; i++) {
					var msg=fns[i]();
					if (msg) {
						msgs[msgs.length] = msg;
					}
				}

				if (0 == msgs.length) {
					return true;
				}

				alert(msgs.join("\n"));
				msgs = [];

				return false;
			};
};
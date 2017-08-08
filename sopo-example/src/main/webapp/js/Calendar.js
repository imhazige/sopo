zk.Calendar.language = {
	"year" : "",
	// "months" :
	// ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],
	"months" : ["һ��", "����", "����", "����", "����", "����", "����", "����", "����", "ʮ��",
			"ʮһ��", "ʮ����"],
	"weeks" : ["����", "��һ", "�ܶ�", "����", "����", "����", "����"],
	"clear" : "���",
	"today" : "����",
	"close" : "�ر�",
	"ok" : "ȷ��"
};

if (!window.test) {
	window.test = {};
}

window.test.Calendar = {
	show : function(el, fmt) {
		if (!window.test.Calendar.dpk) {
			window.test.Calendar.dpk = new zk.Calendar();
		}
		var dp = window.test.Calendar.dpk;
		dp.hide();
		dp.dateFormatStyle = fmt;
		dp.show({
					to : el
				});
	}
}
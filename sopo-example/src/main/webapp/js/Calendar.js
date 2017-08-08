zk.Calendar.language = {
	"year" : "",
	// "months" :
	// ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],
	"months" : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月",
			"十一月", "十二月"],
	"weeks" : ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
	"clear" : "清空",
	"today" : "今天",
	"close" : "关闭",
	"ok" : "确定"
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
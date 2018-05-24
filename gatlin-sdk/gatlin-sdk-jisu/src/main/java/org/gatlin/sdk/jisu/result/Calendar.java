package org.gatlin.sdk.jisu.result;

import java.io.Serializable;

public class Calendar implements Serializable {

	private static final long serialVersionUID = -7733573146675709225L;

	// 年
	private String year;
	// 月
	private String month;
	// 日
	private String day;
	// 星期
	private String week;
	// 农历年
	private String lunaryear;
	// 农历月
	private String lunarmonth;
	// 农历日
	private String lunarday;
	// 干支
	private String ganzhi;
	// 生肖
	private String shengxiao;
	// 黄历
	private String star;
	// 农历
	private HuangLi huangli;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getLunaryear() {
		return lunaryear;
	}

	public void setLunaryear(String lunaryear) {
		this.lunaryear = lunaryear;
	}

	public String getLunarmonth() {
		return lunarmonth;
	}

	public void setLunarmonth(String lunarmonth) {
		this.lunarmonth = lunarmonth;
	}

	public String getLunarday() {
		return lunarday;
	}

	public void setLunarday(String lunarday) {
		this.lunarday = lunarday;
	}

	public String getGanzhi() {
		return ganzhi;
	}

	public void setGanzhi(String ganzhi) {
		this.ganzhi = ganzhi;
	}

	public String getShengxiao() {
		return shengxiao;
	}

	public void setShengxiao(String shengxiao) {
		this.shengxiao = shengxiao;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public HuangLi getHuangli() {
		return huangli;
	}

	public void setHuangli(HuangLi huangli) {
		this.huangli = huangli;
	}

	public static class HuangLi implements Serializable {

		private static final long serialVersionUID = -7525272363042156918L;

		// 星座
		private String nongli;
		// 胎神
		private String taishen;
		// 五行
		private String wuxing;
		// 冲
		private String chong;
		// 煞
		private String sha;
		// 吉日
		private String jiri;
		// 值日星神
		private String zhiri;
		// 凶神
		private String xiongshen;
		// 吉神宜趋
		private String jishenyiqu;
		// 财神
		private String caishen;
		// 喜神
		private String xishen;
		// 福神
		private String fushen;
		// 岁次
		private String[] suici;
		// 宜
		private String[] yi;
		// 忌
		private String[] ji;

		public String getNongli() {
			return nongli;
		}

		public void setNongli(String nongli) {
			this.nongli = nongli;
		}

		public String getTaishen() {
			return taishen;
		}

		public void setTaishen(String taishen) {
			this.taishen = taishen;
		}

		public String getWuxing() {
			return wuxing;
		}

		public void setWuxing(String wuxing) {
			this.wuxing = wuxing;
		}

		public String getChong() {
			return chong;
		}

		public void setChong(String chong) {
			this.chong = chong;
		}

		public String getSha() {
			return sha;
		}

		public void setSha(String sha) {
			this.sha = sha;
		}

		public String getJiri() {
			return jiri;
		}

		public void setJiri(String jiri) {
			this.jiri = jiri;
		}

		public String getZhiri() {
			return zhiri;
		}

		public void setZhiri(String zhiri) {
			this.zhiri = zhiri;
		}

		public String getXiongshen() {
			return xiongshen;
		}

		public void setXiongshen(String xiongshen) {
			this.xiongshen = xiongshen;
		}

		public String getJishenyiqu() {
			return jishenyiqu;
		}

		public void setJishenyiqu(String jishenyiqu) {
			this.jishenyiqu = jishenyiqu;
		}

		public String getCaishen() {
			return caishen;
		}

		public void setCaishen(String caishen) {
			this.caishen = caishen;
		}

		public String getXishen() {
			return xishen;
		}
		
		public String getFushen() {
			return fushen;
		}
		
		public void setFushen(String fushen) {
			this.fushen = fushen;
		}

		public void setXishen(String xishen) {
			this.xishen = xishen;
		}

		public String[] getSuici() {
			return suici;
		}

		public void setSuici(String[] suici) {
			this.suici = suici;
		}

		public String[] getYi() {
			return yi;
		}

		public void setYi(String[] yi) {
			this.yi = yi;
		}

		public String[] getJi() {
			return ji;
		}

		public void setJi(String[] ji) {
			this.ji = ji;
		}
	}
}

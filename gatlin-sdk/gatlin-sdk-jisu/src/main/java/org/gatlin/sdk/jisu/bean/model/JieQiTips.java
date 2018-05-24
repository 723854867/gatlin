package org.gatlin.sdk.jisu.bean.model;

import java.io.Serializable;

public class JieQiTips implements Serializable {

	private static final long serialVersionUID = -4331950199424568216L;

	private String jieqiid;
	private String name;
	private String pic;
	private String time;

	public String getJieqiid() {
		return jieqiid;
	}

	public String getName() {
		return name;
	}

	public String getPic() {
		return pic;
	}

	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
}

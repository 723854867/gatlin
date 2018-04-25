package org.gatlin.sdk.ucpaas.response;

import com.google.gson.annotations.SerializedName;

public class SendSmsResponse extends UcpassResponse {

	private static final long serialVersionUID = -3830412116752238263L;

	private int count;
	private String uid;
	private String smsid;
	private String mobile;
	@SerializedName("create_date")
	private String createDate;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSmsid() {
		return smsid;
	}

	public void setSmsid(String smsid) {
		this.smsid = smsid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}

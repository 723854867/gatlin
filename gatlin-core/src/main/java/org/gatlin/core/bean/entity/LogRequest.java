package org.gatlin.core.bean.entity;

import org.gatlin.util.bean.Identifiable;

/**
 * 请求日志
 * 
 * @author lynn
 */
public class LogRequest implements Identifiable<String> {

	private static final long serialVersionUID = -1624110602560363089L;

	// 请求编号：唯一
	private String _id;
	// 请求时间：yyyy-MM-dd HH:mm:ss.SSS
	private String ctime;
	// 响应时间：yyyy-MM-dd HH:mm:ss.SSS
	private String rtime;
	// 请求ip
	private String ip;
	// 请求方法类型：GET、POST等
	private String type;
	// 请求接口描述
	private String desc;
	// 请求路劲
	private String path;
	// url查询参数
	private String query;
	// 请求类方法：类全名.方法名
	private String method;
	// 请求参数(包括了 body 和 表单等参数，只包括了可序列化且不是io流的参数)
	private String param;
	// 响应结果
	private String response;
	// 是否成功
	private boolean success;
	// 创建时间10位unix戳(用于排序)
	private int created;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getRtime() {
		return rtime;
	}

	public void setRtime(String rtime) {
		this.rtime = rtime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParam() {
		return param;
	}
	
	public void setParam(String param) {
		this.param = param;
	}
	
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}

	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public String key() {
		return this._id;
	}
}

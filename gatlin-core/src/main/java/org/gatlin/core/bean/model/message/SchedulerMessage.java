package org.gatlin.core.bean.model.message;

import java.io.Serializable;

/**
 * 定时任务消息
 * 
 * @author lynn
 */
public class SchedulerMessage implements Message {

	private static final long serialVersionUID = 2948516689768113324L;
	
	// 延迟 dely 毫秒再发送消息
	private Long delay;
	// cron 表达式
	private String cron;
	// 间隔
	private Long period;
	// 执行几次
	private Integer repeat;
	// 消息附件
	private Serializable attach;

	public Long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public Long getPeriod() {
		return period;
	}

	public Integer getRepeat() {
		return repeat;
	}
	
	/**
	 * 每隔 period 毫秒发送一次，总共发送 count 次
	 */
	public void repeat(int count, long period) {
		this.repeat = count;
		this.period = period;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}
	
	public Serializable getAttach() {
		return attach;
	}
	
	public void setAttach(Serializable attach) {
		this.attach = attach;
	}
}

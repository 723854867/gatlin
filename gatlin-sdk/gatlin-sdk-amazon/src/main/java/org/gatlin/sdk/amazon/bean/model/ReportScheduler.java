package org.gatlin.sdk.amazon.bean.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.gatlin.sdk.amazon.bean.enums.ReportType;
import org.gatlin.sdk.amazon.bean.enums.Schedule;

public class ReportScheduler implements Serializable {

	private static final long serialVersionUID = -113042605165072178L;

	private ReportType type;
	private Schedule schedule;
	private String scheduledDate;

	public ReportType getType() {
		return type;
	}

	@XmlElement(name = "ReportType")
	public void setType(ReportType type) {
		this.type = type;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	@XmlElement(name = "Schedule")
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}
	
	@XmlElement(name = "ScheduledDate")
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
}

package org.gatlin.sdk.amazon.response.report;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.gatlin.sdk.amazon.bean.model.ReportScheduler;
import org.gatlin.sdk.amazon.response.AmazonResponse;
import org.gatlin.sdk.amazon.response.ResponseMetaData;

@XmlRootElement(name = "ManageReportScheduleResponse")
public class ManageReportScheduleResponse extends AmazonResponse {

	private static final long serialVersionUID = -5426237810238962035L;

	private Result result;
	private ResponseMetaData metaData;

	public Result getResult() {
		return result;
	}

	@XmlElement(name = "ManageReportScheduleResult")
	public void setResult(Result result) {
		this.result = result;
	}

	public ResponseMetaData getMetaData() {
		return metaData;
	}

	@XmlElement(name = "ResponseMetadata")
	public void setMetaData(ResponseMetaData metaData) {
		this.metaData = metaData;
	}

	public static class Result implements Serializable {

		private static final long serialVersionUID = -1763512812639943197L;

		private int count;
		private ReportScheduler scheduler;

		public int getCount() {
			return count;
		}

		@XmlElement(name = "Count")
		public void setCount(int count) {
			this.count = count;
		}

		public ReportScheduler getScheduler() {
			return scheduler;
		}

		@XmlElement(name = "ReportScheduler")
		public void setScheduler(ReportScheduler scheduler) {
			this.scheduler = scheduler;
		}
	}
}

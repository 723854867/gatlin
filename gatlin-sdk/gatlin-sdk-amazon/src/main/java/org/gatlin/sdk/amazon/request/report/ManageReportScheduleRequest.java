package org.gatlin.sdk.amazon.request.report;

import org.gatlin.sdk.amazon.bean.enums.ReportType;
import org.gatlin.sdk.amazon.bean.enums.Schedule;
import org.gatlin.sdk.amazon.request.AmazonRequest;
import org.gatlin.sdk.amazon.response.report.ManageReportScheduleResponse;

/**
 * 操作的最大请求限额为 10 个，恢复速率为每 45 秒 1 个请求
 * <pre>
 * 操作创建、更新或删除特定报告类型的报告请求计划。目前，只能对订单和亚马逊商品广告报告进行计划。通过组合使用 ReportType 和 Schedule 的值，亚马逊 MWS 可确定您希望执行何种操作。如果不存在 
 * ReportType 和 Schedule 的组合值，则将创建新的报告请求计划。如果已计划 ReportType，但 Schedule 值不同，则将对报告请求计划进行更新，以便使用新的 Schedule 值。如果您传入 
 * ReportType，并在请求中将 Schedule 的值设置为 _NEVER_，则该 ReportType 的报告请求计划将被删除。
 * </pre>
 * 
 * @author lynn
 */
public class ManageReportScheduleRequest extends AmazonRequest<ManageReportScheduleResponse, ManageReportScheduleRequest> {

	public ManageReportScheduleRequest() {
		super("ManageReportSchedule", "2009-01-01", "");
	}
	
	public ManageReportScheduleRequest schedule(Schedule schedule) { 
		return _addParam("Schedule", schedule.name());
	}
	
	public ManageReportScheduleRequest reportType(ReportType reportType) { 
		return _addParam("ReportType", reportType.name());
	}
	
	// 下一个报告请求计划提交的日期(可选-默认值现在)。该值只能在365天之内
	public ManageReportScheduleRequest scheduleDate(String scheduleDate) { 
		return _addParam("ScheduleDate", scheduleDate);
	}
}

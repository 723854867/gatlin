package org.gatlin.sdk.amazon;

import org.gatlin.core.Bootstrap;
import org.gatlin.sdk.amazon.bean.enums.ReportType;
import org.gatlin.sdk.amazon.bean.enums.Schedule;
import org.gatlin.sdk.amazon.bean.model.Address;
import org.gatlin.sdk.amazon.bean.model.InboundShipmentPlanRequestItem;
import org.gatlin.sdk.amazon.request.CreateInboundShipmentPlanRequest;
import org.gatlin.sdk.amazon.request.GetServiceStatusRequest;
import org.gatlin.sdk.amazon.request.report.ManageReportScheduleRequest;
import org.gatlin.sdk.amazon.response.CreateInboundShipmentPlanResponse;
import org.gatlin.sdk.amazon.response.GetServiceStatusResponse;
import org.gatlin.sdk.amazon.response.report.ManageReportScheduleResponse;
import org.gatlin.util.serial.SerializeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Bootstrap.class})
public class AmazonTest {
	
	@Test
	public void test() {}
	
	@Test
	public void testCreateInboundShipmentPlan() {
		CreateInboundShipmentPlanRequest plan = new CreateInboundShipmentPlanRequest();
		Address address = new Address();
		address.name("test");
		address.addressLine1("dwdwdw");
		address.city("杭州市");
		address.countryCode("CN");
		plan.shipFromAddress(address);
		InboundShipmentPlanRequestItem item = new InboundShipmentPlanRequestItem();
		item.sellerSKU("100");
		item.Quantity(20);
		plan.inboundShipmentPlanRequestItems(item);
		CreateInboundShipmentPlanResponse response = plan.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testGetServceStatus() {
		GetServiceStatusRequest req = new GetServiceStatusRequest();
		GetServiceStatusResponse response = req.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testManageReportSchedule() { 
		ManageReportScheduleRequest request = new ManageReportScheduleRequest();
		request.reportType(ReportType._GET_FLAT_FILE_ORDERS_DATA_);
		request.schedule(Schedule._12_HOURS_);
		ManageReportScheduleResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}

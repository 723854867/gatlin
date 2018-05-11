package org.gatlin.skd.chuanglan;

import java.util.ArrayList;
import java.util.List;

import org.gatlin.core.Bootstrap;
import org.gatlin.skd.chuanglan.bean.VarMsg;
import org.gatlin.skd.chuanglan.request.BalanceRequest;
import org.gatlin.skd.chuanglan.request.SmsRequest;
import org.gatlin.skd.chuanglan.request.VarSmsRequest;
import org.gatlin.skd.chuanglan.response.BalanceResponse;
import org.gatlin.skd.chuanglan.response.SmsResponse;
import org.gatlin.skd.chuanglan.response.VarSmsResponse;
import org.gatlin.util.serial.SerializeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Bootstrap.class})
public class ChuangLanTest {
	
	@Test
	public void test() {}
	
	@Test
	public void testBalance() {
		BalanceRequest request = new BalanceRequest();
		BalanceResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testSms() {
		SmsRequest.Builder builder = new SmsRequest.Builder();
		builder.msg("您好");
		builder.phone("15888837752");
		SmsRequest request = builder.build();
		SmsResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testVarSms() {
		VarSmsRequest.Builder builder = new VarSmsRequest.Builder();
		builder.msg("您好{$var}");
		
		List<VarMsg> list = new ArrayList<VarMsg>();
		VarMsg msg = new VarMsg("15888837752");
		msg.append("张辛林");
		list.add(msg);
		msg = new VarMsg("13858192747");
		msg.append("樊水东");
		list.add(msg);
		builder.params(list);
		VarSmsRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.getBody()));
		VarSmsResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}

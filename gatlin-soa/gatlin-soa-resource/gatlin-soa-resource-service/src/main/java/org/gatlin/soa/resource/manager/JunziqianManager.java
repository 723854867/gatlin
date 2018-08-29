package org.gatlin.soa.resource.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;

import org.ebaoquan.rop.thirdparty.com.alibaba.fastjson.JSONArray;
import org.ebaoquan.rop.thirdparty.com.alibaba.fastjson.JSONObject;
import org.ebaoquan.rop.thirdparty.com.google.common.collect.Sets;
import org.gatlin.soa.resource.bean.JzqConfig;
import org.gatlin.soa.resource.bean.param.ContractParam;
import org.springframework.stereotype.Component;

import com.junziqian.api.JunziqianClient;
import com.junziqian.api.bean.Signatory;
import com.junziqian.api.common.DealType;
import com.junziqian.api.common.IdentityType;
import com.junziqian.api.common.SignLevel;
import com.junziqian.api.request.ApplySignHtmlRequest;
import com.junziqian.api.request.DetailAnonyLinkRequest;
import com.junziqian.api.request.FileLinkRequest;
import com.junziqian.api.response.ApplySignResponse;
import com.junziqian.api.response.SignLinkResponse;
import com.junziqian.api.util.LogUtils;

@Component
public class JunziqianManager {

	public JunziqianClient client = new JunziqianClient(JzqConfig.ServicesUrl(), JzqConfig.appKey(),
			JzqConfig.appSecret());

	public String readTemplate(ContractParam param) {
		String result = "";
		try {
			// 读取html的流
			//File srcFile = new File("D:\\ybq\\contract\\touzi.html");
			 File srcFile = new File(param.getResourceUrl()+"touzi.html");
			InputStream inputStream = new FileInputStream(srcFile);
			// 流转换成字符串
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = inputStream.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			String html = out.toString();
			String rate = param.getRate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toString();
			String money = param.getAmount().setScale(2, BigDecimal.ROUND_DOWN).toString();
			html = html.replace("contract", param.getOrderNo()).replace("cDate", param.getContractDate())
					.replace("chineseValue", param.getAmountStr())
					.replaceAll("investRealName", param.getInvestRealName())
					.replaceAll("investCard", param.getInvestCard())
					.replaceAll("borrowRealName", param.getBorrowRealName())
					.replaceAll("borrowCard", param.getBorrowCard()).replace("rate", rate)
					.replace("amount", money).replace("interestType", param.getInterestType())
					.replace("valueDate", param.getValueDate()).replace("expiryDate", param.getExpiryDate());

			inputStream.close();
			// 签署合同
			result = signContract(html,param);
		} catch (IOException e) {
		}
		return result;
	}
	
	public String signContract(String html,ContractParam param) {

		ApplySignHtmlRequest.Builder builder = new ApplySignHtmlRequest.Builder();
		// html文件必须设置meta.charset为utf-8|否则会出现乱码。表单域请使用input
		// type=text的，且注明name属性，宽高设置为0
		builder.withHtml("投资合同.pdf", html);
		// <input type="text" name="ebq"
		// style="width:0;height:0;border:0;margin:0;padding:0;">
		// 这个标签可以支持任何的css，包括相对定位，和绝对定位。只要用浏览器打开在哪，那固定章的位置在哪
		// 使用表单域方式给签字位置：
		// html的话一个表单域为，宽高设为0，name参数写入到signatory.chapteName：<input type="text"
		// name="ebq" style="width:0px;heigth:0px;">
		// 表单域这种不支持原来的pdf文件已有证书的情况

		// builder.withHtml("HTML测试.pdf",FileUtils.readFileToString(new
		// File("D:/tmp/test2.html"), "utf-8"));
		builder.withContractName("借款合同(浙江微鱼)"); // *合同名称
		Double money = param.getAmount().setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		builder.withContractAmount(money); // 合同金额
		HashSet<Signatory> signatories = Sets.newHashSet();
		/**
		 * 签约方1
		 */
		Signatory signatory = new Signatory();
		signatory.setFullName(param.getInvestRealName()); // 姓名
		signatory.setSignatoryIdentityType(IdentityType.IDCARD); // 证件类型
		signatory.setIdentityCard(param.getInvestCard()); // 证件号码
		signatory.setMobile(param.getInvestMobile());

		/*
		 * 设置签约方1的签章位置，签约位置平台自行设置 签字位置
		 * （以文件页左上角(0.0,0.0)为基准，按百分比进行设置）offsetX,offsetY(x,y为比例，值范设置为0-1之间)
		 * page为页码，page从0开始计数， 每页为一个数组，每页不能超过10个签字位置
		 */
		JSONArray chapteJsonArray = new JSONArray();

		JSONObject pageJson = new JSONObject();
		pageJson.put("page", 3);
		JSONArray chaptes = new JSONArray();
		pageJson.put("chaptes", chaptes);
		chapteJsonArray.add(pageJson);

		JSONObject chapte = new JSONObject();
		chapte.put("offsetX", 0.14);
		chapte.put("offsetY", 0.34);
		chaptes.add(chapte);

		signatory.withChapteJson(chapteJsonArray);
		signatory.setSignLevel(0); // 0：标准图形章 1：公章或手写
		signatories.add(signatory);

		/**
		 * 签约方2
		 */
		Signatory signatory1 = new Signatory();
		signatory1.setFullName(param.getBorrowRealName()); // 姓名
		signatory1.setSignatoryIdentityType(IdentityType.IDCARD); // 证件类型
		signatory1.setIdentityCard(param.getBorrowCard()); // 证件号码
		signatory1.setMobile(param.getBorrowMobile());

		/*
		 * 设置签约方1的签章位置，签约位置平台自行设置 签字位置
		 * （以文件页左上角(0.0,0.0)为基准，按百分比进行设置）offsetX,offsetY(x,y为比例，值范设置为0-1之间)
		 * page为页码，page从0开始计数， 每页为一个数组，每页不能超过10个签字位置
		 */
		JSONArray chapteJsonArray1 = new JSONArray();

		JSONObject pageJson1 = new JSONObject();
		pageJson1.put("page", 3);
		JSONArray chaptes1 = new JSONArray();
		pageJson1.put("chaptes", chaptes1);
		chapteJsonArray1.add(pageJson1);

		JSONObject chapte1 = new JSONObject();
		chapte1.put("offsetX", 0.14);
		chapte1.put("offsetY", 0.38);
		chaptes1.add(chapte1);

		signatory1.withChapteJson(chapteJsonArray1);
		signatory1.setSignLevel(0); // 0：标准图形章 1：公章或手写
		signatories.add(signatory1);

		/**
		 * 签约方2
		 */
		signatory = new Signatory();
		signatory.setFullName("浙江微鱼网络科技有限公司"); // 姓名
		signatory.setSignatoryIdentityType(IdentityType.BIZLIC); // 证件类型
		signatory.setIdentityCard("91330105MA2808NH5D");// 证件号码（营业执照号或统一社会信用代码）
	//	signatory.setEmail("demvrsot@www.bccto.me");// 企业账户注册邮箱
	//	signatory.setMobile("15205811215");// 企业代表手机

		/*
		 * 设置签约方2的签章位置，签约位置平台自行设置 签字位置
		 * （以文件页左上角(0.0,0.0)为基准，按百分比进行设置）offsetX,offsetY(x,y为比例，值范设置为0-1之间)
		 * page为页码，page从0开始计数， 每页为一个数组，每页不能超过10个签字位置
		 */
		JSONArray chapteJsonArray3 = new JSONArray();
		JSONObject pageJson3 = new JSONObject();
		pageJson3.put("page", 3);
		JSONArray chaptes3 = new JSONArray();
		pageJson3.put("chaptes", chaptes3);
		chapteJsonArray3.add(pageJson3);

		JSONObject chapte3 = new JSONObject();
		chapte3.put("offsetX", 0.3);
		chapte3.put("offsetY", 0.45);
		chaptes3.add(chapte3);
		
		signatory.withChapteJson(chapteJsonArray3);
		signatory.setSignLevel(1); // 0：标准图形章 1：公章或手写
		signatories.add(signatory);
		
		
		builder.withSignatories(signatories); // 添加签约人
		builder.withSignLevel(SignLevel.GENERAL.getCode()); // 签字类型,这里选择标准图形章
		builder.withServerCa(1);// 使用云证书，0 不使用，1 使用
		builder.withDealType(DealType.AUTH_SIGN);// 自动签
		ApplySignResponse response = client.applySignHtml(builder.build());
		LogUtils.logResponse(response);
		return response.getApplyNo();
	}

	/**
	 * 获取合同连接
	 * 
	 * @return
	 */
	public String contractDetail(String contractNo) {

		DetailAnonyLinkRequest request = new DetailAnonyLinkRequest();
		request.setApplyNo(contractNo);
		SignLinkResponse response = client.detailAnonyLink(request);
		LogUtils.logResponse(response);
		return response.getLink();
	}

	/**
	 * 获取下载合同连接
	 * 
	 * @return
	 */
	public String contractDownload(String contractNo) {

		FileLinkRequest request = new FileLinkRequest();
		request.setApplyNo(contractNo);
		SignLinkResponse response = client.fileLink(request);
		LogUtils.logResponse(response);
		return response.getLink();
	}

	// public void main(String[] args) throws IOException {
	// //readTemplate();
	// //System.out.println(selectContract());
	// System.out.println(contractDetail("APL1027813591144861696"));
	// }
}

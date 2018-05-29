package org.gatlin.sdk.sinapay.request.order;

import java.math.BigDecimal;
import java.util.List;

import org.gatlin.sdk.sinapay.bean.enums.BidType;
import org.gatlin.sdk.sinapay.bean.enums.RepayType;
import org.gatlin.sdk.sinapay.bean.model.BorrowerInfo;
import org.gatlin.sdk.sinapay.response.BidCreateResponse;
import org.gatlin.util.reflect.BeanUtil;

/**
 * 限制：
 * 1、标的金额必须等于借款人借款总额
 * 2、标的借款人不可以重复
 * 3、标的名称不可以重复
 * 
 * @author lynn
 */
public class BidCreateRequest extends OrderRequest<BidCreateResponse, BidCreateRequest> {
	
	public static class Builder extends OrderRequest.Builder<BidCreateResponse, BidCreateRequest, Builder> {

		private static final long serialVersionUID = 2928104137791436579L;

		public Builder() {
			super("create_bid_info");
		}
		
		// 商户网站标的号，不能重复
		public Builder outBidNo(String outBidNo) {
			this.params.put("out_bid_no", outBidNo);
			return this;
		}
		
		// 网站名称/平台名称
		public Builder webSiteName(String webSiteName) {
			this.params.put("web_site_name", webSiteName);
			return this;
		}
		
		// 标的名称
		public Builder bidName(String bidName) {
			this.params.put("bid_name", bidName);
			return this;
		}
		
		// 标的类型
		public Builder bidType(BidType bidType) {
			this.params.put("bid_type", bidType.name());
			return this;
		}
		
		// 年化收益率
		public Builder bidYearRate(BigDecimal bidYearRate) {
			this.params.put("bid_year_rate", bidYearRate.toString());
			return this;
		}
		
		// 发标金额
		public Builder bidAmount(BigDecimal bidAmount) {
			this.params.put("bid_amount", bidAmount.toString());
			return this;
		}
		
		// 借款期限单位天
		public Builder bidDuration(int bidDuration) {
			this.params.put("bid_duration", String.valueOf(bidDuration));
			return this;
		}
		
		// 还款方式
		public Builder repayType(RepayType repayType) {
			this.params.put("repay_type", repayType.name());
			return this;
		}
		
		// 协议类型:可空
		public Builder protocolType(String protocolType) {
			this.params.put("protocol_type", protocolType);
			return this;
		}
		
		// 标的产品类型:可空
		public Builder bidProductType(String bidProductType) {
			this.params.put("bid_product_type", bidProductType);
			return this;
		}
		
		// 推荐机构:可空
		public Builder recommendInst(String recommendInst) {
			this.params.put("recommend_inst", recommendInst);
			return this;
		}
		
		// 限定最低投标份数：可空
		public Builder limitMinBidCopys(int limitMinBidCopys) {
			this.params.put("limit_min_bid_copys", String.valueOf(limitMinBidCopys));
			return this;
		}
		
		// 限定每份投标金额：可空
		public Builder limitPerCopyAmount(BigDecimal limitPerCopyAmount) {
			this.params.put("limit_per_copy_amount", limitPerCopyAmount.toString());
			return this;
		}
		
		// 限定最少投保金额：可空
		public Builder limitMinBidAmount(BigDecimal limitMinBidAmount) {
			this.params.put("limit_min_bid_amount", limitMinBidAmount.toString());
			return this;
		}
		
		// 摘要：可空
		public Builder summary(String summary) {
			this.params.put("summary", summary);
			return this;
		}
		
		// 标的url：可空
		public Builder url(String url) {
			this.params.put("url", url);
			return this;
		}
		
		// 标的开始时间：yyyyMMddHHmmss
		public Builder beginDate(String beginDate) {
			this.params.put("begin_date", beginDate);
			return this;
		}
		
		// 还款期限：yyyyMMddHHmmss
		public Builder term(String term) {
			this.params.put("term", term);
			return this;
		}
		
		// 担保方式
		public Builder guaranteeMethod(String guaranteeMethod) {
			this.params.put("guarantee_method", guaranteeMethod);
			return this;
		}
		
		// 借款人信息列表:借款人信息列表，参考借款人信息,参数之间用“~”分割，条目之间用“$”分割。标的借款人信息列表不能超过10条。恒丰商户目前只支持1条借款人信息
		public Builder borrowerInfoList(List<BorrowerInfo> borrowers) {
			this.params.put("borrower_info_list", BeanUtil.parse(borrowers, "~", "$"));
			return this;
		}
	}
}

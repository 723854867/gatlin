package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.CardAttribute;
import org.gatlin.sdk.sinapay.bean.enums.CardType;
import org.gatlin.sdk.sinapay.bean.enums.CertType;
import org.gatlin.sdk.sinapay.bean.enums.DigestType;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.response.SinapayResponse;

/**
 * 1、提交审核时，需要保证资质文件完整，一旦提交审核成功，进入审核流程，则企业用户信息不可做变更，除非审核被驳回，需要商户重新提交企业用户信息，且二次提交信息时，做增量更新操作
 * 2、请保证上传至SFTP服务器中的企业资质文件已压缩成zip文件，具体企业资质为:
 * 		企业营业执照：yyzz：首次非空
 * 		组织机构代码证：zzjgz：首次非空
 * 		税务登记证：swdjz：首次非空
 * 		单位银行结算账户开户许可证：jsxkz：首次非空
 * 		机构信用代码证：jgxyz：可空
 * 		ICP备案许可：icp：可空
 * 		行业许可证：hyxkz：可空
 * 		企业法人证件正面：frzjz：首次非空
 * 		企业法人证件反面：frzjf：首次非空
 * 且各文件名称以文件缩写为名称加后缀文件格式，如：企业营业执照为: yyzz.jpg
 * @author lynn
 */
public class CompanyApplyRequest extends MemberRequest<SinapayResponse, CompanyApplyRequest> {

	public static class Builder extends MemberRequest.Builder<SinapayResponse, CompanyApplyRequest, Builder> {

		private static final long serialVersionUID = 3623335175321450037L;

		public Builder() {
			super("audit_member_infos");
			certType(CertType.IC);
		}
		
		public Builder auditOrderNo(String auditOrderNo) { 
			this.params.put("audit_order_no", auditOrderNo);
			return this;
		}
		
		public Builder memberType(MemberType memberType) { 
			this.params.put("member_type", String.valueOf(memberType.mark()));
			return this;
		}
		
		// 首次非空
		public Builder companyName(String companyName) { 
			this.params.put("company_name", companyName);
			return this;
		}
		
		// 企业网址
		public Builder website(String website) { 
			this.params.put("website", website);
			return this;
		}
		
		// 首次非空
		public Builder address(String address) { 
			this.params.put("address", address);
			return this;
		}
		
		// 首次非空：密文
		public Builder licenseNo(String licenseNo) { 
			this.params.put("license_no", licenseNo);
			return this;
		}
		
		// 首次非空
		public Builder licenseAddress(String licenseAddress) { 
			this.params.put("license_address", licenseAddress);
			return this;
		}
		
		// 首次非空(YYYYMMDD)
		public Builder licenseExpireDate(String licenseExpireDate) { 
			this.params.put("license_expire_date", licenseExpireDate);
			return this;
		}
		
		// 首次非空：营业范围
		public Builder businessScope(String businessScope) { 
			this.params.put("business_scope", businessScope);
			return this;
		}
		
		// 首次非空：密文
		public Builder telephone(String telephone) { 
			this.params.put("telephone", telephone);
			return this;
		}
		
		// 首次非空：密文
		public Builder email(String email) { 
			this.params.put("email", email);
			return this;
		}
		
		// 首次非空：密文(组织机构代码)
		public Builder organizationNo(String organizationNo) { 
			this.params.put("organization_no", organizationNo);
			return this;
		}
		
		// 首次非空
		public Builder summary(String summary) { 
			this.params.put("summary", summary);
			return this;
		}
		
		// 首次非空：密文
		public Builder legalPerson(String legalPerson) { 
			this.params.put("legal_person", legalPerson);
			return this;
		}
		
		// 首次非空：密文(法人证件号码)
		public Builder certNo(String certNo) { 
			this.params.put("cert_no", certNo);
			return this;
		}
		
		// 首次非空
		public Builder certType(CertType certType) { 
			this.params.put("cert_type", certType.name());
			return this;
		}
		
		// 首次非空:法人证件生效时间
		public Builder certEffectDate(String certEffectDate) { 
			this.params.put("cert_effect_date", certEffectDate);
			return this;
		}
		
		// 首次非空:法人证件失效时间
		public Builder certInvalidDate(String certInvalidDate) { 
			this.params.put("cert_invalid_date", certInvalidDate);
			return this;
		}
		
		// 首次非空：密文(法人手机号)
		public Builder legalPersonPhone(String legalPersonPhone) { 
			this.params.put("legal_person_phone", legalPersonPhone);
			return this;
		}
		
		// 首次非空
		public Builder bankCode(String bankCode) { 
			this.params.put("bank_code", bankCode);
			return this;
		}
		
		// 首次非空：密文(银行卡号)
		public Builder bankAccountNo(String bankAccountNo) { 
			this.params.put("bank_account_no", bankAccountNo);
			return this;
		}
		
		// 首次非空
		public Builder cardType(CardType cardType) { 
			this.params.put("card_type", cardType.name());
			return this;
		}
		
		// 首次非空
		public Builder cardAttribute(CardAttribute cardAttribute) { 
			this.params.put("card_attribute", cardAttribute.name());
			return this;
		}
		
		// 首次非空
		public Builder province(String province) { 
			this.params.put("province", province);
			return this;
		}
		
		// 首次非空
		public Builder city(String city) { 
			this.params.put("city", city);
			return this;
		}
		
		// 首次非空：支行名称
		public Builder bankBranch(String bankBranch) { 
			this.params.put("bank_branch", bankBranch);
			return this;
		}
		
		// 首次非空：文件格式*.zip且文件名只能由数字或字母组成
		public Builder fileName(String fileName) { 
			this.params.put("fileName", fileName);
			return this;
		}
		
		// 首次非空
		public Builder digest(String digest) { 
			this.params.put("digest", digest);
			return this;
		}
		
		// 首次非空
		public Builder digestType(DigestType digestType) { 
			this.params.put("digestType", digestType.name());
			return this;
		}
		
		// 非空
		public Builder clientIp(String clientIp) { 
			this.params.put("client_ip", clientIp);
			return this;
		}
	}
}

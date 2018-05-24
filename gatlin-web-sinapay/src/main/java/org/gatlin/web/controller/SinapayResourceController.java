package org.gatlin.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.gatlin.core.CoreCode;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.Assert;
import org.gatlin.sdk.sinapay.SignUtil;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.bean.param.CompanyApplyParam;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.util.IDWorker;
import org.gatlin.util.io.SFTPConnection;
import org.gatlin.util.io.ZipWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("sinapay/resource")
public class SinapayResourceController {
	
	private static final Set<String> COMPANY_FILE_NAMES = new HashSet<String>() {
		private static final long serialVersionUID = 4938719247526650630L;
		{
			add("yyzz.jpg");
			add("zzjgz.jpg");
			add("swdjz.jpg");
			add("jsxkz.jpg");
			add("jgxyz.jpg");
			add("icp.jpg");
			add("hyxkz.jpg");
			add("frzjz.jpg");
			add("frzjf.jpg");
		}
	};
	
	@Resource
	private CompanyService companyService;
	@Resource
	private SinapayMemberService sinapayMemberService;

	// 企业会员资质申请
	@Valid
	@ResponseBody
	@RequestMapping("company/apply")
	public Object companyApply(@Valid CompanyApplyParam param,@RequestParam(required = false, name = "files") @NotNull @Size(min = 1) MultipartFile[] files) throws Exception {
		Company company = companyService.company(param.getId());
		Assert.notNull(UserCode.COMPANY_NOT_EIXST, company);
		ZipWriter writer = new ZipWriter();
		String prefix = IDWorker.INSTANCE.nextSid();
		Assert.isTrue(CoreCode.PARAM_ERR, files.length >= 6);
		for (MultipartFile file : files){
			Assert.isTrue(CoreCode.PARAM_ERR, COMPANY_FILE_NAMES.contains(file.getOriginalFilename()));
			writer.wrap(prefix + "/" + file.getOriginalFilename(), file.getInputStream());
		}
		writer.close();
		ByteArrayOutputStream out = (ByteArrayOutputStream) writer.getOut();
		byte[] buffer = out.toByteArray();
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);
		SFTPConnection connection = new SFTPConnection();
		try {
			connection.setHost(GatlinConfigration.get("sinapay.sftp.host"));
			connection.setPort(GatlinConfigration.get("sinapay.sftp.port", Integer.class));
			connection.setUsername(GatlinConfigration.get("sinapay.sftp.username"));
			connection.setPriKeyFile(SinapayResourceController.class.getResource("/conf/sinapay_sftp.key").getFile());
			connection.init();
			connection.upload(GatlinConfigration.get("sinapay.sftp.company.directory"), prefix + ".zip", in);
		} finally {
			connection.close();
		}
		param.setFilename(prefix + ".zip");
		param.setDigest(SignUtil.fileMD5(new ByteArrayInputStream(buffer)));
		sinapayMemberService.companyApply(param, company);
		return Response.ok();
	}
	
	public static void main(String[] args) {
		String value = "yyzz.jpgzzjgz.jpgswdjz.jpgjsxkz.jpgfrzjz.jpgfrzjf.jpg";
		String cvalue = "yyzz.jpgzzjgz.jpgswdjz.jpgjsxkz.jpgfrzjz.jpgfrzjf.jpg";
		System.out.println(cvalue.indexOf(value));
	}
}

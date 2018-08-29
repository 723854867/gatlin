package org.gatlin.soa.resource.service;

import javax.annotation.Resource;

import org.gatlin.soa.resource.api.JunziqianService;
import org.gatlin.soa.resource.bean.param.ContractParam;
import org.gatlin.soa.resource.manager.JunziqianManager;
import org.springframework.stereotype.Service;

@Service("junziqianServiceImpl")
public class JunziqianServiceImpl implements JunziqianService {

	@Resource
	private JunziqianManager junziqianManager;

	/**
	 * 读取配置文件并签署合同
	 */
	public String readTemplate(ContractParam param) {
		return junziqianManager.readTemplate(param);
	}

	/**
	 * 获取合同连接
	 * 
	 * @return
	 */
	public String contractDetail(String contractNo) {
		return junziqianManager.contractDetail(contractNo);
	}

	/**
	 * 获取下载合同连接
	 * 
	 * @return
	 */
	public String contractDownload(String contractNo) {
		return junziqianManager.contractDownload(contractNo);
	}
}

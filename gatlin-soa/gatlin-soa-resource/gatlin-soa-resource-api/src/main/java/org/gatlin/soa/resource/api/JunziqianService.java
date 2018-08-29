package org.gatlin.soa.resource.api;

import org.gatlin.soa.resource.bean.param.ContractParam;

public interface JunziqianService {
	/**
	 * 读取配置文件并签署合同
	 */
	String readTemplate(ContractParam param);

	/**
	 * 获取合同连接
	 * 
	 * @return
	 */
	String contractDetail(String contractNo);

	/**
	 * 获取下载合同连接
	 * 
	 * @return
	 */
	String contractDownload(String contractNo);
}

package org.gatlin.soa.resource.api;

import java.util.List;

import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.PubResource;

public interface ResourceService {
	
	CfgResource uploadVerify(int type, int resourceId, long size, long major);
	
	CfgResource uploadVerify(long owner, int type, int resourceId, long size, long major);
	
	/**
	 * 资源上传
	 * 
	 * @param url 资源地址
	 * @param resource 资源配置
	 * @param type 上传类型
	 * @param priority 资源排序(显示时使用)
	 */
	PubResource upload(String url, String path, CfgResource resource, int type, int priority, long major);

	/**
	 * 资源上传
	 * 
	 * @param owner 资源归属者编号
	 * @param url 资源地址
	 * @param resource 资源配置
	 * @param type 上传类型
	 * @param priority 资源排序(显示时使用)
	 */
	PubResource upload(long owner, String url, String path, CfgResource resource, int type, int priority, long major);
	
	/**
	 * 删除资源
	 * 
	 * @param resourceId
	 * @return
	 */
	List<PubResource> unload(long resourceId);
}

package org.gatlin.sdk.jpush.bean.model;

/**
 * 多个 audience 可以并存，多个的关系是 AND
 * 
 * @author lynn
 */
public enum AudienceType {

	all,
	// 数组：多个标签之间是 OR 的关系，即取并集。一次推送最多 20 个，每一个 tag 的长度限制为 40 字节
	tag,
	// 数字：多个别名之间是OR关系。一次推送最多推送1000个，一个设备只能绑定一个别名，但是多个设备可以绑定同一个别名
	alias,
	// AB测试ID，一次只能推送一个
	abtest,
	// 用户分群ID，一次只能推送一个
	segment,
	// 数组：多个标签之间是 AND 关系，即取交集，其他规则同tag
	tag_and,
	// 数组：多个标签之间，先取多标签的并集，再去对该结果的补集
	tag_not,
	// 数组：多个注册ID之间是OR关系。一次推送最多1000个
	registration_id;
}

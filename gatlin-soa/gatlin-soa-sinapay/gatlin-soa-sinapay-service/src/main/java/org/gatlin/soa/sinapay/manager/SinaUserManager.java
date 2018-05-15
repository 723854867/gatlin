package org.gatlin.soa.sinapay.manager;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.request.member.ActivateRequest;
import org.gatlin.sdk.sinapay.request.member.RealnameRequest;
import org.gatlin.sdk.sinapay.response.SinapayResponse;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.mybatis.EntityGenerator;
import org.gatlin.soa.sinapay.mybatis.dao.SinaUserDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SinaUserManager {

	private static final Logger logger = LoggerFactory.getLogger(SinaUserManager.class);
	
	@Resource
	private SinaUserDao sinaUserDao;
	
	@Transactional
	public String activate(String tid, MemberType type, String ip) {
		SinaUser user = null;
		try {
			user = EntityGenerator.newSinaUser(tid, type);
			sinaUserDao.insert(user);
		} catch (DuplicateKeyException e) {
			throw new CodeException(SinaCode.MEMBER_EXIST);
		}
		ActivateRequest.Builder builder = new ActivateRequest.Builder();
		builder.clientIp(ip);
		builder.identityId(user.getSinaId());
		ActivateRequest request = builder.build();
		logger.info("新浪会员激活请求：{}", SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		logger.info("新浪会员激活响应：{}", SerializeUtil.GSON.toJson(response));
		return user.getSinaId();
	}
	
	@Transactional
	public void realname(String tid, String realname, String identity, String ip) {
		Query query = new Query().eq("type", MemberType.PERSONAL.mark()).eq("tid", tid).forUpdate();
		SinaUser user = user(query);
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		Assert.isTrue(SinaCode.MEMBER_ALREADY_REALNAME, !user.isRealname());
		user.setRealname(true);
		user.setUpdated(DateUtil.current());
		sinaUserDao.update(user);
		RealnameRequest.Builder builder = new RealnameRequest.Builder();
		builder.clientIp(ip);
		builder.certNo(identity);
		builder.realname(realname);
		builder.identityId(user.getSinaId());
		RealnameRequest request = builder.build();
		logger.info("新浪实名认证请求：{}", SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		logger.info("新浪实名认证响应：{}", SerializeUtil.GSON.toJson(response));
	}
	
	public SinaUser user(Query query) {
		return sinaUserDao.queryUnique(query);
	}
}

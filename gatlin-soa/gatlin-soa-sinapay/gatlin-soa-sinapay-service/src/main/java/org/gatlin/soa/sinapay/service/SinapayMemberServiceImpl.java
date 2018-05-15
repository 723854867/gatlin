package org.gatlin.soa.sinapay.service;

import javax.annotation.Resource;

import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.manager.SinaUserManager;
import org.springframework.stereotype.Service;

@Service("sinapayMemberService")
public class SinapayMemberServiceImpl implements SinapayMemberService {
	
	@Resource
	private SinaUserManager sinaUserManager;

	@Override
	public String activate(String tid, MemberType type, String ip) {
		return sinaUserManager.activate(tid, type, ip);
	}
	
	@Override
	public void realname(String tid, String realname, String identity, String ip) {
		sinaUserManager.realname(tid, realname, identity, ip);
	}
}

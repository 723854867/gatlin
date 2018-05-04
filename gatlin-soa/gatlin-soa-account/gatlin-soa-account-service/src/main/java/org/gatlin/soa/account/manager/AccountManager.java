package org.gatlin.soa.account.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gatlin.soa.account.bean.EntityGenerator;
import org.gatlin.soa.account.bean.entity.UserAccount;
import org.gatlin.soa.account.bean.enums.UserAccountType;
import org.gatlin.soa.account.mybatis.dao.UserAccountDao;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.stereotype.Component;

@Component
public class AccountManager {

	@Resource
	private UserAccountDao userAccountDao;
	
	public void init(Long uid, int mod) {
		List<UserAccount> accounts = new ArrayList<UserAccount>();
		for (UserAccountType type : UserAccountType.values()) {
			if ((type.mark() & mod) == type.mark()) 
				accounts.add(EntityGenerator.newUserAccount(uid, type.mark()));
		}
		if (!CollectionUtil.isEmpty(accounts))
			userAccountDao.batchInsert(accounts);
	}
}

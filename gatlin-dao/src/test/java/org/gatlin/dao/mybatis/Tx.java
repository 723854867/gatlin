package org.gatlin.dao.mybatis;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.dao.bean.entity.User;
import org.gatlin.dao.mybatis.dao.UserDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Tx {
	
	@Resource
	private UserDao userDao;

	@Transactional(rollbackFor = CodeException.class)
	public void insert(User user) {
		userDao.insert(user);
		throw new CodeException();
	}
}

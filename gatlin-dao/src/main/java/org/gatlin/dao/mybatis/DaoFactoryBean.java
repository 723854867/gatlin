package org.gatlin.dao.mybatis;

import org.mybatis.spring.mapper.MapperFactoryBean;

public class DaoFactoryBean<T> extends MapperFactoryBean<T> {
	
	private DaoAccessor daoAccessor;

	public DaoFactoryBean() {
	}

	public DaoFactoryBean(Class<T> mapperInterface) {
		super(mapperInterface);
	}
	
	@Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        //通用Dao重新绑定动态sql
        if (daoAccessor.isRegister(getObjectType()))
        	daoAccessor.daoReconfig(getSqlSession().getConfiguration(), getObjectType());
    }
	
	public void setDaoAccessor(DaoAccessor daoAccessor) {
		this.daoAccessor = daoAccessor;
	}
}

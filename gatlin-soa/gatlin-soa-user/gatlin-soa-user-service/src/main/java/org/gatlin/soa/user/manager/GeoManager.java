package org.gatlin.soa.user.manager;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.user.Consts;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.UserAddress;
import org.gatlin.soa.user.bean.param.AddressAddparam;
import org.gatlin.soa.user.bean.param.AddressModifyParam;
import org.gatlin.soa.user.mybatis.EntityGenerator;
import org.gatlin.soa.user.mybatis.dao.UserAddressDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GeoManager {

	@Resource
	private ConfigService configService;
	@Resource
	private UserAddressDao userAddressDao;
	
	@Transactional
	public long addressAdd(AddressAddparam param, Geo geo) {
		int count = _addressCount(param.getUser().getId(), param.isUsed());
		int maximum = configService.config(Consts.GlobalKeys.ADDRESS_MAXIMUM);
		Assert.isTrue(UserCode.USER_ADDRESS_COUNT_LIMIT, count < maximum);
		UserAddress address = EntityGenerator.newUserAddress(param, geo);
		userAddressDao.insert(address);
		return address.getId();
	}
	
	@Transactional
	public void addressModify(AddressModifyParam param, Geo geo) {
		UserAddress address = userAddressDao.getByKey(param.getId());
		Assert.notNull(UserCode.USER_ADDRESS_NOT_EXIST, address);
		Assert.isTrue(CoreCode.FORBID, address.getUid() == param.getUser().getId() && !address.isDeleted());
		if (null != param.getUsed() && param.getUsed()) 
			_addressCount(param.getUser().getId(), true);
		if (null != geo) {
			address.setCity(geo.getCity());
			address.setCounty(geo.getCounty());
			address.setProvince(geo.getProvince());
		}
		if (null != param.getUsed())
			address.setUsed(param.getUsed());
		if (StringUtil.hasText(param.getMemo()))
			address.setMemo(param.getMemo());
		if (StringUtil.hasText(param.getDetail()))
			address.setDetail(param.getDetail());
		if (StringUtil.hasText(param.getContacts()))
			address.setContacts(param.getContacts());
		if (StringUtil.hasText(param.getContactsMobile()))
			address.setContactsMobile(param.getContactsMobile());
		address.setUpdated(DateUtil.current());
		userAddressDao.update(address);
	}
	
	public void addressDelete(SoaLidParam param) {
		UserAddress address = userAddressDao.getByKey(param.getId());
		Assert.notNull(UserCode.USER_ADDRESS_NOT_EXIST, address);
		Assert.isTrue(CoreCode.FORBID, address.getUid() == param.getUser().getId());
		address.setUsed(false);
		address.setDeleted(true);
		userAddressDao.update(address);
	}
	
	private int _addressCount(long uid, boolean used) {
		Query query = new Query().eq("uid", uid).eq("deleted", 0).forUpdate();
		List<UserAddress> list = userAddressDao.queryList(query);
		if (used) {
			for (UserAddress address : list) {
				if (address.isUsed()) {
					address.setUsed(false);
					address.setUpdated(DateUtil.current());
					userAddressDao.update(address);
				}
			}
		}
		return list.size();
	}
	
	public UserAddress address(long id) {
		return userAddressDao.getByKey(id);
	}
	
	public List<UserAddress> addresses(Query query) {
		return userAddressDao.queryList(query);
	}
}

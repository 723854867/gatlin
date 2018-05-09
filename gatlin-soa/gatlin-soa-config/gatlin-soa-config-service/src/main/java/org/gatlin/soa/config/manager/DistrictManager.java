package org.gatlin.soa.config.manager;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.config.EntityGenerator;
import org.gatlin.soa.config.bean.ConfigCode;
import org.gatlin.soa.config.bean.entity.CfgDistrict;
import org.gatlin.soa.config.bean.enums.DistrictLevel;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.soa.config.bean.param.DistrictModifyParam;
import org.gatlin.soa.config.mybatis.dao.CfgDistrictDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.util.lang.StringUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DistrictManager {

	@Resource
	private CfgDistrictDao cfgDistrictDao;
	
	public void districtAdd(DistrictAddParam param) {
		if (StringUtil.hasText(param.getParent())) {
			CfgDistrict parent = cfgDistrictDao.getByKey(param.getParent());
			Assert.notNull(ConfigCode.DISTRICT_NOT_EXIST, parent);
			Assert.isTrue(CoreCode.FORBID, parent.getLevel() != DistrictLevel.COUNTY.mark());
		}
		CfgDistrict instance = EntityGenerator.newCfgDistrict(param);
		cfgDistrictDao.insert(instance);
	}
	
	@Transactional
	public void districtModify(DistrictModifyParam param) {
		CfgDistrict district = cfgDistrictDao.getByKey(param.getId());
		Assert.notNull(ConfigCode.DISTRICT_NOT_EXIST, district);
		if (StringUtil.hasText(param.getName()))
			district.setName(param.getName());
		if (StringUtil.hasText(param.getAbname()))
			district.setAbname(param.getAbname());
		if (null != param.getLevel())
			district.setLevel(param.getLevel().mark());
		if (district.getLevel() == DistrictLevel.PROVINCE.mark())
			Assert.hasText(CoreCode.PARAM_ERR, param.getAbname());
		else
			Assert.hasNoText(CoreCode.PARAM_ERR, param.getAbname());
		if (StringUtil.hasText(param.getParent()) && !district.getParent().equals(param.getParent())) {
			CfgDistrict parent = cfgDistrictDao.getByKey(param.getParent());
			Assert.notNull(ConfigCode.DISTRICT_NOT_EXIST, parent);
			Assert.isTrue(parent.getLevel() == district.getLevel() + 1);
		}
		if (null != param.getValid() && district.isValid() ^ param.getValid()) {
			district.setValid(param.getValid());
			if (district.isValid()) {
				LinkedList<CfgDistrict> list = parents(district);
				if (!CollectionUtil.isEmpty(list)) {
					list.forEach(item -> {
						item.setValid(true);
						item.setUpdated(DateUtil.current());
					});
					cfgDistrictDao.batchInsert(list);
				}
			} else {
				List<CfgDistrict> list = childrens(district);
				if (!CollectionUtil.isEmpty(list)) {
					list.forEach(item -> {
						item.setValid(false);
						item.setUpdated(DateUtil.current());
					});
					cfgDistrictDao.batchInsert(list);
				}
			}
		}
		cfgDistrictDao.update(district);
	}
	
	public LinkedList<CfgDistrict> parents(CfgDistrict district) {
		LinkedList<CfgDistrict> list = new LinkedList<CfgDistrict>();
		while(StringUtil.hasText(district.getParent())) 
			list.addFirst(district(district.getParent()));
		return list;
	}
	
	public List<CfgDistrict> childrens(CfgDistrict district) { 
		List<CfgDistrict> list = new ArrayList<CfgDistrict>();
		_childrens(district, list);
		return list;
	}
	
	private void _childrens(CfgDistrict district, List<CfgDistrict> list) {
		List<CfgDistrict> districts = cfgDistrictDao.queryList(new Query().eq("parent", district.getCode()));
		if (CollectionUtil.isEmpty(list))
			return;
		list.addAll(districts);
		districts.forEach(item -> _childrens(item, list));
	}
	
	public CfgDistrict district(String code) {
		return cfgDistrictDao.getByKey(code);
	}
	
	public List<CfgDistrict> districts(Query query) {
		return cfgDistrictDao.queryList(query);
	}
}

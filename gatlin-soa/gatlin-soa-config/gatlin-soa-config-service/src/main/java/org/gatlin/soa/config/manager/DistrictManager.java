package org.gatlin.soa.config.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.config.EntityGenerator;
import org.gatlin.soa.config.bean.ConfigCode;
import org.gatlin.soa.config.bean.entity.CfgDistrict;
import org.gatlin.soa.config.bean.enums.DistrictLevel;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.soa.config.bean.param.DistrictModifyParam;
import org.gatlin.soa.config.mybatis.dao.CfgDistrictDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.algorithm.tree.TreeUtil;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.util.lang.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DistrictManager {

	@Resource
	private CfgDistrictDao cfgDistrictDao;
	
	public void add(DistrictAddParam param) {
		if (StringUtil.hasText(param.getParent())) {
			CfgDistrict parent = cfgDistrictDao.getByKey(param.getParent());
			Assert.notNull(ConfigCode.DISTRICT_NOT_EXIST, parent);
			Assert.isTrue(CoreCode.FORBID, parent.getLevel() != DistrictLevel.COUNTY.mark());
		}
		CfgDistrict instance = EntityGenerator.newCfgDistrict(param);
		cfgDistrictDao.insert(instance);
	}
	
	@Transactional
	public void modify(DistrictModifyParam param) {
		CfgDistrict district = cfgDistrictDao.getByKey(param.getId());
		Assert.notNull(ConfigCode.DISTRICT_NOT_EXIST, district);
		if (StringUtil.hasText(param.getName()))
			district.setName(param.getName());
		if (StringUtil.hasText(param.getAbname()))
			district.setAbname(param.getAbname());
		if (null != param.getLevel())
			district.setLevel(param.getLevel().mark());
		if (StringUtil.hasText(param.getAbname()))
			Assert.isTrue(CoreCode.PARAM_ERR, district.getLevel() == DistrictLevel.PROVINCE.mark());
		if (StringUtil.hasText(param.getParent()) && !district.getParent().equals(param.getParent())) {
			CfgDistrict parent = cfgDistrictDao.getByKey(param.getParent());
			Assert.notNull(ConfigCode.DISTRICT_NOT_EXIST, parent);
			Assert.isTrue(parent.getLevel() == district.getLevel() + 1);
		}
		if (null != param.getValid() && district.isValid() ^ param.getValid()) {
			if (param.getValid()) {
				Map<DistrictLevel, CfgDistrict> parents = parents(district);
				parents.put(DistrictLevel.match(district.getLevel()), district);
				if (!CollectionUtil.isEmpty(parents)) {
					parents.values().forEach(item -> {
						item.setValid(true);
						item.setUpdated(DateUtil.current());
					});
					cfgDistrictDao.updateCollection(parents.values());
				}
			} else {
				Query query = new Query().gt("level", district.getLevel()).eq("valid", 1);
				List<CfgDistrict> list = TreeUtil.children(cfgDistrictDao.queryList(query), district);
				list.add(district);
				if (!CollectionUtil.isEmpty(list)) {
					list.forEach(item -> {
						item.setValid(false);
						item.setUpdated(DateUtil.current());
					});
					cfgDistrictDao.updateCollection(list);
				}
			}
		} else
			cfgDistrictDao.update(district);
	}
	
	public Geo geo(String code, boolean validCheck) {
		CfgDistrict district = district(code);
		Assert.notNull(ConfigCode.DISTRICT_NOT_EXIST, district);
		Map<DistrictLevel, CfgDistrict> m = parents(district);
		m.put(DistrictLevel.match(district.getLevel()), district);
		CfgDistrict city = m.get(DistrictLevel.CITY);
		CfgDistrict county = m.get(DistrictLevel.COUNTY);
		CfgDistrict country = m.get(DistrictLevel.COUNTRY);
		CfgDistrict province = m.get(DistrictLevel.PROVINCE);
		Geo geo = new Geo();
		if (null != city && ((validCheck && city.isValid()) || !validCheck))
			geo.setCity(city.getName());
		if (null != county && ((validCheck && county.isValid()) || !validCheck))
			geo.setCounty(county.getName());
		if (null != country && ((validCheck && country.isValid()) || !validCheck))
			geo.setCountry(country.getName());
		if (null != province && ((validCheck && province.isValid()) || !validCheck))
			geo.setProvince(province.getName());
		return geo;
	}
	
	public Map<DistrictLevel, CfgDistrict> parents(CfgDistrict district) {
		Map<DistrictLevel, CfgDistrict> map = new HashMap<DistrictLevel, CfgDistrict>();
		while(StringUtil.hasText(district.getParent())) {
			district = district(district.getParent());
			map.put(DistrictLevel.match(district.getLevel()), district);
		}
		return map;
	}
	
	public List<CfgDistrict> childrens(CfgDistrict district) { 
		List<CfgDistrict> list = new ArrayList<CfgDistrict>();
		_childrens(district, list);
		return list;
	}
	
	private void _childrens(CfgDistrict district, List<CfgDistrict> list) {
		List<CfgDistrict> districts = cfgDistrictDao.queryList(new Query().eq("parent", district.getCode()));
		if (CollectionUtil.isEmpty(districts))
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

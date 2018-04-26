package org.gatlin.soa.resource.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.resource.bean.entity.CfgResourceMapping;

public interface CfgResourceMappingDao extends DBDao<Integer, CfgResourceMapping> {
	
	@Select("SELECT * FROM cfg_resource_mapping WHERE `type`=2 AND `tid`=#{resourceId} AND group_id IN(SELECT group_id FROM cfg_resource_mapping WHERE `type`=1 AND `tid`=#{uploadType})")
	CfgResourceMapping getByUploadTypeAndResourceId(@Param("uploadType") int uploadType, @Param("resourceId") int resourceId);
}

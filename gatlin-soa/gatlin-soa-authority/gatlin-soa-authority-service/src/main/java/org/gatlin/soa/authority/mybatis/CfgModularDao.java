package org.gatlin.soa.authority.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.authority.bean.entity.CfgModular;

public interface CfgModularDao extends DBDao<Integer, CfgModular> {
	
	@Update("UPDATE cfg_modular SET left=left+2 WHERE trunk=#{trunk} AND left>#{limit}")
	int updateInsertLeft(@Param("trunk") String trunk, @Param("limit") int limit);
	
	@Update("UPDATE cfg_modular SET right=right+2 WHERE trunk=#{trunk} AND right>=#{limit}")
	int updateInsertRight(@Param("trunk") String trunk, @Param("limit") int limit);
	
	@Select("SELECT * FROM cfg_modular WHERE trunk=#{trunk} AND left>#{left} AND right<#{right}")
	List<CfgModular> children(CfgModular modular);
	
	@Update("UPDATE cfg_modular SET left=left-width WHERE trunk=#{trunk} AND left>#{limit}")
	int updateDeleteLeft(@Param("trunk") String trunk, @Param("limit") int limit, @Param("width") int width);
	
	@Update("UPDATE cfg_modular SET right=right-width WHERE trunk=#{trunk} AND right>#{limit}")
	int updateDeleteRight(@Param("trunk") String trunk, @Param("limit") int limit, @Param("width") int width);
}

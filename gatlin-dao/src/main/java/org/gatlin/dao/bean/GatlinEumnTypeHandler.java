package org.gatlin.dao.bean;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.gatlin.util.bean.IEnum;
import org.gatlin.util.lang.EnumUtil;

public class GatlinEumnTypeHandler<E extends Enum<?> & IEnum> extends BaseTypeHandler<IEnum> {
	
	private Class<E> type;
	
	public GatlinEumnTypeHandler(Class<E> type) {
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, IEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.mark());
	}

	@Override
	public IEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int mark = rs.getInt(columnName);
		return rs.wasNull() ? null : _parse(mark);
	}

	@Override
	public IEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int mark = rs.getInt(columnIndex);
		return rs.wasNull() ? null : _parse(mark);
	}

	@Override
	public IEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int mark = cs.getInt(columnIndex);
		return cs.wasNull() ? null : _parse(mark);
	}
	
	private E _parse(int mark) { 
		try {
			return EnumUtil.valueOf(type, mark);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot convert " + mark + " to " + type.getSimpleName() + " by mark value!", e);
		}
	}
}

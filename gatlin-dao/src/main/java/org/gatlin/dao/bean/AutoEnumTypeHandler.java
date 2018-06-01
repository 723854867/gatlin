package org.gatlin.dao.bean;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.gatlin.util.bean.IEnum;

@SuppressWarnings("all")
public class AutoEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
	
	private BaseTypeHandler typeHandler;
	
	public AutoEnumTypeHandler(Class<E> type) {
		if (IEnum.class.isAssignableFrom(type))
			typeHandler = new GatlinEumnTypeHandler(type);
		else
			typeHandler = new EnumTypeHandler<>(type);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		typeHandler.setNonNullParameter(ps, i, parameter, jdbcType);
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return (E) typeHandler.getNullableResult(rs, columnName);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return (E) typeHandler.getNullableResult(rs, columnIndex);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return (E) typeHandler.getNullableResult(cs, columnIndex);
	}
}

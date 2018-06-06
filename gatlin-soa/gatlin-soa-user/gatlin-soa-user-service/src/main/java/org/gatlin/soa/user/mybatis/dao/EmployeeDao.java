package org.gatlin.soa.user.mybatis.dao;

import java.util.List;

import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.user.bean.entity.Employee;
import org.gatlin.soa.user.bean.model.EmployeeInfo;
import org.gatlin.soa.user.bean.param.EmployeesParam;

public interface EmployeeDao extends DBDao<Long, Employee> {

	List<EmployeeInfo> list(EmployeesParam param);
}

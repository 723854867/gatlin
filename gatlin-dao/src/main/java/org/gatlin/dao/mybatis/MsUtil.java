package org.gatlin.dao.mybatis;

import org.apache.ibatis.mapping.MappedStatement;
import org.gatlin.core.bean.exceptions.CodeException;

public class MsUtil {

	/**
	 * 根据msId获取接口类
	 *
	 * @param msId
	 * @return
	 */
	public static Class<?> getMapperClass(String msId) {
		if (msId.indexOf(".") == -1)
			throw new CodeException("当前MappedStatement的id=" + msId + ",不符合MappedStatement的规则!");
		String mapperClassStr = msId.substring(0, msId.lastIndexOf("."));
		ClassLoader[] classLoader = getClassLoaders();
		Class<?> mapperClass = null;
		for (ClassLoader cl : classLoader) {
			if (null != cl) {
				try {
					mapperClass = Class.forName(mapperClassStr, true, cl);
					if (mapperClass != null)
						break;
				} catch (ClassNotFoundException e) {
					// we'll ignore this until all class loaders fail to locate the class
				}
			}
		}
		if (mapperClass == null)
			throw new CodeException("class loaders failed to locate the class " + mapperClassStr);
		return mapperClass;
	}

	private static ClassLoader[] getClassLoaders() {
		return new ClassLoader[] { Thread.currentThread().getContextClassLoader(), MsUtil.class.getClassLoader() };
	}

	/**
	 * 获取执行的方法名
	 */
	public static String getMethodName(MappedStatement ms) {
		return getMethodName(ms.getId());
	}

	/**
	 * 获取执行的方法名
	 */
	public static String getMethodName(String msId) {
		return msId.substring(msId.lastIndexOf(".") + 1);
	}
}

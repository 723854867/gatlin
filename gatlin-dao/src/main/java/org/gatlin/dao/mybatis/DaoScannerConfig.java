package org.gatlin.dao.mybatis;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.dao.DaoConsts.Options;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(DBCondition.class)
public class DaoScannerConfig {

	/**
	 * 该 bean 不能和 @EnableTransactionManagement 一起使用，因为该 bean 是一个spring的生命周期bean
	 * 有特殊的初始化
	 * @return
	 * @throws Exception
	 */
	@Bean
	public DaoScannerConfigurer mapperScannerConfigurer() {
		DaoScannerConfigurer scanner = new DaoScannerConfigurer();
		String value = GatlinConfigration.get(Options.DB_MYBATIS_BASE_PACKAGE);
		if (null != value)
			scanner.setBasePackage(value);
		return scanner;
	}
}

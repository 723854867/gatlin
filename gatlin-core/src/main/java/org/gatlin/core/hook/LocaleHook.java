package org.gatlin.core.hook;

import org.gatlin.core.Gatlin;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.enums.Locale;
import org.springframework.stereotype.Component;

/**
 * 国际化钩子：根据配置的环境不同加载不同的配置文件
 * 
 * @author admin
 */
@Component
public class LocaleHook implements InitialHook {

	@Override
	public void init(Gatlin gatlin) throws Exception {
		Locale locale = gatlin.locale();
		String path = "classpath*:lang/lang_" + locale.mark() + ".properties";
		GatlinConfigration.loadProperties(path);
	}
}

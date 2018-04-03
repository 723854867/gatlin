package org.gatlin.dao.redis;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.gatlin.core.Gatlin;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.hook.InitialHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Conditional(RedisCondition.class)
public class LuaHook implements InitialHook {
	
	private static final Logger logger = LoggerFactory.getLogger(LuaHook.class);
	
	@javax.annotation.Resource
	private LuaFiber luaFiber;

	@Override
	public void init(Gatlin gatlin) throws Exception {
		logger.info("Lua script initial start...");
		int count = 0;
		try {
			
			Resource[] resources = GatlinConfigration.RESOLVER.getResources("classpath*:conf/lua/*.lua");
			for (Resource resource : resources) {
				String url = resource.getURL().toString();
				logger.debug("Lua script load from {}...", url);
				int seperator = url.lastIndexOf("/");
				String key = url.substring(seperator + 1).replaceAll(".lua", "");
				InputStream in = resource.getInputStream();
				BufferedInputStream bin = new BufferedInputStream(in);
				if (in.available() <= 0) {
					logger.warn("Lua script {} is empty, value will be ignored!", resource.getURL());
					continue;
				}
				byte[] buffer = new byte[in.available()];
				bin.read(buffer);
				luaFiber.addScript(key.toUpperCase(), buffer);
				count += 1;
			}
		} catch (Exception e) {
			logger.error("Lua script load failure, system will closed!", e);
			System.exit(1);
		}
		logger.info("Lua script initial finish, total {} records were loaded!", count);
	}
}

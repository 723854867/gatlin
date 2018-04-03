package org.gatlin.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.gatlin.core.bean.enums.Env;
import org.gatlin.core.bean.enums.Locale;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.hook.InitialHook;
import org.gatlin.core.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@Component
public class Gatlin implements ApplicationListener<ApplicationContextEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(Gatlin.class);

	private Env env;
	private Locale locale;
	private boolean initial;
	
	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if (event instanceof ContextClosedEvent) {
			if (initial)
				_dispose();
		} else if (event instanceof ContextRefreshedEvent) {
			logger.info("spring container refreshed!");
			if (!initial)
				_initial();
		} else if (event instanceof ContextStartedEvent) {
			logger.info("spring container started!");
			if (!initial)
				_initial();
		} else if (event instanceof ContextStoppedEvent) {
			if (initial)
				_dispose();
		} else 
			logger.error("Receive unrecognizable spring event {}!", event);
	}
	
	private synchronized void _initial() {
		if (initial)
			return;
		this.initial = true;
		logger.info("spring container initialize success, start initialize application...");
		long start = System.nanoTime();
		this.env = GatlinConfigration.get(CoreConsts.Options.GATLIN_ENV);
		this.locale = GatlinConfigration.get(CoreConsts.Options.GATLIN_LOCALE);
		Map<String, InitialHook> hooks = SpringContextUtil.getApplicationContext().getBeansOfType(InitialHook.class, false, true);
		List<InitialHook> list = new ArrayList<InitialHook>(hooks.values());
		Collections.sort(list, (o1, o2) -> o1.priority() - o2.priority());
		for (InitialHook hook : list)
			try {
				hook.init(this);
			} catch (Exception e) {
				throw new CodeException(e);
			}
		long end = System.nanoTime();
		logger.info("application initial success in {} seconds!", TimeUnit.NANOSECONDS.toSeconds(end - start));
	}
	
	private void _dispose() {
		if (!initial)
			return;
		this.initial = false;
		logger.info("start stop application...");
		long start = System.nanoTime();
		long end = System.nanoTime();
		logger.info("application stop success in {} seconds!", TimeUnit.NANOSECONDS.toSeconds(end - start));
	}
	
	public Env env() {
		return env;
	}
	
	public Locale locale() {
		return locale;
	}
}

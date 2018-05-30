package org.gatlin.core;

import org.gatlin.core.bean.enums.Env;
import org.gatlin.core.bean.enums.Locale;
import org.gatlin.core.bean.model.option.BoolOption;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.Option;
import org.gatlin.core.bean.model.option.StrOption;

public interface CoreConsts {

	final BoolOption HTTP_ENABLE								= new BoolOption("http.enable", false);
	final BoolOption MAIL_ENABLE								= new BoolOption("mail.enable", false);
	final Option<Env> GATLIN_ENV								= new Option<Env>("gatlin.env", Env.LOCAL);
	final Option<Locale> GATLIN_LOCALE							= new Option<Locale>("gatlin.locale", Locale.ZH_CN);
	
	final StrOption MQ_ROLE										= new StrOption("mq.role", "");
	
	final StrOption ACTIVEMQ_USERNAME							= new StrOption("activemq.username", "");
	final StrOption ACTIVEMQ_PASSWORD							= new StrOption("activemq.password", "");
	final StrOption ACTIVEMQ_BROKER_URL							= new StrOption("activemq.brokerUrl");
	final BoolOption ACTIVEMQ_DAEMON							= new BoolOption("activemq.daemon", true);
	final BoolOption ACTIVEMQ_TRUST_ALL_PACKAGES				= new BoolOption("activemq.trustAllPackages", false);
	final BoolOption ACTIVEMQ_USE_ASYNC_SEND					= new BoolOption("activemq.useAsyncSend", false);
	final IntegerOption ACTIVEMQ_MAX_POOL_SIZE					= new IntegerOption("activemq.maxPoolSize", 300);
	final IntegerOption ACTIVEMQ_CORE_POOL_SIZE					= new IntegerOption("activemq.corePoolSize", 150);
	final IntegerOption ACTIVEMQ_MAX_CONNECTIONS				= new IntegerOption("activemq.maxConnections", 100);
	final IntegerOption ACTIVEMQ_RECEIVE_TIMEOUT				= new IntegerOption("activemq.receiveTimeout", 1000);
	final IntegerOption ACTIVEMQ_SESSION_CACHE_SIZE				= new IntegerOption("activemq.sessionCacheSize", 100);
	final IntegerOption ACTIVEMQ_KEEP_ALIVE_SECONDS				= new IntegerOption("activemq.keepAliveSeconds", 120);
	final BoolOption ACTIVEMQ_EXPLICIT_QOS_ENABLED				= new BoolOption("activemq.explicitQosEnabled", true);
	final BoolOption ACTIVEMQ_DELIVERY_PERSISTENT				= new BoolOption("activemq.deliveryPersistent", true);
	final IntegerOption ACTIVEMQ_CONCURRENT_CONSUMERS			= new IntegerOption("activemq.concurrentConsumers", 1);
}

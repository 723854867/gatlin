package org.gatlin.web.util;

import javax.validation.Validator;

import org.gatlin.core.CoreConsts;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.enums.Locale;
import org.gatlin.web.WebConsts;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableAspectJAutoProxy
public class WebConfig {
	
	@Bean("validator")
	public Validator validator() {
		LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		factory.setProviderClass(HibernateValidator.class);
		factory.setValidationMessageSource(messageSource());
		return factory;
	}
	
	@Bean("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		Locale locale = GatlinConfigration.get(CoreConsts.GATLIN_LOCALE);
		String langFile = "classpath:conf/lang/lang_" + locale.mark();
		messageSource.setBasenames(new String[] {langFile, "classpath:org/hibernate/validator/ValidationMessages"});
		messageSource.setUseCodeAsDefaultMessage(false);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(60);
		return messageSource;
	}
	
	@Bean
	@Conditional(UploadCondition.class)
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxUploadSize(GatlinConfigration.get(WebConsts.Options.RAPID_MAX_UPLOAD_SIZE));
		resolver.setMaxInMemorySize(GatlinConfigration.get(WebConsts.Options.RAPID_MAX_IN_MEMORY_SIZE));
		resolver.setMaxUploadSizePerFile((long) GatlinConfigration.get(WebConsts.Options.RAPID_MAX_UPLOAD_SIZE_PER_FILE));
		return resolver;
	}
}

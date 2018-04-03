package org.gatlin.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	private static AutowireCapableBeanFactory autowireCapableBeanFactory;

	/**
	 * ApplicationContext interface call back, set application context
	 * 
	 * @param applicationContext
	 * @throws BeansException
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
		SpringContextUtil.autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Get Object
	 * 
	 * @param name
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * Get class whose type is requiredType If bean can not be converted, throw
	 * BeanNotOfRequiredTypeException
	 * 
	 * @param name:
	 *            bean registration name
	 * @param requiredType:
	 *            return object type
	 * @return Object: return object whose type is required type
	 * @throws BeansException
	 */
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * If BeanFactory is contains a named bean definition, return true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * Check the named bean whether is a singleton or prototype
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	/**
	 * 
	 * @param name
	 * @return Object type
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}

	public static void autowireBean(Object bean) {
		autowireCapableBeanFactory.autowireBean(bean);
	}

	@SuppressWarnings("unchecked")
	public static <T> T autowire(Class<?> beanClass) {
		return (T) autowireCapableBeanFactory.autowire(beanClass, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
	}
}

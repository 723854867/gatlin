package org.gatlin.web.util.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import org.gatlin.core.util.Assert;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.MethodParameter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 1、在 dispatcher.xml 中定义该 intercepto；
 * 2、需要在方法上使用 @Valid 注解标注；
 * 3、并且需要对基本类型的参数使用 @RequestParam同时需要注意的是，由于 @RequestParam 本身有 require 属性来对是否允许为 null 做控制，
 * 而且当 @RequestParam 的 require 为 true时如果客户端未传该值，则会抛出异常。因此如果需要启用 springmvc 的 validate，最好将 @RequestParam 的 require 设置为 false。
 * 
 * @author lynn
 */
public class ValidationInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ValidationInterceptor.class);

	@Resource
	private Validator validator;
	private List<HandlerMethodArgumentResolver> argumentResolvers;
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache = new ConcurrentHashMap<MethodParameter, HandlerMethodArgumentResolver>(256);
	private final Map<Class<?>, Set<Method>> initBinderCache = new ConcurrentHashMap<Class<?>, Set<Method>>();

	@Autowired
	public ValidationInterceptor(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
		this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
		this.argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod))
			return true;
		HandlerMethod method = (HandlerMethod) handler;
		Valid valid = method.getMethodAnnotation(Valid.class);
		if (null == valid)
			return true;
		Class<?>[] groups = new Class<?>[0];
		MethodParameter[] parameters = method.getMethodParameters();
		Object[] parameterValues = new Object[parameters.length];
		LocalValidatorFactoryBean validatorFactoryBean = (LocalValidatorFactoryBean) validator;
		ValidatorImpl validatorImpl = (ValidatorImpl) validatorFactoryBean.getValidator();
		ServletWebRequest webRequest = new ServletWebRequest(request, response);
		for (int i = 0; i < parameters.length; i++) {
			MethodParameter parameter = parameters[i];
			HandlerMethodArgumentResolver resolver = _getArgumentResolver(parameter);
			Assert.notNull("Unknown parameter type [" + parameter.getParameterType().getName() + "]", resolver);
			ModelAndViewContainer mavContainer = new ModelAndViewContainer();
			mavContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
			WebDataBinderFactory webDataBinderFactory = _getDataBinderFactory(method);
			Object value = resolver.resolveArgument(parameter, mavContainer, webRequest, webDataBinderFactory);
			parameterValues[i] = value;
		}
		Set<ConstraintViolation<Object>> violations = validatorImpl.validateParameters(method.getBean(),
				method.getMethod(), parameterValues, groups);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);
		return true;
	}

	private HandlerMethodArgumentResolver _getArgumentResolver(MethodParameter parameter) {
		HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
		if (null != result)
			return result;
		for (HandlerMethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
			logger.debug("Testing if argument resolver [" + methodArgumentResolver + "] supports [" + parameter.getGenericParameterType() + "]");
			if (methodArgumentResolver.supportsParameter(parameter)) {
				result = methodArgumentResolver;
				this.argumentResolverCache.put(parameter, result);
				break;
			}
		}
		return result;
	}

	private WebDataBinderFactory _getDataBinderFactory(HandlerMethod handlerMethod) throws Exception {
		Class<?> handlerType = handlerMethod.getBeanType();
		Set<Method> methods = this.initBinderCache.get(handlerType);
		if (methods == null) {
			methods = MethodIntrospector.selectMethods(handlerType, RequestMappingHandlerAdapter.INIT_BINDER_METHODS);
			this.initBinderCache.put(handlerType, methods);
		}
		List<InvocableHandlerMethod> initBinderMethods = new ArrayList<InvocableHandlerMethod>();
		for (Method method : methods) {
			Object bean = handlerMethod.getBean();
			initBinderMethods.add(new InvocableHandlerMethod(bean, method));
		}
		return new ServletRequestDataBinderFactory(initBinderMethods,
				requestMappingHandlerAdapter.getWebBindingInitializer());
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
}

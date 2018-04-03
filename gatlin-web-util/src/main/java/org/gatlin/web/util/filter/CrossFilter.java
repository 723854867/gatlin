package org.gatlin.web.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

/**
 * 跨域filer
 * 
 * @author lynn
 */
public class CrossFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;  
		response.setCharacterEncoding("UTF-8");  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept, Origin, Token");  
        response.setHeader("Access-Control-Allow-Credentials", "true");  
        if (request.getMethod() == HttpMethod.OPTIONS.name()) {
			response.setStatus(HttpStatus.NO_CONTENT.value());
			return;
        }
        chain.doFilter(servletRequest, servletResponse);  
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}
}

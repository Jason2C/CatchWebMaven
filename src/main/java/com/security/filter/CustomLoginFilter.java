package com.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;
import org.springframework.util.Assert;

/**
 * 从UsernamePasswordAuthenticationFilter继承一个类，然后把关于POST方式判断的代码注释掉即可。默认情况下，Spring
 * Security的用户名是区分大小写，如果觉得没必要，上面的代码同时还演示了如何在Filter中自动将其转换成大写。
 * 
 * @author Jason
 * 
 */
@SuppressWarnings("all")
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String yanz = (String) request.getSession().getAttribute("rCode");
		String shuruyanz = (String) request.getParameter("randx");

		/*
		 * String username = null; String password = null; if
		 * (!request.getMethod().equals("POST")) { throw new
		 * AuthenticationServiceException
		 * ("Authentication method not supported: " + request.getMethod()); } if
		 * (yanz.equals(shuruyanz)) { username = obtainUsername(request);
		 * password = obtainPassword(request); } if (username == null) {
		 * username = ""; }
		 * 
		 * if (password == null) { password = ""; } username = username.trim();
		 * 
		 * UsernamePasswordAuthenticationToken authRequest = new
		 * UsernamePasswordAuthenticationToken(username, password);
		 * 
		 * HttpSession session = request.getSession(false);
		 * 
		 * if (session != null || getAllowSessionCreation()) {
		 * request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY,
		 * TextEscapeUtils.escapeEntities(username)); }
		 * 
		 * setDetails(request, authRequest);
		 */

		if (!yanz.equals(shuruyanz)) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		 return super.attemptAuthentication(request, response);
		 //return this.getAuthenticationManager().authenticate(authRequest);
	}


}

package com.codingwasabi.howtodo.security.oauth2.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
										HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}

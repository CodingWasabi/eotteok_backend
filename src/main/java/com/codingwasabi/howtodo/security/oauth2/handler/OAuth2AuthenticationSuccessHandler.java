package com.codingwasabi.howtodo.security.oauth2.handler;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		// response.encodeRedirectURL("http://localhost:3000/result");
		/*String requestCookie = Arrays.stream(request.getCookies())
								  .filter(cookie -> cookie.getName()
														  .equals("JSESSIONID"))
								  .findAny()
								  .get()
								  .getValue();
*/
		// response.addCookie(new Cookie("localhost:3000", requestCookie));
		// response.encodeURL("http://localhost:3000/result");
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
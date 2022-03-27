package com.codingwasabi.howtodo.security.resolver;

import static com.codingwasabi.howtodo.web.account.entity.Account.*;
import static com.codingwasabi.howtodo.web.account.entity.Authority.*;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.codingwasabi.howtodo.web.account.entity.Account;
import com.codingwasabi.howtodo.security.oauth2.OAuth2UserAdapter;

@Component
public class LoginAccountArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return isCorrectAnnotation(parameter) && isCorrectParameterType(parameter);
	}

	private boolean isCorrectParameterType(MethodParameter parameter) {
		return Account.class.equals(parameter.getParameterType());
	}

	private boolean isCorrectAnnotation(MethodParameter parameter) {
		return parameter.getParameterAnnotation(LoginAccount.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
								  ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest,
								  WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext()
															 .getAuthentication();
		return getAccount(authentication);
	}

	private Object getAccount(Authentication authentication) {
		if (isAnonymousPrincipal(authentication)) {
			return Anonymous;
		}
		return ((OAuth2UserAdapter)authentication.getPrincipal()).getAccount();
	}

	private boolean isAnonymousPrincipal(Authentication authentication) {
		return authentication.getAuthorities()
							 .contains(new SimpleGrantedAuthority(ANONYMOUS.getRole()));
	}
}

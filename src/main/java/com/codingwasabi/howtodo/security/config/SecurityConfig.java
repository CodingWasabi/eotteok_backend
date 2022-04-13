package com.codingwasabi.howtodo.security.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final OAuth2UserService oAuth2UserService;
	private final LogoutSuccessHandler logoutSuccessHandler;
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.formLogin()
			.disable()

			.authorizeRequests()
			.mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.anyRequest()
			.permitAll()

			.and()
			.httpBasic()
			.disable()

			.logout()
			.logoutUrl("/auth/logout")
			.logoutSuccessHandler(logoutSuccessHandler)
			.deleteCookies()
			.and()

			.sessionManagement()
			.maximumSessions(1)
			.and()
			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()

			.oauth2Login()
			.authorizationEndpoint()
			.baseUri("/oauth/authorize")
			.and()
			.userInfoEndpoint()
			.userService(oAuth2UserService)
			.and()
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler);
	}
}

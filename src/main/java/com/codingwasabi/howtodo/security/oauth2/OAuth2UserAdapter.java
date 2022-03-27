package com.codingwasabi.howtodo.security.oauth2;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.codingwasabi.howtodo.web.account.entity.Account;

public class OAuth2UserAdapter extends DefaultOAuth2User implements UserDetails {
	private Account account;

	public OAuth2UserAdapter(Account account, String attributeName, Map<String, Object> attributes) {
		super(Collections.singleton(new SimpleGrantedAuthority(account.getAuthority()
																   .getRole())), attributes, attributeName);
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return account.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

package com.codingwasabi.howtodo.security.oauth2.service;

import static com.codingwasabi.howtodo.security.oauth2.service.OAuthAttributes.*;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.codingwasabi.howtodo.security.oauth2.OAuth2UserAdapter;
import com.codingwasabi.howtodo.web.account.AccountRepository;
import com.codingwasabi.howtodo.web.account.entity.Account;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService {
	private final AccountRepository accountRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String oauth2Name = getOAuth2Name(userRequest);
		String attributeName = getAttributeName(userRequest);

		OAuthAttributes attributes = of(oauth2Name, attributeName, oAuth2User.getAttributes());

		Account account = accountRepository.findByEmail(attributes.getEmail())
										   .orElseGet(() -> {
											   if (attributes.noProvider()) {
												   throw new IllegalArgumentException("uncorrected oauth2 provider");
											   }
											   return accountRepository.save(createAccount(attributes));
										   });

		return new OAuth2UserAdapter(account, attributeName, attributes.getAttributes());
	}

	private String getAttributeName(OAuth2UserRequest userRequest) {
		return userRequest.getClientRegistration()
						  .getProviderDetails()
						  .getUserInfoEndpoint()
						  .getUserNameAttributeName();
	}

	private String getOAuth2Name(OAuth2UserRequest userRequest) {
		return userRequest.getClientRegistration()
						  .getClientName();
	}

	private Account createAccount(OAuthAttributes attributes) {
		return Account.builder()
					  .provider(attributes.getProvider())
					  .oauthId(attributes.getAttributeValue())
					  .email(attributes.getEmail())
					  .ageRange(attributes.getAgeRange())
					  .birthday(attributes.getBirthday())
					  .gender(attributes.getGender())
					  .build();
	}
}
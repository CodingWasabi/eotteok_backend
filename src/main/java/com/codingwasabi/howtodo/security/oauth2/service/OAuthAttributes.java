package com.codingwasabi.howtodo.security.oauth2.service;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;

public class OAuthAttributes {
	public static final String EMAIL = "email";
	public static final String AGE_RANGE = "age_range";
	public static final String BIRTHDAY = "birthday";
	public static final String GENDER = "gender";
	public static final String PROVIDER = "provider";
	public static final String KAKAO_ATTRIBUTE = "kakao_account";

	private final Map<String, Object> attributes;
	private final String NAME_ATTRIBUTE_KEY;

	@Builder
	private OAuthAttributes(String email,
							String ageRange,
							String birthday,
							String gender,
							String nameAttributeKey,
							Long nameAttributeValue,
							String provider) {
		attributes = new HashMap<>();
		NAME_ATTRIBUTE_KEY = nameAttributeKey;

		attributes.put(EMAIL, email);
		attributes.put(AGE_RANGE, ageRange);
		attributes.put(BIRTHDAY, birthday);
		attributes.put(GENDER, gender);
		attributes.put(NAME_ATTRIBUTE_KEY, nameAttributeValue);
		attributes.put(PROVIDER, provider);
	}

	public static OAuthAttributes of(String oauth2Name, String nameAttributeKey, Map<String, Object> attributes) {
		if (isKakao(oauth2Name)) {
			return ofKakao(oauth2Name, nameAttributeKey, attributes);
		}
		return OAuthAttributes.builder()
							  .email("")
							  .ageRange("")
							  .birthday("")
							  .gender("")
							  .nameAttributeValue(null)
							  .provider(null)
							  .build();
	}

	private static OAuthAttributes ofKakao(String oauth2Name, String nameAttributeKey, Map<String, Object> attributes) {
		Map<String, Object> information = (Map<String, Object>)attributes.get(KAKAO_ATTRIBUTE);
		return OAuthAttributes.builder()
							  .email((String)information.get(EMAIL))
							  .ageRange((String)information.get(AGE_RANGE))
							  .birthday((String)information.get(BIRTHDAY))
							  .gender((String)information.get(GENDER))
							  .nameAttributeKey(nameAttributeKey)
							  .nameAttributeValue((Long)attributes.get(nameAttributeKey))
							  .provider(oauth2Name)
							  .build();
	}

	private static boolean isKakao(String oauth2Name) {
		return "kakao".equals(oauth2Name);
	}

	public boolean noProvider() {
		return this.attributes.get(PROVIDER) == null;
	}

	public String getEmail() {
		return (String)attributes.get(EMAIL);
	}

	public String getAgeRange() {
		return (String)attributes.get(AGE_RANGE);
	}

	public String getBirthday() {
		return (String)attributes.get(BIRTHDAY);
	}

	public String getGender() {
		return (String)attributes.get(GENDER);
	}

	public String getProvider() {
		return (String)attributes.get(PROVIDER);
	}

	public Long getAttributeValue() {
		return (Long)attributes.get(NAME_ATTRIBUTE_KEY);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}
}

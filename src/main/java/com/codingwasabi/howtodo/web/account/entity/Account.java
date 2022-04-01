package com.codingwasabi.howtodo.web.account.entity;

import static com.codingwasabi.howtodo.web.account.entity.Authority.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Setter
	private String nickname;
	@Setter
	private int tendency;
	@Enumerated(EnumType.STRING)
	private Authority authority;

	// oauth information
	private Long oauthId;
	private String email;
	private String ageRange;
	private String birthday;
	private String provider;
	private String gender;

	@Builder
	private Account(Long oauthId, String email, String ageRange, String birthday, String provider, String gender) {
		this.oauthId = oauthId;
		this.email = email;
		this.ageRange = ageRange;
		this.birthday = birthday;
		this.provider = provider;
		this.gender = gender;
		this.authority = Authority.USER;
	}

	private Account(Authority authority) {
		this.authority = authority;
	}

	public static final Account Anonymous = new Account(ANONYMOUS);

	public boolean isAnonymous() {
		return authority.equals(ANONYMOUS);
	}
}

package com.codingwasabi.howtodo.web.account.entity;

import static com.codingwasabi.howtodo.web.account.entity.Authority.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.codingwasabi.howtodo.web.plan.entity.Plan;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long oauthId;
	private String email;
	private String ageRange;
	private String birthday;
	private String provider;
	private String gender;

	private String nickname;

	@Enumerated(EnumType.STRING)
	private Authority authority;

	@OneToMany(mappedBy = "account")
	@Builder.Default
	private List<Plan> plans = new ArrayList<>();

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
}

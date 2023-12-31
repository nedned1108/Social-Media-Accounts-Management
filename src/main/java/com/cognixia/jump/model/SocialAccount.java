package com.cognixia.jump.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class SocialAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Platform {
		FACEBOOK, INSTAGRAM
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Schema(description="Social Media platform", example="FACEBOOK", required=true)
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Platform platformName;
	
	@Schema(description="User's social media account name", example="group2_123", required=true)
	@Column
	@NotBlank
	private String accountName;
	
	@Schema(description="social media account description", example="description example", required=true)
	@Column
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	public SocialAccount() {

	}

	public SocialAccount(Integer id, @NotBlank String accountName, String description,
			Platform platformName, User user) {
		super();
		this.id = id;
		this.accountName = accountName;
		this.platformName = platformName;
		this.description = description;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Platform getPlatformName() {
		return platformName;
	}

	public void setPlatformName(Platform platformName) {
		this.platformName = platformName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", account name=" + accountName + ", platform=" + platformName + ", description="
				+ description + ", user=" + user + "]";
	}

}

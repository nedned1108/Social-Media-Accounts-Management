package com.cognixia.jump.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class SocialAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Platform {
		FACEBOOK, INSTAGRAM
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Platform platformName;

	@Column
	@NotBlank
	private String accountName;

	@Column
	private String description;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
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

	public Platform getPlatform() {
		return platformName;
	}

	public void setPlatform(Platform platformName) {
		this.platformName = platformName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
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

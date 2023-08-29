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
		PLATFORM_FACEBOOK, PLATFORM_INSTA
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
	private String desc;

	@Column(nullable = false)
	@NotBlank
	private SimpleDateFormat createdAt;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public SocialAccount() {

	}

	public SocialAccount(Integer id, @NotBlank String accountName, String desc,
			Platform platformName, @NotBlank SimpleDateFormat createdAt, User user) {
		super();
		this.id = id;
		this.accountName = accountName;
		this.platformName = platformName;
		this.createdAt = createdAt;
		this.desc = desc;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return accountName;
	}

	public void setName(String accountName) {
		this.accountName = accountName;
	}

	public Platform getPlatform() {
		return platformName;
	}

	public void setPlatform(Platform platformName) {
		this.platformName = platformName;
	}

	public SimpleDateFormat getCreated() {
		return createdAt;
	}

	public void setCreated(SimpleDateFormat createdAt) {
		this.createdAt = createdAt;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
				+ desc + ", user=" + user + ", timestamp=" + createdAt + "]";
	}
}

package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.text.SimpleDateFormat;
import java.util.List;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Role {
		ROLE_USER
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, nullable = false)
	@NotBlank
	private String username;

	@Column(nullable = false)
	@NotBlank
	private String password;

	@Column(nullable = false)
	@NotBlank
	private String companyName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Column
	private String bio;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<SocialAccount> accounts;

	public User() {

	}

	public User(Integer id, @NotBlank String username, @NotBlank String password, @NotBlank String companyName,
			Role role,
			String bio, List<SocialAccount> accounts) {
		super();
		this.id = id;
		this.username = username;
		this.companyName = companyName;
		this.role = role;
		this.bio = bio;
		this.accounts = accounts;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return companyName;
	}

	public void setName(String companyName) {
		this.companyName = companyName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<SocialAccount> getAccounts() {
		return accounts;
	}

	public void setEnrolled(List<SocialAccount> accounts) {
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", company name=" + companyName
				+ ", role=" + role + ", bio=" + bio + "accounts=" + accounts
				+ "]";
	}
}

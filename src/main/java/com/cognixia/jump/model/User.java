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

import io.swagger.v3.oas.annotations.media.Schema;

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
	
	@Schema(description="User's username", example="group2", required=true)
	@Column(unique = true, nullable = false)
	@NotBlank
	private String username;
	
	@Schema(description="User's password", example="pass123", required=true)
	@Column(nullable = false)
	@NotBlank
	private String password;
	
	@Schema(description="User's company name", example="Cognixia", required=true)
	@Column(nullable = false)
	@NotBlank
	private String companyName;
	
	@Schema(description="User's role", example="ROLE_USER", required=true)
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@Schema(description="User's bio", example="bio example")
	@Column
	private String bio;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<SocialAccount> accounts;

	public User() {

	}

	public User(Integer id, @NotBlank String username, @NotBlank String password, @NotBlank String companyName,
			Role role, String bio, List<SocialAccount> accounts) {
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
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

	public void setAccounts(List<SocialAccount> accounts) {
		for (SocialAccount a : accounts) {
			a.setId(null);
			a.setUser(this);
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", company name=" + companyName
				+ ", role=" + role + ", bio=" + bio + "accounts=" + accounts
				+ "]";
	}
}

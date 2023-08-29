package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.SocialAccount;
import com.cognixia.jump.model.SocialAccount.Platform;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer>{

	@Query("SELECT s FROM SocialAccount s WHERE s.accountName = ?1")
	public Optional<SocialAccount> findByAccountName(String accountName);
	
	@Query("SELECT p FROM SocialAccount p WHERE p.platformName = ?1")
	public Optional<SocialAccount> findByPlatform(Platform platformName);
	
}

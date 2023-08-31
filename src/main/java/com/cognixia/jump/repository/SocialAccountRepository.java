package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.SocialAccount;
import com.cognixia.jump.model.SocialAccount.Platform;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer> {

	@Query("SELECT s FROM SocialAccount s WHERE s.accountName = ?1 AND s.platformName = ?2")
	public List<SocialAccount> findByPlatform(String accountName, Platform platformName);

}

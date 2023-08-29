package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.SocialAccount;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer>{

}

package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.SocialAccount;
import com.cognixia.jump.repository.SocialAccountRepository;

@Service
public class SocialAccountService {

    @Autowired
    SocialAccountRepository repo;

    public List<SocialAccount> getAccounts() {
        return repo.findAll();
    }

    public Optional<SocialAccount> getAccountById(int id) {

        return repo.findById(id);
    }

    public SocialAccount createAccount(SocialAccount account) {

        account.setId(null);

        SocialAccount created = repo.save(account);

        return created;
    }

    public Optional<SocialAccount> updateAccount(SocialAccount account) {

        // existsById() -> returns true if the id exists
        boolean exists = repo.existsById(account.getId());

        // can do update if id exists
        if (exists) {

            SocialAccount updated = repo.save(account);
            return Optional.of(updated);

        } else { // id doesn't exist, can't do update, return null

            return Optional.empty();
        }
    }

    public boolean deleteAccount(int id) {

        boolean exists = repo.existsById(id);

        if (exists) {

            repo.deleteById(id);

            return true;

        } else {

            return false;
        }

    }

}

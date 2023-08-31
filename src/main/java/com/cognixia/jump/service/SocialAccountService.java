package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.SameUserAndPlatformException;
import com.cognixia.jump.model.SocialAccount;
import com.cognixia.jump.repository.SocialAccountRepository;

@Service
public class SocialAccountService {

	@Autowired
	SocialAccountRepository repo;

	public List<SocialAccount> getAccounts() {
		return repo.findAll();
	}

	public SocialAccount getAccountById(int id) throws ResourceNotFoundException {

		Optional<SocialAccount> found = repo.findById(id);

		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Account");
		}

		return found.get();
	}

	public SocialAccount createAccount(SocialAccount account) throws SameUserAndPlatformException {

		List<SocialAccount> foundPlatform = repo.findByPlatform(account.getAccountName().toString(),
				account.getPlatformName());

		// make sure each account created has a unique account name or platform , if not
		// checked, will end up with 409 error
		if (!foundPlatform.isEmpty()) {
			throw new SameUserAndPlatformException();
		} else {
			account.setId(null);

			SocialAccount created = repo.save(account);

			return created;

		}

	}

	public SocialAccount updateAccount(SocialAccount account)
			throws ResourceNotFoundException, SameUserAndPlatformException {

		List<SocialAccount> foundPlatform = repo.findByPlatform(account.getAccountName(), account.getPlatformName());

		if (!foundPlatform.isEmpty()) {
			throw new SameUserAndPlatformException();
		} else {
			// existsById() -> returns true if the id exists
			boolean exists = repo.existsById(account.getId());

			// can do update if id exists
			if (exists) {

				SocialAccount updated = repo.save(account);
				return updated;

			} else { // id doesn't exist, can't do update, return null

				throw new ResourceNotFoundException("Account");
			}
		}
	}

	public boolean deleteAccount(int id) throws ResourceNotFoundException {

		boolean exists = repo.existsById(id);

		if (exists) {

			repo.deleteById(id);

			return true;

		} else {

			throw new ResourceNotFoundException("Account");
		}

	}

}

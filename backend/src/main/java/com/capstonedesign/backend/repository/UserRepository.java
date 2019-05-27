package com.capstonedesign.backend.repository;

import com.capstonedesign.backend.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Account, Long> {

    boolean existsAccountByUserMid(String userMid);
}

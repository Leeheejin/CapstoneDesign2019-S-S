package com.capstonedesign.backend.service;

import com.capstonedesign.backend.domain.Account;
import com.capstonedesign.backend.domain.Login;
import com.capstonedesign.backend.exception.IdPasswordNotMatchingException;
import com.capstonedesign.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewAccount(Account account) {

        userRepository.save(account);
    }

    public Account getUserInfo(Long id) {

        return userRepository.findById(id).orElse(new Account());
    }

    public Integer getOneDrink(Long id) {

        return userRepository.findById(id).orElse(new Account()).getOneDrink();
    }

    public void saveCurrentCup(Account account) {

        userRepository.save(account);
    }

    public void saveOneDrink(Account account) {

        userRepository.save(account);
    }

    public Long getCurrentProductId(Account account) {

        return account.getPid();
    }

    public boolean isAlreadyExistUserId(Account account) {

        return userRepository.existsAccountByUserMid(account.getUserMid());
    }

    public void changeCurrentProduct(Account account) {

        userRepository.save(account);
    }

    public Account loginAuth(Login login) throws Exception {

        Account account = userRepository.findAccountByUserMid(login.getUserMid());
        if(account == null) {
            throw new IdPasswordNotMatchingException();
        }
        if(!account.matchPassword(login.getPassword())) {
            throw new IdPasswordNotMatchingException();
        }
        return new Account();
    }
}

package com.capstonedesign.backend.service;

import com.capstonedesign.backend.domain.Account;
import com.capstonedesign.backend.domain.Water;
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

    public void saveUserInfo(Account account) {

        userRepository.save(account);
    }

    public Account getUserInfo(Long id) {

        return userRepository.findById(id).orElse(new Account());
    }

    public void saveOneDrink(Account account) {

        userRepository.save(account);
    }
}

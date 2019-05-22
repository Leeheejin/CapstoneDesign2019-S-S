package com.capstonedesign.backend.controller;

import com.capstonedesign.backend.domain.Account;
import com.capstonedesign.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public Account register(@RequestBody Account account) {

        if(!userService.isAlreadyExistUserId(account)) {

            userService.createNewAccount(account);

            return userService.getUserInfo(account.getId());
        }

        return null;
    }

    @PostMapping(path = "/confirm")
    public Account confirm(@RequestBody Account account) {

        userService.saveUserInfo(account);

        return userService.getUserInfo(account.getId());
    }

    @GetMapping(path = "/userinfo")
    public Account userInfo(@RequestBody Account account) {

        return userService.getUserInfo(account.getId());
    }

    @PostMapping(path = "/onedrink")
    public void userDrinkInfo(@RequestBody Account account) {

        userService.saveOneDrink(account);
    }
}

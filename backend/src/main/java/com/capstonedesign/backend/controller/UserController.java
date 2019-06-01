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

        Account oldAccountData = userService.getUserInfo(account.getId());
        if (!oldAccountData.getCupList().isEmpty()) {
            account.setCupList(oldAccountData.getCupList());
        }

        if (oldAccountData.getCurrentCup() != null) {
            account.setCurrentCup(oldAccountData.getCurrentCup());
        }

        if (oldAccountData.getRecommendDrink() != null) {
            account.setRecommendDrink(oldAccountData.getRecommendDrink());
        }

        if (oldAccountData.getOneDrink() != null) {
            account.setOneDrink(oldAccountData.getOneDrink());
        }

        userService.saveUserInfo(account);

        return userService.getUserInfo(account.getId());
    }

    @PostMapping(path = "/userinfo")
    public Account getUserInfo(@RequestBody Account account) {
        return userService.getUserInfo(account.getId());
    }

    @PostMapping(path = "/onedrink")
    public void userDrinkInfo(@RequestBody Account account) {

        userService.saveOneDrink(account);
    }

    @PostMapping(path = "/access")
    public void access(@RequestBody Account account) {

        userService.saveOneDrink(account);
    }

    @PostMapping(path = "/disconnect")
    public void disconnect(@RequestBody Account account) {

        Account accountToDataInput = userService.getUserInfo(account.getId());
        accountToDataInput.setAccessTime(account.getAccessTime() - accountToDataInput.getAccessTime());

        userService.saveOneDrink(accountToDataInput);
    }
}

package com.capstonedesign.backend.controller;

import com.capstonedesign.backend.domain.Account;
import com.capstonedesign.backend.domain.Water;
import com.capstonedesign.backend.service.UserService;
import com.capstonedesign.backend.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
public class WaterController {

    private final WaterService waterService;
    private final UserService userService;

    @Autowired
    public WaterController(WaterService waterService, UserService userService) {

        this.waterService = waterService;
        this.userService = userService;
    }

    @PostMapping(path = "/drink")
    public Account userDrinkPerOneTime(@RequestBody Water water) {

        Account account =  userService.getUserInfo(water.getUid());

        account.setNowDrink(account.getNowDrink() + water.getLastDrink());
        account.addLogInList(water.getLastDrink());

        Float temp = 0.0f;

        for (int i =0; i < account.getWaterLog().size(); i++) {
            temp += account.getWaterLog().get(i);
        }

        account.setOneDrink(temp/account.getWaterLog().size());

        if (account.getWaterLog().size() > 10) {
            account.setWaterLog(new ArrayList<Float>());
        }

        account.addDateInList(water.getLastDrinkDate());

        userService.saveUserInfo(account);
        waterService.saveDrinkLog(water);

        return account;
    }

    @PostMapping(path ="/drinkinfo")
    public Water getDrinkInfoPerOneTime(@RequestBody Water water) {

        return waterService.getOneDrink(water);
    }
}

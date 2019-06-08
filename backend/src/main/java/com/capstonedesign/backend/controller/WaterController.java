package com.capstonedesign.backend.controller;

import com.capstonedesign.backend.domain.Account;
import com.capstonedesign.backend.domain.Water;
import com.capstonedesign.backend.service.UserService;
import com.capstonedesign.backend.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Water userDrinkPerOneTime(@RequestBody Water water) {

        Account account =  userService.getUserInfo(water.getUid());

        account.setNowDrink(account.getNowDrink() + water.getLastDrink());
        account.addLogInList(water.getLastDrink());
        account.addDateInList(water.getLastDrinkDate());

        userService.saveUserInfo(account);
        waterService.saveDrinkLog(water);

        return waterService.getDrinkLogWithDate(water);
    }

    @PostMapping(path ="/drinkinfo")
    public Water getDrinkInfoPerOneTime(@RequestBody Water water) {

        return waterService.getOneDrink(water);
    }
}

package com.capstonedesign.backend.controller;

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
    public Water userDrinkPerOneTime(@RequestBody Water water) {

        waterService.saveDrinkLog(water);

        return waterService.getWaterDrinkPerData(water);
    }

    @GetMapping(path ="/getdrinkinfo")
    public Integer getDrinkInfoPerOneTime(@RequestBody Water water) {

        return userService.getOneDrink(water.getCid());
    }

    @GetMapping(path ="/getdrinkhistory")
    public ArrayList<Integer> getDrinkHistory(@RequestBody Water water) {

        return waterService.getDrinkLogWithDate(water);
    }
}

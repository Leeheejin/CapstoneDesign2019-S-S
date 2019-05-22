package com.capstonedesign.backend.controller;

import com.capstonedesign.backend.domain.Account;
import com.capstonedesign.backend.domain.Cup;
import com.capstonedesign.backend.service.CupService;
import com.capstonedesign.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@Controller
public class CupController {

    private final CupService cupService;
    private final UserService userService;

    @Autowired
    public CupController(CupService cupService, UserService userService) {

        this.cupService = cupService;
        this.userService = userService;
    }

    @PostMapping(path = "/savecup")
    public Cup saveCup(@RequestBody Cup cup) {

        cupService.saveCupInfo(cup);

        return cupService.getCupInfo(cup.getCid());
    }

    @PostMapping(path = "/changecup")
    public Cup changeCup(@RequestBody Cup cup) {

        Account account = userService.getUserInfo(cup.getUid());
        account.setCurrentCup(cup);
        account.addCupInList(cup);

        userService.saveUserInfo(account);

        return cupService.getCupInfo(cup.getCid());
    }

    @GetMapping(path = "/cupinfo")
    public Cup cupInfo(@RequestBody Cup cup) {

        return cupService.getCupInfo(cup.getCid());
    }
}

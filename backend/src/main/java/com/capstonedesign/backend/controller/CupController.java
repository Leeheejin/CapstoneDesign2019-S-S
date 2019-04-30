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

    @PostMapping(path = "/savecupinfo")
    public void saveCupInfo(@RequestBody Cup cup) {

        cupService.saveCupInfo(cup);
    }

    @GetMapping(path = "/currentcup")
    public Long getCurrentCup(@RequestBody Account account) {

        return userService.getCurrentProductId(account);
    }
}

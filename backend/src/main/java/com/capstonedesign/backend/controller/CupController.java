package com.capstonedesign.backend.controller;

import com.capstonedesign.backend.domain.Account;
import com.capstonedesign.backend.domain.Cup;
import com.capstonedesign.backend.service.CupService;
import com.capstonedesign.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
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

        Account account = userService.getUserInfo(cup.getUid());
        account.addCupInList(cup);
        cupService.saveCupInfo(cup);

        account.setCurrentCup(cup);

        userService.saveUserInfo(account);

        return cupService.getCupInfo(cup.getCid());
    }

    @PostMapping(path = "/changecup")
    public Cup changeCup(@RequestBody Cup cup) {

        Account account = userService.getUserInfo(cup.getUid());
        account.setCurrentCup(cup);

        userService.saveUserInfo(account);

        return cupService.getCupInfo(cup.getCid());
    }

    @PostMapping(path = "/cupinfo")
    public Cup getCupInfo(@RequestBody Cup cup) {

        return cupService.getCupInfo(cup.getCid());
    }

    @PostMapping(path = "/cupedit")
    public Cup changeCupInfo(@RequestBody Cup cup) {

        Cup oldCupData = cupService.getCupInfo(cup.getCid());

        if (cup.getCupName() == null) {
            cup.setCupName(oldCupData.getCupName());
        }

        if (cup.getCupWeight() == null) {
            cup.setCupWeight(oldCupData.getCupWeight());
        }

        Account account = userService.getUserInfo(cup.getUid());

        if (account.getCurrentCup().getCid().equals(cup.getCid())) {
            account.setCurrentCup(cup);
        }

        Cup toDeleteCup = cupService.getCupInfo(cup.getCid());

        account.getCupList().remove(toDeleteCup);
        account.addCupInList(cup);

        cupService.saveCupInfo(cup);
        userService.saveUserInfo(account);

        return cupService.getCupInfo(cup.getCid());
    }

    @PostMapping(path = "/cupdelete")
    public Cup deleteCup(@RequestBody Cup cup) {

        Account account = userService.getUserInfo(cup.getUid());
        Cup toDeleteCup = cupService.getCupInfo(cup.getCid());
        account.getCupList().remove(toDeleteCup);

        if (account.getCurrentCup() != null) {
            if (account.getCurrentCup().getCid().equals(cup.getCid())) {
                account.setCurrentCup(null);
            }
        }

        userService.saveUserInfo(account);
        cupService.deleteCup(cup);

        return toDeleteCup;
    }
}

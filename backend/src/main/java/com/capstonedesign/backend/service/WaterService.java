package com.capstonedesign.backend.service;

import com.capstonedesign.backend.domain.Water;
import com.capstonedesign.backend.repository.WaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaterService {

    private final WaterRepository waterRepository;

    @Autowired
    public WaterService(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }

    public void saveDrinkLog(Water water) {

        waterRepository.save(water);
    }

    public Water getDrinkLogWithDate(Water water) {

        return waterRepository.findById(water.getWid()).orElse(new Water());
    }

    public Water getOneDrink(Water water) {

        return waterRepository.findById(water.getWid()).orElse(new Water());
    }

}

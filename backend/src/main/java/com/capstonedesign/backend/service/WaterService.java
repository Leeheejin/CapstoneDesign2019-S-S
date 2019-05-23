package com.capstonedesign.backend.service;

import com.capstonedesign.backend.domain.Water;
import com.capstonedesign.backend.repository.WaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WaterService {

    private final WaterRepository waterRepository;

    @Autowired
    public WaterService(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }

    public void saveDrinkLog(Water water) {

        water.addWaterLog(water.getLastDrink());
        waterRepository.save(water);
    }

    public ArrayList<Integer> getDrinkLogWithDate(Water water) {

        return waterRepository.findWaterByUidAndAndCid(water.getUid(), water.getCid()).getWaterDrinkData();
    }

    public Water getWaterDrinkPerData(Water water) {

        return waterRepository.findById(water.getWid()).orElse(new Water());
    }
}

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

    //TODO : 튜플형태로 반환하자, 날짜를 포함해서

    public Water getWaterDrinkPerData(Water water) {

        //return waterRepository.findById(water.getWid()).orElse(new Water());
    }
    //TODO : 워터 객체 그 자체를 반환하자

    //TODO : 물 자체의 데이터를 저장하지 말고 사용자의 지금 시간대 음수량을 기록하자
    //TODO : 블루투스 페어링 시간을 기록하는 엔티티와 사용자 파라메터를 생성하자
    //TODO: 일정 시간대 마다 서버에서 알림 시간을 결정하고 현재 음수량을 반환하는 메소드를 생성하자
}

package com.capstonedesign.backend.service;

import com.capstonedesign.backend.domain.Cup;
import com.capstonedesign.backend.repository.CupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CupService {

    private final CupRepository cupRepository;

    @Autowired
    public CupService(CupRepository cupRepository) {
        this.cupRepository = cupRepository;
    }

    public void saveCupInfo(Cup cup) {

        cupRepository.save(cup);
    }

    public Cup getCupInfo(Long cid) {

        return cupRepository.findById(cid).orElse(new Cup());
    }

    public void deleteCup(Cup cup) {

        cupRepository.deleteById(cup.getCid());
    }
}

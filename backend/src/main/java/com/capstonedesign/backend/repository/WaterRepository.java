package com.capstonedesign.backend.repository;

import com.capstonedesign.backend.domain.Water;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterRepository extends CrudRepository<Water, Long> {
}


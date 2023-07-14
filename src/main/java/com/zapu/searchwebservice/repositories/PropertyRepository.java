package com.zapu.searchwebservice.repositories;

import com.zapu.searchwebservice.entities.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    Page<Property> findByCategoryIdAndCityIdIn(int category, List<Integer> cityIds, Pageable pageable);

    Page<Property> findByCategoryId(int category, Pageable pageable);
}

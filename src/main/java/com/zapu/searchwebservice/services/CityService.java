package com.zapu.searchwebservice.services;

import com.zapu.searchwebservice.entities.City;
import com.zapu.searchwebservice.exceptions.ResourceNotFoundException;
import com.zapu.searchwebservice.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findById(Integer id) {
        Optional<City> city = cityRepository.findById(id);
        return city.orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id + " !"));
    }

    public City getCityByName(String name) {
        return cityRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with name: " + name));
    }

}

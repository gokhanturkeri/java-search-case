package com.zapu.searchwebservice.services;

import com.zapu.searchwebservice.dtos.PropertyDto;
import com.zapu.searchwebservice.entities.Category;
import com.zapu.searchwebservice.entities.City;
import com.zapu.searchwebservice.entities.Property;
import com.zapu.searchwebservice.exceptions.ResourceNotFoundException;
import com.zapu.searchwebservice.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final CategoryService categoryService;
    private final CityService cityService;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, CategoryService categoryService, CityService cityService) {
        this.propertyRepository = propertyRepository;
        this.categoryService = categoryService;
        this.cityService = cityService;
    }

    public void save(PropertyDto propertyDto) {
        Category category = categoryService.findById(propertyDto.getCategory());
        City city = cityService.findById(propertyDto.getCity());

        if (category == null) {
            throw new IllegalArgumentException("Invalid category id !");
        }

        if (city == null) {
            throw new IllegalArgumentException("Invalid city id !");
        }

        Property property = new Property();
        property.setCategory(category);
        property.setTitle(propertyDto.getTitle());
        property.setCity(city);
        property.setPrice(propertyDto.getPrice());
        property.setCurrency(propertyDto.getCurrency());

        propertyRepository.save(property);
    }

    public void update(Integer id, PropertyDto propertyDto) {
        Optional<Property> optionalProperty = propertyRepository.findById(id);

        if (optionalProperty.isPresent()) {
            Property property = optionalProperty.get();

            Category category = categoryService.findById(propertyDto.getCategory());
            City city = cityService.findById(propertyDto.getCity());

            if (category == null) {
                throw new IllegalArgumentException("Invalid category id !");
            }

            if (city == null) {
                throw new IllegalArgumentException("Invalid city id !");
            }

            property.setCategory(category);
            property.setTitle(propertyDto.getTitle());
            property.setCity(city);
            property.setPrice(propertyDto.getPrice());
            property.setCurrency(propertyDto.getCurrency());

            propertyRepository.save(property);
        } else {
            throw new ResourceNotFoundException("Property not found with id: " + id + " !");
        }
    }
}

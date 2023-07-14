package com.zapu.searchwebservice.controllers;

import com.zapu.searchwebservice.dtos.PropertyDto;
import com.zapu.searchwebservice.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/property")
    public String saveProperty(@RequestBody PropertyDto propertyDto) {
        try {
            propertyService.save(propertyDto);
            return "Property saved.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/property/{id}")
    public String updateProperty(@PathVariable Integer id, @RequestBody PropertyDto propertyDto) {
        try {
            propertyService.update(id, propertyDto);
            return "Property updated.";
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}

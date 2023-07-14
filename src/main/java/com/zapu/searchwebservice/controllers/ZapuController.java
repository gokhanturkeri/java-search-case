package com.zapu.searchwebservice.controllers;

import com.zapu.searchwebservice.entities.Property;
import com.zapu.searchwebservice.responses.SearchResult;
import com.zapu.searchwebservice.services.ZapuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping
public class ZapuController {

    private final ZapuService zapuService;

    @Autowired
    public ZapuController(ZapuService zapuService) {
        this.zapuService = zapuService;
    }


    @GetMapping("/detay/{id}")
    public Property getPropertyById(@PathVariable Integer id) {
        try {
            return zapuService.findPropertyById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/arama")
    public ModelAndView searchProperties(
            @RequestParam("category") Integer categoryId,
            @RequestParam(value = "city", required = false) List<Integer> cityIds,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        return zapuService.redirectUserFriendlyUrlAndGetSearchResults(categoryId, cityIds, page, pageSize);
    }

    @GetMapping("/{category}/**")
    @ResponseBody
    public SearchResult<Property> displayResults(
            @PathVariable("category") String category,
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {

        return zapuService.getSearchResults(category, request, page, pageSize);
    }
}

package com.zapu.searchwebservice.services;

import com.zapu.searchwebservice.entities.Category;
import com.zapu.searchwebservice.entities.City;
import com.zapu.searchwebservice.entities.Property;
import com.zapu.searchwebservice.exceptions.ResourceNotFoundException;
import com.zapu.searchwebservice.repositories.PropertyRepository;
import com.zapu.searchwebservice.responses.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ZapuService {

    private final PropertyRepository propertyRepository;
    private final CategoryService categoryService;
    private final CityService cityService;

    @Autowired
    public ZapuService(PropertyRepository propertyRepository, CategoryService categoryService, CityService cityService) {
        this.propertyRepository = propertyRepository;
        this.categoryService = categoryService;
        this.cityService = cityService;
    }

    public Property findPropertyById(Integer id) {
        Optional<Property> property = propertyRepository.findById(id);
        return property.orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id + " !"));
    }

    public ModelAndView redirectUserFriendlyUrlAndGetSearchResults(Integer categoryId, List<Integer> cityIds, Integer page, Integer pageSize) {
        String category = getCategoryName(categoryId);

        if (cityIds == null || cityIds.isEmpty()) {
            String redirectUrl = "/" + category;
            return new ModelAndView(new RedirectView(redirectUrl, true, true));
        }

        StringBuilder redirectUrlBuilder = new StringBuilder("/" + category);
        for (Integer cityId : cityIds) {
            String cityName = getCityName(cityId);
            redirectUrlBuilder.append("/").append(cityName);
        }

        redirectUrlBuilder.append("?page=").append(page);
        redirectUrlBuilder.append("&pageSize=").append(pageSize);
        String redirectUrl = redirectUrlBuilder.toString();

        return new ModelAndView(new RedirectView(redirectUrl, true, true));
    }

    public SearchResult<Property> getSearchResults(String category, HttpServletRequest request, Integer page, Integer pageSize) {
        Integer categoryId = getCategoryId(category);
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchingPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        String finalPath = apm.extractPathWithinPattern(bestMatchingPattern, path);

        List<Integer> cityIds = null;
        if (!finalPath.isEmpty()) {
            List<String> cityNames = Arrays.asList(finalPath.split("/"));
            cityIds = cityNames.stream().map(this::getCityId).collect(Collectors.toList());
        }

        SearchResult<Property> searchResult = searchProperties(categoryId, cityIds, page, pageSize);
        searchResult.setRootUrl(buildRootUrl(category, cityIds));
        return searchResult;
    }

    private SearchResult<Property> searchProperties(int category, List<Integer> cityIds, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Property> propertyPage;
        if (cityIds == null || cityIds.isEmpty()) {
            propertyPage = propertyRepository.findByCategoryId(category, pageable);
        } else {
            propertyPage = propertyRepository.findByCategoryIdAndCityIdIn(category, cityIds, pageable);
        }

        List<Property> properties = propertyPage.getContent();
        int totalCount = (int) propertyPage.getTotalElements();
        int totalPages = propertyPage.getTotalPages();

        return new SearchResult<>(page, pageSize, totalPages, totalCount, properties);
    }

    private String buildRootUrl(String category, List<Integer> cityIds) {
        StringBuilder rootUrl = new StringBuilder(category);

        if (cityIds != null && !cityIds.isEmpty()) {
            rootUrl.append("/");
            for (Integer cityId : cityIds) {
                String cityName = getCityName(cityId);
                rootUrl.append(cityName).append("/");
            }
            rootUrl.deleteCharAt(rootUrl.length() - 1);
        }

        return rootUrl.toString();
    }
    
    private String getCategoryName(Integer categoryId) {
        switch (categoryId) {
            case 1:
                return "konut";
            case 2:
                return "ticari";
            case 3:
                return "arsa";
            default:
                throw new IllegalArgumentException("Invalid category ID");
        }
    }

    private String getCityName(Integer cityId) {
        // Map the city ID to the corresponding city name based on your specific city IDs
        switch (cityId) {
            case 1:
                return "Istanbul";
            case 2:
                return "Ankara";
            case 3:
                return "Izmir";
            case 4:
                return "Bursa";
            case 5:
                return "Antalya";
            default:
                throw new IllegalArgumentException("Invalid city ID");
        }
    }

    public Integer getCategoryId(String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        return category.getId();
    }

    public Integer getCityId(String cityName) {
        City city = cityService.getCityByName(cityName);
        return city.getId();
    }
}

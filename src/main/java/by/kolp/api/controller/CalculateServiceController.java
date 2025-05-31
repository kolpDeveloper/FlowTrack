package by.kolp.api.controller;

import by.kolp.api.model.dto.NumericDataEntryDTO;
import by.kolp.api.service.CalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user/calculate")

public class CalculateServiceController {

    private final CalculateService calculateService;

    @Autowired
    public CalculateServiceController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }



    public String calculateAll(NumericDataEntryDTO data) {

        Integer result = calculateService.sumAllValues(data);
        return "redirect:/results";
    }

}
package by.kolp.api.controller;

import by.kolp.api.model.dto.NumericDataEntryDTO;
import by.kolp.api.service.CalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/api/user/calculate")
public class CalculateServiceController {

    @Autowired
    private CalculateService calculateService;


    public String showForm(Model model, String key, Integer value) {
        model.addAttribute("numericDataEntry", new NumericDataEntryDTO(key , value, Instant.now()));

        //doubtful

        //TODO test form make edits
        return "calculation";

    }


    @PostMapping("/api/user/calculate")
    public String calculateAll(@ModelAttribute("numericDataEntry") NumericDataEntryDTO data, Model model ) {

        Integer result = calculateService.sumAllValues(data);
        model.addAttribute("result", result);
        return "redirect:/results";
    }

}
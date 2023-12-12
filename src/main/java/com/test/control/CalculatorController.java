package com.test.control;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.service.CalculatorService;
@Controller
public class CalculatorController {
	
	@Autowired
    private CalculatorService calculatorService;

    @RequestMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam int num1, @RequestParam int num2, Model model) {
        int result = calculatorService.calculateSum(num1, num2);
        model.addAttribute("result", "Result: " + result);
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        return "index";
    }


    @GetMapping("/download-excel")
    public ResponseEntity<InputStreamResource> downloadExcel(@RequestParam int num1, @RequestParam int num2) throws IOException {
        InputStream excelInputStream = calculatorService.generateExcel(num1, num2);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=calculation_result.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(excelInputStream));
    }

}

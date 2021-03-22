package uz.pdp.RestFullApiOne.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.RestFullApiOne.Entity.Company;
import uz.pdp.RestFullApiOne.payload.ApiResponse;
import uz.pdp.RestFullApiOne.payload.CompanyDto;
import uz.pdp.RestFullApiOne.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        List<Company> company = companyService.getCompany();
        return ResponseEntity.ok(company);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> get(@PathVariable Integer id) {
        Company company = companyService.getOneCompany(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CompanyDto companyDto) {
        ApiResponse company = companyService.createCompany(companyDto);
        return ResponseEntity.status(company.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id, @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

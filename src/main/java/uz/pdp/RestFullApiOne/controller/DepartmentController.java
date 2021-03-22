package uz.pdp.RestFullApiOne.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.RestFullApiOne.Entity.Department;
import uz.pdp.RestFullApiOne.payload.ApiResponse;
import uz.pdp.RestFullApiOne.payload.DepartmentDto;
import uz.pdp.RestFullApiOne.service.DepartmentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody DepartmentDto departmentDto) {
        ApiResponse department = departmentService.createDepartment(departmentDto);
        return ResponseEntity.status(department.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(department);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        List<Department> departmentList = departmentService.getAllDep();
        return ResponseEntity.ok(departmentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> get(@PathVariable Integer id) {
        Department department = departmentService.getDep(id);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id, @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
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

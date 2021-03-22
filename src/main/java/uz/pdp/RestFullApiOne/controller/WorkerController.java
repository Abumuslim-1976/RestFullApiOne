package uz.pdp.RestFullApiOne.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.RestFullApiOne.Entity.Worker;
import uz.pdp.RestFullApiOne.payload.ApiResponse;
import uz.pdp.RestFullApiOne.payload.WorkerDto;
import uz.pdp.RestFullApiOne.service.WorkerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.createWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<List<Worker>> getAll() {
        List<Worker> allWorker = workerService.getAllWorker();
        return ResponseEntity.ok(allWorker);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> get(@PathVariable Integer id) {
        Worker worker = workerService.getWorker(id);
        return ResponseEntity.ok(worker);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,@RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.editWorker(id, workerDto);
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

package uz.pdp.RestFullApiOne.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.RestFullApiOne.Entity.Address;
import uz.pdp.RestFullApiOne.payload.AddressDto;
import uz.pdp.RestFullApiOne.payload.ApiResponse;
import uz.pdp.RestFullApiOne.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody AddressDto addressDto) {
        ApiResponse address = addressService.createAddress(addressDto);
        return ResponseEntity.status(address.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(address);
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        List<Address> address = addressService.getAddress();
        return ResponseEntity.ok(address);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> get(@PathVariable Integer id) {
        Address address = addressService.getAdd(id);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@Valid @PathVariable Integer id) {
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,@Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.editAddress(id, addressDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
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

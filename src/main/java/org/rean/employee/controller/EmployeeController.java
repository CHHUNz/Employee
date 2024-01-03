package org.rean.employee.controller;

import lombok.RequiredArgsConstructor;
import org.rean.employee.Exception.NotFoundException;
import org.rean.employee.model.Employee;
import org.rean.employee.model.request.EmployeeRequest;
import org.rean.employee.model.response.ApiResponse;
import org.rean.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    @PostMapping("")
    public ApiResponse<?> createOrUpdate(@RequestBody Employee employee){
        if (employee == null || employee.getFirstName() == null || employee.getLastName() == null || employee.getEmail() == null){
            throw new IllegalArgumentException("Employee can not be blank");
        }
        employeeService.createOrUpdate(employee);
        return ApiResponse.<Employee>builder()
                .message("create or update successfully")
                .status(HttpStatus.OK)
                .payload(employee)
                .build();
    }

    @GetMapping("")
    public ResponseEntity<?> getAllEmployee(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        return ResponseEntity.ok().body(employeeService.getAllEmployee(pageNo, pageSize));
    }
    @GetMapping("/{id}")
    public ApiResponse<?> getEmployeeById(@PathVariable("id") Long id){
        var findUser = employeeService.getEmployeeById(id);
        if (findUser != null){
            return ApiResponse.<Employee>builder()
                    .message("Find Employee successfully")
                    .payload(findUser)
                    .status(HttpStatus.OK)
                    .build();
        } else {
            throw new NotFoundException("there is no data for this id"+id);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        ApiResponse<Employee> response = ApiResponse.<Employee>builder()
                .message("deleted successfully")
                .status(HttpStatus.OK)
                .payload(null)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateEmployeeById(@RequestBody EmployeeRequest employeeRequest, @PathVariable("id") Long id){
        if (employeeRequest == null || employeeRequest.getLastName() == null || employeeRequest.getFirstName() == null){
            throw new IllegalArgumentException("Employee information can not be blank");
        }
        var payload = employeeService.updateEmployeeById(employeeRequest, id);
        return ApiResponse.<Employee>builder()
                .message("update successfully")
                .status(HttpStatus.OK)
                .payload(payload)
                .build();
    }
}

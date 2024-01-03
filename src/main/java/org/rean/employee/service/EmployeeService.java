package org.rean.employee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rean.employee.Exception.NotFoundException;
import org.rean.employee.model.Employee;
import org.rean.employee.model.request.EmployeeRequest;
import org.rean.employee.model.response.PageResponse;
import org.rean.employee.repository.EmployeeRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {
    public final EmployeeRepo employeeRepo;
    public Employee createOrUpdate(Employee employee){
        if(employee.getId() == null){
            employeeRepo.save(employee);
            return employee;
        }
        Optional<Employee> optionalEmployee = employeeRepo.findById(employee.getId());
        if (optionalEmployee.isPresent()){
            Employee employeeInstance = optionalEmployee.get();
            employeeInstance.setFirstName(employee.getFirstName());
            employeeInstance.setLastName(employee.getLastName());
            employeeInstance.setEmail(employee.getEmail());
            employeeRepo.save(employeeInstance);
            return employeeInstance;

        }

        employeeRepo.save(employee);
        return employee;
    }

    public PageResponse<List<Employee>> getAllEmployee(Integer pageNo, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Employee> pageResult = employeeRepo.findAll(pageable);
        return PageResponse.<List<Employee>>builder()
                .message("get all employee successfully")
                .payload(pageResult.getContent())
                .status(HttpStatus.OK)
                .page(pageNo)
                .size(pageSize)
                .totalElement((int) pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }

    public Employee getEmployeeById (Long id){
        return employeeRepo.findById(id).get();
    }

    public void deleteEmployeeById(Long id){
        Employee employee_ = employeeRepo.findById(id).orElseThrow(()-> new NotFoundException("Id not found"));
        employeeRepo.deleteById(employee_.getId());
    }

    public Employee updateEmployeeById(EmployeeRequest employeeRequest, Long id){
        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepo.findById(id).orElseThrow(()-> new NotFoundException("There is no data for this id")));
        if (optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            // Update the user object with the new values from userRequest
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setEmail(employeeRequest.getEmail());
            return employeeRepo.save(employee);
        } else {
            throw new NotFoundException("Employee not found with id");
        }
    }
}

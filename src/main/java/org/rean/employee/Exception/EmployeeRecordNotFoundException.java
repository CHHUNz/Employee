package org.rean.employee.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeRecordNotFoundException extends Exception{
    public EmployeeRecordNotFoundException(String msg){
        super(msg);
    }
}

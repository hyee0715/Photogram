package com.hy.photogram.handler;

import com.hy.photogram.handler.ex.CustomValidationException;
import com.hy.photogram.util.Script;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) //지정한 예외 발생 시 해당 메소드에서 이를 처리
    public String validationException(CustomValidationException e) {
        return Script.back(e.getErrorMap().toString());
    }
}

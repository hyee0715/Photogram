package com.hy.photogram.handler;

import com.hy.photogram.handler.ex.CustomApiException;
import com.hy.photogram.handler.ex.CustomException;
import com.hy.photogram.handler.ex.CustomValidationApiException;
import com.hy.photogram.handler.ex.CustomValidationException;
import com.hy.photogram.util.Script;
import com.hy.photogram.web.dto.auth.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    /* 유효성 검사 예외 */
    @ExceptionHandler(CustomValidationException.class) //지정한 예외 발생 시 해당 메소드에서 이를 처리
    public String validationException(CustomValidationException e) {
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString());
        }
    }

    /* 유효성 검사 예외(api) */
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
        return Script.back(e.getMessage());
    }
}

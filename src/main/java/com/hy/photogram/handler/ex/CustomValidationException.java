package com.hy.photogram.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException {

    //객체 구분에 사용
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}

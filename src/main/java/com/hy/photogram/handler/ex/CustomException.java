package com.hy.photogram.handler.ex;

public class CustomException extends RuntimeException {

    //객체 구분에 사용
    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }
}

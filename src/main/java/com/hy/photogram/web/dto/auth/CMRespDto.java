package com.hy.photogram.web.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> {

    private int code;   //1: 성공, -1: 실패
    private String message;
    private T data;
}

package com.hy.photogram.web.dto.auth;

import com.hy.photogram.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupDto {

    @Size(min = 2, max = 20)    //최소 길이 2, 최대 길이 20
    @NotBlank   //반드시 입력해야 함
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}

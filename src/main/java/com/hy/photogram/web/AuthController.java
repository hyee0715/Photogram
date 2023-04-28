package com.hy.photogram.web;

import com.hy.photogram.domain.user.User;
import com.hy.photogram.handler.ex.CustomValidationException;
import com.hy.photogram.service.AuthService;
import com.hy.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller     //IoC, 파일을 리턴하는 Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {     //검사 결과 유효하지 않은 데이터가 발견된 경우 실행
        if (bindingResult.hasErrors()) {

            //오류 메시지를 저장하기 위함
            Map<String, String> errorMap = new HashMap<>();

            //발견한 오류의 개수에 따라 반복문을 돌며 오류 메시지 저장
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            //간단한 메시지와 함께, 위에서 저장한 오류 메시지를 예외 처리 클래스에게 넘겨줌
            throw new CustomValidationException("유효성 검사에 실패하였습니다.", errorMap);
        }

        //SignupDto -> User
        User user = signupDto.toEntity();
        authService.join(user);

        return "auth/signin";
    }
}

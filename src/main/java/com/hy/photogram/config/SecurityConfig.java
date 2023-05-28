package com.hy.photogram.config;

//import com.hy.photogram.config.oauth.OAuth2DetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@RequiredArgsConstructor
@EnableWebSecurity //Security 활성화
@Configuration //IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final OAuth2DetailsService oAuth2DetailsService;

    /* 비밀번호 인코더 */
    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    //기존 Security의 기능을 모두 비활성화 시키고 직접 지정

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() //csrf 토큰 비활성화
            .authorizeRequests()
                .antMatchers("/", "/user/**", "image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated()   // 해당 경로로 접근하기 위해서는 권한이 필요함
                .anyRequest().permitAll()   //이외의 경로에 대해서는 모두 허용
            .and()
            .formLogin()
                .loginPage("/auth/signin")  //로그인 페이지 경로(GET)
                .loginProcessingUrl("/auth/signin") //로그인 요청 경로(POST) - 해당 경로로 요청이 올 경우 Spring Security가 로그인 진행
               .defaultSuccessUrl("/")    //로그인에 성공할 시 요청되는 경로
//            .and()
//                .oauth2Login()
//                .userInfoEndpoint()     // OAuth2 로그인 시 최종응답으로 회원정보를 바로 받을 수 있음
//                .userService(oAuth2DetailsService)
        ;
    }
}

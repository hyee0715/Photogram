//package com.hy.photogram.config.oauth;
//
//import com.hy.photogram.config.auth.PrincipalDetails;
//import com.hy.photogram.domain.user.User;
//import com.hy.photogram.domain.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Service
//public class OAuth2DetailsService extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
//
//    /* 페이스북 로그인 */
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oauth2User = super.loadUser(userRequest);
//        Map<String, Object> userInfo = oauth2User.getAttributes();
//
//        String username = "facebook_" + (String) userInfo.get("id");
//        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
//        String name = (String) userInfo.get("name");
//        String email = (String) userInfo.get("email");
//
//        User userEntity = userRepository.findByUsername(username);
//        if (userEntity == null) { // 페이스북 최초 로그인 시 발생
//
//            User user = User.builder()
//                    .username(username)
//                    .password(password)
//                    .name(name)
//                    .email(email)
//                    .role("ROLE_USER")
//                    .build();
//
//            return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes());
//        }
//
//        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
//    }
//}

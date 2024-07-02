package com.sal.saltbackend.security;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // OAuth2 클라이언트 정보를 관리하는 서비스입니다.
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    // application.properties 파일에서 jwt.secret 값을 읽어옵니다.
    @Value("${jwt.secret}")
    private String jwtSecret;

    // OAuth2UserService는 OAuth2 사용자 정보를 가져오는 서비스입니다.
    private final OAuth2UserService oAuth2UserService;

    // 생성자를 통해 oAuth2UserService를 주입받습니다.
    public SecurityConfig(OAuth2UserService oAuth2UserService) {
        this.oAuth2UserService = oAuth2UserService;
    }

    // SecurityFilterChain 빈을 생성하여 HTTP 보안 구성을 정의합니다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 모든 요청을 허용하도록 설정합니다.
                .authorizeRequests(config -> config
                        .anyRequest().permitAll())
                // OAuth2 로그인 설정을 정의합니다.
                .oauth2Login(oauth2Configurer -> oauth2Configurer
                        // 로그인 페이지 URL을 설정합니다.
                        .loginPage("/kakao/signin")
                        // 로그인 성공 시 호출할 핸들러를 설정합니다.
                        .successHandler(successHandler())
                        // 사용자 정보를 가져오는 엔드포인트를 설정합니다.
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                )
                // CSRF 보호를 비활성화합니다.
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
    // 인증 성공 시 실행될 AuthenticationSuccessHandler를 정의합니다.
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                  //이 부분이 카카오에서 받은 토큰을 가져오는 코드임.
//                // OAuth2AuthenticationToken을 가져옵니다.
//                OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
//                // OAuth2AuthorizedClient를 사용하여 액세스 토큰을 가져옵니다.
//                OAuth2AuthorizedClient authorizedClient =
//                        authorizedClientService.loadAuthorizedClient(
//                                oauth2AuthenticationToken.getAuthorizedClientRegistrationId(),
//                                oauth2AuthenticationToken.getName());

                // 인증된 사용자 정보를 가져옵니다.
                DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

                // 사용자 정보를 추출합니다.
                String id = defaultOAuth2User.getAttribute("id").toString();
                Map<String, Object> profileInfo = defaultOAuth2User.getAttribute("kakao_account");
                String nickname = (String) ((Map<String, Object>) profileInfo.get("profile")).get("nickname");
                String email = (String) profileInfo.get("email");

                String jwtToken = generateToken(id, email, nickname);

                // JWT 토큰을 응답에 추가
                response.setHeader("Authorization", "Bearer " + jwtToken);

                // 로그인 성공 후 /login/success로 리디렉션
                response.sendRedirect("/login/success");
            }
            // JWT 토큰을 생성하는 메서드입니다.
            private String generateToken(String id, String email, String nickname) {
                // JWT 서명에 사용할 키를 생성합니다.
                byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
                Key key = Keys.hmacShaKeyFor(keyBytes);

                // JWT 토큰을 생성하고 반환합니다.
                return Jwts.builder()
                        .setSubject(id)
                        .claim("email", email)
                        .claim("nickname", nickname)
                        .signWith(key)
                        .compact();
            }
        };
    }

}


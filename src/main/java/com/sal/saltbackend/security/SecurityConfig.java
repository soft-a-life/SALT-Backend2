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
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;


    private final OAuth2UserService oAuth2UserService;
    @Value("${jwt.secret}")
    private  String jwtSecret;

    public SecurityConfig(OAuth2UserService oAuth2UserService, JwtConfig jwtConfig) {
        this.oAuth2UserService = oAuth2UserService;
    }

    //////////////
    // OAuth2UserService 설정
    @Bean
    public OAuth2UserService oAuth2UserService() {
        return new DefaultOAuth2UserService();
    }
/////////////////////////

    //HTTP 보안 필터 체인을 구성
//    HttpSecurity 객체는 HTTP 요청 및 응답에 대한 보안 구성을 제공
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //요청에 대한 인가 구성을 설정 -> anyRequest().permitAll()은 모든 요청을 허용
                .authorizeRequests(config -> config.anyRequest().permitAll())
                .oauth2Login(oauth2Configurer -> oauth2Configurer
//                        loginPage("/login"): 사용자를 로그인 페이지(/login)로 리다이렉트합니다.
                        .loginPage("/kakao/signin")
//                        로그인 성공 시 호출할 핸들러를 설정합니다. 이 핸들러는 사용자의 로그인 정보를 처리하고, 이후의 동작을 결정합니다.
//                        .successHandler(successHandler())
                        .successHandler(successHandler())
//                        OAuth2User 정보를 가져오는 엔드포인트 설정입니다. userService(oAuth2UserService)는 사용자 정보를 가져오는 데 사용할 OAuth2UserService를 설정합니다.
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                )
//        CSRF 토큰 리포지토리를 설정합니다. CSRF 토큰은 웹 애플리케이션의 폼 기반 요청에서 사용되며, 이를 통해 악의적인 사이트로부터의 요청을 방지합니다.
                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository()));

//        설정한 HttpSecurity를 반환합니다.
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
                ///////////
                OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                OAuth2AuthorizedClient authorizedClient =
                        authorizedClientService.loadAuthorizedClient(
                                oauth2AuthenticationToken.getAuthorizedClientRegistrationId(),
                                oauth2AuthenticationToken.getName());


                ////////////
                DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

                String id = defaultOAuth2User.getAttribute("id").toString();
                Map<String, Object> profileInfo = defaultOAuth2User.getAttribute("kakao_account");
                String nickname = (String) ((Map<String, Object>) profileInfo.get("profile")).get("nickname");
                String email = (String) profileInfo.get("email");


                // 카카오에서받은 accesstoken을 통해 카카오 api 요청
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
//                headers.setBearerAuth((String) ((OAuth2AccessToken) authentication.getCredentials()).getTokenValue());
                headers.setBearerAuth((String) authorizedClient.getAccessToken().getTokenValue());
                HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, requestEntity, String.class);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    String kakaoResponseBody = responseEntity.getBody();
                    System.out.println("Kakao API로부터 받은 사용자 정보: " + kakaoResponseBody);
                } else {
                    System.out.println("카카오 API로부터 사용자 정보를 가져오는 데 실패했습니다.");
                }



                String jwtToken = generateToken(id, email, nickname);

                response.setContentType("application/json");
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//                response.getWriter().write("{\"token\": \"" + jwtToken + "\"}")
                response.getWriter().write("{\"token\": \"" + jwtToken + "\", \"accessToken\": \"" + authorizedClient.getAccessToken().getTokenValue() + "\"}");
            }
//aa
            private String generateToken(String id, String email, String nickname) {
                long expirationTime = 3600000; // 토큰 만료 시간(1시간)

                // 보안 키 생성
                byte[] keyBytes = jwtSecret.getBytes();
                // 256비트 이상의 키 크기로 보안 키 생성
                Key key = Keys.hmacShaKeyFor(keyBytes);

                return Jwts.builder()
                        .setSubject(id)
                        .claim("email", email)
                        .claim("nickname", nickname)
//                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                        .signWith(Keys.hmacShaKeyFor(keyBytes))
                        .compact();
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}


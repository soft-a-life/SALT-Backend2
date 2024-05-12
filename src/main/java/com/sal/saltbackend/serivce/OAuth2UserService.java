package com.sal.saltbackend.serivce;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

//스프링 시큐리티의 DefaultOAuth2UserService를 확장하여 사용자 정보를 가져오고 처리하는 역할
//OAuth2 클라이언트의 사용자 정보를 가져오기 위한 기본 서비스 클래스 DefaultOAuth2UserService
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

//    OAuth2 클라이언트로부터 사용자 정보를 가져오는 역할
//    OAuth2UserRequest: OAuth2 인증에 필요한 정보를 담고 있는 객체입니다. 이 객체를 통해 클라이언트 등록 정보, 인증 서버 URL 등을 알 수 있습니다.
//    OAuth2User: 스프링 시큐리티가 인증 후에 생성한 사용자 정보 객체입니다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
//      부모 클래스인 DefaultOAuth2UserService의 loadUser() 메서드를 호출하여 OAuth2 클라이언트로부터 사용자 정보를 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

//        권한 목록을 생성합니다. 여기서는 단순히 "ROLE_ADMIN" 권한을 부여하고 있습니다.
        // Role generate
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        //커밋
//        사용자의 고유 식별자를 나타내는 속성을 가져옵니다. 예를 들어, Kakao의 경우 사용자의 고유 식별자는 id입니다.
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        for (Map.Entry<String,Object> entry: attributes.entrySet()){
            System.out.println("userInfo : "  + entry.getKey()+  " " + entry.getValue());
        }

        // DB 저장로직이 필요하면 추가
        System.out.println("oAuth2User : " + oAuth2User);
        System.out.println("authorities : " +  authorities);
        System.out.println("userNameAttributeName :" + userNameAttributeName);

//        스프링 시큐리티의 DefaultOAuth2User 객체를 생성합니다. 이 객체는 인증된 사용자를 나타냅니다. 생성자의 인자는 다음과 같습니다.
//        authorities: 사용자의 권한 목록입니다. 여기서는 "ROLE_ADMIN" 하나만 있습니다.
//        oAuth2User.getAttributes(): OAuth2 서비스 제공자로부터 받은 사용자의 속성 목록입니다. 예를 들어, Kakao의 경우에는 사용자의 프로필 정보가 여기에 포함될 수 있습니다.
//        userNameAttributeName: 사용자의 고유 식별자를 나타내는 속성의 이름입니다.
        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
    }
}

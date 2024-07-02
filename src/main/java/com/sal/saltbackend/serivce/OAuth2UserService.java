package com.sal.saltbackend.serivce;

import com.sal.saltbackend.entity.user;
import com.sal.saltbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

//스프링 시큐리티의 DefaultOAuth2UserService를 확장하여 사용자 정보를 가져오고 처리하는 역할
//OAuth2 클라이언트의 사용자 정보를 가져오기 위한 기본 서비스 클래스 DefaultOAuth2UserService
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

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

        //스프링 시큐리티의 DefaultOAuth2User 객체를 생성합니다. 이 객체는 인증된 사용자를 나타냅니다. 생성자의 인자는 다음과 같습니다.
        //authorities: 사용자의 권한 목록입니다. 여기서는 "ROLE_ADMIN" 하나만 있습니다.
        //oAuth2User.getAttributes(): OAuth2 서비스 제공자로부터 받은 사용자의 속성 목록입니다. 예를 들어, Kakao의 경우에는 사용자의 프로필 정보가 여기에 포함될 수 있습니다.
        //userNameAttributeName: 사용자의 고유 식별자를 나타내는 속성의 이름입니다.
        System.out.println("oAuth2User : " + oAuth2User);
        System.out.println("authorities : " +  authorities);
        System.out.println("userNameAttributeName :" + userNameAttributeName);

        // 사용자 정보를 추출
        String connectedAt = (String) attributes.get("connected_at");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = (String) properties.get("nickname");
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        // 새로운 사용자 객체 생성 및 데이터베이스에 저장
        // 이메일로 사용자 검색
        Optional<user> existingUser = userRepository.findByEmail(email);

        // 사용자가 존재하지 않는 경우에만 새로 저장
        if (!existingUser.isPresent()) {
            // 새로운 사용자 객체 생성 및 데이터베이스에 저장
            user user = new user();
            user.setEmail(email);
            user.setNickname(nickname);
            user.setConnectedAt(connectedAt);
            user.setUserUuid(null); // 초기화 시 UUID는 null
            user.setDeletedAt(null); // 초기화 시 deleted_at은 null

            userRepository.save(user);

            System.out.println("New user saved: " + email);
        } else {
            System.out.println("User already exists: " + email);
        }

        //동의 권한은 카카오에서 필수로 하기 때문에 굳이할 필요가 없다.
        //oAuth2User -> id=3480271522, connected_at=2024-05-13T09:53:32Z
        //이메일, 만들어진 시간, 닉네임 DB에 저장
        System.out.println("connectedAt : " + connectedAt);
        System.out.println("nickname : " +  nickname);
        System.out.println("email :" + email);

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
    }
}

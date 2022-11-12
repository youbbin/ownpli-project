package dbproject.ownpli.oauth;

import dbproject.ownpli.domain.user.UserProfile;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

/**
 * google, naver, kakao에서 주는 JSON 형식들이 모두 다르기 때문에 따로 관리해줍니다.
 */

public enum OauthAttributes {
    GOOGLE("google", (attributes) -> {
        UserProfile userProfile = new UserProfile();
        userProfile.setName((String) attributes.get("name"));
        userProfile.setUserId((String) attributes.get("userId"));
        return userProfile;
    }),

    NAVER("naver", (attributes) -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println(response);
        UserProfile userProfile = new UserProfile();
        userProfile.setName((String) response.get("name"));
        userProfile.setUserId(((String) response.get("userId")));
        return userProfile;
    }),

    KAKAO("kakao", (attributes) -> {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        UserProfile userProfile = new UserProfile();
        userProfile.setName((String) kakaoProfile.get("nickname"));
        userProfile.setUserId((String) attributes.get("userId"));
        return userProfile;
    });



    private final String registrationId;
    private final Function<Map<String, Object>, UserProfile> of;

    OauthAttributes(String registrationId, Function<Map<String, Object>, UserProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    /**
     * values()는 enum의 요소들을 순서대로 배열에 리턴.
     * 이를 stream으로 만들어주고 provider가 일치하는 경우에만 filter. (if문같은 기능)
     * 이후 .findFirst()로 하나를 찾아주는데 만약 일치하는게 없다면 orElseThow 메서드를 통해 IllegalArgumentException 발생.
     * 일치하는게 존재한다면 Function의 추상메서드인 apply를 호출하여 (google,naver,kakao) 형식에 맞춰 MemberProfile을 만들어서 반환.
     * @param registrationId
     * @param attributes
     * @return
     */

    public static UserProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
            .filter(provider -> registrationId.equals(provider.registrationId))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new)
            .of.apply(attributes);
    }
}

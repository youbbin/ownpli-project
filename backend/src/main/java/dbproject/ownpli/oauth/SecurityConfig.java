package dbproject.ownpli.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity //웹보안 활성화를위한 annotation
@RequiredArgsConstructor //final 필드 생성자 만들어줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OauthService oAuthService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()//csrf 공격을 막아주는 옵션을 disalbe, rest api같은 경우에는 브라우저를 통해 request 받지 않기 때문에 해당 옵션을 꺼도 됩니다.
            .headers().frameOptions().disable()
            .and()
            .logout().logoutSuccessUrl("/") //logout 요청시 홈으로 이동 - 기본 logout url = "/logout"
            .and()
            .oauth2Login() //OAuth2 로그인 설정 시작점
            .defaultSuccessUrl("/oauth/loginInfo", true) //OAuth2 성공시 redirect
            .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
            .userService(oAuthService); //OAuth2 로그인 성공 시, 작업을 진행할 MemberService
    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests()
//            .anyRequest().permitAll()
////			  .antMatchers("/**").authenticated() // 인가된 사용자만 접근 가능하도록 설정
////			  .antMatchers("게시물등").hasRole(Role.USER.name()) // 특정 ROLE을 가진 사용자만 접근 가능하도록 설정
//            .and()
//            .logout()
//            .logoutSuccessUrl("/")
//            .and()
//            .oauth2Login()
//            .userInfoEndpoint()
//            .userService(customOAuth2UserService);
//
//        return http.build();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests() // 요청에 의한 보안검사 시작
//            .anyRequest().authenticated() //어떤 요청에도 보안검사를 한다.
//            .and()
//            .formLogin();//보안 검증은 formLogin방식으로 하겠다.
//    }
}

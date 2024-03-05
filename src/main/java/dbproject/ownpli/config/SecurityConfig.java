package dbproject.ownpli.config;

import dbproject.ownpli.domain.value.Role;
import dbproject.ownpli.jwt.CustomAuthenticationEntryPoint;
import dbproject.ownpli.jwt.JwtAuthenticationFilter;
import dbproject.ownpli.jwt.JwtProvider;
import dbproject.ownpli.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilter(corsFilter)

                .authorizeRequests((authz) -> authz
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/home/age", "/api/mypage/**")).hasRole(Role.USER.name())
                        .anyRequest().authenticated()
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(customAuthenticationEntryPoint)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtProvider, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

}

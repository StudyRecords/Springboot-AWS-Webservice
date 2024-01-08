package example.org.springboot.config.auth;

import example.org.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity          //Spring Security 설정들을 활성화시켜줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()         //h2 화면을 보기 위한 설정
            .and()
                //url별 권한 관리를 설정 시작
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()       //위에서 설정한 url 이외의 나머지 url들 모두 해당 -> 인증된 사용자들에게만 허용
            .and()
                //로그아웃에 대한 설정 시작
                .logout()
                .logoutSuccessUrl("/")      //로그아웃 성공시 /로 이동
            .and()
                //로그인 기능에 대한 설정 시작
                .oauth2Login()
                .userInfoEndpoint()     //로그인 성공시 가져올 정보들 설정
                .userService(customOAuth2UserService);      //customOAuth2UserService : 구글에서 가져온 정보들로 후속 조치 진행하는 클래스 (UserService 구현체)
    }
}

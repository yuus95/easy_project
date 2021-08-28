package com.yushin.config;

import com.yushin.jwt.JwtAccessDeniedHandler;
import com.yushin.jwt.JwtAuthenticationEntryPoint;
import com.yushin.jwt.TokenProvider;
import com.yushin.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

import static org.hibernate.criterion.Restrictions.and;

/**
 * Spring Security Fliter Chain을 사용한다는것을 명시해주기 위해
 * @EnableWebSecurity 어노테이션 적용
 *
 */
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티를 활성화하는 어노테이션
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    /**
     * DB에 패스워드를 모두 인코딩된 상태로 저장하기위해 사용
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    이렇게 사용해도됨.
//    @Bean
//    public BCryptPasswordEncoder encoder(){
//        return new BCryptPasswordEncoder();
//    }

    // h2 database 테스트가 원활하도록 관련 API 들은 전부 무시
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/h2-console/**", "/favicon.ico");
    }

    /**
     * super()을 삭제하면 기존 시큐리티가 가지고 있는 기능이 다 비활성화
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        // 로긴form은 csrf토큰을 활성화 시킨다.
        // csrf토큰을 비활성화 시키면 postman으로 데이터를 넣든 그냥 데이터를 넣든 상관없다.
        http.csrf().disable()

                /**AuthenticationEntryPoint
                 * 인증과정에서 실패하거나 인증헤더를 보내지 않게 되는 경우 401응답값을 보내도록 처리해주는 인터페이스 -
                 */

                .formLogin()
                .disable()

                // exception handling 할 때 우리가 만든 클래스를 추가
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // h2-console 을 위한 설정을 추가
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .and()
                // authorizeRequest() 인증절차에 대한 설정을 진행
                .authorizeRequests()
                // antMatchers()  : 특정 URL에 대해서 어떻게 인증처리를 할지 결정
                .antMatchers("/auth/**","/login/**","/oauth2/**","/**").permitAll()
                // anyRequest: 모든요청에 대하여
                // authenticated: 스프링 시큐리티 컨텍스트 내에서 인증이 완료되야 api를 사용할 수 있다.
                .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요

                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))


                .and()
                .oauth2Login()
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정을 담당
                .userService(customOAuth2UserService);//소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록


/**
 * 0828 내일 다시 만져보기 Oauth2.0
 */
//                .and()
//                .successHandler();



        /**
         * jwt 없이 필터 적용
         */
        // .addFilterBefore(securityAuthentic)

    }
}

/**
 * authorizeRequests : HttpServeltRequet를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미
 */

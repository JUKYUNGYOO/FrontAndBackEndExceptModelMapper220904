package com.example.app220901.config;


import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Collections;

@Configuration // 설정파일임을 명시
@EnableWebSecurity //전역 WebSecurity구성 제공
@EnableGlobalMethodSecurity(jsr250Enabled = true) // RoleAllowed를 활성화 함.
public class ProjectSecurityConfig extends
        KeycloakWebSecurityConfigurerAdapter {

    /*KeycloakAuthenticationToken을 이용하여 인증정보를 구성하는 메소드
     * */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);

    }
/* 모든 접근 규칙은 configure() 메서드 안에서 정의함.
규칙을 정의하고자 스프링 프레임워크가 전달한 HttpSecurity 클래스를 사용하는데
cors란 서로 다른 Origin끼리 요청을 주고받을 수 있게 정해준 표준임.
백엔드와 프론트의 Origin이 다를 경우 사용해야 됨.
* */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        System.out.println("this is Auth Request");
//        System.out.println(http.authorizeHttpRequests());
//        super.configure(http);
//
//        http.authorizeRequests().anyRequest().permitAll();
    http.cors().configurationSource(new CorsConfigurationSource() {
        @Override
        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setMaxAge(3600L);
            return config;
        }
    }).and().csrf().ignoringAntMatchers("/contact")
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().
            authorizeRequests().antMatchers("/myAccount").authenticated()
            .antMatchers("/myBalance").authenticated()
            .antMatchers("/myLoans").authenticated()
            .antMatchers("/user").authenticated()
            .antMatchers("/notices").permitAll()
            .antMatchers("/contact").permitAll().and().httpBasic();

    }


    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(
                new SessionRegistryImpl());
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver(){
        return new KeycloakConfigResolver() {
            private KeycloakDeployment keycloakDeployment;
            @Override
            public KeycloakDeployment resolve(HttpFacade.Request request) {
                if(keycloakDeployment != null){
                    return  keycloakDeployment;
                }
                InputStream configInputStream = getClass().getResourceAsStream("keycloak.json");
                return KeycloakDeploymentBuilder.build(configInputStream);
            }
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



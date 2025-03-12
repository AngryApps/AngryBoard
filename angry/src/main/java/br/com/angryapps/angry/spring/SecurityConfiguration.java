package br.com.angryapps.angry.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String baseUrl = System.getProperty("BASE_URL");
        http.authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/", "/login/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(f -> f.disable())
            .oauth2Login(oauth -> oauth
                    .loginPage(baseUrl + "/login")
                    .defaultSuccessUrl(baseUrl + "/api/v1/auth/current", true)
                    .failureUrl(baseUrl + "/login-failure")
            )
            .logout(logout -> logout
                    .logoutUrl(baseUrl + "/logout")
                    .logoutSuccessUrl(baseUrl + "/login?logout=true")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID"));

        return http.build();
    }
}

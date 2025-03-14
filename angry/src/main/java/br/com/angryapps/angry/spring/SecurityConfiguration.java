package br.com.angryapps.angry.spring;

import br.com.angryapps.angry.api.filters.RemoveCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private Environment environment;

    @Autowired
    private RemoveCorsFilter removeCorsFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/**"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String baseUrl = environment.getProperty("base.url");

        http.addFilterAfter(removeCorsFilter, CorsFilter.class)
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/", "/login/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(AbstractHttpConfigurer::disable)
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
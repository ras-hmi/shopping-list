package com.shopping.shoppingList.Config;

import com.shopping.shoppingList.models.Items;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests((authz) -> {
                    try {
                        authz
                                .requestMatchers("items/itemById")
                                .permitAll()
                                .requestMatchers("items/allItems")
                                .permitAll()
                                .anyRequest()
                                .authenticated().and().formLogin();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        return httpSecurity.build();
    }
}

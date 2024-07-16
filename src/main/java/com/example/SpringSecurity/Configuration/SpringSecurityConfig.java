package com.example.SpringSecurity.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public  class SpringSecurityConfig{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth)->auth.requestMatchers("/api/v1/auth")
                        .authenticated()).formLogin(Customizer.withDefaults());
        http
                .authorizeHttpRequests((auth)->auth.requestMatchers("/api/v1/noAuth").permitAll());
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                 .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("noAuth")
//                .permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("auth")
//                .authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .build();
//    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        UserDetails admin= User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN").build();

        UserDetails user = User.withUsername("user")
                .password(encoder.encode("user123"))
                .roles("USER").build();

        return new InMemoryUserDetailsManager(admin,user);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


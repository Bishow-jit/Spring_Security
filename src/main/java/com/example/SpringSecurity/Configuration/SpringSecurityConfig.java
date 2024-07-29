package com.example.SpringSecurity.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public  class SpringSecurityConfig{
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((auth)->auth.requestMatchers("/api/v1/auth")
//                        .authenticated()).formLogin(Customizer.withDefaults());
//        http
//                .authorizeHttpRequests((auth)->auth.requestMatchers("/api/v1/noAuth").permitAll());
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                    auth -> {
                        auth.requestMatchers("/api/v1/noAuth/**","/api/v1/login").permitAll();
                        auth.requestMatchers("/api/v1").authenticated();
                        auth.requestMatchers("/admin/**").hasRole("ADMIN");
                        auth.requestMatchers("/user/**").hasRole("USER");
                        auth.anyRequest().authenticated();
                    }).formLogin(AbstractAuthenticationFilterConfigurer::permitAll).build();



    }
//authentication Static
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
// hard code user add and store in the memory
//        UserDetails admin= User.withUsername("admin")
//                .password(encoder.encode("admin123"))
//                .roles("ADMIN").build();
//
//        UserDetails user = User.withUsername("user")
//                .password(encoder.encode("user123"))
//                .roles("USER").build();
//        return new InMemoryUserDetailsManager(admin,user);
//   }


////authentication Dynamic and authentication from db
    public UserDetailsService userDetailsService(){
        return  new UserInfoDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
     return new ProviderManager(authenticationProvider());
    }
//Database Authentication Provider
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}


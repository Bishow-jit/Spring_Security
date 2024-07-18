package com.example.SpringSecurity.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
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
            .csrf(csrf ->csrf.disable())
            .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/noAuth/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()).formLogin(Customizer.withDefaults()).build();

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

    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}


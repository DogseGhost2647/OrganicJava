package com.example.organic.config.security;

import com.example.organic.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/inicio", "/registro", "/css/**", "/js/**", "/images/**").permitAll() //no esta bloqueado
                        .requestMatchers("/admin/**", "/productos/**", "/upload").hasRole("ADMIN") // bloquea usuarios
                        .requestMatchers("/homeusuario", "/carrito/**").hasRole("USER") // bloquea a admins
                        .anyRequest().authenticated() // bloquea a cualquiera que NO este logeado
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("correo")   // <--- le dices que lea "correo"
                        .passwordParameter("password") // <--- y la contraseña sigue igual
                        .successHandler((request, response, authentication) -> {
                            // Obtenemos roles
                            var roles = authentication.getAuthorities();
                            if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
                                response.sendRedirect("/admin/dashboard"); // admin va a su dashboard
                            } else {
                                response.sendRedirect("/homeusuario"); // usuario va al homeusuario
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }


    //proveedor de autenticación real que usa la DB
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); //importante si las contraseñas son plain text
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //si las contraseñas en la DB no están encriptadas
        return NoOpPasswordEncoder.getInstance();
    }

}

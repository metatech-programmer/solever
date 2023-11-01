package co.com.mt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/editar","/editarUsuario","/editarTema", "/editarU","/editarT","/eliminar", "/usuarios")
                .hasRole("ADMIN")
                .requestMatchers("/app", "/horarios", "/ajustes", "/datos")
                .hasAnyRole("ELDERLY", "TEEN", "ADULT", "ADMIN", "SPECIALIST")
                .anyRequest()
                .permitAll()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/app")
                .failureUrl("/login?error")
                .permitAll()
                ).logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
                ).exceptionHandling(exeptions -> exeptions
                .accessDeniedPage("/error")
                );
        return http.build();
    }

}

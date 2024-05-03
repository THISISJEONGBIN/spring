package project_spring.board.Security;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.RequestContextFilter;
import project_spring.board.DTO.DTO;
import project_spring.board.DTO.DTObuilder;
import project_spring.board.repository.mapper;

import java.util.Iterator;
import java.util.List;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
@Autowired
private mapper map;
    @Autowired
    private RequestContextFilter requestContextFilter;

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/singup").permitAll().requestMatchers("/").hasAnyRole("ADMIN","USER").requestMatchers("/admin").hasRole("ADMIN").anyRequest().authenticated()

        );
        http.addFilterBefore(requestContextFilter, UsernamePasswordAuthenticationFilter.class);
        http.formLogin(login ->
                login.loginPage("/userlogin").successForwardUrl("/board").failureForwardUrl("/userlogin?error").permitAll());

        http.logout(logout ->
                logout.logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll().logoutSuccessUrl("/userlogin").invalidateHttpSession(true)
                );

        http.csrf().disable();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager adminUserDetailsManager() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        Iterator<DTO> it = map.Select_User().iterator();

        while(it.hasNext()) {
            DTO user = it.next();
            manager.createUser(
                    User.withUsername(user.getId())
                            .password(passwordEncoder().encode(user.getPw()))
                            .roles("USER").build()
            );
        }
        // 관리자 계정 추가
        manager.createUser(
                User.withUsername("admin")
                        .password(passwordEncoder().encode("wjdqls0510"))
                        .roles("ADMIN")
                        .build()
        );

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

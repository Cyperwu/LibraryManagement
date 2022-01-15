package com.cyper.library.config;

import java.io.PrintWriter;

import com.cyper.library.dto.RespDto;
import com.cyper.library.jwt.JwtTokenConfigurer;
import com.cyper.library.jwt.JwtTokenProvider;
import com.cyper.library.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    private static final String[] AUTH_WHITELIST = {
            "/dev/**",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/webjars/**"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // allow all
        http.cors();

        http.csrf().disable();

        // 不使用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.apply(new JwtTokenConfigurer(jwtTokenProvider));

        http.authorizeRequests()
                .antMatchers("/users/signin").permitAll()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/dev/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, authException) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    String uuu = req.getRequestURI();

                    RespDto respData = new RespDto(401, "请先登录" + uuu, null);
                    out.write(respData.toJson());
                    out.flush();
                    out.close();
                });
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

package com.qin.security.config;

import com.qin.security.filter.TokenAuthFilter;
import com.qin.security.filter.TokenLoginFilter;
import com.qin.security.util.TokenLogoutHandler;
import com.qin.security.util.TokenManager;
import com.qin.security.util.UnAuthEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author sebastian
 * @date 11/15/20
 */
@Configuration
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenManager tokenManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserDetailsService userDetailsService;


    public TokenWebSecurityConfig(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate, UserDetailsService userDetailsService) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnAuthEntryPoint())
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                // todo: logout url
                .and().logout().logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate)).and()
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                .addFilter(new TokenAuthFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        // don't need auth
        web.ignoring().antMatchers("/api/**");
    }
}

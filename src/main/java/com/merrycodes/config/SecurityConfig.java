package com.merrycodes.config;

import com.merrycodes.filter.LoginAuthenticationFilter;
import com.merrycodes.filter.TokenAuthenticationFilter;
import com.merrycodes.handler.CostomLogoutSuccessHandler;
import com.merrycodes.service.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security 配置类
 *
 * @author MerryCodes
 * @date 2020/5/4 10:12
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;

    private final UserService userService;

    private final CostomLogoutSuccessHandler costomLogoutSuccessHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证用户的来源【内存/数据库】
     *
     * @param auth {@link AuthenticationManagerBuilder}
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 配置Spring Security相关信息
     * 路径不需要带上 context-path
     *
     * @param http {@link HttpSecurity}
     * @throws Exception 异常
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilter(new LoginAuthenticationFilter(super.authenticationManager(), jwtConfig))
                .addFilter(new TokenAuthenticationFilter(super.authenticationManager(), userService, jwtConfig))
                .logout().logoutUrl(jwtConfig.getAuthLogoutUrl())
                .logoutSuccessHandler(costomLogoutSuccessHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

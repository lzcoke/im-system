package org.yunqi.comon.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                // 未授权处理
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and()
                .sessionManagement().disable()
                .authorizeRequests()
                // 无授权访问
                .antMatchers("/app/auth/**", "/doc.html", "/swagger-resources/**", "/v3/**").permitAll()
                .anyRequest().authenticated().and()
                // 关闭默认自带的登录
                .formLogin().disable()
                // 关闭默认自带的登出
                .logout().disable()
                // 无授权异常处理
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).and()
                // 添加 CSRF 支持，使用WebSecurityConfigurerAdapter时，默认启用
                .csrf().disable()
                // 过滤处理
                .addFilterAt(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtTokenFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
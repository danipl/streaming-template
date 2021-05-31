package com.danipl.st.api.config.security;

import com.danipl.st.api.constant.ProfileConstant;
import com.danipl.st.api.security.AdminAuthenticationProvider;
import com.danipl.st.api.security.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import static com.danipl.st.api.constant.AppConstant.REALM;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Profile(value = {ProfileConstant.TEST, ProfileConstant.LOCAL})
public class BasicWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminAuthenticationProvider adminAuthenticationProvider;
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManager) {

        authenticationManager
                .authenticationProvider(this.userAuthenticationProvider)
                .authenticationProvider(this.adminAuthenticationProvider);
    }

    @Override
    public void configure(final WebSecurity webSecurity) {

        webSecurity.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**");
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {

        final BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
        basicAuthenticationEntryPoint.setRealmName(REALM);

        httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic()
                .authenticationEntryPoint(basicAuthenticationEntryPoint);
    }

}

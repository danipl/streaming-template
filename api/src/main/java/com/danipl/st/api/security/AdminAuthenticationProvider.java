package com.danipl.st.api.security;

import com.danipl.st.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.danipl.st.api.constant.SecurityConstant.ROLE_ADMIN;
import static com.danipl.st.api.constant.SecurityConstant.ROLE_USER;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Component("adminAuthenticationProvider")
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AdminService adminService;

    @Override
    public Authentication authenticate(final Authentication authentication) {

        final boolean isAdmin = this.adminService.isAdmin(authentication.getName(), authentication.getCredentials().toString());

        if (isAdmin) {

            return new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    List.of(new SimpleGrantedAuthority(ROLE_ADMIN), new SimpleGrantedAuthority(ROLE_USER)));
        }

        throw new BadCredentialsException("Bad credentials.");
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

}

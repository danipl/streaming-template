package com.danipl.st.api.security;

import com.danipl.st.model.User;
import com.danipl.st.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.danipl.st.api.constant.SecurityConstant.ROLE_USER;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Component("userAuthenticationProvider")
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(final Authentication authentication) {

        final Optional<User> userOptional =
                this.userService.findByNameAndPassword(
                        authentication.getName(), authentication.getCredentials().toString());

        userOptional.orElseThrow(() -> new BadCredentialsException("Bad credentials."));

        final UsernamePasswordAuthenticationToken authenticationWithDetails = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                List.of(new SimpleGrantedAuthority(ROLE_USER)));

        authenticationWithDetails.setDetails(userOptional.get());

        return authenticationWithDetails;
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

}

package com.danipl.st.service;

import com.danipl.st.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
public interface UserService {

    Collection<User> getAll();

    Optional<User> findByNameAndPassword(final String name, final String password);

    Optional<User> findById(final long id);

}

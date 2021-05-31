package com.danipl.st.service.impl;

import com.danipl.st.model.User;
import com.danipl.st.repository.UserRepository;
import com.danipl.st.service.UserService;
import com.danipl.st.util.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Service("userService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@CacheConfig(cacheNames = {"user"})
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<User> getAll() {

        return this.userRepository.findAll();
    }

    @Cacheable
    @Override
    public Optional<User> findByNameAndPassword(final String name, final String password) {

        return this.userRepository.findByNameAndPassword(name, Encrypt.toMD5(password));
    }

    @Cacheable
    @Override
    public Optional<User> findById(final long id) {

        return this.userRepository.findById(id);
    }

}
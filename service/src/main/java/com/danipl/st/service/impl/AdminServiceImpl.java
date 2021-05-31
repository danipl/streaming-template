package com.danipl.st.service.impl;

import com.danipl.st.repository.AdminRepository;
import com.danipl.st.service.AdminService;
import com.danipl.st.util.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Service("adminService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public boolean isAdmin(final String name, final String password) {

        return this.adminRepository.isAdmin(name, Encrypt.toMD5(password));
    }

}
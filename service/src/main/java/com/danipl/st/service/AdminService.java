package com.danipl.st.service;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
public interface AdminService {

    boolean isAdmin(final String name, final String password);

}

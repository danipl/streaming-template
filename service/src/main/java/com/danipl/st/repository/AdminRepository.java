package com.danipl.st.repository;

import com.danipl.st.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Admin a " +
            "WHERE a.name = :name AND a.password = :password")
    Boolean isAdmin(@Param("name") final String name, @Param("password") final String password);

}

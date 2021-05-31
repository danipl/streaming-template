package com.danipl.st.repository;

import com.danipl.st.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<Message, Integer> {
}

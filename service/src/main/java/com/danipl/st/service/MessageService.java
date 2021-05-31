package com.danipl.st.service;

import com.danipl.st.dto.SendMessageDto;
import com.danipl.st.model.Message;
import com.danipl.st.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
public interface MessageService {

    void sendMessage(final User user, final SendMessageDto sendMessageDto);

    Collection<Message> getReceivedMessages(final User user, final LocalDateTime from);

}

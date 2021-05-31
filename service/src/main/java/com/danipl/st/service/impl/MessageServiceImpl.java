package com.danipl.st.service.impl;

import com.danipl.st.dto.SendMessageDto;
import com.danipl.st.exception.ApiException;
import com.danipl.st.kafka.MessageKey;
import com.danipl.st.kafka.MessageValue;
import com.danipl.st.model.Message;
import com.danipl.st.model.User;
import com.danipl.st.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Service("messageService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Value("${spring.kafka.message.topic}")
    private String messageTopic;

    @Autowired
    private KafkaTemplate<SpecificRecord, SpecificRecord> kafkaTemplate;

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void sendMessage(final User user, final SendMessageDto sendMessageDto) {

        if (this.validateMessage(user, sendMessageDto)) {

            throw new ApiException(
                    "Cannot send a message to yourself.",
                    HttpStatus.BAD_REQUEST,
                    new Exception("Wrong user message validation."));
        }

        final MessageKey messageKey = this.createMessageKey(user);
        final MessageValue messageValue = this.createMessageValue(user, sendMessageDto);

        try {

            final ListenableFuture<SendResult<SpecificRecord, SpecificRecord>> listenableFuture =
                    this.kafkaTemplate.send(this.messageTopic, messageKey, messageValue);

            this.kafkaTemplate.flush();

            listenableFuture.get();
        } catch (final Exception ex) {

            log.debug("Sending message.", ex);

            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Override
    public Collection<Message> getReceivedMessages(final User user, final LocalDateTime from) {

        return Collections.emptyList();
    }

    private boolean validateMessage(final User user, final SendMessageDto sendMessageDto) {

        return user.getId() == sendMessageDto.getUserToId();
    }

    private MessageKey createMessageKey(User user) {

        return MessageKey.newBuilder().setUserFromId(user.getId()).build();
    }

    private MessageValue createMessageValue(User user, SendMessageDto sendMessageDto) {

        return MessageValue.newBuilder()
                .setUserFromId(user.getId())
                .setUserToId(sendMessageDto.getUserToId())
                .setText(sendMessageDto.getText())
                .setCreateAt(Instant.now())
                .build();
    }

}

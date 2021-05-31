package com.danipl.st.message.consumer.service.impl;

import com.danipl.st.kafka.MessageKey;
import com.danipl.st.kafka.MessageValue;
import com.danipl.st.message.consumer.service.MessageConsumerService;
import com.danipl.st.model.Message;
import com.danipl.st.model.User;
import com.danipl.st.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@Service("messageConsumerService")
@Slf4j
public class MessageConsumerServiceImpl implements MessageConsumerService {

    @Value("${spring.kafka.message.failure.sleep.secs:5}")
    private int messageFailureSleepInSecs;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    @KafkaListener(groupId = "${spring.kafka.message.topic.groupId}", topics = "${spring.kafka.message.topic}")
    public void consumeMessage(final ConsumerRecord<MessageKey, MessageValue> record, final Acknowledgment acknowledgment) {

        try {

            final Message messageSaved = this.messageRepository.save(
                    Message.builder()
                            .from(User.builder().id(record.value().getUserFromId()).build())
                            .to(User.builder().id(record.value().getUserToId()).build())
                            .text(record.value().getText().toString())
                            .createAt(LocalDateTime.ofInstant(record.value().getCreateAt(), ZoneOffset.UTC))
                            .build());

            acknowledgment.acknowledge();

            log.info("Message loaded {}", messageSaved);
        } catch (final Exception ex) {

            acknowledgment.nack(TimeUnit.SECONDS.toMillis(this.messageFailureSleepInSecs));

            log.error("Message not loaded {}", record.value());
        }
    }

}

package com.danipl.st.message.consumer.service;

import com.danipl.st.kafka.MessageKey;
import com.danipl.st.kafka.MessageValue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
public interface MessageConsumerService {

    void consumeMessage(final ConsumerRecord<MessageKey, MessageValue> record, final Acknowledgment acknowledgment);

}
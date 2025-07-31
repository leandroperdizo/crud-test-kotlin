package com.example.crud.adapter.queue

import com.example.crud.domain.port.MessagingPort
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducerAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>
) : MessagingPort {

    override fun send(message: String) {
        kafkaTemplate.send("my-topic", message)
    }

    override fun receive(): List<String> {
        throw UnsupportedOperationException("Consumo Kafka é assíncrono")
    }
}
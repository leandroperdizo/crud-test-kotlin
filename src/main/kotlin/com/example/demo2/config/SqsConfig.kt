package com.example.demo2.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient

@Configuration
class SqsConfig {

    @Value("\${sqs.queue.url}")
    lateinit var queueUrl: String

    @Bean
    fun sqsClient(): SqsClient {
        return SqsClient.builder()
            .region(Region.US_EAST_1)
        .build()
    }

    @Bean
    fun queueUrl(): String {
        return queueUrl
    }
}
package com.example.crud.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import java.net.URI

@Configuration
class SqsConfig {

    @Value("\${sqs.queue.url}")
    lateinit var queueUrl: String

    @Bean
    fun sqsClient(): SqsClient {
        return SqsClient.builder()
            .endpointOverride(URI.create("http://localhost:4566")) // <= Importante!
            .region(Region.US_EAST_1)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create("test", "test")
                )
            )
            .build()
    }

    @Bean
    fun queueUrl(): String {
        return queueUrl
    }
}
package com.ascon.challenge.config

import com.ascon.challenge.adapters.storage.postgresql.QueryHandler
import com.ascon.challenge.ports.`in`.MetricQueryService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetricConfig {

    @Bean
    fun metricQueryService(queryHandler: QueryHandler) = MetricQueryService(queryHandler)

}
package com.ascon.challenge.ports

import com.ascon.challenge.ports.out.AdBatchRepository
import com.ascon.challenge.ports.out.FileLoader
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Profile("dev")
@Component
internal class DataImporter(
    val loader: FileLoader,
    val handler: AdBatchRepository
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val stream = ClassPathResource("data/input.csv").inputStream

        loader.load(stream)
            .also {
                handler.save(it)
            }
    }
}
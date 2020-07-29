package com.ascon.challenge.adapters.storage.postgresql

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

class PostgreSQLContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    internal class SpecifiedPostgreSQLContainer(image: String) : PostgreSQLContainer<SpecifiedPostgreSQLContainer>(image)

    companion object {
        internal val postgres = SpecifiedPostgreSQLContainer("postgres:13")
            .withUsername("admin")
            .withPassword("admin")
            .withDatabaseName("ad")
            .withExposedPorts(5432)
    }

    override fun initialize(context: ConfigurableApplicationContext) {
        postgres.start()

        TestPropertyValues.of(
            "spring.datasource.url=" + postgres.jdbcUrl,
            "spring.datasource.username=" + postgres.username,
            "spring.datasource.password=" + postgres.password
        ).applyTo(context.environment)
    }
}
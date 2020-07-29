package com.ascon.challenge.adapters.storage.postgresql

import com.ascon.challenge.adapters.storage.postgresql.BatchInsertAdCommandHandlerIntegrationTest.Fixtures.INPUT_AD
import com.ascon.challenge.model.Ad
import com.ascon.challenge.model.AdId
import com.ascon.challenge.model.Campaign
import com.ascon.challenge.model.Clicks
import com.ascon.challenge.model.DataSource
import com.ascon.challenge.model.Date
import com.ascon.challenge.model.Impressions
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.core.publisher.Flux
import java.time.LocalDate
import java.util.*

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = [PostgreSQLContainerInitializer::class])
internal class BatchInsertAdCommandHandlerIntegrationTest {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var handler: BatchInsertAdCommandHandler

    @Test
    fun `given input data then store contents in a table`() {
        handler.save(Flux.just(INPUT_AD))

        jdbcTemplate.queryForObject("select count(*) as count from ads", Int::class.java)
            .shouldBe(1)
    }


    private object Fixtures {
        val INPUT_AD = Ad(
            datasource = DataSource("TEST"),
            campaign = Campaign("IT"),
            date = Date(LocalDate.of(2020, 10, 8)),
            clicks = Clicks(5),
            impressions = Impressions(100)
        )
    }
}
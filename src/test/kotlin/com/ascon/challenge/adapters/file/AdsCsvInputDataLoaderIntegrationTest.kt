package com.ascon.challenge.adapters.file

import com.ascon.challenge.adapters.file.AdsCsvInputDataLoaderIntegrationTest.Fixtures.EXPECTED_AD
import com.ascon.challenge.model.Ad
import com.ascon.challenge.model.Campaign
import com.ascon.challenge.model.Clicks
import com.ascon.challenge.model.DataSource
import com.ascon.challenge.model.Date
import com.ascon.challenge.model.Impressions
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import reactor.test.StepVerifier
import java.time.LocalDate

internal class AdsCsvInputDataLoaderIntegrationTest {

    private val loader = AdsCsvInputDataLoader()

    @Test
    fun `input content is read when csv loader supplied with filename`() {
        val inputStream = ClassPathResource("data/test.csv").inputStream

        StepVerifier.create(loader.load(inputStream))
            .expectNext(EXPECTED_AD)
            .expectNextCount(6)
            .expectComplete()
            .verify()
    }

    private object Fixtures {
        val EXPECTED_AD = Ad(
            datasource = DataSource("Google Ads"),
            campaign = Campaign("Adventmarkt Touristik"),
            date = Date(LocalDate.of(2019, 11, 12)),
            clicks = Clicks(7),
            impressions = Impressions(22425)
        )
    }
}
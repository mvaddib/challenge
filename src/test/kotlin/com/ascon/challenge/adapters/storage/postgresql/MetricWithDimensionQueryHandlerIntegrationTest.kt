package com.ascon.challenge.adapters.storage.postgresql

import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.ADVENTMARKT_CAMPAIGN
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.CAMPAIGN_FILTER
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.DATE_FILTER
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.DATE_RANGE
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.DAY_1_DATE
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.DAY_2_DATE
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.GOOGLE_ADS_DATASOURCE
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.NO_FILTERS
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.NO_RANGE
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.REMARKETING_CAMPAIGN
import com.ascon.challenge.adapters.storage.postgresql.MetricWithDimensionQueryHandlerIntegrationTest.Fixtures.TWITTER_ADS_DATASOURCE
import com.ascon.challenge.model.Dimension
import com.ascon.challenge.model.Filter
import com.ascon.challenge.model.Metric
import com.ascon.challenge.model.Range
import com.ascon.challenge.ports.out.MetricWithDimensionQueryHandler
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.time.LocalDate


@Sql("/data/truncate-data.sql", "/data/test-data.sql")
@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = [PostgreSQLContainerInitializer::class])
internal class MetricWithDimensionQueryHandlerIntegrationTest {

    @Autowired
    private lateinit var handler: MetricWithDimensionQueryHandler

    @Test
    fun `given clicks metric should return grouped clicks per day`() {
        val results = handler.query(Metric.CLICKS, NO_RANGE, NO_FILTERS, setOf(Dimension.DATE))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(DAY_1_DATE))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(163)))
        results[1].dimensions.shouldBe(listOf(DAY_2_DATE))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(785)))
    }

    @Test
    fun `given impressions metric should return grouped impressions per day`() {
        val results = handler.query(Metric.IMPRESSIONS, NO_RANGE, NO_FILTERS, setOf(Dimension.DATE))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(DAY_1_DATE))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(1103)))
        results[1].dimensions.shouldBe(listOf(DAY_2_DATE))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(1653)))
    }

    @Test
    fun `given ctr metric should return grouped ctr per day`() {
        val results = handler.query(Metric.CTR, NO_RANGE, NO_FILTERS, setOf(Dimension.DATE))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(DAY_1_DATE))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(14.78)))
        results[1].dimensions.shouldBe(listOf(DAY_2_DATE))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(47.49)))
    }

    @Test
    fun `given clicks metric should return grouped clicks per datasource`() {
        val results = handler.query(Metric.CLICKS, NO_RANGE, NO_FILTERS, setOf(Dimension.DATASOURCE))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(GOOGLE_ADS_DATASOURCE))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(163)))
        results[1].dimensions.shouldBe(listOf(TWITTER_ADS_DATASOURCE))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(785)))
    }

    @Test
    fun `given impressions metric should return grouped impressions per datasource`() {
        val results = handler.query(Metric.IMPRESSIONS, NO_RANGE, NO_FILTERS, setOf(Dimension.DATASOURCE))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(GOOGLE_ADS_DATASOURCE))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(1103)))
        results[1].dimensions.shouldBe(listOf(TWITTER_ADS_DATASOURCE))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(1653)))
    }

    @Test
    fun `given ctr metric should return grouped ctr per datasource`() {
        val results = handler.query(Metric.CTR, NO_RANGE, NO_FILTERS, setOf(Dimension.DATASOURCE))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(GOOGLE_ADS_DATASOURCE))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(14.78)))
        results[1].dimensions.shouldBe(listOf(TWITTER_ADS_DATASOURCE))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(47.49)))
    }

    @Test
    fun `given clicks metric should return grouped clicks per campaign`() {
        val results = handler.query(Metric.CLICKS, NO_RANGE, NO_FILTERS, setOf(Dimension.CAMPAIGN))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(ADVENTMARKT_CAMPAIGN))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(16)))
        results[1].dimensions.shouldBe(listOf(REMARKETING_CAMPAIGN))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(932)))
    }

    @Test
    fun `given impressions metric should return grouped impressions per campaign`() {
        val results = handler.query(Metric.IMPRESSIONS, NO_RANGE, NO_FILTERS, setOf(Dimension.CAMPAIGN))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(ADVENTMARKT_CAMPAIGN))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(452)))
        results[1].dimensions.shouldBe(listOf(REMARKETING_CAMPAIGN))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(2304)))
    }

    @Test
    fun `given ctr metric should return grouped ctr per campaign`() {
        val results = handler.query(Metric.CTR, NO_RANGE, NO_FILTERS, setOf(Dimension.CAMPAIGN))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(ADVENTMARKT_CAMPAIGN))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(3.54)))
        results[1].dimensions.shouldBe(listOf(REMARKETING_CAMPAIGN))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(40.45)))
    }

    @Test
    fun `given clicks metric should return grouped clicks per datasource and campaign`() {
        val results = handler.query(Metric.CLICKS, NO_RANGE, NO_FILTERS, setOf(Dimension.DATASOURCE, Dimension.CAMPAIGN))

        results.shouldHaveSize(3)
        results[0].dimensions.shouldBe(listOf(GOOGLE_ADS_DATASOURCE, ADVENTMARKT_CAMPAIGN))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(16)))
        results[1].dimensions.shouldBe(listOf(GOOGLE_ADS_DATASOURCE, REMARKETING_CAMPAIGN))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(147)))
        results[2].dimensions.shouldBe(listOf(TWITTER_ADS_DATASOURCE, REMARKETING_CAMPAIGN))
        results[2].values.shouldBe(listOf(BigDecimal.valueOf(785)))
    }

    @Test
    fun `given impressions metric should return grouped impressions per datasource and campaign`() {
        val results = handler.query(Metric.IMPRESSIONS, NO_RANGE, NO_FILTERS, setOf(Dimension.DATASOURCE, Dimension.CAMPAIGN))

        results.shouldHaveSize(3)
        results[0].dimensions.shouldBe(listOf(GOOGLE_ADS_DATASOURCE, ADVENTMARKT_CAMPAIGN))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(452)))
        results[1].dimensions.shouldBe(listOf(GOOGLE_ADS_DATASOURCE, REMARKETING_CAMPAIGN))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(651)))
        results[2].dimensions.shouldBe(listOf(TWITTER_ADS_DATASOURCE, REMARKETING_CAMPAIGN))
        results[2].values.shouldBe(listOf(BigDecimal.valueOf(1653)))
    }

    @Test
    fun `given ctr metric should return grouped ctr per datasource and campaign`() {
        val results = handler.query(Metric.CTR, NO_RANGE, NO_FILTERS, setOf(Dimension.DATASOURCE, Dimension.CAMPAIGN))

        results.shouldHaveSize(3)
        results[0].dimensions.shouldBe(listOf(GOOGLE_ADS_DATASOURCE, ADVENTMARKT_CAMPAIGN))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(3.54)))
        results[1].dimensions.shouldBe(listOf(GOOGLE_ADS_DATASOURCE, REMARKETING_CAMPAIGN))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(22.58)))
        results[2].dimensions.shouldBe(listOf(TWITTER_ADS_DATASOURCE, REMARKETING_CAMPAIGN))
        results[2].values.shouldBe(listOf(BigDecimal.valueOf(47.49)))
    }

    @Test
    fun `given ctr metric should return grouped ctr per campaign on specified date and campaign`() {
        val results = handler.query(Metric.CTR, NO_RANGE, setOf(DATE_FILTER, CAMPAIGN_FILTER), setOf(Dimension.CAMPAIGN))

        results.shouldHaveSize(1)
        results[0].dimensions.shouldBe(listOf(REMARKETING_CAMPAIGN))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(22.58)))
    }

    @Test
    fun `given clicks metric should return grouped clicks per campaign within date range`() {
        val results = handler.query(Metric.CLICKS, DATE_RANGE, NO_FILTERS, setOf(Dimension.CAMPAIGN))

        results.shouldHaveSize(2)
        results[0].dimensions.shouldBe(listOf(ADVENTMARKT_CAMPAIGN))
        results[0].values.shouldBe(listOf(BigDecimal.valueOf(16)))
        results[1].dimensions.shouldBe(listOf(REMARKETING_CAMPAIGN))
        results[1].values.shouldBe(listOf(BigDecimal.valueOf(932)))
    }

    private object Fixtures {
        const val GOOGLE_ADS_DATASOURCE = "Google Ads"
        const val TWITTER_ADS_DATASOURCE = "Twitter"

        const val ADVENTMARKT_CAMPAIGN = "Adventmarkt Touristik"
        const val REMARKETING_CAMPAIGN = "Remarketing"

        const val DAY_1_DATE = "2019-11-13 00:00:00"
        const val DAY_2_DATE = "2019-11-14 00:00:00"

        val NO_FILTERS = setOf<Filter>()
        val NO_RANGE = null
        val DATE_RANGE = Range(from = LocalDate.of(2019, 11, 13), to = LocalDate.of(2019, 11, 14))
        val DATE_FILTER = Filter(Dimension.DATE, "2019-11-13")
        val CAMPAIGN_FILTER = Filter(Dimension.CAMPAIGN, REMARKETING_CAMPAIGN)
    }
}
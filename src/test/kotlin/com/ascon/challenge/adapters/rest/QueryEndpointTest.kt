package com.ascon.challenge.adapters.rest

import com.ascon.challenge.adapters.rest.QueryEndpointTest.Fixtures.CAMPAIGN_FILTER
import com.ascon.challenge.adapters.rest.QueryEndpointTest.Fixtures.ONLY_RANGE_REQUEST
import com.ascon.challenge.adapters.rest.QueryEndpointTest.Fixtures.RANGE
import com.ascon.challenge.adapters.rest.QueryEndpointTest.Fixtures.FULL_REQUEST
import com.ascon.challenge.model.Dimension
import com.ascon.challenge.model.Filter
import com.ascon.challenge.model.Metric
import com.ascon.challenge.model.Range
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebFluxTest(QueryEndpoint::class)
@AutoConfigureWebClient
internal class QueryEndpointTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockBean
    private lateinit var facade: QueryFacade

    @Test
    fun `should return status 201 for valid metric request`() {
        webClient.post()
            .uri("/api/query/CLICKS")
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue("{}"))
            .exchange()
            .expectStatus().isCreated

        verify(facade, times(1)).query(Metric.CLICKS, null, setOf(), setOf())
    }

    @Test
    fun `should return status 400 for invalid metric request`() {
        webClient.post()
            .uri("/api/query/SOME")
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue("{}"))
            .exchange()
            .expectStatus().isBadRequest

        verifyNoInteractions(facade)
    }

    @Test
    fun `should return status 201 for valid metric request with range`() {
        webClient.post()
            .uri("/api/query/CLICKS")
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue(ONLY_RANGE_REQUEST))
            .exchange()
            .expectStatus().isCreated

        verify(facade, times(1)).query(Metric.CLICKS, RANGE, setOf(), setOf())
    }

    @Test
    fun `should return status 201 for valid metric request with range, filters and dimensions`() {
        webClient.post()
            .uri("/api/query/CLICKS")
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue(FULL_REQUEST))
            .exchange()
            .expectStatus().isCreated

        verify(facade, times(1))
            .query(Metric.CLICKS, RANGE, setOf(CAMPAIGN_FILTER), setOf(Dimension.DATASOURCE))
    }

    private object Fixtures {
        const val REMARKETING = "Remarketing"

        val RANGE = Range(
            from = LocalDate.of(2019, 10, 10),
            to = LocalDate.of(2019, 10, 11)
        )
        val CAMPAIGN_FILTER = Filter(Dimension.CAMPAIGN, REMARKETING)

        val ONLY_RANGE_REQUEST = QueryRequest(range = RANGE)
        val FULL_REQUEST = QueryRequest(range = RANGE, filters = setOf(CAMPAIGN_FILTER), dimensions = setOf(Dimension.DATASOURCE))
    }

}
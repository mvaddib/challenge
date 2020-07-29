package com.ascon.challenge.adapters.rest

import com.ascon.challenge.model.Dimension
import com.ascon.challenge.model.Filter
import com.ascon.challenge.model.Metric
import com.ascon.challenge.model.Range
import com.ascon.challenge.ports.`in`.MetricQueryService
import org.springframework.stereotype.Component

@Component
class QueryFacade(
    private val queryService: MetricQueryService
) {
    fun query(metric: Metric, range: Range?, filters: Set<Filter>, dimensions: Set<Dimension>) =
        queryService.query(metric, range, filters, dimensions)
}
package com.ascon.challenge.ports.`in`

import com.ascon.challenge.adapters.storage.postgresql.QueryHandler
import com.ascon.challenge.model.Dimension
import com.ascon.challenge.model.Filter
import com.ascon.challenge.model.Metric
import com.ascon.challenge.model.Range

class MetricQueryService(
    private val queryHandler: QueryHandler
) : MetricWithDimensionsQueryUserCase {

    override fun query(metric: Metric, range: Range?, filters: Set<Filter>, dimensions: Set<Dimension>) =
        queryHandler.query(metric, range, filters, dimensions)

}
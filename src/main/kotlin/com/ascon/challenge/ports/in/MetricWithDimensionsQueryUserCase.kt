package com.ascon.challenge.ports.`in`

import com.ascon.challenge.model.Dimension
import com.ascon.challenge.model.Filter
import com.ascon.challenge.model.Metric
import com.ascon.challenge.model.QueryResult
import com.ascon.challenge.model.Range

interface MetricWithDimensionsQueryUserCase {

    fun query(metric: Metric, range: Range?, filters: Set<Filter>, dimensions: Set<Dimension>): List<QueryResult>
}
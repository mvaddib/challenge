package com.ascon.challenge.adapters.storage.postgresql

import com.ascon.challenge.model.Dimension
import com.ascon.challenge.model.Filter
import com.ascon.challenge.model.Metric
import com.ascon.challenge.model.QueryResult
import com.ascon.challenge.model.Range
import com.ascon.challenge.ports.out.MetricWithDimensionQueryHandler
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import javax.sql.DataSource

private const val METRIC_PARAM = "metric"

@Component
class QueryHandler(
    datasource: DataSource
) : MetricWithDimensionQueryHandler {
    private val template = NamedParameterJdbcTemplate(datasource)

    override fun query(metric: Metric, range: Range?, filters: Set<Filter>, dimensions: Set<Dimension>) =
        template.query(constructQuery(metric, range, filters, dimensions), mapOf<String, String>(), mapper(dimensions))

    private fun constructQuery( metric: Metric, range: Range?, filters: Set<Filter>, dimensions: Set<Dimension>) = when(dimensions.isEmpty()){
        true -> simpleMetricQuery(metric, range, filters)
        false -> groupingMetricQuery(metric, range, filters, dimensions)
    }

    private fun mapper(dimensions: Set<Dimension>) = { rs: ResultSet, _: Int ->
        QueryResult(
            dimensions = dimensions.map { rs.getString(it.name) },
            values = listOf(rs.getBigDecimal(METRIC_PARAM))
        )
    }
}
package com.ascon.challenge.adapters.storage.postgresql

import com.ascon.challenge.model.Dimension
import com.ascon.challenge.model.Filter
import com.ascon.challenge.model.Metric
import com.ascon.challenge.model.Range

fun simpleMetricQuery(metric: Metric, range: Range?, filters: Set<Filter>) =
    "SELECT ${getMetric(metric)} FROM ads ${addWhereClauseIfNeeded(range, filters)}${addFilterQuery(filters)}${addRangeQuery(range)}"

fun groupingMetricQuery(metric: Metric, range: Range?, filters: Set<Filter>, dimensions: Set<Dimension>): String {
    val groupingColumns = getGroupingColumns(dimensions)
    val metricColumn = getMetric(metric)
    return "SELECT $groupingColumns, $metricColumn FROM ads ${addWhereClauseIfNeeded(range, filters)}${addFilterQuery(filters)}${addRangeQuery(range)} group by $groupingColumns order by $groupingColumns"

}

private fun addWhereClauseIfNeeded(range: Range?, filters: Set<Filter>) = when (range == null && filters.isEmpty()) {
    true -> ""
    false -> "WHERE "
}

private fun addFilterQuery(filters: Set<Filter>) = when (filters.isEmpty()) {
    true -> ""
    false -> filters.joinToString(" AND ") { "${it.dimension.name} = '${it.value}'" }
}

private fun addRangeQuery(range: Range?) = when (range == null) {
    true -> ""
    false -> "${AdsTable.DATE} BETWEEN '${range.from}' AND '${range.to}'"
}

private fun getMetric(metric: Metric) = when (metric) {
    Metric.CLICKS -> "SUM(clicks) as metric"
    Metric.IMPRESSIONS -> "SUM(impressions) as metric"
    Metric.CTR -> "ROUND((SUM(clicks)*1./SUM(impressions))*100, 2) as metric"
}

private fun getGroupingColumns(dimensions: Set<Dimension>) = dimensions.joinToString { it.name }

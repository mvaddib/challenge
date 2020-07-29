package com.ascon.challenge.adapters.rest

import com.ascon.challenge.model.Dimension
import com.ascon.challenge.model.Filter
import com.ascon.challenge.model.Range

data class QueryRequest(
    val range: Range?,
    val filters: Set<Filter> = setOf(),
    val dimensions: Set<Dimension> = setOf()
)
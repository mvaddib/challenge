package com.ascon.challenge.adapters.rest

import com.ascon.challenge.model.Metric
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class QueryEndpoint(val queryService: QueryFacade) {

    @PostMapping("/query/{metric}")
    fun query(@PathVariable metric: Metric, @RequestBody request: QueryRequest) =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(queryService.query(metric, request.range, request.filters, request.dimensions))
}
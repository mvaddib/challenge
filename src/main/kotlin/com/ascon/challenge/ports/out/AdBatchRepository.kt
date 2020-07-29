package com.ascon.challenge.ports.out

import com.ascon.challenge.model.Ad
import reactor.core.publisher.Flux

interface AdBatchRepository {
    fun save(ads: Flux<Ad>)
}
package com.ascon.challenge.ports.out

import com.ascon.challenge.model.Ad
import reactor.core.publisher.Flux
import java.io.InputStream

interface FileLoader {
    fun load(stream: InputStream): Flux<Ad>
}
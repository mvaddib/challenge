package com.ascon.challenge.adapters.storage.postgresql

import com.ascon.challenge.model.Ad
import com.ascon.challenge.ports.out.AdBatchRepository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.set
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*
import javax.sql.DataSource

@Component
internal class BatchInsertAdCommandHandler(
    datasource: DataSource
) : AdBatchRepository {

    private val template = NamedParameterJdbcTemplate(datasource)

    override fun save(ads: Flux<Ad>) {
        ads.bufferTimeout(50, Duration.ofSeconds(5))
            .subscribe(this::doInsert)
    }

    private fun doInsert(ads: List<Ad>) {
        val sources = ads.map {
            val source = MapSqlParameterSource()
            source[AdsTable.ID] = UUID.randomUUID()
            source[AdsTable.DATASOURCE] = it.datasource.name
            source[AdsTable.CAMPAIGN] = it.campaign.name
            source[AdsTable.DATE] = it.date.value
            source[AdsTable.CLICKS] = it.clicks.value
            source[AdsTable.IMPRESSIONS] = it.impressions.value
            source
        }
        template.batchUpdate(INSERT_AD, sources.toTypedArray())
    }
}
package com.ascon.challenge.adapters.file

import com.ascon.challenge.model.Ad
import com.ascon.challenge.model.Campaign
import com.ascon.challenge.model.Clicks
import com.ascon.challenge.model.DataSource
import com.ascon.challenge.model.Date
import com.ascon.challenge.model.Impressions
import com.ascon.challenge.ports.out.FileLoader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
internal class AdsCsvInputDataLoader : FileLoader {

    override fun load(stream: InputStream) = Flux.create<Ad> { sink -> readFile(stream, sink) }

    fun String.toLocalDate() = LocalDate.parse(this, DateTimeFormatter.ofPattern("MM/dd/yy"))

    private fun readFile(stream: InputStream, sink: FluxSink<Ad>) =
        csvReader()
            .open(stream) {
                readAllWithHeaderAsSequence()
                    .map { toAd(it) }
                    .forEach { sink.next(it) }
            }.also { sink.complete() }

    private fun toAd(it: Map<String, String>) =
        Ad(
            datasource = DataSource(it.getValue(CsvHeaders.DATASOURCE.value)),
            campaign = Campaign(it.getValue(CsvHeaders.CAMPAIGN.value)),
            date = Date(it.getValue(CsvHeaders.DAILY.value).toLocalDate()),
            clicks = Clicks(it.getValue(CsvHeaders.CLICKS.value).toInt()),
            impressions = Impressions(it.getValue(CsvHeaders.IMPRESSIONS.value).toInt())
        )

    enum class CsvHeaders(val value: String) {
        DATASOURCE("Datasource"),
        CAMPAIGN("Campaign"),
        DAILY("Daily"),
        CLICKS("Clicks"),
        IMPRESSIONS("Impressions")
    }
}
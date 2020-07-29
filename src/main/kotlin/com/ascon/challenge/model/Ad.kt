package com.ascon.challenge.model

data class Ad(
    val id: AdId? = null,
    val datasource: DataSource,
    val campaign: Campaign,
    val date: Date,
    val clicks: Clicks,
    val impressions: Impressions
)
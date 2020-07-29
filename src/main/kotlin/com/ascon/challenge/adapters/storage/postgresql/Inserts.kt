package com.ascon.challenge.adapters.storage.postgresql

const val INSERT_AD =
    "INSERT INTO Ads (id, datasource, campaign, date, clicks, impressions) " +
        "VALUES (:id, :datasource, :campaign, :date, :clicks, :impressions)"
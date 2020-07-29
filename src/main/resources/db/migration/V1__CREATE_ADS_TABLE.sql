CREATE TABLE ADS
(
    id          UUID,
    datasource  TEXT,
    campaign    TEXT,
    date        TIMESTAMP,
    clicks      INTEGER,
    impressions INTEGER
);

ALTER TABLE ADS
    ADD CONSTRAINT pk_ads_id PRIMARY KEY (id);

CREATE INDEX idx_datasource ON ADS(datasource) INCLUDE(clicks, impressions);
CREATE INDEX idx_campaign ON ADS(campaign) INCLUDE(clicks, impressions);
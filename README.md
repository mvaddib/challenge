## Simple warehouse challenge
This application is allowing for querying data via REST API in generic way. Data is supplied with CSV file and stored in PostgreSQL database.
  
### How to run
In order to run this application you need to have Docker installed and cloned the repository. Follow the instructions below.

#### Build application
```./gradlew clean build```

#### Run application
```docker-compose up --build``` to start application and corresponding services

#### Stopping application
```docker-compose down -v``` to stop application and corresponding services

### URL and available metrics
Application has only one endpoint ```http://localhost:8080/api/query/{metric}```. You can replace ```{metrics}``` placeholder with the following metrics:
1. CLICKS
2. IMPRESSIONS
3. CTR

You must use HTTP POST method and for the time being you must send and empty body along. 

### Example requests
* Basic request
```
curl --header "Content-Type: application/json" \
     --request POST \
     --data '{}' \
     http://localhost:8080/api/query/CLICKS
```
     
* Grouping by dimension (available DATE/DATASOURCE/CAMPAIGN) request
```
curl --header "Content-Type: application/json" \
     --request POST \
     --data '{"dimensions": ["DATASOURCE","CAMPAIGN"]}' \
     http://localhost:8080/api/query/CLICKS
```

* Filtering request
```
curl --header "Content-Type: application/json" \
     --request POST \
     --data '{"filter": {"dimension": "DATE", "value": "2019-11-11"}}' \
     http://localhost:8080/api/query/CLICKS
```

* Grouping with filtering request
```
curl --header "Content-Type: application/json" \
     --request POST \
     --data '{"filter": {"dimension": "DATE", "value": "2019-11-11"}, "dimensions": ["DATASOURCE","CAMPAIGN"]}}' \
     http://localhost:8080/api/query/CLICKS
```

* Range request (only dates are handled, format needs to be exactly as specified in request)
```
curl --header "Content-Type: application/json" \
     --request POST \
     --data '{"range": {"from": "2019-11-11", "to": "2019-11-12"}}' \
     http://localhost:8080/api/query/CLICKS
```


* Request with range, filter and grouping with CTR
```
curl --header "Content-Type: application/json" \
     --request POST \
     --data '{"range": {"from": "2019-11-01", "to": "2019-11-12"}, "filter": {"dimension": "DATASOURCE", "value": "Twitter Ads"}, "dimensions":["CAMPAIGN"]}' \
     http://localhost:8080/api/query/CTR
```
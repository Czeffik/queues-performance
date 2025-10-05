# queues-performance

## Start prometheus
```shell
docker compose -f metrics.yaml up -d
```

Will start prometheus at [localhost:9090](localhost:9090) and grafana at [localhost:3000](localhost:3000) (admin/admin)
with pre configured [dashboards](http://localhost:3000/dashboards)

## Build application
```shell
gradle clean build
```

## Start application
```shell
java -XX:+UseNUMA -XX:+UseG1GC -Xms4G -Xmx4G -jar ./build/libs/queues-performance.jar
```

or use docker:
```shell
docker compose -f app.yaml up
```

or setup everything app, prometheus and grafana with:
```shell
docker compose up
```

## Run tests
To be able to run tests you have to:
- install python3
- install [prometheus](https://prometheus.io/download/#prometheus)

After that you should be able to run tests using command below:
```shell
python3 run_tests.py
```

It will create test results in `results` directory.

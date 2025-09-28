# queues-performance

## Start prometheus
```shell
docker compose -f metrics.yaml up -d
```


Will start prometheus at [localhost:9090](localhost:9090) and grafana at [localhost:3000](localhost:3000) (admin/admin)
with pre configured [dashboards](http://localhost:3000/dashboards)

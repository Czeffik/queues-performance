import subprocess
import time
from pathlib import Path

OUTPUT_DIR = Path("results")
OUTPUT_DIR.mkdir(exist_ok=True)

SCENARIOS = [
    {"application.queue.type": "ARRAY_BLOCKING_1k"},
    {"application.queue.type": "ARRAY_BLOCKING_10k"},
    {"application.queue.type": "ARRAY_BLOCKING_100k"},
    {"application.queue.type": "LINKED_BLOCKING"},
    {"application.queue.type": "CONCURRENT_NOT_BLOCKING"},
    {"application.queue.type": "CONCURRENT_NOT_BLOCKING_WITH_SEMAPHORE"},
    {"application.queue.type": "JC_TOOLS"},
    {"application.queue.type": "JC_TOOLS_WITH_SEMAPHORE"},
]

DOCKER_COMPOSE_FILE = "docker-compose.yaml"

RUN_DURATION = 10 * 60

def run_docker_compose_up(scenario):
    print(f"Starting docker-compose for scenario: {scenario}")
    subprocess.run(
        ["docker", "compose", "-f", DOCKER_COMPOSE_FILE, "up", "-d"],
        check=True,
        env={
            **dict(subprocess.os.environ),
            "APPLICATION_QUEUE_TYPE": scenario["application.queue.type"]
        }
    )

def run_docker_compose_down():
    print("Stopping docker-compose...")
    subprocess.run(
        ["docker", "compose", "-f", DOCKER_COMPOSE_FILE, "down"],
        check=True
    )

def copy_prometheus_data_to_json(scenario_idx):
    container_name = "prometheus"
    dest_dir = OUTPUT_DIR / f"scenario_{scenario_idx+1}" / "prometheus_data"
    dest_dir.mkdir(parents=True, exist_ok=True)

    print("Copying Prometheus data...")
    subprocess.run(["docker", "cp", f"{container_name}:/prometheus", str(dest_dir)], check=True)

    json_path = OUTPUT_DIR / f"scenario_{scenario_idx+1}" / "prometheus_metrics.json"
    print("Converting Prometheus TSDB to JSON...")
    subprocess.run([
        "promtool", "tsdb", "dump", str(dest_dir / "prometheus")
    ], stdout=open(json_path, "w"), check=True)
    print(f"Prometheus metrics JSON saved to {json_path}")

for idx, scenario in enumerate(SCENARIOS):
    try:
        run_docker_compose_up(scenario)
        print(f"Running scenario {idx+1}/{len(SCENARIOS)} for {RUN_DURATION} seconds...")
        time.sleep(RUN_DURATION)
        copy_prometheus_data_to_json(idx)
    finally:
        run_docker_compose_down()



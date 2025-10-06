# AI generated
import subprocess
from pathlib import Path

normalized_dir = Path("normalized")

prometheus_data_dir = Path("prometheus_data")
prometheus_data_dir.mkdir(exist_ok=True)


for json_file in normalized_dir.glob("*.prom"):
    print(f"Loading {json_file} ...")
    subprocess.run([
        "promtool", "tsdb", "create-blocks-from", "openmetrics",
        str(json_file),
        str(prometheus_data_dir)
    ], check=True)

print("All JSON files loaded successfully.")

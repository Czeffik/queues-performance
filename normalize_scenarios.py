# AI GENERATED
from pathlib import Path

FILE_NAME = "prometheus_metrics.json"
RESULTS_DIR = "results"
SCENARIO_DIR = "scenario_"

def scenario_results_file(index):
    return f"./{RESULTS_DIR}/{SCENARIO_DIR}{index + 1}/{FILE_NAME}"

def parse_promtool_line(line):
    try:
        meta, value_ts = line.strip().split("} ")
        value, ts = value_ts.strip().split()
        return meta + "}", float(value), int(ts)
    except ValueError:
        return None

def load_scenario(file_path):
    data = []
    with open(file_path, "r") as f:
        for line in f:
            parsed = parse_promtool_line(line)
            if parsed:
                data.append(parsed)
    if not data:
        raise ValueError(f"No valid data found in {file_path}")
    return data

def transform_metric_line(line: str) -> str:
    line = line.strip()
    if not line.startswith("{") or "}" not in line:
        raise ValueError("Line is not in expected format")

    labels_part, rest = line[1:].split("}", 1)
    labels = labels_part.split(", ")

    name_label = [l for l in labels if l.startswith("__name__=")][0]
    metric_name = name_label.split("=", 1)[1].strip('"')
    other_labels = [l for l in labels if not l.startswith("__name__=")]

    if other_labels:
        new_line = f'{metric_name}{{{",".join(other_labels)}}}{rest}'
    else:
        new_line = f'{metric_name}{rest}'

    return new_line

def normalize_scenarios(base_file, other_files, output_dir):
    output_dir = Path(output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)

    print(f"📥 Loading base scenario: {base_file}")
    base_data = load_scenario(base_file)
    base_timestamps = [t for _, _, t in base_data]
    sorted_timestamps = sorted(set(base_timestamps))

    print(f"✅ Loaded base scenario with {len(base_timestamps)} points")
    print(f"✅ Loaded base scenario with {len(sorted_timestamps)} unique timestamps")

    for file_path in other_files:
        file_path = Path(file_path)
        if not file_path.exists():
            print(f"❌ File not found: {file_path}")
            continue
        if file_path.is_dir():
            print(f"⚠️ {file_path} is a directory, skipping...")
            continue

        print(f"\n📊 Processing {file_path} ...")

        try:
            data = load_scenario(file_path)
        except Exception as e:
            print(f"❌ Error loading {file_path}: {e}")
            continue

        print(f"   Loaded {len(data)} lines from {file_path}")

        data.sort(key=lambda x: x[2])

        meta, values, timestamps = zip(*data)
        print("   Sorting done, normalizing timestamps...")

        normalized = []
        base_timestamp_index=0
        base_timestamp = -1
        previous_timestamp = -1
        for i in range(len(data)):
            current = data[i]
            if base_timestamp_index < len(base_timestamps):
                base_timestamp = base_timestamps[base_timestamp_index]

            current_timestamp = current[2]
            if current_timestamp != previous_timestamp:
                base_timestamp_index +=1

            current_metric_object = current[0]
            normalized.append((transform_metric_line(current_metric_object).strip(), current[1], current_timestamp/1000))
            previous_timestamp = current_timestamp
            if i % 50000 == 0:
                print(f"   Processed {i}/{len(values)} points")

        output_path = output_dir /  f"{file_path.parent.name}_normalized.prom"
        with open(output_path, "w") as out:
            for m, v, t in normalized:
                out.write(f"{m} {v} {t}\n")
            out.write("# EOF")

        print(f"✅ Saved normalized file to: {output_path}")


def normalize(number_of_scenarios):
    if number_of_scenarios < 3:
        return
    files=[scenario_results_file(i) for i in range(0, number_of_scenarios)]
    print(files)
    normalize_scenarios(
        base_file=scenario_results_file(0),
        other_files=files,
        output_dir="normalized"
    )
normalize(8)

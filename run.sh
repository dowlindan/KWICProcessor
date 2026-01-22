#!/usr/bin/env bash

# Exit immediately if a command fails
set -e
set -x

# ---- Configuration ----
SRC_DIR="src/main"
OUT_DIR="out"
MAIN_CLASS="edu.drexel.se311.kwic.Main"


if [ $# -lt 1 ]; then
  echo "Usage: ./run.sh <filename>"
  exit 1
fi

FILENAME="$1"

# ---- Compile ----

echo "Setting up output directory..."
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "Compiling Java sources..."
javac -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")

# ---- Run ----
echo "Running $MAIN_CLASS"
java -cp "$OUT_DIR" "$MAIN_CLASS" "$FILENAME"
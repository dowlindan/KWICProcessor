#!/usr/bin/env bash

# Exit immediately if a command fails
set -e
set -x

# ---- Configuration ----
SRC_DIR="src/main"
OUT_DIR="out"

# ---- Compile ----

echo "Setting up output directory..."
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "Compiling Java sources..."
javac -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")
jar cfve hw2.jar edu.drexel.se311.kwic.Main -C out .


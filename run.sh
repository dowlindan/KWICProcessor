#!/usr/bin/env bash

# Exit immediately if a command fails
set -e
set -x

# ---- Configuration ----
OUT_DIR="out"
MAIN_CLASS="edu.drexel.se311.kwic.Main"


if [ $# -lt 2 ]; then
  echo "Usage: ./run.sh <kwic-processing|keyword-search|index-generation> <config-filename>"
  exit 1
fi

COMMAND="$1"
CONFIG_FILENAME="$2"
    
# ---- Run ----
echo "Running $MAIN_CLASS"
java -cp "$OUT_DIR" "$MAIN_CLASS" "$COMMAND" "$CONFIG_FILENAME"
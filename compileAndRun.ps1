#!/usr/bin/env pwsh

# Exit immediately on error
$ErrorActionPreference = "Stop"
Set-StrictMode -Version Latest

# Echo commands (rough equivalent of `set -x`)
Set-PSDebug -Trace 1

# ---- Configuration ----
$SRC_DIR   = "src/main"
$OUT_DIR   = "out"
$MAIN_CLASS = "edu.drexel.se311.kwic.Main"

# ---- Arguments ----
if ($args.Count -lt 1) {
    Write-Host "Usage: ./run.ps1 <filename>"
    exit 1
}

$FILENAME = $args[0]

# ---- Compile ----
Write-Host "Setting up output directory..."

if (Test-Path $OUT_DIR) {
    Remove-Item -Recurse -Force $OUT_DIR
}
New-Item -ItemType Directory -Path $OUT_DIR | Out-Null

Write-Host "Compiling Java sources..."

$javaFiles = Get-ChildItem -Path $SRC_DIR -Recurse -Filter "*.java" |
             ForEach-Object { $_.FullName }

javac -d $OUT_DIR $javaFiles

# ---- Run ----
Write-Host "Running $MAIN_CLASS"
java -cp $OUT_DIR $MAIN_CLASS $FILENAME

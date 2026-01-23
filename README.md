# KWICProcessor
SE311 Course Project

## Description

This system currently only accepts plain text files containing sets of sentences and supports multiple text processing and indexing functions.

## Getting started

```
java -cp out edu.drexel.se311.kwic.Main ./inputs/sample.txt
``` 
should work out of the box.

To run the system, from the root directory:

### 1. Compile the program

#### Note: This shouldn't be necessary as the submission should have included a pre-compiled out folder

```
./compile.sh
```

which runs:

```
javac -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")
```

### 2. Run the program

```
./run.sh <input_file>
```

which runs:

```
java -cp "$OUT_DIR" "$MAIN_CLASS" "$FILENAME"
```

where 

```
SRC_DIR="src/main"
OUT_DIR="out"
MAIN_CLASS="edu.drexel.se311.kwic.Main"
```

and `<input_file>` should be chosen among the `./inputs` folder.

So,
```java -cp out edu.drexel.se311.kwic.Main ./inputs/sample.txt```
should work
#### Alternatively, do both in one and just run:

```./compileAndRun.sh <input_file>```

Based on the demo video, it looks like unix, but a (untested) powershell script is included just in case.

### 3. Use the console commands

Follow the prompts on screen.

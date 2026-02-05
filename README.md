# KWICProcessor
SE311 Course Project

## Description

This system accepts text files or csv files containing sets of sentences and supports multiple text processing and indexing functions.

## Getting started

Notes for the TA: 
- This was developed and compiled in WSL Ubuntu with the latest java version.

```
java -jar hw2.jar kwic-processing config.properties``` 
```

should work right away.

To run the system, from the root directory:

### 1. Compile the program

#### Note: This shouldn't be necessary as the submission should have included a pre-compiled out folder and jar file

```
./compile.sh
```

which runs:

```
javac -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")
jar cfve hw2.jar edu.drexel.se311.kwic.Main -C out .
```

where 

```
SRC_DIR="src/main"
OUT_DIR="out"
```

### 2. Run the program

```
./run.sh <kwic-processing|keyword-search|index-generation> <keyword if applicable> <config-filename>
```

which runs:

```
java -jar hw2.jar "$@"
```





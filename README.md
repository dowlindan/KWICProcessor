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

## HW2 Documentation

- Changed newline text parser to DelimTextParser so delim can be chosen as either newline or period
- CsvFileParser or PlaintextFileParser chooses different delims
- Added trivialWords and filterWords field to AbstractSentencesProcessor
- To fit passing which processor to use as a cli command into current input strategy, made a child class wihch stores commands as a list of strings. This also allows for future extension such as multiple commands
- The run() method in KWICDriver does not depend on concrete implementations of abstractions, but the fromConfig file does.
- Added more abstract fields to KWICDriver
- Chose not to do anything with keywords for circular shifts since it made more sense to me.
- For txt output, the folder must already exist





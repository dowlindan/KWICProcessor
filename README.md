# KWICProcessor
SE311 Course Project

## Description

This system is a client-server system that ingests text files or csv files containing sets of sentences and supports multiple text processing and indexing functions.
 
## Quick Start Guide (for graders)

```
./runServer.sh server_config.properties
```
Then in another terminal:
```
./runClient.sh
```


## How to run the system

### 1. Compile the program

#### Note: This shouldn't be necessary as the submission should have included a pre-compiled out folder and jar files

```
./compile.sh
```

which runs:

```
javac -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")
jar cfve KWICClient.jar edu.drexel.se311.kwic.KWICClient -C out .
jar cfve KWICServer.jar edu.drexel.se311.kwic.KWICServer -C out .
```

where 

```
SRC_DIR="src/main"
OUT_DIR="out"
```

### 2. Run the program

```
./runServer.sh server_config.properties
```
which runs 
```
java -jar KWICServer.jar "$@"
```
Then in another terminal:
```
./runClient.sh
```
which runs
```
java -jar KWICClient.jar "$@"
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
- Removed unnecessary concrete class in textparsing

## HW3 Documentation

- Moved OptionReader classes to own package
- Added KWICInputStream InputStrategy for server
- Added KWICOutputStream output strategy for server
- Added KWICProtocolMessage to package messages before sending
- Added ServerRequestTracker to track sever requests across all threads
- Made TxtOutput strategy always append
- Added KWICClient class
- Removed parsing responsibility from KWICDriver to KWICServer
- Added KWICRequestHandler thread extender which server delegates to
- Added KWICServer class
- Removed main

### Submissions

- UML diagrams are in ./uml_diagrams
- DSM outputs are in ./hw1, ./hw2, and ./hw3

HW1
M Score: 54.53%
Propagation Cost: 21.05%
Decoupling Level: 54.15%

HW2
M Score: 67.24%
Propagation Cost: 16.15%
Decoupling Level: 59.02%

HW3
M Score: 67.59%
Propagation Cost: 13.22%
Decoupling Level: 58.43%




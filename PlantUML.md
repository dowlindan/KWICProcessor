@startuml
skinparam classAttributeIconSize 0

' =====================
' Abstract File Parser
' =====================
abstract class AbstractFileParser {
  #file : File
  +AbstractFileParser()
  +openFile(fileName : String) : void
  +closeFile() : void
  +getSentencesAsArray() : List<String>
}

' =====================
' Concrete File Parser
' =====================
class PlaintextFileParser {
  +getSentencesAsArray() : List<String>
}

' =====================
' Sorting Strategy
' =====================
abstract class SortingStrategy {
  #input : Collection<String>
  +SortingStrategy(input : Collection<String>)
  +sort(): List<String>
}

class AlphabetizedSorter {
  +sort(): List<String>
}

' =====================
' Abstract Sentences Processor
' =====================
abstract class AbstractSentencesProcessor {
  -inputSentences : List<String>
  -sortingStrategy : SortingStrategy
  +AbstractSentencesProcessor(
      inputSentences : List<String>,
      sortingStrategy : SortingStrategy
    )
  +getInputSentences() : List<String>
  +getProcessedOutput() : List<String>
}

' =====================
' Concrete Sentences Processors
' =====================
class KWICProcessor {
  -sentences : List<LinkedList<String>>
  +getProcessedOutput() : List<String>
}

class KeywordSearch {
  +getProcessedOutput() : List<String>
}

class IndexGeneration {
  #wordIndexMap : Map<String, List<Integer>>
  +getProcessedOutput() : List<String>
}

' =====================
' Input / Output Strategies
' =====================
abstract class InputStrategy {
  +getCommand() : String
}

class ConsoleInput {
  +getCommand() : String
}

abstract class OutputStrategy {
  +display(output : String) : void
}

class ConsoleOutput {
  +display(output : String) : void
}

' =====================
' Commands Enum
' =====================
enum Commands {
  KWIC
  SEARCH
  INDEX
}

' =====================
' Driver & Entry Point
' =====================
class KWICDriver {
  -inputStrategy : InputStrategy
  -outputStrategy : OutputStrategy
  +run() : void
}

class Main {
  +main(args : String[]) : void
}

' =====================
' Inheritance
' =====================
AbstractFileParser <|-- PlaintextFileParser

AbstractSentencesProcessor <|-- KWICProcessor
AbstractSentencesProcessor <|-- KeywordSearch
AbstractSentencesProcessor <|-- IndexGeneration

SortingStrategy <|-- AlphabetizedSorter

InputStrategy <|-- ConsoleInput
OutputStrategy <|-- ConsoleOutput

' =====================
' Relationships
' =====================
Main --> KWICDriver : starts

KWICDriver --> Commands : uses

KWICDriver --> InputStrategy : uses
KWICDriver --> OutputStrategy : uses

KWICDriver --> AbstractFileParser : selects parser\nbased on file ext
KWICDriver --> AbstractSentencesProcessor : selects processor\nbased on command

AbstractSentencesProcessor --> SortingStrategy : uses

@enduml

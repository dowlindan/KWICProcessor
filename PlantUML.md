@startuml
skinparam classAttributeIconSize 0

' =====================
' Abstract File Parser
' =====================
abstract class AbstractFileParser {
  #filePath : String
  +setFilePath(filePath : String) : void
  {abstract} +getSentencesAsList() : List<String>
}

' =====================
' Concrete File Parser
' =====================
class PlaintextFileParser {
  +getSentencesAsList() : List<String>
}

' =====================
' Sorting Strategy
' =====================
abstract class SortingStrategy {
  {abstract} +sort(Collection<String>): Collection<String>
}

class AlphabetizedSorter {
  +sort(Collection<String>): Collection<String>
}

' =====================
' Abstract Sentences Processor
' =====================
abstract class AbstractSentencesProcessor {
  #inputSentences : List<String>
  #sortingStrategy : SortingStrategy
  +AbstractSentencesProcessor(inputSentences : List<String>, sortingStrategy : SortingStrategy)
  {abstract} +getProcessedOutput() : List<String>
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
  +open(): void
  +close(): void
  {abstract} +getCommand() : String
}

class ConsoleInput {
  +getCommand() : String
}

abstract class OutputStrategy {
  {abstract} +display(output : String) : void
}

class ConsoleOutput {
  +display(output : String) : void
}

' =====================
' Commands Enum
' =====================
class Commands {
  {static} +String KWIC
  {static} +String SEARCH
  {static} +String INDEX
  {static} + String QUIT
}

' =====================
' Driver & Entry Point
' =====================
class KWICDriver {
  -inputStrategy : InputStrategy
  -outputStrategy : OutputStrategy
  +KWICDriver(inputStrategy: InputStratgy, outputStrategy: OutputStrategy)
  -sentences : List<String>
  +loadFile(String filename)
  -displayUsage(): void
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

KWICDriver --o InputStrategy : uses
KWICDriver --o OutputStrategy : uses

KWICDriver --o AbstractFileParser : selects parser\nbased on file ext
KWICDriver --o AbstractSentencesProcessor : selects processor\nbased on command

AbstractSentencesProcessor --o SortingStrategy : uses

@enduml

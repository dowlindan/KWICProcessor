@startuml
skinparam classAttributeIconSize 0

' =====================
' Line Representation
' =====================
class Line {
  -content : String
  -lineNumber : int
  +Line(content : String, lineNumber : int)
  +getContent() : String
  +getLineNumber() : int
}

' =====================
' Abstract File Parser
' =====================
abstract class AbstractFileParser {
  #filePath : String
  #textParser : AbstractTextParser
  +AbstractFileParser(textParser: AbstractTextParser)
  +setFilePath(filePath : String) : void
  {abstract} +getSentencesAsList() : List<String>
  {abstract} +getSentencesAsLines() : List<Line>
}

' =====================
' Abstract Text Parser
' =====================
abstract class AbstractTextParser {
  {abstract} +parseSentencesAsList(String rawText) : List<String>
  {abstract} +parseSentencesAsLines(String rawText) : List<Line>
}

' =====================
' Concrete Text Parsers
' =====================
class NewlineTextParser {
  +parseSentencesAsList(String rawText) : List<String>
  +parseSentencesAsLines(String rawText) : List<Line>
}

class SentenceTextParser {
  +parseSentencesAsList(String rawText) : List<String>
  +parseSentencesAsLines(String rawText) : List<Line>
}

' =====================
' Concrete File Parser
' =====================
class PlaintextFileParser {
  +PlaintextFileParser(textParser: AbstractTextParser)
  +getSentencesAsList() : List<String>
  +getSentencesAsLines() : List<Line>
}

' =====================
' Sorting Strategy
' =====================
abstract class SortingStrategy {
  {abstract} +sort(List<String>) : List<String>
  {abstract} +sort(List<Line>) : List<Line>
}

class AlphabetizedSorter {
  +sort(List<String>) : List<String>
  +sort(List<Line>) : List<Line>
}

' =====================
' Abstract Sentences Processor
' =====================
abstract class AbstractSentencesProcessor {
  #inputLines : List<Line>
  #sortingStrategy : SortingStrategy
  +AbstractSentencesProcessor(inputSentences : List<String>, sortingStrategy : SortingStrategy)
  {abstract} +getProcessedOutput() : List<String>
}

' =====================
' Concrete Sentences Processors
' =====================
class KWICProcessor {
  -circularShifts : List<LinkedList<String>>
  +KWICProcessor(inputSentences : List<String>, sortingStrategy : SortingStrategy)
  +getProcessedOutput() : List<String>
}

class KeywordSearch {
  +KeywordSearch(inputSentences : List<String>, sortingStrategy : SortingStrategy)
  +getProcessedOutput() : List<String>
}

class IndexGeneration {
  +IndexGeneration(inputSentences : List<String>, sortingStrategy : SortingStrategy)
  #wordIndexMap : Map<String, List<Integer>>
  +getProcessedOutput() : List<String>
}

' =====================
' Input / Output Strategies
' =====================
abstract class InputStrategy {
  +open() : void
  +close() : void
  {abstract} +getCommand() : String
}

class ConsoleInput {
  +open() : void
  +close() : void
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
  {static} +String QUIT
}

' =====================
' Driver & Entry Point
' =====================
class KWICDriver {
  -inputStrategy : InputStrategy
  -outputStrategy : OutputStrategy
  -sentences : List<Line>
  +KWICDriver(inputStrategy : InputStrategy, outputStrategy : OutputStrategy)
  +loadFile(filename : String)
  -displayUsage() : void
  +run() : void
}

class Main {
  +main(args : String[]) : void
}

' =====================
' Inheritance
' =====================
AbstractFileParser <|-- PlaintextFileParser

AbstractTextParser <|-- NewlineTextParser
AbstractTextParser <|-- SentenceTextParser

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

AbstractFileParser --o AbstractTextParser : has

AbstractSentencesProcessor --o Line : aggregates
AbstractSentencesProcessor --o SortingStrategy : uses

@enduml

'''
' Accommodation for future change 1: input format changes:
'   Using strategy pattern for file parsing, adding CsvFileParser, etc.
'   Which strategy to used is determined on file extension.
'
' Accommodation for future change 2: Index Generation Policy changes
' such as filtering of stop words:
'   Extending the IndexGeneration class, to make it more complex, and passing of new fields to
'   AbstractSentencesProcessor, such as keywords, stopwords, etc.
'
' Accommodation for future change 3: Alphabetizing Policy Changes to user-selectable:
'   Creation of new SortingStrategy child class, and a new process in KWICDriver that
'   allows command input to specify how to sort
'
' Accommodation for future change 4: Output Method Changes:
'   New child classes for output (and input) strategy classes. For example, HTMLOutput
'   would be responsible for wrapping the text in HTML.
'''

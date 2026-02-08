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
  +AbstractFileParser()
  +AbstractFileParser(textParser: AbstractTextParser)
  +setTextParser(textParser: AbstractTextParser)
  +setFilePath(filePath : String) : void
  {abstract} +getSentencesAsList() : List<String>
  {abstract} +getSentencesAsLines() : List<Line>
}

' =====================
' Abstract Text Parser
' =====================
abstract class AbstractTextParser {
  {abstract} +parseSentencesAsLines(String rawText) : List<Line>
}

' =====================
' Concrete Text Parsers
' =====================
class DelimTextParser {
  -delim: String
  +DelimTextParser(delim: String)
  +parseSentencesAsLines(String rawText) : List<Line>
}

' =====================
' Concrete File Parser
' =====================
class PlaintextFileParser {
  -DELIM: String
  +PlaintextFileParser()
  +getSentencesAsLines() : List<Line>
}

class CsvFileParser {
  -DELIM: String
  +CsvFileParser()
  +getSentencesAsLines() : List<Line>
}

' =====================
' Sorting Strategy
' =====================
abstract class SortingStrategy {
  {abstract} +sort(List<String>) : List<String>
  {abstract} +sort(List<Line>) : List<Line>
}

class AlphabeticSorter {
  +sort(List<String>) : List<String>
  +sort(List<Line>) : List<Line>
}

class ReverseAlphabeticSorter {
  +sort(List<String>) : List<String>
  +sort(List<Line>) : List<Line>
}

' =====================
' Abstract Sentences Processor
' =====================
abstract class AbstractSentencesProcessor {
  #inputLines : List<Line>
  #trivialWords: Set<String>
  #sortingStrategy : SortingStrategy
  #boolean: filterWords
  +AbstractSentencesProcessor(inputSentences : List<String>, trivialWords : Set<String>, sortingStrategy : SortingStrategy, filterWords: boolean)
  #isWordTrivial(word: String) : boolean
  {abstract} +getProcessedOutput() : List<String>
}

' =====================
' Concrete Sentences Processors
' =====================
class KWICProcessor {
  -circularShifts : List<LinkedList<String>>
  +KWICProcessor(inputSentences : List<String>, trivialWords : Set<String>, sortingStrategy : SortingStrategy, filterWords: boolean)
  +getProcessedOutput() : List<String>
}

class KeywordSearch {
  -keyword: String
  +KeywordSearch(inputSentences : List<String>, trivialWords : Set<String>, sortingStrategy : SortingStrategy, filterWords: boolean, keyword: String)
  +getProcessedOutput() : List<String>
}

class IndexGeneration {
  +IndexGeneration(inputSentences : List<String>, trivialWords : Set<String>, sortingStrategy : SortingStrategy, filterWords: boolean)
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

class CommandsAsStringListInput {
  -index: int
  -commands: List<String>
  +addCommand(String command): void
  +getCommand() : String
}

abstract class OutputStrategy {
  {abstract} +display(output : String) : void
  +display(outputStrings: List<String>) : void
}

abstract class TxtOutput {
  -firstLinewritten: boolean
  -outputFilename: String
  +setOutputFilename(outputFilename: String) : void
  +display(output : String) : void
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
  -fileParser: AbstractFileParser
  -inputStrategy : InputStrategy
  -outputStrategy : OutputStrategy
  -sortingStrategy: SortingStrategy
  -filterWords: boolean
  -trivialWords: Set<String>
  -keyword: String
  -sentences : List<Line>
  +KWICDriver(filename : String, fileParser: AbstractFileParser, inputStrategy : InputStrategy, outputStrategy : OutputStrategy, sortingStrategy : SortingStrategy, filterWords : boolean, trivialWords : Set<String>, keyword : String)
  +fromConfig(command : String, keyword : String, configFilename : String) : KWICDriver
  +loadFile(filename : String)
  -displayUsage() : void
  -getCommand() : void
  -getProcessorFromCommand(command: String) : AbstractSentencesProcessor
  +run() : void
}

class Main {
  +main(args : String[]) : void
}

class KWICObjectLoader {
  +loadObject(classname : String) : Object
}

class OptionReader {
  -userOptions: HashMap<String, String>
  -kwicObjLoader: KWICObjectLoader
  -OptionReader() : OptionReader
  +readOptions(configFilepath: String)
  +getObjectFromKey(keyStr: String) : Object
  +getObjectFromStr(objStr: String) : Object
  +getString(keyStr : String) : String
}
' =====================
' Inheritance
' =====================
AbstractFileParser <|-- PlaintextFileParser
AbstractFileParser <|-- CsvFileParser

AbstractTextParser <|-- DelimTextParser

AbstractSentencesProcessor <|-- KWICProcessor
AbstractSentencesProcessor <|-- KeywordSearch
AbstractSentencesProcessor <|-- IndexGeneration

SortingStrategy <|-- AlphabeticSorter
SortingStrategy <|-- ReverseAlphabeticSorter

InputStrategy <|-- ConsoleInput
InputStrategy <|-- CommandsAsStringListInput
OutputStrategy <|-- ConsoleOutput
OutputStrategy <|-- TxtOutput

' =====================
' Relationships
' =====================
Main --> KWICDriver : starts
OptionReader --o KWICObjectLoader

KWICDriver --> Commands : uses
KWICDriver --> OptionReader
KWICDriver --o InputStrategy
KWICDriver --o OutputStrategy
KWICDriver --o SortingStrategy

KWICDriver --o AbstractFileParser : selects parser\nbased on file ext
KWICDriver --> AbstractSentencesProcessor : selects processor\nbased on command

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

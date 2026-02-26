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
' Concrete File Parsers
' «Strategy»
' =====================
class PlaintextFileParser <<Strategy>> {
  -DELIM: String
  +PlaintextFileParser()
  +getSentencesAsLines() : List<Line>
}

class CsvFileParser <<Strategy>> {
  -DELIM: String
  +CsvFileParser()
  +getSentencesAsLines() : List<Line>
}

' =====================
' Sorting Strategy «Strategy»
' =====================
abstract class SortingStrategy {
  {abstract} +sort(List<String>) : List<String>
  {abstract} +sort(List<Line>) : List<Line>
}

class AlphabeticSorter <<Strategy>> {
  +sort(List<String>) : List<String>
  +sort(List<Line>) : List<Line>
}

class ReverseAlphabeticSorter <<Strategy>> {
  +sort(List<String>) : List<String>
  +sort(List<Line>) : List<Line>
}

' =====================
' Abstract Sentences Processor
' =====================
abstract class AbstractSentencesProcessor {
  #inputLines : List<Line>
  #trivialWords: Set
  #sortingStrategy : SortingStrategy
  #boolean: filterWords
  +AbstractSentencesProcessor(inputSentences : List<Line>, trivialWords : Set, sortingStrategy : SortingStrategy, filterWords: boolean)
  #isWordTrivial(word: String) : boolean
  {abstract} +getProcessedOutput() : List<String>
}

' =====================
' Concrete Sentences Processors
' =====================
class KWICProcessor {
  -circularShifts : List<LinkedList<String>>
  +KWICProcessor(inputSentences : List<Line>, trivialWords : Set, sortingStrategy : SortingStrategy, filterWords: boolean)
  +getProcessedOutput() : List<String>
}

class KeywordSearch {
  -keyword: String
  +KeywordSearch(inputSentences : List<Line>, trivialWords : Set, sortingStrategy : SortingStrategy, filterWords: boolean, keyword: String)
  +getProcessedOutput() : List<String>
}

class IndexGeneration {
  #wordIndexMap : Map<String, List>
  +IndexGeneration(inputSentences : List<Line>, trivialWords : Set, sortingStrategy : SortingStrategy, filterWords: boolean)
  +getProcessedOutput() : List<String>
}

' =====================
' Input Strategy «Strategy»
' =====================
abstract class InputStrategy {
  +open() : void
  +close() : void
  {abstract} +getCommand() : String
}

class ConsoleInput <<Strategy>> {
  +open() : void
  +close() : void
  +getCommand() : String
}

class CommandsAsStringListInput <<Strategy>> {
  -index: int
  -commands: List<String>
  +addCommand(String command): void
  +getCommand() : String
}

class KWICInputStream <<Strategy>> {
  -inputStream: InputStream
  -reader: BufferedReader
  +KWICInputStream(inputStream: InputStream)
  +open() : void
  +close() : void
  +getCommand() : String
}

' =====================
' Output Strategy «Strategy»
' =====================
abstract class OutputStrategy {
  {abstract} +display(output : String) : void
  +display(outputStrings: List<String>) : void
}

abstract class TxtOutput <<Strategy>> {
  -firstLineWritten: boolean
  -outputFilename: String
  +setOutputFilename(outputFilename: String) : void
  +display(output : String) : void
}

class ConsoleOutput <<Strategy>> {
  +display(output : String) : void
}

class KWICOutputStream <<Strategy>> {
  -outputStream: OutputStream
  -writer: BufferedWriter
  +KWICOutputStream(outputStream: OutputStream)
  +display(output : String) : void
  +display(outputStrings: List<String>) : void
  +logKeywordSearch(message: KWICProtocolMessage) : void
}

' =====================
' KWICProtocolMessage
' =====================
class KWICProtocolMessage {
  -messages : List<String>
  -messageLines : List<String>
  +KWICProtocolMessage(messages: List<String>)
  +getMessages() : List<String>
  +setMessages(messages: List<String>) : void
  +getMessageLines() : List<String>
  +setMessageLines(messageLines: List<String>) : void
  +toMessageString() : String
}

' =====================
' ServerRequestTracker «Singleton»
' =====================
class ServerRequestTracker <<Singleton>> {
  -{static} instance : ServerRequestTracker
  -successfulSearches : AtomicInteger
  -totalSearches : AtomicInteger
  -ServerRequestTracker()
  +{static} getInstance() : ServerRequestTracker
  +incrementSuccessfulSearches() : void
  +getSuccessfulSearches() : int
  +incrementTotalSearches() : void
  +getTotalSearches() : int
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
' Driver
' =====================
class KWICDriver {
  -fileParser: AbstractFileParser
  -inputStrategy : InputStrategy
  -outputStrategy : OutputStrategy
  -sortingStrategy: SortingStrategy
  -filterWords: boolean
  -trivialWords: Set
  -keyword: String
  -sentences : List<String>
  +KWICDriver(filename : String, fileParser: AbstractFileParser, inputStrategy : InputStrategy, outputStrategy : OutputStrategy, sortingStrategy : SortingStrategy, filterWords : boolean, trivialWords : Set, keyword : String)
  +fromConfig(command : String, keyword : String, configFilename : String) : KWICDriver
  +loadFile(filename : String)
  -displayUsage() : void
  -getCommand() : void
  -getProcessorFromCommand(command: String) : AbstractSentencesProcessor
  +run() : void
}

' =====================
' KWICRequestHandler
' =====================
class KWICRequestHandler {
  -driver: KWICDriver
  -clientSocket: Socket
  +KWICRequestHandler(driver: KWICDriver, clientSocket: Socket)
  +run() : void
}

' =====================
' KWICServer
' =====================
class KWICServer {
  -{static} PORT_NUMBER : int
  -{static} LOG_FILE : String
  +{static} main(args: String[]) : void
  +{static} fromServerConfig(configFilename: String, loggingFile: String, clientSocket: Socket) : KWICServer
}

' =====================
' Support Classes
' =====================
class KWICObjectLoader {
  +loadObject(classname : String) : Object
}

class OptionReader {
  -userOptions: HashMap
  -kwicObjLoader: KWICObjectLoader
  -OptionReader() : OptionReader
  +readOptions(configFilepath: String)
  +getObjectFromKey(keyStr: String) : Object
  +getObjectFromStr(objStr: String) : Object
  +getString(keyStr : String) : String
}

' =====================
' Thread (external)
' =====================
class Thread <<external>> {
  +run() : void
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
InputStrategy <|-- KWICInputStream
OutputStrategy <|-- ConsoleOutput
OutputStrategy <|-- TxtOutput
OutputStrategy <|-- KWICOutputStream
Thread <|-- KWICRequestHandler

' =====================
' Relationships
' =====================
KWICServer --> KWICDriver : initializes
KWICServer --> KWICRequestHandler : creates & starts
KWICServer --> OptionReader : uses via fromServerConfig
OptionReader --o KWICObjectLoader
KWICDriver --> Commands : uses
KWICDriver --> OptionReader
KWICDriver --o InputStrategy : «Strategy»
KWICDriver --o OutputStrategy : «Strategy»
KWICDriver --o SortingStrategy : «Strategy»
KWICDriver --o AbstractFileParser : «Strategy»\nselects on file ext
KWICDriver --> AbstractSentencesProcessor : selects processor\nbased on command
AbstractFileParser --o AbstractTextParser : has
AbstractSentencesProcessor --o Line : aggregates
AbstractSentencesProcessor --o SortingStrategy : «Strategy»
KWICRequestHandler --o KWICDriver
KWICOutputStream --> KWICProtocolMessage : uses
KWICOutputStream --> ServerRequestTracker : «Singleton»

@enduml
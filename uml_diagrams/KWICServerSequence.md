@startuml KWIC Server Sequence Diagram

skinparam sequence {
  ArrowColor DarkBlue
  ActorBorderColor DarkBlue
  LifeLineBorderColor DarkBlue
  LifeLineBackgroundColor LightBlue
  ParticipantBorderColor DarkBlue
  ParticipantBackgroundColor LightBlue
  ParticipantFontName Arial
  BoxBorderColor DarkBlue
  NoteBackgroundColor LightYellow
  NoteBorderColor DarkBlue
}

participant KWICServer
participant ServerSocket
participant ClientSocket
participant OptionReader
participant KWICInputStream
participant KWICOutputStream
participant KWICDriver
participant KWICRequestHandler
participant AbstractSentencesProcessor
participant SortingStrategy

loop server running

  KWICServer -> ServerSocket : new ServerSocket(port)
  activate ServerSocket
  ServerSocket --> KWICServer : serverSocket

  KWICServer -> ServerSocket : accept()
  note right : blocks until client connects
  ServerSocket --> KWICServer : clientSocket
  activate ClientSocket

  KWICServer -> KWICDriver : fromServerConfig(configFilename, logFile, clientSocket)
  activate KWICDriver

    KWICDriver -> OptionReader : getInputFile()
    activate OptionReader
    OptionReader --> KWICDriver : inputFile

    KWICDriver -> OptionReader : getAbstractFileParser()
    OptionReader --> KWICDriver : abstractFileParser

    KWICDriver -> OptionReader : getSortingStrategy()
    OptionReader --> KWICDriver : sortingStrategy

    KWICDriver -> OptionReader : getFilterWords()
    OptionReader --> KWICDriver : filterWords

    KWICDriver -> OptionReader : getTrivialWordSet()
    OptionReader --> KWICDriver : trivialWordSet
    deactivate OptionReader

    KWICDriver -> KWICInputStream : new KWICInputStream(clientSocket)
    activate KWICInputStream
    KWICInputStream --> KWICDriver : inputStrategy

    KWICDriver -> KWICOutputStream : new KWICOutputStream(clientSocket)
    activate KWICOutputStream
    KWICOutputStream --> KWICDriver : outputStrategy

  KWICDriver --> KWICServer : driver
  deactivate KWICDriver

  KWICServer -> KWICRequestHandler : new KWICRequestHandler(clientSocket, driver)
  activate KWICRequestHandler
  KWICServer -> KWICRequestHandler : start()

  note right of KWICRequestHandler : runs in new thread

  KWICRequestHandler -> KWICDriver : run()
  activate KWICDriver

    KWICDriver -> KWICDriver : loadFile(inputFile)
    KWICDriver -> KWICInputStream : open()

    loop until no more commands

      KWICDriver -> KWICInputStream : getCommand()
      KWICInputStream --> KWICDriver : command

      KWICDriver -> AbstractSentencesProcessor : new AbstractSentencesProcessor(command)
      activate AbstractSentencesProcessor

      KWICDriver -> AbstractSentencesProcessor : getProcessedOutput()
      AbstractSentencesProcessor -> SortingStrategy : sort(sentences)
      activate SortingStrategy
      SortingStrategy --> AbstractSentencesProcessor : sortedOutput
      deactivate SortingStrategy
      AbstractSentencesProcessor --> KWICDriver : processedOutput
      deactivate AbstractSentencesProcessor

      KWICDriver -> KWICOutputStream : display(processedOutput)
      KWICOutputStream --> KWICDriver : void

    end

    KWICDriver -> KWICInputStream : close()
    deactivate KWICInputStream
    deactivate KWICOutputStream

  deactivate KWICDriver
  deactivate KWICRequestHandler
  deactivate ClientSocket
  deactivate ServerSocket

end

@enduml

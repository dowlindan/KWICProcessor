@startuml KWICClient

skinparam classAttributeIconSize 0




class KWICClient {
    ' -- Constants --
    - {static} SERVER_HOSTNAME : String
    - {static} SERVER_PORT : int
    - {static} TIMEOUT_MS : int

    ' -- Fields --
    - socket : Socket
    - bufferedWriter : BufferedWriter
    - bufferedReader : BufferedReader
    - inputStrategy : InputStrategy
    - outputStrategy : OutputStrategy

    ' -- Methods --
    + {static} main(args : String[]) : void
    + connect() : void
    + runClientLoop() : void
}

class Commands {
}

class InputStrategy {

}

class OutputStrategy {
}

KWICClient ..> Commands : «uses»
KWICClient --o InputStrategy
KWICClient --o OutputStrategy

@enduml

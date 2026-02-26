@startuml KWIC Client Sequence Diagram

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

participant KWICClient
participant Socket
participant InputStream
participant OutputStream
participant InputStrategy
participant OutputStrategy

KWICClient -> Socket : new Socket(host, port)
activate Socket
Socket --> KWICClient : socket
KWICClient -> Socket : getInputStream()
Socket --> KWICClient : inputStream
KWICClient -> Socket : getOutputStream()
Socket --> KWICClient : outputStream

loop until user quits

  KWICClient -> InputStrategy : getInput()
  activate InputStrategy
  InputStrategy --> KWICClient : inputString
  deactivate InputStrategy

  KWICClient -> OutputStream : write(inputString)
  activate OutputStream
  OutputStream --> KWICClient : void
  deactivate OutputStream

  KWICClient -> Socket : setSoTimeout(timeout)
  Socket --> KWICClient : void

  KWICClient -> InputStream : readString()
  activate InputStream
  InputStream --> KWICClient : resultString
  deactivate InputStream

  KWICClient -> OutputStrategy : display(resultString)
  activate OutputStrategy
  OutputStrategy --> KWICClient : void
  deactivate OutputStrategy

  KWICClient -> Socket : setSoTimeout(0)
  Socket --> KWICClient : void

end

KWICClient -> Socket : close()
deactivate Socket

@enduml

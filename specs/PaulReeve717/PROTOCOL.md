# Calculator Protocol Design

## Transport Protocol

TCP will be used because it will assure us that the message come to destination

## Find the server

The client will find the server by opening a communication on the server on the server's localhost at the port 2000

## Who speaks first ?

The client speaks first

## Messages Sequence

- Client greets Server

- Server accepts/refuses Client connection

- Client ask an operation

- Server respond with the operation result

- Client ask another operation or send a finish message

- Server close connection if client send finish

## Messages syntax

"#" mean a number is required

- Greet message: HI

- Reply message: ACK

- Operation demand message: [ADD|MPY] #OP1 #OP2

- Result: #RESULT

- Finish messae: DONE

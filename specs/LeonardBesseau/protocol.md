# Protocol design

## Transport protocol
TCP

## Finding server
The client knows the adresses or make a DNS query to find them
The port is 4321

## Order
The client is always the one to initiate the communication.

## Flow

- OPERATION:  Client sends operation and 2 numbers
- RES:        Servers send back result or error
- QUIT:       Client stop connection 

## Semantics
### Client
- OPERATION:  - NA (the client is the sender)
- RES:        Check if server returned error and get result
- QUIT:       - NA (the client is the sender)

### Server
- OPERATION:  - check if operation is supported, check if number are valid  
- RES:        NA (the server is the sender)  
- QUIT:       - Close connection to client  

## Syntax
- OPERATION: Operation\tNumber\tNumber
- RES: status\tnumber(optional)
- QUIT: EMPTY

## End of connection
The client is always the one to close the connection. He sends a quit message to the server to let it know, it can close the connection on its side.



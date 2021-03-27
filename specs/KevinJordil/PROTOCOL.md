# Specs - Kevin Jordil
## What transport protocol do we use?
TCP
## How does the client find the server (addresses and ports)?
The IP address of the server is that of the machine, the port must be chosen when launching the server. For the web the default port is 80.
In order for our client to be able to connect to our server, it must be provided with the address and port of our server in one way or another.
The port used will be 8080
## Who speaks first?
The client because it is he who needs the server. The server, on the other hand, must already be waiting for a connection
## What is the sequence of messages exchanged by the client and the server? (flow)
Once the connection is established, the client can directly send a request to the server (multiplication or addition)
SEVER - Wait for connection
CLIENT - Connect to the server
SERVER - Accept connection
CLIENT - Connection established
CLIENT - ADD 2 3
SERVER - OK 5
CLIENT - MULT 3 4
SERVER - OK 12
CLIENT - AB CD EF
SERVER - NOK Unsupported
## What happens when a message is received from the other party? (semantics)
When the server receives a request it will check that it is compliant and therefore understand it. It will then process it and send the response
When the client receives a request, it is the response to one of these requests, so it can read the response.
## What is the syntax of the messages? How we generate and parse them? (syntax)
The first idea is to do it as an assembly instruction: ADD 10 2 or MULT 2 3
It is then easy for the server to parse the request with spaces.
If the calculator was more complex it would have been interesting to use json.
## Who closes the connection and when?
The client closes the connection once it has finished doing these operations and therefore when the user closes the calculator.

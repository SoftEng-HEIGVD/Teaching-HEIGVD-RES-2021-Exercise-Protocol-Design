## Laboratoire 3

# What transport protocol do we use?
We will use the TCP protocol as transport protocol.

# How does the client find the server (addresses and ports)?
The client need to be on the same port as the server (port 80) and fetch the IP address of the server.

# Who speaks first?
The client.

# What is the sequence of messages exchanged by the client and the server? (flow)
Client        ----------> Server
Connexion

Client        <---------- Server
                          Sends succesful or failure response

Client        ----------> Server
Sends operation

Client        <---------- Server
                          Send answer of the operation

Client        ----------> Server
Exit

Client        <---------- Server
                          Closes the connection

# What happens when a message is received from the other party? (semantics)
The server needs to check the correct format of the request and also if the operation is valid or not.
If so, the server has to send either the result or an error.

# What is the syntax of the messages? How we generate and parse them? (syntax)
Client : CALC (for connexion)

Server : ACK

Client : [OPE] VALUE1 VALUE2 (OPE could be ADD, SUB, MUL, etc...)

Server : RESULT VALUE3 or ERROR

Client : EXIT

# Who closes the connection and when?
The client closes the connection when he has finished.
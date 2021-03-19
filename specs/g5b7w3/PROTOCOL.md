### What transport protocol do we use?
We use the TCP protocol
### How does the client find the server (addresses and ports)?
The server IP address and the port 2021
### Who speaks first?
The client to initiate the connection
### What is the sequence of messages exchanged by the client and the server? (flow)
-Client : Try to connect
-Server : Response 
-Client : Send an operation 
-Server : Respond with the answer
-Client : The client end the communication
-Server : Close the connexion
### What happens when a message is received from the other party? (semantics)
Client : Check the answer received by the server 
Server : Check what the client is asking for and do it
### What is the syntax of the messages? How we generate and parse them? (syntax
The token are separated by spaces.
The messages will look like that : "OPERATION NUMBER1 NUMBER2"
### Who closes the connection and when?
If the token "OPERATION" is equal to the char 'q', the server will close the connexion

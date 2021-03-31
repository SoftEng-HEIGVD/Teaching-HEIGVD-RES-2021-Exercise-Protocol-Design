# RES - Exercise-protocol-Design 2021

Authors : Jean-Luc Blanc

Date : 31.03.2021

------

### What transport protocol do we use?

TCP/IP, most used and popular one. It is also the default one with Java sockets.

### How does the client find the server?

When you start a server, it shows the port its listening to. So the client simply has to connect to that port through the right ip adress.

### Who speaks first?

There are no right or wrongs, but usually clients speak first, this therefor allows the server to decide to accept or not the connection of the client

### What is the sequence of messages exchanged by the client and the server?

1. Server listens to a port
2. Client try connecting to the server
3. Server accepts
4. Server is waiting for a calculation
5. Client sends one
6. Server receives it and compute it
7. Server sends an answer to the client
8. Client receives it
9. Client can either ask a new calculation or simply disconnect

### What happens when a message is received from the other party?

Depends on what message was send : 
* if the message sent is an invalid command, we send an error message
* if the message is a valid command then we simply send the answer to the calculation

### What is the syntax of the messages? How do we generate and parse them?

That's an arbitrary choice, I would go for that : 
* add n1 n2
* sub n1 n2
* mult n1 n2
* div n1 n2

Most basic operations can be decomposed in this form : 
* operation
* number on the left
* number on the right

### Who closes the connection and when?

The client decides when to close the connection


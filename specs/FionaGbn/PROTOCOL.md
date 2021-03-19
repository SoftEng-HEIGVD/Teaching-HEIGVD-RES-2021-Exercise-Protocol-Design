#Specification

What transport protocol do we use?
TCP

How does the client find the server (addresses and ports)?
ip adress from server and port 2019

Who speaks first?
Server

What is the sequence of messages exchanged by the client and the server? (flow)
Server: welcome client to the calculator and explain the instructions (ADD/SUB/MULT/DIV for calculation and QUIT to quit)
Client: enter an operation
Server: display result
Loop last 2 parts until client decide to quit

What happens when a message is received from the other party? (semantics)
Server checks the message send by the client is legit
if the operation is valid: calculate and send the result
if the operation is not valid: resend the instructions
if the message is QUIT: close connection


What is the syntax of the messages? How we generate and parse them? (syntax)
Client: 
to send an operation: ADD/SUB/MULT/DIV int1 int2 
to quit: QUIT

Server: 
to send result: RESULT result


Who closes the connection and when?
Server closes the connection after recieving QUIT form the client

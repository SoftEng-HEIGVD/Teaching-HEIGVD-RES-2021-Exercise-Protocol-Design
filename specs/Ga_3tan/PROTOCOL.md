### protocol : 
TCP
### adress and port :
server IP adress and 8080
### who speak first :
the client (to ask the server to connect)
### sequence of messages (flow)
the client ask for connection (hello server)\
the server tell all supported operations (hello client)\
the client ask for an answer to a calculation

- format : OPERATION number number (ADD 3 4) 

the server respond with the result

- format : RESULT number (RESULT 7)

the client can end the connexion

- format : STOP

the server ends the connexion

### when a message is recieved
check if the response is OK, then treat the message
### syntaxe of messages 
tokens are separated by spaces\
first token is OPERATION : "ADD" "SUB" "MULT"\
second token is number in int\
third token is number in int\
to end the connexion the first token must be : "STOP"
### who closes the connexion ?
the client closes the connexion by sending a STOP message,
then the server stop after recieving this message

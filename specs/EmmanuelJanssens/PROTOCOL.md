# What transport protocol do we use?
TCP Protocol will be used for this exercice

# How does the client find the server (adresses and ports)
local network
ports 3101
# Who speaks first?
Server speaks first after confirming connection with the client ( WELCOME )
# What is the sequence of messages exchanged by the client and the server? (flow)

-> client connects\
-> server welcomes\
-> client sends operation(s)(COMPUTE,QUIT)\
-> server responds (RESULT, ERROR)

# What happens when a message is received from the other party?(semantics)

# What is the syntax of the messages? How we generate and parse them? (Synthax)

End of line is defined by : __CRLF__

5 messages are defined in the CALC protocol.
* __WELCOME__\
Sent to client right after a TCP connection was established, is followed by a block beginning with
___AVAILABLE OPERATION CRLF___ and ending with ___END OF OPERATIONS CRLF___ any operation in between is written as _OPERATION_ _NB_OPERANDS_ _CRLF_
* __COMPUTE__\
Sent to the server to submit an operation ___COMPUTE ADD 2 45 CRLF___ 
* __RESULT__\
Sent to the client ___RESULT 47 CRLF___
* __ERROR__\
Sent to the client ___ERROR 300 UNKNOWN OPERATION CRLF___
* __QUIT__\
Sent to the server, closes session

## ERRORS
300 Uknown operation\
400 Syntax error\
9xxx Any other error\
## Example
WELCOME CRLF
* AVAILABLE OPERATIONS CRLF
* ADD 2 CRLF
* DIV 2 CRLF
* MULT 2 CRLF
* END OPERATION CRLF

# Who closes the connection and when?
The client closes the connection at the end of a sessions (When the servers recieves QUIT)
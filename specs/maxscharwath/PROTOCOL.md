### What transport protocol do we use? 
We will use the TCP protocol
### How does the client find the server (addresses and ports)?
Manually, in our case the server is in local so address is localhost, and the port is defined arbitrary. 
So we choose the port number 2203, because it's my birthday, and it's upper than 1024.
### Who speaks first?
Server for init connection and give to the client the available operations.
### What is the sequence of messages exchanged by the client and the server? (flow)
####Init Section
- [server] WELCOME 
- [server] START_OP
- [server] OP ADD 2
- [server] OP SUB 2
- [server] STOP_OP
####Main Section 
- [client] OP \<operation> \<num1> \<num2>
- [server] RESULT \<result>
- [server] ERROR \<status>
- [client] STOP
- [server] _stop the connection_
### What happens when a message is received from the other party? (semantics)
the server and client listen for a message when a message arrived the software parse the message and execute the adequate function linked with the first token as arguments.
for <operation> we can accept the operation that we receive at the init section. 
If an error is throw the server send a ERROR response. 
### What is the syntax of the messages? How we generate and parse them? (syntax)
 We will use token style like this: "token1 token2 token3" with space separator
### Who closes the connection and when?
The server close the connection when the client sends a stop request
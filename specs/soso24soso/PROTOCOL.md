# Specifications

* What transport protocol do we use?

TCP

* How does the client find the server (addresses and ports)?

localhost:1408

* Who speaks first?

The client

* What is the sequence of messages exchanged by the client and the server? (flow)

1. client : connect
2. server : ready
3. client : send operation
4. server : send calculation
5. client : finish

* What happens when a message is received from the other party? (semantics)

client : if the message is known, then process the answer and show it
server : if the message is known, then process the answer and send response

* What is the syntax of the messages? How we generate and parse them? (syntax)

generate : informationnal messages are just strings
operation : postfix notation, + - * /

* Who closes the connection and when?

client closes the connection when it doesn't want more 

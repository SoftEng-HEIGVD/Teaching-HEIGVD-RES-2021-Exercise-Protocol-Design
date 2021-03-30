> What transport protocol do we use?

TCP

> How does the client find the server (addresses and ports)?

port 2021 client, port 2021 serveur

> Who speaks first?

Client

> What is the sequence of messages exchanged by the client and the server? (flow)

Client -> mult 5 2 -> serveur -> 10 -> Client

> What happens when a message is received from the other party? (semantics)

le verifie et le traite

> What is the syntax of the messages? How we generate and parse them? (syntax)

add 5 2,minus 5 2, mult 5 2, div 5 2,

> Who closes the connection and when?

serveur après 2min
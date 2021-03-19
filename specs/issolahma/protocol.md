  * What transport protocol do we use?

    TCP

    

  * How does the client find the server (addresses and ports)?

    IP:2828

    

  * Who speaks first?

    client

    

  * What is the sequence of messages exchanged by the client and the server? (flow)

    client -> askconn -> server

    server -> connOk -> client

    client -> operation -> server

    server -> result/error -> client

    

  * What happens when a message is received from the other party? (semantics)

    - check the message

    - respond to the message

      

  * What is the syntax of the messages? How we generate and parse them? (syntax)

    add num num

    mult num num

    askConn

    connOk

    connErr

    syntaxError

    

  * Who closes the connection and when?

    server after 2min no msg
- What transport protocol do we use ?
TCP

- How does the client find the server (addresses and ports) ?
The client must already know the address of the server it wants to speak to. The port is defined by the protocol to be 2048.

- Who speaks first ?
The client who contact the server and establish the connection.

- What is the sequence of messages exchanged by the client and the server ? (flow)
    1. The client connect to the server and send a question message with a unique id.
    2. The server send a answer/error message with the unique id.

- What happens when a message is received from the other party ? (semantics)
The server has a timeout of 5 seconds to send its answer, otherwise the connection is considered stalled and the client should ask again its question, maximum 5 times before giving up.

- What is the syntax of the messages ? How we generate and parse them ? (syntax)
    - Question message : `question <unique number>: <some math expression>`
        - Max length : 256 bytes
    - Response message : `answer <unique number>: <result>`
        - Max length : 256 bytes
    - Response message : `error <unique number>:`
        - Max length : 256 bytes

- Who closes the connection and when ?
The server closes the connection once it has sent its answer/error.
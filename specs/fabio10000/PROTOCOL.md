# Phase1: write the specification

The server is reachable from the IP address: `insert address` with the port number: `insert port number`.

We will use TCP protocol as a communication protocol.

To initiate a session the client must send a message `OPTIONS` to the server who will respond with a list of available operations separated by a `;`. 

An operation format is: `OPERATION_NAME nb_params`

As a response will take the same codes as in HTTP: `RESPONSE CODE_NB CODE DESCRIPTION`

The client can then send the command to the server and get a response.

When the client want to quit the session he must send a `QUIT` message to end the session.

Example flow:

```
S: = message send by the server
C: = message send by the client

C: OPTIONS
S: ADD 2;SUB 2;MUL 2;DIV 2;

C: ADD 3 2
S: 5 200 OK
```


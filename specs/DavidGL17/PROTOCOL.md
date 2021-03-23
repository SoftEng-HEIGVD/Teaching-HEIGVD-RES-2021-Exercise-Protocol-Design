# Protocole

## 1. Introduction :

### 1.1 Objective of the protocol :

We will use TCP

### 1.2 Adresse IP du serveur :

The server will listen on port 3045, with a fix adress

## 3. Connection/Disconnection Procedure :

- The client will connect to the server by sending the "CONN" message. The server will answer "CONN_OK" if he correctly
  received the previous message, or "ERROR".

- To close the communication, the client can send the "STOP" message. The server will confirm the disconnection by
  answering "STOP_OK" and will then close this connection. The client can close his connection after receiving the
  message.

## 4. Description :

Each message from the client will have the following format :

```
COMPUTE Operation firstParam secondParam
```

The operations that are allowed are :

```
- COMPUTE ADD firstParam secondParam
- COMPUTE SUB firstParam secondParam
- COMPUTE MUL firstParam secondParam
- COMPUTE DIV firstParam secondParam
```

The server will answer with the result in the following format :

```
RES value
```

If there was an error he will respond in the following format :

```
ERROR description
```

The description of the error can be one of the following values, that each indicate the error :

- WRONG MESSAGE : indicates the message was not one accepted by the server at this point in the communication
- SYNTAX ERROR : The message was not well written, it did not answer to the specifications of the protocol
- SYSTEM ERROR : the server had an error while receiving the message and had to close the connection
- COMPUTATION ERROR : the server could not provide results for the given operation, because it was not mathematically
  correct
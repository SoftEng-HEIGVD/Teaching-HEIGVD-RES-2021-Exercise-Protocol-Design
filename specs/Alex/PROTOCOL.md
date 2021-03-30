Author : Alexandre Mottier



## What transport protocol do we use ?

TCP, because we need reliability during the connection client-server. We don't want to lose packets.



## How does the client find the server (addresses and ports)?

The client has to know the IP server address and the port to be able to communicate with it



## Who speaks first ?

The client because the server does not know the potential clients



## What is the sequence of messages exchanged by the client and the server ? (flow) 

| ***Client***                  | ***Server***                    |
| ----------------------------- | ------------------------------- |
| 1. Asks for connection        | 2. Grants or refuses connection |
| 3. Sends operations           | 4. Sends the answer or an error |
| 5. Ask to stop the connection | 6. Closes the connection        |



## What happens when a message is received from the other party? (semantics)

The server verifies that the format of the request is correct, otherwise it will send back an error. The server sends back the information requested by the client.



## What is the syntax of the messages ? How we generate and parse them ?

| ***Message***                | ***Description***                                            |
| ---------------------------- | ------------------------------------------------------------ |
| HELLO                        | *client* Ask connection                                      |
| ACK                          | *server* Confirm connection                                  |
| OPERATION code value1 value2 | *client* Ask server to the operation (OPERATION add 1 2)     |
| RESULT value                 | *server* Return the result                                   |
| ERROR description            | *server* Return error message with description if the request from the client was not known or false |
| QUIT                         | *client*  Ask to end the process                             |



## Who closes the connection and when ?

The server closes the connection after a connection problem with the client or if the client ask to closes it.
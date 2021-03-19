# Protocol specification
* _What transport protocol do we use?_
  * It's obvious that we need to use the TCP protocol to send date between the server and the client. We need this TCP protocol because we can not allow loss of data.
* _How does the client find the server (addresses and ports)?_
  * The server and the cient will both listen/contact on the port **2021** with the local IPv4 used by the local machine
* _Who speaks first?_
  * The client will be the first to speak with a "greatings" message.
  * If the server side received the message, it will answer with a "welcome" message.
* _What is the sequence of messages exchanged by the client and the server? (flow)_
  * Client : Try to connect
  * Server : Response
  * Client : Send an operation
  * Server : Respond with the answer
  * Client : The client end the communication
  * Server : Close the connexion
* _What happens when a message is received from the other party? (semantics)_
  * **Client side :** The client read the stream content and display the result (op result or error thrown)
  * **Server side:** The server read the stream and parse the data to make an operation. After that, send the result (op result or error thrown) through the output
* _What is the syntax of the messages? How we generate and parse them? (syntax)_
  * **Client message :** [operation] [operand1] [operand2]
    * ADD 4 5
  * **Server message :** [response status] [operation] [result]
    * OK 9
* _Who closes the connection and when?_
  * When the client is done, it will close its socket. The server will be notified and will close its connection too. In the case of a unexpected closure of the server, the client will close is socket too.
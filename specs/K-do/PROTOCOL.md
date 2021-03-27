#### What transport protocol do we use?

TCP

#### How does the client find the server (addresses and ports)?

server ip-address and port 2048

#### Who speaks first?

server

#### What is the sequence of messages exchanged by the client and the server?

server lists the operations available

client choose an operation and send it with 2 numbers

server reply with the result

#### What happens when a message is received from the other party?

client receives a list of operations and how to stop the connection. He chooses one of them and send it to the server with the numbers

server receives the operation, checks if legit, (calculate and reply with the answer) OR stop connection. 
If the signal is to end the communication, a last message is send stating that the connection has ended. The connection is then stopped.
If the operation is not recognized an error message is send stating that the operation is not available and completed with the list of available operations.
If the number of number is not correct (2), an error message is send stating that the number of arguments is incorrect and completed with a correct example.
If the number is not an integer or float, an error message is send stating that the type argument is not correct and that only integers or float are accepted.

client receives errors/result/connection-stop messages

#### What is the syntax of the messages? How we generate and parse them?

Stop connection: QUIT
Operations available: ADD, SUB, MULT, DIV

OPERATION <int_1>, <int_2>

Result: <int_result>

#### Who closes the connection and when?

server closes the connection
client request an end by sending QUIT
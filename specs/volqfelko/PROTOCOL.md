* What transport protocol do we use?<br>
    TCP
* How does the client find the server (addresses and ports)?<br>
    The client will contact server on port 2021 on my computer IP address (where the app is running) <br>
    The server will be listening on port 2021 with my computer IP address (where the app is running)
        
* Who speaks first?<br>
    The client will ask the server
* What is the sequence of messages exchanged by the client and the server? (flow)<br>
       Client : send a message to ask if srv up<br>
       Server : **listening**, receive the message and respond ready<br>
       Client : receive response from server<br>
       
* What happens when a message is received from the other party? (semantics)<br>
Check if message OK, then treat it
* What is the syntax of the messages? How we generate and parse them? (syntax)<br>
OPERATION NUMBER1 NUMBER2<br><br>
OPERATIONS : ADD, DIV, MULT, SUB, q <br>
NUMBERS : int format <br>
everything separate with SPACES

* Who closes the connection and when?<br>  
Client with stop msg then the server follow client instruction
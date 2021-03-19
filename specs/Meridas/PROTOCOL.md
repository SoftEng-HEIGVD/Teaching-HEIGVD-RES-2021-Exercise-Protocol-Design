What transport protocol do we use?TCP   
How does the client find the server (addresses and ports)?
Server has a static adress/port combination  
Who speaks first?Client  
What is the sequence of messages exchanged by the client and the server? (flow)
Client : request connexion
Server : allow connexion, available operations  
Client : request operation operand1 operand2  
Server : Result (Value, noErr)  
Client : Close connexion  
What happens when a message is received from the other party? (semantics)  
Message parsed and interpreted    
What is the syntax of the messages? How we generate and parse them? (syntax)  
Operations are separated by a blank space  
Who closes the connection and when?  
Client when he wants to quit.
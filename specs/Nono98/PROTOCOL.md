# Phase 1

>  What transport protocol do we use? 

TCP/IP

> How does the client find the server (addresses and ports)?

Le client se connecte avec la commande TCP numero_port

> Who speaks first?

Le client fait une demande de connexion au serveur

> What is the sequence of messages exchanged by the client and the server? (flow)

S: WELCOME CRLF

S: - OPERATIONS CRLF

S: - ADD 2 CRLF

S: - MULT 2 CRLF

S: ENDOFOPERATIONS CRLF

C: ADD 1 4 CRLF

S RESULT 5 CRLF

C: ADD 7 9 CRLF

S: RESULT 16 CRLF

C: DIV 1 4 CRLF

S: ERROR 300 UNKNOWN OPERATION CRLF

C: FHUIHUVUV CRLF

S: ERROR 400 SNTAX ERROR CRLF

C QUIT CRLF

> What happens when a message is received from the other party? (semantics)

Le client peut envoyer les opérations qu'il veut avec les opérations prises en charge par le protocole. Une fois que le client envoie toutes les informations avec le message COMPUTE. Une fois que le serveur reçoit lr message, il va calculer l'opération, s'il n'y a pas d'erreur il renvoie le résultat avec le message RESULT et sinon il renvoie une erreur avec le message ERROR. 

> What is the syntax of the messages? How we generate and parse them? (syntax)

Le protocole lis ligne par ligne. Il est important de préciiser la séquence CRLF (fin de ligne) à chaque fin de séquence (client + serveur).

**WELCOME**: il est envoyé du serveur au client immédiatement après que la connexion TCP ait été établie

- WELCOME CRLF

**COMPUTE**: il est envoyé du client au serveur pour soumettre une opération

- COMPUTE ADD 2 45 CRLF

**RESULT**: message envoyé du serveur au client 

- RESULT 47 CRLF

**ERROR**: message envoyé du serveur au client

- ERROR 300 UNKNOWN OPERATION CRLF

**QUIT**: message envoyé du client vers le serveur

> Who closes the connection and when?

Le serveur ferme la connexion du client et du serveur une fois que le client ferme la connexion car il a plus de calcul  à faire.
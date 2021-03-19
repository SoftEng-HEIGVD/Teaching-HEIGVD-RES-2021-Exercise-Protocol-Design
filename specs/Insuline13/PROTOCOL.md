## Phase 1

```
*What transport protocol do we use ? 
TCP

*How does the client find the server (addresses and ports) ? 
Il dispose de l'IP du serveur et le port 80

*Who speaks first ? 
Le client

*What is the sequence of messages exchanged by the client and the server ? 
le client envoie l'opération et 2 nombres, le serveur envoie le résultat

*What happens when a message is received from the other party ? 
Le client regarde le résultat et peut renvoyer une opération ou quitter. 
Le serveur fait l'opération demandée.

*What is the syntax of the messages ? How do we generate and parse them ? 
Opérations: ADD, MULT, SUB, DIV suivi de 2 nombres
Pour quitter: quit
Le serveur répond avec le résultat: RESULT suivi d'un nombre.

*Who closes the connection and when ? 
Le client peut fermer la connexion quand il veut avec quit
```
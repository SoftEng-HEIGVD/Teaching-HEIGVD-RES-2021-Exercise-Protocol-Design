# Protocol
## What transport protocol do we use?
TCP.

## How does the client find the server (addresses and ports)?
Alors tout va être en local, sur le port 3616.

## Who speaks first?
Le client ! 

## What is the sequence of messages exchanged by the client and the server? (flow)

| Émis par | Syntaxe     | Description                                |
|----------|-------------|--------------------------------------------|
| CLIENT   | YO \n       | Début de conversation.                     |
| CLIENT   | ADD A B \n  | Demande d'addition de A et de B.           |
| CLIENT   | MULT A B \n | Demande de mulitiplication de A et de B.   |
| CLIENT   | CIAO \n     | Fermeture de la connexion avec le serveur. |
| SERVEUR  | RES X \n    | Retourne le résultat de l'opération X.     |
| SERVEUR  | ERR \n      | Format de requête erroné.                  |

## What happens when a message is received from the other party? (semantics)


## What is the syntax of the messages? How we generate and parse them? (syntax)
Client : 
`OPERATION VAL1 VAL2 \n`.

Serveur : 
`RES VAL1 \n` ou `ERR \n`.

Tous les messages finissent par un retour à la ligne `\n` (à voir si compatible sur tous les OS). 


## Who closes the connection and when?
Le client ferme la connexion, via le message `CIAO`. 
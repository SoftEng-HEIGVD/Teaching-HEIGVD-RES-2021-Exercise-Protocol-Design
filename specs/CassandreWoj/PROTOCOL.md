# Spécifications

> Auteur : Cassandre Wojciechowski
>
> Date : 19.03.2021

**What transport protocol do we use?**

Nous utilisons le protocole TCP. 

**How does the client find the server (addresses and ports)?**

Le client trouve le serveur grâce au port utilisé pour le protocole TCP. 

**Who speaks first?**

Le client envoie le premier message au serveur. 

**What is the sequence of messages exchanged by the client and the server? (flow)**

Le client envoie une demande de connexion, le serveur lui répond avec un message donnant les opérations supportées. 

Le client envoie ensuite l'opération dont il cherche le résultat. Le serveur effectue le calcul puis le retourne au client. En cas d'erreur, la raison de l'erreur est retournée au client. 

Quand le client souhaite arrêter la connexion, il envoie un message au serveur qui répond avant d'interrompre la connexion. 

**What happens when a message is received from the other party? (semantics)**

Client : `connection`

Serveur : `Welcome to CALC. You can choose from the following operations : ADD, MULT.`

Client : ` compute ADD 3 2`

Serveur : `result 5`

Client : `compute MIN 4 2`

Serveur : `error : operation not recognized`

Client : `exit`

Serveur : `bye`

**What is the syntax of the messages? How we generate and parse them? (syntax)**

Client : 

- pour se connecter : `connection`

- pour un calcul : `compute [ADD / MULT] [OP1] [OP2]`
- pour interrompre la connexion : `exit`

Serveur :

- en cas de connexion : `Welcome to CALC. You can choose from the following operations : ADD, MULT.`
- après calcul : `result [RESULT]`
- en cas d'erreur : `error : [info about the problem]`
- en cas d'interruption de connexion : `bye` 

**Who closes the connection and when?**

C'est le client qui interrompt la connexion quand il a terminé les calculs qu'il souhaitait effectuer. 
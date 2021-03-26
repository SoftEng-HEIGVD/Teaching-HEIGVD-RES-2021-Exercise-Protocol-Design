

1. What transport protocol do we use?

Il faut utiliser TCP car le client va établir une connexion avec le serveur

2. How does the client find the server (adresses and ports ) ?

-Si le serveur a une IP et une adresse fixe, on peut coder en dur dans le code du client

-Il fait un broadcast avec UDP pour savoir qui offre les services recherchés et le serveur

lui répond en lui donnant son adresse

Remarques : Je n'ai pas bien compris comment on pouvait connaitre l'adresse IP du serveur sans découverte dynamique et sans coder en dur son IP(afin de faire tourner les serveurs sur différents machines )

- Who speaks first?

C'est le client qui parle en 1er, il contacte le serveur. Le serveur lui est en attente d'un client.

- What is the sequence of messages exchanged by the client and the server? (flow)

Server ---> Client : "Opération disponible :  "DIV, MUL, SUB, ADD"

On peut se passer de l'étape ci-dessus pour simplifier le protocole.

Client --> Serveur : OPERATION n1 n2"

Exemple : DIV 1 2 veut dire que le serveur doit faire : 1 / 2

Serveur --> Client : RESULT res où res est le résultat

Client --> Server : FIN ou une autre OPERATION

- What happens when a message is received from the other party? (semantics)

L'autre partie récupère le message et le traite

- What is the syntax of the messages? How we generate and parse them? (syntax)

Il s'agit de commande, sauf le premier message du serveur qui est une string, il faut récupérer les arguments en fonction de la séquence du message

- Who closes the connection and when?

-Le serveur quand le client lui dit "FIN" 

-Le serveur si le délai d'attente est dépassée(par exemple 5 minutes) afin d'éviter que le serveur soit en attente de client. Le délai s'applique dès que le serveur a envoyé un message au client et qu'il attend sa réponse. Cela "empêche"des attaques DDOS, smurf autre qui auraient pour but de monopoliser les ressources du serveur.

-Le client une fois qu'il a envoyé FIN au serveur






> What transport protocol do we use?

TCP

> How does the client find the server (addresses and ports)?

Adresses IP, port (par exemple 1337)

> Who speaks first?

Le client vient initier la connexion

> What is the sequence of messages exchanged by the client and the server? (flow)

Le serveur anonce quand il est prêt à recevoir une demande au client (available) 

Le serveur attend que le client réponde avec :

- une nouvelle requête
- une demande de terminaison (quit)


Le serveur vérifie que la requête est bien formée

- Si oui, il effectue la requête et envoie la réponse (result)
- Si non, il envoie un message d'erreur au client (error)

Après cela, le serveur revient en attente de requête.

Lorsque le client demande la fin, le serveur envoie une confirmation (terminated)


TLDR: 6 types de messages: AVAILABLE, CALC, RESULT, ERROR, QUIT, TERMINATED

> What happens when a message is received from the other party? (semantics)

Le séparateur est le caractère ';'. La première valeur est la nature du message, et la seconde son contenu 

Le type de message peut être représenté en flags binaires, mais le contenu est forcément une chaîne de caractères (qui peut rester vide)

> What is the syntax of the messages? How we generate and parse them? (syntax)

Lancer une opération:

`CALC;"(1 + 2 - 3 +4)*34"`

Les opérations +,- et · sont supportées, ainsi que les parenthèses. L'opération est d'abord traduite en RPN puis effectuée en prenant compte des priorités de calcul.

> Who closes the connection and when?

Le client indique qu'il veut fermer la connexion et attend que le serveur réponde (terminated)

Une fois cela fait, la connexion TCP peut être terminée

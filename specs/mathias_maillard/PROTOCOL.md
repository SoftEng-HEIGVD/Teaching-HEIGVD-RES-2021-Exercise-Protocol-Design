# Specifications CALC - Mathias Maillard

## Généralités

Nous utilisons le protocole TCP/IP. Le port 3031 est utilisé par défaut.

Après avoir accepté une connection, le serveur envoie la liste des opérations disponibles au client. Celui-ci peut alors envoyer un certain nombre d'opérations.

CALC est un protocole sans état, car nous n'avons pas besoin de garder un trace des messages envoyés par le client.

## Messages échangés

- WELCOME: server->client une fois la connection établie. Le message est constitué de plusieurs lignes, pour indiquer les opérations disponibles.
- COMPUTE: client->server pour faire une opération. Contient une opération et deux opérandes. 
- RESULT: server->client pour envoyer le résultat. Message suivi du résultat.
- ERROR: server->client si l'opération demandée n'est pas valide.
- QUIT: client->server qunad le client a terminé.

## Who speaks first?

Le server commence avec le message WELCOME pour indiquer au client les opérations disponibles.



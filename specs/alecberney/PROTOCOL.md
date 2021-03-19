# RES Laboratoire Calculette

## Procotole de transport utilisé
TCP

## Comment le client trouvera-t-il le serveur
en utilisant TCP l'adresse IP et le port du serveur.
Le port sera 8085.

## Qui parle en premier?
Le client doit parler en premier en envoyant "Hello", ainsi le serveur saura qu'il y a une nouvelle connexion.

## Séquence de messages échangés
La séquence classique est la suivante:
1. Client
2. Serveur
3. Client
4. Serveur
5. ...
6. Client
7. Serveur

### Client
* HELLO
* [Operation] [number] [number]
* ...

### Serveur
* Operations supported : ADD [number] [number], MUL [number] [number], ...
* [OK | ERROR] Result = [number] of [Operation] [number] [number]
* ...

## Qu'arrive-t-il si on reçoit un message de l'autre partie?
On l'analyse et on luit répond en fonction de cette dernière.

## Quelle est la syntaxe des messages ? Comment on les génères et analyses?
### Client
Le client peut émettre les messages d'arrivée ("HELLO"), d'aurevoir ("END") et une opération fournie par le serveur.
Il peut donc envoyer:
* HELLO
* [Operation] [number] [number]
* END

Exemple: ADD 2 3

le client génère ces messages avec un terminal géré par un humain. 
C'est à l'humain d'interpréter les réponses du serveur afin de les comprendre.

### Serveur
Le serveur peut émettre les messages d'arrivée, d'aurevoir et un résultat à un calcul demandé par le client.
Il peut donc envoyer:
* Operations supported : ADD [number] [number], MUL [number] [number], ...
* OK Result = [number] of [Operation] [number] [number]
* ERROR
* BYE

Exemple: OK Result = 5 of ADD 2 3

le serveur génère ces messages à l'aide de l'analyse de la requête du client.
Voici ces comportements:
* Si le client a envoyé "HELLO", il répond avec ces opérations supportées.
* Si le client a envoyé une opération valide, il répond avec la réponse à cette dernière.
* Si le client a envoyé une opération non valide, il répond avec la réponse ERROR.
* Si le client a envoyé "END", il répond "BYE" et clos la connexion.

## Qui ferme la connexion et quand?
Le client ferme la connexion en envoyant END.
Le serveur répliquera BYE.

## Exemple d'échange
Client : Hello
Serveur : Operations supported : ADD [number] [number], MUL [number] [number]
Client : ADD 2 3
Serveur  : OK Reslult = 5 of ADD 2 3
Client: SUB 2 3
Serveur : Error
Client : END
Serveur  : BYE
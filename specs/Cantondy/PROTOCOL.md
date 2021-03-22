# RES - Exercise-protocol-Design 2021

Authors : Alessandro Parrino & Dylan Canton 

Date : 19.03.2021

---



#### What transport protocol do we use?

Nous utilisons le protocole TCP/IP car c'est celui utilisé par défaut par les sockets API de Java. C'est également un protocole de communication classique et très souvent utilisé.



#### How does the client find the server (addresses and ports)?

Lors de son lancement, le serveur indique son port d'écoute. Le client peut alors se connecter en initialisant un socket et indiquant une adresse IP et un port, il faudra donc indiquer l'adresse IP du serveur ainsi que le même port que celui sur lequel le serveur écoute. 

Le port utilisé pour la communication est le : **1440**



#### Who speaks first?

Le client initialise en premier la connexion au serveur, qui décide d'accepter ou non. 



#### What is the sequence of messages exchanged by the client and the server? (flow)

1. En premier le serveur lance l'écoute sur le port sélectionné.
2. Le client essaie ensuite de s'y connecter lors de son lancement directement.
3. Le serveur répond par un message indiquant qu'il peut recevoir un calcul.
4. Le client envoie un calcul.
5. Le serveur le reçoit et le traite, il envoie ensuite une réponse au client : soit le résultat du calcul, soit un message d'erreur si la syntaxe de la commande est erronée. 
6. Le client reçoit la réponse.
7. Le client peut se déconnecter à tout moment en envoyant la commande QUIT.



#### What happens when a message is received from the other party? (semantics)

Lorsque la connexion est établie, le serveur envoie un message de bienvenu au client 

```
Hello, enter your operation :
```



Le serveur effectue le calcul et répond en donnant le résultat sous la forme :  

```
RESULT : result
```

Dans le cas où la commande envoyée est invalide, le serveur répond par un message d'erreur : 

```
ERROR : Invalid command, try again
```



Exemple d'utilisation : 

```
ADD 5 10

Result : 15
```



#### What is the syntax of the messages? How we generate and parse them? (syntax)

Le client peut envoyer des opérations au serveur à l'aide des 3 commandes suivantes : 

```
ADD number number

SUB number number

MULT number number
```



Un message est sous la forme d'une String. Lorsqu'elle est reçue par le serveur, elle est décomposé en 3 parties : 

* Opération
* Opérande gauche
* Opérande droite



#### Who closes the connection and when?

Le client ferme la connexion quand il le désire en effectuant la commande : 

```
QUIT
```


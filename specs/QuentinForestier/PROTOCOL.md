# Exercise Protocol Design

---

### What transport protocol do we use?
L'application doit utiliser TCP, car c'est ce que nous voyons en cours.

### How does the client find the server (addresses and ports)?
Le client doit utiliser le protocole TCP avec l'adresse IP du serveur et le port 2001 par défaut

### Who speaks first?
Le serveur. Il doit envoyer un message de bienvenue pour assurer au client  le bon fonctionnement
de la connexion. \
Le serveur doit pouvoir spécifier au client la liste des opérations possibles dans le message de bienvenue 

### What is the sequence of messages exchanged by the client and the server? (flow)
S : Bienvenue sur le serveur de calcul d'opérations. \
Voici la liste des opérations :\
    - ADD n1 n2\
    - SUB n1 n2\
    - MULT n1 n2\
    - DIV n1 n2\
Pour les utiliser, entrez OPERATE {syntaxe de l'opération}\
C : OPERATE ADD 5 4\
S : RESULT 9\
C : OPERATE POW 2 2\
S : ERROR 300 Opération inconnue \
C : OPERATE DIV 5 0\
S : ERROR 400 Opération invalide
C : QUIT


### What happens when a message is received from the other party? (semantics)
Le serveur doit interprêté le message selon la syntaxe indiquée et renvoyer
- le résultat si cela est possible
- une erreur dans le cas contraire

Le client doit interprêté le message selon la syntaxe indiquée et afficher
- le résultat 
- la raison de l'erreur

### What is the syntax of the messages? How we generate and parse them? (syntax)
| Utilitée | Syntaxe |
|---|----|
| Demander le résultat d'une opération (depuis le client) | OPERATE {syntaxe de l'opération}   |
| Fermer la connexion (depuis le client)  | QUIT  |
| Renvoyer le résultat (depuis le serveur)  | RESULT {resultat}   |
| Renvoyer une erreur (depuis le serveur) | ERROR {numéro de l'erreur} {Raison de l'erreur}   |



### Who closes the connection and when?

Le client arrête la connexion une fois qu'il n'a plus de demande à faire au serveur.
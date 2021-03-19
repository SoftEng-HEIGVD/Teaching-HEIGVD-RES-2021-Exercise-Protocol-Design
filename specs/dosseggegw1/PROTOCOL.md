# Spécifications
> Auteur : Gwendoline Dössegger
> Date   : 19.03.2021

**What transport protocol do we use?**
Le protocole a réalisé utilise un réseau TCP/IP. 

**How does the client find the server (addresses and ports)?**
Le client se connecte au serveur via à un port TCP XXX. 

**Who speaks first?**
Le client demande une connexion au serveur. 

**What is the sequence of messages exchanged by the client and the server? (flow)**
Quand un client demande une connexion et que celle-ci est accepté par le serveur, ce dernier lui envoi un message lui indiquant les opérations qu'il accepte. 

Le client peut ensuite lui transmettre les opérations souhaitées et le serveur lui retourne les résultats. 

Traitement des erreurs
- Si le format de calcul `CALC OPERATOR OPERAND OPERAND` n'est pas respecté, une erreur est retournée indiquant que le format n'est pas correcte.
- Si le message envoyé par le client est autre que `CALC ....`, le serveur lui retourne une erreur indiquant qu'il ne peut pas traiter sa demande.

Lorsque le client n'a plus de calcul à effectuer, il envoie `QUIT` au serveur pour lui indiquer son souhait de terminer la communication.


**What happens when a message is received from the other party? (semantics)**
Client  : `CONNEXION FIN`

Serveur : `Bienvenue ...., voici les différentes opérations que je supporte : ADD, MULT, ... FIN`

Client  : `CALC ADD 5 5 FIN`

Serveur : `RESULT 10 FIN`

Client  : `CALC 3 ADD 3 FIN`

Serveur : `ERROR 300, operation inconnu FIN`

Client  : `BLABLA FIN`

Serveur : `ERROR 301, demande inconnue FIN`

Client  : `QUIT FIN`

Serveur : `ACK fermeture de connexion FIN`

**What is the syntax of the messages? How we generate and parse them? (syntax)**
Client : 
- pour demander une connexion : `CONNEXION FIN`
- pour un calcul : `CALC [ADD | MULT] OPERAND OPERAND FIN`
- pour fermer la connexion : `QUIT FIN`

Serveur :
- premier message de connexion : `Bonjour .... voici les opérations supportés...`
- pour retourner un résultat   : `RESULT xx FIN`
- pour indiquer une erreur     : `ERROR xx, blabla FIN`
- pour aquiter la fermeture    : `ACK fermeture de connexion FIN`

Toutes les communications seront dôtés d'un "mot" indiquant la fin de la phrase. Ceci a pour simplifier le traitement des messages en déterminant où est la fin du message.


**Who closes the connection and when?**
Le client indique quand il souhaite terminer la connection. 


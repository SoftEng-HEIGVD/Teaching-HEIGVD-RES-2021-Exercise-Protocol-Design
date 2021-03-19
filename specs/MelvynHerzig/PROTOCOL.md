1. Introduction

1.1 Objectifs du protocole
Le protocle CALC a été conçu pour que des clients puissent soumettre
des calculs simple à un serveur. Les communications passent au travers d'un réseau
TCP/IP. Les serveurs utilisant ce protocole sont capables de faire, au moins:
des additions et des multiplications. Les serveurs peuvent fournir des
opérations supplémentaires fournies au client lors de sa connexion.

1.2 Fonctionnement général
Le protocole CALC se base sur le protocole TCP. Les serveurs implémentant ce CALC
écouteront sur le port 2001. Lors de l'acceptation d'une connexion le serveur envoie
les opérations supportées. A partir de ce moment le client peut envoyer autant
d'oppérations que souhaitées. Le serveur distant ne supporte qu'une connexion
simultanée.

Le client envoie un message contenant en premier l'opration a effectuer suivi des deux opérandes.
Le serveur répond avec un message RESULT suivi du résultat. En cas d'opération non supportée ou
d'une mauvaise formulation de la requête, le serveur envoie un message BAD_REQUEST suivi d'un code.

Lorsque le client veut terminer la connexion, il envoie un message DONE.

Le protocole est sans état.

2. Syntaxe des messages
Le protocole CALC définit 6 messages de base:
- WELCOLE
- ADD
- MULT
- ERROR
- BAD_REQUEST
- DONE

Les opérations sont donc un message en soit et toutes ne supportes que deux opérandes.
La séquence CRLF (10,13) indique la fin des lignes.

2.1 WELCOME
Ce message est envoyé au client dès sa connexion, il lui indique les opérations possibles
à raison de une par ligne.

Exemple:
WELCOME CRLF
ADD CRLF
MULT CRLF
SUB CRLF


on voit dans l'exemple que le serveur qui envoie ce message supporte la soustraction en plus 
des deux opérations des base.

2.2 ADD/MULT
Pour rappel les opérations sont une requête à proprement parlé.
Toutes les opération sont faites sur deux opérandes.

Exemple:
ADD 1 2 CRLF

ou

MULT -2 1.5 CRLF

2.3 RESULT
Le résultat est envoyé dans un message RESULT suivi du résultat

Exemple
RESULT 8.2 CRLF

2.4 DONE
Le message DONE, envoyé par le client termine la connexion

Exemple:
DONE

3. Éléments spécifiques

3.1. Opérations supportées
Le serveur supportes au moins les requêtes ADD et MULT
pour effectuer des additions et des soustractions.

3.2. Extensibilité
Le serveur peut implémenter de nouvelles requêtes qui effectuent 
de nouvelles opérations, elle doivent prendre deux opérandes.
Ces nouvelles opérations sont transmises dans le message WELCOME à
raison de 1 par ligne.

3.3. Traitement des erreurs
Si le serveur recoit une mauvaise requête, il retourne un message BAD_REQUEST suivi
d'un code décrivant l'erreur:

- 1 : Commande inconnue
- 2 : Commande mal formée
- 3 : Tout autre erreur

4. Exemples
S: WELCOME CRLF
S: ADD CRLF
S: MULT CRLF
C: ADD 1.2 2 CRLF
S: RESULT 2.4 CRLF
C: MULT 4 -1 CRLF
S: RESULT -4 CRLF
C: DONE CRLF

S: WELCOME CRLF
S: ADD CRLF
S: MULT CRLF
S: DIV CRLF
C: DIV 4 2 CRLF
S: RESULT 2 CRLF
C: NOT_A_VALID_REQUEST CRLF
S: BAD_REQUEST 1 CRLF




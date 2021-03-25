# Protocol Design

> Nicolas Crausaz

## Objectifs

Le but de ce laboratoire est d'imaginer et d'implémenter un protocole client-serveur permettant de réaliser des opérations arithmétiques, telle une calculatrice.
Ce protocole cela utilisable au travers d'un réseau TCP/IP. Le serveur est capable de réaliser des opérations arithmétiques de base (+, -, *, /, ^x).

&nbsp;

## Fonctionnement

Ce protocole d'opérations arithmétiques fonctionne sur TCP et utilise le port 8085. 

Pour initialiser une communication, le client envoie une requête vers le serveur.

Plusieurs "codes" sont utilisés pour définir les demandes du client pour que le serveur puisse les interpréter et les traiter (voir Syntaxe).

Ce protocole ne conserve aucune données entre les requêtes (stateless).

&nbsp;

## Syntaxe
Les types de requêtes sont définies ainsi

&nbsp;

### Client

Le client peut envoyer les messages suivants:

| Code   |      Params      |  Description  |
|--------|:----------------:|--------------:|
| _OPERATION_ | VALUE VALUE[optionnal] | Le client envoie une opération à effectuer (voir Opération serveur) (Si la 2e valeur est vide, elle sera à 0) |
| LIST | - | Le client demande à recevoir les instructions d'aide (liste des opérations disponibles) |
| QUIT | - | Le client met fin à la communication |

&nbsp;

### Serveur

| Code   |      Params      |  Description  |
|--------|:----------------:|--------------:|
| LIST  | Liste des opérations | Le serveur renvoie la liste des opérations qu'il implémente |
| RESULT | VALUE | Le serveur retourne le résultat d'un CALC |
| ERROR | ERROR_MSG | Le signale une erreur au client |

La liste des opérations est 
| Code   |  Description  |
|--------|:--------------:|
| ADD | Opérande addition |
| SUB | Opérande soustraction |
| MULT | Opérande multiplication |
| DIV | Opérande division |
| POW | Opérande puissance |


&nbsp;

### Erreurs (serveur)

| Code   |    Nom court     |  Description  |
|--------|:----------------:|--------------:|
| 340 | CMD_ERR | La commande demandée n'existe pas |
| 350 | SYNTAX_ERR | L'opération demandée n'est pas valide (format) |
| 360 | ARM_ERR | Erreur arithmétique (division par 0) |

&nbsp;

## Exemple de fonctionnement

Voici l'exemple d'une séquence d'interaction entre un client et un serveur:

> S: = Serveur, C: = Client


C: INIT  
S: HELLO ADD, SUB, MULT, DIV, POW
C: HELP  
S: LIST ADD, SUB, MULT, DIV, POW
C: CALC 4 + 6  
S: RESULT 10  
C: CALC 5 6  
S: ERROR 350 SYNTAX_ERR  
C: QUIT  

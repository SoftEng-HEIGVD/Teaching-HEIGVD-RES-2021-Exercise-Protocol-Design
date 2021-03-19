# Protocol Design

> Nicolas Crausaz

## Objectifs

Le but de ce laboratoire est d'imaginer et d'implémenter un protocole client-serveur permettant de réaliser des opérations arithmétiques, telle une calculatrice.
Ce protocole sela utilisable au travers d'un réseau TCP/IP. Le serveur est capable de réaliser des opérations aritmétiques de base (+, -, *, /).

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
| Code   |      Params      |  Description  |
|--------|:----------------:|--------------:|
| INIT | - | Initialise la communication |
| CALC | VALUE OPERAND VALUE | Le client envoie une opération à effectuer |
| HELP | - | Le client demande à recevoir les instrauction d'aide (liste des opérations disponibles)
| QUIT | - | Le client met fin à la communication

&nbsp;

### Serveur

| Code   |      Params      |  Description  |
|--------|:----------------:|--------------:|
| HELLO | - | Le serveur confirme l'initalisation du client en lui confirmant qu'il est pret à recevoir sa demande |
| RESULT | VALUE | Le serveur retourne le resultat d'un CALC |
| ERROR | ERROR_MSG | Le signale une erreur au client |
| LIST  | | Le serveur renvoie la liste des opérations qu'il implémente

&nbsp;

### Erreurs (serveur)

| Code   |    Nom court     |  Description  |
|--------|:----------------:|--------------:|
| 350 | SYNTAX_ERR | L'opération demandée n'est pas valide (format) |
| 340 | CONN_ERR | La connexion n'as pas pu être confirmée |

&nbsp;

## Exemple de fonctionnement

Voici l'exemple d'une séquence d'intéraction entre un client et un serveur

> S: = Serveur, C: = Client


C: INIT  
S: HELLO  
C: HELP  
S: LIST  
C: CALC 4 + 6  
S: RESULT 10  
C: CALC 5 6  
S: ERROR 350 SYNTAX_ERR  
C: QUIT  

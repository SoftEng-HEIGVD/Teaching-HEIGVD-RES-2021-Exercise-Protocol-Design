# Protocole specifications

Auteurs: Tailhades Laurent, Corentin Zeller
Date: 30 mars 2021

## But du protocole

Le protocole doit fournir un moyen d'exécuter des opérations mathématique avec une communication basé sur le protocole TCP/IP.

## What transport protocol do we use?

Sur la base de TCP, le protocole permet de faire des "session" de calculs.

## How does the client find the server (addresses and ports)?

On défini un port par défaut: **2021**, l'ip est celle du serveur

## Who speaks first?

Le client se connecte puis le serveur répond avec les operations possibles. C'est donc au client d'initier la conversation.

## What is the sequence of messages exchanged by the client and the server? (flow)

1. client se connecte
2. serveur envoie les opérations disponible
3. le client envoie les opérations qu'il veut éxecuter
   1. envoi de l'opération
   2. le serveur renvoi le resulat
4. le client envoie un message pour terminer la connexion

## What happens when a message is received from the other party? (semantics)

#### Serveur

	- si on recois WELCOME, répondre avec la liste des opérations
 - si on recois une opération
   	- operation possible: repondre avec le resultat
   	- sinon erreur

* sinon erreur

#### Client

* envois de WELCOME
  * si le serveur est pas disponible, afficher une erreur
* envois de EXECUTE selon l'entrée de l'utilisateur
* recevoir RESULT ou ERROR
  * affichage

##  What is the syntax of the messages? 

| Syntaxe du message                                           | Description                                 | Par qui ? |
| ------------------------------------------------------------ | ------------------------------------------- | --------- |
| WELCOME                                                      | Initiation de la connexion                  | Client    |
| AVAILABLE_BEGIN                                              | Debut de la liste des opérations disponible | Serveur   |
| OPERATION `nom operation` `nombre d'operand` (pour chaque operation) | Description de l'opération disponible       | Serveur   |
| AVAILABLE_END                                                | Fin de la liste des opérations disponible   | Serveur   |
| EXECUTE `type d'opération`  `operand1` `operand2`            | Execution d'une opération                   | Client    |
| RESULT `resultat`                                            | Resultat de l'opération                     | Serveur   |
| ERROR `code erreur` `description`                            | Erreur du coté serveur                      | Serveur   |
| BYE                                                          | Envois du message de fin au serveur         | Client    |



## How we generate and parse them? (syntax)

les messages sont terminé par un retour a la ligne et sont sous forme de string a parser.

Le premier mot definit toujours le type de message (WELCOME, AVAILABLE, RESULT etc.), ensuite selon le type on parse les mots suivants.

## Who closes the connection and when?

le client envoi un message (`BYE`) pour dire qu'il a fini.	


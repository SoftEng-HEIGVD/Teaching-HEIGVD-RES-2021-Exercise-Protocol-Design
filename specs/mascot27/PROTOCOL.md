# Protocole specififications

Auteur: Corentin Zeller

## But du protocole

Le protocole doit fournir un moyen d'executer des opérations math 

## What transport protocol do we use?

sur la base de TCP

## How does the client find the server (addresses and ports)?

On défini un port par défaut: 4242, l'ip est celle du server

## Who speaks first?

le client se connecte puis le serveur répond avec les operations possible

## What is the sequence of messages exchanged by the client and the server? (flow)

1. client se connecte
2. serveur envoie les opérations dispo 
3. le client envoie les opérations qu'il veut executer
4. le serveur envoi le resulat pour chaque opérations
5. le client envoie un message pour terminer la connexion

## What happens when a message is received from the other party? (semantics)



##  What is the syntax of the messages? 

WELCOME

AVAILABLE
OPERATION `nom operation` `nombre d'operand` (pour chaque operation)
AVAILABLE_END

EXECUTE `type d'opération`  `operand1` `operand2`
RESULT `resultat`



## How we generate and parse them? (syntax)



## Who closes the connection and when?

le client envoi un message pour dire qu'il a fini	


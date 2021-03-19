# Protocole calculette

## Protocole de transport
Ce protocole utilisera le protocole de transport TCP

## Comment le client trouve le serveur
Le serveur utilise son adresse IP publique et utilise le n° de port: 10001

## Qui parle en premier
Le client salue en premier le serveur ce qui démarre la communication entre les 2

## Quelle est la séquence de messages échangés par le client et le serveur

Client : hello
Seveur : ready
Client : add 40 2
server : res 42
Client : end

## Qu'est ce qui arrive quand un message est reçu par l'autre pair
Le message est parser puis analyser puis répond avec le message adéquat

## Quelle est la syntaxe des messages? Comment les générer et les parser
cmd [param1] [param2]

## Qui ferme la connection
Le client envoie le message de fin de connection

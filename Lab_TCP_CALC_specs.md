# Spécifications

## 1 Introduction

### 1.1 Objectifs du protocole

Le but premier du protocole CALC est de permettre à des clients d'envoyer des opérations arithmétiques sur un serveur de calcul via un réseau TCP/IP. 

Le serveur de calcul va recevoir le calcul à effectuer, le faire et le renvoyer au client. Les opérations supportées à la base seront l'addition et la multiplication. D'autres pourront être implémentées dès que les premières sont opérationnelles. L'ensemble des possibilités est exposée plus loin dans ce document. 

### 1.2 Fonctionnement général

Le protocole CALC utilise le protocole de transport TCP pour permettre les échanges entre le client et le serveur. Le client trouve le serveur grâce au numéro de port sur lequel il est connecté : nous choisissons le port 2021 comme port par défaut. 

Le client envoie une demande de connexion. En cas d'acceptation, le serveur lui répond en mentionnant l'ensemble des opérations qu'il supporte. Le client continue ensuite à envoyer des messages comportant les opérations acceptées par le serveur et le serveur répond avec les résultats de ces calculs. 

Pour effectuer un calcul, le client envoie le mot `compute` suivi de l'opération à effectuer (`ADD`, `MULT`, ...) et les deux `opérandes` souhaitées. Lorsque le serveur reçoit la requête `compute`, il vérifie si elle est correcte. Si elle l'est, il effectue le calcul et renvoie `result` suivi du résultat. Si la requête est incorrecte, le serveur retourne un message d'erreur `error` suivi du problème détecté. 

Le client décide quand interrompre la communication en envoyant `quit` au serveur. 

## 2 Messages

Seuls cinq types de messages sont reconnus lors de la communication entre le client et le serveur. Ces messages permettent de gérer simplement les interactions. 

Nous avons choisi : 

- `CONNECTION` 
- `CALC`
- `RESULT` 
- `ERROR`
- `QUIT` 

Les messages sont encodés avec `utf-8`. Le motif indiquant la fin de la ligne est `END`. 

#### 2.1 CONNECTION

Le message `CONNECTION` permet d'amorcer la connexion du client au serveur. Avec cette requête, le client signale au serveur qu'il souhaite lui communiquer des opérations à effectuer. 

Le serveur, s'il est disponible, répond au client en indiquant les opérations supportées. 

Exemple :

Client : `CONNECTION END`

Serveur : `CONNECTION - Welcome END ` 

Serveur : `Available : ADD, MULT END`

#### 2.2 CALC

Le message `CALC` permet au client d'indiquer que la suite de son message comporte un calcul que le serveur doit effectuer. Il doit ensuite préciser l'opération qu'il souhaite faire, ainsi que les deux opérandes nécessaires. 

Le serveur reçoit la requête commençant par `CALC` et lit les caractères suivants pour identifier l'opération et ses opérandes. 

Exemple : 

Client : `CALC ADD 4 5 END`

#### 2.3 RESULT

Le message `RESULT` permet au serveur d'indiquer que les caractères suivant sont le résultat de l'opération demandée par le client. 

Exemple : 

Serveur : `RESULT 9 END`

#### 2.4 ERROR

Le message `ERROR` permet au serveur de signaler qu'une erreur est survenue. Deux types d'erreurs peuvent être indiqués : soit la première partie du message est erronée (`CALC` est mal écrit ou une autre instruction non reconnue a été entrée), soit l'opération indiquée n'est pas reconnue par le serveur (`MIN` n'est pas implémenté). 

Exemple : 

Client : `CALC MIN 7 3 END `

Serveur : `ERROR - no such op END`

#### 2.5 QUIT

Lorsque le client ne souhaite plus effectuer d'opérations, il interrompt la connexion au serveur grâce au message `QUIT`. Lorsque le serveur reçoit ce message, il ferme la connexion. 

Exemple : 

Client : `QUIT END`

## 3 Eléments spécifiques

### 3.1 Opérations supportées

Les opérations supportées dans un premier temps par le serveur sont l'addition `ADD` et la multiplication `MULT`. 

### 3.2 Extensibilité

Après avoir implémenté les opérations de base, nous pourrons étendre le champs des possibles en proposant la soustraction `MIN`, la division `DIV` et les puissances `POW`. 

### 3.3 Traitement d'erreurs

En cas d'erreur, le traitement du calcul n'est pas effectué et le problème rencontré est identifié (cf. paragraphe 2.4), puis indiqué au client. 

## 4 Exemples

Client : `CONNECTION END`

Serveur : `CONNECTION - Welcome END ` 

Serveur : `Available : ADD, MULT END`

Client : `CALC ADD 7 15 END`

Serveur : `RESULT 22 END`

Client : `CALC MULT 3 4 END`

Serveur : `RESULT 12 END`

Client : `CALC DIV 6 2 END`

Serveur :  `ERROR - no such op END`

Client : `HIHI ADD 4 6 END`

Serveur : `ERROR - no such action END`

Client : `QUIT`
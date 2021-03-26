# Spécification d'un protocole applicatif CALC
Cette application à pour but de permettre à un serveur de calcul de répondre à une opération mathématique fait par un client.
Les opérations gérées sont l'additions et la multiplication.

## Quel protocole de transport utilisons nous ?
Le protocole est TCP/IP car le serveur doit pouvoir informer le client des opérations gérées par l'application et de le prévenir en cas de tout problème.

## Comment le client trouve le serveur (adresses et ports)?
L'application s'appelle CALC et le port a utilisé est le port 26994.

## Qui parle en premier ? Quel est la séquence de message échangé par le client et le serveur ? (flow)
Le client se connecte au serveur et le serveur donne la liste des opérations supportés. (INTRO)<br />
Le client peut ensuite envoyer un calcul sous le format : **Value** **Operator** **value**. (REQUEST)<br />
  Si le format n'est pas respecté ou que l'opérateur n'est pas supporté alors le serveur envoie un message d'erreur. (ERROR)<br />
  Si le format est supporté le serveur renvoie le résultat du calcul. (RESULT)<br />
Le client peut ensuite faire un nouveau calcul.<br />
Quand le client a fini ses demandes il peut quitter l'application en écrivant "exit". (EXIT)<br />

## Que ce passe t'il quand le message est reçu de l'autre côté ? (semantics)
Serveur -> INTRO : Attente d'une réponse de l'utilisateur. Le client peut faire une requête ou quitter.<br />
Client -> REQUEST : Le serveur traite le message et renvoie soit un RESUT ou une ERROR.<br />
Serveur -> ERROR : Le serveur n'a pas pu traiter la requête du client. Le client doit recommencer.<br />
Serveur -> RESULT : Le serveur a pu traiter la requête. Le client voit le résultat et peut refaire une requête ou quitter.<br />
Client -> EXIT : Le client quitte l'application.

## Quel est la syntaxe des messages ? Comment les générons nous et les analysons nous ? (syntax)
INTRO :
- Bienvenu sur CALC. Les opérations supportées sont l'addition (+) et la multiplication (*). Pour quitter taper "Exit"

REQUEST :
- **Valeur** **Opérateur** **Valeur**

ERROR :
- ERREUR : Une erreur est survenue -> **Information_sur_la_cause_potentielle**

RESULT :
- **La_requête_écrite_par_l_utilisateur** = **résultat_de_l_opération**

EXIT :
- Merci d'avoir visiter CALC. A bientôt !

## Qui ferme la connexion et quand ?
Le client ferme l'application avec la commande exit.

## Exemple
Serveur : Bienvenu sur CALC. Les opérations supportées sont l'addition (+) et la multiplication (*). Pour quitter taper "Exit".<br />
Client  : 4 + 5<br />
Serveur : 4 + 5 = 9<br />
Client  : 5 * 9<br />
Serveur : 7 * 6 = 45<br />
Client  : * + asd<br />
Serveur : ERREUR : Une erreur est survenue -> Le format est invalide. Le format a utilisé est : <Valeur> <Operateur> <Valeur><br />
Client  : 4 - 1<br />
Serveur : ERREUR : Une erreur est survenue -> L'opérateur (-) n'est pas supporté.<br />
Client  : Exit<br />
Serveur : Merci d'avoir visité CALC. A bientôt !<br />


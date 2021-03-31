# Specs

Port serveur => 6942

Connexion établie => Le serveur attend une saisie du client

Méthodes serveur : ERROR, RESULT
Méthodes client : CONNECT #ip #port, ADD, SOUS, EXIT

Opérations acceptées => ADD, SOUS

Messages d'erreurs => Le serveur retourne ERROR #code #explication

Les différents codes et leur explications : 

- 400 => erreur de saisie (exemples : type pas numérique, il manque des arguments, trop d'arguments)
- 401 => opération non supportée

Succès => Le serveur retourne RESULT #res

Quitter la connexion => EXIT ou timeout 1mn (inchallah)


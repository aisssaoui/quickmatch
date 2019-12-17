#### Bonjour, ceci est un guide pour monter la base de données postgreSQL sur votre serveur localhost:3000

##### En utilisant pgadmin

1. Installez pgadmin sur votre machine

2. Créez un base de données nommé "quickmatch_db" en utilisant pgadmin

3. laissez l'application pgadmin ouverte et vérifier bien que la database est connectée

4. créez un fichier .env et ajoutez la ligne suivante en remplaçant user et password par les identifiants du propriétaire de la base données " en général user par défaut = postgres":
   DATABASE_URL=postgres://user:password@localhost:5432/quickmatch_db

5. lancez dans votre terminal :

> npm install

> node db createTables

> npm run start

##### En utilsant la commande psql:

1. Lancez psql ou ouvrez le SQL shell

2. Entrez les informations nécessaires telles que serveur, base de données, port, nom d'utilisateur et mot de passe. Appuyez sur Entrée pour accepter la valeur par défaut.

3. Tappez la commande : CREATE DATABASE quickmatch_db.

4. créez un fichier .env et ajoutez la ligne suivante en remplaçant user et password par les identifiants du propriétaire de la base données " en général user par défaut = postgres":
   DATABASE_URL=postgres://user:password@localhost:5432/quickmatch_db

5. lancez dans votre terminal :

> npm install

> node db createTables

> npm run start

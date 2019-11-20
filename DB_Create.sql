DROP DATABASE IF EXISTS Quick_Match;
CREATE DATABASE Quick_Match ENCODING = 'UTF8';

SET client_encoding = 'UTF8';

CREATE TABLE IF NOT EXISTS Player (
id SERIAL PRIMARY KEY,
surname VARCHAR(40) NOT NULL,
first_name VARCHAR(40) NOT NULL,
mail_address VARCHAR(40),
phone_number VARCHAR(20),						/*A voir pour le type selon utilisation*/
address TEXT,
scored_goals SMALLINT DEFAULT 0 NOT NULL,
conceded_goals SMALLINT DEFAULT 0 NOT NULL,
matches_played SMALLINT DEFAULT 0 NOT NULL,
victories SMALLINT DEFAULT 0 NOT NULL
);

CREATE TABLE IF NOT EXISTS Club (				/* Table groupe*/
id SERIAL PRIMARY KEY,
title VARCHAR(40) NOT NULL,						/*Nom du groupe*/
creation_date DATE NOT NULL
);

/*Pas de table feuille stat car (1,1)-(1,1)*/

CREATE TABLE IF NOT EXISTS Slot (
id SERIAL PRIMARY KEY,
start_hour TIME NOT NULL,
end_hour TIME NOT NULL,
repeat_day VARCHAR(9)									/*Champ a preciser*/
);

CREATE TABLE IF NOT EXISTS Invitation (
id SERIAL PRIMARY KEY,
event_type VARCHAR(20)  						/*type discutable si liste finie d'event*/
);

CREATE TABLE IF NOT EXISTS Meet (				/*Nom a revoir peut etre*/
id SERIAL PRIMARY KEY,
slot SMALLINT,
invitation SMALLINT,
FOREIGN KEY (slot) REFERENCES Slot(id),
FOREIGN KEY (invitation) REFERENCES Invitation(id),
location TEXT, 									/*A voir pour le type*/
minimal_team_size SMALLINT
);

CREATE TABLE IF NOT EXISTS Player_Belong_Club (
player SMALLINT,
club SMALLINT,
is_admin SMALLINT,						/*Trouver un meilleur equivalent aux booléens maybe*/
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (club)   REFERENCES Club(id),
PRIMARY KEY (player, club)
);

CREATE TABLE IF NOT EXISTS Player_Received_Invitation (
player SMALLINT,
invitation SMALLINT,
accepted SMALLINT, 						/*Same problem boolean*/
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (invitation) REFERENCES Invitation(id),
PRIMARY KEY (player, invitation)
);

CREATE TABLE IF NOT EXISTS Player_Played_Meet (
player SMALLINT,
meet SMALLINT,
won SMALLINT, 							/*Same problem boolean*/
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (meet) REFERENCES Meet(id),
PRIMARY KEY (player, meet)
);

CREATE TABLE IF NOT EXISTS Player_Available_Slot ( /*Ajout d'un boolean ?*/
player SMALLINT,
slot SMALLINT,
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (slot) REFERENCES Slot(id),
PRIMARY KEY (player, slot)
);

/*ENGINE = INNODB; Pour chaque table si on utilise mySQL*/


	

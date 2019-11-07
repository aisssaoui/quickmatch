CREATE DATABASE Quick_Match CHARACTER SET 'utf8';
USE Quick_Match;

CREATE TABLE IF NOT EXISTS Player (
id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
surname VARCHAR(40) NOT NULL,
first_name VARCHAR(40) NOT NULL,
mail_address VARCHAR(40),
phone_number VARCHAR(20),						/*A voir pour le type selon utilisation*/
address TEXT,
scored_goals SMALLINT UNSIGNED DEFAULT 0 NOT NULL,
conceded_goals SMALLINT UNSIGNED DEFAULT 0 NOT NULL,
matches_played SMALLINT UNSIGNED DEFAULT 0 NOT NULL,
victories SMALLINT UNSIGNED DEFAULT 0 NOT NULL
);

CREATE TABLE IF NOT EXISTS Club (				/* Table groupe*/
id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(40) NOT NULL,						/*Nom du groupe*/
creation_date DATETIME NOT NULL
);

/*Pas de table feuille stat car (1,1)-(1,1)*/

CREATE TABLE IF NOT EXISTS Slot (
id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
start_hour TIME NOT NULL,
end_hour TIME NOT NULL,
repeat_day DATE									/*Champ a preciser*/
);

CREATE TABLE IF NOT EXISTS Invitation (
id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
event_type VARCHAR(20)  						/*type discutable si liste finie d'event*/
);

CREATE TABLE IF NOT EXISTS Meet (				/*Nom a revoir peut etre*/
id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
slot SMALLINT UNSIGNED,
invitation SMALLINT UNSIGNED,
FOREIGN KEY (slot) REFERENCES Slot(id),
FOREIGN KEY (invitation) REFERENCES Invitation(id),
location TEXT, 									/*A voir pour le type*/
minimal_team_size SMALLINT
);

CREATE TABLE IF NOT EXISTS Player_Belong_Group (
player SMALLINT UNSIGNED,
club SMALLINT UNSIGNED,
is_admin TINYINT UNSIGNED,						/*Trouver un meilleur equivalent aux booléens maybe*/
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (club)   REFERENCES Club(id),
PRIMARY KEY (player, club)
);

CREATE TABLE IF NOT EXISTS Player_Received_Invitation (
player SMALLINT UNSIGNED,
invitation SMALLINT UNSIGNED,
accepted TINYINT UNSIGNED, 						/*Same problem boolean*/
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (invitation) REFERENCES Invitation(id),
PRIMARY KEY (player, invitation)
);

CREATE TABLE IF NOT EXISTS Player_Played_Meet (
player SMALLINT UNSIGNED,
meet SMALLINT UNSIGNED,
won TINYINT UNSIGNED, 							/*Same problem boolean*/
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (meet) REFERENCES Meet(id),
PRIMARY KEY (player, meet)
);

CREATE TABLE IF NOT EXISTS Player_Available_Slot ( /*Ajout d'un boolean ?*/
player SMALLINT UNSIGNED,
slot SMALLINT UNSIGNED,
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (slot) REFERENCES Slot(id),
PRIMARY KEY (player, slot)
);

/*ENGINE = INNODB; Pour chaque table si on utilise mySQL*/


	

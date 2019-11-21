DROP DATABASE IF EXISTS Quick_Match;
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
creation_date DATETIME NOT NULL,
private TINYINT UNSIGNED
);

/*Pas de table feuille stat car (1,1)-(1,1)*/

CREATE TABLE IF NOT EXISTS Slot (
id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
start_hour TIME NOT NULL,
end_hour TIME NOT NULL,
repeat_day VARCHAR(9)									/*Champ a preciser*/
);

CREATE TABLE IF NOT EXISTS Invitation (
id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
slot SMALLINT UNSIGNED,
player SMALLINT UNSIGNED,
FOREIGN KEY (slot) REFERENCES Slot(id),
FOREIGN KEY (player) REFERENCES Player(id),
event_type VARCHAR(20)  						/*type discutable si liste finie d'event*/
);

CREATE TABLE IF NOT EXISTS Meet (				/*Nom a revoir peut etre*/
id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
slot SMALLINT UNSIGNED,
invitation SMALLINT UNSIGNED,
FOREIGN KEY (slot) REFERENCES Slot(id),
FOREIGN KEY (invitation) REFERENCES Invitation(id),
location TEXT,
precise_date DATE,								
minimal_team_size SMALLINT,
maximal_team_size SMALLINT,
deletion_date DATE
);

CREATE TABLE IF NOT EXISTS Player_Belong_Club (
player SMALLINT UNSIGNED,
club SMALLINT UNSIGNED,
is_admin TINYINT UNSIGNED,						/*Trouver un meilleur equivalent aux booléens maybe*/
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (club)   REFERENCES Club(id),
PRIMARY KEY (player, club)
);

CREATE TABLE IF NOT EXISTS Meet_Sheet (
player SMALLINT UNSIGNED,
meet SMALLINT UNSIGNED,
scored_goals SMALLINT UNSIGNED DEFAULT 0 NOT NULL,
conceded_goals SMALLINT UNSIGNED DEFAULT 0 NOT NULL,
won TINYINT UNSIGNED, 							/*Same problem boolean*/
FOREIGN KEY (player) REFERENCES Player(id),
FOREIGN KEY (meet) REFERENCES Meet(id),
PRIMARY KEY (player, meet)
);

CREATE TABLE IF NOT EXISTS Invitation_For_Meet (
invitation SMALLINT UNSIGNED,
meet SMALLINT UNSIGNED,
FOREIGN KEY (invitation) REFERENCES Invitation(id),
FOREIGN KEY (meet) REFERENCES Meet(id),
PRIMARY KEY (invitation, meet)
);

/*ENGINE = INNODB; Pour chaque table si on utilise mySQL*/


	

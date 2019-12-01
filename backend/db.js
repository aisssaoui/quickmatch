const { Pool } = require('pg');
const dotenv = require('dotenv');

dotenv.config();

const pool = new Pool({
  connectionString: process.env.DATABASE_URL
});

pool.on('connect', () => {
  console.log('connected to the db');
});

/**
 * Create Tables
 */
const createTables = () => {
  const queryText =
  `CREATE TABLE IF NOT EXISTS Player (
    id SMALLSERIAL PRIMARY KEY,
    surname VARCHAR(40) NOT NULL,
    first_name VARCHAR(40) NOT NULL,
    mail_address VARCHAR(40),
    phone_number VARCHAR(20),						/*A voir pour le type selon utilisation*/
    scored_goals SMALLINT DEFAULT 0 NOT NULL CHECK (scored_goals >= 0),
    conceded_goals SMALLINT DEFAULT 0 NOT NULL CHECK (conceded_goals >= 0),
    matches_played SMALLINT DEFAULT 0 NOT NULL CHECK (matches_played >= 0),
    victories SMALLINT DEFAULT 0 NOT NULL CHECK (victories >= 0)
    );
    
    CREATE TABLE IF NOT EXISTS Club (				/* Table groupe*/
    id SMALLSERIAL PRIMARY KEY,
    title VARCHAR(40) NOT NULL,						/*Nom du groupe*/
    creation_date TIMESTAMP NOT NULL,
    private BOOLEAN
    );
    
    /*Pas de table feuille stat car (1,1)-(1,1)*/
    
    CREATE TABLE IF NOT EXISTS Slot (
    id SMALLSERIAL PRIMARY KEY,
    start_hour TIME NOT NULL,
    end_hour TIME NOT NULL,
    repeat_day VARCHAR(9)									/*Champ a preciser*/
    );
    
    CREATE TABLE IF NOT EXISTS Invitation (
    id SMALLSERIAL PRIMARY KEY,
    slot SMALLINT CHECK (slot >= 0),
    player SMALLINT CHECK (player >= 0),
    FOREIGN KEY (slot) REFERENCES Slot(id),
    FOREIGN KEY (player) REFERENCES Player(id),
    event_type VARCHAR(20)  						/*type discutable si liste finie d'event*/
    );
    
    CREATE TABLE IF NOT EXISTS Meet (				/*Nom a revoir peut etre*/
    id SMALLSERIAL PRIMARY KEY,
    location TEXT,
    precise_date TIMESTAMP,
    minimal_team_size SMALLINT,
    maximal_team_size SMALLINT,
    deletion_date TIMESTAMP
    );
    
    CREATE TABLE IF NOT EXISTS Player_Belong_Club (
    player SMALLINT CHECK (player >= 0),
    club SMALLINT CHECK (club >= 0),
    is_admin BOOLEAN,
    FOREIGN KEY (player) REFERENCES Player(id),
    FOREIGN KEY (club)   REFERENCES Club(id),
    PRIMARY KEY (player, club)
    );
    
    CREATE TABLE IF NOT EXISTS Meet_Sheet (
    player SMALLINT CHECK (player >= 0),
    meet SMALLINT CHECK (meet >= 0),
    scored_goals SMALLINT CHECK (scored_goals >= 0) DEFAULT 0 NOT NULL,
    conceded_goals SMALLINT CHECK (conceded_goals >= 0) DEFAULT 0 NOT NULL,
    won BOOLEAN,
    FOREIGN KEY (player) REFERENCES Player(id),
    FOREIGN KEY (meet) REFERENCES Meet(id),
    PRIMARY KEY (player, meet)
    );
    
    CREATE TABLE IF NOT EXISTS Invitation_For_Meet (
    invitation SMALLINT CHECK (invitation >= 0),
    meet SMALLINT CHECK (meet >= 0),
    FOREIGN KEY (invitation) REFERENCES Invitation(id),
    FOREIGN KEY (meet) REFERENCES Meet(id),
    PRIMARY KEY (invitation, meet)
    )`;

  pool.query(queryText)
    .then((res) => {
      console.log(res);
      pool.end();
    })
    .catch((err) => {
      console.log(err);
      pool.end();
    });
}

/**
 * Drop Tables
 */
const dropTables = () => {
  const queryText = 'DROP TABLE IF EXISTS reflections';
  pool.query(queryText)
    .then((res) => {
      console.log(res);
      pool.end();
    })
    .catch((err) => {
      console.log(err);
      pool.end();
    });
}

pool.on('remove', () => {
  console.log('client removed');
  process.exit(0);
});

module.exports = {
  createTables,
  dropTables
};

require('make-runnable');
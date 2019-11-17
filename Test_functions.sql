SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Player_Belong_Club;
TRUNCATE TABLE Player_Played_Meet;   
TRUNCATE TABLE Player_Available_Slot;
TRUNCATE TABLE Player;
TRUNCATE TABLE Club;
TRUNCATE TABLE Slot;
TRUNCATE TABLE Invitation; 
TRUNCATE TABLE Meet; 
SET FOREIGN_KEY_CHECKS = 1;

-- Function that creates a new player
DELIMITER//
CREATE FUNCTION CreatePlayer(surname VARCHAR(40), first_name VARCHAR(40))
RETURNS INT
BEGIN
    INSERT INTO Player (surname, first_name, scored_goals, conceded_goals, matches_played, victories)
	VALUES (surname, first_name, 0, 0, 0, 0);
    RETURN 1;
END //
DELIMITER;

-- Function that adds scored goals of a player to the table
DELIMITER//
CREATE FUNCTION PlayerScoredGoals(sn VARCHAR(40), fn VARCHAR(40), scored_goals_game SMALLINT)
RETURNS INT
BEGIN
    UPDATE Player
    SET scored_goals = scored_goals + scored_goals_game
    WHERE surname = sn
    AND first_name = fn;
END //
DELIMITER;

-- Function that updates conceded goals after a game
DELIMITER//
CREATE FUNCTION PlayerconcededGoals(meet_id SMALLINT, winner_score SMALLINT, loser_score)
RETURNS INT
BEGIN
    UPDATE Player P INNER JOIN Player_Played_Meet M on P.id = M.player
    SET P.conceded_goals = P.conceded_goals + winner_score
    WHERE won = 0;
    UPDATE Player P INNER JOIN Player_Played_Meet M on P.id = M.player
    SET P.conceded_goals = P.conceded_goals + loser_score
    WHERE won = 1;
END //
DELIMITER;

-- Function that creates a club
DELIMITER//
CREATE FUNCTION CreateClub(club_name VARCHAR(40))
RETURNS INT
BEGIN
    INSERT INTO Club (title, creation_date)
	    VALUES (club_name, DATETIME);
END //
DELIMITER;
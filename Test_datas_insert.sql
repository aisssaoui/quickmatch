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

INSERT INTO Player (surname, first_name, scored_goals, conceded_goals, matches_played, victories)
	VALUES ('Chataigner', 'Johan', 1, 0, 1, 1),
			('Genty', 'Laurent', 2, 1, 1, 0),
            ('Issaoui', 'Ali', 2, 2, 1, 0),
            ('Abderrahmane', 'Faiz', 0, 0, 1, 1),
            ('Rosay', 'Maxime', 2, 2, 1, 0),
            ('Hala', 'Mehdi', 2, 1, 1, 0),
            ('Faverge', 'Mathieu', 100, 0, 1, 1),
            ('Delarue', 'Tony', 101, 0, 1, 1),
            ('Meline', 'Raphael', 1, 2, 1, 0),
            ('Danquigny', 'Antoine', 0, 0, 0, 0);

INSERT INTO Club (title, creation_date)
	VALUES ('Inria', '2019-11-16 17:00:00'),
			('Enseirb', '2019-11-16 17:00:00'),
            ('Gars', '2019-11-16 17:00:00'),
            ('Filles', '2019-11-16 17:00:00');

INSERT INTO Slot (start_hour, end_hour, repeat_day)
	VALUES ('14:00:00', '16:00:00', 'Monday'),
			('14:30:00', '16:30:00', 'Wednesday'),
            ('13:00:00', '15:00:00', 'Saturday'),
            ('15:00:00', '17:00:00', 'Sunday');
          
INSERT INTO Invitation (event_type)
	VALUES ('Match'),
			('Tournoi'),
            ('Match'),
            ('Match'),
            ('Tournoi');
           
INSERT INTO Meet (slot, invitation, location, minimal_team_size)
	VALUES (1, 1, 'François Bordes', 2),
			(2, 2, 'Cosec', 3),
            (3, 3, 'François Bordes', 2),
            (4, 4, 'Cosec', 2),
            (1, 5, 'François Bordes', 3);
            
INSERT INTO Player_Belong_Club (player, club, is_admin)
	VALUES 	(1, 2, 1),
			(2, 2, 0),
            (3, 2, 0),
            (4, 2, 0),
            (5, 2, 0),
            (6, 2, 0),
            (7, 2, 1),
            (8, 2, 0),
            (9, 1, 0),
            (10, 1, 0);
         
INSERT INTO Player_Played_Meet (player, meet, won)
	VALUES (1, 1, 1),
			(2, 1, 0),
            (3, 2, 0),
            (4, 2, 1),
            (5, 2, 0),
            (6, 3, 0),
            (7, 3, 1),
            (8, 4, 1),
            (9, 4, 0),
            (10, 1, 1);
	
INSERT INTO Player_Available_Slot (player, slot)
	VALUES (1, 1),
			(2, 1),
            (3, 1),
            (4, 1),
            (5, 1),
            (5, 2),
            (6, 2),
            (6, 4),
            (7, 3),
            (7, 4),
            (8, 1),
            (8, 2),
            (9, 1),
            (9, 4),
            (10, 1),
            (10, 3);
            
    
-- Indsæt værdier // test-data –-
INSERT INTO [user] (email, phonenumber, firstname, lastname, address, pincode, note, user_type_id)
VALUES ('admin@eamv.dk', '11111111', 'Admin', 'Test', 'Testgade 1', '111111', 'Admin', '1' ),
('instructor@eamv.dk', '22222222', 'Instructor', 'Test', 'Testgade 42', '222222', 'Instructor', '2'),
('client@eamv.dk', '33333333', 'Client', 'Test', 'Testgade 99', '333333', 'Client', '3');
						--email phone firstname lastname address pincode note team_id user_type_id
INSERT INTO [user] VALUES ('admin@resono.dk', '12345678', 'Jens', 'Jensen', 'Testgade 1', '123123', NULL, NULL, '1'); -- admin
INSERT INTO [user] VALUES ('instructor@resono.dk', '31415926', 'Børge', 'Svendsen', 'Testgade 1', '123456', NULL, NULL, '2'); -- instructor
INSERT INTO [user] VALUES ('client@resono.dk', '01123581321', 'Leonardo', 'Bonacci', 'Testgade 34, 5589 Pisa', '144233', NULL, NULL, '3'); -- client

INSERT INTO [team] VALUES ('Privatperson');
INSERT INTO [team] VALUES ('Hold 1');
INSERT INTO [team] VALUES ('Hold 2');
INSERT INTO [team] VALUES ('Hold 3');
INSERT INTO [team] VALUES ('Hold 4');
INSERT INTO [team] VALUES ('Hold 5');
INSERT INTO [team] VALUES ('Hold 6');
INSERT INTO [team] VALUES ('Tordenskjolds soldater');
INSERT INTO [team] VALUES ('Svarrepræsentanter');

INSERT INTO [task] VALUES ('Tegn dit følelseshus');
INSERT INTO [team] VALUES ('Integralligninger');
INSERT INTO [team] VALUES ('Vind over Flemming i skak');
INSERT INTO [team] VALUES ('Leg med en håndgranat');
INSERT INTO [team] VALUES ('Redundans');
INSERT INTO [task] VALUES ('Undgå at plagiere');
INSERT INTO [task] VALUES ('');
INSERT INTO [task] VALUES ('');

INSERT INTO [tag] VALUES ('Skak');
INSERT INTO [tag] VALUES ('Psykoterapi');
INSERT INTO [tag] VALUES ('Kropsterapi');
INSERT INTO [tag] VALUES ('Profilanalyse');
INSERT INTO [tag] VALUES ('Mentor');

INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=prnNSP1lFwU', 'FCE 2.01: Bondens kvadrat')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=YtpM8wMcMn0', 'FCE 2.04: De seks nøglefelter')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=bD1BxGoUjco', 'FCE 2.07D: Diagonal opposition')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=g24ESK4rcMY', 'FCE 6.01: Afskæring')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=P9nPnfzAp6w', 'FCE 2.03: Gensidig træktvang')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=DASugB-Ujw0', 'FCE 3.01+3.01A')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=XR--wzMu8Hs', 'Agopov-Nyback (KB2P vs KB4P)')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=6n-0eUA9xzo', 'How to completely uninstall Visual Studio Code from windows?')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=-5kIt83ldk8', 'How to install IntelliJ IDEA on Windows')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=m4-HM_sCvtQ', 'Why Java is the best programming language')
INSERT INTO [video] VALUES ('https://www.youtube.com/watch?v=CO4BGZOuUkM', 'How to Run C# in Visual Studio Code on Windows 10 2022')


-- hvis læseren vil gøre brug af scriptet til at indsætte i databasen, skal lokal path til .docx filer specificeres herunder. Ellers kan filer også indsættes via programmets GUI
-- INSERT INTO [file] ([file]) SELECT * FROM OPENROWSET (BULK '.docx', SINGLE_BLOB) AS x;

USE master;

IF DB_ID('OpgaveDB') IS NOT NULL
	DROP DATABASE OpgaveDB;

CREATE DATABASE OpgaveDB;
GO


USE OpgaveDB;

-- instructors kan lave nye hold og tilføje clients til dem --
CREATE TABLE [team] (
	[id] int IDENTITY(1, 1),
	[title] nVarChar(25)

	PRIMARY KEY (id)
	);

--Bruger-type - anfører hvorvidt de er en klient, underviser eller admin --
CREATE TABLE [user_type] (
	[id] int IDENTITY(1, 1),
	[type] nVarChar(10)

	PRIMARY KEY (id)
	);

-- Indsætter de forskellige brugertyper - altid faste værdier --
INSERT INTO user_type VALUES ('Admin');
INSERT INTO user_type VALUES ('Instructor');
INSERT INTO user_type VALUES ('Client');

--Bruger-tabellen --
CREATE TABLE [user] (
	[id] int IDENTITY(1, 1),
	[email] nVarChar(35) NOT NULL,
	[phonenumber] nVarChar(15) NOT NULL,
	[firstname] nVarChar(25) NOT NULL,
	[lastname] nVarChar(25) NOT NULL,
	[address] nVarChar(100) NOT NULL,
	[pincode] int NOT NULL,
	[note] nVarChar(max),
	[team_id] INT FOREIGN KEY REFERENCES [team](id),
	[user_type_id] INT FOREIGN KEY REFERENCES [user_type](id)
	
	PRIMARY KEY (id)
	);

-- oversigt over opgaver, med deres id og en beskrivelse -- 
CREATE TABLE [task] (
	[id] INT IDENTITY(1, 1),
	[title] nVarChar(50)

	PRIMARY KEY (id)
	);

-- bridging table mellem user og task --
CREATE TABLE [user_task] (
	[user_id] INT FOREIGN KEY REFERENCES [user](id),
	[task_id] INT FOREIGN KEY REFERENCES [task](id)
		
	ON DELETE CASCADE
	);

-- Video-url gemmes under et ID, så det kan indsættes i tasks -- 
CREATE TABLE [video] (
	[id] INT IDENTITY(1, 1),
	[url] nVarChar(100),
	[title] nVarChar(50)

	PRIMARY KEY (id)
	);

-- Filer gemmes under et ID, så de kan indsættes i tasks --
CREATE TABLE [file] (
	[id] INT IDENTITY(1, 1),
	[file] varBinary(MAX),
	[title] nVarChar(50)

	PRIMARY KEY (id)
	);

-- Tags gemmes under et ID, så de kan indsættes i tasks -- 
CREATE TABLE [tag] (
	[id] INT IDENTITY(1, 1),
	[title] nVarChar(50)

	PRIMARY KEY (id)
	);

-- Bridging table mellem task og video --
CREATE TABLE [task_video] (
	[task_id] INT FOREIGN KEY REFERENCES task(id),
	[video_id] INT FOREIGN KEY REFERENCES video(id)

	ON DELETE CASCADE
	);

-- Bridging table mellem task og file --
CREATE TABLE [task_file] (
	[task_id] INT FOREIGN KEY REFERENCES [task](id),
	[file_id] INT FOREIGN KEY REFERENCES [file](id)

	ON DELETE CASCADE
	);

-- Bridging table mellem tag og task--
CREATE TABLE [tag_task] (
	[tag_id] INT FOREIGN KEY REFERENCES [tag](id),
	[task_id] INT FOREIGN KEY REFERENCES [task](id)

	ON DELETE CASCADE
	);

-- Bridning table mellem tag og file--
CREATE TABLE [tag_file] (
	[tag_id] INT FOREIGN KEY REFERENCES [tag](id),
	[file_id] INT FOREIGN KEY REFERENCES [file](id)

	ON DELETE CASCADE
	);

-- Bridging table mellem tag og video--
CREATE TABLE [tag_video] (
	[tag_id] INT FOREIGN KEY REFERENCES [tag](id),
	[video_id] INT FOREIGN KEY REFERENCES [video](id)

	ON DELETE CASCADE
	);


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
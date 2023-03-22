USE master;

IF DB_ID('OpgaveDB') IS NOT NULL
	DROP DATABASE OpgaveDB;

CREATE DATABASE OpgaveDB;
GO


USE OpgaveDB;

--Bruger-tabellen --

CREATE TABLE [user] (
	[id] int IDENTITY(100, 1),
	[email] nVarChar(25) NOT NULL,
	[phonenumber] INT NOT NULL,
	[firstname] nVarChar(25) NOT NULL,
	[lastname] nVarChar(25) NOT NULL,
	[address] nVarChar(50) NOT NULL,
	[pincode] int,
	
	PRIMARY KEY (id)
	);

--Bruger-type - anfører hvorvidt de er en klient, underviser eller admin --
CREATE TABLE user_type (
	[id] int,
	[type] VarChar(10)

	PRIMARY KEY (id)
	);

-- Indsætter de forskellige brugertyper - altid faste værdier --
INSERT INTO user_type VALUES ('1', 'Admin');
INSERT INTO user_type VALUES ('2', 'Instructor');
INSERT INTO user_type VALUES ('3', 'Client');

-- oversigt over opgaver, med deres id og en beskrivelse -- 

CREATE TABLE task (
	[id] INT IDENTITY(1, 1),
	[text] ntext,

	PRIMARY KEY (id)
	);

-- bridging table mellem user og task --
CREATE TABLE user_task (
	[user] INT FOREIGN KEY REFERENCES [user](id),
	[task_id] INT FOREIGN KEY REFERENCES task(id)
		
		ON DELETE CASCADE
		);

-- Video-url gemmes under et ID, så det kan indsættes i tasks -- 
CREATE TABLE video (
	[id] INT,
	[url] nVarChar(100)

	PRIMARY KEY (id)
	);

-- Billeder gemmes under et ID, så det kan indsættes i tasks -- 
CREATE TABLE image (
	[id] INT,
	[image] image

	PRIMARY KEY (id)
	);

-- Bridging table mellem task og video --
CREATE TABLE task_video (
	[task_id] INT FOREIGN KEY REFERENCES task(id),
	[video_id] INT FOREIGN KEY REFERENCES video(id)

		ON DELETE CASCADE
		);

-- Bridging table mellem task og image -- 
CREATE TABLE task_image (
	[task_id] INT FOREIGN KEY REFERENCES task(id),
	[image_id] INT FOREIGN KEY REFERENCES image(id)

		ON DELETE CASCADE
		);

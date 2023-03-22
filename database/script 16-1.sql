USE master;

IF DB_ID('OpgaveDB') IS NOT NULL
	DROP DATABASE OpgaveDB;

CREATE DATABASE OpgaveDB;
GO


USE OpgaveDB;

--Bruger-tabellen --
CREATE TABLE [user] (
	[id] int IDENTITY(1, 1),
	[email] nVarChar(50) NOT NULL,
	[phonenumber] INT NOT NULL,
	[firstname] nVarChar(25) NOT NULL,
	[lastname] nVarChar(25) NOT NULL,
	[address] nVarChar(50) NOT NULL,
	[pincode] int NOT NULL,
	[profilePic] varBinary(MAX),
	[note] ntext
	
	PRIMARY KEY (id)
	);

-- instructors kan lave nye hold og tilf�je clients til dem --
CREATE TABLE [team] (
	[id] int IDENTITY(1, 1),
	[title] nVarChar(25)

	PRIMARY KEY (id)
	);

--Bruger-type - anfører hvorvidt de er en klient, underviser eller admin --
CREATE TABLE [user_type] (
	[id] int IDENTITY(1, 1),
	[type] VarChar(10)

	PRIMARY KEY (id)
	);

-- Indsætter de forskellige brugertyper - altid faste værdier --
INSERT INTO user_type VALUES ('Admin');
INSERT INTO user_type VALUES ('Instructor');
INSERT INTO user_type VALUES ('Client');

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
	[title] VarChar(50)

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
	[video_id] INT FOREIGN KEY REFERENCES [file](id)

	ON DELETE CASCADE
	);
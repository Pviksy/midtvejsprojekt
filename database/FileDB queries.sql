CREATE DATABASE FileDB

CREATE TABLE [file] (
	[id] INT IDENTITY(1, 1),
	[file] varBinary(MAX)

	PRIMARY KEY (id)
	);




SELECT * FROM [file]

SELECT [file] from [file] WHERE [id] = 4;




INSERT INTO [file]
SELECT * FROM OPENROWSET (BULK 'C:\Users\pvikp\OneDrive\Billeder\8.JPG', SINGLE_BLOB)
AS X

INSERT INTO [file]
SELECT * FROM OPENROWSET (BULK 'C:\Users\pvikp\Dropbox\#Programmering\Databaser\test.docx', SINGLE_BLOB) 
AS X


DELETE FROM [file] WHERE 0=0


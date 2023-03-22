SELECT * FROM [file]


INSERT INTO [file] SELECT [file], [title] FROM OPENROWSET (BULK 'C:\Users\pvikp\Dropbox\#Systemudvikling\Uge 36.docx', SINGLE_BLOB) AS X, 'Uge 36');


SELECT * FROM [tag]

SELECT TOP 1 * FROM [tag] ORDER BY [id] DESC

INSERT INTO [tag] SELECT TOP 1 * FROM [tag] ORDER BY [id] DESC, 'sgdfgfdg';


SELECT * FROM [video]

SELECT * FROM [tag_file]

SELECT * FROM [tag_video]

SELECT [id], [file], [title] FROM [file] JOIN [tag_file] ON [file].[id] = [tag_file].[file_id] WHERE [tag_file].[tag_id] = 1

SELECT * FROM [user]




SELECT * FROM [task]

SELECT * FROM [task_file]
SELECT * FROM [task_video]



SELECT * FROM [user_type]

SELECT [email], [pincode], [user_type_id] FROM [user] WHERE [email] = 'pl@mail.dk';

INSERT INTO [user]
VALUES ('m', '123', 'Instructor', 'Efternavn', 'adresse 99', '1', 'Tom note', null, '2');



SELECT * FROM [user]
SELECT * FROM [user_task]
SELECT [task].[id], [task].[title] FROM [task] JOIN [user_task] ON [task].[id] = [user_task].[task_id] WHERE [user_task].[user_id] = 16

UPDATE [user] SET [pincode] = '000000' WHERE [id] = 19
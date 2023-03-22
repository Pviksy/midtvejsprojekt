SELECT * FROM[video]

SELECT * FROM [tag_video]

SELECT * FROM [tag_task]


SELECT * FROM [video]


INSERT INTO [task_file] VALUES (4,4)
INSERT INTO [task_video] VALUES (4,4)

SELECT * FROM [file]
SELECT * FROM [task]
SELECT * FROM [task_file]
SELECT * FROM [task_video]



SELECT [id], [file], [title] FROM [file] JOIN [task_file] ON [file].[id] = [task_file].[file_id] WHERE [task_file].[task_id] = 4
SELECT [id], [url], [title] FROM [video] JOIN [task_video] ON [video].[id] = [task_video].[video_id] WHERE [task_video].[task_id] = 4


INSERT INTO [task_file] ([task_id], [file_id]) VALUES ('9', '7')


SELECT * FROM [task]
SELECT * FROM [tag]
SELECT * FROM [tag_task]

INSERT INTO [tag_video] ([tag_id], [video_id]) VALUES ('7', '2')


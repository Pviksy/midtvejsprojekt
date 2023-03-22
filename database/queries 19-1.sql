SELECT * FROM [team]

INSERT INTO [user] (email, phonenumber, firstname, lastname, [address], pincode, [team_id])
VALUES 
('jonas.pedersen@hotmail.dk', '30622907', 'Jonas', 'Pedersen', 'Sønderparken 26', '123456', '6'),
('mikkel@weirdchamp.dk', '45732645', 'Mikkel', 'Weirdchamp', 'Børgegade 42', '133769', '6'),
('ulrikke@gammel.dk', '21678423', 'Ulrikke', 'Gammelgaard', 'Plejecenter 99', '111111', '6'),
('casper@lingling.dk', '30347284', 'Casper', 'Ching Chong', 'Kattehjem 7', '645654', '6');

SELECT * FROM [user]


INSERT INTO [user] ([email], [phonenumber], [firstname], [lastname], [address], [pincode]) VALUES ('', '', '', '', '', '')

ALTER TABLE [user] ADD [team_id] INT 
FOREIGN KEY REFERENCES [team](id)




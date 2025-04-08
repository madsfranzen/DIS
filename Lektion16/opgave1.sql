use ddba
select * from kontoa
select * from personadr

use ddbb
select * from kontob
select * from personloen

use ddba

DROP VIEW IF EXISTS person
GO

CREATE VIEW person AS 
SELECT p.cpr, p.navn, p.bynavn, pb.loen, pb.skatteprocent
FROM personadr p
JOIN [ddbb].dbo.personloen pb
ON p.cpr = pb.cpr    
GO

SELECT * FROM person

DROP VIEW IF EXISTS konto
GO

CREATE VIEW konto AS
SELECT kontoa.kontonr, kontoa.rente, kontoa.kontohavercpr
FROM kontoa
UNION ALL
SELECT kontob.kontonr, kontob.rente, kontob.kontohavercpr
FROM [ddbb].dbo.kontob
GO

SELECT * FROM konto
SELECT * FROM person

-- UDREGN RESTSKAT
-- Restskat = rente * skatteprocent / 100
SELECT p.navn as 'Navn', SUM(k.rente) * p.skatteprocent / 100 as 'Restskat til betaling'
FROM person p
JOIN konto k
ON p.cpr = k.kontohavercpr 
WHERE k.rente > 0
GROUP BY skatteprocent, navn


-- INSERT TRIGGER SOM INSERTER I BEGGE DATABASER
CREATE OR ALTER TRIGGER insert_person ON person
INSTEAD OF INSERT
AS 
INSERT INTO [ddba].[dbo].personadr (cpr, navn, bynavn)
SELECT cpr, navn, bynavn
FROM inserted
INSERT INTO [ddbb].[dbo].personloen (cpr, loen, skatteprocent)
SELECT cpr, loen, skatteprocent
FROM inserted
GO

SELECT * FROM person

INSERT INTO person (cpr, navn, bynavn, loen, skatteprocent)
VALUES (1234567890, 'Hans Hansen', 'Helsingør', 50000, 37),
       (1234567891, 'Peter Petersen', 'Helsingør', 60000, 40),
       (1234567892, 'Lone Larsen', 'Helsingør', 70000, 42)
GO

SELECT * FROM person
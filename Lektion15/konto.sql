use BankDB
ALTER DATABASE BankDB SET ALLOW_SNAPSHOT_ISOLATION ON
-- Check if the table exists before attempting to drop it
if EXISTS (
    SELECT
        *
    FROM
        sys.objects
    WHERE
        name='konto' AND
        type='U'
)
DROP TABLE konto if EXISTS (
    SELECT
        *
    FROM
        sys.objects
    WHERE
        name='bankkunde' AND
        type='U'
)
DROP TABLE bankkunde
CREATE TABLE
    bankkunde (cpr CHAR(10) PRIMARY key, navn VARCHAR(50))
CREATE TABLE
    konto (kontonr INT PRIMARY key, saldo INT, kunde CHAR(10) FOREIGN key REFERENCES bankkunde)
INSERT INTO
    bankkunde
VALUES
    ('12', 'Hansen')
INSERT INTO
    bankkunde
VALUES
    ('13', 'Jensen')
INSERT INTO
    bankkunde
VALUES
    ('14', 'Pedersen')
INSERT INTO
    bankkunde
VALUES
    ('15', 'Ibsen')
INSERT INTO
    konto
VALUES
    (1001, 100, '12')
INSERT INTO
    konto
VALUES
    (1002, 1000, '12')
INSERT INTO
    konto
VALUES
    (1003, 10000, '13')
INSERT INTO
    konto
VALUES
    (1004, 100000, '14')
SELECT
    *
FROM
    bankkunde
SELECT
    *
FROM
    konto
INSERT INTO Doctor (username,firstName,lastName,gender,email,phone,password)
VALUES
('johnsmith1234','John','Smith','M','johnsmith1234@gmail.com','5851234567','change_me_NOW'),
('ChickenX1','Leroy','Jenkins','M','atleastihavechicken@yahoo.com','7891333337','chicken'),
('Dr.Mario','Mario','Mario','M','mario@google.com','6541457830','PrincessPeach'),
('Half-Life','Gordon','Freeman','M','silenthero@hotmail.com','1592453621','alyxvance'),
('Wizard','Albus','Dumbledore','M','masterwizard@gmail.com','4781245609','magic');

INSERT INTO Patient (username,firstName,lastName,gender,email,phone,password)
VALUES
('app13','Caroline','Appleton','F','caroline.appleton@gmail.com','8521456579','mypassword'),
('fluffycat','Henry','Minsy','M','coolbeans@live.com','9999999999','opensaysme'),
('fluffydog','Norman','Greenly','M','fluffydog8080@gmail.com','7456321548','stuff&junk'),
('TheScienceGuy','Bill','Nye','M','scienceRULES@rocketmail.com','9874563210','BILL!BILL!BILL!BILL!'),
('XxGardenGuyxX','Pete','Moss','F','mosseverywhere@ymail.com','1203569805','BADpassword');

INSERT INTO Appointment (doctor_id,patient_id,date,duration)
VALUES
(1,1,'2012-04-05 14:30:00',60),
(3,5,'2012-04-01 10:00:00',30),
(4,4,'2012-04-14 11:30:00',120),
(5,2,'2012-04-28 13:30:00',60),
(5,3,'2012-04-29 15:00:00',60),
(1,1,'2012-05-13 14:30:00',60),
(3,5,'2012-05-12 10:00:00',30),
(4,4,'2012-05-13 11:30:00',120),
(5,2,'2012-05-14 13:30:00',60),
(5,3,'2012-05-12 15:00:00',60);
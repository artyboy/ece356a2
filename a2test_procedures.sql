DELIMITER $

DROP PROCEDURE IF EXISTS Test_ResetDB;

CREATE PROCEDURE Test_ResetDB()
BEGIN
/* resets the database to the initial state described in Section 3 */
 	DELETE FROM Review;
 	DELETE FROM Friend;
 	DELETE FROM DoctorAddress;
 	DELETE FROM DoctorSpecialization;
 	DELETE FROM Doctor;
 	DELETE FROM Patients;

	INSERT INTO Doctor(doc_alias,name,gender,email,license_year) 
	VALUES ('doc_aiken','John Aikenhead','male','aiken@head.com',1990),
	('doc_amnio','Jane Amniotic','female','obgyn_clinic@rogers.com',2005),
	('doc_umbilical','Mary Umbilical','female','obgyn_clinic@rogers.com',2006),
	('doc_heart','Jack Hearty','male','jack@healthyheart.com',1980),
	('doc_cutter','Beth Cutter','female','beth@tummytuck.com',2014);

	INSERT INTO DoctorAddress(doc_alias,street,city,province,postal_code) 
	VALUES ('doc_aiken','1 Elizabeth Street','Waterloo','Ontario', 'N2L 2W8'),
	('doc_aiken','1 Jane Street','Kitchener','Ontario', 'N2P 1K2'),
	('doc_amnio','1 Elizabeth Street','Waterloo','Ontario', 'N2L 2W8'),
	('doc_amnio','2 Amniotic Street','Kitchener','Ontario', 'N2P 2K5'),
	('doc_umbilical','1 Mary Street','Cambridge','Ontario', ' N2L 1A2'),
	('doc_umbilical','2 Amniotic Street','Kitchener','Ontario', 'N2P 2K5'),
	('doc_heart','1 Jack Street','Guelph','Ontario', 'N2L 1G2'),
	('doc_heart','2 Heart Street','Waterloo','Ontario', 'N2P 2W5'),
	('doc_cutter','1 Beth Street','Cambridge','Ontario', ' N2L 1C2'),
	('doc_cutter','2 Cutter Street','Kitchener','Ontario', 'N2P 2K5');

	INSERT INTO DoctorSpecialization(doc_alias,specialization)
	VALUES ('doc_aiken','allergologist'),
	('doc_aiken','naturopath'),
	('doc_amnio','obstetrician'),
	('doc_amnio','gynecologist'),
	('doc_umbilical','obstetrician'),
	('doc_umbilical','naturopath'),
	('doc_heart','cardiologist'),
	('doc_heart','surgeon'),
	('doc_cutter','surgeon'),
	('doc_cutter','psychiatrist');



	INSERT INTO Patients(pat_alias,pat_name,city,province,email) 
	VALUES ('pat_bob','Bob Bobberson', 'Waterloo', 'Ontario','thebobbersons@sympatico.ca'),
	('pat_peggy','Peggy Bobberson', 'Waterloo', 'Ontario','thebobbersons@sympatico.ca'),
	('pat_homer','Homer Homerson', 'Kitchener', 'Ontario','homer@rogers.com'),
	('pat_kate','Kate Katemyer', 'Cambridge', 'Ontario','kate@hello.com'),
	('pat_anne','Anne MacDonald', 'Guelph', 'Ontario','anne@gmail.com');
END
$
DELIMITER ;
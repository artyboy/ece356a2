DELIMITER $

DROP PROCEDURE IF EXISTS Test_ResetDB;

CREATE PROCEDURE Test_ResetDB()
BEGIN
/* resets the database to the initial state described in Section 3 */
 
	INSERT INTO Doctor(doc_alias,name,gender,email,years_licensed) 
	VALUES ('doc_aiken','John Aikenhead','male','aiken@head.com',license_year),
	('doc_amnio','Jane Amniotic','female','obgyn_clinic@rogers.com',2005),
	('doc_umbilical','Mary Umbilical','female','obgyn_clinic@rogers.com',2006),
	('doc_heart','Jack Hearty','male','jack@healthyheart.com',1980),
	('doc_cutter','Beth Cutter','female','beth@tummytuck.com',2014)
	;
END
$
DELIMITER ;
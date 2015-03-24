
DELIMITER $

DROP PROCEDURE IF EXISTS Test_DoctorSearch;

CREATE PROCEDURE Test_DoctorSearch(IN gender VARCHAR(20), IN city VARCHAR(20),
 IN specialization VARCHAR(20), IN num_years_licensed INT, OUT num_matches INT)
 BEGIN
/*select count(*) into count from Doctor;*/
	select count(*)  into num_matches from (select Doctor.doc_alias
		from Doctor natural join DoctorAddress natural join DoctorSpecialization
		where Doctor.doc_alias=DoctorAddress.doc_alias 
		and Doctor.doc_alias=DoctorSpecialization.doc_alias
	 	and Doctor.gender=gender and DoctorAddress.city like CONCAT('%',city,'%')
	 	and DoctorSpecialization.specialization=specialization
	 	and (YEAR(CURDATE())-Doctor.license_year)=num_years_licensed
	 	GROUP BY Doctor.doc_alias) as DoctorSimple;
END
$
DELIMITER ;
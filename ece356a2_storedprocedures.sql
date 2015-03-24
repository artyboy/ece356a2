/*5.1 Reset Database*/

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

/* 5.2 - Patient Search */
DROP PROCEDURE IF EXISTS Test_PatientSearch; 
DELIMITER $
CREATE PROCEDURE Test_PatientSearch (IN inProvince VARCHAR(20), IN inCity VARCHAR(20), OUT num_matches INT)
BEGIN 
/* returns in num_matches the total number of patients in the given province and city */ 
     SELECT COUNT(*) INTO num_matches FROM Patients 
     WHERE Patients.city=inCity AND Patients.province=inProvince;
END
$ 
DELIMITER;
 
/* 5.3 - Basic Doctor Search */
DELIMITER $

DROP PROCEDURE IF EXISTS Test_DoctorSearch;

CREATE PROCEDURE Test_DoctorSearch(IN gender VARCHAR(20), IN city VARCHAR(20),
 IN specialization VARCHAR(20), IN num_years_licensed INT, OUT num_matches INT)
 BEGIN
	/* returns in num_matches the total number of doctors that match exactly on all the given
criteria: gender ('male' or 'female'), city, specialization, and number of years licensed */
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

/* 5.4 - Doctor Search By Average Star Rating */
DELIMITER $

DROP PROCEDURE IF EXISTS Test_DoctorSearchStarRating;

CREATE PROCEDURE Test_DoctorSearchStarRating
(IN avg_star_rating FLOAT, OUT num_matches INT)
BEGIN
 	/* returns in num_matches the total number of doctors that match exactly on all the given
criteria: gender ('male' or 'female'), city, specialization, and number of years licensed */
	select count(*) into num_matches from (select Doctor.doc_alias,avg(rating) as avg_srate
			from Doctor natural join Review 
			where Doctor.doc_alias=Review.doc_alias 
			group by Doctor.doc_alias) as Ratings
	where Ratings.avg_srate>=avg_star_rating;
END
$
DELIMITER ;

/* 5.5 - Doctor Search By Friend Review */
DELIMITER $

DROP PROCEDURE IF EXISTS Test_DoctorSearchFriendReview;

CREATE PROCEDURE Test_DoctorSearchFriendReview
(IN patient_alias VARCHAR(20), IN review_keyword VARCHAR(20), OUT num_matches INT)
BEGIN
/* returns in num_matches the total number of doctors who have been reviewed by friends of
the given patient, and where at least one of the reviews for that doctor (not necessarily
written by a friend) contains the given keyword (case-sensitive) */
	select count(*) into num_matches from 
	(select Doctor.doc_alias from Doctor natural join Review
	where Doctor.doc_alias=Review.doc_alias 
		and ( Review.pat_alias in(
		select pat_alias from Patients
			where pat_alias=(
				select pat_alias1 from Friend where pat_alias2=patient_alias 
				and pat_alias1 in
					(select pat_alias2 from Friend where pat_alias1=patient_alias)
			)
		)
		and comments like CONCAT('%',review_keyword,'%'))
	group by Doctor.doc_alias) as Friend;
END
$
DELIMITER ;
 
/* 5.6 - Request Friendship*/
DROP PROCEDURE IF EXISTS Test_RequestFriend; 
DELIMITER $ 
CREATE PROCEDURE Test_RequestFriend (IN requestor_alias VARCHAR(20), IN requestee_alias VARCHAR(20))
BEGIN 
/* add friendship request from requestor_alias to requestee_alias */ 
    INSERT INTO Friend (pat_alias1, pat_alias2) VALUES (requestor_alias, requestee_alias);
END
$ DELIMITER;
 
 
/* 5.7 - Confirm Friendship Request */
DROP PROCEDURE IF EXISTS Test_ConfirmFriendRequest; 
DELIMITER $ 
CREATE PROCEDURE Test_ConfirmFriendRequest (IN requestor_alias VARCHAR(20), IN requestee_alias VARCHAR(20)) 
BEGIN
/* add friendship between requestor_alias and requestee_alias, assuming that friendship was requested previously */ 
    INSERT INTO Friend (pat_alias1, pat_alias2) VALUES (requestee_alias, requestor_alias);
END
$ DELIMITER;
 
/* 5.8 - Check Friendship*/
DROP PROCEDURE IF EXISTS Test_AreFriends; 
DELIMITER $ 
CREATE PROCEDURE Test_AreFriends (IN patient_alias_1 VARCHAR(20), IN patient_alias_2 VARCHAR(20), OUT are_friends INT) 
BEGIN 
/* returns 1 in are_friends if patient_alias_1 and patient_alias_2 are friends, 0 otherwise */ 
    DECLARE temp INT DEFAULT 0;
    SELECT COUNT(*) INTO temp FROM Friend WHERE pat_alias1=patient_alias_1 AND pat_alias2=patient_alias_2 
                OR pat_alias1=patient_alias_2 AND pat_alias2=patient_alias_1; 
    IF(temp = 2) THEN SET are_friends = 1;
    ELSE SET are_friends = 0;
        END IF;
END
$ DELIMITER;

/* 5.9 Add Review */
DELIMITER $

DROP PROCEDURE IF EXISTS Test_AddReview;

CREATE PROCEDURE Test_AddReview
(IN patient_alias VARCHAR(20), IN doctor_alias VARCHAR(20), IN star_rating FLOAT,
IN comments VARCHAR(256))
BEGIN
/* add review by patient_alias for doctor_alias with the given star_rating and comments
fields, assign the current date to the review automatically, assume that star_rating is an
integer multiple of 0.5 (e.g., 1.5, 2.0, 2.5, etc.) */
 
	INSERT INTO Review(Review.pat_alias,Review.doc_alias,Review.date,Review.rating,Review.comments) 
	VALUES (patient_alias,doctor_alias,CURDATE(),star_rating,comments);
END
$
DELIMITER ;

/* 5.10 - Check Reviews */

DELIMITER $

DROP PROCEDURE IF EXISTS Test_CheckReviews;
CREATE PROCEDURE Test_CheckReviews
(IN doctor_alias VARCHAR(20), OUT avg_star FLOAT, OUT num_reviews INT)
BEGIN
 
	select avg(rating) as avg_star_rating,
		count(rating) as num_of_reviews
		into avg_star, num_reviews
		from Doctor natural join Review 
		where Doctor.doc_alias=Review.doc_alias 
		and doc_alias=doctor_alias
		group by Doctor.doc_alias;
END
$
DELIMITER ;
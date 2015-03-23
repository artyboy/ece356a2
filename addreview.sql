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
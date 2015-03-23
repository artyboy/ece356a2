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
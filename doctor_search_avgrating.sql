DELIMITER $

DROP PROCEDURE IF EXISTS Test_DoctorSearchStarRating;

CREATE PROCEDURE Test_DoctorSearchStarRating
(IN avg_star_rating FLOAT, OUT num_matches INT)
BEGIN
 
	select count(*) into num_matches from (select Doctor.doc_alias,avg(rating) as avg_srate
			from Doctor natural join Review 
			where Doctor.doc_alias=Review.doc_alias 
			group by Doctor.doc_alias) as Ratings
	where Ratings.avg_srate>=avg_star_rating;
END
$
DELIMITER ;
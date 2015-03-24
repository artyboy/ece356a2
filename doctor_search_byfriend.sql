DELIMITER $

DROP PROCEDURE IF EXISTS Test_DoctorSearchFriendReview;

CREATE PROCEDURE Test_DoctorSearchFriendReview
(IN patient_alias VARCHAR(20), IN review_keyword VARCHAR(20), OUT num_matches INT)
BEGIN
 
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
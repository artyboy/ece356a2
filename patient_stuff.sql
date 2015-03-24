/* 5.2 - Patient Search */
DROP PROCEDURE IF EXISTS Test_PatientSearch; 
DELIMITER $
CREATE PROCEDURE Test_PatientSearch (IN inProvince VARCHAR(20), IN inCity VARCHAR(20), OUT num_matches INT)
BEGIN /* returns in num_matches the total number of patients in the given province and city */ 
     SELECT COUNT(*) INTO num_matches FROM Patients WHERE Patients.city=inCity AND Patients.province=inProvince;
END
$ 
DELIMITER;
/*CALL Test_PatientSearch('Ontario', 'Waterloo', @patCount);
SELECT @patCount;*/
 
 
/* 5.6 - Request Friendship*/
DROP PROCEDURE IF EXISTS Test_RequestFriend; 
DELIMITER $ 
CREATE PROCEDURE Test_RequestFriend (IN requestor_alias VARCHAR(20), IN requestee_alias VARCHAR(20))
BEGIN /* add friendship request from requestor_alias to requestee_alias */ 
    INSERT INTO Friend (pat_alias1, pat_alias2) VALUES (requestor_alias, requestee_alias);
END
$ DELIMITER;
/*
Foreign key relationships involve a parent table that holds the central data values, 
and a child table with identical values pointing back to its parent. 
The FOREIGN KEY clause is specified in the child table.
It will reject any INSERT or UPDATE operation that attempts to create a foreign key value 
in a child table if there is no a matching candidate key value in the parent table.
*/
/* CALL Test_RequestFriend ('pat_kate', 'pat_bob'); */
 
 
/* 5.7 - Confirm Friendship Request */
DROP PROCEDURE IF EXISTS Test_ConfirmFriendRequest; 
DELIMITER $ 
CREATE PROCEDURE Test_ConfirmFriendRequest (IN requestor_alias VARCHAR(20), IN requestee_alias VARCHAR(20)) 
BEGIN /* add friendship between requestor_alias and requestee_alias, assuming that friendship was requested previously */ 
    INSERT INTO Friend (pat_alias1, pat_alias2) VALUES (requestee_alias, requestor_alias);
END
$ DELIMITER;
/*CALL Test_ConfirmFriendRequest('pat_nathan', 'pat_kate');*/
 
/* 5.8 - Check Friendship*/
DROP PROCEDURE IF EXISTS Test_AreFriends; 
DELIMITER $ 
CREATE PROCEDURE Test_AreFriends (IN patient_alias_1 VARCHAR(20), IN patient_alias_2 VARCHAR(20), OUT are_friends INT) 
BEGIN /* returns 1 in are_friends if patient_alias_1 and patient_alias_2 are friends, 0 otherwise */ 
    DECLARE temp INT DEFAULT 0;
    SELECT COUNT(*) INTO temp FROM Friend WHERE pat_alias1=patient_alias_1 AND pat_alias2=patient_alias_2 
                OR pat_alias1=patient_alias_2 AND pat_alias2=patient_alias_1; 
    IF(temp = 2) THEN SET are_friends = 1;
    ELSE SET are_friends = 0;
        END IF;
END
$ DELIMITER;
/* CALL Test_AreFriends('sai', 'pat_nathan', @friendStatus); 
   SELECT @friendStatus;
*/
package com.mass.UniversityCourseSelection.repo;

import org.springframework.stereotype.Repository;

@Repository
public interface ILoginRepository {
	
	
	boolean verifyApplicantCredentials(int id,String password);
	boolean verifyAdmissionCommiteeMemberCredentials(int id,String password);
	boolean verifyUniversityStaffMemberCredentials(int id,String password);

}

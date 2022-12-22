package com.mass.UniversityCourseSelection.services;

public interface ILoginService {
	
	boolean loginAsApplicant(int id,String password);
	boolean loginAsAdmissionCommiteeMember(int id,String password);
	boolean loginAsUniversityStaffMember(int id,String password);

}

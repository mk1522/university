package com.mass.UniversityCourseSelection.services;

import com.mass.UniversityCourseSelection.repo.ILoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService{

	@Autowired
	private ILoginRepository logRepo;
	
	@Override
	public boolean loginAsApplicant(int id, String password) {
		
		return logRepo.verifyApplicantCredentials(id, password);
	}

	@Override
	public boolean loginAsAdmissionCommiteeMember(int id, String password) {
		// TODO Auto-generated method stub
		return logRepo.verifyAdmissionCommiteeMemberCredentials(id, password);
	}

	@Override
	public boolean loginAsUniversityStaffMember(int id, String password) {
		// TODO Auto-generated method stub
		return logRepo.verifyUniversityStaffMemberCredentials(id, password);
	}

}

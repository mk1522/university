package com.mass.UniversityCourseSelection.services;

import java.util.List;

import com.mass.UniversityCourseSelection.entities.Admission;
import com.mass.UniversityCourseSelection.entities.AdmissionCommiteeMember;
import com.mass.UniversityCourseSelection.entities.AdmissionStatus;
import com.mass.UniversityCourseSelection.entities.Applicant;

public interface IAdmissionCommiteeMemberService {

	AdmissionCommiteeMember addCommitteeMember(AdmissionCommiteeMember member);

	AdmissionCommiteeMember updateCommitteeMember(AdmissionCommiteeMember member);

	AdmissionCommiteeMember viewCommitteeMember(int id);

	void removeCommitteeMember(int id);

	List<AdmissionCommiteeMember> viewAllCommitteeMembers();

	AdmissionStatus provideAdmissionResult(Applicant applicant, Admission admission);

}

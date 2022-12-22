package com.mass.UniversityCourseSelection.services;

import java.util.List;
import java.util.Optional;

import com.mass.UniversityCourseSelection.entities.Applicant;
public interface IApplicantService {
	
	
	Applicant addApplicant(Applicant app);
 	Applicant updateApplicant(Applicant app);
 	Applicant deleteApplicant(Applicant app);
 	Optional<Applicant> viewApplicant(int id);
 	List<Applicant> viewAllApplicantsByStatus(int status);

}

package com.mass.UniversityCourseSelection.services;

import java.util.List;
import java.util.Optional;

import com.mass.UniversityCourseSelection.entities.Applicant;
import com.mass.UniversityCourseSelection.entities.Course;
import com.mass.UniversityCourseSelection.exception.NotFoundException;
import com.mass.UniversityCourseSelection.repo.IApplicantRepository;
import com.mass.UniversityCourseSelection.repo.ICourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApplicantServiceImpl implements IApplicantService {
	
	@Autowired
	IApplicantRepository repo;
	
	@Autowired
	ICourseRepository courseRepo;

	@Override
	public Applicant addApplicant(Applicant app) {
		if(app.getAdmission()==null || app.getAdmission().getCourseId()==0) {
			throw new NotFoundException("Please Enter admission details!");
		}
		if(!courseRepo.existsById(app.getAdmission().getCourseId()))
			throw new NotFoundException("Course not found");
		else {
			Course course=courseRepo.findById(app.getAdmission().getCourseId()).get();
			if(course.getStatus().equals("INACTIVE")) {
				throw new NotFoundException("Course is not available");
			}
		}
			
		Applicant temp=repo.save(app);
		temp.getAdmission().setApplicantId(temp.getApplicantId());
		return repo.save(temp);
	}

	@Override
	public Applicant updateApplicant(Applicant app) {
		if(app==null ||!repo.existsById(app.getApplicantId()))
			throw new NotFoundException("Applicant does'nt exist!");
		if(app.getAdmission()==null || app.getAdmission().getCourseId()==0) {
			throw new NotFoundException("Please Enter admission details!");
		}
		if(!courseRepo.existsById(app.getAdmission().getCourseId()))
			throw new NotFoundException("Course not found");
		else {
			Course course=courseRepo.findById(app.getAdmission().getCourseId()).get();
			if(course.getStatus().equals("INACTIVE")) {
				throw new NotFoundException("Course is not available");
			}
		}
		return repo.save(app);
	}

	@Override
	public Applicant deleteApplicant(Applicant app) {
		if(app==null ||!repo.existsById(app.getApplicantId()))
			throw new NotFoundException("Applicant does'nt exist!");
		repo.delete(app);
		return app;
	}

	@Override
	public Optional<Applicant> viewApplicant(int id) {
		if(!repo.existsById(id))
			throw new NotFoundException("Applicant does'nt exist!");
	   Optional<Applicant> res= repo.findById(id);
	   return res;
	}

	@Override
	public List<Applicant> viewAllApplicantsByStatus(int status) {
		  return repo.viewAllApplicantByStatus(status);
	}

}

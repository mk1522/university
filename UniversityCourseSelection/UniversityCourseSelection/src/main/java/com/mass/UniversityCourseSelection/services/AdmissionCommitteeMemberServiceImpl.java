package com.mass.UniversityCourseSelection.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mass.UniversityCourseSelection.entities.Admission;
import com.mass.UniversityCourseSelection.entities.AdmissionCommiteeMember;
import com.mass.UniversityCourseSelection.entities.AdmissionStatus;
import com.mass.UniversityCourseSelection.entities.Applicant;
import com.mass.UniversityCourseSelection.entities.Course;
import com.mass.UniversityCourseSelection.exception.NotFoundException;
import com.mass.UniversityCourseSelection.repo.IAdmissionCommiteeMemberRepository;
import com.mass.UniversityCourseSelection.repo.IAdmissionRepository;
import com.mass.UniversityCourseSelection.repo.IApplicantRepository;
import com.mass.UniversityCourseSelection.repo.ICourseRepository;

@Service
public class AdmissionCommitteeMemberServiceImpl implements IAdmissionCommiteeMemberService {

	@Autowired
	private IAdmissionCommiteeMemberRepository repo;

	@Autowired
	private IApplicantRepository applicantRepo;

	@Autowired
	private IAdmissionRepository admissionRepo;

	@Autowired
	private ICourseRepository courseRepo;

	@Override
	public AdmissionCommiteeMember addCommitteeMember(AdmissionCommiteeMember member) {
		return repo.save(member);
	}

	@Override
	@Transactional
	public AdmissionCommiteeMember updateCommitteeMember(AdmissionCommiteeMember member) {
		if (repo.existsById(member.getAdminId())) {
			return repo.save(member);
		} else {
			throw new NotFoundException("AdmissionCommitteeMember not available");
		}
	}

	@Override
	public AdmissionCommiteeMember viewCommitteeMember(int id) {
		if (repo.existsById(id)) {
			return repo.findById(id).get();
		} else {
			throw new NotFoundException("AdmissionCommittee Member not found !");
		}
	}

	@Override
	@Transactional
	public void removeCommitteeMember(int id) {
		if (repo.existsById(id)) {
			if (repo.count() == 1) {
				throw new NotFoundException("Cannot delete last record");
			} else {
				repo.deleteById(id);
			}
		} else {
			throw new NotFoundException("No member exists with id:" + id);
		}
	}

	@Override
	public List<AdmissionCommiteeMember> viewAllCommitteeMembers() {
		List<AdmissionCommiteeMember> l = repo.findAll();
		if (l.isEmpty()) {
			throw new NotFoundException("No commitee members found");
		}
		return l;
	}

	@Override
	public AdmissionStatus provideAdmissionResult(Applicant app, Admission adm) {

		// checking if the objects actually exists in DB or not
		Applicant applicant = null;
		Admission admission = null;
		Course course = null;

		if (courseRepo.existsById(adm.getCourseId())) {
			course = courseRepo.findById(adm.getCourseId()).get();
		} else {
			throw new NotFoundException("The course id: " + adm.getCourseId() + " doesnot exists !");
		}

		if (applicantRepo.existsById(app.getApplicantId()) && admissionRepo.existsById(adm.getAdmissionId())) {
			applicant = applicantRepo.findById(app.getApplicantId()).get();
			admission = admissionRepo.findById(adm.getAdmissionId()).get();
		} else {
			throw new NotFoundException("The Admission id: " + adm.getAdmissionId() + " or Applicant id: "
					+ app.getApplicantId() + "  doesnot exists !");
		}

		// criteria 1 (admission date)
		LocalDate admissionDate = admission.getAdmissionDate();
		LocalDate courseStartDate = course.getCourseStartDate();

		if (admissionDate.isAfter(courseStartDate)) {
			applicant.setStatus(AdmissionStatus.REJECTED);
			admission.setStatus(AdmissionStatus.REJECTED);

			applicantRepo.save(applicant);
			return applicant.getStatus();
		}

		// criteria 1 satisfied

		// criteria 2 ( percentage )

		double courseCriteria = course.getCourseCriteria();
		double marks = applicant.getApplicantGraduationPercentage();

		if (marks < courseCriteria) {
			applicant.setStatus(AdmissionStatus.PENDING);
			admission.setStatus(AdmissionStatus.PENDING);
		} else {
			admission.setStatus(AdmissionStatus.CONFIRMED);
			applicant.setStatus(AdmissionStatus.CONFIRMED);
		}

		// criteria 2 satisfied

		applicantRepo.save(applicant);
		return applicant.getStatus();

	}

}

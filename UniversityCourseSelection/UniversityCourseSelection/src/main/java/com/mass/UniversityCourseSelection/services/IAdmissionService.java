package com.mass.UniversityCourseSelection.services;

import java.time.LocalDate;
import java.util.List;

import com.mass.UniversityCourseSelection.entities.Admission;

public interface IAdmissionService {
	
	 	public Admission addAdmission(Admission add);
	 	public Admission cancelAdmission(int admissionId);
	 	public Admission updateAdmission(Admission add);
	 	public List<Admission> showAllAdmissionByCourseId(int courseId);
	 	public List<Admission> showAllAdmissionbyDate(LocalDate date);

}

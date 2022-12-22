package com.mass.UniversityCourseSelection.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mass.UniversityCourseSelection.entities.Admission;

@Repository
public interface IAdmissionRepository extends JpaRepository<Admission,Integer> {

	public List<Admission> findAllAdmissionByCourseId(int courseId);
	
	public List<Admission> findAllAdmissionByAdmissionDate(LocalDate date);
}


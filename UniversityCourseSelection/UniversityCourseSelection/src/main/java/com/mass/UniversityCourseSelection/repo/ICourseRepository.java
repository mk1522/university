package com.mass.UniversityCourseSelection.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mass.UniversityCourseSelection.entities.Course;


@Repository
public interface ICourseRepository extends JpaRepository<Course, Integer> {
	

     

}

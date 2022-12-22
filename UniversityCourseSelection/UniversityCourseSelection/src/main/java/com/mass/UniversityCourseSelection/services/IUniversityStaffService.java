package com.mass.UniversityCourseSelection.services;

import java.util.List;

import com.mass.UniversityCourseSelection.entities.Course;
import com.mass.UniversityCourseSelection.entities.UniversityStaffMember;

public interface IUniversityStaffService {
	
	public UniversityStaffMember addStaff(UniversityStaffMember usm);
	public UniversityStaffMember updateStaff(UniversityStaffMember usm);
	public UniversityStaffMember viewStaff(int id);
	public void removeStaff(int id);
	public List<UniversityStaffMember> viewAllStaffs();
	public Course addCourse(Course course);
	public Course removeCourse(Course course);
	public Course updateCourse(Course course);
}

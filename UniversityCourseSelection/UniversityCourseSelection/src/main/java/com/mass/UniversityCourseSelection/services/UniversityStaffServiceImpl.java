package com.mass.UniversityCourseSelection.services;

import java.util.List;

import com.mass.UniversityCourseSelection.entities.Course;
import com.mass.UniversityCourseSelection.entities.UniversityStaffMember;
import com.mass.UniversityCourseSelection.exception.NotFoundException;
import com.mass.UniversityCourseSelection.repo.IUniversityStaffMemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityStaffServiceImpl implements IUniversityStaffService{
	
	@Autowired
	private IUniversityStaffMemberRepository staffRepo;
	@Autowired
	private ICourseService courseService;
	
	@Override
	public UniversityStaffMember addStaff(UniversityStaffMember usm) {
		return staffRepo.save(usm);
	}

	@Override
	public UniversityStaffMember updateStaff(UniversityStaffMember usm) {
		if(staffRepo.existsById(usm.getStaffId())) {
			return staffRepo.save(usm);
		}
		else {
			throw new NotFoundException("Staff with id: "+usm.getStaffId()+" not found!");
		}
	}

	@Override
	public UniversityStaffMember viewStaff(int id) {
		if(staffRepo.existsById(id)) {
			return staffRepo.getReferenceById(id);
		}
		else {
			throw new NotFoundException("Staff with id: "+id+" not found!");
		}
	}

	@Override
	public void removeStaff(int id) {
		if(staffRepo.existsById(id)) {
			if(staffRepo.count()==1) {
				throw new NotFoundException("Cannot delete last record");
			}
			else {
				staffRepo.deleteById(id);
			}
		}
		else {
			throw new NotFoundException("Staff with id: "+id+" not found!");
		}	
	}

	@Override
	public List<UniversityStaffMember> viewAllStaffs() {
		List<UniversityStaffMember> usmList = staffRepo.findAll();
		if(usmList.isEmpty())
			throw new NotFoundException("No staff records found!");
		return usmList;
	}

	@Override
	public Course addCourse(Course course) {
		return courseService.addCourse(course);
	}

	@Override
	public Course removeCourse(Course course) {
		return courseService.removeCourse(course.getCourseId());
	}

	@Override
	public Course updateCourse(Course course) {
		return courseService.updateCourse(course);
	}
  
}

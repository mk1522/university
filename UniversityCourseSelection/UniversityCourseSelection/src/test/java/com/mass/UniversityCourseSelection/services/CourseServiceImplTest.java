package com.mass.UniversityCourseSelection.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mass.UniversityCourseSelection.entities.Course;
import com.mass.UniversityCourseSelection.exception.NotFoundException;
import com.mass.UniversityCourseSelection.repo.ICourseRepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
	
	@Mock
	private ICourseRepository courseRepo;
	@InjectMocks
	private CourseServiceImpl courseService;
	
	Course COURSE_1 = new Course(1, "Course1", "3 Months", LocalDate.of(2022, 6, 5), LocalDate.of(2022, 9, 20), "20,000", 70.0);
	Course COURSE_2 = new Course(2, "Course2", "6 Months", LocalDate.of(2022, 5, 10), LocalDate.of(2022, 11, 30), "40,000", 65.0);
	Course COURSE_3 = new Course(3, "Course3", "4 Months", LocalDate.of(2022, 1, 15), LocalDate.of(2022, 5, 28), "25,000", 80.0);
	Course COURSE_4 = new Course(4, "Course4", "12 Months", LocalDate.of(2022, 6, 1), LocalDate.of(2023, 6, 30), "100,000", 50.0);
	
	//Test cases for success
	
	@Test
	void testAddCourse_success() {
		Course newCourse = new Course(5, "Course5", "3 Months", LocalDate.of(2022, 6, 5), LocalDate.of(2022, 9, 20), "20,000", 70.0);
		Mockito.when(courseRepo.save(newCourse)).thenReturn(newCourse);
		assertEquals(newCourse, courseService.addCourse(newCourse));
	}
	
	@Test
	void testRemoveCourse_success() {
		Mockito.when(courseRepo.existsById(1)).thenReturn(true);
		Mockito.when(courseRepo.findById(COURSE_1.getCourseId())).thenReturn(Optional.ofNullable(COURSE_1));
		assertEquals(COURSE_1, courseService.removeCourse(1));
	}
	
	@Test
	void testUpdateCourse_success() {
		Course updateCourse = new Course(1, "Course1", "3 Months", LocalDate.of(2022, 6, 5), LocalDate.of(2022, 9, 20), "35,000", 60.0);
		Mockito.when(courseRepo.existsById(updateCourse.getCourseId())).thenReturn(true);
		Mockito.when(courseRepo.save(updateCourse)).thenReturn(updateCourse);
		assertEquals("35,000",courseService.updateCourse(updateCourse).getCourseFees());
		assertEquals(60.0,courseService.updateCourse(updateCourse).getCourseCriteria());
	}
	
	@Test
	void testViewCourse_success() {
		Mockito.when(courseRepo.existsById(COURSE_2.getCourseId())).thenReturn(true);	
		Mockito.when(courseRepo.findById(COURSE_2.getCourseId())).thenReturn(Optional.ofNullable(COURSE_2));
			assertNotNull(courseService.viewCourse(2));
			assertEquals(COURSE_2, courseService.viewCourse(2));
	}

	@Test
	void testViewAllCourses_sucess() {
		List<Course> allCourses = new ArrayList<>(Arrays.asList(COURSE_1,COURSE_2,COURSE_3,COURSE_4));
		Mockito.when(courseRepo.findAll()).thenReturn(allCourses);
		assertEquals(4, courseService.viewAllCourses().size());
		assertEquals("Course2", courseService.viewAllCourses().get(1).getCourseName());
		assertEquals("12 Months", courseService.viewAllCourses().get(3).getCourseDuration());
	}
	
	//Test cases for failure
	
	@Test
	void testAddCourse_failWhenNotFound() {
		Mockito.when(courseRepo.save(COURSE_4)).thenThrow(new NotFoundException());

		assertThrows(NotFoundException.class, () -> {
			courseService.addCourse(COURSE_4);
		});
	}
	
	@Test
	void removeCourse_failWhenNotFound() {
		Mockito.when(courseRepo.existsById(3)).thenReturn(false);
		assertThrows(NotFoundException.class, ()->{
			courseService.removeCourse(3);
		});
		
	}
	
	@Test
	void updateCourse_failWhenNotFound() {
		Course updateCourse = COURSE_2;
		Mockito.when(courseRepo.existsById(updateCourse.getCourseId())).thenReturn(false);
		assertThrows(NotFoundException.class, ()->{
			courseService.updateCourse(updateCourse);
		});
	}
	
	@Test
	void viewCourse_failWhenNotFound() {
		Mockito.when(courseRepo.existsById(3)).thenReturn(false);
		assertThrows(NotFoundException.class, ()->{courseService.viewCourse(3);});
	}
	
	@Test
	void viewAllStaffs_failWhenNoRecords() {
		List<Course> courses = new ArrayList<>();
		Mockito.when(courseRepo.findAll()).thenReturn(courses);
		assertEquals(true, courseService.viewAllCourses().isEmpty());
	}

}

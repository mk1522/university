package com.mass.UniversityCourseSelection.controllers;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mass.UniversityCourseSelection.entities.Course;
import com.mass.UniversityCourseSelection.exception.NotFoundException;
import com.mass.UniversityCourseSelection.services.CourseServiceImpl;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private CourseServiceImpl courseService;
	
	@InjectMocks
	private CourseController courseController;
	
	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
	}
	
	Course COURSE_1 = new Course(1, "Course1", "3 Months", LocalDate.of(2022, 6, 5), LocalDate.of(2022, 9, 20), "20,000", 70.0);
	Course COURSE_2 = new Course(2, "Course2", "6 Months", LocalDate.of(2022, 5, 10), LocalDate.of(2022, 11, 30), "40,000", 65.0);
	Course COURSE_3 = new Course(3, "Course3", "4 Months", LocalDate.of(2022, 1, 15), LocalDate.of(2022, 5, 28), "25,000", 80.0);
	
	//Test case for view Course By Id (Success)

	@Test
	void viewCourse_success() throws Exception {
		Mockito.when(courseService.viewCourse(COURSE_1.getCourseId())).thenReturn(COURSE_1);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/uni/course/view/1")
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(mockRequest)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.courseName", is("Course1")))
		.andExpect(jsonPath("$.courseDuration", is("3 Months")))
		.andExpect(jsonPath("$.courseCriteria", is(70.0)))
		.andExpect(jsonPath("$.courseFees", is("20,000")));
	}
	
	//Test case for view Course By Id (Failure)
	
	@Test
	void viewCourse_failWhenNotFound() throws Exception {
		Course viewCourse = new Course(4, "Course4", "12 Months", LocalDate.of(2022, 6, 1), LocalDate.of(2023, 6, 30), "100,000", 50.0);
		Mockito.when(courseService.viewCourse(viewCourse.getCourseId()))
		.thenThrow(new NotFoundException("Course with id: "+viewCourse.getCourseId()+" not found!"));
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/uni/course/view/4")
				.contentType(MediaType.APPLICATION_JSON);
		
		assertThatThrownBy(()-> mockMvc.perform(mockRequest))
		.hasRootCause(new NotFoundException("Course with id: "+viewCourse.getCourseId()+" not found!"));
	}
	
	//Test case for view all courses (Success)
	
	@Test
	void viewAllCourses_success() throws Exception {
		List<Course> courses = new ArrayList<>(Arrays.asList(COURSE_1,COURSE_2,COURSE_3));
		
		Mockito.when(courseService.viewAllCourses()).thenReturn(courses);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/uni/course/view/all")
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(mockRequest)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$[1].courseName", is("Course2")))
		.andExpect(jsonPath("$[2].courseFees", is("25,000")))
		.andExpect(jsonPath("$[0].courseDuration", is("3 Months")));
	}
	
	//Test case for view all courses (Failure)
	
	@Test
	void viewAllStaffs_NoRecordsFound() throws Exception {
		List<Course> courses = new ArrayList<>();
		
		Mockito.when(courseService.viewAllCourses()).thenReturn(courses);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/uni/course/view/all")
				.contentType(MediaType.APPLICATION_JSON);
		
		assertThatThrownBy(()-> mockMvc.perform(mockRequest))
		.hasRootCause(new NotFoundException("Sorry!, no courses available!!"));
	}

	

}

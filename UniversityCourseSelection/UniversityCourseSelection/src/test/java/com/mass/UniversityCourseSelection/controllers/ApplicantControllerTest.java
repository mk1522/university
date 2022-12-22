package com.mass.UniversityCourseSelection.controllers;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mass.UniversityCourseSelection.entities.Admission;
import com.mass.UniversityCourseSelection.entities.AdmissionStatus;
import com.mass.UniversityCourseSelection.entities.Applicant;
import com.mass.UniversityCourseSelection.exception.NotFoundException;
import com.mass.UniversityCourseSelection.exception.NotLoggedInException;
import com.mass.UniversityCourseSelection.services.IApplicantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ApplicantControllerTest {

	private MockMvc mvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private IApplicantService service;

	@InjectMocks
	private ApplicantController control;

	@BeforeEach
	void setup() {
		this.mvc = MockMvcBuilders.standaloneSetup(control).build();
	}

	static Applicant app1 = new Applicant(1, "john", 9000, "grad", 90.1, "pass", new Admission());
//	static Admission add1=new Admission();
	static Applicant app2 = new Applicant();
	static Applicant app3 = new Applicant();

	@BeforeAll
	static void initMethod() {
//		app1.setApplicantId(1);	
		app1.getAdmission().setAdmissionId(1);
		app1.setStatus(AdmissionStatus.APPLIED);
		app1.getAdmission().setApplicantId(1);
		app1.getAdmission().setCourseId(10);
//		app1.getAdmission().setAdmissionDate(LocalDate.of(2022, 9, 2));
////	app1.setAdmission(add1);
		app2.setApplicantId(2);
		app2.setAdmission(new Admission());
		app3.setApplicantId(3);
		app3.setAdmission(new Admission());
		app3.setStatus(AdmissionStatus.CONFIRMED);

	}

	@Test
	void testApplyForCourse() throws Exception {
		Mockito.when(service.addApplicant(app1)).thenReturn(app1);

		String body = objectWriter.writeValueAsString(app1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/applicant/apply")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(body);
		mvc.perform(mockRequest).andExpect(status().isOk());

	}

	@Test
	void updateStaff_success() throws Exception {
		Applicant app4 = new Applicant();
		app4.setApplicantId(4);
		MockHttpSession session = new MockHttpSession();
		
		session.setAttribute("applicant", 4);

		Mockito.when(service.updateApplicant(app4)).thenReturn(app4);

		String updatedBody = objectWriter.writeValueAsString(app4);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/applicant/update").session(session)
				.contentType(MediaType.APPLICATION_JSON).content(updatedBody).accept(MediaType.APPLICATION_JSON);

		mvc.perform(mockRequest).andExpect(status().isOk());
	}

	@Test
	void testDeleteApplication_success() throws Exception {

		MockHttpSession session = new MockHttpSession();

		session.setAttribute("applicant", 1);

		Mockito.when(service.deleteApplicant(app1)).thenReturn(app1);

		String delBody = objectWriter.writeValueAsString(app1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/applicant/delete").session(session)
				.contentType(MediaType.APPLICATION_JSON).content(delBody).accept(MediaType.APPLICATION_JSON);

		mvc.perform(mockRequest).andExpect(status().isOk());

	}

	@Test
	void testGetById_success() throws Exception {
		Mockito.when(service.viewApplicant(1)).thenReturn(Optional.ofNullable(app1));

		MockHttpSession session = new MockHttpSession();
		session.setAttribute("applicant", 1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/applicant/get/1").session(session)
				.contentType(MediaType.APPLICATION_JSON);

		mvc.perform(mockRequest).andExpect(status().isOk());

	}

	@Test
	void testGetAll_success() throws Exception {
		MockHttpSession session = new MockHttpSession();

		session.setAttribute("commitee", 2);
		List<Applicant> list = new ArrayList<>();
		list.add(app1);
		list.add(app2);
		Mockito.when(service.viewAllApplicantsByStatus(0)).thenReturn(list);

		String getBody = objectWriter.writeValueAsString(list);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/applicant/getAll/0").session(session)
				.contentType(MediaType.APPLICATION_JSON).content(getBody).accept(MediaType.APPLICATION_JSON);

		mvc.perform(mockRequest).andExpect(status().isOk());

	}

	@Test
	void testGetById_NotLoggedIn() throws Exception {
		MockHttpSession session = new MockHttpSession();

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/applicant/get/1").session(session)
				.contentType(MediaType.APPLICATION_JSON);

		assertThatThrownBy(() -> mvc.perform(mockRequest)).hasRootCauseInstanceOf(NotLoggedInException.class);

	}

	@Test
	void testGetById_NotFoundException() throws Exception {
		MockHttpSession session = new MockHttpSession();

		Mockito.when(service.viewApplicant(7)).thenReturn(Optional.ofNullable(null));

		session.setAttribute("commitee", 1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/applicant/get/7").session(session)
				.contentType(MediaType.APPLICATION_JSON);

		assertThatThrownBy(() -> mvc.perform(mockRequest)).hasRootCauseInstanceOf(NotFoundException.class);

	}

	@Test
	void testDeleteApplicant_NotFoundException() throws Exception {
		MockHttpSession session = new MockHttpSession();
		Applicant app = new Applicant();
		session.setAttribute("applicant", 1);

		String delBody = objectWriter.writeValueAsString(app);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/applicant/delete").session(session)
				.contentType(MediaType.APPLICATION_JSON).content(delBody).accept(MediaType.APPLICATION_JSON);

		assertThatThrownBy(() -> mvc.perform(mockRequest)).hasRootCauseInstanceOf(NotFoundException.class);
	}

	@Test
	void testDeleteApplication_NotLoggedIn() throws Exception {

		MockHttpSession session = new MockHttpSession();

		String delBody = objectWriter.writeValueAsString(app1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/applicant/delete").session(session)
				.contentType(MediaType.APPLICATION_JSON).content(delBody).accept(MediaType.APPLICATION_JSON);

		assertThatThrownBy(() -> mvc.perform(mockRequest)).hasRootCauseInstanceOf(NotLoggedInException.class);
	}

	@Test
	void testApplyForCourse_AdmissionNull() throws Exception {
		Applicant app4 = new Applicant();
		Mockito.when(service.addApplicant(app4)).thenThrow(NotFoundException.class);

		String body = objectWriter.writeValueAsString(app4);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/applicant/apply")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(body);

		assertThatThrownBy(() -> mvc.perform(mockRequest)).hasRootCauseInstanceOf(NotFoundException.class);

	}
	
	@Test
	void testGetAll_NotLoggedIn() throws Exception {
		MockHttpSession session = new MockHttpSession();
		

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/applicant/getAll/0").session(session)
				.contentType(MediaType.APPLICATION_JSON);

		assertThatThrownBy(() -> mvc.perform(mockRequest)).hasRootCauseInstanceOf(NotLoggedInException.class);

	}

}

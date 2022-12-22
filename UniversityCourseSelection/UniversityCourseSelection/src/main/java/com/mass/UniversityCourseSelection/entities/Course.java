package com.mass.UniversityCourseSelection.entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
	@Id
	@Column(name = "course_Id")
	@SequenceGenerator(
			name = "course_sequence",
			sequenceName = "course_sequence",
			allocationSize = 1)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "course_sequence")
	private Integer courseId;
	
	@Column(name = "course_Name")
	private String courseName;
	
	@Column(name = "course_Duration")
	private String courseDuration;
	
	@Column(name = "course_StartDate")
	@JsonFormat(pattern="dd-MMM-yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)  
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate courseStartDate;
	
	@Column(name = "course_EndDate")
	@JsonFormat(pattern="dd-MMM-yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)  
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate courseEndDate;
	
	@Column(name = "course_Fees")
	private String courseFees;
	
	@Column(name = "course_Criteria")
	private double courseCriteria;
	
	private String status;
	
	public Course() {
		status="ACTIVE";
	}

	public Course(Integer courseId, String courseName, String courseDuration, LocalDate courseStartDate,
			LocalDate courseEndDate, String courseFees, double courseCriteria) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDuration = courseDuration;
		this.courseStartDate = courseStartDate;
		this.courseEndDate = courseEndDate;
		this.courseFees = courseFees;
		this.courseCriteria = courseCriteria;
		this.status="ACTIVE";
	}
	
	

}

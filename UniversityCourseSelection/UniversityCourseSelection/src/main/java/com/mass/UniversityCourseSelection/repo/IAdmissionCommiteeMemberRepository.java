package com.mass.UniversityCourseSelection.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import com.mass.UniversityCourseSelection.entities.AdmissionCommiteeMember;
import com.mass.UniversityCourseSelection.entities.Course;

@Repository
public interface IAdmissionCommiteeMemberRepository extends JpaRepository<AdmissionCommiteeMember, Integer> {

	@Query("SELECT c FROM Course c where c.courseId = ?1")
	Course getCourseById(int id);
	
	@Query(value="select * from admission_committee_member where admin_id=?1 and admin_password=?2",nativeQuery = true)
	AdmissionCommiteeMember verifyAdmissionCommiteeMemberCred(int id,String password);
	
	@Transactional
	@Modifying
	@Query(value="ALTER SEQUENCE admission_committee_member_admin_id_seq RESTART WITH 1", nativeQuery = true)
	void resetCommitteeIdSequence();
	
}

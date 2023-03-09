package com.example.webservice_for_online_testing.repos;

import com.example.webservice_for_online_testing.domain.StudentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentResultRepository extends JpaRepository<StudentResult, Long> {
    @Query("SELECT r FROM StudentResult r WHERE r.student_name = ?1 AND r.student_surname = ?2")
    List<StudentResult> findStudentResultBystudent_surnameAndstudent_name(String surname, String name);
}

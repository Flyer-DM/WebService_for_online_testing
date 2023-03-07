package com.example.webservice_for_online_testing.repos;

import com.example.webservice_for_online_testing.domain.StudentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentResultRepository extends JpaRepository<StudentResult, Long> {
}

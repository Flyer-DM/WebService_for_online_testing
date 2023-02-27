package com.example.webservice_for_online_testing.repos;

import com.example.webservice_for_online_testing.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.test_id = ?1")
    List<Question> search(Long testId);
}

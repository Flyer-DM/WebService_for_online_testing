package com.example.webservice_for_online_testing.repos;

import com.example.webservice_for_online_testing.domain.Question;
import com.example.webservice_for_online_testing.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface with {@link Repository} annotation that used as a mechanism for encapsulating storage, retrieval,
 * and search behavior which emulates a collection of objects. Contains SQL Query that returns list of Question
 * objects according to their shared field "test_id".
 * @see Question
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.test_id = ?1")
    List<Question> search(Test testId);
}

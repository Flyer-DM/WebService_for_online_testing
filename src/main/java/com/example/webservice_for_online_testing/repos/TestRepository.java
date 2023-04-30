package com.example.webservice_for_online_testing.repos;

import java.util.List;

import com.example.webservice_for_online_testing.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface with {@link Repository} annotation that used as a mechanism for encapsulating storage, retrieval,
 * and search behavior which emulates a collection of objects. Contains SQL Query that returns list of Test
 * objects according to specific keyword.
 * @see Test
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT p FROM Test p WHERE CONCAT(p.id, ' ', p.topic, ' ', p.start_time, ' ', p.end_time, ' ', p.attempts) LIKE %?1%")
    List<Test> search(String keyword);
}

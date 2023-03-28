package com.example.webservice_for_online_testing.repos;

import java.util.List;

import com.example.webservice_for_online_testing.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT p FROM Test p WHERE CONCAT(p.id, ' ', p.topic, ' ', p.start_time, ' ', p.end_time, ' ', p.attempts) LIKE %?1%")
    List<Test> search(String keyword);
}

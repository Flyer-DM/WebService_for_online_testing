package com.example.webservice_for_online_testing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT p FROM Test p WHERE CONCAT(p.class_number, ' ', p.subject, ' ', p.topic, ' ', p.link, ' ', p.difficulty) LIKE %?1%")
    List<Test> search(String keyword);
}

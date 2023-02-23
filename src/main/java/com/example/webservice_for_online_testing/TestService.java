package com.example.webservice_for_online_testing;

import java.util.List;

import com.example.webservice_for_online_testing.domain.Test;
import com.example.webservice_for_online_testing.repos.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public List<Test> listAll(String keyword) {
        if (keyword != null) {
            return testRepository.search(keyword);
        }
        return testRepository.findAll();
    }

    public void save(Test test) {
        testRepository.save(test);
    }

    public Test get(Long id) {
        return testRepository.findById(id).get();
    }

    public void delete(Long id) {
        testRepository.deleteById(id);
    }

}


package com.example.webservice_for_online_testing.service;

import java.util.List;

import com.example.webservice_for_online_testing.domain.Question;
import com.example.webservice_for_online_testing.domain.StudentResult;
import com.example.webservice_for_online_testing.domain.Test;
import com.example.webservice_for_online_testing.repos.QuestionRepository;
import com.example.webservice_for_online_testing.repos.StudentResultRepository;
import com.example.webservice_for_online_testing.repos.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataBaseService{

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final StudentResultRepository studentResultRepository;

    @Autowired
    public DataBaseService(TestRepository testRepository, QuestionRepository questionRepository,
                           StudentResultRepository studentResultRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.studentResultRepository = studentResultRepository;
    }

    public List<Test> listAll(String keyword) {
        if (keyword != null) {
            return testRepository.search(keyword);
        }
        return testRepository.findAll();
    }

    public void saveTest(Test test) {
        testRepository.save(test);
    }

    public Test getTest(Long id) {
        return testRepository.findById(id).get();
    }

    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    public List<Question> listAllTestId(Test testId) {
        return questionRepository.search(testId);
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id).get();
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public void saveStudentResult(StudentResult studentResult) {
        studentResultRepository.save(studentResult);
    }

    public StudentResult getStudentResult(Long id) {
        return studentResultRepository.findById(id).get();
    }

    public void deleteStudentResult(Long id) {
        studentResultRepository.deleteById(id);
    }
}


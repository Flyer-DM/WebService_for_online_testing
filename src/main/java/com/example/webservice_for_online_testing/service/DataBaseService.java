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

/**
 * Class allows user to interact with SQL database through connectors without using SQL queries directly.
 * Contains prescribed methods to insert, get and delete data from database and self-written queries from repositories.
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Service
public class DataBaseService{

    /** @see TestRepository */
    private final TestRepository testRepository;

    /** @see QuestionRepository */
    private final QuestionRepository questionRepository;

    /** @see StudentResultRepository */
    private final StudentResultRepository studentResultRepository;

    /**
     * Constructor of the class.
     * @param testRepository repository to control {@link Test objects in database}
     * @param questionRepository repository to control {@link Question objects in database}
     * @param studentResultRepository repository to control {@link StudentResult objects in database}
     */
    @Autowired
    public DataBaseService(TestRepository testRepository, QuestionRepository questionRepository,
                           StudentResultRepository studentResultRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.studentResultRepository = studentResultRepository;
    }

    /**
     * Method returns a list of Test objects according to specific keyword. Allows user to sort table of available tests
     * by any column to facilitate search if list of test is enormous.
     * @see Test
     * @param keyword a symbol sequence to search Test objects in database by all their fields
     * @return a list of Test objects
     */
    public List<Test> listAll(String keyword) {
        if (keyword != null) {
            return testRepository.search(keyword);
        }
        return testRepository.findAll();
    }

    /**
     * Method saves a Test object to SQL database as an Entity.
     * @see Test
     * @param test Test class
     */
    public void saveTest(Test test) {
        testRepository.save(test);
    }

    /**
     * Method returns one specific Test object from database according its unique identifier.
     * @see Test
     * @param id identifier field of the Test object
     * @return Test object
     */
    public Test getTest(Long id) {
        return testRepository.findById(id).get();
    }

    /**
     * Method returns a list of all existing Test objects from the database.
     * @see Test
     * @return list of all Test objects
     */
    public List<Test> getAll() {
        return testRepository.findAll();
    }

    /**
     * Method deletes one Test object from database according its unique identifier.
     * @see Test
     * @param id identifier field of the Test object
     */
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    /**
     * Method returns list of Question objects those have same test_id field.
     * @see Question
     * @param testId identifier field of the foreign key of Question object
     * @return list of Question
     */
    public List<Question> listAllTestId(Test testId) {
        return questionRepository.search(testId);
    }

    /**
     * Method saves a Question object to SQL database as an Entity.
     * @see Question
     * @param question Question class
     */
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    /**
     * Method returns one specific Question object from database according its unique identifier.
     * @see Question
     * @param id identifier field of the Question object
     * @return Question object
     */
    public Question getQuestion(Long id) {
        return questionRepository.findById(id).get();
    }

    /**
     * Method deletes one Question object from database according its unique identifier.
     * @see Question
     * @param id identifier field of the Question object
     */
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    /**
     * Method saves a StudentResult object to SQL database as an Entity.
     * @see StudentResult
     * @param studentResult StudentResult class
     */
    public void saveStudentResult(StudentResult studentResult) {
        studentResultRepository.save(studentResult);
    }

    /**
     * Method returns a list of all existing StudentResult objects from the database.
     * @see StudentResult
     * @return list of all StudentResult objects
     */
    public List<StudentResult> getAllStudentResults() {
        return studentResultRepository.findAll();
    }

    /**
     * Method returns a list of StudentResult objects according to two columns student_name and student_surname.
     * Used to show user with same name and surname all his previous results after testing and to create statistics
     * circular diagram for every user individually.
     * @see StudentResult
     * @param name student_name field
     * @param surname student_surname field
     * @return list of StudentResult objects
     */
    public List<StudentResult> getStudentResultsBySurname(String name, String surname) {
        return studentResultRepository.findStudentResultBystudent_surnameAndstudent_name(surname, name);
    }

    /**
     * Method deletes one StudentResult object from database according its unique identifier.
     * @see StudentResult
     * @param id identifier field of the StudentResult object
     */
    public void deleteStudentResult(Long id) {
        studentResultRepository.deleteById(id);
    }
}

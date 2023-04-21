package com.example.webservice_for_online_testing.domain;

import jakarta.persistence.*;

/**
 * Class StudentResult to connect with Entity StudentResult in SQL database with the help of {@link jakarta.persistence}
 * annotations.
 * Contains entity`s fields, setters and getters methods, constructors.
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Entity
@Table(name = "student_result")
public class StudentResult {

    /** the id field stores a unique identifier for each object of this class in the database */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** the id field stores a unique identifier for Test class for joining an entity association
     * @see Test
     */
    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test_id;

    /** the student_name field describes the name of the student which solved a test */
    @Column(name = "student_name", nullable = false)
    private String student_name;

    /** the student_surname field describes the surname of the student which solved a test */
    @Column(name = "student_surname", nullable = false)
    private String student_surname;

    /** the student_patronymic field describes the patronymic of the student which solved a test */
    @Column(name = "student_patronymic")
    private String student_patronymic;

    /** the result field describes the result that student got after the solution of the test */
    @Column(name = "result", nullable = false)
    private String result;

    /**
     * Constructor of the class, used in controllers.
     * @see StudentResult#StudentResult(Test)
     * @see StudentResult#StudentResult(Test, String, String, String)
     */
    public StudentResult() {
    }

    /**
     * Constructor of the class, used in controllers.
     * @see StudentResult#StudentResult()
     * @see StudentResult#StudentResult(Test, String, String, String)
     */
    public StudentResult(Test test_id) {
        this.test_id = test_id;
    }

    /**
     * Constructor of the class, used in controllers.
     * @see StudentResult#StudentResult(Test)
     * @see StudentResult#StudentResult()
     */
    public StudentResult(Test test_id, String student_name, String student_surname, String result) {
        this.test_id = test_id;
        this.student_name = student_name;
        this.student_surname = student_surname;
        this.result = result;
    }

    /**
     * Getter method
     * @return id field
     * @see StudentResult#id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method updates value for id field
     * @see StudentResult#id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method
     * @return test_id field
     * @see StudentResult#test_id
     */
    public Test getTest_id() {
        return test_id;
    }

    /**
     * Getter method
     * @return student_name field
     * @see StudentResult#student_name
     */
    public String getStudent_name() {
        return student_name;
    }

    /**
     * Getter method
     * @return student_surname field
     * @see StudentResult#student_surname
     */
    public String getStudent_surname() {
        return student_surname;
    }

    /**
     * Getter method
     * @return student_patronymic field
     * @see StudentResult#student_patronymic
     */
    public String getStudent_patronymic() {
        return student_patronymic;
    }

    /**
     * Setter method updates value for student_patronymic field
     * @see StudentResult#student_patronymic
     */
    public void setStudent_patronymic(String student_patronymic) {
        this.student_patronymic = student_patronymic;
    }

    /**
     * Getter method
     * @return result field
     * @see StudentResult#result
     */
    public String getResult() {
        return result;
    }

    /**
     * Setter method updates value for result field
     * @see StudentResult#result
     */
    public void setResult(String result) {
        this.result = result;
    }
}

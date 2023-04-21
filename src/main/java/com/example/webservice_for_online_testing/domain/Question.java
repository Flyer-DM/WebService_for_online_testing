package com.example.webservice_for_online_testing.domain;

import jakarta.persistence.*;

/**
 * Class Question to connect with Entity Question in SQL database with the help of {@link jakarta.persistence} annotations.
 * Contains entity`s fields, setters and getters methods, constructors.
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Entity
@Table(name = "Question")
public class Question {

    /** the id field stores a unique identifier for each object of this class in the database */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** the id field stores a unique identifier for Test class for joining an entity association
     * @see Test
     */
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test_id;

    /** the problem field describes the question subject */
    @Column(name = "problem", nullable = false)
    private String problem;

    /** the answer field is correct answer for question */
    @Column(name = "answer", nullable = false)
    private String answer;

    /** the variant1 field is incorrect answer for question */
    @Column(name = "variant1", nullable = false)
    private String variant1;

    /** the variant2 field is second incorrect answer for question */
    @Column(name = "variant2", nullable = false)
    private String variant2; // вариант ответа 2 на вопрос

    /**
     * Constructor of the class, used in controllers.
     * @see Question#Question(Test, String, String, String, String)
     * @see Question#Question(Test)
     */
    public Question() {
    }

    /**
     * Constructor of the class, used in controllers.
     * @see Question#Question()
     * @see Question#Question(Test)
     */
    public Question(Test test_id, String problem, String answer, String variant1, String variant2) {
        this.test_id = test_id;
        this.problem = problem;
        this.answer = answer;
        this.variant1 = variant1;
        this.variant2 = variant2;
    }

    /**
     * Constructor of the class, used in controllers.
     * @see Question#Question(Test, String, String, String, String)
     * @see Question#Question()
     */
    public Question(Test test_id) {
        this.test_id = test_id;
    }

    /**
     * Getter method
     * @return id field
     * @see Question#id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method updates value for id field
     * @see Question#id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method
     * @return test_id field
     * @see Question#test_id
     */
    public Test getTest_id() {
        return test_id;
    }

    /**
     * Getter method
     * @return problem field
     * @see Question#problem
     */
    public String getProblem() {
        return problem;
    }

    /**
     * Getter method
     * @return answer field
     * @see Question#answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Getter method
     * @return variant1 field
     * @see Question#variant1
     */
    public String getVariant1() {
        return variant1;
    }

    /**
     * Getter method
     * @return variant2 field
     * @see Question#variant2
     */
    public String getVariant2() {
        return variant2;
    }

}

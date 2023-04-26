package com.example.webservice_for_online_testing.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used during testing, contains three fields, those have values of {@link Question#getAnswer()},
 * {@link Question#getVariant1() }, {@link Question#getVariant2() } shuffled randomly and id and the problem of the
 * question.
 * @see Question
 * @author Kondrashov Dmitry
 * @version 1.0
 */
public class RandomedQuestion{

    /** @see Question#getId()  */
    private Long id;

    /** @see Question#getProblem() */
    private final String problem;

    /** field that could be {@link Question#getAnswer()},
     * {@link Question#getVariant1() } or {@link Question#getVariant2() }*/
    private final String answer1;

    /** @see RandomedQuestion#answer1 */
    private final String answer2;

    /** @see RandomedQuestion#answer1 */
    private final String answer3;

    /**
     * Constructor of the class that gets id and problem of the Question object as well as its correct and two
     * incorrect answers and shuffles them randomly.
     * @param question Question object
     * @see Question
     */
    public RandomedQuestion(Question question) {
        List<String> variants = new ArrayList<>();
        variants.add(question.getAnswer());
        variants.add(question.getVariant1());
        variants.add(question.getVariant2());
        Collections.shuffle(variants);
        this.answer1 = variants.get(0);
        this.answer2 = variants.get(1);
        this.answer3 = variants.get(2);
        this.id = question.getId();
        this.problem = question.getProblem();
    }

    /**
     * Getter method
     * @return answer1 field
     * @see RandomedQuestion#answer1
     */
    public String getAnswer1() {
        return answer1;
    }

    /**
     * Getter method
     * @return answer2 field
     * @see RandomedQuestion#answer2
     */
    public String getAnswer2() {
        return answer2;
    }

    /**
     * Getter method
     * @return answer3 field
     * @see RandomedQuestion#answer3
     */
    public String getAnswer3() {
        return answer3;
    }

    /**
     * Getter method
     * @return id field
     * @see RandomedQuestion#id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method updates value for id field
     * @see RandomedQuestion#id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method
     * @return problem field
     * @see RandomedQuestion#problem
     */
    public String getProblem() {
        return problem;
    }
}

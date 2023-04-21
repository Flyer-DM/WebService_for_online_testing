package com.example.webservice_for_online_testing.domain;

import jakarta.persistence.*;

import java.util.List;

/**
 * Class Test to connect with Entity Test in SQL database with the help of {@link jakarta.persistence} annotations.
 * Contains entity`s fields, setters and getters methods, constructors.
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Entity
@Table(name = "test")
public class Test {

    /** the id field stores a unique identifier for each object of this class in the database */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** the topic field describes the testing subject */
    @Column(name = "topic", nullable = false)
    private String topic;

    /** the start_time field describes the start of the deadline to solve the test */
    @Column(name = "start_time", nullable = false)
    private String start_time;

    /** the end_time field describes the end of the deadline to solve the test */
    @Column(name = "end_time", nullable = false)
    private String end_time;

    /** the attempts field describes number of attempts students solved the certain test */
    @Column(name = "attempts")
    private Long attempts;  // количество прохождений

    /** field to connect with Question objects
     * @see Question Question class
     */
    @OneToMany(mappedBy = "test")
    private List<Question> questions;

    /** field to connect with StudentResults objects
     * @see StudentResult StudentResult class
     */
    @OneToMany(mappedBy = "test")
    private List<StudentResult> studentResults;

    /**
     * Constructor of the class, used in controllers.
     * @see Test#Test(String, String, String)
     */
    public Test() {
    }

    /**
     * Constructor of the class, used in controllers.
     * @see Test#Test()
     */
    public Test(String topic, String start_time, String end_time) {
        this.topic = topic;
        this.start_time = start_time;
        this.end_time = end_time;
        this.attempts = 0L;
    }

    /**
     * Getter method
     * @return id field
     * @see Test#id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Setter method updates value for id field
     * @see Test#id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method
     * @return topic field
     * @see Test#topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Setter method updates value for topic field
     * @see Test#topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Getter method
     * @return start_time field
     * @see Test#start_time
     */
    public String getStart_time() {
        return start_time;
    }

    /**
     * Setter method updates value for start_time field
     * @see Test#start_time
     */
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    /**
     * Getter method
     * @return end_time field
     * @see Test#end_time
     */
    public String getEnd_time() {
        return end_time;
    }

    /**
     * Setter method updates value for end_time field
     * @see Test#end_time
     */
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    /**
     * Getter method
     * @return attempts field
     * @see Test#attempts
     */
    public Long getAttempts() {
        return attempts;
    }

    /**
     * Setter method updates value for attempts field
     * @see Test#attempts
     */
    public void setAttempts(Long attempts) {
        this.attempts = attempts;
    }

}

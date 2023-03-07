package com.example.webservice_for_online_testing.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "student_result")
public class StudentResult {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test_id;
    @Column(name = "student_name", nullable = false)
    private String student_name;
    @Column(name = "student_surname", nullable = false)
    private String student_surname;
    @Column(name = "student_patronymic")
    private String student_patronymic;
    @Column(name = "result", nullable = false)
    private String result;

    public StudentResult() {
    }

    public StudentResult(Test test_id) {
        this.test_id = test_id;
    }

    public StudentResult(Test test_id, String student_name, String student_surname, String student_patronymic, String result) {
        this.test_id = test_id;
        this.student_name = student_name;
        this.student_surname = student_surname;
        this.student_patronymic = student_patronymic;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test getTest_id() {
        return test_id;
    }

    public void setTest_id(Test test_id) {
        this.test_id = test_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_surname() {
        return student_surname;
    }

    public void setStudent_surname(String student_surname) {
        this.student_surname = student_surname;
    }

    public String getStudent_patronymic() {
        return student_patronymic;
    }

    public void setStudent_patronymic(String student_patronymic) {
        this.student_patronymic = student_patronymic;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

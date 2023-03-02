package com.example.webservice_for_online_testing.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "Question")
public class Question {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // автоматическая генерация атрибута ID
    private Long id; // ID

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test_id; // ID теста, к которому относится для соединения
    @Column(name = "problem", nullable = false)
    private String problem;  // содержание вопроса
    @Column(name = "answer", nullable = false)
    private String answer; // правильный ответ на вопрос

    @Column(name = "variant1", nullable = false)
    private String variant1; // вариант ответа 1 на вопрос

    @Column(name = "variant2", nullable = false)
    private String variant2; // вариант ответа 2 на вопрос

    public Question() {
    }

    public Question(Test test_id, String problem, String answer, String variant1, String variant2) {
        this.test_id = test_id;
        this.problem = problem;
        this.answer = answer;
        this.variant1 = variant1;
        this.variant2 = variant2;
    }

    public Question(Test test_id) {
        this.test_id = test_id;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getVariant1() {
        return variant1;
    }

    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }

    public String getVariant2() {
        return variant2;
    }

    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }
}

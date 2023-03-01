package com.example.webservice_for_online_testing.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "Question")
public class Question {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)  // автоматическая генерация атрибута ID
    private Long id; // ID

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test_id; // ID теста, к которому относится для соединения
    @Column(name = "problem", nullable = false)
    private String problem;  // содержание вопроса
    @Column(name = "answer", nullable = false)
    private String answer; // правильный ответ на вопрос

    public Question() {
    }

    public Question(Test test_id, String problem, String answer) {
        this.test_id = test_id;
        this.problem = problem;
        this.answer = answer;
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
}

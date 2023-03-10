package com.example.webservice_for_online_testing.domain;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "test")
public class Test {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)  // автоматическая генерация атрибута ID
    private Long id; // ID

    @Column(name = "topic", nullable = false)
    private String topic; // тема для тестирования
    @Column(name = "start_time", nullable = false)
    private String start_time; // время запуска теста
    @Column(name = "end_time", nullable = false)
    private String end_time;  // время дедлайна для прохождения

    @Column(name = "attempts")
    private Long attempts;  // количество прохождений

    @OneToMany(mappedBy = "test")
    private List<Question> questions;
    @OneToMany(mappedBy = "test")
    private List<StudentResult> studentResults;
    public Test() {
    }

    public Test(String topic, String start_time, String end_time) {
        this.topic = topic;
        this.start_time = start_time;
        this.end_time = end_time;
        this.attempts = 0L;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Long getAttempts() {
        return attempts;
    }

    public void setAttempts(Long attempts) {
        this.attempts = attempts;
    }

}

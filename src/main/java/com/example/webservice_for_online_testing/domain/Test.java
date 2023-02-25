package com.example.webservice_for_online_testing.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // автоматическая генерация атрибута ID
    private Long id; // ID
    private String topic; // тема для тестирования
    private String start_time; // время запуска теста
    private String end_time;  // время дедлайна для прохождения

    private String result; // результат тестирования
    public Test() {
    }

    public Test(String topic, String start_time, String end_time) {
        this.topic = topic;
        this.start_time = start_time;
        this.end_time = end_time;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

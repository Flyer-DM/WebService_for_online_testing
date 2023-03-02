package com.example.webservice_for_online_testing.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomedQuestion{

    private Long id;
    private String problem;
    private String answer1;
    private String answer2;
    private String answer3;

    public RandomedQuestion(Question question) {
        List<String> variants = new ArrayList<>();
        variants.add(question.getAnswer());
        variants.add(question.getVariant1());
        variants.add(question.getVariant2());
        Collections.shuffle(variants); // перемешивание порядка ответов
        this.answer1 = variants.get(0);
        this.answer2 = variants.get(1);
        this.answer3 = variants.get(2);
        this.id = question.getId();
        this.problem = question.getProblem();
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

}

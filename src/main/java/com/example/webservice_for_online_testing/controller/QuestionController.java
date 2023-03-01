package com.example.webservice_for_online_testing.controller;


import com.example.webservice_for_online_testing.domain.Question;
import com.example.webservice_for_online_testing.domain.Test;
import com.example.webservice_for_online_testing.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private DataBaseService dataBaseService;

    @RequestMapping("edit_questions/{id}")
    public String showNewQuestionsForm(Model model, @Param("id") Long id) {
        List<Question> questionList = dataBaseService.listAllTestId(id);
        model.addAttribute("questionList", questionList);
        model.addAttribute("test_id", id);
        return "edit_questions";
    }

    @RequestMapping(value = "/save_new_question", method = RequestMethod.POST)
    public String AddQuestion(Model model,
                              @RequestParam String problem, @RequestParam String answer,
                              @RequestParam Long test_id) {
        Test test = dataBaseService.getTest(test_id);
        Question question = new Question(test, problem, answer);
        dataBaseService.saveQuestion(question);
        List<Question> questionList = dataBaseService.listAllTestId(test_id);
        model.addAttribute("questionList", questionList);
        model.addAttribute("test_id", test_id);
        return "edit_questions";
    }

    @RequestMapping("/edit_question/{id}")
    public String deleteQuestion(@PathVariable(name = "id") Long id) {
        dataBaseService.deleteQuestion(id);
        return "edit_questions";
    }
}

package com.example.webservice_for_online_testing.controller;


import com.example.webservice_for_online_testing.domain.Question;
import com.example.webservice_for_online_testing.domain.Test;
import com.example.webservice_for_online_testing.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class QuestionController {

    @Autowired
    private DataBaseService dataBaseService;

    @RequestMapping("edit_questions/{id}")
    public ModelAndView showNewQuestionsForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_questions");
        Test test = dataBaseService.getTest(id);
        Question question = new Question(test);
        List<Question> questionList = dataBaseService.listAllTestId(test);
        mav.addObject("questionList", questionList);
        mav.addObject("question", question);
        return mav;
    }

    @RequestMapping(value = "/save_new_question", method = RequestMethod.POST)
    public String AddQuestion(Model model,
                              @RequestParam String problem, @RequestParam String answer,
                              @RequestParam Long test_id) {
        Test test = dataBaseService.getTest(test_id);
        Question question = new Question(test, problem, answer);
        dataBaseService.saveQuestion(question);
        List<Question> questionList = dataBaseService.listAllTestId(test);
        model.addAttribute("questionList", questionList);
        model.addAttribute("test_id", test_id);
        return "edit_questions";
    }

    @RequestMapping("/delete_question/{id}/{test_id}")
    public ModelAndView deleteQuestion(@PathVariable(name = "id") Long id, @PathVariable(name = "test_id") Long test_id) {
        dataBaseService.deleteQuestion(id);
        return showNewQuestionsForm(test_id);
    }
}

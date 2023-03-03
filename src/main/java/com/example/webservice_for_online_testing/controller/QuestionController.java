package com.example.webservice_for_online_testing.controller;


import com.example.webservice_for_online_testing.domain.Question;
import com.example.webservice_for_online_testing.domain.Test;
import com.example.webservice_for_online_testing.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private DataBaseService dataBaseService;

    // переход на страницу редактирования вопросов к тексту
    @RequestMapping("/edit_questions/{id}")
    public ModelAndView showNewQuestionsForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_questions");
        Test test = dataBaseService.getTest(id);
        Question question = new Question(test);
        List<Question> questionList = dataBaseService.listAllTestId(test);
        mav.addObject("questionList", questionList);
        mav.addObject("question", question);
        return mav;
    }
    // сохранение вопроса после заполнения формы
    @RequestMapping(value = "/save_new_question", method = RequestMethod.POST)
    public ModelAndView AddQuestion(@RequestParam String problem, @RequestParam String answer,  // вопрос и ответ
                                    @RequestParam String variant1, @RequestParam String variant2,  // неправильные варианты
                                    @RequestParam Long test_id) {
        ModelAndView mav = new ModelAndView("edit_questions");
        Test test = dataBaseService.getTest(test_id);
        Question question = new Question(test, problem, answer, variant1, variant2);
        Question emptyQuestion = new Question(test);
        dataBaseService.saveQuestion(question);
        List<Question> questionList = dataBaseService.listAllTestId(test);
        mav.addObject("questionList", questionList);
        mav.addObject("question", emptyQuestion);
        return mav;
    }
    // удаление вопроса из таблицы
    @RequestMapping("/delete_question/{id}/{test_id}")
    public ModelAndView deleteQuestion(@PathVariable(name = "id") Long id, @PathVariable(name = "test_id") Long test_id) {
        dataBaseService.deleteQuestion(id);
        return showNewQuestionsForm(test_id);
    }
    // кнопка возврата на index
    @RequestMapping("get_to_index")
    public String backToIndex() {
        return "redirect:/index_student";
    }
}

package com.example.webservice_for_online_testing.controller;

import com.example.webservice_for_online_testing.domain.Question;
import com.example.webservice_for_online_testing.domain.Test;
import com.example.webservice_for_online_testing.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.webservice_for_online_testing.domain.RandomedQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private DataBaseService dataBaseService;
    // главная страница студента
    @RequestMapping("/index_student")
    public String viewUserTestsPage(Model model, @Param("keyword") String keyword) {
        List<Test> listTests = dataBaseService.listAll(keyword);
        model.addAttribute("listTests", listTests);
        model.addAttribute("keyword", keyword);
        return "index_student";
    }
    // переход на страницу с вопросами к тесту
    @RequestMapping("/student_testing/{id}")
    public ModelAndView startTest(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("student_testing");
        Test test = dataBaseService.getTest(id);
        Question question = new Question(test);
        List<Question> questionList = dataBaseService.listAllTestId(test);

        // получение списка перемешанного списка вопросов с перемешанными ответами
        List<RandomedQuestion> shuffledList = new ArrayList<>();
        for (Question quest: questionList) {
            RandomedQuestion randomedQuestion = new RandomedQuestion(quest);
            shuffledList.add(randomedQuestion);
        }
        Collections.shuffle(shuffledList);

        mav.addObject("questionList", shuffledList);
        mav.addObject("question", question);
        return mav;
    }
    // кнопка возврата на страницу студента
    @RequestMapping("get_to_index_student")
    public String backToIndexStudent() {
        return "redirect:/index_student";
    }

}

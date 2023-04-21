package com.example.webservice_for_online_testing.controller;

import java.util.List;

import com.example.webservice_for_online_testing.domain.Question;
import com.example.webservice_for_online_testing.domain.Test;
import com.example.webservice_for_online_testing.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Second controller for TEACHER with all mappings those handle all work with Question class,
 * that user with ROLE TEACHER has access to.
 * @see com.example.webservice_for_online_testing.config.WebSecurityConfig
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Controller
public class QuestionController {

    /** @see DataBaseService */
    private final DataBaseService dataBaseService;

    /**
     * Constructor for Question controller.
     * @param dataBaseService connects main service for handling all users` modification in database
     * {@link QuestionController#dataBaseService}
     */
    @Autowired
    public QuestionController(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    /**
     * Method opens a page contains list of Questions for Test by its id and form to add new Questions.
     * @see QuestionController#AddQuestion(String, String, String, String, Long) method saves new Question object
     * to database and connects it with test by ip
     * @see Question Question class
     * @see QuestionController#deleteQuestion(Long, Long) Method deletes existing Question object from database
     * @see QuestionController#backToIndex() Method redirects for main page
     * @param id (parameter got from url) id of the test for which we need to edit the questions.
     * @return folder with both Model and View - a html page with some already preset parameters
     * See src/main/resources/templates/edit_questions.html in templates.
     */
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

    /**
     * Method saves new Question to database, connects it by id with test and updates page
     * @see Question Question class
     * @param problem content of the question
     * @param answer the right answer for question
     * @param variant1 wrong answer for question for possible choose
     * @param variant2 second wrong answer for question for possible choose
     * @param test_id id of the test to connect Question object with
     * @return folder with both Model and View - a html page with some already preset parameters
     * See src/main/resources/templates/edit_questions.html in templates.
     */
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

    /**
     * Method deletes Question object from database
     * @see Question Question class
     * @param id id of the Question to delete (on delete cascade)
     * @param test_id id of the Test object Questions are connected to in order to open appropriate page
     * @return result of {@link QuestionController#showNewQuestionsForm(Long)} method by test_id field
     */
    @RequestMapping("/delete_question/{id}/{test_id}")
    public ModelAndView deleteQuestion(@PathVariable(name = "id") Long id, @PathVariable(name = "test_id") Long test_id) {
        dataBaseService.deleteQuestion(id);
        return showNewQuestionsForm(test_id);
    }

    /**
     * Method used for html button to redirect for main page
     * @return redirection for main page
     * See src/main/resources/templates/index.html in templates.
     */
    @RequestMapping("get_to_index")
    public String backToIndex() {
        return "redirect:/index";
    }
}

package com.example.webservice_for_online_testing.controller;

import java.util.List;

import com.example.webservice_for_online_testing.domain.StudentResult;
import com.example.webservice_for_online_testing.service.DataBaseService;
import com.example.webservice_for_online_testing.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Main controller for TEACHER with all mappings those handle pages and requests,
 * that user with ROLE TEACHER has access to.
 * @see com.example.webservice_for_online_testing.config.WebSecurityConfig
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Controller
public class MainController {

    /** @see DataBaseService */
    private final DataBaseService dataBaseService;

    /**
     * Constructor for Main controller.
     * @param dataBaseService connects main service for handling all user`s modification in database
     * {@link MainController#dataBaseService}
     */
    @Autowired
    public MainController(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    /**
     * Method opens main page of TEACHER with table of available tests, search bar, sorting by every column.
     * @see Test Test class
     * @see MainController#showNewTestForm(Model) Method adds test to database
     * @see MainController#deleteTest(Long) Method deletes test from database
     * @see MainController#showEditTestForm(Long) Method edits existing test
     * @see QuestionController#showNewQuestionsForm(Long) Method edits questions for existing test
     * @see MainController#viewHistPage(Model) Method shows histogram statistics page for tests
     * @see MainController#showResults(Model) Method shows page with table of students` results pages
     * @see LoginController#authorPage() Method shows page with information about author
     * @see com.example.webservice_for_online_testing.config.WebSecurityConfig#securityFilterChain(HttpSecurity)
     * Method to logout from account
     * @param model holder to add attributes into html from java (keyword and list of Test objects)
     * @param keyword keyword to filter data in search bar
     * @return http name of index_teacher page.
     * See src/main/resources/templates/index_teacher.html in templates.
     */
    @RequestMapping("/index_teacher")
    public String viewTestsPage(Model model, @Param("keyword") String keyword) {
        List<Test> listTests = dataBaseService.listAll(keyword);
        model.addAttribute("listTests", listTests);
        model.addAttribute("keyword", keyword);
        return "index_teacher";
    }

    /**
     * Method opens page with form to add test to database.
     * @see QuestionController#backToIndex() Method redirects to main page
     * @param model holder to add attributes into html from java (Test class)
     * @see Test Test class
     * @return http name of page for adding test.
     * See src/main/resources/templates/new_test.html in templates.
     */
    @RequestMapping("/new_test")
    public String showNewTestForm(Model model) {
        Test test = new Test();
        model.addAttribute("test", test);
        return "new_test";
    }

    /**
     * Method opens page with form to edit existing test and save modifications to database.
     * @see QuestionController#backToIndex() Method redirects to main page
     * @param test Test object to edit to, got by its id from database
     * @see Test Test class
     * @return redirection for main page.
     * See src/main/resources/templates/index_teacher.html in templates.
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEditedTest(@ModelAttribute("test") Test test) {
        dataBaseService.saveTest(test);
        return "redirect:/index_teacher";
    }

    /**
     * Method saves new Test object to database.
     * @see Test Test class
     * @param topic topic of the test made for
     * @param start_time date that reflects when test need to be started by students (start of the deadline)
     * @param end_time date that reflects when test need to be ended (end of the deadline)
     * @return redirection for main page.
     * See src/main/resources/templates/index_teacher.html in templates.
     */
    @RequestMapping(value = "/save_new_test", method = RequestMethod.POST)
    public String saveNewTest(@RequestParam String topic,
                           @RequestParam String start_time, @RequestParam String end_time) {
        Test test = new Test(topic, start_time, end_time);
        dataBaseService.saveTest(test);
        return "redirect:/index_teacher";
    }

    /**
     * Method opens page for editing an existing test (Changing its topic, start date or end date).
     * @see Test Test class
     * @see QuestionController#backToIndex() to redirect to main page
     * @param id (parameter got from url) id of the test that need to be modified
     * @return holder with both Model and View - a html page with some already preset parameters.
     * See src/main/resources/templates/edit_test.html in templates.
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditTestForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_test");
        Test test = dataBaseService.getTest(id);
        mav.addObject("test", test);
        return mav;
    }

    /**
     * Method deletes existing test from database from main page.
     * @see Test Test class
     * @param id (parameter got from url) id of the test that need to be deleted
     * @return redirection for main page.
     * See src/main/resources/templates/index_teacher.html in templates.
     */
    @RequestMapping("/delete/{id}")
    public String deleteTest(@PathVariable(name = "id") Long id) {
        dataBaseService.deleteTest(id);
        return "redirect:/index_teacher";
    }

    /**
     * Method opens page with table of student results for testing.
     * @see StudentResult StudentResult class
     * @see MainController#deleteResult(Long) Method deletes result from database
     * @see QuestionController#backToIndex() Method redirects to main page
     * @see com.example.webservice_for_online_testing.config.WebSecurityConfig#securityFilterChain(HttpSecurity)
     * Method logouts from account
     * @param model holder to add attributes into html from java (StudentResults class)
     * @return http name of page with results of students after testing.
     * See src/main/resources/templates/tests_results.html in templates.
     */
    @RequestMapping(value = "/show_results", method = RequestMethod.GET)
    public String showResults(Model model) {
        List<StudentResult> listResults = dataBaseService.getAllStudentResults();
        model.addAttribute("listResults", listResults);
        return "tests_results";
    }

    /**
     * Method deletes existing StudentResult object from database from its page.
     * @see StudentResult StudentResult class
     * @param id (parameter got from url) id of the studentResult that need to be deleted
     * @return redirection for page holding students` results.
     * See src/main/resources/templates/test_results.html in templates.
     */
    @RequestMapping("/delete_result/{id}")
    public String deleteResult(@PathVariable(name = "id") Long id) {
        dataBaseService.deleteStudentResult(id);
        return "redirect:/show_results";
    }

    /**
     * Method opens html page with histogram data that shows solution frequency of every test from database.
     * @see Test Test class
     * @param model holder to add attributes into html from java (list of Test objects)
     * @return http name of histogram page.
     * See src/main/resources/templates/histogram.html in templates.
     */
    @RequestMapping("/histogram")
    public String viewHistPage(Model model){
        List<Test> listTests = dataBaseService.getAll();
        model.addAttribute("listTests", listTests);
        return "histogram";
    }
}


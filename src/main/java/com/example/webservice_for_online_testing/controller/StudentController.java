package com.example.webservice_for_online_testing.controller;

import com.example.webservice_for_online_testing.domain.*;
import com.example.webservice_for_online_testing.service.DataBaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Main controller for STUDENT with all mappings those handle all requests from user with ROLE STUDENT.
 * @see com.example.webservice_for_online_testing.config.WebSecurityConfig
 * @author Kondrashov Dmitry
 * @version 1.0
 */
@Controller
public class StudentController {

    /** @see DataBaseService */
    private final DataBaseService dataBaseService;

    /**
     * Constructor of Student controller.
     * @param dataBaseService connects main service for handling all user`s modifications in database
     * {@link StudentController#dataBaseService}
     */
    @Autowired
    public StudentController(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    /**
     * Method opens main page of STUDENT with table of available tests, search bar, sorting by every column.
     * @see Test Test class
     * @see StudentController#startTest(Long) Method starts chosen test
     * @see LoginController#authorPage() Method shows page with information about author
     * @see com.example.webservice_for_online_testing.config.WebSecurityConfig#securityFilterChain(HttpSecurity)
     * Method logouts from account
     * @param model holder to add attributes into html from java (keyword and list of Test objects)
     * @param keyword keyword to filter data in search bar.
     * @return http name of index page.
     * See src/main/resources/templates/index_student.html in templates.
     */
    @RequestMapping("/index_student")
    public String viewUserTestsPage(Model model, @Param("keyword") String keyword) {
        List<Test> listTests = dataBaseService.listAll(keyword);
        model.addAttribute("listTests", listTests);
        model.addAttribute("keyword", keyword);
        return "index_student";
    }

    /**
     * Method opens page with mixed questions and mixed answers for them. In case there are no questions
     * in database yet method will open appropriate page.
     * @see Test Test class
     * @see RandomedQuestion Question class with random variants to answer
     * @param id id of the Test to collect all its questions in database
     * @return holder with both Model and View - a html page with some already preset parameters.
     * See src/main/resources/templates/student_testing.html in templates.
     * See src/main/resources/templates/unavailable_test.html in templates.
     */
    @RequestMapping("/student_testing/{id}")
    public ModelAndView startTest(@PathVariable(name = "id") Long id) {
        Test test = dataBaseService.getTest(id);
        List<Question> questionList = dataBaseService.listAllTestId(test);
        if (questionList.size() > 0) {
            ModelAndView mav = new ModelAndView("student_testing");
            Question question = new Question(test);

            // getting shuffled list of questions for test with shuffled answers
            List<RandomedQuestion> shuffledList = new ArrayList<>();
            for (Question quest : questionList) {
                RandomedQuestion randomedQuestion = new RandomedQuestion(quest);
                shuffledList.add(randomedQuestion);
            }
            Collections.shuffle(shuffledList);

            mav.addObject("questionList", shuffledList);
            mav.addObject("question", question);
            return mav;
        }
        else {
            ModelAndView second_mav = new ModelAndView("unavailable_test");
            second_mav.addObject("test", test);
            return second_mav;
        }
    }

    /**
     * Method used for html button to redirect for student`s main page.
     * @return redirection for main page.
     * See src/main/resources/templates/index_student.html in templates.
     */
    @RequestMapping("get_to_index_student")
    public String backToIndexStudent() {
        return "redirect:/index_student";
    }

    /**
     * Method collects Student`s answers after testing, calculates result, number of correct answers,
     * incorrect answers, skipped questions, percentage of correct answers. Shows table of correct answers for
     * students incorrect answers if there were such.
     * Contains a form to insert name and surname of user to save it in database.
     * @param request HttpServletRequest object contains data from html page like ids, names, etc.
     * @return holder with both Model and View - a html page with some already preset parameters.
     * See src/main/resources/templates/preresult.html in templates.
     */
    @RequestMapping(value = "save_answers", method = RequestMethod.POST)
    public ModelAndView saveAnswers(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("preresult");
        String[] questionIDs = request.getParameterValues("questionID");  // list of IDs of questions in str
        String[] questionIDAnswers = request.getParameterValues("questionIDAnswer");  // list of answers
        int length = questionIDs.length, correct = 0, incorrect = 0, skipped = 0;
        String correctAnswer, percent;
        Test test = dataBaseService.getQuestion(Long.parseLong(questionIDs[0])).getTest_id();
        StringBuilder result = new StringBuilder(" из ");
        StudentResult studentResult = new StudentResult(test);
        result.append(length);
        List<IncorrectAnswer> incorrectAnswerList = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Question question = dataBaseService.getQuestion(Long.parseLong(questionIDs[i]));
            correctAnswer = question.getAnswer();
            if (Objects.equals("--Выберите ответ--", questionIDAnswers[i])) skipped++;
            else if (Objects.equals(correctAnswer, questionIDAnswers[i])) correct++;
            else {
                incorrect++;
                String questionProblem = question.getProblem();
                incorrectAnswerList.add(new IncorrectAnswer(questionProblem, questionIDAnswers[i], correctAnswer));
            };
        }

        result.insert(0, correct);
        studentResult.setResult(result.toString());
        percent = String.format("%.2f", (float) correct * 100 / length);

        mav.addObject("test", test);
        mav.addObject("studentResult", studentResult);
        mav.addObject("correct", correct);
        mav.addObject("incorrect", incorrect);
        mav.addObject("skipped", skipped);
        mav.addObject("result", result);
        mav.addObject("percent", percent);
        if (!incorrectAnswerList.isEmpty()) {
            mav.addObject("incorrectAnswerList", incorrectAnswerList);
        }
        return mav;
    }

    /**
     * Method collects name of the test, student`s personal data, result for testing and saves them to database.
     * Also, it increments number of attempts for solved test in database. After updating data method opens html page
     * with all results of the student according his surname and name in database and shows circle diagram that means
     * average success rate for all tests.
     * @see StudentResult StudentResult class
     * @param request HttpServletRequest object contains data from html page like ids, names, etc.
     * @param student_surname surname of the user to save in database
     * @param student_name name of the user to save in database
     * @param student_patronymic patronymic of the user to save in database
     * @return holder with both Model and View - a html page with some already preset parameters.
     * See src/main/resources/templates/my_results.html in templates.
     */
    @RequestMapping(value = "save_results", method = RequestMethod.POST)
    public ModelAndView showAndSaveResult(HttpServletRequest request,
                                          @RequestParam(name = "student_surname") String student_surname,
                                          @RequestParam(name = "student_name")    String student_name,
                                          @RequestParam(name = "student_patronymic", required = false, defaultValue = "")
                                                                                  String student_patronymic) {
        Long test_id = Long.parseLong(request.getParameter("test_id"));
        String result = request.getParameter("result");
        ModelAndView mav = new ModelAndView("my_results");
        Test test = dataBaseService.getTest(test_id);
        Long new_attempts = test.getAttempts();
        test.setAttempts(new_attempts + 1);
        StudentResult studentResult = new StudentResult(test, student_name, student_surname, result);
        if (!student_patronymic.isEmpty()) {
            studentResult.setStudent_patronymic(student_patronymic);
        }
        dataBaseService.saveStudentResult(studentResult);
        List<StudentResult> listResults = dataBaseService.getStudentResultsBySurname(student_surname, student_name);
        float percentage, a = 0f, b = 0f;
        String[] rs;
        for (StudentResult res: listResults) {
            rs = res.getResult().split(" из ");
            a += Float.parseFloat(rs[0]);
            b += Float.parseFloat(rs[1]);
        }
        percentage = (float) Math.round(a * 100 / b);
        mav.addObject("listResults", listResults);
        mav.addObject("percentage", percentage);
        return mav;
    }
}

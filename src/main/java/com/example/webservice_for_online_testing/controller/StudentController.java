package com.example.webservice_for_online_testing.controller;

import com.example.webservice_for_online_testing.domain.Question;
import com.example.webservice_for_online_testing.domain.RandomedQuestion;
import com.example.webservice_for_online_testing.domain.StudentResult;
import com.example.webservice_for_online_testing.domain.Test;
import com.example.webservice_for_online_testing.service.DataBaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    // сохранение введённых ответов, введеение данных студента
    @RequestMapping(value = "save_answers", method = RequestMethod.POST)
    public ModelAndView saveAnswers(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("preresult");
        String[] questionIDs = request.getParameterValues("questionID");  // список ID вопросов в строковом виде
        String[] questionIDAnswers = request.getParameterValues("questionIDAnswer");  // список введёных ответов
        int length = questionIDs.length, correct = 0, incorrect = 0, skipped = 0;
        String correctAnswer, percent;
        Test test = dataBaseService.getQuestion(Long.parseLong(questionIDs[0])).getTest_id();
        StringBuilder result = new StringBuilder(" из ");
        StudentResult studentResult = new StudentResult(test);
        result.append(length);

        for (int i = 0; i < length; i++) {
            correctAnswer = dataBaseService.getQuestion(Long.parseLong(questionIDs[i])).getAnswer();
            if (Objects.equals("--Выберите ответ--", questionIDAnswers[i])) skipped++;
            else if (Objects.equals(correctAnswer, questionIDAnswers[i])) correct++;
            else incorrect++;
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
        mav.addObject("result", result);
        return mav;
    }
    // сохранение решения в таблицу и открытие таблицы со всеми результатами
    @RequestMapping(value = "save_results", method = RequestMethod.POST)
    public ModelAndView showAndSaveResult(HttpServletRequest request,
                                          @RequestParam(name = "student_surname") String student_surname,
                                          @RequestParam(name = "student_name") String student_name,
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
        List<StudentResult> listResults= dataBaseService.getStudentResultsBySurname(student_surname, student_name);
        mav.addObject("listResults", listResults);
        return mav;
    }
}

package com.example.webservice_for_online_testing.controller;

import java.util.List;

import com.example.webservice_for_online_testing.service.DataBaseService;
import com.example.webservice_for_online_testing.domain.Test;
import com.example.webservice_for_online_testing.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    private DataBaseService dataBaseService;


    // главная таблица со списком всех тестов со стороны преподавателя
    @RequestMapping("/index")
    public String viewTestsPage(Model model, @Param("keyword") String keyword) {
        List<Test> listTests = dataBaseService.listAll(keyword);
        model.addAttribute("listTests", listTests);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    // переход на страницу добавления новой записи о тесте
    @RequestMapping("/new_test")
    public String showNewTestForm(Model model) {
        Test test = new Test();
        model.addAttribute("test", test);
        return "new_test";
    }
    // редактирование записи о тесте
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTest(@ModelAttribute("test") Test test) {
        dataBaseService.saveTest(test);
        return "redirect:/index";
    }
    // сохранение новой записи о тесте
    @RequestMapping(value = "/save_new_test", method = RequestMethod.POST)
    public String saveTest(@RequestParam String topic,
                           @RequestParam String start_time, @RequestParam String end_time) {
        Test test = new Test(topic, start_time, end_time);
        dataBaseService.saveTest(test);
        return "redirect:/index";
    }
    // переход на страницу редактирования теста
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditTestForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_test");
        Test test = dataBaseService.getTest(id);
        mav.addObject("test", test);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteTest(@PathVariable(name = "id") Long id) {
        dataBaseService.deleteTest(id);
        return "redirect:/index";
    }
}


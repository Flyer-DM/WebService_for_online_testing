package com.example.webservice_for_online_testing;

import java.util.List;

import com.example.webservice_for_online_testing.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    private TestService testService;

    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Test> listTests = testService.listAll(keyword);
        model.addAttribute("listTests", listTests);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewTestForm(Model model) {
        Test test = new Test();
        model.addAttribute("test", test);
        return "new_test";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTest(@ModelAttribute("test") Test test) {
        testService.save(test);
        return "redirect:/";
    }
    @RequestMapping(value = "/save_new", method = RequestMethod.POST)
    public String saveTest(@RequestParam String topic,
                           @RequestParam String start_time, @RequestParam String end_time) {
        Test test = new Test(topic, start_time, end_time);
        testService.save(test);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditTestForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_test");
        Test test = testService.get(id);
        mav.addObject("test", test);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteTest(@PathVariable(name = "id") Long id) {
        testService.delete(id);
        return "redirect:/";
    }
}


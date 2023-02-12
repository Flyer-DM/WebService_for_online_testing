package com.example.webservice_for_online_testing;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    private TestService service;

    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Test> listTests = service.listAll(keyword);
        model.addAttribute("listTests", listTests);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewTestForm(Model model) {
        Test test = new Test();
        test.setDifficulty("Простой");
        String[] difList = new String[]{"Простой", "Средний", "Сложный"};
        model.addAttribute("difList", difList);
        model.addAttribute("test", test);
        return "new_test";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTest(@ModelAttribute("test") Test test) {
        service.save(test);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditTestForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_test");
        Test test = service.get(id);
        mav.addObject("test", test);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteTest(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return "redirect:/";
    }
}


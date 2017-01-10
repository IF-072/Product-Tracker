package com.softserve.academy.controllers;

import com.softserve.academy.dto.Unit;
import com.softserve.academy.mappers.UnitMapper;
import com.softserve.academy.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/units")
public class UnitsController {

    @Autowired
    private UnitService unitService;

    @RequestMapping("/")
    public String unitsMainPage(Model model) {
        model.addAttribute("units", unitService.getAllUnits());
        model.addAttribute("unit", new Unit());

        return "units/index";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUnit(@ModelAttribute("unit") Unit unit) {
        unitService.addUnit(unit);
        return "redirect:/units/";
    }

    @RequestMapping("/remove/{id}")
    public String removeUnit(@PathVariable("id") int id) {
        unitService.removeUnit(id);

        return "redirect:/units/";
    }
}

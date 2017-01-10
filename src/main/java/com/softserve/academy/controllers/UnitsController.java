package com.softserve.academy.controllers;

import com.softserve.academy.dto.Unit;
import com.softserve.academy.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/edit/{id}/name/{name}")
    @ResponseBody
    public ResponseEntity removeUnit(@PathVariable("id") int id, @PathVariable("name") String name) {

        Unit unit = unitService.getUnitById(id);
        if(unit != null){
            unit.setName(name);
            unitService.updateUnit(unit);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

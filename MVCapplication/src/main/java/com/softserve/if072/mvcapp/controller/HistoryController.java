package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.History;
import com.softserve.if072.mvcapp.service.HistoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The HistoryController class handles requests for "/history" and renders appropriate view
 * REST server
 *
 * @author Igor Kryviuk
 */
@Controller
@RequestMapping("/history")
public class HistoryController{
    @Autowired
    HistoryService historyService;

    /**
     * Handles requests for getting all history records for current user
     *
     * @param model - a map that will be handed off to the view for rendering the data to the client
     * @return string with appropriate view name
     */
    @GetMapping
    public String getHistories(Model model) {
        List<History> histories = historyService.getByUserId();
        model.addAttribute("histories", histories);
        if (CollectionUtils.isNotEmpty(histories)) {
            return "history";
        }
        return "emptyHistory";
    }

    /**
     * Handles requests for deleting a record from the history of current user
     *
     * @param historyId - history unique identifier
     * @return string with appropriate view name
     */
    @GetMapping("/delete")
    public String deleteHistory(@RequestParam int historyId) {
        historyService.deleteHistory(historyId);
        return "redirect: /history/";
    }
}

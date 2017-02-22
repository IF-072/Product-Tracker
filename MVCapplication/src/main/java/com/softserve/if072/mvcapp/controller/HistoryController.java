package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.History;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The HistoryController class is used to retrieve appropriate resources from the
 * REST server
 *
 * @author Igor Kryviuk
 */
@Controller
@RequestMapping("/history")
public class HistoryController extends BaseController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Value("${application.restHistoryURL}")
    private String restHistoryURL;
    @Value("${application.restHistoryDeleteURL}")
    private String restHistoryDeleteURL;
    @Value("${application.restHistoryDeleteURL}")
    private String getRestHistoryDeleteURL;
    @Value("${history.found}")
    private String historyFound;
    @Value("${history.SuccessfullyOperation}")
    private String successfullyOperation;

    @GetMapping
    public String getHistory(Model model) {

        RestTemplate template = getRestTemplate();

        List<History> histories = template.getForObject(String.format(restHistoryURL, getCurrentUser().getId()), List.class);
        model.addAttribute("histories", histories);
        LOGGER.info(String.format(historyFound, "user", getCurrentUser().getId(), histories.size()));
        if (CollectionUtils.isNotEmpty(histories)) {
            return "history";
        }
        return "emptyHistory";
    }

    @GetMapping("/delete")
    public String deleteHistory(@RequestParam int historyId) {
        template.delete(String.format(restHistoryDeleteURL, getCurrentUser().getId(), historyId));
        LOGGER.info(String.format(successfullyOperation, historyId, "deleted from"));
        return "redirect: /history/";
    }
}

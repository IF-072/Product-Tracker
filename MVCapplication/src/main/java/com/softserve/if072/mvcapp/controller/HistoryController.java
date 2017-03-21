package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistorySearchDTO;
import com.softserve.if072.mvcapp.service.HistoryService;
import com.softserve.if072.mvcapp.service.PdfCreatorService;
import com.softserve.if072.mvcapp.service.ProductPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The HistoryController class handles requests for "/history" and renders appropriate view
 * REST server
 *
 * @author Igor Kryviuk
 */
@Controller
@RequestMapping("/history")
@SessionAttributes("historiesSession")
public class HistoryController {

    private HistoryService historyService;
    private ProductPageService productPageService;
    private UserService userService;
    private PdfCreatorService pdfCreatorService;

    public HistoryController(HistoryService historyService, ProductPageService productPageService,
                             UserService userService, PdfCreatorService pdfCreatorService) {
        this.historyService = historyService;
        this.productPageService = productPageService;
        this.userService = userService;
        this.pdfCreatorService = pdfCreatorService;
    }

    @InitBinder
    public void customizeBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        dateFormatter.setLenient(false);
        binder.registerCustomEditor(Date.class, "fromDate",
                new CustomDateEditor(dateFormatter, true));
        binder.registerCustomEditor(Date.class, "toDate",
                new CustomDateEditor(dateFormatter, true));
    }

    /**
     * Handles requests for getting all history records for current user
     *
     * @param model - a map that will be handed off to the view for rendering the data to the client
     * @return string with appropriate view name
     */
    @GetMapping
    public String getHistories(Model model) {
        List<History> histories = historyService.getByUserId();

        if (CollectionUtils.isNotEmpty(histories)) {
            model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));
            model.addAttribute("histories", histories);
            model.addAttribute("historySearchDTO", new HistorySearchDTO());
            model.addAttribute("historiesSession", pdfCreatorService.getHistoriesByUserId());
            return "history";
        }
        return "emptyHistory";
    }

    /**
     * Handles requests for getting all history records for current user by given search criterias
     *
     * @param model - a map that will be handed off to the view for rendering the data to the client
     * @return string with appropriate view name
     */
    @PostMapping
    public String searchHistories(Model model, @ModelAttribute("historySearchDTO") HistorySearchDTO searchParams, BindingResult result) {
        model.addAttribute("historySearchDTO", result.hasErrors() ? new HistorySearchDTO() : searchParams);
        model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));

        List<History> histories = historyService.getByUserIdAndSearchParams(searchParams);
        if (CollectionUtils.isNotEmpty(histories)) {
            model.addAttribute("histories", histories);
            model.addAttribute("historiesSession", pdfCreatorService.getByUserIdAndSearchParams(searchParams));
        }

        return "history";
    }

    /**
     * Handles requests for deleting a record from the history of current user
     *
     * @param historyId - history unique identifier
     * @return string with appropriate view name
     */
    @GetMapping("/delete/{historyId}")
    public String deleteRecordFromHistory(@PathVariable int historyId) {
        historyService.deleteRecordFromHistory(historyId);
        return "redirect:/history";
    }

    /**
     * Handles requests for deleting all records from the history of current user
     *
     * @return string with appropriate view name
     */
    @GetMapping("/delete")
    public String deleteAllRecordsFromHistory() {
        historyService.deleteAllRecordsFromHistory();
        return "emptyHistory";
    }

    /**
     * Handles requests for creating PDF file with histories which are displayed on the page
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     */
    @RequestMapping(value="/getpdf", method= RequestMethod.GET)
    public void getPDF(HttpServletRequest request, HttpServletResponse response,
                       @CookieValue(value = "myLocaleCookie", required = false) final String locale) throws IOException {

        HttpSession session = request.getSession();
        List<History> histories = (List) session.getAttribute("historiesSession");

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temperotyFilePath = tempDirectory.getAbsolutePath();

        String fileName = "History.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename="+ fileName);

        try {
            pdfCreatorService.createPDF(temperotyFilePath+"\\"+fileName, histories, locale);
            ByteArrayOutputStream baos = pdfCreatorService.convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

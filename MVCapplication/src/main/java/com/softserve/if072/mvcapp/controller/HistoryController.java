package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistorySearchDTO;
import com.softserve.if072.mvcapp.service.HistoryService;
import com.softserve.if072.mvcapp.service.PdfCreatorService;
import com.softserve.if072.mvcapp.service.ProductPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

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
    public String getHistories(Model model,
                               @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber
// , @PathVariable int pageSize ) {
    ) {
        Page<History> historiesPage = historyService.getHistoryPage(pageNumber, 25);

        int current = historiesPage.getNumber() + 1;
        int begin = 1;
        int end = historiesPage.getTotalPages();

        if (historiesPage.getTotalElements() > 0) {
            model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));
            model.addAttribute("historiesPage", historiesPage);
            model.addAttribute("historySearchDTO", new HistorySearchDTO());
            model.addAttribute("historiesSession", pdfCreatorService.getHistoriesByUserId());
            model.addAttribute("beginIndex", begin);
            model.addAttribute("endIndex", end);
            model.addAttribute("currentIndex", current);
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
    public String searchHistories(Model model,
                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                  @ModelAttribute("historySearchDTO") HistorySearchDTO searchParams, BindingResult result) {

        model.addAttribute("historySearchDTO", result.hasErrors() ? new HistorySearchDTO() : searchParams);
        model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));

        Page<History> histories = historyService.getHistorySearchPage(pageNumber, 25, searchParams);
        if (histories.getTotalElements() > 0) {
            model.addAttribute("histories", histories);
            //model.addAttribute("historiesSession", pdfCreatorService.getByUserIdAndSearchParams(pageNumber, 25, searchParams));
            model.addAttribute("historiesPage", histories);
            model.addAttribute("beginIndex", 1);
            model.addAttribute("endIndex", histories.getTotalPages());
            model.addAttribute("currentIndex", histories.getNumber() + 1);
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
     * @param request  - HttpServletRequest
     * @param response - HttpServletResponse
     */
    @RequestMapping(value = "/getpdf", method = RequestMethod.GET)
    public void getPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        List<History> histories = (List) session.getAttribute("historiesSession");

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temperotyFilePath = tempDirectory.getAbsolutePath();

        String fileName = "History.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        try {
            pdfCreatorService.createPDF(temperotyFilePath + "\\" + fileName, histories);
            ByteArrayOutputStream baos = pdfCreatorService.convertPDFToByteArrayOutputStream(temperotyFilePath + "\\"
                    + fileName);
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}


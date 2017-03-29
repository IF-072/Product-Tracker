package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistorySearchDTO;
import com.softserve.if072.mvcapp.service.HistoryService;
import com.softserve.if072.mvcapp.service.PdfCreatorService;
import com.softserve.if072.mvcapp.service.ProductPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
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
@SessionAttributes({"historiesSession", "historySearchDTO", "pageSize"})
public class HistoryController {

    private static final Logger LOGGER = LogManager.getLogger(HistoryController.class);

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
     * @param model      a map that will be handed off to the view for rendering the data to the client
     * @param pageNumber - number of loaded page
     * @param pageSize   - number of items on page
     * @return string with appropriate view name
     */
    @GetMapping
    public String getHistories(Model model, HttpSession session,
                               @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "25") int pageSize) {

        HistorySearchDTO searchParams = session.getAttribute("historySearchDTO") == null ? new HistorySearchDTO()
                : (HistorySearchDTO) session.getAttribute("historySearchDTO");
        model.addAttribute("historySearchDTO", searchParams);
        model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));

        Page<History> histories = historyService.getHistorySearchPage(searchParams, pageNumber, pageSize);
        if (histories.getTotalElements() > 0) {
            model.addAttribute("historiesSession", histories.getContent());
            model.addAttribute("historiesPage", histories);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("beginIndex", 1);
            model.addAttribute("endIndex", histories.getTotalPages());
            model.addAttribute("currentIndex", histories.getNumber() + 1);
        }

        return "history";
    }

    /**
     * Handles requests for search history records by given search criterias
     *
     * @param searchParams - DTO with search form values
     * @return redirect to regular history page
     */

    @PostMapping
    public String searchHistories(HttpSession session, @ModelAttribute("historySearchDTO") HistorySearchDTO searchParams,
                                  BindingResult result) {
        if (result.hasErrors()) {
            session.setAttribute("historySearchDTO", new HistorySearchDTO());
        }
        return "redirect:/history";
    }

    /**
     * Cleans up the saved search parameters and redirects user to history page
     *
     * @param model a model which stores search params
     * @return redirect to history page
     */
    @GetMapping("/clearFilter")
    public String clearSearchFilters(Model model) {
        model.addAttribute("historySearchDTO", new HistorySearchDTO());
        return "redirect:/history";
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
    public void getPDF(HttpServletRequest request, HttpServletResponse response,
                       @CookieValue(value = "myLocaleCookie", required = false) final String locale) throws
            IOException {

        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<History> histories = (List) session.getAttribute("historiesSession");

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temperotyFilePath = tempDirectory.getAbsolutePath();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String fileName = "History_" + simpleDateFormat.format(new Date()) + ".pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        try {
            pdfCreatorService.createPDF(temperotyFilePath + "\\" + fileName, histories, locale);
            try (ByteArrayOutputStream baos = pdfCreatorService.convertPDFToByteArrayOutputStream(temperotyFilePath +
                    "\\" + fileName);
                 OutputStream os = response.getOutputStream()) {
                baos.writeTo(os);
                os.flush();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}


package com.softserve.if072.mvcapp.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistorySearchDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * The class contains methods to create a PDF file with information about the user's history
 *
 * @author Vitaliy Malisevych
 */
@Service
public class PdfCreatorService {

    private static final Logger LOGGER = LogManager.getLogger(PdfCreatorService.class);

    private UserService userService;
    private RestTemplate restTemplate;
    private MessageSource messageSource;

    @Value("${application.restHistoryURL}")
    private String restHistoryURL;
    @Value("${application.restHistorySearchURL}")
    private String restHistorySearchURL;

    @Autowired
    public PdfCreatorService(UserService userService, RestTemplate restTemplate,
                             MessageSource messageSource) throws IOException, DocumentException {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.messageSource = messageSource;
    }

    private BaseFont bf = BaseFont.createFont("/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

    private Font LARGE = new Font(bf, 18, Font.BOLD);
    private Font SMALL_BOLD = new Font(bf, 12, Font.BOLD);
    private Font SMALL = new Font(bf, 12);

    /**
     * Creates PDF file
     *
     * @param file - future PDF file
     * @return document
     */
    public Document createPDF(String file, List<History> histories, String locale) throws IOException {

        locale = locale == null ? "en" : locale;

        Document document = null;

        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            addMetaData(document);

            addTitlePage(document, locale);

            createTable(document, histories, locale);

            document.close();

        } catch (DocumentException e) {
            LOGGER.error(e.getMessage());
        }
        return document;

    }

    private void addMetaData(Document document) {
        document.addTitle("The history of user: " + userService.getCurrentUser().getName());
        document.addAuthor("Product Tracker");
    }

    private void addTitlePage(Document document, String locale)
            throws DocumentException, IOException {

        Paragraph preface = new Paragraph();
        creteEmptyLine(preface, 1);
        preface.add(new Paragraph(messageSource.getMessage("history.theHistoryOfUser",
                null, new Locale(locale)) + userService.getCurrentUser().getName(), LARGE));

        creteEmptyLine(preface, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        preface.add(new Paragraph(messageSource.getMessage("history.createdOn",
                null, new Locale(locale)) + simpleDateFormat.format(new Date()), SMALL_BOLD));
        document.add(preface);

    }

    private void creteEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void createTable(Document document, List<History> histories, String locale) throws DocumentException {

        Paragraph paragraph = new Paragraph();
        creteEmptyLine(paragraph, 2);
        document.add(paragraph);
        PdfPTable table = new PdfPTable(5);

        PdfPCell c1 = new PdfPCell(new Phrase(messageSource.getMessage("product.product",
                null, new Locale(locale)), SMALL));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingBottom(5);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(messageSource.getMessage("product.description",
                null, new Locale(locale)), SMALL));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingBottom(5);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(messageSource.getMessage("product.category",
                null, new Locale(locale)), SMALL));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingBottom(5);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(messageSource.getMessage("amount",
                null, new Locale(locale)), SMALL));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingBottom(5);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(messageSource.getMessage("history.date",
                null, new Locale(locale)), SMALL));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingBottom(5);
        table.addCell(c1);
        table.setHeaderRows(1);

        for(History history : histories) {
            table.setWidthPercentage(100);
            table.getDefaultCell().setPaddingBottom(5);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(new Phrase(history.getProduct().getName(), SMALL));
            table.addCell(new Phrase(history.getProduct().getDescription(), SMALL));
            table.addCell(new Phrase(history.getProduct().getCategory().getName(), SMALL));
            table.addCell(new Phrase(Integer.toString(history.getAmount()),SMALL));
            table.addCell(new Phrase(history.getUsedDate().toString(), SMALL));
        }

        document.add(table);
    }

    /**
     * Converts PDF file to array of bytes
     *
     * @param fileName - name of the file
     * @return stream of byte's array
     */
    public ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {

        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            inputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return baos;
    }

    /**
     * Make request to a REST server for retrieving all history records for current user
     *
     * @return list of history records or empty list
     */
    public List<History> getHistoriesByUserId() {

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userService.getCurrentUser().getId());

        ResponseEntity<List<History>> historiesResponse = restTemplate.exchange(restHistoryURL, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<History>>() {
                }, param);

        return historiesResponse.getBody();

    }

    public List<History> getByUserIdAndSearchParams(HistorySearchDTO searchDTO) {
        HttpEntity<HistorySearchDTO> request = new HttpEntity<>(searchDTO);
        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userService.getCurrentUser().getId());

        ResponseEntity<List<History>> historiesResponse = restTemplate.exchange(restHistorySearchURL, HttpMethod.POST,
                request, new ParameterizedTypeReference<List<History>>() {
                }, param);

        return historiesResponse.getBody();
    }

}

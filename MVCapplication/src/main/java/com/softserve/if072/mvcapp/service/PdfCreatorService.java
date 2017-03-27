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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class contains methods to create a PDF file with information about the user's history
 *
 * @author Vitaliy Malisevych
 */
@Service
public class PdfCreatorService {

    private HistoryService historyService;
    private UserService userService;
    private RestTemplate restTemplate;

    @Value("${application.restHistoryURL}")
    private String restHistoryURL;
    @Value("${application.restHistorySearchPageURL}")
    private String restHistorySearchPageURL;

    @Autowired
    public PdfCreatorService(HistoryService historyService, UserService userService,
                             RestTemplate restTemplate) throws IOException, DocumentException {
        this.historyService = historyService;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    private BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\ARIAL.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

    private Font LARGE = new Font(bf, 18, Font.BOLD);
    private Font SMALL_BOLD = new Font(bf, 12, Font.BOLD);
    private Font SMALL = new Font(bf, 12);

    /**
     * Creates PDF file
     *
     * @param file - future PDF file
     * @return document
     */
    public Document createPDF(String file, List<History> histories) throws IOException {

        Document document = null;

        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            addMetaData(document);

            addTitlePage(document);

            createTable(document, histories);

            document.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;

    }

    private void addMetaData(Document document) {
        document.addTitle("The history of user: " + userService.getCurrentUser().getName());
        document.addAuthor("Product Tracker");
    }

    private void addTitlePage(Document document)
            throws DocumentException, IOException {

        Paragraph preface = new Paragraph();
        creteEmptyLine(preface, 1);
        preface.add(new Paragraph("The history of user: " + userService.getCurrentUser().getName(), LARGE));

        creteEmptyLine(preface, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        preface.add(new Paragraph("History created on "
                + simpleDateFormat.format(new Date()), SMALL_BOLD));
        document.add(preface);

    }

    private void creteEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void createTable(Document document, List<History> histories) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        creteEmptyLine(paragraph, 2);
        document.add(paragraph);
        PdfPTable table = new PdfPTable(5);

        PdfPCell c1 = new PdfPCell(new Phrase("Product"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingBottom(5);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Description"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingBottom(5);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Category"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingBottom(5);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Amount"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingBottom(5);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Used Date"));
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
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

    public List<History> getByUserIdAndSearchParams(int pageNumber, int pageSize, HistorySearchDTO searchDTO) {
        HttpEntity<HistorySearchDTO> request = new HttpEntity<>(searchDTO);
        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userService.getCurrentUser().getId());

        ResponseEntity<Page<History>> historiesResponse = restTemplate.exchange(restHistorySearchPageURL, HttpMethod.POST,
                request, new ParameterizedTypeReference<Page<History>>() {
                }, param);

        return historiesResponse.getBody().getContent();
    }

}

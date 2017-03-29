package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * The PdfCreatorServiceTest class is used to test
 * PdfCreatorService class methods.
 *
 * @author Vitaliy Malisevych
 */
@RunWith(MockitoJUnitRunner.class)
public class PdfCreatorServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private FileInputStream inputStream;

    @InjectMocks
    private PdfCreatorService pdfCreatorService;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private User user;
    private ResponseEntity<List<History>> historiesResponse;
    private List<History> histories;

    @Before
    public void setup() {
        History history = new History();
        histories = Arrays.asList(history, history);
        historiesResponse = new ResponseEntity<>(histories, HttpStatus.OK);
        user = new User();
        user.setId(1);
    }

    @Test(expected = FileNotFoundException.class)
    public void createPdf_ShouldThrowFileNotFoundException() throws IOException {
        History history = new History();
        List<History> histories = Arrays.asList(history, history);
        String file = "";
        pdfCreatorService.createPDF(file, histories, "en");
    }

    @Test(expected = NullPointerException.class)
    public void createPdf_ShouldThrowNullPointerException() throws IOException {
        History history = new History();
        List<History> histories = Arrays.asList(history, history);
        pdfCreatorService.createPDF(null, histories, "en");
    }

    @Test
    public void convertPDFToByteArrayOutputStream_ShouldReturnByteArrayOutputStream() throws IOException {
        byte[] buffer = new byte[1024];

        File file = File.createTempFile("C:\\rootFolder\\childFolder1", "test-file.pdf");

        pdfCreatorService.convertPDFToByteArrayOutputStream(file.getPath());
        verify(inputStream, times(0)).read();
    }

}

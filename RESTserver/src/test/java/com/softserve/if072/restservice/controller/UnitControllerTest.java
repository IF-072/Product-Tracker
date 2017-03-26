package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Unit;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.UnitService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Contains tests for UnitController methods
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitControllerTest {

    @Mock
    private UnitService unitService;
    private UnitController unitController;
    private MockMvc mockMvc;
    private List<Unit> units;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType()
            , MediaType.APPLICATION_JSON.getSubtype()
            , Charset.forName("utf8"));

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        unitController = new UnitController(unitService);
        mockMvc = standaloneSetup(unitController).build();

        units = new ArrayList<>();
        units.add(new Unit());
        units.add(new Unit());
        ReflectionTestUtils.setField(unitController, "unitNotFound", "unit_not_found");
    }

    @Test
    public void getAllUnits_ShouldReturnUnitsList() throws Exception {
        when(unitService.getAllUnits()).thenReturn(units);
        mockMvc.perform(get("/unit/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(unitService).getAllUnits();
    }

    @Test
    public void getAllUnits_ShouldReturnNullWhenThereAreNoUnitsForCurrentUser() throws Exception {
        when(unitService.getAllUnits()).thenThrow(DataNotFoundException.class).thenReturn(null);
        mockMvc.perform(get("/unit/"))
                .andExpect(status().isNotFound());
        verify(unitService).getAllUnits();
    }

    @Test
    public void getAllUnits_ShouldReturnUnit() throws Exception {
        when(unitService.getUnitById(anyInt())).thenReturn(new Unit());
        mockMvc.perform(get("/unit/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        verify(unitService).getUnitById(anyInt());
    }

    @Test
    public void getAllUnits_ShouldReturnNullWhenUnitIdIsInvalid() throws Exception {
        when(unitService.getUnitById(anyInt())).thenThrow(DataNotFoundException.class);
        mockMvc.perform(get("/unit/0"))
                .andExpect(status().isNotFound());
        verify(unitService).getUnitById(anyInt());
    }
}

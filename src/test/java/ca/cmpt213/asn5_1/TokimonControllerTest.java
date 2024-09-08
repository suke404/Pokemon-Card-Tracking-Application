package ca.cmpt213.asn5_1.controller;

import ca.cmpt213.asn5_1.model.Tokimon;
import ca.cmpt213.asn5_1.service.TokimonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TokimonControllerTest {

    @MockBean
    private TokimonService tokimonService;

    @InjectMocks
    private TokimonController tokimonController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(tokimonController).build();
    }

    @Test
    public void testGetAllTokimon() throws Exception {
        List<Tokimon> tokimonList = Arrays.asList(
                new Tokimon("Tokimon1", "Fire", 5, "http://example.com/tokimon1.png", 100),
                new Tokimon("Tokimon2", "Water", 3, "http://example.com/tokimon2.png", 80)
        );
        when(tokimonService.getAllTokimon()).thenReturn(tokimonList);

        mockMvc.perform(get("/api/tokimon/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tokimon1"))
                .andExpect(jsonPath("$[1].name").value("Tokimon2"));

        verify(tokimonService, times(1)).getAllTokimon();
    }

    @Test
    public void testGetTokimonById() throws Exception {
        Tokimon tokimon = new Tokimon("Tokimon1", "Fire", 5, "http://example.com/tokimon1.png", 100);
        when(tokimonService.getTokimonById(1L)).thenReturn(tokimon);

        mockMvc.perform(get("/api/tokimon/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tokimon1"));

        verify(tokimonService, times(1)).getTokimonById(1L);
    }

    @Test
    public void testGetTokimonById_NotFound() throws Exception {
        when(tokimonService.getTokimonById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/tokimon/1"))
                .andExpect(status().isNotFound());

        verify(tokimonService, times(1)).getTokimonById(1L);
    }


    @Test
    public void testEditTokimon() throws Exception {
        Tokimon tokimon = new Tokimon( "Tokimon1", "Fire", 5, "http://example.com/tokimon1.png", 100);
        when(tokimonService.editTokimon(eq(1L), any(Tokimon.class))).thenReturn(tokimon);

        mockMvc.perform(put("/api/tokimon/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Tokimon1\",\"type\":\"Fire\",\"rarity\":5,\"pictureUrl\":\"http://example.com/tokimon1.png\",\"hp\":100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tokimon1"));

        verify(tokimonService, times(1)).editTokimon(eq(1L), any(Tokimon.class));
    }

    @Test
    public void testEditTokimon_NotFound() throws Exception {
        when(tokimonService.editTokimon(eq(1L), any(Tokimon.class))).thenReturn(null);

        mockMvc.perform(put("/api/tokimon/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Tokimon1\",\"type\":\"Fire\",\"rarity\":5,\"pictureUrl\":\"http://example.com/tokimon1.png\",\"hp\":100}"))
                .andExpect(status().isNotFound());

        verify(tokimonService, times(1)).editTokimon(eq(1L), any(Tokimon.class));
    }

    @Test
    public void testDeleteTokimon() throws Exception {
        when(tokimonService.deleteTokimon(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/tokimon/1"))
                .andExpect(status().isNoContent());

        verify(tokimonService, times(1)).deleteTokimon(1L);
    }

    @Test
    public void testDeleteTokimon_NotFound() throws Exception {
        when(tokimonService.deleteTokimon(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/tokimon/1"))
                .andExpect(status().isNotFound());

        verify(tokimonService, times(1)).deleteTokimon(1L);
    }
}

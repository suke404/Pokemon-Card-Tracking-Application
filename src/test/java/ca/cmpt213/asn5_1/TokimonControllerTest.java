package ca.cmpt213.asn5_1;

import ca.cmpt213.asn5_1.model.Tokimon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokimonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllTokimons() throws Exception {
        mockMvc.perform(get("/tokimons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", greaterThanOrEqualTo(0)));
    }

    @Test
    public void testGetTokimonById() throws Exception {
        int tokimonId = 1; // Assume this ID exists in the database
        mockMvc.perform(get("/tokimons/{id}", tokimonId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(tokimonId));
    }

    @Test
    public void testGetTokimonById_NotFound() throws Exception {
        int tokimonId = 999; // Assume this ID does not exist
        mockMvc.perform(get("/tokimons/{id}", tokimonId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTokimon() throws Exception {
        Tokimon newTokimon = new Tokimon("Toki3", 30.0, 15.0, 20.0);
        mockMvc.perform(post("/tokimons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTokimon)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void testCreateTokimon_InvalidInput() throws Exception {
        Tokimon invalidTokimon = new Tokimon("", -10.0, 0.0, -5.0);
        mockMvc.perform(post("/tokimons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidTokimon)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateTokimon() throws Exception {
        int tokimonId = 1; // Assume this ID exists in the database
        Tokimon updatedTokimon = new Tokimon("UpdatedToki", 40.0, 20.0, 25.0);
        mockMvc.perform(put("/tokimons/{id}", tokimonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedTokimon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedToki"));
    }

    @Test
    public void testUpdateTokimon_NotFound() throws Exception {
        int tokimonId = 999; // Assume this ID does not exist
        Tokimon updatedTokimon = new Tokimon("UpdatedToki", 40.0, 20.0, 25.0);
        mockMvc.perform(put("/tokimons/{id}", tokimonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedTokimon)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTokimon() throws Exception {
        int tokimonId = 1; // Assume this ID exists in the database
        mockMvc.perform(delete("/tokimons/{id}", tokimonId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTokimon_NotFound() throws Exception {
        int tokimonId = 999; // Assume this ID does not exist
        mockMvc.perform(delete("/tokimons/{id}", tokimonId))
                .andExpect(status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

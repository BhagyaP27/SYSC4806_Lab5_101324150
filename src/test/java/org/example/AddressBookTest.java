package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressBookTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateAddressBookAndAddBuddy() throws Exception {
        this.mockMvc.perform(post("/api/addressbooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        String buddyJson = "{\"name\": \"Test Buddy\", \"phoneNumber\": \"12345\", \"address\": \"Test City\"}";
        this.mockMvc.perform(post("/api/addressbooks/1/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buddyJson))
                .andExpect(status().isOk());
    }

    @Test
    void testThymeleafViewController() throws Exception {
        // Test GUI view page loads and passes model attributes
        this.mockMvc.perform(get("/addressbook?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("addressbook"))
                .andExpect(model().attributeExists("addressBook"))
                .andExpect(model().attributeExists("buddies"));
    }
}
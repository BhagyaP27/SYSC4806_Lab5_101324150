package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:mockdb;DB_CLOSE_DELAY=-1")
@AutoConfigureMockMvc
class AddressBookControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    void createAddressBookReturnsNewBook() throws Exception {
        mockMvc.perform(post("/api/addressbooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.buddies").isArray());
    }

    @Test
    void addBuddyToExistingBook() throws Exception {
        AddressBook book = addressBookRepository.save(new AddressBook());

        String buddyJson = """
                {"name":"Alice","phoneNumber":"613-555-1234","address":"Ottawa"}
                """;

        mockMvc.perform(post("/api/addressbooks/" + book.getId() + "/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buddyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buddies[0].name").value("Alice"))
                .andExpect(jsonPath("$.buddies[0].address").value("Ottawa"));
    }

    @Test
    void getAddressBookReturnsIt() throws Exception {
        AddressBook book = addressBookRepository.save(new AddressBook());

        mockMvc.perform(get("/api/addressbooks/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()));
    }

    @Test
    void getMissingAddressBookReturns404() throws Exception {
        mockMvc.perform(get("/api/addressbooks/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listBuddiesOfBook() throws Exception {
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("Eve", "613-555-0005", "Halifax"));
        book = addressBookRepository.save(book);

        mockMvc.perform(get("/api/addressbooks/" + book.getId() + "/buddies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Eve"));
    }
}
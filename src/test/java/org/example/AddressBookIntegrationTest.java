package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressBookIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createBookAddBuddyThenFetchIt() {
        // POST: create a book
        ResponseEntity<AddressBook> created =
                restTemplate.postForEntity("/api/addressbooks", null, AddressBook.class);
        assertEquals(HttpStatus.OK, created.getStatusCode());
        int id = created.getBody().getId();

        // POST: add a buddy to it
        BuddyInfo buddy = new BuddyInfo("Bob", "613-555-9999", "Toronto");
        ResponseEntity<AddressBook> updated =
                restTemplate.postForEntity("/api/addressbooks/" + id + "/buddies", buddy, AddressBook.class);
        assertEquals(HttpStatus.OK, updated.getStatusCode());
        assertEquals(1, updated.getBody().getBuddies().size());

        // GET: fetch it back and verify
        ResponseEntity<AddressBook> fetched =
                restTemplate.getForEntity("/api/addressbooks/" + id, AddressBook.class);
        assertEquals(HttpStatus.OK, fetched.getStatusCode());
        assertEquals("Bob", fetched.getBody().getBuddies().get(0).getName());
    }

    @Test
    void getUnknownBookReturns404() {
        ResponseEntity<AddressBook> response =
                restTemplate.getForEntity("/api/addressbooks/99999", AddressBook.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
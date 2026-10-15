package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:integrationdb;DB_CLOSE_DELAY=-1")
class AddressBookIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createBookAddBuddyThenFetchIt() {
        ResponseEntity<AddressBook> created =
                restTemplate.postForEntity("/api/addressbooks", null, AddressBook.class);
        assertEquals(HttpStatus.OK, created.getStatusCode());
        int id = created.getBody().getId();

        BuddyInfo buddy = new BuddyInfo("Bob", "613-555-9999", "Toronto");
        ResponseEntity<AddressBook> updated =
                restTemplate.postForEntity("/api/addressbooks/" + id + "/buddies", buddy, AddressBook.class);
        assertEquals(HttpStatus.OK, updated.getStatusCode());
        assertEquals(1, updated.getBody().getBuddies().size());

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

    // GET /api/addressbooks/{id}/buddies

    @Test
    void listBuddiesOfAddressBook() {
        int id = restTemplate.postForEntity("/api/addressbooks", null, AddressBook.class)
                .getBody().getId();

        restTemplate.postForEntity("/api/addressbooks/" + id + "/buddies",
                new BuddyInfo("Carol", "613-555-0003", "Montreal"), AddressBook.class);
        restTemplate.postForEntity("/api/addressbooks/" + id + "/buddies",
                new BuddyInfo("Dan", "613-555-0004", "Calgary"), AddressBook.class);

        ResponseEntity<BuddyInfo[]> response =
                restTemplate.getForEntity("/api/addressbooks/" + id + "/buddies", BuddyInfo[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals("Carol", response.getBody()[0].getName());
        assertEquals("Dan", response.getBody()[1].getName());
    }

    @Test
    void listBuddiesOfUnknownBookReturns404() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/addressbooks/99999/buddies", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BuddyInfoTest {

    @Test
    void testGetName() {
        BuddyInfo b = new BuddyInfo("Bobert", "667-430-0000", "Pull st");
        assertEquals("Bobert", b.getName());
    }
    @Test
    void testGetPhoneNumber() {
        BuddyInfo b = new BuddyInfo("Bob", "-667-430-0000", "Push st");
        assertEquals("-667-430-0000", b.getPhoneNumber());
    }

}
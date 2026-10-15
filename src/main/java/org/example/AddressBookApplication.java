package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AddressBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BuddyInfoRepository buddyRepo, AddressBookRepository bookRepo) {
        return (args) -> {
            BuddyInfo b1 = new BuddyInfo("Bhagya", "613-141-5926", "Ottawa");
            BuddyInfo b2 = new BuddyInfo("Amedeo", "602-214-0760", "Italy");

            AddressBook book = new AddressBook();
            book.addBuddy(b1);
            book.addBuddy(b2);

            bookRepo.save(book);   // cascade = ALL means this also saves b1 and b2

            System.out.println("Saved address book with id: " + book.getId());
            bookRepo.findById(book.getId()).ifPresent(savedBook -> {
                for (BuddyInfo b : savedBook.getBuddies()) {
                    System.out.println(b);
                }
            });
        };
    }
}
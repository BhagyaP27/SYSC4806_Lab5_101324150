package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AddressBookRestController {
    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @PostMapping("/addressbooks")
    public AddressBook createAddressBook(){
        AddressBook addressBook = new AddressBook();
        return addressBookRepository.save(addressBook);
    }

    @PostMapping("/addressbooks/{bookId}/buddies")
    public ResponseEntity<AddressBook> addBuddyToAddressBook(
            @PathVariable Integer bookId,
            @RequestBody BuddyInfo buddy){
        Optional<AddressBook> bookOpt = addressBookRepository.findById(bookId);
        if(bookOpt.isPresent()){
            AddressBook Book = bookOpt.get();
            buddyInfoRepository.save(buddy);
            Book.addBuddy(buddy);
            AddressBook updateBook = addressBookRepository.save(Book);
            return ResponseEntity.ok(updateBook);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addressbooks/{bookId}")
    public ResponseEntity<AddressBook> getAddressBook(@PathVariable Integer bookId){
        return addressBookRepository.findById(bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

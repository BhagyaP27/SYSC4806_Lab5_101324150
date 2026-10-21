package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AddressBookViewController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<AddressBook> books = new ArrayList<>();
        addressBookRepository.findAll().forEach(books::add);
        model.addAttribute("addressBooks", books);
        return "index";
    }

    @PostMapping("/addressbook/create")
    public String createAddressBook() {
        addressBookRepository.save(new AddressBook());
        return "redirect:/";
    }

    @GetMapping("/addressbook")
    public String viewAddressBook(@RequestParam(name = "id", defaultValue = "1") Integer id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id).orElse(new AddressBook());
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("buddies", addressBook.getBuddies());
        return "addressbook";
    }

    @PostMapping("/addressbook/{id}/buddies")
    public String addBuddy(@PathVariable int id,
                           @RequestParam String name,
                           @RequestParam String phoneNumber,
                           @RequestParam String address) {
        AddressBook book = addressBookRepository.findById(id).orElse(null);
        if (book != null) {
            BuddyInfo buddy = new BuddyInfo(name, phoneNumber, address);
            buddyInfoRepository.save(buddy);
            book.addBuddy(buddy);
            addressBookRepository.save(book);
        }
        return "redirect:/addressbook?id=" + id;
    }
}
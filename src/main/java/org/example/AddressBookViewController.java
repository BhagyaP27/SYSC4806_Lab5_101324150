package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddressBookViewController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @GetMapping("/addressbook")
    public String viewAddressBook(@RequestParam(name = "id", defaultValue = "1") Integer id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id).orElse(new AddressBook());
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("buddies", addressBook.getBuddies());
        return "addressbook";
    }
}
package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<BuddyInfo> buddies;

    public AddressBook() {
        this.buddies = new ArrayList<>();
    }
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public void addBuddy(BuddyInfo buddy) {
        buddies.add(buddy);
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }
    public void setBuddies(List<BuddyInfo> buddies) {this.buddies = buddies;}

    public void printAll(){
        for (BuddyInfo b : buddies){
            System.out.println(b);
        }
    }
    public static void main(String[] args) {
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("Bhagya", "613-141-5926", "Ottawa"));
        book.addBuddy(new BuddyInfo("Amedeo", "602-214-0760", "Italy"));
        book.printAll();
    }
}

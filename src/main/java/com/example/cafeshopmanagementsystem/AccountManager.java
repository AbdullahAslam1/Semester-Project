package com.example.cafeshopmanagementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {

    private static final String ACCOUNTS_FILE = "accounts.dat"; // File to store account data
    private List<Account> accounts; // List to hold Account objects

    public AccountManager() {
        accounts = new ArrayList<>();
        loadAccounts(); // Load accounts from the file
    }

    // Method to load accounts from the file
    private void loadAccounts() {
        File file = new File(ACCOUNTS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                accounts = (List<Account>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading accounts: " + e.getMessage());
            }
        }
    }

    // Method to save accounts to the file
    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    // Method to register a new account
    public boolean register(String username, String password, String email) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return false; // Username already exists
            }
        }
        accounts.add(new Account(username, password, email));
        saveAccounts();
        return true;
    }

    // Method to verify login credentials
    public boolean verify(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return true; // Found matching username and password
            }
        }
        return false;
    }

    // Check if an email is registered
    public boolean isEmailRegistered(String email) {
        for (Account account : accounts) {
            if (account.getEmail().equals(email)) {
                return true; // Found matching email
            }
        }
        return false;
    }

    // Update password for a given email
    public void updatePassword(String email, String newPassword) {
        for (Account account : accounts) {
            if (account.getEmail().equals(email)) {
                account.setPassword(newPassword);
                saveAccounts();
                return;
            }
        }
    }
}

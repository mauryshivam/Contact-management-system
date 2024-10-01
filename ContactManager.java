import java.io.*;
import java.util.*;

public class ContactManager {

    private static final String FILE_NAME = "contacts.txt";
    private static Map<String, Contact> contacts = new HashMap<>();

    public static void main(String[] args) {
        loadContactsFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Contact Manager ---");
            System.out.println("1. Add New Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Save and Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            switch (option) {
                case 1:
                    addNewContact(scanner);
                    break;
                case 2:
                    viewAllContacts();
                    break;
                case 3:
                    editContact(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    break;
                case 5:
                    saveContactsToFile();
                    System.out.println("Contacts saved. Exiting program.");
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    // Add a new contact
    private static void addNewContact(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email address: ");
        String email = scanner.nextLine();

        if (contacts.containsKey(name)) {
            System.out.println("Contact with this name already exists.");
        } else {
            contacts.put(name, new Contact(name, phone, email));
            System.out.println("Contact added successfully.");
        }
    }

    // View all contacts
    private static void viewAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("\n--- Contact List ---");
            for (Contact contact : contacts.values()) {
                System.out.println(contact);
            }
        }
    }

    // Edit an existing contact
    private static void editContact(Scanner scanner) {
        System.out.print("Enter the name of the contact to edit: ");
        String name = scanner.nextLine();

        if (!contacts.containsKey(name)) {
            System.out.println("Contact not found.");
        } else {
            Contact contact = contacts.get(name);
            System.out.println("Editing contact: " + contact);
            System.out.print("Enter new phone number (leave blank to keep unchanged): ");
            String newPhone = scanner.nextLine();
            if (!newPhone.isEmpty()) {
                contact.setPhoneNumber(newPhone);
            }
            System.out.print("Enter new email address (leave blank to keep unchanged): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                contact.setEmail(newEmail);
            }
            System.out.println("Contact updated successfully.");
        }
    }

    // Delete a contact
    private static void deleteContact(Scanner scanner) {
        System.out.print("Enter the name of the contact to delete: ");
        String name = scanner.nextLine();

        if (contacts.remove(name) != null) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Save contacts to a file for persistent storage
    private static void saveContactsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contacts.values()) {
                writer.write(contact.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    // Load contacts from a file on startup
    private static void loadContactsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    contacts.put(parts[0], new Contact(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous contacts found, starting with an empty contact list.");
        }
    }
}

// Contact class to store contact details
class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toFileFormat() {
        return name + "," + phoneNumber + "," + email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ," Phone: " + phoneNumber + ", Email: " + email;
    }

}
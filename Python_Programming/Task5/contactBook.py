import json
from pathlib import Path

class ContactBook:
    def __init__(self):
        self.contacts = {}
        self.load_from_file('contacts.json')

    def add_contact(self):
        name = input("Enter name: ").strip()
        if name in self.contacts:
            print("Contact already exists.")
            return
        phone = input("Enter phone number: ").strip()
        email = input("Enter email: ").strip()
        address = input("Enter address: ").strip()
        self.contacts[name] = {
            'phone': phone,
            'email': email,
            'address': address
        }
        self.save_to_file('contacts.json')
        print("Contact added successfully.")

    def view_contacts(self):
        if not self.contacts:
            print("No contacts found.")
            return
        print("\nContact List:")
        for name, details in self.contacts.items():
            print(f"Name: {name}, Phone: {details['phone']}")

    def search_contact(self):
        search_term = input("Enter name or phone number to search: ").strip().lower()
        found = False
        for name, details in self.contacts.items():
            if search_term in name.lower() or search_term == details['phone']:
                print(f"\nName: {name}")
                print(f"Phone: {details['phone']}")
                print(f"Email: {details['email']}")
                print(f"Address: {details['address']}")
                found = True
        if not found:
            print("Contact not found.")

    def update_contact(self):
        name = input("Enter the name of the contact to update: ").strip()
        if name not in self.contacts:
            print("Contact not found.")
            return
        print("Leave the field empty if you do not want to update it.")
        phone = input("Enter new phone number: ").strip()
        email = input("Enter new email: ").strip()
        address = input("Enter new address: ").strip()

        if phone:
            self.contacts[name]['phone'] = phone
        if email:
            self.contacts[name]['email'] = email
        if address:
            self.contacts[name]['address'] = address

        self.save_to_file('contacts.json')
        print("Contact updated successfully.")

    def delete_contact(self):
        name = input("Enter the name of the contact to delete: ").strip()
        if name in self.contacts:
            del self.contacts[name]
            self.save_to_file('contacts.json')
            print("Contact deleted successfully.")
        else:
            print("Contact not found.")

    def save_to_file(self, filename):
        with open(filename, 'w') as file:
            json.dump(self.contacts, file, indent=4)

    def load_from_file(self, filename):
        file_path = Path(filename)
        if file_path.exists():
            with open(filename, 'r') as file:
                self.contacts = json.load(file)

def main():
    contact_book = ContactBook()

    while True:
        print("\nContact Book")
        print("1. Add Contact")
        print("2. View Contacts")
        print("3. Search Contact")
        print("4. Update Contact")
        print("5. Delete Contact")
        print("6. Exit")

        choice = input("Enter your choice: ").strip()

        if choice == '1':
            contact_book.add_contact()
        elif choice == '2':
            contact_book.view_contacts()
        elif choice == '3':
            contact_book.search_contact()
        elif choice == '4':
            contact_book.update_contact()
        elif choice == '5':
            contact_book.delete_contact()
        elif choice == '6':
            print("Exiting the contact book.")
            break
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()

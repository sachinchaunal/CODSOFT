import random
import string

def generate_password(length):
    if length < 1:
        return "Error: Password length must be at least 1."
    
    # Character sets
    lower = string.ascii_lowercase
    upper = string.ascii_uppercase
    digits = string.digits
    special = string.punctuation

    # Combine all character sets
    all_characters = lower + upper + digits + special

    # Generate password
    password = ''.join(random.choice(all_characters) for _ in range(length))
    
    return password

def main():
    print("Password Generator")
    
    while True:
        try:
            length = int(input("Enter the desired length for the password: "))
        except ValueError:
            print("Invalid input. Please enter a numeric value.")
            continue
        
        password = generate_password(length)
        print(f"Generated Password: {password}")
        
        # Option to generate another password or exit
        choice = input("Do you want to generate another password? (yes/no): ").strip().lower()
        if choice != 'yes':
            print("Exiting the password generator.")
            break

if __name__ == "__main__":
    main()

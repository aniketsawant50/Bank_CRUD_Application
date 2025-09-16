import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       Bankoperations bank = new Bankoperations();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Bank Management System ===");
            System.out.println("1. Create Account");
            System.out.println("2. View Accounts");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Delete Account");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> bank.createAccount();
                case 2 -> bank.viewAccounts();
                case 3 -> bank.deposit();
                case 4 -> bank.withdraw();
                case 5 -> bank.deleteAccount();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }
}

import java.sql.*;
import java.util.Scanner;

public class Bankoperations {
    private Connection conn;

    public Bankoperations() {
        conn = DBconnection.getConnection();
    }

    // Create a new account
    public void createAccount() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Account Type: ");
            String type = sc.nextLine();
            System.out.print("Enter Initial Balance: ");
            double balance = sc.nextDouble();

            String sql = "INSERT INTO accounts (name, account_type, balance) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setDouble(3, balance);
            ps.executeUpdate();

            System.out.println("Account created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    // View all accounts
    public void viewAccounts() {
        try {
            String sql = "SELECT * FROM accounts";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("ID | Name | Type | Balance");
            while (rs.next()) {
                System.out.println(rs.getInt("account_id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("account_type") + " | " +
                                   rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching accounts: " + e.getMessage());
        }
    }

    // Deposit money
    public void deposit() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Account ID: ");
            int id = sc.nextInt();
            System.out.print("Enter Amount to Deposit: ");
            double amount = sc.nextDouble();

            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Deposit successful!");
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error in deposit: " + e.getMessage());
        }
    }

    // Withdraw money
    public void withdraw() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Account ID: ");
            int id = sc.nextInt();
            System.out.print("Enter Amount to Withdraw: ");
            double amount = sc.nextDouble();

            // Check current balance
            String checkSql = "SELECT balance FROM accounts WHERE account_id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, id);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String sql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setDouble(1, amount);
                    ps.setInt(2, id);
                    ps.executeUpdate();
                    System.out.println("Withdrawal successful!");
                } else {
                    System.out.println("Insufficient balance!");
                }
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error in withdrawal: " + e.getMessage());
        }
    }

    // Delete account
    public void deleteAccount() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Account ID to Delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM accounts WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting account: " + e.getMessage());
        }
    }
}

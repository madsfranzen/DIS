import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class transactions {
    private static Connection con;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("Transactions");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\:1433;" +
                    "databaseName=BankDB;user=sa;password=Password123;encrypt=false;trustServerCertificate=true;");

            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            con.setAutoCommit(false);
            scanner = new Scanner(System.in);
            System.out.println("Indtast kontonummer: ");
            int accountNumber = scanner.nextInt();

            if (!printAccount(accountNumber)) {
                return;
            }

            System.out.println("Indsæt eller hæv beløb: +/-");
            int amount = scanner.nextInt();
            if (amount > 0) {
                insertAmount(accountNumber, amount);
            } else {
                withdrawAmount(accountNumber, amount);
            }

            printAccount(accountNumber);
            con.commit();
            System.out.println("🙏 Thank you for using our ATM! We hope you have a great day! 😎💸");

            if (con != null)
                con.close();
        } catch (Exception e) {
            System.out.print("fejl:  " + e.getMessage());
        }
    }

    private static void insertAmount(int accountNumber, int amount) throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("update konto set saldo = saldo + " + amount + " where kontonr = " + accountNumber);
    }

    private static void withdrawAmount(int accountNumber, int amount) throws SQLException {
        Statement stmt = con.createStatement();
        int positiveAmount = Math.abs(amount);
        stmt.executeUpdate("update konto set saldo = saldo - " + positiveAmount + " where kontonr = " + accountNumber);
    }

    private static boolean printAccount(int accountNumber) throws SQLException {
        System.out.println("\n🌟✨ ACCOUNT ✨🌟:");
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("select * from konto WITH (UPDLOCK) where kontonr = " + accountNumber);
        if (!res.next()) {
            System.out.println("❌ Account not found!");
            con.rollback();
            return false;
        }
        System.out.println(
                "Konto " + res.getInt(1) + " har saldo " + res.getInt(2) + " og ejer " + res.getString(3));
        if (stmt != null)
            stmt.close();
        return true;
    }
}
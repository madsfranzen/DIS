import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class transactionsOptimist {
    private static Connection con;
    private static Scanner scanner;
    private static int oldBalance;

    public static void main(String[] args) {
        System.out.println("Transactions Optimist");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\:1433;" +
                    "databaseName=BankDB;user=sa;password=Password123;encrypt=false;trustServerCertificate=true;");


            Statement stmt = con.createStatement();
            stmt.executeUpdate("SET TRANSACTION ISOLATION LEVEL READ COMMITTED");

            con.setAutoCommit(false);
            scanner = new Scanner(System.in);
            System.out.println("Indtast kontonummer: ");
            int accountNumber = scanner.nextInt();

            if (!printAccount(accountNumber)) {
                return;
            }

            System.out.println("Indsæt eller hæv beløb: +/-");
            int amount = scanner.nextInt();

            ResultSet res = stmt.executeQuery("select saldo from konto where kontonr = " + accountNumber);
            res.next();
            int newBalance = res.getInt(1);
            if (newBalance != oldBalance) {
                System.out.println("🚨 Balance has meanwhile changed! Your transaction has been rolled back.");
                con.rollback();
                return;
            } else {
                if (amount > 0) {
                    insertAmount(accountNumber, amount);
                } else {
                    withdrawAmount(accountNumber, amount);
                }
            }

            printAccount(accountNumber);
            con.commit();
            System.out.println("🙏 Thank you for using our ATM! We hope you have a great day! 😎💸");

            if (con != null)
                con.close();
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof SQLException) {
                System.out.println(((SQLException) e).getErrorCode());
            }
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
        ResultSet res = stmt.executeQuery("select * from konto where kontonr = " + accountNumber);
        if (!res.next()) {
            System.out.println("❌ Account not found!");
            con.rollback();
            return false;
        }
        oldBalance = res.getInt(2);
        System.out.println(
                "Konto " + res.getInt(1) + " har saldo " + res.getInt(2) + " og ejer " + res.getString(3));
        if (stmt != null)
            stmt.close();
        return true;
    }
}
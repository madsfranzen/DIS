import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class transactions {
    private static Connection con;

    public static void main(String[] args) {
        System.out.println("Transactions");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost\\:1433;" +
                    "databaseName=BankDB;user=sa;password=Password123;encrypt=false;trustServerCertificate=true;");

            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            printAllAccounts();

            if (makeTransaction(1002, 1001, 1000)) {
                printAllAccounts();
            } else {
                System.out.println("❌ Transaction failed!");
            }

            if (con != null)
                con.close();
        } catch (Exception e) {
            System.out.print("fejl:  " + e.getMessage());
        }
    }

    private static void printAllAccounts() throws SQLException {
        System.out.println("\n🌟✨ ALL ACCOUNTS ✨🌟:");
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("select * from konto");
        while (res.next()) {
            System.out.println(
                    "Konto " + res.getInt(1) + " har saldo " + res.getInt(2) + " og ejer " + res.getString(3));
        }
        if (stmt != null)
            stmt.close();
    }

    public static boolean makeTransaction(int fromAccount, int toAccount, double amountToTransfer) {
        try {

            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            con.setAutoCommit(false);

            System.out.println("\n🚀 Starting transaction: " + amountToTransfer + " from account " + fromAccount
                    + " to account " + toAccount);
            Statement stmt = con.createStatement();

            int fromAccountBalance = 0;
            int toAccountBalance = 0;

            System.out.println("📊 Checking source account " + fromAccount);
            ResultSet res = stmt.executeQuery("SELECT * FROM konto WITH (UPDLOCK) WHERE kontonr = " + fromAccount);
            if (!res.next()) {
                System.out.println("❌ Source account not found!");
                con.rollback();
                return false;
            } else {
                ResultSet res2 = stmt.executeQuery("SELECT saldo FROM konto WITH (UPDLOCK) WHERE kontonr = " + fromAccount);
                res2.next();
                fromAccountBalance = res2.getInt(1);
                System.out.println("💰 Source account balance: " + fromAccountBalance);
                if (fromAccountBalance < amountToTransfer) {
                    System.out.println("❌ Insufficient funds! Required: " + amountToTransfer + ", Available: "
                            + fromAccountBalance);
                    con.rollback();
                    return false;
                }
            }

            System.out.println("📊 Checking destination account " + toAccount);
            res = stmt.executeQuery("SELECT * FROM konto WHERE kontonr = " + toAccount);
            if (!res.next()) {
                System.out.println("❌ Destination account not found!");
                con.rollback();
                return false;
            } else {
                ResultSet res2 = stmt.executeQuery("SELECT saldo FROM konto WHERE kontonr = " + toAccount);
                res2.next();
                toAccountBalance = res2.getInt(1);
                System.out.println("💰 Destination account balance: " + toAccountBalance);
            }

            Scanner scan = new Scanner(System.in);
            System.out.println("Press enter to continue");
            scan.nextLine();
            scan.close();

            fromAccountBalance -= amountToTransfer;
            toAccountBalance += amountToTransfer;

            System.out.println("💸 Updating source account balance to: " + fromAccountBalance);
            stmt.executeUpdate("UPDATE konto SET saldo = " + fromAccountBalance + " WHERE kontonr = " + fromAccount);

            System.out.println("💸 Updating destination account balance to: " + toAccountBalance);
            stmt.executeUpdate("UPDATE konto SET saldo = " + toAccountBalance + " WHERE kontonr = " + toAccount);

            System.out.println("✅ Transaction completed successfully!");
            con.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error during transaction: " + e.getMessage());
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }
}
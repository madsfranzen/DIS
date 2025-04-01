import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class testsql {
	private static Connection minConnection;

	public static void main(String[] args) {
		try {
			System.out.println("Program started: " + LocalDateTime.now() + "\n");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost\\:1433;" +
					"databaseName=BankDB;user=sa;password=Password123;encrypt=false;trustServerCertificate=true;");

			printAllAccounts();

			if (makeTransaction(1001, 1002, 100)) {
				printAllAccounts();
			} else {
				System.out.println("❌ Transaction failed!");
			}

			if (minConnection != null)
				minConnection.close();
		} catch (Exception e) {
			System.out.print("fejl:  " + e.getMessage());
		}
	}

	private static void printAllAccounts() throws SQLException {
		System.out.println("\n🌟✨ ALL ACCOUNTS ✨🌟:");
		Statement stmt = minConnection.createStatement();
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
			System.out.println("\n🚀 Starting transaction: " + amountToTransfer + " from account " + fromAccount + " to account " + toAccount);
			Statement stmt = minConnection.createStatement();

			int fromAccountBalance = 0;
			int toAccountBalance = 0;

			System.out.println("📊 Checking source account " + fromAccount);
			ResultSet res = stmt.executeQuery("SELECT * FROM konto WHERE kontonr = " + fromAccount);
			if (!res.next()) {
				System.out.println("❌ Source account not found!");
				return false;
			} else {
				ResultSet res2 = stmt.executeQuery("SELECT saldo FROM konto WHERE kontonr = " + fromAccount);
				res2.next();
				fromAccountBalance = res2.getInt(1);
				System.out.println("💰 Source account balance: " + fromAccountBalance);
				if (fromAccountBalance < amountToTransfer) {
					System.out.println("❌ Insufficient funds! Required: " + amountToTransfer + ", Available: " + fromAccountBalance);
					return false;
				}
			}

			System.out.println("📊 Checking destination account " + toAccount);
			res = stmt.executeQuery("SELECT * FROM konto WHERE kontonr = " + toAccount);
			if (!res.next()) {
				System.out.println("❌ Destination account not found!");
				return false;
			} else {
				ResultSet res2 = stmt.executeQuery("SELECT saldo FROM konto WHERE kontonr = " + toAccount);
				res2.next();
				toAccountBalance = res2.getInt(1);
				System.out.println("💰 Destination account balance: " + toAccountBalance);
			}

			fromAccountBalance -= amountToTransfer;
			toAccountBalance += amountToTransfer;

			System.out.println("💸 Updating source account balance to: " + fromAccountBalance);
			stmt.executeUpdate("UPDATE konto SET saldo = " + fromAccountBalance + " WHERE kontonr = " + fromAccount);
			
			System.out.println("💸 Updating destination account balance to: " + toAccountBalance);
			stmt.executeUpdate("UPDATE konto SET saldo = " + toAccountBalance + " WHERE kontonr = " + toAccount);

			System.out.println("✅ Transaction completed successfully!");
			return true;
		} catch (SQLException e) {
			System.out.println("❌ Error during transaction: " + e.getMessage());
			return false;
		}
	}
}
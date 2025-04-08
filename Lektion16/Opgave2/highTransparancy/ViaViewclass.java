package Opgave2.highTransparancy;

import java.sql.*;
import java.util.*;

import Opgave2.Person;

public class ViaViewclass {
    public static void main(String[] args) {
        try {
            ArrayList<Person> liste = new ArrayList<Person>();
            // læser viewet person via native SQL-Server driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection minConnection;
            minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost\\:1433;" +
                    "databaseName=ddba;user=sa;password=Password123;encrypt=false;trustServerCertificate=true;");
            String sql = "select * from person";
            Statement stmt = minConnection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                Person p = new Person();
                p.setCpr(res.getString("cpr"));
                p.setNavn(res.getString("navn"));
                p.setBynavn(res.getString("bynavn"));
                p.setLoen(res.getInt("loen"));
                p.setSkatteprocent(res.getInt("skatteprocent"));
                liste.add(p);
            }
            ;
            // udskriver indholdet af de to tabeller
            for (int i = 0; i < liste.size(); i++) {
                Person s = liste.get(i);
                System.out.printf("%-10s %-20s %-15s %-8d %-5d%n",
                        s.getCpr(),
                        s.getNavn(),
                        s.getBynavn(),
                        s.getLoen(),
                        s.getSkatteprocent());
            }
        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }
    }
}

package Opgave2.lowTransparancy;

import java.sql.*;
import java.util.*;

import Opgave2.Person;

public class ViaTwoParts {
    public static void main(String[] args) {
        try {
            ArrayList<Person> liste = new ArrayList<Person>();
            Connection minConnection;
            minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost\\:1433;" +
                    "databaseName=ddba;user=sa;password=Password123;encrypt=false;trustServerCertificate=true;");
            String sql = "select * from personadr";
            Statement stmt = minConnection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                Person p = new Person();
                p.setCpr(res.getString("cpr"));
                p.setNavn(res.getString("navn"));
                p.setBynavn(res.getString("bynavn"));
                liste.add(p);
            }

            Connection minCon2;
            minCon2 = DriverManager.getConnection("jdbc:sqlserver://localhost\\:1433;" +
                    "databaseName=ddbb;user=sa;password=Password123;encrypt=false;trustServerCertificate=true;");
            String sql2 = "select * from personloen";
            Statement stmt2 = minCon2.createStatement();
            ResultSet res2 = stmt2.executeQuery(sql2);
            while (res2.next()) {
                String cpr = res2.getString("cpr");
                Person p = null;
                p = liste.stream()
                    .filter(person -> person.getCpr().equals(cpr))
                    .findFirst()
                    .orElse(null);
                p.setLoen(res2.getInt("loen"));
                p.setSkatteprocent(res2.getInt("skatteprocent"));
            }

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
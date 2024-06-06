package com.KiraJewelry.KiraJewelry;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class KiraJewelryApplication {


    public static void main(String[] args) {

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("12345");
        ds.setServerName("LAPTOP-O88MF7NC\\SAKURA_YAORI");
        ds.setPortNumber(1433); //port TCP/IP
        ds.setDatabaseName("JewelryStore");

        try (Connection con = ds.getConnection()) {
            System.out.println("Success");
            System.out.println(con.getCatalog());
        } catch (SQLServerException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        SpringApplication.run(KiraJewelryApplication.class, args);

    }
}


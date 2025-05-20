package fr.univrouen.rss25SB.controllers;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;  // <-- bien importer javax.sql.DataSource

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestBddController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/dbtest")
    public String testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return "Connected to DB: " + conn.getMetaData().getURL();
        } catch (SQLException e) {
            return "DB connection failed: " + e.getMessage();
        }
    }
}

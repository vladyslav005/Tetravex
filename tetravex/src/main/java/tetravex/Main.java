package tetravex;


import tetravex.consoleui.ConsoleUI;
import tetravex.data.DatabaseConnection;
import tetravex.data.PropertyReader;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        PropertyReader propertyReader = new PropertyReader();

        consoleUI.mainMenu();

        try {
            DatabaseConnection.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
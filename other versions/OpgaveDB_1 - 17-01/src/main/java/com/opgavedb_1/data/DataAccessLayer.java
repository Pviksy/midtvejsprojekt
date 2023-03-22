package com.opgavedb_1.data;

import java.sql.*;
import java.util.ArrayList;

public class DataAccessLayer {

    private Connection connection;

    public DataAccessLayer(String dbName) {
        openConnection(dbName);
    }

    private boolean openConnection(String databaseName) {
        String connectionString =
                "jdbc:jtds:sqlserver://localhost:1433/FileDB\";" +
                "instanceName=SQLEXPRESS;" +
                "databaseName=" + databaseName + ";" +
                "integratedSecurity=true;";

        connection = null;

        try {
            System.out.println("Connecting to database...");

            connection = DriverManager.getConnection(connectionString);

            System.out.println("Connected to database");

            return true;
        }
        catch (SQLException e) {
            System.out.println("Could not connect to database!");
            System.out.println(e.getMessage());

            return false;
        }
    }

     //tilføj en fil til databasen hvor den er opbevaret som varBinary
    public void addFile(String filePath) {
        try {
            String sql = "INSERT INTO [file] SELECT * FROM OPENROWSET (BULK '" + filePath + "', SINGLE_BLOB) AS X";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add file.");
            System.out.println(e.getMessage());

        }
    }

    public String getFile() {
        try {
            String sql = "SELECT [file] FROM [file] WHERE [id] = 4";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String fileBinary = resultSet.getString("file");

                System.out.println(fileBinary);



                return fileBinary;
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not get all items.");
            System.out.println(e.getMessage());

            return "";
        }
        return "";
    }


}

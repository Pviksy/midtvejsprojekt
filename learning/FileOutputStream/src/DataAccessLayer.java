import java.sql.*;

public class DataAccessLayer {

    private Connection connection;

    public DataAccessLayer() {
        openConnection("FileDB");
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

    public byte[] getFile() {
        try {
            String sql = "SELECT [file] FROM [file] WHERE [id] = 6";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String hexString = resultSet.getString("file");

                int len = hexString.length();
                byte[] data = new byte[len / 2];
                for (int i = 0; i < len; i += 2) {
                    data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                            + Character.digit(hexString.charAt(i+1), 16));
                }
                return data;
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not get all items.");
            System.out.println(e.getMessage());

            return new byte[1];
        }
        return new byte[1];
    }

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

}

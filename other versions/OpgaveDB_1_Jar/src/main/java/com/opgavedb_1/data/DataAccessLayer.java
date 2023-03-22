package com.opgavedb_1.data;

import com.opgavedb_1.entities.*;
import com.opgavedb_1.entities.objects.*;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DataAccessLayer {

    private Connection connection;

    //Mikkel
    public DataAccessLayer(String dbName) {
        openConnection(dbName);
    }

    //Mikkel
    private boolean openConnection(String databaseName) {
        String connectionString =
                "jdbc:jtds:sqlserver://localhost:1433/" + databaseName + "\";" +
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

    //Mikkel
    public void createNewTask(Task task) {
        try {
            String sql = "INSERT INTO [task] VALUES ('" + task.getTitle() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add task.");
            System.out.println(e.getMessage());

        }
    }

    public Task getLatestTask() {
        try {
            String sql = "SELECT TOP 1 * FROM [task] ORDER BY [id] DESC";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");

                return new Task(id, title);
            }
            return null;

        }
        catch (SQLException e) {
            System.out.println("Error: could not get latest task.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    //__________tag_________________mikkel_jonas__________________________________________
    public void createNewTag(Tag tag) {
        try {
            String sql = "INSERT INTO [tag] VALUES ('" + tag.getTitle() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    tag.setId(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add tag.");
            System.out.println(e.getMessage());

        }
    }

    public ArrayList<Tag> getAllTags() {
        try {
            ArrayList<Tag> allTags = new ArrayList<>();

            String sql = "SELECT * FROM [tag]";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");

                allTags.add(new Tag(id, title));
            }

            return allTags;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get all tags.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public void updateTagTitle(Tag tag) {
        try {
            String sql = "UPDATE [tag] SET [title] = '" + tag.getTitle() + "' WHERE [id] = " + tag.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update tag.");
            System.out.println(e.getMessage());
        }
    }

    public void deleteTag(Tag tag) {
        try {
            String sql = "DELETE FROM [tag] WHERE [id] = " + tag.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not remove tag.");
            System.out.println(e.getMessage());
        }
    }

    //__________tag_________________mikkel_jonas__________________________________________
    public void createNewTeam(Team team) {
        try {
            String sql = "INSERT INTO [team] VALUES ('" + team.getTitle() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    team.setId(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add tag.");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Team> getAllTeams() {
        try {
            ArrayList<Team> allTeams = new ArrayList<>();

            String sql = "SELECT * FROM [team]";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");

                allTeams.add(new Team(id, title));
            }

            return allTeams;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get all tags.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public void updateTeam(Team team) {
        try {
            String sql = "UPDATE [team] SET [title] = '" + team.getTitle() + "' WHERE [id] = " + team.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update team.");
            System.out.println(e.getMessage());
        }
    }

    public void deleteTeam(Team team) {
        try {
            String sql = "DELETE FROM [team] WHERE [id] = " + team.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not remove tag.");
            System.out.println(e.getMessage());
        }
    }

//__________client_________________mikkel_jonas__________________________________________

    public void createNewClient(User user) {
        try {
            String sql = "INSERT INTO [user] ([email], [phonenumber], [firstname], [lastname], [address], [pincode], [team_id], [user_type_id]) " +
                         "VALUES ('" + user.getEmail() + "', '" +
                                       user.getPhonenumber() + "', '" +
                                       user.getFirstname() + "', '" +
                                       user.getLastname() + "', '" +
                                       user.getAddress() + "', '" +
                                       generateRandomPincode() + "', '" +
                                       user.getTeam_id() + "', '" +
                                       3 + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    user.setId(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add user.");
            System.out.println(e.getMessage());
        }
    }

    private String generateRandomPincode() {
        Random random = new Random();
        int number = random.nextInt(999999);

        return String.format("%06d", number);
    }

    private int getLatestUserPincode() {
        try {
            String sql = "SELECT TOP 1 * FROM [user] ORDER BY [id] DESC";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                return resultSet.getInt("pincode");
            }
            return -1;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get latest client pincode");
            System.out.println(e.getMessage());

            return -1;
        }
    }

    public void sendWelcomeEmailToClient(User user) {

        String fullName = user.getFirstname() + " " + user.getLastname();

        Email welcomeEmail = EmailBuilder.startingBlank()
                .from("Resono", "eamvstudiegruppe5@gmail.com")
                .to(fullName, user.getEmail())
                .withSubject("Velkommen til Resono")
                .withPlainText("Hej " + fullName + "\n" +
                                  "Du er nu oprettet i vores applikation" + "\n" +
                                  "\n" +
                                  "Herunder finder du dine loginoplysninger:" + "\n" +
                                  "E-mail: " + user.getEmail() + "\n" +
                                  "Password: " + getLatestUserPincode())
                .buildEmail();

        MailerBuilder
                .withSMTPServer("Smtp.gmail.com", 587, "eamvstudiegruppe5@gmail.com", "heovzisxjckdyjcs")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(welcomeEmail);
    }

//__________materials____________________________________________________________

    //tilføj en fil til databasen hvor den er opbevaret som varBinary
    public void addFile(String filePath, String title) {
        try {
            String sql = "INSERT INTO [file] ([file]) SELECT * FROM OPENROWSET (BULK '" + filePath + "', SINGLE_BLOB) AS X";

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

        updateLatestFileTitle(title);
    }

    private void updateLatestFileTitle(String title) {
        try {
            String sql = "UPDATE [file] SET [title] = '" + title + "' WHERE [id] = " + getLatestFileID();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update title.");
            System.out.println(e.getMessage());
        }
    }

    private int getLatestFileID() {
        try {
            String sql = "SELECT TOP 1 * FROM [file] ORDER BY [id] DESC";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
            return -1;

        }
        catch (SQLException e) {
            System.out.println("Error: could not get latest id.");
            System.out.println(e.getMessage());

            return -1;
        }
    }

    //tilføj video til databasen, gemt som youtube url
    public void addNewVideo(String title, String URL) {
        try {
            String sql = "INSERT INTO [video] ([url], [title]) " +
                         "VALUES ('" + URL + "', '" + title + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add video.");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<File> getAllFiles() {
        try {
            ArrayList<File> allFiles = new ArrayList<>();

            String sql = "SELECT * FROM [file]";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String hexString = resultSet.getString("file");
                String title = resultSet.getString("title");

                allFiles.add(new File(id, hexString, title));
            }

            return allFiles;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get all files.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Video> getAllVideos() {
        try {
            ArrayList<Video> allVideos = new ArrayList<>();

            String sql = "SELECT * FROM [video]";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String URL = resultSet.getString("url");
                String title = resultSet.getString("title");

                allVideos.add(new Video(id, URL, title));
            }

            return allVideos;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get all videos.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public File getLatestFile() {
        try {
            String sql = "SELECT TOP 1 * FROM [file] ORDER BY [id] DESC";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String hexString = resultSet.getString("file");
                String title = resultSet.getString("title");

                return new File(id, hexString, title);
            }
            return null;

        }
        catch (SQLException e) {
            System.out.println("Error: could not get latest file.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public Video getLatestVideo() {
        try {
            String sql = "SELECT TOP 1 * FROM [video] ORDER BY [id] DESC";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String URL = resultSet.getString("url");
                String title = resultSet.getString("title");

                return new Video(id, URL, title);
            }
            return null;

        }
        catch (SQLException e) {
            System.out.println("Error: could not get latest video.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public void deleteFile(File file) {
        try {
            String sql = "DELETE FROM [file] WHERE [id] = " + file.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not remove file.");
            System.out.println(e.getMessage());
        }
    }

    public void deleteVideo(Video video) {
        try {
            String sql = "DELETE FROM [video] WHERE [id] = " + video.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not remove video.");
            System.out.println(e.getMessage());
        }
    }

    public void updateFile(File file) {
        try {
            String sql = "UPDATE [file] SET [title] = '" + file.getTitle() + "' WHERE [id] = " + file.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update file.");
            System.out.println(e.getMessage());
        }
    }

    public void updateVideo(Video video) {
        try {
            String sql = "UPDATE [video] SET [title] = '" + video.getTitle() + "' WHERE [id] = " + video.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update video.");
            System.out.println(e.getMessage());
        }
    }

    public void createTagFile(Tag tag, File file) {
        try {
            String sql = "INSERT INTO [tag_file] ([tag_id], [file_id]) " +
                         "VALUES ('" + tag.getId() + "', '" + file.getId() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add tag_file.");
            System.out.println(e.getMessage());
        }
    }

    public void createTagVideo(Tag tag, Video video) {
        try {
            String sql = "INSERT INTO [tag_video] ([tag_id], [video_id]) " +
                         "VALUES ('" + tag.getId() + "', '" + video.getId() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add tag_video.");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<File> getFilesByTag(Tag tag) {
        try {
            ArrayList<File> filesByTag = new ArrayList<>();

            String sql = "SELECT DISTINCT [id], [file], [title] FROM [file] " +
                         "JOIN [tag_file] ON [file].[id] = [tag_file].[file_id] " +
                         "WHERE [tag_file].[tag_id] = " + tag.getId();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String hexString = resultSet.getString("file");
                String title = resultSet.getString("title");

                filesByTag.add(new File(id, hexString, title));
            }

            return filesByTag;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get files by tag.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Video> getVideosByTag(Tag tag) {
        try {
            ArrayList<Video> videosByTag = new ArrayList<>();

            String sql = "SELECT DISTINCT [id], [url], [title] FROM [video] " +
                    "JOIN [tag_video] ON [video].[id] = [tag_video].[video_id] " +
                    "WHERE [tag_video].[tag_id] = " + tag.getId();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String URL = resultSet.getString("url");
                String title = resultSet.getString("title");

                videosByTag.add(new Video(id, URL, title));
            }

            return videosByTag;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get videos by tag.");
            System.out.println(e.getMessage());

            return null;
        }
    }

//_________________________________________________________________

    //metode til at vise client fra kun 1 hold

    public ArrayList<User> getAllClients() {
        try {
            ArrayList<User> allUsers = new ArrayList<>();

            String sql = "SELECT [user].[id], [email], [phonenumber], [firstname], [lastname], [address], [team].[title], [team_id] FROM [user]" +
                         "JOIN [team] ON [user].[team_id] = [team].[id]" +
                         "WHERE [user_type_id] = 3";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String phonenumber = resultSet.getString("phonenumber");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String address = resultSet.getString("address");
                String team_title = resultSet.getString("title");
                int team_id = resultSet.getInt("team_id");

                allUsers.add(new User(id, email, phonenumber, firstname, lastname, address, team_title, team_id));
            }

            return allUsers;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get all clients.");
            System.out.println(e.getMessage());

            return null;
        }
    }


//_________________update_client_info___________________________________________

    public void updateClientTeam(User client, Team team) {
        try {
            String sql = "UPDATE [user] SET [team_id] = '" + team.getId() + "' WHERE [id] = " + client.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update team of this client.");
            System.out.println(e.getMessage());
        }
    }

    public void updateUserFirstname(User user, String firstname) {
        try {
            String sql = "UPDATE [user] SET [firstname] = '" + firstname + "' WHERE [id] = " + user.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update firstname of this user.");
            System.out.println(e.getMessage());
        }
    }

    public void updateUserLastname(User user, String lastname) {
        try {
            String sql = "UPDATE [user] SET [lastname] = '" + lastname + "' WHERE [id] = " + user.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update lastname of this user.");
            System.out.println(e.getMessage());
        }
    }

    public void updateUserEmail(User user, String email) {
        try {
            String sql = "UPDATE [user] SET [email] = '" + email + "' WHERE [id] = " + user.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update email of this user.");
            System.out.println(e.getMessage());
        }
    }

    public void updateUserPhonenumber(User user, String phonenumber) {
        try {
            String sql = "UPDATE [user] SET [phonenumber] = '" + phonenumber + "' WHERE [id] = " + user.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update phonenumber of this user.");
            System.out.println(e.getMessage());
        }
    }

    public void updateUserAddress(User user, String address) {
        try {
            String sql = "UPDATE [user] SET [address] = '" + address + "' WHERE [id] = " + user.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update address of this user.");
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(User user) {
        try {
            String sql = "DELETE FROM [user] WHERE [id] = " + user.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not remove user.");
            System.out.println(e.getMessage());
        }
    }

    public void updateClientNote(User client, String note) {
        try {
            String sql = "UPDATE [user] SET [note] = '" + note + "' WHERE [id] = " + client.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update note of this client.");
            System.out.println(e.getMessage());
        }
    }


    public String getClientNote(User client) {
        try {
            String sql = "SELECT [note] FROM [user] WHERE [id] = " + client.getId();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String note = resultSet.getString("note");

                return note;
            }
            return null;

        }
        catch (SQLException e) {
            System.out.println("Error: could not get note.");
            System.out.println(e.getMessage());

            return null;
        }
    }

//________________email__________Casper_og_Mikkel_________________________

    private ArrayList<String> getEmailsByUsersTeam(User user) {
        try {
            ArrayList<String> teamEmails = new ArrayList<>();

            String sql = "SELECT [email] FROM [user] WHERE [team_id] = " + user.getTeam_id();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String email = resultSet.getString("email");

                teamEmails.add(email);
            }

            String teamTitle = user.getTeam_title();

            return teamEmails;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get emails by team.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public void sendTaskEmailsToTeam(User user) {
        Email emailToTeam = EmailBuilder.startingBlank()
                .from("Resono", "eamvstudiegruppe5@gmail.com")
                .to("Klient", getEmailsByUsersTeam(user))
                .withSubject("Ny opgave til dig")
                .withPlainText("Hej " + user.getTeam_title() + "\n" +
                                  "I har modtaget en ny opgave" + "\n" +
                                  "Log ind på Resono for at se den")
                .buildEmail();

        MailerBuilder
                .withSMTPServer("Smtp.gmail.com", 587, "eamvstudiegruppe5@gmail.com", "heovzisxjckdyjcs")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(emailToTeam);
    }

    public void sendTaskEmailToClient(User user) {
        Email emailToClient = EmailBuilder.startingBlank()
                .from("Resono", "eamvstudiegruppe5@gmail.com")
                .to("Klient", user.getEmail())
                .withSubject("Ny opgave til dig")
                .withPlainText("Hej " + user.getFirstname() + " " + user.getLastname() + "\n" +
                                  "Du har modtaget en ny opgave" + "\n" +
                                  "Log ind på Resono for at se den")
                .buildEmail();

        MailerBuilder
                .withSMTPServer("Smtp.gmail.com", 587, "eamvstudiegruppe5@gmail.com", "heovzisxjckdyjcs")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(emailToClient);
    }

    public void giveTaskToClient(User user, Task task) {
        try {
            String sql = "INSERT INTO [user_task] ([user_id], [task_id]) " +
                         "VALUES ('" + user.getId() + "', '" + task.getId() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add user_task.");
            System.out.println(e.getMessage());

        }
    }

    public void giveTaskToTeam(User user, Task task) {
        try {
            String sql = "INSERT INTO [user_task] ([user_id], [task_id]) " +
                         "VALUES ('" + user.getId() + "', '" + task.getId() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add user_task.");
            System.out.println(e.getMessage());

        }
    }
//________________________________________________________________

    public ArrayList<Task> getAllTasks() {
        try {
            ArrayList<Task> allTasks = new ArrayList<>();

            String sql = "SELECT * FROM [task]";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");

                allTasks.add(new Task(id, title));
            }

            return allTasks;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get all tasks.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Task> getTasksByTag(Tag tag) {
        try {
            ArrayList<Task> tasksByTag = new ArrayList<>();

            String sql = "SELECT DISTINCT [id], [title] FROM [task] " +
                         "JOIN [tag_task] ON [task].[id] = [tag_task].[task_id] " +
                         "WHERE [tag_task].[tag_id] = " + tag.getId();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");

                tasksByTag.add(new Task(id, title));
            }

            return tasksByTag;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get tasks by tag.");
            System.out.println(e.getMessage());

            return null;
        }
    }

//_____________________task_controller______________________________________

    public ArrayList<File> getFilesByTask(Task task) {
        try {
            ArrayList<File> filesByTask = new ArrayList<>();

            String sql = "SELECT [id], [file], [title] FROM [file] " +
                         "JOIN [task_file] ON [file].[id] = [task_file].[file_id] " +
                         "WHERE [task_file].[task_id] = " + task.getId();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String file = resultSet.getString("file");
                String title = resultSet.getString("title");

                filesByTask.add(new File(id, file, title));
            }

            return filesByTask;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get files by task.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Video> getVideosByTask(Task task) {
        try {
            ArrayList<Video> videosByTask = new ArrayList<>();

            String sql = "SELECT [id], [url], [title] FROM [video] " +
                         "JOIN [task_video] ON [video].[id] = [task_video].[video_id] " +
                         "WHERE [task_video].[task_id] = " + task.getId();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String URL = resultSet.getString("url");
                String title = resultSet.getString("title");

                videosByTask.add(new Video(id, URL, title));
            }

            return videosByTask;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get videos by task.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public void deleteTaskFile(Task task, File file) {
        try {
            String sql = "DELETE FROM [task_file] " +
                         "WHERE [task_id] = " + task.getId() +
                         "AND [file_id] = " + file.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not remove task_file.");
            System.out.println(e.getMessage());
        }
    }

    public void deleteTaskVideo(Task task, Video video) {
        try {
            String sql = "DELETE FROM [task_video] " +
                         "WHERE [task_id] = " + task.getId() +
                         "AND [video_id] = " + video.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not remove task_video.");
            System.out.println(e.getMessage());
        }
    }

    public void addFileToTask(Task task, File file) {
        try {
            String sql = "INSERT INTO [task_file] ([task_id], [file_id]) " +
                         "VALUES ('" + task.getId() + "', '" + file.getId() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add task_file.");
            System.out.println(e.getMessage());

        }
    }

    public void addVideoToTask(Task task, Video video) {
        try {
            String sql = "INSERT INTO [task_video] ([task_id], [video_id]) " +
                         "VALUES ('" + task.getId() + "', '" + video.getId() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add task_video.");
            System.out.println(e.getMessage());

        }
    }

    public void addTagToTask(Task task, Tag tag) {
        try {
            String sql = "INSERT INTO [tag_task] ([tag_id], [task_id]) " +
                         "VALUES ('" + tag.getId() + "', '" + task.getId() + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add tag_task.");
            System.out.println(e.getMessage());

        }
    }

    public void updateTaskTitle(Task task) {
        try {
            String sql = "UPDATE [task] SET [title] = '" + task.getTitle() + "' WHERE [id] = " + task.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update task.");
            System.out.println(e.getMessage());
        }
    }

    public void deleteTask(Task task) {
        try {
            String sql = "DELETE FROM [task] WHERE [id] = " + task.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not remove task.");
            System.out.println(e.getMessage());
        }
    }

//_________________login_______mikkel___________________

    public User getUserByEmail(String emailInput) {
        try {

            String sql = "SELECT [id], [email], [pincode], [user_type_id] " +
                         "FROM [user] WHERE [email] = '" + emailInput + "'";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                int pincode = resultSet.getInt("pincode");
                int user_type_id = resultSet.getInt("user_type_id");

                return new User(id, email, pincode, user_type_id);
            }

            return null;
        }
        catch (SQLException e) {
            System.out.println("Error: email does not exist.");
            System.out.println(e.getMessage());

            return null;
        }
    }

//___________client_scene______mikkel_____________________

    public ArrayList<Task> getTasksByClient(User client) {
        try {
            ArrayList<Task> clientTasks = new ArrayList<>();

            String sql = "SELECT DISTINCT [task].[id], [task].[title] FROM [task] " +
                         "JOIN [user_task] ON [task].[id] = [user_task].[task_id] " +
                         "WHERE [user_task].[user_id] = " + client.getId();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");

                clientTasks.add(new Task(id, title));
            }

            return clientTasks;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get tasks by client.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public byte[] getFile(File file) {
        try {
            String sql = "SELECT [file] FROM [file] WHERE [id] = " + file.getId();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String hexString = resultSet.getString("file");

                //konversion fra hexstring til bytearray
                /*
                (Afsnit 6.7 i rapporten)
                Konversionen fungerer ved at oprette et bytearray med halv længde, af vores hexstring. Det er fordi
                vores hexstring består af en kontinuerlig række bytes, hver repræsenteret af 2 tegn fra Base-16 (0123456789abcdef).
                Det itereres startende fra index 0, og index øges med 2 ved hver iteration, for at få fat på hvert byte.
                 */
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
            System.out.println("Error: could not get file.");
            System.out.println(e.getMessage());

            return new byte[1];
        }
        return new byte[1];
    }

//__________admin_scene______________________________________________________

    public ArrayList<User> getAllInstructors() {
        try {
            ArrayList<User> allInstructors = new ArrayList<>();

            String sql = "SELECT [id], [email], [phonenumber], [firstname], [lastname], [address] FROM [user]" +
                         "WHERE [user_type_id] = 2";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String phonenumber = resultSet.getString("phonenumber");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String address = resultSet.getString("address");

                allInstructors.add(new User(id, email, phonenumber, firstname, lastname, address));
            }

            return allInstructors;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get all Instructors.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public void createNewInstructor(User user) {
        try {
            String sql = "INSERT INTO [user] ([email], [phonenumber], [firstname], [lastname], [address], [pincode], [user_type_id]) " +
                    "VALUES ('" + user.getEmail() + "', '" +
                    user.getPhonenumber() + "', '" +
                    user.getFirstname() + "', '" +
                    user.getLastname() + "', '" +
                    user.getAddress() + "', '" +
                    generateRandomPincode() + "', '" +
                    2 + "')";

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    user.setId(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add user.");
            System.out.println(e.getMessage());
        }
    }

    public void sendLoginDetailsEmailToInstructor(User user) {

        String fullName = user.getFirstname() + " " + user.getLastname();

        Email welcomeEmail = EmailBuilder.startingBlank()
                .from("Resono", "eamvstudiegruppe5@gmail.com")
                .to(fullName, user.getEmail())
                .withSubject("Loginoplysninger")
                .withPlainText("Hej " + fullName + "\n" +
                        "Herunder finder du dine loginoplysninger:" + "\n" +
                        "E-mail: " + user.getEmail() + "\n" +
                        "Password: " + getLatestUserPincode())
                .buildEmail();

        MailerBuilder
                .withSMTPServer("Smtp.gmail.com", 587, "eamvstudiegruppe5@gmail.com", "heovzisxjckdyjcs")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(welcomeEmail);
    }
}
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

    public void updateTag(Tag tag) {
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

            String sql = "SELECT [id], [file], [title] FROM [file] " +
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

            String sql = "SELECT [id], [url], [title] FROM [video] " +
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

    public User getClientByID(int id) {
        try {
            String sql = "SELECT [email], [phonenumber], [firstname], [lastname], [address], [team].[title] FROM [user]" +
                         "JOIN [team] ON [user].[team_id] = [team].[id]" +
                         "WHERE [user].[id] = " + id;

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String phonenumber = resultSet.getString("phonenumber");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String address = resultSet.getString("address");
                String team_title = resultSet.getString("title");

                return new User(id, email, phonenumber, firstname, lastname, address, team_title);
            }
            return null;

        }
        catch (SQLException e) {
            System.out.println("Error: could not get client by id.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public String getFullNameByID(int id) {
        try {
            String sql = "SELECT [firstname], [lastname] FROM [user] WHERE [id] = " + id;

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");

                return firstname + " " + lastname;
            }
            return null;

        }
        catch (SQLException e) {
            System.out.println("Error: could not get full name by id.");
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

    public void updateClientFirstname(User client, String firstname) {
        try {
            String sql = "UPDATE [user] SET [firstname] = '" + firstname + "' WHERE [id] = " + client.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update firstname of this client.");
            System.out.println(e.getMessage());
        }
    }

    public void updateClientLastname(User client, String lastname) {
        try {
            String sql = "UPDATE [user] SET [lastname] = '" + lastname + "' WHERE [id] = " + client.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update lastname of this client.");
            System.out.println(e.getMessage());
        }
    }

    public void updateClientEmail(User client, String email) {
        try {
            String sql = "UPDATE [user] SET [email] = '" + email + "' WHERE [id] = " + client.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update email of this client.");
            System.out.println(e.getMessage());
        }
    }

    public void updateClientPhonenumber(User client, String phonenumber) {
        try {
            String sql = "UPDATE [user] SET [phonenumber] = '" + phonenumber + "' WHERE [id] = " + client.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update phonenumber of this client.");
            System.out.println(e.getMessage());
        }
    }

    public void updateClientAddress(User client, String address) {
        try {
            String sql = "UPDATE [user] SET [address] = '" + address + "' WHERE [id] = " + client.getId();

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: could not update address of this client.");
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

    public void sendEmailsToTeam(User user) {
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

    public void sendEmailToClient(User user) {
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

            String sql = "SELECT [id], [title] FROM [task] " +
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
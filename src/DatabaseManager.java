import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    public static void main(String[] args) {
        DatabaseManager project = new DatabaseManager();
        try {
            project.createConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3304/dbex", "root", "abcd1234");
            System.out.println("Database Connection Success");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player");
            while(rs.next()) {
                String name = rs.getString("name");
                int coins = rs.getInt("coins");
                int level = rs.getInt("level");
                int exp = rs.getInt("exp");
                int maxExp = rs.getInt("maxExp");
                System.out.println("The Player Name Is " + name);
                System.out.println(name + " has " + coins + " Coins");
                System.out.println("Warrior " + name + " is LVL. " + level);
                System.out.println("He Has EXP Of " + exp + "/" + maxExp);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

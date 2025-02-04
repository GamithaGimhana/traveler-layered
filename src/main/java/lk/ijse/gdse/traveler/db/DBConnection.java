package lk.ijse.gdse.traveler.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/travelmanagementsystem",
                    "root",
                    "Ijse@1234"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection==null){
            dbConnection=new DBConnection();
        }
        return dbConnection;
    }

    public void reconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();  // Close running connection
            }
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/travelmanagementsystem",
                    "root",
                    "Ijse@1234"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package edu.msu.frib.xal.exl2DB.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author lv
 * @author chu
 */
public class DBTools {

    public static Connection getConnection() {
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/discs_model?rewriteBatchedStatements=true";
        String userName = "root";
        String userAddress = "826529";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, userAddress);
        } catch (Exception e) {
             e.printStackTrace();
        }

        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement state) {
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement state) {
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                 e.printStackTrace();
            }
        }
    }

    
    public static void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
}

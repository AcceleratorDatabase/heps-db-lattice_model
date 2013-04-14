package edu.msu.frib.xal.exl2DB.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.tools.persistence.PersistenceTools;

/**
 *
 * @author lv
 * @author chu
 */
public class DBTools {

    public static Connection getConnection(String driver, String url, String user, String password) {
        Connection conn = null;
        Map properties = PersistenceTools.getPersistenceParameters(driver, url, user, password);
        String driver1 = (String) properties.get("javax.persistence.jdbc.driver");
        String url1 = (String) properties.get("javax.persistence.jdbc.url") + "?rewriteBatchedStatements=true";
        // String url = "jdbc:mysql://localhost:3306/discs_model?rewriteBatchedStatements=true";

        try {
            Class.forName(driver1);
            conn = DriverManager.getConnection(url1, user, password);
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return conn;
    }

    public static Connection getConnection(String user, String password) {
        Connection conn = null;
        Map properties = ReadPersistenceXML.getPropMap();
        String driver = (String) properties.get("javax.persistence.jdbc.driver");
        String url = properties.get("javax.persistence.jdbc.url") + "?rewriteBatchedStatements=true";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection() {
        Connection conn = null;
        Map properties = ReadPersistenceXML.getPropMap();
        String driver = (String) properties.get("javax.persistence.jdbc.driver");
        String url = properties.get("javax.persistence.jdbc.url") + "?rewriteBatchedStatements=true";
        String user = (String) properties.get("javax.persistence.jdbc.user");
        String password = (String) properties.get("javax.persistence.jdbc.password");
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
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

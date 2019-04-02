package helpers.database;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;

import helpers.files.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBHelper {

  static final String DRIVER_NAME = "org.hsqldb.jdbc.JDBCDriver";
  static final String URL = "jdbc:hsqldb:file:db-data/eventDatabase";
  static final String USERNAME = "sa";
  static final String PASSWORD = "";

  private static Logger log = LoggerFactory.getLogger(DBHelper.class);

  private DBHelper() {
  }

  static {
    try {
      Class.forName(DRIVER_NAME);
    } catch (ClassNotFoundException e) {
      log.info("Driver class not found");
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USERNAME, PASSWORD);
  }

  public static void closeConnection(Connection connection) throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }

  public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
    if (preparedStatement != null) {
      preparedStatement.close();
    }
  }

  public static void closeResultSet(ResultSet resultSet) throws SQLException {
    if (resultSet != null) {
      resultSet.close();
    }
  }

  public static void executeStatement(String sql) throws SQLException {
    try (Statement statement = DBHelper.getConnection().createStatement()) {
      statement.execute(sql);
    }
  }
}
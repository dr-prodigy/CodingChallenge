package helpers.database;

import domain.Event;
import domain.LogTypeEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.ValidationException;

public class DBEventHelper {

  Logger log = LoggerFactory.getLogger(DBEventHelper.class);

  private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " +
          "event (id VARCHAR(45), state VARCHAR(45), TIMESTAMP DATE , TYPE INTEGER, " +
          "host VARCHAR (50), duration_ms INT , alert BOOLEAN);";
  private static final String CLEANUP_SQL = "DELETE FROM event;";
  private static final String SELECT_SQL = "SELECT id, state, timestamp, type, host, duration_ms, alert FROM event";
  private static final String INSERT_SQL = "INSERT INTO event " +
          "(id, state,timestamp, type, host, duration_ms, alert) " +
          "VALUES(?,?,?,?,?,?,?)";

  public void init() throws SQLException {
    DBHelper.executeStatement(CREATE_SQL);
    DBHelper.executeStatement(CLEANUP_SQL);
  }

  public List<Event> select() throws SQLException, ValidationException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Event> events = new ArrayList<>();
    try {
      connection = DBHelper.getConnection();
      if (connection != null) {
        preparedStatement = connection.prepareStatement(SELECT_SQL);
        resultSet = preparedStatement.executeQuery();
        log.info("select events => {}", preparedStatement);
        while (resultSet.next()) {
          Event event = new Event();
          event.setId(resultSet.getString("id"));
          event.setHost(resultSet.getString("host"));
          event.setState(resultSet.getString("state"));
          event.setTimestamp(resultSet.getDate("timestamp"));
          event.setType(LogTypeEnum.getEnumFromValue(resultSet.getInt("type")));
          event.setDurationMs(resultSet.getLong("duration_ms"));
          event.setAlert(resultSet.getBoolean("alert"));
          events.add(event);
        }
      }
    } finally {
      DBHelper.closeConnection(connection);
      DBHelper.closePreparedStatement(preparedStatement);
      DBHelper.closeResultSet(resultSet);
    }
    return events;
  }

  public void insert(Event event) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = DBHelper.getConnection();
      if (connection != null) {
        preparedStatement = connection.prepareStatement(INSERT_SQL);
        preparedStatement.setString(1, event.getId());
        preparedStatement.setString(2, event.getState());
        preparedStatement.setDate(3, event.getTimestamp());
        preparedStatement.setInt(4, event.getType() == null ? 1 : event.getType().toValue());
        preparedStatement.setString(5, event.getHost());
        preparedStatement.setInt(6, Math.toIntExact(event.getDurationMs()));
        preparedStatement.setBoolean(7, event.getAlert());
        preparedStatement.execute();
        log.info("insert event => {}",  preparedStatement);
      }
    } finally {
      DBHelper.closeConnection(connection);
      DBHelper.closePreparedStatement(preparedStatement);
    }
  }
}

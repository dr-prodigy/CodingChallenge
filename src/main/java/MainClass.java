import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Event;
import helpers.database.DBEventHelper;
import helpers.parser.EventLogParser;

import javax.xml.bind.ValidationException;

public class MainClass {

  private static Logger log = LoggerFactory.getLogger(MainClass.class);

  public static void main(String[] args) throws SQLException, IOException, URISyntaxException, ValidationException {
    initDatabase();
    insertEvents("input/sample.json");
    logEvents();
  }

  private static void initDatabase() throws URISyntaxException, IOException, SQLException {
    log.info("Initializing DB");
    final DBEventHelper dbEventHelper = new DBEventHelper();
    dbEventHelper.init();
  }

  private static void insertEvents(String logs) throws URISyntaxException, IOException {
    EventLogParser operationalEvent = new EventLogParser();
    final DBEventHelper dbEventHelper = new DBEventHelper();

    log.info("Converting input data");
    List<Event> events = operationalEvent.getEventList(logs);
    log.info("Inserting data in DB");
    Arrays.stream(events.toArray(new Event[0])).forEach((event) -> {
      try {
        dbEventHelper.insert(event);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  private static void logEvents() throws SQLException, ValidationException {
    log.info("Logging collected events");
    final DBEventHelper dbEventHelper = new DBEventHelper();
    List<Event> dbEventList = dbEventHelper.select();
    dbEventList.stream().forEach(event -> log
            .info(String.format("Id: %s duration_ms: %s [ms] alert: %s",
                    event.getId(),
                    event.getDurationMs().toString(),
                    event.getAlert())));
  }
}

package helpers.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Event;
import helpers.files.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class JsonParser {

  Logger log = LoggerFactory.getLogger(JsonParser.class);

  FileHelper fileHelper = new FileHelper();
  ObjectMapper objectMapper = new ObjectMapper();

  public Event[] getEventsFromJson(String path) throws URISyntaxException, IOException {
    log.debug("Parsed path {}", path);
    String jsonContent = fileHelper.loadFile(path);
    return jsonContent != null ? objectMapper.readValue(jsonContent, Event[].class) : new Event[]{};
  }
}

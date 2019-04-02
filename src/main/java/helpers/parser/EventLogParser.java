package helpers.parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;

import domain.Event;

public class EventLogParser {
  private JsonParser jsonParser = new JsonParser();

  public List<Event> getEventList(String logContent) throws IOException, URISyntaxException {
    ArrayList<Event> eventList = new ArrayList<>();
    Map<String, List<Event>> collect = Arrays.asList(jsonParser.getEventsFromJson(logContent))
                                          .stream().collect(Collectors.groupingBy(Event::getId));
    collect.forEach((id, items) -> {
      if (items.size() == 2) {
        Event item1 = items.get(0);
        Event item2 = items.get(1);
        Event completeEvent = new Event();
        completeEvent.setId(id);
        completeEvent.setDurationMs(Math.abs(item1.getTimestamp().getTime() - item2.getTimestamp().getTime()));
        completeEvent.setAlert(completeEvent.getDurationMs() > 4);
        completeEvent.setHost(item1.getHost());
        completeEvent.setState(item1.getState());
        completeEvent.setType(item1.getType());
        eventList.add(completeEvent);
      }
    });
    return eventList;
  }
}

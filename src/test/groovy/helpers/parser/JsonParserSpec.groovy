package helpers.parser

import domain.Event
import helpers.files.FileHelper
import spock.lang.Specification

class JsonParserSpec extends Specification {
    JsonParser SUT

    def setup() {
        SUT = new JsonParser()
        SUT.fileHelper = Mock(FileHelper)
    }

    def 'the log file should return six events' () {
        given:'mock on file service'
        def goodPath = "/logSixEvents.json"
        mockFileService(goodPath)
        when: 'create from json Event array'
        Event[] events = SUT.getEventsFromJson(goodPath)
        then:'the array size is'
        events.size() == 6
    }

    def 'the path is wrong, so the return size is zero' () {
        given:'bad path to json resource'
        def badPath = "bad path, do not exist"
        mockBadFileService(badPath)
        when:
        Event[] events = SUT.getEventsFromJson(badPath)
        then:
        events.size() == 0
    }

    private void mockFileService(String path) {
        SUT.fileHelper.loadFile(path) >> jsonString
    }
    private void mockBadFileService(String path) {
        SUT.fileHelper.loadFile(path) >> null
    }
    def jsonString = "[\n" +
            "  {\n" +
            "    \"id\": \"scsmbstgra\",\n" +
            "    \"state\": \"STARTED\",\n" +
            "    \"type\": \"APPLICATION_LOG\",\n" +
            "    \"host\": \"12345\",\n" +
            "    \"timestamp\": 1491377495212\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"scsmbstgrb\",\n" +
            "    \"state\": \"STARTED\",\n" +
            "    \"timestamp\": 1491377495213\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"scsmbstgrc\",\n" +
            "    \"state\": \"FINISHED\",\n" +
            "    \"timestamp\": 1491377495218\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"scsmbstgra\",\n" +
            "    \"state\": \"FINISHED\",\n" +
            "    \"type\": \"APPLICATION_LOG\",\n" +
            "    \"host\": \"12345\",\n" +
            "    \"timestamp\": 1491377495217\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"scsmbstgrc\",\n" +
            "    \"state\": \"STARTED\",\n" +
            "    \"timestamp\": 1491377495210\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"scsmbstgrb\",\n" +
            "    \"state\": \"FINISHED\",\n" +
            "    \"timestamp\": 1491377495216\n" +
            "  }\n" +
            "]"
}

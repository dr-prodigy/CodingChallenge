package helpers.parser


import spock.lang.Specification

class EventLogParserSpec extends Specification {
    EventLogParser eventLogParser = new EventLogParser()

    def 'build event list'() {
        when:
        def events = eventLogParser.getEventList("input/sample.json")
        then: '3 events should be'
        events.size() == 3
    }

    def 'marked 2 alerts'() {
        when:
        def events = eventLogParser.getEventList("input/sample.json")
        then: 'if duration_ms is greater than 4ms, alert = true, otherwise false'
        def alertsCount = 0
        events.each {
            def thresholdAlert = 4
            if (it.getDurationMs() > thresholdAlert) {
                it.alert
                alertsCount++
            } else !it.alert
        }
        alertsCount == 2
    }
}

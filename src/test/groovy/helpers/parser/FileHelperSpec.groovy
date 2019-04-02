package helpers.parser

import helpers.files.FileHelper
import spock.lang.Specification

class FileHelperSpec extends Specification {
    FileHelper fileService

    def setup() {
        fileService = new FileHelper()
    }

    def 'get content from sample.json'() {
        given: 'create sql'
        def path = "input/sample.json"
        when:
        def from = fileService.loadFile(path)
        then:
        from.contains("scsmbstgra") == true
    }
}

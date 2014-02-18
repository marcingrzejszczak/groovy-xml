package com.blogspot.toomuchcoding.xml._01_parser

import spock.lang.Specification

class TeamSaxParserSpec extends Specification {

    def "should parse and print barcelona squad with sax"() {
        given:
            TeamSaxParser xmlParser = new TeamSaxParser()
        expect:
            xmlParser.parseXml("barcelona_squad.xml")
    }

}

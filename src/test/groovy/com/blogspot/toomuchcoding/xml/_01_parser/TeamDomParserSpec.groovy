package com.blogspot.toomuchcoding.xml._01_parser

import spock.lang.Specification

class TeamDomParserSpec extends Specification {

    def "should parse and print barcelona squad with dom"() {
        given:
            TeamDomParser xmlParser = new TeamDomParser()
        expect:
            xmlParser.parseXml("barcelona_squad.xml")
    }

}

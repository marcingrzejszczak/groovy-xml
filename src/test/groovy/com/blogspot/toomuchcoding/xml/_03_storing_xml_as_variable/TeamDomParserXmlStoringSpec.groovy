package com.blogspot.toomuchcoding.xml._03_storing_xml_as_variable

import com.blogspot.toomuchcoding.xml._01_parser.TeamDomFromStringParser
import spock.lang.Specification

class TeamDomParserXmlStoringSpec extends Specification {

    def "should print part of the fc barcelona squad"() {
        given:
            TeamDomFromStringParser teamDomParser = new TeamDomFromStringParser()
            String barcelonaSquad =
                    "<team name=\"FCBarcelona\">\n" +
                    "    <players>\n" +
                    "        <player position=\"forward\">\n" +
                    "            <name>Lionel</name>\n" +
                    "            <surname>Messi</surname>\n" +
                    "            <number>10</number>\n" +
                    "        </player>\n" +
                    "        <player position=\"forward\">\n" +
                    "            <name>Neymar</name>\n" +
                    "            <surname>Neymar</surname>\n" +
                    "            <number>11</number>\n" +
                    "        </player>\n" +
                    "        <player position=\"midfielder\">\n" +
                    "            <name>Xavi</name>\n" +
                    "            <surname/>\n" +
                    "            <number>6</number>\n" +
                    "        </player>\n" +
                    "        <player position=\"midfielder\">\n" +
                    "            <name>Andrés</name>\n" +
                    "            <surname>Iniesta</surname>\n" +
                    "            <number>8</number>\n" +
                    "        </player>\n" +
                    "    </players>\n" +
                    "</team>"
            expect:
                teamDomParser.parseXmlFromString(barcelonaSquad)
    }
}

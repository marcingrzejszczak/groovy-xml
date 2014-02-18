package com.blogspot.toomuchcoding.xml._03_storing_xml_as_variable

import spock.lang.Specification

class XmlSlurperXmlStoringSpec extends Specification {

    def "should print part of the fc barcelona squad"() {
        given:
            String barcelonaSquad = """
                                    <team name="FCBarcelona">
                                        <players>
                                            <player position="forward">
                                                <name>Lionel</name>
                                                <surname>Messi</surname>
                                                <number>10</number>
                                            </player>
                                            <player position="forward">
                                                <name>Neymar</name>
                                                <surname>Neymar</surname>
                                                <number>11</number>
                                            </player>
                                            <player position="midfielder">
                                                <name>Xavi</name>
                                                <surname/>
                                                <number>6</number>
                                            </player>
                                            <player position="midfielder">
                                                <name>Andrés</name>
                                                <surname>Iniesta</surname>
                                                <number>8</number>
                                            </player>
                                        </players>
                                    </team>
                                    """
            def team = new XmlSlurper().parseText(barcelonaSquad)
        expect:
            team.players.player.each { println "Player name [$it.name], surname [$it.surname], position [${it.@position}], number [$it.number]" }
    }
}
